package com.hgsoft.airrecharge.serviceInterface;

import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddSure;

import java.util.Date;
import java.util.Map;

/**
 * @author : 吴锡霖
 * file : IAirRechargeSureService.java
 * date : 2017-06-29
 * time : 9:44
 */
public interface IAirRechargeSureService {
	Long saveRechargeSure(PrepaidC prepaidC, Date addReqTimeCheck,
			ScAddSure scAddSure, ScAddSureSend addSureSend);

	Map<String, Object> saveHalfTrue(PrepaidCBussiness oldPrepaidCBussiness);

	Map<String, Object> saveHalfFalse(PrepaidCBussiness oldPrepaidCBussiness);
}
