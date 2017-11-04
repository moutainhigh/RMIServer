package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;


public class AddRegHis implements java.io.Serializable {

	private static final long serialVersionUID = 947470517343110140L;
	private Long id;
	private Long userid;
	private BigDecimal totalFee;
	private Long register;
	private Date registrationTime;
	private Long registrationPlaceId;
	private String addStyle;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	// Constructors

	/** default constructor */
	public AddRegHis() {
	}

	/** full constructor */
	public AddRegHis(AddReg addReg) {
		this.userid=addReg.getUserid();
		this.totalFee=addReg.getTotalFee();
		this.register=addReg.getRegister();
		this.registrationTime=addReg.getRegistrationTime();
		this.registrationPlaceId=addReg.getRegistrationPlaceId();
		this.operNo=addReg.getOperNo();
		this.operName = addReg.getOperName();
		this.placeNo=addReg.getPlaceNo();
		this.placeName=addReg.getPlaceName();
		this.addStyle=addReg.getAddStyle();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public Long getRegister() {
		return register;
	}

	public void setRegister(Long register) {
		this.register = register;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Long getRegistrationPlaceId() {
		return registrationPlaceId;
	}

	public void setRegistrationPlaceId(Long registrationPlaceId) {
		this.registrationPlaceId = registrationPlaceId;
	}
	public String getAddStyle() {
		return addStyle;
	}
	public void setAddStyle(String addStyle) {
		this.addStyle = addStyle;
	}
	
}