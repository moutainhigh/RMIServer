package com.hgsoft.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 通行费账单打印表
 * @author lzl
 *
 */
public class PassBill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1453759947439636532L;
	private Long ID;
	private String cardNo;
	private String userNo;
	private String cardType;
	private String bankNo;
	private Date passMonth;
	private Integer passNum;
	private BigDecimal passFee;
	private Integer otherNum;
	private BigDecimal otherFee;
	private Integer printNum;
	private Integer onceNum;
	private BigDecimal onceFee;
	private String state;
	private BigDecimal mendFee;
	private Integer mendNum;
	private Integer officeDealNum;
	private BigDecimal officeDealFee;
	private BigDecimal manageFee;
	private BigDecimal serviceFee;
	private BigDecimal lateFee;
	private BigDecimal backFee;
	/**
	 * 对应清算账单表的ID
	 */
	private String cardNos;
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getPassMonth() {
		return passMonth;
	}
	public void setPassMonth(Date passMonth) {
		this.passMonth = passMonth;
	}
	public Integer getPassNum() {
		return passNum;
	}
	public void setPassNum(Integer passNum) {
		this.passNum = passNum;
	}
	public BigDecimal getPassFee() {
		return passFee;
	}
	public void setPassFee(BigDecimal passFee) {
		this.passFee = passFee;
	}
	public Integer getOtherNum() {
		return otherNum;
	}
	public void setOtherNum(Integer otherNum) {
		this.otherNum = otherNum;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public Integer getPrintNum() {
		return printNum;
	}
	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}
	public Integer getOnceNum() {
		return onceNum;
	}
	public void setOnceNum(Integer onceNum) {
		this.onceNum = onceNum;
	}
	public BigDecimal getOnceFee() {
		return onceFee;
	}
	public void setOnceFee(BigDecimal onceFee) {
		this.onceFee = onceFee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getOfficeDealNum() {
		return officeDealNum;
	}
	public void setOfficeDealNum(Integer officeDealNum) {
		this.officeDealNum = officeDealNum;
	}
	public BigDecimal getOfficeDealFee() {
		return officeDealFee;
	}
	public void setOfficeDealFee(BigDecimal officeDealFee) {
		this.officeDealFee = officeDealFee;
	}
	public BigDecimal getManageFee() {
		return manageFee;
	}
	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public BigDecimal getLateFee() {
		return lateFee;
	}
	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}
	public BigDecimal getBackFee() {
		return backFee;
	}
	public void setBackFee(BigDecimal backFee) {
		this.backFee = backFee;
	}
	public String getCardNos() {
		return cardNos;
	}
	public void setCardNos(String cardNos) {
		this.cardNos = cardNos;
	}
	public BigDecimal getMendFee() {
		return mendFee;
	}
	public void setMendFee(BigDecimal mendFee) {
		this.mendFee = mendFee;
	}
	public Integer getMendNum() {
		return mendNum;
	}
	public void setMendNum(Integer mendNum) {
		this.mendNum = mendNum;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
}
