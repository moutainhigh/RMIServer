package com.hgsoft.macao.entity;

import java.util.Date;

public class MacaoCardSection implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9201481227345735418L;

	
	private Long id;
	private String startCode;
	private String endCode;
	private String bankNo;
	private String bankName;
	private String compoundFlag;
	private String remark;
	private String cardType;
	private Long operateId;
	private String operateName;
	private Date operateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartCode() {
		return startCode;
	}
	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}
	public String getEndCode() {
		return endCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCompoundFlag() {
		return compoundFlag;
	}
	public void setCompoundFlag(String compoundFlag) {
		this.compoundFlag = compoundFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Long getOperateId() {
		return operateId;
	}
	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	
	
}
