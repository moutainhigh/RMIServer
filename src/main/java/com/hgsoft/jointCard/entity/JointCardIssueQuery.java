package com.hgsoft.jointCard.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 联营卡发行信息，不映射到数据表
 * 
 * @author huanghaoyu
 */
public class JointCardIssueQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cardNo = null;
	private Date startTime = null;
	private Date endTime = null;
	private String vehiclePlate = null;
	private String vehicleColor = null;
	private String cardHolderName = null;
	private String idType = null;
	private String idCode = null;

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

}