package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.CardStateInfo;
import com.hgsoft.clearInterface.entity.PrepaidCBalance;
import com.hgsoft.clearInterface.entity.UserStateInfo;
import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;
@Repository
public class CardObuDao extends EtcTollingBaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;

	/**
	 * 保存卡片状态信息
	 */
	public void saveCardStateInfo(CardStateInfo cardStateInfo){
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1015");
		cardStateInfo.setBoardListNo(new Long(boardListNo));
		cardStateInfo.setUpdateTime(new Date());
		cardStateInfo.setId(sequenceUtil.getSequenceLong("SEQ_CARDSTATEINFO_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_CARDSTATEINFO_SEND(");
		sql.append(FieldUtil.getFieldMap(CardStateInfo.class, cardStateInfo).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(CardStateInfo.class, cardStateInfo).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1015", "TB_CARDSTATEINFO_SEND", new Date(), 0, new Long(1));
	}

	public void saveUserStateInfo(UserStateInfo userStateInfo) {
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1024");
		userStateInfo.setBoardListNo(new Long(boardListNo));
		userStateInfo.setUpdateTime(new Date());
		userStateInfo.setId(sequenceUtil.getSequenceLong("SEQ_TBUSERSTATEINFOSEND_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_USERSTATEINFO_SEND(");
		sql.append(FieldUtil.getFieldMap(UserStateInfo.class, userStateInfo).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(UserStateInfo.class, userStateInfo).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1024", "TB_USERSTATEINFO_SEND", new Date(), 0, new Long(1));
	}

	public void saveCardBalance(PrepaidCBalance prepaidCBalance) {
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1020");
		prepaidCBalance.setBoardListNo(new Long(boardListNo));
		prepaidCBalance.setId(sequenceUtil.getSequenceLong("SEQ_STORECARDGETBALANCESEND_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_STORECARDGETBALANCE_SEND(");
		sql.append(FieldUtil.getFieldMap(PrepaidCBalance.class, prepaidCBalance).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(PrepaidCBalance.class, prepaidCBalance).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1020", "TB_STORECARDGETBALANCE_SEND", new Date(), 0, new Long(1));
	}
}
