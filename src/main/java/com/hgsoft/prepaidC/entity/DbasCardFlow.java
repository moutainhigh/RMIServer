package com.hgsoft.prepaidC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DbasCardFlow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4441579622977510688L;
	private Long id;
	//新卡号
	private String newCardNo;
	//旧卡号
	private String oldCardNo;
	//原始号
	private String cardNo;
	//卡类型5：储值卡；6：记帐卡
	private String cardType;
	//业务类型
	private String serType;
	//办理时间
	private Date applyTime;
	//办理业务ID	
	private Long serviceId;
	//卡片实际余额
	private BigDecimal cardAmt;
	//是否可读卡
	private String readFlag;
	//资金转移卡号
	private String chgCardNo;
	//完成的业务记录ID
	private Long endServiceId;
	//完成的业务类型
	private String endSerType;
	//完成的金额
	private BigDecimal endCardAmt;
	//完成的标志
	private String endFlag;
	//备注
	private String Memo;
	//资金争议期
	private String expireFlag;
	//充值时间
	private Date rechargeTime;
	//资金争议期过期时间
	private Date expireTime;
	//系统余额时间
	private Date balanceTime;
	//更新时间
	private Date updateTime;
	
	private Long operId;
	private Long placeId;
	private String operno;
	private String opername;
	private String placeno;
	private String placename;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNewCardNo() {
		return newCardNo;
	}
	public void setNewCardNo(String newCardNo) {
		this.newCardNo = newCardNo;
	}
	public String getOldCardNo() {
		return oldCardNo;
	}
	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
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
	public String getSerType() {
		return serType;
	}
	public void setSerType(String serType) {
		this.serType = serType;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public BigDecimal getCardAmt() {
		return cardAmt;
	}
	public void setCardAmt(BigDecimal cardAmt) {
		this.cardAmt = cardAmt;
	}
	public String getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	public String getChgCardNo() {
		return chgCardNo;
	}
	public void setChgCardNo(String chgCardNo) {
		this.chgCardNo = chgCardNo;
	}
	public Long getEndServiceId() {
		return endServiceId;
	}
	public void setEndServiceId(Long endServiceId) {
		this.endServiceId = endServiceId;
	}
	public String getEndSerType() {
		return endSerType;
	}
	public void setEndSerType(String endSerType) {
		this.endSerType = endSerType;
	}
	public BigDecimal getEndCardAmt() {
		return endCardAmt;
	}
	public void setEndCardAmt(BigDecimal endCardAmt) {
		this.endCardAmt = endCardAmt;
	}
	public String getEndFlag() {
		return endFlag;
	}
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public String getExpireFlag() {
		return expireFlag;
	}
	public void setExpireFlag(String expireFlag) {
		this.expireFlag = expireFlag;
	}
	public Date getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
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
	public String getPlaceno() {
		return placeno;
	}
	public void setPlaceno(String placeno) {
		this.placeno = placeno;
	}
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Date getBalanceTime() {
		return balanceTime;
	}
	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
