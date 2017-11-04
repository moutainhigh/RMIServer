package com.hgsoft.daysettle.serviceInterface;

import java.util.Date;
import java.util.List;

import com.hgsoft.daysettle.entity.AfterDaySetWare;
import com.hgsoft.daysettle.entity.DaySetWareDetail;
import com.hgsoft.daysettle.entity.DaySetWareRecord;
import com.hgsoft.daysettle.entity.SumDaySettleWare;
import com.hgsoft.utils.Pager;

public interface IDaySetWareRecordService {

	public List<String> findDaySettleList(String type, Long operPlaceId, List<String> placeList);

	public DaySetWareRecord find(DaySetWareRecord daySetWareRecord);

	public void saveRecord(DaySetWareRecord daySetWareRecord,List<DaySetWareDetail> list);

	public List<DaySetWareDetail> findList(DaySetWareDetail daySetWareDetail);

	public Pager findByPage(Pager pager, Date starTime, Date endTime,
			DaySetWareRecord daySetWareRecord);

	public void updateWareRecord(DaySetWareRecord daySetWareRecord,
			List<AfterDaySetWare> list, Long reportOperID,
			Long reportPlaceID);
	
	public void saveCorrectOne(AfterDaySetWare afterDaySetWare, String settleDay);

	public SumDaySettleWare checkDaySettleNum(String settleDay, Long operPlaceId,List<String> placeList,Integer stockPlace);

}
