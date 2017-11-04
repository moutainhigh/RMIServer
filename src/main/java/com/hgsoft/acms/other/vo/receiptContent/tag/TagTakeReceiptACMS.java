package com.hgsoft.acms.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签提货回执
 * Created by wiki on 2017/8/22.
 */
public class TagTakeReceiptACMS extends BaseReceiptContent{
    /**
     * 起始标签号
     */
    private String takeBeginTagNo;
    /**
     * 结束标签号
     */
    private String takeEndTagNo;
    /**
     * 提货数量
     */
    private String takeAmount;
    /**
     * 标签单价
     */
    private String takeTagPrice;
    /**
     * 合计
     */
    private String taketotalPrice;
    /**
     * 提货后余额
     */
    private String takeBalance;
    /**
     * 特别说明
     */
    private String explanation;

    public String getTakeBeginTagNo() {
        return takeBeginTagNo;
    }

    public void setTakeBeginTagNo(String takeBeginTagNo) {
        this.takeBeginTagNo = takeBeginTagNo;
    }

    public String getTakeEndTagNo() {
        return takeEndTagNo;
    }

    public void setTakeEndTagNo(String takeEndTagNo) {
        this.takeEndTagNo = takeEndTagNo;
    }

    public String getTakeAmount() {
        return takeAmount;
    }

    public void setTakeAmount(String takeAmount) {
        this.takeAmount = takeAmount;
    }

    public String getTakeTagPrice() {
        return takeTagPrice;
    }

    public void setTakeTagPrice(String takeTagPrice) {
        this.takeTagPrice = takeTagPrice;
    }

    public String getTaketotalPrice() {
        return taketotalPrice;
    }

    public void setTaketotalPrice(String taketotalPrice) {
        this.taketotalPrice = taketotalPrice;
    }

    public String getTakeBalance() {
        return takeBalance;
    }

    public void setTakeBalance(String takeBalance) {
        this.takeBalance = takeBalance;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
