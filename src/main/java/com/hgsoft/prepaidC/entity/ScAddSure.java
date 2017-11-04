package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ScAddSure implements java.io.Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4149678628693010703L;
	private Long id;
	private String cardNo;
	private BigDecimal balReq;
	private BigDecimal moneyReq;
	private BigDecimal returnMoneyReq;
	private BigDecimal transferSumReq;
	private String randomReq;
	private String transverReq;
	private String keyverReq;
	private Long onlineTradenoReq;
	private Long offlineTradenoReq;
	private String termnoReq ;
	private String psamnoReq;
	private Date timeReq;
	private String placeNoReq;
	private String opcodeReq;
	private Date dealtimeReq;
	private String tradeType;
	private String checkCode;
	private String mac;
	private String tac;
	private String state;
	private BigDecimal balSur;
	private BigDecimal moneySur;
	private BigDecimal returnMoneySur;
	private BigDecimal transferSumSur;
	private String randomSur;
	private String transverSur;
	private String keyverSur;
	private Long onlineTradenoSur;
	private Long offlineTradenoSur;
	private String termnoSur;
	private String psamnoSur;
	private Date timeSur;
	private String placeNoSur;
	private String opcodeSur;
	private Date dealtimeSur;
	
	private String payChannel;

	private String confirmType;
	
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
	public BigDecimal getTransferSumReq() {
		return transferSumReq;
	}
	public void setTransferSumReq(BigDecimal transferSumReq) {
		this.transferSumReq = transferSumReq;
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
	public Long getOnlineTradenoReq() {
		return onlineTradenoReq;
	}
	public void setOnlineTradenoReq(Long onlineTradenoReq) {
		this.onlineTradenoReq = onlineTradenoReq;
	}
	public Long getOfflineTradenoReq() {
		return offlineTradenoReq;
	}
	public void setOfflineTradenoReq(Long offlineTradenoReq) {
		this.offlineTradenoReq = offlineTradenoReq;
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
	public String getPlaceNoReq() {
		return placeNoReq;
	}
	public void setPlaceNoReq(String placeNoReq) {
		this.placeNoReq = placeNoReq;
	}
	public String getOpcodeReq() {
		return opcodeReq;
	}
	public void setOpcodeReq(String opcodeReq) {
		this.opcodeReq = opcodeReq;
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
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getTac() {
		return tac;
	}
	public void setTac(String tac) {
		this.tac = tac;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getBalSur() {
		return balSur;
	}
	public void setBalSur(BigDecimal balSur) {
		this.balSur = balSur;
	}
	public BigDecimal getMoneySur() {
		return moneySur;
	}
	public void setMoneySur(BigDecimal moneySur) {
		this.moneySur = moneySur;
	}
	public BigDecimal getReturnMoneySur() {
		return returnMoneySur;
	}
	public void setReturnMoneySur(BigDecimal returnMoneySur) {
		this.returnMoneySur = returnMoneySur;
	}
	public BigDecimal getTransferSumSur() {
		return transferSumSur;
	}
	public void setTransferSumSur(BigDecimal transferSumSur) {
		this.transferSumSur = transferSumSur;
	}
	public String getRandomSur() {
		return randomSur;
	}
	public void setRandomSur(String randomSur) {
		this.randomSur = randomSur;
	}
	public String getTransverSur() {
		return transverSur;
	}
	public void setTransverSur(String transverSur) {
		this.transverSur = transverSur;
	}
	public String getKeyverSur() {
		return keyverSur;
	}
	public void setKeyverSur(String keyverSur) {
		this.keyverSur = keyverSur;
	}
	public Long getOnlineTradenoSur() {
		return onlineTradenoSur;
	}
	public void setOnlineTradenoSur(Long onlineTradenoSur) {
		this.onlineTradenoSur = onlineTradenoSur;
	}
	public Long getOfflineTradenoSur() {
		return offlineTradenoSur;
	}
	public void setOfflineTradenoSur(Long offlineTradenoSur) {
		this.offlineTradenoSur = offlineTradenoSur;
	}
	public String getTermnoSur() {
		return termnoSur;
	}
	public void setTermnoSur(String termnoSur) {
		this.termnoSur = termnoSur;
	}
	public String getPsamnoSur() {
		return psamnoSur;
	}
	public void setPsamnoSur(String psamnoSur) {
		this.psamnoSur = psamnoSur;
	}
	public Date getTimeSur() {
		return timeSur;
	}
	public void setTimeSur(Date timeSur) {
		this.timeSur = timeSur;
	}
	public String getPlaceNoSur() {
		return placeNoSur;
	}
	public void setPlaceNoSur(String placeNoSur) {
		this.placeNoSur = placeNoSur;
	}
	public String getOpcodeSur() {
		return opcodeSur;
	}
	public void setOpcodeSur(String opcodeSur) {
		this.opcodeSur = opcodeSur;
	}
	public Date getDealtimeSur() {
		return dealtimeSur;
	}
	public void setDealtimeSur(Date dealtimeSur) {
		this.dealtimeSur = dealtimeSur;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}


	public String getConfirmType() {
		return confirmType;
	}

	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}
}
