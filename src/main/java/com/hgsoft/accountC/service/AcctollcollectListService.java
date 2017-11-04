package com.hgsoft.accountC.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AcctollcollectListDao;
import com.hgsoft.accountC.dao.AcctollcollectRecordDao;
import com.hgsoft.accountC.dao.HandPaymentDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AcctollcollectList;
import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.accountC.entity.HandPaymentDetail;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IAcctollcollectListService;
import com.hgsoft.accountC.serviceInterface.IRelieveStopPayService;
import com.hgsoft.clearInterface.dao.RelieveStopPayDao;
import com.hgsoft.clearInterface.dao.StopPayRelieveApplyDao;
import com.hgsoft.clearInterface.entity.AcOrder;
import com.hgsoft.clearInterface.entity.AcOrderResult;
import com.hgsoft.clearInterface.entity.HandlePayToll;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.serviceInterface.IHandlePayTollService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccCardMemberRechargeTollFeeReceipt;
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
import com.hgsoft.utils.StringUtil;

import net.sf.json.JSONObject;

@Service
public class AcctollcollectListService implements IAcctollcollectListService {
    private static Logger logger = Logger
            .getLogger(AcctollcollectListService.class.getName());
    @Resource
    SequenceUtil sequenceUtil;
    @Resource
    private AcctollcollectListDao acctollcollectListDao;
    @Resource
    private MainAccountInfoDao mainAccountInfoDao;
    @Resource
    private AccountCDao accountCDao;
    /*@Resource
    private ACinfoDao aCinfoDao;*/
    @Resource
    private AcctollcollectRecordDao acctollcollectRecordDao;
    @Resource
    private AccountCBussinessDao accountCBussinessDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private AcTradeDetailInfoDao acTradeDetailInfoDao;
    @Resource
    private ServiceWaterDao serviceWaterDao;
    @Resource
    private AccountCApplyDao accountCApplyDao;
    @Resource
    private HandPaymentDao handPaymentDao;
    @Resource
    private IUnifiedInterface unifiedInterfaceService;
    @Resource
    private IRelieveStopPayService relieveStopPayService;
    @Resource
    private IAccountCService accountCService;
    @Resource
    private StopPayRelieveApplyDao stopPayRelieveApplyDao;
    @Resource
    private RelieveStopPayDao relieveStopPayDao;
    @Resource
    private SubAccountInfoDao subAccountInfoDao;
    @Resource
    private IHandlePayTollService handlePayTollService;
    @Resource
    private ReceiptDao receiptDao;

//	@Resource
//	private IOrderService orderService;

    @Override
    public Pager findByPage(Pager pager, Customer customer,
                            AccountCApply accountCApply) {
        pager = acctollcollectListDao
                .findByPage(pager, customer, accountCApply);
        return pager;
    }

    @Override
    public AcctollcollectList findById(Long id) {
        // TODO Auto-generated method stub
        AcctollcollectList acctollcollectList = acctollcollectListDao
                .findById(id);
        return acctollcollectList;
    }
    
    /**
	 * @Descriptioqn: 手工缴纳通行费
	 * @param pager
	 * @param paymentCardBlacklistRecv
	 * @return
	 * @author lgm
	 * @date 2017年5月4日
	 * update:黄志炜
	 * 2017年10月12日17:04:23
	 */
	@Override
	public Pager findAcctollcollectList(Pager pager,PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		if (paymentCardBlacklistRecv!=null) {
			AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
			if (accountCApply!=null) {
				pager = handPaymentDao.findAcctollcollectList(pager,accountCApply.getBankAccount());
				return pager;
			}else {
				return null;
			}
		}else {
			return handPaymentDao.findAcctollcollectList(pager,null);
		}
	}
    
