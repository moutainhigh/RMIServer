package com.hgsoft.daysettle.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.PreDaySetDetailDao;
import com.hgsoft.daysettle.dao.PreDaySetRecordDao;
import com.hgsoft.daysettle.entity.PreDaySetDetail;
import com.hgsoft.daysettle.entity.PreDaySetRecord;
import com.hgsoft.daysettle.serviceInterface.IPreDaySetRecordService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.SequenceUtil;

/**
 * 日结
 * @author gaosiling
 * 2016年1月27日11:37:26
 */
@Service
public class PreDaySetRecordService implements IPreDaySetRecordService{
	
	private static Logger logger = Logger.getLogger(PreDaySetRecordService.class.getName());
	
	@Resource
	private PreDaySetRecordDao preDaySetRecordDao;
	
	@Resource
	private PreDaySetDetailDao preDaySetDetailDao;
	
	@Resource
	private SequenceUtil sequenceUtil;

	/**
	 * 日结日期获取
	 * @param  type 1已日结 2未日结
	 * @author gaosiling
	 */
	@Override
	public List<String> findDaySettleList(String type,String operId) {
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
			String daySettle = preDaySetRecordDao.findDaySettleList(type,operId);
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			if(daySettle!=null){
				Date  date = format.parse(daySettle);
				start.setTime(date);
			}
	        
	        list = getDates(start, end);
		} catch (ParseException e) {
			logger.error(e.getMessage()+"日期格式转换失败");
			e.printStackTrace();
			throw new ApplicationException("日期格式转换失败");
		}
		return list;
	}
	
 
	/**
	 * 日期差
	 * @param  p_start 开始日期
	 * @param  p_end 结束日期
	 * @author gaosiling
	 */
    private List<String> getDates(Calendar p_start, Calendar p_end) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<String> result = new ArrayList<String>();
        Calendar temp = Calendar.getInstance();
        temp.setTime(p_start.getTime());
       
        System.out.println(sdf.format(p_end.getTime()));
        int i=0;
        while (temp.before(p_end)) {
        	if(i!=0){
        		result.add( sdf.format(temp.getTime()));
        	}
            temp.add(Calendar.DAY_OF_YEAR, 1);
            i++;
        }
        return result;
    }
    
    /**
     * 保存模拟日结记录
     * @author gaosiling
     */
    public void saveRecord(PreDaySetRecord preDaySetRecord,List<PreDaySetDetail> list){
    	try {
    		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSPreDaySetRecord_NO");
    		preDaySetRecord.setId(Long.parseLong(seq.toString()));
    		preDaySetRecordDao.save(preDaySetRecord);
    		preDaySetDetailDao.batchSaveDaySetDetail(list, preDaySetRecord);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存模拟日结记录失败");
			e.printStackTrace();
			throw new ApplicationException("保存模拟日结记录失败");
		}
    }
    
    /**
     * 查询模拟日结记录
     * @author gaosiling
     */
    @Override
    public PreDaySetRecord find(PreDaySetRecord preDaySetRecord){
    	return preDaySetRecordDao.find(preDaySetRecord);
    }
    
    /**
     * 查询模拟日结明细
     * @author gaosiling
     */
    @Override
    public List<PreDaySetDetail> findList(PreDaySetDetail preDaySetDetail){
		return preDaySetDetailDao.findList(preDaySetDetail);
	}

}
