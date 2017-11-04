package com.hgsoft.prepaidC.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.prepaidC.entity.ScAddFixResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangzhongji on 17/9/11.
 */
@Repository
public class ScAddFixResultDao extends BaseDao {

    public List<ScAddFixResult> findScAddFixResultByCheckResultAndDealStatus(Integer checkResult, Integer dealStatus) {
        String sql = "select * from TB_SCADDFIXRESULT where checkResult=? and dealStatus=?";
        return queryObjectList(sql, ScAddFixResult.class, new Object[] {checkResult, dealStatus});
    }

    public int updateDealed(ScAddFixResult scAddFixResult) {
        String sql = "update TB_SCADDFIXRESULT set dealStatus=?,dealtime=? where id=?";
        return update(sql, scAddFixResult.getDealStatus(), scAddFixResult.getDealtime(), scAddFixResult.getId());
    }
}
