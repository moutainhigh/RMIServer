package com.hgsoft.acms.obu.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.OBUActDetail;
import com.hgsoft.obu.entity.OBUActDetailHis;
import com.hgsoft.obu.entity.OBUActRecord;

@Component
public class OBUActDetailHisDaoACMS extends BaseDao{

	public void saveHis(OBUActDetailHis obuActDetailHis,OBUActRecord obuActRecord) {
		
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_OBUAct_DetailHis( "
				+ "ID,MainID,VehiclePlate,TagNo,ImportState,Memo )  "
				+ "SELECT SEQ_CSMSOBUActDetailHis_NO.Nextval,"+obuActDetailHis.getMainID()
				+",VehiclePlate,TagNo,ImportState,Memo FROM CSMS_OBUAct_Detail WHERE MainID="+obuActRecord.getId()+"");
		save(sql.toString());
	}
}
