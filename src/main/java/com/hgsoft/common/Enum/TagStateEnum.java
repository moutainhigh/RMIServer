package com.hgsoft.common.Enum;

public enum TagStateEnum {
//1：正常2：停用3：维修 4：注销
	normal("正常","1"),stop("停用","2"),repair("维修","3"),cancel("注销","4");
	private String value;

	private String name;

	/** Internal constructor */
	TagStateEnum(String name,String value) {
		this.name=name;
		this.value = value;
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
	
	public static TagStateEnum getIdTypeEnum(String value) {
		TagStateEnum type = null;
		for (TagStateEnum tempEnum : TagStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
