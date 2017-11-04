package com.hgsoft.bank.service;

import com.hgsoft.bank.dao.BankInterfaceAuthInfoDao;
import com.hgsoft.bank.entity.BankInterfaceAuthInfo;
import com.hgsoft.bank.serviceInterface.IBankInterfaceAuthService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 孙晓伟
 * file : BankInterfaceAuthService.java
 * date : 2017/7/27
 * time : 10:05
 */
@Service
public class BankInterfaceAuthService implements IBankInterfaceAuthService {
    private static Logger logger = Logger.getLogger(BankInterfaceAuthService.class.getName());

    @Resource
    private BankInterfaceAuthInfoDao bankInterfaceAuthInfoDao;

    /***
     * 查找银行接口权限
     * @param bankCode
     * @param transactionCode
     * @param businessCode
     * @param businessType
     * @return
     */
    @Override
    public boolean findBankAuthInfo(String bankCode, String transactionCode, String businessCode, String businessType) {
        boolean flag = false;
        List<BankInterfaceAuthInfo> list = bankInterfaceAuthInfoDao.findBankAuthInfo(bankCode,transactionCode,businessCode,businessType);
        if(list!=null&&!list.isEmpty()){
            flag = true;
        }
        return flag;
    }
}
