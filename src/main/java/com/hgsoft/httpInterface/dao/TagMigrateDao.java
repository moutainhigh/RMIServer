package com.hgsoft.httpInterface.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class TagMigrateDao extends BaseDao{
	
	
	
	public Pager findVehicleList(Pager pager,String tagNo,String vehiclePlate,String vehicleColor,String authState){
		StringBuffer sql = new StringBuffer(" select tr.authState,vi.id viid,"
										   +" tr.id trid,"
                                           +" tr.neworgan,"
                                           +" vi.vehicleplate,"
                                           +" vi.vehiclecolor,"
                                           +" tr.tagno,"
                                           +" vi.operno,"
                                           +" vi.placeno,"
                                           +" to_char(tr.uptime, 'yyyy-MM-dd hh24:mi:ss') uptime" 
                                           +" from csms_vehicle_info vi"
                                           +" join csms_tagmigrate_record tr"
                                           +" on vi.id = tr.newvehicleid where 1=1");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(tagNo)){
			sqlp.eq("tr.tagNo", tagNo);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			sqlp.eq("vi.vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			sqlp.eq("vi.vehicleColor", vehicleColor);
		}
		if(StringUtil.isNotBlank(authState)){
			sqlp.eq("tr.authState", authState);
		}
		sql=sql.append(sqlp.getParam());
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	public Map<String,Object> findVehicleInfo(String viid){
		StringBuffer sql = new StringBuffer(" select vi.vehicleplate        vehicleplate,"
										   +" vi.vehiclecolor               vehiclecolor,"
										   +" vi.model                      model,"
										   +" vi.VehicleType                VehicleType,"
										   +" vi.vehicleWeightLimits        vehicleWeightLimits,"
										   +" vi.NSCvehicletype             NSCvehicletype,"
										   +" vi.vehicleSpecificInformation vehicleSpecificInformation,"
										   +" vi.vehicleLong                vehicleLong,"
										   +" vi.vehicleWidth               vehicleWidth,"
										   +" vi.vehicleHeight              vehicleHeight,"
										   +" vi.vehicleAxles               vehicleAxles,"
										   +" vi.vehicleWheels              vehicleWheels,"
										   +" vi.vehicleEngineNo            vehicleEngineNo,"
										   +" vi.UsingNature                UsingNature,"
										   +" vi.IdentificationCode         IdentificationCode,"
										   +" vi.owner                      owner,"
										   +" m.picaddr                     picaddr"
										   +" from csms_vehicle_info vi"
										   +" join csms_material m"
										   +" on m.vehicleid = vi.id where 1=1 and vi.id=?");
		List<Map<String, Object>> list = this.queryList(sql.toString(), viid);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> findMigrateByTrid(String trid){
		String sql = "select * from CSMS_tagMigrate_record where id=?";
		List<Map<String, Object>> list = this.queryList(sql, trid);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public String findCarObuCardTagId(String newVehicleId){
		String sql ="select ci.TagID from  CSMS_CarObuCard_info ci where ci.VehicleID=?";
		List<Map<String, Object>> list = this.queryList(sql, newVehicleId);
		if(list.size()>0){
			if(list.get(0).get("TAGID")!=null)
				return list.get(0).get("TAGID").toString();
		}
		return "null";
	}
	public String findTagInfoId(String tagNo){
		String sql = "select id from CSMS_Tag_info where tagno=?";
		List<Map<String, Object>> list = this.queryList(sql, tagNo);
		if(list.size()>0){
			return (String) list.get(0).get("ID").toString();
		}
		return null;
	}
	public void updateNewCarObuCardTagId(String newVehicleId,String tagInfoId){
		String sql = "update CSMS_CarObuCard_info set tagId=? where VehicleID=?"; 
		saveOrUpdate(sql, tagInfoId,newVehicleId);
		
	}
	public void updateCarObuCardTagId(String vehicleId){
		String sql = "update CSMS_CarObuCard_info set tagId='' where VehicleID=?"; 
		saveOrUpdate(sql,vehicleId);
		
	}
	public void updateTagInfoClientId(String newCustomerId,String tagNo){
		String sql = "update CSMS_Tag_info set ClientID=? where tagNo=?";
		saveOrUpdate(sql, newCustomerId,tagNo);
	}
	public void updateMigrate(String trid, Date authDate, String authId, String authNo, String authName, String authState){
		String sql = "update CSMS_tagMigrate_record set authDate=?,authId=?,authNo=?,authName=?,authState=? where id=?";
		saveOrUpdate(sql, authDate,authId,authNo,authName,authState,trid);
	}
}
