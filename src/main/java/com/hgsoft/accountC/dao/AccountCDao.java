package com.hgsoft.accountC.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class AccountCDao extends BaseDao{

	private static final Logger logger = LoggerFactory.getLogger(AccountCDao.class);

	public boolean findByMacaoCardCustomerAndCardNo(MacaoCardCustomer macaoCardCustomer,String cardNo){
		long id = 0;
		if(macaoCardCustomer != null)
			id = macaoCardCustomer.getId();
		StringBuffer sql = new StringBuffer("select ai.* from csms_accountc_info ai join csms_cardholder_info ci on ai.id=ci.typeid join csms_macao_bankaccount mb on ci.macaobankaccountid=mb.id join csms_macao_card_customer mcc on mcc.id=mb.mainid where mcc.id=? and ai.cardno=? and rownum=1 ");
		List<Map<String,Object>> list = queryList(sql.toString(), id, cardNo);
		if(list.size()>0)
			return true;
		return false;
	}
	
	public AccountCInfo findById(Long id) {
		String sql = "select * from csms_accountc_info where id=?";
		List<AccountCInfo> accountCInfos = super.queryObjectList(sql, AccountCInfo.class, id);
		if (accountCInfos == null || accountCInfos.isEmpty()) {
			return null;
		} else if (accountCInfos.size() > 1) {
			logger.error("csms_accountc_info中id[{}]有多条[{}]记录", id, accountCInfos.size());
			throw new ApplicationException("AccountCDao.findById有多条记录");
		}
		return accountCInfos.get(0);
	}
	
	public AccountCInfo find(AccountCInfo accountCInfo) {
		if (accountCInfo == null) {
			return null;
		}

		Map map = FieldUtil.getPreFieldMap(AccountCInfo.class, accountCInfo);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("AccountCDao.find查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from csms_accountc_info where 1=1 ");
		sql.append(condition);
		sql.append(" order by id");

		List<AccountCInfo> accountCInfos = super.queryObjectList(sql.toString(), AccountCInfo.class, ((List) map.get("paramNotNull")).toArray());
		if (accountCInfos == null || accountCInfos.isEmpty()) {
			return null;
		} else if (accountCInfos.size() > 1) {
			logger.error("AccountCDao.find有多条[{}]记录", accountCInfos.size());
			throw new ApplicationException("AccountCDao.find有多条记录");
		}
		return accountCInfos.get(0);
	}
	
	public AccountCInfo findByCardNo(String cardNo) {
		return find(new AccountCInfo(cardNo));
	}

	public List<Map<String, Object>> findByBankAcount(String acbAccount) {
		String sql = "select * from csms_accountc_info ai join CSMS_SubAccount_Info si on si.id=ai.AccountID join " +
				" CSMS_AccountC_apply aa on aa.id=si.ApplyID and aa.bankAccount=?";
		List<Map<String, Object>> list = queryList(sql,acbAccount);
		return list;
	}

	/*public List<Map<String, Object>> findAccountcinfoList(PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		List<Map<String, Object>> list = new ArrayList<>();
		if(paymentCardBlacklistRecv!=null){
			if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getCardCode())){
				String sql = "select * from csms_accountc_info ai where ai.cardno = ?";
				list = queryList(sql,paymentCardBlacklistRecv.getCardCode());
			}else if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getAcbAccount())){
				String sql = "select * from csms_accountc_info ai join CSMS_SubAccount_Info si on si.id=ai.AccountID join " +
						" CSMS_AccountC_apply aa on aa.id=si.ApplyID and aa.bankAccount=?";
				list = queryList(sql,paymentCardBlacklistRecv.getAcbAccount());
			}
		}
		return list;
	}*/
	
	public AccountCInfo findByCardNoAndCustomerId(String cardNo,Long customerId) {
		AccountCInfo c = new AccountCInfo();
		c.setCardNo(cardNo);
		c.setCustomerId(customerId);
		return find(c);
	}
	
	public void update(AccountCInfo accountCInfo) {
		/*StringBuffer sql=new StringBuffer("update csms_accountc_info set ");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("nameAndValue")+" where id="+accountCInfo.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(AccountCInfo.class,accountCInfo);
		StringBuffer sql=new StringBuffer("update csms_accountc_info set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),accountCInfo.getId());
	}


	public void saveHis(AccountCInfoHis accountCInfoHis) {
		StringBuffer sql=new StringBuffer("insert into csms_accountc_info_his(");
		sql.append(FieldUtil.getFieldMap(AccountCInfoHis.class,accountCInfoHis).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountCInfoHis.class,accountCInfoHis).get("valueStr")+")");
		save(sql.toString());
	}
	
	/**
	 * 根据ID删除记帐卡发行信息
	 * @param id
	 */
	public void delete(Long id){
		String sql = "delete from csms_accountc_info where id=?";

		delete(sql, id);
	}
	public void updateBind(String state,Long id){
		String sql="update csms_accountc_info set Bind=? where id=?";
		saveOrUpdate(sql.toString(),state,id);
	}
	/**
	 * 记帐卡发行保存
	 * @param accountCInfo
	 */
	public void save(AccountCInfo accountCInfo) {
		/*StringBuffer sql=new StringBuffer("insert into csms_accountc_info(");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("valueStr")+")");
		save(sql.toString());*/
		accountCInfo.setHisSeqId(-accountCInfo.getId());
		Map map = FieldUtil.getPreFieldMap(AccountCInfo.class,accountCInfo);
		StringBuffer sql=new StringBuffer("insert into csms_accountc_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public List<Map<String, Object>> findByList(AccountCInfo accountCInfo){
		String sql  ="select * from CSMS_AccountC_info where AccountID = ? ";
		List<Map<String, Object>> list = queryList(sql,accountCInfo.getAccountId());
		return list;
	}
	/**
	 *迁移列表：注销以外的普通的记帐卡均可迁移、没有过户业务
	 * @param accountCInfo
	 * @return
	 */
	public List<Map<String, Object>> findCardNoByList(AccountCInfo accountCInfo){
		/*String sql  ="select CardNo,customerID,AccountID,State from CSMS_AccountC_info"
				+ " where AccountID = ? and (state='0' or state='3') and CardNo not in("
				+ " select cardno from csms_migrate m join csms_migrate_detail md on m.id = md.migrateid" 
				+ " where appstate = '1' or appstate = '2' "
				+ " union all  "
				+ " select cardno from CSMS_transfer_apply t  join CSMS_transfer_detail " 
				+ " td on t.id = td.TransferID  where appstate = '1' or appstate = '2' " 
				+ " union all  "
				+ " select CardNo from csms_dark_list d where d.gencau!=1"
				+ " )";*/
		String sql  ="select CardNo,customerID,AccountID,State from CSMS_AccountC_info"
				+ " where AccountID = ? and (state!='2') and CardNo not in("
				+ " select cardno from csms_migrate m join csms_migrate_detail md on m.id = md.migrateid" 
				+ " where appstate = '1' or appstate = '2' or appstate='4' "
//				+ " union all  "
//				//csms_dark_list废除
				//尼玛，CSMS_BLACKLIST_STATUS也废除了，老子不查了，在最后保存的时候判断黑名单卡行了吧
//				//+ " select CardNo from csms_dark_list d where d.gencau!=1"
//				+ " select cardNo from CSMS_BLACKLIST_STATUS where cardno is not null "
				+ " )";
		List<Map<String, Object>> list = queryList(sql,accountCInfo.getAccountId());
		return list;
	}
	
	public int[] batchUpdate(final List<AccountCInfo> list) {  
        String sql = "update csms_accountc_info set AccountID = ?,HisSeqID=? where CardNo = ?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				AccountCInfo accountCInfo = list.get(i);
				ps.setLong(1, accountCInfo.getAccountId());
				ps.setLong(2, accountCInfo.getHisSeqId());
				ps.setString(3, accountCInfo.getCardNo());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    } 
	
	public int[] batchUpdateWithCustomer(final List<AccountCInfo> list) {  
        String sql = "update csms_accountc_info set AccountID = ?,HisSeqID=?,customerID=?,Bind=? where CardNo = ?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				AccountCInfo accountCInfo = list.get(i);
				ps.setLong(1, accountCInfo.getAccountId());
				ps.setLong(2, accountCInfo.getHisSeqId());
				ps.setLong(3, accountCInfo.getCustomerId());
				ps.setString(4, accountCInfo.getBind());
				ps.setString(5, accountCInfo.getCardNo());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    } 

	/**
	 * 记帐卡终止使用列表
	 * @param pager
	 * @param customer
	 * @return
	 */
	public Pager findStopCardByCustomer(Pager pager, Customer customer) {
		StringBuffer sql = new StringBuffer("select a.id,a.code, a.reason,a.remark,a.canceltime,ROWNUM as num  from CSMS_CANCEL a where  a.flag='2' and a.CUSTOMERID = ?");
		sql.append(" order by a.canceltime desc ");
		return this.findByPages(sql.toString(), pager, new Object[] {customer.getId()});
	}
	public List<Map<String, Object>> getAccountCInfoByBank(Long customerId, Long subAccountInfoId) {
		StringBuffer sql = new StringBuffer("select a.id,a.cardno name from csms_accountc_info a where (a.state='0' or a.state='3') and a.AccountID="+subAccountInfoId);
		if (customerId != null) {
			sql.append(" and a.customerid="+customerId);
		}
		//已申请过户的卡号不能再过户
		sql.append(" and a.CardNo not in(select cardno from csms_transfer_apply t join csms_transfer_detail td on t.id = td.TransferID where t.AppState = 1 or t.AppState = 2)");
		//已申请迁移的卡号不能再过户
		sql.append(" and a.cardno not in(select md.cardno from csms_migrate m left join csms_migrate_detail md on m.id=md.migrateid where m.appstate=1 or m.AppState = 2) ");
		//黑名单表不能过户
		sql.append(" and a.cardno not in(select CardNo from csms_dark_list d where d.gencau!=1) ");
		return queryList(sql.toString());
	}

	public AccountCInfo findByCardNoToGain(String cardNo) {
		AccountCInfo temp = null;
		if (StringUtils.isBlank(cardNo)) {
			throw new ApplicationException("AccountCDao.findByCardNoToGain查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select a.* from csms_accountc_info a where a.cardno=?");
		sql.append(" and a.CardNo not in(select OldCardNo from csms_accountc_bussiness where OldCardNo=? and state=7)");
		List<AccountCInfo> accountCInfos = super.queryObjectList(sql.toString(), AccountCInfo.class, cardNo, cardNo);
		if (accountCInfos == null || accountCInfos.isEmpty()) {
			return null;
		} else if (accountCInfos.size() > 1) {
			logger.error("AccountCDao.findByCardNoToGain有多条[{}]记录", accountCInfos.size());
			throw new ApplicationException("AccountCDao.findByCardNoToGain有多条记录");
		}
		return accountCInfos.get(0);
	}
	
	public AccountCInfo findByCustomerId(Long id) {
		String sql = "select * from csms_accountc_info where customerId=?";
		List<AccountCInfo> accountCInfos = super.queryObjectList(sql.toString(), AccountCInfo.class, id);
		if (accountCInfos == null || accountCInfos.isEmpty()) {
			return null;
		}
		return accountCInfos.get(0);
	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		
		StringBuffer sql=new StringBuffer("update csms_accountc_info set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(IssueTime,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(IssueTime,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		saveOrUpdate(sql.toString(),settleDay,startTime,endTime);
	}
	
	public Pager findByCustomer(Pager pager, Customer customer) {
		String sql = "select a.*,ROWNUM as num from csms_accountc_info a where a.customerId=?";
		sql=sql+(" order by a.ID ");
		return super.findByPages(sql.toString(), pager, new Object[]{customer.getId()});
	}

	public boolean hasMigrateOrTransfer(String cardNo) {
		String sql = "select count(*) from csms_accountc_info a where "
				+ " a.cardno not in(select md.cardno from csms_migrate m left join csms_migrate_detail md on m.id=md.migrateid where m.appstate=1 or m.appstate=2) "
				+ " and a.cardno not in(select td.cardno from csms_transfer_apply t left join csms_transfer_detail td on t.id=td.transferid where t.appstate=1 or t.appstate=2)"
				+ " and a.cardNo=?";
		int count = count(sql, cardNo);
		if (count == 0) {
			return true;
		}
		return false;
	}

	public Pager findStopCardByCustomer(Pager pager, Customer customer, AccountCInfo accountCInfo) {
		StringBuffer sql = new StringBuffer("select a.id,a.code, a.reason,a.remark,a.canceltime,ROWNUM as num  from CSMS_CANCEL a where  a.flag='2' and a.CUSTOMERID = "+customer.getId());
		if(accountCInfo!=null){
			sql.append(" and a.code="+accountCInfo.getCardNo());
		}
		sql.append(" order by a.canceltime desc ");
		return this.findByPages(sql.toString(), pager, null);
	}
	
	/**
	 * 联营卡
	 * @param pager
	 * @param customer
	 * @param accountCInfo
	 * @return
	 */
	public Pager findLianCard(Pager pager, Customer customer, AccountCInfo accountCInfo,Date startTime,Date endTime) {
		StringBuffer sql = new StringBuffer("select ID,CardNo,customerID,State,Cost,"
				+ "RealCost,IssueTime,startDate,"
				+ "IssueOperId,IssuePlaceId  "
				+ "from CSMS_AccountC_info where customerID = "+customer.getId());
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(accountCInfo.getCardNo())){
			params.eq("CardNo", accountCInfo.getCardNo());
		}
		if(startTime !=null){
			params.geDate("IssueTime", params.getFormat().format(startTime));
		}
		if(endTime !=null){
			params.leDate("IssueTime", params.getFormatEnd().format(endTime));
		}
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by IssueTime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	/**
	 * 香港联营卡状态查询
	 * @param pager
	 * @param customer
	 * @param accountCInfo
	 * @return
	 */
	public Pager findJointCardState(Pager pager, Customer customer, AccountCInfo accountCInfo) {
		StringBuffer sql = new StringBuffer("select cardno, state, status, gentime from( "
				+ "select cardno, state, status, gentime, row_number() over (partition by cardno order by gentime desc) as group_idx from ( "
				+ "select ac.id as id, ac.cardno as cardno, ac.state as state, bw.status as status, bw.gentime as gentime "
				+ "from csms_accountc_info ac "
				+ "left join csms_black_list_water bw on ac.cardno=bw.cardno "
				+ "left join csms_customer cu on ac.customerid=cu.id ");
		sql.append("where cu.id=" + customer.getId());
		sql.append(") "
				 + ") where group_idx=1 ");
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(accountCInfo.getCardNo())) {
			params.eq("cardno", accountCInfo.getCardNo());
		} // if
		sql.append(params.getParam());
		Object[] objects = params.getList().toArray();
		return this.findByPages(sql.toString(), pager, objects);
	}
	
	/**
	 * 香港联营卡状态导出
	 * @param customer
	 * @return
	 */
	public List listJointCardState(Customer customer) {
		if (customer == null || customer.getId() == null) {
			throw new ApplicationException("customer或者customerId为空");
		}
		String sql = "select cardno, state, status, gentime from ( "
				+ "select cardno, state, status, gentime, row_number() over (partition by cardno order by gentime desc) as group_idx from ( "
				+ "select ac.id as id, ac.cardno as cardno, ac.state as state, bw.status, bw.gentime as gentime "
				+ "from csms_accountc_info ac "
				+ "left join csms_black_list_water bw on ac.cardno=bw.cardno "
				+ "left join csms_customer cu on ac.customerid=cu.id "
				+ "where cu.id=?)) where group_idx=1";
		List<Map<String, Object>> list = queryList(sql, customer.getId());
		return list;
	}
	
	public List list(AccountCInfo accountCInfo, Customer customer){
		String sql="select ID,CardNo,customerID,State,Cost,"
				+ "RealCost,IssueTime,startDate,"
				+ "IssueOperId,IssuePlaceId,ROWNUM as num  "
				+ "from CSMS_AccountC_info where customerID = ?";
		sql=sql+" order by IssueTime desc ";
		List<Map<String, Object>> list = queryList(sql, customer.getId());
		return list;
	}

	public boolean hasTransfer(String cardNo) {
		String sql = "select count(1) from csms_transfer_apply t left join csms_transfer_detail " +
				"td on t.id=td.transferid  where t.appstate=1 " +
				" and cardno = ?";
		int count = count(sql, cardNo);
		if (count == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 修改写卡标志
	 * @param cardNo
	 */
	public void updateWriteCardFlag(String cardNo) {
		String sql="update CSMS_ACCOUNTC_INFO set writeCardFlag=2 where cardno=?";
		saveOrUpdate(sql, cardNo);
	}

	/**
	 * 把服务密码设置为null
	 * @param id
	 */
	public void updateSerPwd(Long id) {
		String sql = "update CSMS_ACCOUNTC_INFO set CARDSERVICEPWD='' where CUSTOMERID = ?";
		update(sql,id);
	}
	public Pager stopDetail(Pager pager,List<String> cardNoList,String newCurrentTime, String nowTime){
		StringBuffer sql = new StringBuffer();
		SqlParamer params=new SqlParamer();
		//只显示  记帐卡卡号 车道序列号 入口路段名称 入口站名 入口时间 
		//出口路段名称 出口站名 出口时间 实收金额 优惠金额 应收金额
		sql.append("select CARDNO,TABLEID,ENTRANCEROADNAME,ENTRANCESTATIONNAME," +
				"TO_CHAR(ENTRANCETIME,'YYYY-MM-DD HH24:MI:SS') ENTRANCETIME," +
				"EXITROADNAME,EXITSTATIONNAME,"+
				"TO_CHAR(EXITTIME,'YYYY-MM-DD HH24:MI:SS') EXITTIME," +
				"REALTOLL,DISCOUNTAMOUNT,TOLL " +
				"from CSMS_AC_TRADE_DETAILINFO  "+
				"where DEALSTATUS=0 AND PAYFLAG=0  ");
		if (newCurrentTime!=null) {
			if(cardNoList.size()!=0){
				params.in("CARDNO", cardNoList);
			}
			if(StringUtil.isNotBlank(newCurrentTime)){
				params.ge("to_char(BALANCETIME, 'YYYYMMDDHH24MISS')", newCurrentTime);
			}
			if(StringUtil.isNotBlank(nowTime)){
				params.le("to_char(BALANCETIME, 'YYYYMMDDHH24MISS')", nowTime);
			}
			
			sql.append(params.getParam());
			sql=sql.append(" order by cardNo,BalanceTIME desc");
			List list = params.getList();
			Object[] Objects= list.toArray();
			return findByPages(sql.toString(), pager, Objects);
		}else {
			if(cardNoList.size()!=0){
				params.in("CARDNO", cardNoList);
			}
			sql.append(params.getParam());
			sql=sql.append(" order by cardNo,BalanceTIME desc");
			List list = params.getList();
			Object[] Objects= list.toArray();
			
			return findByPages(sql.toString(), pager, Objects);
		}
	}

	public List<Map<String,Object>> findAccountCInfoList(AccountCApply accountCApply) {
		StringBuilder sql = new StringBuilder("SELECT CAI.CARDNO CARDNO FROM CSMS_ACCOUNTC_INFO CAI JOIN  CSMS_SUBACCOUNT_INFO CSI ON CAI.ACCOUNTID = CSI.ID WHERE CSI.APPLYID = ?");
//		List<AccountCInfo> accountCList = null;
		List<Map<String, Object>> list = queryList(sql.toString(),accountCApply.getId());

		return list;
	}

	public List<Map<String,Object>> findAccountCinfoMap(Long migrateid) {
		StringBuilder sql = new StringBuilder("SELECT ai.CARDNO CARDNO,ai.State,ai.blackFlag,ai.IssueTime FROM CSMS_Migrate m JOIN  CSMS_Migrate_detail md ON m.id = md.MigrateID join CSMS_AccountC_info ai on ai.cardno= md.cardno and m.id=? ");
//		List<AccountCInfo> accountCList = null;
		List<Map<String, Object>> list = queryList(sql.toString(),migrateid);
		return list;
	}
	
	/**
	 * 根据子账户id查找记帐卡列表(保证金退还功能用到)
	 * @param subAccountInfoId
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findAccountCInfosBySubID(Long subAccountInfoId) {
		String sql = "select ai.cardno,ai.state from csms_accountc_info ai where ai.accountid=?";
		List<Map<String, Object>> list = queryList(sql,subAccountInfoId);
		return list;
	}

	/**
	 * 根据客户id找几张卡号
	 * @param customerId
	 * @return
	 */
	public List<Map<String,Object>> findCardNoByCustomerId(Long customerId){
		return queryList("select cardNo from Csms_Accountc_Info where customerId = ?",customerId);
	}
}
