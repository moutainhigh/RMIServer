package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class FileImport  implements Serializable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4030075029658036430L;
	private Long id;
	private Long customerId;
	private String fileName;
	private Integer totalRow;
	private Integer importRow;
	private Long operNo;
	private Date setTime;
	private Long placeNo;
	private String memo;
	private String checkCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
	}
	public Integer getImportRow() {
		return importRow;
	}
	public void setImportRow(Integer importRow) {
		this.importRow = importRow;
	}
	public Long getOperNo() {
		return operNo;
	}
	public void setOperNo(Long operNo) {
		this.operNo = operNo;
	}
	public Date getSetTime() {
		return setTime;
	}
	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}
	public Long getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(Long placeNo) {
		this.placeNo = placeNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	
	
}

