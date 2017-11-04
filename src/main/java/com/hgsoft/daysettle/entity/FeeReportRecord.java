package com.hgsoft.daysettle.entity;

import java.util.Date;

public class FeeReportRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4148130751000541869L;


	private Long id;
	
	private Long daySetID;
	
	private String settleDay;
	
	private Long payee;
	
	private String payeeCode;
	
	private String payeeName;
	
	private Double cash;
	
	private Double pos;
	
	private Double transferAccount;
	
	private Double wechat;
	
	private Double alipay;
	
	private Double bill;
	
	private Long operid;
	
	private String operCode;
	
	private String operName;
	
	private Long placeid;
	
	private String placeCode;
	
	private String placeName;
	
//	private Double sysCash;
//	
//	private Double sysPos;
//	
//	private Double sysTransferAccount;
//	
//	private Double sysWechat;
//	
//	private Double sysAlipay;;
//	
//	private Date startTime;
//	
//	private Date endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDaySetID() {
		return daySetID;
	}

	public void setDaySetID(Long daySetID) {
		this.daySetID = daySetID;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public Long getPayee() {
		return payee;
	}

	public void setPayee(Long payee) {
		this.payee = payee;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
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

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Long getPlaceid() {
		return placeid;
	}

	public void setPlaceid(Long placeid) {
		this.placeid = placeid;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Double getBill() {
		return bill;
	}

	public void setBill(Double bill) {
		this.bill = bill;
	}

//	public Double getSysCash() {
//		return sysCash;
//	}
//
//	public void setSysCash(Double sysCash) {
//		this.sysCash = sysCash;
//	}
//
//	public Double getSysPos() {
//		return sysPos;
//	}
//
//	public void setSysPos(Double sysPos) {
//		this.sysPos = sysPos;
//	}
//
//	public Double getSysTransferAccount() {
//		return sysTransferAccount;
//	}
//
//	public void setSysTransferAccount(Double sysTransferAccount) {
//		this.sysTransferAccount = sysTransferAccount;
//	}
//
//	public Double getSysWechat() {
//		return sysWechat;
//	}
//
//	public void setSysWechat(Double sysWechat) {
//		this.sysWechat = sysWechat;
//	}
//
//	public Double getSysAlipay() {
//		return sysAlipay;
//	}
//
//	public void setSysAlipay(Double sysAlipay) {
//		this.sysAlipay = sysAlipay;
//	}
//
//	public Date getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(Date startTime) {
//		this.startTime = startTime;
//	}
//
//	public Date getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
//	
//	
	
	
}
