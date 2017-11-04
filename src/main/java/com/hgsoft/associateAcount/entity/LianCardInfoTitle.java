package com.hgsoft.associateAcount.entity;

import java.io.Serializable;
import java.util.Date;

public class LianCardInfoTitle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2053947503846598470L;

	private Long id;
	private String cardNo;
	private String happenedTime;
	private String importTime;
	private String title;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getHappenedTime() {
		return happenedTime;
	}
	public void setHappenedTime(String happenedTime) {
		this.happenedTime = happenedTime;
	}
	public String getImportTime() {
		return importTime;
	}
	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
