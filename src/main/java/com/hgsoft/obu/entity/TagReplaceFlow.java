package com.hgsoft.obu.entity;

import java.io.Serializable;
import java.util.Date;

public class TagReplaceFlow implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 579769380306599099L;
	private Long id;
	 private String newTagNo;
	 private String oldTagNo;
	 private String tagNo;
	 private Date replaceTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNewTagNo() {
		return newTagNo;
	}
	public void setNewTagNo(String newTagNo) {
		this.newTagNo = newTagNo;
	}
	public String getOldTagNo() {
		return oldTagNo;
	}
	public void setOldTagNo(String oldTagNo) {
		this.oldTagNo = oldTagNo;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public Date getReplaceTime() {
		return replaceTime;
	}
	public void setReplaceTime(Date replaceTime) {
		this.replaceTime = replaceTime;
	}
	 
	 
}
