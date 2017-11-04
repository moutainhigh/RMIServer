package com.hgsoft.system.entity;

import java.io.Serializable;
import java.util.Date;

public class CusPointTreeEnd implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2897874262881266340L;
	private Long id;
	private String address;
	private String area;
	private String business;
	private String city;
	private String code;
	private Long createID;
	private Date createTime;
	private int customPointType;
	private String fax;
	private int layer;
	private String name;
	private String openTime;
	private int parent;
	private String phone;
	private String remark;
	private String status;
	private String stockName;
	private int stockPlace;
	private String terminalType;
	private String treeEnd;
	private String useState;
	private Long bankCode ;//所属银行
	
	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCreateID() {
		return createID;
	}

	public void setCreateID(Long createID) {
		this.createID = createID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCustomPointType() {
		return customPointType;
	}

	public void setCustomPointType(int customPointType) {
		this.customPointType = customPointType;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public int getStockPlace() {
		return stockPlace;
	}

	public void setStockPlace(int stockPlace) {
		this.stockPlace = stockPlace;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getTreeEnd() {
		return treeEnd;
	}

	public void setTreeEnd(String treeEnd) {
		this.treeEnd = treeEnd;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	@Override
	public String toString() {
		return "CusPointTreeEnd [id=" + id + ", address=" + address + ", area=" + area + ", business=" + business
				+ ", city=" + city + ", code=" + code + ", createID=" + createID + ", createTime=" + createTime
				+ ", customPointType=" + customPointType + ", fax=" + fax + ", layer=" + layer + ", name=" + name
				+ ", openTime=" + openTime + ", parent=" + parent + ", phone=" + phone + ", remark=" + remark
				+ ", status=" + status + ", stockName=" + stockName + ", stockPlace=" + stockPlace + ", terminalType="
				+ terminalType + ", treeEnd=" + treeEnd + ", useState=" + useState + "]";
	}

	
}
