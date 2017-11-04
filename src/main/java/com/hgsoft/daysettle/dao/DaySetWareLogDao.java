package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.DaySetWareLog;

@Component
public class DaySetWareLogDao extends BaseDao{

	public void save(DaySetWareLog daySetWareLog) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_DaySetWare_Log(");
		sql.append(FieldUtil.getFieldMap(DaySetWareLog.class,daySetWareLog).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(DaySetWareLog.class,daySetWareLog).get("valueStr")+")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(DaySetWareLog.class,daySetWareLog);
		StringBuffer sql=new StringBuffer("insert into CSMS_DaySetWare_Log");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public DaySetWareLog find(DaySetWareLog daySetWareLog) {
		DaySetWareLog temp = null;
		if (daySetWareLog != null) {
			/*StringBuffer sql = new StringBuffer("select * from CSMS_DaySetWare_Log where 1=1 ");
			sql.append(FieldUtil.getFieldMap(DaySetWareLog.class,daySetWareLog).get("nameAndValueNotNull"));
			sql.append(" order by settleDay desc");
			System.out.println(sql);
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			
			StringBuffer sql = new StringBuffer("select * from CSMS_DaySetWare_Log ");
			Map map = FieldUtil.getPreFieldMap(DaySetWareLog.class,daySetWareLog);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by settleDay desc");
			System.out.println(sql.toString());
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());

			if (!list.isEmpty()) {
				temp = new DaySetWareLog();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public void update(DaySetWareLog daySetWareLog){
		Date date = daySetWareLog.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String endTime = sdf.format(date);
		
		String sql ="update CSMS_DaySetWare_Log set State =?,EndTime = to_date(?,'YYYYMMDDHH24MISS') where ReportPlaceID = ? and SettleDay=?";
		saveOrUpdate(sql,daySetWareLog.getState(),endTime,daySetWareLog.getReportPlaceID(),daySetWareLog.getSettleDay());
	}
}
