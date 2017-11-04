package com.hgsoft.clearInterface.service;


import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.dao.CashMoneyDao;
import com.hgsoft.clearInterface.entity.RecoverDebtSend;
import com.hgsoft.clearInterface.serviceInterface.IChaseMoneyInterfaceService;
import com.hgsoft.system.dao.ExceptionListNoDao;
import com.hgsoft.system.entity.ExceptionListNo;


@Service
public class ChaseMoneyInterfaceService implements IChaseMoneyInterfaceService {

	@Resource
	private CashMoneyDao cashMoneyDao;
	@Resource
	private ExceptionListNoDao exceptionListNoDao;
	@Override
	public void saveChaseMoney(ExceptionListNo exceptionListNo) {
		if(exceptionListNo.getCardType()!=null&&exceptionListNo.getCardType().equals("22")) {
			RecoverDebtSend recoverDebtSend = new RecoverDebtSend();
			recoverDebtSend.setBillNo(exceptionListNo.getHandleNo());
			recoverDebtSend.setScCode(exceptionListNo.getCardNo());
			recoverDebtSend.setTollType(exceptionListNo.getTransitListNoType());
			if (StringUtils.isNotBlank(exceptionListNo.getOffTradeNo())){
				recoverDebtSend.setTrseq(Integer.parseInt(exceptionListNo.getOffTradeNo()));
			}
			recoverDebtSend.setTrseqhex(exceptionListNo.getHexOffNo());
			recoverDebtSend.setFlag("1");//1表示追款，2表示退款
			recoverDebtSend.setRealFee(exceptionListNo.getProceeds());
			recoverDebtSend.setExportNo(exceptionListNo.getOutTradeNo());
			if (exceptionListNo.getInSiteInfo() != null){
				recoverDebtSend.setEntryMsg(String.valueOf(exceptionListNo.getInSiteInfo()));
			}
			recoverDebtSend.setEntryTime(exceptionListNo.getIntoTime());
			if (exceptionListNo.getOutSiteInfo() != null){
				recoverDebtSend.setExportMsg(String.valueOf(exceptionListNo.getOutSiteInfo()));
			}
			recoverDebtSend.setExportTime(exceptionListNo.getOutTime());
			recoverDebtSend.setExportType(exceptionListNo.getOutLaneType());
			recoverDebtSend.setTollFee(exceptionListNo.getReceivable());
			recoverDebtSend.setBalance(exceptionListNo.getTransitBalance());
			recoverDebtSend.setRegTime(exceptionListNo.getRegisterTime());
			recoverDebtSend.setRegOpno(exceptionListNo.getRegisterName());
			recoverDebtSend.setCompleteFlag(exceptionListNo.getFinishFlag());
			recoverDebtSend.setCompleteTime(exceptionListNo.getFinishTime());
			if (exceptionListNo.getCustomPoint() != null){
				recoverDebtSend.setComplaceNo(String.valueOf(exceptionListNo.getCustomPoint()));
			}
			recoverDebtSend.setDealOpno(null);
			recoverDebtSend.setServiceNo(exceptionListNo.getRefundListNo());
			recoverDebtSend.setCheckFlag(exceptionListNo.getCheckFlag());
			recoverDebtSend.setCheckTime(exceptionListNo.getCheckTime());
			if (exceptionListNo.getCheckID() != null){
				recoverDebtSend.setCheckOpno(String.valueOf(exceptionListNo.getCheckID()));
			}
			recoverDebtSend.setBlockFlag(exceptionListNo.getLagInterceptFlag());
			recoverDebtSend.setSysbalFlag(exceptionListNo.getSysBalanceFlag());
			Map<String, Object> exceptionType = exceptionListNoDao.findExceptionType(exceptionListNo.getExceptionType());
			if(exceptionType!=null) {
				recoverDebtSend.setExceptFlag(String.valueOf(Integer.parseInt(exceptionType.get("CODE").toString())));
			}
			
			recoverDebtSend.setBalanceTime(exceptionListNo.getClearingTime());
			recoverDebtSend.setBalanceWaterNo(exceptionListNo.getClearingListNo());
			cashMoneyDao.saveCashMoney(recoverDebtSend);
			
		}
		
		
		
	}

}
