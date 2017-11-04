package com.hgsoft.agentCard.service;

import com.hgsoft.agentCard.dao.CardBlacklistDao;
import com.hgsoft.agentCard.serviceInterface.ICreditCardBlacklistService;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @FileName CreditCardBlacklistService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年3月22日 下午6:00:04 
*/
@Service
public class CreditCardBlacklistService implements ICreditCardBlacklistService {

	@Resource
	private CardBlacklistDao cardBlacklistDao;

	@Override
	public Pager findCreditCardBlacklist(Pager pager,String beginCode,String endCode, Customer customer, String startTime, String endTime) {
		return cardBlacklistDao.findCreditCardBlacklist(pager,beginCode,endCode, customer,startTime,endTime);
	}

}
