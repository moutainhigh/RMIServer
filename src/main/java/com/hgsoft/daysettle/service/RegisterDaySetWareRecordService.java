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

import com.hgsoft.daysettle.dao.RegisterDaySetWareDetailDao;
import com.hgsoft.daysettle.dao.RegisterDaySetWareRecordDao;
import com.hgsoft.daysettle.entity.RegisterDaySetWareDetail;
import com.hgsoft.daysettle.entity.RegisterDaySetWareRecord;
import com.hgsoft.daysettle.serviceInterface.IRegisterDaySetWareRecordService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.SequenceUtil;

/**
 * 库存日结上报
 * @author gaosiling
 * 2016年1月28日10:10:56
 */
@Service
public class RegisterDaySetWareRecordService implements IRegisterDaySetWareRecordService{
	
	private static Logger logger = Logger.getLogger(RegisterDaySetWareRecordService.class.getName());
	
	@Resource
	private RegisterDaySetWareRecordDao registerDaySetWareRecordDao;
	
	@Resource
	private RegisterDaySetWareDetailDao registerDaySetWareDetailDao;
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	

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
			//查询可以上报的日期
			String daySettle = registerDaySetWareRecordDao.findDaySettleList(type,operPlaceId,placeList);
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();

			String startTime = format.format(start.getTime());
			String orType="1";
			Date date = null;
			try {
				if(daySettle==null){
					daySettle = registerDaySetWareRecordDao.findDaySettleList(type,null,null);
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
    public void saveRecord(RegisterDaySetWareRecord registerDaySetWareRecord,List<RegisterDaySetWareDetail> list){
    	try {
    		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSRDaySetWareRecord_NO");
    		registerDaySetWareRecord.setId(Long.parseLong(seq.toString()));
    		registerDaySetWareRecordDao.save(registerDaySetWareRecord);//保存CSMS_RegisterDaySetWare_Record
    		//批量保存CSMS_RegisterDaySetWare_Detail记录
    		registerDaySetWareDetailDao.savebatchDaySetDetail(list, registerDaySetWareRecord);
    		
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
    public RegisterDaySetWareRecord find(RegisterDaySetWareRecord registerDaySetWareRecord){
    	return registerDaySetWareRecordDao.find(registerDaySetWareRecord);
    }
    
    /**
     * 查询库存上报明细
     * @author gaosiling
     */
    @Override
    public List<RegisterDaySetWareDetail> findList(RegisterDaySetWareDetail registerDaySetWareDetail){
		return registerDaySetWareDetailDao.findList(registerDaySetWareDetail);
	}
    


}
