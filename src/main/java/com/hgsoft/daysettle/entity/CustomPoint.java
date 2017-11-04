package com.hgsoft.daysettle.entity;

import java.io.Serializable;
import java.util.Date;

public class CustomPoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;//
	private String code;// 编码
	private Long customPointType;// 网点类型
	private String name;// 名称
	private String terminalType;// 终端类型1.未有终端2.人工终端3.自助终端
	private String city;// 所在城市
	private String area;// 所属区域
	private String phone;// 联系电话
	private String fax;// 传真
	private String address;// 地址
	private String status;// 营业状态
	private String business;// 受理业务
	private String openTime;// 营业时间
	private String remark;// 备注
	private String useState;// 使用状态
	private String treeEnd;// 是否营业网点1.否2.是
	private Long parent;// 父ID
	private Long createID;// 创建人
	private Date createTime;// 创建时间
	private Integer layer;// 层级
	private Long stockPlace;// 库存地id
	private String stockName;// 库存地名称
	private Long bankCode ;//所属银行
	
	@Override
	public String toString() {
		return "CustomPoint [id=" + id + ", code=" + code + ", customPointType=" + customPointType + ", name=" + name
				+ ", terminalType=" + terminalType + ", city=" + city + ", area=" + area + ", phone=" + phone + ", fax="
				+ fax + ", address=" + address + ", status=" + status + ", business=" + business + ", openTime="
				+ openTime + ", remark=" + remark + ", useState=" + useState + ", treeEnd=" + treeEnd + ", parent="
				+ parent + ", createID=" + createID + ", createTime=" + createTime + ", layer=" + layer
				+ ", stockPlace=" + stockPlace + ", stockName=" + stockName + ", bankCode=" + bankCode+ "]";
	}

	public CustomPoint() {
		super();
	}

	public CustomPoint(Long id, String code, Long customPointType, String name, String terminalType, String city,
			String area, String phone, String fax, String address, String status, String business, String openTime,
			String remark, String useState, String treeEnd, Long parent, Long createID, Date createTime, Integer layer,
			Long stockPlace, String stockName,Long bankCode) {
		super();
		this.id = id;
		this.code = code;
		this.customPointType = customPointType;
		this.name = name;
		this.terminalType = terminalType;
		this.city = city;
		this.area = area;
		this.phone = phone;
		this.fax = fax;
		this.address = address;
		this.status = status;
		this.business = business;
		this.openTime = openTime;
		this.remark = remark;
		this.useState = useState;
		this.treeEnd = treeEnd;
		this.parent = parent;
		this.createID = createID;
		this.createTime = createTime;
		this.layer = layer;
		this.stockPlace = stockPlace;
		this.stockName = stockName;
		this.bankCode = bankCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCustomPointType() {
		return customPointType;
	}

	public void setCustomPointType(Long customPointType) {
		this.customPointType = customPointType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	public String getTreeEnd() {
		return treeEnd;
	}

	public void setTreeEnd(String treeEnd) {
		this.treeEnd = treeEnd;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
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

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Long getStockPlace() {
		return stockPlace;
	}

	public void setStockPlace(Long stockPlace) {
		this.stockPlace = stockPlace;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

}
