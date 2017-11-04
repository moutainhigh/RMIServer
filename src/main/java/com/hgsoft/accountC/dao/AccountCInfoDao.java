package com.hgsoft.accountC.dao;

import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Repository
public class AccountCInfoDao extends BaseDao{
	
	public Pager findAccountCInfosByPager(AccountCInfo accountCInfo,SubAccountInfo subAccountInfo,Pager pager){
		StringBuffer sql = new StringBuffer(
				"select a.ID,a.cardNo,a.accountID,a.state,a.cost,a.realCost,"
				+ "a.issueTime,a.issueFlag,a.bind,a.issueOperId,a.issuePlaceId,"
				+ "a.s_con_pwd_flag,a.tradingPwd,a.isDaySet,a.settleDay,a.settletTime,a.hisSeqID,"
				+ "c.organ,ROWNUM as num,a.operNo,a.operName,a.placeName,a.placeNo,vi.vehiclePlate," +
				  "vi.vehicleColor,a.bail from CSMS_AccountC_info a join CSMS_Customer c on " +
				  "a.customerid=c.id left join CSMS_CarObuCard_info coc on a.id=coc.accountCID left join " +
				  "CSMS_Vehicle_Info vi on vi.id=coc.VehicleID  where 1=1 ");
		if(accountCInfo.getCustomerId() != null){
			sql.append(" and a.customerID="+accountCInfo.getCustomerId());
		}
		if(subAccountInfo.getId() != null){
			sql.append(" and a.accountID="+subAccountInfo.getId());
		}
		sql.append(" order by  a.ID desc ");
		return this.findByPages(sql.toString(), pager,null);
	}
	
