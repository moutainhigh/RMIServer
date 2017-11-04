package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;

@Repository
public class BillGetDao extends BaseDao{
	
	public void save(BillGet billGet) {
		billGet.setHisSeqId(-billGet.getId());
		Map map = FieldUtil.getPreFieldMap(BillGet.class,billGet);
		StringBuffer sql=new StringBuffer("insert into CSMS_bill_get");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql=new StringBuffer("insert into CSMS_bill_get(");
		sql.append(FieldUtil.getFieldMap(BillGet.class,billGet).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(BillGet.class,billGet).get("valueStr")+")");
		save(sql.toString());*/
		
	}

	public void delete(Long id) {
		String sql="delete from CSMS_bill_get where id=?";
		super.delete(sql,id);
	}
	public BillGet findAccCardNo(String cardNo){
		String sql = "select bi.*  from csms_accountc_info ac "
				+ " join csms_subaccount_info su on ac.accountid = su.id "
				+ " join csms_bill_get bi on su.id=bi.cardaccountid "
				+ " where bi.cardType = 2 and ac.cardno =?";
		List<BillGet> billGets = super.queryObjectList(sql, BillGet.class, cardNo);
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}
	public BillGet findPreCardNo(String cardNo){
		String sql = "select * from CSMS_bill_get where cardType=1 and cardbankno=?";
		List<BillGet> billGets = super.queryObjectList(sql, BillGet.class, cardNo);
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}
	public void update(BillGet billGet) {
		Map map = FieldUtil.getPreFieldMap(BillGet.class,billGet);
		StringBuffer sql=new StringBuffer("update CSMS_bill_get set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),billGet.getId());
		/*StringBuffer sql=new StringBuffer("update CSMS_bill_get set ");
		sql.append(FieldUtil.getFieldMap(BillGet.class,billGet).get("nameAndValue")+" where id="+billGet.getId());
		update(sql.toString());*/
	}

	public BillGet findById(Long id) {
		String sql = "select * from CSMS_bill_get where id=?";
		List<BillGet> billGets = super.queryObjectList(sql, BillGet.class, id);
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}

	public List<Map<String, Object>> findAll(BillGet billGet) {
		/*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from CSMS_bill_get where 1=1 ");
		if (billGet != null) {
			sql.append(FieldUtil.getFieldMap(BillGet.class,billGet).get("nameAndValueNotNull"));
		}
		list=queryList(sql.toString());*/

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(0);
		if (billGet == null) {
			return list;
		}
		Map map = FieldUtil.getPreFieldMap(BillGet.class, billGet);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("billGetDao.findAll查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_bill_get where 1=1 ");
		sql.append(condition);
		list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		return list;
	}

	public Pager findByPage(Pager pager,BillGet billGet) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_bill_get t where 1=1 ");
		Map map = FieldUtil.getPreFieldMap(BillGet.class,billGet);
		if (billGet != null) {
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			/*sql.append(FieldUtil.getFieldMap(BillGet.class,billGet).get("nameAndValueNotNull"));*/
		}
		sql.append(" order by t.opertime desc ");
		return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
	}

	public BillGet find(BillGet billGet) {
		if (billGet == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(BillGet.class,billGet);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("billGetDao.find查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_bill_get where 1=1 ");
		sql.append(condition);
		sql.append(" order by id desc");
		List<BillGet> billGets = super.queryObjectList(sql.toString(), BillGet.class, ((List) map.get("paramNotNull")).toArray());
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}
	
	public Pager findByPage(Pager pager,BillGet billGet, Customer customer) {
		StringBuffer sql = new StringBuffer("SELECT c.organ,	c.idType,	c.idCode,  c.USERNO,	"
				+ "b.ID,	b.SERTYPE,	b.BEGTIME,	b.ENDTIME,	ROWNUM AS num FROM	CSMS_CUSTOMER c, CSMS_BILL_GET b WHERE	1 = 1 and c.id=b.MAINID and b.MAINID=" + customer.getId());
		sql.append(" order by c.id ");
		return this.findByPages(sql.toString(), pager,null);
	}

	public List<Map<String, Object>> findAllByMainId(Long mainId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select t.*,c.organ,c.idType,c.userType,c.idCode,c.userno from CSMS_bill_get t ," +
				" CSMS_Customer c where t.mainId = c.id and t.mainId = ?");
		list=queryList(sql.toString(), mainId);
		return list;
	}
	
	/*public void delete(String sql) {
		sql="delete from CSMS_bill_get where "+ sql;
		super.delete(sql);
	}*/
	
	public BillGet findByCardBankNo(String cardBankNo) {
		String sql = "select * from csms_bill_get where cardBankNo=?";
		List<BillGet> billGets = super.queryObjectList(sql.toString(), BillGet.class, cardBankNo);
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}
	
	public BillGet findByCardAccountID(Long cardAccountID) {
		String sql = "select * from csms_bill_get where cardAccountID=?";
		List<BillGet> billGets = super.queryObjectList(sql.toString(), BillGet.class, cardAccountID);
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}
	
	/**
	 * 接口使用
	 * @param billGetList
	 * @return
	 */
	public int[] batchUpdateBillGet(final List<BillGet> billGetList) {  
		String sql = "update csms_bill_get set SerItem=?,HisSeqID=?,OperID=?,OperTime=to_date(?, 'yyyy-mm-dd hh24:mi:ss') where CardBankNo=? and CardAccountID=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
    		
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				BillGet billGet = billGetList.get(i);
				ps.setString(1, billGet.getSerItem());
				ps.setLong(2, billGet.getHisSeqId());
				ps.setLong(3, billGet.getOperId());
				ps.setString(4, format.format(billGet.getOperTime()));
				ps.setString(5, billGet.getCardBankNo());
				ps.setLong(6, billGet.getCardAccountID());
			}
			
			@Override
			public int getBatchSize() {
				 return billGetList.size();
			}
		});
    }
	
	/**
	 * 接口使用
	 * @param billGetCardBankNo
	 * @return
	 */
	public Map<String, Map<String, Object>> findAllByCardBankNo(String billGetCardBankNo) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select p.cardno,a.bankaccount,b.* from csms_bill_get b left join csms_prepaidc p on b.cardbankno = p.cardno "
				+ "left join csms_accountc_apply a on b.cardbankno = a.bankaccount where 1=1");
		if (StringUtil.isNotBlank(billGetCardBankNo)) {
			sql.append(" and b.cardbankno in( "+billGetCardBankNo+" )");
		}
		list=queryList(sql.toString());
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).get("cardbankno").toString(), list.get(i));
		}
		return map;
	}
	
	/**
	 * 接口使用
	 * @param cardBankNo
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> findByNoAndType(String cardBankNo,String type) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from csms_bill_get where 1=1");
		if (StringUtil.isNotBlank(cardBankNo)) {
			sql.append(" and cardBankNo='"+cardBankNo+"'");
		}
		if (StringUtil.isNotBlank(cardBankNo)) {
			sql.append(" and cardType='"+type+"'");
		}
		list=queryList(sql.toString());
		return list;
	}
	
}

