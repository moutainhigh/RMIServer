package com.hgsoft.acms.other.vo.receiptContent.accountC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 记账卡挂失回执
 * Created by wiki on 2017/8/21.
 */
public class AccCardLossReceiptACMS extends BaseReceiptContent {
    /**
     * 记账卡号
     */
    private String accCardNo;
    /**
     * 特别说明
     */
    private String explanation;

    public AccCardLossReceiptACMS() {
    }

    /**
     *
     * @param accCardNo 记账卡号
     * @param explanation 特别说明
     */
    public AccCardLossReceiptACMS(String accCardNo, String explanation) {
        this.accCardNo = accCardNo;
        this.explanation = explanation;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(super.toString());
        sb.append(",\"accCardNo\":\"").append(accCardNo).append('\"');
        sb.append(",\"explanation\":\"").append(explanation).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
