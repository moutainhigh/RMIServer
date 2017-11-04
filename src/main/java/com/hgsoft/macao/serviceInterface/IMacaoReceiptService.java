package com.hgsoft.macao.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.utils.Pager;

public interface IMacaoReceiptService {
	public List<Receipt> findAll(Receipt receipt,Date starTime,Date endTime);
	public Receipt findByCustomerId(Long id);
	public Receipt find(Receipt receipt);
	public List<Map<String,Object>> findReceiptDetail(List<Receipt> receipts,Customer sessionCustomer,AccountCApply sessionAccountCApply);
	public int[] batchUpdateReceipt(final List<Long> receiptIds);
	public Pager findPrintAgainAllByPager(Pager pager,Date starTime, Date endTime,Long id);
}
