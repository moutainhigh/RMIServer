package com.hgsoft.acms.other.entity;

import java.math.BigDecimal;

public class ReceiptPrintDetailACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long MainId;
	private Integer parentTypeCode;
	private Integer typeCode;
	private Long businessId;
	private String cardTagNo;
	private String oldCardTagNo;
	private BigDecimal realPrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainId() {
		return MainId;
	}
	public void setMainId(Long mainId) {
		MainId = mainId;
	}
	public Integer getParentTypeCode() {
		return parentTypeCode;
	}
	public void setParentTypeCode(Integer parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}
	public Integer getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getCardTagNo() {
		return cardTagNo;
	}
	public void setCardTagNo(String cardTagNo) {
		this.cardTagNo = cardTagNo;
	}
	public String getOldCardTagNo() {
		return oldCardTagNo;
	}
	public void setOldCardTagNo(String oldCardTagNo) {
		this.oldCardTagNo = oldCardTagNo;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	

}
