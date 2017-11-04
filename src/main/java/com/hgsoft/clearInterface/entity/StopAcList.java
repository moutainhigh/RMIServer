package com.hgsoft.clearInterface.entity;

import java.util.Date;

public class StopAcList implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9078898366856379732L;
	private String accode;
	private Long genCau;
	private Date genTime;
	private String flag;
	private Date Updatetime;
	private String remark;
	private String userNo;
	private Integer OBANo;
	private String ACBAccount;
	private Integer AccType;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Integer getOBANo() {
		return OBANo;
	}
	public void setOBANo(Integer oBANo) {
		OBANo = oBANo;
	}
	public String getACBAccount() {
		return ACBAccount;
	}
	public void setACBAccount(String aCBAccount) {
		ACBAccount = aCBAccount;
	}
	public Integer getAccType() {
		return AccType;
	}
	public void setAccType(Integer accType) {
		AccType = accType;
	}
	
	public Long getGenCau() {
		return genCau;
	}
	public void setGenCau(Long genCau) {
		this.genCau = genCau;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getUpdatetime() {
		return Updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		Updatetime = updatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccode() {
		return accode;
	}
	public void setAccode(String accode) {
		this.accode = accode;
	}
	
}
