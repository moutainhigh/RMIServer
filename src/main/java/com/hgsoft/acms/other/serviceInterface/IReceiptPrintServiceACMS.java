package com.hgsoft.acms.other.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.other.entity.ReceiptPrintRecord;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.utils.Pager;

public interface IReceiptPrintServiceACMS {

	Pager findAllByCustomerId(Pager pager, Long id);

	ReceiptPrint findById(Long id);

	public void updateEndTime(Long id);

	void addReceiptPrintDetail(Long customerId, ReceiptPrint receiptPrint);

	List<Map<String, Object>> findDetailsByTypeAndTime(ReceiptPrint receiptPrint);
	public ReceiptPrint findByCustomerIdAndTime(Customer customer);
	public void addReceiptPrint(ReceiptPrint receiptPrint);
	public void updateReceiptPrint(ReceiptPrint receiptPrint);

	void updatePrintNum(ReceiptPrint receiptPrint,ReceiptPrintRecord receiptPrintRecord);

	AccountCBussiness findAccountCBusinessById(long id);

	TagBusinessRecord findTagBusinessById(long id);
	
	AccardBussiness findAccardBussinessById(Long id);
	
	public Pager findByAssociate(Pager pager,Date starTime ,Date endTime,String no,String type,Long id);
	
	public void updatePrintServiceNum(String type, Long businessId);
	public ReceiptPrint findByCustomerIdAndBeginTime(Customer customer);
	
}
