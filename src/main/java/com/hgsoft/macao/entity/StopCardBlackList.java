package com.hgsoft.macao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 止付黑名单表
 * @author guanshaofeng
 * @date 2017年4月5日
 */
public class StopCardBlackList implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6883155081739070636L;
	
	
	private Long id;
	private BigDecimal recordId;
	private String cardCode;
	private String genCau;
	private Date genTime;
	private Date insertTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getRecordId() {
		return recordId;
	}
	public void setRecordId(BigDecimal recordId) {
		this.recordId = recordId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getGenCau() {
		return genCau;
	}
	public void setGenCau(String genCau) {
		this.genCau = genCau;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
}
