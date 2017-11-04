package com.hgsoft.other.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.utils.Pager;

@Repository
public class ReceiptPrintDao extends BaseDao{
	
	public void save(ReceiptPrint receiptPrint) {
		StringBuffer sql=new StringBuffer("insert into CSMS_ReceiptPrint");
		Map map = FieldUtil.getPreFieldMap(ReceiptPrint.class,receiptPrint);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(ReceiptPrint.class,receiptPrint).get("nameStr")+") values(");
		//sql.append(FieldUtil.getFieldMap(ReceiptPrint.class,receiptPrint).get("valueStr")+")");
		//save(sql.toString());
	}

	public void delete(Long id) {
		String sql="delete from CSMS_ReceiptPrint where id=?";
		super.delete(sql,id);
	}

	public void update(ReceiptPrint receiptPrint) {
		StringBuffer sql=new StringBuffer("update CSMS_ReceiptPrint set ");
		Map map = FieldUtil.getPreFieldMap(ReceiptPrint.class,receiptPrint);
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),receiptPrint.getId());
		//sql.append(FieldUtil.getFieldMap(ReceiptPrint.class,receiptPrint).get("nameAndValue")+" where id="+receiptPrint.getId());
		//update(sql.toString());
	}

	public ReceiptPrint findById(Long id) {
		String sql = "select ID,customerID,beginTime,endTime,printNum,memo from CSMS_ReceiptPrint where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		ReceiptPrint receiptPrint = null;
		if (!list.isEmpty()) {
			receiptPrint = new ReceiptPrint();
			this.convert2Bean(list.get(0), receiptPrint);
		}

		return receiptPrint;
	}

	public List<Map<String, Object>> findAll(ReceiptPrint receiptPrint) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select ID,customerID,beginTime,endTime,printNum,memo from CSMS_ReceiptPrint where 1=1 ");
		if (receiptPrint != null) {
			//sql.append(FieldUtil.getFieldMap(ReceiptPrint.class,receiptPrint).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ReceiptPrint.class, receiptPrint);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			return queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		} else{
			return queryList(sql.toString());
		}
	}

	public Pager findByPage(Pager pager,ReceiptPrint receiptPrint) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_ReceiptPrint t where 1=1");
		if (receiptPrint != null) {
			//sql.append(FieldUtil.getFieldMap(ReceiptPrint.class,receiptPrint).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ReceiptPrint.class, receiptPrint);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by t.ID ");
			return findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else{
			sql.append(" order by t.ID ");
			return this.findByPages(sql.toString(), pager,null);
		}
	}

	public ReceiptPrint find(ReceiptPrint receiptPrint) {
		ReceiptPrint temp = null;
		if (receiptPrint != null) {
			StringBuffer sql = new StringBuffer("select ID,customerID,beginTime,endTime,printNum,memo from CSMS_ReceiptPrint where 1=1 ");
			//sql.append(FieldUtil.getFieldMap(ReceiptPrint.class,receiptPrint).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(ReceiptPrint.class, receiptPrint);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new ReceiptPrint();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public void delete(String sql) {
		sql="delete from CSMS_ReceiptPrint where ?";
		super.delete(sql,sql);
	}

	public Pager findAllByCustomerId(Pager pager, Long id) {
		StringBuffer sql = new StringBuffer("select r.id,c.ORGAN,r.BEGINTIME,r.ENDTIME,r.PRINTNUM,r.MEMO,ROWNUM as num from CSMS_CUSTOMER c, CSMS_RECEIPTPRINT r WHERE c.id=r.CUSTOMERID and c.id=" + id + " ORDER BY r.beginTime DESC ");
		return this.findByPages(sql.toString(), pager,null);
	}

	public void updateEndTime(Long id) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer("update CSMS_RECEIPTPRINT set ENDTIME=TO_DATE('"+sd.format(new Date())+"', 'yyyy-MM-dd   HH24:mi:ss') where ENDTIME is null and CUSTOMERID="+id);
		update(sql.toString());
	}


	public ReceiptPrint findByCustomer(Long customerID) {
		ReceiptPrint temp = null;
		if (customerID != null) {
			StringBuffer sql = new StringBuffer("select ID,customerID,beginTime,endTime,printNum,memo  from CSMS_ReceiptPrint where 1=1 and CustomerID="+customerID+" and BeginTime is not null and EndTime is null");
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new ReceiptPrint();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public ReceiptPrint findByCustomerIdAndTime(Customer customer){
		String sql = "select ID,customerID,beginTime,endTime,printNum,memo "
				+ "from CSMS_ReceiptPrint where customerID="+customer.getId()+" and beginTime is not null and endTime is null";
		List<Map<String, Object>> list = queryList(sql);
		ReceiptPrint receiptPrint = null;
		if (!list.isEmpty()) {
			receiptPrint = new ReceiptPrint();
			this.convert2Bean(list.get(0), receiptPrint);
		}

		return receiptPrint;
	}
	
	public ReceiptPrint findByCustomerIdAndBeginTime(Customer customer){
		String sql = "select ID,customerID,beginTime,endTime,printNum,memo "
				+ "from CSMS_ReceiptPrint where customerID="+customer.getId()+" and beginTime is not null";
		List<Map<String, Object>> list = queryList(sql);
		ReceiptPrint receiptPrint = null;
		if (!list.isEmpty()) {
			receiptPrint = new ReceiptPrint();
			this.convert2Bean(list.get(0), receiptPrint);
		}

		return receiptPrint;
	}

	public void updatePrintNum(ReceiptPrint receiptPrint) {
		StringBuffer sql = new StringBuffer("update CSMS_RECEIPTPRINT set PRINTNUM=PRINTNUM+1 where id="+receiptPrint.getId());
		update(sql.toString());
	}
	
}
