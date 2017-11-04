package com.hgsoft.common.Enum;

public enum TagSalesTypeEnum {
	//1,正常销售 2,优惠销售
		
	normalSale("正常销售","0001"),preferentSale("优惠销售","0000");
	private String value;
	
	private String name;

	/** Internal constructor */
	TagSalesTypeEnum(String name,String value) {
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
	
	public static TagSalesTypeEnum getIdTypeEnum(String value) {
		TagSalesTypeEnum type = null;
		for (TagSalesTypeEnum tempEnum : TagSalesTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
