package com.hgsoft.invoice.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.invoice.entity.PassRecord;
import com.hgsoft.utils.Pager;

@Repository
public class PassRecordDao extends BaseDao{
	
	public void save(PassRecord passRecord) {
		StringBuffer sql=new StringBuffer("insert into csms_pass_record");
		Map map = FieldUtil.getPreFieldMap(PassRecord.class,passRecord);
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("nameStr")+") values(");
		//sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("valueStr")+")");
		//save(sql.toString());
	}

	public void delete(Long id) {
		String sql="delete from csms_pass_record where id=?";
		super.delete(sql,id);
	}

	public void update(PassRecord passRecord) {
		StringBuffer sql=new StringBuffer("update csms_pass_record set ");
		Map map = FieldUtil.getPreFieldMap(PassRecord.class,passRecord);
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),passRecord.getId());
		//sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("nameAndValue")+" where id="+passRecord.getId());
		//update(sql.toString());
	}

	public PassRecord findById(Long id) {
		String sql = "select * from csms_pass_record where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		PassRecord passRecord = null;
		if (!list.isEmpty()) {
			passRecord = new PassRecord();
			this.convert2Bean(list.get(0), passRecord);
		}

		return passRecord;
	}

	public List<Map<String, Object>> findAll(PassRecord passRecord) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from csms_pass_record where 1=1 ");
		if (passRecord != null) {
			//sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PassRecord.class, passRecord);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			return queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		} else{
			return queryList(sql.toString());
		}
	}

	public Pager findByPage(Pager pager,PassRecord passRecord) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_pass_record t where 1=1");
		if (passRecord != null) {
			//sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PassRecord.class, passRecord);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			return findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else{
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,null);
		}
	}

	public PassRecord find(PassRecord passRecord) {
		PassRecord temp = null;
		if (passRecord != null) {
			StringBuffer sql = new StringBuffer("select * from csms_pass_record where 1=1 ");
/*			sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());*/
			Map map = FieldUtil.getPreFieldMap(PassRecord.class, passRecord);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new PassRecord();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

	public Pager findPassListByPager(Pager pager, PassRecord passRecord,
			Customer customer, String startTime, String endTime) {
		StringBuffer sql = new StringBuffer("select cus.idtype,cus.idcode,cus.organ,pass.*,ROWNUM as num from csms_pass_record pass,csms_customer cus where pass.customerid = cus.id");
		if (customer != null && customer.getId() != null) {
			sql.append(" and cus.id="+customer.getId());
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" and tollsTime>=to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" and tollsTime<=to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}
		sql.append(" and PassInvoice is null");
		if (passRecord != null) {
			//sql.append(FieldUtil.getFieldMap(PassRecord.class,passRecord).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PassRecord.class, passRecord);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by pass.ID ");
			return this.findByPages(sql.toString(), pager, ((List) map.get("paramNotNull")).toArray());
		} else{
			sql.append(" order by pass.ID ");
			return this.findByPages(sql.toString(), pager, null);
		}
	}

	public void batchUpdatePassInvoiceById(final String[] passRecordIds, final Long passInvoiceId) {
        String sql = "update csms_pass_record set PassInvoice=? where id=?";
    	batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				ps.setLong(1, passInvoiceId);
				ps.setLong(2, Long.parseLong(passRecordIds[i]));
			}
			
			@Override
			public int getBatchSize() {
				 return 0;
			}
		});
    }

	public Pager findPassPrintAgainListByPager(Pager pager,
			String cardNo, Customer customer, Date startTime,
			Date endTime,String type) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from ("
				+ "select distinct ip.*,pi.PrintTimes from CSMS_pass_invoice pi join CSMS_invoice_print ip on ip.passinvoiceid=pi.id join csms_invoice_detail id on id.passinvoiceid=pi.id "
				+ "where id.type="+type+" and ip.state=1 and ip.userid="+customer.getId());
			if(cardNo!="" && cardNo !=null){
				sql.append(" and id.cardNo="+ cardNo);
			}
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startTime !=null){
				sql=sql.append(" and id.reckontime >= to_date('"+format.format((startTime) )+"','YYYY-MM-DD HH24:MI:SS')  ");
			}
			if(endTime != null){
				sql=sql.append(" and id.reckontime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			sql=sql.append("  ) t");
			return this.findByPages(sql.toString(), pager,null);
	}
	
	public void updatePassInvoiceByID(String[] passRecordIds, Long passInvoiceId ) {
		StringBuffer sql=new StringBuffer("update csms_pass_record set PassInvoice="+passInvoiceId+" where id in("+Arrays.toString(passRecordIds).replace("[", "").replace("]", "")+")");
		update(sql.toString());
	}
	
}
