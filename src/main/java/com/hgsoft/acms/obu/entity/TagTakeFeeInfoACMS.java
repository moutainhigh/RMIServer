package com.hgsoft.acms.obu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TagTakeFeeInfoACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6018629396145560011L;
	private Long id;
	private String clientName;
	private String certType;
	private String certNumber;
	private BigDecimal chargeFee;
	private String chargeType;
	private String payAccount;
	private BigDecimal takeBalance;
	private Long registerOperID;
	private Date registerDate;
	private Long registerPlace;
	private Long modifyOperID;
	private Date modifyDate;
	private String memo;
	private Long his_SeqID;
	private String isDaySet;
	private String settleDay;
	private Date settletTime;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String modifyOperName;
	private String modifyOperNo;
	private Long receiptId;
	private Long bankTransferInfoId;//如果收费方式为转账、则要记录该id
	private String voucherNo;//缴款单号
	
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public Long getBankTransferInfoId() {
		return bankTransferInfoId;
	}
	public void setBankTransferInfoId(Long bankTransferInfoId) {
		this.bankTransferInfoId = bankTransferInfoId;
	}
	public Long getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}
	public String getModifyOperName() {
		return modifyOperName;
	}
	public void setModifyOperName(String modifyOperName) {
		this.modifyOperName = modifyOperName;
	}
	public String getModifyOperNo() {
		return modifyOperNo;
	}
	public void setModifyOperNo(String modifyOperNo) {
		this.modifyOperNo = modifyOperNo;
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNumber() {
		return certNumber;
	}
	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}
	public BigDecimal getChargeFee() {
		return chargeFee;
	}
	public void setChargeFee(BigDecimal chargeFee) {
		this.chargeFee = chargeFee;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	public BigDecimal getTakeBalance() {
		return takeBalance;
	}
	public void setTakeBalance(BigDecimal takeBalance) {
		this.takeBalance = takeBalance;
	}
	
	
	public Long getRegisterOperID() {
		return registerOperID;
	}
	public void setRegisterOperID(Long registerOperID) {
		this.registerOperID = registerOperID;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Long getModifyOperID() {
		return modifyOperID;
	}
	public void setModifyOperID(Long modifyOperID) {
		this.modifyOperID = modifyOperID;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getHis_SeqID() {
		return his_SeqID;
	}
	public void setHis_SeqID(Long his_SeqID) {
		this.his_SeqID = his_SeqID;
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
	public Long getRegisterPlace() {
		return registerPlace;
	}
	public void setRegisterPlace(Long registerPlace) {
		this.registerPlace = registerPlace;
	}
	
	
}
