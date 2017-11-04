package com.hgsoft.system.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.SaleType;
import com.hgsoft.system.entity.SaleTypeDetail;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
@SuppressWarnings("rawtypes")
public class SaleTypeDetailDao extends BaseDao{

	
	public void save(SaleTypeDetail saleTypeDetail) {
		Map map = FieldUtil.getPreFieldMap(SaleTypeDetail.class,saleTypeDetail);
		StringBuffer sql=new StringBuffer("insert into CSMS_SALE_TYPE_DETAIL");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	
	public SaleTypeDetail find(SaleTypeDetail saleTypeDetail) {
		SaleTypeDetail temp = null;
		if (saleTypeDetail != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_SALE_TYPE_DETAIL where 1=1 ");
			Map map = FieldUtil.getPreFieldMap(SaleTypeDetail.class,saleTypeDetail);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List)map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new SaleTypeDetail();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	

	public SaleTypeDetail findById(Long id) {
		SaleTypeDetail temp = null;
		StringBuffer sql = new StringBuffer("select * from CSMS_SALE_TYPE_DETAIL where id=? ");
		List<Map<String, Object>> list = queryList(sql.toString(),id);
		if (!list.isEmpty()&&list.size()==1) {
			temp = new SaleTypeDetail();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	public List<SaleTypeDetail> findByparamNo(String paramNo){
		String sql=null;
		sql="select * from CSMS_SALE_TYPE_DETAIL where code=? and flag = 0";
		List<Map<String, Object>> l = queryList(sql, paramNo);
		SaleTypeDetail temp = null;
		List<SaleTypeDetail> list=new ArrayList<SaleTypeDetail>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new SaleTypeDetail();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		
	}
	
	public List<SaleTypeDetail> findBySaleTypeDetail(SaleTypeDetail saleTypeDetail){
		//参数取营运的数据库
		String sql="select * from CSMS_SALE_TYPE_DETAIL ";
		List<Map<String, Object>> l = null;
		if(saleTypeDetail!=null && StringUtil.isNotBlank(saleTypeDetail.getFlag())){
			sql = sql+"  where flag =? ";
			l = queryList(sql, saleTypeDetail.getFlag());
		}else{
			l = queryList(sql);
		}
		
		SaleTypeDetail temp = null;
		List<SaleTypeDetail> list=new ArrayList<SaleTypeDetail>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new SaleTypeDetail();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		
	}
	
	public List<SaleType> findBySaleType(SaleType saleType){
		//参数取营运的数据库
		StringBuffer sql=new StringBuffer("select * from OMS_saleType where 1=1 ");
		Map map = FieldUtil.getPreFieldMap(SaleType.class,saleType);
		sql.append(map.get("selectNameStrNotNullAndWhere"));
		List<Map<String, Object>> l = null;
		l = queryList(sql.toString(),((List)map.get("paramNotNull")).toArray());
		SaleType temp = null;
		List<SaleType> list=new ArrayList<SaleType>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new SaleType();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
		
	}
	public String findSaleTypeNanmeByCode(String code){
		//参数取营运的数据库
		StringBuffer sql=new StringBuffer("select * from OMS_saleType where code=?");
		List<Map<String, Object>> l = null;
		l = queryList(sql.toString(),code);
		SaleType temp = null;
		if (!l.isEmpty()&&l.size()>0) {
			temp = new SaleType();
			this.convert2Bean(l.get(0), temp);
			return temp.getName();
			}
		return null;
		
	}
	

	public Pager findByPager(Pager pager, SaleTypeDetail saleTypeDetail) {
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select * from CSMS_SALE_TYPE_DETAIL where 1=1 ");
		/*if(StringUtil.isNotBlank(paramConfig.getParamNo())){
			params.eq("ParamNo", paramConfig.getParamNo());
		}*/
		sql.append(params.getParam());
		sql.append(" order by id ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public void update(SaleTypeDetail saleTypeDetail) {
		Map map = FieldUtil.getPreFieldMap(SaleTypeDetail.class,saleTypeDetail);
		StringBuffer sql=new StringBuffer("update CSMS_SALE_TYPE_DETAIL set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),saleTypeDetail.getId());
	}

	/**
	 * 根据Code找销售方式
	 * @param code
	 * @return
	 */
	public SaleTypeDetail findByCode(String code){
		String sql = "select " + FieldUtil.getFieldMap(SaleTypeDetail.class,new SaleTypeDetail()).get("nameStr") + " from CSMS_SALE_TYPE_DETAIL where code = ? and flag = 0";
		List<SaleTypeDetail> list = queryObjectList(sql,SaleTypeDetail.class,code);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return new SaleTypeDetail();
	}
	
}
