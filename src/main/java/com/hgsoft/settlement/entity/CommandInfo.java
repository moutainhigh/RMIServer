package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommandInfo implements Serializable {

    private static final long serialVersionUID = 3991777159216936747L;

    private Long id;
    private Date gentime;
    private String bankNo;
    private String acbAccount;
    private String feeType;
    private String cardCode;
    private String detailNo;
    private String roadCode;
    private BigDecimal realToll;
    private BigDecimal income;
    private Long boardListNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGentime() {
        return gentime;
    }

    public void setGentime(Date gentime) {
        this.gentime = gentime;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getAcbAccount() {
        return acbAccount;
    }

    public void setAcbAccount(String acbAccount) {
        this.acbAccount = acbAccount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    public String getRoadCode() {
        return roadCode;
    }

    public void setRoadCode(String roadCode) {
        this.roadCode = roadCode;
    }

    public BigDecimal getRealToll() {
        return realToll;
    }

    public void setRealToll(BigDecimal realToll) {
        this.realToll = realToll;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Long getBoardListNo() {
        return boardListNo;
    }

    public void setBoardListNo(Long boardListNo) {
        this.boardListNo = boardListNo;
    }

    public boolean isSameAcBankReturn(CommandInfo commandInfo) {
        if (this.gentime.getTime() == commandInfo.getGentime().getTime()
                && this.acbAccount.equals(commandInfo.getAcbAccount())
                && this.bankNo.equals(commandInfo.getBankNo())) {
            return true;
        }
        return false;
    }

    public enum FeeTypeEnum {
        TOLL_FEE("通行费","1"), LATE_FEE("滞纳金","2"), SPECIAL_COST("特殊优惠", "3");

        private String value;

        private String name;

        /** Internal constructor */
        FeeTypeEnum(String name, String value) {
            this.value = value;
            this.name=name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(String value) {
            String name = null;
            for (FeeTypeEnum tempEnum : FeeTypeEnum.values()) {
                if (tempEnum.getValue().equals(value)) {
                    name = tempEnum.getName();
                    break;
                }
            }
            return name;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
