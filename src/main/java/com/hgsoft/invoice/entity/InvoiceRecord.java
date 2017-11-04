package com.hgsoft.invoice.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class InvoiceRecord implements Serializable{
	
	private Long id;
	private String invoiceTitle;
	private Long customerID;
	private String state;
	private String billType;
	
	/**
	 * 发票号码
	 */
	private String invoiceNum;
	
	/**
	 * 发票代码
	 */
	private String invoiceCode;
	
	/**
	 * 通行账单ID
	 */
	private Long passBillID;
	
	/**
	 * 充值账单ID
	 */
	private Long addBillID;
	private Long operID;
	private Long placeID;
	private Date printTime;
	
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public Long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public Long getPassBillID() {
		return passBillID;
	}
	public void setPassBillID(Long passBillID) {
		this.passBillID = passBillID;
	}
	public Long getAddBillID() {
		return addBillID;
	}
	public void setAddBillID(Long addBillID) {
		this.addBillID = addBillID;
	}
	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public Long getPlaceID() {
		return placeID;
	}
	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	
}
