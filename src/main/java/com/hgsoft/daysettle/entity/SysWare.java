package com.hgsoft.daysettle.entity;

public class SysWare implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1938003960957492595L;

	private Long id;
	
	private String settleDay;
	
	private String productCode;
	
	private String productName;
	
	private String productState;
	
	private String productSource;
	
	private Integer productNum;
	
	private Long settleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	public String getProductSource() {
		return productSource;
	}

	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Long getSettleId() {
		return settleId;
	}

	public void setSettleId(Long settleId) {
		this.settleId = settleId;
	}
	
	
}
