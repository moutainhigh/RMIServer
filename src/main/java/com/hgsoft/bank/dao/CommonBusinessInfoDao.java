package com.hgsoft.bank.dao;

import com.hgsoft.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommonBusinessInfoDao extends BaseDao {
	
	public Map<String,Object> getLicenseInfo(String vehiclePlate,String vehicleColor){
		String PerOrAccSQL = "select vi.id,ci.prepaidcid,ci.AccountCID from CSMS_Vehicle_Info vi join CSMS_CarObuCard_info ci on vi.id=ci.VehicleID where vi.vehiclePlate=? and vi.vehicleColor=?";
		List<Map<String, Object>> PerOrAccList = this.queryList(PerOrAccSQL,vehiclePlate,vehicleColor);
		Map<String,Object> PerOrAccMap = null;
		String sql = "";
		if(PerOrAccList.size()>0){
			PerOrAccMap = PerOrAccList.get(0);
		}else{
			return null;
		}
		if (PerOrAccMap.get("PREPAIDCID") != null) {
			sql = "select vi.vehiclePlate,vi.vehicleColor,'' lockFlag,'' lockUnit,"
					+ "c.CardNo,vi.vehicleSpecificInformation from csms_vehicle_info"
					+ " vi join CSMS_CarObuCard_info ci on vi.id = ci.VehicleID "
					+ "join CSMS_PrePaidC c on ci.prepaidcid=c.id where vi.vehiclePlate=? and vi.vehicleColor=?";
		} else if (PerOrAccMap.get("ACCOUNTCID") != null) {
			sql = "select vi.vehiclePlate,vi.vehicleColor,'' lockFlag,'' lockUnit,"
					+ "ai.CardNo,vi.vehicleSpecificInformation from csms_vehicle_info"
					+ " vi join CSMS_CarObuCard_info ci on vi.id = ci.VehicleID"
					+ " join CSMS_AccountC_info ai on ci.AccountCID=ai.id where"
					+ " vi.vehiclePlate=? and vi.vehicleColor=? ";
		} else {
			return null;
		}
		List<Map<String, Object>> list = this.queryList(sql, vehiclePlate,
				vehicleColor);
		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}
	
	public Map<String,Object> getVehicleInfoByPreC(String cardNo){
		String sql = "select p.cardno,vi.vehiclePlate,vi.vehicleColor," +
				"vi.VehicleType,vi.vehicleWeightLimits,vi.vehicleSpecificInformation" +
				" from CSMS_Vehicle_Info vi join CSMS_CarObuCard_info ci " +
				"on vi.id=ci.VehicleID right join CSMS_PrePaidC p on ci.PrePaidCID=p.id where p.cardno=?";
		List<Map<String, Object>> list = this.queryList(sql,cardNo);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	public Map<String,Object> getVehicleInfoByAccC(String cardNo){
		String sql = "select ai.cardno,vi.vehiclePlate,vi.vehicleColor," +
				"vi.VehicleType,vi.vehicleWeightLimits,vi.vehicleSpecificInformation" +
				" from CSMS_Vehicle_Info vi join CSMS_CarObuCard_info " +
				"ci on vi.id=ci.VehicleID right join CSMS_AccountC_info ai " +
				"on ci.AccountCID=ai.id where ai.cardno=?";
		List<Map<String, Object>> list = this.queryList(sql,cardNo);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	public Map<String, Object> getCardStateInfoByPreC(String cardNo,
			String cardType) {
		String sql = "select cardno,'" + cardType
				+ "' cardType,to_char(issueTime,'yyyy-MM-dd hh24:mi:ss') " +
				"startdate,State,'3' stopPayState,blackFlag from CSMS_PrePaidC where cardno=?";
		List<Map<String, Object>> list = this.queryList(sql, cardNo);
		if (list.isEmpty()) {
			return null;
		}
		Map<String, Object> bean = list.get(0);
		sql = "select count(*) from CSMS_BLACKLIST_TEMP where cardNo=?";
		int blackList = queryCount(sql, cardNo);
		if (blackList == 0) {
			bean.put("State", 0);
			bean.put("stopPayState", 0);
			return bean;
		}
		bean.put("state", 1);
		sql = "select count(*) from CSMS_BLACKLIST_TEMP where cardNo=? and GENMODE='4' and STATUS='5'";
		int stopList = queryCount(sql, cardNo);
		if (stopList == 0) {
			bean.put("stopPayState", 0);
		} else {
			bean.put("stopPayState", 1);
		}
		return bean;
	}
	
	public Map<String,Object> getCardStateInfoByAccC(String cardNo, String cardType){
		String sql = "select cardno,'" + cardType
				+ "' cardType,to_char(issueTime,'yyyy-MM-dd hh24:mi:ss') " +
				"startdate,State from CSMS_AccountC_info where cardno=?";
		List<Map<String, Object>> list = this.queryList(sql, cardNo);
		if (list.isEmpty()) {
			return null;
		}
		Map<String, Object> bean = list.get(0);
		sql = "select count(*) from CSMS_BLACKLIST_TEMP where cardNo=?";
		int blackList = queryCount(sql, cardNo);
		if (blackList == 0) {
			bean.put("stopPayState", 0);
			bean.put("blackFlag", 0);
			return bean;
		}
		sql = "select count(*) from CSMS_BLACKLIST_TEMP where cardNo=? and GENMODE='4' and STATUS='5'";
		int stopList = queryCount(sql, cardNo);
		if (stopList == 0) {
			bean.put("stopPayState", 0);
		} else {
			bean.put("stopPayState", 1);
		}
		bean.put("blackFlag", 1);
		return bean;
	}

	public Map<String, Object> getflagandTollcarInfo(String date,
			String vehiclePlate, String vehicleColor, String VehicleType,
			String vehicleWeightLimits) {
		//用户类型 01：套装  00：单卡
		String sql = "select vehiclePlate,vehicleColor,'' userType,NSCvehicletype" +
				" from CSMS_Vehicle_Info where to_char(createTime,'yyyy-MM-dd hh24:mi:ss')=? " +
				"and vehiclePlate=? and vehicleColor=? and VehicleType=? and vehicleWeightLimits=?";
		List<Map<String, Object>> list = this.queryList(sql, date, vehiclePlate,
				vehicleColor, VehicleType, vehicleWeightLimits);
		if (list.isEmpty()) {
			return null;
		}
		Map<String, Object> bean = list.get(0);
		sql = "select ci.PREPAIDCID,ci.AccountCID,ci.TagID from csms_vehicle_info vi join CSMS_CarObuCard_info ci" +
				" on vi.id=ci.VehicleID where vi.vehiclePlate=? and vi.vehicleColor=?";
		list = queryList(sql, vehiclePlate, vehicleColor);
		if (list.isEmpty()) {
			return bean;
		}
		Map<String,Object> PerOrAccMap = list.get(0);
		if (PerOrAccMap.get("PREPAIDCID") == null && PerOrAccMap.get("ACCOUNTCID") == null) {
			return bean;
		}
		if (PerOrAccMap.get("TAGID") == null) {
			bean.put("USERTYPE", "00");
			return bean;
		}
		bean.put("USERTYPE", "01");
		return bean;
	}

}
