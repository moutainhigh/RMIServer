package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class ProviceRecvBoard implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5601283383480323948L;

	private Long listNo;
	
	private String tableCode;
	
	private String tableName;
	
	private String sendCode;
	
	private Date updateTime;
	
	private Integer updateFlag;
	
	private Long cnt;

	public Long getListNo() {
		return listNo;
	}

	public void setListNo(Long listNo) {
		this.listNo = listNo;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Integer updateFlag) {
		this.updateFlag = updateFlag;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	
	

}
