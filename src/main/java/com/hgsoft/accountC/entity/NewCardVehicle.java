package com.hgsoft.accountC.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class NewCardVehicle implements Serializable{

	private static final long serialVersionUID = -4305353030006173865L;
	private Long id;
	private Long newCardApplyid;
	private String vehiclePlate;
	private String vehicleColor;
	private String bailType;
	private BigDecimal bail;
	private String state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNewCardApplyid() {
		return newCardApplyid;
	}

	public void setNewCardApplyid(Long newCardApplyid) {
		this.newCardApplyid = newCardApplyid;
	}

	public String getVehiclePlate() {
		return vehiclePlate;
	}

	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getBailType() {
		return bailType;
	}

	public void setBailType(String bailType) {
		this.bailType = bailType;
	}

	public BigDecimal getBail() {
		return bail;
	}

	public void setBail(BigDecimal bail) {
		this.bail = bail;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
