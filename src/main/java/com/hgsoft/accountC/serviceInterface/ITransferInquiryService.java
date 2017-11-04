package com.hgsoft.accountC.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.TransferApply;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

/**
 * @FileName IBailService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月19日 上午9:25:41 
*/
public interface ITransferInquiryService {

	Pager findByPager(Pager pager, Customer customer, String cardNo, String bankAccount, String startTime,
			String endTime);
	
	public boolean saveTransfer(TransferApply transferApply,
			AccountCBussiness accountCBussiness, String cardNoRights, Long newCustomerId);
	
	TransferApply getById(Long id);
	
	List<Map<String, Object>> findCardById(Long id);
	
	List<Map<String, Object>> getCustomerByBank(String oldbankaccount);
	public AccountCApply findBySubAccId(Long id);
//	public List<Map<String,Object>> getStopAcList(String bankAccount);
}
