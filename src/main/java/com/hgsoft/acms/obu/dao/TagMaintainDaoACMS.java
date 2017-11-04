package com.hgsoft.acms.obu.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.obu.entity.TagMainRecordHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Component
public class TagMaintainDaoACMS extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	

	@SuppressWarnings("unchecked")
	public Pager findTagMaintain(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerId) {
		/*String sql = "select a.*,row_number() over(order by TID) num from( select t.id as TID,v.id as VID,tr.id as TRID,ct.id as CID,t.tagno,v.vehicleplate,"
				+ "v.vehiclecolor,t.tagstate,tr.MaintainType,tr.BackToCustomerTime,ct.organ,TR.Repairbacktime, idType,idCode,IdentificationCode , row_number() over(partition by t.id order by tr.id desc) r "
				+ "from csms_tag_info t left join csms_carobucard_info c on t.id = c.tagid left join csms_vehicle_info v on c.vehicleid = v.id "
				+ "left join CSMS_TagMain_Record tr on tr.TagInfoID = t.id  join csms_customer ct on ct.id=t.clientid where (t.tagstate ='1' and v.id is not null) "
				+ "or (t.tagstate ='3' and MaintainType=3 and  BackToCustomerTime is null) ) a  where CID="+customerId+" and r=1   ";*/
		String sql = "select * from (select a.*,ROWNUM as  num from( select t.id as TID,t.clientid tclientid,"+customerId+" CID,v.id as VID,tr.id as TRID,t.tagno,v.vehicleplate,"
				+ "v.vehiclecolor,t.tagstate,tr.MaintainType,tr.BackToCustomerTime BackToCustomerTime,tr.SendRepairTime SendRepairTime,tr.RepairBackTime RepairBackTime,tr.faultType faultType,tr.reason reason," +
				"tr.NoticeCustomerTime NoticeCustomerTime,IdentificationCode , row_number() over(partition by t.id order by tr.id desc) r "
				+ "from csms_tag_info t left join csms_carobucard_info c on t.id = c.tagid left join csms_vehicle_info v on c.vehicleid = v.id "
				+ "left join CSMS_TagMain_Record tr on tr.TagInfoID = t.id  where (t.tagstate ='1' and v.id is not null) "
				+ "or (t.tagstate ='3' and MaintainType=3 and  BackToCustomerTime is null) ) a  where tclientid="+customerId+" and r=1  ) al " +
						"where  not exists (select BACKUPTAGNO from csms_tagmain_record where BACKTOCUSTOMERTIME is  null and BACKUPTAGNO=al.tagno ) ";
		SqlParamer params=new SqlParamer();
		
		if(StringUtil.isNotBlank(tagNo)){
			params.eq("tagNo", tagNo);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			params.eq("vehicleColor", vehicleColor);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			params.eq("vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}
		if(StringUtil.isNotBlank(endSixNo)){
			params.like("substr(IdentificationCode,-6)", endSixNo);
		}
		
		sql=sql+params.getParam();
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql=sql+" order by TID ";
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}

	/**
	 * 查看电子标签详情
	 * 正常状态的电子标签，没有维护记录表或者维护记录返回客户时间不为空，不显示维护信息
	 * 维修状态的电子标签，有维护记录且返回客户时间为空，没有车辆信息
	 * 电子标签信息，客户信息是一定有的
	 * @param customerId
	 * @param vehicleId
	 * @param tagInfoId
	 * @param tagMaintainId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findTagById(Long customerId, Long vehicleId, Long tagInfoId, Long tagMaintainId) {
		//客户信息
		String sql_1 = "select c.organ,c.idtype,c.idcode from csms_customer c where c.id=?";
		Map<String, Object> map_1 = jdbcUtil.getJdbcTemplate().queryForMap(sql_1,customerId);
		//车辆信息
		if(vehicleId!=null){
			String sql_2 = "select v.vehicleplate,v.vehiclecolor,v.model,v.vehicletype,v.vehicleWeightLimits,v.Vehicletype,"
					+ "v.vehicleSpecificInformation,v.vehicleLong,v.vehiclewidth,v.vehicleheight,v.vehicleaxles,v.vehiclewheels,"
					+ "v.Vehicleengineno,v.Usingnature,v.Identificationcode,v.owner from csms_vehicle_info v where v.id=?";
			Map<String, Object> map_2 = jdbcUtil.getJdbcTemplate().queryForMap(sql_2,vehicleId);			
			map_1.putAll(map_2);
		}
		//电子标签发行信息
		String sql_3 = "select t.tagno,t.chargecost as tagchargecost,t.issuetime,t.maintenancetime,t.starttime,t.endtime "
				+ "from csms_tag_info t where t.id=?";
		Map<String, Object> map_3 = jdbcUtil.getJdbcTemplate().queryForMap(sql_3,tagInfoId);
		map_1.putAll(map_3);
		//电子标签维修信息
		if(tagMaintainId!=null){
			String sql_4 = "select m.chargecost as mainchargecost,m.BackOperID,m.maintaintype,m.FaultType,m.reason,m.receiveplace,m.memo,m.backuptagno,"
					+ "m.contactman,m.contactphone,m.postcode,m.address,m.invoicehead,m.recovertime,m.sendrepairtime,m.repairbacktime,"
					+ "m.noticecustomertime,m.backtocustomertime,m.operid,m.FaultTypeId,m.ReasonId from csms_tagmain_record m where m.id=?";
			Map<String, Object> map_4 = jdbcUtil.getJdbcTemplate().queryForMap(sql_4,tagMaintainId);
			map_1.putAll(map_4);
			String backupTagNo = (String) map_4.get("backuptagno");
			if(StringUtil.isNotBlank(backupTagNo)){
				String sql_5 = "select salesType,t.issuetime as backupissuetime,t.maintenancetime as backupmaintenancetime,t.starttime as backstarttime,t.endtime as backendtime from csms_tag_info t where tagno=?";
				Map<String, Object> map_5 = jdbcUtil.getJdbcTemplate().queryForMap(sql_5,backupTagNo);
				map_1.putAll(map_5);
			}
			if(StringUtil.isNotBlank((String)map_4.get("backuptagno"))){
				String sql_5 = "select issuetime as backupissuetime,endTime as backupendTime,startTime as backupstartTime,maintenanceTime as backupmaintenanceTime from csms_tag_info where tagno='"+ map_4.get("backuptagno").toString() +"'";
				Map<String, Object> map_5 = jdbcUtil.getJdbcTemplate().queryForMap(sql_5);
				map_1.putAll(map_5);
			}
		}
		return map_1;
	}


//	public Map<String, Object> findRegisterNeededInfo(Long tagInfoId) {
//		String sql = "select organ,IdType,IdCode,vehiclePlate,vehicleColor,tagNo,ChargeCost,Issuetime,MaintenanceTime,StartTime,EndTime "
//				+ "from csms_tag_info tag join csms_customer cus on cus.id=tag.clientid join csms_carobucard_info caro on caro.tagid=tag.id "
//				+ "join csms_vehicle_info veh on caro.vehicleid=veh.id where tag.id=? ";
//		return jdbcUtil.getJdbcTemplate().queryForMap(sql, tagInfoId);
//	}


	/*public void createBackupTagInfo(String backupTagNo, String salesType, Long customerId, Long vehicleId) {
		BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTaginfo_NO");
		Long id = Long.valueOf(SEQ_CSMSTagBusinessRecord_NO.toString());
		StringBuffer sql = new StringBuffer("insert into csms_tag_info(id,tagNo,salestype,issuetime,maintenancetime,starttime,endtime,clientid) values (");
		if(id==null)
			sql.append("NULL,");
		else
			sql.append(id+",");
		
		if(!StringUtil.isNotBlank(backupTagNo))
			sql.append("NULL,");
		else
			sql.append("'"+backupTagNo+"',");
		
		if(!StringUtil.isNotBlank(salesType))
			sql.append("NULL,");
		else
			sql.append(salesType+",");
		
		sql.append("sysdate,sysdate,sysdate,sysdate+60,");
		
		if(customerId==null)
			sql.append("NULL,");
		else
			sql.append(customerId+")");
		
		save(sql.toString());
		//更新车卡绑定表
		updateCarObuCardInfo(id, vehicleId);
//		StringBuffer sql_2=new StringBuffer("update CSMS_CarObuCard_info set ");
//		sql_2.append("TagID=" +id+"  where vehicleID="+vehicleId);
//		update(sql_2.toString());
	}*/
	public void save(TagMainRecord tagMainRecord){
		tagMainRecord.setHisSeqID(-tagMainRecord.getId());
		Map map = FieldUtil.getPreFieldMap(TagMainRecord.class,tagMainRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_TagMain_Record ");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	/*public void updateCarObuCardInfo(Long tagInfoId,Long vehicleId) {
		StringBuffer sql = new StringBuffer("update CSMS_CarObuCard_info set ");
		sql.append("TagID=" +tagInfoId+"  where vehicleID="+vehicleId);
		update(sql.toString());
		
	}*/

	public void updateTagInfo(Long tagInfoId,Long hisSeqId, int tagState) {
		StringBuffer sql = new StringBuffer("update CSMS_Tag_info set TagState=?,hisseqid=?,blackflag=? where id=? ");
		super.update(sql.toString(), tagState, hisSeqId, BlackFlagEnum.black.getValue(), tagInfoId);
	}




//	public void saveTagBusinessRecord(Long tagInfoId, String tagNo, Long clientID, Long vehicleID, Object installmanID,
//			Object memo, int currentTagState) {
//	String sqlString = null;
//	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
//	Long id = Long.valueOf(SEQ_CSMSTagBusinessRecord_NO.toString());
//	
//	StringBuffer sql = new StringBuffer("insert into "
//			+ "CSMS_Tag_BusinessRecord(ID,tagNo,clientID,vehicleID,"
//			+ "OperID,OperTime,OperplaceID,BusinessType,InstallmanID,"
//			+ "CurrentTagState,ImportCardNo,WriteState,Memo,FromID) values(");
//	
//	if(id == null){
//		sql.append("NULL,");
//	}else{
//		sql.append(id + ",");
//	}
//	if(!StringUtil.isNotBlank(tagNo)){
//		sql.append("NULL,");
//	}else{
//		sql.append("'" + tagNo + "',");
//	}
//	if(clientID == null){
//		sql.append("NULL,");
//	}else{
//		sql.append(clientID + ",");
//	}
//	if(vehicleID == null){
//		sql.append("NULL,");
//	}else{
//		sql.append(vehicleID + ",");
//	}
//	//操作人、操作时间、操作网点
//	sql.append("1,");
//	sql.append("sysdate,");
//	sql.append("1,");
//	//业务原因是维修
//	sql.append("2,");
//	if(installmanID == null){
//		sql.append("NULL,");
//	}else{
//		sql.append(installmanID + ",");
//	}
//	//CurrentTagState
//	sql.append(currentTagState+",");
//	//ImportCardNo
//	sql.append("'1',");
//	//WriteState
//	sql.append("'1',");
//	
//	
//	if(memo == null){
//		sql.append("NULL,");
//	}else{
//		sql.append("'"+memo + "',");
//	}
//	if(tagInfoId == null){
//		sql.append("NULL,");
//	}else{
//		sql.append(tagInfoId + ",");
//	}
//	
//	if (sql.toString().endsWith(",")) {
//		sqlString = sql.substring(0, sql.length() - 1);
//	}
//	sqlString += ")";
//	save(sqlString);
//		
//	}


//	public void saveMaintainRecord(Long tagInfoId, String tagNo, Long customerId, Long vehicleId, TagMainRecord tagMainRecord) {
//		BigDecimal SEQ_CSMSTagMaintainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecord_NO");
//		Long id = Long.valueOf(SEQ_CSMSTagMaintainRecord_NO.toString());
//		StringBuffer sql = new StringBuffer("insert into csms_tagmain_record(id,taginfoid,tagNo,clientid,vehicleid,maintaintype,Chargecost,faulttype,reason,receiveplace,memo,contactman,contactphone,postcode,address,invoicehead,backuptagno)values(");
//		sql.append(id+",");
//		if(tagInfoId==null)	sql.append("NULL,");
//		else sql.append(tagInfoId+",");
//		if(!StringUtil.isNotBlank(tagNo))	sql.append("NULL,");
//		else sql.append(tagNo+",");
//		if(customerId==null)	sql.append("NULL,");
//		else sql.append(customerId+",");
//		if(vehicleId==null)	sql.append("NULL,");
//		else sql.append(vehicleId+",");
//		sql.append("3,");
//		if(tagMainRecord.getChargeCost()==null)	sql.append("NULL,");
//		else sql.append(tagMainRecord.getChargeCost()+",");
//		if(!StringUtil.isNotBlank(tagMainRecord.getFaultType()))	sql.append("NULL,");
//		else sql.append(tagMainRecord.getFaultType()+",");
//		if(!StringUtil.isNotBlank(tagMainRecord.getReason()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getReason()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getReceivePlace()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getReceivePlace()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getMemo()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getMemo()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getContactMan()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getContactMan()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getContactPhone()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getContactPhone()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getPostcode()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getPostcode()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getAddress()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getAddress()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getInvoiceHead()))	sql.append("NULL,");
//		else sql.append("'"+tagMainRecord.getInvoiceHead()+"',");
//		if(!StringUtil.isNotBlank(tagMainRecord.getBackupTagNo()))	sql.append("NULL");
//		else sql.append("'"+tagMainRecord.getBackupTagNo()+"'");
//		sql.append(")");
//		
//		save(sql.toString());
//	}


	public void saveMaintainHis(Long RecordId,Long hisId) {
		
		StringBuffer sql = new StringBuffer(
				"insert into  CSMS_TAGMAIN_RECORDHIS(ID,taginfoid,tagno,clientid,vehicleid,maintaintype,"
				+ "installman,chargecost,reason,memo,newtagno,faulttype,receiveplace,backuptagno,"
				+ "contactman,contactphone,postcode,address,invoicehead,recovertime,sendrepairtime,repairbacktime,"
				+ "noticecustomertime,backtocustomertime,backoperid,operid,issueplaceid,issuetime,hisseqid，CreateDate,CreateReason,operNo,operName,placeNo,placeName )"
				+ "SELECT "+hisId+",taginfoid,tagno,clientid,vehicleid,maintaintype,installman,chargecost,reason,memo,newtagno,faulttype,receiveplace,backuptagno,"
				+ "contactman,contactphone,postcode,address,invoicehead,recovertime,sendrepairtime,repairbacktime,"
				+ "noticecustomertime,backtocustomertime,backoperid,operid,issueplaceid,issuetime,hisseqid,sysdate,'修改维修记录',operNo,operName,placeNo,placeName "
				+ " FROM CSMS_TAGMAIN_RECORD WHERE ID=? ");
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(), RecordId);
	}
	
	public TagMainRecord findById(Long id){
		TagMainRecord temp = new TagMainRecord();
		temp.setId(id);
		/*StringBuffer tempsql = new StringBuffer("select * from csms_tagmain_record ") ;
		Map map = FieldUtil.getPreFieldMap(TagMainRecord.class,temp);
		tempsql.append(map.get("selectNameStrNotNull"));
		tempsql.append(" order by id desc");
		List<Map<String, Object>> list = queryList(tempsql.toString(), ((List) map.get("paramNotNull")).toArray());
			try {
				if (!list.isEmpty()) {
				this.convert2Bean(list.get(0), temp);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		return findByTagMainRecord(temp);
	}

	public void updateMaintainRecord(TagMainRecord tagMainRecord) {
		TagMainRecord temp = findById(tagMainRecord.getId());
		temp.setContactMan(tagMainRecord.getContactMan());
		temp.setContactPhone(tagMainRecord.getContactPhone());
		temp.setFaultType(tagMainRecord.getFaultType());
		temp.setFaultTypeId(tagMainRecord.getFaultTypeId());
		temp.setReasonId(tagMainRecord.getReasonId());
		temp.setReason(tagMainRecord.getReason());
		temp.setPostcode(tagMainRecord.getPostcode());
		temp.setAddress(tagMainRecord.getAddress());
		temp.setInvoiceHead(tagMainRecord.getInvoiceHead());
		temp.setReceivePlace(tagMainRecord.getReceivePlace());
		temp.setMemo(tagMainRecord.getMemo());
		temp.setSendRepairTime(tagMainRecord.getSendRepairTime());
		temp.setRepairBackTime(tagMainRecord.getRepairBackTime());
		temp.setNoticeCustomerTime(tagMainRecord.getNoticeCustomerTime());
		//temp.setBackToCustomerTime(tagMainRecord.getBackToCustomerTime());
		temp.setHisSeqID(tagMainRecord.getHisSeqID());
//		StringBuffer sql = new StringBuffer("update CSMS_TAGMAIN_RECORD set ");
//		sql.append(FieldUtil.getFieldMap(TagMainRecord.class,temp).get("nameAndValue")+" where id="+tagMainRecord.getId());
		Map map = FieldUtil.getPreFieldMap(TagMainRecord.class,temp);
		StringBuffer sql=new StringBuffer("update CSMS_TAGMAIN_RECORD set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),temp.getId());
	}
	public TagMainRecord findByHisId(Long hisId){
		TagMainRecord temp = new TagMainRecord();
		temp.setHisSeqID(hisId);
		return findByTagMainRecord(temp);
	}
	
	public TagMainRecord findByTagMainRecord(TagMainRecord temp){
		StringBuffer tempsql = new StringBuffer("select * from csms_tagmain_record ") ;
		Map map = FieldUtil.getPreFieldMap(TagMainRecord.class,temp);
		String condition = (String) map.get("selectNameStrNotNull");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("TagMaintainDaoACMS.findByTagMainRecord查询条件为空");
		}
		tempsql.append(condition);
		tempsql.append(" order by id desc");
		List<TagMainRecord> tagMainRecords = super.queryObjectList(tempsql.toString(), TagMainRecord.class, ((List) map.get("paramNotNull")).toArray());
		if (tagMainRecords == null || tagMainRecords.isEmpty()) {
			return null;
		}
		return tagMainRecords.get(0);
	}
	
	public TagMainRecordHis findHisByHisId(Long id){
		String sql="select * from csms_tagmain_recordHis where hisseqid=?";
		List<TagMainRecordHis> tagMainRecordHises = super.queryObjectList(sql, TagMainRecordHis.class, id);
		if (tagMainRecordHises == null || tagMainRecordHises.isEmpty()) {
			return null;
		}
		return tagMainRecordHises.get(0);
	}
	public void savetagInfoHis(TagInfoHis tagInfoHis, String backupTagNo) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_Tag_infoHis(ID,TagNo,ClientID,IssueType,SalesType,Installman,"
				+ "ChargeCost,OperID,IssueplaceID,Issuetime,MaintenanceTime,"
				+ "StartTime,EndTime,TagState,HisSeqID,CreateDate,CreateReason )"
				+ "SELECT "+tagInfoHis.getId()+",TagNo,ClientID,IssueType,"
				+ "SalesType,Installman,ChargeCost,OperID,IssueplaceID,"
				+ "Issuetime,MaintenanceTime,StartTime,EndTime,TagState,HisSeqID,"
				+ "sysdate,'"+tagInfoHis.getCreateReason()+"' FROM CSMS_Tag_info WHERE tagNo=? ");
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(), backupTagNo);
	}

	public void saveTagMainRecordHis(TagMainRecordHis tmr, Long tagMainId) {
		StringBuffer sql = new StringBuffer(
				"insert into csms_tagmain_recordhis(ID,TagInfoID,TagNo,CLIENTID,VEHICLEID,MaintainType,Installman,ChargeCost,Reason,"
				+ "Memo,FAULTTYPE,RECEIVEPLACE,BACKUPTAGNO,CONTACTMAN,CONTACTPHONE,POSTCODE,ADDRESS,INVOICEHEAD,RECOVERTIME,SENDREPAIRTIME,"
				+ "REPAIRBACKTIME,NOTICECUSTOMERTIME,OperID,IssueplaceID,Issuetime,HisSeqID,"
				+ "CreateDate,CreateReason )"
				+ "SELECT "+tmr.getId()+",TagInfoID,TagNo,CLIENTID,VEHICLEID,MaintainType,Installman,"
				+ "ChargeCost,Reason,Memo,FAULTTYPE,RECEIVEPLACE,BACKUPTAGNO,CONTACTMAN,CONTACTPHONE,POSTCODE,ADDRESS,INVOICEHEAD,RECOVERTIME,SENDREPAIRTIME,"
				+ "REPAIRBACKTIME,NOTICECUSTOMERTIME,OperID,IssueplaceID,"
				+ "Issuetime,HisSeqID,"
				+ "sysdate,'"+tmr.getCreateReason()+"' FROM CSMS_TAGMAIN_RECORD WHERE id=? ");
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(), tagMainId);
	}

	public void updateMaintainBackToCustomerTime(TagMainRecord temp) {
		StringBuffer sql = new StringBuffer("update CSMS_TAGMAIN_RECORD set HisSeqID="+temp.getHisSeqID()+", BackOperID=?, BackToCustomerTime=sysdate ");
		sql.append(" where id =? ");
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(),temp.getBackOperID(),temp.getId());
	}

	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		
		StringBuffer sql=new StringBuffer("update CSMS_TAGMAIN_RECORD set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
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

	
}
