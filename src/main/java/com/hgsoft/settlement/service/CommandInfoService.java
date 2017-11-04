package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.CommandInfoDao;
import com.hgsoft.settlement.entity.AccBankListReturn;
import com.hgsoft.settlement.entity.CommandInfo;
import com.hgsoft.settlement.serviceinterface.ICommandInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangzhongji on 17/6/13.
 */
@Service
public class CommandInfoService implements ICommandInfoService {

    @Resource
    private CommandInfoDao commandInfoDao;

    @Override
    public List<CommandInfo> findTollFeeCommand() {
        return commandInfoDao.findByFeeType(CommandInfo.FeeTypeEnum.TOLL_FEE.getValue());
    }

    @Override
    public int deleteByAccBankReturn(AccBankListReturn accBankListReturn) {
        return commandInfoDao.deleteByAccBankReturn(accBankListReturn);
    }
}

