package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.TransferApply;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;

@Repository
public class TransferApplyDao extends BaseDao{

	public void save(TransferApply transferApply) {
		Map map = FieldUtil.getPreFieldMap(TransferApply.class,transferApply);
		StringBuffer sql=new StringBuffer("insert into CSMS_transfer_apply");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql=new StringBuffer("insert into CSMS_transfer_apply(");
		sql.append(FieldUtil.getFieldMap(TransferApply.class,transferApply).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(TransferApply.class,transferApply).get("valueStr")+")");
		save(sql.toString());*/
	}
	
	public Pager findTransferApplyList(Pager pager, Customer customer, String cardNo, String bankAccount,
			String startTime, String endTime) {
		/*StringBuffer sql =new StringBuffer( "select * from(select app.id as applyid,oldcus.userno as olduserno,oldaca.bankaccount as oldbankaccount,oldcus.organ as oldorgan,"
				+ "newcus.userno as newuserno,newaca.bankaccount as newbankaccount,newcus.organ as neworgan,app.OperTime,app.effecttime,app.appstate,row_number() over (order by app.id desc) as num"
				+ ",row_number() over (partition by app.id order by app.id desc) as rn "
				+ " from csms_transfer_apply app  join csms_subaccount_info olda on app.oldaccountid=olda.id  join csms_transfer_detail det on det.transferid=app.id "
				+ " join csms_accountc_apply oldaca on olda.applyid=oldaca.id join csms_subaccount_info newa on app.newaccountid=newa.id join csms_accountc_apply newaca on newa.applyid=newaca.id"
				+ " join csms_mainaccount_info oldmaina on olda.mainid=oldmaina.id join csms_customer oldcus on oldmaina.mainid=oldcus.id join csms_mainaccount_info newmaina on newa.mainid=newmaina.id"
				+ " join csms_customer newcus on newmaina.mainid=newcus.id where 1=1 ");//把操作时间作为申请时间
		//客户号,过户前或者过户后
		sql.append(" and (oldcus.userno='"+customer.getUserNo()+"' or newcus.userno='"+customer.getUserNo()+"') ");*/
		
		StringBuffer sql = new StringBuffer(
				"select  applyid,newbankaccount,newuserno,neworgan,oldbankaccount,olduserno,oldorgan,OperTime,effecttime,appstate,ROWNUM as num from("
						+" select distinct ta.id as applyid,ta.id,ta.oldaccountid,ta.newaccountid,ta.flag,ta.appstate,ta.effecttime,ta.approver,ta.apptime,ta.operid,ta.placeid,ta.opertime,"
						+" newsb.agentsmay,newsb.id,newsb.mainid,newsb.subaccountno,newsb.subaccounttype ,"
						+" newaca.bankaccount as newbankaccount,oldaca.bankaccount as oldbankaccount,"
						+" oldcus.organ as oldorgan,oldcus.userno as olduserno,  "
						+" newcus.organ as neworgan,newcus.userno as newuserno   "
						+" from CSMS_transfer_apply ta   "
						+" join csms_subaccount_info newsb on  ta.newaccountid=newsb.id  join CSMS_AccountC_apply newaca on newsb.ApplyID = newaca.id "
						+" join csms_customer newcus on newaca.CustomerID = newcus.id                                                                 "
						+" join csms_subaccount_info oldsb on  ta.oldaccountid=oldsb.id  join CSMS_AccountC_apply oldaca on oldsb.ApplyID = oldaca.id "
						+" join csms_customer oldcus on oldaca.CustomerID = oldcus.id "
						+ " join csms_transfer_detail det on det.transferid=ta.id  "
						+ " where 1=1     "
				);
		List<String> temp = new ArrayList<String>();
		if(StringUtils.isNotBlank(cardNo)){
			sql.append(" and det.cardno=?");
			temp.add(cardNo);
		}
		
		sql.append(" and (oldcus.userno='"+customer.getUserNo()+"' or newcus.userno='"+customer.getUserNo()+"') ");
		
		
		//银行账号，过户前或者过户后 
		if(StringUtils.isNoneBlank(bankAccount)){
			sql.append(" and (oldaca.bankaccount=? or newaca.bankaccount=?) ");
			temp.add(bankAccount);
			temp.add(bankAccount);
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" and ta.OperTime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			temp.add(startTime+" 00:00:00");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" and ta.OperTime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			temp.add(endTime+" 23:59:59");
		}
		//卡号
		
		sql.append(")");
		sql.append(" order by OperTime desc ");
		
		return this.findByPages(sql.toString(), pager,temp.toArray());
	}

	public TransferApply findById(Long id) {
		String sql = "select * from csms_transfer_apply where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		TransferApply transferApply = null;
		if (!list.isEmpty()) {
			transferApply = new TransferApply();
			this.convert2Bean(list.get(0), transferApply);
		}

		return transferApply;
	}

