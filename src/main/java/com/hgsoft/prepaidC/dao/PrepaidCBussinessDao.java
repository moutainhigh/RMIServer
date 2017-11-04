package com.hgsoft.prepaidC.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.ReceiptPrint;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class PrepaidCBussinessDao extends BaseDao{
	@Resource
	private ReceiptDao receiptDao;
	
	public void save(PrepaidCBussiness prepaidCBussiness) {
		/*StringBuffer sql=new StringBuffer("insert into csms_prepaidc_bussiness(");
		sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("valueStr")+")");
		save(sql.toString());
		System.out.println(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
		StringBuffer sql=new StringBuffer("insert into csms_prepaidc_bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);
	}
	
	/**
	 * 卡片挂起用
	 * @param prepaidCBussiness
	 * @return void
	 */
	public void saveWithOutReceipt(PrepaidCBussiness prepaidCBussiness){
		Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
		StringBuffer sql=new StringBuffer("insert into csms_prepaidc_bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public void saveTemp(PrepaidCBussiness prepaidCBussiness) {
		Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
		StringBuffer sql=new StringBuffer("insert into csms_prepaidc_bussiness");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		//receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);
	}

	public void delete(Long id) {
		String sql="delete from csms_prepaidc_bussiness where id=?";
		super.delete(sql, id);
	}

	public void update(PrepaidCBussiness prepaidCBussiness) {
		/*StringBuffer sql=new StringBuffer("update csms_prepaidc_bussiness set ");
		sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("nameAndValue")+" where id="+prepaidCBussiness.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
		StringBuffer sql=new StringBuffer("update csms_prepaidc_bussiness set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), prepaidCBussiness.getId());
	}
	
	public void updateById(String state,Long id){
		String sql="update csms_prepaidc_bussiness set tradeState=? where id=?";
		saveOrUpdate(sql, state,id);
	}
	
	public PrepaidCBussiness findById(Long id) {
		String sql = "select * from csms_prepaidc_bussiness where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		PrepaidCBussiness prepaidCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			prepaidCBussiness = new PrepaidCBussiness();
			this.convert2Bean(list.get(0), prepaidCBussiness);
		}

		return prepaidCBussiness;
	}
	
	public PrepaidCBussiness findByCardNo(String cardNo) {
		String sql = "select * from csms_prepaidc_bussiness where state in('9','11') and cardno=?  order by tradeTime desc ";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		PrepaidCBussiness prepaidCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			prepaidCBussiness = new PrepaidCBussiness();
			this.convert2Bean(list.get(0), prepaidCBussiness);
		}

		return prepaidCBussiness;
	}
	
	/**
	 * 根据卡号查询最新的一条业务记录，获取卡内余额
	 * @param cardNo
	 * @return
	 */
	public PrepaidCBussiness findLastPrepaidCBussinessByCardNo(String cardNo) {
		String sql = "select * from csms_prepaidc_bussiness where cardno=?  order by tradeTime desc ";
		List<Map<String, Object>> list = queryList(sql,cardNo);
		PrepaidCBussiness prepaidCBussiness = null;
		if (!list.isEmpty()&&list.size()==1) {
			prepaidCBussiness = new PrepaidCBussiness();
			this.convert2Bean(list.get(0), prepaidCBussiness);
		}

		return prepaidCBussiness;
	}

	public List<Map<String, Object>> findAll(PrepaidCBussiness prepaidCBussiness) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from csms_prepaidc_bussiness where 1=1 ");
		if (prepaidCBussiness != null) {
			//sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		}else{
			list = queryList(sql.toString());
		}
		
		//list=queryList(sql.toString());
		return list;
	}

	public Pager findByPage(Pager pager,PrepaidCBussiness prepaidCBussiness) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from csms_prepaidc_bussiness t where 1=1");
		/*if (prepaidCBussiness != null) {
			sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("nameAndValueNotNull"));
		}*/
		Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
		sql.append(map.get("selectNameStrNotNullAndWhere"));
		if((List) map.get("paramNotNull")!=null){
			sql.append(" order by tradeTime ");
			return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
		}else{
			sql.append(" order by tradeTime ");
			return this.findByPages(sql.toString(), pager,null);
		}
		
	}

	public PrepaidCBussiness find(PrepaidCBussiness prepaidCBussiness) {
		PrepaidCBussiness temp = null;
		if (prepaidCBussiness != null) {
			StringBuffer sql = new StringBuffer("select * from csms_prepaidc_bussiness where 1=1 ");
			//sql.append(FieldUtil.getFieldMap(PrepaidCBussiness.class,prepaidCBussiness).get("nameAndValueNotNull"));
			//sql.append(" order by id");
			//List<Map<String, Object>> list = queryList(sql.toString());
			Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by tradeTime");
			List<Map<String, Object>> list = queryList(sql.toString(),((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new PrepaidCBussiness();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}

	/**
	 * 根据[卡号]和[类型]找到最新的那条业务记录
	 * @param cardNo
	 * @param state
	 * @return
	 */
	public PrepaidCBussiness findByCardNoAndState(String cardNo,String state){
		String sql="select "+FieldUtil.getFieldMap(PrepaidCBussiness.class,new PrepaidCBussiness()).get("nameStr")+
					" from CSMS_PrepaidC_Bussiness where cardNo=? and state=? and tradeTime is not null order By tradeTime Desc";
		List<PrepaidCBussiness> list = queryObjectList(sql,PrepaidCBussiness.class,cardNo,state);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据[旧卡号]和[类型]找到最新的那条业务记录
	 * @param oldCardNo
	 * @param state
	 * @return
	 */
	public PrepaidCBussiness findByOldCardNoAndState(String oldCardNo,String state){
		String sql="select "+FieldUtil.getFieldMap(PrepaidCBussiness.class,new PrepaidCBussiness()).get("nameStr")+
					" from CSMS_PrepaidC_Bussiness where oldCardNo=? and state=? and tradeTime is not null order By tradeTime Desc";
		List<PrepaidCBussiness> list = queryObjectList(sql,PrepaidCBussiness.class,oldCardNo,state);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public PrepaidCBussiness findBussinessNotReturn(String cardNo,String state) {
		String sql="select * from CSMS_PrepaidC_Bussiness where invoiceState !=3 and cardNo=? and state=? order By tradeTime Desc";
		List<Map<String, Object>> list = queryList(sql,cardNo,state);
		PrepaidCBussiness prepaidCBussiness = null;
		if (!list.isEmpty()) {
			prepaidCBussiness = new PrepaidCBussiness();
			this.convert2Bean(list.get(0), prepaidCBussiness);
		}

		return prepaidCBussiness;
	}
	
	public Pager findBussinessListByPager(Pager pager, String cardNo, String startTime, String endTime) {
		StringBuffer sql = new StringBuffer("select ID,CARDNO,TRADETIME,ONLINETRADENO,OFFLINETRADENO,REALPRICE,invoiceState,printTimes,row_number() over (order by tradeTime desc) as num from CSMS_PREPAIDC_BUSSINESS where state in (2,3) and TRADESTATE =2");
		
		SqlParamer params=new SqlParamer();
		/*if(StringUtils.isNoneBlank(cardNo)) {
			sql.append(" and cardNo="+cardNo);
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" and tradeTime>=to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" and tradeTime<=to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}*/
		if(StringUtils.isNoneBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}
		if(StringUtils.isNotBlank(startTime)){
			params.geDate("tradeTime", startTime+" 00:00:00");
		}
		if(StringUtils.isNotBlank(endTime)){
			params.leDate("tradeTime", endTime+" 23:59:59");
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
		//return this.findByPages(sql.toString(), pager,null);
	}

	public List<Map<String, Object>> findAllByTime(ReceiptPrint receiptPrint) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//StringBuffer sql = new StringBuffer("SELECT	P .state,P . ID,p.cardNo,p.OldCardNo,p.RealPrice FROM	CSMS_PREPAIDC_BUSSINESS P WHERE	P .TRADETIME BETWEEN(TO_DATE('"+sd.format(receiptPrint.getBeginTime())+"', 'yyyy-MM-dd   HH24:mi:ss')) AND (TO_DATE('"+ sd.format(receiptPrint.getEndTime()) +"', 'yyyy-MM-dd   HH24:mi:ss')) AND P .USERID = "+receiptPrint.getCustomerId());
		StringBuffer sql = new StringBuffer("SELECT	P .state,P . ID,p.cardNo,p.OldCardNo,p.RealPrice FROM	CSMS_PREPAIDC_BUSSINESS P WHERE	P .TRADETIME BETWEEN(TO_DATE(?, 'yyyy-MM-dd   HH24:mi:ss')) AND (TO_DATE(?, 'yyyy-MM-dd   HH24:mi:ss')) AND P .USERID =?");
		return queryList(sql.toString(),sd.format(receiptPrint.getBeginTime()),sd.format(receiptPrint.getEndTime()),receiptPrint.getCustomerId());
	}

	public int[] batchSavePrepaidCBussiness(final List<PrepaidCBussiness> list) {
        String sql = "insert into csms_prepaidc_bussiness(id,cardNo,State,Userid,TRADETIME,OperID,PlaceID,operNo,placeNO,operName,placeName) values(?,?,?,?,sysdate,?,?,?,?,?,?)";
    	return batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, list.get(i).getId());
				ps.setString(2, list.get(i).getCardno());
				ps.setString(3, list.get(i).getState());
				ps.setLong(4, list.get(i).getUserid());
				ps.setLong(5, list.get(i).getOperid());
				ps.setLong(6, list.get(i).getPlaceid());
				ps.setString(7, list.get(i).getOperNo());
				ps.setString(8, list.get(i).getPlaceNo());
				ps.setString(9, list.get(i).getOperName());
				ps.setString(10, list.get(i).getPlaceName());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
    }
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		
		StringBuffer sql=new StringBuffer("update csms_prepaidc_bussiness set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(tradeTime,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(tradeTime,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(),settleDay,startTime,endTime);
	}

	/**
	 * MQ接口使用
	 * 找到最近一条储值卡注销业务记录
	 * @param prepaidC
	 * @return Map<String, Object>
	 */
	public Map<String, Object> findLastByCardNo(PrepaidC prepaidC){
		StringBuffer sql = new StringBuffer(
				" select * from (select row_number() over(order by tradeTime desc) num,p.* from csms_prepaidc_bussiness p "
						+ " where p.state='12' or p.state='13' ");

		SqlParamer params=new SqlParamer();
		if(prepaidC!=null&& StringUtil.isNotBlank(prepaidC.getCardNo())){
			params.eq("p.cardno", prepaidC.getCardNo());
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();

		sql.append(" ) where num=1 ");
		System.out.println(sql.toString());

		List<Map<String, Object>> resultList = queryList(sql.toString(), Objects);
		if(resultList!=null&&!resultList.isEmpty()){
			return resultList.get(0);
		}
		return null;
	}

	public int updateTradeState(PrepaidCBussiness prepaidCBussiness, String oldTradeState) {
		Map map = FieldUtil.getPreFieldMap(PrepaidCBussiness.class,prepaidCBussiness);
		StringBuffer sql=new StringBuffer("update csms_prepaidc_bussiness set ");
		sql.append(map.get("updateNameStr") +" where id = ? and tradestate=? ");
		return saveOrUpdate(sql.toString(), (List) map.get("param"), prepaidCBussiness.getId(), oldTradeState);
	}

	public PrepaidCBussiness findNewRechargeBusinessByCardNo(String cardNo){
		String sql="select * from CSMS_PrepaidC_Bussiness where cardNo=? and state in ('2','3','4','19','20','94') and tradeState!=3 order By tradeTime Desc fetch first 1 rows only";
		List<PrepaidCBussiness> list = queryObjectList(sql, PrepaidCBussiness.class, cardNo);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public PrepaidCBussiness findNewRechargeBusinessByCardNoId(String cardNo, Long id){
		String sql="select * from CSMS_PrepaidC_Bussiness where cardNo=? and state in ('2','3','4','19','20','94') and tradeState!=3 and id<? order By tradeTime Desc fetch first 1 rows only";
		List<PrepaidCBussiness> list = queryObjectList(sql, PrepaidCBussiness.class, cardNo, id);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public PrepaidCBussiness findNewRechargeBusinessByCardNoMaxTradetime(String cardNo, Date maxTradetime){
		String sql="select * from CSMS_PrepaidC_Bussiness where cardNo=? and state in ('2','3','4','19','20','94') and tradeState!=3 and tradeTime<? order By tradeTime Desc fetch first 1 rows only";
		List<PrepaidCBussiness> list = queryObjectList(sql, PrepaidCBussiness.class, cardNo, maxTradetime);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/*public void updateState(Long businessId, String state) {
		String sql = "update CSMS_PrePaidC_bussiness set tradeState=? where " +
				"businessId=?";
		update(sql, state, businessId);
	}
*/
	public PrepaidCBussiness findByCardNoAndTradeTime(String cardNo, Date dealtimeReq, String state) {
		String sql = "select * from CSMS_PrePaidC_bussiness where " +
				"cardNo=? and tradeTime=? and state=?";
		try {
			return queryRowObject(sql, PrepaidCBussiness.class, cardNo, dealtimeReq, state);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int findRechargeNum(String cardNo, String tradeState) {
		String sql="select count(id) from CSMS_PrepaidC_Bussiness where  state in ('2','3','4','19','20','94') and tradeState=? and cardNo=?";
		return this.count(sql, tradeState, cardNo);
	}

	/**
	 * 根据[卡号]和[类型]找到最新的那条业务记录
	 * @param cardNo
	 * @param state
	 * @return
	 */
	public PrepaidCBussiness findByCardNoAndState_(String cardNo,String state){
		String sql="select "+FieldUtil.getFieldMap(PrepaidCBussiness.class,new PrepaidCBussiness()).get("nameStr")+
				" from CSMS_PrepaidC_Bussiness where cardNo=? and state=? and tradeTime is not null order By tradeTime Desc";
		List<PrepaidCBussiness> list = queryObjectList(sql,PrepaidCBussiness.class,cardNo,state);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return new PrepaidCBussiness();
	}

	public PrepaidCBussiness findRechargeBusinessByCardNoTradetime(String cardNo, Date tradetime){
		String sql="select * from CSMS_PrepaidC_Bussiness where cardNo=? and state in ('2','3','4','19','20','94') and tradeTime=? order By tradeTime Desc fetch first 1 rows only";
		List<PrepaidCBussiness> list = queryObjectList(sql, PrepaidCBussiness.class, cardNo, tradetime);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
