package com.hgsoft.ivr.service;

import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IRelieveStopPayService;
import com.hgsoft.clearInterface.dao.StopPayRelieveApplyDao;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.entity.RelieveStopPay;
import com.hgsoft.ivr.dao.IVRReportCsmsDao;
import com.hgsoft.ivr.dao.IVRReportDao;
import com.hgsoft.ivr.serviceInterface.IIVRReportService;
import com.hgsoft.settlement.dao.AcTradeDetailInfoDao;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.StringUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class IVRReportService implements IIVRReportService {
	@Resource
	private IVRReportDao ivrReportDao;
	@Resource
	private IVRReportCsmsDao iVRReportCsmsDao;
	@Resource
	private StopPayRelieveApplyDao stopPayRelieveApplyDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private AcTradeDetailInfoDao acTradeDetailInfoDao;
	@Resource
	private IRelieveStopPayService relieveStopPayService;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private AccountCApplyDao accountCApplyDao;

	@Override
	public List findAllCustomPointType() {
		return ivrReportDao.findAllCustomPointType();
	}

	@Override
	public List findAllCustomPointArea() {
		return ivrReportDao.findAllCustomPointArea();
	}

	@Override
	public List findAllOperator() {
		return ivrReportDao.findAllOperator();
	}

	@Override
	public List findRoadDiscountList() {
		return ivrReportDao.findRoadDiscountList();
	}

	@Override
	public List<Map<Object, String>> findCardNoList(String bankno, String userNo,
			String organ) {
		return iVRReportCsmsDao.findCardNoList(bankno,userNo,organ);
	}

	@Override
	public List findPlaceNameList(String area,String placeType) {
		return ivrReportDao.findPlaceNameList(area,placeType);
	}

	@Override
	public List<Map<String, Object>> findCustomerList(String organ,
			String idCode, String idType) {
		return ivrReportDao.findCustomerList(organ,idCode,idType);
	}

	@Override
	public Map<String, Object> getEtcFeeAndLatefee(String cardno,String bankno) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		PaymentCardBlacklistRecv paymentCardBlacklistRecv = new PaymentCardBlacklistRecv();
		paymentCardBlacklistRecv.setCardCode(cardno);
		paymentCardBlacklistRecv.setAcbAccount(bankno);
		AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
		if(accountCApply!=null){
			String startTime = null;
			Date newDate = new Date();//新建一个时间来作为基准时间
			String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
			BigDecimal etcFeeOne = new BigDecimal(0);
			BigDecimal lateFeeOne = new BigDecimal(0);
			Timestamp newStopPayTime = null;
		
			//获得最后一次扣款时间(回盘时间,通行费依据)
			String newCurrentTime=stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
			if (newCurrentTime !=null) {
				 startTime = newCurrentTime;
			}
			//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
			newStopPayTime=stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());
			SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
			
			List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFeeByOne(subAccountInfo.getId(), startTime, nowTime,cardno);
			String lateFeeRate = relieveStopPayService.getLateFeeRate("LateFeeRate");//营运接口获取滞纳金比例
			//数据只会有一条
			if (acEtcFeeList.size()!=0) {
				for(Map<String,Object> map:acEtcFeeList){
					etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总后的通行费
					lateFeeOne = relieveStopPayService.getLateFee(etcFeeOne,lateFeeRate,new Date(newStopPayTime.getTime()),newDate);//滞纳金
				}
			}
			resultmap.put("etcFee", etcFeeOne.divide(new BigDecimal(100)));
			resultmap.put("lateFee", lateFeeOne.divide(new BigDecimal(100)));
		}
		return resultmap;
	}

	@Override
	public String findBankNoBycardNo(String cardNo) {
		return ivrReportDao.findBankNoBycardNo(cardNo);
	}

	@Override
	public List<Map<String,Object>> getLateFeeInfo(String bankAccount, String startTime, String endTime) {
		
		List<Map<String,Object>>  feeList = new ArrayList<Map<String,Object>>();
		PaymentCardBlacklistRecv paymentCardBlacklistRecv = new PaymentCardBlacklistRecv();
		paymentCardBlacklistRecv.setAcbAccount(bankAccount);
		
		AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
		if(accountCApply!=null){
			Date newDate = new Date();//新建一个时间来作为基准时间
			String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
			BigDecimal etcFeeAll = new BigDecimal(0);
			BigDecimal lateFeeAll = new BigDecimal(0);
			BigDecimal etcFeeOne = new BigDecimal(0);
			BigDecimal lateFeeOne = new BigDecimal(0);
			Timestamp newStopPayTime = null;
			Date endTimeDate = null;
			Date startTimeDate = null;
			
			DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			DateFormat sdfOther = new SimpleDateFormat("yyyy-MM-dd");
			//当没有查询条件，结束时间为当前时间；当有查询条件，需要转换格式，用于下面的区间比较
			if (StringUtil.isEmpty(endTime)) {
				endTime = sdf.format(new Date());
			}else {
				try {
					endTimeDate = sdfOther.parse(endTime);
					endTime = sdf.format(endTimeDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//当没有查询条件，开始时间为当前时间往前30天；当有查询条件，需要转换格式，用于下面的区间比较
			if (StringUtil.isEmpty(startTime)) {
				Calendar now = Calendar.getInstance();
		        now.add(Calendar.DAY_OF_MONTH, -30);
		        startTime = sdf.format(now.getTime());
			}else {
				try {
					startTimeDate = sdfOther.parse(startTime);
					startTime = sdf.format(startTimeDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//先查找客服手工解除止付表，是否有记录
			List<RelieveStopPay> relieveStopPayList = stopPayRelieveApplyDao.findListRelieveStopPay(bankAccount, startTime, endTime);
			if (relieveStopPayList.size()!=0) {
				for(RelieveStopPay relieveStop:relieveStopPayList){
					Map<String, Object> resultmap = new  HashMap<String, Object>();
					resultmap.put("etcFee", relieveStop.getEtcFee());
					resultmap.put("lateFee", relieveStop.getLateFee());
					resultmap.put("stopPayTime", DateUtil.formatDate(relieveStop.getLateFeeStartTime(), "yyyy-MM-dd"));
					resultmap.put("payTime", DateUtil.formatDate(relieveStop.getApplyTime(), "yyyy-MM-dd"));
					feeList.add(resultmap);
				}
			}
			

			//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
			newStopPayTime=stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());
			//如果止付时间在搜索范围内，去查找黑名单止付表 CSMS_PAYMENTCARDBLACKLIST 是否有数据
			if (newStopPayTime.getTime()>=startTimeDate.getTime() && newStopPayTime.getTime()<=endTimeDate.getTime()) {
				
				//获得最后一次扣款时间(回盘时间,通行费依据)
				String newCurrentTime=stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
				if (newCurrentTime !=null) {
					 startTime = newCurrentTime;
				}
				SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
				
				List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, nowTime);
				String lateFeeRate = relieveStopPayService.getLateFeeRate("LateFeeRate");//营运接口获取滞纳金比例
				
				if (acEtcFeeList.size()!=0) {
					for(Map<String,Object> map:acEtcFeeList){
						etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总后的通行费
						lateFeeOne = relieveStopPayService.getLateFee(etcFeeOne,lateFeeRate,new Date(newStopPayTime.getTime()),newDate);//滞纳金
						//计算页面总算
						etcFeeAll = etcFeeAll.add(etcFeeOne);
						lateFeeAll = lateFeeAll.add(lateFeeOne);
						
					}
					Map<String, Object> resultmap = new  HashMap<String, Object>();
					resultmap.put("etcFee", etcFeeAll);
					resultmap.put("lateFee", lateFeeAll);
					resultmap.put("stopPayTime", DateUtil.formatDate(newStopPayTime, "yyyy-MM-dd"));
					resultmap.put("payTime", "");//还没办理业务
					feeList.add(resultmap);
				}else {
					Map<String, Object> resultmap = new  HashMap<String, Object>();
					resultmap.put("etcFee", "");
					resultmap.put("lateFee", "");
					resultmap.put("stopPayTime", "");
					resultmap.put("payTime", "");
					feeList.add(resultmap);
				}
			}
			
		}
		return feeList;
	}

	@Override
	public String findOrganByBankNo(String bankNo) {
	 
		return ivrReportDao.findOrganByBankNo(bankNo);
	}
}
