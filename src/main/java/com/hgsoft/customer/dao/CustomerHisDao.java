package com.hgsoft.customer.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerHis;

@Repository
public class CustomerHisDao extends BaseDao{
	
	public void save(CustomerHis customerHis) {
		StringBuffer sql=new StringBuffer("insert into CSMS_Customer_his(");
		sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("valueStr")+")");
		save(sql.toString());
	}

	/*public void delete(Long id) {
		String sql="delete from CSMS_Customer_his where id="+id;
		super.delete(sql);
	}
*/
	/*public void update(CustomerHis customerHis) {
		StringBuffer sql=new StringBuffer("update CSMS_Customer_his set ");
		sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("nameAndValue")+" where id="+customerHis.getId());
		update(sql.toString());
	}*/

	public CustomerHis findById(Long id) {
		String sql = "select * from CSMS_Customer_his where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		CustomerHis customerHis = null;
		if (!list.isEmpty()) {
			customerHis = new CustomerHis();
			this.convert2Bean(list.get(0), customerHis);
		}

		return customerHis;
	}
	public CustomerHis findByHisId(Long id) {
		String sql = "select * from CSMS_Customer_his ";
		if(id!=null){
			sql=sql+" where HISSEQID="+id;
		}else{
			sql=sql+" where HISSEQID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		CustomerHis customerHis = null;
		if (!list.isEmpty()) {
			customerHis = new CustomerHis();
			this.convert2Bean(list.get(0), customerHis);
		}

		return customerHis;
	}
	
	public Customer findCustomerByHisId(Long id) {
		String sql = "select "+FieldUtil.getFieldMap(Customer.class, new Customer()).get("nameStr")+" from CSMS_Customer_his ";
		if(id!=null){
			sql=sql+" where HISSEQID="+id;
		}else{
			sql=sql+" where HISSEQID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		Customer customer = null;
		if (!list.isEmpty()) {
			customer = new Customer();
			this.convert2Bean(list.get(0), customer);
		}

		return customer;
	}
	
	/*public List<Map<String, Object>> findAll(CustomerHis customerHis) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from CSMS_Customer_his where 1=1 ");
		if (customerHis != null) {
			sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("nameAndValueNotNull"));
		}
		list=queryList(sql.toString());
		return list;
	}

	public Pager findByPage(Pager pager,CustomerHis customerHis) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by ID) as num  from CSMS_Customer_his t where 1=1");
		if (customerHis != null) {
			sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("nameAndValueNotNull"));
		}
		return this.findByPages(sql.toString(), pager,null);
	}*/

	/*public CustomerHis find(CustomerHis customerHis) {
		CustomerHis temp = null;
		if (customerHis != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_Customer_his where 1=1 ");
			sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());
			try {
				if (!list.isEmpty()) {
					temp = new CustomerHis();
					this.convert2Bean(list.get(0), temp);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}*/

	/*public Pager findByPage(Pager pager, CustomerHis customerHis,
			String startDate, String endDate) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by ID) as num  from CSMS_Customer_his t where 1=1");
		if (customerHis != null) {
			sql.append(FieldUtil.getFieldMap(CustomerHis.class,customerHis).get("nameAndValueNotNull"));
		}
		if (StringUtils.isNotBlank(startDate)) {
			sql.append(" and cancelTime >=to_date('"+startDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" and cancelTime >=to_date('"+startDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		return this.findByPages(sql.toString(), pager,null);
	}*/

	public int[] batchSave(final List<CustomerHis> customerHisList) {  
        String sql = "insert into CSMS_CUSTOMER_HIS"
        		+ "(ID,GENTIME,GENREASON,USERNO,ORGAN,SERVICEPWD,USERTYPE,LINKMAN,IDTYPE,IDCODE,REGISTEREDCAPITAL,TEL,MOBILE,SHORTTEL,ADDR,ZIPCODE,EMAIL,STATE,CANCELTIME,OPERID,UPDATETIME,FIRRUNTIME,PLACEID,HISSEQID)  "
				+ "SELECT ?,sysdate,?,"
				+ " USERNO,ORGAN,SERVICEPWD,USERTYPE,LINKMAN,IDTYPE,IDCODE,REGISTEREDCAPITAL,TEL,MOBILE,SHORTTEL,ADDR,ZIPCODE,EMAIL,STATE,CANCELTIME,OPERID,UPDATETIME,FIRRUNTIME,PLACEID,HISSEQID"
				+ " FROM csms_customer WHERE USERNO=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				CustomerHis customerHis = customerHisList.get(i);
				ps.setLong(1, customerHis.getId());
				ps.setString(2, customerHis.getGenReason());
				ps.setString(3, customerHis.getUserNo());
			}
			
			@Override
			public int getBatchSize() {
				 return customerHisList.size();
			}
		});
    }
	
}
