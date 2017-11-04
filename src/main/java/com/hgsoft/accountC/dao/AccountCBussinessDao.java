package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.ReceiptPrint;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Repository
public class AccountCBussinessDao extends BaseDao{
	@Resource
	private ReceiptDao receiptDao;
	public AccountCBussiness findById(Long id) {
		String sql = "select * from Csms_Accountc_Bussiness where id=?";
		List<Map<String, Object>> list = queryList(sql, id);
		AccountCBussiness accountCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCBussiness = new AccountCBussiness();
			this.convert2Bean(list.get(0), accountCBussiness);
		}

		return accountCBussiness;
	}
	
	public AccountCBussiness findLockByCno(String cardNo) {
		String sql = "select * from Csms_Accountc_Bussiness where cardNo=? and lockType is not null order by id desc";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		AccountCBussiness accountCBussiness = null;
		if (!list.isEmpty()) {
			accountCBussiness = new AccountCBussiness();
			this.convert2Bean(list.get(0), accountCBussiness);
		}

		return accountCBussiness;
	}
	
	public AccountCBussiness find(AccountCBussiness accountCBussiness) {
		AccountCBussiness temp = null;
		if (accountCBussiness!= null) {
			StringBuffer sql = new StringBuffer("select * from Csms_Accountc_Bussiness where 1=1 ");
			/*sql.append(FieldUtil.getFieldMap(AccountCBussiness.class,accountCBussiness).get("nameAndValueNotNull"));*/
			Map map = FieldUtil.getPreFieldMap(AccountCBussiness.class,accountCBussiness);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new AccountCBussiness();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public AccountCBussiness findByBussinessID(Long businessId) {
		String sql = "select * from Csms_Accountc_Bussiness where businessId=?";
		List<Map<String, Object>> list = queryList(sql, businessId);
		AccountCBussiness accountCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCBussiness = new AccountCBussiness();
			this.convert2Bean(list.get(0), accountCBussiness);
		}

		return accountCBussiness;
	}
	
	public void update(AccountCBussiness accountCBussiness) {

		Map map = FieldUtil.getPreFieldMap(AccountCBussiness.class,accountCBussiness);
		StringBuffer sql=new StringBuffer("update Csms_Accountc_Bussiness set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),accountCBussiness.getId());
		/*StringBuffer sql=new StringBuffer("update Csms_Accountc_Bussiness set ");
		sql.append(FieldUtil.getFieldMap(AccountCBussiness.class,accountCBussiness).get("nameAndValue")+" where id="+accountCBussiness.getId());
		update(sql.toString());*/
	}


	/**
	 * 根据ID删除记帐卡业务记录
	 * @param id
	 */
	public void delete(Long id){
		String sql = "delete from Csms_Accountc_Bussiness where id=?";

		delete(sql, id);
	}
	
	/**
	 * 记帐卡业务记录保存
	 * @param accountCBussiness
	 */
	public void save(AccountCBussiness accountCBussiness) {
		Map map = FieldUtil.getPreFieldMap(AccountCBussiness.class,accountCBussiness);
		StringBuffer sql=new StringBuffer("insert into Csms_Accountc_Bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	/*	StringBuffer sql=new StringBuffer("insert into Csms_Accountc_Bussiness(");
		sql.append(FieldUtil.getFieldMap(AccountCBussiness.class,accountCBussiness).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountCBussiness.class,accountCBussiness).get("valueStr")+")");
		save(sql.toString());*/
		//receiptDao.saveByBussiness(null, null, null, null, accountCBussiness);
	}
	
	/**
	 * 卡片解除挂起用
	 * @param accountCBussiness
	 */
	public void saveWithOutReceipt(AccountCBussiness accountCBussiness) {
		Map map = FieldUtil.getPreFieldMap(AccountCBussiness.class,accountCBussiness);
		StringBuffer sql=new StringBuffer("insert into Csms_Accountc_Bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public List<Map<String, Object>> findAllByTime(ReceiptPrint receiptPrint) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer("SELECT	a .state,a . ID,a.cardNo,a.OldCardNo,a.RealPrice FROM	CSMS_ACCOUNTC_BUSSINESS a WHERE a .TRADETIME BETWEEN(TO_DATE('"+sd.format(receiptPrint.getBeginTime())+"', 'yyyy-MM-dd   HH24:mi:ss')) AND (TO_DATE('"+ sd.format(receiptPrint.getEndTime()) +"', 'yyyy-MM-dd   HH24:mi:ss')) AND a .USERID = "+receiptPrint.getCustomerId());
		return queryList(sql.toString());
	}

	public int[] batchSave(final List<AccountCBussiness> bussList) {  
        String sql = "insert into CSMS_ACCOUNTC_BUSSINESS(PLACEID,OPERID,TRADETIME,LASTSTATE,RECEIPTPRINTTIMES,"
        		+ "REALPRICE,STATE,OLDCARDNO,CARDNO,USERID,ID,OLDACCOUNTID,ACCOUNTID,OLDUSERID,OperName,OperNo,PlaceName,PlaceNo,BUSINESSID)  "
				+ "values(?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				AccountCBussiness accountCBussiness = bussList.get(i);
				if(accountCBussiness.getPlaceId()==null)ps.setNull(1, Types.BIGINT);else ps.setLong(1, accountCBussiness.getPlaceId());
				if(accountCBussiness.getOperId()==null)ps.setNull(2, Types.BIGINT);else ps.setLong(2, accountCBussiness.getOperId());
				if(accountCBussiness.getLastState()==null)ps.setNull(3, Types.VARCHAR);else ps.setString(3, accountCBussiness.getLastState());
				if(accountCBussiness.getReceiptPrintTimes()==null)ps.setNull(4, Types.INTEGER);else ps.setInt(4, accountCBussiness.getReceiptPrintTimes());
				if(accountCBussiness.getRealPrice()==null)ps.setNull(5, Types.DOUBLE);else ps.setBigDecimal(5, accountCBussiness.getRealPrice());
				if(accountCBussiness.getState()==null)ps.setNull(6, Types.VARCHAR);else ps.setString(6, accountCBussiness.getState());
				if(accountCBussiness.getOldCardNo()==null)ps.setNull(7, Types.VARCHAR);else ps.setString(7, accountCBussiness.getOldCardNo());
				if(accountCBussiness.getCardNo()==null)ps.setNull(8, Types.VARCHAR);else ps.setString(8, accountCBussiness.getCardNo());
				if(accountCBussiness.getUserId()==null)ps.setNull(9, Types.BIGINT);else ps.setLong(9, accountCBussiness.getUserId());
				if(accountCBussiness.getId()==null)ps.setNull(10, Types.BIGINT);else ps.setLong(10, accountCBussiness.getId());
				if(accountCBussiness.getOldAccountId()==null)ps.setNull(11, Types.BIGINT);else ps.setLong(11, accountCBussiness.getOldAccountId());
				if(accountCBussiness.getAccountId()==null)ps.setNull(12, Types.BIGINT);else ps.setLong(12, accountCBussiness.getAccountId());
				if(accountCBussiness.getOldUserId()==null)ps.setNull(13, Types.BIGINT);else ps.setLong(13, accountCBussiness.getOldUserId());
				//新增字段
				if(accountCBussiness.getOperName()==null)ps.setNull(14, Types.VARCHAR);else ps.setString(14, accountCBussiness.getOperName());
				if(accountCBussiness.getOperNo()==null)ps.setNull(15, Types.VARCHAR);else ps.setString(15, accountCBussiness.getOperNo());
				if(accountCBussiness.getPlaceName()==null)ps.setNull(16, Types.VARCHAR);else ps.setString(16, accountCBussiness.getPlaceName());
				if(accountCBussiness.getPlaceNo()==null)ps.setNull(17, Types.VARCHAR);else ps.setString(17, accountCBussiness.getPlaceNo());
				if(accountCBussiness.getBusinessId()==null)ps.setNull(18, Types.BIGINT);else ps.setLong(18, accountCBussiness.getBusinessId());
			}
			
			@Override
			public int getBatchSize() {
				 return bussList.size();
			}
		});
    }
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		StringBuffer sql=new StringBuffer("update CSMS_ACCOUNTC_BUSSINESS set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(TRADETIME,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(TRADETIME,'YYYYMMDDHH24MISS')<? and placeno in(");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(),settleDay,startTime,endTime);
	}

	public AccountCBussiness findByBaiBackCardNo(String cardNo) {
		String sql = "select * from Csms_Accountc_Bussiness where state in('25') and cardno=?  order by id desc ";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		AccountCBussiness accountCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCBussiness = new AccountCBussiness();
			this.convert2Bean(list.get(0), accountCBussiness);
		}

		return accountCBussiness;
	}
	public AccountCBussiness findByCardNo(String cardNo) {
		String sql = "select * from Csms_Accountc_Bussiness where state in('5','7') and cardno=?  order by id desc ";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		AccountCBussiness accountCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			accountCBussiness = new AccountCBussiness();
			this.convert2Bean(list.get(0), accountCBussiness);
		}

		return accountCBussiness;
	}

	/**
	 * 根据[卡号]和[类型]找到最新的那条业务记录
	 * @param cardNo
	 * @param state
	 * @return
	 */
	public AccountCBussiness findByCardNoAndState(String cardNo,String state){
		String sql="select "+FieldUtil.getFieldMap(AccountCBussiness.class,new AccountCBussiness()).get("nameStr")+
				" from csms_accountc_bussiness where cardNo=? and state=? and tradeTime is not null order By tradeTime Desc";
		List<AccountCBussiness> list = queryObjectList(sql,AccountCBussiness.class,cardNo,state);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return new AccountCBussiness();
	}

}
