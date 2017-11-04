package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.macao.entity.CarUserCardInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
/**
 * 澳门通车辆、用户、卡片关联表
 * @author guanshaofeng
 * @date 2016年11月7日
 */
@Repository
public class CarUserCardInfoDao extends BaseDao{
	public void save(CarUserCardInfo carUserCardInfo) {
		Map map = FieldUtil.getPreFieldMap(CarUserCardInfo.class,carUserCardInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_CarUserCard_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	/**
	 * 根据条件查找车用户卡片绑定表对象
	 * @param carUserCardInfo  (条件set进该对象)
	 * @return CarUserCardInfo
	 */
	public CarUserCardInfo find(CarUserCardInfo carUserCardInfo) {
		CarUserCardInfo temp = null;
		if (carUserCardInfo != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_CarUserCard_info ");
			Map map = FieldUtil.getPreFieldMap(CarUserCardInfo.class,carUserCardInfo);
			sql.append(map.get("selectNameStrNotNull"));
			System.out.println(sql.toString());
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new CarUserCardInfo();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	/**
	 * 根据车辆ID查找
	 * @param carUserCardInfo  (条件set进该对象)
	 * @return CarUserCardInfo
	 */
	public CarUserCardInfo findByVehicleId(Long id) {
		CarUserCardInfo temp = null;
			StringBuffer sql = new StringBuffer("select * from CSMS_CarUserCard_info where vehicleId="+id);
			System.out.println(sql.toString());
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()) {
				temp = new CarUserCardInfo();
				this.convert2Bean(list.get(0), temp);
			}

		return temp;
	}
	
	/**
	 * 根据车辆ID查找
	 * @param carUserCardInfo  (条件set进该对象)
	 * @return CarUserCardInfo
	 */
	public CarUserCardInfo findByAccountCId(Long id) {
		CarUserCardInfo temp = null;
			StringBuffer sql = new StringBuffer("select * from CSMS_CarUserCard_info where ACCOUNTCID="+id);
			System.out.println(sql.toString());
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()) {
				temp = new CarUserCardInfo();
				this.convert2Bean(list.get(0), temp);
			}

		return temp;
	}
	
	
	public void updateCarUserCardInfo(CarUserCardInfo carUserCardInfo) {
		Map map = FieldUtil.getPreFieldMap(CarUserCardInfo.class,carUserCardInfo);
		StringBuffer sql=new StringBuffer("update CSMS_CarUserCard_info set ");
		sql.append(map.get("updateNameStr") +" where vehicleId = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),carUserCardInfo.getVehicleId());
	}
	
	/**
	 * 根据电子标签查找绑定关系
	 * @param id
	 * @return CarUserCardInfo
	 */
	public CarUserCardInfo findByTagid(Long id){
		CarUserCardInfo temp = null;
			StringBuffer sql = new StringBuffer("select * from CSMS_CarUserCard_info where tagid="+id);
			System.out.println(sql.toString());
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()) {
				temp = new CarUserCardInfo();
				this.convert2Bean(list.get(0), temp);
			}

		return temp;
	}
	
	public int updateAccountID(Long newAccountCId, Long oldAccountCId) {
		StringBuffer sql=new StringBuffer("update csms_carObuCard_info "
				+ "set AccountCID = " +newAccountCId +" where AccountCID="+oldAccountCId);
		return this.jdbcUtil.update(sql.toString());
		//update(sql.toString());
	}
}
