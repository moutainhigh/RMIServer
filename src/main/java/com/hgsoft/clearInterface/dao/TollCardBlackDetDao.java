/*package com.hgsoft.clearInterface.dao;

import org.springframework.stereotype.Component;

import com.hgsoft.clearInterface.entity.TollCardBlackDet;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Component
public class TollCardBlackDetDao extends BaseDao {
	public void save(TollCardBlackDet tollCardBlackDet) {
		StringBuffer sql=new StringBuffer("insert into CSMS_TollCardBlackDet(");
		sql.append(FieldUtil.getFieldMap(TollCardBlackDet.class,tollCardBlackDet).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(TollCardBlackDet.class,tollCardBlackDet).get("valueStr")+")");
		save(sql.toString());
	}
}
*/