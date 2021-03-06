package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡充值冲正回执(人工充值冲正)
 * Created by wiki on 2017/8/21.
 */
public class PreCardRechargeCorrectReceiptACMS extends BaseReceiptContent {

    /**
     * 原充值回执编号
     */
    private String rechargeReciptNo;
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 冲正前余额
     */
    private String preCardBeforebalance;
    /**
     * 冲正金额
     */
    private String correctMoney;
    /**
     * 冲正后余额
     */
    private String preCardBalance;

    public String getRechargeReciptNo() {
        return rechargeReciptNo;
    }

    public void setRechargeReciptNo(String rechargeReciptNo) {
        this.rechargeReciptNo = rechargeReciptNo;
    }

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getPreCardBeforebalance() {
        return preCardBeforebalance;
    }

    public void setPreCardBeforebalance(String preCardBeforebalance) {
        this.preCardBeforebalance = preCardBeforebalance;
    }

    public String getCorrectMoney() {
        return correctMoney;
    }

    public void setCorrectMoney(String correctMoney) {
        this.correctMoney = correctMoney;
    }

    public String getPreCardBalance() {
        return preCardBalance;
    }

    public void setPreCardBalance(String preCardBalance) {
        this.preCardBalance = preCardBalance;
    }
}
