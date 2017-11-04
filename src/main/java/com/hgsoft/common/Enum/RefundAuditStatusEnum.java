package com.hgsoft.common.Enum;

public enum RefundAuditStatusEnum {
	directorNotPass("主任审核不通过","0"),request("申请","1"),directorPass("主任审核通过","2"),operView("营运中心审核","3"),financeRefund("财务退款完成","4"),revoke("撤销","6"),financeRefundFail("财务退款失败","7"),refundAgain("再次退款","8")
	,settleDone("结算中心处理完成","9"),waitSettle("待结算中心处理","10"),waitOmsApp("待营运审批","11"),omsAppNotPass("营运审批不通过","12");
	
	private String value;

	private String name;
	
	 // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
    private RefundAuditStatusEnum(String name, String value) {
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
    RefundAuditStatusEnum(String value) {
		this.value = value;
	}
	
	public static RefundAuditStatusEnum getRefundAuditStatusEnum(String value) {
		RefundAuditStatusEnum refundAuditStatusEnum = null;
		for (RefundAuditStatusEnum tempEnum : RefundAuditStatusEnum.values()) {
			if (tempEnum.getValue().equals(value)) {
				refundAuditStatusEnum = tempEnum;
				break;
			}
		}
		return refundAuditStatusEnum;
	}
	 public static String getNameByValue(String value){
		  String name = null;
		  for (RefundAuditStatusEnum tempEnum : RefundAuditStatusEnum.values()) {
		   if (tempEnum.getValue().equals(value)) {
		    name=tempEnum.getName();
		    break;
		   }
		  }
		  return name;
		 }
}
