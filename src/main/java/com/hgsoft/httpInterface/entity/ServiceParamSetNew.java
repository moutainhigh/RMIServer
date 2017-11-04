package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

public class ServiceParamSetNew implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2981219559392444252L;
	private Long id;
	private String verNo;
	private Date startDate;
	private Date operateTime;
	private Long operateId;
	private String operateName;
	private String key;
	private String value;
	private String dataType;//1.String 2.Integer 3.Float 4.Double 5.Boolean
	private String status;
	private String remark;
	private String unit;//单位1 元；2 年；3 月；4 天；5 小时；6分钟；7 秒；8 其他

	
	public ServiceParamSetNew() {
		// TODO Auto-generated constructor stub
	}
	
	public ServiceParamSetNew(Long id, String verNo, Date startDate, Date operateTime, Long operateId,
			String operateName, String key, String value, String dataType, String status, String remark,String unit) {
		super();
		this.id = id;
		this.verNo = verNo;
		this.startDate = startDate;
		this.operateTime = operateTime;
		this.operateId = operateId;
		this.operateName = operateName;
		this.key = key;
		this.value = value;
		this.dataType = dataType;
		this.status = status;
		this.remark = remark;
		this.unit=unit;
	}

	
	@Override
	public String toString() {
		return "ServiceParamSetNew [id=" + id + ", verNo=" + verNo + ", startDate=" + startDate + ", operateTime="
				+ operateTime + ", operateId=" + operateId + ", operateName=" + operateName + ", key=" + key
				+ ", value=" + value + ", dataType=" + dataType + ", status=" + status + ", remark=" + remark
				+ ", unit=" + unit + "]";
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVerNo() {
		return verNo;
	}
	public void setVerNo(String verNo) {
		this.verNo = verNo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public Long getOperateId() {
		return operateId;
	}
	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	
	
	
	
}
