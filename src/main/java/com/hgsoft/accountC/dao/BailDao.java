package com.hgsoft.accountC.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.Bail;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

/**
 * @FileName BailDao.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月19日 上午9:58:40 
*/
@Repository
public class BailDao extends BaseDao{

	public  Pager bailList(Pager pager, Customer customer, String cardNo, String bankAccount) {
		SqlParamer params = new SqlParamer();
		
		//保证金新增部分的sql
		StringBuffer sql = new StringBuffer(
				  " select bl.id as bailid,null refundInfoId,aa.bankaccount," 
				+ "   bl.CARDNO,bl.PayFlag,bl.BailFee,bl.AppState,bl.Up_Date,bl.applyTime,"
				+ "   bl.operName,vi.vehiclePlate,vi.vehicleColor "
				+ " from csms_bail bl "
				+ " join CSMS_SubAccount_Info si on si.id = bl.accountID " 
				+ " join CSMS_AccountC_apply aa on aa.id = si.ApplyID " 
				+ " left join CSMS_AccountC_info ai on ai.cardNo = bl.cardNo " 
				+ " left join CSMS_CarObuCard_info ci on ci.AccountCID = ai.id " 
				+ " left join CSMS_Vehicle_Info vi on vi.id = ci.vehicleId " 
				+ " where PayFlag='0' and bl.userno=? "
				);
		
		params.getList().add(customer.getUserNo());
		
		if(StringUtil.isNotBlank(bankAccount)){
			//params.eq("aa.bankaccount", bankAccount);
			sql.append(" and aa.bankaccount=? ");
			params.getList().add(bankAccount);
		}
		if(StringUtil.isNotBlank(cardNo)){
			//params.eq("bl.cardNo", cardNo);
			sql.append(" and bl.cardNo=? ");
			params.getList().add(cardNo);
		}
		//保证金退还部分的sql
		sql.append(
				  "   union all "
				+ " select null bailid,r.id refundInfoId,r.bankAccount,"
				//'1' payFlag  将payFlag设为1模拟csms_bail表的payFlag
				+ "   '' cardNo,'1' payFlag,r.CurrentRefundBalance BailFee,r.auditStatus AppState,r.RefundApplyTime Up_Date,r.RefundApplyTime applyTime,"
				+ "   r.operName,'' vehiclePlate,'' vehicleColor "
				+ " from csms_refundInfo r "
				+ " where r.RefundType='2' and r.mainid=? ");
		
		params.getList().add(customer.getId());
		
		if(StringUtil.isNotBlank(bankAccount)){
			//params.eq("r.bankaccount", bankAccount);
			sql.append(" and r.bankaccount=? ");
			params.getList().add(bankAccount);
		}
		if(StringUtil.isNotBlank(cardNo)){
			//params.eq("cardNo", cardNo);
			sql.append(" and cardNo=? ");
			params.getList().add(cardNo);
		}
		
		Object[] objects = params.getList().toArray();
		sql.append(" order by applyTime desc ");
		return this.findByPages(sql.toString(), pager,objects);
	}
	
	public  Pager findByPage(Pager pager, Customer customer,AccountCApply accountCApply,AccountCInfo accountCInfo) {
		StringBuffer sql = new StringBuffer(
				" select bailfeeSum/100 bailfeeSum,sb.id sbid ,aa.bankname,aa.bankaccount,aa.accname,ai.cardno,ai.state," +
				" ai.accountid,f.count,g.countall countall,CASE state  WHEN  '2' THEN '0'  ELSE to_char(bailfeeSum/f.count/100)  END countNum from ( "+
				" select sum(bailfee) bailfeeSum ,accountid from csms_bail b where userno = '"+customer.getUserNo()+"'  and "+
				" ( b.AppState is  null or b.AppState=1 or b.AppState=3)  "+
				" group by accountid ) a   join csms_subaccount_info sb on a.accountid = sb.id "+
				" join csms_accountc_apply aa on sb.applyid = aa.id "+
				" left join csms_accountc_info ai on ai.accountid = sb.id "+
				" left join (select count(1) count,accountid from csms_accountc_info a where " +
				" a.customerid="+customer.getId()+" and state != 2  group by a.accountid) f "+
				" on ai.accountid = f.accountid" +
				" left join (select count(1) countall,accountid " +
				" from csms_accountc_info a where  a.customerid="+customer.getId()+"  group by a.accountid) g  " +
				" on ai.accountid = g.accountid " +
				" where 1=1 "
		        );
		
		SqlParamer params=new SqlParamer();
		if(accountCApply!=null && StringUtil.isNotBlank(accountCApply.getBankAccount())){
			params.eq("aa.bankAccount", accountCApply.getBankAccount());
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by sb.id desc  ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	
	

	public List<Map<String, Object>> prepareAddInfo(String userNo, String cardNo, String bankAccount) {
		StringBuffer sql = new StringBuffer(" select organ,idtype,idcode,addr,app.LinkMan,bank,accName," +
				"bankName,bankAccount,sub.id as subid  from csms_accountc_apply app "
				+ " join csms_customer cus on app.customerid=cus.id join csms_subaccount_info sub " +
				" on sub.applyid=app.id left join csms_accountc_info acc on acc.customerid=cus.id"
				+ " where 1=1 ");
		SqlParamer params = new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			params.eq("cus.userno", userNo);
		}
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("acc.cardno", cardNo);
		}
		if(StringUtil.isNotBlank(bankAccount)){
			params.eq("app.bankaccount", bankAccount);
		}
		if (StringUtils.isBlank(params.getParam())) {
			throw new ApplicationException("BailDao.prepareAddInfo条件为空");
		}
		sql = sql.append(params.getParam());
		Object[] objects = params.getList().toArray();
		return jdbcUtil.getJdbcTemplate().queryForList(sql.toString(), objects);
	}

