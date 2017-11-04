package com.hgsoft.prepaidC.service;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.prepaidC.serviceInterface.ICancelService;
import com.hgsoft.unifiedInterface.service.CustomerUnifiedInterfaceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CancelService implements ICancelService{
	
	private static Logger logger = Logger.getLogger(CancelService.class.getName());
	
	private CancelDao cancelDao;
	@Resource
	public void setCancelDao(CancelDao cancelDao) {
		this.cancelDao = cancelDao;
	}
	@Resource
	private CustomerUnifiedInterfaceService customerUnifiedInterfaceService;
	
	@Override
	public Cancel find(Cancel cancel) {
		return cancelDao.find(cancel);
	}

	@Override
	public Cancel findById(Long id) {
		return cancelDao.findById(id);
	}

	@Override
	public void saveCancel(Customer customer, Cancel cancel,ServiceFlowRecord serviceFlowRecord) {
		try {
			customerUnifiedInterfaceService.saveCancel(customer, cancel,serviceFlowRecord);
		} catch (ApplicationException e) {
			logger.error("注销客户失败，客户id:"+customer.getId()+";注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("注销客户失败，客户id:"+customer.getId()+";注销登记id:"+cancel.getId());
		}
		
	}

	@Override
	public void updateCancel(Cancel cancel) {
		try {
			customerUnifiedInterfaceService.updateCancel(cancel);
		} catch (ApplicationException e) {
			logger.error("修改客户注销信息,注销登记id:"+cancel.getId());
			e.printStackTrace();
			throw new ApplicationException("修改客户注销信息,注销登记id:"+cancel.getId());
		}
		
	}

	@Override
	public List<Cancel> findCancelLists(String customerId, String flag, String code, String creditCardNo, String bankCode, String source) {
		try{

			return cancelDao.findCancelLists(customerId,flag,code,creditCardNo,bankCode,source);
		}catch (Exception e) {
			logger.error("查找注销登记信息错误，客户id:"+customerId);
			e.printStackTrace();
			throw new ApplicationException("查找注销登记信息错误，客户id:"+customerId);
		}
	}

	@Override
	public void deleteById(Long id) {
		try{
			cancelDao.delete(id);
		}catch (ApplicationException e) {
			logger.error("删除注销登记信息错误，注销登记表id:"+id);
			e.printStackTrace();
			throw new ApplicationException("删除注销登记信息错误，注销登记表id:"+id);
		}
	}
}
