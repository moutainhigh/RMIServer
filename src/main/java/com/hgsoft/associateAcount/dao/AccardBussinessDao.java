package com.hgsoft.associateAcount.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.AccardBussiness;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Repository
public class AccardBussinessDao extends BaseDao{

	public void save(AccardBussiness accardBussiness) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_ACCARD_BUSSINESS(");
		sql.append(FieldUtil.getFieldMap(AccardBussiness.class,accardBussiness).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccardBussiness.class,accardBussiness).get("valueStr")+")");
		save(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(AccardBussiness.class,accardBussiness);
		StringBuffer sql=new StringBuffer("insert into CSMS_ACCARD_BUSSINESS");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AccardBussiness findById(Long id) {
		String sql = "select * from CSMS_ACCARD_BUSSINESS where id=? ";
		List<Map<String, Object>> list = queryList(sql,id);
		AccardBussiness accardBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			accardBussiness = new AccardBussiness();
			this.convert2Bean(list.get(0), accardBussiness);
		}

		return accardBussiness;
	}
	
	public void updatePrintTimes(Long id){
		StringBuffer sql=new StringBuffer(" update CSMS_ACCARD_BUSSINESS set receiptPrintTimes = (CASE  WHEN receiptPrintTimes IS NULL THEN 1 ELSE receiptPrintTimes+1 END) where id = ? ");
		saveOrUpdate(sql.toString(),id);
	}
}
