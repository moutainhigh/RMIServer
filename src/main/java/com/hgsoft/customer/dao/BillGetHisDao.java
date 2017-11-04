package com.hgsoft.customer.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.BillGetHis;
import com.hgsoft.utils.Pager;

@Repository
public class BillGetHisDao extends BaseDao{
	
	public void save(BillGetHis billGetHis) {
		StringBuffer sql=new StringBuffer("insert into CSMS_bill_get_his(");
		sql.append(FieldUtil.getFieldMap(BillGetHis.class,billGetHis).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(BillGetHis.class,billGetHis).get("valueStr")+")");
		save(sql.toString());
	}

	public void saveHis(BillGetHis billGetHis,BillGet billGet) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_bill_get_his("
				+ "ID,GenTime,GenReason,"
				+ "mainId,cardType,CardAccountID,CardBankNo,SerItem,SerType,BegTime,EndTime,OperTime,OperID,PlaceID,HisSeqID,operno,opername,placeno,placename ) "
				+ "select "+billGetHis.getId()+",sysdate,"+billGetHis.getGenReason()+","
				+ "mainId,cardType,CardAccountID,CardBankNo,SerItem,SerType,BegTime,EndTime,OperTime,OperID,PlaceID,HisSeqID,operno,opername,placeno,placename"
				+ " from CSMS_BILL_GET where id=?");
		save(sql.toString(), billGet.getId());
	}
	
	/*public void delete(Long id) {
		String sql="delete from CSMS_bill_get_his where id=?";
		super.delete(sql,id);
	}*/

	/*public void update(BillGetHis billGetHis) {
		StringBuffer sql=new StringBuffer("update CSMS_bill_get_his set ");
		sql.append(FieldUtil.getFieldMap(BillGetHis.class,billGetHis).get("nameAndValue")+" where id="+billGetHis.getId());
		update(sql.toString());
	}

	public BillGetHis findById(Long id) {
		String sql = "select * from CSMS_bill_get_his where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		BillGetHis billGetHis = null;
		try {
			if (!list.isEmpty()) {
				billGetHis = new BillGetHis();
				this.convert2Bean(list.get(0), billGetHis);
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
		return billGetHis;
	}*/

	/*public List<Map<String, Object>> findAll(BillGetHis billGetHis) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from CSMS_bill_get_his where 1=1 ");
		if (billGetHis != null) {
			sql.append(FieldUtil.getFieldMap(BillGetHis.class,billGetHis).get("nameAndValueNotNull"));
		}
		list=queryList(sql.toString());
		return list;
	}
*/
	/*public Pager findByPage(Pager pager,BillGetHis billGetHis) {
		StringBuffer sql = new StringBuffer("select t.*,row_number() over (order by ID) as num  from CSMS_bill_get_his t where 1=1");
		if (billGetHis != null) {
			sql.append(FieldUtil.getFieldMap(BillGetHis.class,billGetHis).get("nameAndValueNotNull"));
		}
		return this.findByPages(sql.toString(), pager,null);
	}*/

	/*public BillGet find(BillGetHis billGetHis) {
		BillGet temp = null;
		if (billGetHis != null) {
			StringBuffer sql = new StringBuffer("select * from CSMS_bill_get_his where 1=1 ");
			sql.append(FieldUtil.getFieldMap(BillGetHis.class,billGetHis).get("nameAndValueNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString());
			try {
				if (!list.isEmpty()) {
					temp = new BillGet();
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
	
	/*public void delete(String sql) {
		sql="delete from CSMS_bill_get_his where "+ sql;
		super.delete(sql);
	}*/

	public int[] batchSave(final List<BillGetHis> billGetHisList) {  
        String sql = "insert into CSMS_BILL_GET_HIS"
        		+ "(GenTime,GenReason,id,HISSEQID,PLACEID,OPERID,ENDTIME,BEGTIME,SERTYPE,MAINID,SERITEM,CARDBANKNO,CARDACCOUNTID,CARDTYPE,OPERTIME)  "
				+ "SELECT sysdate,?,?,HISSEQID,PLACEID,OPERID,ENDTIME,BEGTIME,SERTYPE,MAINID,SERITEM,CARDBANKNO,CARDACCOUNTID,CARDTYPE,OPERTIME FROM csms_bill_get WHERE CARDBANKNO=? and CardAccountID=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				BillGetHis billGetHis = billGetHisList.get(i);
				ps.setString(1, billGetHis.getGenReason());
				ps.setLong(2, billGetHis.getId());
				ps.setString(3, billGetHis.getCardBankNo());
				ps.setLong(4, billGetHis.getCardAccountID());
			}
			
			@Override
			public int getBatchSize() {
				 return billGetHisList.size();
			}
		});
    }
	
	public BillGetHis findByHisId(Long hisId) {
		String sql = "select * from CSMS_bill_get_his where hisseqid=?";
		List<BillGetHis> billGetHises = super.queryObjectList(sql, BillGetHis.class, hisId);
		if (billGetHises == null || billGetHises.isEmpty()) {
			return null;
		}
		return billGetHises.get(0);
	}
	
}
