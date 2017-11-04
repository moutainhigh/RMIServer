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
import com.hgsoft.macao.entity.CardHolderInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
/**
 * 澳门通持卡人绑定关系表
 * @author guanshaofeng
 * @date 2016年11月7日
 */
@Repository
public class CardHolderInfoDao extends BaseDao{
	
	public void save(CardHolderInfo cardHolderInfo) {
		Map map = FieldUtil.getPreFieldMap(CardHolderInfo.class,cardHolderInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_CARDHOLDER_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	/**
	 * 根据电子标签查找
	 * @param tagId
	 * @return CardHolderInfo
	 */
	public CardHolderInfo findByTagId(Long tagId){
		CardHolderInfo temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_CARDHOLDER_INFO where Typeid=? and type='3'" );
		System.out.println(sql.toString());
		List<Map<String, Object>> list = queryList(sql.toString(),tagId);
		if (!list.isEmpty()) {
			temp = new CardHolderInfo();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	/**
	 * 根据车辆id查找
	 * @param tagId
	 * @return CardHolderInfo
	 */
	public CardHolderInfo findByVehicle(Long vehicleId){
		CardHolderInfo temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_CARDHOLDER_INFO where Typeid=? and type='1'" );
		System.out.println(sql.toString());
		List<Map<String, Object>> list = queryList(sql.toString(),vehicleId);
		if (!list.isEmpty()) {
			temp = new CardHolderInfo();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	/**
	 * 根据车辆id查找
	 * @param tagId
	 * @return CardHolderInfo
	 */
	public CardHolderInfo findByCardId(Long cardId){
		CardHolderInfo temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_CARDHOLDER_INFO where Typeid=? and type='2'" );
		System.out.println(sql.toString());
		List<Map<String, Object>> list = queryList(sql.toString(),cardId);
		if (!list.isEmpty()) {
			temp = new CardHolderInfo();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	public void update(CardHolderInfo cardHolderInfo) {
		Map map = FieldUtil.getPreFieldMap(CardHolderInfo.class,cardHolderInfo);
		StringBuffer sql=new StringBuffer("update CSMS_CARDHOLDER_INFO set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),cardHolderInfo.getId());
	}
	
}
