package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangzhongji on 17/9/23.
 */
public class AccBankListReturn implements Serializable {

    private static final long serialVersionUID = 5058254530361693604L;

    private Long id;
    private Long boardListNo;
    private Integer commandType;
    private Date gentime;
    private String bankNo;
    private String acbAccount;
    private BigDecimal income;
    private Integer status;
    private Date hdlDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardListNo() {
        return boardListNo;
    }

    public void setBoardListNo(Long boardListNo) {
        this.boardListNo = boardListNo;
    }

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
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

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getHdlDatetime() {
        return hdlDatetime;
    }

    public void setHdlDatetime(Date hdlDatetime) {
        this.hdlDatetime = hdlDatetime;
    }

    public enum CommandTypeEnum {
        SAME_BANK("银行本行划账",1), CROSS_BANK("银行跨行划扣",2);

        private Integer value;

        private String name;

        /** Internal constructor */
        CommandTypeEnum(String name, Integer value) {
            this.value = value;
            this.name=name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(Integer value) {
            String name = null;
            for (CommandTypeEnum tempEnum : CommandTypeEnum.values()) {
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

    public enum StatusEnum {
        SUCCESS("转账成功",0), MONEY_NOT_ENOUGH("账户余额不足",1), SRC_ACC_NOT_EXISTS("源账户不存在", 2),
        DST_ACC_NOT_EXISTS("目的账户不存在", 3), NO_AUTH("未授权", 4), OTHER("其他", 5);

        private Integer value;

        private String name;

        /** Internal constructor */
        StatusEnum(String name, Integer value) {
            this.value = value;
            this.name=name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(Integer value) {
            String name = null;
            for (StatusEnum tempEnum : StatusEnum.values()) {
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
