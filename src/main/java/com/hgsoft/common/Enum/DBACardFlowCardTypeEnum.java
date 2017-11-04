package com.hgsoft.common.Enum;

public enum DBACardFlowCardTypeEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行
	
	prePaidCard("储值卡","22"),accountCard("记帐卡","23");
	
	private String value;

	private String name;

	/** Internal constructor */
	DBACardFlowCardTypeEnum(String name,String value) {
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
	
	public static DBACardFlowCardTypeEnum getIdTypeEnum(String value) {
		DBACardFlowCardTypeEnum type = null;
		for (DBACardFlowCardTypeEnum tempEnum : DBACardFlowCardTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
