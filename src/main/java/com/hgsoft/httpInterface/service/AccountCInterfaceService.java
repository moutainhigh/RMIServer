package com.hgsoft.httpInterface.service;

import com.alibaba.fastjson.JSON;
import com.hgsoft.account.dao.*;
import com.hgsoft.account.entity.*;
import com.hgsoft.accountC.dao.*;
import com.hgsoft.accountC.entity.*;
import com.hgsoft.clearInterface.dao.AccCardTransferSendDao;
import com.hgsoft.clearInterface.entity.AccCardTransferSend;
import com.hgsoft.clearInterface.serviceInterface.IAccCardUserInfoService;
import com.hgsoft.common.Enum.AccountCShutDownStatusEnum;
import com.hgsoft.common.Enum.RefundAuditStatusEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.customer.dao.*;
import com.hgsoft.customer.entity.*;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.entity.NewCardVehicleVo;
import com.hgsoft.httpInterface.serviceInterface.IAccountCInterfaceService;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountCInterfaceService implements IAccountCInterfaceService{

	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private NewCardApplyDao newCardApplyDao;
	@Resource 
	private MigrateDao migrateDao;
	@Resource 
	private TransferApplyDao transferApplyDao;
	@Resource 
	private AccountNCApplyDao accountNCApplyDao;
	@Resource 
	private AccountCBussinessDao accountCBussinessDao;
	@Resource 
	private AccountCDao accountCDao;
	@Resource 
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource 
	private ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource 
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource 
	private BailAccountInfoDao bailAccountInfoDao;
	@Resource 
	private BillGetDao billGetDao;
	@Resource 
	private BillGetHisDao billGetHisDao;
	@Resource 
	private CustomerDao customerDao;
	@Resource 
	private CustomerHisDao customerHisDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private BailDao bailDao;
	@Resource
	private BailAccountInfoHisDao bailAccountInfoHisDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private IAccCardUserInfoService accCardUserInfoService;
	@Resource
	private NewCardApplyHisDao newCardApplyHisDao;
	
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao ;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private AccountBankInfoDao accountBankInfoDao;
	@Resource
	private AccCardTransferSendDao accCardTransferSendDao;

	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	
	private static Logger logger = Logger.getLogger(AccountCInterfaceService.class.getName());

	/**
	 * 初次申请列表信息
	 */
	@Override
	public Pager accountCFirstApplyList(Pager pager, Customer customer, String bankAccount, String state,String startTime,String endTime,Long placeId, String type) {
		try {
			return accountCApplyDao.findAccountCFirstApplyList(pager, customer, bankAccount, state, startTime, endTime,placeId, type);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡初次申请查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡初次申请查询失败！");
		}
	}

	/**
	 * 导出至银行（状态：未审批[0]和待银行审批中[1]）
	 */
	@Override
	public List<Map<String, Object>> accountCFirstApplyExportList(String state,
			String startTime, String endTime,String accountType) {
		try {
			return accountCApplyDao.accountCFirstApplyExportList(state, startTime, endTime,accountType);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡初次申请导出至银行查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡初次申请导出至银行查询失败！");
		}
	}
	
	/**
	 * 将审批的数据导出银行后，返回导出的数据，将该数据的状态批量改成银行审批中【state=1】
	 */
	@Override
	public boolean batchUpdateAccountCApplyState(String param) {
		try {
			JSONObject paramJson = (JSONObject) JSONObject.fromObject(param).get("param");
			JSONArray array = (JSONArray) paramJson.get("resultList");
			List<Map> list = JSON.parseArray(array.toString(),Map.class);
			
			//List<Map> list = JSON.parseArray(param,Map.class);
			AccountCApply accountCApply = null;
			List<AccountCApply> accountCApplies = new ArrayList<AccountCApply>();
			Long hisId = null;
			//状态为：未审批（appstate=0）才需更新状态为银行审批中，并新增历史数据
			for (Map<String, Object> map : list) {
				if (map.get("APPSTATE") != null && map.get("APPSTATE").toString().equals("0")) {
					hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
					accountCApply = new AccountCApply();
					accountCApply.setId(Long.parseLong(map.get("ACCOUNTCAPPLYID").toString()));
					accountCApply.setAppState(map.get("APPSTATE").toString());
					accountCApply.setHisseqId(hisId);
					accountCApplies.add(accountCApply);
				}
			}
			//批量新增记帐卡申请历史记录
			accountCApplyDao.batchSaveAccountCApplyHis(accountCApplies);
			//批量修改记帐卡申请记录的状态为银行审批中
			accountCApplyDao.batchUpdateAccountCApplyState(accountCApplies);
			return true;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡初次申请导出至银行后，更新状态为银行审批中失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡初次申请导出至银行后，更新状态为银行审批中失败！");
		}
	}

	/**
	 * 初次申请详情信息
	 */
	@Override
	public Map<String, Object> accountCFirstApplyInfo(Long accountCApplyId) {
		try {
			return accountCApplyDao.accountCFirstApplyInfo(accountCApplyId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡初次申请查询详情失败！accountCApplyId:="+accountCApplyId);
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡初次申请查询详情失败！accountCApplyId:="+accountCApplyId);
		}
	}

	/**
	 * 更新初次申请状态
	 * 保存：
		1.新增记帐卡申请历史记录
		2.修改记帐卡申请记录：状态，审批人，审批时间，历史id
	 */
	@Override
	public boolean updateAccountCFirstApplyState(Long accountCApplyId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime,AccountCBussiness accountCBussiness,String payAgreementNo,String appFailMemo,String validity,String virenum,String maxAcr) {
		// TODO 初次申请
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Map<String, Object> map = accountCApplyDao.findSubAccountCByApplyId(accountCApplyId, oldState);
			if (map!=null&&map.get("customerid")!=null) {
				//记帐卡申请历史记录
				Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
				accountCApplyDao.saveAccountCApplyHis(accountCApplyId,"1",hisId);
				//记帐卡申请记录
				AccountCApply accountCApply = new AccountCApply();
				accountCApply.setId(accountCApplyId);
				accountCApply.setCustomerId(Long.valueOf(map.get("customerid").toString()));
				accountCApply.setAppState(newState);
				accountCApply.setAppTime(format.parse(appTime));
				accountCApply.setApprOver(Long.parseLong(approver));
				accountCApply.setApproverNo(approverNo);
				accountCApply.setApproverName(approverName);
				accountCApply.setHisseqId(hisId);
				
				AccountCApply oldAccountCApply = accountCApplyDao.findById(accountCApplyId);
				//2银行审批通过、3银行审批不通过
				//不管跨行还是非跨行
				if("2".equals(accountCApply.getAppState())){
					//银行审批成功就记录客户付款协议号
					accountCApply.setPayAgreementNo(payAgreementNo);
				}else if("3".equals(accountCApply.getAppState())){
					//如果银行审批失败的话就要记录审批失败原因和客户付款协议号
					accountCApply.setPayAgreementNo(payAgreementNo);
					accountCApply.setAppFailMemo(appFailMemo);
				}
				
				if(StringUtil.isNotBlank(validity)){
					accountCApply.setValidity(dayFormat.parse(validity));
				}
				if(StringUtil.isNotBlank(virenum)){
					accountCApply.setVirenum(Integer.parseInt(virenum));
				}else{
					accountCApply.setVirenum(0);
				}
				if(StringUtil.isNotBlank(maxAcr)){
					accountCApply.setMaxacr(new BigDecimal(maxAcr));
				}else{
					accountCApply.setMaxacr(oldAccountCApply.getMaxacr());
				}
				
				accountCApplyDao.updateAccountCApply(accountCApply);
				AccountCApply newAccountCApply = accountCApplyDao.findById(accountCApplyId);


				Customer customer = customerDao.findById(oldAccountCApply.getCustomerId());

				//todo 这里的记帐卡银行信息表暂时没有其他地方调用，所以不知道有什么用
				//记录记帐卡银行信息表
				AccountBankInfo accountBankInfo = accountBankInfoDao.findSubAccountNo(oldAccountCApply.getSubAccountNo());
				if(accountBankInfo == null){
					Long bankId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountBankInfo");
					accountBankInfo = new AccountBankInfo();
					accountBankInfo.setId(bankId);
					accountBankInfo.setOrgan(customer.getOrgan());
					accountBankInfo.setSubAccountNo(newAccountCApply.getSubAccountNo());
					accountBankInfo.setAccountType(newAccountCApply.getAccountType());
					accountBankInfo.setLinkMan(newAccountCApply.getLinkman());
					accountBankInfo.setTel(newAccountCApply.getTel());
					if(StringUtil.isNotBlank(validity)){
						accountBankInfo.setValidity(dayFormat.parse(validity));
					}else{
						accountBankInfo.setValidity(newAccountCApply.getValidity());
					}
					accountBankInfo.setBank(newAccountCApply.getBank());
					accountBankInfo.setBankSpan(newAccountCApply.getBankSpan());
					accountBankInfo.setObaNo(newAccountCApply.getObaNo());
					accountBankInfo.setBankAccount(newAccountCApply.getBankAccount());
					accountBankInfo.setBankName(newAccountCApply.getBankName());
					accountBankInfo.setAccName(newAccountCApply.getAccName());
					accountBankInfo.setVirType(newAccountCApply.getVirType());
					if(StringUtil.isNotBlank(maxAcr)){
						accountBankInfo.setMaxAcr(new BigDecimal(maxAcr));
					}else{
						accountBankInfo.setMaxAcr(newAccountCApply.getMaxacr());
					}
					if(StringUtil.isNotBlank(virenum)){
						accountBankInfo.setVirenum(Integer.parseInt(virenum));
					}
					accountBankInfo.setBankClearNo(newAccountCApply.getBankClearNo());
					accountBankInfo.setBankAcceptNo(newAccountCApply.getBankAcceptNo());
					accountBankInfo.setPayAgreementNo(newAccountCApply.getPayAgreementNo());

					accountBankInfoDao.save(accountBankInfo);
				}else{
					if(StringUtil.isNotBlank(validity)){
						accountBankInfo.setValidity(dayFormat.parse(validity));
					}else{
						accountBankInfo.setValidity(newAccountCApply.getValidity());
					}
					if(StringUtil.isNotBlank(maxAcr)){
						accountBankInfo.setMaxAcr(new BigDecimal(maxAcr));
					}else{
						accountBankInfo.setMaxAcr(newAccountCApply.getMaxacr());
					}
					if(StringUtil.isNotBlank(virenum)){
						accountBankInfo.setVirenum(Integer.parseInt(virenum));
					}

					accountBankInfoDao.update(accountBankInfo);
				}

				if(StringUtils.isNotBlank(newState) && newState.equals("6")){//审批状态为营运审批通过添加清算数据
					//Customer cus = customerDao.findById(accountCApply.getCustomerId());
					accCardUserInfoService.saveAccCardUserInfo(newAccountCApply, customer, "记帐卡初次申请审核通过", 0);
				}
				
				/*//记帐卡业务记录  不要了？？？
				accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
				accountCBussiness.setAccountId(Long.parseLong(map.get("subAccountId").toString()));
				accountCBussiness.setUserId(Long.parseLong(map.get("customerid").toString()));
				accountCBussiness.setState("24");
				accountCBussiness.setTradeTime(new Date());
				accountCBussinessDao.save(accountCBussiness);*/
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡初次申请更新状态失败！accountCApplyId：="+accountCApplyId+";state：="+oldState);
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡初次申请更新状态失败！accountCApplyId：="+accountCApplyId+";state：="+oldState);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 新增卡申请列表信息
	 */
	@Override
	public Pager accountCNewApplyList(Pager pager,  String userNo, String organ,String idCode,String bankAccount, String state,String startTime,String endTime) {
		try {
			return newCardApplyDao.findAccountCNewApplyList(pager, userNo, organ, idCode,bankAccount, state, startTime, endTime);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡新增申请查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡新增申请查询失败！");
		}
	}

	/**
	 * 新增卡申请详情信息
	 */
	@Override
	public Map<String, Object> accountCNewApplyInfo(Long newCardId) {
		try {
			return newCardApplyDao.accountCNewApplyInfo(newCardId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡新增申请查询详情失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡新增申请查询详情失败！");
		}
	}

	/**
	 * 更新新增卡申请状态
	 * 保存：
			1.新增新增卡申请历史记录
		2.修改新增卡申请记录 状态、历史id，审批人，审批时间
	 */
	@Override
	public boolean updateAccountCNewApplyState(Long newCardId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime,AccountCBussiness accountCBussiness,String validity,String virenum,String maxAcr, List<NewCardVehicleVo> newCardVehicleVoList) {
		// TODO 新增卡申请
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Map<String, Object> map = newCardApplyDao.findSubAccountCByNewApplyId(newCardId);
			if (map != null) {
				//新增卡申请历史记录
				//Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSNewCardapplyhis_NO");
				//newCardApplyDao.saveNewCardApplyHis(newCardId,"1",hisId);
				NewCardApply oldNewCardApply = newCardApplyDao.findById(newCardId);
				NewCardApplyHis newCardApplyHis = new NewCardApplyHis();
				Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSNewCardapplyhis_NO");
				newCardApplyHis.setId(hisId);
				newCardApplyHis.setGenReason("1");
				newCardApplyHisDao.saveHis(newCardApplyHis, oldNewCardApply);
				//新增卡申请记录
				NewCardApply newCardApply = new NewCardApply();
				newCardApply.setId(newCardId);
				newCardApply.setAppState(newState);
				newCardApply.setAppTime(format.parse(appTime));
				newCardApply.setHisseqId(hisId);
				newCardApply.setApprOver(Long.parseLong(approver));
				newCardApply.setApproverNo(approverNo);
				newCardApply.setApproverName(approverName);
				newCardApplyDao.updateNewCardApply(newCardApply);
				int size = newCardVehicleVoList.size();
				NewCardVehicleVo[] CustomPointTypeArr = (NewCardVehicleVo[])newCardVehicleVoList.toArray(new NewCardVehicleVo[size]);



				//1:营运审批中 4:营运审批不通过 8: 营运审批通过
				// TODO 是否需要判断新旧状态 ？才修改记帐卡申请信息记录   旧状态:设置保证金;新状态：保证金确认
				if (oldState.equals("1")&&newState.equals("8")) {//审批通过
					for(int i=0;i<size;i++){
						logger.error("CustomPointTypeArr[i].getBailType():"+CustomPointTypeArr[i].getBailType());
						logger.error("CustomPointTypeArr[i].getBail():"+CustomPointTypeArr[i].getBail());
						newCardApplyDao.updateBail(CustomPointTypeArr[i],"1");//未使用
					}
					//记帐卡申请历史记录
					hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
					accountCApplyDao.saveAccountCApplyHis(Long.parseLong(map.get("applyid").toString()),"1",hisId);
					//记帐卡申请记录
					AccountCApply accountCApply = new AccountCApply();
					accountCApply.setId(Long.parseLong(map.get("applyid").toString()));
					accountCApply.setReqcount(Long.parseLong(map.get("applyRegcount").toString())+Long.parseLong(map.get("newCardReqcount").toString()));
					accountCApply.setResidueCount(Long.parseLong(map.get("residuecount").toString())+Long.parseLong(map.get("newCardReqcount").toString()));
					accountCApply.setHisseqId(hisId);
					
					accountCApply.setAppTime(format.parse(appTime));
					accountCApply.setApprOver(Long.parseLong(approver));
					accountCApply.setApproverNo(approverNo);
					accountCApply.setApproverName(approverName);
					accountCApply.setShutDownStatus(AccountCShutDownStatusEnum.start.getValue());
					accountCApply.setNewCardFlag("1");//1:是   0:否
					
					if(StringUtil.isNotBlank(validity)){
						accountCApply.setValidity(dayFormat.parse(validity));
					}
					if(StringUtil.isNotBlank(virenum)){
						accountCApply.setVirenum(Integer.parseInt(virenum));
					}
					if(StringUtil.isNotBlank(maxAcr)){
						accountCApply.setMaxacr(new BigDecimal(maxAcr));
					}
					
					accountCApplyDao.updateAccountCApply(accountCApply);

					// TODO: 2017/5/3 以下更新记帐卡银行信息表不知道要不要删掉
					AccountCApply temp = accountCApplyDao.findById(accountCApply.getId());
					//修改  记帐卡银行信息表记录
					AccountBankInfo accountBankInfo = accountBankInfoDao.findSubAccountNo(temp.getSubAccountNo());
					if(accountBankInfo != null){
						if(StringUtil.isNotBlank(validity)){
							accountBankInfo.setValidity(dayFormat.parse(validity));
						}
						if(StringUtil.isNotBlank(virenum)){
							accountBankInfo.setVirenum(Integer.parseInt(virenum));
						}
						if(StringUtil.isNotBlank(maxAcr)){
							accountBankInfo.setMaxAcr(new BigDecimal(maxAcr));
						}
						accountBankInfoDao.update(accountBankInfo);
					}
					
					/*//记帐卡业务记录
					Long id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
					AccountCBussiness accountCBussiness = new AccountCBussiness();
					accountCBussiness.setId(id);
					accountCBussiness.setAccountId(Long.parseLong(map.get("subAccountId").toString()));
					accountCBussiness.setUserId(Long.parseLong(map.get("customerid").toString()));
					accountCBussiness.setState("25");
					//---------------------------------------------------------------
					accountCBussiness.setOperId(1l);
					accountCBussiness.setPlaceId(1l);
					accountCBussiness.setTradeTime(new Date());
					accountCBussinessDao.save(accountCBussiness);*/
				}else if(oldState.equals("1")&&newState.equals("4")){//审批不通过
					for(int i=0;i<size;i++){
						newCardApplyDao.updateBail(CustomPointTypeArr[i],"0");//已使用
					}
				}
				
				//记帐卡业务记录
				Long id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(id);
				accountCBussiness.setAccountId(Long.parseLong(map.get("subAccountId").toString()));
				accountCBussiness.setUserId(Long.parseLong(map.get("customerid").toString()));
				accountCBussiness.setState("23");//23.	新增卡申请审核
				accountCBussiness.setTradeTime(new Date());
				accountCBussinessDao.save(accountCBussiness);

				logger.error("营运系统，记帐卡新增申请更新状态成功！newCardId：="+newCardId+";state：="+oldState);
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡新增申请更新状态失败！newCardId：="+newCardId+";state：="+oldState);
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡新增申请更新状态失败！newCardId：="+newCardId+";state：="+oldState);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/** 
	 * 记帐卡迁移列表信息
	 */
	@Override
	public Pager migrateList(Pager pager, Customer customer,Long migrateId,String bankAccount, String startTime,
			String endTime, String state) {
		try {
			return migrateDao.migrateList(pager,customer,migrateId,bankAccount,startTime,endTime,state);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡迁移申请查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡迁移申请查询失败！");
		}
	}

	/**
	 * 记帐卡迁移详情信息
	 */
	@Override
	public Map<String, Object> migrateInfo(Long migrateId) {
		try {
			return migrateDao.migrateInfo(migrateId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡迁移申请查询详情失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡迁移申请查询详情失败！");
		}
	}

	/**
	 * 迁移审批通过：
	 * 保存：
		1.修改记帐卡迁移记录：状态，审批人，审批时间
		若更新状态为：审核通过，则还需一下操作
		2.新增记帐卡历史记录[8:迁移]
		3.修改记帐卡记录的账户，历史id
		4.客服流水（以卡为依据）[15：迁移]
	 */
	@Override
	public boolean updateMigrateState(Long migrateId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime) {
		//TODO 迁移
		//Date now = new Date();
		try {
			MigrateDetail migrateDetail = migrateDao.getMigrateDetailByMigrateId(migrateId, oldState);
				
			//审核通过往清算插入一条数据
			if (migrateDetail!=null){
				migrateDao.updateMigrateState(migrateId,newState,oldState,approver,approverNo,approverName,appTime);
				if("2".equals(newState)) {
					Migrate migrate = migrateDao.findById(migrateId);
					AccountCApply oldAccountCApply = accountCApplyDao.findBySubAccountInfoId(migrate.getOldAccountId());
					AccountCApply newAccountCApply = accountCApplyDao.findBySubAccountInfoId(migrate.getNewAccountId());
					
					AccCardTransferSend accCardTransferSend = new AccCardTransferSend();
					accCardTransferSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO"));
					accCardTransferSend.setCardCode(migrateDetail.getCardNo());
					accCardTransferSend.setOldAccountId(oldAccountCApply.getBankAccount());//2017年10月21日18:10:00 修改为获取银行账号(hzw)
					accCardTransferSend.setOldBankNo(Long.parseLong(oldAccountCApply.getObaNo()));
					accCardTransferSend.setNewAccountId(newAccountCApply.getBankAccount());//2017年10月21日18:10:00 修改为获取银行账号(hzw)
					accCardTransferSend.setNewBankNo(Long.parseLong(newAccountCApply.getObaNo()));
					accCardTransferSend.setFlag(new Long(1));//新账号
					accCardTransferSend.setReqTime(migrate.getOperTime());
					accCardTransferSend.setEffectTime(migrate.getEffectTime());
					accCardTransferSend.setUpdateTime(new Date());
					accCardTransferSendDao.save(accCardTransferSend);
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage()+"营运系统，记帐卡新增申请更新状态失败！migrateId：="+migrateId+";state：="+oldState);
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡迁移申请更新状态失败！migrateId：="+migrateId+";state：="+oldState);
		}
	}


	/**
	 * 过户列表信息
	 */
	@Override
	public Pager transferList(Pager pager, Customer customer,Long transferId,String bankAccount, String startTime,
			String endTime, String state) {
		try {
			return transferApplyDao.transferList(pager,customer,transferId,bankAccount,startTime,endTime,state);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡过户申请查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡过户申请查询失败！");
		}
	}

	/**
	 * 过户详情信息
	 */
	@Override
	public Map<String, Object> transferInfo(Long transferId) {
		try {
			return transferApplyDao.transferInfo(transferId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡过户申请查询详情失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡过户申请查询详情失败！");
		}
	}
	/**
	 * 过户审批通过：
		保存：
		1.修改记帐卡过户记录
		若新的审批状态：审核通过，则还需一下操作
		2.新增记帐卡历史记录[7]
		3.修改记帐卡记录的账户id，客户id，卡签绑定标志，历史id
		4.修改车卡绑定关系（以卡为依据）
		5.客服流水（以卡为依据）[16]
		6.旧客户减保证金，新客户增加保证金,并添加保证金账户历史
	 */
	@Override
	public boolean updateTransferState(Long transferId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime, AccountCBussiness accountCBussiness) {
		// TODO 过户
		//Date now = new Date();
		try {
			//过户明细
			List<Map<String, Object>> list = transferApplyDao.getTransferDetailByTransferId(transferId, oldState);
			if (list!=null && !list.isEmpty()) {
				//更新过户申请记录
				transferApplyDao.updateTransferState(transferId,newState,oldState,approver,approverNo,approverName,appTime);
				//1待审批、2审批通过、3审批不通过、4审核通过、5审核不通过
				if ("2".equals(oldState)&&"4".equals(newState)) {
					//记帐卡卡信息
					List<AccountCInfo> accountCInfoList = new ArrayList<AccountCInfo>();
					List<AccountCApply> accountCApplyList = new ArrayList<AccountCApply>();
					
					AccountCInfo accountCInfo = null;
					//创建记帐卡信息历史集合
					List<AccountCInfoHis> hisList = new ArrayList<AccountCInfoHis>();
					AccountCInfoHis accountCInfoHis = null;
					//车卡绑定
					List<CarObuCardInfo> carObuCardInfoList = new ArrayList<CarObuCardInfo>();
					CarObuCardInfo carObuCardInfo = null;
					/*//创建记帐卡业务记录集合
					List<AccountCBussiness> bussList = new ArrayList<AccountCBussiness>();
					AccountCBussiness accountCBussiness = null;*/
					//创建客服流水
					List<ServiceFlowRecord> serviceFlowRecordList = new ArrayList<ServiceFlowRecord>();
					ServiceFlowRecord serviceFlowRecord = null;
					
					Long id = null;
					
					//保证金
					BigDecimal bail = new BigDecimal(0);
					for (Map<String, Object> map : list) {
						//创建记帐卡历史对象
						id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
						accountCInfoHis = new AccountCInfoHis();
						accountCInfoHis.setId(id);
						accountCInfoHis.setGenReason("7");
						accountCInfoHis.setCardNo(map.get("cardno").toString());
						hisList.add(accountCInfoHis);
						
						//创建记帐卡信息对象
						accountCInfo = new AccountCInfo();
						accountCInfo.setCardNo(map.get("cardno").toString());
						accountCInfo.setCustomerId(Long.parseLong(map.get("newCustomerid").toString()));
						accountCInfo.setAccountId(Long.parseLong(map.get("newaccountid").toString()));
						accountCInfo.setBind("0");
						accountCInfo.setHisSeqId(id);
						accountCInfo.setState(map.get("state").toString());
						accountCInfoList.add(accountCInfo);
						
						//车卡绑定关系
						carObuCardInfo = new CarObuCardInfo();
						carObuCardInfo.setAccountCID(Long.parseLong(map.get("accountCid").toString()));
						carObuCardInfoList.add(carObuCardInfo);
						
						//累加每张卡的单卡保证金
						//System.out.println(map.get("accountCBail"));
						if(map.get("accountCBail")!=null){
							bail = bail.add(new BigDecimal(map.get("accountCBail").toString()));
						}
						
						
						/*//记帐卡业务记录
						id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
						accountCBussiness = new AccountCBussiness();
						accountCBussiness.setId(id);
						accountCBussiness.setCardNo(accountCInfo.getCardNo());
						accountCBussiness.setUserId(accountCInfo.getCustomerId());
						accountCBussiness.setOldUserId(Long.parseLong(map.get("customerID").toString()));
						accountCBussiness.setAccountId(Long.parseLong(map.get("newaccountid").toString()));
						accountCBussiness.setOldAccountId(Long.parseLong(map.get("oldaccountid").toString()));
						accountCBussiness.setState("23");
						accountCBussiness.setLastState(accountCInfo.getState());
						//---------------------------------------------------------------
						accountCBussiness.setTradeTime(now);
						accountCBussiness.setOperId(1l);
						accountCBussiness.setPlaceId(1l);
						bussList.add(accountCBussiness);*/
						
						//客服流水
						id = sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO");
						serviceFlowRecord = new ServiceFlowRecord();
						serviceFlowRecord.setId(id);
						serviceFlowRecord.setClientID(accountCInfo.getCustomerId());
						//要记录旧客户 不？？？？？？？？？？？？
						serviceFlowRecord.setCardTagNO(accountCInfo.getCardNo());
						serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
						serviceFlowRecord.setServicePTypeCode(2);
						serviceFlowRecord.setServiceTypeCode(16);
						serviceFlowRecord.setCreateTime(new Date());
						//---------------------------------------------------------------
						serviceFlowRecord.setOperID(accountCBussiness.getOperId());
						serviceFlowRecord.setPlaceID(accountCBussiness.getPlaceId());
						serviceFlowRecord.setOperName(accountCBussiness.getOperName());
						serviceFlowRecord.setOperNo(accountCBussiness.getOperNo());
						serviceFlowRecord.setPlaceName(accountCBussiness.getPlaceName());
						serviceFlowRecord.setPlaceNo(accountCBussiness.getPlaceNo());
						serviceFlowRecordList.add(serviceFlowRecord);
						//--------------------------------------------------------------------
						SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
						AccountCApply accountCApply = accountCApplyDao.findById(subAccountInfo.getApplyID());
						accountCApplyList.add(accountCApply);
					}
					accountCInfoHisDao.batchSaveForbranches(hisList);
					accountCDao.batchUpdateWithCustomer(accountCInfoList);
					carObuCardInfoDao.batchUpdate(carObuCardInfoList);
					//accountCBussinessDao.batchSave(bussList);
					serviceFlowRecordDao.batchSave(serviceFlowRecordList);
					
					//旧客户减保证金金额
					id = sequenceUtil.getSequenceLong("SEQ_CSMSBailAccountInfoHis_NO");
					bailAccountInfoDao.updateWithHisByTransfer(id,Long.parseLong(list.get(0).get("customerID").toString()),bail,"4","-");//4、过户扣减
					//新客户加保证金金额
					id = sequenceUtil.getSequenceLong("SEQ_CSMSBailAccountInfoHis_NO");
					bailAccountInfoDao.updateWithHisByTransfer(id,Long.parseLong(list.get(0).get("newCustomerid").toString()),bail,"5","+");//5、过户增加

					//--------------------------------------------
					//原清算数据，没用了
					/*aCinfoDao.batchSaveACinfo(accountCApplyList);*/
					
					//存清算数据
					
					
				}
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，记帐卡过户申请更新状态失败！transferId：="+transferId+";state：="+oldState);
			e.printStackTrace();
			throw new ApplicationException("营运系统，记帐卡过户申请更新状态失败！transferId：="+transferId+";state：="+oldState);
		}
	}
	
	/**
	 * 账号名称变更列表信息
	 */
	@Override
	public Pager accountCRenameList(Pager pager, Customer customer, String bankAccount, String state) {
		try {
			return accountNCApplyDao.accountCRenameList(pager,customer,bankAccount,state);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，账户名称变更申请查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，账户名称变更申请查询失败！");
		}
	}

	/**
	 * 账号名称变更详情信息
	 */
	@Override
	public Map<String, Object> accountCRenameInfo(Long accountCRenameId) {
		try {
			return accountNCApplyDao.accountCRenameInfo(accountCRenameId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，账户名称变更申请查询详情失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，账户名称变更申请查询详情失败！");
		}
	}
	/**
	 * 账户名称变更申请通过：
	 * 保存：
		1.新增账户名称变更历史记录
		2.修改账户名称变更记录：状态，审批时间，审批人，历史id
		若新的审批状态为：审批通过，则还需一下操作
		3.新增记帐卡申请历史记录
		4.修改记帐卡申请记录的开户帐户户名
		5.记录客服流水（后续处理）
	 */
	@Override
	public boolean updateAccountCRenameState(Long accountCRenameId, String newState, String oldState, String approver,String approverNo,String approverName, String appTime, AccountCBussiness accountCBussiness) {
		// TODO 账户重命名
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			Long hisId = null;
			Map<String, Object> accountCRename = accountNCApplyDao.findAccountCRenameById(accountCRenameId, oldState);
			if (accountCRename != null) {
				//数据库操作
				//账户名称变更历史
				hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountNCapplyhis_NO");
				accountNCApplyDao.saveAccountCRenameHis(accountCRenameId,hisId,"1");
				//账户名称变更
				accountNCApplyDao.updateAccountCRenameState(accountCRenameId,newState,oldState,hisId,approver,approverNo,approverName,appTime);
				AccountCApply temp = null;
				if (oldState.equals("1")&&newState.equals("2")) {
					//记帐卡申请历史记录
					hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
					accountCApplyDao.saveAccountCApplyHis(Long.parseLong(accountCRename.get("accountCApplyid").toString()),"1",hisId);
					//记帐卡申请记录
					AccountCApply accountCApply = new AccountCApply();
					accountCApply.setId(Long.parseLong(accountCRename.get("accountCApplyid").toString()));
					accountCApply.setAccName(accountCRename.get("newaccname").toString());
					accountCApply.setHisseqId(hisId);
					accountCApplyDao.updateAccountCApply(accountCApply);
					
					temp = accountCApplyDao.findById(accountCApply.getId());
					if(temp == null){
						logger.error("数据异常：无法找到记帐卡申请表记录 ");
						throw new ApplicationException("数据异常：无法找到记帐卡申请表记录 ");
					}
					//同时变更记帐卡银行信息
					AccountBankInfo accountBankInfo = accountBankInfoDao.findSubAccountNo(temp.getSubAccountNo());
					if(accountBankInfo != null){
						accountBankInfo.setAccName(accountCRename.get("newaccname").toString());
						accountBankInfoDao.update(accountBankInfo);
					}
					temp.setOperTime((Date)accountCRename.get("OPERTIME"));
					temp.setAppTime(format.parse(appTime));
					//发送数据给铭鸿接口
					Customer customer = customerDao.findById(temp.getCustomerId());
					accCardUserInfoService.saveAccCardUserInfo(temp, customer, "银行账户变更", 0);
				}
				/*//记帐卡业务记录
				Long id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(id);
				accountCBussiness.setAccountId(Long.parseLong(accountCRename.get("subAccountId").toString()));
				accountCBussiness.setUserId(Long.parseLong(accountCRename.get("customerid").toString()));
				accountCBussiness.setState("26");
				accountCBussiness.setTradeTime(new Date());
				accountCBussinessDao.save(accountCBussiness);*/
				
				/*//客服流水
				id = sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO");
				ServiceFlowRecord serviceFlowRecord = new ServiceFlowRecord();
				serviceFlowRecord.setId(id);
				serviceFlowRecord.setClientID(accountCInfo.getCustomerId());
				serviceFlowRecord.setCardTagNO(accountCInfo.getCardNo());
				serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
				serviceFlowRecord.setServicePTypeCode(2);
				serviceFlowRecord.setServiceTypeCode(16);
				//---------------------------------------------------------------
				serviceFlowRecord.setOperID(1l);
				serviceFlowRecord.setPlaceID(1l);*/
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，账户名称变更申请更新状态失败！accountCRenameId：="+accountCRenameId+";state：="+oldState);
			e.printStackTrace();
			throw new ApplicationException("营运系统，账户名称变更申请更新状态失败！accountCRenameId：="+accountCRenameId+";state：="+oldState);
		} catch (ParseException e) {
			logger.error("时间格式化异常");
			throw new ApplicationException("时间格式化异常");
		}
	}



	/**
	 * 设置单卡保证金(记帐卡申请表)
	 * type:操作类型，1：删除保证金，2：设置保证金，3：保证金确认
	 */
	@Override
	public boolean updateBail(Long accountCApplyId, String state, String carBail,String truckBail, String approver,String approverNo,String approverName, String appTime, String type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			//记帐卡审批状态的状态值：0未审批、1银行审批中、2银行审批通过、3银行审批不通过、4营运审批通过、5营运审批不通过、6营运审核通过、7营运审核不通过、8设置保证金、9保证金确认
			if(state.equals("6") || state.equals("8")|| state.equals("2")){
				Map<String, Object> map = accountCApplyDao.findSubAccountCByApplyId(accountCApplyId, state);
				if (map!=null) {
					//记帐卡申请历史记录
					Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
					accountCApplyDao.saveAccountCApplyHis(accountCApplyId,"1",hisId);
					//记帐卡申请记录
					AccountCApply accountCApply = new AccountCApply();
					accountCApply.setId(accountCApplyId);
					accountCApply.setHisseqId(hisId);
					accountCApply.setApprOver(Long.parseLong(approver));
					accountCApply.setApproverNo(approverNo);
					accountCApply.setApproverName(approverName);
					accountCApply.setAppTime(format.parse(appTime));
					//accountCApply.setBail(new BigDecimal(Double.parseDouble(bail)));
					accountCApply.setBail(new BigDecimal(carBail));//卡车
					accountCApply.setTruckBail(new BigDecimal(truckBail));//货车单卡保证金
					if (type.equals("1")) {
						accountCApply.setAppState("2");
					}
					// TODO: 2017/4/19 这里原来需求是要设置8和9,现在初次申请已经不需要设置保证金
					/*else if (type.equals("2")) {
						accountCApply.setAppState("8");
					}
					else if (type.equals("3")) {
						accountCApply.setAppState("6");
					}*/
					accountCApplyDao.updateAccountCApply(accountCApply);
					return true;
				}
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，设置单卡保证金失败！accountCApplyId:="+accountCApplyId);
			e.printStackTrace();
			throw new ApplicationException("营运系统，设置单卡保证金失败！accountCApplyId:="+accountCApplyId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 设置单卡保证金(新增卡申请表)
	 * type:操作类型，1：删除保证金，2：设置保证金，3：保证金确认
	 * 保存：
	 *  1.新增新增卡申请历史记录
		2.修改新增卡申请记录 状态、历史id，审批人，审批时间，保证金
		如果是保证金确认，则还需
	 * 	3.新增记帐卡申请历史记录
		4.修改记帐卡申请记录的申请数量、剩余数量、历史id、新增卡标识等
	 */
	@Override
	public boolean updateNewCardBail(Long newCardId, String state, String carBail,String truckBail, String approver,String approverNo,String approverName, String appTime, String type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			//1未审批、2营运审批中、3营运审批通过、4营运审批不通过、5营运审核通过、6营运审核不通过、7设置保证金、8保证金确认
			if(state.equals("5") || state.equals("7") || state.equals("3")){
				Map<String, Object> map = newCardApplyDao.findSubAccountCByNewApplyId(newCardId,state);
				if (map!=null) {
					//新增卡申请历史记录
					//Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSNewCardapplyhis_NO");
					//newCardApplyDao.saveNewCardApplyHis(newCardId,"1",hisId);
					NewCardApply oldNewCardApply = newCardApplyDao.findById(newCardId);
					NewCardApplyHis newCardApplyHis = new NewCardApplyHis();
					Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSNewCardapplyhis_NO");
					newCardApplyHis.setId(hisId);
					newCardApplyHis.setGenReason("1");
					newCardApplyHisDao.saveHis(newCardApplyHis, oldNewCardApply);
					//新增卡申请记录
					NewCardApply newCardApply = new NewCardApply();
					newCardApply.setId(newCardId);
					//newCardApply.setBail(new BigDecimal(Double.parseDouble(bail)));
					newCardApply.setBail(new BigDecimal(carBail)); //卡车单卡保证金
					newCardApply.setTruckBail(new BigDecimal(truckBail));//货车单卡保证金
					
					newCardApply.setHisseqId(hisId);
					newCardApply.setAppTime(format.parse(appTime));
					newCardApply.setApprOver(Long.parseLong(approver));
					newCardApply.setApproverNo(approverNo);
					newCardApply.setApproverName(approverName);
					if (type.equals("1")) {
						newCardApply.setAppState("3");
					}else if (type.equals("2")) {
						newCardApply.setAppState("7");
					}else if (type.equals("3")) {
						newCardApply.setAppState("8");
					}
					newCardApplyDao.updateNewCardApply(newCardApply);
					//保证金确认时，需要修改记帐卡初次申请记录
					if (type.equals("3")) {
						//记帐卡申请历史记录
						hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
						accountCApplyDao.saveAccountCApplyHis(Long.parseLong(map.get("applyid").toString()),"1",hisId);
						//记帐卡申请记录
						AccountCApply accountCApply = new AccountCApply();
						accountCApply.setId(Long.parseLong(map.get("applyid").toString()));
						accountCApply.setReqcount(Long.parseLong(map.get("applyRegcount").toString())+Long.parseLong(map.get("newCardReqcount").toString()));
						accountCApply.setResidueCount(Long.parseLong(map.get("residuecount").toString())+Long.parseLong(map.get("newCardReqcount").toString()));
						accountCApply.setHisseqId(hisId);
						
						accountCApply.setAppTime(format.parse(appTime));
						accountCApply.setApprOver(Long.parseLong(approver));
						accountCApply.setApproverNo(approverNo);
						accountCApply.setApproverName(approverName);
						accountCApply.setShutDownStatus(AccountCShutDownStatusEnum.start.getValue());
						accountCApply.setNewCardFlag("1");//1:是   0:否
						
						accountCApplyDao.updateAccountCApply(accountCApply);
					}
					return true;
				}
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，设置单卡保证金失败！accountCApplyId:="+newCardId);
			e.printStackTrace();
			throw new ApplicationException("营运系统，设置单卡保证金失败！accountCApplyId:="+newCardId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断预迁移的卡是否都为正常卡
	 */
	@Override
	public boolean migrateCardIsOk(Long migrateId) {
		try {
			return migrateDao.migrateCardIsOk(migrateId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，判断预迁移的卡是否都为正常卡失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，判断预迁移的卡是否都为正常卡失败");
		}
	}

	/**
	 * 判断预过户的卡是否都为正常卡
	 */
	@Override
	public boolean transferCardIsOk(Long transferId) {
		try {
			return transferApplyDao.transferCardIsOk(transferId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，判断预过户的卡是否都为正常卡失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，判断预过户的卡是否都为正常卡失败");
		}
	}

	/**
	 * 批量取消电子邮件
	 * 
	 * serItem:[1:邮寄地址，2：电子邮件]
	 * param(数据格式：json)
		如:
		{" param ":{" approver ":1," appTime ":"2016-01-01 12:01:01","resultList":[{"type":1," cardno ":"1"," bankAccount ":"1"}{第二行}…] }}

		approver（审批人）
		appTime（审批时间）[时间格式：yyyy-MM-dd HH:mm:ss]
		卡类型、卡号、银行账号
	           卡类型：type(1、储值卡，2、记帐卡)
		卡号：cardno
		银行账号：bankAccount
  
	 * 		
	 * @return
	 */
	@Override
	public String batchUpdateBillGet(String param,String serItem) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			//List<Map> list = JSON.parseArray(param,Map.class);
			JSONObject paramJson = (JSONObject) JSONObject.fromObject(param).get("param");
			JSONArray array = (JSONArray) paramJson.get("resultList");
			List<Map> list = JSON.parseArray(array.toString(),Map.class);
			
			int i = 1;
			if (list != null && !list.isEmpty()) {
				StringBuffer errorMsg = new StringBuffer("");
				int errorCount = 0;
				JSONObject json = new JSONObject();
				StringBuffer billGetCardBankNo = new StringBuffer("");
				//获取sql拼接字符串
				for (Map<String, String> map : list) {
					if (map.get("type")==null
							||(map.get("type")!=null&&!map.get("type").equals("1")&&!map.get("type").equals("2"))
							||(map.get("type")!=null&&map.get("type").equals("1")&&map.get("cardno")==null)
							||(map.get("type")!=null&&map.get("type").equals("2")&&map.get("bankAccount")==null)) {
						errorMsg.append(","+i+":传入的数据不全");
						errorCount++;
					} else if (map.get("type").equals("1")) {
						List l = billGetDao.findByNoAndType(map.get("cardno"), "1");
						if(l.isEmpty()){
							errorMsg.append(","+i+":没有卡类型为储值卡、卡号为"+map.get("cardno")+"的数据");
							errorCount++;
						}
						
						billGetCardBankNo.append(",'"+map.get("cardno")+"'");
					} else if (map.get("type").equals("2")){
						List l = billGetDao.findByNoAndType(map.get("bankAccount"), "2");
						if(l.isEmpty()){
							errorMsg.append(","+i+":没有卡类型为记帐卡、银行账号为"+map.get("bankAccount")+"的数据");
							errorCount++;
						}
						
						billGetCardBankNo.append(",'"+map.get("bankAccount")+"'");
					} 
					
					i++;
				}
				//有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}
				
				//查询数据库
				Map<String, Map<String, Object>> billgetMaps = billGetDao.findAllByCardBankNo(billGetCardBankNo.toString().substring(1));
				
				Map<String, Object> billGetMap = null;
				i = 1;
				for (Map<String, String> map : list) {
					if (map.get("type").equals("1")) {
						billGetMap = billgetMaps.get(map.get("cardno"));
					} else if (map.get("type").equals("2")){
						billGetMap = billgetMaps.get(map.get("bankAccount"));
					}
					
					//System.out.println("i:="+i+";"+billGetMap.get("SerItem").toString()+";"+billGetMap.get("SerItem").toString().contains(serItem));
					//判断该卡是不是只有一种服务方式
					/*int billCount = 0;
					if(billGetMap!=null){
						String [] arrayBill = billGetMap.get("SerItem").toString().split(",");
						for(String bill:arrayBill){
							if(StringUtil.isNotBlank(bill)){
								billCount++;
							}
						}
					}*/
					
					if (billGetMap == null) {
						errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号":"银行账号")+"不存在");
						errorCount++;
					}else if(billGetMap != null && billGetMap.get("SerItem")==null){
						errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号"+map.get("cardno"):"银行账号"+map.get("bankAccount"))+"不存在服务方式");
						errorCount++;
					}else if(billGetMap != null && !billGetMap.get("SerItem").toString().contains(serItem)){
						errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号"+map.get("cardno"):"银行账号"+map.get("bankAccount"))+"不存在"+(serItem.equals("1")?"普通邮寄":"电子邮件")+"的服务方式");
						errorCount++;
						/*if(serItem.equals(SerItemEnum.invoiceBillService.getValue())){
							errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号"+map.get("cardno"):"银行账号"+map.get("bankAccount"))+"不存在普通邮寄的服务方式");
							errorCount++;
						}else if(serItem.equals(SerItemEnum.invoiceBillGetAmtService.getValue())){
							errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号"+map.get("cardno"):"银行账号"+map.get("bankAccount"))+"不存在邮寄到付的服务方式");
							errorCount++;
						}else if(serItem.equals(SerItemEnum.emailService.getValue())){
							errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号"+map.get("cardno"):"银行账号"+map.get("bankAccount"))+"不存在电子清单服务的服务方式");
							errorCount++;
						}*/
						
					}/*else if(billGetMap != null && billCount <= 1){
						//如果服务方式只有一种以下，则不能取消
						errorMsg.append(","+i+":"+(map.get("type").equals("1")?"卡号":"银行账号")+"只有一种服务方式，不能取消");
						errorCount++;
					}*/
					i = i+1;
				}
				//有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}
				
				//更新服务方式
				List<BillGet> billGetList = new ArrayList<BillGet>();
				List<BillGetHis> billGetHisList = new ArrayList<BillGetHis>();
				BillGet billGet = null;
				BillGetHis billGetHis = null;
				Long id = null;
				for (Map<String, String> map : list) {
					id = sequenceUtil.getSequenceLong("SEQ_CSMS_bill_get_his_NO");
					//服务方式
					billGet = new BillGet();
					billGet.setHisSeqId(id);
					billGet.setOperId(paramJson.getLong("approver"));
					billGet.setOperNo(paramJson.getString("approverNo"));
					billGet.setOperName(paramJson.getString("approverName"));
					billGet.setOperTime(format.parse(paramJson.getString("appTime")));
					if (map.get("type").equals("1")) {
						billGetMap = billgetMaps.get(map.get("cardno"));
					} else if (map.get("type").equals("2")){
						billGetMap = billgetMaps.get(map.get("bankAccount"));
					}
					billGet.setCardBankNo(billGetMap.get("cardbankno").toString());
					billGet.setCardAccountID(Long.parseLong(billGetMap.get("CardAccountID").toString()));
					//为什么有的服务方式有空格有的没有空格，这样我很难做啊
					String [] serItemArr = billGetMap.get("SerItem").toString().split(",");
					StringBuffer finalSerIem = new StringBuffer("");
					for(String str:serItemArr){
						if(!serItem.equals(str.trim())){
							finalSerIem.append(","+str.trim());
						}
					}
					if(StringUtil.isNotBlank(finalSerIem.toString()))billGet.setSerItem(finalSerIem.substring(1));
					//billGet.setSerItem(billGetMap.get("SerItem").toString().replace(","+serItem, "").replace(serItem+",", ""));
					billGetList.add(billGet);
					
					//服务方式历史
					billGetHis = new BillGetHis();
					billGetHis.setCardBankNo(billGetMap.get("cardbankno").toString());
					billGetHis.setCardAccountID(Long.parseLong(billGetMap.get("CardAccountID").toString()));
					billGetHis.setId(id);
					billGetHis.setGenReason("3");
					billGetHisList.add(billGetHis);
					
				}
				
				billGetHisDao.batchSave(billGetHisList);
				billGetDao.batchUpdateBillGet(billGetList);
				return "true";
			}
			return "没有可处理的数据";
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，批量去除服务方式失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，批量去除服务方式失败");
		} catch (ParseException e) {
			e.printStackTrace();
			return "操作时间格式不正确";
		}
	}



	/**
	 * 批量修改邮寄地址 [type=1]

	 * 参数：  {" param ":{" approver ":1," appTime ":"2016-01-01 12:01:01","resultList":[{" userNo ":"1"," address ":"1"," zipCode ":"1"}{第二行}…] }}
	 * 
	 * 批量修改电子邮件地址  [type=2]

	 * 参数：  {" param ":{" approver ":1," appTime ":"2016-01-01 12:01:01","resultList":[{" userNo ":"1"," email ":"1"}{第二行}…] }}
	 * 			
	 * @return
	 */
	@Override
	public String batchUpdateAddressEmail(String param,String type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		try {
			JSONObject paramJson = (JSONObject) JSONObject.fromObject(param).get("param");
			JSONArray array = (JSONArray) paramJson.get("resultList");
			List<Map> list = JSON.parseArray(array.toString(),Map.class);
			
			if (list != null && !list.isEmpty()) {
				StringBuffer errorMsg = new StringBuffer("");
				int errorCount = 0;
				JSONObject json = new JSONObject();
				StringBuffer userNo = new StringBuffer("");
				int i = 1;
				//获取sql拼接字符串
				for (Map<String, String> map : list) {
					if (map.get("userNo")==null
							||(type.equals("1")&&(map.get("address")==null||map.get("zipCode")==null))
							||(type.equals("2")&&map.get("email")==null)) {
						errorMsg.append(","+i+":传入的数据不全");
						errorCount++;
					}else if(type.equals("1")&&(StringUtil.length(map.get("address"))>255||StringUtil.length(map.get("zipCode"))>6)){
						errorMsg.append(","+i+":传入的"+(StringUtil.length(map.get("address"))>255?"地址长度过长;":"")+(StringUtil.length(map.get("zipCode"))>6?"邮编长度过长":""));
						errorCount++;
					}else if(type.equals("2")&&!StringUtil.regExp(map.get("email"),"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")){
						errorMsg.append(","+i+":传入的电子邮箱格式有误");
						errorCount++;
					}else {
						userNo.append(",'"+map.get("userNo")+"'");
					}
					i++;
				}
				
				//有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}
				
				//查询数据库
				Map<String, Map<String, Object>> customerMaps = customerDao.findAllByUserNo(userNo.toString().substring(1));
				
				Map<String, Object> customerMap = null;
				i = 1;
				for (Map<String, String> map : list) {
					customerMap = customerMaps.get(map.get("userNo"));
					if (customerMap == null) {
						errorMsg.append(","+i+":客户号不存在");
						errorCount++;
					}
					i = i+1;
				}
				//有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}
				//更新邮寄地址
				List<Customer> customerList = new ArrayList<Customer>();
				List<CustomerHis> customerHisList = new ArrayList<CustomerHis>();
				Customer customer = null;
				CustomerHis customerHis = null;
				//记帐卡业务记录
				Long id = null;
				for (Map<String, String> map : list) {
					id = sequenceUtil.getSequenceLong("SEQ_CSMS_Customer_his_NO");
					customerHis = new CustomerHis();
					customerHis.setId(id);
					customerHis.setUserNo(map.get("userNo"));
					customerHis.setGenReason("3");
					customerHisList.add(customerHis);
					
					customer = new Customer();
					customer.setOperId(paramJson.getLong("approver"));
					customer.setOperNo(paramJson.getString("approverNo"));
					customer.setOperName(paramJson.getString("approverName"));
					customer.setUpDateTime(format.parse(paramJson.getString("appTime")));
					customer.setUserNo(map.get("userNo"));
					if (type.equals("1")) {
						customer.setAddr(map.get("address"));
						customer.setZipCode(map.get("zipCode"));
					}else if (type.equals("2")) {
						customer.setEmail(map.get("email"));
					}
					customer.setHisSeqId(id);
					customerList.add(customer);
					
				}
				customerHisDao.batchSave(customerHisList);
				customerDao.batchUpdateAddressEmail(customerList,type);
				return "true";
			}
			return "没有可处理的数据";
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，批量修改邮寄地址失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，批量修改邮寄地址失败");
		} catch (ParseException e) {
			e.printStackTrace();
			return "操作时间格式不正确";
		}
	}

	@Override
	public Map<String,Object> deleteNewCardVehicle(Long id){
		Map<String,Object> result=new HashMap<>();
		try {
			Map<String,Object> map=accountCApplyDao.findNewCardVehicle(id);
			if(map==null){
				result.put("result",false);
				result.put("message","该新增卡车辆信息找不到");
			}else if(map.get("state")!=null&&"1".equals(map.get("state").toString())){
				accountCApplyDao.deleteNewCardVehicle(id);
				
				//将记录加入历史表
				Long newcardapplyid = Long.parseLong(map.get("newcardapplyid").toString());
				NewCardApply newCardApply = newCardApplyDao.findById(newcardapplyid);
				NewCardApplyHis newCardApplyHis = new NewCardApplyHis();
				Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSNewCardapplyhis_NO");
				newCardApplyHis.setId(hisId);
				newCardApplyHis.setGenReason("2");
				newCardApplyHisDao.saveHis(newCardApplyHis, newCardApply);
				
				//删除后需要将申请数量减1
				newCardApply.setReqcount(newCardApply.getReqcount() - 1);
				newCardApplyDao.update(newCardApply);
				
				//将记录加入历史表
				AccountCApply accountCApply = accountCApplyDao.findById(newCardApply.getApplyId());
				Long accountChisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCapplyhis_NO");
				accountCApplyDao.saveAccountCApplyHis(accountCApply.getId(), "1" ,accountChisId);
				
				//删除后需要将申请数量和剩余数量减1
				accountCApply.setReqcount(accountCApply.getReqcount() - 1);
				accountCApply.setResidueCount(accountCApply.getResidueCount() - 1);
				accountCApplyDao.updateAccountCApply(accountCApply);
				
				result.put("result",true);
				result.put("message","操作成功");
			}else if(map.get("state")!=null&&"0".equals(map.get("state").toString())){
				result.put("result",false);
				result.put("message","该新增卡车辆信息已发行");
			}else if(map.get("state")!=null&&"2".equals(map.get("state").toString())){
				result.put("result",false);
				result.put("message","该新增卡车辆信息已删除");
			}else{
				result.put("result",false);
				result.put("message","未知错误,请联系管理员");
			}
			return result;

		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，删除新增卡车辆信息失败！id:="+id);
			e.printStackTrace();
			throw new ApplicationException("营运系统，删除新增卡车辆信息失败！id:="+id);

		}

	}

	/**
	 * 单卡保证金详情
	 */
	@Override
	public Map<String, Object> bailInfo(Long accountCApplyId) {
		try {
			return accountCApplyDao.bailInfo(accountCApplyId);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，保证金设置查询详情失败！accountCApplyId:="+accountCApplyId);
			e.printStackTrace();
			throw new ApplicationException("营运系统，保证金设置查询详情失败！accountCApplyId:="+accountCApplyId);
		}
	}

	/**
	 * 设置单卡保证金
	 */
	/*@Override
	public boolean updateBail(Long accountCApplyId, String state, String bail, String approver, String appTime) {
		// TODO 未完成
		try {
			Map<String, Object> map = accountCApplyDao.findSubAccountCByApplyId(accountCApplyId, state);
			if (map!=null) {
				//记帐卡申请历史记录
				Long hisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
				accountCApplyDao.saveAccountCApplyHis(accountCApplyId,"1",hisId);
				//记帐卡申请记录
				AccountCApply accountCApply = new AccountCApply();
				accountCApply.setId(accountCApplyId);
				//accountCApply.setApprOver(Long.parseLong(approver));
				accountCApply.setHisseqId(hisId);
				accountCApply.setBail(new BigDecimal(Double.parseDouble(bail)));
				accountCApplyDao.updateAccountCApply(accountCApply);
				
				return true;
			}
			return false;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，设置单卡保证金失败！accountCApplyId:="+accountCApplyId);
			e.printStackTrace();
			throw new ApplicationException("营运系统，设置单卡保证金失败！accountCApplyId:="+accountCApplyId);
		}
	}*/
	/**
	 * 保证金退还营运审批
	 */
	@Override
	public boolean saveBailBackApp(Bail bail,String newAppState,Long bussinessPlaceId) {
		boolean result = false;
		try {
			Customer customer = customerDao.findByUserNo(bail.getUserNo());
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			BailAccountInfo bailAccountInfo = bailAccountInfoDao.findByCustomerID(customer.getId());
			UnifiedParam unifiedParam = new UnifiedParam();
			unifiedParam.setType("33");//33保证金审批成功后增加主账户可退余额
			unifiedParam.setMainAccountInfo(mainAccountInfo);
			unifiedParam.setBailAccountInfo(bailAccountInfo);
			unifiedParam.setBailFee(bail.getBailFee().abs());
			unifiedParam.setPlaceId(bail.getPlaceId());
			unifiedParam.setOperId(bail.getOperId());
			unifiedParam.setOperName(bail.getOperName());
			unifiedParam.setOperNo(bail.getOperNo());
			unifiedParam.setPlaceName(bail.getPlaceName());
			unifiedParam.setPlaceNo(bail.getPlaceNo());
			if("3".equals(newAppState)){
				//保证金退还审批成功后，根据保证金退回申请记录对应退还金额去扣除保证金账户中的冻结金额，并将扣除的冻结金额增加到主账户的可退余额中，新增账户资金变动流水
				if(unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)){
					//此处记录的是否是已经增加可退余额的？
					MainAccountInfo newMainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
					//保证金退还申请审批成功后，记录一条主账户退款记录
					RefundInfo refundInfo = new RefundInfo();
					Long refundInfoID = sequenceUtil.getSequenceLong("SEQ_CSMSRefundInfo_NO");
					refundInfo.setId(refundInfoID);
					refundInfo.setMainId(customer.getId());//客户id
					refundInfo.setMainAccountId(newMainAccountInfo.getId());//主账户id
					refundInfo.setRefundType("2");//1：储值卡注销；2：保证金退还；3：账户退款
					refundInfo.setBalance(newMainAccountInfo.getBalance());
					refundInfo.setAvailableBalance(newMainAccountInfo.getAvailableBalance());
					refundInfo.setPreferentialBalance(newMainAccountInfo.getPreferentialBalance());
					refundInfo.setFrozenBalance(newMainAccountInfo.getFrozenBalance());
					refundInfo.setAvailableRefundBalance(newMainAccountInfo.getAvailableRefundBalance());
					refundInfo.setRefundApproveBalance(newMainAccountInfo.getRefundApproveBalance());
					if(bail.getBailFee()!=null)refundInfo.setCurrentRefundBalance(bail.getBailFee().abs());//本次退款金额
					else refundInfo.setCurrentRefundBalance(new BigDecimal("0"));//本次退款金额
					refundInfo.setBankNo(bail.getBankNo());
					refundInfo.setBankMember(bail.getBankMember());
					refundInfo.setBankOpenBranches(bail.getBankOpenBranches());
					refundInfo.setOperId(bail.getOperId());//默认为保证金退款申请的申请人
					refundInfo.setOperNo(bail.getOperNo());
					refundInfo.setOperName(bail.getOperName());
					refundInfo.setPlaceID(bail.getPlaceId());
					refundInfo.setPlaceNo(bail.getPlaceNo());
					refundInfo.setPlaceName(bail.getPlaceName());
					refundInfo.setRefundApplyTime(bail.getSetTime());
					
					refundInfo.setRefundApplyOper(bail.getPlaceId());//默认为保证金退款申请的申请网点
					/*refundInfo.setAuditId(auditId);
					refundInfo.setAuditTime(auditTime);
					refundInfo.setAuditStatus(auditStatus);*/
					refundInfo.setAuditStatus(RefundAuditStatusEnum.request.getValue());
					//默认为保证金退款申请的时候的操作员所属营业部
					refundInfo.setBussinessPlaceId(bussinessPlaceId);
					refundInfo.setCardNo(bail.getCardno());
					
					refundInfoDao.save(refundInfo);
					
					result = true;
					//更新审批状态（"3"）
					if(bail.getBailFrozenBalance()!=null)bail.setBailFrozenBalance(bail.getBailFrozenBalance().add(bail.getBailFee()));
					bail.setAppState(newAppState);
					bailDao.update(bail);
					
					//扣除子账户保证金冻结金额
					if(subAccountInfoDao.updateFrozenBalance(bail.getAccountId(), bail.getBailFee().abs()) == 0){
						logger.error("数据异常：子账户保证金冻结金额不足");
						throw new ApplicationException("数据异常：子账户保证金冻结金额不足");
					}
					
				}else{
					result = false;
				}
			}else if("2".equals(newAppState)){
				//保证金退还审批失败后，根据保证金退回申请记录对应退还金额去扣除保证金账户中的冻结金额，并将扣除的冻结金额增加到保证金账户的保证金金额中
				//保证金历史信息表部分处理
				BailAccountInfoHis bailAccountInfoHis = new BailAccountInfoHis();
				BigDecimal SEQ_CSMSBailAccountInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSBailAccountInfoHis_NO");
				bailAccountInfoHis.setId(Long.valueOf(SEQ_CSMSBailAccountInfoHis_NO.toString()));
				bailAccountInfoHis.setCreateReason("7");//保证金退还审批失败
				bailAccountInfoHisDao.saveHis(bailAccountInfoHis, bailAccountInfo);
				
				//冻结金额扣除到保证金金额
				bailAccountInfo.setHisSeqId(bailAccountInfoHis.getId());
				if(bailAccountInfoDao.updateBailToFrozen(bail.getBailFee(), bailAccountInfo) == 0){
					logger.error("数据异常：主账户保证金出现变动");
					throw new ApplicationException("数据异常：主账户保证金出现变动");
				}
				
				result = true;
				//更新审批状态（"2"）
				bail.setAppState(newAppState);
				if(bail.getBailAllFee()!=null)bail.setBailAllFee(bail.getBailAllFee().subtract(bail.getBailFee()));
				if(bail.getBailFrozenBalance()!=null)bail.setBailFrozenBalance(bail.getBailFrozenBalance().add(bail.getBailFee()));
				bailDao.update(bail);
				
				//子账户 冻结保证金     --> 保证金金额与保证金余额增加
				if(subAccountInfoDao.updateBail2Frozen(bail.getAccountId(), (bail.getBailFee().abs()).negate()) == 0){
					logger.error("数据异常：子账户保证金出现变动");
					throw new ApplicationException("数据异常：子账户保证金出现变动");
				}
				
				//2017-09-29 需求变动：保证金退还按子账户，所以设计也改了，不update单卡保证金了
				// 保存记帐卡历史表
				/*AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(bail.getCardno());
				AccountCInfoHis accountCInfoHis = new AccountCInfoHis();
				Long accountCInfoHisId = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
				accountCInfoHis.setId(accountCInfoHisId);
				accountCInfo.setHisSeqId(accountCInfoHisId);
				accountCInfoHis.setGenReason("23");//保证金退还营运审批失败
				accountCInfoHisDao.save(accountCInfo,accountCInfoHis);

				//更新记帐卡单卡保证金(加单卡保证金)
//				accountCInfo.setHisSeqId(accountCInfoHis.getId());
				accountCInfoDao.updateBailFee(bail.getBailFee().negate(),accountCInfo);*/
				
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，保证金退款申请审批失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，保证金退款申请审批失败");
		}
		return result;
	}
	/**
	 * 保证金退还申请审批列表
	 * userNo:客户号
	 * bankNo:银行账号
	 * startTime:开始时间
	 * endTime：结束时间
	 * state:审批状态
	 */
	public Pager bailBackAppList(Pager pager,String userNo,String organ,String IdCode,String bankNo,String startTime,String endTime,String state){
		return bailDao.findBailBackAppList(pager, userNo,organ,IdCode, bankNo, startTime, endTime,state);
	}
	
	public Map<String , Object> bailBackAppInfo(Long bailId){
		return bailDao.findBailByID(bailId);
	}
	
	/**
	 * 查找判断卡号是否已注销
	 * @param cardNo 储值卡号或记帐卡号
	 * @return
	 */
	public Map<String, Object> findSureIsCancle(String cardNo){
		Map<String, Object> resultMap;
		try {
			resultMap = new HashMap<String, Object>();
			String message = "";
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardNo);
			PrepaidC prepaidC = null;
			if(accountCInfo!=null){
				//传入的是记帐卡号
				if("2".equals(accountCInfo.getState())){
					//如果是"2"注销
					resultMap.put("isCancle", "1");
					resultMap.put("cardType", "23");//是记帐卡类型
					resultMap.put("result", "true");
					message = "记帐卡卡号已注销";
				}else{
					resultMap.put("isCancle", "0");//未注销
					resultMap.put("cardType", "23");//是记帐卡类型
					message = "卡号未注销";
				}
			}else{
				prepaidC = prepaidCDao.findByPrepaidCNo(cardNo);
				if(prepaidC!=null){
					if("2".equals(prepaidC.getState())){
						//如果是"2"注销
						resultMap.put("isCancle", "1");
						resultMap.put("cardType", "22");//是储值卡类型
						message = "储值卡卡号已注销";
					}else{
						resultMap.put("isCancle", "0");//未注销
						resultMap.put("cardType", "22");//是储值卡类型
						message = "卡号未注销";
					}
				}else{
					message = "卡号不存在";
				}
			}
			
			resultMap.put("message", message);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，查询判断卡号是否已注销失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，查询判断卡号是否已注销失败");
		}
		
		return resultMap;
	}

	/**
	 * 检验卡或电子标签与车辆是否对应
	 * @param param
	 * @return Map<String,String>
	 */
	@Override
	public Map<String, String> checkCardVehicle(String param) {
		Map<String, String> resultMap = new HashMap<String,String>();
		JSONObject paramJson = (JSONObject) JSONObject.fromObject(param).get("param");
		JSONArray array = (JSONArray) paramJson.get("resultList");
		List<Map> list = JSON.parseArray(array.toString(),Map.class);
		
		int i = 1;
		if (list != null && !list.isEmpty()) {
			StringBuffer errorMsg = new StringBuffer("");
			StringBuffer successMsg = new StringBuffer("");
			int errorCount = 0;
			int successCount = 0;
			//JSONObject json = new JSONObject();
			//StringBuffer billGetCardBankNo = new StringBuffer("");
			//获取sql拼接字符串
			for (Map<String, String> map : list) {
				String vehiclePlate = "";
				/*try {
					vehiclePlate = URLDecoder.decode(map.get("vehiclePlate"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				String vehicleColor = map.get("vehicleColor");
				String cardNo = map.get("cardNo");
				String cardType = map.get("cardType");
				
				if(!StringUtil.isNotBlank(vehiclePlate)
						||!StringUtil.isNotBlank(vehicleColor)
						||!StringUtil.isNotBlank(cardNo)
						||!StringUtil.isNotBlank(cardType)){
					errorMsg.append(";"+i+":传入的数据不全");
					errorCount++;
				}else{
					VehicleInfo vehicleInfo = vehicleInfoDao.loadByPlateAndColor(vehiclePlate, vehicleColor);
					
					//车牌颜色转为中文
					String colorCh = "";
					if(vehicleColor.equals(VehicleColorEnum.blue.getValue())){
						colorCh = VehicleColorEnum.blue.getName();
					}else if(vehicleColor.equals(VehicleColorEnum.yellow.getValue())){
						colorCh = VehicleColorEnum.yellow.getName();
					}else if(vehicleColor.equals(VehicleColorEnum.blace.getValue())){
						colorCh = VehicleColorEnum.blace.getName();
					}else if(vehicleColor.equals(VehicleColorEnum.white.getValue())){
						colorCh = VehicleColorEnum.white.getName();
					}else if(vehicleColor.equals(VehicleColorEnum.bluewhite.getValue())){
						colorCh = VehicleColorEnum.bluewhite.getName();
					}
					
					if(vehicleInfo == null){
						//首先判断车辆是否存在
						errorMsg.append(";"+i+":车牌号码为"+vehiclePlate+",车牌颜色为"+colorCh+"的车辆不存在");
						errorCount++;
					}else{
						//再判断卡或标签是否存在(1.储值卡、2.记帐卡、3.电子标签)
						if("1".equals(cardType)){
							PrepaidC prepaidC = prepaidCDao.findByPrepaidCNo(cardNo);
							if(prepaidC == null){
								errorMsg.append(";"+i+":储值卡"+cardNo+"未发行");
								errorCount++;
							}else{
								CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByPrepaidCID(prepaidC.getId());
								if(carObuCardInfo == null){
									errorMsg.append(";"+i+":储值卡"+cardNo+"没有绑定车辆");
									errorCount++;
								}else{
									JSONObject successJson = new JSONObject();
									successJson.accumulate("cardNo", cardNo);
									successJson.accumulate("cardType", cardType);
									successJson.accumulate("vehiclePlate", vehiclePlate);
									successJson.accumulate("vehicleColor", vehicleColor);
									if((long)vehicleInfo.getId() != (long)carObuCardInfo.getVehicleID()){
										successJson.accumulate("isCorrect", "0");
									}else{
										successJson.accumulate("isCorrect", "1");
									}
									successMsg.append(","+successJson.toString());
									successCount++;
									
									successJson = null;
								}
							}
						}else if("2".equals(cardType)){
							AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(cardNo);
							if(accountCInfo == null){
								errorMsg.append(";"+i+":记帐卡"+cardNo+"未发行");
								errorCount++;
							}else{
								CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByAccountCID(accountCInfo.getId());
								if(carObuCardInfo == null){
									errorMsg.append(";"+i+":记帐卡"+cardNo+"没有绑定车辆");
									errorCount++;
								}else{
									JSONObject successJson = new JSONObject();
									successJson.accumulate("cardNo", cardNo);
									successJson.accumulate("cardType", cardType);
									successJson.accumulate("vehiclePlate", vehiclePlate);
									successJson.accumulate("vehicleColor", vehicleColor);
									if((long)vehicleInfo.getId() != (long)carObuCardInfo.getVehicleID()){
										successJson.accumulate("isCorrect", "0");
									}else{
										successJson.accumulate("isCorrect", "1");
									}
									successMsg.append(","+successJson.toString());
									successCount++;
									
									successJson = null;
								}
							}
						}else if("3".equals(cardType)){
							TagInfo tagInfo = tagInfoDao.findByTagNo(cardNo);
							if(tagInfo == null){
								errorMsg.append(";"+i+":电子标签"+cardNo+"未发行");
								errorCount++;
							}else{
								CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByTagid(tagInfo.getId());
								if(carObuCardInfo == null){
									errorMsg.append(";"+i+":电子标签"+cardNo+"没有绑定车辆");
									errorCount++;
								}else{
									JSONObject successJson = new JSONObject();
									successJson.accumulate("cardNo", cardNo);
									successJson.accumulate("cardType", cardType);
									successJson.accumulate("vehiclePlate", vehiclePlate);
									successJson.accumulate("vehicleColor", vehicleColor);
									if((long)vehicleInfo.getId() != (long)carObuCardInfo.getVehicleID()){
										successJson.accumulate("isCorrect", "0");
									}else{
										successJson.accumulate("isCorrect", "1");
									}
									successMsg.append(","+successJson.toString());
									successCount++;
									
									successJson = null;
								}
							}
						}
					}
				}
				
				i++;
			}
			//json.accumulate("errorMsg", errorMsg.toString());
			//json.accumulate("errorCount", errorCount);
			//json.accumulate("allCount", list.size());
			resultMap.put("errorMsg", errorMsg.toString());
			resultMap.put("successMsg", successMsg.toString());
			resultMap.put("errorCount", errorCount+"");
			resultMap.put("successCount", successCount+"");
			resultMap.put("allCount", list.size()+"");
			resultMap.put("result", "true");
			return resultMap;
			
		}
		resultMap.put("result", "false");
		resultMap.put("message", "没有要查询的数据");
		return resultMap;
	}

	public List<Map<String, Object>> findAccountCinfoMap(Long migrateid){
		return accountCDao.findAccountCinfoMap(migrateid);
	}

}


