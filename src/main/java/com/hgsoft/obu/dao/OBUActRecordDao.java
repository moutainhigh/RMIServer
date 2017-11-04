package com.hgsoft.obu.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.entity.OBUActDetail;
import com.hgsoft.obu.entity.OBUActRecord;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class OBUActRecordDao extends BaseDao{

	public Pager findByPager(Pager pager,OBUActDetail obuActDetail,OBUActRecord obuActRecord,Date startTime,Date endTime) {
		// TODO Auto-generated method stub
		/*StringBuffer sql =new StringBuffer("select t.ID,t.ActivateCardNo,t.OperID,t.OperName,t.OperNo,t.MakeDate,t.Memo,t.WritebackFlag,t.WritebackOperName,"
				+ "t.WritebackTime,t.WritebackOperID,t.HisSeqID,s.id sid,s.MainID,"
				+ "s.VehiclePlate,s.TagNo,s.ImportState,s.Memo as me,ROWNUM  as num  from csms_obuact_record t join csms_obuact_detail s on t.id=s.mainId where 1=1 ");*/
		
		StringBuffer sql =new StringBuffer("select t.ID,t.ActivateCardNo,t.OperID,t.OperName,t.OperNo,t.MakeDate,t.Memo,t.WritebackFlag,t.WritebackOperName,"
				+ "t.WritebackTime,t.WritebackOperID,t.HisSeqID,ROWNUM  as num  from csms_obuact_record t where 1=1 ");
		
		SqlParamer params=new SqlParamer();
		if(obuActRecord.getId()!=null){
			params.eq("t.ID", obuActRecord.getId());
		}
		if(startTime !=null){
			params.geDate("t.MakeDate", params.getFormat().format(startTime));
		}
		if(endTime !=null){
			params.leDate("t.MakeDate", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(obuActRecord.getActivateCardNo())){
			params.eq("t.ActivateCardNo", obuActRecord.getActivateCardNo());
		}
		/*if(StringUtil.isNotBlank(obuActDetail.getTagNo())){
			params.eq("s.TagNo",obuActDetail.getTagNo());
		}
		if(StringUtil.isNotBlank(obuActDetail.getVehiclePlate())){
			params.eq("s.VehiclePlate", obuActDetail.getVehiclePlate());
		}*/
		
		if(StringUtil.isNotBlank(obuActRecord.getOperName())){
			params.eq("t.OperName", obuActRecord.getOperName());
		}
		if(StringUtil.isNotBlank(obuActRecord.getWritebackFlag())){
			params.eq("t.WritebackFlag", obuActRecord.getWritebackFlag());
		}
		if(StringUtil.isNotBlank(obuActRecord.getSystemType())){
			if("2".equals(obuActRecord.getSystemType())){
				params.eq("t.systemtype", obuActRecord.getSystemType());
			}else {
				params.ne("t.systemtype", "2");
			}
		}
		if(StringUtil.isNotBlank(obuActRecord.getWritebackOperName())){
			params.eq("t.WritebackOperName", obuActRecord.getWritebackOperName());
		}
		sql.append(params.getParam());
		sql.append(" order by t.MakeDate desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public void save(OBUActRecord obuActRecord) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_OBUAct_Record(");
		sql.append(FieldUtil.getFieldMap(OBUActRecord.class,obuActRecord).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(OBUActRecord.class,obuActRecord).get("valueStr")+")");
		save(sql.toString());*/
		obuActRecord.setHisSeqID(-obuActRecord.getId());
		Map map = FieldUtil.getPreFieldMap(OBUActRecord.class,obuActRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_OBUAct_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}


	public void update(OBUActRecord obuActRecord) {
		/*StringBuffer sql=new StringBuffer("update CSMS_OBUAct_Record set ");
		sql.append(FieldUtil.getFieldMap(OBUActRecord.class,obuActRecord).get("nameAndValue")+" where id="+obuActRecord.getId());
		update(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(OBUActRecord.class,obuActRecord);
		StringBuffer sql=new StringBuffer("update CSMS_OBUAct_Record set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),obuActRecord.getId());
	}
	
	public List<Map<String, Object>> obuActRecordList(OBUActRecord obuActRecord) {
		/*String sql ="select t.ID,t.ActivateCardNo,t.OperID,t.MakeDate,t.Memo,t.WritebackFlag,"
				+ "t.WritebackTime,t.WritebackOperID,t.HisSeqID,s.id sid,s.MainID,"
				+ "s.VehiclePlate,s.TagNo,s.ImportState,s.Memo as me,row_number() over (order by t.MakeDate desc)  as num  from csms_obuact_record t join csms_obuact_detail s on t.id=s.mainId where 1=1 ";*/
		String sql ="select * from CSMS_OBUAct_Record t where 1=1 ";
		SqlParamer params=new SqlParamer();
		if(obuActRecord.getId()!=null){
			params.eq("t.ID", obuActRecord.getId());
		}
		if(StringUtil.isNotBlank(obuActRecord.getActivateCardNo())){
			params.eq("t.ActivateCardNo", obuActRecord.getActivateCardNo());
		}
		
		if(obuActRecord.getOperID()!=null){
			params.eq("t.OperID", obuActRecord.getOperID());
		}
		if(StringUtil.isNotBlank(obuActRecord.getWritebackFlag())){
			params.eq("t.WritebackFlag", obuActRecord.getWritebackFlag());
		}
		if(StringUtil.isNotBlank(obuActRecord.getSystemType())){
			if("2".equals(obuActRecord.getSystemType())){
				params.eq("t.systemtype", obuActRecord.getSystemType());
			}else {
				params.ne("t.systemtype", "2");
			}
		}
		if (StringUtils.isBlank(params.getParam())) {
			throw new ApplicationException("条件为空");
		}
		sql=sql+params.getParam();
		sql = sql+" order by makedate desc ";
		Object[] Objects= params.getList().toArray();
		return queryList(sql, Objects);
	}
	
	public OBUActRecord find(OBUActRecord obuActRecord) {
		if (obuActRecord == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(OBUActRecord.class,obuActRecord);
		String condition = (String) map.get("selectNameStrNotNull");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("OBUActRecordDao.find条件为空");
		}

		StringBuffer sql = new StringBuffer("select * from CSMS_OBUAct_Record ");
		sql.append(condition);
		sql.append(" order by id");
		List<OBUActRecord> obuActRecords = super.queryObjectList(sql.toString(), OBUActRecord.class, ((List) map.get("paramNotNull")).toArray());
		if (obuActRecords == null || obuActRecords.isEmpty()) {
			return null;
		}
		return obuActRecords.get(0);
	}
	
	public OBUActRecord findById(Long id) {
		String sql = "select * from CSMS_OBUAct_Record where id=? ";
		List<OBUActRecord> obuActRecords = super.queryObjectList(sql, OBUActRecord.class, id);
		if (obuActRecords == null || obuActRecords.isEmpty()) {
			return null;
		}
		return obuActRecords.get(0);
	}
	
	public OBUActRecord findByActivateCardNo(String activateCardNo,OBUActRecord temp) {
		List<OBUActRecord> list = null;
		if(temp.getSystemType()==null){
			String sql = "select * from CSMS_OBUAct_Record where ActivateCardNo=? and WritebackFlag=? and systemtype!=2 order by WritebackTime desc";
			list = super.queryObjectList(sql, OBUActRecord.class, activateCardNo,temp.getWritebackFlag());
		}else{
			String sql = "select * from CSMS_OBUAct_Record where ActivateCardNo=? and WritebackFlag=? and systemtype=? order by WritebackTime desc";
			list = super.queryObjectList(sql, OBUActRecord.class, activateCardNo,temp.getWritebackFlag(),temp.getSystemType());
		}
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	public List<Map<String,Object>> getTagNoPlate(List<Map<String,String>> cardInfos){
		String sql="select t.tagNo,v.vehicleplate from csms_tag_info t inner join csms_carobucard_info c on c.tagid=t.id inner join csms_vehicle_info v on v.id=c.vehicleId where  t.tagNo in( ";
		sql=sql+"'"+cardInfos.get(0).get("tagNo")+"'";
		for(int i=1;i<cardInfos.size();i++){
			sql=sql+","+"'"+cardInfos.get(i).get("tagNo")+"'";
		}
		sql=sql+")";
		List<Map<String, Object>> list = queryList(sql);
		return list;
	}
}
