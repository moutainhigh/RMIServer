package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.AccBankListReturn;
import com.hgsoft.settlement.entity.CommandInfo;

import java.util.List;

public interface ICommandInfoService {

    /**
     * 查询按照生成时间、银行编号、划扣账户、流水汇总的通行费指令
     * @return
     */
    List<CommandInfo> findTollFeeCommand();

    /**
     * 根据银行返盘数据（生成时间、银行编号、划扣账户）删除指令对照表记录
     * @param accBankListReturn
     * @return
     */
    int deleteByAccBankReturn(AccBankListReturn accBankListReturn);
}
