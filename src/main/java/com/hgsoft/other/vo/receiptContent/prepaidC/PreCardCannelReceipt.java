package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡终止使用回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardCannelReceipt extends BaseReceiptContent {

    /**
     * 储值卡卡号
     */
    private String preCardNo;
    /**
     * 终止使用方式
     */
    private String cannelWay;
    /**
     * 卡内余额
     */
    private String preCardBalance;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getCannelWay() {
        return cannelWay;
    }

    public void setCannelWay(String cannelWay) {
        this.cannelWay = cannelWay;
    }

    public String getPreCardBalance() {
        return preCardBalance;
    }

    public void setPreCardBalance(String preCardBalance) {
        this.preCardBalance = preCardBalance;
    }

}
