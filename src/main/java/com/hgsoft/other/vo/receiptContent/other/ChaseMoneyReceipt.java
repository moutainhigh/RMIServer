package com.hgsoft.other.vo.receiptContent.other;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 追款回执
 * Created by wiki on 2017/9/11.
 */
public class ChaseMoneyReceipt extends BaseReceiptContent {
    /**
     * 二级编码
     */
    private String customerSecondNo;
    /**
     * 二级编码名称
     */
    private String customerSecondName;
    /**
     * 追款金额
     */
    private String proceeds;

    public String getCustomerSecondNo() {
        return customerSecondNo;
    }

    public void setCustomerSecondNo(String customerSecondNo) {
        this.customerSecondNo = customerSecondNo;
    }

    public String getCustomerSecondName() {
        return customerSecondName;
    }

    public void setCustomerSecondName(String customerSecondName) {
        this.customerSecondName = customerSecondName;
    }

    public String getProceeds() {
        return proceeds;
    }

    public void setProceeds(String proceeds) {
        this.proceeds = proceeds;
    }
}
