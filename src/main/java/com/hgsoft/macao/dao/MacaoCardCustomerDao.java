package com.hgsoft.macao.dao;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
@Repository
public class MacaoCardCustomerDao extends BaseDao{
	
	public MacaoCardCustomer findByTagNo(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mcc.* from csms_macao_card_customer mcc join csms_macao_bankaccount mb on mcc.id=mb.mainid join csms_cardholder_info ci on ci.macaobankaccountid=mb.id join csms_tag_info ti on ci.typeid=ti.id where ci.type='3' and ti.tagno=?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			convert2Bean(list.get(0), macaoCardCustomer);
		}
		return macaoCardCustomer;
	}
	
	public MacaoCardCustomer getMacaoCardCustomerByTagNo(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select ti.tagno,mcc.* from csms_macao_card_customer mcc join  csms_cardholder_info ci on mcc.id=ci.macaocarcustomerid join csms_tag_info ti on ti.id=ci.typeid where ci.type='3' and ti.tagno=?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			convert2Bean(list.get(0), macaoCardCustomer);
		}
		return macaoCardCustomer;
	}
	
	public MacaoCardCustomer getMacaoCardCustomerById(Long id) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select ti.tagno,mcc.* from csms_macao_card_customer mcc join  csms_cardholder_info ci on mcc.id=ci.macaocarcustomerid join csms_tag_info ti on ti.id=ci.typeid where ci.type='3' and ti.id=?";
		List<Map<String,Object>> list = queryList(sql,id);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			convert2Bean(list.get(0), macaoCardCustomer);
		}
		return macaoCardCustomer;
	}
	
	/*public MacaoCardCustomer getMacaoCardCustomerByAccountCId(Long id) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mcc.* from csms_macao_card_customer mcc join csms_cardholder_info ci on mcc.id=ci.macaocarcustomerid where  ci.type='2' and ci.typeid=?";
		List<Map<String,Object>> list = queryList(sql,id);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			convert2Bean(list.get(0), macaoCardCustomer);
		}
		return macaoCardCustomer;
	}*/
	/**
	 * 传入车牌号码、车牌颜色，查找该车辆所属持卡人
	 * @param vehicleInfo
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @return MacaoCardCustomer
	 */
	public MacaoCardCustomer findByVehicleInfo(VehicleInfo vehicleInfo) {
		String sql = "select mcc.* from csms_macao_card_customer mcc join csms_macao_bankaccount mb on mb.mainid=mcc.id join csms_cardholder_info ci on mb.id=ci.macaobankaccountid join csms_vehicle_info vi on vi.id=ci.typeid where  ci.type='1' and vi.vehiclePlate=? and vi.vehicleColor=?";
		List<Map<String,Object>> list = queryList(sql,vehicleInfo.getVehiclePlate(),vehicleInfo.getVehicleColor());
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			convert2Bean(list.get(0), macaoCardCustomer);

		}
		return macaoCardCustomer;
	}
	
}
