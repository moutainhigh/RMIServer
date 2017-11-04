package com.hgsoft.common.Enum;

public enum UserStateInfoDealFlagEnum {
	bindCarAndCard("新增车牌（卡）","1"),bindCarAndObu("新增车牌（OBU）","2"),unbindCarAndCard("删除车牌（卡）","3"),unbindCarAndObu("删除车牌（OBU）","4");
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private UserStateInfoDealFlagEnum(String name, String value) {
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
    UserStateInfoDealFlagEnum(String value) {
		this.value = value;
	}
	
	public static UserStateInfoDealFlagEnum getAccChangeTypeEnum(String value) {
		UserStateInfoDealFlagEnum accChangeTypeEnum = null;
		for (UserStateInfoDealFlagEnum tempEnum : UserStateInfoDealFlagEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (UserStateInfoDealFlagEnum tempEnum : UserStateInfoDealFlagEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
