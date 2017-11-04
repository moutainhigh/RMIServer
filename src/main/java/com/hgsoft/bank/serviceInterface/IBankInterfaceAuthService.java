package com.hgsoft.bank.serviceInterface;

/**
 * Created by 孙晓伟
 * file : IBankInterfaceAuthService.java
 * date : 2017/7/27
 * time : 10:04
 */
public interface IBankInterfaceAuthService {
    public boolean findBankAuthInfo(String bankCode, String transactionCode, String businessCode, String businessType);
}


