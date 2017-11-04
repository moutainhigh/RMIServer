package com.hgsoft.macao.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.MacaoCardSection;
@Repository
public class MacaoCardSectionDao extends BaseDao{
	private static Logger logger = Logger.getLogger(MacaoCardSectionDao.class.getName());
	
	public List findMacaoCardSection(String cardNo){
		String sql = "select "+FieldUtil.getFieldMap(MacaoCardSection.class, new MacaoCardSection()).get("nameStr")
				+ " from CSMS_macaoCardSection where cardType='23' and (? between StartCode and EndCode) ";
		logger.debug("sql:"+sql);
		List<Map<String, Object>> list = queryList(sql,cardNo);
		return list;
	}
}
