package com.hgsoft.common.Enum;

public enum TagMigrateReqAhtuStateEnum {
	
	wateAuth("待审核",1),
	authPass("审核通过",2),
	authNoPass("审核不通过",3);
	
	
	
	
	
	private Integer value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private TagMigrateReqAhtuStateEnum(String name, Integer value) {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer index) {
        this.value = index;
    }

	/** Internal constructor */
    TagMigrateReqAhtuStateEnum(Integer value) {
		this.value = value;
	}
	
	public static TagMigrateReqAhtuStateEnum getTagBussinessTypeEnum(String value) {
		TagMigrateReqAhtuStateEnum type = null;
		for (TagMigrateReqAhtuStateEnum tempEnum : TagMigrateReqAhtuStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (TagMigrateReqAhtuStateEnum tempEnum : TagMigrateReqAhtuStateEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
