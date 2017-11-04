package com.hgsoft.macao.entity;

import java.util.Date;

public class NotifyMCRecord implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 317456080751243263L;
	
	private Long id;
	private String reqSN;
	private String resSN;
	private Date reqTime;
	private Date resTime;
	private String tran_status;
	private Integer reqResult;
	private String interfaceFlag;
	private String verify;
	
	private String bankAccountNumber;
	private String etcCardNumber;
	private String oldCardNumber;
	private String newCardNumber;
	private String carNumber;
	private String carColor;
	
	private Long hisSeqID;
	
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getEtcCardNumber() {
		return etcCardNumber;
	}
	public void setEtcCardNumber(String etcCardNumber) {
		this.etcCardNumber = etcCardNumber;
	}
	public String getOldCardNumber() {
		return oldCardNumber;
	}
	public void setOldCardNumber(String oldCardNumber) {
		this.oldCardNumber = oldCardNumber;
	}
	public String getNewCardNumber() {
		return newCardNumber;
	}
	public void setNewCardNumber(String newCardNumber) {
		this.newCardNumber = newCardNumber;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getInterfaceFlag() {
		return interfaceFlag;
	}
	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public Date getResTime() {
		return resTime;
	}
	public void setResTime(Date resTime) {
		this.resTime = resTime;
	}
	public String getTran_status() {
		return tran_status;
	}
	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
	}
	public Integer getReqResult() {
		return reqResult;
	}
	public void setReqResult(Integer reqResult) {
		this.reqResult = reqResult;
	}
	
	
}
