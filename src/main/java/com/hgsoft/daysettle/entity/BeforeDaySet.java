package com.hgsoft.daysettle.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BeforeDaySet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7088173637191471595L;
	private Long id;
	private String sourceTable;
	private Long sourceId;
	private String businessType;
	private BigDecimal beforeFee;
	private BigDecimal afterFee;
	private Long operId;
	private Date operTime;
	private Long operPlaceId;
	private String memo;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long customerId;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public BigDecimal getBeforeFee() {
		return beforeFee;
	}
	public void setBeforeFee(BigDecimal beforeFee) {
		this.beforeFee = beforeFee;
	}
	public BigDecimal getAfterFee() {
		return afterFee;
	}
	public void setAfterFee(BigDecimal afterFee) {
		this.afterFee = afterFee;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getOperPlaceId() {
		return operPlaceId;
	}
	public void setOperPlaceId(Long operPlaceId) {
		this.operPlaceId = operPlaceId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	

}
