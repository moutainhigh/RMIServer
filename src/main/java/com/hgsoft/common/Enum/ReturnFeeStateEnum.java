package com.hgsoft.common.Enum;

public enum ReturnFeeStateEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行

	notUse("未使用","1"),rechargeLocked("充值锁定","2"), recharged("充值成功", "3"), gainCard("已做换卡继承", "4");

	private String value;

	private String name;

	/** Internal constructor */
	ReturnFeeStateEnum(String name, String value) {
		this.value = value;
		this.name=name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.name();
	}
	
	public String getName() {
		//name = this.toString();
		return name;
	}
	
	public static ReturnFeeStateEnum getEnumByValue(String value) {
		ReturnFeeStateEnum type = null;
		for (ReturnFeeStateEnum tempEnum : ReturnFeeStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}

	public static String getNameByValue(String value) {
		String name = null;
		for (ReturnFeeStateEnum tempEnum : ReturnFeeStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				name = tempEnum.getName();
				break;
			}
		}
		return name;
	}
}
