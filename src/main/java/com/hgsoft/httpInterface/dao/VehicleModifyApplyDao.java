package com.hgsoft.httpInterface.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.httpInterface.entity.CustomerModifyApply;
import com.hgsoft.httpInterface.entity.VehicleModifyApply;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class VehicleModifyApplyDao extends BaseDao {

	/**
	 * 获取未审核记录
	 * @param id
	 * @return boolean
	 * @author lgm
	 * @date 2017年4月26日
	 */
	public boolean hasApproval(Long id){
		String sql = "select * from CSMS_VehicleModify_Apply where appState='1' and vehicleId=?";
		List<Map<String,Object>> list = queryList(sql,id);
		return list.size()>0;
	}
	
	public void save(Long id,VehicleInfo oldVehicleInfo, VehicleInfo newVehicleInfo,String path,Date createTime){
		String sql = "insert into CSMS_VehicleModify_Apply(id,vehicleId,oldVehicleType,newVehicleType,"
				+ "oldvehicleWeightLimits,newvehicleWeightLimits,oldNSCvehicletype,newNSCvehicletype,"
				+ "oldPicAddr,newPicAddr,appState,createTime) values(?,?,?,?,?,?,?,?,?,?,'1',?)";
		saveOrUpdate(sql, id,newVehicleInfo.getId(),oldVehicleInfo.getVehicleType(),newVehicleInfo.getVehicleType(),oldVehicleInfo.getVehicleWeightLimits(),newVehicleInfo.getVehicleWeightLimits(),oldVehicleInfo.getNSCVehicleType(),newVehicleInfo.getNSCVehicleType(),path,path,createTime);
	}
	
	public Pager list(Pager pager, String organ,String vehiclePlate, String vehicleColor, String appState,String approverName,String appTime,String createTime){
		StringBuffer sql = new StringBuffer("select a.id,oldVehicleType, newVehicleType, oldvehicleWeightLimits, newvehicleWeightLimits, "
				+ "oldNSCvehicletype, newNSCvehicletype, appState, approverName,i.vehiclePlate,i.vehicleColor,i.placename,c.organ,"
				+ " to_char(appTime,'yyyy-MM-dd hh24:mi:ss') appTime ,to_char(a.createTime,'yyyy-MM-dd hh24:mi:ss') createTime "
				+ "from CSMS_VehicleModify_Apply a join CSMS_Vehicle_Info i on i.id=a.vehicleID join csms_customer c on c.id=i.customerID where 1=1");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(organ)){
			sqlp.eq("c.organ", organ);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			sqlp.eq("i.vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			sqlp.eq("i.vehicleColor", vehicleColor);
		}
		if(StringUtil.isNotBlank(appState)){
			sqlp.eq("appState", appState);
		}
		if(StringUtil.isNotBlank(approverName)){
			sqlp.eq("approverName", approverName);
		}
		if(StringUtil.isNotBlank(appTime)){
			sqlp.eq("to_char(appTime,'yyyy-MM-dd hh24:mi:ss')", appTime);
		}
		if(StringUtil.isNotBlank(createTime)){
			sqlp.eq("to_char(a.createTime,'yyyy-MM-dd hh24:mi:ss')", createTime);
		}
		sql=sql.append(sqlp.getParam());
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
		
	}
	
	public void approval(Long id,String appState,Long approverId,String approverNo,String approverName,Date appTime){
		String sql = "update CSMS_VehicleModify_Apply set appstate=?,approverId=?,approverNo=?,approverName=?,appTime=? where id=?";
		saveOrUpdate(sql, appState,approverId,approverNo,approverName,appTime,id);
		
	}
	
	public VehicleModifyApply findById(Long id) {
		String sql = "select * from CSMS_VehicleModify_Apply where id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		VehicleModifyApply vehicleModifyApply = null;
		if(list.size()>0){
			vehicleModifyApply = new VehicleModifyApply();
			convert2Bean(list.get(0), vehicleModifyApply);
		}
		return vehicleModifyApply;
	}
	
	public Map<String,Object> detailList(Long id,String rootPath){
		String sql ="select '"+rootPath +"' rootPath,";
		sql = sql + "a.id,c.Organ,c.idtype,c.idcode,oldVehicleType, newVehicleType, oldvehicleWeightLimits, newvehicleWeightLimits, "
				+ "oldNSCvehicletype, newNSCvehicletype, appState, approverName,i.vehiclePlate,i.vehicleColor,oldPicAddr,newPicAddr,"
				+ " to_char(appTime,'yyyy-MM-dd hh24:mi:ss') appTime ,to_char(a.createTime,'yyyy-MM-dd hh24:mi:ss') createTime,i.opername,i.placename  "
				+ "from CSMS_VehicleModify_Apply a join CSMS_Vehicle_Info i on i.id=a.vehicleID join csms_customer c on c.id=i.customerID  where a.id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		if(list.size()>0)
			return list.get(0);
		return null;
		
	}
}
