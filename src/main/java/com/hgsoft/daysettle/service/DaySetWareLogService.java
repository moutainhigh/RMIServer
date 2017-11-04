package com.hgsoft.daysettle.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.DaySetWareLogDao;
import com.hgsoft.daysettle.entity.DaySetWareLog;
import com.hgsoft.daysettle.serviceInterface.IDaySetWareLogService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.SequenceUtil;

/**
 * 库存日结上报日志
 * @author gaosiling
 * 2016年2月1日09:04:45
 */
@Service
public class DaySetWareLogService implements IDaySetWareLogService{
	
	private static Logger logger = Logger.getLogger(DaySetWareLogService.class.getName());
	
	@Resource
	private DaySetWareLogDao daySetWareLogDao;
	
	@Resource
	private SequenceUtil sequenceUtil;

	/**
	 * 库存日结上报日志保存
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public void save(DaySetWareLog daySetWareLog) {
		Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSDaySetWareLog_NO");
		daySetWareLog.setId(seq);
		try{
			daySetWareLogDao.save(daySetWareLog);
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存库存日结上报日志失败");
			e.printStackTrace();
			throw new ApplicationException("保存库存日结上报日志失败");
		}
		
	}
	
	/**
	 * 库存日结上报日志查询
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public DaySetWareLog find(DaySetWareLog daySetWareLog) {
		return daySetWareLogDao.find(daySetWareLog);
	} 
	
	/**
	 * 日结日志查询
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public DaySetWareLog findById(Long reportPlaceID,Integer state,String settleDay){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String settleDay = sdf.format(new Date());
		DaySetWareLog daySetWareLog = new DaySetWareLog();
		if(settleDay!=null){
			daySetWareLog.setSettleDay(settleDay);
		}
		daySetWareLog.setReportPlaceID(reportPlaceID);//操作网点
		daySetWareLog.setState(state);//state为2代表日结已完成
		
		return daySetWareLogDao.find(daySetWareLog);
	} 
	
	/**
	 * 日结日志更新
	 * @param daySetLog
	 * @author gaosiling
	 */
	@Override
	public void update(DaySetWareLog daySetWareLog){
		try{
			daySetWareLogDao.update(daySetWareLog);
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"更新库存日结上报日志ID："+daySetWareLog.getId()+"失败");
			e.printStackTrace();
			throw new ApplicationException("更新库存日结上报日志ID："+daySetWareLog.getId()+"失败");
		}
	}

}
