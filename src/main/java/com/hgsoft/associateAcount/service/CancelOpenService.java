package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.dao.ReqInfoDao;
import com.hgsoft.associateAcount.dao.ReqInfoHisDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.associateAcount.entity.ReqInfoHis;
import com.hgsoft.associateAcount.serviceInterface.ICancelOpenService;
import com.hgsoft.associateReport.dao.ServiceAppDao;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CancelOpenService implements ICancelOpenService  {
	private static Logger logger = Logger.getLogger(CancelOpenService.class.getName());
	@Resource
	private ReqInfoDao reqInfoDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private ReqInfoHisDao reqInfoHisDao;
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
	public Map<String,Object> save(ReqInfo reqInfo, ServiceApp serviceApp,String cardType){
		try{
		reqInfo=reqInfoDao.find(reqInfo);
		
		ReqInfoHis reqInfoHis = new ReqInfoHis();
		reqInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSREQINFOHIS_NO"));
		reqInfoHis.setMemo("联营卡取消开通");
		reqInfoHis.setCreateReason("3");
		reqInfoHisDao.save(reqInfo, reqInfoHis);
		reqInfo.setCancelTime(new Date());
		reqInfo.setHisseqId(reqInfoHis.getId());
		reqInfoDao.update(reqInfo);
		//reqInfoDao.deleteById(reqInfo.getId());
		
		AccountCInfo accountCInfo=accountCInfoDao.findByCardNo(reqInfo.getAccode());
		AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
		accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
		accountCInfoHis.setGenReason("11");
		accountCInfoHisDao.save(accountCInfo,accountCInfoHis);
		accountCInfo.setState("16");
		accountCInfo.setHisSeqId(accountCInfoHis.getId());
		accountCInfoDao.update(accountCInfo);
		//accountCInfoDao.delete(accountCInfo.getId());
		if(!"1".equals(cardType)){
			
			carObuCardInfoDao.updateAccountID(null,accountCInfo.getId());
		}
		
		AccardBussiness accardBussiness=new AccardBussiness();
		accardBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO"));
		accardBussiness.setCustomerId(reqInfo.getCustomerId());
		accardBussiness.setCardNo(reqInfo.getAccode());
		accardBussiness.setState("4");
		accardBussiness.setRealPrice(new BigDecimal("0"));
		accardBussiness.setReceiptPrintTimes(0);
		accardBussiness.setTradeTime(new Date());
		accardBussiness.setLastState("16");
		accardBussiness.setMemo("联营卡取消开通");
		accardBussiness.setOperId(serviceApp.getOperId());
		accardBussiness.setPlaceId(serviceApp.getPlaceId());
		accardBussinessDao.save(accardBussiness);
		
		
		serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
		serviceApp.setPlaceType("7");
		serviceApp.setSerDate(new Date());
		serviceApp.setHandleType("5");
		serviceApp.setAccode(reqInfo.getAccode());
		//serviceApp.setFlowNo(reqInfo.getUseNo());
		serviceApp.setExraNo("大中华地区");
		serviceAppDao.save(serviceApp);
		return null;
	}catch (ApplicationException e){
		logger.error(e.getMessage()+"联营卡取消开通失败");
		throw new ApplicationException("联营卡取消开通失败");
	}
	}
	@Override
	public Pager list(Pager pager, Date starTime, Date endTime,
			LianCardInfo lianCardInfo,Customer customer) {
		
		return reqInfoDao.list(pager, starTime, endTime, lianCardInfo,customer);
	}
	@Override
	public List list(Date starTime, Date endTime, LianCardInfo lianCardInfo,Customer customer){
		try {
			return reqInfoDao.list(starTime, endTime,lianCardInfo,customer);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡取消开通查询失败");
			throw new ApplicationException("联营卡取消开通查询失败");
		}
	}
	
	
}
