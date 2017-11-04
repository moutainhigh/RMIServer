package com.hgsoft.ygz.util;

/**
 * Created by RuiHaoZ on 2017/11/1.
 */
public class DataTransfer {

        public static String idTypeTransfer(String idType,String userType) {
                try {
                        if (idType == null || userType == null){
                                return null;
                        }
                        if (idType.equals("1")) {//身份证
                                return "101";
                        } else if (idType.equals("8")) {//护照
                                return "102";
                        } else if (idType.equals("10")) {//港澳居民来往内地通行证
                                return "103";
                        } else if (idType.equals("11")) {//台湾居民来往大陆通行证
                                return "104";
                        } else if (idType.equals("0")) {//军官证
                                return "106";
                        } else if (idType.equals("12")) {//武警警官身份证
                                return "104";
                        } else if (idType.equals("9")) {//信用代码证
                                return "201";
                        } else if (idType.equals("7")) {//组织机构代码证
                                return "202";
                        } else if (idType.equals("2")) {//营业执照
                                return "203";
                        } else if (idType.equals("20")) {//事业单位法人证书
                                return "204";
                        } else if (idType.equals("21")) {//社会团体法人登记证书
                                return "205";
                        } else if (idType.equals("22")) {//律师事务所执业许可证
                                return "206";
                        }
                        if (userType.equals("1")) {
                                return "199";
                        } else {
                                return "299";
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
        }


}
