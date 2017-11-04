package com.hgsoft.bank.service;

import com.hgsoft.bank.dao.BankInterfaceDao;
import com.hgsoft.bank.entity.BankInterface;
import com.hgsoft.bank.serviceInterface.IBankInterfaceService;
import com.hgsoft.common.util.FieldUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : 吴锡霖
 *         file : BankInterfaceService.java
 *         date : 2017-07-10
 *         time : 13:58
 */
@Service
public class BankInterfaceService implements IBankInterfaceService {
    @Resource
    private BankInterfaceDao bankInterfaceDao;
    
    @Override
    public void save(BankInterface bankInterface) {
        bankInterfaceDao.save(bankInterface);
    }
}
