package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ScBillInfoMonthly implements Serializable {

    private static final long serialVersionUID = 7435112902328410047L;
    private Long id;
    private Integer settleMonth;
    private String userNo;
    private String cardNo;
    private Integer cardType;
    private Integer dealStatus;
    private Integer dealNum;
    private BigDecimal dealFee;
    private BigDecimal realDealFee;
    private Integer onceNum;
    private BigDecimal onceFee;
    private BigDecimal serverFee;
    private BigDecimal lateFee;
    private Integer otherNum;
    private BigDecimal otherFee;
    private BigDecimal otherRealFee;
    private BigDecimal otherServerFee;
    private Integer rechargeNum;
    private BigDecimal rechargeFee;
    private BigDecimal transferFee;
    private BigDecimal mustReturnFee;
    private BigDecimal hadReturnFee;

    public Integer getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(Integer settleMonth) {
        this.settleMonth = settleMonth;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Integer getDealNum() {
        return dealNum;
    }

    public void setDealNum(Integer dealNum) {
        this.dealNum = dealNum;
    }

    public BigDecimal getDealFee() {
        return dealFee;
    }

    public void setDealFee(BigDecimal dealFee) {
        this.dealFee = dealFee;
    }

    public BigDecimal getRealDealFee() {
        return realDealFee;
    }

    public void setRealDealFee(BigDecimal realDealFee) {
        this.realDealFee = realDealFee;
    }

    public Integer getOnceNum() {
        return onceNum;
    }

    public void setOnceNum(Integer onceNum) {
        this.onceNum = onceNum;
    }

    public BigDecimal getOnceFee() {
        return onceFee;
    }

    public void setOnceFee(BigDecimal onceFee) {
        this.onceFee = onceFee;
    }

    public BigDecimal getServerFee() {
        return serverFee;
    }

    public void setServerFee(BigDecimal serverFee) {
        this.serverFee = serverFee;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public Integer getOtherNum() {
        return otherNum;
    }

    public void setOtherNum(Integer otherNum) {
        this.otherNum = otherNum;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getOtherRealFee() {
        return otherRealFee;
    }

    public void setOtherRealFee(BigDecimal otherRealFee) {
        this.otherRealFee = otherRealFee;
    }

    public BigDecimal getOtherServerFee() {
        return otherServerFee;
    }

    public void setOtherServerFee(BigDecimal otherServerFee) {
        this.otherServerFee = otherServerFee;
    }

    public Integer getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Integer rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getMustReturnFee() {
        return mustReturnFee;
    }

    public void setMustReturnFee(BigDecimal mustReturnFee) {
        this.mustReturnFee = mustReturnFee;
    }

    public BigDecimal getHadReturnFee() {
        return hadReturnFee;
    }

    public void setHadReturnFee(BigDecimal hadReturnFee) {
        this.hadReturnFee = hadReturnFee;
    }
}
