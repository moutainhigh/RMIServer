package com.hgsoft.daysettle.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DaySetRecordHis implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9145757793745979783L;
	private Long id;
	private String settleDay;
	private String differentialMarker;
	private Long operID;
	private String macAddress;
	private Date operTime;
	private Long operPlaceID;
	private String memo;
	private Long hisSeqID;
	private Date createDate;
	private String createReason;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private BigDecimal beforeFee;
	
	public BigDecimal getBeforeFee() {
		return beforeFee;
	}
	public void setBeforeFee(BigDecimal beforeFee) {
		this.beforeFee = beforeFee;
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
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	public String getDifferentialMarker() {
		return differentialMarker;
	}
	public void setDifferentialMarker(String differentialMarker) {
		this.differentialMarker = differentialMarker;
	}
	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
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
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateReason() {
		return createReason;
	}
	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}
	
	
}
