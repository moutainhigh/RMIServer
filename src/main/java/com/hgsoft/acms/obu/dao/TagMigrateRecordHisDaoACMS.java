package com.hgsoft.acms.obu.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagMigrateRecord;
import com.hgsoft.obu.entity.TagMigrateRecordHis;

@Component
public class TagMigrateRecordHisDaoACMS extends BaseDao {

	public void saveHis(TagMigrateRecordHis tagMigrateRecordHis,TagMigrateRecord tagMigrateRecord) {
		StringBuffer sql = new StringBuffer(
				" insert into CSMS_tagMigrate_record_His( "
				+" Reason,FaultType,CreateDate,hisseqid,placename,placeno,opername,operno,placeid,operid," 
				+" authstate,authname,authno,authid,authdate,uptime," 
				+" reqdate,neworgan,newvehicleid,newcustomerid,vehicleid,organ," 
				+" customerid,tagno,id,CreateReason)"
				+ " SELECT "
				+" Reason,FaultType,sysdate,hisseqid,placename,placeno,opername,operno,placeid,operid," 
				+" authstate,authname,authno,authid,authdate,uptime," 
				+" reqdate,neworgan,newvehicleid,newcustomerid,vehicleid,organ," 
				+" customerid,tagno,"+tagMigrateRecordHis.getId()+","+tagMigrateRecordHis.getCreateReason()
				+" FROM CSMS_tagMigrate_record WHERE ID="+tagMigrateRecord.getId()+"");
		save(sql.toString());
	}
}
