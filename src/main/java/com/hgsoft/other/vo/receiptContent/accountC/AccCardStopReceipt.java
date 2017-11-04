package com.hgsoft.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记账卡终止使用回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardStopReceipt extends BaseReceiptContent {
    /**
     * 记账卡号
     */
    private String accCardNo;
    /**
     * 终止使用方式
     */
    private String bussState;

    public String getAccCardNo() {
        return accCardNo;
    }

    public void setAccCardNo(String accCardNo) {
        this.accCardNo = accCardNo;
    }

    public String getBussState() {
        return bussState;
    }

    public void setBussState(String bussState) {
        this.bussState = bussState;
    }
}
