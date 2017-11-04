package com.hgsoft.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankTransferBussiness  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8005412945061596019L;
	private Long id             ;
	private String idType         ;
	private String idCode         ;
	private String clientName;
	private String payName        ;
	private BigDecimal transferBlanace;
	private String bankNo         ;
	private Long operId         ;
	private Long placeId        ;
	private Date operDate       ;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String rechargeType;//RechargeType
	private BigDecimal blanace;//缴款后余额
	private BigDecimal RechargeCost;
	private Long bankTransferId;
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getBankTransferId() {
		return bankTransferId;
	}
	public void setBankTransferId(Long bankTransferId) {
		this.bankTransferId = bankTransferId;
	}
	public BigDecimal getRechargeCost() {
		return RechargeCost;
	}
	public void setRechargeCost(BigDecimal rechargeCost) {
		RechargeCost = rechargeCost;
	}
	public String getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	
	public BigDecimal getBlanace() {
		return blanace;
	}
	public void setBlanace(BigDecimal blanace) {
		this.blanace = blanace;
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
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public BigDecimal getTransferBlanace() {
		return transferBlanace;
	}
	public void setTransferBlanace(BigDecimal transferBlanace) {
		this.transferBlanace = transferBlanace;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	
	
}
