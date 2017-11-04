package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

import java.util.Map;

public interface IAccountCFirstApplyService {
	public void saveAccountCFirstApply(Customer customer,AccountCApply accountCApply, BillGet billGet);
	public Map<String, Object> saveAccountCFirstApplyReturnMap(Customer customer,AccountCApply accountCApply, BillGet billGet,Map<String,Object> params);
	public boolean sureIsApplied(AccountCApply accountCApply);
	public Pager findAccountCFirstApplyList(Pager pager, Customer customer, String bankAccount);
	public AccountCApply findAccountCApplyById(Long accountCApplyId);
	public BillGet findBillGetByAccountCApply(AccountCApply accountCApply);
	public void updateAccountCFirstApply(Customer customer,AccountCApply accountCApply, BillGet billGet,Map<String,Object> params);
	public AccountCApply findAccountCApply(AccountCApply accountCApply);
	public void updateShutDownStatus(AccountCApply accountCApply);
	public void update(AccountCApply accountCApply);

}
