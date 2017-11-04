package com.hgsoft.daysettle.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AfterDaySetFee implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6684215989988685503L;
	private Long id;
	private Long daySetID;
	private Long daySetDId;
	private String feeType;
	private BigDecimal differenceFee;
	private Long operID;
	private Date operTime;
	private Long operPlaceID;
	private String memo;
	private Long hisSeqID;
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
	
	public Long getDaySetID() {
		return daySetID;
	}
	public void setDaySetID(Long daySetID) {
		this.daySetID = daySetID;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public BigDecimal getDifferenceFee() {
		return differenceFee;
	}
	public void setDifferenceFee(BigDecimal differenceFee) {
		this.differenceFee = differenceFee;
	}
	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getOperPlaceID() {
		return operPlaceID;
	}
	public void setOperPlaceID(Long operPlaceID) {
		this.operPlaceID = operPlaceID;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getDaySetDId() {
		return daySetDId;
	}
	public void setDaySetDId(Long daySetDId) {
		this.daySetDId = daySetDId;
	}
	
	
}
