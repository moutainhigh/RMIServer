package com.hgsoft.timerTask.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.account.serviceInterface.IAccountFundChangeService;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.dao.MigrateDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.clearInterface.dao.AccCardTransferResultRecvDao;
import com.hgsoft.clearInterface.dao.CardBalanceDataRecvDao;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.common.Enum.MacaoBlackTypeEnum;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.macao.dao.AcIssuedStopInfoDao;
import com.hgsoft.macao.dao.AcStopInfoDao;
import com.hgsoft.macao.dao.MacaoCardBlackListDao;
import com.hgsoft.macao.dao.StopCardBlackListDao;
import com.hgsoft.macao.entity.AcIssuedStopInfo;
import com.hgsoft.macao.entity.AcStopInfo;
import com.hgsoft.macao.entity.StopCardBlackList;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.serviceInterface.ITagInfoService;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.dao.InvoiceChangeFlowDao;
import com.hgsoft.prepaidC.entity.InvoiceChangeFlow;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.prepaidC.serviceInterface.IElectronicPurseService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.prepaidC.serviceInterface.IReturnFeeService;
import com.hgsoft.timerTask.dao.InvoiceChangeFlowTaskDao;
import com.hgsoft.timerTask.dao.ReturnFeeTaskDao;
import com.hgsoft.timerTask.dao.TagTaskDao;
import com.hgsoft.timerTask.serviceinterface.ITimedTaskService;
import com.hgsoft.timerTask.vo.MQTimerVo;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.SequenceUtil;
@Service
public class TimedTaskService implements ITimedTaskService {
	@Resource
	private ReturnFeeTaskDao returnFeeTaskDao;
	@Resource
	private TagTaskDao tagTaskDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private ITagInfoService tagInfoService;
	@Resource
	private IPrepaidCService prepaidCService;
	@Resource
	private IReturnFeeService returnFeeService;
	@Resource
	private InvoiceChangeFlowDao invoiceChangeFlowDao;
	@Resource
	private InvoiceChangeFlowTaskDao invoiceChangeFlowTaskDao;
	@Resource
	private MacaoCardBlackListDao macaoCardBlackListDao;
	@Resource
	private StopCardBlackListDao stopCardBlackListDao;
	@Resource
	private AcStopInfoDao acStopInfoDao;
	@Resource
	private AcIssuedStopInfoDao acIssuedStopInfoDao;
	@Resource
	private MigrateDao migrateDao;
	@Resource
	private AccCardTransferResultRecvDao accCardTransferResultRecvDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private ServiceFlowRecordDao serviceFlowRecordDao;
	/*@Resource
	private ACinfoDao aCinfoDao;*/
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private IElectronicPurseService electronicPurseService;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	@Resource
	private CardBalanceDataRecvDao cardBalanceDataRecvDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private IAccountFundChangeService accountFundChangeService;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;

	//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171024
	@Resource
	private NoRealTransferService noRealTransferService;
	//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171024
	
