package com.hgsoft.macao.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.MacaoReqRecord;
import com.hgsoft.macao.entity.NotifyMCRecord;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Repository
public class MacaoReqRecordDao extends BaseDao{
	public void saveMacaoReqRecord(MacaoReqRecord macaoReqRecord){
		Map map = FieldUtil.getPreFieldMap(MacaoReqRecord.class,macaoReqRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_MACAO_REQ_RECORD");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	/**
	 * 查询通讯记录
	 * @param pager
	 * @return Pager
	 */
	public Pager findMacaoReqRecords(Pager pager,String beginTime,String endTime,String interfaceFlag){
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(MacaoReqRecord.class, new MacaoReqRecord()).get("nameStr")+" from CSMS_MACAO_REQ_RECORD WHERE 1=1  ");
		
		SqlParamer sqlp = new SqlParamer();
		if(StringUtil.isNotBlank(beginTime)){
			sqlp.geDate("resTime", beginTime + " 00:00:00");
		}
		if(StringUtil.isNotBlank(endTime)){
			sqlp.leDate("resTime", endTime + " 23:59:59");
		}
		if(StringUtil.isNotBlank(interfaceFlag)){
			sqlp.eq("interfaceFlag", interfaceFlag);
		}
		sql = sql.append(sqlp.getParam());
		sql = sql.append(" order by id desc ");
		return this.findByPages(sql.toString(), pager, sqlp.getList().toArray());
	}
}
