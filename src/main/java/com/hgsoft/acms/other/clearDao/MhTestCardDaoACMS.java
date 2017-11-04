package com.hgsoft.acms.other.clearDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.dao.ProviceSendBoardDao;
import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.entity.TestCard;
@Repository
public class MhTestCardDaoACMS extends EtcTollingBaseDao{
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;
	
	public void save(TestCard testCard) {
		String boardListNo = String.valueOf((new Date()).getTime())+"1009";
		
		StringBuffer sql = new StringBuffer("insert into TB_TESTCARD_SEND");
		sql.append(" (id,projectNo,carType,setTime,updateTime,remark,boardListNo) values" + "(?,?,?,?,?,?,?)");
		Object[] arg = new Object[8];
		arg[0] = testCard.getId();
		arg[1] = testCard.getCardCode();
		arg[2] = testCard.getCardType();
		arg[3] = testCard.getDelFlag();
		arg[4] = new java.sql.Timestamp(testCard.getSetTime().getTime());
		arg[5] = new java.sql.Timestamp(testCard.getUpdateTime().getTime());
		arg[6] = testCard.getRemark();
		arg[7] = Long.valueOf(boardListNo);
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(arg);
		super.saveByBatchUpdate(sql.toString(), batchArgs);
		//铭鸿保存增加公告号
		proviceSendBoardDao.saveProviceSendBoard(Long.valueOf(boardListNo), "1009", "TB_TESTCARD_SEND", new Date(), 0, new Long(1));
	}
	
	public void update(TestCard oldTestCard) {
		Map map = FieldUtil.getPreFieldMap(TestCard.class, oldTestCard);
		StringBuffer sql = new StringBuffer("update TB_TESTCARD_SEND set ");
		sql.append(map.get("updateNameStr")+" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), oldTestCard.getId());
	}
	
}
