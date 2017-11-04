package com.hgsoft.account.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class RechargeInfoDao extends BaseDao  {
	public Pager list(Pager pager,Date starTime ,Date endTime, RechargeInfo rechargeInfo,Customer customer, CusPointPoJo cusPointPoJo) {
		// TODO Auto-generated method stub
		String sql="select r.id as id, c.Organ as Organ,r.PayMentType as PayMentType,r.IsCorrect as isCorrect ,r.CorrectID as correctID ,"
				+ "r.PayMentNo as PayMentNo,r.TakeBalance as TakeBalance,"
				+ "r.Balance as Balance,r.AvailableBalance as AvailableBalance,r.transactionType as TransactionType, "
				+ "FrozenBalance as FrozenBalance,r.AvailableRefundBalance as AvailableRefundBalance,"
				+ "r.RefundApproveBalance,r.PlaceID as PlaceID ,r.OperTime as OperTime,r.OperID as OperID,r.isDaySet as isDaySet,"
				+ "r.operNo as operNo,r.operName as operName,r.placeNo as placeNo,r.placeName as placeName, ROWNUM as num  "
				+ "from CSMS_RechargeInfo r  join CSMS_Customer c on r.MainID=c.ID where 1=1  ";		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.OperTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS')  ");
		}
		if(endTime != null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.OperTime <= to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		/*if(customer!=null && StringUtil.isNotBlank(customer.getOrgan())){
			params.like("c.organ", "%"+customer.getOrgan()+"%");
		}*/
		if(customer!=null && customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}
		if(StringUtil.isNotBlank(rechargeInfo.getPayMentType())){
			params.eq("r.PayMentType",rechargeInfo.getPayMentType());
		}
		if(rechargeInfo.getOperId() !=null){
			params.eq("r.OperID", rechargeInfo.getOperId());
		}
		if(StringUtil.isNotBlank(rechargeInfo.getOperName())){
			params.eq("r.OperName", rechargeInfo.getOperName());
		}
		if(rechargeInfo.getBankTransferId() != null){
			params.eq("r.BankTransferID",rechargeInfo.getBankTransferId());
		}
		if(cusPointPoJo!=null && cusPointPoJo.getCusPointCode() != null){
			params.eq("r.Placeno",cusPointPoJo.getCusPointCode());
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by  R.OperTime DESC,r.AvailableBalance desc ");
		return this.findByPages(sql, pager,Objects);
		
	}
	public Pager list2(Pager pager,String starTime ,String endTime, RechargeInfo rechargeInfo,Customer customer, CusPointPoJo cusPointPoJo) {
		// TODO Auto-generated method stub
		String sql="select r.id as id, c.Organ as Organ,r.PayMentType as PayMentType,r.IsCorrect as isCorrect ,r.CorrectID as correctID ,"
				+ "r.PayMentNo as PayMentNo,r.TakeBalance as TakeBalance,"
				+ "r.Balance as Balance,r.AvailableBalance as AvailableBalance,r.transactionType as TransactionType, "
				+ "FrozenBalance as FrozenBalance,r.AvailableRefundBalance as AvailableRefundBalance,"
				+ "r.RefundApproveBalance,r.PlaceID as PlaceID ,r.OperTime as OperTime,r.OperID as OperID,r.isDaySet as isDaySet,"
				+ "r.operNo as operNo,r.operName as operName,r.placeNo as placeNo,r.placeName as placeName, ROWNUM as num  "
				+ "from CSMS_RechargeInfo r  join CSMS_Customer c on r.MainID=c.ID where 1=1  ";		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(starTime)){
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.OperTime >= to_date('"+starTime+"','YYYY-MM-DD HH24:MI:SS')  ");
		}
		if(StringUtil.isNotBlank(endTime)){
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.OperTime <= to_date('"+endTime+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		/*if(customer!=null && StringUtil.isNotBlank(customer.getOrgan())){
			params.like("c.organ", "%"+customer.getOrgan()+"%");
		}*/
		if(customer!=null && customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}
		if(StringUtil.isNotBlank(rechargeInfo.getPayMentType())){
			params.eq("r.PayMentType",rechargeInfo.getPayMentType());
		}
		if(rechargeInfo.getOperId() !=null){
			params.eq("r.OperID", rechargeInfo.getOperId());
		}
		if(StringUtil.isNotBlank(rechargeInfo.getOperName())){
			params.eq("r.OperName", rechargeInfo.getOperName());
		}
		if(rechargeInfo.getBankTransferId() != null){
			params.eq("r.BankTransferID",rechargeInfo.getBankTransferId());
		}
		if(cusPointPoJo!=null && cusPointPoJo.getCusPointCode() != null){
			params.eq("r.Placeno",cusPointPoJo.getCusPointCode());
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by  R.OperTime DESC,r.AvailableBalance desc ");
		return this.findByPages(sql, pager,Objects);
		
	}
	
	
	public Pager findByPageForSumAmt(Pager pager,Date starTime ,Date endTime, RechargeInfo rechargeInfo,Customer customer, CusPointPoJo cusPointPoJo) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer("select r.id as id,r.PayMentType as PayMentType,r.IsCorrect as isCorrect ,r.CorrectID as correctID ,"
				+ "r.PayMentNo as PayMentNo,r.TakeBalance as TakeBalance,"
				+ "r.Balance as Balance,r.AvailableBalance as AvailableBalance,r.transactionType as TransactionType, "
				+ "FrozenBalance as FrozenBalance,r.AvailableRefundBalance as AvailableRefundBalance,"
				+ "r.RefundApproveBalance,r.PlaceID as PlaceID ,r.OperTime as OperTime,r.OperID as OperID,r.isDaySet as isDaySet,"
				+ "r.operNo as operNo,r.operName as operName,r.placeNo as placeNo,r.placeName as placeName, ROWNUM as num,sum(r.takebalance) over (order by r.opertime desc) amt  "
				+ "from CSMS_RechargeInfo r  where mainid = "+rechargeInfo.getMainId()+" AND  opertime>=(" 
				+ " select opertime from(select r.opertime  as opertime,sum(r.takebalance)" +
				" over (order by r.opertime desc) amt  from CSMS_RechargeInfo r  where mainid = "+rechargeInfo.getMainId()+") where amt >= "+rechargeInfo.getTakeBalance()+" fetch first 1 rows only) ");		
		
		/*sql.append(" union all ");
		
		sql.append("select * from(select * from(select r.id as id,r.PayMentType as PayMentType,r.IsCorrect as isCorrect ,r.CorrectID as correctID ,"
				+ "r.PayMentNo as PayMentNo,r.TakeBalance as TakeBalance,"
				+ "r.Balance as Balance,r.AvailableBalance as AvailableBalance,r.transactionType as TransactionType, "
				+ "FrozenBalance as FrozenBalance,r.AvailableRefundBalance as AvailableRefundBalance,"
				+ "r.RefundApproveBalance,r.PlaceID as PlaceID ,r.OperTime as OperTime,r.OperID as OperID,r.isDaySet as isDaySet,"
				+ "r.operNo as operNo,r.operName as operName,r.placeNo as placeNo,r.placeName as placeName, ROWNUM as num,sum(r.takebalance) over (order by r.opertime desc) amt  "
				+ "from CSMS_RechargeInfo r  where mainid = "+rechargeInfo.getMainId()+") where amt >= "+rechargeInfo.getTakeBalance()+" fetch first 1 rows only) ");	*/
		
		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			params.geDate("r.OperTime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("r.OperTime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(rechargeInfo.getPayMentType())){
			params.eq("r.PayMentType",rechargeInfo.getPayMentType());
		}
		if(rechargeInfo.getOperId() !=null){
			params.eq("r.OperID", rechargeInfo.getOperId());
		}
		if(StringUtil.isNotBlank(rechargeInfo.getOperName())){
			params.eq("r.OperName", rechargeInfo.getOperName());
		}
		if(rechargeInfo.getBankTransferId() != null){
			params.eq("r.BankTransferID",rechargeInfo.getBankTransferId());
		}
		if(cusPointPoJo!=null && cusPointPoJo.getCusPoint() != null){
			params.eq("r.PlaceID",cusPointPoJo.getCusPoint());
		}
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
		
	}
	
	public void save(RechargeInfo rechargeInfo){
		/*StringBuffer sql=new StringBuffer("insert into CSMS_RechargeInfo(");
		sql.append(FieldUtil.getFieldMap(RechargeInfo.class,rechargeInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(RechargeInfo.class,rechargeInfo).get("valueStr")+")");
		save(sql.toString());*/
		
		/*Map map = FieldUtil.getPreFieldMap(RechargeInfo.class,rechargeInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_RechargeInfo");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));*/
		
		StringBuffer sql = new StringBuffer("insert into " +
				"CSMS_RechargeInfo(id,mainId,mainAccountId,correctId,bankTransferId,refundID,payMember," +
				"payMentType,payMentNo,transactionType,balance,availableBalance,preferentialBalance,frozenBalance," +
				"availableRefundBalance,refundApproveBalance,state,takeBalance,posId,memo,operId,placeId,operTime," +
				"isCorrect,isDaySet,settleDay,settletTime,operNo,operName,placeNo,placeName,prepaidCBussinessId,voucherNo) select ");
				//"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		sql.append(rechargeInfo.getId()+",");
		sql.append(rechargeInfo.getMainId()+",");
		sql.append(rechargeInfo.getMainAccountId()+",");
		sql.append(rechargeInfo.getCorrectId()+",");
		sql.append(rechargeInfo.getBankTransferId()+",");
		sql.append(rechargeInfo.getRefundID()+",");
		sql.append("?,");
		sql.append("?,");
		sql.append("?,");
		sql.append("?,");
		if(rechargeInfo.getAvailableBalance()!=null && rechargeInfo.getAvailableBalance().compareTo(new BigDecimal("0"))==-1){
			sql.append(" balance+("+rechargeInfo.getAvailableBalance()+"), ");
			sql.append(" availablebalance+("+rechargeInfo.getAvailableBalance()+") ,"
					+ "preferentialbalance ,"
					+ "frozenbalance,"
					+ "availablerefundbalance,"
					+ "refundapprovebalance, ");
		}else{
			sql.append(" balance, ");
			sql.append(" availablebalance ,"
					+ "preferentialbalance ,"
					+ "frozenbalance,"
					+ "availablerefundbalance,"
					+ "refundapprovebalance, ");
		}
		sql.append("?,");
		sql.append(rechargeInfo.getTakeBalance()+",");
		sql.append(rechargeInfo.getPosId()+",");
		sql.append("?,");
		sql.append(rechargeInfo.getOperId()+",");
		sql.append(rechargeInfo.getPlaceId()+",");
		sql.append("sysdate ,");
		sql.append("?,");
		sql.append("?,");
		sql.append("?,");
		sql.append(rechargeInfo.getSettletTime()+",");
		sql.append("'"+rechargeInfo.getOperNo()+"',");
		sql.append("'"+rechargeInfo.getOperName()+"',");
		sql.append("'"+rechargeInfo.getPlaceNo()+"',");
		sql.append("'"+rechargeInfo.getPlaceName()+"',");
		sql.append(rechargeInfo.getPrepaidCBussinessId()+",");
		sql.append("?");
		sql.append(" from csms_mainaccount_info where id = "+rechargeInfo.getMainAccountId());
		saveOrUpdate(sql.toString(),rechargeInfo.getPayMember(),
				rechargeInfo.getPayMentType(),rechargeInfo.getPayMentNo(),
				rechargeInfo.getTransactionType(),rechargeInfo.getState(),
				rechargeInfo.getMemo(),rechargeInfo.getIsCorrect(),
				rechargeInfo.getIsCorrect(),rechargeInfo.getSettleDay(),rechargeInfo.getVoucherNo());
	}
	
	
	public RechargeInfo findByCorrectId(Long correctId){
		String sql="select "+FieldUtil.getFieldMap(RechargeInfo.class,new RechargeInfo()).get("nameStr")+" from CSMS_RechargeInfo where CorrectId=?";
		List list=queryList(sql, correctId);
		RechargeInfo rechargeInfo = null;
		if(list!=null && list.size()!=0) {
			rechargeInfo = (RechargeInfo) this.convert2Bean((Map<String, Object>) list.get(0), new RechargeInfo());
		}

		return rechargeInfo;
		
	}
	public RechargeInfo findById(Long id) {
			String sql="select "+FieldUtil.getFieldMap(RechargeInfo.class,new RechargeInfo()).get("nameStr")+" from CSMS_RechargeInfo where ID=?";
			List list=queryList(sql, id);	
			RechargeInfo rechargeInfo = null;
			if(list!=null && list.size()!=0) {
				return rechargeInfo = (RechargeInfo) this.convert2Bean((Map<String, Object>) list.get(0), new RechargeInfo());

			}

			return rechargeInfo;
		}
	public RechargeInfo findByPrepaidCBussinessId(Long id) {
		String sql="select "+FieldUtil.getFieldMap(RechargeInfo.class,new RechargeInfo()).get("nameStr")+" from CSMS_RechargeInfo where prepaidCBussinessId=?";
		List list=queryList(sql, id);	
		RechargeInfo rechargeInfo = null;
		if(list!=null && list.size()!=0) {
			return rechargeInfo = (RechargeInfo) this.convert2Bean((Map<String, Object>) list.get(0), new RechargeInfo());

		}

		return rechargeInfo;
	}
	public int updatePrepaidCBussinessId(Long id,Long newId){
		String sql="update CSMS_RechargeInfo set prepaidCBussinessId=? where prepaidCBussinessId=?";
		return update(sql, newId, id);
		//this.jdbcUtil.getJdbcTemplate().update(sql);
	}
	public String findGetOperTime(Integer operPlaceId) {
		String sql="select operTime from CSMS_RechargeInfo where placeId="+operPlaceId+" order by operTime";
		List list=queryList(sql);	
		String time = null;
		try {
			if(list!=null && list.size()!=0) {
				Map<String, Object> map = (Map<String, Object>) list.get(0);
				return time = map.get("operTime").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return time;
	}
		
	public int updateCorrectState(Long id) {
		String sql="update CSMS_RechargeInfo set IsCorrect='1' where id=? and IsCorrect='0' and nvl(IsDaySet,-1)!=1";
		return update(sql, id);
	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		StringBuffer sql=new StringBuffer("update CSMS_RechargeInfo set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(OperTime,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(OperTime,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		saveOrUpdate(sql.toString(),settleDay,startTime,endTime);
	}
	public static void main(String[] args) {
	}
}
