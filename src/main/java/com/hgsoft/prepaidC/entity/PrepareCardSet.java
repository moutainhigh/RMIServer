package com.hgsoft.prepaidC.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrepareCardSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6845165136162764879L;
	private Long id;
	private String projectName;//项目名称
	private String startCode;//起始卡号
	private String endCode;//结束卡号
	private BigDecimal giveAmount;//赠送金额
	private BigDecimal billOffset;//发票冲减
	private String remark;
	private String oldCode;//旧卡卡号
	private String firstCode;//首次卡号
	private BigDecimal remainAmount;//剩余赠送金额
	private String flag;//审核标志1.未审核2.审核通过3.审核未通过
	private Long operateID;
	private String operateName;
	private Date operateTime;
	private String customPointCode;//受理网点编号

	public Long getId() {
		return id;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getStartCode() {
		return startCode;
	}
	public String getEndCode() {
		return endCode;
	}
	public BigDecimal getGiveAmount() {
		return giveAmount;
	}
	public String getRemark() {
		return remark;
	}
	public String getOldCode() {
		return oldCode;
	}
	public String getFirstCode() {
		return firstCode;
	}
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	public String getFlag() {
		return flag;
	}
	public Long getOperateID() {
		return operateID;
	}
	public String getOperateName() {
		return operateName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public void setGiveAmount(BigDecimal giveAmount) {
		this.giveAmount = giveAmount;
	}

	public BigDecimal getBillOffset() {
		return billOffset;
	}
	public void setBillOffset(BigDecimal billOffset) {
		this.billOffset = billOffset;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setOperateID(Long operateID) {
		this.operateID = operateID;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getCustomPointCode() {
		return customPointCode;
	}
	public void setCustomPointCode(String customPointCode) {
		this.customPointCode = customPointCode;
	}
	
}
