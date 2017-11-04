package com.hgsoft.acms.obu.entity;

import java.util.*;

import java.io.Serializable;

public class TagMigrateRecordACMS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8939458623487367945L;
	private Long id;
	// 电子标签号
	private String tagNo;
	// 原客户id
	private Long customerId;
	//原客户名称
	private String organ;
	//原车辆id
	private Long vehicleId;
	//新客户id
	private Long newcustomerId;
	//新车辆id
	private Long newvehicleId;
	//新客户名称
	private String newOrgan;
	//申请时间
	private Date reqdate;
	//更新时间
	private Date uptime;
	//审核时间
	private Date authDate;
	//审核人id
	private Long authId;
	//审核人编号
	private String authNo;
	//审核人名称
	private String authName;
	//审核状态
	private Integer authState;
	private Long operId;
	private Long placeId;
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
	private Long hisSeqId;
	private String reason;
	private String faultType;
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagNo() {
		return tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getNewcustomerId() {
		return newcustomerId;
	}

	public void setNewcustomerId(Long newcustomerId) {
		this.newcustomerId = newcustomerId;
	}

	public Long getNewvehicleId() {
		return newvehicleId;
	}

	public void setNewvehicleId(Long newvehicleId) {
		this.newvehicleId = newvehicleId;
	}

	public String getNewOrgan() {
		return newOrgan;
	}

	public void setNewOrgan(String newOrgan) {
		this.newOrgan = newOrgan;
	}

	public Date getReqdate() {
		return reqdate;
	}

	public void setReqdate(Date reqdate) {
		this.reqdate = reqdate;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public Integer getAuthState() {
		return authState;
	}

	public void setAuthState(Integer authState) {
		this.authState = authState;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getPlaceNo() {
		return placeNo;
	}

	public void setPlaceNo(String placeNo) {
		this.placeNo = placeNo;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Long getHisSeqId() {
		return hisSeqId;
	}

	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}

}
