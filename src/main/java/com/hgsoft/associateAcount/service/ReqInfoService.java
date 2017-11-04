package com.hgsoft.associateAcount.service;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.associateAcount.dao.AccardBussinessDao;
import com.hgsoft.associateAcount.dao.ReqInfoDao;
import com.hgsoft.associateAcount.dao.ReqInfoHisDao;
import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.associateAcount.entity.ReqInfoHis;
import com.hgsoft.associateAcount.serviceInterface.IReqInfoService;
import com.hgsoft.associateReport.dao.ServiceAppDao;
import com.hgsoft.associateReport.entity.ServiceApp;
import com.hgsoft.common.Enum.SystemTypeEnum;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.httpInterface.serviceInterface.IInventoryServiceForAgent;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 联营卡开通申请
 * @author gaosiling
 * 2016-04-29 09:32:14
 */
@Service
public class ReqInfoService implements IReqInfoService {

	private static Logger logger = Logger.getLogger(ReqInfoService.class
			.getName());

	@Resource
	private ReqInfoDao reqInfoDao;

	@Resource
	private ReqInfoHisDao reqInfoHisDao;

	@Resource
	private AccardBussinessDao accardBussinessDao;

	@Resource
	private AccountCInfoDao accountCInfoDao;

	@Resource
	private ServiceAppDao serviceAppDao;

	@Resource
	private SequenceUtil sequenceUtil;
	
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private IInventoryServiceForAgent inventoryServiceForAgent;

