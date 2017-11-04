package com.hgsoft.timerTask.service;

import com.hgsoft.settlement.entity.ProviceRecvBoard;
import com.hgsoft.settlement.serviceinterface.*;
import com.hgsoft.timerTask.serviceinterface.ISettlementTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangzhongji on 17/6/13.
 */
@Service
public class SettlementTaskService implements ISettlementTaskService {

    @Resource
    private IRoadService roadService;

    @Resource
    private IProviceRecvBoardService proviceRecvBoardService;
    @Resource
    private ICardInSettleDetailRecvService cardInSettleDetailRecvService;
    @Resource
    private ICardOutSettleDetailRecvService cardOutSettleDetailRecvService;
    @Resource
    private IScTradeDetailInfoService scTradeDetailInfoService;

    @Override
    public void helloWorld() {
        //路段信息处理
        roadService.deleteAll();
        List<Object[]> roads = roadService.findAllObjsFromOms();
        roadService.batchSave(roads);

        //公告记录表预处理
        List<Object[]> proviceArgs = proviceRecvBoardService.queryObjsByFlag(ProviceRecvBoard.UPDAETFLAG_INIT);
        proviceRecvBoardService.batchUpdateFlagToClear(proviceArgs);
        //从清分库获取未处理的数据保存到本库
        List<Object[]> proviceObjs = proviceRecvBoardService.findAllObjsFromClear();
        proviceRecvBoardService.batchSave(proviceObjs);

        //从本地获取的公告数据
        List<ProviceRecvBoard> proviceBoards = proviceRecvBoardService.queryByFlag(ProviceRecvBoard.UPDAETFLAG_TAKE);
        for (ProviceRecvBoard proviceRecvBoard : proviceBoards) {
            Long boardListNo = proviceRecvBoard.getListNo();
            String tableCode = proviceRecvBoard.getTableCode();
            if (ProviceRecvBoard.TABLECODE_CARDINSETTLEDETAIL.equals(tableCode)) {
                cardInSettleDetailRecvService.deleteByBoardListNo(boardListNo);
                List<Object[]> cardInSettleDetailArgs = cardInSettleDetailRecvService.findAllObjsByBoardListNoFromClear(boardListNo);
                if (cardInSettleDetailArgs != null && proviceRecvBoard.getCnt()!=null && proviceRecvBoard.getCnt().equals(cardInSettleDetailArgs.size())) {
                    cardInSettleDetailRecvService.batchSave(cardInSettleDetailArgs);
                    proviceRecvBoardService.updateFlag(boardListNo, tableCode, ProviceRecvBoard.UPDAETFLAG_VERIFIED, ProviceRecvBoard.UPDAETFLAG_TAKE);
                } else {
                    proviceRecvBoardService.updateFlag(boardListNo, tableCode, ProviceRecvBoard.UPDAETFLAG_VERIFYFAIL, ProviceRecvBoard.UPDAETFLAG_TAKE);
                }
            } else if (ProviceRecvBoard.TABLECODE_CARDOUTSETTLEDETAIL.equals(tableCode)) {
                cardOutSettleDetailRecvService.deleteByBoardListNo(boardListNo);
                List<Object[]> cardOutSettleDetailArgs = cardOutSettleDetailRecvService.findAllObjsByBoardListNoFromClear(boardListNo);
                if (cardOutSettleDetailArgs != null && proviceRecvBoard.getCnt() != null && proviceRecvBoard.getCnt().equals(cardOutSettleDetailArgs.size())) {
                    cardInSettleDetailRecvService.batchSave(cardOutSettleDetailArgs);
                    proviceRecvBoardService.updateFlag(boardListNo, tableCode, ProviceRecvBoard.UPDAETFLAG_VERIFIED, ProviceRecvBoard.UPDAETFLAG_TAKE);
                } else {
                    proviceRecvBoardService.updateFlag(boardListNo, tableCode, ProviceRecvBoard.UPDAETFLAG_VERIFYFAIL, ProviceRecvBoard.UPDAETFLAG_TAKE);
                }
            }
        }

    }


}
