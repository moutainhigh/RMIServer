package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.Date;

public class AccCardTransferResultRecv implements Serializable{

	private static final long serialVersionUID = 7158730182883441728L;
	private Long id;
	private String cardCode;
	private String oldAccountId;
	private Long oldBankNo;
	private String newAccountId;
	private Long newBankNo;
	private Long flag;
	private Date reqTime;
	private Date effectTime;
	private Long result;
	private Long boardListNo;
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
	public String getOldAccountId() {
		return oldAccountId;
	}
	public void setOldAccountId(String oldAccountId) {
		this.oldAccountId = oldAccountId;
	}
	public Long getOldBankNo() {
		return oldBankNo;
	}
	public void setOldBankNo(Long oldBankNo) {
		this.oldBankNo = oldBankNo;
	}
	public String getNewAccountId() {
		return newAccountId;
	}
	public void setNewAccountId(String newAccountId) {
		this.newAccountId = newAccountId;
	}
	public Long getNewBankNo() {
		return newBankNo;
	}
	public void setNewBankNo(Long newBankNo) {
		this.newBankNo = newBankNo;
	}
	public Long getFlag() {
		return flag;
	}
	public void setFlag(Long flag) {
		this.flag = flag;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
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