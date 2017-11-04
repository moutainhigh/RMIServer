package com.hgsoft.accountC.dao;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class AccountCApplyDao extends BaseDao{
	public AccountCApply findBySubAccountInfoId(Long id) {
		String sql = "select aa.* from csms_accountc_apply aa join csms_subaccount_info si on aa.id=si.applyid where si.id=?";
		List<AccountCApply> accountCApplies = super.queryObjectList(sql, AccountCApply.class, id);
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}
	
	public List<Map<String, Object>> findByList(Customer customer){
		/*String sql  ="select * from ( select aa.ID,aa.AccountType,aa.LinkMan,aa.Tel,aa.validity,"
				+ "aa.bank,aa.bankSpan,aa.bankAccount,aa.bankName,aa.accName,"
				+ "aa.InvoicePrn,aa.reqCount,aa.residueCount,aa.Bail,aa.VirType,"
				+ "aa.MaxAcr,aa.BankClearNo,aa.BankAcceptNo,ac.AppState,c.idType,c.idCode,c.UserNo,sb.subAccountType,c.state "
				+ " from CSMS_SubAccount_Info sb  "
				+" join CSMS_AccountC_apply aa  on aa.id =sb.ApplyID "
				+" join CSMS_MainAccount_Info  mi on sb.mainId = mi.id "
				+" join csms_customer c on mi.mainid = c.id "
				+" left join  CSMS_AccountNC_apply ac on sb.id =  ac.AccountID "
				+" where  sb.id not in (select AccountID from CSMS_AccountNC_apply ac where ac.AppState ='1')  and aa.AppState = '2') "
				+"where UserNo = '"+customer.getUserNo()+"' and subAccountType = 2 and state = '1'";*/
		
		
		String sql  ="select aa.ID,aa.AccountType,aa.LinkMan,aa.Tel,aa.validity,"
				+ " aa.bank,aa.bankSpan,aa.bankAccount,aa.bankName,aa.accName,aa.InvoicePrn,"
				+ " aa.reqCount,aa.residueCount,aa.bail,aa.truckBail,aa.VirType,aa.MaxAcr,aa.BankClearNo,aa.BankAcceptNo,c.idType,c.idCode,"
				+ " c.UserNo,sb.subAccountType,c.state  from CSMS_SubAccount_Info sb   join CSMS_AccountC_apply aa  on aa.id =sb.ApplyID "
				+ " join CSMS_MainAccount_Info  mi on sb.mainId = mi.id  join csms_customer c on mi.mainid = c.id "
				+ " where  UserNo = ? and subAccountType = 2 and c.state = '1'";
			
		List<Map<String, Object>> list = queryList(sql,customer.getUserNo());
		return list;
	}
	
	public List<Map<String, Object>> findByListByState(Customer customer,AccountCApply accountCApply){
		StringBuffer sql  =new StringBuffer("select aa.ID aaid,sb.id sbid,aa.AccountType,aa.LinkMan,aa.Tel,aa.validity,"
				+ "aa.bank,aa.bankSpan,aa.bankAccount,aa.bankName,aa.accName,"
				+ "aa.InvoicePrn,aa.reqCount,aa.residueCount,aa.bail,aa.truckBail,aa.VirType,"
				+ "aa.MaxAcr,aa.BankClearNo,aa.BankAcceptNo,aa.AppState,aa.debitCardType,c.idType,c.idCode,c.state,c.UserNo "
				+ " from CSMS_SubAccount_Info sb  "
				+" join CSMS_AccountC_apply aa  on aa.id =sb.ApplyID "
				+" join CSMS_MainAccount_Info  mi on sb.mainId = mi.id "
				+" join csms_customer c on mi.mainid = c.id "
				+"where sb.subAccountType = 2 and c.state = '1' ");
		
		SqlParamer params = new SqlParamer();
		if(accountCApply.getResidueCount()!=null){
			sql.append(" and aa.residueCount !=0 ");
		}
		if(StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("c.userNo", customer.getUserNo());
		}
		if(StringUtil.isNotBlank(accountCApply.getAppState())){
			params.eq("aa.AppState", accountCApply.getAppState());
		}
		if(accountCApply.getDebitCardType() != null){
			params.eq("aa.debitCardType", accountCApply.getDebitCardType());
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		
		sql.append(" order by sb.OperTime desc ");
		List<Map<String, Object>> resultList = queryList(sql.toString(),Objects);
		return resultList;
	}
	
	public AccountCApply findById(Long id) {
		String sql = "select * from CSMS_AccountC_apply where id=?";
		List<AccountCApply> accountCApplies = super.queryObjectList(sql, AccountCApply.class, id);
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}

	public BillGet findBillGetByAccountCApply(AccountCApply accountCApply) {
		String sql = "select * from CSMS_bill_get where cardType=2 and CardBankNo=?";
		List<BillGet> billGets = super.queryObjectList(sql, BillGet.class, accountCApply.getBankAccount());
		if (billGets == null || billGets.isEmpty()) {
			return null;
		}
		return billGets.get(0);
	}
	public AccountCApply findBySubAccId(Long id) {
		String sql = "select a.* from CSMS_AccountC_apply a join csms_subaccount_info s on a.id=s.applyid where s.id=?";
		List<AccountCApply> accountCApplies = super.queryObjectList(sql, AccountCApply.class, id);
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}

	
	public void update(AccountCApply accountCApply){
		Map map = FieldUtil.getPreFieldMap(AccountCApply.class, accountCApply);
		StringBuffer sql =  new StringBuffer("update CSMS_AccountC_apply set ");
		sql.append(map.get("updateNameStrNotNull")+" where id=?");
		super.saveOrUpdate(sql.toString(), (List)map.get("paramNotNull"), accountCApply.getId());
	}
	
	//专门给更新不为null又可以为""的对象用：银行账号申请修改使用
	public void update4NotNullStr(AccountCApply accountCApply){
		Map map = FieldUtil.getPreFieldMap(AccountCApply.class, accountCApply);
		StringBuffer sql=new StringBuffer("update CSMS_AccountC_apply set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),accountCApply.getId());
	}
	
	public AccountCApply findByCardNo(String cardNo){
		String sql = "select ap.* from csms_accountc_info a left join csms_subaccount_info s on a.accountid=s.id left join csms_accountc_apply ap on ap.id=s.applyid where a.cardNo=?";
		List<AccountCApply> accountCApplies = super.queryObjectList(sql, AccountCApply.class, cardNo);
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}
	public AccountCApply findByHisId(Long hisId){
		String sql ="";
		if(hisId!=null){
			sql= "select * from CSMS_AccountC_apply where HisSeqID="+hisId;
		}else{
			sql= "select * from CSMS_AccountC_apply where HisSeqID is null";
		}
		List<Map<String, Object>> list = queryList(sql);
		AccountCApply accountCApply = null;
		if (!list.isEmpty()) {
			accountCApply = new AccountCApply();
			this.convert2Bean(list.get(0), accountCApply);
		}

		return accountCApply;
	}
//todo 这里需要不知道是否需要加shutDownStatus条件筛选
	public AccountCApply findByBankAccount(String bankAccount) {
		String sql = "select * from CSMS_AccountC_apply where BankAccount = ? ";
		List<AccountCApply> accountCApplies = super.queryObjectList(sql, AccountCApply.class, bankAccount);
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}

	public AccountCApply findAccountCApply(PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		AccountCApply accountCApply = null;
		if(paymentCardBlacklistRecv!=null){
			List<Map<String, Object>> list = new ArrayList<>();
			if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getCardCode())){
				String sql = "select aa.* from CSMS_AccountC_info ai join CSMS_SubAccount_Info si on si.id=ai.AccountID " +
						"join CSMS_AccountC_apply aa on aa.id=si.ApplyID and ai.cardno=?";
				list = queryList(sql,paymentCardBlacklistRecv.getCardCode());

			}else if(StringUtil.isNotBlank(paymentCardBlacklistRecv.getAcbAccount())){
				String sql = "select * from CSMS_AccountC_apply where BankAccount=?";
				list = queryList(sql,paymentCardBlacklistRecv.getAcbAccount());
			}
			if (!list.isEmpty()) {
				accountCApply = new AccountCApply();
				this.convert2Bean(list.get(0), accountCApply);
			}

		}
		return accountCApply;
	}

	/**
	 *记帐卡申请状态查询
	 */
	public List findByBankAccountByUserno(String userNo){

		if (StringUtils.isBlank(userNo)) {
			throw new ApplicationException("accountCApplyDao.findByBankAccountByUserno查询条件为空");
		}

		StringBuffer sql = new StringBuffer("select aa.bankAccount \"bankAccount\" , to_char(aa.OperTime,'yyyy/MM/dd') \"OperTime\" , aa.reqCount \"reqCount\" , aa.AccountType \"AccountType\" ,\n" +
				"aa.AppState \"AppState\" , (aa.reqCount-aa.residueCount) \"issueCount\" , aa.appFailMemo \"appFailMemo\" \n" +
				"from CSMS_ACCOUNTC_APPLY aa join CSMS_Customer cr on cr.id = aa.CustomerID where 1=1 and cr.userno=? ");

		return queryList(sql.toString(), userNo);
	}
	
	public AccountCApply find(AccountCApply accountCApply) {
		if (accountCApply == null) {
			return null;
		}
		Map map = FieldUtil.getPreFieldMap(AccountCApply.class,accountCApply);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("accountCApplyDao.find查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_AccountC_apply where 1=1 ");
		sql.append(condition);
		sql.append(" order by id desc");

		List<AccountCApply> accountCApplies = super.queryObjectList(sql.toString(), AccountCApply.class, ((List) map.get("paramNotNull")).toArray());
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}
	/**
	 * 分页查询，记帐卡初次申请的详细信息查询
	 * @param pager
	 * @param customer
	 * @param bankAccount
	 * @return
	 */
	public Pager findAccountCFirstApplys(Pager pager,Customer customer,String bankAccount){
		
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId,a.shutDownStatus as shutDownStatus,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,a.virenum,s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,"
				+ "(select count (ai.id) from CSMS_AccountC_info ai where ai.Accountid = s.id) as maxcount,"
				+ "(select count (ai.id) from CSMS_AccountC_info ai where ai.Accountid = s.id and trim(ai.State)='2') as count,"
				+ "ROWNUM as num "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "csms_mainaccount_info m on s.mainid=m.id join "
				+ "CSMS_Customer c on c.id=m.mainid where 1=1 and c.id=a.customerid and s.subaccounttype='2'");
		/*if (StringUtil.isNotBlank(customer.getOrgan())) {
			sql.append(" and organ='"+customer.getOrgan()+"'");
		}
		if (StringUtil.isNotBlank(customer.getIdCode())) {
			sql.append(" and idCode='"+customer.getIdCode()+"'");
		}
		if (StringUtil.isNotBlank(customer.getIdType())) {
			sql.append(" and idType='"+customer.getIdType()+"'");
		}
		if (StringUtil.isNotBlank(customer.getUserNo())) {
			sql.append(" and userNo='"+customer.getUserNo()+"'");
		}*/
		List list=new ArrayList();
		if(customer != null){
			/*sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));*/
			Map map = FieldUtil.getPreFieldMap(Customer.class,customer);
			sql.append(map.get("selectNameStrNotNullAndWhere"));
			list=((List) map.get("paramNotNull"));
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and a.bankAccount=?");
			list.add(bankAccount);
		}
		//sql.append(" order by t.id desc");
		sql.append(" order by  a.APPLYTIME desc ");
		return this.findByPages(sql.toString(), pager,list.toArray());
		
	}
	
	public void saveAccountCApply(AccountCApply accountCApply){
		accountCApply.setHisseqId(-accountCApply.getId());
		Map map = FieldUtil.getPreFieldMap(AccountCApply.class,accountCApply);
		StringBuffer sql=new StringBuffer("insert into CSMS_AccountC_apply");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		/*StringBuffer sql = new StringBuffer("insert into CSMS_AccountC_apply(");
		sql.append(FieldUtil.getFieldMap(AccountCApply.class, accountCApply).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(AccountCApply.class, accountCApply).get("valueStr")+")");
		save(sql.toString());*/
	}

	public List<Map<String, Object>> getBankByCustomerId(Long customerId) {
		return queryList("select b.id id,a.bankAccount name,a.customerid,b.mainid from csms_accountc_apply a left join csms_subaccount_info b on a.id=b.ApplyID where a.AppState='6' and a.customerid=?", customerId);
	}

	public AccountCApply findbyCustomerId(Long customerId) {
		String sql = "select * from CSMS_AccountC_apply where customerId=?";
		List<AccountCApply> accountCApplies = super.queryObjectList(sql.toString(), AccountCApply.class, customerId);
		if (accountCApplies == null || accountCApplies.isEmpty()) {
			return null;
		}
		return accountCApplies.get(0);
	}

	public List<Map<String, Object>> findListByCustomerId(Long customerId) {
		String sql = "select * from CSMS_AccountC_apply where customerId=?";
		List<Map<String, Object>> list = queryList(sql,customerId);
		return list;
	}
	
	public List<Map<String, Object>> findListByCustomerId(Long customerId, String appState) {
		String sql = "select * from CSMS_AccountC_apply where customerId=? and appState=?";
		List<Map<String, Object>> list = queryList(sql,customerId,appState);
		return list;
	}
	/*
	 * 自营系统 保证金管理 新增或退还时查询扣款银行卡类型为自营的
	 * (以防上面的方法其他系统有用，新写一个)
	 * auther:hzw
	 */
	public List<Map<String, Object>> findListByCustomerIdCSMS(Long customerId, String appState) {
		String sql = "select aa.*,sub.BailBalance,sub.BailFee,sub.id subid from CSMS_AccountC_apply aa "
				+ " join CSMS_SubAccount_Info sub on sub.applyid=aa.id "
				+ " where customerId=? and appState=? and debitCardType='0'";
		List<Map<String, Object>> list = queryList(sql,customerId,appState);
		return list;
	}
	
	/**
	 * 营运系统
	 * 分页查询，记帐卡初次申请的信息查询
	 * @param pager
	 * @param customer
	 * @param bankAccount
	 * @return
	 */
	public Pager findAccountCFirstApplyList(Pager pager,Customer customer,String bankAccount,String state){
		
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId, "
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,a.virenum,s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,ROWNUM as num "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "CSMS_Customer c on c.id=a.CustomerID where 1=1 and c.state=1 and subaccounttype='2' ");
		
		if(customer != null){
			sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and bankAccount='"+bankAccount+"'");
		}
		if(StringUtil.isNotBlank(state)){
			sql.append(" and a.appState='"+state+"'");
		}
		sql.append(" order by c.userNo desc ");
		return this.findByPages(sql.toString(), pager,null);
		
	}
	
	/**
	 * 营运系统
	 * 分页查询，记帐卡初次申请的信息查询
	 * @param pager
	 * @param customer
	 * @param bankAccount
	 * @return
	 */
	public Pager findAccountCFirstApplyList(Pager pager,Customer customer,String bankAccount,String state,String startTime,String endTime,Long placeId, String type){
		
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId,"
				+ "a.applyTime accountCApplyApplyTime,a.AppTime accountCApplyAppTime,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,"
				+ "a.OperID accountCApplyOperID,a.PlaceID accountCApplyPlaceID,a.payAgreementNo,appFailMemo,a.virenum,a.placeName,"
				+ "s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,"
				+ "c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,rownum as num "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "CSMS_Customer c on c.id=a.CustomerID where 1=1 and c.state=1 and subaccounttype='2' and debitCardType=0 ");
		
		if(customer != null&&StringUtil.isNotBlank(customer.getUserNo())){
			sql.append(" and c.userNo='"+customer.getUserNo()+"'");
		}
		if(customer != null&&StringUtil.isNotBlank(customer.getOrgan())){
			sql.append(" and c.organ like '%"+customer.getOrgan()+"%'");
		}
		if(customer != null&&StringUtil.isNotBlank(customer.getIdCode())){
			sql.append(" and c.idCode='"+customer.getIdCode()+"'");
		}
		if(placeId != null){
			sql.append(" and a.PlaceID="+placeId);
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and bankAccount='"+bankAccount+"'");
		}
		if(StringUtil.isNotBlank(startTime)){
			sql.append(" and a.applyTime>=to_date('"+startTime+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(endTime)){
			sql.append(" and a.applyTime<=to_date('"+endTime+" 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		//type:1.初次申请审批列表；2.单卡保证金设置列表
		if(StringUtil.isNotBlank(state)){
			sql.append(" and a.appState='"+state+"'");
		}else {
			if(type.equals("2")){
				// TODO: 2017/4/19 这里的单卡保证金需要改
				//记帐卡审批状态的状态值：0未审批、1银行审批中、2银行审批通过、3银行审批不通过、4营运审批通过、5营运审批不通过、6营运审核通过、7营运审核不通过、8设置保证金、9保证金确认
				sql.append(" and (a.appState=2 or a.appState=8 or a.appState=9)");
			}
		}
		sql.append(" order by a.applyTime desc");
		return this.findByPages(sql.toString(), pager,null);
		
	}

	public Map<String, Object> accountCFirstApplyInfo(Long accountCApplyId) {
		String sql = "select a.ID as accountCApplyId,"
				+ "a.OperTime accountCApplyOperTime,a.AppTime accountCApplyAppTime,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,"
				+ "a.OperID accountCApplyOperID,a.PlaceID accountCApplyPlaceID,a.payAgreementNo,appFailMemo,a.virenum,a.placeName,"
				+ "s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "CSMS_Customer c on c.id=a.CustomerID where 1=1 and c.state=1 and subaccounttype='2' and a.id=? order by c.userNo desc ";
		List<Map<String, Object>> list = queryList(sql,accountCApplyId);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
		
	}

	public void saveAccountCApplyHis(Long accountCApplyid, String genReason, Long hisId) {
		String sql = "insert into csms_accountc_apply_his"
				+ "(ID,CUSTOMERID,ACCOUNTTYPE,LINKMAN,TEL,VALIDITY,BANK,"
				+ "BANKSPAN,BANKACCOUNT,BANKNAME,ACCNAME,INVOICEPRN,REQCOUNT,"
				+ "RESIDUECOUNT,bail,truckbail,VIRTYPE,MAXACR,BANKCLEARNO,BANKACCEPTNO,"
				+ "APPSTATE,APPROVER,OPERID,PLACEID,HISSEQID,GENTIME,GENREASON,OperTime,AppTime,NewCardFlag,"
				+ "ApproverNo,ApproverName,payAgreementNo,appFailMemo,virenum)  "
				
				+ " SELECT "+hisId+",CUSTOMERID,ACCOUNTTYPE,LINKMAN,TEL,VALIDITY,BANK,"
				+ "BANKSPAN,BANKACCOUNT,BANKNAME,ACCNAME,INVOICEPRN,REQCOUNT,"
				+ "RESIDUECOUNT,bail,truckbail,VIRTYPE,MAXACR,BANKCLEARNO,BANKACCEPTNO,"
				+ "APPSTATE,APPROVER,OPERID,PLACEID,HISSEQID,sysdate,'"+genReason+"',OperTime,AppTime,NewCardFlag,ApproverNo,ApproverName,payAgreementNo,appFailMemo,virenum"
				+ " FROM csms_accountc_apply a WHERE a.id=?";
		update(sql, accountCApplyid);
	}

	public void updateAccountCApply(AccountCApply accountCApply) {
		if (accountCApply != null) {
			StringBuffer sql=new StringBuffer("update csms_accountc_apply set ");
			String param = FieldUtil.getFieldMap(AccountCApply.class,accountCApply).get("nameAndValueNotNullToUpdate");
//			param = param.substring(1);
			if (StringUtil.isNotBlank(param)) {
				sql.append(param+" where id="+accountCApply.getId());
			}
			update(sql.toString());
		}
	}

	public Map<String, Object> findSubAccountCByApplyId(Long accountCApplyId, String oldState) {
		String sql = "select s.id subAccountId,a.customerid from csms_subaccount_info s left join csms_accountc_apply a on s.applyid=a.id where a.id=? and a.AppState=?";
		List<Map<String, Object>> list = queryList(sql,accountCApplyId,oldState);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	public Pager findBailList(Pager pager, Customer customer, String bankAccount, String state) {
		
		StringBuffer sql = new StringBuffer("select a.ID as accountCApplyId,"
				//记帐卡申请信息
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,"
				//客户信息
				+ "c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,ROWNUM as num "
				
				+ "from CSMS_AccountC_apply a join "
				+ "CSMS_Customer c on c.id=a.CustomerID where 1=1 and c.state=1 ");
		
		if(customer != null){
			sql.append(FieldUtil.getFieldMap(Customer.class,customer).get("nameAndValueNotNull"));
		}
		if(StringUtil.isNotBlank(bankAccount)){
			sql.append(" and bankAccount='"+bankAccount+"'");
		}
		if(StringUtil.isNotBlank(state)){
			sql.append(" and a.appState='"+state+"'");
		}
		sql.append(" order by c.userNo desc ");
		return this.
				findByPages(sql.toString(), pager,null);
		
	}

	public Map<String, Object> findNewCardVehicle(Long id) {
		String sql = "select * from CSMS_NewCard_Vehicle where id=? ";
		List<Map<String, Object>> list = queryList(sql,id);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	public String deleteNewCardVehicle(Long id){

		String sql = "update CSMS_NewCard_Vehicle set state='2' where state='1' and id=? ";
		saveOrUpdate(sql,id);
		return "true";
	}
	
	public Map<String, Object> bailInfo(Long accountCApplyId) {
		String sql = "select a.ID as accountCApplyId,"
				//记帐卡申请信息
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,"
				//客户信息
				+ "c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime "
				
				+ "from CSMS_AccountC_apply a join "
				+ "CSMS_Customer c on c.id=a.CustomerID where 1=1 and c.state=1 and a.id=? order by c.userNo desc ";
		List<Map<String, Object>> list = queryList(sql,accountCApplyId);
		if (!list.isEmpty()&&list.size()==1) {
			return list.get(0);
		}
		return null;
		
	}

	public List<Map<String, Object>> accountCFirstApplyExportList(String state,
			String startTime, String endTime,String accountType) {
		
		StringBuffer sql = new StringBuffer("select '740846765' partCode,'94902' bussinessType,a.ID as accountCApplyId,"
				+ "a.OperTime accountCApplyOperTime,a.AppTime accountCApplyAppTime,"
				+ "a.accountType,a.linkman,a.validity,a.bank,a.bankSpan,"
				+ "a.bankAccount,a.bankName,a.tel linkTel,a.accName,a.InvoicePrn,a.reqcount,"
				+ "a.residueCount,a.bail,a.truckbail,a.virType,a.maxAcr,a.bankClearNo,"
				+ "a.bankAcceptNo,a.appState,a.Approver,a.virenum,"
				+ "a.OperID accountCApplyOperID,a.PlaceID accountCApplyPlaceID,a.payAgreementNo,appFailMemo,"
				+ "s.ID as subAccountInfoId,"
				+ "s.MainID as mainaccountinfoID,s.subAccountNo,s.subAccountType,"
				+ "s.ApplyID,c.ID as customerId,c.userNo,c.organ,c.servicePwd,"
				+ "c.userType,c.idType,c.idCode,c.registeredCapital,c.tel,c.mobile,"
				+ "c.shortTel,c.addr,c.zipCode,c.email,c.state,c.cancelTime,"
				+ "c.upDateTime,c.firRunTime,row_number() over (order by c.userNo desc) as num "
				+ "from CSMS_AccountC_apply a join CSMS_SubAccount_Info s on a.id=s.ApplyID join "
				+ "CSMS_Customer c on c.id=a.CustomerID where 1=1 and c.state=1 and subaccounttype='2' ");
		if(StringUtil.isNotBlank(accountType)&&"3".equals(accountType)){
			sql.append(" and a.accountType='"+accountType+"'");
		}else if(StringUtil.isNotBlank(accountType)&&!"3".equals(accountType)){
			sql.append(" and a.accountType<>'3' ");
		}
		if(StringUtil.isNotBlank(startTime)){
			sql.append(" and a.OperTime>=to_date('"+startTime+" 00:00:00','YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtil.isNotBlank(endTime)){
			sql.append(" and a.OperTime<=to_date('"+endTime+" 23:59:59','YYYY-MM-DD HH24:MI:SS')");
		}
		//导出银行状态只能是：0/1，不然返回null
		if(StringUtil.isNotBlank(state)){
			if (state.equals("0")||state.equals("1")) {
				sql.append(" and a.appState='"+state+"'");
			}else {
				sql.append(" and a.appState is null");
			}
		}else {
			//记帐卡审批状态的状态值：0未审批、1银行审批中、2银行审批通过、3银行审批不通过、4营运审批通过、5营运审批不通过、6营运审核通过、7营运审核不通过、8设置保证金、9保证金确认
			sql.append(" and (a.appState=0 or a.appState=1)");
		}
		return queryList(sql.toString());
	}

	public int[] batchUpdateAccountCApplyState(final List<AccountCApply> accountCApplies) {  
        String sql = "update csms_accountc_apply a set a.appstate=1,a.hisseqid=? where a.appstate=0 and a.id=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				ps.setLong(1, accountCApplies.get(i).getHisseqId());
				ps.setLong(2, accountCApplies.get(i).getId());
			}
			
			@Override
			public int getBatchSize() {
				 return accountCApplies.size();
			}
		});
    }

	public int[] batchSaveAccountCApplyHis(final List<AccountCApply> accountCApplies) {  
        String sql = "insert into csms_accountc_apply_his"
				+ "(ID,CUSTOMERID,ACCOUNTTYPE,LINKMAN,TEL,VALIDITY,BANK,"
				+ "BANKSPAN,BANKACCOUNT,BANKNAME,ACCNAME,INVOICEPRN,REQCOUNT,"
				+ "RESIDUECOUNT,bail,truckbail,VIRTYPE,MAXACR,BANKCLEARNO,BANKACCEPTNO,"
				+ "APPSTATE,APPROVER,OPERID,PLACEID,HISSEQID,GENTIME,GENREASON,OperTime,AppTime,NewCardFlag)  "
				
				+ " SELECT ?,CUSTOMERID,ACCOUNTTYPE,LINKMAN,TEL,VALIDITY,BANK,"
				+ "BANKSPAN,BANKACCOUNT,BANKNAME,ACCNAME,INVOICEPRN,REQCOUNT,"
				+ "RESIDUECOUNT,bail,truckbail,VIRTYPE,MAXACR,BANKCLEARNO,BANKACCEPTNO,"
				+ "APPSTATE,APPROVER,OPERID,PLACEID,HISSEQID,sysdate,'1',OperTime,AppTime,NewCardFlag"
				+ " FROM csms_accountc_apply a WHERE a.appstate=? and a.id=?";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				ps.setLong(1, accountCApplies.get(i).getHisseqId());
				ps.setString(2, accountCApplies.get(i).getAppState());
				ps.setLong(3, accountCApplies.get(i).getId());
			}
			
			@Override
			public int getBatchSize() {
				 return accountCApplies.size();
			}
		});
    }
	
	public void updateShutDownStatus(AccountCApply accountCApply){
		String sql = "update CSMS_AccountC_apply set shutDownStatus = '1',HisSeqID = ? where id = ? ";
		saveOrUpdate(sql,accountCApply.getHisseqId(),accountCApply.getId());
	}

}

