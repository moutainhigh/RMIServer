package com.hgsoft.common.Enum;

public enum UserTypeEnum {
/*
	1.国家机关:state_organs
	2.事业单位:cause_company
	3.国有企业:state_owned_enterprise
	4.外资企业:foreign_enterprise
	5.民营企业:private_enterprise
	6.部队:troops
	7.团体:team
	8.个人:personal

*/
	stateAgency("国家机关","1"),institution("事业单位","2"),enterprises("国有企业","3"),
	foreignEnterprise("外资企业","4"),privateEnterprise("民营企业","5"),troops("部队","6"),group("团体","7"),person("个人","8")/*,personal("个人","08")*/;
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private UserTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (UserTypeEnum c : UserTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    
 // 普通方法
    public static String getValue(String name) {
        for (UserTypeEnum c : UserTypeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.value;
            }
        }
        return null;
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
    UserTypeEnum(String value) {
		this.value = value;
	}
	
	public static UserTypeEnum getUserTypeEnum(String value) {
		UserTypeEnum userTypeEnum = null;
		for (UserTypeEnum tempEnum : UserTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				userTypeEnum = tempEnum;
				break;
			}
		}
		return userTypeEnum;
	}
}
