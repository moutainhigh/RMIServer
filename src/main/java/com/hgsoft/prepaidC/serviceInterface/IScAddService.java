package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddReq;
import com.hgsoft.prepaidC.entity.ScAddSure;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * @author : 吴锡霖
 * file : IScAddService.java
 * date : 2017-06-27
 * time : 16:27
 */
public interface IScAddService {

	void saveScAddReq(ScAddReq scAddReq);

	void saveScAddSend(ScaddSend scaddSend);

	void saveScAddReqByBussiness(PrepaidCBussiness prepaidCBussiness,
			String str, String paychannel);

	void saveScAddSendByBussiness(PrepaidCBussiness prepaidCBussiness,
			String str);

	void saveScAddSure(ScAddSure scAddSure);

	void saveScAddSureSend(ScAddSureSend scAddSureSend);

	void saveScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness,
			String str, String paychannel);

	void saveScAddSureSendByBussiness(
			PrepaidCBussiness prepaidCBussiness, String str);

	void updateScAddSure(ScAddSure scAddSure);

	void updateScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness,
			String state);

	void updateScAddSureSend(ScAddSureSend scAddSureSend);

	void updateScAddSureSendByBussiness(
			PrepaidCBussiness prepaidCBussiness, String state);

	ScAddSure findScAddSureByCardNO(String cardNo);

	ScAddSureSend findScAddSureSendByCardNO(String cardNo);

    /**
	 * @Description:用于空充 ：一天对账流水查询
	 * @return ScAddSureSend termNo、placeno都是用申请的字段？
	 */
	List<ScAddSure> findScAddSure(String placeNo, String termNo,
			String reqBeginDate, String reqEndDate, String sureBeginDate,
			String sureEndDate);

    /**
	 * @Description:TODO
	 * @param placeNo
	 * @param termNo
	 * @param reqDate
	 * @param cardNo
	 * @return List<ScAddSure> termNo、placeno都是用申请的字段？
	 */
	List<ScAddSure> findScAddSure(String placeNo, String termNo,
			String reqDate, String cardNo);

    /**
	 * 查半条，即state状态为空
	 * 
	 * @param termNo
	 * @param cardNo
	 * @return List<ScAddSure>
	 */
	List<ScAddSure> findScAddSure(String termNo, String cardNo)
			throws IllegalAccessException, NoSuchMethodException,
			InvocationTargetException;

    ScAddSure findScAddSureByCardTime(String cardNo,Date timeReq);

    ScAddSureSend findScAddSureSendByCardTime(String cardNo,Date timeReq);

    ScAddSure findLastSureByCardNo(String cardNo);
}
