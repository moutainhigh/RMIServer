package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.NewCardApply;
import com.hgsoft.accountC.entity.NewCardVehicle;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.httpInterface.entity.NewCardVehicleVo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class NewCardApplyDao extends BaseDao{
	public List<Map<String, Object>> listAccountCApplys(Customer customer,String debitCardType){
		/*StringBuffer sql = new StringBuffer(
				"select a.ID as accountCApplyId,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,shutdownstatus "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "csms_mainaccount_info m on s.mainid=m.id join "
				+ "CSMS_Customer c on c.id=m.mainid where 1=1 and c.id=a.customerid and subaccounttype='2' and "
				+ "a.residueCount=0 and a.appState='6' or shutdownstatus='0' ");*/
		
		StringBuffer sql = new StringBuffer(
				" select a.ID as accountCApplyId,a.accountType,a.linkman,a.validity,a.bank,a.bankSpan," +
				"a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,a.residueCount,a.bail,a.truckbail," +
				"a.virType,a.maxAcr,a.bankClearNo,a.bankAcceptNo,a.appState,a.Approver,s.ID as subAccountInfoId," +
				"s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,s.ApplyID,c.ID as customerId,c.userNo," +
				"c.organ,c.servicePwd,c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,c.shortTel," +
				"c.addr,c.zipCode,c.email,c.state,c.cancelTime,c.upDateTime,c.firRunTime,shutdownstatus from CSMS_AccountC_apply a " +
				"join CSMS_SubAccount_Info s on a.id=s.ApplyID join csms_mainaccount_info m on s.mainid=m.id join CSMS_Customer c " +
				"on c.id=m.mainid where  c.id=a.customerid and s.subaccounttype='2' and a.appState='6' and a.debitCardType=? and userNo=? ");
		
		if(customer != null){
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			return queryList(sql.toString(),debitCardType,customer.getUserNo());
		}else{
			return queryList(sql.toString(),debitCardType);
		}
		
		
	}

	public List<Map<String, Object>> findAccountCApplys(Customer customer,String bankname){
		StringBuffer sql = new StringBuffer(
				" select a.bankAccount from CSMS_AccountC_apply a " +
						"join CSMS_Customer c on c.id=a.CustomerID "+
				" where  c.id=a.customerid  "+
				" and a.appState='6' and c.userNo=? and a.bankName=? "
				);

		if(customer != null){
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			return queryList(sql.toString(),customer.getUserNo(),bankname);
		}else{
			return queryList(sql.toString());
		}


	}
	
	public Pager findNewCardApplyListByPager(Pager pager,Customer customer,String bankAccount){
		StringBuffer sql = new StringBuffer(
				"select a.ID as accountCApplyId,"
				+ "(select sum(bail) from csms_newcard_vehicle  where newCardApplyId=n.id and state != '2') as bailSum,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,a.virenum,s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,n.appstate as newCardAppState,n.reqcount as newCardReqCount,"
				+ "n.id as newCardApplyId,ROWNUM as num "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "csms_mainaccount_info m on s.mainid=m.id join "
				+ "CSMS_Customer c on c.id=m.mainid join CSMS_NewCard_apply n on n.applyid=a.id "
				+ "where  c.id=a.customerid and subaccounttype='2' and "
				+ "a.appState='6' ");
		List list=new ArrayList();
		if(customer != null){
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			Map map = FieldUtil.getPreFieldMap(Customer.class,customer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list=((List) map.get("paramNotNull"));
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and bankAccount=?");
			list.add(bankAccount);
		}
		sql.append(" order by n.applytime desc ");
		return this.findByPages(sql.toString(), pager,list.toArray());
	}
	
	public void saveNewCardApply(NewCardApply newCardApply){
		// TODO: 2017/4/13 这里是不是多了个"-"
		newCardApply.setHisseqId(-newCardApply.getId());
		Map map = FieldUtil.getPreFieldMap(NewCardApply.class,newCardApply);
		StringBuffer sql=new StringBuffer("insert into CSMS_NewCard_apply");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql = new StringBuffer(
				"insert into CSMS_NewCard_apply(");
		sql.append(FieldUtil.getFieldMap(NewCardApply.class, newCardApply).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(NewCardApply.class, newCardApply).get("valueStr")+")");
		save(sql.toString());*/
		
	}

	public void saveNewCardVehicle(NewCardVehicle newCardVehicle){
		Map map = FieldUtil.getPreFieldMap(NewCardVehicle.class,newCardVehicle);
		StringBuffer sql=new StringBuffer("insert into CSMS_NewCard_Vehicle");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql = new StringBuffer(
				"insert into CSMS_NewCard_apply(");
		sql.append(FieldUtil.getFieldMap(NewCardApply.class, newCardApply).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(NewCardApply.class, newCardApply).get("valueStr")+")");
		save(sql.toString());*/

	}
	
	public NewCardApply findById(Long newCardApplyId){
		String sql = "select ID,ApplyID,reqCount,AppState,Approver,OperID,PlaceID,HisSeqID,operName,operNo,placeName,placeNo,operTime,appTime,approverName,applyTime "
				+ "from CSMS_NewCard_apply where id="+newCardApplyId;
		
		List<Map<String, Object>> list = queryList(sql);
		NewCardApply newCardApply = null;
		if (!list.isEmpty()) {
			newCardApply = new NewCardApply();
			this.convert2Bean(list.get(0), newCardApply);
		}

		return newCardApply;
	}
	public NewCardApply findByHisId(Long hisId){
		String sql ="";
		if(hisId!=null){
			sql= "select ID,ApplyID,reqCount,AppState,Approver,OperID,PlaceID,HisSeqID "
					+ "from CSMS_NewCard_apply where HisSeqID="+hisId;
		}else{
			sql= "select ID,ApplyID,reqCount,AppState,Approver,OperID,PlaceID,HisSeqID "
					+ "from CSMS_NewCard_apply where HisSeqID is null";

		}
		
		List<Map<String, Object>> list = queryList(sql);
		NewCardApply newCardApply = null;
		if (!list.isEmpty()) {
			newCardApply = new NewCardApply();
			this.convert2Bean(list.get(0), newCardApply);
		}

		return newCardApply;
	}
	public void update(NewCardApply newCardApply){

		Map map = FieldUtil.getPreFieldMap(NewCardApply.class,newCardApply);
		StringBuffer sql=new StringBuffer("update CSMS_NewCard_apply set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),newCardApply.getId());
		
		/*StringBuffer sql = new StringBuffer(
				"update CSMS_NewCard_apply set ");
		if(newCardApply!=null){
			sql.append(FieldUtil.getFieldMap(NewCardApply.class, newCardApply).get("nameAndValue")+" where id="+newCardApply.getId());
		}
		
		update(sql.toString());*/
	}

	public Pager findAccountCNewApplyList(Pager pager, String userNo, String organ,String idCode, String bankAccount,String state,String startTime,String endTime) {
		//加了/*+RULE*/ 之后会优化查询速度
		StringBuffer sql = new StringBuffer(" select /*+RULE*/ c.userNo,c.organ,c.mobile,c.idCode,n.reqCount,a.accountType,"
				+ " NVL(a.bank,a.bankSpan) openbank,a.applyTime accountCApplyApplyTime,a.AppTime accountCApplyAppTime,"
				+ " a.linkman,a.validity,a.bankAccount,(select sum(ncv.bail) from CSMS_NewCard_Vehicle ncv"
				+ " where ncv.newCardApplyid = n.id and ncv.state != '2' group by n.id) as applybail,n.applyTime newCardApplyTime,"
				+ " n.appstate newCardState,n.id newCardId,"
				+ " row_number() over (order by n.appstate desc) as num  "
				+ " from csms_newcard_apply n "
				+ " left join csms_accountc_apply a on n.applyid=a.id "
				+ " left join csms_customer c on a.customerid=c.id where 1=1");
		SqlParamer sqlp=new SqlParamer();
		if(userNo != null){
			sqlp.eq("c.userNo", userNo);
		}
		if(StringUtil.isNotBlank(organ)){
			//sql.append(" and a.bankAccount='"+bankAccount+"'");
			sqlp.eq("c.organ", organ);
		}
		if(StringUtil.isNotBlank(idCode)){
			//sql.append(" and a.bankAccount='"+bankAccount+"'");
			sqlp.eq("c.idCode", idCode);
		}
		if(StringUtil.isNotBlank(startTime)){
			sqlp.geDate("n.applyTime", startTime+" 00:00:00");
			//sql.append(" and n.OperTime>=to_date('"+startTime+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(endTime)){
			sqlp.leDate("n.applyTime", endTime+" 23:59:59");
			//sql.append(" and n.OperTime<=to_date('"+endTime+" 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(bankAccount)){
			//sql.append(" and n.appstate='"+state+"'");
			sqlp.eq("a.bankAccount", bankAccount);
		}
		//type:1.初次申请审批列表；2.单卡保证金设置列表
		if(StringUtil.isNotBlank(state)){
			sql.append(" and n.appState='"+state+"'");
		}
		sql=sql.append(sqlp.getParam());
		
		sql.append(" order by n.applyTime desc");
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		
	}
	
	public Pager findAccountCNewApplyList(Pager pager, Long accountCApplyId, String bankAccount, String state) {
		
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId,a.accountType"
				+ ",a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,"
				+ "a.reqcount,a.residueCount,a.bail,a.truckbail,a.virType,"
				+ "a.maxAcr,a.bankClearNo,a.bankAcceptNo,a.appState,"
				+ "a.Approver,a.virenum"
				+ ",n.id newCardId,n.applyid,n.reqcount newCardreqcount,"
				+ "n.appstate newCardState,"
				+ "c.ID as customerId,c.userNo,c.organ,"
				+ "c.servicePwd,c.userType,c.idType,c.idCode,c.registeredCapital,"
				+ "c.tel,c.mobile,c.shortTel,c.addr,c.zipCode,c.email,c.state,"
				+ "c.cancelTime,c.upDateTime,c.firRunTime,ROWNUM as num  "
				+ "from csms_newcard_apply n "
				+ "left join csms_accountc_apply a on n.applyid=a.id "
				+ "left join csms_customer c on a.customerid=c.id where 1=1");
		SqlParamer sqlp=new SqlParamer();
		if(accountCApplyId != null){
			//sql.append(" and a.ID="+accountCApplyId);
			sqlp.eq("a.ID", accountCApplyId);
		}
		if(StringUtil.isNotBlank(bankAccount)){
			//sql.append(" and a.bankAccount='"+bankAccount+"'");
			sqlp.eq("a.bankAccount", bankAccount);
		}
		if(StringUtil.isNotBlank(state)){
			//sql.append(" and n.appstate='"+state+"'");
			sqlp.eq("a.appstate", state);
		}
		sql=sql.append(sqlp.getParam());
		sql.append(" order by c.userNo desc ");
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		
	}

	public Map<String, Object> accountCNewApplyInfo(Long newCardId) {
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId,a.accountType,"
				+ "a.applyTime accountCApplyApplyTime,a.AppTime accountCApplyAppTime,"
				+ "a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,"
				+ "a.reqcount,a.residueCount,a.bail,a.truckbail,a.virType,"
				+ "a.maxAcr,a.bankClearNo,a.bankAcceptNo,a.appState,"
				+ "a.Approver,a.virenum,a.Tel aTel,"
				+ "n.id newCardId,n.applyid,n.reqcount newCardreqcount,"
				+ "n.appstate newCardState,"
				+ "n.placeName newCardplaceName,n.ApproverName newCardApproverName, n.operName newCardoperName,"
				+ "n.bail newCardBail,n.truckBail newCardTruckBail,n.applyTime newCardApplyTime,n.AppTime newCardAppTime,"
				+ "c.ID as customerId,c.userNo,c.organ,"
				+ "c.servicePwd,c.userType,c.idType,c.idCode,c.registeredCapital,"
				+ "c.tel,c.mobile,c.shortTel,c.addr,c.zipCode,c.email,c.state,"
				+ "c.cancelTime,c.upDateTime,c.firRunTime,(select sum(ncv.bail) from CSMS_NewCard_Vehicle ncv where ncv.newCardApplyid=n.id and ncv.state != '2' group by n.id) as applybail "
				+ "from csms_newcard_apply n "
				+ "left join csms_accountc_apply a on n.applyid=a.id "
				+ "left join csms_customer c on a.customerid=c.id where 1=1 and n.id=? order by c.userNo desc ");
		StringBuffer sql2 = new StringBuffer("Select ncv.id,ncv.newCardApplyid,ncv.vehiclePlate,ncv.vehicleColor,vi.VehicleType,vi.vehicleWeightLimits," +
				"vi.NSCvehicletype,ncv.bailType,ncv.bail,ncv.state from CSMS_NewCard_Vehicle ncv join CSMS_Vehicle_Info vi on ncv.vehiclePlate=vi.vehiclePlate" +
				" and ncv.vehicleColor=vi.vehicleColor and ncv.state<>'2' and ncv.newCardApplyid=?");
		List<Map<String, Object>> list = queryList(sql.toString(),newCardId);
		if (!list.isEmpty()&&list.size()==1) {
			List<Map<String, Object>> list2 = queryList(sql2.toString(),newCardId);
			Map<String,Object> ls = new HashMap<String,Object>();
			ls = list.get(0);
			ls.put("newCardVehicleList",list2);
			return ls;
		}
		return null;
		
	}

	public void saveNewCardApplyHis(Long newcardApplyid, String genReason, Long hisId) {
		String sql = "insert into csms_newcard_apply_his"
				+ "(ID,APPLYID,REQCOUNT,APPSTATE,APPROVER,OPERID,PLACEID,HISSEQID,GENTIME,GENREASON,bail,truckbail,OperTime,AppTime)  "
				
				+ " SELECT "+hisId+",APPLYID,REQCOUNT,APPSTATE,APPROVER,OPERID,PLACEID,HISSEQID,sysdate,? "
				+ " FROM csms_newcard_apply a WHERE a.id="+newcardApplyid;
		//update(sql);
		super.saveOrUpdate(sql, genReason);
	}

	public void updateNewCardApply(NewCardApply newCardApply) {
		if (newCardApply != null) {
			Map map = FieldUtil.getPreFieldMap(NewCardApply.class,newCardApply);
			StringBuffer sql=new StringBuffer("update csms_newcard_apply set ");
			sql.append(map.get("updateNameStrNotNull") +" where id = ?");
			saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),newCardApply.getId());
			
			/*StringBuffer sql=new StringBuffer("update csms_newcard_apply set ");
			String param = FieldUtil.getFieldMap(NewCardApply.class,newCardApply).get("nameAndValueNotNullToUpdate");
			param = param.substring(1);
			if (StringUtil.isNotBlank(param)) {
				sql.append(param+" where id="+newCardApply.getId());
			}
			update(sql.toString());*/
		}
	}
	
	public Map<String, Object> findSubAccountCByNewApplyId(Long newCardId) {
		String sql = "select n.id newCardId ,n.applyid,n.reqcount newCardReqcount,a.reqcount applyRegcount,a.residuecount,a.customerid,s.id subAccountId "
				+ "from csms_newcard_apply n "
				+ "left join csms_accountc_apply a on n.applyid=a.id "
				+ "left join csms_subaccount_info s on a.id=s.applyid where n.id=? and n.appstate!='8'";
		List<Map<String, Object>> list = queryList(sql,newCardId);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> findSubAccountCByNewApplyId(Long newCardId,String oldstate) {
		String sql = "select n.id newCardId ,n.applyid,n.reqcount newCardReqcount,a.reqcount applyRegcount,a.residuecount,a.customerid,s.id subAccountId "
				+ "from csms_newcard_apply n "
				+ "left join csms_accountc_apply a on n.applyid=a.id "
				+ "left join csms_subaccount_info s on a.id=s.applyid where n.id=? and n.appstate=? ";
		List<Map<String, Object>> list = queryList(sql,newCardId,oldstate);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	// TODO: 2017/4/19 这里需要改,记帐卡申请已经没有保证金设置 
	public List<Map<String, Object>> findByBankAccount(String bankAccount,Long customerId){
		String sql = "select a.id,a.CustomerID,a.bankAccount,n.ApplyID,n.AppState from CSMS_AccountC_apply a"
				+ " join CSMS_NewCard_apply n on a.id=n.applyid "
				+ " where a.bankAccount=? and a.CustomerID="+customerId+" and not n.AppState='8' ";
		return queryList(sql,bankAccount);
	}

	
	public NewCardApply findLastByApplyId(Long applyId){
		String sql = "select * from(select ID,ApplyID,reqCount,AppState,Approver,OperID,PlaceID,HisSeqID,bail,truckbail "
				+ "from CSMS_NewCard_apply where applyid="+applyId+" and AppState='8' order by id desc) where  rownum=1";
		
		List<Map<String, Object>> list = queryList(sql);
		NewCardApply newCardApply = null;
		if (!list.isEmpty()) {
			newCardApply = new NewCardApply();
			this.convert2Bean(list.get(0), newCardApply);
		}

		return newCardApply;
	}

	public Map<String, Object> findAccountCApplySomeInfo(Customer customer,String bankAccount) {
		String sql = "select a.id as \"id\",a.LinkMan as \"linkman\" ,a.accName as \"accname\" "
				+ "from CSMS_AccountC_apply a " +
				"join CSMS_Customer cr on cr.id=a.CustomerID "
				+ "where a.AppState='6' and a.bankAccount=? and cr.UserNo=?";
		List<Map<String, Object>> list = queryList(sql,bankAccount,customer.getUserNo());
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public Pager findNewCardVehiclePager(Long newCardApplyId,Pager pager){
		StringBuffer sql = new StringBuffer(
				"select * from CSMS_NewCard_Vehicle where 1=1");
		List list=new ArrayList();
		if(newCardApplyId!=null&&!"".equals(newCardApplyId)){
			sql.append(" and newCardApplyId=?");
			list.add(newCardApplyId);
		}
		sql.append(" order by ID desc ");
		return this.findByPages(sql.toString(), pager,list.toArray());
	}
	// TODO 这里需要确认是否需要更新国际收费车型
	public void updateBail(NewCardVehicleVo newCardVehicleVo,String state){
//		Map map = FieldUtil.getPreFieldMap(NewCardVehicle.class, newCardVehicleVo);
		StringBuffer sql =  new StringBuffer("update CSMS_NewCard_Vehicle set ");
		sql.append(" bail=?,bailType=?,state=? where newCardApplyid=? and vehiclePlate=? and vehicleColor=? ");
		super.saveOrUpdate(sql.toString(),newCardVehicleVo.getBail(),newCardVehicleVo.getBailType(),state,newCardVehicleVo.getNewCardApplyid(),newCardVehicleVo.getVehiclePlate(),newCardVehicleVo.getVehicleColor());
	}

	public NewCardVehicle findNewCardVehicleById(Long id) {
		String sql = "select nv.id,nv.newCardApplyid,nv.vehiclePlate,nv.vehicleColor,nv.bailType,nv.bail,nv.state from CSMS_NewCard_Vehicle nv "
				+ " join CSMS_NewCard_apply na on na.id=nv.newcardapplyid and na.AppState='8' "
				+ " join CSMS_Vehicle_Info vi on vi.vehiclePlate=nv.vehiclePlate and vi.vehicleColor=nv.vehicleColor and nv.state='1' and vi.id=? ";
		List<Map<String, Object>> list = queryList(sql,id);
		NewCardVehicle newCardVehicle = null;
		if (!list.isEmpty()) {
			newCardVehicle = new NewCardVehicle();
			this.convert2Bean(list.get(0), newCardVehicle);
		}

		return newCardVehicle;
	}

	public void update(NewCardVehicle newCardVehicle){
		/*StringBuffer sql =  new StringBuffer("update CSMS_AccountC_apply set ");
		sql.append(FieldUtil.getFieldMap(AccountCApply.class, accountCApply).get("nameAndValue")+" where id="+accountCApply.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(NewCardVehicle.class, newCardVehicle);
		StringBuffer sql =  new StringBuffer("update CSMS_NewCard_Vehicle set ");
		sql.append(map.get("updateNameStrNotNull")+" where id=?");
		super.saveOrUpdate(sql.toString(), (List)map.get("paramNotNull"), newCardVehicle.getId());
	}
}
