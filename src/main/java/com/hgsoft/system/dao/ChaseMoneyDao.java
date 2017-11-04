package com.hgsoft.system.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.ChaseMoneyInfo;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;

@Repository
@SuppressWarnings("rawtypes")
public class ChaseMoneyDao extends BaseDao{
	
	public Pager findByPager(Pager pager, ChaseMoneyInfo chaseMoneyInfo) {
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select * from csms_chasemoney where 1=1 ");
		if(chaseMoneyInfo.getCustomerId() != null){
			params.eq("customerid", chaseMoneyInfo.getCustomerId());
		}
		sql.append(params.getParam());
		sql.append(" order by finish_flag asc, updatetime desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
		
		/*SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select * from csms_chasemoney where 1=1 ");
		if(chaseMoneyInfo.getCustomerId() != null){
			params.eq("customerid", chaseMoneyInfo.getCustomerId());
		}
		sql.append(params.getParam());
		sql.append(" order by finish_flag asc, updatetime desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);*/
	}
	
	public ChaseMoneyInfo findById(Long id) {
		ChaseMoneyInfo temp = null;
		StringBuffer sql = new StringBuffer("select * from csms_chasemoney where id=? ");
		List<Map<String, Object>> list = queryList(sql.toString(),id);
		if (!list.isEmpty()&&list.size()==1) {
			temp = new ChaseMoneyInfo();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	public void update(ChaseMoneyInfo chaseMoneyInfo) {
		Map map = FieldUtil.getPreFieldMap(ChaseMoneyInfo.class,chaseMoneyInfo);
		StringBuffer sql=new StringBuffer("update csms_chasemoney set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),chaseMoneyInfo.getId());
	}

}
