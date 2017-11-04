package com.hgsoft.accountC.serviceInterface;

import java.util.Date;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountNCApply;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface IAccountNCApplyService {

	public Pager findByPage(Pager pager, Customer customer, AccountCApply accountCApply,Date startTime,Date endTime);

	public Map<String, Object> save(AccountNCApply accountNCApply, Long custId, Map<String,Object> params);

	public void delete(Long id);

	public AccountNCApply findById(Long id);
	
	public void update(AccountNCApply accountNCApply);

}
