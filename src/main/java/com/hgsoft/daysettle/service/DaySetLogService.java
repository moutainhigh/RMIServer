package com.hgsoft.daysettle.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetLogDao;
import com.hgsoft.daysettle.entity.DaySetLog;
import com.hgsoft.daysettle.serviceInterface.IDaySetLogService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.SequenceUtil;

/**
 * 日结日志
 * @author gaosiling
 * 2016年2月1日09:04:45
 */
@Service
public class DaySetLogService implements IDaySetLogService{
	
	private static Logger logger = Logger.getLogger(DaySetLogService.class.getName());
	
	@Resource
	private DaySetLogDao daySetLogDao;
	
	@Resource
	private SequenceUtil sequenceUtil;

	/**
	 * 日结日志保存
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public void save(DaySetLog daySetLog) {
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSDaySetLog_NO");
		daySetLog.setId(seq);
		try{
			daySetLogDao.save(daySetLog);
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存日结日志失败");
			e.printStackTrace();
			throw new ApplicationException("保存日结日志失败");
		}
		
	}
	
	/**
	 * 日结日志查询
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public DaySetLog find(DaySetLog daySetLog) {
		return daySetLogDao.find(daySetLog);
	} 
	
	/**
	 * 日结日志查询
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public DaySetLog findById(List<String> placeList,Integer state,String settleDay){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String settleDay = sdf.format(new Date());
		DaySetLog daySetLog = new DaySetLog();
		if(settleDay!=null){
			daySetLog.setSettleDay(settleDay);
		}
		/*daySetLog.setOperPlaceId(operPlaceId);*/
		daySetLog.setState(state);
		return daySetLogDao.find(placeList,daySetLog);
	} 
	
	/**
	 * 日结日志更新
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public void update(DaySetLog daySetLog){
		try{
			daySetLogDao.update(daySetLog);
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"更新日结日志ID："+daySetLog.getId()+"失败");
			e.printStackTrace();
			throw new ApplicationException("更新日结日志ID："+daySetLog.getId()+"失败");
		}
	}
	
	/**
	 * 日结校验
	 * @author gaosiling
	 */
	@Override
	public boolean checkDaySet(Date date,Long placeId,List<String> list){
		DaySetLog daySetLog = daySetLogDao.findDaySetOnlyOne(placeId,list);
		if(daySetLog==null){
			logger.info("无日结日！");
			return true;
		}
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		logger.info("业务时间："+sm.format(date)+"----------------"+"上一次日结时间："+sm.format(daySetLog.getEndTime()));
		if(date.after(daySetLog.getEndTime())){
			return true;
		}
		return false;
	}
	

}
