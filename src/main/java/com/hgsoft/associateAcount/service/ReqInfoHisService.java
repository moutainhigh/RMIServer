package com.hgsoft.associateAcount.service;

import com.hgsoft.associateAcount.dao.ReqInfoHisDao;
import com.hgsoft.associateAcount.serviceInterface.IReqInfoHisService;
import com.hgsoft.exception.ApplicationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class ReqInfoHisService implements IReqInfoHisService{
	
	private static Logger logger = Logger.getLogger(ReqInfoHisService.class.getName());

	@Resource
	private ReqInfoHisDao reqInfoHisDao;
	
	@Override
	public BigDecimal findCountByCardNo(String cardNo){
		try{
			return reqInfoHisDao.findCountByCardNo(cardNo);
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"卡号："+cardNo+"查询失败");
			throw new ApplicationException();
		}
	}
}
