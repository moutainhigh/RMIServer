package com.hgsoft.utils;

public class ReceiptUtil {

    /**
     * 根据验证方式，获取回执办理方式
     * @param authenticationType
     * @return
     */
    public static String getHandleWay(String authenticationType){
        if(StringUtil.isNotBlank(authenticationType)&&!"null".equals(authenticationType.toLowerCase())){
            if("3".equals(authenticationType)||"5".equals(authenticationType)){
                return "凭密码办理";
            }else{
                return "凭资料办理";
            }
        }else{
            return "";
        }
    }
}
