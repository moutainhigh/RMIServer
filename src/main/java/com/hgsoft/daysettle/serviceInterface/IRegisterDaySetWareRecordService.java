package com.hgsoft.daysettle.serviceInterface;

import java.util.List;

import com.hgsoft.daysettle.entity.RegisterDaySetWareDetail;
import com.hgsoft.daysettle.entity.RegisterDaySetWareRecord;

public interface IRegisterDaySetWareRecordService {

	public List<String> findDaySettleList(String type, Long operPlaceId, List<String> placeList);

	public RegisterDaySetWareRecord find(RegisterDaySetWareRecord registerDaySetWareRecord);

	public void saveRecord(RegisterDaySetWareRecord registerDaySetWareRecord,List<RegisterDaySetWareDetail> list);

	public List<RegisterDaySetWareDetail> findList(RegisterDaySetWareDetail registerDaySetWareDetail);

	


}
