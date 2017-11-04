package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;

public class AdminLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -937694428488676718L;
	private Long id;
	private String type;
	private Long admin;
	private String loginName;
	private String userName;
	private Long module;
	private String moduleName;
	private String logData;
	private Date createTime;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getAdmin() {
		return admin;
	}
	public void setAdmin(Long admin) {
		this.admin = admin;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getModule() {
		return module;
	}
	public void setModule(Long module) {
		this.module = module;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getLogData() {
		return logData;
	}
	public void setLogData(String logData) {
		this.logData = logData;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
