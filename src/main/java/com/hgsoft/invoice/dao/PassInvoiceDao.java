package com.hgsoft.invoice.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.invoice.entity.PassInvoice;
import com.hgsoft.utils.Pager;

@Repository
public class PassInvoiceDao extends BaseDao{
	
	public void save(PassInvoice passInvoice) {
		StringBuffer sql=new StringBuffer("insert into csms_pass_invoice");
		Map map = FieldUtil.getPreFieldMap(PassInvoice.class,passInvoice);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(PassInvoice.class,passInvoice).get("nameStr")+") values(");
		//sql.append(FieldUtil.getFieldMap(PassInvoice.class,passInvoice).get("valueStr")+")");
		//save(sql.toString());
	}

	public void delete(Long id) {
		String sql="delete from csms_pass_invoice where id=?";
		super.delete(sql,id);
	}

	public void update(PassInvoice passInvoice) {
		StringBuffer sql=new StringBuffer("update csms_pass_invoice set ");
		Map map = FieldUtil.getPreFieldMap(PassInvoice.class,passInvoice);
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),passInvoice.getId());
		//sql.append(FieldUtil.getFieldMap(PassInvoice.class,passInvoice).get("nameAndValue")+" where id="+passInvoice.getId());
		//update(sql.toString());
	}

	public PassInvoice findById(Long id) {
		String sql = "select * from csms_pass_invoice where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		PassInvoice passInvoice = null;
		if (!list.isEmpty()) {
			passInvoice = new PassInvoice();
			this.convert2Bean(list.get(0), passInvoice);
		}

		return passInvoice;
	}

	public List<Map<String, Object>> findAll(PassInvoice passInvoice) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from csms_pass_invoice where 1=1 ");
		if (passInvoice != null) {
			//sql.append(FieldUtil.getFieldMap(PassInvoice.class,passInvoice).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PassInvoice.class, passInvoice);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			return queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		} else{
			return queryList(sql.toString());
		}
	}

	public Pager findByPage(Pager pager,PassInvoice passInvoice) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_pass_invoice t where 1=1");
		if (passInvoice != null) {
			//sql.append(FieldUtil.getFieldMap(PassInvoice.class,passInvoice).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PassInvoice.class, passInvoice);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			return findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else{
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,null);
		}
	}

	public PassInvoice find(PassInvoice passInvoice) {
		PassInvoice temp = null;
		if (passInvoice != null) {
			StringBuffer sql = new StringBuffer("select * from csms_pass_invoice where 1=1 ");
			//sql.append(FieldUtil.getFieldMap(PassInvoice.class,passInvoice).get("nameAndValueNotNull"));
			//sql.append(" order by id");
			//List<Map<String, Object>> list = queryList(sql.toString());
			Map map = FieldUtil.getPreFieldMap(PassInvoice.class, passInvoice);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new PassInvoice();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

}
