package com.hgsoft.macao.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.macao.entity.NotifyMCRecord;
import com.hgsoft.macao.entity.NotifyMCRecordHis;

@Repository
public class NotifyMCRecordHisDao extends BaseDao{
	
	/**
	 * 保存历史
	 * @param notifyMCRecord
	 * @param notifyMCRecordHis
	 * @return void
	 */
	public void save(NotifyMCRecord notifyMCRecord,NotifyMCRecordHis notifyMCRecordHis) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_NOTIFYMC_RECORD_HIS(ID,genTime,genReason,HisSeqID,reqSN,reqTime,resSN,resTime,Tran_status,reqResult,interfaceFlag,verify,bankAccountNumber,etcCardNumber,oldCardNumber,newCardNumber,carNumber,carColor)"
				+ "SELECT "+notifyMCRecordHis.getId()+",sysdate,'"+notifyMCRecordHis.getGenReason()+"',HisSeqID,reqSN,reqTime,resSN,resTime,Tran_status,reqResult,interfaceFlag,verify,bankAccountNumber,etcCardNumber,oldCardNumber,newCardNumber,carNumber,carColor"
				+ " FROM CSMS_NOTIFYMC_RECORD WHERE ID="+notifyMCRecord.getId()+"");
		save(sql.toString());
	}
}
