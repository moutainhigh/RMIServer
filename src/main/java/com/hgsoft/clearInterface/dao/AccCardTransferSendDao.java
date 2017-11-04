package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.AccCardTransferSend;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
@Component
public class AccCardTransferSendDao extends ClearBaseDao {
	
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;
	
	public void save(AccCardTransferSend accCardTransferSend) {
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1014");
		accCardTransferSend.setBoardListNo(new Long(boardListNo));
		StringBuffer sql=new StringBuffer("insert into TB_ACCCARDTRANSFER_SEND(");
		sql.append(FieldUtil.getFieldMap(AccCardTransferSend.class,accCardTransferSend).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccCardTransferSend.class,accCardTransferSend).get("valueStr")+")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1014", "TB_ACCCARDTRANSFER_SEND", new Date(), 0, new Long(1));
	}
}
