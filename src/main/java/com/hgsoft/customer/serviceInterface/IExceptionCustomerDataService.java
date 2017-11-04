package com.hgsoft.customer.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.httpInterface.entity.CustomerModifyApply;

public interface IExceptionCustomerDataService {
	/**
	 * 查询异常客户数据
	 * @param searchCustomer
	 * @param searchVehicle
	 * @param cardNo
	 * @param tagNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findExceptionCustomerDatas(Customer searchCustomer,VehicleInfo searchVehicle,String cardNo,String tagNo);
	/**
	 * 查询产品信息
	 * @param customerId
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findProductInfoList(Long customerId); 
	/**
	 * 查询车辆信息
	 * @param customerId
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findVehicleList(Long customerId); 
	/**
	 * 修改异常客户信息
	 * @param customer
	 * @param customerBussiness
	 * @param customerModifyApply 若修改了证件类型或证件号码或客户名称，则填
	 * @return void
	 */
	public void updateExceptionCustomer(Customer customer,CustomerBussiness customerBussiness,Customer beforeCustomer);
	/***
	 * 保存异常客户合并信息
	 * @param existCustomer 已存在的客户
	 * @param thisCustomer 要合并的客户
	 * @param customerBussiness
	 * @param params (客户验证类型)
	 * @return Map<String,Object>
	 */
	public Map<String, Object> saveCombine(Customer existCustomer,Customer thisCustomer,CustomerBussiness customerBussiness,Map<String,Object> params);

	/**
	 * 查询异常客户合并记录列表
	 * @param combineStartTime
	 * @param combineEndTime
	 * @param searchCustomer
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findCustomerCombineRecordList(String combineStartTime,String combineEndTime,Customer searchCustomer);
}
