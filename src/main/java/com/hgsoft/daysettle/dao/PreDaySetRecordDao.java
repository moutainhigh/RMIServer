package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.PreDaySetRecord;

@Repository
public class PreDaySetRecordDao extends BaseDao{

	public String findDaySettleList(String type,String operId){
		String sql = "select PreSettleDay from CSMS_Pre_DaySet_Record where DaySettleFlag = ? and operId = ? order by PreSettleDay desc FETCH FIRST 1 ROW ONLY"; 
		List<Map<String, Object>> list = queryList(sql,type,operId);
		String daySettle=null;
		if (!list.isEmpty()) {
			daySettle=list.get(0).get("PreSettleDay").toString();
		}
		return daySettle;
	}
	
	public void save(PreDaySetRecord preDaySetRecord) {
		StringBuffer sql=new StringBuffer("insert into CSMS_Pre_DaySet_Record(");
		sql.append(FieldUtil.getFieldMap(PreDaySetRecord.class,preDaySetRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(PreDaySetRecord.class,preDaySetRecord).get("valueStr")+")");
		save(sql.toString());
	}
	
	public PreDaySetRecord find(PreDaySetRecord preDaySetRecord) {
		PreDaySetRecord temp = null;
		if (preDaySetRecord != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_Pre_DaySet_Record where 1=1 ");
			sql.append(FieldUtil.getFieldMap(PreDaySetRecord.class,preDaySetRecord).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()) {
				temp = new PreDaySetRecord();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
}
