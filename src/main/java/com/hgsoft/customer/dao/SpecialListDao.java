package com.hgsoft.customer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.SpecialList;
@Repository
public class SpecialListDao extends BaseDao{
	/**
	 * 根据客户id查找特殊名单
	 * @param customerId
	 * @return List<SpecialList>
	 */
	public List<SpecialList> findAllByCustomerId(Long customerId){
		String sql = "select "+FieldUtil.getFieldMap(SpecialList.class,new SpecialList()).get("nameStr")+" from CSMS_Special_list where  customerId=? " ;
		List<Map<String, Object>> list = queryList(sql,customerId);
		SpecialList specialList = null;
		List<SpecialList> specialLists = null;
		if (list.size() > 0) {
			specialLists = new ArrayList<SpecialList>();
			
			for (Map<String, Object> c : list) {
				specialList = new SpecialList();
				this.convert2Bean(c, specialList);
				specialLists.add(specialList);
			}
		}
		return specialLists;
	}
}
