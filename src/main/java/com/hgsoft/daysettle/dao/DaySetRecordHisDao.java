package com.hgsoft.daysettle.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.daysettle.entity.DaySetRecord;
import com.hgsoft.daysettle.entity.DaySetRecordHis;

@Component
public class DaySetRecordHisDao extends BaseDao{
	
	public void saveHis(DaySetRecordHis daySetRecordHis,DaySetRecord daySetRecord) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_DaySet_RecordHis( ID,settleDay,DifferentialMarker,OperID,MacAddress,"
				+ "OperTime,OperPlaceID,Memo,HisSeqID,CreateDate,CreateReason )  "
				+ "SELECT "+daySetRecordHis.getId()+",settleDay,DifferentialMarker,OperID,MacAddress,OperTime,OperPlaceID,Memo,HisSeqID,sysdate,'" 
				+ daySetRecordHis.getCreateReason()+"' FROM CSMS_DaySet_Record WHERE ID="+daySetRecord.getId()+"");
		save(sql.toString());
	}



}
