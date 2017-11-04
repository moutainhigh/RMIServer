package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.Migrate;
import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class MigrateDao extends BaseDao{

	private static final Logger logger = LoggerFactory.getLogger(MigrateDao.class);

	public Map<String,Object> findIdByCardNo(String cardNo, String appState){
		String sql = "select m.id from csms_migrate m join csms_migrate_detail md on md.migrateid=m.id where md.cardno=? and m.appstate=?";
		List<Map<String,Object>> list = queryList(sql,cardNo,appState);
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	public int[] batchUpdateAppState(final List<Map<String,Object>> list) {  
        String sql =  "update csms_migrate set appstate= ? where id = ?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				Map<String, Object> map = list.get(i);
				ps.setString(1, "6");//修改状态为：扣款账户变更成功
				ps.setLong(2, ((BigDecimal) map.get("ID")).longValue());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
    }
	
	public List<Map<String,Object>> findByAppState(String appState){
		StringBuffer sql = new StringBuffer("select m.opertime opertime,m.reqtime reqtime,ai.state state,ai.customerid customerid,md.cardno cardno,m.APPROVER APPROVER,m.APPROVERNAME APPROVERNAME,m.APPROVERNO APPROVERNO,m.OldAccountID OldAccountID,m.NewAccountID NewAccountID from csms_migrate m join csms_migrate_detail md on md.migrateid=m.id join csms_accountc_info ai on ai.cardno=md.cardno where 1=1 and m.appState=? order by opertime desc ");
		return queryList(sql.toString(), appState);
	}

	public void save(Migrate migrate) {
		/*StringBuffer sql=new StringBuffer("insert into CSMS_Migrate(");
		sql.append(FieldUtil.getFieldMap(Migrate.class,migrate).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(Migrate.class,migrate).get("valueStr")+")");
		save(sql.toString());*/
		
		
		Map map = FieldUtil.getPreFieldMap(Migrate.class,migrate);
		StringBuffer sql=new StringBuffer("insert into CSMS_Migrate");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	public Migrate findById(Long id){
		String sql = "select * from csms_migrate  where id=?";
		List<Migrate> migrates = super.queryObjectList(sql, Migrate.class, id);
		if (migrates == null || migrates.isEmpty()) {
			return null;
		}
		return migrates.get(0);
	}
	public Pager findByPager(Pager pager,Date starTime ,Date endTime, Migrate migrate,Customer customer,String newBankAccount,String oldBankAccount) {
		StringBuffer sql=new StringBuffer(" select me.ID, me.OldAccountID, me.NewAccountID, "
				+" me.Flag, me.AppState, me.EffectTime, me.Approver, me.AppTime, me.Remark, me.OperID, "
				+" me.PlaceID, me.Reqtime, me.userno, me.oldBankAccount, me.newBankAccount, me.OperTime "
				+" from (select m.ID,m.OldAccountID,m.NewAccountID,"
				+" m.Flag,m.AppState,m.EffectTime,m.Approver,m.AppTime,m.Remark,m.OperID,"
				+" m.PlaceID,m.Reqtime,c.userno,aa.bankAccount oldBankAccount,"
				+" (select bankAccount from CSMS_SubAccount_Info s join CSMS_AccountC_apply aa "
				+" on aa.id = s.applyid  where s.id = m.NewAccountID) newBankAccount, m.OperTime "
				+" from CSMS_SubAccount_Info sb join CSMS_MainAccount_Info  mi on sb.mainId = mi.id  join csms_customer c on mi.mainid = c.id "
				+" join CSMS_AccountC_apply aa on aa.id = sb.applyid"
				+" join csms_migrate m on sb.id = m.oldaccountid) me where 1=1 ");
		SqlParamer params=new SqlParamer();
		/*if(starTime !=null){
			params.geDate("OperTime", params.getFormat().format(starTime));
			params.leDate("OperTime", params.getFormat().format(endTime));
		}*/
		if(starTime !=null){
			params.geDate("me.Reqtime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("me.Reqtime", params.getFormatEnd().format(endTime));
		}
		if(migrate.getOldAccountId()!=null){
			params.eq("me.OldAccountID", migrate.getOldAccountId());
		}
		if(migrate.getNewAccountId()!=null){
			params.eq("me.NewAccountID", migrate.getNewAccountId());
		}
		if(StringUtil.isNotBlank(oldBankAccount)){
			params.eq("me.oldBankAccount",oldBankAccount);
		}
		if(StringUtil.isNotBlank(newBankAccount)){
			params.eq("me.newBankAccount", newBankAccount);
		}
		if(StringUtil.isNotBlank(migrate.getFlag())){
			params.eq("me.Flag",migrate.getFlag());
		}
		if(StringUtil.isNotBlank(migrate.getAppState())){
			params.eq("me.AppState",migrate.getAppState());
		}
		if(migrate.getId()!=null){
			params.eq("me.id", migrate.getId());
		}
		if(StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("me.userNo", customer.getUserNo());
		}
		
		sql.append(params.getParam());
		sql.append(" order by me.OperTime desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
		
	}

	
	//营运接口
	
	
	
	public Pager migrateList(Pager pager, Customer customer,Long migrateId,String bankAccount, String startTime,
			String endTime, String state) {
		
		/*StringBuffer sql = new StringBuffer(" select * from (select m.ID,m.OldAccountID,m.NewAccountID,"
				//迁移信息
				+" m.Flag,m.AppState,m.EffectTime,m.Approver,m.AppTime,m.Remark,m.OperID,"
				+" m.PlaceID,m.OperTime"
				//客户信息
				+ ",c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.LinkMan,c.upDateTime,c.firRunTime,"
				//银行账户
				+ "aa.bankAccount oldBankAccount,"
				+" (select bankAccount from CSMS_AccountC_apply a1 join CSMS_SubAccount_Info s "
				+" on a1.id = s.applyid  where s.id = m.NewAccountID) newBankAccount ,"
				+ "aa.reqCount AccountCApplyreqCount,aa.residueCount AccountCApplyresidueCount,"
				+ "aa.accName,aa.AccountType,aa.MaxAcr,aa.bankName,aa.VirType,aa.bail,aa.truckbail,aa.bank,"
				+" row_number() over (order by m.OperTime desc) as num "
				+" from csms_migrate m "
				+ " left join CSMS_SubAccount_Info sb on sb.id = m.oldaccountid"
				+" left join CSMS_AccountC_apply aa on aa.id = sb.applyid"
				+" left join csms_customer c on aa.customerid = c.id) where 1=1 ");*/

		StringBuffer sql = new StringBuffer("select id,userNo,organ,idcode,mobile,newBankAccount,oldBankAccount,reqtime,appstate from (select "
				//客户信息
				+ "c.userNo,c.organ,"
				+ "c.idCode,c.mobile,"
				//迁移信息
				+ " m.id,"
				+ "aa.bankAccount as newBankAccount,"
//				+ " (select aa.bankAccount from CSMS_AccountC_apply aa join CSMS_SubAccount_Info si on si.ApplyID=aa.id and si.id=m.NewAccountID) as newBankAccount,"
				+ " (select aa.bankAccount from CSMS_AccountC_apply aa join CSMS_SubAccount_Info si on si.ApplyID=aa.id and si.id=m.OldAccountID) as oldBanKAccount,"
				+" m.Reqtime,m.AppState"
				+" from csms_migrate m "
				+" left join CSMS_SubAccount_Info si on si.id=m.NewAccountID "
				+" left join CSMS_AccountC_apply aa on si.ApplyID=aa.id "
//				+ "left join CSMS_Migrate_detail md on md.MigrateID = m.id"
//				+" left join CSMS_AccountC_info ai on ai.cardno = md.cardno"
				+" left join csms_customer c on aa.customerID = c.id"
				+" ) mi where 1=1 ");

		if(customer != null){
			sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));
		}
//		if(migrateId != null){
//			sql.append(" and m.id="+migrateId);
//		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and newBankAccount='"+bankAccount+"'");
		}
		if(StringUtil.isNotBlank(startTime)){
			sql.append(" and reqtime>=to_date('"+startTime+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(endTime)){
			sql.append(" and reqtime<=to_date('"+endTime+" 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(state)){
			sql.append(" and appState='"+state+"'");
		}
		sql.append(" order by id desc ");
		return this.findByPages(sql.toString(), pager,null);
		
	}

	public Map<String, Object> migrateInfo(Long migrateId) {
		/*String sql = "select * from (select m.ID,m.OldAccountID,m.NewAccountID,"
				//迁移信息
				+" m.Flag,m.AppState,m.EffectTime,m.Approver,m.AppTime,m.Remark,m.OperID,"
				+" m.PlaceID,m.OperTime"
				//客户信息
				+ ",c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,"
				//银行账户
				+ "aa.bankAccount oldBankAccount,"
				+" (select bankAccount from CSMS_SubAccount_Info s join CSMS_AccountC_apply aa "
				+" on aa.id = s.applyid  where s.id = m.NewAccountID) newBankAccount ,"
				+ "aa.reqCount AccountCApplyreqCount,aa.residueCount AccountCApplyresidueCount,"
				+ "aa.accName,aa.AccountType,aa.MaxAcr,aa.bankName,aa.VirType,aa.bail,aa.truckbail,aa.bank,"
				+" row_number() over (order by m.OperTime desc) as num "
				+" from CSMS_SubAccount_Info sb "
				+" left join CSMS_AccountC_apply aa on aa.id = sb.applyid"
				+" left join csms_customer c on aa.customerid = c.id"
				+" left join csms_migrate m on sb.id = m.oldaccountid) where 1=1 and id=?";*/

		String sql = "select mid,userNo,organ,idType,idcode,mobile,newBankAccount,oldBankAccount,reqtime,appstate,placeNo from (select "
				//客户信息
				+ "m.id as mid,c.userNo,c.organ,"
				+ "c.idType,c.idCode,c.mobile,"
				//迁移信息
				+ "aa.bankAccount as newBankAccount,"
				+ "(select aa.bankAccount from CSMS_AccountC_apply aa join CSMS_SubAccount_Info si on si.ApplyID=aa.id and si.id=m.OldAccountID) as oldBankAccount,"
				+" m.Reqtime,m.AppState,m.placeNo"
				+" from csms_migrate m "
				+" left join CSMS_SubAccount_Info si on si.id=m.NewAccountID "
				+" left join CSMS_AccountC_apply aa on si.ApplyID=aa.id "
//				+" left join CSMS_Migrate_detail md on md.MigrateID = m.id "
//				+" left join CSMS_AccountC_info ai on ai.cardno = md.cardno"
				+" left join csms_customer c on aa.CustomerID = c.id"
				+" ) mi where 1=1 and mid=?";

		List<Map<String, Object>> list = queryList(sql,migrateId);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() > 1) {
			logger.error("MigrateDao.migrateInfo方法mid[{}]有多条[{}]记录", migrateId, list.size());
		}
		return list.get(0);
	}

	/**
	 * 客服提供给营运接口使用
	 * @param migrateId
	 * @param newState
	 * @param oldState
	 * @param approver
	 * @param approverNo
	 * @param approverName
	 * @param appTime
	 * @return
	 */
	public boolean updateMigrateState(Long migrateId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime) {
		StringBuffer sql = new StringBuffer("update csms_migrate set AppState='"+newState+"',approver="+approver+",approverNo='"+approverNo+"',approverName='"+approverName+"',appTime=to_date('"+appTime+"','YYYY-MM-DD HH24:MI:SS') where 1=1 and id="+migrateId);
		if (StringUtil.isNotBlank(oldState)) {
			sql.append(" and AppState='"+oldState+"'");
		}
		update(sql.toString());
		return true;
	}

	public MigrateDetail getMigrateDetailByMigrateId(Long migrateId,String oldstate) {
		String sql = "select a.id accountCid,a.cardno,a.customerID,a.state,m.oldaccountid,m.newaccountid"
				+ " from csms_migrate_detail md "
				+ " left join csms_accountc_info a on a.cardno=md.cardno "
				+ " left join csms_migrate m on md.migrateid=m.id where 1=1 and m.id=? and m.AppState=?";
		List<MigrateDetail> migrateDetails = super.queryObjectList(sql, MigrateDetail.class, migrateId, oldstate);
		if (migrateDetails == null || migrateDetails.isEmpty()) {
			return null;
		}
		return migrateDetails.get(0);
	}

	public boolean migrateCardIsOk(Long migrateId) {
		String sql = "select count(*) from csms_migrate m left join csms_migrate_detail md on m.id=md.migrateid"
				+ " left join csms_accountc_info a on md.cardno=a.cardno where a.state!=0 and m.id=?";
		if(count(sql, migrateId)==0){
			return true;
		}
		return false;
	}
}
 