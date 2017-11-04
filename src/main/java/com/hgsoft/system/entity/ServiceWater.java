package com.hgsoft.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceWater implements Serializable{

	private static final long serialVersionUID = 5340143238855315288L;
	
	private Long id;
	private Long customerId;
	private String userNo;
	private String userName;
	private String cardNo;
	private String newCardNo;
	private String obuSerial;
	private String serType;
	private String bankAccount;
	private BigDecimal amt;
	private BigDecimal aulAmt;
	private String saleWate;
	private String flowState;
	private String bankNo;
	private Long customerBussinessId;
	private Long accountBussinessId;
	private Long prepaidCBussinessId;
	private Long accountCBussinessId;
	private Long tagInfoBussinessId;
	private Long invoiceId;
	private Long daySettleId;
	private Long receiptPrintId;
	private Long operId;
	private Long placeId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Date operTime;
	private String remark;
	
	private Long macaoCardCustomerId;
	private Long vehicleBussinessId;
	
	private Long macaoBankAccountId;
	
	
	
	public ServiceWater() {
		super();
	}

	public ServiceWater(Long id, Long customerId, String userNo,
			String userName, String cardNo, String newCardNo, String obuSerial,
			String serType, String bankAccount, BigDecimal amt,
			BigDecimal aulAmt, String saleWate, String flowState,
			String bankNo, Long customerBussinessId, Long accountBussinessId,
			Long prepaidCBussinessId, Long accountCBussinessId,
			Long tagInfoBussinessId, Long invoiceId, Long daySettleId,
			Long receiptPrintId, Long operId, Long placeId, String operNo,
			String operName, String placeNo, String placeName, Date operTime,
			String remark,Long macaoCardCustomerId) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.userNo = userNo;
		this.userName = userName;
		this.cardNo = cardNo;
		this.newCardNo = newCardNo;
		this.obuSerial = obuSerial;
		this.serType = serType;
		this.bankAccount = bankAccount;
		this.amt = amt;
		this.aulAmt = aulAmt;
		this.saleWate = saleWate;
		this.flowState = flowState;
		this.bankNo = bankNo;
		this.customerBussinessId = customerBussinessId;
		this.accountBussinessId = accountBussinessId;
		this.prepaidCBussinessId = prepaidCBussinessId;
		this.accountCBussinessId = accountCBussinessId;
		this.tagInfoBussinessId = tagInfoBussinessId;
		this.invoiceId = invoiceId;
		this.daySettleId = daySettleId;
		this.receiptPrintId = receiptPrintId;
		this.operId = operId;
		this.placeId = placeId;
		this.operNo = operNo;
		this.operName = operName;
		this.placeNo = placeNo;
		this.placeName = placeName;
		this.operTime = operTime;
		this.remark = remark;
		this.macaoCardCustomerId = macaoCardCustomerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getNewCardNo() {
		return newCardNo;
	}

	public void setNewCardNo(String newCardNo) {
		this.newCardNo = newCardNo;
	}

	public String getObuSerial() {
		return obuSerial;
	}

	public void setObuSerial(String obuSerial) {
		this.obuSerial = obuSerial;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getAulAmt() {
		return aulAmt;
	}

	public void setAulAmt(BigDecimal aulAmt) {
		this.aulAmt = aulAmt;
	}

	public String getSaleWate() {
		return saleWate;
	}

	public void setSaleWate(String saleWate) {
		this.saleWate = saleWate;
	}

	public String getFlowState() {
		return flowState;
	}

	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public Long getCustomerBussinessId() {
		return customerBussinessId;
	}

	public void setCustomerBussinessId(Long customerBussinessId) {
		this.customerBussinessId = customerBussinessId;
	}

	public Long getAccountBussinessId() {
		return accountBussinessId;
	}

	public void setAccountBussinessId(Long accountBussinessId) {
		this.accountBussinessId = accountBussinessId;
	}

	public Long getPrepaidCBussinessId() {
		return prepaidCBussinessId;
	}

	public void setPrepaidCBussinessId(Long prepaidCBussinessId) {
		this.prepaidCBussinessId = prepaidCBussinessId;
	}

	public Long getAccountCBussinessId() {
		return accountCBussinessId;
	}

	public void setAccountCBussinessId(Long accountCBussinessId) {
		this.accountCBussinessId = accountCBussinessId;
	}

	public Long getTagInfoBussinessId() {
		return tagInfoBussinessId;
	}

	public void setTagInfoBussinessId(Long tagInfoBussinessId) {
		this.tagInfoBussinessId = tagInfoBussinessId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getDaySettleId() {
		return daySettleId;
	}

	public void setDaySettleId(Long daySettleId) {
		this.daySettleId = daySettleId;
	}

	public Long getReceiptPrintId() {
		return receiptPrintId;
	}

	public void setReceiptPrintId(Long receiptPrintId) {
		this.receiptPrintId = receiptPrintId;
	}

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

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getMacaoCardCustomerId() {
		return macaoCardCustomerId;
	}

	public void setMacaoCardCustomerId(Long macaoCardCustomerId) {
		this.macaoCardCustomerId = macaoCardCustomerId;
	}

	public Long getVehicleBussinessId() {
		return vehicleBussinessId;
	}

	public void setVehicleBussinessId(Long vehicleBussinessId) {
		this.vehicleBussinessId = vehicleBussinessId;
	}

	public Long getMacaoBankAccountId() {
		return macaoBankAccountId;
	}

	public void setMacaoBankAccountId(Long macaoBankAccountId) {
		this.macaoBankAccountId = macaoBankAccountId;
	}
	
	
}
