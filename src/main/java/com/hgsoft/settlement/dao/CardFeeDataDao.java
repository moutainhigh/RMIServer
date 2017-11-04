package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.settlement.entity.CardFeeData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangzhongji on 17/10/19.
 */
@Repository
public class CardFeeDataDao extends BaseDao {

    public List<CardFeeData> listCardFeeData() {
        String sql = "select * from csms_cardfeedata ";
        return super.queryObjectList(sql, CardFeeData.class);
    }

    public int delete(Long id) {
        String sql = "delete from csms_cardfeedata where id=?";
        return super.update(sql, id);
    }
}
