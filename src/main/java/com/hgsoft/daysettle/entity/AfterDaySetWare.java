package com.hgsoft.daysettle.entity;

import java.util.Date;

public class AfterDaySetWare implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8351310227874204765L;
	private Long id;
	private Long datSetWareID;
	private Long daySetWareDId;
	private String productType;
	private Integer balanceDiffNum;
	private Integer recoverDiffNum;
	private Long reportOperID;
	private Date reportTime;
	private Long reportPlaceID;
	private Long hisSeqID;
	private String serviceType;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getDatSetWareID() {
		return datSetWareID;
	}
	public void setDatSetWareID(Long datSetWareID) {
		this.datSetWareID = datSetWareID;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public Integer getBalanceDiffNum() {
		return balanceDiffNum;
	}
	public void setBalanceDiffNum(Integer balanceDiffNum) {
		this.balanceDiffNum = balanceDiffNum;
	}
	public Integer getRecoverDiffNum() {
		return recoverDiffNum;
	}
	public void setRecoverDiffNum(Integer recoverDiffNum) {
		this.recoverDiffNum = recoverDiffNum;
	}
	public Long getReportOperID() {
		return reportOperID;
	}
	public void setReportOperID(Long reportOperID) {
		this.reportOperID = reportOperID;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public Long getReportPlaceID() {
		return reportPlaceID;
	}
	public void setReportPlaceID(Long reportPlaceID) {
		this.reportPlaceID = reportPlaceID;
	}
	public Long getDaySetWareDId() {
		return daySetWareDId;
	}
	public void setDaySetWareDId(Long daySetWareDId) {
		this.daySetWareDId = daySetWareDId;
	}
	
	
}
