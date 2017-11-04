package com.hgsoft.prepaidC.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCStopCardService;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.Pager;

@Service
public class PrepaidCStopCardService implements IPrepaidCStopCardService{
	
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private CancelDao cancelDao;
	@Resource
	private PrepaidCUnifiedInterfaceService prepaidCUnifiedInterfaceService;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	@Resource
	private RefundInfoDao refundInfoDao;

	@Override
	public PrepaidCBussiness find(PrepaidCBussiness prepaidCBussiness) {
		return prepaidCBussinessDao.find(prepaidCBussiness);
	}



	@Override
	public void addCancelRecord(Cancel cancel) {
		cancelDao.save(cancel);
	}



	@Override
	public void addBussinessRecord(PrepaidCBussiness prepaidCBussiness) {
		prepaidCBussinessDao.save(prepaidCBussiness);
	}






	@Override
	public Map<String, Object> saveStopCard(ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness prepaidCBussiness, Cancel cancel,Customer customer,PrepaidC prepaidC,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveStopCard(serviceFlowRecord,prepaidCBussiness, cancel,customer,prepaidC,params);
		
	}
	@Override
	public Map<String, Object> saveStopCardForAMMS(ServiceFlowRecord serviceFlowRecord,PrepaidCBussiness prepaidCBussiness, Cancel cancel,Customer customer,PrepaidC prepaidC,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.saveStopCardForAMMS(serviceFlowRecord,prepaidCBussiness, cancel,customer,prepaidC,params);
		
	}
	@Override
	public Map<String, Object> saveStopCardAgain(ServiceFlowRecord serviceFlowRecord,RefundInfo refundInfo,Customer customer,PrepaidC prepaidC,String materialIds,Long bussinessPlaceId) {
		return prepaidCUnifiedInterfaceService.saveStopCardAgain(serviceFlowRecord,refundInfo,customer,prepaidC,materialIds,bussinessPlaceId);
		
	}
	@Override
	public Map<String, Object> saveStopCardAgainForAMMS(ServiceFlowRecord serviceFlowRecord,RefundInfo refundInfo,Customer customer,PrepaidC prepaidC,String materialIds) {
		return prepaidCUnifiedInterfaceService.saveStopCardAgainForAMMS(serviceFlowRecord,refundInfo,customer,prepaidC,materialIds);
		
	}



	@Override
	public Pager findStopCardByCustomer(Pager pager,Customer customer) {
		return prepaidCDao.findStopCardByCustomer(pager,customer);
	}



	@Override
	public Cancel findCancelById(Long id) {
		return cancelDao.findById(id);
	}



	@Override
	public void update(Cancel cancel) {
		cancelDao.update(cancel);
	}



	@Override
	public Cancel findCancel(Cancel cancel) {
		return cancelDao.find(cancel);
	}



	@Override
	public Pager findStopCardByCustomer(Pager pager, Customer customer, PrepaidC prepaidC) {
		return prepaidCDao.findStopCardByCustomer(pager,customer,prepaidC);
	}
	
	public Pager findByCodeAndCusID(Pager pager,Cancel cancel,Customer customer){
		//return cancelDao.findByCodeAndCusID(pager, cancel, customer);
		return cancelDao.findCancelList(pager, cancel, customer);
	}
	
	public Pager findByCodeAndCusIDForAMMS(Pager pager,Cancel cancel,Customer customer,String cardType,String bankCode){
		return cancelDao.findByCodeAndCusIDForAMMS(pager, cancel, customer,cardType,bankCode);
	}



	/**
	 * @Descriptioqn:
	 * @param pager
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年5月8日
	 */
	@Override
	public Pager findByCancelId(Pager pager,Cancel cancel,Long customerId) {
		return cancelDao.findByCancelId(pager,cancel,customerId);
	}


	/*
	 * 根据卡号查询出储值卡注销退款记录
	 * (non-Javadoc)
	 * @see com.hgsoft.prepaidC.serviceInterface.IPrepaidCStopCardService#findCancelRefundByCardnNo(com.hgsoft.utils.Pager, com.hgsoft.prepaidC.entity.Cancel)
	 */
	@Override
	public Pager findCancelRefundByCardnNo(Pager pager, Cancel cancel) {
		return refundInfoDao.findCancelRefundByCardNo(pager, cancel.getCode());
	}



	@Override
	public Map<String, Object> savePrepaidCRefund(PrepaidCBussiness prepaidCBussiness,Cancel cancel, RefundInfo refundInfo,Customer customer, PrepaidC prepaidC,Long bussinessPlaceId,Map<String,Object> params) {
		return prepaidCUnifiedInterfaceService.savePrepaidCRefund(prepaidCBussiness, cancel, refundInfo, customer, prepaidC,bussinessPlaceId,params);
	}



	@Override
	public Pager findByCardNo(Pager pager, String cardNo) {
		return refundInfoDao.findByCardNo(pager, cardNo);
	}
	
	@Override
	public Pager findByCardNoForAMMS(Pager pager, String cardNo) {
		return refundInfoDao.findByCardNoForAMMS(pager, cardNo);
	}



	@Override
	public List findDbasCardFlows4StopCard(String cardNo) {
		//先找到终止使用的资金转移记录
		DbasCardFlow dbasCardFlow = dbasCardFlowDao.findPreCancelDbasCardFlow(cardNo);
		//根据终止使用的资金转移记录的原始卡号找到继承的列表
		return dbasCardFlowDao.findByOriginalCard(dbasCardFlow.getCardNo());
	}

}
