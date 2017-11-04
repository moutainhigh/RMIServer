package com.hgsoft.clearInterface.dao;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.StoreCardRecharge;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class StoreCardRechargeDao extends ClearBaseDao {
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;
	
	public void savePrepaidCCharge(StoreCardRecharge storeCardRecharge){
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1012");
		storeCardRecharge.setBoardListNo(new Long(boardListNo));
		StringBuffer sql = new StringBuffer("insert into tb_storecardrecharge_send(");
		Map<String, String> fieldMap = FieldUtil.getFieldMap(StoreCardRecharge.class, storeCardRecharge);
		sql.append(fieldMap.get("nameStr")
				+ ") values(");
		sql.append(fieldMap.get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1012", "TB_STORECARDRECHARGE_SEND", storeCardRecharge.getUpdatetime(), 0, new Long(1));
	}
}