	/**
	 * 联营卡开通申请保存
	 * @author gaosiling
	 */
	@Override
	public String saveReqInfo(ReqInfo reqInfo, AccountCInfo accountCInfo,
			AccountCApply accountCApply) {
		InterfaceRecord interfaceRecord = null;
		Map<String, Object> map = inventoryServiceForAgent.omsInterface(accountCInfo.getCardNo(), "1", interfaceRecord,"issue",
				null,null,"",
				"1","",null,accountCInfo.getRealCost(),"31");// 31代表记帐卡发行
		boolean result = (Boolean) map.get("result");
		if (!result) {
			return map.get("message").toString();
		}
		//设置有效起始时间与结束时间
		Map<String,Object> m = (Map<String,Object>)map.get("initializedOrNotMap");
		if(m!=null){
			accountCInfo.setStartDate((Date)m.get("startDate"));
			accountCInfo.setEndDate((Date)m.get("endDate"));
		}
		

		AccardBussiness accardBussiness = new AccardBussiness();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
		accardBussiness.setId(seq);
		accardBussiness.setCustomerId(reqInfo.getCustomerId());
		accardBussiness.setCardNo(reqInfo.getAccode());
		accardBussiness.setState("1");
		accardBussiness.setLastState(accountCInfo.getState());
		accardBussiness.setRealPrice(new BigDecimal("0"));
		accardBussiness.setTradeTime(new Date());
		accardBussiness.setReceiptPrintTimes(0);
		accardBussiness.setOperId(reqInfo.getOperid());
		accardBussiness.setPlaceId(reqInfo.getPlaceId());

		ServiceApp serviceApp = new ServiceApp();
		seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
		serviceApp.setId(seq);
		serviceApp.setPlaceType("7");
		serviceApp.setHandleType("4");
		serviceApp.setAccode(reqInfo.getAccode());
		serviceApp.setBaccount(accountCApply.getBankAccount());
		serviceApp.setBankName(accountCApply.getBankName());
		serviceApp.setPlaceId(reqInfo.getPlaceId());
		serviceApp.setBalance(new BigDecimal("0"));
		serviceApp.setSerDate(new Date());
		serviceApp.setOperId(reqInfo.getOperid());

		try {
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSREQINFO_NO");
			reqInfo.setId(seq);
			seq = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfo_NO");
			accountCInfo.setId(seq);
			
			reqInfoDao.save(reqInfo);
			accountCInfoDao.save(accountCInfo);
			serviceAppDao.save(serviceApp);
			accardBussinessDao.save(accardBussiness);
			
			accountCService.saveACinfo(0, accountCInfo,  SystemTypeEnum.ACMS.getValue());
			return null;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"卡号：" + reqInfo.getAccode() + "申请开通失败");
			throw new ApplicationException("卡号：" + reqInfo.getAccode() + "申请开通失败");
		}
	}

	
	/**
	 * 联营卡开通申请查询
	 * @author gaosiling
	 */
	@Override
	public Pager findByPager(Pager pager, Date startTime, Date endTime,
			ReqInfo reqInfo, Customer customer) {
		try {
			return reqInfoDao.findByPager(pager, startTime, endTime, reqInfo,
					customer);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"查询联营卡申请失败");
			throw new ApplicationException("查询联营卡申请失败");
		}
	}

	/**
	 * 联营卡开通申请单卡查询
	 * @author gaosiling
	 */
	@Override
	public ReqInfo find(ReqInfo reqInfo) {
		try {
			reqInfo = reqInfoDao.find(reqInfo);
			return reqInfo;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"联营卡开通查询失败");
			throw new ApplicationException("联营卡开通查询失败");
		}
	}

	/**
	 * 联营卡开通申请删除
	 * @author gaosiling
	 */
	@Override
	public void delete(ReqInfo reqInfo,AccountCInfo accountCInfo,AccountCApply accountCApply) {
		
		
		
		AccardBussiness accardBussiness = new AccardBussiness();
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSACCARDBUSSINESS_NO");
		accardBussiness.setId(seq);
		accardBussiness.setCustomerId(accountCInfo.getCustomerId());
		accardBussiness.setCardNo(accountCInfo.getCardNo());
		accardBussiness.setState("2");
		accardBussiness.setRealPrice(new BigDecimal("0"));
		accardBussiness.setTradeTime(new Date());
		accardBussiness.setOperId(reqInfo.getOperid());
		accardBussiness.setPlaceId(reqInfo.getPlaceId());
		accardBussiness.setReceiptPrintTimes(0);

		ServiceApp serviceApp = new ServiceApp();
		seq = sequenceUtil.getSequenceLong("SEQ_CSMSSERVICEAPP_NO");
		serviceApp.setId(seq);
		serviceApp.setPlaceType("7");
		serviceApp.setHandleType("9");
		serviceApp.setAccode(accountCInfo.getCardNo());
		serviceApp.setBaccount(accountCApply.getBankAccount());
		serviceApp.setBankName(accountCApply.getBankName());
		serviceApp.setPlaceId(reqInfo.getPlaceId());
		serviceApp.setBalance(new BigDecimal("0"));
		serviceApp.setSerDate(new Date());
		serviceApp.setOperId(reqInfo.getOperid());
		
		
		ReqInfoHis reqInfoHis = new ReqInfoHis();
		reqInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSREQINFOHIS_NO"));
		reqInfoHis.setMemo("联营卡取消开通申请");
		reqInfoHis.setCreateReason("2");
	
		AccountCInfoHis accountCInfoHis=new AccountCInfoHis();
		accountCInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
		accountCInfoHis.setGenReason("12");

		try {
			reqInfoHisDao.save(reqInfo, reqInfoHis);
			accountCInfoHisDao.saveForBranches(accountCInfo,accountCInfoHis);
			reqInfoDao.deleteById(reqInfo.getId());
			accountCInfoDao.delete(accountCInfo.getId());
			serviceAppDao.save(serviceApp);
			accardBussinessDao.save(accardBussiness);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"删除当前联营卡卡号为"+accountCInfo.getCardNo()+"，取消开通申请失败");
			throw new ApplicationException("删除当前联营卡卡号为"+accountCInfo.getCardNo()+"，取消开通申请失败");
		}

	}

}

