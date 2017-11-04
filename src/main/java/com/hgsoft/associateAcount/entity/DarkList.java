package com.hgsoft.associateAcount.entity;
import java.io.Serializable;
import java.util.Date;


public class DarkList  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3641489292073596681L;
	private Long id;
	private Long customerId;
	private String cardNo;
	private String obuSerial;
	private String cardType;
	private Date genDate;
	private String gencau;
	private String genmode;
	private Long operId;
	private Long placeId;
	private String remark;
	private Long hisseqId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Date updateTime;
	private String userNo;
	private String userName;
	private String state;
	private Date insertTime;
	
	
	
	public String getObuSerial() {
		return obuSerial;
	}
	public void setObuSerial(String obuSerial) {
		this.obuSerial = obuSerial;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getGenDate() {
		return genDate;
	}
	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}
	public String getGencau() {
		return gencau;
	}
	public void setGencau(String gencau) {
		this.gencau = gencau;
	}
	public String getGenmode() {
		return genmode;
	}
	public void setGenmode(String genmode) {
		this.genmode = genmode;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getHisseqId() {
		return hisseqId;
	}
	public void setHisseqId(Long hisseqId) {
		this.hisseqId = hisseqId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}

