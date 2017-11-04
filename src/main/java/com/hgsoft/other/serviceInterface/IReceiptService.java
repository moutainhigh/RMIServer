package com.hgsoft.other.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.utils.Pager;

public interface IReceiptService {
	public List<Receipt> findAll(Receipt receipt,Date starTime,Date endTime);
	public Receipt findByCustomerId(Long id);
	public Receipt find(Receipt receipt);
	public List<Map<String,Object>> findReceiptDetail(List<Receipt> receipts);
	public int[] batchUpdateReceipt(final List<Long> receiptIds);
	
	public Pager findPrintAgainAllByPager(Pager pager, Date starTime, Date endTime);
	
	/**
	 * 2017-10-10
	 * 根据条件，查找回执打印信息
	 * @param pager
	 * @param params
	 * @return Pager
	 */
	public Pager findPrintReceiptsByPager(Pager pager,Map<String,String> params);

	/**
	 * 根据id找Receipt
	 * @param id
	 * @return
	 */
	public Receipt findById_(Long id);
	
	public Pager findReceiptsForACMS(Pager pager, Customer customer, String cardNo, Date startTime, Date endTime);
}
