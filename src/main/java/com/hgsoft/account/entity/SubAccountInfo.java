package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SubAccountInfo   implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5608653181853814772L;
	private Long id ;
	private Long mainId ;
	private String subAccountNo;
	private String subAccountType ;
	private Long applyID ;
	private Long operId ;
	private Long placeId ;
	private Date operTime ;
	private Long hisSeqId ;
	private Long agentsMay ;
	
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	
	private String subServicePwd;
	
	private BigDecimal bailBalance;//子账户保证金余额
	private BigDecimal bailFee;//子账户保证金总额
	private BigDecimal bailFrozenBalance;
	
	public BigDecimal getBailFrozenBalance() {
		return bailFrozenBalance;
	}
	public void setBailFrozenBalance(BigDecimal bailFrozenBalance) {
		this.bailFrozenBalance = bailFrozenBalance;
	}
	public BigDecimal getBailBalance() {
		return bailBalance;
	}
	public void setBailBalance(BigDecimal bailBalance) {
		this.bailBalance = bailBalance;
	}
	public BigDecimal getBailFee() {
		return bailFee;
	}
	public void setBailFee(BigDecimal bailFee) {
		this.bailFee = bailFee;
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
	
	public String getSubAccountNo() {
		return subAccountNo;
	}
	public void setSubAccountNo(String subAccountNo) {
		this.subAccountNo = subAccountNo;
	}
	public String getSubAccountType() {
		return subAccountType;
	}
	public void setSubAccountType(String subAccountType) {
		this.subAccountType = subAccountType;
	}
	
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	
	public Long getApplyID() {
		return applyID;
	}
	public void setApplyID(Long applyID) {
		this.applyID = applyID;
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
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}
	public Long getAgentsMay() {
		return agentsMay;
	}
	public void setAgentsMay(Long agentsMay) {
		this.agentsMay = agentsMay;
	}
	public String getSubServicePwd() {
		return subServicePwd;
	}
	public void setSubServicePwd(String subServicePwd) {
		this.subServicePwd = subServicePwd;
	}
	
}
