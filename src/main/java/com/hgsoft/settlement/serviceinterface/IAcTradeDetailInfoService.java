package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.AcTradeDetailInfo;

import java.util.List;

public interface IAcTradeDetailInfoService {

    void batchSaveFromCardInSettleDetail();

    void batchSaveFromCardOutSettleDetail();

    List<AcTradeDetailInfo> findByDetailNoAndStatus(String detailNo, Integer dealStatus);

    int updatePayInfo(AcTradeDetailInfo acTradeDetailInfo, Integer oldPayFlag);

}
