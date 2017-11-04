package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.daysettle.entity.DaySetWareRecord;
import com.hgsoft.daysettle.entity.RegisterDaySetWareRecord;
import com.hgsoft.daysettle.entity.SumDaySettleWare;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class RegisterDaySetWareRecordDao extends BaseDao{

	public String findDaySettleList(String type,Long operId,List<String> placeList){
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		if(operId!=null){
			sql.append( "select settleDay from CSMS_RegisterDaySetWare_Record where placeno in("); 
			for (int i = 0; i < placeList.size(); i++) {
				sql.append("'"+placeList.get(i)+"'");
				if(i!=placeList.size()-1){
					sql.append(" , ");
				}
			}
			sql.append(") order by settleDay desc FETCH FIRST 1 ROW ONLY ");
			
			list = queryList(sql.toString());
		}else{
			sql.append("select SettleDay from CSMS_RegisterDaySetWare_Record where reportplaceid is null order by SettleDay desc FETCH FIRST 1 ROW ONLY"); 
			list = queryList(sql.toString());
		}
		String daySettle=null;
		if (!list.isEmpty()) {
			daySettle=list.get(0).get("SettleDay").toString();
		}
		return daySettle;
	}
	
	@SuppressWarnings("rawtypes")
	public void save(RegisterDaySetWareRecord registerDaySetWareRecord) {
		
		Map map = FieldUtil.getPreFieldMap(RegisterDaySetWareRecord.class,registerDaySetWareRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_RegisterDaySetWare_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	@SuppressWarnings("rawtypes")
	public RegisterDaySetWareRecord find(RegisterDaySetWareRecord registerDaySetWareRecord) {
		RegisterDaySetWareRecord temp = null;
		if (registerDaySetWareRecord != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_RegisterDaySetWare_Record ");
			Map map = FieldUtil.getPreFieldMap(RegisterDaySetWareRecord.class,registerDaySetWareRecord);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			
			if (!list.isEmpty()) {
				temp = new RegisterDaySetWareRecord();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	
	
	public Pager findByPage(Pager pager,Date starTime ,Date endTime, DaySetWareRecord daySetWareRecord) {
		StringBuffer sql=new StringBuffer("select dr.ID,dr.SettleDay,dr.SettleTime,"
				+ "dr.ReportOperID,dr.ReportTime,dr.ReportPlaceID,operName,operNo,PlaceNo,PlaceName,"
				+ "ROWNUM as num "
				+ "from  CSMS_RegisterDaySetWare_Record dr "
				+ " where 1=1 and ReportPlaceID is not null ");		
		
		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			params.geDate("dr.SettleTime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("dr.SettleTime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(daySetWareRecord.getSettleDay())){
			params.eq("dr.SettleDay", daySetWareRecord.getSettleDay());
		}
		if(daySetWareRecord.getReportPlaceID()!=null){
			params.eq("dr.ReportPlaceID", daySetWareRecord.getReportPlaceID());
		}
		
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by dr.SettleDay desc ");
		return this.findByPages(sql.toString(), pager,Objects);
		
	}
	
	
	public boolean checkRecord(Long operPlaceId,String SettleDay){
		String sql = "select count(1) from CSMS_RegisterDaySetWare_Record r join "
				+ "CSMS_RegisterDaySetWare_Detail d on r.id = d.mainid where  r.ReportPlaceID = ? and r.SettleDay = ?";
		int count = count(sql,operPlaceId,SettleDay);
		if(count!=0){
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	public SumDaySettleWare checkDaySettleSum(String startTime,String endTime,Long placeId){
		SumDaySettleWare sumDaySettleWare = new SumDaySettleWare();
		try {
			//查询日结后资金修正
			String afterDaySetSql=" select sum(BalanceDiffNum) as BalanceDiffNum,sum(RecoverDiffNum) as RecoverDiffNum,"
					+ "productType,serviceType from CSMS_AfterDaySetWare  "
					+ " where to_char(reporttime,'YYYYMMDDHH24MISS')>=? and to_char(reporttime,'YYYYMMDDHH24MISS')<? and reportplaceid=? group by productType,serviceType ";
		
			List<Map<String, Object>> afterDaySetList = queryList(afterDaySetSql,startTime,endTime,placeId);
			
			if (!afterDaySetList.isEmpty()) {
				for (Map<String, Object> map : afterDaySetList) {
					
					if("1".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setPaidBalance(map.get("BalanceDiffNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setPaidRecover(map.get("RecoverDiffNum").toString());
						}
					}else if("2".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setAccBalance(map.get("BalanceDiffNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setAccRecover(map.get("RecoverDiffNum").toString());
						}
					}else if("3".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setTagBalance(map.get("BalanceDiffNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setTagRecover(map.get("RecoverDiffNum").toString());
						}
					}else if("4".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setBillBalance(map.get("BalanceDiffNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setBillRecover(map.get("RecoverDiffNum").toString());
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sumDaySettleWare;
	}
	
	
}
