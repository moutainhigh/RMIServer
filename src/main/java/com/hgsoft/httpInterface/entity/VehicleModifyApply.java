package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

public class VehicleModifyApply implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long vehicleId;
    private String oldVehicleType;
    private String newVehicleType;
    private Long oldVehicleWeightLimits;
    private Long newVehicleWeightLimits;
	private String oldNSCVehicleType;
	private String newNSCVehicleType;
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
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getOldVehicleType() {
		return oldVehicleType;
	}
	public void setOldVehicleType(String oldVehicleType) {
		this.oldVehicleType = oldVehicleType;
	}
	public String getNewVehicleType() {
		return newVehicleType;
	}
	public void setNewVehicleType(String newVehicleType) {
		this.newVehicleType = newVehicleType;
	}
	public Long getOldVehicleWeightLimits() {
		return oldVehicleWeightLimits;
	}
	public void setOldVehicleWeightLimits(Long oldVehicleWeightLimits) {
		this.oldVehicleWeightLimits = oldVehicleWeightLimits;
	}
	public Long getNewVehicleWeightLimits() {
		return newVehicleWeightLimits;
	}
	public void setNewVehicleWeightLimits(Long newVehicleWeightLimits) {
		this.newVehicleWeightLimits = newVehicleWeightLimits;
	}
	public String getOldNSCVehicleType() {
		return oldNSCVehicleType;
	}
	public void setOldNSCVehicleType(String oldNSCVehicleType) {
		this.oldNSCVehicleType = oldNSCVehicleType;
	}
	public String getNewNSCVehicleType() {
		return newNSCVehicleType;
	}
	public void setNewNSCVehicleType(String newNSCVehicleType) {
		this.newNSCVehicleType = newNSCVehicleType;
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
