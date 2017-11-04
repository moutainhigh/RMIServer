package com.hgsoft.customer.service;

import com.hgsoft.customer.dao.InvoiceDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.customer.entity.InvoiceHis;
import com.hgsoft.customer.serviceInterface.IInvoiceService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.unifiedInterface.service.CustomerUnifiedInterfaceService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService implements IInvoiceService{
	
	@Resource
	private CustomerUnifiedInterfaceService customerUnifiedInterfaceService;
	
	private InvoiceDao invoiceDao;
	@Resource
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
	@Resource
	SequenceUtil sequenceUtil;
	
	@Override
	public Invoice find(Invoice invoice) {
		return invoiceDao.find(invoice);
	}

	@Override
	public Invoice findById(Long id) {
		return invoiceDao.findById(id);
	}

	@Override
	public Pager findByPager(Pager pager, Invoice invoice) {
		return invoiceDao.findByPage(pager, invoice);
	 
	}

	@Override
	public void save(Invoice invoice) {
		
		
		customerUnifiedInterfaceService.saveInvoice(invoice);
		
	}

	@Override
	public void update(Invoice invoice) {
		customerUnifiedInterfaceService.updateInvoice(invoice);
	}

	@Override
	public void delete(Long id) {
		//为什么不记录客户记录表？
		
		//invoiceDao.delete(id);
		customerUnifiedInterfaceService.deleteInvoice(id);
	}

	@Override
	public Pager findInvoiceInfoByPager(Pager pager,Invoice invoice, Customer customer) {
		return invoiceDao.findInvoiceInfoByPager(pager, invoice,customer);
	}
	
	@Override
	public Invoice findByCusAndDef(Long mainID,String isDefault) {
		return invoiceDao.findByCusAndDef(mainID, isDefault);
	}

	@Override
	public List<Map<String, Object>> findByCustomer(Long id) {
		return invoiceDao.findByCustomer(id);
	}

	@Override
	public Invoice findByPrepaidCNo(String cardNo) {
		return invoiceDao.findByPrepaidCNo(cardNo);
	}

	@Override
	public Pager findInvoiceInfoByPager(Pager pager, Long id) {
		return invoiceDao.findInvoiceInfoByPager(pager, id);
	}

	@Override
	public Invoice findByAccountCNo(String cardNo) {
		return invoiceDao.findByAccountCNo(cardNo);
	}

	@Override
	public Invoice findByCustomerId(Long id) {
		return invoiceDao.findByCustomerId(id);
	}

	@Override
	public Invoice findDefaultById(Long id) {
		return invoiceDao.findDefaultById(id);
	}

	@Override
	public InvoiceHis saveInvoiceHis(InvoiceHis his) {
		try {
			BigDecimal SEQ_CSMS_invoice_his_NO = sequenceUtil.getSequence("SEQ_CSMS_invoice_his_NO");
			his.setId(Long.valueOf(SEQ_CSMS_invoice_his_NO.toString()));
			return invoiceDao.saveInvoiceHis(his);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("保存发票信息历史失败");
		}
		
	}
	
	/**
	 * @detail query default titile count
	 * @author gaosiling
	 * @param mainId
	 * @return
	 */
	@Override
	public long isDefaultCount(Long mainId){
		try {
			return invoiceDao.isDefaultCount(mainId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("查询默认发票数失败");
		}
	}

	@Override
	public List<Map<String, Object>> findAllInvoiceOrderByDefault(Long mainid) {
		try {
			return invoiceDao.findAllInvoiceOrderByDefault(mainid);
		}catch (ApplicationException e) {
			e.printStackTrace();
			throw new ApplicationException("查询客户发票失败");
		}
	}
}
