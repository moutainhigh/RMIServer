package com.hgsoft.customer.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class VehicleInfoHisDao extends BaseDao {
	@Autowired
	private SequenceUtil sequenceUtil;

	public void save(VehicleInfoHis vehicleInfoHis) {
		StringBuffer sql = new StringBuffer("insert into CSMS_Vehicle_Info_his(");
		sql.append(FieldUtil.getFieldMap(VehicleInfoHis.class,vehicleInfoHis).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(VehicleInfoHis.class,vehicleInfoHis).get("valueStr")+")");
		super.save(sql.toString());
	}
	
	/**
	 * 保存车辆历史
	 * @param vehicleInfo 查找出来的车辆信息记录
	 * @param genReason 历史产生原因
	 * @return Long 返回历史id
	 */
	public Long saveVehicleInfoHis(VehicleInfo vehicleInfo,String genReason){
		VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
		vehicleInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfoHis_NO"));
		vehicleInfoHis.setGenTime(new Date());
		vehicleInfoHis.setGenReason(genReason); // 1表示修改，2表示删除
		this.saveHis(vehicleInfoHis, vehicleInfo);
		return vehicleInfoHis.getId();
	}
	
	public void saveHis(VehicleInfoHis vehicleInfoHis, VehicleInfo vehicleInfo) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_Vehicle_Info_his(id,customerID,GenTime,vehiclePlate,vehicleColor,UsingNature,IdentificationCode,"
				+ "VehicleType,vehicleWheels,vehicleAxles,vehicleWheelBases,vehicleWeightLimits,vehicleSpecificInformation,"
				+ "vehicleEngineNo,vehicleWidth,vehicleLong,vehicleHeight,owner,Model,OperID,PlaceID,createTime,HisSeqID,GenReason,NSCVehicleType,vehicleUserType ) "
				+ "SELECT "+vehicleInfoHis.getId()
				+ ",customerID,to_date('"+format.format(vehicleInfoHis.getGenTime())+"','YYYY-MM-DD HH24:MI:SS'),"
				+ "vehiclePlate,vehicleColor,UsingNature,IdentificationCode,"
				+ "VehicleType,vehicleWheels,vehicleAxles,vehicleWheelBases,vehicleWeightLimits,vehicleSpecificInformation,"
				+ "vehicleEngineNo,vehicleWidth,vehicleLong,vehicleHeight,owner,Model,OperID,PlaceID,createTime,HisSeqID,"
				+ vehicleInfoHis.getGenReason()+" ,NSCVehicleType,vehicleUserType FROM CSMS_Vehicle_Info WHERE id="+vehicleInfo.getId()+"");
		super.save(sql.toString());
	}
	
	public VehicleInfoHis findByHisId(Long hisId) {
		String sql = "select * from CSMS_Vehicle_Info_his ";
		if(hisId==null){
			sql=sql+" where HisSeqID is null";
		}else{
			sql=sql+" where HisSeqID="+hisId;
		}
		List<Map<String, Object>> list = queryList(sql);
		VehicleInfoHis vehicleInfoHis = null;
		if (!list.isEmpty()) {
			vehicleInfoHis = new VehicleInfoHis();
			this.convert2Bean(list.get(0), vehicleInfoHis);
		}

		return vehicleInfoHis;
	}
	
	public VehicleInfoHis findByHisVc(String v,String c) {
		String sql = "select * from CSMS_Vehicle_Info_his where vehicleplate=? and vehiclecolor=? ";
		List<Map<String, Object>> list = queryList(sql,v,c);
		VehicleInfoHis vehicleInfoHis = null;
		if (!list.isEmpty()) {
				vehicleInfoHis = new VehicleInfoHis();
				this.convert2Bean(list.get(0), vehicleInfoHis);
			}

		return vehicleInfoHis;
	}

	public VehicleInfoHis findById(Long id){
		String sql = "select "+ FieldUtil.getFieldMap(VehicleInfoHis.class,new VehicleInfoHis()).get("nameStr")+" from CSMS_Vehicle_Info_his where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		VehicleInfoHis vehicleInfoHis = null;
		if (!list.isEmpty()) {
			vehicleInfoHis = new VehicleInfoHis();
			this.convert2Bean(list.get(0), vehicleInfoHis);
		}
		return vehicleInfoHis;
	}
}
