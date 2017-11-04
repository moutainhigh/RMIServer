package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记账卡解挂回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardCanceLossReceiptACMS extends BaseReceiptContent {

    /**
     * 原挂失回执编号
     */
    private String lostReceiptNo;
    /**
     * 记账卡号
     */
    private String accCardNo;
    /**
     * 特别说明
     */
    private String explanation;

    public String getLostReceiptNo() {
        return lostReceiptNo;
    }

    public void setLostReceiptNo(String lostReceiptNo) {
        this.lostReceiptNo = lostReceiptNo;
    }

    public String getAccCardNo() {
        return accCardNo;
    }

    public void setAccCardNo(String accCardNo) {
        this.accCardNo = accCardNo;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
