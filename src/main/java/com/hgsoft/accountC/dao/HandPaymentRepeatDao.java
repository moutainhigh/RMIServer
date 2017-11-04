package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.accountC.entity.HandPaymentRepeat;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.settlement.entity.AccBankListReturn;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangzhongji on 17/9/28.
 */
@Repository
public class HandPaymentRepeatDao extends BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(HandPaymentRepeatDao.class);

    public int insert(HandPayment handPayment, AccBankListReturn accBankListReturn) {
        HandPaymentRepeat handPaymentRepeat = new HandPaymentRepeat();
        try {
            BeanUtils.copyProperties(handPaymentRepeat, handPayment);
        } catch (Exception e) {
            logger.error("对象转换失败", e);
            throw new RuntimeException("保存手工缴纳重复记录失败");
        }
        handPaymentRepeat.setGenTime(new Date());
        handPaymentRepeat.setAcbAccount(accBankListReturn.getAcbAccount());
        handPaymentRepeat.setCmdGenTime(accBankListReturn.getGentime());
        handPaymentRepeat.setHdlDatetime(accBankListReturn.getHdlDatetime());
        handPaymentRepeat.setIncome(accBankListReturn.getIncome());
        handPaymentRepeat.setUpdateTime(new Date());

        Map map = FieldUtil.getPreFieldMap(HandPaymentRepeat.class, handPaymentRepeat);
        StringBuffer sql = new StringBuffer(" insert into CSMS_HANDPAYMENTREPEAT ");
        sql.append(map.get("insertNameStr"));
        return saveOrUpdate(sql.toString(), (List) map.get("param"));
    }
}
