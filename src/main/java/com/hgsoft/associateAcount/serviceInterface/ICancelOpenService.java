package com.hgsoft.associateAcount.serviceInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface ICancelOpenService {
	public Map<String, Object> save(ReqInfo reqInfo, ServiceApp serviceApp,
			String cardType);

	public Pager list(Pager pager, Date starTime, Date endTime,
			LianCardInfo lianCardInfo,Customer customer);

	public List list(Date starTime, Date endTime, LianCardInfo lianCardInfo,Customer customer);
}
