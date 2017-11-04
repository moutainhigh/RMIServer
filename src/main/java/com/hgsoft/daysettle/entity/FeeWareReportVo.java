package com.hgsoft.daysettle.entity;

import java.util.List;

public class FeeWareReportVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8729684523430490634L;

	private DaySetApprove daySetApprove;
	
	private List<FeeReportRecord> feeList;
	
	private List<WareReportRecord> wareList;
	
	private List<FeeReportRecordVo> feeVoList;
	
	private List<CashDepositDaySet> cashDepositList;
	
	private List<DaySetCorrectRecord> correctList;
	
	private List<WareUntakeVo> untakeList;
	
	private List<WareReportRecord> totalWare;
	
	private List<LongFeeCorrect> longFeeCorrectList;
	
	private Integer productNum;
		
	public FeeWareReportVo(){
		
	}
	
	public FeeWareReportVo(DaySetApprove daySetApprove){
		this.daySetApprove = daySetApprove;
	}

	public DaySetApprove getDaySetApprove() {
		return daySetApprove;
	}

	public void setDaySetApprove(DaySetApprove daySetApprove) {
		this.daySetApprove = daySetApprove;
	}

	public List<FeeReportRecord> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<FeeReportRecord> feeList) {
		this.feeList = feeList;
	}

	public List<WareReportRecord> getWareList() {
		return wareList;
	}

	public void setWareList(List<WareReportRecord> wareList) {
		this.wareList = wareList;
	}

	public List<FeeReportRecordVo> getFeeVoList() {
		return feeVoList;
	}

	public void setFeeVoList(List<FeeReportRecordVo> feeVoList) {
		this.feeVoList = feeVoList;
	}

	public List<CashDepositDaySet> getCashDepositList() {
		return cashDepositList;
	}

	public void setCashDepositList(List<CashDepositDaySet> cashDepositList) {
		this.cashDepositList = cashDepositList;
	}

	public List<DaySetCorrectRecord> getCorrectList() {
		return correctList;
	}

	public void setCorrectList(List<DaySetCorrectRecord> correctList) {
		this.correctList = correctList;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public List<WareUntakeVo> getUntakeList() {
		return untakeList;
	}

	public void setUntakeList(List<WareUntakeVo> untakeList) {
		this.untakeList = untakeList;
	}

	public List<WareReportRecord> getTotalWare() {
		return totalWare;
	}

	public void setTotalWare(List<WareReportRecord> totalWare) {
		this.totalWare = totalWare;
	}

	public List<LongFeeCorrect> getLongFeeCorrectList() {
		return longFeeCorrectList;
	}

	public void setLongFeeCorrectList(List<LongFeeCorrect> longFeeCorrectList) {
		this.longFeeCorrectList = longFeeCorrectList;
	}

	
	
	

}
