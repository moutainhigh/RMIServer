package com.hgsoft.timerTask.serviceinterface;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public interface ITimedTaskService extends Serializable {
	public void helloWorld();
	public void prepaidCStopCard();
	public void addDarkListFromEtcTolling();
	public void saveReturnFeeFromClear();
	
	public void updateTagInfoBlackFlag();
	public void updateInvoiceChangeFlow();
	public void setTime(String month,Date realDate);
	
	public void updateAccountCMigrate();
	
	public void saveBlackListAll();
	
	public void saveDealBlackListWater() throws Exception;
	
	public void saveBlackListSend() throws IOException, ParseException;
	
	public void savePrepaidCBalance();
	
	public void saveStopCard();
}
