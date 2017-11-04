package com.hgsoft.macao.entity;

import java.util.Date;

/**
 * 通讯系统黑名单
 * @author lingengmin
 *
 */
public class MacaoCardBlackList implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long boardListNo;
	private String cardCode;
	private String genCau;
	private Date genTime;
	private String fileName;
	private Date insertTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBoardListNo() {
		return boardListNo;
	}
	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getGenCau() {
		return genCau;
	}
	public void setGenCau(String genCau) {
		this.genCau = genCau;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
