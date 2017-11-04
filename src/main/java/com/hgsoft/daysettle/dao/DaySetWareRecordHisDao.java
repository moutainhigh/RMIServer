package com.hgsoft.daysettle.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.daysettle.entity.DaySetWareRecord;
import com.hgsoft.daysettle.entity.DaySetWareRecordHis;

@Component
public class DaySetWareRecordHisDao extends BaseDao{

	public void saveHis(DaySetWareRecordHis daySetWareRecordHis,DaySetWareRecord daySetWareRecord) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_DaySetWare_RecordHis( ID,SettleDay,SettleTime,SettleFlag,DifferenceFlag,"
				+ "ReportOperID,ReportTime,ReportPlaceID,HisSeqID,CreateDate,CreateReason )  "
				+ "SELECT "+daySetWareRecordHis.getId()+",SettleDay,SettleTime,SettleFlag,DifferenceFlag,ReportOperID,ReportTime,ReportPlaceID,HisSeqID,sysdate,'" 
				+ daySetWareRecordHis.getCreateReason()+"' FROM CSMS_DaySetWare_Record WHERE ID="+daySetWareRecord.getId()+"");
		save(sql.toString());
	}
}
