package com.hgsoft.associateReport.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ServiceApp  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1242493549211681077L;
	private Long id;
	private String flowNo;
	private String placeType;
	private String exraNo;
	private String handleType;
	private String accode;
	private String baccount;
	private String bankName;
	private Long placeId;
	private BigDecimal balance;
	private Date serDate;
	private String serPlace;
	private Long operId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getPlaceType() {
		return placeType;
	}
	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	
	public String getExraNo() {
		return exraNo;
	}
	public void setExraNo(String exraNo) {
		this.exraNo = exraNo;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getAccode() {
		return accode;
	}
	public void setAccode(String accode) {
		this.accode = accode;
	}
	public String getBaccount() {
		return baccount;
	}
	public void setBaccount(String baccount) {
		this.baccount = baccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Date getSerDate() {
		return serDate;
	}
	public void setSerDate(Date serDate) {
		this.serDate = serDate;
	}
	public String getSerPlace() {
		return serPlace;
	}
	public void setSerPlace(String serPlace) {
		this.serPlace = serPlace;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	
	
	
}

