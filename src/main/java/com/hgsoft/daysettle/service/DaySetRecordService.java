package com.hgsoft.daysettle.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.daysettle.dao.AfterDaySetFeeDao;
import com.hgsoft.daysettle.dao.DaySetDetailDao;
import com.hgsoft.daysettle.dao.DaySetDetailHisDao;
import com.hgsoft.daysettle.dao.DaySetLogDao;
import com.hgsoft.daysettle.dao.DaySetRecordDao;
import com.hgsoft.daysettle.dao.DaySetRecordHisDao;
import com.hgsoft.daysettle.entity.AfterDaySetFee;
import com.hgsoft.daysettle.entity.DaySetDetail;
import com.hgsoft.daysettle.entity.DaySetDetailHis;
import com.hgsoft.daysettle.entity.DaySetLog;
import com.hgsoft.daysettle.entity.DaySetRecord;
import com.hgsoft.daysettle.entity.DaySetRecordHis;
import com.hgsoft.daysettle.entity.SumDaySettle;
import com.hgsoft.daysettle.serviceInterface.IDaySetRecordService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagMaintainDao;
import com.hgsoft.obu.dao.TagTakeFeeInfoDao;
import com.hgsoft.obu.dao.TagTakeInfoDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * 日结
 * @author gaosiling
 * 2016年1月27日11:37:26
 */
@Service
public class DaySetRecordService implements IDaySetRecordService{
	
	private static Logger logger = Logger.getLogger(DaySetRecordService.class.getName());
	
	@Resource
	private DaySetRecordDao daySetRecordDao;
	
	@Resource
	private DaySetDetailDao daySetDetailDao;
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	@Resource
	private AfterDaySetFeeDao afterDaySetFeeDao;
	

	@Resource
	private DaySetRecordHisDao daySetRecordHisDao;
	
	@Resource
	private DaySetDetailHisDao daySetDetailHisDao;
	
	@Resource
	private DaySetLogDao daySetLogDao;
	
	@Resource
	private RechargeInfoDao rechargeInfoDao;
	
	@Resource
	private TagTakeFeeInfoDao tagTakeFeeInfoDao;
	
	@Resource
	private TagMaintainDao tagMaintainDao;
	
	@Resource
	private TagTakeInfoDao tagTakeInfoDao;
	
	@Resource
	private TagInfoDao tagInfoDao;
	
	@Resource
	private PrepaidCDao prepaidCDao;
	
