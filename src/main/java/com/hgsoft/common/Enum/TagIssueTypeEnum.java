package com.hgsoft.common.Enum;

public enum TagIssueTypeEnum {
//1：提货发行一次完成；2：先提货后发行3：备件标签发行4：更换发行
	
	firstIss("提货发行一次完成","1"),issue("先提货后发行","2"),backTagIss("备件标签发行","3"),replaceTagIss("更换发行","4");
	
	private String value;

	private String name;

	/** Internal constructor */
	TagIssueTypeEnum(String name,String value) {
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
	
	public static TagIssueTypeEnum getIdTypeEnum(String value) {
		TagIssueTypeEnum type = null;
		for (TagIssueTypeEnum tempEnum : TagIssueTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
}
