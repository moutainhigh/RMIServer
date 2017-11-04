package com.hgsoft.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡充值登记回执
 * Created by wiki on 2017/9/13.
 */
public class AddRegReceipt extends BaseReceiptContent {
    /**
     * 数组json格式卡号和金额
     */
    private String cardAndMoneyJsonData;

    public String getCardAndMoneyJsonData() {
        return cardAndMoneyJsonData;
    }

    public void setCardAndMoneyJsonData(String cardAndMoneyJsonData) {
        this.cardAndMoneyJsonData = cardAndMoneyJsonData;
    }
}
