package com.hgsoft.system.entity;

import java.io.Serializable;

public class CusPointPoJo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3976326735952047488L;
	private Long cusPoint;
	private String cusPointName;
	private String cusPointCode;
	private String bankNo;

	public Long getCusPoint() {
		return cusPoint;
	}
	public void setCusPoint(Long cusPoint) {
		this.cusPoint = cusPoint;
	}
	public String getCusPointName() {
		return cusPointName;
	}
	public void setCusPointName(String cusPointName) {
		this.cusPointName = cusPointName;
	}
	public String getCusPointCode() {
		return cusPointCode;
	}
	public void setCusPointCode(String cusPointCode) {
		this.cusPointCode = cusPointCode;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	@Override
	public String toString() {
		return "CusPointPoJo [cusPoint=" + cusPoint + ", cusPointName="
				+ cusPointName + ", cusPointCode=" + cusPointCode 
				+ ", bankNo=" + bankNo + "]";
	}
	
	
}
