package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.ProviceRecvBoard;
import com.hgsoft.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProviceRecvBoardDao extends BaseDao {

    @Autowired
    private UrlUtils urlUtils;

    public void batchSave(List<Object[]> objs) {
        String sql = "INSERT INTO tb_ProviceRecvBoard " +
                " (ID,listno,tablecode,tablename,sendcode, updatetime,updateflag,cnt,operatetime) " +
                " values(?,?,?,?,?, ?,?,?,sysdate)  ";
        int[] argsType = {Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.TIMESTAMP, Types.INTEGER, Types.INTEGER
        };
        super.batchUpdate(sql, objs, argsType);
    }

    public void batchUpdateFlagForTake(List<Object[]> objs) {
        String sql = "update tb_ProviceRecvBoard set updateflag=1 where listno=? and tablecode=? ";

        int[] argsType = {Types.BIGINT, Types.VARCHAR};

        super.batchUpdate(sql, objs, argsType);
    }

    public void updateFlag(Long listNo, String tableCode, int newFlag, int oldFlag) {
        String sql = "update tb_ProviceRecvBoard set updateflag=?,operatetime=? where listno=? and tablecode=? and updateflag=?";
        super.update(sql, newFlag, new Date(), listNo, tableCode, oldFlag);
    }

    public List<ProviceRecvBoard> queryByFlag(int flag) {
        String sql = "select * from tb_ProviceRecvBoard where updateflag=? order by listno";
        return super.queryObjectList(sql, ProviceRecvBoard.class, flag);
    }

    public List<ProviceRecvBoard> queryByFlagTableCode(int flag, String tableCode) {
        String sql = "select * from tb_ProviceRecvBoard where updateflag=? and tablecode=? order by listno";
        return super.queryObjectList(sql, ProviceRecvBoard.class, flag, tableCode);
    }

    public List<Object[]> queryObjsByFlag(int flag) {
        String sql = "select listno, tablecode from tb_ProviceRecvBoard where updateflag=? order by listno";
        Object[] args = {flag};

        final List<Object[]> proviceBoardsObjs = new ArrayList<Object[]>();
        super.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Object[] objs = {resultSet.getObject(1), resultSet.getObject(2)};
                proviceBoardsObjs.add(objs);
            }
        }, args);
        return proviceBoardsObjs;
    }

    public void updateInFlagEnd() {
        String sqlIn = "UPDATE tb_provicerecvboard a SET updateflag=? WHERE tablecode=? AND NOT EXISTS (SELECT 1 FROM tb_CARDINSETTLEDETAIL_RECV b WHERE b.boardlistno=a.listno)";
        super.update(sqlIn, ProviceRecvBoard.UPDAETFLAG_DONE, ProviceRecvBoard.TABLECODE_CARDINSETTLEDETAIL);
    }

    public void updateOutFlagEnd() {
        String sqlOut = "UPDATE tb_provicerecvboard a SET updateflag=? WHERE tablecode=? AND NOT EXISTS (SELECT 1 FROM TB_CARDOUTSETTLEDETAIL_RECV b WHERE b.boardlistno=a.listno)";
        super.update(sqlOut, ProviceRecvBoard.UPDAETFLAG_DONE, ProviceRecvBoard.TABLECODE_CARDOUTSETTLEDETAIL);
    }

    public int updateGenDetailFlag(int genDetailFlag, String tableCode, int updateFlag) {
        String sql = "UPDATE tb_provicerecvboard SET gendetailflag=gendetailflag+? where gendetailflag<3 and gendetailflag!=? and tablecode=? and updateflag=?";
        return  super.update(sql, genDetailFlag, genDetailFlag, tableCode, updateFlag);
    }

    public int updateFlagDone() {
        String sql = "UPDATE tb_provicerecvboard SET updateflag=? where gendetailflag=3";
        return  super.update(sql, ProviceRecvBoard.UPDAETFLAG_DONE);
    }


    /***
     * 获取待处理的清算数据公告序列号
     * @return
     */
    public List<ProviceRecvBoard> queryClearData() {
        String sql = "SELECT recv.* FROM tb_ProviceRecvBoard recv INNER JOIN CSMS_TABLECODE4CLEARDATA T ON RECV.tablecode = T .TABLECODE AND T .flag = 1 WHERE recv.UPDATEFLAG = 0 ORDER BY listno";
        return super.queryObjectList(sql, ProviceRecvBoard.class);
    }


    /***
     * 根据公告序列号获取指定数据表的记录数
     * @param boardListNo
     * @param tableName
     * @return
     */
    public int queryCountClearData(String tableName,Long boardListNo){
        String sql = "select count(*) from tableName where BOARDLISTNO = ?";
        String querySql = sql.replace("tableName",tableName);
        return super.queryCount(querySql,boardListNo );
    }




    /***
     * 保存清算数据至客服数据表
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int saveClearData2Csms(String tableName, Long boardListNo) {
        String sql = "insert into tableName SELECT * FROM tableSource where BOARDLISTNO = ?";
        String querySql = sql.replace("tableName",tableName).replace("TB_","CSMS_").replace("_RECV","").replace("tableSource",tableName);
        System.out.println(querySql);
        return super.update(querySql,boardListNo );
    }


    /***
     * 保存清算数据至历史表
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int saveClearData2CsmsHis(String tableName, Long boardListNo) {
        String sql = "insert into tableName SELECT * FROM tableSource where BOARDLISTNO = ?";
        String querySql = sql.replace("tableName",tableName).replace("TB_","CSMS_").replace("_RECV","_HIS").replace("tableSource",tableName);
        System.out.println(querySql);
        return super.update(querySql,boardListNo);
    }

    /***
     * 根据公告序列号保存指定表的清算数据至客服异常表
     * @param tableName
     * @param boardListNo
     * @return
     */
    public int saveClearData2CsmsErr(String tableName, Long boardListNo) {
        String sql = "insert into tableName SELECT * FROM tableSource where BOARDLISTNO = ?";
        String querySql = sql.replace("tableName",tableName).replace("TB_","CSMS_").replace("_RECV","_ERR").replace("tableSource",tableName);
        System.out.println(querySql);
        return super.update(querySql,boardListNo);
    }

    /***
     * 根据公告序列号删除指定表的清算数据
     * @param tableName
     * @param boardListNo
     */
    public void deleteClearData(String tableName, Long boardListNo) {
        String sql = "delete from tableName where BOARDLISTNO = ?";
        String querySql = sql.replace("tableName",tableName);
        super.delete(querySql,boardListNo);
    }




    /***
     * 备份客服接收公告表记录至客服接收公告历史表
     * @param boardListNo
     */
    public int saveRecvBoard2CsmsHis(Long boardListNo,int updateFlag) {
        String sql = "insert into CSMS_PROVICERECVBOARD_HIS (listno,tablecode,tablename,sendcode, updatetime,updateflag,cnt,operatetime) SELECT listno,tablecode,tablename,sendcode, updatetime,?,cnt,sysdate  FROM TB_PROVICERECVBOARD where listno = ?";
        return super.update(sql,updateFlag,boardListNo);
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ProviceRecvBoardDao proviceRecvBoardDao = (ProviceRecvBoardDao)context.getBean("proviceRecvBoardDao");
        System.out.println("11");

//        String tableName = "TB_CLEARRESULTSTATE_RECV";
//        String sql = "insert into TB_CLEARRESULTSTATE_RECV SELECT * FROM ? where BOARDLISTNO = 10086";
//        String querySql = sql.replace("tableName",tableName).replace("TB_","CSMS_").replace("_RECV","");
//        System.out.println(querySql);
    }

    public void deleteRecvBoardByListNo(Long boardListNo) {
        String sql = "delete  FROM TB_PROVICERECVBOARD where listno = ?";
        super.delete(sql,boardListNo);
    }
}
