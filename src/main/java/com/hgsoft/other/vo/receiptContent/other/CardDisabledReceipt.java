package com.hgsoft.other.vo.receiptContent.other;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 卡片挂起回执(停用)
 * Created by wiki on 2017/9/11.
 */
public class CardDisabledReceipt extends BaseReceiptContent{
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡片类型
     */
    private String cardType;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
