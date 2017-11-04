package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class SpecialCostSubclass implements Serializable {

    private static final long serialVersionUID = -4554146210824208572L;

    private Long id;
    private Long omsId;
    private Long specialCostType;
    private String code;
    private String categoryName;
    private BigDecimal charge;
    private String remark;
    private String state;//启用标志
    //2017年6月28日15:45:28
    private String flag;//审核标志
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSpecialCostType() {
		return specialCostType;
	}
	public void setSpecialCostType(Long specialCostType) {
		this.specialCostType = specialCostType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getOmsId() {
		return omsId;
	}
	public void setOmsId(Long omsId) {
		this.omsId = omsId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
    
}
