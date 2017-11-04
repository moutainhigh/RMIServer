package com.hgsoft.macao.entity;

import java.util.Date;

public class MacaoBankAccount implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8353218592505305518L;

	
	private Long id;
	private Long mainId;
	private Date reqTime;
	private String bankCode;
	private Long branchId;
	private String bankAccountNumber;
	private Date reqOperTime;
	private Long reqOperId;
	private Long reqPlaceId;
	private String reqOperNo;
	private String reqOperName;
	private String reqPlaceNo;
	private String reqPlaceName;
	private String reqSN;
	private String resSN;
	private Date resTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public Date getReqOperTime() {
		return reqOperTime;
	}
	public void setReqOperTime(Date reqOperTime) {
		this.reqOperTime = reqOperTime;
	}
	public Long getReqOperId() {
		return reqOperId;
	}
	public void setReqOperId(Long reqOperId) {
		this.reqOperId = reqOperId;
	}
	public Long getReqPlaceId() {
		return reqPlaceId;
	}
	public void setReqPlaceId(Long reqPlaceId) {
		this.reqPlaceId = reqPlaceId;
	}
	public String getReqOperNo() {
		return reqOperNo;
	}
	public void setReqOperNo(String reqOperNo) {
		this.reqOperNo = reqOperNo;
	}
	public String getReqOperName() {
		return reqOperName;
	}
	public void setReqOperName(String reqOperName) {
		this.reqOperName = reqOperName;
	}
	public String getReqPlaceNo() {
		return reqPlaceNo;
	}
	public void setReqPlaceNo(String reqPlaceNo) {
		this.reqPlaceNo = reqPlaceNo;
	}
	public String getReqPlaceName() {
		return reqPlaceName;
	}
	public void setReqPlaceName(String reqPlaceName) {
		this.reqPlaceName = reqPlaceName;
	}
	public String getReqSN() {
		return reqSN;
	}
	public void setReqSN(String reqSN) {
		this.reqSN = reqSN;
	}
	public String getResSN() {
		return resSN;
	}
	public void setResSN(String resSN) {
		this.resSN = resSN;
	}
	public Date getResTime() {
		return resTime;
	}
	public void setResTime(Date resTime) {
		this.resTime = resTime;
	}
	
	
}
