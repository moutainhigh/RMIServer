package com.hgsoft.daysettle.serviceInterface;

import java.util.Date;
import java.util.List;

import com.hgsoft.daysettle.entity.DaySetLog;

public interface IDaySetLogService {

	public void save(DaySetLog daySetLog) ;

	public DaySetLog find(DaySetLog daySetLog);

	public void update(DaySetLog daySetLog);

	public DaySetLog findById(List<String> placeList,Integer state,String settleDay);

	public boolean checkDaySet(Date date,Long placeId,List<String> list);
}
