package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.AccBankListReturn;
import com.hgsoft.settlement.entity.CommandInfo;

import java.util.List;

/**
 * Created by yangzhongji on 17/9/23.
 */
public interface IAccBankListReturnService {

    List<AccBankListReturn> listByCommandInfo(CommandInfo commandInfo);
}
