package com.hgsoft.daysettle.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.daysettle.entity.AfterDaySetWare;
import com.hgsoft.daysettle.entity.BeforeDaySet;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class BeforeDaySetDao extends BaseDao{
	/**
	 * 查出未日结的记录
	 * @return
	 */
	public Pager list(Pager pager,Date starTime ,Date endTime,Customer customer,VehicleInfo vehicleInfo,String type,String settleDay,Long placeId,Long id,List<String> pointList){
		
		/*StringBuffer dayLogSql =  new StringBuffer("select count(1) from( select endtime from CSMS_DaySet_Log  where endtime is not null  and placeno in( " ); 
		
		for (int i = 0; i < pointList.size(); i++) {
			dayLogSql.append("'"+pointList.get(i)+"'");
			if(i!=pointList.size()-1){
				dayLogSql.append(" , ");
			}
		};
		dayLogSql.append(" ) order by settleday desc  FETCH FIRST 1 ROW ONLY )");
		int num = jdbcUtil.getJdbcTemplate().queryForInt(dayLogSql.toString());*/
		
		StringBuffer sql = new StringBuffer(
				 " select id,cid,type,name,organ,idtype,idcode,no,vehiclecolor,vehicleplate,ChargeCost,identificationcode,placeId,IssueTime,placeno,placename,issuetype,"
				+" ROWNUM as num  from( "
				+" select distinct t.id id,c.id cid,1 type,'电子标签发行' name,t.issuetype issuetype, "
				+" c.organ organ,c.idtype idtype,c.idcode idcode, t.tagno no,v.vehiclecolor vehiclecolor,  "
				+" v.vehicleplate vehicleplate, t.ChargeCost ChargeCost,v.identificationcode identificationcode,  "
				+" t.issueplaceid placeId,t.issuetime IssueTime,t.placeno,t.placename   from csms_customer c join CSMS_Tag_info t on c.id = t.ClientID "
				+" left join CSMS_Tag_BusinessRecord tr on tr.TagNo = t.tagno  left join csms_carobucard_info cb  on t.id = cb.tagid left join csms_vehicle_info v  "
				+" on cb.vehicleid = v.id    where tr.BusinessType='1' and c.state='1' and tr.isdayset is  null "
				+" union all "
				+" select distinct t.id id,c.id cid,1 type,'电子标签更换' name,'0' issuetype, "
				+" c.organ organ,c.idtype idtype,c.idcode idcode, t.tagno no,v.vehiclecolor vehiclecolor,  "
				+" v.vehicleplate vehicleplate, t.ChargeCost ChargeCost,v.identificationcode identificationcode,  "
				+" t.issueplaceid placeId,t.issuetime IssueTime,t.placeno,t.placename   from csms_customer c join CSMS_Tag_info t on c.id = t.ClientID "
				+" left join CSMS_Tag_BusinessRecord tr on tr.TagNo = t.tagno  left join csms_carobucard_info cb  on t.id = cb.tagid left join csms_vehicle_info v  "
				+" on cb.vehicleid = v.id    where tr.BusinessType='3' and c.state='1' and tr.isdayset is  null "
				+" union all "
				+" select distinct p.id id,c.id cid,3 type,'储值卡发行' name,'0' issuetype,c.organ organ,c.idtype idtype,c.idcode idcode,"
				+" p.cardno no,v.vehiclecolor vehiclecolor,v.vehicleplate vehicleplate, p.RealCost ChargeCost,v.identificationcode identificationcode," +
				"  p.saleplaceid placeId,p.saletime IssueTime,p.placeno,p.placename "
				+" from csms_customer c  join CSMS_PrePaidC p on c.id = p.customerid  join CSMS_PrePaidC_bussiness pb  on pb.cardNo = p.cardNo  "
				+" left join csms_carobucard_info cb  on p.id = cb.prepaidcid left join  csms_vehicle_info v on cb.vehicleid = v.id  "
				+" where pb.state = '1' and c.state='1'  and pb.isdayset is  null "
				+" union all "
				+" select distinct p.id id,c.id cid,4 type,'储值卡换卡' name,'0' issuetype,c.organ organ,c.idtype idtype,c.idcode idcode,"
				+" p.cardno no,v.vehiclecolor vehiclecolor,v.vehicleplate vehicleplate, p.RealCost ChargeCost,v.identificationcode identificationcode," +
				"  p.saleplaceid placeId,p.saletime IssueTime,p.placeno,p.placename "
				+" from csms_customer c  join CSMS_PrePaidC p on c.id = p.customerid  join CSMS_PrePaidC_bussiness pb  on pb.cardNo = p.cardNo  "
				+" left join csms_carobucard_info cb  on p.id = cb.prepaidcid left join  csms_vehicle_info v on cb.vehicleid = v.id "
				+" where  pb.state = '11' and c.state='1'  and pb.isdayset is  null "
				+" union all "
				+" select distinct p.id id,c.id cid,5 type,'储值卡补领' name,'0' issuetype,c.organ organ,c.idtype idtype,c.idcode idcode,"
				+" p.cardno no,v.vehiclecolor vehiclecolor,v.vehicleplate vehicleplate, p.RealCost ChargeCost,v.identificationcode identificationcode," +
				"  p.saleplaceid placeId,p.saletime IssueTime,p.placeno,p.placename "
				+" from csms_customer c  join CSMS_PrePaidC p on c.id = p.customerid  join CSMS_PrePaidC_bussiness pb  on pb.cardNo = p.cardNo  "
				+" left join csms_carobucard_info cb  on p.id = cb.prepaidcid left join  csms_vehicle_info v on cb.vehicleid = v.id "
				+" where  pb.state = '9' and c.state='1'  and pb.isdayset is  null "
				+" union all "
				+" select distinct a.id id,c.id cid,6 type,'记帐卡发行' name,'0' issuetype,c.organ organ,c.idtype idtype,c.idcode idcode,"
				+" a.cardno no,v.vehiclecolor vehiclecolor,v.vehicleplate vehicleplate, a.RealCost ChargeCost,v.identificationcode identificationcode," +
				"  a.issueplaceid placeId,a.issuetime IssueTime,a.placeno,a.placename "
				+" from csms_customer c join CSMS_AccountC_info a  on c.id = a.customerid  join CSMS_AccountC_bussiness ab "
				+" on ab.cardNo = a.cardNo left join csms_carobucard_info cb  on a.id = cb.AccountCID left join  csms_vehicle_info v on cb.vehicleid = v.id "
				+" where ab.state = '1' and c.state='1' and ab.isdayset is  null "
				+" union all "
				+" select distinct a.id id,c.id cid,7 type,'记帐卡换卡' name,'0' issuetype,c.organ organ,c.idtype idtype,c.idcode idcode,"
				+" a.cardno no,v.vehiclecolor vehiclecolor,v.vehicleplate vehicleplate, a.RealCost ChargeCost,v.identificationcode identificationcode," +
				"  a.issueplaceid placeId,a.issuetime IssueTime,a.placeno,a.placename "
				+" from csms_customer c join CSMS_AccountC_info a  on c.id = a.customerid  join CSMS_AccountC_bussiness ab "
				+" on ab.cardNo = a.cardNo left join csms_carobucard_info cb  on a.id = cb.AccountCID left join  csms_vehicle_info v on cb.vehicleid = v.id "
				+" where ab.state = '7' and c.state='1' and ab.isdayset is  null "
				+" union all "
				+" select distinct a.id id,c.id cid,8 type,'记帐卡补领' name,'0' issuetype,c.organ organ,c.idtype idtype,c.idcode idcode,"
				+" a.cardno no,v.vehiclecolor vehiclecolor,v.vehicleplate vehicleplate, a.RealCost ChargeCost,v.identificationcode identificationcode," +
				"  a.issueplaceid placeId,a.issuetime IssueTime,a.placeno,a.placename "
				+" from csms_customer c join CSMS_AccountC_info a  on c.id = a.customerid  join CSMS_AccountC_bussiness ab "
				+" on ab.cardNo = a.cardNo left join csms_carobucard_info cb  on a.id = cb.AccountCID left join  csms_vehicle_info v on cb.vehicleid = v.id "
				+" where ab.state = '5' and c.state='1' and ab.isdayset is  null "
				+")  "
				); 
		
		/*if(num!=0){
			sql.append(" where IssueTime > (select endtime from CSMS_DaySet_Log  where endtime is not null  and placeno in( ");
			for (int i = 0; i < pointList.size(); i++) {
				sql.append("'"+pointList.get(i)+"'");
				if(i!=pointList.size()-1){
					sql.append(" , ");
				}
			};
			sql.append(")  order by settleday desc  FETCH FIRST 1 ROW ONLY) ");
		}else{
		}*/
		sql.append(" where 1=1 ");
		
		SqlParamer params=new SqlParamer();
		/*if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			sql.append(" and IssueTime = to_date('"+format.format((starTime) )+"','YYYY-MM-DD')  ");
		}*/
		/*if(endTime != null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql.append(" and r.OperTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}*/
		if(StringUtil.isNotBlank(customer.getIdCode())){
			params.eq("IdCode",customer.getIdCode());
		}
		if(StringUtil.isNotBlank(customer.getIdType())){
			params.eq("IdType",customer.getIdType());
		}
		if(StringUtil.isNotBlank(customer.getOrgan())){
			params.eq("organ",customer.getOrgan());
		}
		if(StringUtil.isNotBlank(type)){
			params.eq("type",type);
		}
		if(StringUtil.isNotBlank(settleDay)){
			/*SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			try {
				Date date = format.parse(settleDay);
				format=new SimpleDateFormat("yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
			/*sql.append(" and to_char(IssueTime,'YYYYMMDD') ="+settleDay+" ");*/
			params.eq("to_char(IssueTime,'YYYYMMDD')",settleDay);
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
			params.eq("vehiclecolor",vehicleInfo.getVehicleColor());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
			params.eq("vehicleplate",vehicleInfo.getVehiclePlate());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getIdentificationCode())){
			params.eq("substr(IdentificationCode, -6)",vehicleInfo.getIdentificationCode());
		}
	/*	if(placeId!=null){
			params.eq("placeId",placeId);
		}*/
		if(id!=null){
			params.eq("id",id);
		}
		sql.append(" and placeno  in( ");
		for (int i = 0; i < pointList.size(); i++) {
			sql.append("'"+pointList.get(i)+"'");
			if(i!=pointList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(")  ");
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by IssueTime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public void save(BeforeDaySet beforeDaySet) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_BeforeDaySetFee(");
		sql.append(FieldUtil.getFieldMap(BeforeDaySet.class,beforeDaySet).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(BeforeDaySet.class,beforeDaySet).get("valueStr")+")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(BeforeDaySet.class,beforeDaySet);
		StringBuffer sql=new StringBuffer("insert into CSMS_BeforeDaySetFee");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

}
