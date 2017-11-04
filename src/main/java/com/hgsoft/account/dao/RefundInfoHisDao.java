package com.hgsoft.account.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.account.entity.RefundInfoHis;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class RefundInfoHisDao extends BaseDao{

	public void saveHis(RefundInfoHis refundInfoHis,RefundInfo refundInfo) {
		String sql = "insert into CSMS_RefundInfoHis( ID,MainID,MainAccountId,RefundType,Balance,AvailableBalance,preferentialBalance,"
				+ "FrozenBalance,AvailableRefundBalance,RefundApproveBalance,CurrentRefundBalance,BankNo,"
			+ "BankMember,BankOpenBranches,OperID,RefundApplyTime,RefundApplyOper,AuditID,AuditNo,AuditName,AuditTime,"
			+ "AuditStatus,RefundFailReason,RefundID,RefundTime,Memo,HisSeqID,operNo,operName,placeNo,placeName,placeID,CreateDate,CreateReason,refundNo,refundName,"
			+ "cardNo,transferAmt,returnAmt,cardAmt,cardSystemAmt,checkAmt,differentInfo,settleAutdiInfo,settleId,settleNo,settleName,settleTime,"
			+ "finalRefundAmt,personalCorrectAmt,expireFlag,directorAuditId,directorAuditNo,directorAuditName,directorAuditTime,waitSettleAuditTime,bussinessPlaceId,bankAccount,bailBackReason)"
			+ "select "+refundInfoHis.getId()+",MainID,MainAccountId,RefundType,Balance,AvailableBalance,preferentialBalance,"
			+ "FrozenBalance,AvailableRefundBalance,RefundApproveBalance,CurrentRefundBalance,BankNo,"
			+ "BankMember,BankOpenBranches,OperID,RefundApplyTime,RefundApplyOper,AuditID,AuditNo,AuditName,AuditTime,"
			+ "AuditStatus,RefundFailReason,RefundID,RefundTime,Memo,HisSeqID,operNo,operName,placeNo,placeName,placeID,sysdate,'"+refundInfoHis.getCreateReason()+"',refundNo,refundName,"
			+ "cardNo,transferAmt,returnAmt,cardAmt,cardSystemAmt,checkAmt,differentInfo,settleAutdiInfo,settleId,settleNo,settleName,settleTime,"
			+ "finalRefundAmt,personalCorrectAmt,expireFlag,directorAuditId,directorAuditNo,directorAuditName,directorAuditTime,waitSettleAuditTime,bussinessPlaceId,bankAccount,bailBackReason"
			+ " from CSMS_RefundInfo where ID="+refundInfo.getId();
		save(sql);
	}
	
	public RefundInfoHis findById(Long id) {
		String sql="select "+FieldUtil.getFieldMap(RefundInfoHis.class,new RefundInfoHis()).get("nameStr")+" from CSMS_RefundInfoHis where ID=?";
		List list=queryList(sql, id);
		RefundInfoHis refundInfohis = null;
		if(list!=null && list.size()!=0) {
			refundInfohis = (RefundInfoHis) this.convert2Bean((Map<String, Object>) list.get(0), new RefundInfoHis());
		}

		return refundInfohis;
	}
	
	
}
