package com.hgsoft.daysettle.serviceInterface;

import java.util.List;

import com.hgsoft.daysettle.entity.PreDaySetDetail;
import com.hgsoft.daysettle.entity.PreDaySetRecord;

public interface IPreDaySetRecordService {

	public List<String> findDaySettleList(String type,String operId);
	
	public void saveRecord(PreDaySetRecord preDaySetRecord,List<PreDaySetDetail> list);

	public PreDaySetRecord find(PreDaySetRecord preDaySetRecord);

	public List<PreDaySetDetail> findList(PreDaySetDetail preDaySetDetail);
}
