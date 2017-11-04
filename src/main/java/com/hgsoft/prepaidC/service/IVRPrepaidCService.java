package com.hgsoft.prepaidC.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.customer.serviceInterface.IBillGetService;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.customer.serviceInterface.IInvoiceService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptPrintDetailDao;
import com.hgsoft.prepaidC.dao.IVRPrepaidCDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.serviceInterface.IIVRPrepaidCService;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.SequenceUtil;

@Service
@Repository
public class IVRPrepaidCService implements IIVRPrepaidCService{

	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private IVRPrepaidCDao ivrPrepaidCDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ReceiptPrintDetailDao receiptPrintDetailDao;

	@Resource
	private ICustomerService customerService;
	@Resource
	private IBillGetService billGetService;
	@Resource
	private IInvoiceService invoiceService;

	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());
	@Override
	public List<Map<String, Object>> findCustomerInfoByCardNo(String cardno) {
		
		return ivrPrepaidCDao.findCustomerInfoByCardNo(cardno);
	}
	
	@Override
	public void updateCustomerInfo(Customer oldCustomer,
			CustomerHis customerHis, BillGet billGet, Invoice temp) {
		try {
			customerService.updateCustomer(oldCustomer, customerHis);
			billGetService.updateSerItem(billGet);
			invoiceService.update(temp);
		} catch (Exception e) {
			logger.error("客户信息修改失败");
			e.printStackTrace();
			throw new ApplicationException("客户信息修改失败");
		}
		
	}

	@Override
	public List<Map<String, Object>>  findCustomerByCardNo(String cardno) {
		// TODO Auto-generated method stub
		return ivrPrepaidCDao.findCustomerByCardNo(cardno);
	}

	

}
