package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangzhongji on 17/9/28.
 */
public class HandPaymentRepeatDetail implements Serializable {

    private static final long serialVersionUID = -6998047512756931307L;

    private Long id;
    private String userNo;
    private Long tableId;
    private String detailNo;
    private Integer settleMonth;
    private String cardNo;
    private Integer cardType;
    private Date entranceTime;
    private String entranceStationName;
    private String entranceRoadName;
    private Date exitTime;
    private String exitStationName;
    private String exitRoadName;
    private BigDecimal beforeBalance;
    private BigDecimal postBalance;
    private Long onlineTradeNo;
    private String offlineTradeNo;
    private Date squadDate;
    private Integer dealStatus;
    private String passType;
    private Integer inProvinceFlag;
    private BigDecimal toll;
    private BigDecimal realToll;
    private BigDecimal discountAmount;
    private String passProvince;
    private Date balanceTime;
    private String bankAccount;
    private String bankNo;
    private Date comGenTime;
    private Integer payFlag;
    private Date payTime;
    private Integer payType;
    private Date genTime;
    private Long acBillId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    public Integer getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(Integer settleMonth) {
        this.settleMonth = settleMonth;
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

    public Date getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getEntranceStationName() {
        return entranceStationName;
    }

    public void setEntranceStationName(String entranceStationName) {
        this.entranceStationName = entranceStationName;
    }

    public String getEntranceRoadName() {
        return entranceRoadName;
    }

    public void setEntranceRoadName(String entranceRoadName) {
        this.entranceRoadName = entranceRoadName;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public String getExitStationName() {
        return exitStationName;
    }

    public void setExitStationName(String exitStationName) {
        this.exitStationName = exitStationName;
    }

    public String getExitRoadName() {
        return exitRoadName;
    }

    public void setExitRoadName(String exitRoadName) {
        this.exitRoadName = exitRoadName;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(BigDecimal postBalance) {
        this.postBalance = postBalance;
    }

    public Long getOnlineTradeNo() {
        return onlineTradeNo;
    }

    public void setOnlineTradeNo(Long onlineTradeNo) {
        this.onlineTradeNo = onlineTradeNo;
    }

    public String getOfflineTradeNo() {
        return offlineTradeNo;
    }

    public void setOfflineTradeNo(String offlineTradeNo) {
        this.offlineTradeNo = offlineTradeNo;
    }

    public Date getSquadDate() {
        return squadDate;
    }

    public void setSquadDate(Date squadDate) {
        this.squadDate = squadDate;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public Integer getInProvinceFlag() {
        return inProvinceFlag;
    }

    public void setInProvinceFlag(Integer inProvinceFlag) {
        this.inProvinceFlag = inProvinceFlag;
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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPassProvince() {
        return passProvince;
    }

    public void setPassProvince(String passProvince) {
        this.passProvince = passProvince;
    }

    public Date getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Date balanceTime) {
        this.balanceTime = balanceTime;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Date getComGenTime() {
        return comGenTime;
    }

    public void setComGenTime(Date comGenTime) {
        this.comGenTime = comGenTime;
    }

    public Integer getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public Long getAcBillId() {
        return acBillId;
    }

    public void setAcBillId(Long acBillId) {
        this.acBillId = acBillId;
    }
}
