package com.hgsoft.daysettle.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.AfterDaySetWareDao;
import com.hgsoft.daysettle.dao.DaySetWareDetailDao;
import com.hgsoft.daysettle.dao.DaySetWareDetailHisDao;
import com.hgsoft.daysettle.dao.DaySetWareLogDao;
import com.hgsoft.daysettle.dao.DaySetWareRecordDao;
import com.hgsoft.daysettle.dao.DaySetWareRecordHisDao;
import com.hgsoft.daysettle.entity.AfterDaySetWare;
import com.hgsoft.daysettle.entity.DaySetWareDetail;
import com.hgsoft.daysettle.entity.DaySetWareDetailHis;
import com.hgsoft.daysettle.entity.DaySetWareLog;
import com.hgsoft.daysettle.entity.DaySetWareRecord;
import com.hgsoft.daysettle.entity.DaySetWareRecordHis;
import com.hgsoft.daysettle.entity.SumDaySettleWare;
import com.hgsoft.daysettle.serviceInterface.IDaySetWareRecordService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.serviceInterface.IDaySetWareInterfaceService;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagTakeInfoDao;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

/**
 * 库存日结上报
 * @author gaosiling
 * 2016年1月28日10:10:56
 */
@Service
public class DaySetWareRecordService implements IDaySetWareRecordService{
	
	private static Logger logger = Logger.getLogger(DaySetWareRecordService.class.getName());
	
	@Resource
	private DaySetWareRecordDao daySetWareRecordDao;
	
	@Resource
	private DaySetWareDetailDao daySetWareDetailDao;
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	@Resource
	private AfterDaySetWareDao afterDaySetWareDao;
	
	@Resource
	private DaySetWareRecordHisDao daySetWareRecordHisDao;
	
	@Resource
	private DaySetWareDetailHisDao daySetWareDetailHisDao;
	
	@Resource
	private DaySetWareLogDao daySetWareLogDao;
	
	@Resource
	private TagTakeInfoDao tagTakeInfoDao;
	
	@Resource
	private TagInfoDao tagInfoDao;
	
	@Resource
	private IDaySetWareInterfaceService daySetWareInterfaceService;

	/**
	 * 日结日期获取
	 * @param  type 1已日结 2未日结
	 * @author gaosiling
	 */
	@Override
	public List<String> findDaySettleList(String type,Long operPlaceId, List<String> placeList) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
			String daySettle = daySetWareRecordDao.findDaySettleList(type,operPlaceId,placeList);
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();

