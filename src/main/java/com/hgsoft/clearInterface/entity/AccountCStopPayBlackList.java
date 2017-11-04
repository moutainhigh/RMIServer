package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class AccountCStopPayBlackList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7043152528606773160L;

	private Long id;
	
	private Long  boardListNo;
	
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

	public Long getBoardListNo() {
		return boardListNo;
	}

	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
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
