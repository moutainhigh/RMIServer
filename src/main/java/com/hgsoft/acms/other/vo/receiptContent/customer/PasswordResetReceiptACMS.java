package com.hgsoft.acms.other.vo.receiptContent.customer;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 客户服务密码重设回执
 * Created by wiki on 2017/8/21.
 */
public class PasswordResetReceiptACMS extends BaseReceiptContent {
    /**
     * 联系人
     */
    private String customerLinkMan;
    /**
     * 联系手机
     */
    private String customerMobile;

    public String getCustomerLinkMan() {
        return customerLinkMan;
    }

    public void setCustomerLinkMan(String customerLinkMan) {
        this.customerLinkMan = customerLinkMan;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }
}