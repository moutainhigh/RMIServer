package com.hgsoft.clearInterface.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.ProviceSendBoard;
import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class ProviceSendBoardDao extends EtcTollingBaseDao {
	@Resource
	SequenceUtil sequenceUtil;

	public void saveProviceSendBoard(Long listNo,String tableCode,String tableName,Date updateTime,
			Integer updateFlag,Long cnt){
		ProviceSendBoard proviceSendBoard = new ProviceSendBoard(listNo,tableCode,tableName,updateTime, updateFlag, cnt);
		StringBuffer sql = new StringBuffer("insert into TB_PROVINCESEND2ETCCENTER(");
		sql.append(FieldUtil.getFieldMap(ProviceSendBoard.class, proviceSendBoard).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(ProviceSendBoard.class, proviceSendBoard).get("valueStr")
				+ ")");
		save(sql.toString());
	}
	/**
	 * 公告表保存通行明细
	 * @param i
	 * @param boardlistno
	 * @return void
	 */
	public void saveForResult(int i, Long boardlistnoDetail){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String sql = "insert into tb_provicesendboard(listno,tablecode,tablename,updatetime,updateflag,cnt) "
				+ "values("+boardlistnoDetail+",'0501','CSMS_MACAO_PassageDetail',to_date("+format.format(new Date())+",'yyyyMMdd'),'0',"+i+")";
		super.save(sql);
	}
	
	/**
	 * 公告表保存汇总数据
	 * @param i
	 * @param boardlistnoResult
	 * @return void
	 */
	public void saveForDetail(int i, Long boardlistnoResult){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String sql = "insert into tb_provicesendboard(listno,tablecode,tablename,updatetime,updateflag,cnt) "
				+ "values("+boardlistnoResult+",'0502','CSMS_MACAO_PassageDetail',to_date("+format.format(new Date())+",'yyyyMMdd'),'0',"+i+")";
		super.save(sql);
	}

}