    /**
     * 列表详情页面
     */
	@Override
	public Pager listDetailPage(Pager pager, HandPayment handPayment) {
		return handPaymentDao.accTollPageDetail(pager, handPayment);
	}
    /**
     * 点击手工缴纳通行费方法
     */
	@Override
	public Map<String, Object> payDetail(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		Map<String,Object> resultmap = new HashMap<>();
		String message = null;
		AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
		
		if(accountCApply == null){
			resultmap.put("result", "false");
			resultmap.put("message", "该银行账号不存在");
			return resultmap;
		}
		SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
		if(subAccountInfo == null){
			resultmap.put("result", "false");
			resultmap.put("message", "数据异常：无法找到子账户信息");
			return resultmap;
		}

		
		//获得清算记帐卡账号最新下止付的时间(止付时间,滞纳金依据)
		Timestamp newStopPayTime=stopPayRelieveApplyDao.findNewStopPayMenuTime(accountCApply.getBankAccount());
		//止付时间不等于空的时候 就去做手工申请解除止付的业务
		if (newStopPayTime!=null) {
			resultmap.put("result", "false");
			resultmap.put("message", "请去办理手工申请解除止付业务!");
			return resultmap;
		}
		//获取手工缴纳通行费的详情页面
		else {
			String bankAccount = accountCApply.getBankAccount();
			String cardNos = "";
			String startTime = null;
			BigDecimal etcFeeAll = new BigDecimal(0);
			//BigDecimal lateFeeAll = new BigDecimal(0);
			BigDecimal etcFeeOne = new BigDecimal(0);
			
			//校验最近一次清算的日期，筛选此日期前的通行费数据
			String endTime = handPaymentDao.getRecentlyClearTime();
			
			//获得最后一次扣款时间(回盘时间,通行费依据)
			String newCurrentTime=stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
			if (newCurrentTime !=null) {
				 startTime = newCurrentTime;
			}
			
			//通行费流水表
			List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, endTime);
			//记帐卡转账中间数据 CSMS_ACCCARDVIREMENTMID
			List<Map<String, Object>> accTransferMidList = handPaymentDao.findAccTransferMid(bankAccount);
				
			message = handPaymentDao.checkTollAvailable(acEtcFeeList, accTransferMidList);
			if(StringUtil.isNotBlank(message)){
				resultmap.put("result", "false");
				resultmap.put("message", message);
				return resultmap;
			}
			
			for(Map<String,Object> map:acEtcFeeList){
				cardNos += "'"+map.get("CARDNO")+"',";
				etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总后的通行费
				//计算页面总数
				etcFeeAll = etcFeeAll.add(etcFeeOne);
			}
			
			if(StringUtil.isNotBlank(cardNos)){
				cardNos = cardNos.substring(0, cardNos.length()-1);
				resultmap.put("ETCFEE",etcFeeAll);
				//resultmap.put("LATEFEE",lateFeeAll);
				resultmap.put("PAGER",handPaymentDao.deductionDetail4PayToll(pager, cardNos,startTime,endTime));
			}
			//银行账号必须返回
			resultmap.put("BANKACCOUNT",bankAccount);
			resultmap.put("result", "true");
		}
		
