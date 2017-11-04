package com.hgsoft.customer.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.utils.SequenceUtil;
/**
 * 
 * @author guanshaofeng
 * 2016-05-24
 */
@Repository
public class VehicleBussinessDao extends BaseDao{
	@Resource
	private SequenceUtil sequenceUtil;
	
	public void save(VehicleBussiness vehicleBussiness) {
		if(vehicleBussiness.getId()==null){
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
		}
		Map map = FieldUtil.getPreFieldMap(VehicleBussiness.class,vehicleBussiness);
		StringBuffer sql=new StringBuffer("insert into CSMS_Vehicle_Bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public VehicleBussiness findById(Long id) {
		String sql = "select * from CSMS_Vehicle_bussiness where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		VehicleBussiness vehicleBussiness = null;
		if (!list.isEmpty()) {
			vehicleBussiness = new VehicleBussiness();
			this.convert2Bean(list.get(0), vehicleBussiness);
		}

		return vehicleBussiness;
	}
	
	public VehicleBussiness findByTagNo(String tagNo) {
		String sql = "select * from CSMS_Vehicle_bussiness where tagNo="+tagNo;
		List<Map<String, Object>> list = queryList(sql);
		VehicleBussiness vehicleBussiness = null;
		if (!list.isEmpty()) {
			vehicleBussiness = new VehicleBussiness();
			this.convert2Bean(list.get(0), vehicleBussiness);
		}
		return vehicleBussiness;
	}
	
	public VehicleBussiness findByCardNoAndType(String cardNo,String type) {
		String sql = "select * from CSMS_Vehicle_bussiness where CardNo='"+cardNo+"' and CardType='2' and type='"+type+"' order by id desc";
		List<Map<String, Object>> list = queryList(sql);
		VehicleBussiness vehicleBussiness = null;
		if (!list.isEmpty()) {
			vehicleBussiness = new VehicleBussiness();
			this.convert2Bean(list.get(0), vehicleBussiness);
		}

		return vehicleBussiness;
	}
	
	/**
	 * 根据卡号查找上次有卡挂起或无卡挂起的车辆业务记录
	 * @param cardNo
	 * @return VehicleBussiness
	 */
	public VehicleBussiness findBeforeStopVehicleBussiness(String cardNo){
		String sql = "select "+FieldUtil.getFieldMap(VehicleBussiness.class,new VehicleBussiness()).get("nameStr")+" from CSMS_Vehicle_bussiness where cardno=? and type in(?,?) order by id desc";
		List<Map<String, Object>> list = queryList(sql,cardNo,VehicleBussinessEnum.accountCDisabledWithCard.getValue(),VehicleBussinessEnum.accountCDisabledWithoutCard.getValue());
		VehicleBussiness vehicleBussiness = null;
		if (!list.isEmpty()) {
			vehicleBussiness = new VehicleBussiness();
			this.convert2Bean(list.get(0), vehicleBussiness);
		}

		return vehicleBussiness;
	}
	
	/** 
	 * @author luningyun
	 * @description 营改增新增根据卡号查找储值卡上次有卡挂起或无卡挂起的车辆业务记录
	 * @param cardNo
	 * @return VehicleBussiness
	 */
	public VehicleBussiness findStoreBeforeStopVehicleBussiness(String cardNo){
		String sql = "select "+FieldUtil.getFieldMap(VehicleBussiness.class,new VehicleBussiness()).get("nameStr")+" from CSMS_Vehicle_bussiness where cardno=? and type in(?,?) order by id desc";
		List<Map<String, Object>> list = queryList(sql,cardNo,VehicleBussinessEnum.prepaidCDisabledWithCard.getValue(),VehicleBussinessEnum.prepaidCDisabledWithoutCard.getValue());
		VehicleBussiness vehicleBussiness = null;
		if (!list.isEmpty()) {
			vehicleBussiness = new VehicleBussiness();
			this.convert2Bean(list.get(0), vehicleBussiness);
		}

		return vehicleBussiness;
	}
}
