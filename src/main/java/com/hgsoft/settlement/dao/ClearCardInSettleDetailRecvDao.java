package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.settlement.entity.CardInSettleDetailRecv;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClearCardInSettleDetailRecvDao extends ClearBaseDao {


    public List<Object[]> findAllObjsByBoardListNo(Long boardListNo) {
        final List<Object[]> cardInSettleDetailObjs = new ArrayList<Object[]>();

        final String sql = "select " + CardInSettleDetailRecv.TABLE_FIELD_NAMES +
                " from tb_cardinsettledetail_recv WHERE boardlistno=?";

        super.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Object[] objs = {resultSet.getObject(1), resultSet.getObject(2),resultSet.getObject(3), resultSet.getObject(4), resultSet.getObject(5),
                        resultSet.getObject(6), resultSet.getObject(7), resultSet.getObject(8), resultSet.getObject(9), resultSet.getObject(10),
                        resultSet.getObject(11), resultSet.getObject(12),resultSet.getObject(13), resultSet.getObject(14), resultSet.getObject(15),
                        resultSet.getObject(16), resultSet.getObject(17), resultSet.getObject(18), resultSet.getObject(19), resultSet.getObject(20),
                        resultSet.getObject(21), resultSet.getObject(22),resultSet.getObject(23), resultSet.getObject(24), resultSet.getObject(25),
                        resultSet.getObject(26), resultSet.getObject(27), resultSet.getObject(28), resultSet.getObject(29), resultSet.getObject(30),
                        resultSet.getObject(31), resultSet.getObject(32),resultSet.getObject(33), resultSet.getObject(34), resultSet.getObject(35),
                        resultSet.getObject(36)
                };
                cardInSettleDetailObjs.add(objs);
            }
        }, new Object[]{boardListNo});

        return cardInSettleDetailObjs;
    }

}
