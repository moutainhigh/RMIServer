package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountNCApply;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class AccountNCApplyDao extends BaseDao{

	public Pager findByPage(Pager pager,Customer customer,AccountCApply accountCApply,Date startTime,Date endTime) {
		String sql ="select c.ID cid,sb.id sbid,ac.id acid,aa.ID aid,mi.id miid, c.UserNo,"
				+ "c.Organ,c.IdType,c.IdCode,aa.AccountType,aa.bank, aa.bankAccount,aa.bankName,"
				+ "aa.accName, ac.OldAccName,ac.NewAccName,ac.Remark,ac.AppState,ac.Approver,"
				+ "ac.AppTime,ac.OperID,ac.PlaceID,ac.OperTime,ROWNUM as num  "
				+" from CSMS_AccountNC_apply ac join CSMS_SubAccount_Info  sb  on ac.AccountID =sb.id "
				+ "join CSMS_AccountC_apply aa  on aa.id =sb.ApplyID"
				+" join CSMS_MainAccount_Info  mi on sb.mainId = mi.id"
				+" join csms_customer c on mi.mainid = c.id where 1=1 ";
		
		SqlParamer params=new SqlParamer();
		if(startTime !=null){
			params.geDate("ac.OperTime", params.getFormat().format(startTime));
		}
		if(endTime !=null){
			params.leDate("ac.OperTime", params.getFormatEnd().format(endTime));
		}
		
		if(StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("c.UserNo", customer.getUserNo());
		}
		
		if(StringUtil.isNotBlank(accountCApply.getBankAccount())){
			params.eq("aa.bankAccount", accountCApply.getBankAccount());
		}
		if(customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by ac.OperTime desc ");
		return this.findByPages(sql, pager,Objects);
	}
	
	public List<Map<String,Object>> find(Long custId) {
		String sql ="select sb.subAccountNo subAccountNo"
				+" from CSMS_AccountNC_apply ac join CSMS_SubAccount_Info  sb  on ac.AccountID =sb.id "
				+ "join CSMS_AccountC_apply aa  on aa.id =sb.ApplyID"
				+" join CSMS_MainAccount_Info  mi on sb.mainId = mi.id"
				+" join csms_customer c on mi.mainid = c.id where c.id=? ";
		List<Map<String, Object>> list = queryList(sql.toString(),custId);
		return list;
	}
	
	public void delete(Long id){
		String sql = "delete from CSMS_AccountNC_apply where id=?";
		delete(sql,id);
	}
	
	public void save(AccountNCApply accountNCApply) {
		accountNCApply.setHisSeqId(-accountNCApply.getId());
		/*StringBuffer sql=new StringBuffer("insert into CSMS_AccountNC_apply(");
		sql.append(FieldUtil.getFieldMap(AccountNCApply.class,accountNCApply).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountNCApply.class,accountNCApply).get("valueStr")+")");*/
		Map map = FieldUtil.getPreFieldMap(AccountNCApply.class,accountNCApply);
		StringBuffer sql=new StringBuffer("insert into CSMS_AccountNC_apply");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*save(sql.toString());*/
	}
	
	public AccountNCApply findById(Long id) {
		String sql = "select * from CSMS_AccountNC_apply where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		AccountNCApply accountNCApply = null;
		if (!list.isEmpty()) {
			accountNCApply = new AccountNCApply();
			this.convert2Bean(list.get(0), accountNCApply);
		}

		return accountNCApply;
	}
	
	public AccountNCApply findByHisId(Long hisId) {
		String sql = "select * from CSMS_AccountNC_apply where hisSeqId=?";
		List<Map<String, Object>> list = queryList(sql,hisId);
		AccountNCApply accountNCApply = null;
		if (!list.isEmpty()) {
			accountNCApply = new AccountNCApply();
			this.convert2Bean(list.get(0), accountNCApply);
		}

		return accountNCApply;
	}

	public void update(AccountNCApply accountNCApply) {
		String sql = "update CSMS_AccountNC_apply set NewAccName = ? ,Remark=? ,appstate=?,hisseqid=? where id = ?";
		saveOrUpdate(sql,accountNCApply.getNewAccName(),accountNCApply.getRemark(),accountNCApply.getAppstate(),accountNCApply.getHisSeqId(),accountNCApply.getId());
	}

	public Pager accountCRenameList(Pager pager, Customer customer,
			String bankAccount, String state) {
		
		StringBuffer sql = new StringBuffer("select "
				//账户名称变更信息
				+"n.ID renameID,n.ACCOUNTID,n.OLDACCNAME,n.NEWACCNAME,n.REMARK,n.APPSTATE,"
				+ "n.APPROVER,n.APPTIME,n.OPERID,n.PLACEID,n.OPERTIME,"
				//客户信息
				+ "c.ID as customerId,c.userNo,c.organ,"
				+ "c.userType,c.idType,c.idCode,"
				//银行信息
				+"a.AccountType,a.bankAccount,ROWNUM as num "
				+ " from csms_accountnc_apply n "
				+ "left join csms_subaccount_info s on n.accountid=s.id "
				+ "left join csms_accountc_apply a on s.applyid=a.id "
				+ "left join csms_customer c on a.customerid=c.id "
				+ "where 1=1 ");
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
		if(StringUtil.isNotBlank(state)){
			sql.append(" and n.APPSTATE=?");
			list.add(state);
		}
		sql.append(" order by n.OPERTIME desc");
		return this.findByPages(sql.toString(), pager,list.toArray());
		
	}

	public Map<String, Object> accountCRenameInfo(Long accountCRenameId) {
		String sql = "select "
				//账户名称变更信息
				+"n.ID renameID,n.ACCOUNTID,n.OLDACCNAME,n.NEWACCNAME,n.REMARK,n.APPSTATE,"
				+ "n.APPROVER,n.APPTIME,n.OPERID,n.PLACEID,n.OPERTIME,"
				//客户信息
				+ "c.ID as customerId,c.userNo,c.organ,"
				+ "c.userType,c.idType,c.idCode,"
				//银行信息
				+"a.AccountType,a.bankAccount "
				+ " from csms_accountnc_apply n "
				+ "left join csms_subaccount_info s on n.accountid=s.id "
				+ "left join csms_accountc_apply a on s.applyid=a.id "
				+ "left join csms_customer c on a.customerid=c.id "
				+ "where 1=1 and n.ID=?";
		List<Map<String, Object>> list = queryList(sql,accountCRenameId);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
		
	}

	public boolean updateAccountCRenameState(Long accountCRenameId, String newState, String oldState, Long hisId, String approver,String approverNo,String approverName, String appTime) {
		StringBuffer sql = new StringBuffer("update csms_accountnc_apply set AppState='"+newState+"',approver='"+approver+"',approverNo='"+approverNo+"',approverName='"+approverName+"',appTime=to_date('"+appTime+"','YYYY-MM-DD HH24:MI:SS'),HisSeqID="+hisId+" where 1=1 and id="+accountCRenameId);
		if (StringUtil.isNotBlank(oldState)) {
			sql.append(" and AppState='"+oldState+"'");
		}
		update(sql.toString());
		return true;
	}

	public Map<String, Object> findAccountCRenameById(Long accountCRenameId,String oldstate) {
		String sql = "select a.opertime,a.id,a.accountid,a.newaccname,a.oldaccname,a.appstate,ap.id accountCApplyid,ap.customerid ,s.id subAccountId  "
				+ " from csms_accountnc_apply a "
				+ " left join csms_subaccount_info s on a.accountid=s.id "
				+ " left join csms_accountc_apply ap on s.applyid=ap.id"
				+ " where 1=1 and a.id=? and a.AppState=?";
		List<Map<String, Object>> list = queryList(sql,accountCRenameId,oldstate);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public void saveAccountCRenameHis(Long accountCRenameId, Long hisId, String genReason) {
		String sql = "insert into CSMS_ACCOUNTNC_APPLY_HIS"
				+ "(ID,ACCOUNTID,OLDACCNAME,NEWACCNAME,REMARK,APPSTATE,APPROVER,APPTIME,"
				+ "OPERID,PLACEID,OPERTIME,HISSEQID,GENTIME,GENREASON)  "
				
				+ " SELECT "+hisId+",ACCOUNTID,OLDACCNAME,NEWACCNAME,REMARK,APPSTATE,APPROVER,APPTIME,"
				+ " OPERID,PLACEID,OPERTIME,HISSEQID,sysdate,'"+genReason+"'"
				+ " FROM csms_accountnc_apply a WHERE a.id="+accountCRenameId;
		update(sql);
	}
}
