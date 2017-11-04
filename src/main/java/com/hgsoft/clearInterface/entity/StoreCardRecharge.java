package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StoreCardRecharge implements Serializable{

	private static final long serialVersionUID = -1136980290097771476L;

	private Long id;
	private String cardCode;
	private Date timeReq;
	private String Dealtype;
	private BigDecimal moneySur;
	private BigDecimal ReturnMoneySur;
	private BigDecimal BalReq;
	private BigDecimal BalSur;
	private String LinkReq;
	private String LinkSur;
	private String TrReq;
	private String TrSur;
	private String enVersion;
	private String alVersion;
	private String random;
	private String mac1;
	private String tac;
	private Date timeConfirm;
	private String remark;
	private Date updatetime;
	private Long boardListNo;
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
	public Date getTimeReq() {
		return timeReq;
	}
	public void setTimeReq(Date timeReq) {
		this.timeReq = timeReq;
	}
	public String getDealtype() {
		return Dealtype;
	}
	public void setDealtype(String dealtype) {
		Dealtype = dealtype;
	}
	public BigDecimal getMoneySur() {
		return moneySur;
	}
	public void setMoneySur(BigDecimal moneySur) {
		this.moneySur = moneySur;
	}
	public BigDecimal getReturnMoneySur() {
		return ReturnMoneySur;
	}
	public void setReturnMoneySur(BigDecimal returnMoneySur) {
		ReturnMoneySur = returnMoneySur;
	}
	public BigDecimal getBalReq() {
		return BalReq;
	}
	public void setBalReq(BigDecimal balReq) {
		BalReq = balReq;
	}
	public BigDecimal getBalSur() {
		return BalSur;
	}
	public void setBalSur(BigDecimal balSur) {
		BalSur = balSur;
	}
	public String getLinkReq() {
		return LinkReq;
	}
	public void setLinkReq(String linkReq) {
		LinkReq = linkReq;
	}
	public String getLinkSur() {
		return LinkSur;
	}
	public void setLinkSur(String linkSur) {
		LinkSur = linkSur;
	}
	public String getTrReq() {
		return TrReq;
	}
	public void setTrReq(String trReq) {
		TrReq = trReq;
	}
	public String getTrSur() {
		return TrSur;
	}
	public void setTrSur(String trSur) {
		TrSur = trSur;
	}

	public String getEnVersion() {
		return enVersion;
	}

	public void setEnVersion(String enVersion) {
		this.enVersion = enVersion;
	}

	public String getAlVersion() {
		return alVersion;
	}

	public void setAlVersion(String alVersion) {
		this.alVersion = alVersion;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getMac1() {
		return mac1;
	}

	public void setMac1(String mac1) {
		this.mac1 = mac1;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public Date getTimeConfirm() {
		return timeConfirm;
	}

	public void setTimeConfirm(Date timeConfirm) {
		this.timeConfirm = timeConfirm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Long getBoardListNo() {
		return boardListNo;
	}
	public void setBoardListNo(Long boardListNo) {
		this.boardListNo = boardListNo;
	}

}
