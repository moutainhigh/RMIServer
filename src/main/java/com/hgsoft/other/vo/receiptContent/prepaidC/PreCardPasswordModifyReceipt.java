package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡消费密码更改回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardPasswordModifyReceipt extends BaseReceiptContent {
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
