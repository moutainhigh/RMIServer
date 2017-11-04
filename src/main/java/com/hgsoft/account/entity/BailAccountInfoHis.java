package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BailAccountInfoHis   implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1152271846753271859L;
	private Long id;
	private Long mainId;
	private BigDecimal bailFee;
	private BigDecimal bailFrozenBalance;
	private Long operId;
	private Long placeId;
	private Date operTime;
	private Long hisSeqId;
	private Date createDate;
	private String createReason;
	
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public BigDecimal getBailFee() {
		return bailFee;
	}
	public void setBailFee(BigDecimal bailFee) {
		this.bailFee = bailFee;
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
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
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
	public BigDecimal getBailFrozenBalance() {
		return bailFrozenBalance;
	}
	public void setBailFrozenBalance(BigDecimal bailFrozenBalance) {
		this.bailFrozenBalance = bailFrozenBalance;
	}
	
}
