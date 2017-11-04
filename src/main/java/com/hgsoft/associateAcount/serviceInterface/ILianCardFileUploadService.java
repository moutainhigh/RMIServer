package com.hgsoft.associateAcount.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.associateAcount.entity.FileImport;
import com.hgsoft.associateReport.entity.ImportInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface ILianCardFileUploadService {

	public Map<String, Object> saveUpload(FileImport fileImport,
			List<String[]> list, AccountCApply accountCApply,
			SubAccountInfo subAccountInfo);

	public Pager findByPage(Pager pager, ImportInfo importInfo,
			Customer customer, Date startTime, Date endTime);

}
