package com.hgsoft.macao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MacaoPassageDetail implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3907939334591670939L;
	private Long id;
	private Long boardlistno;
	private String cardCode;
	private Long transId;
	private String detailNo;
	private Date enTime;
	private String entry;
	private Date exTime;
	private String export;
	private BigDecimal toll;
	private BigDecimal realToll;
	private String dealType;
	private String provinceCode;
	private Date balanceDate;
	private String isSet;
	private String isverify;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBoardlistno() {
		return boardlistno;
	}
	public void setBoardlistno(Long boardlistno) {
		this.boardlistno = boardlistno;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public String getDetailNo() {
		return detailNo;
	}
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}
	public Date getEnTime() {
		return enTime;
	}
	public void setEnTime(Date enTime) {
		this.enTime = enTime;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Date getExTime() {
		return exTime;
	}
	public void setExTime(Date exTime) {
		this.exTime = exTime;
	}
	public String getExport() {
		return export;
	}
	public void setExport(String export) {
		this.export = export;
	}
	public BigDecimal getToll() {
		return toll;
	}
	public void setToll(BigDecimal toll) {
		this.toll = toll;
	}
	public BigDecimal getRealToll() {
		return realToll;
	}
	public void setRealToll(BigDecimal realToll) {
		this.realToll = realToll;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Date getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getIsSet() {
		return isSet;
	}
	public void setIsSet(String isSet) {
		this.isSet = isSet;
	}
	public String getIsverify() {
		return isverify;
	}
	public void setIsverify(String isverify) {
		this.isverify = isverify;
	}
	
}
