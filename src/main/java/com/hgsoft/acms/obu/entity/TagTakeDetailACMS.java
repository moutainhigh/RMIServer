package com.hgsoft.acms.obu.entity;

import java.util.Date;

public class TagTakeDetailACMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1682026529868767338L;
	private Long id;
	private Long mainID;
	private String tagNo;
	private String tagState;
	private String memo;
	
	private String obuSerial;
	
	private Date startTime;
	private Date endTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainID() {
		return mainID;
	}
	public void setMainID(Long mainID) {
		this.mainID = mainID;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public String getTagState() {
		return tagState;
	}
	public void setTagState(String tagState) {
		this.tagState = tagState;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getObuSerial() {
		return obuSerial;
	}
	public void setObuSerial(String obuSerial) {
		this.obuSerial = obuSerial;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
