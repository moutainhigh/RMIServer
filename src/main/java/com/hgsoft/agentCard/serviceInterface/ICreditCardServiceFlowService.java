package com.hgsoft.agentCard.serviceInterface;

import java.util.Date;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.utils.Pager;

/**
 * @FileName ICreditCardServiceFlowService.java
 * @Description: TODO
 * @author guanshaofeng
 * @Date 2016年06月29日 下午3:06:49 
*/
public interface ICreditCardServiceFlowService {

	public Pager findCreditCardServiceFlowList(Pager pager, Customer customer, 
			String createStartTime, String createEndTime,String businessStartTime,String businessEndTime,
			String cardType,String cardNo,String businessType,ServiceFlowRecord serviceFlowRecord);

	public Map<String,Object> findServiceFlowDetail(Long id);
}
