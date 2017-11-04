package com.hgsoft.common.Enum;

public enum DBACardFlowEndSerTypeEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行
	
	prePaidReplace("充值","04"),
	accountBankCannel("记帐卡账户注销","17"),
	prepaidCCardIssuice("储值卡发行","24"),
	accountCCardIssuice("记帐卡发行","27");;
	
	private String value;

	private String name;

	/** Internal constructor */
	DBACardFlowEndSerTypeEnum(String name,String value) {
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
	
	public static DBACardFlowEndSerTypeEnum getIdTypeEnum(String value) {
		DBACardFlowEndSerTypeEnum type = null;
		for (DBACardFlowEndSerTypeEnum tempEnum : DBACardFlowEndSerTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