	@Resource
	private AccountCDao accountCDao;
	
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	/**
	 * 日结日期获取
	 * @param  type 1已日结 2未日结
	 * @author gaosiling
	 */
	@Override
	public List<String> findDaySettleList(Long operPlaceId, List<String> placeList) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
			String daySettle = daySetRecordDao.findDaySettleList(operPlaceId,placeList);
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			
			String startTime = format.format(start.getTime());
			Date date = null;
			String type="1";
			try {
				//是否已存在日结记录，若不存在判断最初缴款时间
				if(daySettle==null){
					daySettle = daySetRecordDao.findDaySettleList(null,null);
					if(daySettle==null){
						list.add(startTime);
						return list;
					}
				}
				//若存在日结日则按照日结日为开始日结时间
				date = format.parse(daySettle);
				type="2";
				start.setTime(date);
				startTime = format.format(start.getTime());
				String endTime = format.format(end.getTime());
				if(!endTime.equals(startTime)){
					list = DateUtil.getDates(start, end,type);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"日期格式转换失败");
			e.printStackTrace();
			throw new ApplicationException("日期格式转换失败");
		}
		return list;
	}
	
    /**
     * 保存日结记录
     * @author gaosiling
     */
	@Override
    public void saveRecord(DaySetRecord daySetRecord,List<DaySetDetail> list, List<String> placeList){
    	try {
    		
    		
    		BigDecimal bfHandleFee = daySetRecordDao.getBeforeNoRechargeHandleFee(placeList);
    		logger.info("日结日："+daySetRecord.getSettleDay()+"上一日余额："+bfHandleFee);
    		daySetRecord.setBeforeFee(bfHandleFee);
    		
    		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSDaySetRecord_NO");
    		daySetRecord.setId(Long.parseLong(seq.toString()));
    		//保存日结记录和明细
    		daySetRecordDao.save(daySetRecord);
    		daySetDetailDao.batchSaveDaySetDetail(list, daySetRecord);
    		
    		//修改日结日志结束时间和状态
    		DaySetLog daySetLog = new DaySetLog();
    		daySetLog.setOperPlaceId(daySetRecord.getOperPlaceID());
    		daySetLog.setState(2);
    		//查询上一个日结日的结束时间
    		String preSettleDay = DateUtil.getPreDay(daySetRecord.getSettleDay());
    		daySetLog.setSettleDay(preSettleDay);
    		DaySetLog beforeDaySetLog = daySetLogDao.find(daySetLog);
    		
    		daySetLog.setSettleDay(daySetRecord.getSettleDay());
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    		Date date = new Date();
    		String nowDate = sdf.format(date);
    		
    		//当前日和日结日是否为同一天，若是则直接记录当前日结时间，否则需记录当前日结时间为日结日的23:59:59
    		sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    		if(nowDate.equals(daySetRecord.getSettleDay())){
    			daySetLog.setEndTime(null);
    		}else{
    			try {
    				date = sdf.parse(daySetRecord.getSettleDay()+"235959");
    				daySetLog.setEndTime(date);
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
    		daySetLogDao.update(daySetLog);
    		
        	String startTime = null;
        	String endTime = null;
        	if(beforeDaySetLog!=null){
        		startTime = sdf.format(DateUtil.addSecond(beforeDaySetLog.getEndTime(), 1));
//        		endTime = sdf.format(beforeDaySetLog.getEndTime());
        	}else{
        		startTime = daySetRecord.getSettleDay()+"000000";
        	}
        	endTime = daySetRecord.getSettleDay()+"235959";
    		
        	//更新日结
    		rechargeInfoDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		tagTakeFeeInfoDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		tagMaintainDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		tagTakeInfoDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		tagInfoDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		prepaidCDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		accountCDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		prepaidCBussinessDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		accountCBussinessDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		tagBusinessRecordDao.updateDaySettle(daySetLog.getSettleDay(), startTime, endTime, daySetLog.getOperPlaceId(),placeList);
    		
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存日结记录失败");
			e.printStackTrace();
			throw new ApplicationException("保存日结记录失败");
		}
    }
    
    public Pager list(Pager pager,Date starTime ,Date endTime,DaySetRecord daySetRecord){
		return daySetRecordDao.list(pager, starTime, endTime,daySetRecord);
	}
    
    /**
     * 查询日结记录
     * @author gaosiling
     */
    @Override
    public DaySetRecord find(DaySetRecord DaySetRecord){
    	return daySetRecordDao.find(DaySetRecord);
    }
    
    /**
     * 查询日结明细
     * @author gaosiling
     */
    @Override
    public List<DaySetDetail> findList(DaySetDetail daySetDetail){
		return daySetDetailDao.findList(daySetDetail);
	}
    
    /**
     * 查询日结明细
     * @author gaosiling
     */
    @Override
    public List<DaySetDetail> findDetailList(DaySetDetail daySetDetail){
		return daySetDetailDao.findDetailList(daySetDetail);
	}
    
    /**
     * 查询长短调整和系统金额
     * @author gaosiling
     */
    @Override
    public SumDaySettle checkDaySettleAmt(String settleDay,Long operPlaceId,List<String> placeList){
    	DaySetLog daySetLog = new DaySetLog();
    	daySetLog.setOperId(operPlaceId);
    	daySetLog.setState(2);
    	String preSettleDay = DateUtil.getPreDay(settleDay);
    	daySetLog.setSettleDay(preSettleDay);
    	daySetLog = daySetLogDao.find(daySetLog);
    	
    	/*daySetLog.setSettleDay(settleDay);*/
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String startTime = null;
    	String endTime = null;
    	if(daySetLog!=null){
//    		startTime = sdf.format(daySetLog.getStartTime());
//    		endTime = sdf.format(daySetLog.getEndTime());
    		startTime = sdf.format(DateUtil.addSecond(daySetLog.getEndTime(), 1));
    	}else{
    		startTime = settleDay+"000000";
    	}
    	endTime = settleDay+"235959";
    	return daySetRecordDao.checkDaySettleAmt(startTime,endTime,operPlaceId,placeList,settleDay);
    	
    	
    }
    /**
     * 保存修正
     */
	@Override
	public void saveCorrect(List<DaySetDetail> daySetDetailList) {
		try{
			if(daySetDetailList.size()>0){
				DaySetRecord daySetRecord=new DaySetRecord();
				daySetRecord.setId(daySetDetailList.get(0).getMainID());
				daySetRecord=daySetRecordDao.find(daySetRecord);
				
				DaySetRecordHis daySetRecordHis=new DaySetRecordHis();
				BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSDaySetRecordHis_NO");
				daySetRecordHis.setId(Long.parseLong(seq.toString()));
				daySetRecordHis.setCreateReason("日结后差异金额修正");
				daySetRecordHisDao.saveHis(daySetRecordHis, daySetRecord);
				daySetRecordDao.updateDifferentialMarkerById(daySetDetailList.get(0).getMainID(),daySetRecordHis.getId(), "2");//修改记录表里的状态  2：差异已修正
	
				DaySetDetailHis daySetDetailHis=new DaySetDetailHis();
				daySetDetailHis.setMainID(daySetRecordHis.getId());
				daySetDetailHisDao.saveHis(daySetDetailHis, daySetDetailList.get(0).getMainID());
				afterDaySetFeeDao.batchSaveAfterDaySetFee(daySetDetailList, daySetRecord);//插入修正记录表
			}	
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存修正失败");
			e.printStackTrace();
			throw new ApplicationException("保存修正失败");
		}
	}

	/**
     * 保存单条修正
     * @author gaosiling
     */
	@Override
	public void saveCorrectOne(AfterDaySetFee afterDaySetFee,String settleDay) {
		//DaySetDetail daySetDetail = new DaySetDetail();
		//daySetDetail.setId(afterDaySetFee.getDaySetDId());
		DaySetDetail daySetDetail = daySetDetailDao.findById(afterDaySetFee.getDaySetDId());
		BigDecimal fee = daySetDetail.getDifferenceFee().subtract(afterDaySetFee.getDifferenceFee());
		if(fee.compareTo(new BigDecimal("0"))==0){
			daySetDetail.setDifferenceFlag("2");
		}
		Long id = sequenceUtil.getSequenceLong("SEQ_CSMSAfterDaySetFee_NO");
		afterDaySetFee.setId(id);
		try{
			daySetDetail.setDifferenceFee(afterDaySetFee.getDifferenceFee());
			daySetDetailDao.update(daySetDetail);
			afterDaySetFeeDao.save(afterDaySetFee);
			if(daySetRecordDao.checkRecord(afterDaySetFee.getOperPlaceID(), settleDay)){
				daySetRecordDao.updateDifferentialMarkerById(afterDaySetFee.getDaySetID(),null, "2");//修改记录表里的状态  2：差异已修正
			}
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存资金修正失败");
			e.printStackTrace();
			throw new ApplicationException("保存资金修正失败");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
