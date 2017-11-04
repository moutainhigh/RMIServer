package com.hgsoft.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签注销回执
 * Created by wiki on 2017/8/22.
 */
public class TagCannelReceipt extends BaseReceiptContent {

    /**
     * 标 签 号
     */
    private String tagNo;

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }
}
