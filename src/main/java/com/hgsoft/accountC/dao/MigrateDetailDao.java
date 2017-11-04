package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MigrateDetailDao extends BaseDao{

	public int[] batchSaveMigrateDetail(final List<MigrateDetail> list) {  
        String sql = "insert into CSMS_Migrate_detail(ID,MigrateID,cardNo,OperID,PlaceID,OperName,OperNo,PlaceName,PlaceNo,OperTime) values(SEQ_CSMSMigratedetail_NO.nextval,?,?,?,?,?,?,?,?,sysdate)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				MigrateDetail migrateDetail = list.get(i);
				ps.setLong(1, migrateDetail.getMigrateId());
				ps.setString(2, migrateDetail.getCardNo());
				ps.setLong(3, migrateDetail.getOperId());
				ps.setLong(4, migrateDetail.getPlaceId());
				ps.setString(5, migrateDetail.getOperName());
				ps.setString(6, migrateDetail.getOperNo());
				ps.setString(7, migrateDetail.getPlaceName());
				ps.setString(8, migrateDetail.getPlaceNo());
				
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }  
	
	
	public Pager findByPager(Pager pager,MigrateDetail migrateDetail) {
		StringBuffer sql=new StringBuffer("select md.ID,md.MigrateID,"
				+ " md.cardNo,md.OperID,md.PlaceID,md.OperTime,ROWNUM as num  " 
				+ " from csms_migrate_detail md where 1=1 ");
		SqlParamer params=new SqlParamer();
		if(migrateDetail.getMigrateId()!=null){
			params.eq("md.MigrateID",migrateDetail.getMigrateId());
		}
		if(StringUtil.isNotBlank(migrateDetail.getCardNo())){
			params.eq("md.cardNo",migrateDetail.getCardNo());
		}
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by OperTime ");
		return this.findByPages(sql.toString(), pager,Objects);
		
	}


	public List<Map<String, Object>> find(MigrateDetail migrateDetail) {
		List<Map<String, Object>> list = null;
		StringBuffer sql=new StringBuffer("select md.ID,md.MigrateID,"
				+ " md.cardNo,md.OperID,md.PlaceID,md.OperTime  " 
				+ " from csms_migrate_detail md where MigrateID=? order by OperTime desc");
		list = queryList(sql.toString(),migrateDetail.getMigrateId());
		/*if(migrateDetail.getMigrateId()!=null){
			sql.append(" and MigrateID=?");
			sql.append(" order by OperTime desc ");
			list = queryList(sql.toString(),migrateDetail.getMigrateId());
		}else{
			sql.append(" order by OperTime desc ");
			list = queryList(sql.toString());
		}*/
		return list;
	}
}
