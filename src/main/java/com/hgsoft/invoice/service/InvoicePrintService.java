package com.hgsoft.invoice.service;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.invoice.dao.InvoiceDetailDao;
import com.hgsoft.invoice.dao.InvoicePrintDao;
import com.hgsoft.invoice.dao.PassRecordDao;
import com.hgsoft.invoice.entity.InvoicePrint;
import com.hgsoft.invoice.entity.PassInvoice;
import com.hgsoft.invoice.entity.PassRecord;
import com.hgsoft.invoice.serviceInterface.IInvoicePrintService;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.unifiedInterface.service.InvoicePrintUnifiedInterfaceService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InvoicePrintService implements IInvoicePrintService{

	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private PassRecordDao passRecordDao;
	@Resource
	private InvoicePrintUnifiedInterfaceService invoicePrintUnifiedInterfaceService;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	@Resource
	private InvoicePrintDao invoicePrintDao;
	@Resource
	private InvoiceDetailDao  invoiceDetailDao;
	
	@Override
	public Pager findPassListByPager(Pager pager, PassRecord passRecord,
			Customer customer, String startTime, String endTime) {
		return passRecordDao.findPassListByPager(pager,passRecord,customer,startTime,endTime);
	}

	@Override
	public void savePrint(List<Map<String,Object>> list , PassInvoice passInvoice,
			InvoicePrint invoicePrint,String type) {
		invoicePrintUnifiedInterfaceService.savePrint(list,passInvoice,invoicePrint,type);
	}

	@Override
	public Pager findPassPrintAgainListByPager(Pager pager,
			String cardNo, Customer customer, Date startTime,
			Date endTime,String type) {
		return passRecordDao.findPassPrintAgainListByPager(pager, cardNo, customer, startTime, endTime, type);
	}

	@Override
	public InvoicePrint findInvoicePrint(Long invoicePrintId) {
		return invoicePrintDao.findById(invoicePrintId);
	}

	@Override
	public void savePrintAgain(PassInvoice passInvoice,
			InvoicePrint invoicePrint, InvoicePrint oldInvoicePrint) {
		invoicePrintUnifiedInterfaceService.savePrintAgain(passInvoice,invoicePrint,oldInvoicePrint);
	}
	
	public List<Map<String,Object>>  findReckOnListNoByPassInvoicId(Long id){
		return invoiceDetailDao.findReckOnListNoByPassInvoicId(id);
	}
	
	@Override
	public void saveRechargeInvoice(PrepaidCBussiness prepaidCBussiness,InvoicePrint invoicePrint,InvoicePrint oldInvoicePrint) {
		prepaidCBussinessDao.update(prepaidCBussiness);
		//发票打印记录
		BigDecimal invoicePrint_NO = sequenceUtil.getSequence("SEQ_CSMSinvoiceprint_NO");
		invoicePrint.setId(Long.valueOf(invoicePrint_NO.toString()));
		invoicePrintDao.save(invoicePrint);
		if(oldInvoicePrint != null) {
			invoicePrintDao.update(oldInvoicePrint);
		}
		
	}
	
	@Override
	public InvoicePrint findByBussinessID(Long bussinessID) {
		return invoicePrintDao.findByBussinessID(bussinessID);
	}
	
}
