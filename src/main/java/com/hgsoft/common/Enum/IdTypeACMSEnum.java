package com.hgsoft.common.Enum;

public enum IdTypeACMSEnum {

	macaoIdCard("澳门永久/非永久居民身份证","13"),hongKong("香港永久/非永久居民身份证","14"),chinaIdCard("中华人民共和国居民身份证","15"),
	passport("护照","16"),companyBusReg("公司商业登记副本（3个月内）","17"),
	companyBusLicM8("公司营业税单M8副本","18"),companyBusLicM1("公司营业税开业/更改M1副本","19");
	
	private String value;
	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private IdTypeACMSEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (IdTypeEnum c : IdTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.getName();
            }
        }
        return null;
    }
    
    public static String getValue(String name) {
        for (IdTypeEnum c : IdTypeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.getValue();
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
    IdTypeACMSEnum(String value) {
		this.value = value;
	}
	
	public static IdTypeEnum getIdTypeEnum(String value) {
		IdTypeEnum idTypeEnum = null;
		for (IdTypeEnum tempEnum : IdTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				idTypeEnum = tempEnum;
				break;
			}
		}
		return idTypeEnum;
	}
	
}