	public AccountCInfo findById(Long id) {
		String sql = "select * from csms_accountc_info where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}
		return accountCInfo;
	}
	public AccountCInfo findByHisId(Long hisId){
		String sql = "";
		if(hisId!=null){
			sql = "select * from csms_accountc_info where hisSeqID="+hisId;
		}else{
			sql = "select * from csms_accountc_info where hisSeqID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}

	public void updateEndDate(AccountCInfo accountCInfo) {
		Format format = new SimpleDateFormat("yyyy/MM/dd");
		String d = format.format(accountCInfo.getEndDate());
		String sql = "update csms_accountc_info set endDate=to_date('"+d+"','yyyy/MM/dd') where cardNo="+accountCInfo.getCardNo();
		update(sql);
	}

	public void update(AccountCInfo accountCInfo) {
		Map map = FieldUtil.getPreFieldMap(AccountCInfo.class,accountCInfo);
		StringBuffer sql=new StringBuffer("update csms_accountc_info set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),accountCInfo.getId());
		
		/*StringBuffer sql=new StringBuffer("update csms_accountc_info set ");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("nameAndValue")+" where id="+accountCInfo.getId());
		update(sql.toString());*/
	}
	
	public void updateNotNull(AccountCInfo accountCInfo) {
		/*Map map = FieldUtil.getPreFieldMap(AccountCInfo.class,accountCInfo);
		StringBuffer sql=new StringBuffer("update csms_accountc_info set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),accountCInfo.getId());*/
		
		/*StringBuffer sql=new StringBuffer("update csms_accountc_info set ");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("nameAndValue")+" where id="+accountCInfo.getId());
		update(sql.toString());*/
		
		String sql ="update csms_accountc_info set RealCost=?,HisSeqID=?  ";
		
		if(accountCInfo.getMaintainTime()!=null){
			sql=sql+",MaintainTime=?  where id = ?";
			saveOrUpdate(sql.toString(), accountCInfo.getRealCost(),accountCInfo.getHisSeqId(),accountCInfo.getMaintainTime(),accountCInfo.getId());
		}else{
			sql=sql+"  where id = ?";
			saveOrUpdate(sql.toString(), accountCInfo.getRealCost(),accountCInfo.getHisSeqId(),accountCInfo.getId());
		}
		
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
		String sql = "delete from csms_accountc_info where id=" + "?";

		delete(sql,id);
	}
	
	/**
	 * 记帐卡发行保存
	 * @param accountCInfo
	 */
	public void save(AccountCInfo accountCInfo) {
		accountCInfo.setHisSeqId(-accountCInfo.getId());
		Map map = FieldUtil.getPreFieldMap(AccountCInfo.class,accountCInfo);
		StringBuffer sql=new StringBuffer("insert into csms_accountc_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	/*	StringBuffer sql=new StringBuffer("insert into csms_accountc_info(");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountCInfo.class,accountCInfo).get("valueStr")+")");
		save(sql.toString());*/
	}
	
	public Pager findAccountCInfos(Pager pager,Customer customer,String bankAccount){
		
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId,a.shutDownStatus as shutDownStatus,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.Approver,s.ID as subAccountInfoId,s.MainID as mainaccountinfoID,s.subAccountNo," +
				"s.subAccountType,s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,s.bailBalance as bailcount,bailFrozenBalance as BailFrozen , a.AppState ,ROWNUM  as num "
				+ "from CSMS_AccountC_apply a  join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "csms_mainaccount_info m on s.mainid=m.id join "
				+ "CSMS_Customer c on c.id=m.mainid  where 1=1 and " +
				" c.id=a.customerid and subaccounttype='2' ");
		List list=new ArrayList();
		if(customer != null){
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			Map map = FieldUtil.getPreFieldMap(Customer.class,customer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list=((List) map.get("paramNotNull"));
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and a.bankAccount=?");
			list.add(bankAccount);
		}
		//sql.append(" order by t.id desc");
		sql.append(" order by a.ID desc ");
		return this.findByPages(sql.toString(), pager,list.toArray());
		
	}
	
	public AccountCInfo findByCardNo(String cardNo){
		String sql = "select * from csms_accountc_info where cardNo= ? ";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}

	public AccountCInfo findByCustomerId(Long customerId){
		String sql = "select * from csms_accountc_info where customerId= ? ";
		List<Map<String, Object>> list = queryList(sql,customerId);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}
	
	public AccountCInfo findByCardNoAndCustomer(String cardNo,Customer customer){
		String sql = "select * from csms_accountc_info where customerId=? and cardNo=?";
		List<Map<String, Object>> list = queryList(sql,customer.getId(),cardNo);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}

	/***
	 * 根据客户id查询记帐卡信息集合
	 * @param customerId
	 * @return
     */
	public List<AccountCInfo> findAccountListByCustomerId (Long customerId) {
		String sql = "select * from csms_accountc_info where customerId= ?";

		List<Map<String, Object>> list = queryList(sql,customerId);
		AccountCInfo accountCInfo = null;
		List<AccountCInfo> accountCInfos = new ArrayList<AccountCInfo>();
		if (list.size() > 0) {
			for (Map<String, Object> c : list) {
				accountCInfo = new AccountCInfo();
				this.convert2Bean(c, accountCInfo);

				accountCInfos.add(accountCInfo);
			}
		}
		return accountCInfos;

	}

	/***
	 * 根据记账卡申请表账户号查询非注销状态记帐卡信息集合
	 * @param SubAccountNo
	 * @return
	 */
	public List<AccountCInfo> findNoCancelAccountListBySubAccountNo (String SubAccountNo) {
		String sql = "SELECT ac.* FROM csms_accountc_info ac INNER JOIN CSMS_SUBACCOUNT_INFO sb on AC.ACCOUNTID = SB.id INNER JOIN CSMS_ACCOUNTC_APPLY ap ON ap.subaccountno = sb.subaccountno where ap.subaccountno = ? and ac.state != 2";

		List<Map<String, Object>> list = queryList(sql,SubAccountNo);
		AccountCInfo accountCInfo = null;
		List<AccountCInfo> accountCInfos = new ArrayList<AccountCInfo>();
		if (list.size() > 0) {
			for (Map<String, Object> c : list) {
				accountCInfo = new AccountCInfo();
				this.convert2Bean(c, accountCInfo);

				accountCInfos.add(accountCInfo);
			}
		}
		return accountCInfos;

	}



	/**
	 * 2017-08-17 保证金退款申请不需要判断资金争议期，终止使用了的就行
	 * @param customerid
	 * @param cardno
	 * @return AccountCInfo
	 */
	public AccountCInfo checkAccountCInfo(Long customerid,String cardno){
		/*String sql = "select * from csms_accountc_info ai join CSMS_cancel cl on ai.CardNo=cl.Code and" +
				" cl.Flag='2' and ai.State='2' and ai.Bind='0' and (select (trunc(sysdate,'DD') - trunc(cl.CancelTime,'DD')) from dual)>=30 and ai.customerID=? and ai.cardNo=?";*/
		String sql = "select * from csms_accountc_info ai "
				+ "   join CSMS_cancel cl on ai.CardNo=cl.Code "
				+ "   where cl.Flag='2' and ai.State='2' and ai.customerID=? and ai.cardNo=? ";
		List<Map<String, Object>> list = queryList(sql,customerid,cardno);
		AccountCInfo accountCInfo = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCInfo = new AccountCInfo();
			this.convert2Bean(list.get(0), accountCInfo);
		}

		return accountCInfo;
	}

	public Map<String, Object> findByCardNo(Long customerid,String cardno){
		String sql = "select id,Bail from csms_accountc_info where  customerID=? and cardNo="+"?";
//		String sql = "select id,Bail from csms_accountc_info where Bind='0'and Suit='0' and State='2' and customerID=? and cardNo="+"?";
		List<Map<String, Object>> list = this.queryList(sql,customerid,cardno);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public VehicleInfo find(VehicleInfo vehicleInfo) {
		VehicleInfo temp = null;
		if (vehicleInfo != null) {
			StringBuffer sql = new StringBuffer(
					"select id,model,vehicleWeightLimits,vehicleType,customerID "
					+ "from CSMS_Vehicle_Info v join csms_carobucard_info c "
					+ "on v.id=c.vehicleID where 1=1  and prePaidCID is null and accountCID is null ");
			/*sql.append(FieldUtil.getFieldMap(VehicleInfo.class,vehicleInfo).get("nameAndValueNotNull"));*/
			Map map = FieldUtil.getPreFieldMap(VehicleInfo.class,vehicleInfo);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			//sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new VehicleInfo();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public List<Map<String, Object>> findAvailableVehicle(Customer customer,Long accountCApplyId){
		StringBuffer sql = new StringBuffer("select v.id,ncv.vehiclePlate,ncv.vehicleColor,v.model,v.vehicleWeightLimits,"
				+ "v.VehicleType,t.TagNo,v.NSCvehicletype,ncv.bail from CSMS_NewCard_Vehicle ncv join " +
				"CSMS_NewCard_apply na on na.id=ncv.newCardApplyid and na.ApplyID=? and na.AppState='8' join  csms_vehicle_info v " +
				"on ncv.vehiclePlate=v.vehiclePlate and ncv.vehicleColor=v.vehicleColor and ncv.state ='1' "
				+ "join csms_carobucard_info coc on v.id=coc.vehicleid left join csms_tag_info t on coc.tagid=t.id " +
				"where coc.prepaidcid is null and coc.accountcid is null and customerid=? ");
		return queryList(sql.toString(),accountCApplyId,customer.getId());
	}

	public List<Map<String, Object>> findAvailableVehicle(Customer customer,String vehicleInfoIdstr){
		StringBuffer sql = new StringBuffer("select v.id,customerID,vehiclePlate,vehicleColor,model,vehicleWeightLimits,"
				+ "VehicleType,TagNo,NSCvehicletype,coc.prepaidcid,coc.accountcid from csms_vehicle_info v "
				+ "join csms_carobucard_info coc on v.id=coc.vehicleid "
				+ "left join csms_tag_info t on coc.tagid=t.id " +
				"where coc.prepaidcid is null and coc.accountcid is null and customerid=? " +
				"and v.id not in (select vi.id from CSMS_NewCard_Vehicle ncv "
				+ " join CSMS_NewCard_apply na on na.id=ncv.newcardapplyid and na.AppState='8' or na.AppState='1' "
				+ " join CSMS_Vehicle_Info vi on vi.vehiclePlate=ncv.vehiclePlate and vi.vehicleColor=ncv.vehicleColor and ncv.state ='1') ");
		if(!"".equals(vehicleInfoIdstr)&&vehicleInfoIdstr!=null){
			sql.append(" and v.id not in ("+vehicleInfoIdstr+")");
		}
		return queryList(sql.toString(),customer.getId());
	}

	public Map<String, Object> getAvailableVehicleCount(Customer customer) {
		String sql = "select count(v.id) as vcount from csms_vehicle_info v "
				+ "join csms_carobucard_info coc on v.id=coc.vehicleid "
				+ "left join csms_tag_info t on coc.tagid=t.id " +
				"where coc.prepaidcid is null and coc.accountcid is null and customerid=? " +
				"and v.id not in (select vi.id from CSMS_NewCard_Vehicle ncv join CSMS_Vehicle_Info vi " +
				"on vi.vehiclePlate=ncv.vehiclePlate and vi.vehicleColor=ncv.vehicleColor and ncv.state <>'2') ";
		List<Map<String, Object>> list = queryList(sql,customer.getId());
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> findBindingVehicle(Customer customer,Long accountCApplyId){
		String sql = "select v.id,v.customerID customerID,v.vehiclePlate vehiclePlate," +
				"v.vehicleColor vehicleColor,v.model model,v.vehicleWeightLimits vehicleWeightLimits,"
				+ "v.VehicleType VehicleType,t.TagNo TagNo,v.NSCvehicletype NSCvehicletype," +
				" coc.prepaidcid prepaidcid,coc.accountcid accountcid," +
				" cast(ai.Bail/100 as decimal(12,2)) Bail,ai.cardno cardno from CSMS_SubAccount_Info sai " +
				" join CSMS_AccountC_info ai on sai.id=ai.AccountID and ai.customerID=? and sai.ApplyID=? "
				+ " join csms_carobucard_info coc on ai.id = coc.AccountCID "
				+ " join csms_vehicle_info v on coc.VehicleID=v.id  "
				+ " left join csms_tag_info t on coc.tagid=t.id ";
		return queryList(sql,customer.getId(),accountCApplyId);
	}

	// TODO: 2017/4/28   卡片已终止使用且已过资金争议期30天,这里的30天争议期不知道怎么判断
	public List<Map<String, Object>> findBackVehicle(Customer customer,Long accountCApplyId){
		String sql = "select v.id,v.customerID customerID,v.vehiclePlate vehiclePlate," +
				"v.vehicleColor vehicleColor,v.model model,v.vehicleWeightLimits vehicleWeightLimits,"
				+ "v.VehicleType VehicleType,t.TagNo TagNo,v.NSCvehicletype NSCvehicletype," +
				" coc.prepaidcid prepaidcid,coc.accountcid accountcid," +
				" cast(ai.Bail/100 as decimal(12,2)) Bail,ai.cardno cardno from CSMS_SubAccount_Info sai " +
				" join CSMS_AccountC_info ai on sai.id=ai.AccountID and ai.customerID=? and sai.ApplyID=? and " +
				" ai.State='2' "
				+ " join csms_carobucard_info coc on ai.id = coc.AccountCID "
				+ " join csms_vehicle_info v on coc.VehicleID=v.id  "
				+ " left join csms_tag_info t on coc.tagid=t.id ";
		return queryList(sql,customer.getId(),accountCApplyId);
	}
	
	public List<Map<String, Object>> findAccountCBySubID(Long subID){
		String sql = "select * from csms_accountc_info a where a.accountid=?";
		return queryList(sql,subID);
	}
	
	public List<Map<String, Object>> findAccountCByCustomerID(Long customerID,Long AccountID){
		String sql = "select * from csms_accountc_info a where a.customerid=? and a.AccountID=? and state != 2 ";
		return queryList(sql,customerID,AccountID);
	}

	public boolean getStopCardBlackList(String bankAccount) {
		String sql = "select count(*) from CSMS_STOPCARDBLACKLIST s where "
				+ "s.bankAccount=?";
		int count = count(sql, bankAccount);
		if (count == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 增加保证金金额(传入正数就是增加)
	 * @param bail
	 * @param accountCInfo
	 */
	public void updateBailFee(BigDecimal bail, AccountCInfo accountCInfo){
		StringBuffer sql = new StringBuffer(
				"update CSMS_AccountC_info set Bail=Bail+?");
		if(accountCInfo.getHisSeqId()!=null){
			sql.append(",HisSeqID=? where id=?");
			saveOrUpdate(sql.toString(),bail,accountCInfo.getHisSeqId(),accountCInfo.getId());
		}else{
			sql.append(" where id=? ");
			saveOrUpdate(sql.toString(),bail,accountCInfo.getId());
		}
	}

	/**
	 * 根据客户id找出卡号
	 * @param customerId
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getAccountCInfoByCustomerId(Long customerId) {
		StringBuffer sql = new StringBuffer("select a.cardno cardno from csms_accountc_info a where a.customerid = ? ");
		return queryList(sql.toString(),customerId);
	}


}
