package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡换卡回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardGetNewCardReceipt extends BaseReceiptContent {

    /**
     * 原 卡 号
     */
    private String oldPreCardNo;
    /**
     * 换卡方式
     */
    private String preCardNotCardStatus;
    /**
     * 新 卡 号
     */
    private String preCardNo;
    /**
     * 工 本 费
     */
    private String preCardCost;
    /**
     * 卡内余额
     */
    private String preCardOldCardBalance;
    /**
     * 资金争议期
     */
    private String expireTime;

    public String getOldPreCardNo() {
        return oldPreCardNo;
    }

    public void setOldPreCardNo(String oldPreCardNo) {
        this.oldPreCardNo = oldPreCardNo;
    }

    public String getPreCardNotCardStatus() {
        return preCardNotCardStatus;
    }

    public void setPreCardNotCardStatus(String preCardNotCardStatus) {
        this.preCardNotCardStatus = preCardNotCardStatus;
    }

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getPreCardCost() {
        return preCardCost;
    }

    public void setPreCardCost(String preCardCost) {
        this.preCardCost = preCardCost;
    }

    public String getPreCardOldCardBalance() {
        return preCardOldCardBalance;
    }

    public void setPreCardOldCardBalance(String preCardOldCardBalance) {
        this.preCardOldCardBalance = preCardOldCardBalance;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
