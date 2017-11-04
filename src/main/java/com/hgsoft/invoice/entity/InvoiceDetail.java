package com.hgsoft.invoice.entity;

import java.io.Serializable;
import java.util.Date;

public class InvoiceDetail  implements Serializable  {
	private Long id;
	private String cardNo;
	private String type;
	private Long reckOnListNo;
	private Long passInvoiceId;
	private Date reckOnTime;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getReckOnListNo() {
		return reckOnListNo;
	}
	public void setReckOnListNo(Long reckOnListNo) {
		this.reckOnListNo = reckOnListNo;
	}
	public Long getPassInvoiceId() {
		return passInvoiceId;
	}
	public void setPassInvoiceId(Long passInvoiceId) {
		this.passInvoiceId = passInvoiceId;
	}
	public Date getReckOnTime() {
		return reckOnTime;
	}
	public void setReckOnTime(Date reckOnTime) {
		this.reckOnTime = reckOnTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
