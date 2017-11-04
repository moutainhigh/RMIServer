package com.hgsoft.accountC.service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCStopPayDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.serviceInterface.IAccountCApplyService;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IRelieveStopPayService;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.clearInterface.dao.ApplyRemovePaymentSendDao;
import com.hgsoft.clearInterface.dao.ManualRemoveBlackListSendDao;
import com.hgsoft.clearInterface.dao.PaymentCardBlacklistRecvDao;
import com.hgsoft.clearInterface.dao.RelieveStopPayDao;
import com.hgsoft.clearInterface.dao.StopPayRelieveApplyDao;
import com.hgsoft.clearInterface.entity.ApplyRemovePaymentSend;
import com.hgsoft.clearInterface.entity.ManualRemoveBlackListSend;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.entity.RelieveStopPay;
import com.hgsoft.clearInterface.entity.RelieveStopPayDetail;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.ServiceFlowRecordDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardApplyCancelStopPayReceipt;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardHandCancelStopPayReceipt;
import com.hgsoft.settlement.dao.AcTradeDetailInfoDao;
import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.ygz.common.CardBlackTypeEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.NoRealTransferService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhengwenhai
 * @FileName RelieveStopPayService.java
 * @Description: TODO
 * @Date 2016年2月23日 下午3:58:10
 */
@Service
public class RelieveStopPayService implements IRelieveStopPayService {

	private static Logger logger = Logger.getLogger(RelieveStopPayService.class
			.getName());

	@Resource
	SequenceUtil sequenceUtil;

	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	//	@Resource
//	private ITollService tollService;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private IAccountCApplyService accountCApplyService;
	@Resource
	private IRelieveStopPayService relieveStopPayService;
	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;

//	@Resource
//	private IAcctollcollectListService acctollcollectListService;

	/*@Resource
	private StopAcListDao stopAcListDao;
	@Resource
	private ACinfoDao aCinfoDao;*/
	@Resource
	AccountCStopPayDao accountCStopPayDao;
	@Resource
	ServiceFlowRecordDao serviceFlowRecordDao;
	@Resource
	AccountCBussinessDao accountCBussinessDao;
	@Resource
	MainAccountInfoDao mainAccountInfoDao;
	@Resource
	AcTradeDetailInfoDao acTradeDetailInfoDao;
	@Resource
	private AccountCDao accountCDao;
	@Resource
	private ManualRemoveBlackListSendDao manualRemoveBlackListSendDao;
	@Resource
	private ApplyRemovePaymentSendDao applyRemovePaymentSendDao;
	@Resource
	private PaymentCardBlacklistRecvDao paymentCardBlacklistRecvDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private StopPayRelieveApplyDao stopPayRelieveApplyDao;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private RelieveStopPayDao relieveStopPayDao;
	@Resource
	private ReceiptDao receiptDao;

	//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171023
	@Resource
	private NoRealTransferService noRealTransferService;
	//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171023


	/**
	 * 查找该银行账号是否为止付黑名单
	 */
	@Override
	public boolean checkStopBlackList(String bankAccount) {
		return stopPayRelieveApplyDao.checkStopBlackList(bankAccount);
	}

	/**
	 * 申请解除止付列表页面详情(第一个页面)
	 */

	@Override
	public Pager relieveStopDetail(Pager pager, RelieveStopPay relieveStopPay) {
		return stopPayRelieveApplyDao.stopPageDetail(pager, relieveStopPay);
	}

	/**
	 * @param paymentCardBlacklistRecv
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年5月4日
	 * @update author hzw
	 * @date 2017年9月27日
	 */
	@Override
	public Map<String, Object> stopDetail(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		Map<String, Object> resultmap = new HashMap<>();
		//List<Map<String,Object>> list = null;
		String bankAccount = "";

		AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
		if (accountCApply != null) {
			bankAccount = accountCApply.getBankAccount();
			String startTime = null;
			Date newDate = new Date();//新建一个时间来作为基准时间
			String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
			BigDecimal etcFeeAll = new BigDecimal(0);
			BigDecimal lateFeeAll = new BigDecimal(0);
			BigDecimal etcFeeOne = new BigDecimal(0);
			BigDecimal lateFeeOne = new BigDecimal(0);
			Timestamp newStopPayTime = null;
			List<String> cardNoList = new ArrayList<>();
			//获得最后一次扣款时间(回盘时间,通行费依据)
			String newCurrentTime = stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
			if (newCurrentTime != null) {
				startTime = newCurrentTime;
			}
			//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
			newStopPayTime = stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());
			SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());

