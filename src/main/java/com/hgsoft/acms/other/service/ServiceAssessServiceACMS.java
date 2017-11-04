package com.hgsoft.acms.other.service;

import java.util.Date;
import java.math.BigDecimal;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptPrintDao;
import com.hgsoft.other.dao.ServiceAssessDao;
import com.hgsoft.other.dao.ServiceAssessDetailDao;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.other.entity.ServiceAssess;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.serviceInterface.IServiceAssessService;
import com.hgsoft.utils.SequenceUtil;

@Service
public class ServiceAssessServiceACMS implements IServiceAssessService {
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ServiceAssessDao serviceAssessDao;
	@Resource
	private ServiceAssessDetailDao serviceAssessDetailDao;
	@Resource
	private ReceiptPrintDao receiptPrintDao;

	@Override
	public ServiceAssess findByCustomerIdAndTime(Customer customer) {
		return serviceAssessDao.findByCustomerIdAndTime(customer);
	}
	
	@Override
	public ServiceAssess findByCustomerIdAndBeginTime(Customer customer) {
		return serviceAssessDao.findByCustomerIdAndTime(customer);
	}

	@Override
	public void updateServiceAssess(ServiceAssess serviceAssess) {
		serviceAssessDao.update(serviceAssess);
	}

	@Override
	public void addServiceAssess(ServiceAssess serviceAssess) {
		BigDecimal SEQ_CSMSServiceAssess = sequenceUtil.getSequence("SEQ_CSMSServiceAssess");
		serviceAssess.setId(Long.valueOf(SEQ_CSMSServiceAssess.toString()));
		
		serviceAssessDao.save(serviceAssess);
	}
	
	private static Logger logger = Logger.getLogger(ServiceAssessServiceACMS.class.getName());

	
	@Override
	public boolean lgout(Long customerID,String assessLevel) {
		try {
			Date now = new Date();
			//服务评价
			ServiceAssess serviceAssess = serviceAssessDao.findByCustomer(customerID);
			if (serviceAssess != null) {
				//更新服务评价主表
				serviceAssess.setEndTime(now);
				serviceAssess.setAssessTime(now);
				serviceAssess.setAssessLevel(assessLevel);
				serviceAssessDao.update(serviceAssess);
			}
			//回执打印
			ReceiptPrint receiptPrint = receiptPrintDao.findByCustomer(customerID);
			if (receiptPrint != null) {
				//更新回执打印
				receiptPrint.setEndTime(now);
				receiptPrintDao.update(receiptPrint);
			}
			//服务评价明细和回执打印明细
			serviceAssessDetailDao.batchInsertByCustomer(serviceAssess,receiptPrint,customerID);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"结束服务失败");
			throw new ApplicationException();
		}
		
		return false;
	}
	
}
