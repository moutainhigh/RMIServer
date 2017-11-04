package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.AccCardUserInfoSend;
import com.hgsoft.clearInterface.entity.AccountCSecondIssued;
import com.hgsoft.clearInterface.entity.PrepaidCSecondIssued;
import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;

@Component
public class CardSecondIssuedDao extends EtcTollingBaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;
	
	/**
	 * 保存储值卡发行信息以及删除至铭鸿清算系统
	 * @param cardSecondIssued
	 */
	public void savePrepaidCCardIssued(PrepaidCSecondIssued cardSecondIssued){
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1010");
		cardSecondIssued.setBoardListNo(new Long(boardListNo));
		cardSecondIssued.setId(sequenceUtil.getSequenceLong("SEQ_TBSTORECARDSECONDSEND_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_STORECARDSECONDISSUED_SEND(");
		sql.append(FieldUtil.getFieldMap(PrepaidCSecondIssued.class, cardSecondIssued).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(PrepaidCSecondIssued.class, cardSecondIssued).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1010", "TB_STORECARDSECONDISSUED_SEND", new Date(), 0, new Long(1));
	}
	
	/**
	 * 保存记帐卡发行信息以及删除至清算系统
	 * @param cardSecondIssued
	 */
	public void saveAccountCCardIssued(AccountCSecondIssued cardSecondIssued){
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1011");
		cardSecondIssued.setBoardListNo(new Long(boardListNo));
		cardSecondIssued.setId(sequenceUtil.getSequenceLong("SEQ_TBTOLLYCARDSECONDSEND_NO"));
		StringBuffer sql = new StringBuffer("insert into TB_TOLLYCARDSECONDISSUED_SEND(");
		sql.append(FieldUtil.getFieldMap(AccountCSecondIssued.class, cardSecondIssued).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(AccountCSecondIssued.class, cardSecondIssued).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(boardListNo, "1011", "TB_TOLLYCARDSECONDISSUED_SEND", new Date(), 0, new Long(1));
	}
	
	public void saveAccountCUser(AccCardUserInfoSend accCardUserInfoSend){

		StringBuffer sql = new StringBuffer("insert into TB_ACCCARDUSERINFO_SEND(");
		sql.append(FieldUtil.getFieldMap(AccCardUserInfoSend.class, accCardUserInfoSend).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(AccCardUserInfoSend.class, accCardUserInfoSend).get("valueStr")
				+ ")");
		save(sql.toString());
		proviceSendBoardDao.saveProviceSendBoard(accCardUserInfoSend.getBoardListNo(), "1013", "TB_ACCCARDUSERINFO_SEND", new Date(), 0, new Long(1));
	}
}
