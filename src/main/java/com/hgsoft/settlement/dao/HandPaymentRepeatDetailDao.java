package com.hgsoft.settlement.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by yangzhongji on 17/9/28.
 */
@Repository
public class HandPaymentRepeatDetailDao extends BaseDao {

    public int insertByAcTradeDetail(AcTradeDetailInfo acTradeDetailInfo) {
        Map map = FieldUtil.getPreFieldMap(AcTradeDetailInfo.class, acTradeDetailInfo);
        StringBuffer sql = new StringBuffer(" insert into CSMS_HANDPAYMENTREPEAT_DETAIL ");
        sql.append(map.get("insertNameStr"));
        return saveOrUpdate(sql.toString(), (List) map.get("param"));
    }
}
