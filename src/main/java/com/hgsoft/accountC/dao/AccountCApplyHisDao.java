package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCapplyHis;
import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class AccountCApplyHisDao extends BaseDao{
	public void saveHis(AccountCapplyHis accountCapplyHis,AccountCApply accountCApply) {
		/*StringBuffer sql = new StringBuffer(
				"insert into CSMS_AccountC_apply_his( ID,genTime,genReason,AccountID,OldAccName,NewAccName,Remark,AppState,Approver,AppTime,OperID,PlaceID,OperTime,HisSeqID )  "
				+ "SELECT "+accountNCApplyHis.getId()+",sysdate,"+accountNCApplyHis.getGenReason()
				+",AccountID,OldAccName,NewAccName,Remark,AppState,Approver,AppTime,OperID,PlaceID,OperTime,HisSeqID"
				+ " FROM CSMS_AccountNC_apply WHERE ID="+accountNCApply.getId()+"");*/
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_AccountC_apply_his( ID,genTime,genReason,"
				+ "AccountType,LinkMan,Tel,validity,bank,bankSpan,bankAccount,"
				+ "bankName,accName,InvoicePrn,reqCount,residueCount,bail,truckbail,VirType,"
				+ "MaxAcr,BankClearNo,BankAcceptNo,AppState,Approver,OperID,"
				+ "PlaceID,operNo,operName,placeNo,placeName,HisSeqID,customerId,newCardFlag,appTime,ApproverNo,ApproverName,operTime,payAgreementNo,appFailMemo,subAccountNo,shutDownStatus,obaNo,virenum ) "
				+ "select "+accountCapplyHis.getId()+",sysdate,"+accountCapplyHis.getGenReason()
				+ ",AccountType,LinkMan,Tel,validity,bank,bankSpan,bankAccount,"
				+ "bankName,accName,InvoicePrn,reqCount,residueCount,bail,truckbail,VirType,"
				+ "MaxAcr,BankClearNo,BankAcceptNo,AppState,Approver,OperID,PlaceID,operNo,operName,placeNo,placeName,HisSeqID,customerId,newCardFlag,appTime,ApproverNo,ApproverName,operTime,payAgreementNo,appFailMemo,subAccountNo,shutDownStatus,obaNo,virenum "
				+ " from CSMS_AccountC_apply where id="+accountCApply.getId()+"");
		save(sql.toString());
	}
	
	public AccountCapplyHis findByHisId(Long hisId){
		String sql ="";
		if(hisId!=null){
			sql= "select * from CSMS_AccountC_apply_his where HisSeqID="+hisId;
		}else{
			sql= "select * from CSMS_AccountC_apply_his where HisSeqID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		AccountCapplyHis accountCapplyHis = null;
		if (!list.isEmpty()) {
			accountCapplyHis = new AccountCapplyHis();
			this.convert2Bean(list.get(0), accountCapplyHis);
		}

		return accountCapplyHis;
	}
	public AccountCapplyHis findById(Long id){
		String sql= "select * from CSMS_AccountC_apply_his where id=?";
		List<AccountCapplyHis> accountCapplyHises = super.queryObjectList(sql, AccountCapplyHis.class, id);
		if (accountCapplyHises == null || accountCapplyHises.isEmpty()) {
			return null;
		}
		return accountCapplyHises.get(0);
	}
	
}
