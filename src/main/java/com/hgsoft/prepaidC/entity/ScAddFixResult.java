package com.hgsoft.prepaidC.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ScAddFixResult implements java.io.Serializable {


    private static final long serialVersionUID = -4893380640097989735L;

    private Long id;
    private Long reqId;
    private String cardNo;
    private BigDecimal balReq;
    private BigDecimal moneyReq;
    private BigDecimal returnMoneyReq;
    private BigDecimal transfersumReq;
    private String randomReq;
    private String transverReq;
    private String keyverReq;
    private Long onlineTradeNoReq;
    private Long offlineTradeNoReq;
    private String termnoReq;
    private String psamnoReq;
    private Date timeReq;
    private Date dealtimeReq;
    private String dealType;
    private String placeNo;
    private String opCode;

    private Long lastid;
    private BigDecimal lastMoneyReq;
    private BigDecimal lastReturnMoneyReq;
    private BigDecimal lasTransfersumReq;
    private Long lastOnlineTradeNoReq;
    private Long lastOfflineTradeNoReq;
    private Date lastTimeReq;
    private Date lastDealtimeReq;
    private String lastDealType;
    private Integer lastStatus;
    private Long nextId;
    private BigDecimal nextMoneyReq;
    private BigDecimal nextReturnMoneyReq;
    private BigDecimal nexTransfersumReq;
    private Long nextOnlineTradeNoReq;
    private Long nextOfflineTradeNoReq;
    private Date nextTimeReq;
    private Date nextDealtimeReq;
    private String nextDealType;
    private Integer nextStatus;

    private String lastDetailNo;
    private Long lastOfflineTradeNo;
    private Date lastExtime;
    private BigDecimal paycardBalance;
    private String nextDetailNo;
    private Long nextOfflineTradeNo;
    private Date nextExtime;
    private BigDecimal nextPaycardBalanceBefore;
    private BigDecimal nextPaycardBalance;
    private BigDecimal nextEtcMoney;

    private Integer result;
    private Date hdlTime;
    private Integer checkResult;
    private Integer managerNo;
    private Integer dealStatus;
    private Date dealtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getBalReq() {
        return balReq;
    }

    public void setBalReq(BigDecimal balReq) {
        this.balReq = balReq;
    }

    public BigDecimal getMoneyReq() {
        return moneyReq;
    }

    public void setMoneyReq(BigDecimal moneyReq) {
        this.moneyReq = moneyReq;
    }

    public BigDecimal getReturnMoneyReq() {
        return returnMoneyReq;
    }

    public void setReturnMoneyReq(BigDecimal returnMoneyReq) {
        this.returnMoneyReq = returnMoneyReq;
    }

    public BigDecimal getTransfersumReq() {
        return transfersumReq;
    }

    public void setTransfersumReq(BigDecimal transfersumReq) {
        this.transfersumReq = transfersumReq;
    }

    public String getRandomReq() {
        return randomReq;
    }

    public void setRandomReq(String randomReq) {
        this.randomReq = randomReq;
    }

    public String getTransverReq() {
        return transverReq;
    }

    public void setTransverReq(String transverReq) {
        this.transverReq = transverReq;
    }

    public String getKeyverReq() {
        return keyverReq;
    }

    public void setKeyverReq(String keyverReq) {
        this.keyverReq = keyverReq;
    }

    public Long getOnlineTradeNoReq() {
        return onlineTradeNoReq;
    }

    public void setOnlineTradeNoReq(Long onlineTradeNoReq) {
        this.onlineTradeNoReq = onlineTradeNoReq;
    }

    public Long getOfflineTradeNoReq() {
        return offlineTradeNoReq;
    }

    public void setOfflineTradeNoReq(Long offlineTradeNoReq) {
        this.offlineTradeNoReq = offlineTradeNoReq;
    }

    public String getTermnoReq() {
        return termnoReq;
    }

    public void setTermnoReq(String termnoReq) {
        this.termnoReq = termnoReq;
    }

    public String getPsamnoReq() {
        return psamnoReq;
    }

    public void setPsamnoReq(String psamnoReq) {
        this.psamnoReq = psamnoReq;
    }

    public Date getTimeReq() {
        return timeReq;
    }

    public void setTimeReq(Date timeReq) {
        this.timeReq = timeReq;
    }

    public Date getDealtimeReq() {
        return dealtimeReq;
    }

    public void setDealtimeReq(Date dealtimeReq) {
        this.dealtimeReq = dealtimeReq;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getPlaceNo() {
        return placeNo;
    }

    public void setPlaceNo(String placeNo) {
        this.placeNo = placeNo;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public Long getLastid() {
        return lastid;
    }

    public void setLastid(Long lastid) {
        this.lastid = lastid;
    }

    public BigDecimal getLastMoneyReq() {
        return lastMoneyReq;
    }

    public void setLastMoneyReq(BigDecimal lastMoneyReq) {
        this.lastMoneyReq = lastMoneyReq;
    }

    public BigDecimal getLastReturnMoneyReq() {
        return lastReturnMoneyReq;
    }

    public void setLastReturnMoneyReq(BigDecimal lastReturnMoneyReq) {
        this.lastReturnMoneyReq = lastReturnMoneyReq;
    }

    public BigDecimal getLasTransfersumReq() {
        return lasTransfersumReq;
    }

    public void setLasTransfersumReq(BigDecimal lasTransfersumReq) {
        this.lasTransfersumReq = lasTransfersumReq;
    }

    public Long getLastOnlineTradeNoReq() {
        return lastOnlineTradeNoReq;
    }

    public void setLastOnlineTradeNoReq(Long lastOnlineTradeNoReq) {
        this.lastOnlineTradeNoReq = lastOnlineTradeNoReq;
    }

    public Long getLastOfflineTradeNoReq() {
        return lastOfflineTradeNoReq;
    }

    public void setLastOfflineTradeNoReq(Long lastOfflineTradeNoReq) {
        this.lastOfflineTradeNoReq = lastOfflineTradeNoReq;
    }

    public Date getLastTimeReq() {
        return lastTimeReq;
    }

    public void setLastTimeReq(Date lastTimeReq) {
        this.lastTimeReq = lastTimeReq;
    }

    public Date getLastDealtimeReq() {
        return lastDealtimeReq;
    }

    public void setLastDealtimeReq(Date lastDealtimeReq) {
        this.lastDealtimeReq = lastDealtimeReq;
    }

    public String getLastDealType() {
        return lastDealType;
    }

    public void setLastDealType(String lastDealType) {
        this.lastDealType = lastDealType;
    }

    public Integer getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }

    public BigDecimal getNextMoneyReq() {
        return nextMoneyReq;
    }

    public void setNextMoneyReq(BigDecimal nextMoneyReq) {
        this.nextMoneyReq = nextMoneyReq;
    }

    public BigDecimal getNextReturnMoneyReq() {
        return nextReturnMoneyReq;
    }

    public void setNextReturnMoneyReq(BigDecimal nextReturnMoneyReq) {
        this.nextReturnMoneyReq = nextReturnMoneyReq;
    }

    public BigDecimal getNexTransfersumReq() {
        return nexTransfersumReq;
    }

    public void setNexTransfersumReq(BigDecimal nexTransfersumReq) {
        this.nexTransfersumReq = nexTransfersumReq;
    }

    public Long getNextOnlineTradeNoReq() {
        return nextOnlineTradeNoReq;
    }

    public void setNextOnlineTradeNoReq(Long nextOnlineTradeNoReq) {
        this.nextOnlineTradeNoReq = nextOnlineTradeNoReq;
    }

    public Long getNextOfflineTradeNoReq() {
        return nextOfflineTradeNoReq;
    }

    public void setNextOfflineTradeNoReq(Long nextOfflineTradeNoReq) {
        this.nextOfflineTradeNoReq = nextOfflineTradeNoReq;
    }

    public Date getNextTimeReq() {
        return nextTimeReq;
    }

    public void setNextTimeReq(Date nextTimeReq) {
        this.nextTimeReq = nextTimeReq;
    }

    public Date getNextDealtimeReq() {
        return nextDealtimeReq;
    }

    public void setNextDealtimeReq(Date nextDealtimeReq) {
        this.nextDealtimeReq = nextDealtimeReq;
    }

    public String getNextDealType() {
        return nextDealType;
    }

    public void setNextDealType(String nextDealType) {
        this.nextDealType = nextDealType;
    }

    public Integer getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(Integer nextStatus) {
        this.nextStatus = nextStatus;
    }

    public String getLastDetailNo() {
        return lastDetailNo;
    }

    public void setLastDetailNo(String lastDetailNo) {
        this.lastDetailNo = lastDetailNo;
    }

    public Long getLastOfflineTradeNo() {
        return lastOfflineTradeNo;
    }

    public void setLastOfflineTradeNo(Long lastOfflineTradeNo) {
        this.lastOfflineTradeNo = lastOfflineTradeNo;
    }

    public Date getLastExtime() {
        return lastExtime;
    }

    public void setLastExtime(Date lastExtime) {
        this.lastExtime = lastExtime;
    }

    public BigDecimal getPaycardBalance() {
        return paycardBalance;
    }

    public void setPaycardBalance(BigDecimal paycardBalance) {
        this.paycardBalance = paycardBalance;
    }

    public String getNextDetailNo() {
        return nextDetailNo;
    }

    public void setNextDetailNo(String nextDetailNo) {
        this.nextDetailNo = nextDetailNo;
    }

    public Long getNextOfflineTradeNo() {
        return nextOfflineTradeNo;
    }

    public void setNextOfflineTradeNo(Long nextOfflineTradeNo) {
        this.nextOfflineTradeNo = nextOfflineTradeNo;
    }

    public Date getNextExtime() {
        return nextExtime;
    }

    public void setNextExtime(Date nextExtime) {
        this.nextExtime = nextExtime;
    }

    public BigDecimal getNextPaycardBalanceBefore() {
        return nextPaycardBalanceBefore;
    }

    public void setNextPaycardBalanceBefore(BigDecimal nextPaycardBalanceBefore) {
        this.nextPaycardBalanceBefore = nextPaycardBalanceBefore;
    }

    public BigDecimal getNextPaycardBalance() {
        return nextPaycardBalance;
    }

    public void setNextPaycardBalance(BigDecimal nextPaycardBalance) {
        this.nextPaycardBalance = nextPaycardBalance;
    }

    public BigDecimal getNextEtcMoney() {
        return nextEtcMoney;
    }

    public void setNextEtcMoney(BigDecimal nextEtcMoney) {
        this.nextEtcMoney = nextEtcMoney;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getHdlTime() {
        return hdlTime;
    }

    public void setHdlTime(Date hdlTime) {
        this.hdlTime = hdlTime;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(Integer managerNo) {
        this.managerNo = managerNo;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Date getDealtime() {
        return dealtime;
    }

    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }

    public enum Result {
        FAIL("失败", 0),
        SUCCESS("成功", 1),
        ERROR("异常", -1);

        private String name;
        private Integer value;

        /**
         * Internal constructor
         */
        Result(String name, Integer value) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(Integer value) {
            String name = null;
            for (Result tempEnum : Result.values()) {
                if (tempEnum.getValue().equals(value)) {
                    name = tempEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    public enum CheckResult {
        NOT_CHECK("未审核", 0),
        CHECKED_THROUGH("审核通过", 1),
        CHECKED_NOT_THROUGH("审核不通过", 2);

        private String name;
        private Integer value;

        /**
         * Internal constructor
         */
        CheckResult(String name, Integer value) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(Integer value) {
            String name = null;
            for (CheckResult tempEnum : CheckResult.values()) {
                if (tempEnum.getValue().equals(value)) {
                    name = tempEnum.getName();
                    break;
                }
            }
            return name;
        }
    }

    public enum DealStatus {
        NOT_DEAL("未处理", 0),
        DEALT("已处理", 1);

        private String name;
        private Integer value;

        /**
         * Internal constructor
         */
        DealStatus(String name, Integer value) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(Integer value) {
            String name = null;
            for (DealStatus tempEnum : DealStatus.values()) {
                if (tempEnum.getValue().equals(value)) {
                    name = tempEnum.getName();
                    break;
                }
            }
            return name;
        }
    }
}
