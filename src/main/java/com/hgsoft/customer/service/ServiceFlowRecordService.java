package com.hgsoft.customer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.serviceInterface.IServiceFlowRecordService;

@Service
public class ServiceFlowRecordService implements IServiceFlowRecordService {
	
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	public void setServiceFlowRecordDao(ServiceFlowRecordDao serviceFlowRecordDao) {
		this.serviceFlowRecordDao = serviceFlowRecordDao;
	}
	
	/*@Override
	public ServiceFlowRecord findByCardTagNoAndCode(String cardTagNo,String servicePTypeCode,String serviceTypeCode){
		return serviceFlowRecordDao.findByCardTagNoAndCode(cardTagNo, servicePTypeCode, serviceTypeCode);
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<ServiceFlowRecord> halfList(String cardNo) {
		return serviceFlowRecordDao.halfList(cardNo);
	}*/

}
