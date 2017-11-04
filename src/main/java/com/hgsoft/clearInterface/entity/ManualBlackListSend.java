package com.hgsoft.clearInterface.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 手工黑名单
 * Created by 孙晓伟
 * file : ManualBlackListSend.java
 * date : 2017/8/11
 * time : 14:52
 */
public class ManualBlackListSend implements Serializable{
    private static final long serialVersionUID = -7736648473968172360L;


    private String cardCode;

    private String cardType;

    private String srtType;

    private String darkReason;

    private Timestamp reqTime;

    private String remark;

    private Timestamp updateTime;

    private Long boardListNo;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getSrtType() {
        return srtType;
    }

    public void setSrtType(String srtType) {
        this.srtType = srtType;
    }

    public String getDarkReason() {
        return darkReason;
    }

    public void setDarkReason(String darkReason) {
        this.darkReason = darkReason;
    }

    public Timestamp getReqTime() {
        return reqTime;
    }

    public void setReqTime(Timestamp reqTime) {
        this.reqTime = reqTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getBoardListNo() {
        return boardListNo;
    }

    public void setBoardListNo(Long boardListNo) {
        this.boardListNo = boardListNo;
    }
}
