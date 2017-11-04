package com.hgsoft.settlement.serviceinterface;

import java.util.List;

public interface ICardInSettleDetailRecvService {

    List<Object[]> findAllObjsByBoardListNoFromClear(Long boardListNo);

    void batchSave(List<Object[]> objs);

    void deleteByBoardListNo(Long boardListNo);
}
