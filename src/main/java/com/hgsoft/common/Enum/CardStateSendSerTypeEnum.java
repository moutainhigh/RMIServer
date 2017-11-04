package com.hgsoft.common.Enum;

public enum CardStateSendSerTypeEnum {
	scIssue("储值卡发行","27"),acIssue("记帐卡发行","24"),loss("卡片挂失","10"),unloss("卡片解挂","05"),
	stopWithCard("有卡挂起","G1"),stopWithOutCard("无卡挂起","G2"),startCard("卡片解除挂起","G3"),
    cancelWithCard("有卡注销","06"),cancelWithOutCard("无卡注销","01");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private CardStateSendSerTypeEnum(String name, String value) {
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
    CardStateSendSerTypeEnum(String value) {
		this.value = value;
	}
	
	public static CardStateSendSerTypeEnum getAccChangeTypeEnum(String value) {
		CardStateSendSerTypeEnum accChangeTypeEnum = null;
		for (CardStateSendSerTypeEnum tempEnum : CardStateSendSerTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (CardStateSendSerTypeEnum tempEnum : CardStateSendSerTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
