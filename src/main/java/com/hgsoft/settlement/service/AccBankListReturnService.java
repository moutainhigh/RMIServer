package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.AccBankListReturnDao;
import com.hgsoft.settlement.entity.AccBankListReturn;
import com.hgsoft.settlement.entity.CommandInfo;
import com.hgsoft.settlement.serviceinterface.IAccBankListReturnService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangzhongji on 17/9/23.
 */
@Service
public class AccBankListReturnService implements IAccBankListReturnService {
    @Resource
    private AccBankListReturnDao accBankListReturnDao;

    @Override
    public List<AccBankListReturn> listByCommandInfo(CommandInfo commandInfo) {
        return accBankListReturnDao.listByCommandInfo(commandInfo);
    }
}
