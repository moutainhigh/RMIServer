package com.hgsoft.daysettle.serviceInterface;

import java.util.List;

import com.hgsoft.daysettle.entity.RegisterDaySetDetail;
import com.hgsoft.daysettle.entity.RegisterDaySetRecord;

public interface IRegisterDaySetRecordService {

	public List<String> findDaySettleList(Long operId, List<String> placeList);

	public void save(RegisterDaySetRecord registerDaySetRecord,
			List<RegisterDaySetDetail> registerDaySetDetailList);

}
