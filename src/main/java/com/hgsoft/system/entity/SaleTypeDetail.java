package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;

public class SaleTypeDetail implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5340143238855315288L;
	private Long id;
	private String code;
	private String name;
	private String flag;
	private Date settime;
	private Long operId;
	private String operno;
	private String opername;
	private String memo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getSettime() {
		return settime;
	}
	public void setSettime(Date settime) {
		this.settime = settime;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public String getOperno() {
		return operno;
	}
	public void setOperno(String operno) {
		this.operno = operno;
	}
	public String getOpername() {
		return opername;
	}
	public void setOpername(String opername) {
		this.opername = opername;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
