package com.hgsoft.common.Enum;

public enum NSCVehicleTypeEnum {
	oneModelTruck("1型货车","11"),
	twoModelTruck("2型货车","12"),
	threeModelTruck("3型货车","13"),
	fourModelTruck("4型货车","14"),
	fiveModelTruck("5型货车","15"),
	oneModelCar("1型客车","1"),
	twoModelCar("2型客车","2"),
	threeModelCar("3型客车","3"),
	fourModelCar("4型客车","4");
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private NSCVehicleTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String index) {
        for (NSCVehicleTypeEnum c : NSCVehicleTypeEnum .values()) {
            if (c.getValue().equals(index)) {
                return c.name;
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
    NSCVehicleTypeEnum(String value) {
		this.value = value;
	}
	
	public static NSCVehicleTypeEnum getNSCVehicleTypeEnum(String value) {
		NSCVehicleTypeEnum nscChangeTypeEnum = null;
		for (NSCVehicleTypeEnum tempEnum : NSCVehicleTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				nscChangeTypeEnum = tempEnum;
				break;
			}
		}
		return nscChangeTypeEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (NSCVehicleTypeEnum tempEnum : NSCVehicleTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
