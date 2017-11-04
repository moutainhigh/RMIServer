package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class BlackListVersion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4743227476280955766L;

	private Long id;
	
	private Long versionNo;
	
	private String versionType;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