	public void save(Bail bail,Customer customer) {
		/*StringBuffer sql=new StringBuffer("insert into csms_bail ");
		Map map = FieldUtil.getPreFieldMap(Bail.class,bail);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));*/
		StringBuffer s = new StringBuffer("insert into csms_bail(ID,userNo,cardno,accountID,PayFlag,BailFee," +
				"Up_Date,UpReason,Dflag,SetTime,tradingType,OperID,operNo,operName," +
				"PlaceID,placeNo,placeName,BankNo,BankMember,BankOpenBranches,AppState," +
				"AppPlaceId,Approver,approverNo,approverName,AppTime,BailAllFee,BailFrozenBalance,applyTime)" +
				" select ");
		s.append(bail.getId()+",'"+bail.getUserNo()+"','"+bail.getCardno()+"',"+bail.getAccountId()+",'"+bail.getPayFlag()+"',"+bail.getBailFee()+",");
		s.append("sysdate,'"+bail.getUpreason()+"','"+bail.getDflag()+"',sysdate,");
		
		if(bail.getTradingType()!=null)s.append("'"+bail.getTradingType()+"',");
		else s.append("null,");
		
		
		s.append(bail.getOperId()+",'"+bail.getOperNo()+"','"+bail.getOperName()+"',"+bail.getPlaceId()+",'"+bail.getPlaceNo()+"','");
		s.append(bail.getPlaceName()+"','"+bail.getBankNo()+"','"+bail.getBankMember()+"','"+bail.getBankOpenBranches()+"',");
		if(bail.getAppState()!=null)s.append("'"+bail.getAppState()+"',");
		else s.append("null,");
		if(bail.getAppPlaceId()!=null)s.append(bail.getAppPlaceId()+",");
		else s.append("null,");
		if(bail.getApprover()!=null)s.append(bail.getApprover()+",");
		else s.append("null,");
		if(bail.getApproverNo()!=null)s.append("'"+bail.getApproverNo()+"',");
		else s.append("null,");
		if(bail.getApproverName()!=null)s.append("'"+bail.getApproverName()+"',");
		else s.append("null,");
		if(bail.getAppTime()!=null)s.append("sysdate,");
		else s.append("null,");
		s.append("b.BailFee,b.BailFrozenBalance, ");
		if(bail.getApplyTime()!=null)s.append("sysdate");
		else s.append("null");
		s.append(" from CSMS_BailAccount_Info b where MainID = ?");
		
		saveOrUpdate(s.toString(), customer.getId());
	}

	public Bail findById(Long id) {
		String sql = "select * from csms_bail where id=?";
		List<Bail> bails = super.queryObjectList(sql, Bail.class, id);
		if (bails == null || bails.isEmpty()) {
			return null;
		}
		return bails.get(0);
	}

	public BigDecimal findAccountIDByBankAccount(String bankAccount) {
		String sql = " select sub.id as accountID from csms_accountc_apply app join csms_subaccount_info sub on sub.applyid=app.id where bankAccount=?";
		return (BigDecimal)queryList(sql, bankAccount).get(0).get("accountID");
	}
	
