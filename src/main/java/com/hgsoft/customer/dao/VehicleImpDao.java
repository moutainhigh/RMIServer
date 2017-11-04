package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleImp;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class VehicleImpDao extends BaseDao {
	public void updateFlag2(Long id){
		String sql = "update csms_vehicle_imp set flag='0' where id=?";
		saveOrUpdate(sql, id);
	}
	
	public VehicleImp findByVehicleImp(VehicleImp vehicleImp){
		StringBuffer sql = new StringBuffer("select vi.* from csms_vehicle_imp vi where 1=1");
		SqlParamer param = new SqlParamer();
		String vehiclePlate = vehicleImp.getVehiclePlate();
		if(StringUtil.isNotBlank(vehiclePlate)){
			param.eq("vi.vehiclePlate",vehiclePlate);
		}
		String vehicleColor = vehicleImp.getVehicleColor();
		if(StringUtil.isNotBlank(vehicleColor)){
			param.eq("vi.vehicleColor",vehicleColor);
		}
		
		sql.append(param.getParam());
		List<Map<String,Object>> list = queryList(sql.toString(),param.getList().toArray());
		
		VehicleImp temp = null;
		if(list.size()>0){
			temp = new VehicleImp();
			convert2Bean(list.get(0), temp);
		}
		return temp;
	}
	
	public void updateFlag(Long id){
		String sql = "update csms_vehicle_imp set flag='1' where id=?";
		saveOrUpdate(sql, id);
	}
	
	public List<Map<String,Object>> findByPlateAndColor(String vehiclePlate,String vehicleColor){
		String sql = "select * from csms_vehicle_imp where flag='0' and vehiclePlate=? and vehicleColor=?";
		return queryList(sql, vehiclePlate,vehicleColor);
	}
	public VehicleImp findByPlateAndColor(VehicleImp vehicleImp) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_vehicle_imp where vehiclePlate=? and vehicleColor=?";
		List<Map<String,Object>> list = queryList(sql,vehicleImp.getVehiclePlate(),vehicleImp.getVehicleColor());
		VehicleImp vehicle = null;
		if(list.size()>0){
			vehicle = new VehicleImp();
			convert2Bean(list.get(0), vehicle);
		}
		return vehicle;
	}
	
	public int[] batchSaveVehicle(final List<VehicleImp> list,final ServiceFlowRecord serviceFlowRecord) {
		String sql = "insert into CSMS_vehicle_Imp(id,vehiclePlate,vehicleColor,model,vehicleType,vehicleWeightLimits,vehicleWidth,vehicleAxles,vehicleWheels,vehicleHeight,vehicleLong,vehicleEngineNo,usingNature,identificationCode,owner,flag,impTime,updateTime,operId,operName,operNo,placeId,placeName,placeNo) " +
				"values(SEQ_CSMS_CustomerImp_NO.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'0',sysdate,sysdate,?,?,?,?,?,?)";
		
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				VehicleImp vehicleImp = list.get(i);
				ps.setString(1,vehicleImp.getVehiclePlate());
				ps.setString(2,vehicleImp.getVehicleColor());
				ps.setString(3,vehicleImp.getModel());
				ps.setString(4,vehicleImp.getVehicleType());
				ps.setLong(5,vehicleImp.getVehicleWeightLimits());
				ps.setLong(6,vehicleImp.getVehicleWidth());
				if(vehicleImp.getVehicleAxles()==null)ps.setNull(7, Types.BIGINT);else ps.setLong(7, vehicleImp.getVehicleAxles());
				if(vehicleImp.getVehicleWheels()==null)ps.setNull(8, Types.BIGINT);else ps.setLong(8, vehicleImp.getVehicleWheels());
				ps.setLong(9,vehicleImp.getVehicleHeight());
				ps.setLong(10,vehicleImp.getVehicleLong());
				ps.setString(11,vehicleImp.getVehicleEngineNo());
				ps.setString(12,vehicleImp.getUsingNature());
				ps.setString(13,vehicleImp.getIdentificationCode());
				ps.setString(14,vehicleImp.getOwner());
				ps.setLong(15,serviceFlowRecord.getOperID());
				ps.setString(16,serviceFlowRecord.getOperName());
				ps.setString(17,serviceFlowRecord.getOperNo());
				ps.setLong(18,serviceFlowRecord.getPlaceID());
				ps.setString(19,serviceFlowRecord.getPlaceName());
				ps.setString(20,serviceFlowRecord.getPlaceNo());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
}
