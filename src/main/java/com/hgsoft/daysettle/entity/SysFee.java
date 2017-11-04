package com.hgsoft.daysettle.entity;

import java.util.Date;

public class SysFee implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5828746304697980334L;

	private Long id;
	
	private Date startTime;
	
	private Date endTime;
	
	private Long daySetId;
	
	private Double cash;
	
	private Double pos;
	
	private Double transferAccount;
	
	private Double wechat;
	
	private Double alipay;
	
	private Double bill;
	
	private Date createTime;
	
	private Long placeId;
	
	private String settleDay;
	
	private String placeNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDaySetId() {
		return daySetId;
	}

	public void setDaySetId(Long daySetId) {
		this.daySetId = daySetId;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getPos() {
		return pos;
	}

	public void setPos(Double pos) {
		this.pos = pos;
	}

	public Double getTransferAccount() {
		return transferAccount;
	}

	public void setTransferAccount(Double transferAccount) {
		this.transferAccount = transferAccount;
	}

	public Double getWechat() {
		return wechat;
	}

	public void setWechat(Double wechat) {
		this.wechat = wechat;
	}

	public Double getAlipay() {
		return alipay;
	}

	public void setAlipay(Double alipay) {
		this.alipay = alipay;
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

	public String getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public Double getBill() {
		return bill;
	}

	public void setBill(Double bill) {
		this.bill = bill;
	}
	
	
}
