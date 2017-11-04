package com.hgsoft.daysettle.entity;

import java.io.Serializable;

public class RegisterDaySetWareDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7837022368543964665L;
	private Long id;
	private Long mainID;
	private String settleDay;
	private String productType;
	private Integer currBalanceNum;
	private Integer currRecoverNum;
	private String serviceType;
	private String memo;
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainID() {
		return mainID;
	}
	public void setMainID(Long mainID) {
		this.mainID = mainID;
	}
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Integer getCurrBalanceNum() {
		return currBalanceNum;
	}
	public void setCurrBalanceNum(Integer currBalanceNum) {
		this.currBalanceNum = currBalanceNum;
	}
	public Integer getCurrRecoverNum() {
		return currRecoverNum;
	}
	public void setCurrRecoverNum(Integer currRecoverNum) {
		this.currRecoverNum = currRecoverNum;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
}
