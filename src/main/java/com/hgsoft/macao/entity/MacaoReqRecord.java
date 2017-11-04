package com.hgsoft.macao.entity;

import java.util.Date;

public class MacaoReqRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2116703402418644317L;

	
	private Long id;
	private String interfaceFlag;
	private String interfaceName;
	private Date reqTime;
	private String reqSN;
	private Date resTime;
	private String resSN;
	private String recordState;
	private String remark;
	private String ip;
	private String tranStatus;
	
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterfaceFlag() {
		return interfaceFlag;
	}
	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public String getReqSN() {
		return reqSN;
	}
	public void setReqSN(String reqSN) {
		this.reqSN = reqSN;
	}
	public Date getResTime() {
		return resTime;
	}
	public void setResTime(Date resTime) {
		this.resTime = resTime;
	}
	public String getResSN() {
		return resSN;
	}
	public void setResSN(String resSN) {
		this.resSN = resSN;
	}
	public String getRecordState() {
		return recordState;
	}
	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
