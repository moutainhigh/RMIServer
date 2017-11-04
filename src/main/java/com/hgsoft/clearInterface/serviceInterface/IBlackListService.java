package com.hgsoft.clearInterface.serviceInterface;

import com.hgsoft.clearInterface.entity.BlackListRelieveTemp;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.clearInterface.entity.BlackListWarter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IBlackListService {

	public void autoImportAccountCStopPay();

	public void ImportAccountCStopPay(String netNo, String cardType, String cardNo, Date genTime, String genType,
			Integer status, String stopPayStatus, String flag);

	public Boolean saveBlackListWarter(String obuId, String license, String cardType, String cardNo, Date genTime,
			String genType, Integer status, String flag, Long operId, String operNo, String operName, Long placeId,
			String placeNo, String placeName, Date operTime, String stopPayStatus);

	public Boolean saveBlackListWarter(BlackListWarter blackListWarter);

	public void dealBlackListWarter() throws Exception;



	public Boolean saveBlackListTemp(BlackListWarter blackListWarter, List<BlackListTemp> blackListTempList) throws Exception;

	public void saveBlackListAllSend();
	
	public void saveCardLost(String cardType, String cardNo, Date genTime,
			String genType, Long operId, String operNo, String operName, Long placeId,
			String placeNo, String placeName, Date operTime);

	public void saveCardNoLost(String pREPAIDTYPE, String cardNo, Date tradetime, String string, Long operid,
			String operNo, String operName, Long placeid, String placeNo, String placeName, Date date);

	public void saveCardCancle(String cardType, String cardNo, Date genTime, String genType, Long operId, String operNo,
			String operName, Long placeId, String placeNo, String placeName, Date operTime);
	
	public void saveOBUDisassemble(String obuId,Date genTime,String genType, Long operId, String operNo,
			String operName, Long placeId, String placeNo, String placeName, Date operTime);

	void saveOBUCancel(String obuId, Date genTime, String genType, Long operId, String operNo, String operName,
			Long placeId, String placeNo, String placeName, Date operTime);

	void saveStopPayCard(String cardType, String cardNo, Date genTime, String genType, Long operId, String operNo,
			String operName, Long placeId, String placeNo, String placeName, Date operTime);
	
	public void saveStopUseCard(String cardType, String cardNo, Date genTime, String genType, Long operId, String operNo,
			String operName, Long placeId, String placeNo, String placeName, Date operTime);

	public void saveRelieveStopPayCard(String aCCOUNTCTYPE, String cardNo, Date produceTime, String string, Long operId,
			String operNo, String operName, Long placeId, String placeNo, String placeName, Date date);

	public void saveCardRelieveCancel(String aCCOUNTCTYPE, String cardNo, Date produceTime, String string, Long operId,
			String operNo, String operName, Long placeId, String placeNo, String placeName, Date date);

	public void saveRelieveStopUseCard(String cardType, String cardNo, Date genTime, String genType, Long operId,
			String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime);

	public void saveOBUStopUse(String obuSerial, Date date, String string, Long operID, String operNo, String operName,
			Long operplaceID, String placeNo, String placeName, Date date2);

	public void saveOBURelieveStopUse(String obuId, Date genTime, String genType, Long operId, String operNo, String operName,
			Long placeId, String placeNo, String placeName, Date operTime);

	public void saveCardStopUse(String cardType, String cardNo, Date genTime, String genType, Long operId, String operNo,
			String operName, Long placeId, String placeNo, String placeName, Date operTime);

	void saveOBUNoLost(String obuId, Date genTime, String genType, Long operId, String operNo, String operName,
			Long placeId, String placeNo, String placeName, Date operTime);

	void saveOBULost(String obuId, Date genTime, String genType, Long operId, String operNo, String operName,
			Long placeId, String placeNo, String placeName, Date operTime);

	void saveBlackListAdditionSend();

	public List<BlackListTemp> findBlackListByCardNo4AgentCard(String cardNo);

	public List<BlackListTemp> findBlackList(String cardNo,String status,String genMode);

	public void saveBlackListTemp(BlackListRelieveTemp blackListRelieveTemp);

	public void saveBlackListSend() throws IOException, ParseException;

}
