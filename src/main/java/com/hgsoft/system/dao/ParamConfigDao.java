package com.hgsoft.system.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.system.entity.SpecialCostSubclass;
import com.hgsoft.system.entity.SpecialCostType;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
@SuppressWarnings("rawtypes")
public class ParamConfigDao extends BaseDao{
	
	public void save(ParamConfig paramConfig) {
		Map map = FieldUtil.getPreFieldMap(ParamConfig.class,paramConfig);
		StringBuffer sql=new StringBuffer("insert into CSMS_param_config");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	
	public ParamConfig find(ParamConfig paramConfig) {
		ParamConfig temp = null;
		if (paramConfig != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_param_config where 1=1 ");
			Map map = FieldUtil.getPreFieldMap(ParamConfig.class,paramConfig);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List)map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new ParamConfig();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public List<ParamConfig> findParamOrderByValue(String paramNo){
		String sql=null;
		List<Map<String, Object>> l = null;
		if(paramNo!=null){
			sql="select * from CSMS_param_config where paramNo=? and state = 1 ORDER BY PARAMVALUE DESC";
			l = queryList(sql, paramNo);
		}else{
			sql="select * from CSMS_param_config where state = 1 and paramlevel=1 ORDER BY PARAMVALUE DESC";
			l = queryList(sql);
		}
		ParamConfig temp = null;
		List<ParamConfig> list=new ArrayList<ParamConfig>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new ParamConfig();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
	}

	public ParamConfig findById(Long id) {
		ParamConfig temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_param_config where id=? ");
		List<Map<String, Object>> list = queryList(sql.toString(),id);
		if (!list.isEmpty()&&list.size()==1) {
			temp = new ParamConfig();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	public List<ParamConfig> findByparamNo(String paramNo){
		String sql=null;
		List<Map<String, Object>> l = null;
		if(paramNo!=null){
			sql="select * from CSMS_param_config where paramNo=? and state = 1 order by createtime";
			l = queryList(sql, paramNo);
		}else{
			sql="select * from CSMS_param_config where state = 1 and paramlevel=1 order by createtime";
			l = queryList(sql);
		}
		ParamConfig temp = null;
		List<ParamConfig> list=new ArrayList<ParamConfig>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new ParamConfig();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		
	}
	
	public List<ParamConfig> findByparamNo(ParamConfig paramConfig){
		String sql="select * from CSMS_param_config where paramId=? and state = 1";
		List<Map<String, Object>> l = queryList(sql, paramConfig.getParamId());
		ParamConfig temp = null;
		List<ParamConfig> list=new ArrayList<ParamConfig>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new ParamConfig();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		
	}
	
	public List<ParamConfig> findByparamNoAndBankNo(String paramNo,String bankNo){
		String sql="select * from CSMS_param_config where paramNo=? and paramValueTwice = ? and state = 1";
		List<Map<String, Object>> l = queryList(sql, paramNo,bankNo);
		ParamConfig temp = null;
		List<ParamConfig> list=new ArrayList<ParamConfig>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new ParamConfig();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		
	}


	public Pager findByPager(Pager pager, ParamConfig paramConfig) {
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select * from csms_param_config where 1=1 ");
		if(StringUtil.isNotBlank(paramConfig.getParamNo())){
			params.eq("ParamNo", paramConfig.getParamNo());
		}
		sql.append(params.getParam());
		sql.append(" order by id desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public void update(ParamConfig paramConfig) {
		Map map = FieldUtil.getPreFieldMap(ParamConfig.class,paramConfig);
		StringBuffer sql=new StringBuffer("update csms_param_config set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),paramConfig.getId());
	}
	
	public void delete(ParamConfig paramConfig) {
		String sql="delete from  csms_param_config where id=?";
		this.jdbcUtil.getJdbcTemplate().update(sql, paramConfig.getId());
	}


	public List<SpecialCostType> findAllTypes() {
		String sql="select sct.* from OMS_specialCostType sct  where sct.flag=2 and sct.state=1";
		List<Map<String, Object>> l = queryList(sql);
		SpecialCostType temp = null;
		List<SpecialCostType> list=new ArrayList<SpecialCostType>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new SpecialCostType();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		 
	}


	public List<SpecialCostSubclass> findByFatherId(Long id) {
		 
		String sql="select * from OMS_SPECIALCOSTSUBCLASS  where flag=2 and state=1 and specialCostType = ? ";
		List<Map<String, Object>> l = queryList(sql,id);
		SpecialCostSubclass temp = null;
		List<SpecialCostSubclass> list=new ArrayList<SpecialCostSubclass>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new SpecialCostSubclass();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
	}

	/**
	 * 根据ID找SpecialCostType
	 * @param id
	 * @return
	 */
	public SpecialCostType findSpecialCostTypeById(Long id){
		String sql = "select " + FieldUtil.getFieldMap(SpecialCostType.class,new SpecialCostType()).get("nameStr") + " from CSMS_specialCostType where id = ?";
		List<SpecialCostType> list = super.queryObjectList(sql,SpecialCostType.class,id);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return new SpecialCostType();
	}

	/**
	 * 根据ID找SpecialCostSubclass
	 * @param id
	 * @return
	 */
	public SpecialCostSubclass findSpecialCostSubclassById(Long id){
		String sql = "select " + FieldUtil.getFieldMap(SpecialCostSubclass.class,new SpecialCostSubclass()).get("nameStr") + " from CSMS_specialCostSubclass where id = ?";
		List<SpecialCostSubclass> list = super.queryObjectList(sql,SpecialCostSubclass.class,id);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return new SpecialCostSubclass();
	}
}
