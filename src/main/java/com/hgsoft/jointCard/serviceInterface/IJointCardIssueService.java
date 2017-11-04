package com.hgsoft.jointCard.serviceInterface;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.entity.JointCardIssueQuery;
import com.hgsoft.utils.Pager;

public interface IJointCardIssueService {

	public Pager findJointCardIssueInfo(Pager pager, Customer customer, JointCardIssueQuery jointCardIssueQuery);
	public AccountCInfo saveAccountCInfo(Customer customer, AccountCInfo accountCInfo, VehicleInfo vehicleInfo, CardHolder cardHolder, CarObuCardInfo carObuCardInfo);
	
}