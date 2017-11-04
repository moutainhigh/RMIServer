package com.hgsoft.common.Enum;

public enum CustomerBussinessTypeEnum {
	customerAdd("客户信息新增","11"),
	customerUpdate("客户信息修改","12"),
	customerCancel("客户信息注销","13"),
	customerCancelUpdate("客户注销原因修改","14"),
	vechileAdd("车辆信息新增","15"),
	vechileUpdate("车辆信息修改","16"),
	vechileMigrate("车辆迁移","17"),
	invoiceAdd("客户发票信息新增","18"),
	invoiceUpdate("客户发票信息修改","19"),
	invoiceDelete("客户发票信息删除","20"),
	billUpdate("客户信息服务项修改","21"),
	passwordUpdate("客户服务密码修改","22"),
	passwordReset("客户服务密码重设","23"),
	vechileDelete("客户车辆删除","24"),
	customerInfoCombine("客户信息合并","25");
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private CustomerBussinessTypeEnum(String name, String value) {
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
    CustomerBussinessTypeEnum(String value) {
		this.value = value;
	}
	
	public static CustomerBussinessTypeEnum getCustomerBussinessTypeEnum(String value) {
		CustomerBussinessTypeEnum accChangeTypeEnum = null;
		for (CustomerBussinessTypeEnum tempEnum : CustomerBussinessTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				accChangeTypeEnum = tempEnum;
				break;
			}
		}
		return accChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (CustomerBussinessTypeEnum tempEnum : CustomerBussinessTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
