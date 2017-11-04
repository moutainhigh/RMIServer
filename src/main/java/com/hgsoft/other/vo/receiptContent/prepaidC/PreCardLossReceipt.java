package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡挂失回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardLossReceipt extends BaseReceiptContent {
    /**
     * 储值卡号
     */
    private String preCardNo;

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
    }
}
