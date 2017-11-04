package com.hgsoft.settlement.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangzhongji on 17/10/19.
 */
public class CardFeeData implements Serializable {

    private Long id;
    private Long boardListno;
    private String cardCode;
    private String feeType;
    private String returnFee;
    private Date balanceTime;
    private Date receviceTime;
    private String filename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getReturnFee() {
        return returnFee;
    }

    public void setReturnFee(String returnFee) {
        this.returnFee = returnFee;
    }

    public Date getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Date balanceTime) {
        this.balanceTime = balanceTime;
    }

    public Long getBoardListno() {
        return boardListno;
    }

    public void setBoardListno(Long boardListno) {
        this.boardListno = boardListno;
    }

    public Date getReceviceTime() {
        return receviceTime;
    }

    public void setReceviceTime(Date receviceTime) {
        this.receviceTime = receviceTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
