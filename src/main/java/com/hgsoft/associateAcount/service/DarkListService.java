package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.service.AccountCInfoService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.associateAcount.entity.JointCardBlackListQuery;
import com.hgsoft.associateAcount.serviceInterface.IDarkListService;
import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.entity.BlackListStatus;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @FileName DarkListService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月9日 上午10:45:50 
*/
@Service
public class DarkListService implements IDarkListService {
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private BlackListDao blackListDao;
	
	private static Logger logger = Logger.getLogger(AccountCInfoService.class.getName());

	@Override
	public boolean isInBlackList(String cardNo) {
		return blackListDao.isInBlackList(cardNo);
	}

	@Override
	public Pager findBlackList(Pager pager, Customer customer, JointCardBlackListQuery jointCardBlackListQuery) {
		if (jointCardBlackListQuery == null) {
			return null;
		} // if
		if (jointCardBlackListQuery.getEndTime() != null) {
			Date newEndTime = new Date(jointCardBlackListQuery.getEndTime().getTime() + (24*60*60-1)*1000);
			jointCardBlackListQuery.setEndTime(newEndTime);
		} // if
		return blackListDao.findBlackList(pager, customer, jointCardBlackListQuery);
	}

	@Override
	public List list(Customer customer) {
		return blackListDao.list(customer);
	}

}
