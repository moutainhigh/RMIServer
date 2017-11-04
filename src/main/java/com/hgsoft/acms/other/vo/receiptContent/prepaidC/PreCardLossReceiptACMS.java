package com.hgsoft.acms.other.vo.receiptContent.prepaidC;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 储值卡挂失回执
 * Created by wiki on 2017/8/21.
 */
public class PreCardLossReceiptACMS extends BaseReceiptContent {
    /**
     * 储值卡号
     */
    private String preCardNo;
    /**
     * 特别说明
     */
    private String explanation;

    public PreCardLossReceiptACMS() {
    }

    /**
     *
     * @param preCardNo 储值卡号
     * @param explanation 特别说明
     */
    public PreCardLossReceiptACMS(String preCardNo, String explanation) {
        this.preCardNo = preCardNo;
        this.explanation = explanation;
    }

    public String getPreCardNo() {
        return preCardNo;
    }

    public void setPreCardNo(String preCardNo) {
        this.preCardNo = preCardNo;
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
        sb.append(",\"preCardNo\":\"").append(preCardNo).append('\"');
        sb.append(",\"explanation\":\"").append(explanation).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
