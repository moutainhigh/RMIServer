package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * AddReg entity. @author MyEclipse Persistence Tools
 */

public class AddRegDetailHis implements java.io.Serializable {

	private static final long serialVersionUID = 947470517343110140L;
	private Long id;
	private Long addRegID;
	private String cardNo;
	private BigDecimal fee;
	private String flag;
	private Date addTime;
	private Long addPlaceID;
	private Long addOperID;
	private Long bussinessID;
	private String addStyle;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long operId;
	private Long placeId;
	private Date operTime;
	
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
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

	public Long getBussinessID() {
		return bussinessID;
	}

	public void setBussinessID(Long bussinessID) {
		this.bussinessID = bussinessID;
	}
	// Constructors

	/** default constructor */
	public AddRegDetailHis() {
	}

	/** full constructor */
	public AddRegDetailHis(AddRegDetail addRegDetail) {

		this.bussinessID=addRegDetail.getBussinessID();
		
		this.operNo=addRegDetail.getOperNo();
		this.operName=addRegDetail.getOperName();
		this.placeNo=addRegDetail.getPlaceNo();
		this.placeName=addRegDetail.getPlaceName();
		this.addRegID = addRegDetail.getAddRegID();
		this.cardNo = addRegDetail.getCardNo();
		this.fee = addRegDetail.getFee();
		this.flag = addRegDetail.getFlag();
		this.addTime = addRegDetail.getAddTime();
		this.addPlaceID = addRegDetail.getAddPlaceID();
		this.addOperID = addRegDetail.getAddOperID();
		this.addStyle = addRegDetail.getAddStyle();
	}
	
	// Property accessors
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAddRegID() {
		return addRegID;
	}

	public void setAddRegID(Long addRegID) {
		this.addRegID = addRegID;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Long getAddPlaceID() {
		return addPlaceID;
	}

	public void setAddPlaceID(Long addPlaceID) {
		this.addPlaceID = addPlaceID;
	}

	public Long getAddOperID() {
		return addOperID;
	}

	public void setAddOperID(Long addOperID) {
		this.addOperID = addOperID;
	}
	public String getAddStyle() {
		return addStyle;
	}
	public void setAddStyle(String addStyle) {
		this.addStyle = addStyle;
	}
	
}