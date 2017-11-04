package com.hgsoft.acms.obu.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.Enum.TagStateEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class TagInfoDaoACMS extends BaseDao {

	public Pager obuRecordTagInfoList(Pager pager,Date starTime ,Date endTime, TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo) {
		StringBuffer sql=new StringBuffer("select t.id,t.TagNo,t.Issuetime,t.StartTime,t.TagState,c.Organ,c.UserNo,c.IdType,c.IdCode,c.state,c.id as customerId,t.chargeCost , "
				+ "v.vehiclePlate,v.vehicleColor,v.vehicleType,v.id as vehicleId ,t.writebackflag,obuserial,ROWNUM as num "
				+ "from CSMS_Tag_info t join csms_customer c on t.clientid=c.id join  CSMS_CarObuCard_info ca on t.id = ca.TagID join csms_vehicle_info v on ca.VehicleID = v.id where 1=1 and c.state = '1' ");		
		SqlParamer params=new SqlParamer();
		/*if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			params.ge("r.Issuetime","to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS')");
			params.le("r.Issuetime","to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS')");
			
		}*/
		if(starTime !=null){
			params.geDate("t.Issuetime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("t.Issuetime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(customer.getOrgan())){
			params.eq("c.organ", customer.getOrgan());
		}
		if(StringUtil.isNotBlank(customer.getIdType())){
			params.eq("c.IdType", customer.getIdType());
		}
		if(customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}
		if(StringUtil.isNotBlank(customer.getIdCode())){
			params.eq("c.IdCode", customer.getIdCode());
		}
		if(StringUtil.isNotBlank(customer.getSystemType())){
			params.eq("c.systemtype", customer.getSystemType());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
			params.eq("v.vehicleColor", vehicleInfo.getVehicleColor());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
			params.eq("v.vehiclePlate", vehicleInfo.getVehiclePlate());
		}
		if(StringUtil.isNotBlank(tagInfo.getTagNo())){
			params.eq("t.TagNo", tagInfo.getTagNo());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getIdentificationCode())){
			params.eq("substr(v.IdentificationCode,-6)", vehicleInfo.getIdentificationCode());
		}
		if(StringUtil.isNotBlank(tagInfo.getTagState())){
			params.eq("t.TagState", tagInfo.getTagState());
		}
		
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by t.Issuetime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
		
	}
	
	public Pager obuRecordTagInfoListForAMMS(Pager pager,Date starTime ,Date endTime, TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo,String bankCode) {
		StringBuffer sql=new StringBuffer("select * from (select v.IdentificationCode IdentificationCode,t.id,t.TagNo tagNo,t.Issuetime Issuetime,t.StartTime,t.TagState,c.Organ,c.UserNo,c.IdType,c.IdCode,c.state,c.id as customerId,t.chargeCost ,v.vehiclePlate vehiclePlate,v.vehicleColor vehicleColor,v.vehicleType,v.id as vehicleId ,t.writebackflag,obuserial,ROWNUM as num ");
		sql.append(" from CSMS_Tag_info t join csms_customer c on t.clientid=c.id join  CSMS_CarObuCard_info ca on t.id = ca.TagID join csms_vehicle_info v on ca.VehicleID = v.id ");
		sql.append(" where ca.prepaidcid is null and ca.accountcid is null and c.state = '1' and t.ClientID="+customer.getId());
		sql.append(" union all");
		sql.append(" select v.IdentificationCode IdentificationCode,t.id,t.TagNo tagNo,t.Issuetime Issuetime,t.StartTime,t.TagState,c.Organ,c.UserNo,c.IdType,c.IdCode,c.state,c.id as customerId,t.chargeCost ,v.vehiclePlate vehiclePlate,v.vehicleColor vehicleColor,v.vehicleType,v.id as vehicleId ,t.writebackflag,obuserial,ROWNUM as num ");
		sql.append(" from CSMS_Tag_info t join csms_customer c on t.clientid=c.id join  CSMS_CarObuCard_info ca on t.id = ca.TagID join csms_vehicle_info v on ca.VehicleID = v.id ");
		sql.append(" join csms_prepaidc p on p.id=ca.prepaidcid join CSMS_joinCardNoSection j on substr(p.cardno, 0, length(p.cardno) - 1) between j.code and j.endcode where j.cardtype ='22' and j.bankno ="+ bankCode +" and c.state = '1' and t.ClientID="+customer.getId());
		sql.append(" union all");
		sql.append(" select v.IdentificationCode IdentificationCode,t.id,t.TagNo tagNo,t.Issuetime Issuetime,t.StartTime,t.TagState,c.Organ,c.UserNo,c.IdType,c.IdCode,c.state,c.id as customerId,t.chargeCost ,v.vehiclePlate vehiclePlate,v.vehicleColor vehicleColor,v.vehicleType,v.id as vehicleId ,t.writebackflag,obuserial,ROWNUM as num ");
		sql.append(" from CSMS_Tag_info t join csms_customer c on t.clientid=c.id join  CSMS_CarObuCard_info ca on t.id = ca.TagID join csms_vehicle_info v on ca.VehicleID = v.id ");
		sql.append(" join csms_accountc_info a on a.id=ca.accountcid join CSMS_joinCardNoSection j on substr(a.cardno, 0, length(a.cardno) - 1) between j.code and j.endcode  where j.cardtype ='23' and j.bankno ="+ bankCode +" and c.state = '1' and t.ClientID="+customer.getId()+") where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(tagInfo.getTagNo())){
			params.eq("tagNo", tagInfo.getTagNo());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
			params.eq("vehicleColor", vehicleInfo.getVehicleColor());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
			params.eq("vehiclePlate", vehicleInfo.getVehiclePlate());
		}
		if(StringUtil.isNotBlank(vehicleInfo.getIdentificationCode())){
			params.eq("substr(IdentificationCode,-6)", vehicleInfo.getIdentificationCode());
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by issuetime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public TagInfo findById(Long id){
		String sql="select ID,TagNo,ClientID,IssueType,SalesType,Installman,InstallmanName," +
				"ChargeCost,OperID,IssueplaceID,Issuetime,MaintenanceTime," +
				"StartTime,EndTime,TagState,HisSeqID,CorrectOperID,correctTime," +
				"correctPlaceID,IsDaySet,SettleDay,SettletTime,operNo,operName," +
				"placeNo,placeName,CorrectOperNo,CorrectOperName,correctPlaceNo," +
				"correctPlaceName,WriteBackFlag,productType,Cost,InstallmanName," +
				"blackFlag,obuserial,IsWriteOBU from CSMS_Tag_info where id=?";
		List<TagInfo> tagInfos = super.queryObjectList(sql, TagInfo.class, id);
		if (tagInfos == null || tagInfos.isEmpty()) {
			return null;
		}
		return tagInfos.get(0);
	}
	public TagInfoHis findHisByHisId(Long hisId){
		String sql="";
		List list=null;
		if(hisId!=null){
			sql="select * from csms_tag_infohis where hisSeqId="+hisId;
		}else{
			sql="select * from csms_tag_infohis where hisSeqId is null";
		}
		list=queryList(sql);
		TagInfoHis tagInfoHis = null;
		if(list!=null && list.size()!=0) {
			tagInfoHis = (TagInfoHis) this.convert2Bean((Map<String, Object>) list.get(0), new TagInfoHis());
		}

		return tagInfoHis;
	}

	public TagInfo findByHisId(Long hisId){
		String sql="";
		List list=null;
		if(hisId!=null){
			sql="select * from csms_tag_info where hisSeqId="+hisId;
		}else{
			sql="select * from csms_tag_info where hisSeqId is null";
		}
		list=queryList(sql);
		TagInfo tagInfoHis = null;
		if(list!=null && list.size()!=0) {
			tagInfoHis = (TagInfo) this.convert2Bean((Map<String, Object>) list.get(0), new TagInfo());
		}

		return tagInfoHis;
	}

	public TagInfo findByVehicleInfoId(Long id){
		String sql="select ID,TagNo,ClientID,"
				+ "IssueType,SalesType,Installman,ChargeCost,OperID"
				+ ",IssueplaceID,Issuetime,MaintenanceTime,StartTime,EndTime,"
				+ "TagState,HisSeqID,CorrectOperID,correctTime,correctPlaceID from CSMS_Tag_info t join csms_carobucard_info c on t.id=c.TagID  where c.VehicleID=?";
		List<TagInfo> tagInfos = super.queryObjectList(sql, TagInfo.class, id);
		if (tagInfos == null || tagInfos.isEmpty()) {
			return null;
		}
		return tagInfos.get(0);
	}

	public TagInfo findByTagNo(String tagNo){
		String sql="select ID,TagNo,ClientID,"
				+ "IssueType,SalesType,Installman,ChargeCost,OperID"
				+ ",IssueplaceID,Issuetime,MaintenanceTime,StartTime,EndTime,"
				+ "TagState,HisSeqID,CorrectOperID,correctTime,correctPlaceID,obuserial,IsWriteOBU,WriteBackFlag from CSMS_Tag_info where TagNo=?";
		List<TagInfo> tagInfos = super.queryObjectList(sql, TagInfo.class, tagNo);
		if (tagInfos == null || tagInfos.isEmpty()) {
			return null;
		}
		return tagInfos.get(0);
	}
	/**
	 * 根据始末电子标签查找
	 * @param beginNo
	 * @param endNo
	 * @return
	 */
	public TagInfo findByTagNo(String beginNo,String endNo){
		String sql="select * from CSMS_Tag_info where TagNo=? or TagNo=? ";
		List<TagInfo> tagInfos = super.queryObjectList(sql, TagInfo.class, beginNo, endNo);
		if (tagInfos == null || tagInfos.isEmpty()) {
			return null;
		}
		return tagInfos.get(0);
	}
	
	public void save(TagInfo tagInfo){
		tagInfo.setHisSeqID(-tagInfo.getId());
		Map map = FieldUtil.getPreFieldMap(TagInfo.class,tagInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_Tag_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		
			/*StringBuffer sql=new StringBuffer("insert into CSMS_Tag_info(");
			sql.append(FieldUtil.getFieldMap(TagInfo.class,tagInfo).get("nameStr")+") values(");
			sql.append(FieldUtil.getFieldMap(TagInfo.class,tagInfo).get("valueStr")+")");
			save(sql.toString());*/
		}
	
	public void update(TagInfo tagInfo) {
		Map map = FieldUtil.getPreFieldMap(TagInfo.class,tagInfo);
		StringBuffer sql=new StringBuffer("update CSMS_Tag_info set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),tagInfo.getId());
		
		/*StringBuffer sql=new StringBuffer("update CSMS_Tag_info set ");
		sql.append(FieldUtil.getFieldMap(TagInfo.class,tagInfo).get("nameAndValue")+" where id="+tagInfo.getId());
		update(sql.toString());*/
		
	}
	
	/**
	 * 更新tagInfo对象里面不为null的字段信息
	 * @param tagInfo
	 * @return void
	 */
	public void updateNotNullTagInfo(TagInfo tagInfo){
		Map map = FieldUtil.getPreFieldMap(TagInfo.class,tagInfo);
		StringBuffer sql=new StringBuffer("update CSMS_Tag_info set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),tagInfo.getId());
	}
	
	
	public void updateNotNull(TagInfo tagInfo) {
		/*Map map = FieldUtil.getPreFieldMap(TagInfo.class,tagInfo);
		StringBuffer sql=new StringBuffer("update CSMS_Tag_info set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),tagInfo.getId());*/
		
		/*StringBuffer sql=new StringBuffer("update CSMS_Tag_info set ");
		sql.append(FieldUtil.getFieldMap(TagInfo.class,tagInfo).get("nameAndValue")+" where id="+tagInfo.getId());
		update(sql.toString());*/
		
		String sql ="update CSMS_Tag_info set ChargeCost=?,HisSeqID=? ";
		if(tagInfo.getMaintenanceTime()!=null){
			sql=sql+",MaintenanceTime=?  where id = ?";
			saveOrUpdate(sql.toString(), tagInfo.getChargeCost(),tagInfo.getHisSeqID(),tagInfo.getMaintenanceTime(),tagInfo.getId());
		}else{
			sql=sql+"  where id = ?";
			saveOrUpdate(sql.toString(), tagInfo.getChargeCost(),tagInfo.getHisSeqID(),tagInfo.getId());
		}
	}
		
	public void save(Long vehicleid,Long tagid){
		String sql="insert into  csms_carobucard_info(vehicleid,tagid) values(?,?)";
		this.jdbcUtil.getJdbcTemplate().update(sql, new Object[]{vehicleid,tagid});
	}
	
	public void delete(Long id){
		String sql="delete from  CSMS_Tag_info where id=?";
		this.jdbcUtil.getJdbcTemplate().update(sql, id);
	}
	public void deleteByTagNo(String  tagNo){
		String sql="delete from  CSMS_Tag_info where tagNo=?";
		this.jdbcUtil.getJdbcTemplate().update(sql, tagNo);
	}
	
	public TagInfo findByCutomerId(Long clientId) {
		String sql = "select * from CSMS_Tag_info where clientId=?";
		List<TagInfo> tagInfos = super.queryObjectList(sql, TagInfo.class, clientId);
		if (tagInfos == null || tagInfos.isEmpty()) {
			return null;
		}
		return tagInfos.get(0);
	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		
		StringBuffer sql=new StringBuffer("update csms_tag_info set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(Issuetime,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(Issuetime,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		saveOrUpdate(sql.toString(),settleDay,startTime,endTime);
	}
	public void updateTransfer(Long id, String tagNo) {
		StringBuffer sql=new StringBuffer("update csms_tag_info set ClientID=?,TagState=?,WriteBackFlag=?  where TagNo=?");
		saveOrUpdate(sql.toString(),id,TagStateEnum.stop.getValue(),"0",tagNo);
	}
	public void updateIsWriteObu(Long id) {
		String sql = "update csms_tag_info set ISWRITEOBU=2 where id="+id+"";
		update(sql);
	}
	
}