	public Pager transferList(Pager pager, Customer customer,Long transferId,String bankAccount, String startTime,
			String endTime, String state) {
		
		StringBuffer sql = new StringBuffer(" select * from (select t.ID,t.OLDACCOUNTID,t.NEWACCOUNTID,t.FLAG,t.APPSTATE,"
				//过户信息
				+ "t.EFFECTTIME,t.APPROVER,t.APPTIME,t.OPERID,t.PLACEID,t.OPERTIME,"
				//客户信息
				+ "c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.LinkMan,c.upDateTime,c.firRunTime,"
				//银行账户
				+ "aa.bankAccount oldBankAccount,"
				+" (select bankAccount from CSMS_AccountC_apply aa1 join CSMS_SubAccount_Info s "
				+" on aa1.id = s.applyid  where s.id = t.NewAccountID) newBankAccount ,"
				+ "aa.reqCount AccountCApplyreqCount,aa.residueCount AccountCApplyresidueCount,"
				+ "aa.accName,aa.AccountType,aa.MaxAcr,aa.bankName,aa.VirType,aa.bail,aa.truckbail,aa.bank,"
				+" ROWNUM as num "
				+" from csms_transfer_apply t "
				+" left join CSMS_SubAccount_Info sb on sb.id = t.oldaccountid "
				+" left join CSMS_AccountC_apply aa on aa.id = sb.applyid"
				+" left join csms_customer c on aa.customerid = c.id) where 1=1 ");
		List list=new ArrayList();
		if(customer != null){
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			Map map = FieldUtil.getPreFieldMap(Customer.class,customer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list=((List) map.get("paramNotNull"));
		}
		if(transferId != null){
			sql.append(" and id="+transferId);
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and oldBankAccount='"+bankAccount+"'");
		}
		if(StringUtil.isNotBlank(startTime)){
			sql.append(" and OPERTIME>=to_date('"+startTime+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(endTime)){
			sql.append(" and OPERTIME<=to_date('"+endTime+" 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(state)){
			sql.append(" and APPSTATE=?");
			list.add(state);
		}
		sql.append(" order by OPERTIME ");
		return this.findByPages(sql.toString(), pager,list.toArray());
		
	}

	public Map<String, Object> transferInfo(Long transferId) {
		String sql = " select * from (select t.ID,t.OLDACCOUNTID,t.NEWACCOUNTID,t.FLAG,t.APPSTATE,"
				//过户信息
				+ "t.EFFECTTIME,t.APPROVER,t.APPTIME,t.OPERID,t.PLACEID,t.OPERTIME,"
				//客户信息
				+ "c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,"
				//银行账户
				+ "aa.bankAccount oldBankAccount,"
				+" (select bankAccount from CSMS_SubAccount_Info s join CSMS_AccountC_apply aa1 "
				+" on aa1.id = s.applyid  where s.id = t.NewAccountID) newBankAccount ,"
				+ "aa.reqCount AccountCApplyreqCount,aa.residueCount AccountCApplyresidueCount,"
				+ "aa.accName,aa.AccountType,aa.MaxAcr,aa.bankName,aa.VirType,aa.bail,aa.truckbail,aa.bank,"
				+" row_number() over (order by t.OperTime) as num "
				+" from CSMS_SubAccount_Info sb "
				+" left join CSMS_AccountC_apply aa on aa.id = sb.applyid"
				+" left join csms_customer c on aa.customerid = c.id"
				+" left join csms_transfer_apply t on sb.id = t.oldaccountid) where 1=1 and id=?";
		List<Map<String, Object>> list = queryList(sql,transferId);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
		
	}
	/**
	 * 客服提供给营运接口使用
	 * @param transferId
	 * @param newState
	 * @param oldState
	 * @param approver
	 * @param approverNo
	 * @param approverName
	 * @param appTime
	 * @return
	 */
	public boolean updateTransferState(Long transferId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime) {
		StringBuffer sql = new StringBuffer("update csms_transfer_apply set AppState=?,approver=?,approverNo=?,approverName=?,appTime=to_date(?,'YYYY-MM-DD HH24:MI:SS')  where 1=1 and id="+transferId);
		if (StringUtil.isNotBlank(oldState)) {
			sql.append(" and AppState=?");
			saveOrUpdate(sql.toString(), new Object[]{newState,approver,approverNo,approverName,appTime,oldState});
		}else{
		 saveOrUpdate(sql.toString(), new Object[]{newState,approver,approverNo,approverName,appTime});
		}
		/*update(sql.toString());*/
		return true;
	}

	public List<Map<String, Object>> getTransferDetailByTransferId(Long transferId, String oldstate) {
		String sql = "select a.id accountCid,a.cardno,a.customerID,a.state,a.bail accountCBail,t.oldaccountid,t.newaccountid,"
				+ "(select c.id from csms_customer c "
				+ "left join csms_mainaccount_info m on c.id=m.mainid "
				+ "left join csms_subaccount_info sb on sb.mainid=m.id where sb.id=t.newaccountid) newCustomerid,"
				+ " ap.bail,ap.truckbail "
				+ " from csms_transfer_detail td "
				+ " left join csms_transfer_apply t on td.transferid=t.id "
				+ " left join csms_accountc_info a on td.cardno=a.cardno "
				+ " left join csms_subaccount_info s on a.accountid=s.id "
				+ " left join csms_accountc_apply ap on ap.id=s.applyid"
				+ " where 1=1 and t.id=? and t.AppState=?";
		return queryList(sql,transferId,oldstate);
	}

	public boolean transferCardIsOk(Long transferId) {
		String sql = "select count(*) from csms_transfer_apply t left join csms_transfer_detail td on t.id=td.transferid "
				+ " left join csms_accountc_info a on td.cardno=a.cardno where a.state!=0 and t.id=?";
		if(count(sql, transferId)==0){
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> getCustomerByBank(String oldbankaccount) {
		StringBuffer sql = new StringBuffer("select a.*,c.id subId from csms_customer a left join csms_accountc_apply b on a.id=b.customerid left join csms_subaccount_info c on b.id=c.ApplyID where  b.bankaccount=?");
		List<Map<String, Object>> list = queryList(sql.toString(),oldbankaccount);
		return list;
	}
}
 