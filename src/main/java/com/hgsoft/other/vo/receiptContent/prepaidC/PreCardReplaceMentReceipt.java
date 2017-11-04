package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡挂失补领回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardReplaceMentReceipt extends BaseReceiptContent {

    /**
     * 原挂失回执编号
     */
    private String lostReceiptNo;
    /**
     * 原储值卡号
     */
    private String oldPreCardNo;
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 工本费
     */
    private String preCardCost;
    /**
     * 资金争议期
     */
    private String expireTime;

    public String getLostReceiptNo() {
        return lostReceiptNo;
    }

    public void setLostReceiptNo(String lostReceiptNo) {
        this.lostReceiptNo = lostReceiptNo;
    }

    public String getOldPreCardNo() {
        return oldPreCardNo;
    }

    public void setOldPreCardNo(String oldPreCardNo) {
        this.oldPreCardNo = oldPreCardNo;
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

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