	private static String month;
	private static Date realDate;
	
	
	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());

	@Override
	public void helloWorld() {
		logger.info("===============helloworld==================");
	}
	/**
	 * 储值卡终止使用
	 * @author lgm
	 * @date 2017年5月12日
	 */
	public void prepaidCStopCard(){
//		try {
//			//获取资金争议期
//			Map<String,Object> omsParamInterfaceMap = omsParamInterfaceService.findOmsParam("Capital dispute period");
//			//营运系统访问成功
//			if("0".equals(omsParamInterfaceMap.get("flag"))){
//				//获取资金争议期
//				Integer expireTime = Integer.parseInt((String)omsParamInterfaceMap.get("value"));
//				//当前时间减去资金争议期
//				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
//				Calendar c=Calendar.getInstance();   
//				c.setTime(new Date());   
//				c.add(Calendar.DATE,-expireTime); 
//				System.out.println(format.format(c.getTime()));
//				//处于资金争议期的储值卡
//				//清算数据
//				List<Map<String,Object>> cardBalanceDataRecvList = cardBalanceDataRecvDao.findAll();
//				
//				String cardCode;//清算数据表卡号
//				BigDecimal cardBalance;//清算数据表储值卡余额
//				Date cancelTime;//储值卡终止使用时间
//				
//				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//				for(Map<String,Object> cardBalanceDataRecvMap : cardBalanceDataRecvList){
//					cardCode = (String)cardBalanceDataRecvMap.get("CARDCODE");
//					cardBalance = (BigDecimal)cardBalanceDataRecvMap.get("CARDBALANCE");
//					cardBalance = cardBalance.multiply(new BigDecimal("100"));
//					List<Map<String,Object>> refundInfoList = refundInfoDao.findByAuditStatus(RefundAuditStatusEnum.inExpire.getValue(),cardCode);
//					for(Map<String,Object> refundInfoMap : refundInfoList){
//						cancelTime = format.parse(refundInfoMap.get("CANCELTIME").toString());
//						//储值卡终止时间加上资金争议期大于当前时间，则更新储值卡终止使用相关信息
//						if(c.getTime().getTime()>=cancelTime.getTime()){
//							Map<String,Object>  map = new HashMap<String,Object>();
//							map.put("CARDNO", cardCode);
//							map.put("BALANCE", cardBalance);
//							map.put("MAINACCOUNTID", ((BigDecimal)refundInfoMap.get("MAINACCOUNTID")).longValue());
//							list.add(map);
//						}
//					}
//				}
//				System.out.println(list.size());
//				if(list.size()>0){
//					refundInfoDao.batchUpdateStatusAndCurReBa(list);
//					accountFundChangeService.saveAccountFundChangeList(list);
//					mainAccountInfoDao.batchUpdateStatusAndCurReBa(list);
//					dbasCardFlowDao.batchUpdateFlagAndCardAmt(list);
//				}
//			}
			
//		} catch (ParseException e) {
//			logger.error("储值卡终止使用资金争议期及卡片余额获取失败");
//			e.printStackTrace();
//			throw new ApplicationException("");
//		}
	}
	
	/**
	 * 记帐卡迁移
	 * @return void
	 * @author lgm
	 * @date 2017年4月28日
	 */
	public void updateAccountCMigrate(){
		//查询客服数据库迁移审核通过的记录
		List<Map<String,Object>> migrateList = migrateDao.findByAppState("2");//2：审核通过
		//查询记账卡过户结果通知表过户成功的记录
		List<Map<String,Object>> accountCTransferResultList = accCardTransferResultRecvDao.findByResult(new Long(0));
		
		//记帐卡卡信息
		List<AccountCInfo> accountCInfoList = new ArrayList<AccountCInfo>();
		List<AccountCApply> accountCApplyList = new ArrayList<AccountCApply>();
		AccountCInfo accountCInfo = null;
		//创建记帐卡信息历史集合
		List<AccountCInfoHis> hisList = new ArrayList<AccountCInfoHis>();
		AccountCInfoHis accountCInfoHis = null;
		//创建客服流水
		List<ServiceFlowRecord> serviceFlowRecordList = new ArrayList<ServiceFlowRecord>();
		ServiceFlowRecord serviceFlowRecord = null;
		Long id = null;
		
		for(Map<String,Object> accountCTransferResultMap : accountCTransferResultList){
			String cardNo = (String)accountCTransferResultMap.get("CARDCODE");
			Date accountCTransferResultMapTime = (Date)accountCTransferResultMap.get("REQTIME");

			for(Map<String,Object> migrateMap : migrateList){
				Date migrateMapTime = (Date) migrateMap.get("REQTIME");
				//如果卡号和申请时间相同，则进行迁移
				if(cardNo.equals(migrateMap.get("CARDNO")) && accountCTransferResultMapTime.getTime()==migrateMapTime.getTime()){
					//创建记帐卡历史对象
					id = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
					accountCInfoHis = new AccountCInfoHis();
					accountCInfoHis.setId(id);
					accountCInfoHis.setGenReason("8");
					accountCInfoHis.setCardNo(migrateMap.get("CARDNO").toString());
					hisList.add(accountCInfoHis);
					
					//创建记帐卡信息对象
					accountCInfo = new AccountCInfo();
					accountCInfo.setCardNo(migrateMap.get("CARDNO").toString());
					accountCInfo.setCustomerId(Long.parseLong(migrateMap.get("CUSTOMERID").toString()));
					accountCInfo.setAccountId(Long.parseLong(migrateMap.get("NEWACCOUNTID").toString()));
					accountCInfo.setHisSeqId(id);
					accountCInfo.setState(migrateMap.get("STATE").toString());
					accountCInfoList.add(accountCInfo);
					
					//客服流水:未处理完成（新旧账户id）
					id = sequenceUtil.getSequenceLong("SEQ_csms_serviceflow_record_NO");
					serviceFlowRecord = new ServiceFlowRecord();
					serviceFlowRecord.setId(id);
					serviceFlowRecord.setClientID(accountCInfo.getCustomerId());
					serviceFlowRecord.setCardTagNO(accountCInfo.getCardNo());
					serviceFlowRecord.setServiceFlowNO(serviceFlowRecord.getId()+"");
					serviceFlowRecord.setServicePTypeCode(2);
					serviceFlowRecord.setServiceTypeCode(15);
					//---------------------------------------------------------------
					serviceFlowRecord.setOperID(((BigDecimal)migrateMap.get("APPROVER")).longValue());
					serviceFlowRecord.setOperName((String)migrateMap.get("APPROVERNAME"));
					serviceFlowRecord.setOperNo((String)migrateMap.get("APPROVERNO"));
					/*serviceFlowRecord.setPlaceID(Long.parseLong((String)migrateMap.get("PLACEID")));
					serviceFlowRecord.setPlaceName((String)migrateMap.get("PLACENAME"));
					serviceFlowRecord.setPlaceNo((String)migrateMap.get("PLACENO"));*/
					serviceFlowRecordList.add(serviceFlowRecord);
					
					//------------------------------------------------------------
					SubAccountInfo subAccountInfo = subAccountInfoDao.findById(accountCInfo.getAccountId());
					AccountCApply accountCApply = accountCApplyDao.findById(subAccountInfo.getApplyID());
					accountCApplyList.add(accountCApply);
					
				}
			}
		}
		
		if(hisList.size()>0){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int num=0;num<accountCInfoList.size();num++){
				AccountCInfo accountcInfo = accountCInfoList.get(num);
				String cardNo = accountcInfo.getCardNo();
				Map<String,Object> map = migrateDao.findIdByCardNo(cardNo.toString(),"2");
				if(map!=null)
					list.add(map);
			}
			migrateDao.batchUpdateAppState(list);
			accountCInfoHisDao.batchSaveForbranches(hisList);
			accountCDao.batchUpdate(accountCInfoList);
			//accountCBussinessDao.batchSave(bussList);
			serviceFlowRecordDao.batchSave(serviceFlowRecordList);
			//--------------------------------------------
			/*aCinfoDao.batchSaveACinfo(accountCApplyList);*/
		}
	}
	
	/**
	 * 把TB_MacaoCardBlackList新添加的数据添加到csms_dark_list
	 * */
	public void addDarkListFromEtcTolling(){
		//这样处理可能会有问题，先注释掉
		/*DarkList darkList;
		try {
			darkList = darkListDao.getLast();
			String insertTime = null;
			if(darkList!=null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				insertTime = format.format(darkList.getInsertTime());
			}
			List<Map<String,Object>> list = macaoCardBlackListDao.getNewBlackList(insertTime);
			
			if(!list.isEmpty()){
				List<DarkList> darkListList = new ArrayList<DarkList>();
				Long customerId = darkListDao.getMacaoCustomer().getId();
				for(Map<String,Object> map : list){
					DarkList temp = new DarkList();
					temp.setCustomerId(customerId);
					temp.setCardNo((String)map.get("CARDCODE"));
					temp.setCardType("1");
					temp.setGenDate(((Timestamp)map.get("GENTIME")));
					temp.setGencau((String)map.get("GENCAU"));
					temp.setUpdateTime(new Date());
					temp.setInsertTime((Timestamp)map.get("INSERTTIME"));
					darkListList.add(temp);
				}
				darkListDao.batchSaveDarkList(darkListList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//（1）查找最近一条CSMS_STOPCARDBLACKLIST(新建的止付名单表)数据，根据该条记录的时间为开始时间查询清分的TB_MACAOCARDBLACKLIST批量数据，批量插入CSMS_STOPCARDBLACKLIST。
		//（2）根据TB_MACAOCARDBLACKLIST的数据类型（止付或解除止付）同时批量插入表csms_acstop_info（记帐卡止付名单数据表）、CSMS_ACISSUEDSTOP_INFO（下发止付名单清算表）。
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StopCardBlackList lastBlack = stopCardBlackListDao.getLast();
		if(lastBlack!=null&&lastBlack.getInsertTime()!=null){
			String insertTime = format.format(lastBlack.getInsertTime());
			List<Map<String,Object>> list = macaoCardBlackListDao.getNewBlackList(insertTime);
			
			if(list!=null&&!list.isEmpty()){
				List<StopCardBlackList> stopCardBlackList = new ArrayList<StopCardBlackList>();
				//解除止付黑名单列表
				List<AcStopInfo> acStopInfoList = new ArrayList<AcStopInfo>();
				//下发止付黑名单列表
				List<AcIssuedStopInfo> acIssuedStopInfoList = new ArrayList<AcIssuedStopInfo>();
				for(Map<String,Object> map : list){
					StopCardBlackList temp = new StopCardBlackList();
					temp.setRecordId((BigDecimal)map.get("BOARDLISTNO"));
					temp.setCardCode((String)map.get("CARDCODE"));
					temp.setGenTime((Date)map.get("GENTIME"));
					temp.setGenCau((String)map.get("GENCAU"));
					temp.setInsertTime((Date)map.get("INSERTTime"));
					stopCardBlackList.add(temp);
					
					//记帐卡解除止付名单清算数据表csms_acstop_info
					AcStopInfo acStopInfo = null;
					MQTimerVo mqTimerVo = new MQTimerVo();
					if(MacaoBlackTypeEnum.relieveStop.getValue().equals((String)map.get("GENCAU"))){
						acStopInfo = new AcStopInfo();
						acStopInfo.setInterCode("91005");//MQ接口编码
						if(map.get("INSERTTime")!=null)acStopInfo.setCreateTime(format2.format((Date)map.get("INSERTTime")));
						else acStopInfo.setCreateTime(format2.format(new Date()));
						acStopInfo.setCode((String)map.get("CARDCODE"));
						acStopInfo.setBankNo(mqTimerVo.getBankNo());
						acStopInfo.setPlaceNo(mqTimerVo.getPlaceNo());
						acStopInfo.setOperNo(mqTimerVo.getOperNo());
						
						acStopInfoList.add(acStopInfo);
					}
					//记帐卡下发止付名单清算数据表CSMS_ACISSUEDSTOP_INFO
					AcIssuedStopInfo issuedStopInfo  = null;
					if(MacaoBlackTypeEnum.issueStop.getValue().equals((String)map.get("GENCAU"))){
						issuedStopInfo = new AcIssuedStopInfo();
						issuedStopInfo.setInterCode("91004");//MQ接口编码
						if(map.get("INSERTTime")!=null)issuedStopInfo.setCreateTime(format2.format((Date)map.get("INSERTTime")));
						else issuedStopInfo.setCreateTime(format2.format(new Date()));
						issuedStopInfo.setCode((String)map.get("CARDCODE"));
						
						acIssuedStopInfoList.add(issuedStopInfo);
					}
					
				}
				try {
					stopCardBlackListDao.batchSaveStopCardBlackList(stopCardBlackList);
					acStopInfoDao.batchSaveAcStopInfo(acStopInfoList);
					acIssuedStopInfoDao.batchSaveAcIssuedStopInfo(acIssuedStopInfoList);
				} catch (ApplicationException e) {
					logger.error("批量同步澳门通止付黑名单数据失败");
					e.printStackTrace();
					throw new ApplicationException("");
				}

				//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171024
				// 解除止付黑名单
				if (!acStopInfoList.isEmpty()) {
					for (AcStopInfo acStopInfo : acStopInfoList) {
						noRealTransferService.blackListTransfer(acStopInfo.getCode(), new Date(),
								CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu
										.EX_BLACK.getCode());
					}
				}
				// 下发止付黑名单
				if (!acIssuedStopInfoList.isEmpty()) {
					for (AcIssuedStopInfo acIssuedStopInfo : acIssuedStopInfoList) {
						noRealTransferService.blackListTransfer(acIssuedStopInfo.getCode(), new Date(),
								CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu
										.EN_BLACK.getCode());
					}
				}
				//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171024
				
			}
		}else{
			//如果CSMS_STOPCARDBLACKLIST没有数据，说明还没有开始搬数据。就把TB_MacaoCardBlackList所有数据从搬过去CSMS_STOPCARDBLACKLIST。
			List<Map<String,Object>> list = macaoCardBlackListDao.getNewBlackList(null);
			
			if(!list.isEmpty()){
				List<StopCardBlackList> stopCardBlackList = new ArrayList<StopCardBlackList>();
				//解除止付黑名单列表
				List<AcStopInfo> acStopInfoList = new ArrayList<AcStopInfo>();
				//下发止付黑名单列表
				List<AcIssuedStopInfo> acIssuedStopInfoList = new ArrayList<AcIssuedStopInfo>();
				for(Map<String,Object> map : list){
					StopCardBlackList temp = new StopCardBlackList();
					temp.setRecordId((BigDecimal)map.get("BOARDLISTNO"));
					temp.setCardCode((String)map.get("CARDCODE"));
					temp.setGenTime((Date)map.get("GENTIME"));
					temp.setGenCau((String)map.get("GENCAU"));
					temp.setInsertTime((Date)map.get("INSERTTime"));
					stopCardBlackList.add(temp);
					
					//记帐卡解除止付名单清算数据表csms_acstop_info
					AcStopInfo acStopInfo = null;
					MQTimerVo mqTimerVo = new MQTimerVo();
					if(MacaoBlackTypeEnum.relieveStop.getValue().equals((String)map.get("GENCAU"))){
						acStopInfo = new AcStopInfo();
						acStopInfo.setInterCode("91005");//MQ接口编码
						if(map.get("INSERTTime")!=null)acStopInfo.setCreateTime(format2.format((Date)map.get("INSERTTime")));
						else acStopInfo.setCreateTime(format2.format(new Date()));
						acStopInfo.setCode((String)map.get("CARDCODE"));
						acStopInfo.setBankNo(mqTimerVo.getBankNo());
						acStopInfo.setPlaceNo(mqTimerVo.getPlaceNo());
						acStopInfo.setOperNo(mqTimerVo.getOperNo());
						
						acStopInfoList.add(acStopInfo);
					}
					//记帐卡下发止付名单清算数据表CSMS_ACISSUEDSTOP_INFO
					AcIssuedStopInfo issuedStopInfo  = null;
					if(MacaoBlackTypeEnum.issueStop.getValue().equals((String)map.get("GENCAU"))){
						issuedStopInfo = new AcIssuedStopInfo();
						issuedStopInfo.setInterCode("91004");//MQ接口编码
						if(map.get("INSERTTime")!=null)issuedStopInfo.setCreateTime(format2.format((Date)map.get("INSERTTime")));
						else issuedStopInfo.setCreateTime(format2.format(new Date()));
						issuedStopInfo.setCode((String)map.get("CARDCODE"));
						
						acIssuedStopInfoList.add(issuedStopInfo);
					}
					
				}
				try {
					stopCardBlackListDao.batchSaveStopCardBlackList(stopCardBlackList);
					acStopInfoDao.batchSaveAcStopInfo(acStopInfoList);
					acIssuedStopInfoDao.batchSaveAcIssuedStopInfo(acIssuedStopInfoList);
				} catch (ApplicationException e) {
					logger.error("批量同步澳门通止付黑名单数据失败");
					e.printStackTrace();
					throw new ApplicationException("");
				}

				//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171024
				// 解除止付黑名单
				if (!acStopInfoList.isEmpty()) {
					for (AcStopInfo acStopInfo : acStopInfoList) {
						noRealTransferService.blackListTransfer(acStopInfo.getCode(), new Date(),
								CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu
										.EX_BLACK.getCode());
					}
				}
				// 下发止付黑名单
				if (!acIssuedStopInfoList.isEmpty()) {
					for (AcIssuedStopInfo acIssuedStopInfo : acIssuedStopInfoList) {
						noRealTransferService.blackListTransfer(acIssuedStopInfo.getCode(), new Date(),
								CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu
										.EN_BLACK.getCode());
					}
				}
				//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171024j
				
			}
		}
		
	}
	
	/**
	 * @Description:把清算的回退金额表里面的数据搬到客服系统表
	 * @return void
	 */
	public void saveReturnFeeFromClear(){
		try {
			returnFeeTaskDao.saveReturnFee();
			returnFeeTaskDao.deleteReturnFeeFromClear();
		} catch (ApplicationException e) {
			logger.error("");
			e.printStackTrace();
			throw new ApplicationException("");
		}
	}	
	
	/**
	 * @Description:查询电子标签发行表   然后看当前电子标签的回写标志是否已回写
	 *				若未回写的   查看发行时间  判断是否过了24小时，如果过了就下黑名单
	 * @return void
	 */
	public void updateTagInfoBlackFlag(){
		try {
			List<TagInfo> tagInfoList = tagTaskDao.findTagInfoByWritebackFlag();
			if(tagInfoList!=null && tagInfoList.size()>0){
				for (TagInfo tagInfo : tagInfoList) {
					Date issuetime = tagInfo.getIssuetime();
					long time = System.currentTimeMillis() - issuetime.getTime();
					double result = time * 1.0 / (1000 * 60 * 60);
					if(result>24){ 
						//下发黑名单
						tagInfo.setBlackFlag("1");
						tagInfoDao.update(tagInfo);
						//加入黑名单表
						//DarkList darkList = darkListDao.findByCardNo(tagInfo.getTagNo());
						//tagInfoService.saveDarkList(tagInfo,darkList,"6", "1");//6—电子标签黑名单进入    1:待下发
						blackListService.saveOBUStopUse(tagInfo.getObuSerial(), new Date(), "2", null, null, null, null, null, null, new Date());
					} 
				}
			}
		} catch (ApplicationException e) {
			logger.error("");
			e.printStackTrace();
			throw new ApplicationException("");
		}
	}
	
	public void saveBlackListAll(){
		blackListService.saveBlackListAllSend();
	}
	
	/**
	 * @Description:发票类型变更
	 *				
	 * @return void
	 */
	public void updateInvoiceChangeFlow(){
		try {
			List<Map<String, Object>> cardNos = invoiceChangeFlowTaskDao.findCardNos();
			for (Map<String, Object> cardNoMap : cardNos) {
				
				InvoiceChangeFlow invoiceChangeFlow = invoiceChangeFlowDao.findByCardNo((String)cardNoMap.get("cardNo"));
				if(invoiceChangeFlow!=null){
					//查询储值卡优惠金额和系统余额
					List<ReturnFee> newCardReturnFeeList = returnFeeService.findByCardNoState((String)cardNoMap.get("cardNo"), "1");
					//优惠金额
					BigDecimal realCost = new BigDecimal("0");
					for (ReturnFee returnFee : newCardReturnFeeList) {
						if(returnFee.getFeeType().equals("1")){//1为回退金额2为转移金额
							realCost.add(returnFee.getReturnFee());
						}
					}
					invoiceChangeFlow.setRealcost(realCost); //设置优惠金额
					// TODO: 2017/3/29 设置系统金额     没找到
//					invoiceChangeFlow.setSyscost(0);
					invoiceChangeFlow.setMonth(month);
					invoiceChangeFlow.setRealDate(realDate);
					//获得旧卡号
					List<Map<String, Object>> cards = prepaidCService.findCards((String)cardNoMap.get("cardNo"));
					if(cards != null){
						for (Map<String, Object> map : cards) {
							String oldCardNo = (String)map.get("oldCardNo");
							//资金转移优惠金额
							BigDecimal tranferrealcost = new BigDecimal("0");
							List<ReturnFee> returnFeeList = returnFeeService.findByCardNoState(oldCardNo, "1");//1：未使用 2：充值锁定 3：充值成功 4：冲正锁定
							for (ReturnFee returnFee : returnFeeList) {
								if(returnFee.getFeeType().equals("1")){//1为回退金额2为转移金额
									tranferrealcost.add(returnFee.getReturnFee());
									
									invoiceChangeFlow.setTranferrealcost(tranferrealcost);
									
								}
							}
							invoiceChangeFlowDao.updateByCardNo(invoiceChangeFlow);
						}
					}
				}
			
			}
			
		} catch (ApplicationException e) {
			logger.error("");
			e.printStackTrace();
			throw new ApplicationException("");
		}
	}

	
	public void setTime(String month1,Date realDate1) {
		realDate = realDate1;
		month = month1;
	}

	
	public void saveDealBlackListWater() throws Exception {
		blackListService.dealBlackListWarter();
	}
	
	public void saveBlackListSend() throws IOException, ParseException{
		blackListService.saveBlackListSend();
	}
	
	public void savePrepaidCBalance(){
		electronicPurseService.saveTransferMoney();
	}

	public static void main(String[] args) {

		//加载spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		TimedTaskService timedTaskService = (TimedTaskService)applicationContext.getBean("timedTaskService");
		timedTaskService.updateAccountCMigrate();

		}
	
	
	@Override
	public void saveStopCard() {
		blackListService.autoImportAccountCStopPay();
	}
	
}