		return resultmap;
	}
    
    /**
     * 9、手工缴纳通行费
     * @author HZW
     * update Time 2017年10月15日16:00:32
     */
    @Override
    public Map<String, Object> savePay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, Customer customer, AccountCBussiness accountCBussiness,Map<String,Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		String message = null;
		try {
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(customer.getId());
			AccountCApply accountCApply = accountCApplyDao.findAccountCApply(paymentCardBlacklistRecv);
			if(accountCApply!=null){
				resultMap.put("result", "true");
				String cardno = "";
				String startTime = null;
				Date newDate = new Date();//新建一个时间来作为基准时间
				String nowTime = DateUtil.formatDate(newDate, "yyyyMMddHHmmss");
				BigDecimal etcFeeAll = new BigDecimal(0);
				BigDecimal etcFeeOne = new BigDecimal(0);
				Date currentDate = new Date();//格式转换需要
				Date endDate = new Date();//格式转换需要
				DateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
				Long SEQ_HANDPAYMENT_NO = sequenceUtil.getSequenceLong("SEQ_HANDPAYMENT");//先获取总表ID
				
				//校验最近一次清算的日期，筛选此日期前的通行费数据
				String endTime = handPaymentDao.getRecentlyClearTime();
				
				//获得最后一次扣款时间(回盘时间,通行费依据)
				String newCurrentTime=stopPayRelieveApplyDao.findStopPayFeeStartDate(accountCApply.getBankAccount());
				if (newCurrentTime !=null) {
					 startTime = newCurrentTime;
				}
				//根据银行账号查找子账户id 减少查询通行费时的连表数量
				SubAccountInfo subAccountInfo = subAccountInfoDao.findByBankAccount(accountCApply.getBankAccount());
				//通行费流水表
				List<Map<String, Object>> acEtcFeeList = acTradeDetailInfoDao.findAcCardEtcFee(subAccountInfo.getId(), startTime, endTime);
				//记帐卡转账中间数据 CSMS_ACCCARDVIREMENTMID
				List<Map<String, Object>> accTransferMidList = handPaymentDao.findAccTransferMid(accountCApply.getBankAccount());
					
				message = handPaymentDao.checkTollAvailable(acEtcFeeList, accTransferMidList);
				if(StringUtil.isNotBlank(message)){
					resultMap.put("result", "false");
					resultMap.put("message", message);
					return resultMap;
				}

				List<HandPaymentDetail>handPaymentDetailList = new ArrayList<>();
				List<HandlePayToll>handlePayTollList = new ArrayList<>();
				List<AcTradeDetailInfo>acTradeDetailInfoList = new ArrayList<>();
				//根据子账户ID，回盘时间，当前时间去查询记帐卡交易明细表的交易记录
				List<AcTradeDetailInfo> acDetailInfoList = acTradeDetailInfoDao.findAcDetail(subAccountInfo.getId(),startTime,nowTime);
				for(AcTradeDetailInfo acOne :acDetailInfoList) {
					acOne.setPayFlag(1);//支付标识已支付
					acOne.setPayType(3);//手工缴纳
					acOne.setPayTime(newDate);//支付时间为当前
					acOne.setComGenTime(newDate);//业务时间：当前时间
					//先存储进list
					acTradeDetailInfoList.add(acOne);
				}
				
				for(Map<String,Object> map:acEtcFeeList){
					cardno =(String) map.get("CARDNO");
					etcFeeOne = (BigDecimal) map.get("REALTOLLALL");//单卡汇总的通行费
	
					//保存申请解除止付明细记录表
					HandPaymentDetail handPaymentDetail = new HandPaymentDetail();
					Long SEQ_CSMSHANDPAYMENT_DETAIL_NO = sequenceUtil.getSequenceLong("SEQ_CSMSHANDPAYMENT_DETAIL_NO");
					handPaymentDetail.setId(SEQ_CSMSHANDPAYMENT_DETAIL_NO);
					handPaymentDetail.setCardNo(cardno);
					handPaymentDetail.setHandPayMentId(SEQ_HANDPAYMENT_NO);
					handPaymentDetail.setBaccount(accountCApply.getBankAccount());
					handPaymentDetail.setPayTime(new Timestamp(newDate.getTime()));
					if (newCurrentTime != null) {
						try {
							 currentDate = sdf.parse(newCurrentTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						handPaymentDetail.setStartTime(new Timestamp(currentDate.getTime()));
					}else {
						handPaymentDetail.setStartTime(null);
					}
					if (endTime != null) {
						try {
							endDate = sdf.parse(endTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						handPaymentDetail.setEndTime(new Timestamp(endDate.getTime()));
					}else {
						handPaymentDetail.setEndTime(null);
					}
					handPaymentDetail.setEtcFee(etcFeeOne);
					handPaymentDetail.setOperId(accountCApply.getOperId());
					handPaymentDetail.setOperNo(accountCApply.getOperNo());
					handPaymentDetail.setOperName(accountCApply.getOperName());
					handPaymentDetail.setPlaceId(accountCApply.getPlaceId());
					handPaymentDetail.setPlaceNo(accountCApply.getPlaceNo());
					handPaymentDetail.setPlaceName(accountCApply.getPlaceName());
					handPaymentDetail.setRemark("手工缴纳通行费明细");
					
					//先存储进list
					handPaymentDetailList.add(handPaymentDetail);
	
					//存储清算数据
					HandlePayToll handlePayToll = new HandlePayToll();
					Long SEQ_TBPAYTOLLBYHANDSEND_NO = sequenceUtil.getSequenceLong("SEQ_TBPAYTOLLBYHANDSEND_NO");
					handlePayToll.setId(SEQ_TBPAYTOLLBYHANDSEND_NO);
					handlePayToll.setBaccount(accountCApply.getBankAccount());
					handlePayToll.setCardNo(cardno);
					handlePayToll.setHandPayTime(new Timestamp(newDate.getTime()));
					handlePayToll.setHandPayFee(etcFeeOne);
					handlePayToll.setPayAccount(null);//20170811修改 经确认设置为空
					handlePayToll.setPayFlag("5");//20170811修改 经确认设置为空
					handlePayToll.setBankFlag("0");//TODO
					handlePayToll.setRemark("手工缴纳通行费");
					handlePayToll.setUpdateTime(new Timestamp(newDate.getTime()));
					String boardListNo = String.valueOf((new Date()).getTime())+"1019";//手工缴纳通行费是1019
					handlePayToll.setBoardListNo(Long.valueOf(boardListNo));
					//先存储进list
					handlePayTollList.add(handlePayToll);
	
					//把总的通行费和滞纳金计算好
					etcFeeAll = etcFeeAll.add(etcFeeOne);
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
				unifiedParam.setLateAndEtcFee(etcFeeAll);//通行费总和
				unifiedParam.setType("19");//手工缴纳通行费
				unifiedParam.setOperId(accountCBussiness.getOperId());
				unifiedParam.setPlaceId(accountCBussiness.getPlaceId());
				unifiedParam.setOperName(accountCBussiness.getOperName());
				unifiedParam.setOperNo(accountCBussiness.getOperNo());
				unifiedParam.setPlaceName(accountCBussiness.getPlaceName());
				unifiedParam.setPlaceNo(accountCBussiness.getPlaceNo());
				//判断账户资金是否充足
				if (!unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					resultMap.put("result", "false");
					resultMap.put("message", "处理失败!账户可用余额不足");
					return resultMap;
				}

				//保存手工缴纳通行费总表
				HandPayment handPayment = new HandPayment();
				handPayment.setId(SEQ_HANDPAYMENT_NO);
				handPayment.setBaccount(accountCApply.getBankAccount());
				handPayment.setPayTime(new Timestamp(newDate.getTime()));
				if (newCurrentTime != null) {
					try {
						 currentDate = sdf.parse(newCurrentTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					handPayment.setStartTime(new Timestamp(currentDate.getTime()));
				}else {
					handPayment.setStartTime(null);
				}
				if (endTime != null) {
					try {
						endDate = sdf.parse(endTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					handPayment.setEndTime(new Timestamp(endDate.getTime()));
				}else {
					handPayment.setEndTime(null);
				}
				handPayment.setFlag("0");//标识为正常
				handPayment.setEtcFee(etcFeeAll);
				handPayment.setOperId(accountCApply.getOperId());
				handPayment.setOperNo(accountCApply.getOperNo());
				handPayment.setOperName(accountCApply.getOperName());
				handPayment.setPlaceId(accountCApply.getPlaceId());
				handPayment.setPlaceNo(accountCApply.getPlaceNo());
				handPayment.setPlaceName(accountCApply.getPlaceName());
				handPayment.setRemark("手工缴纳通行费");
				handPaymentDao.save(handPayment);
				//保存明细表,清算数据,更近记帐卡交易明细
				handPaymentDao.batchSaveDetail(handPaymentDetailList);
				handPaymentDao.batchSaveHandlePayToll(handlePayTollList);
				acTradeDetailInfoDao.batchUpdatePayInfo(acTradeDetailInfoList);

				//调整的客服流水
				ServiceWater serviceWater = new ServiceWater();
				Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

				serviceWater.setId(serviceWater_id);
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
				serviceWater.setSerType("223");//223手工缴纳通行费

				if(accountCApply!=null)serviceWater.setBankAccount(accountCApply.getBankAccount());
				if(accountCApply!=null)serviceWater.setBankNo(accountCApply.getObaNo());
				serviceWater.setAccountCBussinessId(accountCBussiness.getId());
				serviceWater.setOperId(accountCBussiness.getOperId());
				serviceWater.setOperName(accountCBussiness.getOperName());
				serviceWater.setOperNo(accountCBussiness.getOperNo());
				serviceWater.setPlaceId(accountCBussiness.getPlaceId());
				serviceWater.setPlaceName(accountCBussiness.getPlaceName());
				serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
				serviceWater.setRemark("自营客服系统：手工缴纳通行费");
				serviceWater.setOperTime(newDate);
				serviceWaterDao.save(serviceWater);
				
				//增加业务记录表，需要一个accountCBussiness对象 CSMS_AccountC_bussiness 在前面new
				BigDecimal bid = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
				accountCBussiness.setId(Long.parseLong(bid.toString()));
				accountCBussiness.setUserId(customer.getId());
				accountCBussiness.setState("12");//类型为：手工缴纳通行费
				accountCBussiness.setTradeTime(newDate);
				accountCBussiness.setBusinessId(handPayment.getId());//手工缴纳通行费的id
				accountCBussinessDao.save(accountCBussiness);

              //记帐卡手工缴纳通行费回执
              AccCardMemberRechargeTollFeeReceipt rechargeTollFeeReceipt = new AccCardMemberRechargeTollFeeReceipt();
              rechargeTollFeeReceipt.setTitle("记帐卡手工缴纳通行费回执");
              rechargeTollFeeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
              rechargeTollFeeReceipt.setApplyAccName(accountCApply.getAccName());
              rechargeTollFeeReceipt.setApplyBankAccount(accountCApply.getBankAccount());
              rechargeTollFeeReceipt.setApplyBankName(accountCApply.getBank());
              rechargeTollFeeReceipt.setRelieveETCFee(NumberUtil.get2Decimal(handPayment.getEtcFee()));//总通行费
              Receipt receipt = new Receipt();
              receipt.setTypeCode(AccountCBussinessTypeEnum.accCardMemberRechargeTollFee.getValue());
              receipt.setTypeChName(AccountCBussinessTypeEnum.accCardMemberRechargeTollFee.getName());
              this.saveReceipt(receipt,accountCBussiness,rechargeTollFeeReceipt,customer);
			}
			return resultMap;
		} catch (Exception e) {
			logger.error(e.getMessage()+" 手工缴纳通行费失败");
			e.printStackTrace();
			throw new ApplicationException("手工缴纳通行费失败");
		}
		
		
    }


    /**
     * 保存回执
     * @param receipt 回执主要信息
     * @param accountCBussiness 记账卡业务
     * @param baseReceiptContent 回执VO
     * @param customer 客户信息
     */
    private void saveReceipt(Receipt receipt, AccountCBussiness accountCBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
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
