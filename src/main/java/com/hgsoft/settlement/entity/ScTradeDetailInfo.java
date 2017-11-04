package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScTradeDetailInfo implements Serializable {


    private static final long serialVersionUID = -929126780686341263L;

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
    private String tradeType;
    private BigDecimal costAmt;
    private BigDecimal credited;
    private BigDecimal passOughtAmt;
    private String passProvince;
    private Integer inProvinceFlag;
    private Date balanceTime;
    private Date genTime;
    private Long scBillId;

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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(BigDecimal costAmt) {
        this.costAmt = costAmt;
    }

    public BigDecimal getCredited() {
        return credited;
    }

    public void setCredited(BigDecimal credited) {
        this.credited = credited;
    }

    public BigDecimal getPassOughtAmt() {
        return passOughtAmt;
    }

    public void setPassOughtAmt(BigDecimal passOughtAmt) {
        this.passOughtAmt = passOughtAmt;
    }

    public String getPassProvince() {
        return passProvince;
    }

    public void setPassProvince(String passProvince) {
        this.passProvince = passProvince;
    }

    public Integer getInProvinceFlag() {
        return inProvinceFlag;
    }

    public void setInProvinceFlag(Integer inProvinceFlag) {
        this.inProvinceFlag = inProvinceFlag;
    }

    public Date getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Date balanceTime) {
        this.balanceTime = balanceTime;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public Long getScBillId() {
        return scBillId;
    }

    public void setScBillId(Long scBillId) {
        this.scBillId = scBillId;
    }
}
