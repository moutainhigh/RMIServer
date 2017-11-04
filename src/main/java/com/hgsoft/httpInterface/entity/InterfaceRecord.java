package com.hgsoft.httpInterface.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * AddReg entity. @author MyEclipse Persistence Tools
 */

public class InterfaceRecord implements java.io.Serializable {

	private static final long serialVersionUID = 8112098159295917267L;
	private Long id;
	private String cardno;
	private String omsState;
	private String csmsState;
	private String type;
	private Date operTime;
	private String startCode;
	private String endCode;
	private BigDecimal realPrice;
	private String serType;
	
	private Date startDate;
	private Date endDate;
	private String obuSerial;
	
	
	public String getObuSerial() {
		return obuSerial;
	}

	public void setObuSerial(String obuSerial) {
		this.obuSerial = obuSerial;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public InterfaceRecord() {
	}

	public InterfaceRecord(Long id, String cardno, String omsState,
			String csmsState, String type, Date operTime) {
		super();
		this.id = id;
		this.cardno = cardno;
		this.omsState = omsState;
		this.csmsState = csmsState;
		this.type = type;
		this.operTime = operTime;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getOmsState() {
		return omsState;
	}

	public void setOmsState(String omsState) {
		this.omsState = omsState;
	}

	public String getCsmsState() {
		return csmsState;
	}

	public void setCsmsState(String csmsState) {
		this.csmsState = csmsState;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getStartCode() {
		return startCode;
	}

	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

}