package com.hgsoft.clearInterface.dao;

import com.hgsoft.clearInterface.entity.ManualBlackListSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 孙晓伟
 * file : ManualBlackListSendDao.java
 * date : 2017/8/11
 * time : 15:58
 */
@Repository
public class ManualBlackListSendDao  extends ClearBaseDao {

    @Resource
    private ProviceSendBoardDao proviceSendBoardDao;

    public void save(ManualBlackListSend manualBlackListSend) {
        StringBuffer sql=new StringBuffer("insert into TB_MANUALBLACKLIST_SEND(");
        sql.append(FieldUtil.getFieldMap(ManualBlackListSend.class,manualBlackListSend).get("nameStr")+") values(");
        sql.append(FieldUtil.getFieldMap(ManualBlackListSend.class,manualBlackListSend).get("valueStr")+")");
        save(sql.toString());
        proviceSendBoardDao.saveProviceSendBoard(manualBlackListSend.getBoardListNo(), "1027", "TB_MANUALBLACKLIST_SEND", new Date(), 0, new Long(1));
    }
}
