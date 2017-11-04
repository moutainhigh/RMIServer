package com.hgsoft.agentCard.service;

import com.hgsoft.agentCard.serviceInterface.ICreditCardCancelService;
import com.hgsoft.prepaidC.dao.CancelDao;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @FileName CreditCardCancelService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年3月22日 下午3:21:25 
*/
@Service
public class CreditCardCancelService implements ICreditCardCancelService {
	
	@Resource
	private CancelDao cancelDao;

	@Override
	public Pager findCreditCardCancelList(Pager pager,String code,String endcode, Long customerId, String startTime, String endTime,String flag,String source) {
		return cancelDao.findCreditCardCancelListByPage(pager,code,endcode, customerId,startTime,endTime,flag,source);
	}

}
