package com.hgsoft.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceFundMonitor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7376036908092026949L;
	
	private Long id;
	private Long customPoint;
	private String pointName;
	private String buinessType;
	private String fundModel;//资金模式
	private BigDecimal fundMax;
	private BigDecimal hint;//提示百分比 0725添加
	private Long operateId;
	private String operateName;
	private Date operateTime;
	private BigDecimal useFund;
	
	public ServiceFundMonitor() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public Long getCustomPoint() {
		return customPoint;
	}

	public String getPointName() {
		return pointName;
	}

	public String getBuinessType() {
		return buinessType;
	}

	public String getFundModel() {
		return fundModel;
	}

	public BigDecimal getFundMax() {
		return fundMax;
	}


	public Long getOperateId() {
		return operateId;
	}

	public String getOperateName() {
		return operateName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public BigDecimal getUseFund() {
		return useFund;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCustomPoint(Long customPoint) {
		this.customPoint = customPoint;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public void setBuinessType(String buinessType) {
		this.buinessType = buinessType;
	}

	public void setFundModel(String fundModel) {
		this.fundModel = fundModel;
	}

	public void setFundMax(BigDecimal fundMax) {
		this.fundMax = fundMax;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public void setUseFund(BigDecimal useFund) {
		this.useFund = useFund;
	}

	public BigDecimal getHint() {
		return hint;
	}

	public void setHint(BigDecimal hint) {
		this.hint = hint;
	}

	public ServiceFundMonitor(Long id, Long customPoint, String pointName, String buinessType, String fundModel,
			BigDecimal fundMax, BigDecimal hint, Long operateId, String operateName, Date operateTime,
			BigDecimal useFund) {
		super();
		this.id = id;
		this.customPoint = customPoint;
		this.pointName = pointName;
		this.buinessType = buinessType;
		this.fundModel = fundModel;
		this.fundMax = fundMax;
		this.hint = hint;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateTime = operateTime;
		this.useFund = useFund;
	}

	
}
