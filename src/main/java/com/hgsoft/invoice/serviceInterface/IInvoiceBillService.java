package com.hgsoft.invoice.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.invoice.entity.AddBill;
import com.hgsoft.invoice.entity.InvoiceRecord;
import com.hgsoft.invoice.entity.PassBill;
import com.hgsoft.utils.Pager;

public interface IInvoiceBillService {

	public void save(PassBill pb);

	public Pager getClearACBillByCardNo(Pager pager, String cardNo,
			String startDate2, String endDate2);

	public Pager getClearACBillByBankNo(Pager pager, String bankNo,
			String startDate2, String endDate2);

	public List<Map<String, Object>> getACPassBillByBankNo(String bankNo,
			String startDate, String endDate);

	public List<Map<String, Object>> getACPassBillByCardNo(String cardNo,
			String startDate, String endDate);

	public List<PassBill> getACBillByCardno(String cardNo, String billMonth2);

	public List<Map<String, Object>> getClearACBillByBankNo(String bankNo,
			String billMonth);

	public List<PassBill> getACBillByBankno(String bankNo, String billMonth);

	public List<Map<String, Object>> getClearACBillByCardNo(String cardNo,
			String billMonth);

	public List<Map<String, Object>> getACClearDetails(String cardNo,
			String billMonth);

	public void saveBillAndInvoice(List<PassBill> passBills,
			List<InvoiceRecord> invoiceRecords, String billType);

	public String checkCardPrint(String cardNo, String printType);

	public Long findInvoiceRecrod(String invoiceNum, String invoiceCode);

	public void updateState(String invoiceNum,String invoiceCode, String string);

	public void reprint(List<PassBill> passBills,
			List<InvoiceRecord> invoiceRecords);

	public List<Map<String,Object>> getACBillDetail(String flag, String cardNo,
			String billMonth);

	public Pager getSCBillByCardNo(Pager pager, String cardNo,
			String startDate, String endDate);

	public List<Map<String, Object>> getSCPassBillByCardNo(String cardNo,
			String startDate, String endDate);

	public List<Map<String, Object>> getClearSCBillByCardNo(String cardNo,
			String billMonth);

	public List<Map<String, Object>> getSCClearDetails(String cardNo,
			String billMonth);

	public Pager findAddBillList(Pager pager,
			String cardNo, String startDate, String endDate);

	public AddBill findAddBillByID(Long id);

	public void saveAddBillAndInvoice(AddBill addBill,
			InvoiceRecord invoiceRecord);

	public List<Map<String, Object>> findAddBillDetail(Long id);

	public void addBillReprint(AddBill addBill, InvoiceRecord invoiceRecord);

	public List<Map<String, Object>> getSCBillDetail(String cardNo,
			String billMonth);


}
