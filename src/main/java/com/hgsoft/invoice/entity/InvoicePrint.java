package com.hgsoft.invoice.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InvoicePrint implements Serializable {

	private Long id;
	private Long userID;
	private Long bussinessID;
	private Long passInvoiceID;
	private String type;
	private String invoiceTitle;
	private String batchNo;
	private String invoiceNo;
	private String state;
	private Long placeID;
	private Long operID;
	
	public InvoicePrint() {
		super();
	}
	
	public InvoicePrint(Long  bussinessID) {
		super();
		this.bussinessID = bussinessID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getBussinessID() {
		return bussinessID;
	}

	public void setBussinessID(Long bussinessID) {
		this.bussinessID = bussinessID;
	}

	public Long getPassInvoiceID() {
		return passInvoiceID;
	}

	public void setPassInvoiceID(Long passInvoiceID) {
		this.passInvoiceID = passInvoiceID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getPlaceID() {
		return placeID;
	}

	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}

	public Long getOperID() {
		return operID;
	}

	public void setOperID(Long operID) {
		this.operID = operID;
	}
	
	
}
