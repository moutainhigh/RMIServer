package com.hgsoft.common.Enum;

public enum InvoiceChangeFlowStateEnum {
	noeffect("未生效","0"),
	effect("已生效","1"),
	merge("合并打印","2"),
	single("不合并打印","3");
    
	private String value;

	private String name;

    // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private InvoiceChangeFlowStateEnum(String name, String value) {
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
    InvoiceChangeFlowStateEnum(String value) {
		this.value = value;
	}
	
	public static InvoiceChangeFlowStateEnum getInvoiceChangeFlowStateEnum(String value) {
		InvoiceChangeFlowStateEnum invoiceChangeFlowStateEnum = null;
		for (InvoiceChangeFlowStateEnum tempEnum : InvoiceChangeFlowStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				invoiceChangeFlowStateEnum = tempEnum;
				break;
			}
		}
		return invoiceChangeFlowStateEnum;
	}

	public static String getNameByValue(String value) {
		String name = null;
		for (InvoiceChangeFlowStateEnum tempEnum : InvoiceChangeFlowStateEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				name = tempEnum.getName();
				break;
			}
		}
		return name;
	}
}
