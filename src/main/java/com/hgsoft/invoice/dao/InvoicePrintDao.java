package com.hgsoft.invoice.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.invoice.entity.InvoicePrint;
import com.hgsoft.utils.Pager;

@Repository
public class InvoicePrintDao extends BaseDao{
	
	public void save(InvoicePrint invoicePrint) {
		StringBuffer sql=new StringBuffer("insert into csms_invoice_print");
		Map map = FieldUtil.getPreFieldMap(InvoicePrint.class,invoicePrint);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(InvoicePrint.class,invoicePrint).get("nameStr")+") values(");
		//sql.append(FieldUtil.getFieldMap(InvoicePrint.class,invoicePrint).get("valueStr")+")");
		//save(sql.toString());
	}

	public void delete(Long id) {
		String sql="delete from csms_invoice_print where id=?";
		super.delete(sql,id);
	}

	public void update(InvoicePrint invoicePrint) {
		StringBuffer sql=new StringBuffer("update csms_invoice_print set ");
		Map map = FieldUtil.getPreFieldMap(InvoicePrint.class,invoicePrint);
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),invoicePrint.getId());
		//sql.append(FieldUtil.getFieldMap(InvoicePrint.class,invoicePrint).get("nameAndValue")+" where id="+invoicePrint.getId());
		//update(sql.toString());
	}

	public InvoicePrint findById(Long id) {
		String sql = "select * from csms_invoice_print where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		InvoicePrint invoicePrint = null;
		if (!list.isEmpty()) {
			invoicePrint = new InvoicePrint();
			this.convert2Bean(list.get(0), invoicePrint);
		}

		return invoicePrint;
	}

	public List<Map<String, Object>> findAll(InvoicePrint invoicePrint) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from csms_invoice_print where 1=1 ");
		if (invoicePrint != null) {
			//sql.append(FieldUtil.getFieldMap(InvoicePrint.class,invoicePrint).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(InvoicePrint.class, invoicePrint);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			return queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			
		} else{
			return queryList(sql.toString());
		}
	}

	public Pager findByPage(Pager pager,InvoicePrint invoicePrint) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_invoice_print t where 1=1");
		if (invoicePrint != null) {
			//sql.append(FieldUtil.getFieldMap(InvoicePrint.class,invoicePrint).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(InvoicePrint.class, invoicePrint);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			return findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else{
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,null);
		}
	}

	public InvoicePrint find(InvoicePrint invoicePrint) {
		InvoicePrint temp = null;
		if (invoicePrint != null) {
			StringBuffer sql = new StringBuffer("select * from csms_invoice_print where 1=1 ");
			//sql.append(FieldUtil.getFieldMap(InvoicePrint.class,invoicePrint).get("nameAndValueNotNull"));
			//List<Map<String, Object>> list = queryList(sql.toString());
			Map map = FieldUtil.getPreFieldMap(InvoicePrint.class, invoicePrint);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id desc");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new InvoicePrint();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public InvoicePrint findByBussinessID(Long bussinessID) {
		return find(new InvoicePrint(bussinessID));
	}

}
