package com.hgsoft.common.Enum;

/**
 * Created by yangzhongji on 17/7/15.
 */
public enum ServiceWaterSerType {
	
	/*客户模块*/
	servicePwdResert("服务密码重设", "101"),
	servicePwdUpdate("服务密码更改", "102"),
	vehicleInfoUpdate("车辆信息修改", "103"),
	customerInfoUpdate("客户资料修改", "104"),
	invoiceEmailRegiste("发票邮寄登记", "105"),
	invoiceCancel("发票邮寄取消", "106"),
	openApply("开户申请", "107"),
	vehicleInfoAdd("车辆信息新增", "108"),
	customerInfoAdd("客户资料新增", "109"),
	customerInfoCancel("客户资料注销", "110"),
	vehicleInfoDelete("车辆信息删除", "111"),
	invoiceInfoAdd("发票信息新增", "112"),
	invoiceInfoUpdate("发票信息修改", "113"),
	invoiceInfoDelete("发票信息删除", "114"),
	billGetUpdate("信息服务修改", "115"),
	cusCancelReasonUpate("客户注销原因修改", "116"),
	
	/*记帐卡模块*/
	accIssue("记帐卡发行", "201"),
	accCancelWithCard("记帐卡有卡终止使用", "202"),
	accCancelWithoutCard("记帐卡无卡终止使用", "203"),
	accPwdUpdate("记帐卡密码更改", "204"),
	accPwdReset("记帐卡密码重设", "205"),
	accReplaceCard("记帐卡补领", "206"),
	accUnusable("记帐卡旧卡锁定", "207"),
	accGainCard("记帐卡领取新卡", "208"),
	utCardLost("粤通卡挂失", "209"),
	utCardCancelApply("粤通卡销户申请", "210"),
	accFirstApply("记帐卡初次申请", "211"),
	accFirstApplyUpdate("记帐卡初次申请修改", "212"),
	accNewApply("记帐卡新增卡申请", "213"),
	accNewApplyUpdate("记帐卡新增卡申请修改", "214"),
	bailAdd("保证金新增", "215"),
	bailBack("保证金退还", "216"),
	accountRename("账户名称变更", "217"),
	accMigrate("记帐卡迁移", "218"),
	accTransfer("记帐卡过户", "219"),
	accUnLost("记帐卡解挂", "220"),
	applyRelieveStopBlack("申请解除止付", "221"),
	relieveStopBlackWithHand("手工解除止付黑名单", "222"),
	rechargePassFeeWithHand("手工缴纳通行费", "223"),
	bailDeduction("记帐卡保证金抵扣", "224"),
	bailBackCancel("保证金退还撤销", "225"),
	
	/*标签模块*/
	tagIssue("电子标签发行", "301"),
	tagDelete("电子标签删除", "302"),
	tagReplace("电子标签更换", "303"),
	tagRecover("电子标签恢复", "304"),
	tagCancel("电子标签注销", "305"),
	tagMigrate("电子标签迁移", "306"),
	tagStop("电子标签挂起", "307"),
	tagWriteBack("电子标签回写", "308"),
	stopWithoutTag("无标签挂起", "309"),
	tagMantainRegiste("电子标签维修登记", "310"),
	tagMantainReturnCustomer("电子标签维修返回客户", "311"),
	tagTakeAdd("电子标签提货", "312"),
	
	/*其他模块的*/
	cardStop("卡片挂起", "401"),
	cardStart("卡片挂起", "402"),
	stopWithoutCard("无卡挂起", "403"),
	veicleOwnerChange("被动挂起", "404"),
	suitTransfer("套装过户", "405"),
	chaseMoney("营业厅追款", "406"),
	specialFee("特殊费用收取", "407"),
	
	/*储值卡模块*/
	preIssue("储值卡发行", "501"),
    manualRecharge("自营客服系统：储值卡人工充值", "502"),
    addRegRecharge("自营客服系统：储值卡充值登记充值", "503"),
    manualRechargeReversal("自营客服系统：储值卡充值冲正", "504"),
    preTradingPwdUpdate("储值卡消费密码修改", "505"),
    preTradingPwdReset("储值卡消费密码重设", "506"),
    preLost("储值卡挂失", "507"),
    preUnlost("储值卡解挂", "508"),
    preReplace("储值卡补领", "509"),
    preUnusable("储值卡旧卡锁定", "510"),
    preGainCard("储值卡领取新卡", "511"),
    preCancelWithCard("储值有卡终止使用", "512"),
    preCancelWithoutCard("储值无卡终止使用", "513"),
    preStop("储值卡挂起", "514"),
    preStart("储值卡解除挂起", "515"),
    preDelete("储值卡删除", "516"),
    preInvoiceTypeChange("储值卡发票类型变更", "517"),
    preTransfer("储值卡过户", "518"),
    noLoginRecharge("自营客服系统：储值卡直充充值", "519"),
    noLoginRechargeReversal("自营客服系统：储值卡直充充值冲正", "520"),
    addRegWithHand("储值卡手工录入充值登记", "532"),
    addRegBatchInput("储值卡批量导入充值登记", "533"),
    deleteAddReg("储值卡充值登记删除", "536"),
    
    /*账户模块*/
    accountRecharge("账户缴款", "601"),
    accountCorrect("账户冲正", "602"),
    accountRechargeUpdate("账户缴款修改", "603"),
    accountTransferRecharge("账户转账缴款", "604"),
    accountRefund("账户退款", "605"),
    accountRefundDelete("账户退款撤销", "606"),
    
    /*不知名模块*/
    stopBlackListAdd("止付黑名单下发", "701"),
    stopBlackListRelieve("止付黑名单解除", "702"),
    restartAfterCancel("销户后重启", "703"),
    notTransferReplace("非过户补领", "704"),
    gainCardApply("换卡申请", "705"),
    writeCardAfter("补写卡", "706"),
	
	/*联营卡专用*/
	cardHolderInfoEdit("持卡人资料修改", "801");
	

    private String value;
    private String name;

    /** Internal constructor */
    ServiceWaterSerType(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getName() {
        return name;
    }

    public static ServiceWaterSerType getEnumByValue(String value) {
        ServiceWaterSerType type = null;
        for (ServiceWaterSerType tempEnum : ServiceWaterSerType.values()) {
            if (tempEnum.getValue().equals(value)) {
                type = tempEnum;
                break;
            }
        }
        return type;
    }

    public static String getNameByValue(String value) {
        String name = null;
        for (ServiceWaterSerType tempEnum : ServiceWaterSerType.values()) {
            if (tempEnum.getValue().equals(value)) {
                name = tempEnum.getName();
                break;
            }
        }
        return name;
    }
}
