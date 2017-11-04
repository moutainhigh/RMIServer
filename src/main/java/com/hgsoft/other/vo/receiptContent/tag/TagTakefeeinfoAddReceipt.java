package com.hgsoft.other.vo.receiptContent.tag;

import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;

/**
 * 电子标签提货金额登记回执
 * Created by wiki on 2017/8/21.
 */
public class TagTakefeeinfoAddReceipt extends BaseReceiptContent {
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
     * 银行账号
     */
    private String takeFeePayAccount;
    /**
     * 缴款单号
     */
    private String takeFeeVoucherNo;
    /**
     * POS缴款流水号
     */
    private String posId;
    /**
     * 银行账号(转账)
     */
    private String bankTransferNo;
    /**
     * 银行账户名称(转账)
     */
    private String bankTransferPayName;
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

    public String getTakeFeeVoucherNo() {
        return takeFeeVoucherNo;
    }

    public void setTakeFeeVoucherNo(String takeFeeVoucherNo) {
        this.takeFeeVoucherNo = takeFeeVoucherNo;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getBankTransferNo() {
        return bankTransferNo;
    }

    public void setBankTransferNo(String bankTransferNo) {
        this.bankTransferNo = bankTransferNo;
    }

    public String getBankTransferPayName() {
        return bankTransferPayName;
    }

    public void setBankTransferPayName(String bankTransferPayName) {
        this.bankTransferPayName = bankTransferPayName;
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
}


