package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class BlackListWarter implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -241007919310398799L;

	private Long id;
	
	private String netNo;
	
	private String obuId;
	
	private String license;
	
	private String cardType;
	
	private String cardNo;
	
	private Date genTime;
	
	private String genType;
	
	private Integer status;
	
	private String flag;
	
	private Long operId;
	
	private String operNo;
	
	private String operName;
	
	private Long placeId;
	
	private String placeNo;
	
	private String placeName;
	
	private Date operTime;
	
	private String stopPayStatus;
	
	public BlackListWarter(){
		
	}
	
	public BlackListWarter(String netNo,String cardType,String cardNo,Date genTime,String genType,Integer status
			,String stopPayStatus,String flag){
		this.netNo = netNo;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.genTime = genTime;
		this.genType = genType;
		this.status = status;
		this.stopPayStatus = stopPayStatus;
		this.flag = flag;
	}

	public BlackListWarter( String obuId, String license, String cardType, String cardNo, Date genTime, String genType, Integer status, String flag,
			 Long operId, String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime, String stopPayStatus){
		this.obuId = obuId;
		
		this.license = license;
		
		this.cardType = cardType;
		
		this.cardNo = cardNo;
		
		this.genTime = genTime;
		
		this.genType = genType;
		
		this.status = status;
		
		this.flag = flag;
		
		this.operId = operId;
		
		this.operNo = operNo;
		
		this.operName = operName;
		
		this.placeId = placeId;
		
		this.placeNo = placeNo;
		
		this.placeName = placeName;
		
		this.operTime = operTime;
		
		this.stopPayStatus = stopPayStatus;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNetNo() {
		return netNo;
	}

	public void setNetNo(String netNo) {
		this.netNo = netNo;
	}

	public String getObuId() {
		return obuId;
	}

	public void setObuId(String obuId) {
		this.obuId = obuId;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getGenType() {
		return genType;
	}

	public void setGenType(String genType) {
		this.genType = genType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
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

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
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

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getStopPayStatus() {
		return stopPayStatus;
	}

	public void setStopPayStatus(String stopPayStatus) {
		this.stopPayStatus = stopPayStatus;
	}

	
	
}
