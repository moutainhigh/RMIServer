package com.hgsoft.account.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.utils.Pager;

@Repository
public class SubAccountInfoDao extends BaseDao{
	private static Logger logger = Logger.getLogger(SubAccountInfoDao.class.getName());
	
	public void save(SubAccountInfo subAccountInfo) {
		subAccountInfo.setHisSeqId(-subAccountInfo.getId());
		Map map = FieldUtil.getPreFieldMap(SubAccountInfo.class,subAccountInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_SUBACCOUNT_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	/*	StringBuffer sql=new StringBuffer("insert into CSMS_SUBACCOUNT_INFO(");
		sql.append(FieldUtil.getFieldMap(SubAccountInfo.class,subAccountInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(SubAccountInfo.class,subAccountInfo).get("valueStr")+")");
		save(sql.toString());*/
	}

	public void delete(Long id) {
		String sql="delete from CSMS_SUBACCOUNT_INFO where id=?";
		super.delete(sql, id);
	}

	public void update(SubAccountInfo subAccountInfo) {
		Map map = FieldUtil.getPreFieldMap(SubAccountInfo.class,subAccountInfo);
		StringBuffer sql=new StringBuffer("update CSMS_SUBACCOUNT_INFO set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), subAccountInfo.getId());
		/*StringBuffer sql=new StringBuffer("update CSMS_SUBACCOUNT_INFO set ");
		sql.append(FieldUtil.getFieldMap(SubAccountInfo.class,subAccountInfo).get("nameAndValue")+" where id="+subAccountInfo.getId());
		update(sql.toString());*/
	}

	public SubAccountInfo findById(Long id) {
		String sql = "select * from CSMS_SUBACCOUNT_INFO where id = ?";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, id);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}

	public List<Map<String, Object>> findAll(SubAccountInfo subAccountInfo) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select * from CSMS_SUBACCOUNT_INFO where 1=1 ");
		if (subAccountInfo == null) {
			logger.warn("数据异常：subAccountInfoDao.findAll方法查询没有条件");
			return new ArrayList<Map<String, Object>>(0);
		}
		Map map = FieldUtil.getPreFieldMap(SubAccountInfo.class, subAccountInfo);
		String condition = (String)map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			logger.warn("数据异常：subAccountInfoDao.findAll方法查询没有条件");
			throw new ApplicationException("数据异常：subAccountInfoDao.findAll方法查询条件为空");
		}
		sql.append(condition);
		list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
		return list;
		/*List list = new ArrayList<SubAccountInfo>();
		StringBuffer sql = new StringBuffer("select * from CSMS_SUBACCOUNT_INFO where 1=1 ");
		if (subAccountInfo != null) {
			sql.append(FieldUtil.getFieldMap(SubAccountInfo.class,subAccountInfo).get("nameAndValueNotNull"));
		}
		list=queryList(sql.toString());
		return list;*/
	}

	public Pager findByPage(Pager pager,SubAccountInfo subAccountInfo) {
		StringBuffer sql = new StringBuffer("select t.*,ROWNUM as num  from CSMS_SUBACCOUNT_INFO t where 1=1");
		Map map = FieldUtil.getPreFieldMap(SubAccountInfo.class,subAccountInfo);
		if (subAccountInfo != null) {
			sql.append(map.get("selectNameStrNotNullAndWhere"));
		}
		sql.append(" order by id ");
		return this.findByPages(sql.toString(), pager,((List) map.get("paramNotNull")).toArray());
	
	/*	if (subAccountInfo != null) {
			sql.append(FieldUtil.getFieldMap(SubAccountInfo.class,subAccountInfo).get("nameAndValueNotNull"));
		}
		return this.findByPages(sql.toString(), pager,null);*/
	}

	public SubAccountInfo find(SubAccountInfo subAccountInfo) {
		if (subAccountInfo == null) {
			logger.warn("数据异常：subAccountInfoDao.find方法查询没有条件");
			return null;
		}

		StringBuffer sql = new StringBuffer("select * from CSMS_SUBACCOUNT_INFO where 1=1 ");
		Map map = FieldUtil.getPreFieldMap(SubAccountInfo.class,subAccountInfo);
		String condition = (String)map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)){
			logger.warn("数据异常：subAccountInfoDao.find方法查询没有条件");
			throw new ApplicationException("数据异常：subAccountInfoDao.find方法查询条件为空");
		}
		sql.append(condition);
		sql.append(" order by id");
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql.toString(), SubAccountInfo.class, ((List) map.get("paramNotNull")).toArray());
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}
	
	@SuppressWarnings("rawtypes")
	public SubAccountInfo findByMainIdAndType(Long mainId,String subAccountType) {
		String sql="select ID,MAINID,SUBACCOUNTNO,SUBACCOUNTTYPE,APPLYID,OPERID,PLACEID,OPERTIME,HISSEQID from CSMS_SUBACCOUNT_INFO where mainID=? and SUBACCOUNTTYPE=?";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, mainId, subAccountType);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}
	/**
	 * 返回对应申请id的最新一条子账户数据，该数据只有子账户号一个字段
	 * @param customerId 根据传进来的申请表id一一对应记帐卡子账户
	 * @return
	 */
	public SubAccountInfo findLastDateSub(Long customerId){
		String sql = "select * from(select s.subAccountNo,s.OperTime,subAccountType,applyid,"
				+ "customerid,rownum from csms_subaccount_info s join "
				+ "csms_accountc_apply a on s.applyid=a.id where a.customerid=? and subAccountType='2' order by s.OperTime desc) where rownum=1" ;

		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, customerId);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}
	
	public SubAccountInfo findByApplyId(Long applyId){
		String sql = "select * from CSMS_SUBACCOUNT_INFO where applyID=?";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, applyId);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}
	public SubAccountInfo findByBankAccount(String bankAccount){
		String sql = "select s.* from CSMS_SUBACCOUNT_INFO s join csms_accountc_apply a on s.applyid=a.id  where a.bankAccount=? and shutDownStatus='0' ";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, bankAccount);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}

	public SubAccountInfo findByCardNo(String cardno){
		String sql = "select s.* from CSMS_SUBACCOUNT_INFO s join CSMS_AccountC_info ai on s.id=ai.AccountID  where ai.CardNo=?";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, cardno);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}
	
	public SubAccountInfo findByPrepaidCNo(String cardNo){
		String sql = "select csi.* from CSMS_SubAccount_Info csi join csms_prepaidc cp on csi.id = cp.accountid where csi.subAccountType = 1 and cp.cardno = ?";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, cardNo);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}

	public SubAccountInfo findByCustomerIdAndType(Long customerId,String subAccountType) {
		String sql="select s.id,s.mainid,s.subaccountno,s.subaccounttype,s.applyid,s.operid,s.placeid,s.opertime,s.hisseqid from"
				+ " CSMS_SUBACCOUNT_INFO s left join csms_mainaccount_info m on(s.mainid=m.id) where m.mainid=? and s.subaccounttype=?";
		List<SubAccountInfo> subAccountInfos = super.queryObjectList(sql, SubAccountInfo.class, customerId, subAccountType);
		if (subAccountInfos == null || subAccountInfos.isEmpty()) {
			return null;
		}
		return subAccountInfos.get(0);
	}
	
	/**
	 * 子账户保证金金额变动（保证金余额与保证金金额）
	 * @param changeBail 变动金额      传入正数为新增，传入负数为减少
	 * @return int
	 */
	public int updateBail(Long subId,BigDecimal changeBail){
		String sql = "update CSMS_SUBACCOUNT_INFO set BailBalance=BailBalance+?,BailFee=BailFee+? where id=?";
		if(changeBail.compareTo(BigDecimal.ZERO) < 0){
			//防止出现负数
			sql = sql + " and BailBalance >= ? and BailFee >= ? ";
			return this.update(sql, changeBail,changeBail,subId,changeBail.abs(),changeBail.abs());
		}else{
			return this.update(sql, changeBail,changeBail,subId);
		}
	}
	
	/**
	 * 子账户保证金金额变动:扣除保证金到冻结保证金
	 * 传入正数:保证金 --> 冻结保证金
	 * 传入负数  冻结保证金 --> 保证金金额和保证金余额	
	 * @param subId
	 * @param changeBail
	 * @return int
	 */
	public int updateBail2Frozen(Long subId,BigDecimal changeBail){
		String sql = "update CSMS_SUBACCOUNT_INFO set BailBalance=BailBalance-?,BailFee=BailFee-?,BailFrozenBalance=BailFrozenBalance+? where id=?";
		if(changeBail.compareTo(BigDecimal.ZERO) > 0){
			sql = sql + " and BailBalance >= ? and BailFee >= ? ";
			return this.update(sql, changeBail,changeBail,changeBail,subId,changeBail.abs(),changeBail.abs());
		}else if(changeBail.compareTo(BigDecimal.ZERO) < 0){
			sql = sql + " and BailFrozenBalance >= ? ";
			return this.update(sql, changeBail,changeBail,changeBail,subId,changeBail.abs());
		}else{
			return this.update(sql, changeBail,changeBail,changeBail,subId);
		}
		//saveOrUpdate(sql, changeBail,changeBail,changeBail,subId);
	}
	/**
	 * 直接扣除子账户冻结保证金
	 * 传入正数：扣除冻结保证金
	 * @param subId
	 * @param changeBail
	 * @return int
	 */
	public int updateFrozenBalance(Long subId,BigDecimal changeBail){
		String sql = "update CSMS_SUBACCOUNT_INFO set BailFrozenBalance=BailFrozenBalance-? where id=? and BailFrozenBalance >= ?";
		return this.update(sql,changeBail,subId,changeBail.abs());
	}
	
}
