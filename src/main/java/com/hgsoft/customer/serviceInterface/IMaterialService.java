package com.hgsoft.customer.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.VehicleInfo;

public interface IMaterialService {

	public List<Material> findMateria(Material material);
	public void updateVehicleId(String materialIds,Long vehicleId);
	
	public Map<String, Object> saveMateria(Customer customer,VehicleInfo vehicleInfo,Material material,String[] tempPicNameList,String[] deleteOldMaterialIDList,String species);
	
	/**
	 * 根据退款记录id查找图片资料
	 * @param refundId
	 * @return List<Material>
	 */
	public List<Material> findPreCardRefund(Long refundId);
	/**
	 * 根据退款记录id、图片类型获取图片资料
	 * @param bussinessid 退款记录id
	 * @param type 图片类型，根据退款记录类型转化
	 * @return List<Material>
	 */
	public List<Material> findRefundMaterial(Long bussinessid,String type);
	public List<Material> findRefundMaterialForAMMS(Long bussinessid);
}
