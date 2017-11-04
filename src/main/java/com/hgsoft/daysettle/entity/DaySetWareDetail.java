package com.hgsoft.daysettle.entity;

import java.math.BigDecimal;

public class DaySetWareDetail implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287427285935061011L;
	private Long id;
	private Long mainID;
	private String settleDay;
	private String productType;
	private Integer currBalanceNum;
	private Integer currRecoverNum;
	private Integer sysCurrBalanceNum;
	private Integer sysCurrRecoverNum;
	private Integer balanceDiffNum;
	private Integer recoverDiffNum;
	private String differenceFlag;
	private String serviceType;
	private Integer lsadjustNum;
	private Integer inStockNum;
	private Integer outStockNum;
	
	public Integer getInStockNum() {
		return inStockNum;
	}
	public void setInStockNum(Integer inStockNum) {
		this.inStockNum = inStockNum;
	}
	public Integer getOutStockNum() {
		return outStockNum;
	}
	public void setOutStockNum(Integer outStockNum) {
		this.outStockNum = outStockNum;
	}
	public Integer getLsadjustNum() {
		return lsadjustNum;
	}
	public void setLsadjustNum(Integer lsadjustNum) {
		this.lsadjustNum = lsadjustNum;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainID() {
		return mainID;
	}
	public void setMainID(Long mainID) {
		this.mainID = mainID;
	}
	public String getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Integer getCurrBalanceNum() {
		return currBalanceNum;
	}
	public void setCurrBalanceNum(Integer currBalanceNum) {
		this.currBalanceNum = currBalanceNum;
	}
	public Integer getCurrRecoverNum() {
		return currRecoverNum;
	}
	public void setCurrRecoverNum(Integer currRecoverNum) {
		this.currRecoverNum = currRecoverNum;
	}
	public Integer getSysCurrBalanceNum() {
		return sysCurrBalanceNum;
	}
	public void setSysCurrBalanceNum(Integer sysCurrBalanceNum) {
		this.sysCurrBalanceNum = sysCurrBalanceNum;
	}
	public Integer getSysCurrRecoverNum() {
		return sysCurrRecoverNum;
	}
	public void setSysCurrRecoverNum(Integer sysCurrRecoverNum) {
		this.sysCurrRecoverNum = sysCurrRecoverNum;
	}
	public Integer getBalanceDiffNum() {
		return balanceDiffNum;
	}
	public void setBalanceDiffNum(Integer balanceDiffNum) {
		this.balanceDiffNum = balanceDiffNum;
	}
	public Integer getRecoverDiffNum() {
		return recoverDiffNum;
	}
	public void setRecoverDiffNum(Integer recoverDiffNum) {
		this.recoverDiffNum = recoverDiffNum;
	}
	public String getDifferenceFlag() {
		return differenceFlag;
	}
	public void setDifferenceFlag(String differenceFlag) {
		this.differenceFlag = differenceFlag;
	}
	
	
}
