package com.hgsoft.acms.obu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TagInfoACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5976724608338606246L;
	private Long id;
	private String tagNo;
	private Long clientID;
	private String issueType;
	private String salesType;
	private Long installman;
	private BigDecimal chargeCost;
	private Long operID;
	private Long issueplaceID;
	private Date issuetime;
	private Date maintenanceTime;
	private Date startTime;
	private Date endTime;
	private String tagState;
	private Long hisSeqID;
	private Long correctOperID;
	private Date correctTime;
	private Long correctPlaceID;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String correctOperNo;
	private String correctOperName;
	private String correctPlaceNo;
	private String correctPlaceName;
	
	private String writeBackFlag;
	
	private String  productType;
	private BigDecimal cost;
	private String  installmanName;
	private String blackFlag;
	
	private String obuSerial;
	private String isWriteObu;
	
	public String getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(String blackFlag) {
		this.blackFlag = blackFlag;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getInstallmanName() {
		return installmanName;
	}
	public void setInstallmanName(String installmanName) {
		this.installmanName = installmanName;
	}
	public String getWriteBackFlag() {
		return writeBackFlag;
	}
	public void setWriteBackFlag(String writeBackFlag) {
		this.writeBackFlag = writeBackFlag;
	}
	public String getCorrectOperNo() {
		return correctOperNo;
	}
	public void setCorrectOperNo(String correctOperNo) {
		this.correctOperNo = correctOperNo;
	}
	public String getCorrectOperName() {
		return correctOperName;
	}
	public void setCorrectOperName(String correctOperName) {
		this.correctOperName = correctOperName;
	}
	public String getCorrectPlaceNo() {
		return correctPlaceNo;
	}
	public void setCorrectPlaceNo(String correctPlaceNo) {
		this.correctPlaceNo = correctPlaceNo;
	}
	public String getCorrectPlaceName() {
		return correctPlaceName;
	}
	public void setCorrectPlaceName(String correctPlaceName) {
		this.correctPlaceName = correctPlaceName;
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
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public Long getInstallman() {
		return installman;
	}
	public void setInstallman(Long installman) {
		this.installman = installman;
	}
	public BigDecimal getChargeCost() {
		return chargeCost;
	}
	public void setChargeCost(BigDecimal chargeCost) {
		this.chargeCost = chargeCost;
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
	public Date getMaintenanceTime() {
		return maintenanceTime;
	}
	public void setMaintenanceTime(Date maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTagState() {
		return tagState;
	}
	public void setTagState(String tagState) {
		this.tagState = tagState;
	}
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}
	public Long getCorrectOperID() {
		return correctOperID;
	}
	public void setCorrectOperID(Long correctOperID) {
		this.correctOperID = correctOperID;
	}
	public Date getCorrectTime() {
		return correctTime;
	}
	public void setCorrectTime(Date correctTime) {
		this.correctTime = correctTime;
	}
	public Long getCorrectPlaceID() {
		return correctPlaceID;
	}
	public void setCorrectPlaceID(Long correctPlaceID) {
		this.correctPlaceID = correctPlaceID;
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
	public String getIsWriteObu() {
		return isWriteObu;
	}
	public void setIsWriteObu(String isWriteObu) {
		this.isWriteObu = isWriteObu;
	}
	
}
