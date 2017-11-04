package com.hgsoft.obu.entity;

import java.util.Date;

public class OBUActRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3099504799704147812L;
	private Long id;
	private String activateCardNo;
	private Long operID;
	private Date makeDate;
	private String memo;
	private String writebackFlag;
	private Date writebackTime;
	private Long writebackOperID;
	private String writebackOperName;
	private String writebackOperNo;
	private Long hisSeqID;
	private String systemType;
	private Long placeID;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	public String getWritebackOperName() {
		return writebackOperName;
	}
	public void setWritebackOperName(String writebackOperName) {
		this.writebackOperName = writebackOperName;
	}
	public String getWritebackOperNo() {
		return writebackOperNo;
	}
	public void setWritebackOperNo(String writebackOperNo) {
		this.writebackOperNo = writebackOperNo;
	}
	public Long getPlaceID() {
		return placeID;
	}
	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActivateCardNo() {
		return activateCardNo;
	}
	public void setActivateCardNo(String activateCardNo) {
		this.activateCardNo = activateCardNo;
	}
	public Long getOperID() {
		return operID;
	}
	public void setOperID(Long operID) {
		this.operID = operID;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getWritebackFlag() {
		return writebackFlag;
	}
	public void setWritebackFlag(String writebackFlag) {
		this.writebackFlag = writebackFlag;
	}
	public Date getWritebackTime() {
		return writebackTime;
	}
	public void setWritebackTime(Date writebackTime) {
		this.writebackTime = writebackTime;
	}
	public Long getWritebackOperID() {
		return writebackOperID;
	}
	public void setWritebackOperID(Long writebackOperID) {
		this.writebackOperID = writebackOperID;
	}
	public Long getHisSeqID() {
		return hisSeqID;
	}
	public void setHisSeqID(Long hisSeqID) {
		this.hisSeqID = hisSeqID;
	}
	
	
}
