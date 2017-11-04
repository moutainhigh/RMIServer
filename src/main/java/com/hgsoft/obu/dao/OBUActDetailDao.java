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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class OBUActDetailDao extends BaseDao{

	public void save(OBUActDetail obuActDetail) {
		/*StringBuffer sql=new StringBuffer("insert into csms_obuact_detail(");
		sql.append(FieldUtil.getFieldMap(OBUActDetail.class,obuActDetail).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(OBUActDetail.class,obuActDetail).get("valueStr")+")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(OBUActDetail.class,obuActDetail);
		StringBuffer sql=new StringBuffer("insert into csms_obuact_detail");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}


	public void update(OBUActDetail obuActDetail) {
		/*StringBuffer sql=new StringBuffer("update csms_obuact_detail set ");
		sql.append(FieldUtil.getFieldMap(OBUActDetail.class,obuActDetail).get("nameAndValue")+" where id="+obuActDetail.getId());
		update(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(OBUActDetail.class,obuActDetail);
		StringBuffer sql=new StringBuffer("update csms_obuact_detail set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),obuActDetail.getId());
	}
	
	public List<Map<String, Object>> obuActRecordDetail(Long id) {
		String sql ="select t.ID,t.ActivateCardNo,t.OperID,t.MakeDate,t.Memo,t.WritebackFlag,t.OperName,t.OperNo,t.WritebackOperName,"
				+ "t.WritebackTime,t.WritebackOperID,t.HisSeqID,s.MainID,"
				+ "s.VehiclePlate,s.TagNo,s.ImportState,s.Memo as me from csms_obuact_record t join csms_obuact_detail s on t.id=s.mainId where s.ID=? ";
		
		return queryList(sql, id);
	}
	
	public OBUActDetail findByMainId(Long mainId) {
		String sql ="select ID,MainID,VehiclePlate,TagNo,ImportState,Memo from CSMS_OBUAct_Detail where t.MainID=? ";
		List<OBUActDetail> obuActDetails = super.queryObjectList(sql, OBUActDetail.class, mainId);
		if (obuActDetails == null || obuActDetails.isEmpty()) {
			return null;
		}
		return obuActDetails.get(0);
	}
	
	public int[] batchSaveOBUActDetail(final List<OBUActDetail> list) {  
        String sql = "insert into CSMS_OBUAct_Detail(ID,MainID,VehiclePlate,TagNo,ImportState,Memo) values(SEQ_CSMSOBUActDetail_NO.nextval,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				OBUActDetail obuActDetail = list.get(i);
				ps.setLong(1, obuActDetail.getMainID());
				ps.setString(2, obuActDetail.getVehiclePlate());
				ps.setString(3, obuActDetail.getTagNo());
				ps.setString(4, obuActDetail.getImportState());
				ps.setString(5, obuActDetail.getMemo());

			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
    }  
	
	public int[] batchUpdateOBUActDetail(final List<OBUActDetail> list,final OBUActRecord obuActRecord) {  
        String sql = "update CSMS_OBUAct_Detail set ImportState=? where TagNo=? and MainID=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				OBUActDetail obuActDetail = list.get(i);
				ps.setString(1, obuActDetail.getImportState());
				ps.setString(2, obuActDetail.getTagNo());
				ps.setLong(3, obuActRecord.getId());

			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
    } 
	
	
	public OBUActDetail find(OBUActDetail obuActDetail) {
		if (obuActDetail == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(OBUActDetail.class,obuActDetail);
		String condition = (String) map.get("selectNameStrNotNull");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("OBUActDetailDao.find条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_OBUAct_Detail ");
		sql.append(condition);
		sql.append(" order by id");
		List<OBUActDetail> obuActDetails = super.queryObjectList(sql.toString(), OBUActDetail.class, ((List) map.get("paramNotNull")).toArray());
		if (obuActDetails == null || obuActDetails.isEmpty()) {
			return null;
		}
		return obuActDetails.get(0);
	}
	
	public List<OBUActDetail> findList(OBUActDetail obuActDetail) {
		if (obuActDetail == null) {
			return new ArrayList<OBUActDetail>(0);
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_OBUAct_Detail where mainid=? and ImportState is null");
		sql.append(" order by id");
		return super.queryObjectList(sql.toString(), OBUActDetail.class, obuActDetail.getMainID());
	}
	public List<OBUActDetail> findListByMainId(OBUActDetail obuActDetail) {
		if (obuActDetail == null) {
			return new ArrayList<OBUActDetail>(0);
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_OBUAct_Detail where mainid=?");
		sql.append(" order by id");
		return super.queryObjectList(sql.toString(), OBUActDetail.class, obuActDetail.getMainID());
	}
	
	public Pager findByPager(Pager pager,OBUActDetail obuActDetail,OBUActRecord obuActRecord,Date startTime,Date endTime) {
		// TODO Auto-generated method stub
		StringBuffer sql =new StringBuffer("select s.id sid,s.MainID,"
				+ "s.VehiclePlate,s.TagNo,s.ImportState,s.Memo as me,ROWNUM  as num  from csms_obuact_detail s where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(obuActDetail.getTagNo())){
			params.eq("s.TagNo",obuActDetail.getTagNo());
		}
		if(StringUtil.isNotBlank(obuActDetail.getVehiclePlate())){
			params.eq("s.VehiclePlate", obuActDetail.getVehiclePlate());
		}
		if(StringUtil.isNotBlank(obuActDetail.getVehiclePlate())){
			params.eq("s.VehiclePlate", obuActDetail.getVehiclePlate());
		}
		if(obuActDetail.getMainID()!=null){
			params.eq("s.MainID", obuActDetail.getMainID());
		}
		sql.append(params.getParam());
		sql.append(" order by s.ImportState desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
}
