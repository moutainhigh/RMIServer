package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.dao.ReqInfoDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.associateAcount.serviceInterface.ILogOutService;
import com.hgsoft.associateReport.dao.ServiceAppDao;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.clearInterface.entity.TollCardBlackDet;
import com.hgsoft.clearInterface.entity.TollCardBlackDetSend;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service
public class LogOutService implements ILogOutService {
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource 
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccardBussinessDao accardBussinessDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource 
	private ServiceAppDao serviceAppDao;
	@Resource
	private ReqInfoDao reqInfoDao;
	@Resource 
	private DarkListDao darkListDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private IBlackListService blackListService;
	
	private static Logger logger = Logger.getLogger(LogOutService.class.getName());
	
	public Map<String,Object> saveLogOut(ReqInfo reqInfo, ServiceApp serviceApp,String cardType){
		try {
			reqInfo=reqInfoDao.find(reqInfo);
			AccountCInfo accountCInfo=accountCInfoDao.findByCardNo(reqInfo.getAccode());
			AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("3");//注销
			accountCInfoHisDao.saveForBranches(accountCInfo,accountCInfoHis);
			accountCInfo.setState("10");//有卡注销
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);
			
			//如果是国标卡，要解绑，如果是地标卡，不用解绑
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			if(carObuCardInfo!=null){
				carObuCardInfo.setAccountCID(null);
				carObuCardInfoDao.update(carObuCardInfo);
			}
			
			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(reqInfo.getCustomerId());
			accardBussiness.setCardNo(reqInfo.getAccode());
			accardBussiness.setState("7");//有卡终止使用
			accardBussiness.setRealPrice(new BigDecimal("0"));//业务费用
			accardBussiness.setTradeTime(new Date());//操作时间
			accardBussiness.setLastState("10");//卡片最后状态
			accardBussiness.setMemo("有卡终止使用");
			accardBussiness.setOperId(serviceApp.getOperId());
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setPlaceId(serviceApp.getPlaceId());
			accardBussinessDao.save(accardBussiness);
			
			serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
			serviceApp.setPlaceType("7");//网点类型	快易通
			serviceApp.setSerDate(new Date());
			serviceApp.setHandleType("7");//客服项目	有卡注销
			serviceApp.setAccode(reqInfo.getAccode());
			//serviceApp.setFlowNo(reqInfo.getUseNo());
			serviceAppDao.save(serviceApp);
			
			accountCService.saveACinfo(22, accountCInfo, SystemTypeEnum.ACMS.getValue());
			return null;
		} catch (ApplicationException e){
			logger.error(e.getMessage()+"联营卡有卡注销失败");
			throw new ApplicationException("联营卡有卡注销失败");
		}
	}
	public Map<String,Object> saveBlackList(ReqInfo reqInfo, ServiceApp serviceApp,String cardType){
		try {
			reqInfo=reqInfoDao.find(reqInfo);
			AccountCInfo accountCInfo=accountCInfoDao.findByCardNo(reqInfo.getAccode());
			AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
			accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
			accountCInfoHis.setGenReason("3");
			accountCInfoHisDao.saveForBranches(accountCInfo,accountCInfoHis);
			accountCInfo.setState("11");
			accountCInfo.setHisSeqId(accountCInfoHis.getId());
			accountCInfoDao.update(accountCInfo);
			
			//如果是国标卡，要解绑，如果是地标卡，不用解绑
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
			if(carObuCardInfo!=null){
				carObuCardInfo.setAccountCID(null);
				carObuCardInfoDao.update(carObuCardInfo);
			}
//----------------------------------------------			
			/*DarkList darkList=new DarkList();
			darkList.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
			darkList.setCustomerId(reqInfo.getCustomerId());
			darkList.setCardNo(reqInfo.getAccode());
			darkList.setCardType("1");
			darkList.setGenDate(new Date());
			darkList.setGencau("2");//产生原因		无卡注销进入
			darkList.setGenmode("0");//产生方式	系统产生
			darkList.setOperId(serviceApp.getOperId());
			darkList.setPlaceId(serviceApp.getPlaceId());
			darkList.setRemark("无卡注销");
			darkListDao.save(darkList);*/
			
			long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
			AccardBussiness accardBussiness = new AccardBussiness();
			accardBussiness.setId(seq);
			accardBussiness.setCustomerId(reqInfo.getCustomerId());
			accardBussiness.setCardNo(reqInfo.getAccode());
			accardBussiness.setState("8");//无卡终止使用
			accardBussiness.setRealPrice(new BigDecimal("0"));
			accardBussiness.setTradeTime(new Date());
			accardBussiness.setLastState("11");
			accardBussiness.setMemo("无卡终止使用");
			accardBussiness.setReceiptPrintTimes(0);
			accardBussiness.setOperId(serviceApp.getOperId());
			accardBussiness.setPlaceId(serviceApp.getPlaceId());
			accardBussinessDao.save(accardBussiness);
			
			serviceApp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO"));
			serviceApp.setPlaceType("7");
			serviceApp.setSerDate(new Date());
			serviceApp.setHandleType("3");//客服项目	黑名单
			serviceApp.setAccode(reqInfo.getAccode());
			//serviceApp.setFlowNo(reqInfo.getUseNo());
			serviceAppDao.save(serviceApp);
			
			TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", null,10, new Date(),0, new Date());
			TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", null,10, new Date(),0, new Date());
			accountCService.saveTollCardBlack(accountCInfo, tollCardBlackDet, tollCardBlackDetSend);
			accountCService.saveACinfo(23, accountCInfo, SystemTypeEnum.ACMS.getValue());
			
			//
			//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
			//
			//accountCService.saveDarkList(accountCInfo,darkList,"10", "1");
			blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
					, "2", accardBussiness.getOperId(), null, null,
					accardBussiness.getPlaceId(), null, null, 
					new Date());
			return null;
		} catch (ApplicationException e){
			logger.error(e.getMessage()+"联营卡无卡注销失败");
			throw new ApplicationException("联营卡无卡注销失败");
		}
	}
}

