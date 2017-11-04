package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ScAddReq implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4520932191836184726L;
	private Long id;
	private String cardNo;
	private BigDecimal balReq;
	private BigDecimal moneyReq;
	private BigDecimal returnMoneyReq;
	private BigDecimal transfersumReq;
	private String randomReq;
	private String transverReq;
	private String keyverReq;
	private Long onlineTradeNoReq;
	private Long offlineTradeNoReq;
	private String termnoReq;
	private String psamnoReq;
	private Date timeReq;
	private Date dealtimeReq;
	private String tradeType;
	private String placeNo;
	private String opCode;
	private String payChannel;
	private String ipaddr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getBalReq() {
		return balReq;
	}
	public void setBalReq(BigDecimal balReq) {
		this.balReq = balReq;
	}
	public BigDecimal getMoneyReq() {
		return moneyReq;
	}
	public void setMoneyReq(BigDecimal moneyReq) {
		this.moneyReq = moneyReq;
	}
	public BigDecimal getReturnMoneyReq() {
		return returnMoneyReq;
	}
	public void setReturnMoneyReq(BigDecimal returnMoneyReq) {
		this.returnMoneyReq = returnMoneyReq;
	}
	public BigDecimal getTransfersumReq() {
		return transfersumReq;
	}
	public void setTransfersumReq(BigDecimal transfersumReq) {
		this.transfersumReq = transfersumReq;
	}
	public String getRandomReq() {
		return randomReq;
	}
	public void setRandomReq(String randomReq) {
		this.randomReq = randomReq;
	}
	public String getTransverReq() {
		return transverReq;
	}
	public void setTransverReq(String transverReq) {
		this.transverReq = transverReq;
	}
	public String getKeyverReq() {
		return keyverReq;
	}
	public void setKeyverReq(String keyverReq) {
		this.keyverReq = keyverReq;
	}
	public Long getOnlineTradeNoReq() {
		return onlineTradeNoReq;
	}
	public void setOnlineTradeNoReq(Long onlineTradeNoReq) {
		this.onlineTradeNoReq = onlineTradeNoReq;
	}
	public Long getOfflineTradeNoReq() {
		return offlineTradeNoReq;
	}
	public void setOfflineTradeNoReq(Long offlineTradeNoReq) {
		this.offlineTradeNoReq = offlineTradeNoReq;
	}
	public String getTermnoReq() {
		return termnoReq;
	}
	public void setTermnoReq(String termnoReq) {
		this.termnoReq = termnoReq;
	}
	public String getPsamnoReq() {
		return psamnoReq;
	}
	public void setPsamnoReq(String psamnoReq) {
		this.psamnoReq = psamnoReq;
	}
	public Date getTimeReq() {
		return timeReq;
	}
	public void setTimeReq(Date timeReq) {
		this.timeReq = timeReq;
	}
	public Date getDealtimeReq() {
		return dealtimeReq;
	}
	public void setDealtimeReq(Date dealtimeReq) {
		this.dealtimeReq = dealtimeReq;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPlaceNo() {
		return placeNo;
	}
	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	
	

}
