package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.CustomerAdvance;
import com.hgsoft.customer.entity.VehicleAdvance;

@Repository
public class AdvanceEntryDao extends BaseDao {

	public CustomerAdvance findCustomerAdvance(String idType, String idCode) {
		CustomerAdvance temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_Customer_advanceEntry where 1=1 ");
		sql.append(" and idType = '" + idType +"'");
		sql.append(" and idCode = '" + idCode +"'");

		List<Map<String, Object>> list = queryList(sql.toString());
		if (!list.isEmpty()) {
			temp = new CustomerAdvance();
			this.convert2Bean(list.get(0), temp);
		}


		return temp;
	}

	public int[] batchSaveCustomer(final List<CustomerAdvance> list) {
		String sql = "insert into CSMS_Customer_advanceEntry(ID,Organ,UserType,LinkMan,IdType," +
				"IdCode,Tel,Mobile,ShortTel,Addr,ZipCode,Email,invoiceTitle,InvoiceFlag,EmailFlag," +
				"AddFlag,MonthFlag,OperID,PlaceID,ImportTime,ImportPlace,ImportOper," +
				"operNo,operName,placeNo,placeName,importOperNo,importOperName,importPlaceNo,importPlaceName) " +
				"values(SEQ_CSMS_Customer_advEntry_NO.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?)";
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				CustomerAdvance customerAdvance = list.get(i);
				ps.setString(1,customerAdvance.getOrgan());
				ps.setString(2,customerAdvance.getUserType());
				ps.setString(3,customerAdvance.getLinkMan());
				ps.setString(4,customerAdvance.getIdType());
				ps.setString(5,customerAdvance.getIdCode());
				ps.setString(6, customerAdvance.getTel());
				ps.setString(7,customerAdvance.getMobile());
				ps.setString(8,customerAdvance.getShortTel());
				ps.setString(9,customerAdvance.getAddr());
				ps.setString(10,customerAdvance.getZipCode());
				ps.setString(11,customerAdvance.getEmail());
				ps.setString(12,customerAdvance.getInvoiceTitle());
				ps.setString(13, customerAdvance.getInvoiceFlag());
				ps.setString(14,customerAdvance.getEmailFlag());
				ps.setString(15,customerAdvance.getAddFlag());
				ps.setString(16,customerAdvance.getMonthFlag());
				ps.setLong(17,customerAdvance.getOperId());
				ps.setLong(18,customerAdvance.getPlaceId());
			//	ps.setDate(19,new Date(customerAdvance.getImportTime().getTime()));
				ps.setLong(19,customerAdvance.getImportPlace());
				ps.setLong(20,customerAdvance.getImportOper());
				
				ps.setString(21, customerAdvance.getOperNo());
				ps.setString(22, customerAdvance.getOperName());
				ps.setString(23, customerAdvance.getPlaceNo());
				ps.setString(24, customerAdvance.getPlaceName());
				ps.setString(25, customerAdvance.getImportOperNo());
				ps.setString(26, customerAdvance.getImportOperName());
				ps.setString(27, customerAdvance.getImportPlaceNo());
				ps.setString(28, customerAdvance.getImportPlaceName());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}

	public int[] batchSaveVehicle(final List<VehicleAdvance> list) {
		String sql = "insert into CSMS_Vehicle_advanceEntry(ID,vehiclePlate,vehicleColor,vehicleUserType," +
				"UsingNature,IdentificationCode,VehicleType,vehicleWheels,vehicleAxles,vehicleWheelBases," +
				"vehicleWeightLimits,vehicleSpecificInformation,vehicleEngineNo,vehicleWidth,vehicleLong," +
				"vehicleHeight,owner,Model,OperID,PlaceID,ImportTime,ImportPlace,ImportOper," +
				"operNo,operName,placeNo,placeName,importOperNo,importOperName,importPlaceNo,importPlaceName)" +
				" values(SEQ_CSMS_Vehicle_advEntry_NO.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?)";
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				VehicleAdvance v = list.get(i);
				ps.setString(1, v.getVehiclePlate());
				ps.setString(2, v.getVehicleColor());
				ps.setString(3, v.getVehicleUserType());
				ps.setString(4, v.getUsingNature());
				ps.setString(5, v.getIdentificationCode());
				ps.setString(6, v.getVehicleType());
				ps.setLong(7, v.getVehicleWheels());
				ps.setLong(8, v.getVehicleAxles());
				ps.setLong(9, v.getVehicleWheelBases());
				ps.setLong(10, v.getVehicleWeightLimits());
				ps.setString(11, v.getVehicleSpecificInformation());
				ps.setString(12, v.getVehicleEngineNo());
				ps.setLong(13, v.getVehicleWidth());
				ps.setLong(14, v.getVehicleLong());
				ps.setLong(15, v.getVehicleHeight());
				ps.setString(16, v.getOwner());
				ps.setString(17, v.getModel());
				ps.setLong(18, v.getOperId());
				ps.setLong(19, v.getPlaceId());
				//ps.setDate(20, new Date(v.getImportTime().getTime()));
				ps.setLong(20, v.getImportPlace());
				ps.setLong(21, v.getImportOper());
			//	ps.setLong(22, v.getCusAdvEntryId());
				
				ps.setString(22, v.getOperNo());
				ps.setString(23, v.getOperName());
				ps.setString(24, v.getPlaceNo());
				ps.setString(25, v.getPlaceName());
				ps.setString(26, v.getImportOperNo());
				ps.setString(27, v.getImportOperName());
				ps.setString(28, v.getImportPlaceNo());
				ps.setString(29, v.getImportPlaceName());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		
	}

	public VehicleAdvance findVehicleAdvance(String vehiclePlate,String vehicleColor) {
		VehicleAdvance temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_Vehicle_advanceEntry where 1=1 ");
		sql.append(" and vehiclePlate = '" + vehiclePlate+"'");
		sql.append(" and vehicleColor = '" + vehicleColor+"'");
		sql.append(" order by importtime desc ");
		List<Map<String, Object>> list = queryList(sql.toString());
		if (!list.isEmpty()) {
			temp = new VehicleAdvance();
			this.convert2Bean(list.get(0), temp);
		}


		return temp;
	}

	
	
}
