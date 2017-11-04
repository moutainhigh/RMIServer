package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.RecoverDebtSend;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Repository
public class CashMoneyDao extends BaseDao {

	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;
	
	public void saveCashMoney(RecoverDebtSend recoverDebtSend) {
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1026");
		recoverDebtSend.setBoardListNo(boardListNo);
		recoverDebtSend.setUpdateTime(new Date());
		StringBuffer sql = new StringBuffer("insert into TB_RECOVERDEBT_SEND(");
		sql.append(FieldUtil.getFieldMap(RecoverDebtSend.class, recoverDebtSend).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(RecoverDebtSend.class, recoverDebtSend).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1026", "TB_RECOVERDEBT_SEND", new Date(), 0, new Long(1));
	}
}
