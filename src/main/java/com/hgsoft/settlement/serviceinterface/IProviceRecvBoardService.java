package com.hgsoft.settlement.serviceinterface;

import com.hgsoft.settlement.entity.ProviceRecvBoard;

import java.util.List;

public interface IProviceRecvBoardService {

    List<Object[]> findAllObjsFromClear();

    void batchUpdateFlagToClear(List<Object[]> objs);

    void batchSave(List<Object[]> objs);

    void batchUpdateFlagForTake(List<Object[]> objs);

    void updateFlag(Long listNo, String tableCode, int newFlag, int oldFlag);

    List<ProviceRecvBoard> queryByFlag(int flag);

    List<ProviceRecvBoard> queryByFlagTableCode(int flag, String tableCode);

    List<Object[]> queryObjsByFlag(int flag);

    void updateInFlagEnd();

    void updateOutFlagEnd();

    int updateInScGenDetailFlag();

    int updateInAcGenDetailFlag();

    int updateOutScGenDetailFlag();

    int updateOutAcGenDetailFlag();

    int updateFlagDone();

    /**
     * 查询待处理的清算数据公告序列号
     * @return
     */
    List<ProviceRecvBoard> queryClearData();

    /***
     * 根据公告序列号查询指定表名的数据条数
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int queryCountClearData(String tableName,Long boardListNo);

    /***
     * 根据公告序列号保存指定表的清算数据至客服表
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int saveClearData2Csms(String tableName,Long boardListNo);

    /***
     * 根据公告序列号保存指定表的清算数据至客服历史表
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int saveClearData2CsmsHis(String tableName,Long boardListNo);

    /***
     * 根据公告序列号保存指定表的清算数据至客服异常表
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int saveClearData2CsmsErr(String tableName,Long boardListNo);


    /***
     * 根据公告序列号删除指定表的清算数据
     * @param tableName
     * @param boardListNo
     */
    public void deleteClearData(String tableName,Long boardListNo);


    /***
     * 备份客服接收公告表记录至客服接收公告历史表
     * @param boardListNo
     */
    public int  saveRecvBoard2CsmsHis(Long boardListNo,int updateFlag);


    /***
     * 根据公告序列号删除清算接收公告表记录
     * @param boardListNo
     */
    public void deleteRecvBoardByListNo(Long boardListNo);






}
