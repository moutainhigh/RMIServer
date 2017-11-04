package com.hgsoft.associateAcount.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;

@Repository
public class LianCardInfoDao extends BaseDao {

	public void save(LianCardInfo lianCardInfo) {
		/*StringBuffer sql = new StringBuffer("insert into csms_lian_card_info(");
		sql.append(FieldUtil.getFieldMap(LianCardInfo.class, lianCardInfo).get(
				"nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(LianCardInfo.class, lianCardInfo).get(
				"valueStr")
				+ ")");
		save(sql.toString());*/
		
		Map map = FieldUtil.getPreFieldMap(LianCardInfo.class,lianCardInfo);
		StringBuffer sql=new StringBuffer("insert into csms_lian_card_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public LianCardInfo find(LianCardInfo lianCardInfo) {
		LianCardInfo temp = null;
		if (lianCardInfo != null) {
			/*StringBuffer sql = new StringBuffer(
					"select * from csms_lian_card_info where 1=1 ");
			sql.append(FieldUtil.getFieldMap(LianCardInfo.class, lianCardInfo)
					.get("nameAndValueNotNull"));
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			
			
			StringBuffer sql = new StringBuffer("select * from csms_lian_card_info ");
			Map map = FieldUtil.getPreFieldMap(LianCardInfo.class,lianCardInfo);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new LianCardInfo();
				this.convert2Bean(list.get(0), temp);
			}
		}
		return temp;

	}

	public void update(LianCardInfo lianCardInfo) {
		StringBuffer sql = new StringBuffer("update csms_lian_card_info set ");
		sql.append(" memo = ?, ");
		sql.append(" sex = ?, ");
		sql.append(" idType = ?, ");
		sql.append(" idCode = ?, ");
		sql.append(" name = ?, ");
		sql.append(" emFlag = ?, ");
		sql.append(" nflag = ?, ");
		sql.append(" vehicleType = ?, ");
		sql.append(" hisseqid = ? ");
		sql.append(" where id= ? ");
		saveOrUpdate(sql.toString(),lianCardInfo.getMemo(),lianCardInfo.getSex(),lianCardInfo.getIdType(),
				lianCardInfo.getIdCode(),lianCardInfo.getName(),lianCardInfo.getEmFlag(),
				lianCardInfo.getNflag(),lianCardInfo.getVehicleType(),lianCardInfo.getHisseqId(),lianCardInfo.getId());
	}
}
