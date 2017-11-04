package com.hgsoft.airrecharge.serviceInterface;

import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.ScAddReq;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;

/**
 * @author : 吴锡霖
 * file : IAirRechargeReqService.java
 * date : 2017-06-28
 * time : 14:40
 */
public interface IAirRechargeReqService {
	void save(PrepaidC prepaidC, ScAddReq scAddReq, ScAddSure scAddSure,
			ScaddSend scaddSend, ScAddSureSend scAddSureSend,
			CusPointPoJo cusPointPoJo, SysAdmin sysAdmin);
}
