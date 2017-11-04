package com.hgsoft.associateAcount.serviceInterface;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;

public interface IPwdService {

	public boolean updatePwd(AccountCInfo accountCInfo);
	public boolean resetPwd(AccountCInfo accountCInfo);
	public boolean updatePwdForACMS(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness);
	public boolean resetPwdForACMS(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness);
	
}
