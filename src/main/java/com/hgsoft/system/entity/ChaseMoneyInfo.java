package com.hgsoft.system.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ChaseMoneyInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4694255840949978384L;
	
	private Long id; 
	private String processNum;
	private BigDecimal needPayment;
	private BigDecimal realPayment;
	private Date enterTime;
	private String enterRoad_info;
	private String enterStation_info;
	private String exitStation_info;
	private String placename;
	private String processMan;
	private Date updateTime;
	private String finish_flag;
	private String register_man;
	private Date balanceDate ;
	private String cardNo;
	private String chase_cardNo;
	private String cardType;
	private String flowType;
	private String exit_lane_flag;
	private String hold_up_flag;
	private String balance_flag;
	private String exception_flag;
	private String remark;
	private String chase_flow_num;
	private Date register_time;
	private String balance_num;
	private Long customerId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessNum() {
		return processNum;
	}
	public void setProcessNum(String processNum) {
		this.processNum = processNum;
	}
	public BigDecimal getNeedPayment() {
		return needPayment;
	}
	public void setNeedPayment(BigDecimal needPayment) {
		this.needPayment = needPayment;
	}
	public BigDecimal getRealPayment() {
		return realPayment;
	}
	public void setRealPayment(BigDecimal realPayment) {
		this.realPayment = realPayment;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public String getEnterRoad_info() {
		return enterRoad_info;
	}
	public void setEnterRoad_info(String enterRoad_info) {
		this.enterRoad_info = enterRoad_info;
	}
	public String getEnterStation_info() {
		return enterStation_info;
	}
	public void setEnterStation_info(String enterStation_info) {
		this.enterStation_info = enterStation_info;
	}
	public String getExitStation_info() {
		return exitStation_info;
	}
	public void setExitStation_info(String exitStation_info) {
		this.exitStation_info = exitStation_info;
	}
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	public String getProcessMan() {
		return processMan;
	}
	public void setProcessMan(String processMan) {
		this.processMan = processMan;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getFinish_flag() {
		return finish_flag;
	}
	public void setFinish_flag(String finish_flag) {
		this.finish_flag = finish_flag;
	}
	public String getRegister_man() {
		return register_man;
	}
	public void setRegister_man(String register_man) {
		this.register_man = register_man;
	}
	public Date getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getChase_cardNo() {
		return chase_cardNo;
	}
	public void setChase_cardNo(String chase_cardNo) {
		this.chase_cardNo = chase_cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getExit_lane_flag() {
		return exit_lane_flag;
	}
	public void setExit_lane_flag(String exit_lane_flag) {
		this.exit_lane_flag = exit_lane_flag;
	}
	public String getHold_up_flag() {
		return hold_up_flag;
	}
	public void setHold_up_flag(String hold_up_flag) {
		this.hold_up_flag = hold_up_flag;
	}
	public String getBalance_flag() {
		return balance_flag;
	}
	public void setBalance_flag(String balance_flag) {
		this.balance_flag = balance_flag;
	}
	public String getException_flag() {
		return exception_flag;
	}
	public void setException_flag(String exception_flag) {
		this.exception_flag = exception_flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChase_flow_num() {
		return chase_flow_num;
	}
	public void setChase_flow_num(String chase_flow_num) {
		this.chase_flow_num = chase_flow_num;
	}
	public Date getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	public String getBalance_num() {
		return balance_num;
	}
	public void setBalance_num(String balance_num) {
		this.balance_num = balance_num;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
 
}
