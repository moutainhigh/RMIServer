package com.hgsoft.clearInterface.serviceInterface;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.customer.entity.Customer;

public interface IAccCardUserInfoService {

	public void saveAccCardUserInfo(AccountCApply accountCApply,Customer customer,String remark,Integer systemType);
}
