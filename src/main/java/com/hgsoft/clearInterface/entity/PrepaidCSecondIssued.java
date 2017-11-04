package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.Date;

public class PrepaidCSecondIssued implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9160925937549227393L;
	private Long id;
	private String cardCode;
	private String cardType;
	private Date sdate;
	private Integer status;
	private Date updatetime;
	private Long boardListNo;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Long getBoardListNo() {
		return boardListNo;
	}
	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}
	
	

}
