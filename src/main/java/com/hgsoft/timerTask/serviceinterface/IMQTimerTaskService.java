package com.hgsoft.timerTask.serviceinterface;

public interface IMQTimerTaskService {

	public void saveLoss();
	public void saveUnLoss();
	public void saveIssueAgain();
	public void saveStopCard();
	public void saveRelieveStopCard();
	public void saveVehicleChange();
	public void saveServerChange();
	public void saveTagIssueAgain();
	public void saveTagReplace();
	public void saveMigrate();
	public void saveCancelCard();
}
