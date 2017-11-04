package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

public class CustomerModifyApply implements Serializable {

    private static final long serialVersionUID = -4554146210824208572L;

    private Long id;
    private Long customerId;
    private String oldOrgan;
    private String newOrgan;
    private String oldIdType;
    private String newIdType;
    private String oldIdCode;
    private String newIdCode;
    private String oldPicAddr;
    private String newPicAddr;
    private String appState;
    private Long approverId;
    private String approverNo;
    private String approverName;
    private Date appTime;
    private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getOldOrgan() {
		return oldOrgan;
	}
	public void setOldOrgan(String oldOrgan) {
		this.oldOrgan = oldOrgan;
	}
	public String getNewOrgan() {
		return newOrgan;
	}
	public void setNewOrgan(String newOrgan) {
		this.newOrgan = newOrgan;
	}
	public String getOldIdType() {
		return oldIdType;
	}
	public void setOldIdType(String oldIdType) {
		this.oldIdType = oldIdType;
	}
	public String getNewIdType() {
		return newIdType;
	}
	public void setNewIdType(String newIdType) {
		this.newIdType = newIdType;
	}
	public String getOldIdCode() {
		return oldIdCode;
	}
	public void setOldIdCode(String oldIdCode) {
		this.oldIdCode = oldIdCode;
	}
	public String getNewIdCode() {
		return newIdCode;
	}
	public void setNewIdCode(String newIdCode) {
		this.newIdCode = newIdCode;
	}
	public String getOldPicAddr() {
		return oldPicAddr;
	}
	public void setOldPicAddr(String oldPicAddr) {
		this.oldPicAddr = oldPicAddr;
	}
	public String getNewPicAddr() {
		return newPicAddr;
	}
	public void setNewPicAddr(String newPicAddr) {
		this.newPicAddr = newPicAddr;
	}
	public String getAppState() {
		return appState;
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	public String getApproverNo() {
		return approverNo;
	}
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public Date getAppTime() {
		return appTime;
	}
	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}
