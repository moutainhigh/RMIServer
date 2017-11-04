package com.hgsoft.daysettle.entity;

import java.io.Serializable;

public class SumDaySettleWare  implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 230716926245087988L;
	//日结后储值卡修正回收
	private String paidRecover= "0";
	//日结后储值卡修正结余
	private String paidBalance= "0";
	//日结后记帐卡修正回收
	private String accRecover= "0";
	//日结后记帐卡修正结余
	private String accBalance= "0";
	//日结后电子标签修正回收
	private String tagRecover= "0";
	//日结后电子标签修正结余
	private String tagBalance= "0";
	//粤通卡缴费专用发票数回收
	private String billRecover= "0";
	//粤通卡缴费专用发票数结余
	private String billBalance= "0";
	
	//储值卡回收系统数
	private String sysPaidRecover= "0";
	//储值卡结余系统数
	private String sysPaidBalance= "0";
	//记帐卡回收系统数
	private String sysAccRecover= "0";
	//记帐卡结余系统数
	private String sysAccBalance= "0";
	//电子标签回收系统数
	private String sysTagRecover= "0";
	//电子标签结余系统数
	private String sysTagBalance= "0";
	//粤通卡缴费专用发票数回收系统数
	private String sysbillRecover= "0";
	//粤通卡缴费专用发票数结余系统数
	private String sysbillBalance= "0";
	
	//网点储值卡回收数
	private String registerPaidRecover= "0";
	//网点储值卡结余数
	private String registerPaidBalance= "0";
	//网点记帐卡回收数
	private String registerAccRecover= "0";
	//网点记帐卡结余数
	private String registerAccBalance= "0";
	//网点电子标签回收数
	private String registerTagRecover= "0";
	//网点电子标签结余数
	private String registerTagBalance= "0";
	//网点粤通卡缴费专用发票回收数
	private String registerbillBalance= "0";
	
	//储值卡入库总数
	private String preInStockNum= "0";
	//储值卡出库总数
	private String preOutStockNum= "0";
	//记帐卡入库总数
	private String accInStockNum= "0";
	//记帐卡出库总数
	private String accOutStockNum= "0";
	//电子标签入库总数
	private String tagInStockNum= "0";
	//电子标签出库总数
	private String tagOutStockNum= "0";
	//发票入库总数
	private String billInStockNum= "0";
	//发票出库总数
	private String billOutStockNum= "0";
	
	

	public String getPreInStockNum() {
		return preInStockNum;
	}

	public void setPreInStockNum(String preInStockNum) {
		this.preInStockNum = preInStockNum;
	}

	public String getPreOutStockNum() {
		return preOutStockNum;
	}

	public void setPreOutStockNum(String preOutStockNum) {
		this.preOutStockNum = preOutStockNum;
	}

	public String getAccInStockNum() {
		return accInStockNum;
	}

	public void setAccInStockNum(String accInStockNum) {
		this.accInStockNum = accInStockNum;
	}

	public String getAccOutStockNum() {
		return accOutStockNum;
	}

	public void setAccOutStockNum(String accOutStockNum) {
		this.accOutStockNum = accOutStockNum;
	}

	public String getTagInStockNum() {
		return tagInStockNum;
	}

	public void setTagInStockNum(String tagInStockNum) {
		this.tagInStockNum = tagInStockNum;
	}

	public String getTagOutStockNum() {
		return tagOutStockNum;
	}

	public void setTagOutStockNum(String tagOutStockNum) {
		this.tagOutStockNum = tagOutStockNum;
	}

	public String getBillInStockNum() {
		return billInStockNum;
	}

	public void setBillInStockNum(String billInStockNum) {
		this.billInStockNum = billInStockNum;
	}

	public String getBillOutStockNum() {
		return billOutStockNum;
	}

	public void setBillOutStockNum(String billOutStockNum) {
		this.billOutStockNum = billOutStockNum;
	}

	public String getSysPaidRecover() {
		return sysPaidRecover;
	}

	public void setSysPaidRecover(String sysPaidRecover) {
		this.sysPaidRecover = sysPaidRecover;
	}

	public String getSysPaidBalance() {
		return sysPaidBalance;
	}

	public void setSysPaidBalance(String sysPaidBalance) {
		this.sysPaidBalance = sysPaidBalance;
	}

	public String getSysAccRecover() {
		return sysAccRecover;
	}

	public void setSysAccRecover(String sysAccRecover) {
		this.sysAccRecover = sysAccRecover;
	}

	public String getSysAccBalance() {
		return sysAccBalance;
	}

	public void setSysAccBalance(String sysAccBalance) {
		this.sysAccBalance = sysAccBalance;
	}

	public String getSysTagRecover() {
		return sysTagRecover;
	}

	public void setSysTagRecover(String sysTagRecover) {
		this.sysTagRecover = sysTagRecover;
	}

	public String getSysTagBalance() {
		return sysTagBalance;
	}

	public void setSysTagBalance(String sysTagBalance) {
		this.sysTagBalance = sysTagBalance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPaidRecover() {
		return paidRecover;
	}

	public void setPaidRecover(String paidRecover) {
		this.paidRecover = paidRecover;
	}

	public String getPaidBalance() {
		return paidBalance;
	}

	public void setPaidBalance(String paidBalance) {
		this.paidBalance = paidBalance;
	}

	public String getAccRecover() {
		return accRecover;
	}

	public void setAccRecover(String accRecover) {
		this.accRecover = accRecover;
	}

	public String getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(String accBalance) {
		this.accBalance = accBalance;
	}

	public String getTagRecover() {
		return tagRecover;
	}

	public void setTagRecover(String tagRecover) {
		this.tagRecover = tagRecover;
	}

	public String getTagBalance() {
		return tagBalance;
	}

	public void setTagBalance(String tagBalance) {
		this.tagBalance = tagBalance;
	}

	public String getBillRecover() {
		return billRecover;
	}

	public void setBillRecover(String billRecover) {
		this.billRecover = billRecover;
	}

	public String getBillBalance() {
		return billBalance;
	}

	public void setBillBalance(String billBalance) {
		this.billBalance = billBalance;
	}

	public String getSysbillRecover() {
		return sysbillRecover;
	}

	public void setSysbillRecover(String sysbillRecover) {
		this.sysbillRecover = sysbillRecover;
	}

	public String getSysbillBalance() {
		return sysbillBalance;
	}

	public void setSysbillBalance(String sysbillBalance) {
		this.sysbillBalance = sysbillBalance;
	}
	
	public String getRegisterPaidRecover() {
		return registerPaidRecover;
	}

	public void setRegisterPaidRecover(String registerPaidRecover) {
		this.registerPaidRecover = registerPaidRecover;
	}

	public String getRegisterPaidBalance() {
		return registerPaidBalance;
	}

	public void setRegisterPaidBalance(String registerPaidBalance) {
		this.registerPaidBalance = registerPaidBalance;
	}

	public String getRegisterAccRecover() {
		return registerAccRecover;
	}

	public void setRegisterAccRecover(String registerAccRecover) {
		this.registerAccRecover = registerAccRecover;
	}

	public String getRegisterAccBalance() {
		return registerAccBalance;
	}

	public void setRegisterAccBalance(String registerAccBalance) {
		this.registerAccBalance = registerAccBalance;
	}

	public String getRegisterTagRecover() {
		return registerTagRecover;
	}

	public void setRegisterTagRecover(String registerTagRecover) {
		this.registerTagRecover = registerTagRecover;
	}

	public String getRegisterTagBalance() {
		return registerTagBalance;
	}

	public void setRegisterTagBalance(String registerTagBalance) {
		this.registerTagBalance = registerTagBalance;
	}

	public String getRegisterbillBalance() {
		return registerbillBalance;
	}

	public void setRegisterbillBalance(String registerbillBalance) {
		this.registerbillBalance = registerbillBalance;
	}

	@Override
	public String toString() {
		return "SumDaySettleWare [" +
					"长短款储值卡回收paidRecover=" + paidRecover
				+ ", 长短款储值卡结余paidBalance=" + paidBalance
				+ ", 长短款记帐卡回收accRecover=" + accRecover
				+ ", 长短款记帐卡结余accBalance=" + accBalance
				+ ", 长短款电子标签回收tagRecover=" + tagRecover
				+ ", 长短款电子标签结余tagBalance=" + tagBalance
				+ ", 长短款发票回收billRecover=" + billRecover
				+ ", 长短款发票结余billBalance=" + billBalance
				+ ", 系统储值卡回收数sysPaidRecover=" + sysPaidRecover
				+ ", 系统储值卡结余数sysPaidBalance=" + sysPaidBalance
				+ ", 系统记帐卡回收数sysAccRecover=" + sysAccRecover 
				+ ", 系统记帐卡结余数sysAccBalance=" + sysAccBalance 
				+ ", 系统电子标签回收数sysTagRecover=" + sysTagRecover
				+ ", 系统电子标签结余数sysTagBalance=" + sysTagBalance 
				+ ", 系统发票系统数sysbillRecover=" + sysbillRecover
				+ ", 系统发票结余数sysbillBalance=" + sysbillBalance
				+ ", 网点储值卡回收registerPaidRecover=" + registerPaidRecover
				+ ", 网点储值卡结余registerPaidBalance=" + registerPaidBalance
				+ ", 网点记帐卡回收registerAccRecover=" + registerAccRecover
				+ ", 网点记帐卡结余registerAccBalance=" + registerAccBalance
				+ ", 网点电子标签回收registerTagRecover=" + registerTagRecover
				+ ", 网点电子标签结余registerTagBalance=" + registerTagBalance
				+ ", 网点发票结余registerbillRecover=" + registerbillBalance
				+ ", 储值卡入库总数preInStockNum=" + preInStockNum
				+ ", 储值卡出库总数preOutStockNum=" + preOutStockNum
				+ ", 记帐卡入库总数accInStockNum=" + accInStockNum
				+ ", 记帐卡出库总数accOutStockNum=" + accOutStockNum
				+ ", 电子标签入库总数tagInStockNum=" + tagInStockNum
				+ ", 电子标签出库总数tagOutStockNum=" + tagOutStockNum
				+ ", 发票入库总数billInStockNum=" + billInStockNum
				+ ", 发票出库总数billOutStockNum=" + billOutStockNum+ "]";
	}

	

	

	
	
	
	
}