			List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, nowTime);
			String lateFeeRate = this.getLateFeeRate("LateFeeRate");//营运接口获取滞纳金比例
			if (lateFeeRate.equals("0")) {
				resultmap.put("RESULT", "false");
				resultmap.put("MSG", "处理失败!滞纳金比例获取出错,请联系管理员");
				return resultmap;
			}
			if (acEtcFeeList.size() != 0) {
				for (Map<String, Object> map : acEtcFeeList) {
					cardNoList.add((String) map.get("CARDNO"));
					etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总后的通行费
					lateFeeOne = relieveStopPayService.getLateFee(etcFeeOne, lateFeeRate, new Date(newStopPayTime.getTime()), newDate);//滞纳金
					//计算页面总算
					etcFeeAll = etcFeeAll.add(etcFeeOne);
					lateFeeAll = lateFeeAll.add(lateFeeOne);
				}
				resultmap.put("ETCFEE", etcFeeAll);
				resultmap.put("LATEFEE", lateFeeAll);
				resultmap.put("PAGER", accountCDao.stopDetail(pager, cardNoList, newCurrentTime, nowTime));
				resultmap.put("BANKACCOUNT", bankAccount);
			} else {
				resultmap.put("ETCFEE", etcFeeAll);
				resultmap.put("LATEFEE", lateFeeAll);
				resultmap.put("PAGER", null);
				resultmap.put("BANKACCOUNT", bankAccount);
			}


		}
		return resultmap;
	}


	/**
	 * 申请解除止付
	 * 数据保存方法
	 */
	@Override
	public Map<String, Object> saveApplyRelieveStopPay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, Customer customer, AccountCBussiness accountCBussiness, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
			if (accountCApply != null) {
				resultMap.put("result", "true");
				String cardno = "";
				String startTime = null;
				Date newDate = new Date();//新建一个时间来作为基准时间
				String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
				BigDecimal etcFeeAll = new BigDecimal(0);
				BigDecimal lateFeeAll = new BigDecimal(0);
				BigDecimal etcFeeOne = new BigDecimal(0);
				BigDecimal lateFeeOne = new BigDecimal(0);
				Timestamp newStopPayTime = null;
				Date currentDate = new Date();//
				DateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
				//获得最后一次扣款时间(回盘时间,通行费依据)
				String newCurrentTime = stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
				if (newCurrentTime != null) {
					startTime = newCurrentTime;
				}
				//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
				newStopPayTime = stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());

				Long SEQ_RELIEVESTOPPAY_NO = sequenceUtil.getSequenceLong("SEQ_RELIEVESTOPPAY_NO");//先获取总表ID
				//根据银行账号查找子账户id 减少查询通行费时的连表数量
				SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
				//根据子账户ID，回盘时间，当前时间去查询记帐卡交易明细表的交易记录
				List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, nowTime);
				String lateFeeRate = this.getLateFeeRate("LateFeeRate");//营运接口获取滞纳金比例
				if (lateFeeRate.equals("0")) {
					resultMap.put("result", "false");
					resultMap.put("msg", "处理失败!滞纳金比例获取出错,请联系管理员");
					return resultMap;
				}
				for (Map<String, Object> map : acEtcFeeList) {
					cardno = (String) map.get("CARDNO");
					etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总的通行费
					lateFeeOne = relieveStopPayService.getLateFee(etcFeeOne, lateFeeRate, new Date(newStopPayTime.getTime()), newDate);//滞纳金

					etcFeeAll = etcFeeAll.add(etcFeeOne);
					lateFeeAll = lateFeeAll.add(lateFeeOne);

					//保存申请解除止付明细记录表
					RelieveStopPayDetail relieveStopPayDetail = new RelieveStopPayDetail();
					Long SEQ_RELIEVESTOPPAYDE_NO = sequenceUtil.getSequenceLong("SEQ_RELIEVESTOPPAYDETAIL_SEQ");
					relieveStopPayDetail.setRelieveStopPayId(SEQ_RELIEVESTOPPAY_NO);
					relieveStopPayDetail.setBAccount(paymentCardBlacklistRecv.getAcbAccount());
					relieveStopPayDetail.setCardNo(cardno);
					relieveStopPayDetail.setId(SEQ_RELIEVESTOPPAYDE_NO);
					relieveStopPayDetail.setLateFeeStartTime(newStopPayTime);
					if (newCurrentTime != null) {
						try {
							currentDate = sdf.parse(newCurrentTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						relieveStopPayDetail.setEtcFeeStartTime(new Timestamp(currentDate.getTime()));
					} else {
						relieveStopPayDetail.setEtcFeeStartTime(null);
					}

					relieveStopPayDetail.setFeeEndTime(new Timestamp(newDate.getTime()));
					relieveStopPayDetail.setEtcFee(etcFeeOne);//通行费
					relieveStopPayDetail.setLateFee(lateFeeOne);//滞纳金金额
					relieveStopPayDetail.setRemark("申请解除止付明细");
					relieveStopPayDetail.setApplyTime(newDate);
					relieveStopPayDetail.setCreateTime(newDate);
					relieveStopPayDao.saveDetail(relieveStopPayDetail);


					//存储清算数据
					ApplyRemovePaymentSend applyRemovePaymentSend = new ApplyRemovePaymentSend();
					Long SEQ_APPLYREMOVEPAYMENTSEND_NO = sequenceUtil.getSequenceLong("SEQ_APPLYREMOVEPAYMENTSEND_NO");
					applyRemovePaymentSend.setId(SEQ_APPLYREMOVEPAYMENTSEND_NO);
					applyRemovePaymentSend.setBaccount(relieveStopPayDetail.getBAccount());
					applyRemovePaymentSend.setCardNo(cardno);
					applyRemovePaymentSend.setGenTime(newDate);
					applyRemovePaymentSend.setLateFee(lateFeeOne);
					applyRemovePaymentSend.setRemark("申请解除止付");
					applyRemovePaymentSend.setUpdateTime(newDate);
					BigDecimal boardListNo = new BigDecimal(newDate.getTime() + "1017");
					applyRemovePaymentSend.setBoardListNo(boardListNo);
					applyRemovePaymentSendDao.save(applyRemovePaymentSend);

				}

				//保存申请解除支付记录表
				RelieveStopPay relieveStopPay = new RelieveStopPay();
				relieveStopPay.setBAccount(paymentCardBlacklistRecv.getAcbAccount());
				relieveStopPay.setId(SEQ_RELIEVESTOPPAY_NO);
				relieveStopPay.setLateFeeStartTime(newStopPayTime);
				if (newCurrentTime != null) {
					try {
						currentDate = sdf.parse(newCurrentTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					relieveStopPay.setEtcFeeStartTime(new Timestamp(currentDate.getTime()));
				} else {
					relieveStopPay.setEtcFeeStartTime(null);
				}
				relieveStopPay.setFeeEndTime(new Timestamp(newDate.getTime()));
				relieveStopPay.setEtcFee(etcFeeAll);//通行费
				relieveStopPay.setLateFee(lateFeeAll);//滞纳金金额
				relieveStopPay.setRemark("申请解除止付");
				relieveStopPay.setFlag(0);
				relieveStopPay.setState(0);
				relieveStopPay.setApplyTime(newDate);
				relieveStopPay.setCreateTime(newDate);
				//2017年10月20日09:55:28 新增字段
				relieveStopPay.setOperId(accountCBussiness.getOperId());
				relieveStopPay.setOperName(accountCBussiness.getOperName());
				relieveStopPay.setOperNo(accountCBussiness.getOperNo());
				relieveStopPay.setPlaceId(accountCBussiness.getPlaceId());
				relieveStopPay.setPlaceNo(accountCBussiness.getPlaceNo());
				relieveStopPay.setPlaceName(accountCBussiness.getPlaceName());
				relieveStopPayDao.save(relieveStopPay);

				//增加业务记录表，需要一个accountCBussiness对象 CSMS_AccountC_bussiness 在前面new
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setState("10");//类型为：申请解除止付
				accountCBussiness.setTradeTime(newDate);
				accountCBussiness.setBusinessId(relieveStopPay.getId());//申请解除支付的id
				accountCBussinessDao.save(accountCBussiness);

				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				//serviceWater.setCardNo(relieveStopPay.getCardNo());
				serviceWater.setSerType("221");//221申请解除止付

				if (accountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
				if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：申请解除止付");
				serviceWater.setOperTime(newDate);
				serviceWaterDao.save(serviceWater);


				//记帐卡申请解除止付回执
				AccCardApplyCancelStopPayReceipt accCardApplyCancelStopPayReceipt = new AccCardApplyCancelStopPayReceipt();
				accCardApplyCancelStopPayReceipt.setTitle("记帐卡申请解除止付回执");
				accCardApplyCancelStopPayReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
				accCardApplyCancelStopPayReceipt.setApplyAccName(accountCApply.getAccName());
				accCardApplyCancelStopPayReceipt.setApplyBankAccount(accountCApply.getBankAccount());
				accCardApplyCancelStopPayReceipt.setApplyBankName(accountCApply.getBank());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardApplyCancelStopPay.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardApplyCancelStopPay.getName());
				this.saveReceipt(receipt, accountCBussiness, accCardApplyCancelStopPayReceipt, customer);

				resultMap.put("result", "true");
				return resultMap;
			} else {
				resultMap.put("result", "false");
				return resultMap;
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "申请解除止付失败");
			e.printStackTrace();
			throw new ApplicationException("申请解除止付失败");
		}
	}

	/**
	 * 手工申请解除止付
	 */


	@Override
	public Map<String, Object> saveRelieveStopPay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, Customer customer, AccountCBussiness accountCBussiness, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
			if (accountCApply != null) {
				resultMap.put("result", "true");
				String cardno = "";
				String startTime = null;
				Date newDate = new Date();//新建一个时间来作为基准时间
				String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
				BigDecimal etcFeeAll = new BigDecimal(0);
				BigDecimal lateFeeAll = new BigDecimal(0);
				BigDecimal etcFeeOne = new BigDecimal(0);
				BigDecimal lateFeeOne = new BigDecimal(0);
				Timestamp newStopPayTime = null;
				Date currentDate = new Date();//格式转换需要
				DateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
				Long SEQ_RELIEVESTOPPAY_NO = sequenceUtil.getSequenceLong("SEQ_RELIEVESTOPPAY_NO");//先获取总表ID

				//获得最后一次扣款时间(回盘时间,通行费依据)20170923183134
				String newCurrentTime = stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
				if (newCurrentTime != null) {
					startTime = newCurrentTime;
				}
				//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
				newStopPayTime = stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());

				//List<Map<String,Object>> cardNoList = accountCService.findAccountCInfoList(accountCApply);
				List<RelieveStopPayDetail> relieveStopPayDetailList = new ArrayList<>();
				List<ManualRemoveBlackListSend> manualSendList = new ArrayList<>();
				List<AcTradeDetailInfo> acTradeDetailInfoList = new ArrayList<>();
				//根据银行账号查找子账户id 减少查询通行费时的连表数量
				SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
				//根据子账户ID，回盘时间，当前时间去查询记帐卡交易明细表的交易记录
				List<AcTradeDetailInfo> acDetailInfoList = acTradeDetailInfoDao.findAcDetail(subAccountInfo.getId(), startTime, nowTime);
				for (AcTradeDetailInfo acOne : acDetailInfoList) {
					acOne.setPayFlag(1);//支付标识已支付
					acOne.setPayType(3);//手工申请解除止付
					acOne.setPayTime(newDate);//支付时间为当前
					acOne.setComGenTime(newDate);//业务时间：当前时间
					//先存储进list
					acTradeDetailInfoList.add(acOne);
				}
				String lateFeeRate = this.getLateFeeRate("LateFeeRate");//营运接口获取滞纳金比例
				if (lateFeeRate.equals("0")) {
					resultMap.put("result", "false");
					resultMap.put("msg", "处理失败!滞纳金比例获取出错,请联系管理员");
					return resultMap;
				}
				//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171023
				Set<String> cardNoSet = new HashSet<String>();
				//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171023
				List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, nowTime);
				for (Map<String, Object> map : acEtcFeeList) {
					cardno = (String) map.get("CARDNO");
					etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总的通行费
					lateFeeOne = relieveStopPayService.getLateFee(etcFeeOne, lateFeeRate, new Date(newStopPayTime.getTime()), newDate);//滞纳金

					//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171023
					cardNoSet.add(cardno);
					//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171023

					//保存申请解除止付明细记录表
					RelieveStopPayDetail relieveStopPayDetail = new RelieveStopPayDetail();
					Long SEQ_RELIEVESTOPPAYDE_NO = sequenceUtil.getSequenceLong("SEQ_RELIEVESTOPPAYDETAIL_SEQ");
					relieveStopPayDetail.setRelieveStopPayId(SEQ_RELIEVESTOPPAY_NO);
					relieveStopPayDetail.setBAccount(paymentCardBlacklistRecv.getAcbAccount());
					relieveStopPayDetail.setCardNo(cardno);
					relieveStopPayDetail.setId(SEQ_RELIEVESTOPPAYDE_NO);
					relieveStopPayDetail.setLateFeeStartTime(newStopPayTime);
					if (newCurrentTime != null) {
						try {
							currentDate = sdf.parse(newCurrentTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						relieveStopPayDetail.setEtcFeeStartTime(new Timestamp(currentDate.getTime()));
					} else {
						relieveStopPayDetail.setEtcFeeStartTime(null);
					}

					relieveStopPayDetail.setFeeEndTime(new Timestamp(newDate.getTime()));
					relieveStopPayDetail.setEtcFee(etcFeeOne);//通行费
					relieveStopPayDetail.setLateFee(lateFeeOne);//滞纳金金额
					relieveStopPayDetail.setRemark("手工申请解除止付明细");
					relieveStopPayDetail.setApplyTime(newDate);
					relieveStopPayDetail.setCreateTime(newDate);
					//先存储进list
					relieveStopPayDetailList.add(relieveStopPayDetail);

					//存储清算数据
					ManualRemoveBlackListSend manualRemoveBlackListSend = new ManualRemoveBlackListSend();
					Long SEQ_APPLYREMOVEPAYMENTSEND_NO = sequenceUtil.getSequenceLong("SEQ_TBMANUALREMOVEBLACKLIST_NO");
					manualRemoveBlackListSend.setId(SEQ_APPLYREMOVEPAYMENTSEND_NO);
					manualRemoveBlackListSend.setBaccount(relieveStopPayDetail.getBAccount());
					manualRemoveBlackListSend.setCardNo(cardno);
					manualRemoveBlackListSend.setHandpaytime(new Timestamp(newDate.getTime()));
					manualRemoveBlackListSend.setHandpayfee(lateFeeOne.add(etcFeeOne));
					manualRemoveBlackListSend.setEtcmoney(etcFeeOne);
					manualRemoveBlackListSend.setLatefee(lateFeeOne);
					manualRemoveBlackListSend.setPayAccount(null);//20170811修改 经确认设置为空
					manualRemoveBlackListSend.setPayFlag("5");//支付类型为其他
					manualRemoveBlackListSend.setRemark("手工申请解除止付");
					manualRemoveBlackListSend.setUpdateTime(new Timestamp(newDate.getTime()));
					String boardListNo = String.valueOf((new Date()).getTime()) + "1018";//手工解除止付是1018
					manualRemoveBlackListSend.setBoardListNo(Long.valueOf(boardListNo));
					//先存储进list
					manualSendList.add(manualRemoveBlackListSend);


					//把总的通行费和滞纳金计算好
					etcFeeAll = etcFeeAll.add(etcFeeOne);
					lateFeeAll = lateFeeAll.add(lateFeeOne);
				}

				//验证账户资金是否足够
				mainAccountInfo.setOperId(accountCBussiness.getOperId());
				mainAccountInfo.setPlaceId(accountCBussiness.getPlaceId());
				mainAccountInfo.setOperName(accountCBussiness.getOperName());
				mainAccountInfo.setOperNo(accountCBussiness.getOperNo());
				mainAccountInfo.setPlaceName(accountCBussiness.getPlaceName());
				mainAccountInfo.setPlaceNo(accountCBussiness.getPlaceNo());

				UnifiedParam unifiedParam = new UnifiedParam();
				unifiedParam.setMainAccountInfo(mainAccountInfo);//主账户
				unifiedParam.setLateAndEtcFee(etcFeeAll.add(lateFeeAll));//通行费+滞纳金总和
				unifiedParam.setType("20");
				unifiedParam.setOperId(accountCBussiness.getOperId());
				unifiedParam.setPlaceId(accountCBussiness.getPlaceId());
				unifiedParam.setOperName(accountCBussiness.getOperName());
				unifiedParam.setOperNo(accountCBussiness.getOperNo());
				unifiedParam.setPlaceName(accountCBussiness.getPlaceName());
				unifiedParam.setPlaceNo(accountCBussiness.getPlaceNo());
				//判断账户资金是否充足
				if (!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					resultMap.put("result", "false");
					resultMap.put("msg", "处理失败!账户可用余额不足");
					return resultMap;
				}

				//保存申请解除支付记录表
				RelieveStopPay relieveStopPay = new RelieveStopPay();
				relieveStopPay.setBAccount(paymentCardBlacklistRecv.getAcbAccount());
				relieveStopPay.setId(SEQ_RELIEVESTOPPAY_NO);
				relieveStopPay.setLateFeeStartTime(newStopPayTime);
				if (newCurrentTime != null) {
					try {
						currentDate = sdf.parse(newCurrentTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					relieveStopPay.setEtcFeeStartTime(new Timestamp(currentDate.getTime()));
				} else {
					relieveStopPay.setEtcFeeStartTime(null);
				}
				relieveStopPay.setFeeEndTime(new Timestamp(newDate.getTime()));
				relieveStopPay.setEtcFee(etcFeeAll);//通行费
				relieveStopPay.setLateFee(lateFeeAll);//滞纳金金额
				relieveStopPay.setRemark("手工申请解除止付");
				relieveStopPay.setFlag(1);//手工申请解除止付
				relieveStopPay.setState(0);//申请中
				relieveStopPay.setApplyTime(newDate);
				relieveStopPay.setCreateTime(newDate);
				//2017年10月20日09:55:28 新增字段
				relieveStopPay.setOperId(accountCBussiness.getOperId());
				relieveStopPay.setOperName(accountCBussiness.getOperName());
				relieveStopPay.setOperNo(accountCBussiness.getOperNo());
				relieveStopPay.setPlaceId(accountCBussiness.getPlaceId());
				relieveStopPay.setPlaceNo(accountCBussiness.getPlaceNo());
				relieveStopPay.setPlaceName(accountCBussiness.getPlaceName());
				relieveStopPayDao.save(relieveStopPay);
				//保存明细表,清算数据,更近记帐卡交易明细
				relieveStopPayDao.batchSaveDetail(relieveStopPayDetailList);
				manualRemoveBlackListSendDao.batchSend(manualSendList);
				acTradeDetailInfoDao.batchUpdatePayInfo(acTradeDetailInfoList);

				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				serviceWater.setSerType("222");//222手工申请解除止付

				if (accountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());
				if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：手工解除止付");
				serviceWater.setOperTime(newDate);
				serviceWaterDao.save(serviceWater);

				//增加业务记录表，需要一个accountCBussiness对象 CSMS_AccountC_bussiness 在前面new
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setState("10");//类型为：申请解除止付
				accountCBussiness.setTradeTime(newDate);
				accountCBussiness.setBusinessId(relieveStopPay.getId());//申请解除支付的id
				accountCBussinessDao.save(accountCBussiness);

				//记帐卡手工解除止付黑名单回执
				AccCardHandCancelStopPayReceipt accCardHandCancelStopPayReceipt = new AccCardHandCancelStopPayReceipt();
				accCardHandCancelStopPayReceipt.setTitle("记帐卡手工解除止付黑名单回执");
				accCardHandCancelStopPayReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
				accCardHandCancelStopPayReceipt.setApplyAccName(accountCApply.getAccName());
				accCardHandCancelStopPayReceipt.setApplyBankAccount(accountCApply.getBankAccount());
				accCardHandCancelStopPayReceipt.setApplyBankName(accountCApply.getBank());
				accCardHandCancelStopPayReceipt.setRelieveETCFee(NumberUtil.get2Decimal(relieveStopPay.getEtcFee().doubleValue() * 0.01));
				accCardHandCancelStopPayReceipt.setRelieveLateFee(NumberUtil.get2Decimal(relieveStopPay.getLateFee().doubleValue() * 0.01));
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardHandCancelStopPay.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardHandCancelStopPay.getName());
				this.saveReceipt(receipt, accountCBussiness, accCardHandCancelStopPayReceipt, customer);

				//ygz wangjinhao---------------------------------- start CARDBLACKLISTUPLOAD20171023
				if (!cardNoSet.isEmpty()) {
					for (String cardNo : cardNoSet) {
						noRealTransferService.blackListTransfer(cardNo, new Date(),
								CardBlackTypeEmeu.ACCOUNT_OVERDRAFT.getCode(), OperationTypeEmeu.EX_BLACK.getCode());
					}
				}
				//ygz wangjinhao---------------------------------- end CARDBLACKLISTUPLOAD20171023

			}
			return resultMap;
		} catch (Exception e) {
			logger.error(e.getMessage() + " 解除止付失败");
			e.printStackTrace();
			throw new ApplicationException("解除止付失败");
		}

	}


	/*
	 * 获取通信费
	 */
	@Override
	public List<Map<String, Object>> getEtcFee(String cardno, String startTime, String endTime) {

		List<Map<String, Object>> list = stopPayRelieveApplyDao.findEtcFee(startTime, endTime, cardno);
		if (list == null) {
			return null;
		} else {
			return list;
		}
	}

	/**
	 * 获取滞纳金比例
	 *
	 * @param key
	 * @return
	 */
	public String getLateFeeRate(String key) {
		Map<String, Object> rateMap = new HashMap<>();
		//如果不存在任意一次扣款记录，那么计算账号的最早一次通行时间
		String feeRate = "0";
		try {
			rateMap = omsParamInterfaceService.findOmsParam(key);
			if (rateMap.get("value") != null) {
				feeRate = (String) rateMap.get("value");
			}
		} catch (Exception e1) {
			logger.error("连接营运接口获取滞纳金比例失败 ");
			e1.printStackTrace();
		}
		return feeRate;
	}

	/*
	 * 获取滞纳金
	 */
	@Override
	public BigDecimal getLateFee(BigDecimal etcFee, String feeRate, Date startDate, Date endDate) {
		//20170811 经与增海确认 日期不需+1
		long day = DateUtil.getDaySub(DateUtil.formatDate(new Date(startDate.getTime()), "yyyy-MM-dd"), DateUtil.formatDate(endDate, "yyyy-MM-dd"));
		return etcFee.multiply(new BigDecimal(day)).multiply(new BigDecimal(feeRate));
	}


	/**
	 * @param pager
	 * @param paymentCardBlacklistRecv
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年5月4日
	 * update:hze
	 * 2017年10月2日
	 */
	@Override
	public Pager findApplyRelieveStoppayList(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		if (paymentCardBlacklistRecv != null) {
			AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
			if (accountCApply != null) {
				//筛选出该客户下所有的记帐卡有下发止付的记帐卡
				pager = relieveStopPayDao.findApplyRelieveStoppayList(pager, accountCApply.getBankAccount());
				return pager;
			} else {
				return null;
			}
		} else {
			return relieveStopPayDao.findApplyRelieveStoppayList(pager, null);
		}
	}

	/**
	 * @param pager
	 * @param paymentCardBlacklistRecv
	 * @return
	 * @Descriptioqn: 手工解除止付
	 * @author lgm
	 * @date 2017年5月4日
	 * update:hze
	 * 2017年10月2日
	 */
	@Override
	public Pager findManualRelieveStoppayList(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		if (paymentCardBlacklistRecv != null) {
			AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
			if (accountCApply != null) {
				//筛选出该客户下所有的记帐卡有下发止付的记帐卡
				pager = relieveStopPayDao.findManualRelieveStoppayList(pager, accountCApply.getBankAccount());
				return pager;
			} else {
				return null;
			}
		} else {
			return relieveStopPayDao.findManualRelieveStoppayList(pager, null);
		}
	}


	@Override
	public Map<String, Object> saveApplyRelieveStopPayByBankNoCustomer(String bankNo, Customer customer, AccountCBussiness accountCBussiness, AccountCApply accountCApply, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (accountCApply != null) {
				resultMap.put("result", "true");
				String cardno = "";
				String startTime = null;
				Date newDate = new Date();//新建一个时间来作为基准时间
				String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
				BigDecimal etcFeeAll = new BigDecimal(0);
				BigDecimal lateFeeAll = new BigDecimal(0);
				BigDecimal etcFeeOne = new BigDecimal(0);
				BigDecimal lateFeeOne = new BigDecimal(0);
				Timestamp newStopPayTime = null;
				Date currentDate = new Date();//
				DateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
				//获得最后一次扣款时间(回盘时间,通行费依据)
				String newCurrentTime = stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
				if (newCurrentTime != null) {
					startTime = newCurrentTime;
				}
				//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
				newStopPayTime = stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());

				Long SEQ_RELIEVESTOPPAY_NO = sequenceUtil.getSequenceLong("SEQ_RELIEVESTOPPAY_NO");//先获取总表ID
				//根据银行账号查找子账户id 减少查询通行费时的连表数量
				SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
				//根据子账户ID，回盘时间，当前时间去查询记帐卡交易明细表的交易记录
				List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, nowTime);
				String lateFeeRate = this.getLateFeeRate("LateFeeRate");//营运接口获取滞纳金比例
				if (lateFeeRate.equals("0")) {
					resultMap.put("result", "false");
					resultMap.put("msg", "处理失败!滞纳金比例获取出错,请联系管理员");
					return resultMap;
				}
				for (Map<String, Object> map : acEtcFeeList) {
					cardno = (String) map.get("CARDNO");
					etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总的通行费
					lateFeeOne = relieveStopPayService.getLateFee(etcFeeOne, lateFeeRate, new Date(newStopPayTime.getTime()), newDate);//滞纳金

					etcFeeAll = etcFeeAll.add(etcFeeOne);
					lateFeeAll = lateFeeAll.add(lateFeeOne);

					//保存申请解除止付明细记录表
					RelieveStopPayDetail relieveStopPayDetail = new RelieveStopPayDetail();
					Long SEQ_RELIEVESTOPPAYDE_NO = sequenceUtil.getSequenceLong("SEQ_RELIEVESTOPPAYDETAIL_SEQ");
					relieveStopPayDetail.setRelieveStopPayId(SEQ_RELIEVESTOPPAY_NO);
					relieveStopPayDetail.setBAccount(bankNo);
					relieveStopPayDetail.setCardNo(cardno);
					relieveStopPayDetail.setId(SEQ_RELIEVESTOPPAYDE_NO);
					relieveStopPayDetail.setLateFeeStartTime(newStopPayTime);
					if (newCurrentTime != null) {
						try {
							currentDate = sdf.parse(newCurrentTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						relieveStopPayDetail.setEtcFeeStartTime(new Timestamp(currentDate.getTime()));
					} else {
						relieveStopPayDetail.setEtcFeeStartTime(null);
					}

					relieveStopPayDetail.setFeeEndTime(new Timestamp(newDate.getTime()));
					relieveStopPayDetail.setEtcFee(etcFeeOne);//通行费
					relieveStopPayDetail.setLateFee(lateFeeOne);//滞纳金金额
					relieveStopPayDetail.setRemark("网上营业厅系统：申请解除止付明细");
					relieveStopPayDetail.setApplyTime(newDate);
					relieveStopPayDetail.setCreateTime(newDate);
					relieveStopPayDao.saveDetail(relieveStopPayDetail);


					//存储清算数据
					ApplyRemovePaymentSend applyRemovePaymentSend = new ApplyRemovePaymentSend();
					Long SEQ_APPLYREMOVEPAYMENTSEND_NO = sequenceUtil.getSequenceLong("SEQ_APPLYREMOVEPAYMENTSEND_NO");
					applyRemovePaymentSend.setId(SEQ_APPLYREMOVEPAYMENTSEND_NO);
					applyRemovePaymentSend.setBaccount(relieveStopPayDetail.getBAccount());
					applyRemovePaymentSend.setCardNo(cardno);
					applyRemovePaymentSend.setGenTime(newDate);
					applyRemovePaymentSend.setLateFee(lateFeeOne);
					applyRemovePaymentSend.setRemark("网上营业厅系统：申请解除止付");
					applyRemovePaymentSend.setUpdateTime(newDate);
					BigDecimal boardListNo = new BigDecimal(newDate.getTime() + "1017");
					applyRemovePaymentSend.setBoardListNo(boardListNo);
					applyRemovePaymentSendDao.save(applyRemovePaymentSend);

				}

				//保存申请解除支付记录表
				RelieveStopPay relieveStopPay = new RelieveStopPay();
				relieveStopPay.setBAccount(bankNo);
				relieveStopPay.setId(SEQ_RELIEVESTOPPAY_NO);
				relieveStopPay.setLateFeeStartTime(newStopPayTime);
				if (newCurrentTime != null) {
					try {
						currentDate = sdf.parse(newCurrentTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					relieveStopPay.setEtcFeeStartTime(new Timestamp(currentDate.getTime()));
				} else {
					relieveStopPay.setEtcFeeStartTime(null);
				}
				relieveStopPay.setFeeEndTime(new Timestamp(newDate.getTime()));
				relieveStopPay.setEtcFee(etcFeeAll);//通行费
				relieveStopPay.setLateFee(lateFeeAll);//滞纳金金额
				relieveStopPay.setRemark("网上营业厅系统：申请解除止付");
				relieveStopPay.setFlag(0);
				relieveStopPay.setState(0);
				relieveStopPay.setApplyTime(newDate);
				relieveStopPay.setCreateTime(newDate);
				//2017年10月20日09:55:28 新增字段
				relieveStopPay.setOperId(accountCBussiness.getOperId());
				relieveStopPay.setOperName(accountCBussiness.getOperName());
				relieveStopPay.setOperNo(accountCBussiness.getOperNo());
				relieveStopPay.setPlaceId(accountCBussiness.getPlaceId());
				relieveStopPay.setPlaceNo(accountCBussiness.getPlaceNo());
				relieveStopPay.setPlaceName(accountCBussiness.getPlaceName());
				relieveStopPayDao.save(relieveStopPay);

				//增加业务记录表，需要一个accountCBussiness对象 CSMS_AccountC_bussiness 在前面new
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setState("10");//类型为：申请解除止付
				accountCBussiness.setTradeTime(newDate);
				accountCBussiness.setBusinessId(relieveStopPay.getId());//申请解除支付的id
				accountCBussinessDao.save(accountCBussiness);

				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
				serviceWater.setId(serviceWater_id);
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				//serviceWater.setCardNo(relieveStopPay.getCardNo());
				serviceWater.setSerType("221");//221申请解除止付

				if (accountCApply != null)
					serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
				if (accountCApply != null) serviceWater.setBankNo(accountCApply.getObaNo());//银行编码
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：申请解除止付");
				serviceWater.setOperTime(newDate);
				serviceWaterDao.save(serviceWater);


//				//记帐卡申请解除止付回执
				AccCardApplyCancelStopPayReceipt accCardApplyCancelStopPayReceipt = new AccCardApplyCancelStopPayReceipt();
				accCardApplyCancelStopPayReceipt.setTitle("记帐卡申请解除止付回执");
				accCardApplyCancelStopPayReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
				accCardApplyCancelStopPayReceipt.setApplyAccName(accountCApply.getAccName());
				accCardApplyCancelStopPayReceipt.setApplyBankAccount(accountCApply.getBankAccount());
				accCardApplyCancelStopPayReceipt.setApplyBankName(accountCApply.getBank());
				Receipt receipt = new Receipt();
				receipt.setTypeCode(AccountCBussinessTypeEnum.accCardApplyCancelStopPay.getValue());
				receipt.setTypeChName(AccountCBussinessTypeEnum.accCardApplyCancelStopPay.getName());
				this.saveReceipt(receipt, accountCBussiness, accCardApplyCancelStopPayReceipt, customer);

				resultMap.put("result", "true");
				return resultMap;
			} else {
				resultMap.put("result", "false");
				resultMap.put("msg", "网上营业厅系统：申请解除止付失败");
				return resultMap;
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "申请解除止付失败");
			e.printStackTrace();
			throw new ApplicationException("申请解除止付失败");
		}
	}

	/**
	 * 检验是否可以做(手工)申请解除止付业务
	 */

	@Override
	public Integer checkStopPay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, String stopPayFlag) {
		Integer num = -1;//用-1表示异常
		AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
		if (accountCApply != null) {

			//查找客户的     申请解除止付或手工申请记录表且状态为0(申请中)的记录
			//stopPayFlag  0为申请解除止付 1为手工申请解除
			Map<String, Object> statemap = relieveStopPayDao.getState(accountCApply.getBankAccount(), stopPayFlag);
			if (statemap != null && statemap.get("STATE") != null) {
				num = 1;
			} else {
				//查找最近一条   铭鸿的   止付卡黑名单全量数据
				Map<String, Object> map = paymentCardBlacklistRecvDao.findStateMap(accountCApply.getBankAccount());
				if (map != null && map.get("FLAG") != null) {
					num = 2;
				} else {
					num = 4;//支付卡黑名单数据为空
				}
			}
		} else {
			num = 3;
		}
		return num;
	}


	/**
	 * 保存回执
	 *
	 * @param receipt
	 * 		回执主要信息
	 * @param accountCBussiness
	 * 		记账卡业务
	 * @param baseReceiptContent
	 * 		回执VO
	 * @param customer
	 * 		客户信息
	 */
	private void saveReceipt(Receipt receipt, AccountCBussiness accountCBussiness, BaseReceiptContent baseReceiptContent, Customer customer) {
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.accountC.getValue());
		receipt.setCreateTime(accountCBussiness.getTradeTime());
		receipt.setPlaceId(accountCBussiness.getPlaceId());
		receipt.setPlaceNo(accountCBussiness.getPlaceNo());
		receipt.setPlaceName(accountCBussiness.getPlaceName());
		receipt.setOperId(accountCBussiness.getOperId());
		receipt.setOperNo(accountCBussiness.getOperName());
		receipt.setOperName(accountCBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}


}

