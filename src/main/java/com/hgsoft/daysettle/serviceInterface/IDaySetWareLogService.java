package com.hgsoft.daysettle.serviceInterface;

import com.hgsoft.daysettle.entity.DaySetWareLog;

public interface IDaySetWareLogService {

	public void save(DaySetWareLog daySetWareLog) ;

	public DaySetWareLog find(DaySetWareLog daySetWareLog);

	public void update(DaySetWareLog daySetWareLog);

	public DaySetWareLog findById(Long id,Integer state,String settleDay);
}
