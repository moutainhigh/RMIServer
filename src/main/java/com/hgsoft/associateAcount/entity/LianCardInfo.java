package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class LianCardInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1075518796037629616L;
	/**
	 * 
	 */
	private Long hisseqId;
	private Long customerId;
	private String memo;
	private Long placeNo;
	private Date setTime;
	private Long opNo;
	private String prekeep;
	private Date endTime;
	private Date begTime;
	private String sex;
	private String idType;
	private String idCode;
	private String name;
	private String emFlag;
	private String nflag;
	private String cardNo;
	private String vehicleType;
	private Long id;
	
	
	
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getHisseqId() {
		return hisseqId;
	}
	public void setHisseqId(Long hisseqId) {
		this.hisseqId = hisseqId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(Long placeNo) {
		this.placeNo = placeNo;
	}
	public Date getSetTime() {
		return setTime;
	}
	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}
	public Long getOpNo() {
		return opNo;
	}
	public void setOpNo(Long opNo) {
		this.opNo = opNo;
	}
	public String getPrekeep() {
		return prekeep;
	}
	public void setPrekeep(String prekeep) {
		this.prekeep = prekeep;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getBegTime() {
		return begTime;
	}
	public void setBegTime(Date begTime) {
		this.begTime = begTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmFlag() {
		return emFlag;
	}
	public void setEmFlag(String emFlag) {
		this.emFlag = emFlag;
	}
	
	public String getNflag() {
		return nflag;
	}
	public void setNflag(String nflag) {
		this.nflag = nflag;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "LianCardInfo [hisseqId=" + hisseqId + ", customerId="
				+ customerId + ", memo=" + memo + ", placeNo=" + placeNo
				+ ", setTime=" + setTime + ", opNo=" + opNo + ", prekeep="
				+ prekeep + ", endTime=" + endTime + ", begTime=" + begTime
				+ ", sex=" + sex + ", idType=" + idType + ", idCode=" + idCode
				+ ", name=" + name + ", emFlag=" + emFlag + ", nFlag=" + nflag
				+ ", cardNo=" + cardNo + ", id=" + id + "]";
	}
	
	
}

