package com.hgsoft.associateReport.entity;

import java.io.Serializable;
import java.util.Date;

public class ImportInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2342348915142356547L;
	private Long id;
	private String handleType;
	private String cardCode;
	private Date operateTime;
	private String password;
	private String genCause;
	private String startDate;
	private String endDate;
	private String errorReason;
	private Date importTime;
	private String fileName;
	private Long placeId;
	private Long OperId;
	private Long mainId;
	private String importResult;
	
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Long getOperId() {
		return OperId;
	}
	public void setOperId(Long operId) {
		OperId = operId;
	}
	
	public String getImportResult() {
		return importResult;
	}
	public void setImportResult(String importResult) {
		this.importResult = importResult;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGenCause() {
		return genCause;
	}
	public void setGenCause(String genCause) {
		this.genCause = genCause;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public Date getImportTime() {
		return importTime;
	}
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	
	
	

}
