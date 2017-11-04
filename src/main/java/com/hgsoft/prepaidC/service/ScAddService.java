package com.hgsoft.prepaidC.service;

import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.prepaidC.dao.ScAddDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddReq;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.prepaidC.serviceInterface.IScAddService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class ScAddService implements IScAddService {

	private static Logger logger = Logger.getLogger(ScAddService.class);
	
	@Resource
	private ScAddDao scAddDao;

	public void saveScAddReq(ScAddReq scAddReq){
		scAddDao.saveScAddReq(scAddReq);
	}

	public void saveScAddSend(ScaddSend scaddSend){
		scAddDao.saveScAddSend(scaddSend);
	}

	public void  saveScAddReqByBussiness(PrepaidCBussiness prepaidCBussiness, String str, String paychannel){
		scAddDao.saveScAddReqByBussiness(prepaidCBussiness, str, paychannel);
	}

	public void  saveScAddSendByBussiness(PrepaidCBussiness prepaidCBussiness,String str){
		scAddDao.saveScAddSendByBussiness(prepaidCBussiness, str);
	}

	public void saveScAddSure(ScAddSure scAddSure){
		scAddDao.saveScAddSure(scAddSure);
	}

	public void saveScAddSureSend(ScAddSureSend scAddSureSend){
		scAddDao.saveScAddSureSend(scAddSureSend);
	}

	public void saveScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness,String str, String paychannel){
		scAddDao.saveScAddSureByBussiness(prepaidCBussiness, str, paychannel);
	}

	public void saveScAddSureSendByBussiness(PrepaidCBussiness prepaidCBussiness,String str){
		scAddDao.saveScAddSureSendByBussiness(prepaidCBussiness, str);
	}

	public void updateScAddSure(ScAddSure scAddSure) {
		scAddDao.updateScAddSure(scAddSure);
	}

	public void updateScAddSureByBussiness(PrepaidCBussiness prepaidCBussiness,String state) {
		scAddDao.updateScAddSureByBussiness(prepaidCBussiness, state);
	}

	public void updateScAddSureSend(ScAddSureSend scAddSureSend) {
		scAddDao.updateScAddSureSend(scAddSureSend);
	}

	public void updateScAddSureSendByBussiness(
			PrepaidCBussiness prepaidCBussiness, String state) {
		scAddDao.updateScAddSureSendByBussiness(prepaidCBussiness, state);
	}

	public ScAddSure findScAddSureByCardNO(String cardNo){
		return scAddDao.findScAddSureByCardNO(cardNo);
	}

	public ScAddSureSend findScAddSureSendByCardNO(String cardNo){
		return scAddDao.findScAddSureSendByCardNO(cardNo);
	}

	/**
	 * @Description:用于空充 ：一天对账流水查询
	 * @return ScAddSureSend termNo、placeno都是用申请的字段？
	 */
	public List<ScAddSure> findScAddSure(String placeNo, String termNo,
			String reqBeginDate, String reqEndDate, String sureBeginDate,
			String sureEndDate) {

		return scAddDao.findScAddSure(placeNo, termNo, reqBeginDate, reqEndDate,
				sureBeginDate, sureEndDate);
	}

	/**
	 * @Description:TODO
	 * @param placeNo
	 * @param termNo
	 * @param reqDate
	 * @param cardNo
	 * @return List<ScAddSure> termNo、placeno都是用申请的字段？
	 */
	public List<ScAddSure> findScAddSure(String placeNo, String termNo,
			String reqDate, String cardNo) {
		return scAddDao.findScAddSure(placeNo, termNo, reqDate, cardNo);
	}

	/**
	 * 查半条，即state状态为空
	 * 
	 * @param termNo
	 * @param cardNo
	 * @return List<ScAddSure>
	 */
	public List<ScAddSure> findScAddSure(String termNo, String cardNo)
			throws IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		
		return scAddDao.findScAddSure(termNo, cardNo);

	}

	public ScAddSure findScAddSureByCardTime(String cardNo,Date timeReq){
		return scAddDao.findScAddSureByCardTime(cardNo, timeReq);
	}

	public ScAddSureSend findScAddSureSendByCardTime(String cardNo,Date timeReq){
		return scAddDao.findScAddSureSendByCardTime(cardNo, timeReq);
	}

	public ScAddSure findLastSureByCardNo(String cardNo) {
		return scAddDao.findLastSureByCardNo(cardNo);
	}

}
