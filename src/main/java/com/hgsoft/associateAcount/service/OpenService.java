package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.dao.ReqInfoDao;
import com.hgsoft.associateAcount.dao.ReqInfoHisDao;
import com.hgsoft.associateAcount.dao.UserInfoDao;
import com.hgsoft.associateAcount.entity.*;
import com.hgsoft.associateAcount.serviceInterface.IOpenService;
import com.hgsoft.associateReport.dao.ServiceAppDao;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.common.Enum.SuitEnum;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OpenService implements IOpenService {
	private static Logger logger = Logger.getLogger(OpenService.class.getName());
	@Resource
	private ReqInfoDao reqInfoDao;
	@Resource
	private ReqInfoHisDao reqInfoHisDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private UserInfoDao userInfoDao;
	@Resource 
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccardBussinessDao accardBussinessDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource 
	private ServiceAppDao serviceAppDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao; 
	@Resource
	private IAccountCService accountCService; 
	
	@Override
	public Map<String,Object> save(ReqInfo reqInfo,VehicleInfo vehicleInfo,ServiceApp serviceApp,String cardType){
		
		try{
		reqInfo=reqInfoDao.find(reqInfo);
		
		UserInfo userInfo=new UserInfo();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSUSERINFO_NO");
		userInfo.setId(seq);
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		userInfo.setUserNo("22"+format.format(new Date()));
		userInfo.setAccode(reqInfo.getAccode());
		userInfo.setCustomerId(reqInfo.getCustomerId());
		userInfo.setMemo("联营卡开通");
		userInfo.setReqtime(new Date());
		userInfo.setOperId(serviceApp.getOperId());
		userInfo.setPlaceId(serviceApp.getPlaceId());
		userInfoDao.save(userInfo);
		
		ReqInfoHis reqInfoHis = new ReqInfoHis();
		reqInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSREQINFOHIS_NO"));
		reqInfoHis.setMemo("联营卡开通");
		reqInfoHis.setCreateReason("6");
		reqInfoHisDao.save(reqInfo, reqInfoHis);
		reqInfo.setUseNo(userInfo.getUserNo());
		reqInfo.setSetTime(new Date());
		reqInfo.setHisseqId(reqInfoHis.getId());
		reqInfoDao.update(reqInfo);
		
	
		AccountCInfo accountCInfo=accountCInfoDao.findByCardNo(reqInfo.getAccode());
		AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
		accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
		accountCInfoHis.setGenReason("10");
		accountCInfoHisDao.saveForBranches(accountCInfo,accountCInfoHis);
		accountCInfo.setState("13");
		accountCInfo.setHisSeqId(accountCInfoHis.getId());
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, 20);
		Date endDate = cal.getTime();
		accountCInfo.setStartDate(new Date());
		accountCInfo.setEndDate(endDate);
		
		accountCInfo.setSuit(SuitEnum.noBindTag.getValue());
		if(!"1".equals(cardType)){
			CarObuCardInfo carObuCardInfo=carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			carObuCardInfo.setAccountCID(accountCInfo.getId());
			if(carObuCardInfo.getTagID()!=null){
				accountCInfo.setSuit(SuitEnum.bindTag.getValue());
			}
			carObuCardInfoDao.update(carObuCardInfo);
		}
		accountCInfoDao.update(accountCInfo);
		
		seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
		AccardBussiness accardBussiness = new AccardBussiness();
		accardBussiness.setId(seq);
		accardBussiness.setCustomerId(reqInfo.getCustomerId());
		accardBussiness.setCardNo(reqInfo.getAccode());
		accardBussiness.setState("3");
		accardBussiness.setRealPrice(new BigDecimal("0"));
		accardBussiness.setTradeTime(new Date());
		accardBussiness.setMemo("联营卡开通");
		accardBussiness.setLastState("13");
		accardBussiness.setOperId(serviceApp.getOperId());
		accardBussiness.setReceiptPrintTimes(0);
		accardBussiness.setPlaceId(serviceApp.getPlaceId());
		accardBussinessDao.save(accardBussiness);
		
		serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
		serviceApp.setPlaceType("7");
		serviceApp.setSerDate(new Date());
		serviceApp.setHandleType("6");
		serviceApp.setAccode(reqInfo.getAccode());
//		serviceApp.setFlowNo(reqInfo.getUseNo());
		serviceApp.setExraNo("大中华地区");
		serviceAppDao.save(serviceApp);
		
		accountCService.saveACinfo(0, accountCInfo,  SystemTypeEnum.ACMS.getValue());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("message", "true");
		map.put("startDate", accountCInfo.getStartDate());
		map.put("endDate", endDate);
		map.put("suit", accountCInfo.getSuit());
		return map;
	}catch (ApplicationException e){
		logger.error(e.getMessage()+"联营卡开通失败");
		throw new ApplicationException("联营卡开通失败");
	}
	}

	@Override
	public Pager list(Pager pager, Date starTime, Date endTime,
			LianCardInfo lianCardInfo,Customer customer) {
		return userInfoDao.list(pager, starTime, endTime, lianCardInfo,customer);
	}
	
	@Override
	public List list(Date starTime, Date endTime, LianCardInfo lianCardInfo,Customer customer) {
		try {
			return userInfoDao.list(lianCardInfo,starTime, endTime,customer);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡查询失败");
			throw new ApplicationException("联营卡查询失败");
		}
	}

	
}
