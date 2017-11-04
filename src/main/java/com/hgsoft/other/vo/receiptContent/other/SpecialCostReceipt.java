package com.hgsoft.other.vo.receiptContent.other;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 特殊费用收取回执
 * Created by wiki on 2017/9/12.
 */
public class SpecialCostReceipt extends BaseReceiptContent {
    /**
     * 二级编码
     */
    private String customerSecondNo;
    /**
     * 二级编码名称
     */
    private String customerSecondName;
    /**
     * 费用类别
     */
    private String specialCostType;
    /**
     * 费用子类别
     */
    private String specialCostSubType;
    /**
     * 金额（元）
     */
    private String charge;

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

    public String getSpecialCostType() {
        return specialCostType;
    }

    public void setSpecialCostType(String specialCostType) {
        this.specialCostType = specialCostType;
    }

    public String getSpecialCostSubType() {
        return specialCostSubType;
    }

    public void setSpecialCostSubType(String specialCostSubType) {
        this.specialCostSubType = specialCostSubType;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }
}
