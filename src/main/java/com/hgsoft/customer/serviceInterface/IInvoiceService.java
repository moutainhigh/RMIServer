package com.hgsoft.customer.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.customer.entity.InvoiceHis;
import com.hgsoft.utils.Pager;

public interface IInvoiceService {
	public Invoice find(Invoice invoice);
	public Invoice findById(Long id);
	
	public Pager findByPager(Pager pager, Invoice invoice);
	public void save(Invoice invoice);
	public void update(Invoice invoice);
	public void delete(Long id);
	public Pager findInvoiceInfoByPager(Pager pager, Invoice invoice, Customer customer);
	public Invoice findByCusAndDef(Long mainID,String isDefault);
	public List<Map<String, Object>> findByCustomer(Long id);
	public Invoice findByPrepaidCNo(String cardNo);
	public Invoice findByAccountCNo(String cardNo);
	public Pager findInvoiceInfoByPager(Pager pager, Long id);
	public Invoice findByCustomerId(Long id);
	public Invoice findDefaultById(Long id);
	public InvoiceHis saveInvoiceHis(InvoiceHis his);
	public long isDefaultCount(Long mainId);
	public List<Map<String, Object>> findAllInvoiceOrderByDefault(Long mainid);
}
