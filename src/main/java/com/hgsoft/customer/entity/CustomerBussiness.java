package com.hgsoft.customer.entity;
import java.util.*;
import java.math.BigDecimal;


import java.io.Serializable;


public class CustomerBussiness  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7220399619213507795L;
	private String memo;
	private Date createTime;
	private Long placeId;
	private Long operId;
	private String type;
	private String seritem;
	private String cardBankNo;
	private Long cardAccountId;
	private String cardType;
	private String isdefault;
	private String invoicetitle;
	private Long billId;
	private Long invoiceId;
	private Long migratecustomerId;
	private Long vehicleId;
	private Long oldcustomerId;
	private Long customerId;
	private Long id;
	private String operNo;
	private Long invoiceHisId;
	private Long billHisId;
	private Long vehicleHisId;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSeritem() {
		return seritem;
	}
	public void setSeritem(String seritem) {
		this.seritem = seritem;
	}
	public String getCardBankNo() {
		return cardBankNo;
	}
	public void setCardBankNo(String cardBankNo) {
		this.cardBankNo = cardBankNo;
	}
	public Long getCardAccountId() {
		return cardAccountId;
	}
	public void setCardAccountId(Long cardAccountId) {
		this.cardAccountId = cardAccountId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public String getInvoicetitle() {
		return invoicetitle;
	}
	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Long getMigratecustomerId() {
		return migratecustomerId;
	}
	public void setMigratecustomerId(Long migratecustomerId) {
		this.migratecustomerId = migratecustomerId;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Long getOldcustomerId() {
		return oldcustomerId;
	}
	public void setOldcustomerId(Long oldcustomerId) {
		this.oldcustomerId = oldcustomerId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getInvoiceHisId() {
		return invoiceHisId;
	}
	public void setInvoiceHisId(Long invoiceHisId) {
		this.invoiceHisId = invoiceHisId;
	}
	public Long getBillHisId() {
		return billHisId;
	}
	public void setBillHisId(Long billHisId) {
		this.billHisId = billHisId;
	}
	public Long getVehicleHisId() {
		return vehicleHisId;
	}
	public void setVehicleHisId(Long vehicleHisId) {
		this.vehicleHisId = vehicleHisId;
	}
	
}

