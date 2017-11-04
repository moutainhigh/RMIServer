package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.settlement.entity.ProviceRecvBoard;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClearProviceRecvBoardDao extends ClearBaseDao {

    public List<Object[]> findAllObjs() {
        final List<Object[]> provinceRecvBoardsObjs = new ArrayList<Object[]>();

        final String sql = " select listno,tablecode,tablename,sendcode,updatetime,  updateflag,cnt " +
                " from tb_ProviceRecvBoard where tablecode in ('"+ ProviceRecvBoard.TABLECODE_CARDINSETTLEDETAIL+"', '"+ProviceRecvBoard.TABLECODE_CARDOUTSETTLEDETAIL+"') and updateflag=0 order by listno,tablecode";

        super.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Object[] objs = {resultSet.getObject(1), resultSet.getObject(2), resultSet.getObject(3), resultSet.getObject(4), resultSet.getObject(5),
                        resultSet.getObject(6), resultSet.getObject(7)
                };
                provinceRecvBoardsObjs.add(objs);
            }
        });
        return provinceRecvBoardsObjs;
    }

    public void batchUpdateFlagForTake(List<Object[]> objs) {
        String sql = "update tb_ProviceRecvBoard set updateflag=1 where listno=? and tablecode=? ";

        int[] argsType = {Types.BIGINT, Types.VARCHAR};

        super.batchUpdate(sql, objs, argsType);
    }

    public void batchDelete(List<Object[]> objs) {
        String sql = "delete from tb_ProviceRecvBoard where listno=? and tablecode=? ";

        int[] argsType = {Types.BIGINT, Types.VARCHAR};

        super.batchUpdate(sql, objs, argsType);
    }

}
