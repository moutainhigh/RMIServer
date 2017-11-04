package com.hgsoft.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值即领发票账单
 * @author lzl
 *
 */
public class AddBill implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1820302986780570786L;
	private Long id;
	private String cardNo;
	private Long customerID;
	
	private String state;
	
	/**
	 * 充值金额
	 */
	private BigDecimal addFee;
	
	private Integer printNum;
	
	/**
	 * 系统余额
	 */
	private BigDecimal sysCost;
	
	/**
	 * 优惠金额
	 */
	private BigDecimal realCost;
	
	/**
	 * 资金转移金额
	 */
	private BigDecimal transferCost;
	
	
	/**
	 * 资金转移优惠金额
	 */
	private BigDecimal transferRealCost;
	private Date addTime;
	
	/**
	 * 备注
	 */
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}
	public BigDecimal getAddFee() {
		return addFee;
	}
	public void setAddFee(BigDecimal addFee) {
		this.addFee = addFee;
	}
	public BigDecimal getSysCost() {
		return sysCost;
	}
	public void setSysCost(BigDecimal sysCost) {
		this.sysCost = sysCost;
	}
	public BigDecimal getRealCost() {
		return realCost;
	}
	public void setRealCost(BigDecimal realCost) {
		this.realCost = realCost;
	}
	public BigDecimal getTransferCost() {
		return transferCost;
	}
	public void setTransferCost(BigDecimal transferCost) {
		this.transferCost = transferCost;
	}
	public BigDecimal getTransferRealCost() {
		return transferRealCost;
	}
	public void setTransferRealCost(BigDecimal transferRealCost) {
		this.transferRealCost = transferRealCost;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getPrintNum() {
		return printNum;
	}
	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}
}
