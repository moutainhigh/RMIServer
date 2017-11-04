package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.CardOutSettleDetailRecvDao;
import com.hgsoft.settlement.dao.ClearCardOutSettleDetailRecvDao;
import com.hgsoft.settlement.serviceinterface.ICardOutSettleDetailRecvService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CardOutSettleDetailRecvService implements ICardOutSettleDetailRecvService {

    private static Logger logger = Logger.getLogger(CardOutSettleDetailRecvService.class);

    @Resource
    private CardOutSettleDetailRecvDao cardOutSettleDetailRecvDao;
    @Resource
    private ClearCardOutSettleDetailRecvDao clearCardOutSettleDetailRecvDao;

    @Override
    public List<Object[]> findAllObjsByBoardListNoFromClear(Long boardListNo) {
        return clearCardOutSettleDetailRecvDao.findAllObjsByBoardListNo(boardListNo);
    }

    @Override
    public void batchSave(List<Object[]> objs) {
        cardOutSettleDetailRecvDao.batchSave(objs);
    }

    @Override
    public void deleteByBoardListNo(Long boardListNo) {
        cardOutSettleDetailRecvDao.deleteByBoardListNo(boardListNo);
    }
}
