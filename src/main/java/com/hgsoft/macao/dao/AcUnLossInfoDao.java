package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcLossInfo;
import com.hgsoft.macao.entity.AcUnLossInfo;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class AcUnLossInfoDao extends BaseDao{
	
	public void save(AcUnLossInfo acUnLossInfo) {
		Map map = FieldUtil.getPreFieldMap(AcUnLossInfo.class,acUnLossInfo);
		StringBuffer sql=new StringBuffer("insert into csms_acunloss_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AcUnLossInfo findAcUnLossInfo(){
		String sql = "select "+FieldUtil.getFieldMap(AcUnLossInfo.class, new AcUnLossInfo()).get("nameStr")+" from csms_acunloss_info  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		AcUnLossInfo acUnLossInfo = null;
		if (!list.isEmpty()) {
			acUnLossInfo = new AcUnLossInfo();
			this.convert2Bean(list.get(0), acUnLossInfo);
		}

		return acUnLossInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update csms_acunloss_info set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from csms_acunloss_info  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
}
