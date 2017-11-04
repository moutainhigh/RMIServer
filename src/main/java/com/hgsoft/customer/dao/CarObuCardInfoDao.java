package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CarObuCardInfo;

@Repository
public class CarObuCardInfoDao extends BaseDao{

	public CarObuCardInfo findByPrepaidCNo(String cardNo) {
		String sql="select coc.PrePaidCID,coc.AccountCID,coc.VehicleID,coc.TagID from CSMS_CarObuCard_info coc join csms_prepaidc p on coc.prepaidcid=p.id where p.cardno=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, cardNo);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	
	public CarObuCardInfo findByTagid(Long id){
		String sql="select PrePaidCID,AccountCID,VehicleID,TagID from CSMS_CarObuCard_info where TagID=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, id);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	public CarObuCardInfo findByTagNo(String tagNo){
		String sql="select ci.PrePaidCID PrePaidCID,ci.AccountCID AccountCID,ci.VehicleID VehicleID,ci.TagID TagID from CSMS_CarObuCard_info ci join csms_tag_info ti on ti.id=ci.tagid where TagNo=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, tagNo);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	
	public CarObuCardInfo findByPrepaidCID(Long id){
		String sql="select PrePaidCID,AccountCID,VehicleID,TagID from CSMS_CarObuCard_info where PrepaidCID=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, id);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	
	public CarObuCardInfo findByAccountCID(Long id){
		String sql="select PrePaidCID,AccountCID,VehicleID,TagID from CSMS_CarObuCard_info where AccountCID=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, id);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	public CarObuCardInfo findByVehicleID(Long id){
		String sql="select PrePaidCID,AccountCID,VehicleID,TagID from CSMS_CarObuCard_info where VehicleID=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, id);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	
	/**
	 * 根据储值卡ID删除车卡标签关联关系
	 * @param prepaidCID
	 */
	/*public void delete(Long prepaidCID){
		String sql = "delete from CSMS_CarObuCard_Info where PrepaidCID=" + prepaidCID;

		delete(sql);
	}*/
	
	public void update(CarObuCardInfo carObuCardInfo) {
		StringBuffer sql=new StringBuffer("update csms_carObuCard_info set ");
		sql.append(FieldUtil.getFieldMap(CarObuCardInfo.class,carObuCardInfo).get("nameAndValue")+" where vehicleID="+carObuCardInfo.getVehicleID());
		update(sql.toString());
	}
	
	public void updateTagNo(CarObuCardInfo carObuCardInfo) {
		StringBuffer sql=new StringBuffer("update csms_carObuCard_info "
				+ "set tagID = " +carObuCardInfo.getTagID() +" where vehicleID="+carObuCardInfo.getVehicleID());
		update(sql.toString());
	}
	
	public int updateAccountID(Long newAccountCId, Long oldAccountCId) {
		StringBuffer sql=new StringBuffer("update csms_carObuCard_info "
				+ "set AccountCID = " +newAccountCId +" where AccountCID="+oldAccountCId);
		return this.jdbcUtil.update(sql.toString());
		//update(sql.toString());
	}
	
	public int updatePerPaidCID(Long newPerPaidCID, Long oldPerPaidCID) {
		StringBuffer sql=new StringBuffer("update csms_carObuCard_info "
				+ "set prePaidCID = " +newPerPaidCID +" where prepaidCID="+oldPerPaidCID);
		return this.jdbcUtil.update(sql.toString());
		//update(sql.toString());
	}
	
	public void deleteByTagNo(String tagNo){
		String sql = "delete from CSMS_CarObuCard_Info where TagID=?" ;
		this.jdbcUtil.getJdbcTemplate().update(sql, tagNo);
	}
	
	public void save(CarObuCardInfo carObuCardInfo) {
		StringBuffer sql=new StringBuffer("insert into csms_CarObuCard_info(");
		sql.append(FieldUtil.getFieldMap(CarObuCardInfo.class,carObuCardInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(CarObuCardInfo.class,carObuCardInfo).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void deleteByVehicleId(Long id){
		String sql = "delete from CSMS_CarObuCard_Info where VehicleId="+id ;
		super.delete(sql);
	}

	public void relieveBind(Long vid) {
		String sql = "update csms_carobucard_info set prepaidcid=null,accountcid=null,tagid=null where vehicleid="+vid;
		update(sql.toString());
	}
	
	public int[] batchUpdateCardObu(final List<String> cardNoList) {
		StringBuffer sql=new StringBuffer("update csms_carobucard_info set prepaidcid=null where prepaidCId in (select ID from csms_prepaidc where cardNo=?)");
    	return batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, cardNoList.get(i));
			}
			@Override
			public int getBatchSize() {
				 return cardNoList.size();
			}
		});
    }

	public int[] batchUpdate(final List<CarObuCardInfo> list) {  
        String sql = "update csms_carobucard_info set accountcid = null where accountcid = ?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				CarObuCardInfo carObuCardInfo = list.get(i);
				ps.setLong(1, carObuCardInfo.getAccountCID());
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	public CarObuCardInfo getCarObuCardInfo(String vehiclePlate) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select coc.* from csms_carobucard_info coc join csms_vehicle_info vi on coc.vehicleid=vi.id where vi.vehicleplate=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, vehiclePlate);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}
	
	public CarObuCardInfo getCarObuCardInfo(Long vehicleId) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select coc.* from csms_carobucard_info coc where coc.vehicleId=?";
		List<CarObuCardInfo> carObuCardInfos = super.queryObjectList(sql, CarObuCardInfo.class, vehicleId);
		if (carObuCardInfos == null || carObuCardInfos.isEmpty()) {
			return null;
		}
		return carObuCardInfos.get(0);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> findByCustomerId(Long id){
		StringBuffer sql = new StringBuffer("select decode(c.cardno,null,d.cardNo,c.cardNo) as cardNo,e.tagno,a.vehicleplate,a.vehiclecolor ");
		sql.append("from csms_vehicle_info a ");
		sql.append("left join csms_carobucard_info b on a.id = b.vehicleId ");
		sql.append("left join csms_prepaidc c on b.prepaidcid = c.id ");
		sql.append("left join Csms_Accountc_Info d on b.accountcid = d.id ");
		sql.append("left join csms_tag_info e on b.tagid = e.id ");
		sql.append("where a.customerid = ?");
		return super.queryList(sql.toString(),id);
	}
}
