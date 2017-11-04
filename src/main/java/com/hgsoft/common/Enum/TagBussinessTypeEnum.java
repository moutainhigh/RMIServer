package com.hgsoft.common.Enum;

public enum TagBussinessTypeEnum {
	
	tagIssuce("电子标签发行","1"),
	tagRepair("电子标签维修登记","2"),
	tagChange("电子标签更换","3"),
	tagRecover("电子标签恢复","4"),
	tagStop("电子标签挂起","5"),
	tagMirate("电子标签解除挂起","6"),
	tagCannel("电子标签注销","7"),
	tagDelete("电子标签发行删除","8"),
	tagWriteBack("激活卡回写","9"),
	tagRepairReturn("电子标签维修返回客户","10"),
	tagUnLoss("电子标签解挂","11"),	
	tagLoss("电子标签挂失","12"),	
	tagInfoUpdate("修改电子标签发行信息","13"),	
	tagTake("电子标签提货","14"),	
	tagNoCashRevoer("电子标签无偿恢复","15"),	
	tagLianCardOpenRequire("联营卡开通申请","16"),	
	tagWriteCard("电子标签写入车牌","17"),	
	tagDeleteCard("电子标签删除车牌","18"),	
	tagTakeInfoUpdate("电子标签提货修改","19"),	
	tagtakefeeinfoAdd("新增电子标签提货金额","20"),
	tagtakefeeinfoUpdate("修改电子标签提货金额","21"),
	tagtakefeeinfoDelete("删除电子标签提货金额","22"),
	tagTransfer("电子标签过户","23");
	
	
	
	
	
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private TagBussinessTypeEnum(String name, String value) {
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
    TagBussinessTypeEnum(String value) {
		this.value = value;
	}
	
	public static TagBussinessTypeEnum getTagBussinessTypeEnum(String value) {
		TagBussinessTypeEnum type = null;
		for (TagBussinessTypeEnum tempEnum : TagBussinessTypeEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				type = tempEnum;
				break;
			}
		}
		return type;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (TagBussinessTypeEnum tempEnum : TagBussinessTypeEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
