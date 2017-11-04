package com.hgsoft.account.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.account.entity.BankTransferInfoHis;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class BankTransferInfoHisDao extends BaseDao{
	
	
	public void saveHis( BankTransferInfoHis bankTransferInfoHis,BankTransferInfo bankTransferInfo) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_BankTransfer_InfoHis(ID,MainID,PayName,TransferBlanace,Blanace,BankNo,arrivalTime,Memo,AuditState,HisSeqID,CreateDate,CreateReason,"
				+ "accountTime,certificateType,certificateNo,Abstracts,FileName,DealTime,OperId,OperNo,OperName,AuditId,AuditNo,AuditName,AuditTime,residulBalance,sequenceNo,payAccount )  "
				+ "SELECT "+bankTransferInfoHis.getId()+",MainID,PayName,TransferBlanace,Blanace,BankNo,arrivalTime,Memo,AuditState,HisSeqID,sysdate,'" 
				+ bankTransferInfoHis.getCreateReason()+"',accountTime,certificateType,certificateNo,Abstracts,FileName,DealTime,OperId,OperNo,OperName,AuditId,AuditNo,AuditName,AuditTime,residulBalance,sequenceNo,payAccount "
				+ " FROM CSMS_BankTransfer_Info WHERE ID="+bankTransferInfo.getId()+"");
		save(sql.toString());
	}
	public BankTransferInfoHis findById(Long id){
		String sql="select "+FieldUtil.getFieldMap(BankTransferInfo.class,new BankTransferInfo()).get("nameStr")+" from CSMS_BankTransfer_Infohis where id=?";
		return super.queryObjectFromObjectList(sql, BankTransferInfoHis.class, id);
	}

}
