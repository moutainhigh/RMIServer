package com.hgsoft.acms.obu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TagBusinessRecordACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8291639455995032645L;
	private Long id;
	private String tagNo;
	private Long clientID;
	private Long vehicleID;
	private Long operID;
	private Date operTime;
	private Long operplaceID;
	private String businessType;
	private Long installmanID;
	private String currentTagState;
	private Long receiptPrintTimes;
	private String importCardNo;
	private String writeState;
	private String memo;
	private Long fromID;
	private BigDecimal realPrice;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long bussinessid;
	
	private String oldTagNo;
	private Date oldTagIssueTime;
	
	private String obuSerial;
	private String oldObuSerial;
	
	private String installmanName;
	private String installmanNo;
	private Long reviewManId;
	private String reviewManName;
	private String reviewManNo;
	private String managerNo;
	private String managerName;
	private String faultType;
	private String reason;
	private Long faultTypeId;
	private Long reasonId;
	
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getInstallmanName() {
		return installmanName;
	}
	public void setInstallmanName(String installmanName) {
		this.installmanName = installmanName;
	}
	public String getInstallmanNo() {
		return installmanNo;
	}
	public void setInstallmanNo(String installmanNo) {
		this.installmanNo = installmanNo;
	}
	public Long getReviewManId() {
		return reviewManId;
	}
	public void setReviewManId(Long reviewManId) {
		this.reviewManId = reviewManId;
	}
	public String getReviewManName() {
		return reviewManName;
	}
	public void setReviewManName(String reviewManName) {
		this.reviewManName = reviewManName;
	}
	public String getReviewManNo() {
		return reviewManNo;
	}
	public void setReviewManNo(String reviewManNo) {
		this.reviewManNo = reviewManNo;
	}
	public String getManagerNo() {
		return managerNo;
	}
	public void setManagerNo(String managerNo) {
		this.managerNo = managerNo;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getBussinessid() {
		return bussinessid;
	}
	public void setBussinessid(Long bussinessid) {
		this.bussinessid = bussinessid;
	}
	public Long getReceiptPrintTimes() {
		return receiptPrintTimes;
	}
	public void setReceiptPrintTimes(Long receiptPrintTimes) {
		this.receiptPrintTimes = receiptPrintTimes;
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
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public Long getClientID() {
		return clientID;
	}
	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}
	public Long getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Long vehicleID) {
		this.vehicleID = vehicleID;
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
	public Long getOperplaceID() {
		return operplaceID;
	}
	public void setOperplaceID(Long operplaceID) {
		this.operplaceID = operplaceID;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Long getInstallmanID() {
		return installmanID;
	}
	public void setInstallmanID(Long installmanID) {
		this.installmanID = installmanID;
	}
	public String getCurrentTagState() {
		return currentTagState;
	}
	public void setCurrentTagState(String currentTagState) {
		this.currentTagState = currentTagState;
	}
	public String getImportCardNo() {
		return importCardNo;
	}
	public void setImportCardNo(String importCardNo) {
		this.importCardNo = importCardNo;
	}
	public String getWriteState() {
		return writeState;
	}
	public void setWriteState(String writeState) {
		this.writeState = writeState;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getFromID() {
		return fromID;
	}
	public void setFromID(Long fromID) {
		this.fromID = fromID;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	public String getIsDaySet() {
		return isDaySet;
	}
	public void setIsDaySet(String isDaySet) {
		this.isDaySet = isDaySet;
	}
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	public Date getSettletTime() {
		return settletTime;
	}
	public void setSettletTime(Date settletTime) {
		this.settletTime = settletTime;
	}
	public String getOldTagNo() {
		return oldTagNo;
	}
	public void setOldTagNo(String oldTagNo) {
		this.oldTagNo = oldTagNo;
	}
	public Date getOldTagIssueTime() {
		return oldTagIssueTime;
	}
	public void setOldTagIssueTime(Date oldTagIssueTime) {
		this.oldTagIssueTime = oldTagIssueTime;
	}
	public String getObuSerial() {
		return obuSerial;
	}
	public void setObuSerial(String obuSerial) {
		this.obuSerial = obuSerial;
	}
	public String getOldObuSerial() {
		return oldObuSerial;
	}
	public void setOldObuSerial(String oldObuSerial) {
		this.oldObuSerial = oldObuSerial;
	}
	public Long getFaultTypeId() {
		return faultTypeId;
	}
	public void setFaultTypeId(Long faultTypeId) {
		this.faultTypeId = faultTypeId;
	}
	public Long getReasonId() {
		return reasonId;
	}
	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}
	
}
