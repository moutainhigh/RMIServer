package com.hgsoft.prepaidC.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CancelDao extends BaseDao{

	private static Logger logger = LoggerFactory.getLogger(CancelDao.class);
	
	public Pager findByCancelId(Pager pager,Cancel cancel,Long customerId){
		StringBuffer sql = new StringBuffer("select c.id cid,c.reason,c.remark,r.cardno,c.cancelTime,d.cardAmt,r.bankNo,r.bankMember,r.bankOpenBranches,r.auditStatus,r.refundTime,r.refundName from csms_cancel c join CSMS_DBASCARDFLOW d on d.newcardno=c.code join csms_refundinfo r on c.code=r.cardno where d.cardtype='22' and d.sertype in ('01','03') and r.refundtype='1' and c.flag='1' and c.customerid="+customerId);
		SqlParamer param = new SqlParamer();
		if(cancel!=null){
			if(cancel.getId()!=null){
				param.eq("c.id",cancel.getId());
			}
			if(StringUtil.isNotBlank(cancel.getCode())){
				param.eq("c.code",cancel.getCode());
			}
		}
		sql.append(param.getParam());
		sql.append(" order by r.refundTime");
		return findByPages(sql.toString(),pager, param.getList().toArray());
	}
	
	public Pager findByCodeAndCusID(Pager pager,Cancel cancel,Customer customer){
		StringBuffer sql = new StringBuffer("select c.id cid,r.id rid,c.reason,c.remark,c.operName,r.cardno,c.cancelTime,r.bankNo,r.bankMember,r.bankOpenBranches,r.auditStatus,r.refundTime,r.refundName  from csms_cancel c join csms_refundInfo r on c.code=r.cardno where flag='1' and r.refundtype='1' and r.AuditStatus!='8' ");
		SqlParamer param = new SqlParamer();
		if(cancel!=null && StringUtil.isNotBlank(cancel.getCode())){
			param.eq("c.code", cancel.getCode());
		}
		if(customer!=null && customer.getId()!=null){
			param.eq("c.customerId", customer.getId());
		}
		sql.append(param.getParam());
		sql.append(" order by cancelTime");
		return findByPages(sql.toString(), pager, param.getList().toArray());
	}
	
	/**
	 * 自营系统查询储值卡注销记录
	 * @param pager
	 * @param cancel
	 * @param customer
	 * @return Pager
	 */
	public Pager findCancelList(Pager pager,Cancel cancel,Customer customer){
		StringBuffer sql = new StringBuffer(
				  " select c.id cid,c.reason,c.remark,c.operName,c.cancelTime,c.code cardno,c.refundState,p.invoiceprint,"
		        + " r.id rid,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,r.auditTime,r.auditStatus,r.refundTime,r.refundName,r.currentRefundBalance,r.cardAmt,r.returnAmt,r.transferAmt,r.checkAmt from csms_cancel c "
		        + " left join (select id,cardno,bankNo,bankMember,bankOpenBranches,refundApplyTime,auditTime,auditStatus,refundTime,refundName,currentRefundBalance,cardAmt,returnAmt,transferAmt,checkAmt "
		        + "   from (select id,cardno,bankNo,bankMember,bankOpenBranches,refundApplyTime,auditTime,auditStatus,refundTime,refundName,currentRefundBalance,cardAmt,returnAmt,transferAmt,checkAmt,ROW_NUMBER() "
		        + "     OVER(PARTITION BY cardno ORDER BY id DESC) RN from csms_refundinfo) where RN = 1) r on r.cardno=c.code left join csms_prepaidc p on c.code=p.cardno"
		        + " where c.flag='1' ");
		SqlParamer param = new SqlParamer();
		if(cancel!=null && StringUtil.isNotBlank(cancel.getCode())){
			param.eq("c.code", cancel.getCode());
		}
		if(customer!=null && customer.getId()!=null){
			param.eq("c.customerId", customer.getId());
		}
		sql.append(param.getParam());
		sql.append(" order by cancelTime desc");
		return findByPages(sql.toString(), pager, param.getList().toArray());
	} 
	
	public Pager findByCodeAndCusIDForAMMS(Pager pager,Cancel cancel,Customer customer,String cardType,String bankCode){
		StringBuffer sql = new StringBuffer(
				  " select c.id cid,c.reason,c.remark,c.operName,c.cancelTime,c.code cardno,c.refundState,"
		        + " r.id rid,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,r.auditTime,r.auditStatus,r.refundTime,r.refundName,r.currentRefundBalance,r.cardAmt,r.returnAmt,r.transferAmt,r.checkAmt from csms_cancel c "
		        + " left join (select id,cardno,bankNo,bankMember,bankOpenBranches,refundApplyTime,auditTime,auditStatus,refundTime,refundName,currentRefundBalance,cardAmt,returnAmt,transferAmt,checkAmt "
		        + "   from (select id,cardno,bankNo,bankMember,bankOpenBranches,refundApplyTime,auditTime,auditStatus,refundTime,refundName,currentRefundBalance,cardAmt,returnAmt,transferAmt,checkAmt,ROW_NUMBER() "
		        + "     OVER(PARTITION BY cardno ORDER BY id DESC) RN from csms_refundinfo) where RN = 1) r on r.cardno=c.code join CSMS_joinCardNoSection j on substr(c.code,0,length(c.code)-1) between j.code and j.endcode"
		        + " where c.flag='1' ");
		SqlParamer param = new SqlParamer();
		param.eq("j.cardtype", cardType);
		param.eq("j.bankno", bankCode);
		if(cancel!=null && StringUtil.isNotBlank(cancel.getCode())){
			param.eq("c.code", cancel.getCode());
		}
		if(customer!=null && customer.getId()!=null){
			param.eq("c.customerId", customer.getId());
		}
		sql.append(param.getParam());
		sql.append(" order by cancelTime desc");
		return findByPages(sql.toString(), pager, param.getList().toArray());
	}


	public void save(Cancel cancel) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_Cancel(");
		sql.append(FieldUtil.getFieldMap(Cancel.class,cancel).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Cancel.class,cancel).get("valueStr")+")");
		save(sql.toString());*/
		/*Map map = FieldUtil.getPreFieldMap(Cancel.class,cancel);
		StringBuffer sql=new StringBuffer("insert into CSMS_Cancel");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));*/
		StringBuffer sql=new StringBuffer("insert into CSMS_Cancel(");
		sql.append(FieldUtil.getFieldMap(Cancel.class,cancel).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Cancel.class,cancel).get("valueStr")+")");
		save(sql.toString());
	}

	public void delete(Long id) {
		String sql="delete from CSMS_Cancel where id=?";
		super.delete(sql, id);
	}

	public void update(Cancel cancel) {
		/*StringBuffer sql=new StringBuffer("update CSMS_Cancel set ");
		sql.append(FieldUtil.getFieldMap(Cancel.class,cancel).get("nameAndValue")+" where id="+cancel.getId());
		update(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(Cancel.class,cancel);
		StringBuffer sql=new StringBuffer("update CSMS_Cancel set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), cancel.getId());
	}
	
	public void updateNotNull(Cancel cancel) {
		Map map = FieldUtil.getPreFieldMap(Cancel.class,cancel);
		StringBuffer sql=new StringBuffer("update CSMS_Cancel set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),cancel.getId());
	}

	public Cancel findById(Long id) {
		String sql = "select * from CSMS_Cancel where id=?";
		List<Cancel> cancels = super.queryObjectList(sql, Cancel.class, id);
		if (cancels == null || cancels.isEmpty()) {
			return null;
		}
		return cancels.get(0);
	}
	public List<Cancel> findCancelLists(String customerId,String flag,String code,String creditCardNo,String bankCode,String source){
		String sql = "SELECT * FROM CSMS_Cancel c LEFT JOIN CSMS_joinCardNoSection j ON substr(c.code, 1, 15) BETWEEN j.CODE AND j.ENDCODE AND j.bankno = ? WHERE c.CustomerID = ? AND c.flag = ? AND c.code = ? AND c.creditCardNo = ? AND c.source = ?";
		return super.queryObjectList(sql, Cancel.class, bankCode, customerId, flag, code, creditCardNo, source);
	}


	public Cancel findByCode(String code) {
		String sql = "select * from CSMS_Cancel where code=? order by id desc fetch first 1 rows only";
		List<Cancel> cancels = super.queryObjectList(sql, Cancel.class, code);
		if (cancels == null || cancels.isEmpty()) {
			return null;
		}
		return cancels.get(0);
	}

	public List<Map<String, Object>> findAll(Cancel cancel) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(0);
		if (cancel == null) {
			return list;
		}
		Map map = FieldUtil.getPreFieldMap(Cancel.class,cancel);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("CancelDao.findAll条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_Cancel where 1=1 ");
		sql.append(condition);
		list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());

		return list;
	}

	public Pager findByPage(Pager pager,Cancel cancel) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_Cancel t where 1=1");
		if (cancel != null) {
			//sql.append(FieldUtil.getFieldMap(Cancel.class,cancel).get("nameAndValueNotNull"));
			Map map = FieldUtil.getPreFieldMap(Cancel.class,cancel);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
		}else{
			sql.append(" order by id ");
			return this.findByPages(sql.toString(), pager,null);
		}
		
	}

	public Cancel find(Cancel cancel) {
		if (cancel == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(Cancel.class,cancel);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("CancelDao.find条件为空");
		}

		StringBuffer sql = new StringBuffer("select * from CSMS_Cancel where 1=1 ");
		sql.append(condition);
		sql.append(" order by id");
		List<Cancel> cancels = super.queryObjectList(sql.toString(), Cancel.class, ((List) map.get("paramNotNull")).toArray());
		if (cancels == null || cancels.isEmpty()) {
			return null;
		} else if (cancels.size() > 1) {
			logger.error("CancelDao.find有多条[{}]记录", cancels.size());
		}
		return cancels.get(0);
	}
	/**
	 * 联名卡（信用卡）服务的
	 * @param pager
	 * @param customerId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Pager findCreditCardCancelListByPage(Pager pager,String code,String endcode, Long customerId, String startTime, String endTime,String flag,String source) {
		StringBuffer sql = new StringBuffer("select Code,CreditCardNo,CancelTime,row_number() over (order by id desc) as num from csms_cancel where substr(code,1,15) between '" + code + "' and '" + endcode + "'");
		sql.append(" and flag = "+ flag + "and CustomerID = "+customerId);
		sql.append(" and source = "+source);
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" and CancelTime>=to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" and CancelTime<=to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.findByPages(sql.toString(), pager,null);
	}
	
}
