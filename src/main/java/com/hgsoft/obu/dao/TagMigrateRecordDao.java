package com.hgsoft.obu.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMigrateRecord;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class TagMigrateRecordDao extends BaseDao {

	public List<Map<String, Object>> findByList(
			TagMigrateRecord tagMigrateRecord) {
		StringBuffer sql = new StringBuffer(
				"select hisseqid,placename,placeno,opername,operno,placeid,operid,authstate,authname,authno,authid" +
				",authdate,uptime,reqdate,neworgan,newvehicleid,newcustomerid,vehicleid,organ,customerid,tagno,id from CSMS_tagMigrate_record ");
		List<Map<String, Object>> list = queryList(sql.toString());
		return list;
	}

	public TagMigrateRecord findById(Long id) {
		StringBuffer sql = new StringBuffer(
				"select hisseqid,placename,placeno,opername,operno,placeid,operid,authstate,authname,authno,authid,authdate,uptime," +
				"reqdate,neworgan,newvehicleid,newcustomerid,vehicleid,organ,customerid,tagno,id from CSMS_tagMigrate_record where id=?");
		List<Map<String, Object>> list = queryList(sql.toString(), id);
		TagMigrateRecord tagMigrateRecord = null;
		if (!list.isEmpty()) {
			tagMigrateRecord = new TagMigrateRecord();
			this.convert2Bean(list.get(0), tagMigrateRecord);
		}

		return tagMigrateRecord;
	}
	
	public int queryCountByTagNo(String tagNo,int auTHState) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from csms_tagmigrate_record t" +
				" where t.tagno = ? and t.authstate=?");
		return queryCount(sql.toString(), tagNo,auTHState);
	}

	public TagMigrateRecord find(TagMigrateRecord tagMigrateRecord) {
		TagMigrateRecord temp = null;
		if (tagMigrateRecord != null) {
			StringBuffer sql = new StringBuffer(
					"select hisseqid,placename,placeno,opername,operno,placeid,operid,authstate,authname,authno,authid,authdate,uptime," +
					"reqdate,neworgan,newvehicleid,newcustomerid,vehicleid,organ,customerid,tagno,id from CSMS_tagMigrate_record where 1=1 ");
			Map map = FieldUtil.getPreFieldMap(TagMigrateRecord.class,
					tagMigrateRecord);
			if (tagMigrateRecord != null) {
				sql.append(map.get("selectNameStrNotNull"));
			}
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString(),
					((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new TagMigrateRecord();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

	public void save(TagMigrateRecord tagMigrateRecord) {
		Map map = FieldUtil.getPreFieldMap(TagMigrateRecord.class,tagMigrateRecord);
		StringBuffer sql = new StringBuffer("insert into CSMS_tagMigrate_record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public Pager findByPage(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer, String identificationCode6) {
		StringBuffer sql = new StringBuffer(
				"select tr.*,v.vehicleplate,v.vehiclecolor,v.nscvehicletype from(" +
				"select hisseqid,placename,placeno,opername,operno,placeid,operid,authstate," +
				"authname,authno,authid,authdate,uptime,reqdate,neworgan,newvehicleid, newcustomerid," +
				"vehicleid,organ,customerid,tagno,id from CSMS_tagMigrate_record tr" +
				" where tr.customerid="+customer.getId()+" )tr join csms_vehicle_info v on v.id =  tr.newvehicleid ");
		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(tagInfo.getTagNo())){
			params.eq("tr.tagno", tagInfo.getTagNo());
		}
		//以下条件页面已经注释
		if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
			params.eq("v.vehicleColor", vehicleInfo.getVehicleColor());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
			params.eq("v.vehiclePlate", vehicleInfo.getVehiclePlate());
		}
		if(StringUtil.isNotBlank(identificationCode6)){
			params.like("v.identificationCode", identificationCode6);
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by tr.reqdate desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}

	public void update(TagMigrateRecord tagMigrateRecord) {
		if (tagMigrateRecord != null) {
			Map map = FieldUtil.getPreFieldMap(TagMigrateRecord.class,
					tagMigrateRecord);
			StringBuffer sql = new StringBuffer(
					"update CSMS_tagMigrate_record set ");
			sql.append(map.get("updateNameStr") + " where id = ?");
			saveOrUpdate(sql.toString(), (List) map.get("param"),
					tagMigrateRecord.getId());
		}
	}
}
