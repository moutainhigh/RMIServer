package com.hgsoft.acms.obu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TagMainRecordACMS implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4514047507021291667L;
	private Long id;
	private Long tagInfoID;
	private String tagNo;
	private Long clientID;
	private Long vehicleID;
	private String maintainType;
	private String installman;
	private BigDecimal chargeCost;
	private String reason;
	private String memo;
	private String newTagNo;
	private String faultType;
	private String receivePlace;
	private String backupTagNo;
	private String contactMan;
	private String contactPhone;
	private String postcode;
	private String address;
	private String invoiceHead;
	private Date recoverTime;
	private Date sendRepairTime;
	private Date repairBackTime;
	private Date noticeCustomerTime;
	private Date backToCustomerTime;
	private Long backOperID;
	private Long operID;
	private Long issueplaceID;
	private Date issuetime;
	private Long hisSeqID;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long backTagHisId;
	
	private String obuSerial;
	private String oldObuSerial;
	private Long faultTypeId;
	private Long reasonId;
	
	public Long getBackTagHisId() {
		return backTagHisId;
	}
	public void setBackTagHisId(Long backTagHisId) {
		this.backTagHisId = backTagHisId;
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
	public Long getTagInfoID() {
		return tagInfoID;
	}
	public void setTagInfoID(Long tagInfoID) {
		this.tagInfoID = tagInfoID;
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
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public String getInstallman() {
		return installman;
	}
	public void setInstallman(String installman) {
		this.installman = installman;
	}
	public BigDecimal getChargeCost() {
		return chargeCost;
	}
	public void setChargeCost(BigDecimal chargeCost) {
		this.chargeCost = chargeCost;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getNewTagNo() {
		return newTagNo;
	}
	public void setNewTagNo(String newTagNo) {
		this.newTagNo = newTagNo;
	}
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public String getReceivePlace() {
		return receivePlace;
	}
	public void setReceivePlace(String receivePlace) {
		this.receivePlace = receivePlace;
	}
	public String getBackupTagNo() {
		return backupTagNo;
	}
	public void setBackupTagNo(String backupTagNo) {
		this.backupTagNo = backupTagNo;
	}
	public String getContactMan() {
		return contactMan;
	}
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInvoiceHead() {
		return invoiceHead;
	}
	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}
	public Date getRecoverTime() {
		return recoverTime;
	}
	public void setRecoverTime(Date recoverTime) {
		this.recoverTime = recoverTime;
	}
	public Date getSendRepairTime() {
		return sendRepairTime;
	}
	public void setSendRepairTime(Date sendRepairTime) {
		this.sendRepairTime = sendRepairTime;
	}
	public Date getRepairBackTime() {
		return repairBackTime;
	}
	public void setRepairBackTime(Date repairBackTime) {
		this.repairBackTime = repairBackTime;
	}
	public Date getNoticeCustomerTime() {
		return noticeCustomerTime;
	}
	public void setNoticeCustomerTime(Date noticeCustomerTime) {
		this.noticeCustomerTime = noticeCustomerTime;
	}
	public Date getBackToCustomerTime() {
		return backToCustomerTime;
	}
	public void setBackToCustomerTime(Date backToCustomerTime) {
		this.backToCustomerTime = backToCustomerTime;
	}
	public Long getBackOperID() {
		return backOperID;
	}
	public void setBackOperID(Long backOperID) {
		this.backOperID = backOperID;
	}
	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public Long getIssueplaceID() {
		return issueplaceID;
	}
	public void setIssueplaceID(Long issueplaceID) {
		this.issueplaceID = issueplaceID;
	}
	public Date getIssuetime() {
		return issuetime;
	}
	public void setIssuetime(Date issuetime) {
		this.issuetime = issuetime;
	}
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
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
