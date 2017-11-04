package com.hgsoft.settlement.service;

import com.hgsoft.settlement.dao.ClearProviceRecvBoardDao;
import com.hgsoft.settlement.dao.ProviceRecvBoardDao;
import com.hgsoft.settlement.entity.ProviceRecvBoard;
import com.hgsoft.settlement.serviceinterface.IProviceRecvBoardService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;

@Service
public class ProviceRecvBoardService implements IProviceRecvBoardService {

    private static Logger logger = Logger.getLogger(ProviceRecvBoardService.class);

    @Resource
    private ProviceRecvBoardDao proviceRecvBoardDao;
    @Resource
    private ClearProviceRecvBoardDao clearProviceRecvBoardDao;

    @Override
    public List<Object[]> findAllObjsFromClear() {
        return clearProviceRecvBoardDao.findAllObjs();
    }

    @Override
    public void batchUpdateFlagToClear(List<Object[]> objs) {
        clearProviceRecvBoardDao.batchUpdateFlagForTake(objs);
    }

    @Override
    public void batchSave(List<Object[]> objs) {
        proviceRecvBoardDao.batchSave(objs);
    }

    @Override
    public void batchUpdateFlagForTake(List<Object[]> objs) {
        String sql = "update tb_ProviceRecvBoard set updateflag=1 where listno=? and tablecode=? ";

        int[] argsType = {Types.BIGINT, Types.VARCHAR};

        proviceRecvBoardDao.batchUpdate(sql, objs, argsType);
    }

    @Override
    public void updateFlag(Long listNo, String tableCode, int newFlag, int oldFlag) {
        proviceRecvBoardDao.updateFlag(listNo, tableCode, newFlag, oldFlag);
    }

    @Override
    public List<ProviceRecvBoard> queryByFlag(int flag) {
        return proviceRecvBoardDao.queryByFlag(flag);
    }



    @Override
    public List<ProviceRecvBoard> queryByFlagTableCode(int flag, String tableCode) {
        return proviceRecvBoardDao.queryByFlagTableCode(flag, tableCode);
    }

    @Override
    public List<Object[]> queryObjsByFlag(int flag) {
        return proviceRecvBoardDao.queryObjsByFlag(flag);
    }

    @Override
    public void updateInFlagEnd() {
        proviceRecvBoardDao.updateInFlagEnd();
    }

    @Override
    public void updateOutFlagEnd() {
        proviceRecvBoardDao.updateOutFlagEnd();
    }

    @Override
    public int updateInScGenDetailFlag() {
        return proviceRecvBoardDao.updateGenDetailFlag(ProviceRecvBoard.GENDETAILFLAG_SC_DONE, ProviceRecvBoard.TABLECODE_CARDINSETTLEDETAIL, ProviceRecvBoard.UPDAETFLAG_VERIFIED);
    }

    @Override
    public int updateInAcGenDetailFlag() {
        return proviceRecvBoardDao.updateGenDetailFlag(ProviceRecvBoard.GENDETAILFLAG_AC_DONE, ProviceRecvBoard.TABLECODE_CARDINSETTLEDETAIL, ProviceRecvBoard.UPDAETFLAG_VERIFIED);
    }

    @Override
    public int updateOutScGenDetailFlag() {
        return proviceRecvBoardDao.updateGenDetailFlag(ProviceRecvBoard.GENDETAILFLAG_SC_DONE, ProviceRecvBoard.TABLECODE_CARDOUTSETTLEDETAIL, ProviceRecvBoard.UPDAETFLAG_VERIFIED);
    }

    @Override
    public int updateOutAcGenDetailFlag() {
        return proviceRecvBoardDao.updateGenDetailFlag(ProviceRecvBoard.GENDETAILFLAG_AC_DONE, ProviceRecvBoard.TABLECODE_CARDOUTSETTLEDETAIL, ProviceRecvBoard.UPDAETFLAG_VERIFIED);
    }

    @Override
    public int updateFlagDone() {
        return proviceRecvBoardDao.updateFlagDone();
    }

    @Override
    public List<ProviceRecvBoard> queryClearData() {
        return proviceRecvBoardDao.queryClearData();
    }

    @Override
    public int queryCountClearData(String tableName, Long boardListNo) {
        return  proviceRecvBoardDao.queryCountClearData(tableName,boardListNo);
    }

    @Override
    public int saveClearData2Csms(String tableName, Long boardListNo) {
        return proviceRecvBoardDao.saveClearData2Csms(tableName,boardListNo);
    }

    @Override
    public int saveClearData2CsmsHis(String tableName, Long boardListNo){
        return proviceRecvBoardDao.saveClearData2CsmsHis(tableName,boardListNo);
    }

    @Override
    public int saveClearData2CsmsErr(String tableName, Long boardListNo) {
        return proviceRecvBoardDao.saveClearData2CsmsErr(tableName,boardListNo);
    }

    @Override
    public void deleteClearData(String tableName, Long boardListNo) {
        proviceRecvBoardDao.deleteClearData(tableName,boardListNo);
    }

    @Override
    public int saveRecvBoard2CsmsHis(Long boardListNo,int updateFlag) {
        return proviceRecvBoardDao.saveRecvBoard2CsmsHis(boardListNo,updateFlag);
    }

    @Override
    public void deleteRecvBoardByListNo(Long boardListNo) {
        proviceRecvBoardDao.deleteRecvBoardByListNo(boardListNo);
    }


}
