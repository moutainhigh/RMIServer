package com.hgsoft.accountC.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.Migrate;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface IMigrateService {

	public void saveMigrate(Migrate migrate, List<String> rightsCode,Long custId,String materialIds);
	public Map<String, Object> saveMigrateReturnMap(Migrate migrate, List<String> rightsCode, Long custId, AccountCApply oldAccountCApply,AccountCApply newAccountCApply,Map<String,Object> params);
	
	public Pager findByPager(Pager pager, Date starTime, Date endTime,
			Migrate migrate, Customer customer, String newSubaccountno,
			String oldSubaccountno);

}
