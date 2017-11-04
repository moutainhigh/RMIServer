package com.hgsoft.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class PassInvoice implements Serializable {

	private Long id;
	private BigDecimal invoiceAmount;
	private Integer printTimes;
	private String state;
	private Long placeID;
	private Long operID;
	
	public PassInvoice() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Integer getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
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
