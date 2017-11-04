package com.hgsoft.clearInterface.service;

import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.dao.ProviceSendBoardDao;
import com.hgsoft.clearInterface.entity.BlackListRelieveStatus;
import com.hgsoft.clearInterface.entity.BlackListRelieveTemp;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.clearInterface.entity.BlackListVersion;
import com.hgsoft.clearInterface.entity.BlackListWarter;
import com.hgsoft.clearInterface.entity.ClearBlackList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.common.dao.EtcTollingBaseDao;
import com.hgsoft.httpInterface.dao.OmsParamDao;
import com.hgsoft.httpInterface.entity.ServiceParamSetNew;
import com.hgsoft.system.dao.ParamConfigDao;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlackListService extends EtcTollingBaseDao
		implements IBlackListService {

	private static Logger logger = Logger.getLogger(BlackListService.class.getName());

	@Resource
	private BlackListDao blackListDao;
	@Resource
	private ParamConfigDao paramConfigDao;
	@Resource
	private OmsParamDao omsParamDao;
	@Autowired
	private ApplicationContext ctx;
	@Resource
	private ProviceSendBoardDao proviceSendBoardDao;

	//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171024
	@Resource
	private NoRealTransferService noRealTransferService;
	//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171024

	private static BigDecimal BATCHCOUNT = new BigDecimal(500);

	@Override
	public void autoImportAccountCStopPay() {
		//List<ProviceRecvBoard> recvList = blackListDao.findRecvByTableCode("8112");
		//获取止付卡全量历史表里面当前的公告号
		String currentBoardNo = blackListDao.findCurrentStopPayBoardNo();
		if (currentBoardNo == null) {
			return;
		}

		//获取已处理的支付卡全量表里比当前公告号大的最小的那个公告序号
		String nextBoardNo = blackListDao.findNextStopPayBoardNo(currentBoardNo);
		if (StringUtil.isEmpty(nextBoardNo)) {
			return;
		}
		//对比两批公告的数据，导入新的止付卡信息到黑名单流水表
		blackListDao.importStopPayCard(Constant.NETNO, Constant.ACCOUNTCTYPE, nextBoardNo);
//		//删除历史表信息
		blackListDao.deleteStopPayCardHis();
//		//把最新版的支付卡信息更新到历史表
		blackListDao.importStopPayCardHis(nextBoardNo);
		//删除已处理的止付卡中公告号比当前处理的公告号小的记录
		blackListDao.deletePerStopPayCard(nextBoardNo);

//		if(recvList!=null){
//			for(ProviceRecvBoard proviceRecvBoard:recvList){
//				
//				
//				//导入新的支付卡信息
//				blackListDao.importStopPayCard(Constant.NETNO,Constant.ACCOUNTCTYPE,proviceRecvBoard.getListNo().toString());
//				//删除历史表信息
//				blackListDao.deleteStopPayCardHis();
//				//把最新版的支付卡信息更新到历史表
//				blackListDao.importStopPayCardHis(proviceRecvBoard.getListNo().toString());
//			}
//		}

	}

	@Override
	public void ImportAccountCStopPay(String netNo, String cardType, String cardNo, Date genTime, String genType, Integer status, String stopPayStatus, String flag) {
		BlackListWarter blw = new BlackListWarter(netNo, cardType, cardNo, genTime, genType, status, stopPayStatus, flag);
		try {
			blackListDao.saveBlackListWarter(blw);
		} catch (Exception e) {

		}
	}

	@Override
	public Boolean saveBlackListWarter(String obuId, String license, String cardType, String cardNo, Date genTime, String genType, Integer status,
	                                   String flag, Long operId, String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime, String stopPayStatus) {
		BlackListWarter blw = new BlackListWarter(obuId, license, cardType, cardNo, genTime, genType, status, flag,
				operId, operNo, operName, placeId, placeNo, placeName, operTime, stopPayStatus);
		try {
			if (blw.getNetNo() == null) {
				blw.setNetNo(Constant.NETNO);
			}
			blackListDao.saveBlackListWarter(blw);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean saveBlackListWarter(BlackListWarter blackListWarter) {
		try {
			blackListDao.saveBlackListWarter(blackListWarter);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* 
	 * 1、当出现黑名单流水数据量太大的时候，需要把事务分开来，对数据实现分批提交
	 * 2、黑名单解除状态表CSMS_BLACK_LIST_STATUS如果在同一个增量版本内，同时出现黑名单和解除黑名单，则
	 * 不会生成黑名单解除状态表。
	 * 3、如果在同一个增量版本内，出现黑名单--解除黑名单--黑名单，如果黑名单类型级别比之前的小，则在同一个增量版本内，不需要发送
	 */
	@Override
	public void dealBlackListWarter() throws Exception {


//		String currentTime = DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss");
//		//获取黑名单流水表里所有未处理的黑名单流水信息
//		Long pageIndex = new Long(1);
//		Pager pager = new Pager();
//		pager.setBegin(0);
//		pager.setCurrentPage(String.valueOf(pageIndex));
//		pager.setPageSize(BATCHCOUNT.toString());
		//分批查找黑名单流水表比当前时间小，并且没处理的黑名单流水信息
//		List<BlackListWarter> blackListWarterList = blackListDao.findNoHandleBlackListWarter(pager,currentTime);
//		while(blackListWarterList!=null){
//			handBlackListWater(blackListWarterList);
//			pageIndex++;
//			pager.setBegin(0);
//			pager.setCurrentPage(String.valueOf(pageIndex));
//			pager.setPageSize(BATCHCOUNT.toString());
//			blackListWarterList = blackListDao.findNoHandleBlackListWarter(pager,currentTime);
//		}

		List<BlackListWarter> blackListWarterList = blackListDao.findNoHandleBlackListWarter(BATCHCOUNT.toString());

		handBlackListWater(blackListWarterList);
	}

	private void addBlackListVersion(BlackListVersion blackListVersion) throws IOException {
		ServiceParamSetNew spn = omsParamDao.findOmsParam("BLACKLISTPERIOD");
		if (spn == null) {
			return;
		}
		String blackListPeriod = spn.getValue();
		ServiceParamSetNew spnBlackListAll = omsParamDao.findOmsParam("BLACKLISTALL");
		String blackListAll = "";
		if (spnBlackListAll == null) {
			blackListAll = "09:00:00";
		} else {
			blackListAll = spnBlackListAll.getValue();
		}

		Date lastDate = blackListVersion.getStartTime();
		Long versionNo = blackListVersion.getVersionNo();
		Date thisDate = DateUtil.addHour(lastDate, Integer.parseInt(blackListPeriod));
		BlackListVersion newVersion = new BlackListVersion();
		newVersion.setVersionNo(versionNo + 1);
		newVersion.setStartTime(thisDate);
		newVersion.setStatus(0);
		//判断当前时点版本生成的是全量还是增量
		if (StringUtil.isEquals(DateUtil.formatDate(thisDate, "HH:mm:ss"), blackListAll)) {
			newVersion.setVersionType("0");
		} else {
			newVersion.setVersionType("1");
		}

		blackListDao.saveBlackListVersion(newVersion);
	}

	private void handBlackListWater(List<BlackListWarter> blackListWarterList) throws Exception {
		try {
			if (blackListWarterList != null) {
				for (BlackListWarter blackListWarter : blackListWarterList) {
					//1表，获取黑名单临时表的数据
					//List<BlackListTemp> blackListTempList = blackListDao.findBlackListTemp(blackListWarter);
					//状态大于0表示黑名单

					/**
					 * 2017/10/01修改判断条件，当出现卡号时，只按卡号作判断条件，优先级为：卡号--obu号---车牌号
					 */
					if (!StringUtil.isEmpty(blackListWarter.getCardNo())) {
						blackListWarter.setCardNo(blackListWarter.getCardNo().trim());
						blackListWarter.setObuId(null);
						blackListWarter.setLicense(null);
					} else if (!StringUtil.isEmpty(blackListWarter.getObuId())) {
						blackListWarter.setObuId(blackListWarter.getObuId().trim());
						blackListWarter.setCardNo(null);
						blackListWarter.setLicense(null);
					} else if (!StringUtil.isEmpty(blackListWarter.getLicense())) {
						blackListWarter.setLicense(blackListWarter.getLicense().trim());
						blackListWarter.setCardNo(null);
						blackListWarter.setObuId(null);
					} else {
						blackListDao.updateBlackListWarter(blackListWarter);
						continue;
					}


					if (blackListWarter.getStatus() > 0) {
						saveBlackList(blackListWarter);
					} else {
						saveRelieveBlackList(blackListWarter);
					}
					//更新黑名单流水表为已处理状态
					blackListDao.updateBlackListWarter(blackListWarter);
					logger.info("当前处理流水：卡号：" + blackListWarter.getCardNo() + ",车牌号：" + blackListWarter.getLicense() + ",OBU序号：" + blackListWarter.getObuId());

					//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171024
					Integer operatype = null;

					if ("4".equals(blackListWarter.getStatus().toString())) {
						// 进入止付黑名单
						operatype = OperationTypeEmeu.EN_BLACK.getCode();
					} else if ("-4".equals(blackListWarter.getStatus().toString())) {
						// 解除止付黑名单
						operatype = OperationTypeEmeu.EX_BLACK.getCode();
					}

					noRealTransferService.blackListTransfer(blackListWarter.getCardNo(), new Date(),
							CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), operatype);
					//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171024

				}
			}
		} catch (Exception e) {
			logger.error("流水处理失败，失败原因：" + e.getMessage());
		}
	}

	//处理黑名单的单条流水
	public void saveBlackList(BlackListWarter blackListWarter) {
		//判断黑名单中间表是否存在相同的状态，如果存在相同的状态，则不做任何处理
//		if(blackListTempList!=null){
//			for(BlackListTemp blackListTemp:blackListTempList){
//				//如果存在状态以及产生原因都相同的记录，则不做任何处理
//				if(blackListTemp.getStatus()== blackListWarter.getStatus()
//						&&StringUtil.isEquals(blackListTemp.getGenMode(), blackListWarter.getGenType())){
//					return true;
//				}
//			}
//		}
		BlackListTemp existTemp = blackListDao.findBlackListTemp(blackListWarter, true);
		if (existTemp != null) {
			return;
		}
		BlackListTemp blackListTemp = addBlackListTemp(blackListWarter.getNetNo(), blackListWarter.getObuId(), blackListWarter.getLicense()
				, blackListWarter.getCardType(), blackListWarter.getCardNo(), blackListWarter.getGenTime(),
				blackListWarter.getStatus(), blackListWarter.getGenType());
		//添加中间解除表
		addBlackListRelieveTemp(blackListWarter.getNetNo(), blackListWarter.getObuId(), blackListWarter.getLicense()
				, blackListWarter.getCardType(), blackListWarter.getCardNo(), blackListWarter.getGenTime(),
				blackListWarter.getStatus(), null, new Date(), blackListWarter.getGenType(), new Date(), null, 0, blackListTemp.getId());
	}


	public void saveBlackListAddition(BlackListWarter blackListWarter, Long versionNo) {
		//TODO
	}


	//保存解除黑名单
	public Boolean saveRelieveBlackList(BlackListWarter blackListWarter) {
		BlackListTemp blackListTemp = blackListDao.findBlackListTemp(blackListWarter, false);
		//如果本来黑名单临时表都没有数据，解除黑名单状态的流水不能处理
		if (blackListTemp == null) {
			return false;
		}
		blackListDao.deleteBlackListTemp(blackListTemp);
		//更新黑名单解除中间表，设置状态为解除状态
		blackListDao.updateBlackListRelieveTemp(blackListTemp, blackListWarter);
		return true;
	}


//	private boolean isBlackListSameArea(BlackListWarter blackListWarter, BlackListTemp blackListTemp) {
//		BlackListVersion blackListVersion = blackListDao.findBlackListVersion();
//		if(blackListVersion==null){
//			return false;
//		}
//		if(blackListWarter.getId()<blackListVersion.getCurrentRelieveListId()&&blackListTemp.getId()<blackListVersion.getCurrentRelieveListId()){
//			return true;
//		}
//		return false;
//	}

	public Boolean isBlackListTempExists(List<BlackListTemp> blackListTempList) {
		if (blackListTempList.size() > 1) {
			return true;
		}
		return false;
	}

	private Boolean isRelieveExists(List<BlackListTemp> blackListTempList, BlackListWarter blackListWarter) {
		if (blackListWarter.getStatus() > 0) {
			return true;
		}
		if (blackListTempList == null) {
			return false;
		}
		for (BlackListTemp blackListTemp : blackListTempList) {
			//黑名单状态与黑名单解除状态是一正一负的，在判断是否存在相应的黑名单状态时，只需要判断两个值相加是否为0即可
			if ((blackListTemp.getStatus() + blackListWarter.getStatus()) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean saveBlackListTemp(BlackListWarter blackListWarter, List<BlackListTemp> blackListTempList) throws Exception {
//		if (addBlackListTemp(blackListWarter, blackListTempList)
//				&& addBlackListStatus(blackListWarter, blackListTempList)) {
//			return true;
//		}else{
//			throw new Exception("处理黑名单流水失败");
//		}
		return null;
	}

	private Boolean addBlackListRelieveStatus(String netNo, String obuId, String license, String cardType
			, String cardNo, Date relieveTime, Integer status, Date genTime, String genMode) {
		BlackListRelieveStatus blackListStatus = new BlackListRelieveStatus(netNo, obuId, license, cardType
				, cardNo, relieveTime, status, genTime, genMode);
		try {
			blackListDao.saveBlackListRelieveStatus(blackListStatus);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * 添加黑名单中间表
	 *
	 * @param blackListWarter
	 * @return
	 */
//	private Boolean addBlackListTemp(BlackListWarter blackListWarter,List<BlackListTemp> blackListTempList){
//		//判断黑名单中间表是否存在相同卡号和状态的记录，如果存在，则不做处理，否则，把该记录添加进黑名单中间表
//		
//		//如果存在，即查询结果为空，则直接添加近黑名单中间表
//		if(blackListTempList==null){
//			if(blackListWarter.getStatus() > 0){
//				return addBlackListTemp(blackListWarter.getNetNo(),blackListWarter.getObuId(),blackListWarter.getLicense()
//						,blackListWarter.getCardType(),blackListWarter.getCardNo(),blackListWarter.getGenTime(),
//						blackListWarter.getStatus(),blackListWarter.getGenType());
//			}else{//如果黑名单中间表没有这条记录的但是黑名单流水表传入的是解除状态，则对该黑名单流水表不做处理
//				return false;
//			}
//		}else{
//			//判断黑名单中间表是否存在相同的状态，如果存在相同的状态，则不做任何处理
//			for(BlackListTemp blackListTemp:blackListTempList){
//				if(blackListTemp.getStatus()== blackListWarter.getStatus()){
//					return false;
//				}
//			}
//			//如果黑名单中间表不存在相同的状态，则判断黑名单流水表的状态
//			//如果黑名单流水表的状态是负数，则需要把黑名单中间表的记录删除，并在黑名单解除中间表上加一条新的记录
//			if(blackListWarter.getStatus()<0){//如果是解除状态，则需要判断黑名单流水表是否存在跟解除状态相对应的黑名单状态
//				for(BlackListTemp blackListTemp:blackListTempList){
//					//黑名单状态与黑名单解除状态是一正一负的，在判断是否存在相应的黑名单状态时，只需要判断两个值相加是否为0即可
//					if((blackListTemp.getStatus()+blackListWarter.getStatus())==0){
//						blackListDao.deleteBlackListTemp(blackListTemp);
//						//往黑名单解除中间表插数据
//						return addBlackListRelieveTemp(blackListWarter.getNetNo(),blackListWarter.getObuId(),blackListWarter.getLicense()
//								,blackListWarter.getCardType(),blackListWarter.getCardNo(),blackListWarter.getGenTime(),
//								blackListTemp.getStatus(),blackListWarter.getStatus(),new Date(),blackListWarter.getGenType());
//					}
//				}
//				
//			}else{
//				//往黑名单中间表插数据
//				return addBlackListTemp(blackListWarter.getNetNo(),blackListWarter.getObuId(),blackListWarter.getLicense()
//						,blackListWarter.getCardType(),blackListWarter.getCardNo(),blackListWarter.getGenTime(),
//						blackListWarter.getStatus(),blackListWarter.getGenType());
//			}
//		}
//		return false;
//	}
	private BlackListTemp addBlackListTemp(String netNo, String obuId, String license, String cardType, String cardNo, Date genTime, Integer status, String genMode) {
		BlackListTemp blackListTemp = new BlackListTemp(netNo, obuId, license, cardType, cardNo, genTime, status, genMode);
		blackListTemp = blackListDao.saveBlackListTemp(blackListTemp);
		return blackListTemp;
	}

	private Boolean addBlackListRelieveTemp(String netNo, String obuId, String license, String cardType, String cardNo, Date genTime, Integer status,
	                                        Integer dealStatus, Date dealTime, String genMode, Date sysGenTime,
	                                        Date sysDealTime, Integer flag, Long tempId) {
		BlackListRelieveTemp blackListTemp = new BlackListRelieveTemp(netNo, obuId, license, cardType, cardNo, genTime, status, dealStatus, dealTime, genMode, sysGenTime,
				sysDealTime, flag, tempId);
		blackListDao.saveBlackListRelieveTemp(blackListTemp);
		return true;
	}

	@Override
	public void saveBlackListAllSend() {
//		try {
//			
//			Map<String,Object> blackListAllInfo = blackListDao.findBlackListAllTotal();
//			if(blackListAllInfo==null){
//				logger.info("黑名单表不存在记录");
//				return;
//			}
//			BigDecimal totalCount = (BigDecimal)blackListAllInfo.get("TOTALCOUNT");
//			BigDecimal maxId = (BigDecimal)blackListAllInfo.get("MAXID");
//			Pager pager = new Pager();
//			Long pageNum = null;
//			if(totalCount.compareTo(BATCHCOUNT)==-1){
//				pageNum = new Long(0);
//			}else{
//				pageNum = totalCount.divide(BATCHCOUNT).longValue();
//			}
//			
//			String boardListNo = getAllBoardListNo("1021");
//			//要发送的总记录数
//			BlackListVersion blackListVersion = blackListDao.findBlackListVersion();
//			Long version = null;
//			//当前黑名单发送的最后一个id
//			Long currentId = null;
//			Long currentRelieveId = null;
//			if(blackListVersion == null){
//				version = new Long(1);
//				currentId = new Long(0);
//				currentRelieveId = new Long(0);
//			}else{
//				version = blackListVersion.getVersionNo()+1;
//				currentId = blackListVersion.getCurrentBlackListId();
//				currentRelieveId = blackListVersion.getCurrentRelieveListId();
//			}
//			for(int i = 0;i<pageNum+1;i++){
//				pager.setBegin(0);
//				pager.setCurrentPage(String.valueOf(i));
//				pager.setPageSize(BATCHCOUNT.toString());
//				Pager pagerResult = blackListDao.findBlackAllByPager(pager,maxId);	
//				currentId = batchSaveBlackListAllSend(boardListNo, version, currentId, pagerResult);
//				
//			}
//			if(totalCount.compareTo(BigDecimal.ZERO)==1){
//				proviceSendBoardDao.saveProviceSendBoard(new Long(boardListNo), "1021", "TB_GBTOLLCARDBLACKBASE_SEND", new Date(),0, totalCount.longValue());
//			}
//			BlackListVersion newBlackListVersion = new BlackListVersion();	
//			newBlackListVersion.setVersionNo(version);
//			newBlackListVersion.setVersionType("0");
//			newBlackListVersion.setGenTime(new Date());
//			newBlackListVersion.setCurrentBlackListId(currentId);
//			newBlackListVersion.setCurrentRelieveListId(currentRelieveId);
//			blackListDao.saveBlackListVersion(newBlackListVersion);
//			
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}

	private Long batchSaveBlackListAllSend(String boardListNo, Long version, Long currentId, Pager pagerResult) {
		DataSourceTransactionManager txManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
		List<Map<String, Object>> list = pagerResult.getResultList();
		String netNo = null;
		String cardCode = null;
		String cardType = null;
		String obuId = null;
		String license = null;
		Integer genCau = null;
		String genMode = null;
		Date genTime = null;
		String remark = null;
		Integer mode = null;

		if (list != null) {
			for (Map<String, Object> map : list) {
				netNo = map.get("NETNO") == null ? null : (String) map.get("NETNO");
				cardType = map.get("CARDTYPE") == null ? null : (String) map.get("CARDTYPE");
				cardCode = map.get("CARDNO") == null ? null : (String) map.get("CARDNO");
				obuId = map.get("OBUID") == null ? null : (String) map.get("OBUID");
				license = map.get("LICENSE") == null ? null : (String) map.get("LICENSE");
				genCau = ((BigDecimal) map.get("STATUS")).intValue();
				genMode = map.get("GENMODE") == null ? null : (String) map.get("GENMODE");

				if (StringUtil.isEquals(genMode, "1")) {
					mode = 0;
				} else {
					mode = 1;
				}
				genTime = map.get("GENTIME") == null ? null : (Date) map.get("GENTIME");
				ClearBlackList clearBlackList = new ClearBlackList(netNo, cardCode, cardType, obuId,
						license, genCau, mode, genTime, version, remark, new Date(), new Long(boardListNo));
				blackListDao.saveBlackListClearAll(clearBlackList);
				currentId = ((BigDecimal) map.get("ID")).longValue();
			}

		}
		txManager.commit(txStatus);//事务处理成功，提交当前事务  
		return currentId;
	}

	private BlackListVersion findBlackListVersion() throws IOException, ParseException {
		BlackListVersion blackListVersion = blackListDao.findBlackListVersion();
		if (blackListVersion == null) {
			return null;
		}
		Date currentTime = new Date();
		String blackListPeriod = "";
		ServiceParamSetNew spn = omsParamDao.findOmsParam("BLACKLISTPERIOD");
		if (spn == null) {
			blackListPeriod = "2";
		} else {
			blackListPeriod = spn.getValue();
		}

		ServiceParamSetNew spnBlackListAll = omsParamDao.findOmsParam("BLACKLISTALL");
		String blackListAll = "";
		if (spnBlackListAll == null) {
			blackListAll = "09:00:00";
		} else {
			blackListAll = spnBlackListAll.getValue();
		}
		//获取上一个版本的结束时间，作为本版本的开始时间
		Date startTime = blackListVersion.getEndTime();

		//当上一个版本的时间+2小时比当前时间要大，即还没到发送版本的时间
		if (DateUtil.addHour(startTime, Integer.valueOf(blackListPeriod)).after(currentTime)) {
			return null;
		}
		//获取本版本的结束时间
		Date endTime = blackListVersion.getEndTime();
		//计算本版本的结束时间，如果当前时间加上2小时后还是比当前时间要小的话，继续加
		while (DateUtil.addHour(endTime, Integer.valueOf(blackListPeriod)).before(currentTime)) {
			endTime = DateUtil.addHour(endTime, Integer.valueOf(blackListPeriod));
		}

		BlackListVersion currentVersion = new BlackListVersion();

		currentVersion.setStartTime(startTime);
		currentVersion.setEndTime(endTime);
		currentVersion.setVersionNo(blackListVersion.getVersionNo() + 1);

		//判断当前版本是增量还是全量
		//1.当两个时间差大于24小时，则发送全量
		//2.当开始时间和结束时间包含了当天的9点，则发送全量
		//算出当天的全量时间（当天9点）
		Date blackAllTime = DateUtil.toDate(DateUtil.formatDate(currentTime, "yyyy-MM-dd") + " " + blackListAll, "yyyy-MM-dd HH:mm:ss");
		//TODO
		if (blackAllTime.after(startTime) && (blackAllTime.before(endTime) || blackAllTime.compareTo(endTime) == 0)) {
			currentVersion.setVersionType("0");
		} else {
			currentVersion.setVersionType("1");
		}
		return currentVersion;
	}

	@Override
	public void saveBlackListSend() throws IOException, ParseException {
		//处理版本信息

//		List<BlackListVersion> blackListVersionList = blackListDao.findBlackListVersion("1");
//		if(blackListVersionList==null){
//			return;
//		} 
//		for(BlackListVersion blackListVersion:blackListVersionList){
		BlackListVersion blackListVersion = findBlackListVersion();
		if (blackListVersion == null) {
			return;
		}
		if (blackListVersion.getVersionType() == null) {
			finishBlackListSend(blackListVersion);
		}
		Date versionTime = blackListVersion.getStartTime();
		String boardListNo;
		//1表示发送增量
		if (StringUtil.isEquals(blackListVersion.getVersionType(), "1")) {
			boardListNo = getAllBoardListNo("1022");

			String startTime = DateUtil.formatDate(blackListVersion.getStartTime(), "yyyyMMddHHmmss");
			blackListDao.saveBlackListAddition(boardListNo, startTime, DateUtil.formatDate(blackListVersion.getEndTime(), "yyyyMMddHHmmss"), blackListVersion.getVersionNo());
			finishBlackListSend(blackListVersion);
		} else {//发送全量
			boardListNo = getAllBoardListNo("1022");
			//保存所有全量黑名单历史信息
			blackListDao.saveBlackListAllHis(boardListNo, DateUtil.formatDate(versionTime, "yyyyMMddHHmmss"), DateUtil.formatDate(blackListVersion.getEndTime(), "yyyyMMddHHmmss"), blackListVersion.getVersionNo());
			//发送国标黑名单信息
			Long totalCount = blackListDao.saveGBBlackList(boardListNo);
			//发送黑名单公告表
			proviceSendBoardDao.saveProviceSendBoard(Long.valueOf(boardListNo), "1022", "TB_GBTOLLCARDBLACKBASE_SEND", new Date(), 0, totalCount);
			//发送地标黑名单信息
			String boardListNoLocal = getAllBoardListNo("1023");
			totalCount = blackListDao.saveLocalBlackList(boardListNo, boardListNoLocal);
			proviceSendBoardDao.saveProviceSendBoard(Long.valueOf(boardListNoLocal), "1023", "TB_DBTOLLCARDBLACKLIST_SEND", new Date(), 0, totalCount);
			//blackListDao.saveBlackListHis(boardListNo,"TB_GBTOLLCARDBLACKBASE_SEND");

			finishBlackListSend(blackListVersion);
		}
//		}
	}

	private void finishBlackListSend(BlackListVersion blackListVersion) {
		//blackListVersion.setEndTime(new Date());
		blackListVersion.setStatus(2);
		blackListDao.saveBlackListVersion(blackListVersion);
	}


	@Override
	public void saveBlackListAdditionSend() {
	}

	private String getAllBoardListNo(String tableCode) {
		return String.valueOf((new Date()).getTime()) + tableCode;
	}

	@Override
	public void saveCardLost(String cardType, String cardNo, Date genTime, String genType,
	                         Long operId, String operNo, String operName, Long placeId, String placeNo, String placeName,
	                         Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, 2, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);

	}

	@Override
	public void saveCardNoLost(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                           String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, -2, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveCardCancle(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                           String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, 10, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveCardStopUse(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                            String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, 5, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveOBUDisassemble(String obuId, Date genTime, String genType, Long operId, String operNo,
	                               String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(obuId, null, null, null, genTime, genType, 6, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveOBULost(String obuId, Date genTime, String genType, Long operId, String operNo,
	                        String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(obuId, null, null, null, genTime, genType, 2, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveOBUNoLost(String obuId, Date genTime, String genType, Long operId, String operNo,
	                          String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(obuId, null, null, null, genTime, genType, -2, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveOBUCancel(String obuId, Date genTime, String genType, Long operId, String operNo,
	                          String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(obuId, null, null, null, genTime, genType, 10, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}


	//止付黑名单-status为4
	@Override
	public void saveStopPayCard(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                            String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, 4, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveStopUseCard(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                            String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, 5, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveRelieveStopUseCard(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                                   String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, -5, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveRelieveStopPayCard(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                                   String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, -4, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);

	}

	@Override
	public void saveCardRelieveCancel(String cardType, String cardNo, Date genTime, String genType, Long operId,
	                                  String operNo, String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(null, null, cardType, cardNo, genTime, genType, -10, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveOBUStopUse(String obuId, Date genTime, String genType, Long operId, String operNo,
	                           String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(obuId, null, null, null, genTime, genType, 5, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public void saveOBURelieveStopUse(String obuId, Date genTime, String genType, Long operId, String operNo,
	                                  String operName, Long placeId, String placeNo, String placeName, Date operTime) {
		BlackListWarter blw = new BlackListWarter(obuId, null, null, null, genTime, genType, -5, "0",
				operId, operNo, operName, placeId, placeNo, placeName, operTime, null);
		if (blw.getNetNo() == null) {
			blw.setNetNo(Constant.NETNO);
		}
		blackListDao.saveBlackListWarter(blw);
	}

	@Override
	public List<BlackListTemp> findBlackListByCardNo4AgentCard(String cardNo) {
		return blackListDao.findBlackListByCardNo4AgentCard(cardNo);
	}

	@Override
	public List<BlackListTemp> findBlackList(String cardNo, String status, String genMode) {
		return blackListDao.findBlackList(cardNo, status, genMode);
	}

	@Override
	public void saveBlackListTemp(BlackListRelieveTemp blackListRelieveTemp) {
		blackListDao.saveBlackListRelieveTemp(blackListRelieveTemp);
	}
}
