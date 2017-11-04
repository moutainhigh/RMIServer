package com.hgsoft.common.Enum;

public enum TagTakeFeeChargeTypeEnum {
	cash("现金","1"),pos("POS","2"),transfer("转账","3"),overCorrect("长款修正","4"),other("其他","5"),voucher("缴款单","6");
	private String value;

	private String name;

	/** Internal constructor */
	TagTakeFeeChargeTypeEnum(String name, String value) {
		this.name=name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.name();
	}
	
	public static String getNameByValue(String value) {
		String name = null;
		for(TagTakeFeeChargeTypeEnum tempEnum : TagTakeFeeChargeTypeEnum.values()){
			if(tempEnum.getValue().equals(value)){
				name = tempEnum.getName();
				break;
			}
		}
		return name;
	}
	
	public static TagTakeFeeChargeTypeEnum getIdTypeEnum(String value) {
		TagTakeFeeChargeTypeEnum type = null;
		for (TagTakeFeeChargeTypeEnum tempEnum : TagTakeFeeChargeTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
