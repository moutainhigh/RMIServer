package com.hgsoft.customer.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CarObuCardInfo implements Serializable {

	private Long prepaidCID;
	private Long accountCID;
	private Long vehicleID;
	private Long tagID;
	
	public CarObuCardInfo() {
		super();
	}

	public Long getPrepaidCID() {
		return prepaidCID;
	}

	public void setPrepaidCID(Long prepaidCID) {
		this.prepaidCID = prepaidCID;
	}

	public Long getAccountCID() {
		return accountCID;
	}

	public void setAccountCID(Long accountCID) {
		this.accountCID = accountCID;
	}

	public Long getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Long vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	
	
	
	
	
}
