package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SysAdmin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3157211550146785180L;
	private Long id;
	private String loginName;
	private String userName;
	private String password;
	private String sex;
	private String staffNo;
	private String email;
	private String phone;
	private String userState;
	private String useState;
	private String ipLimit;
	private Date loginTime;
	private String loginIp;
	private Date lastLoginTime;
	private String lastLoginIp;
	private String remark;
	private Long company;
	private Long department;
	private String deName;
	private Long customPoint;
	private String pointName	;
	private Long adminGroup;
	private Long createId;
	private Long updateId;
	private Date createTime;
	private Date updateTime;
	private Set<SysRole> roles = new HashSet<SysRole>(0);
	
	private String customPointType;
	private String pointCode;
	
	private String subuumstemCode;
	
	public String getSubuumstemCode() {
		return subuumstemCode;
	}
	public void setSubuumstemCode(String subuumstemCode) {
		this.subuumstemCode = subuumstemCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getUseState() {
		return useState;
	}
	public void setUseState(String useState) {
		this.useState = useState;
	}
	public String getIpLimit() {
		return ipLimit;
	}
	public void setIpLimit(String ipLimit) {
		this.ipLimit = ipLimit;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCompany() {
		return company;
	}
	public void setCompany(Long company) {
		this.company = company;
	}
	public Long getDepartment() {
		return department;
	}
	public void setDepartment(Long department) {
		this.department = department;
	}
	public String getDeName() {
		return deName;
	}
	public void setDeName(String deName) {
		this.deName = deName;
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
	public Long getAdminGroup() {
		return adminGroup;
	}
	public void setAdminGroup(Long adminGroup) {
		this.adminGroup = adminGroup;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Set<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}
	public String getCustomPointType() {
		return customPointType;
	}
	public void setCustomPointType(String customPointType) {
		this.customPointType = customPointType;
	}
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	
	

}