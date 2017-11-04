package com.hgsoft.common.Enum;

public enum CardTypeEnum {
	standardCard("标准粤通卡","0"),autotollCard("快易通粤通卡","1"),affinityCard("粤通卡银行联名卡","2");
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private CardTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String index) {
        this.value = index;
    }

	/** Internal constructor */
    CardTypeEnum(String value) {
		this.value = value;
	}
	
	public static CardTypeEnum getAccChangeTypeEnum(String value) {
		CardTypeEnum accChangeTypeEnum = null;
		for (CardTypeEnum tempEnum : CardTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (CardTypeEnum tempEnum : CardTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
