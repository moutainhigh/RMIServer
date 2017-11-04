package com.hgsoft.clearInterface.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.ClearBaseDao;
@Component
public class GbissueuserListDao extends ClearBaseDao {
	private static Logger logger = Logger.getLogger(GbissueuserListDao.class.getName());
	
	public boolean find(String license, String licenseColor) {
		String sql="select * from TB_GBISSUEUSERLIST where LICENSE='"+license+"' and LICENSECOLOR='"+licenseColor+"'";
		logger.debug("sql:"+sql);
		List<Map<String, Object>> list = queryList(sql.toString());
		if (list.isEmpty()|| list.size()==0) {
			return false;
		}
		return true;
	}
}
