package com.hgsoft.daysettle.entity;

import java.util.Date;

public class RemarkInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145616935010947480L;

	private Long id;
	
	private String tableName;
	
	private Long businessId;
	
	private String content;
	
	private Date remarkDate;
	
	private Long operid;
	
	private String operCode;
	
	private String operName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRemarkDate() {
		return remarkDate;
	}

	public void setRemarkDate(Date remarkDate) {
		this.remarkDate = remarkDate;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	
}
