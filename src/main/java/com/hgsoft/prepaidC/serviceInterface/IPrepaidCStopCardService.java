package com.hgsoft.prepaidC.serviceInterface;


import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;

public interface IPrepaidCStopCardService {
	public PrepaidCBussiness find(PrepaidCBussiness prepaidCBussiness);
	public void addBussinessRecord(PrepaidCBussiness prepaidCBussiness);
	public void addCancelRecord(Cancel cancel);
	public Map<String, Object> saveStopCard(ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness prepaidCBussiness,Cancel cancel, Customer customer,PrepaidC prepaidC,Map<String,Object> params);
	public Map<String, Object> saveStopCardForAMMS(ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness prepaidCBussiness,Cancel cancel, Customer customer,PrepaidC prepaidC,Map<String,Object> params);
	public Pager findStopCardByCustomer(Pager pager,Customer customer);
	public Cancel findCancelById(Long id);
	public void update(Cancel cancel);
	public Cancel findCancel(Cancel cancel);
	public Pager findStopCardByCustomer(Pager pager, Customer customer, PrepaidC prepaidC);
	public Pager findByCodeAndCusID(Pager pager,Cancel cancel,Customer customer);
	public Pager findByCodeAndCusIDForAMMS(Pager pager,Cancel cancel,Customer customer,String cardType,String bankCode);
	public Pager findByCancelId(Pager pager,Cancel cancel,Long customerId);
	public Map<String, Object> saveStopCardAgain(ServiceFlowRecord serviceFlowRecord,RefundInfo refundInfo,Customer customer,PrepaidC prepaidC,String materialIds,Long bussinessPlaceId);
	public Map<String, Object> saveStopCardAgainForAMMS(ServiceFlowRecord serviceFlowRecord,RefundInfo refundInfo,Customer customer,PrepaidC prepaidC,String materialIds);
	/**
	 * 储值卡终止使用首次退款
	 * @param prepaidCBussiness
	 * @param cancel 只有id
	 * @param refundInfo
	 * @param customer
	 * @param prepaidC
	 * @param bussinessPlaceId 营业部
	 * @return Map<String,Object>
	 */
	public Map<String, Object> savePrepaidCRefund(PrepaidCBussiness prepaidCBussiness,Cancel cancel,RefundInfo refundInfo,Customer customer,PrepaidC prepaidC,Long bussinessPlaceId,Map<String,Object> params);
	/**
	 * 根据储值卡卡号，查询出来该储值卡的注销退款记录
	 * @param pager
	 * @param cancel
	 * @return Pager
	 */
	public Pager findCancelRefundByCardnNo(Pager pager,Cancel cancel);
	/**
	 * 找到该卡所有退款记录
	 * @param pager
	 * @param cardNo
	 * @return Pager
	 */
	public Pager findByCardNo(Pager pager,String cardNo);
	public Pager findByCardNoForAMMS(Pager pager,String cardNo);
	/**
	 * 根据终止使用的卡号，查询其继承的所有未完成的资金转移记录
	 * @param cardNo 终止使用的卡号
	 * @return List
	 */
	public List findDbasCardFlows4StopCard(String cardNo);
}
