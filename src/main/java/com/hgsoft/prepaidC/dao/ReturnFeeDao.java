package com.hgsoft.prepaidC.dao;

import com.hgsoft.common.Enum.ReturnFeeStateEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ReturnFeeDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;
	
	public List<ReturnFee> findByCardNoState(String cardNo,String state) {
		String sql = "select id,cardNo,feeType,returnFee,state,bussinessID,returnTime from csms_ReturnFee where settlemonth is not null and cardNo=? and state =? order by inserttime ";
		return super.queryObjectList(sql, ReturnFee.class, cardNo, state);
	}

	public List<ReturnFee> findByCardNoStateIgnoreSettleMonth(String cardNo,String state) {
		String sql = "select id,cardNo,feeType,returnFee,state,bussinessID,returnTime,BalanceTime,InsertTime,SettleMonth from csms_ReturnFee where cardNo=? and state =? order by inserttime ";
		return super.queryObjectList(sql, ReturnFee.class, cardNo, state);
	}
	
	public List<ReturnFee> findByIDStr(String idStr) {
		String sql = "select id,cardNo,feeType,returnFee,state,bussinessID,returnTime from csms_ReturnFee where id in("+idStr+")";
		List<Map<String, Object>> list = queryList(sql.toString());
		return super.queryObjectList(sql, ReturnFee.class);
	}
	
	public List<ReturnFee> findByBussinessID(Long bussinessID) {
		String sql = "select id,cardNo,feeType,returnFee,state,bussinessID,returnTime from csms_ReturnFee where BussinessID=?";
		return super.queryObjectList(sql, ReturnFee.class, bussinessID);
	}
	
	public void update(ReturnFee returnFee) {
		/*StringBuffer sql=new StringBuffer("update CSMS_ReturnFee set ");
		sql.append(FieldUtil.getFieldMap(ReturnFee.class,returnFee).get("nameAndValue")+" where id="+returnFee.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(ReturnFee.class,returnFee);
		StringBuffer sql=new StringBuffer("update CSMS_ReturnFee set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), returnFee.getId());
	}
	
	public void updateByBussinessID(String state,Long bussinessID){
		String sql="update CSMS_ReturnFee set state=? where bussinessID=?";
		//update(sql.toString());
		saveOrUpdate(sql, state, bussinessID);
	}

	/*public void updateCardno(String prepaidCCardno, String newPrepaidCCardno) {
		//String sql="update CSMS_ReturnFee set CardNo='"+newPrepaidCCardno+"' where cardno='"+prepaidCCardno+"' and State=1";
		String sql="update CSMS_ReturnFee set CardNo=? where cardno=? and State=1";
		//update(sql.toString());
		saveOrUpdate(sql, newPrepaidCCardno, prepaidCCardno);
	}*/
	
	public void saveByMap(Map<String,Object> m,Long prepaidBussinessId){
		ReturnFee returnFee=new ReturnFee();
		
		returnFee.setId(sequenceUtil.getSequenceLong("SEQ_CSMSRreturnFee_NO"));
		returnFee.setBussinessID(prepaidBussinessId);
		returnFee.setFeeType(m.get("FEETYPE").toString());
		returnFee.setCardNo(m.get("CARDNO").toString());
		returnFee.setReturnFee((BigDecimal)m.get("RETURNFEE"));
		returnFee.setReturnTime((Date)m.get("RETURNTIME"));
		
		StringBuffer sql=new StringBuffer("insert into csms_returnfee(");
		sql.append(FieldUtil.getFieldMap(ReturnFee.class,returnFee).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(ReturnFee.class,returnFee).get("valueStr")+")");
		save(sql.toString());
	}

	/**
	 * 根据卡号查找回退金额总数
	 * 类型是   回退金额
	 * 状态是   未使用
	 * @param cardNo
	 * @return
	 */
	public BigDecimal findSumReturnByCardNo(String cardNo){
		String sql = "select sum(returnfee) sumReturnFee from csms_returnfee " +
				"where cardno=? and settleMonth is not null and state = '1'";
		List<Map<String, Object>> list = queryList(sql, cardNo);
		BigDecimal returnFee;
		if (!list.isEmpty()) {
			if (list.get(0).get("sumReturnFee") == null) {
				return BigDecimal.valueOf(0);
			}
			returnFee = ((BigDecimal) list.get(0).get("sumReturnFee"))
					.setScale(0, BigDecimal.ROUND_DOWN);
			return returnFee;
		}
		return BigDecimal.valueOf(0);
	}

	public void updateState(String cardNo, String state) {
		String sql = "update csms_returnfee set state=? " +
				"where cardno=? and settleMonth is not null and state = '1'";
		update(sql, state, cardNo);
	}

	/*public List<ReturnFee> findByCardNoState(String cardNo,String feeType, String state) {
		ReturnFee temp = null;
		List<ReturnFee> returnFeeList = new ArrayList<ReturnFee>();
		String sql = "select id,cardNo,feeType,returnFee,state,bussinessID,returnTime from csms_ReturnFee where cardNo=? and feeType=? and state =? order by id desc";
		List<Map<String, Object>> list = queryList(sql.toString(),cardNo,feeType,state);
		for (Map<String, Object> map : list) {
			temp = new ReturnFee();
			this.convert2Bean(map, temp);
			returnFeeList.add(temp);
		}
		return returnFeeList;
	}*/

	public int updateLockState(Long businessId, Date returntime, Long id) {
		String sql = "update CSMS_ReturnFee set state=?,bussinessid=?,returntime=? where settlemonth is not null and state=? and id=?";
		return update(sql, ReturnFeeStateEnum.rechargeLocked.getValue(), businessId, returntime, ReturnFeeStateEnum.notUse.getValue(), id);
	}

	/*public int updateRechargeSuccessState(Date returntime, Long id) {
		String sql = "update CSMS_ReturnFee set state=?,returntime=? where settlemonth is not null and state=? and id=?";
		return update(sql, ReturnFeeStateEnum.recharged.getValue(), returntime, ReturnFeeStateEnum.rechargeLocked.getValue(), id);
	}*/

	public void updateRechargeSuccessState(Date returntime, Long businessId) {
		String sql = "update CSMS_ReturnFee set state=?,returntime=? where bussinessid=?";
		update(sql, ReturnFeeStateEnum.recharged.getValue(), returntime, businessId);
	}

	public void updateNotUseState(Long businessId) {
		String sql = "update CSMS_ReturnFee set state=?,returntime=null,bussinessid=null where bussinessid=?";
		update(sql, ReturnFeeStateEnum.notUse.getValue(), businessId);
	}

	public int save(ReturnFee returnFee) {
		if (returnFee.getId() == null) {
			returnFee.setId(sequenceUtil.getSequenceLong("seq_csmsreturnfee_no"));
		}
		Map map = FieldUtil.getPreFieldMap(ReturnFee.class, returnFee);
		StringBuffer sql = new StringBuffer(" insert into CSMS_ReturnFee ");
		sql.append(map.get("insertNameStr"));
		return saveOrUpdate(sql.toString(), (List) map.get("param"));
	}

	public int updateGainCardState(Long businessId, Date returntime, Long id) {
		String sql = "update CSMS_ReturnFee set state=?,bussinessid=?,returntime=? where state=? and id=?";
		return update(sql, ReturnFeeStateEnum.gainCard.getValue(), businessId, returntime, ReturnFeeStateEnum.notUse.getValue(), id);
	}

}
