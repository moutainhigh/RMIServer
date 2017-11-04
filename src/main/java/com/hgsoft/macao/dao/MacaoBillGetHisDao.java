package com.hgsoft.macao.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.MacaoBillGetHis;
@Repository
public class MacaoBillGetHisDao extends BaseDao{
	
	public void save(MacaoBillGetHis macaoBillGetHis) {
		Map map = FieldUtil.getPreFieldMap(MacaoBillGetHis.class,macaoBillGetHis);
		StringBuffer sql=new StringBuffer("insert into CSMS_MACAO_BILL_GET_HIS");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
}
