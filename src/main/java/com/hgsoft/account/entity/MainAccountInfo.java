package com.hgsoft.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MainAccountInfo   implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4089259048312473230L;
	private Long id ;
	private Long mainId ;
	private BigDecimal balance ;
	private BigDecimal availableBalance ;
	private BigDecimal preferentialBalance ;
	private BigDecimal frozenBalance;
	private BigDecimal availableRefundBalance ;
	private BigDecimal refundApproveBalance;
	private String state ;
	private Long operId ;
	private Long placeId;
	private Date opebAccountDate ;
	private Long hisSeqId;
	
	private String operNo;
	private String operName;
	private String placeNo;
	private String placeName;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
	public BigDecimal getPreferentialBalance() {
		return preferentialBalance;
	}
	public void setPreferentialBalance(BigDecimal preferentialBalance) {
		this.preferentialBalance = preferentialBalance;
	}
	public BigDecimal getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(BigDecimal frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public BigDecimal getAvailableRefundBalance() {
		return availableRefundBalance;
	}
	public void setAvailableRefundBalance(BigDecimal availableRefundBalance) {
		this.availableRefundBalance = availableRefundBalance;
	}
	public BigDecimal getRefundApproveBalance() {
		return refundApproveBalance;
	}
	public void setRefundApproveBalance(BigDecimal refundApproveBalance) {
		this.refundApproveBalance = refundApproveBalance;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Date getOpebAccountDate() {
		return opebAccountDate;
	}
	public void setOpebAccountDate(Date opebAccountDate) {
		this.opebAccountDate = opebAccountDate;
	}
	public Long getMainId() {
		return mainId;
	}
	public void setMainId(Long mainId) {
		this.mainId = mainId;
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
	public Long getHisSeqId() {
		return hisSeqId;
	}
	public void setHisSeqId(Long hisSeqId) {
		this.hisSeqId = hisSeqId;
	}


	public enum State {
		Normal("账户正常","1"),
		Frozen("账户冻结","2"),
		Cancel("账户注销","3");

		private String value;
		private String name;

		// 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
		private State(String name, String value) {
			this.name = name;
			this.value = value;
		}

		// get 方法
		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public static State getByValue(String value) {
			State accChangeTypeEnum = null;
			for (State tempEnum : State.values()) {
				if (tempEnum.getValue().equals(value)) {
					accChangeTypeEnum = tempEnum;
					break;
				}
			}
			return accChangeTypeEnum;
		}
		public static String getNameByValue(String value){
			String name = null;
			for (State tempEnum : State.values()) {
				if (tempEnum.getValue().equals(value)) {
					name=tempEnum.getName();
					break;
				}
			}
			return name;
		}
	}
}
