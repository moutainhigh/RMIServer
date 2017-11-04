package com.hgsoft.system.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 粤通卡异常流水登记
 * @author Administrator zhuangyixia
 *
 */
public class ExceptionListNo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6811293170106094453L;
    
	private Long id;//
	private Date intoTime;//入口时间
	private String refundListNo;//营业厅退款客服流水号
	private String handleNo;//处理单号
	private Long outSiteInfo;//出口站信息ID
/*	private String outSiteInfoName;//出口站名称
*/	private Long outRoadInfo;//出口路段信息ID
	private String outRoadInfoName;//出口路段名称
	private String checkFlag;//复核标志
	private String cardNo;//粤通卡号
	private Date outTime;//出口时间
	private Date checkTime;//复核时间
	private String transitListNoType;//通行流水类型
	private String outLaneType;//出口车道类型
	private Long checkID;//复核人员ID
	private String checkName;//复核人员
	private String offTradeNo;//脱机交易号	
	private BigDecimal transitBalance;//通行余额
	private String lagInterceptFlag;//滞留拦截标志
	private String hexOffNo;//十六进制脱机序号
	private Date registerTime ;//登记时间
	private String sysBalanceFlag;//系统余额标志
	private Long registerID;//登记人ID
	private String registerName;//登记人
	private Long exceptionType;//异常类型ID
	private String exTypeName;//异常类型名称
	private BigDecimal receivable;//通行费/应收/退金额
	private String finishFlag;//完成标志
	private Date clearingTime;//清算时间
	private String outTradeNo;//出口交易流水号
	private Long inSiteInfo;//入口站信息ID
	/*private String inSiteInfoName;//入口站名称*/
	private Long inRoadInfo;//入口路段信息ID
	private String inRoadInfoName;//入口路段名称
	private String remark;//备注
	private BigDecimal proceeds;//实收/退金额
	private Date finishTime;//完成时间
	private String clearingListNo;//清算流水号
	private Long customPoint;//完成网点ID
	private String pointName;//完成网点
	private Long handleID;//处理人ID
	private String handleName;//处理人
	private Date updateTime;//更新时间
	private String currentCardNo;//前前粤通卡号
	private String cardType;//卡类型1.储值卡2. 记帐卡

	
	public ExceptionListNo() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getIntoTime() {
		return intoTime;
	}


	public void setIntoTime(Date intoTime) {
		this.intoTime = intoTime;
	}


	public String getRefundListNo() {
		return refundListNo;
	}


	public void setRefundListNo(String refundListNo) {
		this.refundListNo = refundListNo;
	}


	public String getHandleNo() {
		return handleNo;
	}


	public void setHandleNo(String handleNo) {
		this.handleNo = handleNo;
	}


	public Long getOutSiteInfo() {
		return outSiteInfo;
	}


	public void setOutSiteInfo(Long outSiteInfo) {
		this.outSiteInfo = outSiteInfo;
	}


	public Long getOutRoadInfo() {
		return outRoadInfo;
	}


	public void setOutRoadInfo(Long outRoadInfo) {
		this.outRoadInfo = outRoadInfo;
	}


	public String getOutRoadInfoName() {
		return outRoadInfoName;
	}


	public void setOutRoadInfoName(String outRoadInfoName) {
		this.outRoadInfoName = outRoadInfoName;
	}


	public String getCheckFlag() {
		return checkFlag;
	}


	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public Date getOutTime() {
		return outTime;
	}


	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}


	public Date getCheckTime() {
		return checkTime;
	}


	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}


	public String getTransitListNoType() {
		return transitListNoType;
	}


	public void setTransitListNoType(String transitListNoType) {
		this.transitListNoType = transitListNoType;
	}


	public String getOutLaneType() {
		return outLaneType;
	}


	public void setOutLaneType(String outLaneType) {
		this.outLaneType = outLaneType;
	}


	public Long getCheckID() {
		return checkID;
	}


	public void setCheckID(Long checkID) {
		this.checkID = checkID;
	}


	public String getCheckName() {
		return checkName;
	}


	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}


	public String getOffTradeNo() {
		return offTradeNo;
	}


	public void setOffTradeNo(String offTradeNo) {
		this.offTradeNo = offTradeNo;
	}


	public BigDecimal getTransitBalance() {
		return transitBalance;
	}


	public void setTransitBalance(BigDecimal transitBalance) {
		this.transitBalance = transitBalance;
	}


	public String getLagInterceptFlag() {
		return lagInterceptFlag;
	}


	public void setLagInterceptFlag(String lagInterceptFlag) {
		this.lagInterceptFlag = lagInterceptFlag;
	}


	public String getHexOffNo() {
		return hexOffNo;
	}


	public void setHexOffNo(String hexOffNo) {
		this.hexOffNo = hexOffNo;
	}


	public Date getRegisterTime() {
		return registerTime;
	}


	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}


	public String getSysBalanceFlag() {
		return sysBalanceFlag;
	}


	public void setSysBalanceFlag(String sysBalanceFlag) {
		this.sysBalanceFlag = sysBalanceFlag;
	}


	public Long getRegisterID() {
		return registerID;
	}


	public void setRegisterID(Long registerID) {
		this.registerID = registerID;
	}


	public String getRegisterName() {
		return registerName;
	}


	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}


	public Long getExceptionType() {
		return exceptionType;
	}


	public void setExceptionType(Long exceptionType) {
		this.exceptionType = exceptionType;
	}


	public String getExTypeName() {
		return exTypeName;
	}


	public void setExTypeName(String exTypeName) {
		this.exTypeName = exTypeName;
	}


	public BigDecimal getReceivable() {
		return receivable;
	}


	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}


	public String getFinishFlag() {
		return finishFlag;
	}


	public void setFinishFlag(String finishFlag) {
		this.finishFlag = finishFlag;
	}


	public Date getClearingTime() {
		return clearingTime;
	}


	public void setClearingTime(Date clearingTime) {
		this.clearingTime = clearingTime;
	}


	public String getOutTradeNo() {
		return outTradeNo;
	}


	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}


	public Long getInSiteInfo() {
		return inSiteInfo;
	}


	public void setInSiteInfo(Long inSiteInfo) {
		this.inSiteInfo = inSiteInfo;
	}


	public Long getInRoadInfo() {
		return inRoadInfo;
	}


	public void setInRoadInfo(Long inRoadInfo) {
		this.inRoadInfo = inRoadInfo;
	}


	public String getInRoadInfoName() {
		return inRoadInfoName;
	}


	public void setInRoadInfoName(String inRoadInfoName) {
		this.inRoadInfoName = inRoadInfoName;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public BigDecimal getProceeds() {
		return proceeds;
	}


	public void setProceeds(BigDecimal proceeds) {
		this.proceeds = proceeds;
	}


	public Date getFinishTime() {
		return finishTime;
	}


	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}


	public String getClearingListNo() {
		return clearingListNo;
	}


	public void setClearingListNo(String clearingListNo) {
		this.clearingListNo = clearingListNo;
	}


	public Long getCustomPoint() {
		return customPoint;
	}


	public void setCustomPoint(Long customPoint) {
		this.customPoint = customPoint;
	}


	public String getPointName() {
		return pointName;
	}


	public void setPointName(String pointName) {
		this.pointName = pointName;
	}


	public Long getHandleID() {
		return handleID;
	}


	public void setHandleID(Long handleID) {
		this.handleID = handleID;
	}


	public String getHandleName() {
		return handleName;
	}


	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getCurrentCardNo() {
		return currentCardNo;
	}


	public void setCurrentCardNo(String currentCardNo) {
		this.currentCardNo = currentCardNo;
	}


	public String getCardType() {
		return cardType;
	}


	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
}