	public void update(Bail bail){
		Map map = FieldUtil.getPreFieldMap(Bail.class,bail);
		StringBuffer sql=new StringBuffer("update csms_bail set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),bail.getId());
	}
	
	/**
	 * 客服提供给营运的保证金退款申请列表查询
	 * @param pager
	 * @param userNo
	 * @param bankNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Pager findBailBackAppList(Pager pager,String userNo,String organ,String IdCode,String bankNo,String startTime,String endTime,String state){
		StringBuffer sql = new StringBuffer("select b.userNo,cr.Organ,b.BankNo,b.BankOpenBranches,nvl(aa.bank,aa.bankSpan),"
				+" b.CARDNO,-b.BailFee bailFee,0 as Owingbail,-(b.BailFee-0) as actualBackBail,b.applyTime,b.AppState,b.ID bailId"
				+" from CSMS_bail b join CSMS_Customer cr on cr.UserNo=b.userNo join CSMS_SubAccount_Info si "
				+" on si.id=b.accountID join CSMS_AccountC_apply aa on aa.id=si.ApplyID and 1=1 and b.payflag='1' ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SqlParamer params=new SqlParamer();
		
		if(StringUtil.isNotBlank(userNo)){
			params.eq("b.userNo", userNo);
		}
		if(StringUtil.isNotBlank(organ)){
			params.eq("cr.organ", organ);
		}
		if(StringUtil.isNotBlank(IdCode)){
			params.eq("cr.IdCode", IdCode);
		}
		if(StringUtil.isNotBlank(bankNo)){
			params.eq("b.BankNo", bankNo);
		}
		if(StringUtil.isNotBlank(startTime)){
			params.geDate("b.applyTime", startTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(endTime)){
			params.leDate("b.applyTime", endTime+" 23:59:59");
		}
		if(StringUtil.isNotBlank(state)){
			params.eq("b.AppState", state);
		}
		
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by b.id desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);

		return pager;
	}
	/**
	 * 客服提供给营运的接口用，保证金退还申请详情查询
	 * bailId(保证金设置表详情)
	 * @return
	 */
	public Map<String , Object> findBailByID(Long bailId){
		String sql = "select b.id bailId,cr.organ,cr.idType,cr.idCode,cr.addr,aa.LinkMan,aa.bank,aa.accName," +
				"aa.bankName,aa.bankAccount,-b.bailFee as bailFee,b.cardno,b.bankNo,b.bankMember,b.bankOpenBranches,b.appState," +
				"b.UpReason,ml.Type,ml.picAddr "
				+ " from CSMS_bail b join CSMS_Customer cr on cr.UserNo=b.UserNo and b.id=? join CSMS_SubAccount_Info si on" +
				" si.id=b.accountID join CSMS_AccountC_apply aa on aa.id=si.ApplyID left join CSMS_Material ml on ml.BUSSINESSID=b.id and ml.type='24' ";
		
		List<Map<String, Object>> list = this.queryList(sql, bailId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 客服保证金新增详情
	 * @param bailId
	 * @return
     */
	public Map<String , Object> findBailAddDetail(Long bailId){
		String sql = "select cr.Organ,cr.IdType,cr.IdCode,cr.Addr,aa.LinkMan,aa.bank,aa.accName,aa.accountType,aa.bankSpan," 
				+" aa.bankName,aa.bankAccount,vi.vehiclePlate,vi.vehicleColor,ci.Bail,b.cardno,b.BailFee,b.UpReason,b.tradingType "
				+" from CSMS_bail b "
				+" join CSMS_Customer cr on cr.UserNo=b.UserNo and b.id=? "
				+" join CSMS_SubAccount_Info si on si.id=b.accountID "
				+" join CSMS_AccountC_apply aa on aa.id=si.ApplyID "
				+" join CSMS_AccountC_info ci on ci.cardNo=b.cardNo "
				+" left join CSMS_CarObuCard_info coc on coc.AccountCID=ci.id " 
				+" left join CSMS_Vehicle_Info vi on vi.id=coc.VehicleID ";

		List<Map<String, Object>> list = this.queryList(sql, bailId);
		if(list.size()>0){
			return list.get(0);
		}

		return null;
	}

	/**
	 * 客服保证金退还详情
	 * @param bailId
	 * @return
     */
	public Map<String , Object> findBailBackDetail(Long refundInfoId){
		String sql = "select cr.Organ,cr.IdType,cr.IdCode,cr.Addr,aa.LinkMan,aa.bank,aa.accName,aa.accountType,aa.bankSpan," 
				+ " aa.bankName,aa.bankAccount,r.id refundInfoId,r.currentRefundBalance,r.BankNo," 
				+ " r.BankMember,r.BankOpenBranches,r.AuditStatus AppState,r.bailBackReason UpReason,r.CurrentRefundBalance bail "
				+ " from csms_refundInfo r "
				+ " join CSMS_Customer cr on cr.id=r.mainid "
				+ " join CSMS_AccountC_apply aa on aa.bankAccount=r.bankAccount and aa.shutDownStatus='0' "
				+ " where r.id=? ";
		List<Map<String, Object>> list = this.queryList(sql, refundInfoId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
