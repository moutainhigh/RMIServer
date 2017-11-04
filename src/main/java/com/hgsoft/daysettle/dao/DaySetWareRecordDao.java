package com.hgsoft.daysettle.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.daysettle.entity.DaySetLog;
import com.hgsoft.daysettle.entity.DaySetWareRecord;
import com.hgsoft.daysettle.entity.SumDaySettle;
import com.hgsoft.daysettle.entity.SumDaySettleWare;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class DaySetWareRecordDao extends BaseDao{

	public String findDaySettleList(String type,Long operId,List<String> placeList){
		StringBuffer sql = new StringBuffer();
		List<Map<String, Object>> list = null;
		if(operId!=null){
			sql.append( "select settleDay from CSMS_DaySetWare_Record where SettleFlag=? and placeno in("); 
			for (int i = 0; i < placeList.size(); i++) {
				sql.append("'"+placeList.get(i)+"'");
				if(i!=placeList.size()-1){
					sql.append(" , ");
				}
			}
			sql.append(") order by settleDay desc FETCH FIRST 1 ROW ONLY ");
			
			list = queryList(sql.toString(),type);
		}else{
			sql.append("select SettleDay from CSMS_DaySetWare_Record where reportplaceid is null order by SettleDay desc FETCH FIRST 1 ROW ONLY"); 
			list = queryList(sql.toString());
		}
		String daySettle=null;
		if (!list.isEmpty()) {
			daySettle=list.get(0).get("SettleDay").toString();
		}
		return daySettle;
	}
	
	public void save(DaySetWareRecord daySetWareRecord) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_DaySetWare_Record(");
		sql.append(FieldUtil.getFieldMap(DaySetWareRecord.class,daySetWareRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(DaySetWareRecord.class,daySetWareRecord).get("valueStr")+")");
		save(sql.toString());*/
		
		daySetWareRecord.setHisSeqID(-daySetWareRecord.getId());
		Map map = FieldUtil.getPreFieldMap(DaySetWareRecord.class,daySetWareRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_DaySetWare_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public DaySetWareRecord find(DaySetWareRecord daySetWareRecord) {
		DaySetWareRecord temp = null;
		if (daySetWareRecord != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_DaySetWare_Record ");
			
			/*sql.append(FieldUtil.getFieldMap(DaySetWareRecord.class,daySetWareRecord).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			Map map = FieldUtil.getPreFieldMap(DaySetWareRecord.class,daySetWareRecord);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new DaySetWareRecord();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	
	
	/*public Pager findByPage(Pager pager,Date starTime ,Date endTime, DaySetWareRecord daySetWareRecord) {
		StringBuffer sql=new StringBuffer("select dr.ID,dr.SettleDay,dr.SettleTime,dr.SettleFlag,"
				+ "dr.DifferenceFlag,dr.ReportOperID,dr.ReportTime,dr.ReportPlaceID,dd.ID as did,"
				+ "dd.MainID,dd.SettleDay dsd,dd.ProductType,dd.CurrBalanceNum,dd.CurrRecoverNum,"
				+ "dd.DifferenceNum,row_number() over (order by dr.SettleDay desc) as num "
				+ "from  CSMS_DaySetWare_Record  dr join CSMS_DaySetWare_Detail dd on dr.id = dd.mainid where 1=1  ");		
		
		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			params.ge("to_char(dr.SettleTime,'YYYYMMDDHH24MISS')",format.format(starTime));
			params.le("to_char(dr.SettleTime,'YYYYMMDDHH24MISS')",format.format(endTime));
		}
		if(StringUtil.isNotBlank(daySetWareRecord.getSettleDay())){
			params.eq("dr.SettleDay", daySetWareRecord.getSettleDay());
		}
		if(StringUtil.isNotBlank(daySetWareRecord.getDifferenceFlag())){
			params.eq("dr.DifferenceFlag", daySetWareRecord.getDifferenceFlag());
		}
		
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
		
	}*/
	
	public Pager findByPage(Pager pager,Date starTime ,Date endTime, DaySetWareRecord daySetWareRecord) {
		StringBuffer sql=new StringBuffer("select dr.ID,dr.SettleDay,dr.SettleTime,dr.SettleFlag,"
				+ "dr.DifferenceFlag,dr.ReportOperID,dr.ReportTime,dr.ReportPlaceID,"
//				+ "dd.ID as did, dd.MainID,dd.SettleDay dsd,dd.ProductType,dd.CurrBalanceNum,dd.CurrRecoverNum,dd.DifferenceNum,"
				+ "ROWNUM as num "
				+ "from  CSMS_DaySetWare_Record dr "
				/*+ "   join CSMS_DaySetWare_Detail  dd on dr.id = dd.mainid "*/
				+ " where 1=1 and ReportPlaceID is not null ");		
		
		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
//			sql.append(" and dr.SettleTime >= to_date('"+format.format(starTime)+"','YYYY-MM-DD HH24:MI:SS')");
//			sql.append(" and dr.SettleTime <= to_date('"+format.format(endTime)+"','YYYY-MM-DD HH24:MI:SS')");
			params.geDate("dr.SettleTime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
//			sql.append(" and dr.SettleTime >= to_date('"+format.format(starTime)+"','YYYY-MM-DD HH24:MI:SS')");
//			sql.append(" and dr.SettleTime <= to_date('"+format.format(endTime)+"','YYYY-MM-DD HH24:MI:SS')");
			params.leDate("dr.SettleTime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(daySetWareRecord.getSettleDay())){
			params.eq("dr.SettleDay", daySetWareRecord.getSettleDay());
		}
		if(StringUtil.isNotBlank(daySetWareRecord.getDifferenceFlag())){
			params.eq("dr.DifferenceFlag", daySetWareRecord.getDifferenceFlag());
		}
		if(daySetWareRecord.getReportPlaceID()!=null){
			params.eq("dr.ReportPlaceID", daySetWareRecord.getReportPlaceID());
		}
		
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by dr.SettleDay desc ");
		return this.findByPages(sql.toString(), pager,Objects);
		
	}
	
	public void update(DaySetWareRecord daySetWareRecord){
		String sql ="update CSMS_DaySetWare_Record set DifferenceFlag =?,HisSeqID  = ? where id = ? ";
		saveOrUpdate(sql,daySetWareRecord.getDifferenceFlag(),daySetWareRecord.getHisSeqID(),daySetWareRecord.getId());
	}
	
	public boolean checkRecord(Long operPlaceId,String SettleDay){
		String sql = "select count(1) from CSMS_DaySetWare_Record r join "
				+ "csms_daysetware_detail d on r.id = d.mainid where d.DifferenceFlag = '1' and r.ReportPlaceID = ? and r.SettleDay = ?";
		int count = count(sql,operPlaceId,SettleDay);
		if(count!=0){
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	public SumDaySettleWare checkDaySettleSum(String startTime,String endTime,Long placeId,List<String> placeList,String settleDay){
		SumDaySettleWare sumDaySettleWare = new SumDaySettleWare();
		try {
			//查询日结后资金修正
			/*String afterDaySetSql=" select sum(BalanceDiffNum) as BalanceDiffNum,sum(RecoverDiffNum) as RecoverDiffNum,"
					+ "productType,serviceType from CSMS_AfterDaySetWare  "
					+ " where to_char(reporttime,'YYYYMMDDHH24MISS')>=? and to_char(reporttime,'YYYYMMDDHH24MISS')<? and reportplaceid=?" +
					" group by productType,serviceType ";*/
			
			//查询电子标签提货金额
			StringBuffer afterDaySetSql = new StringBuffer(" select sum(BalanceDiffNum) as BalanceDiffNum,sum(RecoverDiffNum) as RecoverDiffNum,productType,serviceType from CSMS_AfterDaySetWare "
					+ "  where to_char(reporttime,'YYYYMMDDHH24MISS')>=? and to_char(reporttime,'YYYYMMDDHH24MISS')<? and placeno in( ");
			
			
			StringBuffer handFee = new StringBuffer(
					" select productType,serviceType,sum(CurrBalanceNum) CurrBalanceNum,sum(CurrRecoverNum) " +
					"CurrRecoverNum from csms_RegisterDaySetWare_record r join CSMS_RegisterDaySetWare_Detail d" +
					" on r.id = d.mainid  where r.settleday  = "+settleDay+" and placeno in(  ");
			
			
			for (int i = 0; i < placeList.size(); i++) {
				afterDaySetSql.append("'"+placeList.get(i)+"'");
				handFee.append("'"+placeList.get(i)+"'");
				if(i!=placeList.size()-1){
					afterDaySetSql.append(" , ");
					handFee.append( " , ");
				}
			}
			afterDaySetSql.append(" ) group by productType,serviceType ");
			handFee.append( " ) group by productType,serviceType order by productType ");
			
			System.out.println("上日修正库存："+afterDaySetSql);
			System.out.println("网点登记库存："+handFee);
			
			List<Map<String, Object>> afterDaySetList = queryList(afterDaySetSql.toString(),startTime,endTime);
			List<Map<String, Object>> handFeeLig = queryList(handFee.toString());
			
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
			
			if (!handFeeLig.isEmpty()) {
				for (Map<String, Object> map : handFeeLig) {
					
					if("1".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterPaidBalance(map.get("CurrBalanceNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterPaidRecover(map.get("CurrRecoverNum").toString());
						}
					}else if("2".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterAccBalance(map.get("CurrBalanceNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterAccRecover(map.get("CurrRecoverNum").toString());
						}
					}else if("3".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterTagBalance(map.get("CurrBalanceNum").toString());
						}else if("2".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterTagRecover(map.get("CurrRecoverNum").toString());
						}
					}else if("4".equals(map.get("productType"))){
						if("1".equals(map.get("serviceType"))){
							sumDaySettleWare.setRegisterbillBalance(map.get("CurrBalanceNum").toString());
						}
					}
					
				}
			}
			
			System.out.println(sumDaySettleWare);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sumDaySettleWare;
	}
	
	
}
