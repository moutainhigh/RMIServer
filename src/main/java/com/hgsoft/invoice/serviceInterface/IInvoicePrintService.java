package com.hgsoft.invoice.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.invoice.entity.InvoicePrint;
import com.hgsoft.invoice.entity.PassInvoice;
import com.hgsoft.invoice.entity.PassRecord;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;

public interface IInvoicePrintService {

	public Pager findPassListByPager(Pager pager, PassRecord passRecord,
			Customer customer, String startTime, String endTime);

	public void savePrint(List<Map<String,Object>> list ,PassInvoice passInvoice,
			InvoicePrint invoicePrint,String type);
	
	public Pager findPassPrintAgainListByPager(Pager pager,
			String cardNo, Customer customer, Date startTime,
			Date endTime,String type);
	public InvoicePrint findInvoicePrint(Long invoicePrintId);
	public List<Map<String,Object>>  findReckOnListNoByPassInvoicId(Long id);
	public void saveRechargeInvoice(PrepaidCBussiness prepaidCBussiness,InvoicePrint invoicePrint,InvoicePrint oldInvoicePrint);
	public InvoicePrint findByBussinessID(Long bussinessID);
	public void savePrintAgain(PassInvoice passInvoice,
			InvoicePrint invoicePrint, InvoicePrint oldInvoicePrint);
}
