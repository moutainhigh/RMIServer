package com.hgsoft.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServiceFundMonitorHis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6290917682657300017L;
	
	private Long id;
	private Long customPoint;
	private String pointName;
	private String buinessType;
	private String fundModel;
	private BigDecimal fundMax;
	private BigDecimal hint;//提示百分比 0725添加
	private Long operateId;
	private String operateName;
	private Date operateTime;
	private BigDecimal useFund;
	private Long serviceFundMonitorId;
	private Date genTime;
	private String genReason;
	
	public ServiceFundMonitorHis() {
		// TODO Auto-generated constructor stub
	}
	public ServiceFundMonitorHis(ServiceFundMonitor serviceFundMonitor){
		this.customPoint = serviceFundMonitor.getCustomPoint();
		this.pointName = serviceFundMonitor.getPointName();
		this.buinessType = serviceFundMonitor.getBuinessType();
		this.fundModel = serviceFundMonitor.getFundModel();
		this.fundMax = serviceFundMonitor.getFundMax();
		this.hint = serviceFundMonitor.getHint();
		this.useFund = serviceFundMonitor.getUseFund();
		this.serviceFundMonitorId = serviceFundMonitor.getId();
		this.operateId = serviceFundMonitor.getOperateId();
		this.operateName = serviceFundMonitor.getOperateName();
		this.operateTime = serviceFundMonitor.getOperateTime();
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
	public Long getServiceFundMonitorId() {
		return serviceFundMonitorId;
	}
	public Date getGenTime() {
		return genTime;
	}
	public String getGenReason() {
		return genReason;
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
	public void setServiceFundMonitorId(Long serviceFundMonitorId) {
		this.serviceFundMonitorId = serviceFundMonitorId;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public void setGenReason(String genReason) {
		this.genReason = genReason;
	}
	public BigDecimal getHint() {
		return hint;
	}
	public void setHint(BigDecimal hint) {
		this.hint = hint;
	}
	public ServiceFundMonitorHis(Long id, Long customPoint, String pointName, String buinessType, String fundModel,
			BigDecimal fundMax, BigDecimal hint, Long operateId, String operateName, Date operateTime,
			BigDecimal useFund, Long serviceFundMonitorId, Date genTime, String genReason) {
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
		this.serviceFundMonitorId = serviceFundMonitorId;
		this.genTime = genTime;
		this.genReason = genReason;
	}

	
	
	
}
