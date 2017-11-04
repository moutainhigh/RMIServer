package com.hgsoft.agentCard.serviceInterface;

import com.hgsoft.utils.Pager;

/**
 * @FileName ICreditCardService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年3月22日 下午3:06:49 
*/
public interface ICreditCardCancelService {

	Pager findCreditCardCancelList(Pager pager,String code,String endcode, Long customerId, String startTime, String endTime,String flag,String source);

}
