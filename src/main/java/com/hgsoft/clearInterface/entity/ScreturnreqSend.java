package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.util.Date;

public class ScreturnreqSend implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8922247336912840182L;
	private Long id;
	private String oldCardNo;
	private String newCardNo;
	private Date optime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOldCardNo() {
		return oldCardNo;
	}
	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
	}
	public String getNewCardNo() {
		return newCardNo;
	}
	public void setNewCardNo(String newCardNo) {
		this.newCardNo = newCardNo;
	}
	public Date getOptime() {
		return optime;
	}
	public void setOptime(Date optime) {
		this.optime = optime;
	}
	
}
