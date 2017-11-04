package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.CardInSettleDetailRecvDao;
import com.hgsoft.settlement.dao.ClearCardInSettleDetailRecvDao;
import com.hgsoft.settlement.serviceinterface.ICardInSettleDetailRecvService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CardInSettleDetailRecvService implements ICardInSettleDetailRecvService {

    private static Logger logger = Logger.getLogger(CardInSettleDetailRecvService.class);

    @Resource
    private CardInSettleDetailRecvDao cardInSettleDetailRecvDao;
    @Resource
    private ClearCardInSettleDetailRecvDao clearCardInSettleDetailRecvDao;

    @Override
    public List<Object[]> findAllObjsByBoardListNoFromClear(Long boardListNo) {
        return clearCardInSettleDetailRecvDao.findAllObjsByBoardListNo(boardListNo);
    }

    @Override
    public void batchSave(List<Object[]> objs) {
        cardInSettleDetailRecvDao.batchSave(objs);
    }

    @Override
    public void deleteByBoardListNo(Long boardListNo) {
        cardInSettleDetailRecvDao.deleteByBoardListNo(boardListNo);
    }
}
