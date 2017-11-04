package com.hgsoft.prepaidC.service;

import com.hgsoft.prepaidC.dao.ReturnFeeDao;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.prepaidC.serviceInterface.IReturnFeeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ReturnFeeService implements IReturnFeeService {

	private static Logger logger = Logger.getLogger(ReturnFeeService.class);

	@Resource
	private ReturnFeeDao returnFeeDao;
	
	@Resource
	public void setReturnFeeDao(ReturnFeeDao returnFeeDao) {
		this.returnFeeDao = returnFeeDao;
	}
	
	@Override
	public List<ReturnFee> findByCardNoState(String cardNo,String state){
		return returnFeeDao.findByCardNoState(cardNo, state);
	}
	
	@Override
	public List<ReturnFee> findByIDStr(String idStr){
		return returnFeeDao.findByIDStr(idStr);
	}
	
	@Override
	public List<ReturnFee> findByBussinessID(Long bussinessID) {
		return returnFeeDao.findByBussinessID(bussinessID);
	}

	/*@Override
	public List<String> getReturnMoney(List<String> list, SysAdmin sysAdmin,
			CusPointPoJo cusPointPoJo) {
		List<String> retlist = new ArrayList<String>();
		String cardNo = list.get(1);
		//String cardType = list.get(2);
		try {
			if(StringUtil.isEmpty(cardNo)){
				logger.warn("没有传入卡号！");
				retlist.add("1");
				retlist.add("");
				return retlist;
			}
			//判断卡号是否为储值卡，是否为16位等
			boolean canCheck = canRechargeRequest(cardNo);
			if(!canCheck){
				retlist.add("1");
				retlist.add("");
				return retlist;
			}
			BigDecimal returnFee = returnFeeDao.findSumReturnByCardNo(cardNo)
					.setScale(2, BigDecimal.ROUND_DOWN);
			retlist.add("0");
			retlist.add(returnFee.toString());
			return retlist;
		} catch (ApplicationException e) {
			String msg = "查询回退金额失败: "+list;
			logger.error(msg, e);
			throw new ApplicationException(msg);
		}
	}*/

    @Override
    public BigDecimal findSumReturnByCardNo(String cardNo) {
        return returnFeeDao.findSumReturnByCardNo(cardNo);
    }

	@Override
	public int save(ReturnFee returnFee) {
		return returnFeeDao.save(returnFee);
	}


}
