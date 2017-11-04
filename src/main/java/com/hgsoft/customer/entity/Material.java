package com.hgsoft.customer.entity;

import java.util.Date;
/**
 * 图片资料表实体类
 * @author Administrator
 *
 */
public class Material implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8224618108864654447L;
	
	private Long id;
	private Long customerID;
	private Long vehicleID;
	private String type;
	private String code;
	private String picAddr;
	private Integer updateTime;
	private Date up_Date;
	private String remark;
	private Long bussinessId;
	public Long getBussinessId() {
		return bussinessId;
	}
	public void setBussinessId(Long bussinessId) {
		this.bussinessId = bussinessId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}
	public Long getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Long vehicleID) {
		this.vehicleID = vehicleID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public Integer getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
	public Date getUp_Date() {
		return up_Date;
	}
	public void setUp_Date(Date up_Date) {
		this.up_Date = up_Date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
