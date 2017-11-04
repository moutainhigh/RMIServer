package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class ProviceSendBoard implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4171820215948601023L;

	private Long listNo;
	
	private String tableCode;
	
	private String tableName;
	
	private Date updateTime;
	
	private Integer updateFlag;
	
	private Long cnt;

	public ProviceSendBoard(){
		
	}
	
	public ProviceSendBoard(Long listNo,String tableCode,String tableName,Date updateTime,Integer updateFlag,Long cnt){
		this.listNo = listNo;
		this.tableCode = tableCode;
		this.tableName = tableName;
		this.updateTime = updateTime;
		this.updateFlag = updateFlag;
		this.cnt = cnt;
	}
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
