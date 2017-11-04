package com.hgsoft.common.Enum;

public enum ScAddSureConfirmTypeEnum {
	normal("正常充值","0"),
	frontSure("通过前端页面半条确认","1"),
	backSure("后端处理半条确认","2");

	private String value;
	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private ScAddSureConfirmTypeEnum(String name, String value) {
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


	/** Internal constructor */
    ScAddSureConfirmTypeEnum(String value) {
		this.value = value;
	}
	
	public static ScAddSureConfirmTypeEnum getEnumByValue(String value) {
		ScAddSureConfirmTypeEnum retEnum = null;
		for (ScAddSureConfirmTypeEnum tempEnum : ScAddSureConfirmTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				retEnum = tempEnum;
				break;
			}
		}
		return retEnum;
	}

	public static String getNameByValue(String value){
		  String name = null;
		  for (ScAddSureConfirmTypeEnum tempEnum : ScAddSureConfirmTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
	}
}
