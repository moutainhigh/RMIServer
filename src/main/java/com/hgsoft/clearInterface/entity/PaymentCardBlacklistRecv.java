package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentCardBlacklistRecv implements Serializable{


	private static final long serialVersionUID = -5649091524968972598L;
	private Long id;
	private BigDecimal boardlistNo;
	private String cardCode;
	private String acbAccount;
	private Date genTime;
	private Integer flag;
	private Date updateTime;
	private String remark;
	private Date receviceTime;
	private String fileName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getBoardlistNo() {
		return boardlistNo;
	}
	public void setBoardlistNo(BigDecimal boardlistNo) {
		this.boardlistNo = boardlistNo;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getAcbAccount() {
		return acbAccount;
	}
	public void setAcbAccount(String acbAccount) {
		this.acbAccount = acbAccount;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getReceviceTime() {
		return receviceTime;
	}
	public void setReceviceTime(Date receviceTime) {
		this.receviceTime = receviceTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}