			String startTime = format.format(start.getTime());
			String orType="1";
			Date date = null;
			try {
				if(daySettle==null){
					daySettle = daySetWareRecordDao.findDaySettleList(type,null,null);
					if(daySettle==null){
						list.add(startTime);
						return list;
					}
				}
				//若存在日结日则按照日结日为开始日结时间
				date = format.parse(daySettle);
				orType="2";
				start.setTime(date);
				startTime = format.format(start.getTime());
				String endTime = format.format(end.getTime());
				if(!endTime.equals(startTime)){
					list = DateUtil.getDates(start, end,orType);
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
     * 保存库存上报记录
     * @author gaosiling
     */
	@Override
    public void saveRecord(DaySetWareRecord daySetWareRecord,List<DaySetWareDetail> list){
    	try {
    		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSDaySetWareRecord_NO");
    		daySetWareRecord.setId(Long.parseLong(seq.toString()));
    		daySetWareRecordDao.save(daySetWareRecord);
    		daySetWareDetailDao.savebatchDaySetDetail(list, daySetWareRecord);
    		
    		
    		//修改日结日志结束时间和状态
    		DaySetWareLog daySetWareLog = new DaySetWareLog();
    		daySetWareLog.setState(2);
    		daySetWareLog.setReportPlaceID(daySetWareRecord.getReportPlaceID());
    		
    		String preSettleDay = DateUtil.getPreDay(daySetWareRecord.getSettleDay());
    		daySetWareLog.setSettleDay(preSettleDay);
    	/*	DaySetWareLog beforeDaySetWareLog = daySetWareLogDao.find(daySetWareLog);*/
    		
    		daySetWareLog.setSettleDay(daySetWareRecord.getSettleDay());
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    		Date date = new Date();
    		String nowDate = sdf.format(date);
    		
    		//当前日和日结日是否为同一天，若是则直接记录当前日结时间，否则需记录当前日结时间为日结日的23:59:59
    		if(nowDate.equals(daySetWareRecord.getSettleDay())){
    			daySetWareLog.setEndTime(new Date());
    		}else{
    			sdf = new SimpleDateFormat("yyyyMMddHHssmm");
    			try {
    				date = sdf.parse(daySetWareRecord.getSettleDay()+"235959");
    				daySetWareLog.setEndTime(date);
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
    		
    		daySetWareLogDao.update(daySetWareLog);
    		/*String startTime = null;
        	String endTime = null;
        	if(beforeDaySetWareLog!=null){
        		startTime = sdf.format(beforeDaySetWareLog.getStartTime());
        		endTime = sdf.format(beforeDaySetWareLog.getEndTime());
        	}else{
        		startTime = daySetWareRecord.getSettleDay()+"000000";
        		endTime = daySetWareRecord.getSettleDay()+"235959";
        	}*/
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存库存上报记录失败");
			e.printStackTrace();
			throw new ApplicationException("保存库存上报记录失败");
		}
    }
    
    /**
     * 查询库存上报记录
     * @author gaosiling
     */
    @Override
    public DaySetWareRecord find(DaySetWareRecord daySetWareRecord){
    	return daySetWareRecordDao.find(daySetWareRecord);
    }
    
    /**
     * 查询库存上报明细
     * @author gaosiling
     */
    @Override
    public List<DaySetWareDetail> findList(DaySetWareDetail daySetWareDetail){
		return daySetWareDetailDao.findList(daySetWareDetail);
	}
    
    /**
     * 分页查询
     * @author gaosiling
     */
    @Override
    public Pager findByPage(Pager pager,Date starTime ,Date endTime, DaySetWareRecord daySetWareRecord) {
    	return daySetWareRecordDao.findByPage(pager, starTime, endTime, daySetWareRecord);
    }
    
    @Override
    public void updateWareRecord(DaySetWareRecord daySetWareRecord,List<AfterDaySetWare> list,Long reportOperID,Long reportPlaceID){
    	
    	try {
    		DaySetWareRecordHis daySetWareRecordHis = new DaySetWareRecordHis();
    		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSDaySetWareRecordHis_NO");
    		daySetWareRecordHis.setId(Long.parseLong(seq.toString()));
    		daySetWareRecordHis.setCreateReason("日结后差异数量修正");
    		
    		DaySetWareDetailHis daySetWareDetailHis = new DaySetWareDetailHis();
    		daySetWareDetailHis.setMainID(daySetWareRecordHis.getId());
    		daySetWareRecord.setHisSeqID(daySetWareRecordHis.getId());
    		
    		daySetWareRecordHisDao.saveHis(daySetWareRecordHis, daySetWareRecord);
    		daySetWareDetailHisDao.saveHis(daySetWareDetailHis, daySetWareRecord.getId());
    		daySetWareRecordDao.update(daySetWareRecord);
    		afterDaySetWareDao.batchSaveAfterDaySetWare(list, reportOperID, reportPlaceID);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存库存日结差异失败");
			e.printStackTrace();
			throw new ApplicationException("保存库存日结差异失败");
		}
    }

    /**
     * 保存单条修正
     * @author gaosiling
     */
	@Override
	public void saveCorrectOne(AfterDaySetWare afterDaySetWare, String settleDay) {
		DaySetWareDetail daySetWareDetail = daySetWareDetailDao.findById(afterDaySetWare.getDaySetWareDId());
		Integer num = 0;
		if("1".equals(daySetWareDetail.getServiceType())){
			num = daySetWareDetail.getBalanceDiffNum() - afterDaySetWare.getBalanceDiffNum();
		}else if("2".equals(daySetWareDetail.getServiceType())){
			num = daySetWareDetail.getRecoverDiffNum() - afterDaySetWare.getRecoverDiffNum();
		}
		if(num==0){
			daySetWareDetail.setDifferenceFlag("2");
		}
		//daySetWareDetail.setId(afterDaySetWare.getDaySetWareDId());
		
		/*BigDecimal fee = daySetWareDetail.getDifferenceFee().subtract(afterDaySetFee.getDifferenceFee());
		if(fee.compareTo(new BigDecimal("0"))==0){
			daySetDetail.setDifferenceFlag("2");
		}*/
		
		Long id = sequenceUtil.getSequenceLong("SEQ_CSMSAfterDaySetWare_NO");
		afterDaySetWare.setId(id);
		
		
		try{
			daySetWareDetail.setBalanceDiffNum(afterDaySetWare.getBalanceDiffNum());
			daySetWareDetail.setRecoverDiffNum(afterDaySetWare.getRecoverDiffNum());
			daySetWareDetailDao.update(daySetWareDetail);
			afterDaySetWareDao.save(afterDaySetWare);
			if(daySetWareRecordDao.checkRecord(afterDaySetWare.getReportPlaceID(), settleDay)){
				DaySetWareRecord daySetWareRecord = new DaySetWareRecord();
				daySetWareRecord.setDifferenceFlag("2");
				daySetWareRecord.setId(afterDaySetWare.getDatSetWareID());
				daySetWareRecordDao.update(daySetWareRecord);
			}
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存库存上报修正失败");
			e.printStackTrace();
			throw new ApplicationException("保存库存上报修正失败");
		}
		
	}
	
	/**
     * 查询长短调整和系统金额
     * @author gaosiling
     */
    @Override
    public SumDaySettleWare checkDaySettleNum(String settleDay,Long operPlaceId,List<String> placeList,Integer stockPlace){
    	DaySetWareLog daySetWareLog = new DaySetWareLog();
    	daySetWareLog.setReportOperID(operPlaceId);
    	daySetWareLog.setState(2);
    	String preSettleDay = DateUtil.getPreDay(settleDay);
    	daySetWareLog.setSettleDay(preSettleDay);
    	daySetWareLog = daySetWareLogDao.find(daySetWareLog);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String startTime = null;
    	String endTime = null;
    	if(daySetWareLog!=null){
    		startTime = sdf.format(DateUtil.addSecond(daySetWareLog.getEndTime(), 1));
    	}else{
    		startTime = settleDay+"000000";
    	}
    	endTime = settleDay+"235959";
    	SumDaySettleWare sumDaySettleWare = daySetWareRecordDao.checkDaySettleSum(startTime,endTime,operPlaceId,placeList,settleDay);
    	try {
    		System.out.println("stockPlace="+stockPlace);
    		String data = daySetWareInterfaceService.getProductDetailDay(settleDay, stockPlace);
    		System.out.println("ProductDetail返回值："+data);
        	JSONObject json = JSONObject.fromObject(data);
        	if("0".equals(json.get("flag"))){
        		//储值卡
        		JSONObject map = JSONObject.fromObject(json.get("prePaidCardMap"));
        		if(map!=null){
        			sumDaySettleWare.setSysPaidBalance(map.getString("detailCount"));
        			sumDaySettleWare.setSysPaidRecover(map.getString("reclaimCount"));
        		}
        		//记帐卡
        		map = JSONObject.fromObject(json.get("deBitCardMap"));
        		if(map!=null){
        			sumDaySettleWare.setSysAccBalance(map.getString("detailCount"));
        			sumDaySettleWare.setSysAccRecover(map.getString("reclaimCount"));
        		}
        		//obuMap
        		map = JSONObject.fromObject(json.get("obuMap"));
        		if(map!=null){
        			sumDaySettleWare.setSysTagBalance(map.getString("detailCount"));
        			sumDaySettleWare.setSysTagRecover(map.getString("reclaimCount"));
        		}
        		//billMap
        		map = JSONObject.fromObject(json.get("billMap"));
        		if(map!=null){
        			sumDaySettleWare.setSysbillBalance(map.getString("detailCount"));
        			sumDaySettleWare.setSysbillRecover(map.getString("reclaimCount"));
        		}
        	}
        	data = daySetWareInterfaceService.getInOutStockDay(settleDay, stockPlace);
        	System.out.println("InOutStock返回值："+data);
        	json = JSONObject.fromObject(data);
        	if("0".equals(json.get("flag"))){
        		JSONObject map = JSONObject.fromObject(json.get("prePaidCardMap"));
        		if(map!=null){
        			sumDaySettleWare.setPreOutStockNum(map.getString("outStockCount"));
        			sumDaySettleWare.setPreInStockNum(map.getString("inStockCount"));
        		}
        		//记帐卡
        		map = JSONObject.fromObject(json.get("deBitCardMap"));
        		if(map!=null){
        			sumDaySettleWare.setAccOutStockNum(map.getString("outStockCount"));
        			sumDaySettleWare.setAccInStockNum(map.getString("inStockCount"));
        		}
        		//obuMap
        		map = JSONObject.fromObject(json.get("obuMap"));
        		if(map!=null){
        			sumDaySettleWare.setTagOutStockNum(map.getString("outStockCount"));
        			sumDaySettleWare.setTagInStockNum(map.getString("inStockCount"));
        		}
        		//billMap
        		map = JSONObject.fromObject(json.get("billMap"));
        		if(map!=null){
        			sumDaySettleWare.setBillOutStockNum(map.getString("outStockCount"));
        			sumDaySettleWare.setBillInStockNum(map.getString("inStockCount"));
        		}
        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	System.out.println(sumDaySettleWare);
    	return sumDaySettleWare;
    	
    	
    }

}
