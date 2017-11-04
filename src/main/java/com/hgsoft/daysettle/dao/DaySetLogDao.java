package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.DaySetLog;

@Component
public class DaySetLogDao extends BaseDao{

	public void save(DaySetLog daySetLog) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_DaySet_Log(");
		sql.append(FieldUtil.getFieldMap(DaySetLog.class,daySetLog).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(DaySetLog.class,daySetLog).get("valueStr")+")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(DaySetLog.class,daySetLog);
		StringBuffer sql=new StringBuffer("insert into CSMS_DaySet_Log");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public DaySetLog find(DaySetLog daySetLog) {
		DaySetLog temp = null;
		if (daySetLog != null) {
		/*	StringBuffer sql = new StringBuffer("select id,settleday,starttime,endtime,state,operid,macaddress,opertime,operplaceid,memo from CSMS_DaySet_Log where 1=1 ");
			sql.append(FieldUtil.getFieldMap(DaySetLog.class,daySetLog).get("nameAndValueNotNull"));
			sql.append(" order by settleDay desc");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			
			StringBuffer sql = new StringBuffer("select id,settleday,starttime,endtime,state,operid,macaddress,opertime,operplaceid,memo from CSMS_DaySet_Log ");
			Map map = FieldUtil.getPreFieldMap(DaySetLog.class,daySetLog);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by settleDay desc ");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			
			if (!list.isEmpty()) {
				temp = new DaySetLog();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	
	public DaySetLog find(List<String> placeList,DaySetLog daySetLog) {
		DaySetLog temp = null;
		if (daySetLog != null) {
		/*	StringBuffer sql = new StringBuffer("select id,settleday,starttime,endtime,state,operid,macaddress,opertime,operplaceid,memo from CSMS_DaySet_Log where 1=1 ");
			sql.append(FieldUtil.getFieldMap(DaySetLog.class,daySetLog).get("nameAndValueNotNull"));
			sql.append(" order by settleDay desc");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			
			StringBuffer sql = new StringBuffer("select id,settleday,starttime,endtime,state,operid,macaddress,opertime,operplaceid,memo from CSMS_DaySet_Log ");
			Map map = FieldUtil.getPreFieldMap(DaySetLog.class,daySetLog);
			sql.append(map.get("selectNameStrNotNull"));
			
			if(placeList.size()!=0){
				sql.append(" and placeno in( ");
				for (int i = 0; i < placeList.size(); i++) {
					sql.append("'"+placeList.get(i)+"'");
					if(i!=placeList.size()-1){
						sql.append(" , ");
					}
				}
				sql.append(" ) ");
			}
			
			
			sql.append(" order by settleDay desc ");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			
			if (!list.isEmpty()) {
				temp = new DaySetLog();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	
	
	
	
	public void update(DaySetLog daySetLog){
		String sql = null;
		if(daySetLog.getEndTime()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String endTime = sdf.format(daySetLog.getEndTime());
			sql ="update CSMS_DaySet_Log set State =?,EndTime = to_date(?,'YYYYMMDDHH24MISS') where operPlaceId = ? and SettleDay=?";
			saveOrUpdate(sql,daySetLog.getState(),endTime,daySetLog.getOperPlaceId(),daySetLog.getSettleDay());
		}else{
			sql ="update CSMS_DaySet_Log set State =?,EndTime = sysdate where operPlaceId = ? and SettleDay=?";
			saveOrUpdate(sql,daySetLog.getState(),daySetLog.getOperPlaceId(),daySetLog.getSettleDay());
		}
	}
	
	public DaySetLog findDaySetOnlyOne(Long placeId,List<String> longList){
		DaySetLog temp = null;
		StringBuffer sql =new StringBuffer(" select endtime from CSMS_DaySet_Log" +
				"  where endtime is not null and placeno in( ");
		for (int i = 0; i < longList.size(); i++) {
			/*if(i!=longList.size()){
				sql.append(" or ");
			}*/
			sql.append("'"+longList.get(i)+"'");
			if(i!=longList.size()-1){
				sql.append(" , ");
			}
		}
		sql.append(") order by settleday desc  FETCH FIRST 1 ROW ONLY ");
		List<Map<String, Object>> list = queryList(sql.toString());
		if (!list.isEmpty()) {
			temp = new DaySetLog();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
}
