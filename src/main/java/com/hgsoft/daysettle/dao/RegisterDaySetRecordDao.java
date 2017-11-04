package com.hgsoft.daysettle.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.RegisterDaySetRecord;

@Repository
public class RegisterDaySetRecordDao extends BaseDao{
	
	public String findDaySettleList(Long operId,List<String> placeList){
		List<Map<String, Object>> list = null;
		StringBuffer sql = new StringBuffer();
		if(operId!=null){
			sql.append( "select settleDay from CSMS_RegisterDaySet_Record where placeno in("); 
			for (int i = 0; i < placeList.size(); i++) {
				sql.append("'"+placeList.get(i)+"'");
				if(i!=placeList.size()-1){
					sql.append(" , ");
				}
			}
			sql.append(") order by settleDay desc FETCH FIRST 1 ROW ONLY ");
		}else{
			sql.append( "select settleDay from CSMS_RegisterDaySet_Record where operPlaceId is null order by settleDay desc FETCH FIRST 1 ROW ONLY"); 
		}
		list = queryList(sql.toString());
		String daySettle=null;
		if (!list.isEmpty()) {
			daySettle=list.get(0).get("settleDay").toString();
		}
		return daySettle;
	}
	
	public void save(RegisterDaySetRecord registerDaySetRecord) {
		Map map = FieldUtil.getPreFieldMap(RegisterDaySetRecord.class,registerDaySetRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_RegisterDaySet_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

}
