package com.hgsoft.common.Enum;

public enum VehicleBussinessEnum {
	addVehicle("车辆新增","11"),
	deleteVehicle("车辆删除","12"),
	migrateVehicle("车辆迁移","13"),//已废弃功能
	passiveStop("被动挂起","14"),//涉及到解除绑定车辆
	updateVehicle("车辆修改","15"),
	
	prepaidCIssue("储值卡发行","21"),
	prepaidCDelete("储值卡发行删除","22"),
	prepaidCDisabledWithCard("储值卡有卡挂起","23"),
	prepaidCDisabledWithoutCard("储值卡无卡挂起","24"),
	prepaidCAbled("储值卡解除挂起","25"),
	prepaidCReplaceCard("储值卡补领","26"),
	prepaidCGainWithCard("储值卡有卡换卡","27"),
	prepaidCGainWithoutCard("储值卡无卡换卡","28"),
	prepaidCStopWithCard("储值卡有卡终止使用","29"),
	prepaidCStopWithoutCard("储值卡无卡终止使用","210"),
	
	accountCIssue("记帐卡发行","31"),
	accountCDisabledWithCard("记帐卡有卡挂起","32"),
	accountCDisabledWithoutCard("记帐卡无卡挂起","33"),
	accountCAbled("记帐卡解除挂起","34"),
	accountCReplaceCard("记帐卡补领","35"),
	accountCGainWithCard("记帐卡有卡换卡","36"),
	accountCGainWithoutCard("记帐卡无卡换卡","37"),
	accountCStopWithCard("记帐卡有卡终止使用","38"),
	accountCStopWithoutCard("记帐卡无卡终止使用","39"),
	
	tagIssue("电子标签发行","41"),
	tagDelete("电子标签删除","42"),
	tagReplace("电子标签更换","43"),
	tagDisabledWithTag("电子标签有标签挂起","44"),
	tagDisabledWithoutTag("电子标签无标签挂起","45"),
	tagMigrate("电子标签迁移","46"),
	tagCancelWithTag("电子标签有标签注销","47"),
	tagCancelWithoutTag("电子标签无标签注销","48"),
	tagMaintainRegiste("电子标签维修登记","49"),
	tagMaintainReturnCustomer("电子标签维修返回客户","410");
	
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private VehicleBussinessEnum(String name, String value) {
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
    VehicleBussinessEnum(String value) {
		this.value = value;
	}
	
	public static VehicleBussinessEnum getVehicleBussinessEnum(String value) {
		VehicleBussinessEnum vehicleBussinessEnum = null;
		for (VehicleBussinessEnum tempEnum : VehicleBussinessEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				vehicleBussinessEnum = tempEnum;
				break;
			}
		}
		return vehicleBussinessEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (VehicleBussinessEnum tempEnum : VehicleBussinessEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
