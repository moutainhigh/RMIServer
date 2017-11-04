package com.hgsoft.acms.other.entity;

import java.io.Serializable;
import java.util.Date;

public class TestCardACMS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3344262782090511654L;
	private Long id;
	private String cardCode;
	private Integer cardType;
	private Integer delFlag;
	private Date setTime;
	private String remark;
	private Date updateTime;
	 
	public TestCardACMS() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public TestCardACMS(Long id, String cardCode, Integer cardType, Integer delFlag, Date setTime, String remark,
			Date updateTime) {
		super();
		this.id = id;
		this.cardCode = cardCode;
		this.cardType = cardType;
		this.delFlag = delFlag;
		this.setTime = setTime;
		this.remark = remark;
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "TestCard [id=" + id + ", cardCode=" + cardCode + ", cardType=" + cardType + ", delFlag=" + delFlag
				+ ", setTime=" + setTime + ", remark=" + remark + ", updateTime=" + updateTime + "]";
	}
	
	
	
}
