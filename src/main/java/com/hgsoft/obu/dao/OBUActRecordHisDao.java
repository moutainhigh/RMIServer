package com.hgsoft.obu.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.OBUActRecord;
import com.hgsoft.obu.entity.OBUActRecordHis;

@Component
public class OBUActRecordHisDao extends BaseDao{

	public void saveHis(OBUActRecordHis obuActRecordHis,OBUActRecord obuActRecord) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_OBUAct_RecordHis( "
				+ "ID,ActivateCardNo,OperID,PLACEID,operNo,operName,placeNo,placeName,MakeDate,Memo,WritebackFlag,"
				+ "WritebackTime,WritebackOperID,HisSeqID,CreateDate,CreateReason )  "
				+ "SELECT "+obuActRecordHis.getId()+",ActivateCardNo,OperID,PLACEID,operNo,operName,placeNo,placeName,MakeDate,Memo,WritebackFlag,"
				+ "WritebackTime,WritebackOperID,HisSeqID,sysdate," 
				+ obuActRecordHis.getCreateReason()+" FROM CSMS_OBUAct_Record WHERE ID="+obuActRecord.getId()+"");
		save(sql.toString());
	}
}
