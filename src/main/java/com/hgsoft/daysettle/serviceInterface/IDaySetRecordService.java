package com.hgsoft.daysettle.serviceInterface;

import java.util.Date;
import java.util.List;

import com.hgsoft.daysettle.entity.AfterDaySetFee;
import com.hgsoft.daysettle.entity.DaySetDetail;
import com.hgsoft.daysettle.entity.DaySetRecord;
import com.hgsoft.daysettle.entity.SumDaySettle;
import com.hgsoft.utils.Pager;

public interface IDaySetRecordService {

	public List<String> findDaySettleList(Long operId, List<String> placeList);
	
	public void saveRecord(DaySetRecord DaySetRecord,List<DaySetDetail> list, List<String> placeList);

	public DaySetRecord find(DaySetRecord daySetRecord);

	public List<DaySetDetail> findList(DaySetDetail DaySetDetail);

	public SumDaySettle checkDaySettleAmt(String daySettle,Long operId,List<String> placeList);
	
	public Pager list(Pager pager,Date starTime ,Date endTime,DaySetRecord daySetRecord);
	
	public void saveCorrect(List<DaySetDetail> daySetDetailList);

	public void saveCorrectOne(AfterDaySetFee afterDaySetFee,String settleDay);
	
	public List<DaySetDetail> findDetailList(DaySetDetail daySetDetail);
}
