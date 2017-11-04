package com.hgsoft.invoice.service;

import com.hgsoft.invoice.dao.PassInvoiceDao;
import com.hgsoft.invoice.entity.PassInvoice;
import com.hgsoft.invoice.serviceInterface.IPassInvoiceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PassInvoiceService implements IPassInvoiceService{
	
	private PassInvoiceDao passInvoiceDao;
	@Resource
	public void setPassInvoiceDao(PassInvoiceDao passInvoiceDao) {
		this.passInvoiceDao = passInvoiceDao;
	}
	@Override
	public PassInvoice findById(Long id) {
		return passInvoiceDao.findById(id);
	}
	
}
