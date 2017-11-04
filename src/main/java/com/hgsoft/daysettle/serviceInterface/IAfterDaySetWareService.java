package com.hgsoft.daysettle.serviceInterface;

import java.util.List;

import com.hgsoft.daysettle.entity.AfterDaySetWare;

public interface IAfterDaySetWareService {

	public void batchSaveAfterDaySetWare(List<AfterDaySetWare> list,
			Long reportOperID, Long reportPlaceID);

}
