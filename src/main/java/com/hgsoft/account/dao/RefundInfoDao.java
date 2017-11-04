package com.hgsoft.account.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class RefundInfoDao  extends BaseDao{
	
	//资金争议期过后更新退款记录表里金额和状态（0>1由资金争议期变申请退款状态）
	public void updateStatusAndCurReBa(String auditStatus,BigDecimal currentRefundBalance,String cardNo){
		
		String sql = "update csms_refundInfo set auditStatus='"+auditStatus+"',"
				+ "currentRefundBalance= "+currentRefundBalance+" where refundType='1' and auditStatus = '1' and cardNo = '"+cardNo+"'";
    	update(sql);
	}
	public int[] batchUpdateStatusAndCurReBa(final List<Map<String,Object>> list) {  
        String sql = "update csms_refundInfo set auditStatus=?,currentRefundBalance= ? where refundType='1' and cardNo = ?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String,Object> map = list.get(i);
				ps.setString(1, "1");
				ps.setBigDecimal(2, (BigDecimal)map.get("BALANCE"));
				ps.setString(3, (String)map.get("CARDNO"));
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	public List<Map<String,Object>> findByAuditStatus(String auditStatus,String cardNo){
		String sql = "select r.mainAccountId,c.canceltime from csms_refundInfo r join csms_cancel c on r.cardNo=c.code where r.refundType='1' and r.auditStatus=? and r.cardNo=?";
		return queryList(sql,auditStatus,cardNo);
	}
	
	/*public RefundInfo findByCardNoAndId(String cardNo, Long customerId) {
		String sql = "select * from csms_refundInfo where cardno=? and mainid=?";
		List<Map<String,Object>> list = queryList(sql,cardNo,customerId);
		RefundInfo refundInfo = null;
		if(list.size()>0){
			refundInfo = new RefundInfo();
			convert2Bean(list.get(0), refundInfo);
		}
		return refundInfo;
	}*/
	
	/**
	 * 查找储值卡注销退款记录，可能有再次退款，所以有有可能有多条记录
	 * @param cardNo
	 * @param refundType
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findByCardNoAndType(String cardNo, String refundType){
		String sql = "select "+FieldUtil.getFieldMap(RefundInfo.class,new RefundInfo()).get("nameStr")+" from csms_refundInfo where cardno=? and refundType=?";
		List<Map<String,Object>> list = queryList(sql,cardNo,refundType);
		return list;
	}
	
	public Pager list(Pager pager,Date starTime, Date endTime, Customer customer){
		String sql="select c.organ as organ,r.frozenBalance as frozenBalance, r.ID as id ,MainID as mainId ,RefundType as refundType ,"
				+ "Balance as balance,AvailableBalance as availableBalance,"
				+ "AvailableRefundBalance as availableRefundBalance,RefundApproveBalance as refundApproveBalance,"
				+ " CurrentRefundBalance as currentRefundBalance, RefundApplyTime as refundApplyTime,"
				+ "AuditStatus as auditStatus ,bankNo,bankMember,bankOpenBranches,refundNo,refundName,AuditTime,RefundTime,ROWNUM as num "
				+ "from CSMS_RefundInfo r  join Csms_Customer c on r.mainid=c.id where 1=1   ";
		
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.refundApplyTime >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(endTime !=null){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.refundApplyTime <=to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(StringUtil.isNotBlank(customer.getOrgan())){
			params.like("c.organ", "%"+customer.getOrgan()+"%");
		}
		if(customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}
		if(StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("c.UserNo",customer.getUserNo());
		}
	
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by r.id desc ");
		return this.findByPages(sql, pager,Objects);	
	}
	public Pager list(Pager pager,String starTime, String endTime, Customer customer){
		String sql="select c.organ as organ,r.frozenBalance as frozenBalance, r.ID as id ,MainID as mainId ,RefundType as refundType ,"
				+ "Balance as balance,AvailableBalance as availableBalance,"
				+ "AvailableRefundBalance as availableRefundBalance,RefundApproveBalance as refundApproveBalance,"
				+ " CurrentRefundBalance as currentRefundBalance, RefundApplyTime as refundApplyTime,"
				+ "AuditStatus as auditStatus ,bankNo,bankMember,bankOpenBranches,refundNo,refundName,AuditTime,RefundTime,ROWNUM as num "
				+ "from CSMS_RefundInfo r  join Csms_Customer c on r.mainid=c.id where RefundType='3'   ";
		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(starTime)){
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.refundApplyTime >= to_date('"+starTime+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(StringUtil.isNotBlank(endTime)){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql=sql+(" and r.refundApplyTime <=to_date('"+endTime+"','YYYY-MM-DD HH24:MI:SS') ");
		}
		if(!StringUtil.isNotBlank(starTime) && !StringUtil.isNotBlank(endTime)){ //默认最近一个月
			 
			sql=sql+(" and to_char(r.refundApplyTime,'YYYY-MM-DD')  >= to_char(sysdate-30,'YYYY-MM-DD') ");
		}
		/*if(StringUtil.isNotBlank(customer.getOrgan())){
			params.like("c.organ", "%"+customer.getOrgan()+"%");
		}*/
		if(customer.getId()!=null){
			params.eq("c.id", customer.getId());
		}
		if(StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("c.UserNo",customer.getUserNo());
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by r.id desc ");
		return this.findByPages(sql, pager,Objects);	
	}
	

	public RefundInfo findById(Long id) {
		String sql="select "+FieldUtil.getFieldMap(RefundInfo.class,new RefundInfo()).get("nameStr")+" from CSMS_RefundInfo where ID=?";
		List list=queryList(sql, id);
		RefundInfo refundInfo = null;
		if(list!=null && list.size()!=0) {
			refundInfo = (RefundInfo) this.convert2Bean((Map<String, Object>) list.get(0), new RefundInfo());
		}

		return refundInfo;
	}
	
	public void save(RefundInfo refundInfo){
		refundInfo.setHisSeqId(-refundInfo.getId());
		/*StringBuffer sql=new StringBuffer("insert into CSMS_RefundInfo(");
		sql.append(FieldUtil.getFieldMap(RefundInfo.class,refundInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(RefundInfo.class,refundInfo).get("valueStr")+")");
		save(sql.toString());*/
		Map map = FieldUtil.getPreFieldMap(RefundInfo.class,refundInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_RefundInfo");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		
	}
	
/*	public void updateAuditStatus(RefundInfo refundInfo){
		StringBuffer sql = new StringBuffer("update CSMS_RefundInfo set ");
		if (refundInfo.getAuditStatus() == null) {
			sql.append("AuditStatus=NULL,");
		} else {
			sql.append("AuditStatus='" + refundInfo.getAuditStatus() + "',");
			
		}
	}*/
	
	public void updateBankInfo(RefundInfo refundInfo){
		RefundInfo refundInfo1= findById(refundInfo.getId());
		refundInfo1.setMemo(refundInfo.getMemo());
		refundInfo1.setBankNo(refundInfo.getBankNo());
		refundInfo1.setBankMember(refundInfo.getBankMember());
		refundInfo1.setRefundApplyTime(new Date());
		refundInfo1.setBankOpenBranches(refundInfo.getBankOpenBranches());
		
		Map map = FieldUtil.getPreFieldMap(RefundInfo.class,refundInfo1);
		StringBuffer sql=new StringBuffer("update CSMS_RefundInfo set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),refundInfo.getId());
		
		
		/*StringBuffer sql = new StringBuffer("update CSMS_RefundInfo set ");
		if (refundInfo.getMemo() == null) {
			sql.append("Memo=NULL,");
		} else {
			sql.append("Memo='" + refundInfo.getMemo() + "',");
			
		}
		if (refundInfo.getBankNo() == null) {
				sql.append("BankNo=NULL,");
			} else {
				sql.append("BankNo='" + refundInfo.getBankNo() + "',");
					
			}
		if (refundInfo.getBankMember() == null) {
			sql.append("BankMember=NULL,");
		} else {
			sql.append("BankMember='" + refundInfo.getBankMember() + "',");
				
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sql.append("RefundApplyTime=to_date('"+format.format((new Date()) )+"','YYYY-MM-DD HH24:MI:SS') "+" , ");
		
		if (refundInfo.getBankOpenBranches() == null) {
			sql.append("BankOpenBranches=NULL,");
		} else {
			sql.append("BankOpenBranches='" + refundInfo.getBankOpenBranches() + "' ");
				
		}
			
			sql =sql.append("where id=" + refundInfo.getId() );
			update(sql.toString());*/
		
	}
	public void update(RefundInfo refundInfo) {
		
		RefundInfo refundInfo1= findById(refundInfo.getId());
		refundInfo1.setAuditStatus(refundInfo.getAuditStatus());
		refundInfo1.setRefundId(refundInfo.getRefundId());
		refundInfo1.setRefundTime(refundInfo.getRefundTime());
		refundInfo1.setMemo(refundInfo.getMemo());
		refundInfo1.setHisSeqId(refundInfo.getHisSeqId());
		
		
		Map map = FieldUtil.getPreFieldMap(RefundInfo.class,refundInfo1);
		StringBuffer sql=new StringBuffer("update CSMS_RefundInfo set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),refundInfo.getId());
		
		/*StringBuffer sql = new StringBuffer("update CSMS_RefundInfo set ");
		String sqlString = "";
		
		if (refundInfo.getAuditStatus() == null) {
			sql.append("AuditStatus=NULL,");
		} else {
			sql.append("AuditStatus='" + refundInfo.getAuditStatus() + "',");
		}
		
		if (refundInfo.getRefundId() == null) {
			sql.append("RefundID=NULL,");
		} else {
			sql.append("RefundID='" + refundInfo.getRefundId() + "',");
		}
		
		if (refundInfo.getRefundTime() == null) {
			sql.append("RefundTime=NULL,");
		} else {
			sql.append("RefundTime=systime,");
		}
		
		
		if (refundInfo.getMemo() == null) {
			sql.append("Memo=NULL,");
		} else {
			sql.append("Memo='" + refundInfo.getMemo() + "',");
		}
		if (refundInfo.getHisSeqId() == null) {
			sql.append("HisSeqID=NULL,");
		} else {
			sql.append("HisSeqID='" + refundInfo.getHisSeqId() + "',");
		}
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += "where id='" + refundInfo.getId() + "'";
		update(sqlString);*/
	}
	
	/**
	 * 客服提供给营运的退款审批查询接口
	 */
	public Pager FindRefundList(Pager pager, String refundType, String refundApplyStartTime,
			String refundApplyEndTime, String auditStatus,Long refundId,String expireFlag,String queryFlag){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer("select c.id,c.Organ,c.UserNo,c.LinkMan,c.Tel,c.IdType,c.IdCode,r.ID refundId,"
				+ "r.MainID,r.MainAccountId,r.RefundType,r.CurrentRefundBalance,"
				+ "r.BankNo,r.BankMember,r.BankOpenBranches,r.OperNo,r.RefundApplyTime,r.RefundApplyOper RefundApplyPlaceId,"
				+ "r.PlaceNo RefundApplyNo,r.AuditNo,r.AuditName,r.AuditTime,r.AuditStatus,RefundTime,refundNo,refundName,refundFailReason,memo,"
				+ "r.cardAmt,r.cardSystemAmt,r.checkAmt,r.differentInfo,r.personalCorrectAmt,r.finalRefundAmt,r.expireFlag,r.waitSettleAuditTime "
				+ " from CSMS_RefundInfo r"
				+ " join CSMS_Customer c on r.MainID=c.id where 1=1 ");
		//queryFlag 0:营运审核列表    1：财务退款列表
		if("0".equals(queryFlag)){
			sql.append(" and r.AuditStatus in('2','3','4','7','9','10') ");
		}else if("1".equals(queryFlag)){
			sql.append(" and r.AuditStatus in('3','4','7') ");
		}
		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(refundType)){
			params.eq("r.RefundType", refundType);
		}
		if(StringUtil.isNotBlank(auditStatus)){
			params.eq("r.auditStatus", auditStatus);
		}
		if(StringUtil.isNotBlank(refundApplyStartTime)){
			params.geDate("r.RefundApplyTime", refundApplyStartTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(refundApplyEndTime)){
			params.leDate("r.RefundApplyTime", refundApplyEndTime+" 23:59:59");
		}
		if(refundId != null){
			params.eq("r.id", refundId);
		}
		if(StringUtil.isNotBlank(expireFlag)){
			params.eq("r.expireFlag", expireFlag);
		}
		
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by r.refundApplyTime desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	/**
	 * 客服提供给营运的退款审批详情查询
	 * @param refundId
	 * @return
	 */
	public Map<String, Object> findRefundInfoByID(Long refundId){
		String sql = "select c.Organ,c.IdType,c.IdCode,c.userNo,c.linkMan,c.tel,r.ID refundId,"
				+ "r.MainID,r.MainAccountId,r.RefundType,r.CurrentRefundBalance,"
				+ "r.BankNo,r.BankMember,r.BankOpenBranches,r.OperNo,r.RefundApplyTime,r.RefundApplyOper RefundApplyPlaceId,"
				+ "r.PlaceNo RefundApplyNo,r.AuditNo,r.AuditName,r.AuditTime,r.AuditStatus,RefundTime,refundNo,refundName,refundFailReason,memo,"
				+ "r.cardNo,r.cardAmt,r.cardSystemAmt,r.checkAmt,r.differentInfo,r.personalCorrectAmt,r.finalRefundAmt,r.expireFlag,r.directorAuditName,"
				+ "r.directorAuditTime,r.settleName,r.settleTime,r.waitSettleAuditTime "
				+ " from CSMS_RefundInfo r"
				+ " join CSMS_Customer c on r.MainID=c.id where r.ID=? ";
		List<Map<String, Object>> list = this.queryList(sql, refundId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 客服提供给营运的接口使用
	 * @param refundIds
	 * @return
	 */
	public Map<String, Map<String, Object>> findAllById(String refundIds) {
		StringBuffer sql = new StringBuffer("select * from CSMS_RefundInfo where 1=1 ");
		if (StringUtil.isNotBlank(refundIds)) {
			sql.append(" and id in( "+refundIds+" )");
		}
		List<Map<String, Object>> list=queryList(sql.toString());
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).get("id").toString(), list.get(i));
		}
		return map;
	}
	/**
	 * 跟新退款记录表记录
	 * @param refundInfo
	 */
	public void updateForRefundInterface(RefundInfo refundInfo){
		Map map = FieldUtil.getPreFieldMap(RefundInfo.class,refundInfo);
		StringBuffer sql=new StringBuffer("update CSMS_RefundInfo set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),refundInfo.getId());
	}
	
	public Pager findCancelRefundByCardNo(Pager pager,String cardNo){
		StringBuffer sql = new StringBuffer("select r.id rid,r.cardno,r.bankNo,r.bankMember,r.bankOpenBranches,r.auditStatus,r.refundTime,r.refundName  from  csms_refundInfo r  where r.refundtype='1' and r.AuditStatus!='8' ");
		SqlParamer param = new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			param.eq("r.cardno", cardNo);
		}
		sql.append(param.getParam());
		sql.append(" order by r.id desc ");
		return findByPages(sql.toString(), pager, param.getList().toArray());
	}
	
	/**
	 * 找到该储值卡所有退款记录
	 * @param pager
	 * @param cardNo
	 * @return Pager
	 */
	public Pager findByCardNo(Pager pager,String cardNo){
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(RefundInfo.class,new RefundInfo()).get("nameStr")+"  from  csms_refundInfo r  where RefundType='1' ");
		SqlParamer param = new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			param.eq("r.cardno", cardNo);
		}
		sql.append(param.getParam());
		sql.append(" order by id desc ");
		return findByPages(sql.toString(), pager, param.getList().toArray());
	}
	
	/**
	 * 找到该储值卡所有退款记录
	 * @param pager
	 * @param cardNo
	 * @return Pager
	 */
	public Pager findByCardNoForAMMS(Pager pager,String cardNo){
		StringBuffer sql = new StringBuffer("select r.id,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,r.auditStatus,r.currentRefundBalance,r.cardAmt,r.returnAmt,r.transferAmt,r.checkAmt,r.auditTime,r.refundTime,oc.name customPointName from csms_refundInfo r left join oms_custompoint oc on r.bussinessplaceid=oc.id  where r.RefundType='1' ");
		SqlParamer param = new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			param.eq("r.cardno", cardNo);
		}
		sql.append(param.getParam());
		sql.append(" order by id desc ");
		return findByPages(sql.toString(), pager, param.getList().toArray());
	}
	
	
	public void updateNotNull(RefundInfo refundInfo) {
		Map map = FieldUtil.getPreFieldMap(RefundInfo.class,refundInfo);
		StringBuffer sql=new StringBuffer("update CSMS_RefundInfo set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),refundInfo.getId());
	}
	
	public void updateAll(RefundInfo refundInfo){
		Map map = FieldUtil.getPreFieldMap(RefundInfo.class,refundInfo);
		StringBuffer sql=new StringBuffer("update CSMS_RefundInfo set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),refundInfo.getId());
	}
	
	
	/**
	 * 客服退款查询列表
	 * @param pager
	 * @param refundInfo
	 * @param refundApplyStartTime 退款申请开始时间
	 * @param refundApplyEndTime 退款结束时间
	 * @return Pager
	 */
	public Pager findRefundServicePager(Pager pager, RefundInfo refundInfo, String refundApplyStartTime,
			String refundApplyEndTime,Long bussinessPlaceId){
		StringBuffer sql = new StringBuffer(
				  " select c.organ,r.id refundInfoId,r.refundType,r.currentRefundBalance,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,"
				+ " r.auditStatus,r.directorAuditName,r.directorAuditTime,r.auditTime,r.refundTime,r.cardNo from CSMS_RefundInfo r"
				+ " join CSMS_Customer c on c.id=r.mainID where 1=1 ");
		SqlParamer param = new SqlParamer();
		
		param.eq("r.bussinessPlaceId", bussinessPlaceId);//操作员所属营业部
		
		//进入该界面。默认呈现当前状态为主任审批不通过的退款申请记录
		if(!StringUtil.isNotBlank(refundApplyStartTime) && !StringUtil.isNotBlank(refundApplyEndTime) && refundInfo == null){
			param.eq("r.auditStatus", "0");//审核状态主任审核不通过的记录
		}else{
			if(StringUtil.isNotBlank(refundApplyStartTime)){
				param.geDate("r.refundApplyTime", refundApplyStartTime);
			}
			if(StringUtil.isNotBlank(refundApplyEndTime)){
				param.leDate("r.refundApplyTime", refundApplyEndTime);
			}
			if(refundInfo != null){
				if(StringUtil.isNotBlank(refundInfo.getRefundType())){
					param.eq("r.refundType", refundInfo.getRefundType());
				}
				if(StringUtil.isNotBlank(refundInfo.getAuditStatus())){
					param.eq("r.auditStatus", refundInfo.getAuditStatus());
				}
			}
		}
		
		
		sql.append(param.getParam());
		sql.append(" order by r.id desc ");
		return findByPages(sql.toString(),pager, param.getList().toArray());
	}
	
	/**
	 * 客服退款查询列表
	 * @param pager
	 * @param refundInfo
	 * @param refundApplyStartTime 退款申请开始时间
	 * @param refundApplyEndTime 退款结束时间
	 * @return Pager
	 */
	public Pager findRefundServicePagerForAMMS(Pager pager,String bankCode,String cardType,RefundInfo refundInfo,String refundApplyStartTime,String refundApplyEndTime){
		StringBuffer sql = new StringBuffer(
				" select c.organ,r.id refundInfoId,r.refundType,r.currentRefundBalance,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,"
						+ " r.auditStatus,r.directorAuditName,r.directorAuditTime,r.auditTime,r.refundTime,r.cardNo from CSMS_RefundInfo r"
						+ " join CSMS_Customer c on c.id=r.mainID join CSMS_joinCardNoSection j on substr(r.cardno,0,length(r.cardno)-1) between j.code and j.endcode where 1=1 ");
		SqlParamer param = new SqlParamer();
		
		param.eq("j.bankno", bankCode);
		param.eq("j.cardtype", cardType);
		
		//进入该界面。默认呈现当前状态为主任审批不通过的退款申请记录
		if(!StringUtil.isNotBlank(refundApplyStartTime) && !StringUtil.isNotBlank(refundApplyEndTime) && refundInfo == null){
			param.eq("r.auditStatus", "0");//审核状态主任审核不通过的记录
		}else{
			if(StringUtil.isNotBlank(refundApplyStartTime)){
				param.geDate("r.refundApplyTime", refundApplyStartTime);
			}
			if(StringUtil.isNotBlank(refundApplyEndTime)){
				param.leDate("r.refundApplyTime", refundApplyEndTime);
			}
			if(refundInfo != null){
				if(StringUtil.isNotBlank(refundInfo.getRefundType())){
					param.eq("r.refundType", refundInfo.getRefundType());
				}
				if(StringUtil.isNotBlank(refundInfo.getAuditStatus())){
					param.eq("r.auditStatus", refundInfo.getAuditStatus());
				}
			}
		}
		
		
		sql.append(param.getParam());
		sql.append(" order by r.id desc ");
		return findByPages(sql.toString(),pager, param.getList().toArray());
	}
	
	/**
	 * 根据退款记录id查询详情
	 * @param refundInfoId
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findRefundServiceDetail(Long refundInfoId){
		String sql =  
				  " select c.id customerId,c.organ,c.idType,c.idCode,c.userType,c.secondNo,c.secondName,r.id refundInfoId,r.bussinessPlaceId bussinessPlaceId,"
				+ " r.refundType,r.currentRefundBalance,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,r.auditName,r.auditTime,"
				+ " r.auditStatus,r.directorAuditName,r.directorAuditTime,r.refundTime,r.cardNo,r.operNo,r.operName,r.placeName from CSMS_RefundInfo r"
				+ " join CSMS_Customer c on c.id=r.mainID where r.id=? ";
		List<Map<String, Object>> list = queryList(sql, refundInfoId);
		if(list.size() == 1) return list.get(0);
		else return null;
		
	}
}
