package com.hgsoft.associateAcount.serviceInterface;

import java.util.Date;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface IReqInfoService {

	public String saveReqInfo(ReqInfo reqInfo, AccountCInfo accountCInfo,
			AccountCApply accountCApply);

	public Pager findByPager(Pager pager, Date startTime, Date endTime,
			ReqInfo reqInfo, Customer customer);
	public ReqInfo find(ReqInfo reqInfo);
	public void delete(ReqInfo reqInfo,AccountCInfo accountCInfo,AccountCApply accountCApply);

}
