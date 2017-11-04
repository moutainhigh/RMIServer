package com.hgsoft.daysettle.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.RegisterDaySetDetailDao;
import com.hgsoft.daysettle.dao.RegisterDaySetRecordDao;
import com.hgsoft.daysettle.entity.RegisterDaySetDetail;
import com.hgsoft.daysettle.entity.RegisterDaySetRecord;
import com.hgsoft.daysettle.serviceInterface.IRegisterDaySetRecordService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.SequenceUtil;

/**
 * 网点日结登记
 * @author gaosiling
 * 2016-06-17 15:57:05
 *
 */
@Service
public class RegisterDaySetRecordService implements IRegisterDaySetRecordService{
	
	private static Logger logger = Logger.getLogger(RegisterDaySetRecordService.class.getName());

	@Resource
	private RegisterDaySetRecordDao registerDaySetRecordDao;
	
	@Resource
	private RegisterDaySetDetailDao registerDaySetDetailDao;
	
	@Resource
	private SequenceUtil sequenceUtil;
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
			//查出CSMS_RegisterDaySet_Record表中最新的一条日结时间记录
			String daySettle = registerDaySetRecordDao.findDaySettleList(operPlaceId,placeList);
			Calendar end = Calendar.getInstance();
			Calendar start = Calendar.getInstance();
			
			String startTime = format.format(start.getTime());
			Date date = null;
			String type="1";
			try {
				//是否已存在日结记录，若不存在判断最初缴款时间
				if(daySettle==null){
					daySettle = registerDaySetRecordDao.findDaySettleList(null,null);
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
	
	
	public void save(RegisterDaySetRecord registerDaySetRecord) {
		
	}


	@Override
	public void save(RegisterDaySetRecord registerDaySetRecord,List<RegisterDaySetDetail> registerDaySetDetailList) {
		try {
			registerDaySetRecord.setId(sequenceUtil.getSequenceLong("SEQ_CSMSReDaySetRecord_NO"));
			registerDaySetRecordDao.save(registerDaySetRecord);//保存CSMS_RegisterDaySet_Record网点资金上报记录表
			//批量保存详情CSMS_RegisterDaySet_Detail
			registerDaySetDetailDao.savebatchDaySetDetail(registerDaySetRecord, registerDaySetDetailList);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存网点日结登记失败");
			e.printStackTrace();
			throw new ApplicationException("保存网点日结登记失败");
		}
	}
}
