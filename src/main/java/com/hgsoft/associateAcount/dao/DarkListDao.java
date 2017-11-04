package com.hgsoft.associateAcount.dao;

import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.entity.BlackListStatus;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DarkListDao extends BaseDao {
	
	public DarkList getLast() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
		String sql = "select * from csms_dark_list where inserttime is not null order by inserttime desc fetch first 1 rows only";
		List<DarkList> darkLists = super.queryObjectList(sql, DarkList.class);
		if (darkLists == null || darkLists.isEmpty()) {
			return null;
		}
		return darkLists.get(0);
	}
	
	public int[] batchSaveDarkList(final List<DarkList> list){
		 String sql = "insert into csms_dark_list(id,customerId,cardNo,cardType,genDate,genCau,updateTime,insertTime) values(SEQ_CSMSDARKLIST_NO.nextval,?,?,?,?,?,?,?)";
	    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					DarkList darkList = list.get(i);
					ps.setLong(1, darkList.getCustomerId());
					ps.setString(2, darkList.getCardNo());
					ps.setString(3, darkList.getCardType());
					ps.setTimestamp(4,new Timestamp(darkList.getGenDate().getTime()));
					ps.setString(5, darkList.getGencau());
					ps.setTimestamp(6, new Timestamp(darkList.getUpdateTime().getTime()));
					ps.setTimestamp(7, new Timestamp(darkList.getInsertTime().getTime()));

				}
				@Override
				public int getBatchSize() {
					 return list.size();
				}
			});
	}
	
	public Customer getMacaoCustomer() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_customer where systemtype='3'";
		List<Map<String,Object>> list = queryList(sql);
		Customer customer = null;
		if(list.size()>0){
			customer = new Customer();
			convert2Bean(list.get(0), customer);
		}
		return customer;
	}
	
	
	
	public void save(DarkList darkList){
		/*StringBuffer sql=new StringBuffer("insert into CSMS_DARK_LIST(");
		sql.append(FieldUtil.getFieldMap(DarkList.class,darkList).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(DarkList.class,darkList).get("valueStr")+")");
		save(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(DarkList.class,darkList);
		StringBuffer sql=new StringBuffer("insert into CSMS_DARK_LIST");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	/**
	 * @author gsf
	 * @param cardNo
	 * @return
	 */
	public DarkList findByCardNoAndCau(String cardNo,String genCau){
		DarkList temp = null;
		StringBuffer sql = new StringBuffer(
				"select ID,CustomerID,CardNo,CardType,genDate,genCau,genMode,OperId,placeId,Remark,HisSeqID "
				+ "from CSMS_DARK_LIST where cardno=? and genCau=?");
		List<DarkList> darkLists = super.queryObjectList(sql.toString(), DarkList.class,cardNo,genCau);
		if (darkLists == null || darkLists.isEmpty()) {
			return null;
		}
		return darkLists.get(0);
	}
	/**
	 * @author gaosiling
	 * @param cardNo
	 * @return
	 */
	public DarkList findByCardNo(String cardNo){
		//这个表已经没有在用，这个方法也暂时没有在用
		String sql = "select * from CSMS_BLACKLIST_STATUS where CARDNO=?";
		List<Map<String, Object>> list = queryList(sql.toString(), cardNo);
		
		BlackListStatus blackListStatus = null;
		if (!list.isEmpty()) {
			blackListStatus = new BlackListStatus();
			this.convert2Bean(list.get(0), blackListStatus);
		}

		if(blackListStatus == null){
			return null;
		}
		DarkList darkList = new DarkList();
		darkList.setCardNo(blackListStatus.getCardNo());
		darkList.setCardType(blackListStatus.getCardType());
		darkList.setGencau(blackListStatus.getStatus().toString());
		darkList.setGenDate(blackListStatus.getGenTime());
		return darkList;
	}
	/**
	 * @author gsf
	 * @param darkListId
	 */
	public void deleteById(Long darkListId){
		String sql = "delete from CSMS_DARK_LIST where id=?";
		delete(sql,darkListId);
	}
	
	public Pager findDarkList(Pager pager, Customer customer, DarkList darkList, Date startTime, Date endTime) {
		StringBuffer sql = new StringBuffer("select d.*,ROWNUM as num from csms_dark_list d where customerid="+customer.getId());
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(darkList.getCardNo())){
			params.eq("CardNo", darkList.getCardNo());
		}
		if(startTime!=null){
			params.geDate("genDate", params.getFormat().format(startTime));
		}
		if(endTime!=null){
			params.leDate("genDate", params.getFormatEnd().format(endTime));
		}
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by genDate desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public List list(Customer customer, DarkList darkList, Date startTime, Date endTime) {
		String sql = "select d.* from csms_dark_list d where customerid=? order by genDate desc ";
		List<Map<String, Object>> list = queryList(sql.toString(),customer.getId());
		return list;
	}
	
	public void update(DarkList darkList){
		String sql = "update csms_dark_list set Gencau=?,Genmode=?,Remark=?，HisseqId=? where id=?";
		saveOrUpdate(sql, darkList.getGencau(),darkList.getGenmode(),darkList.getRemark(),darkList.getHisseqId(),darkList.getId());
	}
	
	public void updateDarkList(DarkList darkList){
		Map map = FieldUtil.getPreFieldMap(DarkList.class, darkList);
		StringBuffer sql =  new StringBuffer("update CSMS_DARK_LIST set ");
		sql.append(map.get("updateNameStrNotNull")+" where id=?");
		super.saveOrUpdate(sql.toString(), (List)map.get("paramNotNull"), darkList.getId());
	}
	
}
