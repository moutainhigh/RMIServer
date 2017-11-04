package com.hgsoft.customer.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.customer.entity.InvoiceHis;
import com.hgsoft.utils.Pager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class InvoiceDao extends BaseDao{
	
	public void save(Invoice invoice) {
		invoice.setHisSeqId(-invoice.getId());
		Map map = FieldUtil.getPreFieldMap(Invoice.class,invoice);
		StringBuffer sql=new StringBuffer("insert into CSMS_Invoice");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql=new StringBuffer("insert into CSMS_Invoice(");
		sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("valueStr")+")");
		save(sql.toString());*/
	}

	public void delete(Long id) {
		String sql="delete from CSMS_Invoice where id=?";
		super.delete(sql,id);
	}

	public void update(Invoice invoice) {
		
		Map map = FieldUtil.getPreFieldMap(Invoice.class,invoice);
		StringBuffer sql=new StringBuffer("update CSMS_Invoice set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),invoice.getId());
		
		/*StringBuffer sql=new StringBuffer("update CSMS_Invoice set ");
		sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("nameAndValue")+" where id="+invoice.getId());
		update(sql.toString());*/
	}

	/**
	 * @param id
	 * @return
	 */
	public Invoice findById(Long id) {
		String sql = "select * from CSMS_Invoice where id="+id;
		//+"and isdefault =1";
		List<Map<String, Object>> list = queryList(sql);
		Invoice invoice = null;
		if (!list.isEmpty()) {
			invoice = new Invoice();
			this.convert2Bean(list.get(0), invoice);
		}

		return invoice;
	}
	
	/**
	 * @param id
	 * @return
	 * @author Hodor-zhengyuhang
	 */
	public Invoice findDefaultById(Long id) {
		String sql = "select * from CSMS_Invoice where mainId = ? and isdefault =1";
		List<Map<String, Object>> list = queryList(sql,id);
		Invoice invoice = null;
		if (!list.isEmpty()) {
			invoice = new Invoice();
			this.convert2Bean(list.get(0), invoice);
		}

		return invoice;
	}

	public List<Map<String, Object>> findAll(Invoice invoice) {
		/*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from CSMS_Invoice where 1=1 ");
		if (invoice != null) {
			sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("nameAndValueNotNull"));
		}
		sql.append(" order by isDefault desc");
		list=queryList(sql.toString());
		return list;*/
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from CSMS_Invoice where 1=1 ");
		if (invoice != null) {
		Map map = FieldUtil.getPreFieldMap(Invoice.class,invoice);
		sql.append(map.get("selectNameStrNotNullAndWhere"));
		sql.append(" order by isDefault desc");
		list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		}else{
			sql.append(" order by isDefault desc");
			list=queryList(sql.toString());
		}
		return list;
		
	}

	public Pager findByPage(Pager pager,Invoice invoice) {
		/*StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by ID) as num  from CSMS_Invoice t where 1=1");
		if (invoice != null) {
			sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("nameAndValueNotNull"));
		}
		return this.findByPages(sql.toString(), pager,null);*/
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_Invoice t where 1=1 ");
		Map map = FieldUtil.getPreFieldMap(Invoice.class,invoice);
		if (invoice != null) {
			sql.append(map.get("selectNameStrNotNullAndWhere"));
		}
		sql.append(" order by  t.ID ");
		return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
	}

	public Invoice find(Invoice invoice) {
		Invoice temp = null;
		if (invoice != null) {
			/*StringBuffer sql = new StringBuffer("select * from CSMS_Invoice where 1=1 ");
			sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			StringBuffer sql = new StringBuffer("select * from CSMS_Invoice where 1=1 ");
			Map map = FieldUtil.getPreFieldMap(Invoice.class,invoice);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new Invoice();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

	public Pager findInvoiceInfoByPager(Pager pager, Invoice invoice,Customer customer) {
		StringBuffer sql = new StringBuffer("select c.organ,c.idType,c.idCode,i.id,i.invoiceTitle,i.isdefault,i.mainId,ROWNUM as num from CSMS_CUSTOMER c, CSMS_INVOICE i where 1=1 ");
		sql.append(" and i.mainId=c.id ");
		//设置客户状态为可用
		sql.append(" and c.state = '1'");
		
		Map map = FieldUtil.getPreFieldMap(Invoice.class,invoice);
		if (invoice != null) {
			sql.append(map.get("selectNameStrNotNullAndWhere"));
		}
		Map map1 = FieldUtil.getPreFieldMap(Customer.class,customer);
		if (customer != null) {
			sql.append(map1.get("selectNameStrNotNullAndWhere"));
			
			if(((List) map1.get("paramNotNull")).size()>0){
				if(map.get("paramNotNull").toString()=="")map.put("paramNotNull",new ArrayList());
				for(int i=0;i<((List)map1.get("paramNotNull")).size();i++){
					((List)map.get("paramNotNull")).add(((List)map1.get("paramNotNull")).get(i));
				}
			}
		}
		sql.append(" order by c.ID ");
		return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
		/*if(customer != null){
			sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));
		}
		
		if (invoice != null) {
			sql.append(FieldUtil.getFieldMap(Invoice.class,invoice).get("nameAndValueNotNull"));
		}
		return this.findByPages(sql.toString(), pager,null);*/
	}
	
	public Invoice findByCusAndDef (Long mainID,String isDefault) {
		Invoice temp = null;
		String sql = "select * from csms_Invoice where mainId ="+ mainID+ " and isDefault =?";
		List<Map<String, Object>> list = queryList(sql.toString(),isDefault);
		if (!list.isEmpty() && list.size() == 1) {
			temp = new Invoice();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}

	public List<Map<String, Object>> findByCustomer(Long mainid) {
		return findAll(new Invoice(mainid));
	}
	
	public Invoice findByCustomerId(Long mainId) {
		String sql = "select * from csms_invoice i where i.mainid="+mainId;
		List<Map<String, Object>> list = queryList(sql);
		Invoice invoice = null;
		if (!list.isEmpty()&&list.size()==1) {
			invoice = new Invoice();
			this.convert2Bean(list.get(0), invoice);
		}

		return invoice;
	}
	
	/**
	 * 根据储值卡号查询
	 * @param cardNo
	 * @return
	 */
	public Invoice findByPrepaidCNo(String cardNo) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>> ();
		StringBuffer sql = new StringBuffer("select distinct invoice.*"
				+ " from csms_invoice invoice,csms_prepaidc prepaidc,csms_subaccount_info subinfo,"
				+ "csms_mainaccount_info maininfo,csms_customer customer where prepaidc.accountid = subinfo.id"
				+ " and subinfo.mainid = maininfo.id and maininfo.mainid = customer.id and customer.id = invoice.mainid");
		if(!cardNo.equals("")) {
			sql.append(" and prepaidc.cardno =?");
			list = queryList(sql.toString(),cardNo);
		}else{
			list = queryList(sql.toString());
		}
		Invoice invoice = null;
		if (!list.isEmpty()&&list.size()==1) {
			invoice = new Invoice();
			this.convert2Bean(list.get(0), invoice);
		}

		return invoice;
	}

	/**
	 * 根据记帐卡号查询
	 * @param cardNo
	 * @return
	 */
	public Invoice findByAccountCNo(String cardNo) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>> ();
		StringBuffer sql = new StringBuffer("select distinct invoice.*"
				+ " from csms_invoice invoice,csms_accountc_info accountc,csms_subaccount_info subinfo,"
				+ "csms_mainaccount_info maininfo,csms_customer customer where accountc.accountid = subinfo.id"
				+ " and subinfo.mainid = maininfo.id and maininfo.mainid = customer.id and customer.id = invoice.mainid");
		if(!cardNo.equals("")) {
			sql.append(" and accountc.cardno =?");
			list = queryList(sql.toString(),cardNo);
		}else{
			list = queryList(sql.toString());
		}
		Invoice invoice = null;
		if (!list.isEmpty()&&list.size()==1) {
			invoice = new Invoice();
			this.convert2Bean(list.get(0), invoice);
		}

		return invoice;
	}
	
	public void setDefault(Long id,Long customerId) {
		StringBuffer sql = new StringBuffer("update CSMS_Invoice set isDefault = 0 where id<>" + id +" and mainId = ?");
		saveOrUpdate(sql.toString(),customerId);
	}

	public Pager findInvoiceInfoByPager(Pager pager, Long id) {
		StringBuffer sql = new StringBuffer("select c.organ,c.idType,c.idCode,i.id,i.invoiceTitle,i.isdefault,i.mainId,ROWNUM as num from CSMS_INVOICE i, CSMS_CUSTOMER c where 1=1 and i.mainId = c.id and i.MAINID=" + id);
		//设置客户状态为可用
		sql.append(" and c.state = '1'");
		sql.append(" order by  i.isdefault desc, Up_date desc");
		return this.findByPages(sql.toString(), pager, null);
	}

	public InvoiceHis saveInvoiceHis(InvoiceHis his) {
		StringBuffer sql=new StringBuffer("insert into CSMS_Invoice_His(");
		sql.append(FieldUtil.getFieldMap(InvoiceHis.class,his).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(InvoiceHis.class,his).get("valueStr")+")");
		save(sql.toString());
		return his;
	}
	
	public long isDefaultCount(Long mainId){
		String sql = "select count(1) from csms_invoice  where mainid = ? and isdefault = 1";
		return jdbcUtil.getJdbcTemplate().queryForInt(sql,mainId);
	}
	public InvoiceHis findInvoiceHisByHisId(Long hisId) {
		String sql = "select * from CSMS_Invoice_his where hisseqid="+hisId;
		List<Map<String, Object>> list = queryList(sql);
		InvoiceHis his = null;
		if (!list.isEmpty()) {
			his = new InvoiceHis();
			this.convert2Bean(list.get(0), his);
		}

		return his;
	}

	public List<Map<String, Object>> findAllInvoiceOrderByDefault(Long mainid) {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM CSMS_INVOICE WHERE MAINID=? ORDER BY ISDEFAULT DESC");
		return queryList(sql.toString(), mainid);
	}
}
