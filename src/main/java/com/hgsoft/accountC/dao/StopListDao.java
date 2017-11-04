package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.StopList;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Repository
public class StopListDao extends BaseDao {
	// TODO: 2017/4/21 这个方法没有业务调用,是否删除掉
	public StopList findByAcountId(Long id){
			String sql = "select "+FieldUtil.getFieldMap(StopList.class, new StopList()).get("nameStr")+" from CSMS_stop_list where flag='0' and  accountId="+id;
			List<Map<String, Object>> list = queryList(sql);
			StopList stopList = null;
			if (!list.isEmpty()) {
				stopList = new StopList();
				this.convert2Bean(list.get(0), stopList);
			}

			return stopList;
	}
}
