package com.hgsoft.agentCard.serviceInterface;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

/**
 * @FileName ICreditCardService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年3月22日 下午3:06:49 
*/
public interface ICreditCardBlacklistService {

	Pager findCreditCardBlacklist(Pager pager,String beginCode,String endCode, Customer customer, String startTime, String endTime);

}
