package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.CardOutSettleDetailRecv;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CardOutSettleDetailRecvDao extends BaseDao {

    public void batchSave(List<Object[]> objs) {
        String sql = "INSERT INTO tb_cardoutsettledetail_recv " +
                " ("+ CardOutSettleDetailRecv.TABLE_FIELD_NAMES+") " +
                " values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?)  ";

        super.batchUpdate(sql, objs, CardOutSettleDetailRecv.TABLE_FIELD_TYPES);
    }

    public void deleteByBoardListNo(Long boardListNo) {
        String sql = "delete from tb_cardoutsettledetail_recv where boardlistno=?";
        super.delete(sql, boardListNo);
    }
}
