package com.hgsoft.acms.other.serviceInterface;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.utils.Pager;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReceiptServiceACMS {
	public List<Receipt> findAll(Receipt receipt,Date starTime,Date endTime);
	public Receipt findByCustomerId(Long id);
	public Receipt find(Receipt receipt);
	public List<Map<String,Object>> findReceiptDetail(List<Receipt> receipts);
	public int[] batchUpdateReceipt(final List<Long> receiptIds);
	
	public Pager findPrintAgainAllByPager(Pager pager, Date starTime, Date endTime);

//	public Receipt findById_(Long id);

	/**
	 * 2017-06-07
	 * 根据条件，查找回执打印信息
	 * @param pager
	 * @param starTime
	 * @param endTime
	 * @param customer
	 * @param bussinessType 打印的业务类型
	 * @param alreadyPrint 是否已打印
	 * @return Pager
	 */
	public Pager findPrintReceiptsByPager(Pager pager,Date starTime, Date endTime,Customer customer,String bussinessType,String alreadyPrint);
}
