package com.hgsoft.clearInterface.dao;

import com.hgsoft.clearInterface.entity.CloseAccountRestartSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 孙晓伟
 * file : CloseAccountRestartSendDao.java
 * date : 2017/8/11
 * time : 19:36
 */
@Repository
public class CloseAccountRestartSendDao extends ClearBaseDao {
    @Resource
    private ProviceSendBoardDao proviceSendBoardDao;

    public void save(CloseAccountRestartSend closeAccountRestartSend) {
        StringBuffer sql=new StringBuffer("insert into TB_CLOSEDACCOUNTRESTART_SEND(");
        sql.append(FieldUtil.getFieldMap(CloseAccountRestartSend.class,closeAccountRestartSend).get("nameStr")+") values(");
        sql.append(FieldUtil.getFieldMap(CloseAccountRestartSend.class,closeAccountRestartSend).get("valueStr")+")");
        save(sql.toString());
        proviceSendBoardDao.saveProviceSendBoard(closeAccountRestartSend.getBoardListNo(), "1028", "TB_CLOSEDACCOUNTRESTART_SEND", new Date(), 0, new Long(1));
    }
}
