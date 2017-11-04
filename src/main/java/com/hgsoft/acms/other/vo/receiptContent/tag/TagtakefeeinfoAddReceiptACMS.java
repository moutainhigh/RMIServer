package com.hgsoft.acms.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签提货金额登记回执
 * Created by wiki on 2017/8/21.
 */
public class TagtakefeeinfoAddReceiptACMS extends BaseReceiptContent {
    /**
     * 缴款方式code
     */
    private String takeFeeChargeTypeCode;
    /**
     * 缴款方式name
     */
    private String takeFeeChargeTypeName;
    /**
     * 缴款金额
     */
    private String takeFeeChargeFee;
    /**
     * 银行账号/缴款单号
     */
    private String takeFeePayAccount;
    /**
     * POS缴款流水号
     */
    private String posId;
    /**
     * 到账金额(转账)
     */
    private String bankTransferBalance;
    /**
     * 到账时间(转账)
     */
    private String bankTransferArrivalTime;
    /**
     * 备    注
     */
    private String takeFeeMemo;

    public TagtakefeeinfoAddReceiptACMS() {
    }

    /**
     *
     * @param takeFeeChargeTypeCode 缴款方式code
     * @param takeFeeChargeTypeName 缴款方式name
     * @param takeFeeChargeFee 缴款金额
     * @param takeFeePayAccount 银行账号/缴款单号
     * @param posId POS缴款流水号
     * @param bankTransferBalance 到账金额(转账)
     * @param bankTransferArrivalTime 到账时间(转账)
     * @param takeFeeMemo 备    注
     */
    public TagtakefeeinfoAddReceiptACMS(String takeFeeChargeTypeCode, String takeFeeChargeTypeName, String takeFeeChargeFee, String takeFeePayAccount, String posId, String bankTransferBalance, String bankTransferArrivalTime, String takeFeeMemo) {
        this.takeFeeChargeTypeCode = takeFeeChargeTypeCode;
        this.takeFeeChargeTypeName = takeFeeChargeTypeName;
        this.takeFeeChargeFee = takeFeeChargeFee;
        this.takeFeePayAccount = takeFeePayAccount;
        this.posId = posId;
        this.bankTransferBalance = bankTransferBalance;
        this.bankTransferArrivalTime = bankTransferArrivalTime;
        this.takeFeeMemo = takeFeeMemo;
    }

    public String getTakeFeeChargeTypeCode() {
        return takeFeeChargeTypeCode;
    }

    public void setTakeFeeChargeTypeCode(String takeFeeChargeTypeCode) {
        this.takeFeeChargeTypeCode = takeFeeChargeTypeCode;
    }

    public String getTakeFeeChargeTypeName() {
        return takeFeeChargeTypeName;
    }

    public void setTakeFeeChargeTypeName(String takeFeeChargeTypeName) {
        this.takeFeeChargeTypeName = takeFeeChargeTypeName;
    }

    public String getTakeFeeChargeFee() {
        return takeFeeChargeFee;
    }

    public void setTakeFeeChargeFee(String takeFeeChargeFee) {
        this.takeFeeChargeFee = takeFeeChargeFee;
    }

    public String getTakeFeePayAccount() {
        return takeFeePayAccount;
    }

    public void setTakeFeePayAccount(String takeFeePayAccount) {
        this.takeFeePayAccount = takeFeePayAccount;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getBankTransferBalance() {
        return bankTransferBalance;
    }

    public void setBankTransferBalance(String bankTransferBalance) {
        this.bankTransferBalance = bankTransferBalance;
    }

    public String getBankTransferArrivalTime() {
        return bankTransferArrivalTime;
    }

    public void setBankTransferArrivalTime(String bankTransferArrivalTime) {
        this.bankTransferArrivalTime = bankTransferArrivalTime;
    }

    public String getTakeFeeMemo() {
        return takeFeeMemo;
    }

    public void setTakeFeeMemo(String takeFeeMemo) {
        this.takeFeeMemo = takeFeeMemo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(super.toString());
        sb.append(",\"takeFeeChargeTypeCode\":\"").append(takeFeeChargeTypeCode).append('\"');
        sb.append(",\"takeFeeChargeTypeName\":\"").append(takeFeeChargeTypeName).append('\"');
        sb.append(",\"takeFeeChargeFee\":\"").append(takeFeeChargeFee).append('\"');
        sb.append(",\"takeFeePayAccount\":\"").append(takeFeePayAccount).append('\"');
        sb.append(",\"posId\":\"").append(posId).append('\"');
        sb.append(",\"bankTransferBalance\":\"").append(bankTransferBalance).append('\"');
        sb.append(",\"bankTransferArrivalTime\":\"").append(bankTransferArrivalTime).append('\"');
        sb.append(",\"takeFeeMemo\":\"").append(takeFeeMemo).append('\"');
        sb.append('}');
        return sb.toString();
    }
}


