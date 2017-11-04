package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.AcTradeDetailInfo;

/**
 * Created by yangzhongji on 17/9/28.
 */
public interface IHandPaymentRepeatDetailService {

    int insertByAcTradeDetail(AcTradeDetailInfo acTradeDetailInfo);
}
