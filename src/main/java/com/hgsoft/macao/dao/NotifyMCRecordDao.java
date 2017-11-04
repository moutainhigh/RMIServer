package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.NotifyMCRecord;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class NotifyMCRecordDao extends BaseDao{
	public void save(NotifyMCRecord notifyMCRecord){
		Map map = FieldUtil.getPreFieldMap(NotifyMCRecord.class,notifyMCRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_NOTIFYMC_RECORD");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	/**
	 * 找出所有通知澳门通失败的记录
	 * @param pager
	 * @return Pager
	 */
	public Pager findNotifyMCRecords(Pager pager,String beginTime,String endTime,NotifyMCRecord notifyMCRecord){
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(NotifyMCRecord.class, new NotifyMCRecord()).get("nameStr")+" from CSMS_NOTIFYMC_RECORD WHERE reqresult<>1  ");
		
		SqlParamer sqlp = new SqlParamer();
		if(StringUtil.isNotBlank(beginTime)){
			sqlp.geDate("reqTime", beginTime + " 00:00:00");
		}
		if(StringUtil.isNotBlank(endTime)){
			sqlp.leDate("reqTime", endTime + " 23:59:59");
		}
		if(notifyMCRecord!=null&&StringUtil.isNotBlank(notifyMCRecord.getInterfaceFlag())){
			sqlp.eq("interfaceFlag", notifyMCRecord.getInterfaceFlag());
		}
		sql = sql.append(sqlp.getParam());
		sql = sql.append(" order by id desc ");
		return this.findByPages(sql.toString(), pager, sqlp.getList().toArray());
	}
	
	public NotifyMCRecord findById(Long id){
		String sql = "select "+FieldUtil.getFieldMap(NotifyMCRecord.class, new NotifyMCRecord()).get("nameStr")+" from CSMS_NOTIFYMC_RECORD WHERE id=? ";
		List<Map<String, Object>> list = this.queryList(sql, id);
		NotifyMCRecord notifyMCRecord = null;
		if(!list.isEmpty()&&list.size()>0){
			notifyMCRecord = new NotifyMCRecord();
			convert2Bean(list.get(0), notifyMCRecord);

		}
		return notifyMCRecord;
	}
	
	public void deleteById(Long id){
		String sql = "delete from CSMS_NOTIFYMC_RECORD where id=?";
		this.delete(sql, id);
	}
}
