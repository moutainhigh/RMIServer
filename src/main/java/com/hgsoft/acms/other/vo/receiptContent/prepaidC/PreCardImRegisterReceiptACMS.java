package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡充值回执(直充)
 * Created by wiki on 2017/8/21.
 */
public class PreCardImRegisterReceiptACMS extends BaseReceiptContent{

    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 缴款方式
     */
    private String rechargePayMentType;
    /**
     * 银行账号
     */
    private String rechargePayMentNo;
    /**
     * POS缴款流水号
     */
    private String rechargePosId;
    /**
     * 充值前余额
     */
    private String preCardBeforebalance;
    /**
     * 充值金额
     */
    private String preCardRealPrice;
    /**
     * 优惠返还
     */
    private String mainCountPreferentialBalance;
    /**
     * 资金转移
     */
    private String preCardTransferSum;
    /**
     * 充值后余额
     */
    private String preCardBalance;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }

    public String getRechargePayMentType() {
        return rechargePayMentType;
    }

    public void setRechargePayMentType(String rechargePayMentType) {
        this.rechargePayMentType = rechargePayMentType;
    }

    public String getRechargePayMentNo() {
        return rechargePayMentNo;
    }

    public void setRechargePayMentNo(String rechargePayMentNo) {
        this.rechargePayMentNo = rechargePayMentNo;
    }

    public String getRechargePosId() {
        return rechargePosId;
    }

    public void setRechargePosId(String rechargePosId) {
        this.rechargePosId = rechargePosId;
    }

    public String getPreCardBeforebalance() {
        return preCardBeforebalance;
    }

    public void setPreCardBeforebalance(String preCardBeforebalance) {
        this.preCardBeforebalance = preCardBeforebalance;
    }

    public String getPreCardRealPrice() {
        return preCardRealPrice;
    }

    public void setPreCardRealPrice(String preCardRealPrice) {
        this.preCardRealPrice = preCardRealPrice;
    }

    public String getMainCountPreferentialBalance() {
        return mainCountPreferentialBalance;
    }

    public void setMainCountPreferentialBalance(String mainCountPreferentialBalance) {
        this.mainCountPreferentialBalance = mainCountPreferentialBalance;
    }

    public String getPreCardTransferSum() {
        return preCardTransferSum;
    }

    public void setPreCardTransferSum(String preCardTransferSum) {
        this.preCardTransferSum = preCardTransferSum;
    }

    public String getPreCardBalance() {
        return preCardBalance;
    }

    public void setPreCardBalance(String preCardBalance) {
        this.preCardBalance = preCardBalance;
    }
}
