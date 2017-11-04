package com.hgsoft.associateAcount.serviceInterface;

import com.hgsoft.associateAcount.entity.JointCardBlackListQuery;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

import java.util.List;

/**
 * @FileName IDarkListService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月9日 
*/
public interface IDarkListService {

	boolean isInBlackList(String cardNo);
	List list(Customer customer);
	Pager findBlackList(Pager pager, Customer customer, JointCardBlackListQuery jointCardBlackListQuery);
}
