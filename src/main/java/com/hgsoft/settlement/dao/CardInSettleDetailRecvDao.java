package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.CardInSettleDetailRecv;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardInSettleDetailRecvDao extends BaseDao {


    public void batchSave(List<Object[]> objs) {
        String sql = "INSERT INTO tb_cardinsettledetail_recv " +
                " ("+CardInSettleDetailRecv.TABLE_FIELD_NAMES+") " +
                " values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)  ";

        super.batchUpdate(sql, objs, CardInSettleDetailRecv.TABLE_FIELD_TYPES);
    }



    public void deleteByBoardListNo(Long boardListNo) {
        String sql = "delete from tb_cardinsettledetail_recv where boardlistno=?";
        super.delete(sql, boardListNo);
    }
}
