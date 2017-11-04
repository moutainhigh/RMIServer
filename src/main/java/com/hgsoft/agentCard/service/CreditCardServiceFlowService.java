package com.hgsoft.agentCard.service;

import com.hgsoft.agentCard.dao.CardBusinessInfoDao;
import com.hgsoft.agentCard.serviceInterface.ICreditCardServiceFlowService;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @FileName CreditCardServiceFlowService.java
 * @Description: TODO
 * @author gsf
 * @Date 2016年6月29日 下午6:00:04 
*/
@Service
public class CreditCardServiceFlowService implements ICreditCardServiceFlowService {
	@Resource
	private CardBusinessInfoDao cardBusinessInfoDao;
	
	@Override
	public Pager findCreditCardServiceFlowList(Pager pager, Customer customer, String createStartTime, String createEndTime,
			String businessStartTime, String businessEndTime, String cardType, String cardNo, String businessType,ServiceFlowRecord serviceFlowRecord) {
		return cardBusinessInfoDao.findCreditServiceFlowlist(pager, customer, createStartTime, createEndTime, businessStartTime, businessEndTime, cardType, cardNo, businessType,serviceFlowRecord);
	}

	@Override
	public Map<String, Object> findServiceFlowDetail(Long id) {
		return cardBusinessInfoDao.findServiceFlowDetailId(id);
	}
	
	

}
