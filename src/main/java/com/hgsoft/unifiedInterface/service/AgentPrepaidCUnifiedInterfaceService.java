package com.hgsoft.unifiedInterface.service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.clearInterface.dao.ScreturnSendDao;
import com.hgsoft.clearInterface.dao.StoreCardRechargeDao;
import com.hgsoft.clearInterface.dao.TbScreturnSendDao;
import com.hgsoft.clearInterface.entity.StoreCardRecharge;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.*;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.invoice.dao.AddBillDao;
import com.hgsoft.invoice.entity.AddBill;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardImRegisterCorrectReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardImRegisterReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardMemberRechargeReceipt;
import com.hgsoft.other.vo.receiptContent.prepaidC.PreCardRechargeCorrectReceipt;
import com.hgsoft.prepaidC.dao.*;
import com.hgsoft.prepaidC.entity.*;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.system.dao.ServiceFundMonitorDao;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceFundMonitor;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.serviceInterface.IServiceFundMonitorService;
import com.hgsoft.utils.NumberUtil;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.awt.datatransfer.DataTransferer;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 储值卡业务接口类
 * 
 * @author zxy 2016年1月25日14:08:36
 */
@Service
public class AgentPrepaidCUnifiedInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(AgentPrepaidCUnifiedInterfaceService.class);
	//private static Logger logger = Logger.getLogger(AgentPrepaidCUnifiedInterfaceService.class.getName());

	@Resource
	private CustomerDao customerDao;

	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;

	@Resource
	private ReturnFeeDao returnFeeDao;


	@Resource
	private IPrepaidCBussinessService prepaidCBussinessService;

	@Resource
	SequenceUtil sequenceUtil;

	@Resource
	private ScreturnSendDao screturnSendDao;
	@Resource
	private TbScreturnSendDao tbScreturnSendDao;
	@Resource
	private ScAddDao scAddDao;
	@Resource
	private ServiceFundMonitorDao fundMonitorDao;
	@Resource
	private StoreCardRechargeDao storeCardRechargeDao;
	@Resource
	private IPrepaidCService prepaidCService;
	@Resource
	private IServiceFundMonitorService serviceFundMonitorService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;
	@Resource
	private InvoiceChangeFlowDao invoiceChangeFlowDao;
	@Resource
	private AddBillDao addBillDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private ICardObuService cardObuService;



	public boolean saveRecharge(PrepaidCBussiness prepaidCBussiness, AddRegDetail addRegDetail,
			MainAccountInfo mainAccountInfo, Integer type, List<ReturnFee> returnFeeList,RechargeInfo newRechargeInfo) {
		try {
			if (prepaidCBussiness.getRealprice() == null) {
				prepaidCBussiness.setRealprice(BigDecimal.ZERO);
			}
			if (prepaidCBussiness.getTransferSum() == null) {
				prepaidCBussiness.setTransferSum(BigDecimal.ZERO);
			}
			if (prepaidCBussiness.getReturnMoney() == null) {
				prepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
			}

			BigDecimal returnMoney = BigDecimal.ZERO;
			if (returnFeeList != null) {
				for (ReturnFee returnFee : returnFeeList) {
					returnMoney = returnMoney.add(returnFee.getReturnFee());
				}
			}
			//1,请求清算接口（参数：卡号和金额）锁定转移金额（成功走下一步，失败返回）
//-----------------------------------------------------------------------------------
			BigDecimal transfee = BigDecimal.ZERO;
			List<Map<String, Object>> cards = null;//prepaidCService.findCards(prepaidCBussiness.getCardno());
			if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
				cards = prepaidCService.findCards(prepaidCBussiness.getCardno());
				if (cards != null && !cards.isEmpty()) {
					Map<String, Object> map = cards.get(0);
					BigDecimal amt = (BigDecimal)map.get("CARDAMT");
					transfee = transfee.add(amt);
				}
			}

			//判断页面传过来的金额与查出金额是否一致
			if (prepaidCBussiness.getTransferSum().compareTo(transfee) != 0) {
				logger.warn("系统转移金额[{}]与申请的转移金额[{}]不一致", transfee, prepaidCBussiness.getTransferSum());
				return false;
			}
			if (prepaidCBussiness.getReturnMoney().compareTo(returnMoney) != 0) {
				logger.warn("系统回退金额[{}]与申请的回退金额[{}]不一致", returnMoney, prepaidCBussiness.getReturnMoney());
				return false;
			}
			BigDecimal totalMoney = prepaidCBussiness.getRealprice().add(transfee).add(returnMoney);
			//判断金额是否大于0，总充值金额是否大于0
			if (prepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) < 0
					|| transfee.compareTo(BigDecimal.ZERO) < 0
					|| returnMoney.compareTo(BigDecimal.ZERO) < 0
					|| totalMoney.compareTo(BigDecimal.ZERO) <= 0
					|| totalMoney.add(prepaidCBussiness.getBeforebalance()).compareTo(new BigDecimal("3000000")) > 0) {
				logger.error("卡余额[{}],充值金额非法[{}]、[{}]、[{}]", prepaidCBussiness.getBeforebalance(), prepaidCBussiness.getRealprice(), transfee, returnMoney);
				return false;
			}
//-----------------------------------------------------------------------------------------------------

			if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())
					&& prepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) != 0) {

				Long operPlaceId= prepaidCBussiness.getPlaceid();
				ServiceFundMonitor fundMonitor = serviceFundMonitorService.findFundMonitorRechargeByCustomPoint(operPlaceId);
				//判断已用金额+充值金额+回退金额+转移金额是否超过充值上限
				if (fundMonitor != null) {
					if (fundMonitor.getUseFund().add(prepaidCBussiness.getRealprice()).compareTo(fundMonitor.getFundMax())>0) {
						logger.error("网点[{}]超过授权资金上限，无法充值", operPlaceId);
						throw new ApplicationException("超过授权资金上限，不能进行充值");
					}
					int ret = serviceFundMonitorService.updateFundMonitorRechargeByCustomPoint(operPlaceId, prepaidCBussiness.getRealprice());
					if (ret != 1) {
						logger.error("网点[{}]授权资金发生变更，需重试", operPlaceId);
						throw new ApplicationException("授权资金发生变更，需重试");
					}
				}
			} else if (!PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
				logger.error("业务类型[{}]非法", prepaidCBussiness.getState());
				throw new ApplicationException("业务类型非法");
			}

			prepaidCBussiness.setBusinessId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
			prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness);

			if (StringUtils.isBlank(prepaidCBussiness.getCardno())) {
				logger.error("卡号为空[{}]", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号为空");
			}
			PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());
			if (prepaidC == null) {
				logger.error("卡号[{}]未发行", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号未发行");
			} else if (!PrepaidCardStateEnum.nomal.getIndex().equals(prepaidC.getState())) {
				logger.error("卡号[{}]状态非正常状态[{}]", prepaidCBussiness.getCardno(), PrepaidCardStateEnum.getName(prepaidC.getState()));
				throw new ApplicationException("卡状态不是正常的");
			}

			Customer customer = customerService.getCustomerByPrepaidCardNo(prepaidCBussiness.getCardno());
			if (customer == null) {
				logger.error("卡号[{}]没有对应的正常用户", prepaidCBussiness.getCardno());
				throw new ApplicationException("卡号没有对应的正常用户");
			}

			//锁定回退资金
			if (returnFeeList != null) {
				for (ReturnFee returnFee : returnFeeList) {
					if (returnFeeDao.updateLockState(prepaidCBussiness.getId(), prepaidCBussiness.getTradetime(), returnFee.getId()) != 1) {
						throw new ApplicationException("回退金额数据发生变更，请重新操作");
					}
				}
			}
			//锁定转移资金
			if (cards != null && !cards.isEmpty() && prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
				Map<String, Object> map = cards.get(0);
				int ret = dbasCardFlowDao.updateLockEndFlagById(prepaidCBussiness.getId(), (BigDecimal) map.get("CARDAMT"), prepaidCBussiness.getTradetime(), Long.parseLong(map.get("ID").toString()));
				if (ret != 1) {
					throw new ApplicationException("转移金额数据发生变更，请重新操作");
				}
			}
			//给清算数据
			scAddDao.saveScAddReqByBussiness(prepaidCBussiness, "01", ScAddReqPaychannelEnum.AGENT.getValue());
			scAddDao.saveScAddSendByBussiness(prepaidCBussiness, "01");//清算数据

			scAddDao.saveScAddSureByBussiness(prepaidCBussiness, "01", ScAddReqPaychannelEnum.AGENT.getValue());
			scAddDao.saveScAddSureSendByBussiness(prepaidCBussiness, "01");//清算数据

			if (!PrepaidCFristRechargeEnum.NOT_FIRST.getValue().equals(prepaidC.getFirstRecharge())) {
				prepaidCService.updateFirstRecharge(PrepaidCFristRechargeEnum.NOT_FIRST.getValue(), prepaidC.getId());
			}

			return true;
		} catch (ApplicationException e) {
			logger.error("储值卡充值失败", e);// 记录删除日志
			throw new ApplicationException("储值卡充值失败");
		}
	}

	private boolean isToday(Date date) {
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		return (day.format(new Date()).equals(day.format(date)));
	}

	public boolean saveReversal(MainAccountInfo mainAccountInfo, PrepaidCBussiness prepaidCBussiness,
			PrepaidCBussiness oldPrepaidCBussiness) {

		oldPrepaidCBussiness = prepaidCBussinessService.findById(oldPrepaidCBussiness.getId());

		if (oldPrepaidCBussiness == null
				|| !PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(oldPrepaidCBussiness.getState())
				|| !PrepaidCardBussinessTradeStateEnum.success.getValue().equals(oldPrepaidCBussiness.getTradestate())
				|| !oldPrepaidCBussiness.getPlaceNo().equals(prepaidCBussiness.getPlaceNo())) {
			logger.warn("储值卡业务记录[{}]不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		} else if (oldPrepaidCBussiness.getBalance().compareTo(prepaidCBussiness.getBeforebalance()) != 0
				|| !oldPrepaidCBussiness.getOnlinetradeno().equals(prepaidCBussiness.getOnlinetradeno())
				|| !oldPrepaidCBussiness.getOfflinetradeno().equals(prepaidCBussiness.getOfflinetradeno())) {
			logger.warn("卡内信息联机[{}]、脱机[{}]、金额[{}]与储值卡业务记录[{}]不一致，不可以冲正",
					prepaidCBussiness.getOnlinetradeno(), prepaidCBussiness.getOfflinetradeno(),
					prepaidCBussiness.getBeforebalance(), oldPrepaidCBussiness.getId());
			return false;
		} else if (prepaidCBussiness.getRealprice().compareTo(oldPrepaidCBussiness.getRealprice()) != 0) {
			logger.warn("前端冲正金额[{}]与系统可冲正金额[{}]不一致，不可以冲正",
					prepaidCBussiness.getRealprice(), oldPrepaidCBussiness.getRealprice());
			return false;
		} else if (oldPrepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) == 0) {
			logger.warn("储值卡业务记录[{}]可冲正金额为0，不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		} else if (!isToday(oldPrepaidCBussiness.getTradetime())) {
			logger.warn("储值卡业务记录[{}]不是当天，不可以冲正", oldPrepaidCBussiness.getId());
			return false;
		}

		prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue());
		prepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
		prepaidCBussiness.setTransferSum(BigDecimal.ZERO);
		prepaidCBussiness.setTradestate(PrepaidCardBussinessTradeStateEnum.save.getValue());
		prepaidCBussiness.setCardno(oldPrepaidCBussiness.getCardno());


		/**
		 * 1、增加储值卡 业务记录 2、修改充值业务记录发票打印状态 3、增加客服流水 4、增加总账户可用余额
		 */
		try {
			PrepaidCBussiness tempBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNo(oldPrepaidCBussiness.getCardno());
			if (!tempBusiness.getId().equals(oldPrepaidCBussiness.getId())) {
				throw new ApplicationException("储值卡业务记录[" + oldPrepaidCBussiness.getId() + "]不是最新的充值记录，不可以冲正");
			} else if (!PrepaidCardBussinessTradeStateEnum.success.getValue().equals(tempBusiness.getTradestate())) {
				throw new ApplicationException("储值卡业务记录[" + tempBusiness.getId() + "]交易状态不是充值成功，不可以冲正");
			} else if (!PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(tempBusiness.getState())
					|| "1".equals(tempBusiness.getIsDaySet())) {
				throw new ApplicationException("储值卡业务记录[" + oldPrepaidCBussiness.getId() + "]类型不是可以冲正的");
			}

			if (tempBusiness.getRealprice().compareTo(oldPrepaidCBussiness.getRealprice()) != 0) {
				throw new ApplicationException("储值卡业务记录[" + oldPrepaidCBussiness.getId() + "]发生变更，不可以冲正");
			}

			PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(tempBusiness.getCardno());
			if (prepaidC == null) {
				throw new ApplicationException("储值卡[" + tempBusiness.getCardno() + "]不存在，不可以冲正");
			} else if (!PrepaidCardStateEnum.nomal.getIndex().equals(prepaidC.getState())) {
				logger.error("卡号[{}]状态非正常状态[{}]", prepaidCBussiness.getCardno(), PrepaidCardStateEnum.getName(prepaidC.getState()));
				throw new ApplicationException("卡状态不是正常的");
			}

			Customer customer = customerService.findById(prepaidC.getCustomerID());
			if (customer == null || !CustomerStateEnum.normal.getValue().equals(customer.getState())) {
				throw new ApplicationException("储值卡[" + oldPrepaidCBussiness.getCardno() + "]不存在正常用户，不可以冲正");
			}
			/*DarkList darkList = darkListService.findByCardNo(prepaidC.getCardNo());
			if(darkList!=null){
				throw new ApplicationException("储值卡["+tempBusiness.getCardno()+"]在黑名单，不可以冲正");
			}*/

			/* 储值卡业务记录 */
			prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().negate());

			prepaidCBussiness.setBusinessId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
			prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness);

			//给清算数据
			scAddDao.saveScAddReqByBussiness(prepaidCBussiness, "02", ScAddReqPaychannelEnum.AGENT.getValue());
			scAddDao.saveScAddSendByBussiness(prepaidCBussiness, "02");
			scAddDao.saveScAddSureByBussiness(prepaidCBussiness, "02", ScAddReqPaychannelEnum.AGENT.getValue());
			scAddDao.saveScAddSureSendByBussiness(prepaidCBussiness, "02");

			return true;

		} catch (Exception e) {
			//logger.error("充值冲正失败", e);
			throw new ApplicationException("充值冲正失败", e);
		}
	}

	public String tenTacToHexTac(String tenTac) {
		try {
			String tacHex = Long.toHexString(Long.valueOf(tenTac)).toUpperCase();
			int diff = 8-tacHex.length();
			if (diff < 0) {
				logger.error("10进制tac码[{}]的16进制[{}]超过8位，非法", tenTac, tacHex);
				return null;
			}
			StringBuilder tacHexBuilder = new StringBuilder();
			for (int i=0; i<diff; i++) {
				tacHexBuilder.append("0");
			}
			tacHexBuilder.append(tacHex);
			return tacHexBuilder.toString();
		} catch (Exception e) {
			logger.error("10进制tac码[{}]无法转16进制", tenTac);
		}
		return null;
	}

	public void updateRecharge(PrepaidCBussiness prepaidCBussiness, AddRegDetail addRegDetail,MainAccountInfo mainAccountInfo, List<ReturnFee> returnFeeList, Map<String,Object> params) {
		/**
		 * 1、修改储值卡业务记录状态 2、修改快速充值登记记录状态 3、更改总帐户信息冻结金额
		 */
		try {
			if (StringUtils.isNotBlank(prepaidCBussiness.getTac())) {
				prepaidCBussiness.setTac(tenTacToHexTac(prepaidCBussiness.getTac()));
			}

			//完成储值卡业务记录
			if (prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
				throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
			}

			Date addSureTime = new Date(); //临时存储充值确认时间，后边使用

			//完成回退金额
			if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
				returnFeeDao.updateRechargeSuccessState(addSureTime, prepaidCBussiness.getId());
			}
			//完成转移金额
			if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
				dbasCardFlowDao.updateRechargedEndFlagByEndServiceId(prepaidCBussiness.getId());

				DbasCardFlow dbasCardFlow = dbasCardFlowDao.findByEndServiceId(prepaidCBussiness.getId());
				if (dbasCardFlow == null) {
					logger.error("储值卡业务记录[{}]不存在对应的资金转移记录", prepaidCBussiness.getId());
					throw new RuntimeException("不存在对应的资金转移记录");
				}
				String remark = "新卡["+dbasCardFlow.getNewCardNo()+"]充值旧卡金额:" + dbasCardFlow.getCardAmt();
				cardObuService.saveCardBalance(dbasCardFlow.getOldCardNo(), prepaidCBussiness.getTransferSum(),
						BigDecimal.ZERO, addSureTime, null, remark, new Date());
			}
//----------------------------------------------------------------------------------------------------
			//添加充值账单
			PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());
			saveAddBill(prepaidC, prepaidCBussiness,
					StringUtils.isNotBlank(prepaidCBussiness.getReason())
							&& prepaidCBussiness.getReason().equals("1"));

			//给清算数据，插入后半部分数据
			ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(prepaidCBussiness, "01", addSureTime, "3", ScAddSureConfirmTypeEnum.normal.getValue());
			//写给原清算的数据
			scAddDao.updateScAddSureSendByBussiness(prepaidCBussiness, "01", addSureTime, "3");

			//ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("01", prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());

			//给清算数据    充值	给铭鸿
			saveStoreCardRecharge(prepaidCBussiness, scAddSure);
			//给清算数据    资金转移		给铭鸿
			saveStoreCardRecharge4Transfer(prepaidCBussiness, scAddSure);

			Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if (customer != null) {
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
			}
			serviceWater.setCardNo(prepaidCBussiness.getCardno());
			if (PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState())) {
				//人工充值
				serviceWater.setSerType(ServiceWaterSerType.manualRecharge.getValue());//储值卡人工充值
				serviceWater.setRemark(ServiceWaterSerType.manualRecharge.getName());
			} else if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) {
				//充值登记充值
				serviceWater.setSerType(ServiceWaterSerType.addRegRecharge.getValue());//储值卡充值登记充值
				serviceWater.setRemark(ServiceWaterSerType.addRegRecharge.getName());
			} else if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
				//直充
				serviceWater.setSerType(ServiceWaterSerType.noLoginRecharge.getValue());//储值卡直充充值
				serviceWater.setRemark(ServiceWaterSerType.noLoginRecharge.getName());
			}
			serviceWater.setAmt(prepaidCBussiness.getRealprice());//应收金额
			serviceWater.setAulAmt(prepaidCBussiness.getRealprice());//实收金额
			//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
			serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());

			serviceWater.setFlowState(ServiceWaterFlowStateEnum.complete.getValue());//完成

			serviceWater.setOperId(prepaidCBussiness.getOperid());
			serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
			serviceWater.setOperNo(prepaidCBussiness.getOperNo());
			serviceWater.setOperName(prepaidCBussiness.getOperName());
			serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
			serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
			serviceWater.setOperTime(scAddSure.getTimeReq());

			serviceWaterDao.save(serviceWater);

			//储值卡充值回执(代理点充值不经过账号，这里暂用客户人工充值的回执VO)
			PreCardMemberRechargeReceipt preCardMemberRechargeReceipt = new PreCardMemberRechargeReceipt();
			preCardMemberRechargeReceipt.setTitle("储值卡充值回执");
			preCardMemberRechargeReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			preCardMemberRechargeReceipt.setPreCardNo(prepaidCBussiness.getCardno());
			preCardMemberRechargeReceipt.setPreCardBeforebalance(NumberUtil.get2Decimal(prepaidCBussiness.getBeforebalance().doubleValue()*0.01));
			preCardMemberRechargeReceipt.setPreCardRealPrice(NumberUtil.get2Decimal(prepaidCBussiness.getRealprice().doubleValue()*0.01));
			preCardMemberRechargeReceipt.setMainCountPreferentialBalance(NumberUtil.get2Decimal(prepaidCBussiness.getReturnMoney().doubleValue()*0.01));
			preCardMemberRechargeReceipt.setPreCardTransferSum(NumberUtil.get2Decimal(prepaidCBussiness.getTransferSum().doubleValue()*0.01));
			preCardMemberRechargeReceipt.setPreCardBalance(NumberUtil.get2Decimal(prepaidCBussiness.getBalance().doubleValue()*0.01));
			Receipt receipt = new Receipt();
			receipt.setTypeCode(prepaidCBussiness.getState());
			receipt.setTypeChName("储值卡充值");	//配合页面，代理点没有人工或直充的概念，直接写充值
			receipt.setBusinessId(prepaidCBussiness.getId());
			receipt.setCardNo(preCardMemberRechargeReceipt.getPreCardNo());
			this.saveReceipt(receipt,prepaidCBussiness,preCardMemberRechargeReceipt,customer);


		} catch (ApplicationException e) {
			logger.error("储值卡充值失败", e);// 记录删除日志
			throw new ApplicationException("储值卡充值失败");
		}
	}

	public void saveStoreCardRecharge(PrepaidCBussiness prepaidCBussiness, ScAddSure scAddSure) {
		if (prepaidCBussiness.getRealprice().add(prepaidCBussiness.getReturnMoney()).compareTo(BigDecimal.ZERO) != 0) {
            StoreCardRecharge storeCardRecharge = new StoreCardRecharge();
            storeCardRecharge.setCardCode(prepaidCBussiness.getCardno());//储值卡卡号
            storeCardRecharge.setTimeReq(prepaidCBussiness.getTradetime());//
            storeCardRecharge.setDealtype("01");//交易类型:充值
            storeCardRecharge.setMoneySur(prepaidCBussiness.getRealprice());//充值金额
            storeCardRecharge.setReturnMoneySur(prepaidCBussiness.getReturnMoney());//
            storeCardRecharge.setBalReq(prepaidCBussiness.getBeforebalance());//充值前余额
            storeCardRecharge.setBalSur(prepaidCBussiness.getBalance());//充值后余额
            storeCardRecharge.setLinkReq(scAddSure.getOnlineTradenoReq().toString());//充值前联机号
            storeCardRecharge.setLinkSur(prepaidCBussiness.getOnlinetradeno());//充值后联机号
            storeCardRecharge.setTrReq(scAddSure.getOfflineTradenoReq().toString());//充值前脱机号
            storeCardRecharge.setTrSur(prepaidCBussiness.getOfflinetradeno());//充值后脱机号
			storeCardRecharge.setTac(prepaidCBussiness.getTac());
			storeCardRecharge.setTimeConfirm(scAddSure.getTimeSur());
            storeCardRecharge.setUpdatetime(new Date());//
            Long id = sequenceUtil.getSequenceLong("SEQ_tbstorecardrechargesend_NO");
            storeCardRecharge.setId(id);//
            storeCardRechargeDao.savePrepaidCCharge(storeCardRecharge);//
        }
	}

	public void saveStoreCardRecharge4Transfer(PrepaidCBussiness prepaidCBussiness, ScAddSure scAddSure) {
		if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
			StoreCardRecharge storeCardRecharge = new StoreCardRecharge();
			storeCardRecharge.setCardCode(prepaidCBussiness.getCardno());//储值卡卡号
			storeCardRecharge.setTimeReq(prepaidCBussiness.getTradetime());//
			storeCardRecharge.setDealtype("03");//交易类型:资金转移
			storeCardRecharge.setMoneySur(prepaidCBussiness.getTransferSum());//
			storeCardRecharge.setReturnMoneySur(BigDecimal.ZERO);//
			storeCardRecharge.setBalReq(prepaidCBussiness.getBeforebalance());//充值前余额
			storeCardRecharge.setBalSur(prepaidCBussiness.getBalance());//充值后余额
			storeCardRecharge.setLinkReq(scAddSure.getOnlineTradenoReq().toString());//充值前联机号
			storeCardRecharge.setLinkSur(prepaidCBussiness.getOnlinetradeno());//充值后联机号
			storeCardRecharge.setTrReq(scAddSure.getOfflineTradenoReq().toString());//充值前脱机号
			storeCardRecharge.setTrSur(prepaidCBussiness.getOfflinetradeno());//充值后脱机号
			storeCardRecharge.setTac(prepaidCBussiness.getTac());
			storeCardRecharge.setTimeConfirm(scAddSure.getTimeSur());
			storeCardRecharge.setUpdatetime(new Date());//
			Long id = sequenceUtil.getSequenceLong("SEQ_tbstorecardrechargesend_NO");
			storeCardRecharge.setId(id);
			storeCardRechargeDao.savePrepaidCCharge(storeCardRecharge);//
		}
	}

	public void saveAddBill(PrepaidC prepaidC, PrepaidCBussiness prepaidCBussiness, boolean isMerge) {
		if("1".equals(prepaidC.getInvoicePrint())){
			//判断是否有发票类型变更
			InvoiceChangeFlow invoiceChangeFlow = invoiceChangeFlowDao.findByCardNo(prepaidCBussiness.getCardno());
			if(invoiceChangeFlow!=null && "1".equals(invoiceChangeFlow.getState())){
				if(isMerge){
					//添加充值账单
					AddBill addBill = new AddBill();
					Long addbillId = sequenceUtil.getSequenceLong("seq_csmsaddbill_no");
					addBill.setCustomerID(prepaidCBussiness.getUserid());
					addBill.setId(addbillId);
					addBill.setCardNo(prepaidCBussiness.getCardno());
					addBill.setAddFee(prepaidCBussiness.getRealprice());//充值金额
					addBill.setSysCost(invoiceChangeFlow.getSyscost()); //系统余额
					addBill.setRealCost(invoiceChangeFlow.getRealcost());//优惠金额
					addBill.setTransferCost(invoiceChangeFlow.getRealcost()); //资金转移金额
					addBill.setTransferRealCost(invoiceChangeFlow.getTranferrealcost());//资金转移优惠金额
					addBill.setAddTime(prepaidCBussiness.getTradetime());
					addBill.setState(AddBillStateEnum.notPrint.getValue());//未打印
					addBill.setPrintNum(0);
					addBillDao.save(addBill);
					invoiceChangeFlow.setState(InvoiceChangeFlowStateEnum.merge.getValue());//合并打印
				}else{
					//添加充值账单
					AddBill addBill1 = new AddBill();
					Long addbillId1 = sequenceUtil.getSequenceLong("seq_csmsaddbill_no");
					addBill1.setCustomerID(prepaidCBussiness.getUserid());
					addBill1.setId(addbillId1);
					addBill1.setCardNo(prepaidCBussiness.getCardno());
					addBill1.setAddFee(prepaidCBussiness.getRealprice());//充值金额
					addBill1.setSysCost(BigDecimal.ZERO); //系统余额
					addBill1.setRealCost(BigDecimal.ZERO);
					addBill1.setTransferCost(BigDecimal.ZERO);
					addBill1.setTransferRealCost(BigDecimal.ZERO);//资金转移优惠金额
					addBill1.setState(AddBillStateEnum.notPrint.getValue());//未打印
					addBill1.setPrintNum(0);
					addBill1.setAddTime(prepaidCBussiness.getTradetime());
					addBillDao.save(addBill1);

					AddBill addBill2 = new AddBill();
					Long addbillId2 = sequenceUtil.getSequenceLong("seq_csmsaddbill_no");
					addBill2.setCustomerID(prepaidCBussiness.getUserid());
					addBill2.setId(addbillId2);
					addBill2.setCardNo(prepaidCBussiness.getCardno());
					addBill2.setAddFee(new BigDecimal("0"));//充值金额
					addBill2.setSysCost(invoiceChangeFlow.getSyscost());
					addBill2.setRealCost(invoiceChangeFlow.getRealcost());//优惠金额
					addBill2.setTransferCost(invoiceChangeFlow.getTranfercost());
					addBill2.setTransferRealCost(invoiceChangeFlow.getTranferrealcost());//资金转移优惠金额
					addBill2.setAddTime(prepaidCBussiness.getTradetime());
					addBill2.setState(AddBillStateEnum.notPrint.getValue());//未打印
					addBill2.setPrintNum(0);
					addBillDao.save(addBill2);
					invoiceChangeFlow.setState(InvoiceChangeFlowStateEnum.single.getValue());//不合并
				}
				invoiceChangeFlowDao.update(invoiceChangeFlow);
			}else{
				AddBill addBill = new AddBill();
				Long addbillId = sequenceUtil.getSequenceLong("seq_csmsaddbill_no");
				addBill.setId(addbillId);
				addBill.setCardNo(prepaidCBussiness.getCardno());
				addBill.setAddFee(prepaidCBussiness.getRealprice());//充值金额
				addBill.setCustomerID(prepaidCBussiness.getUserid());
				addBill.setSysCost(BigDecimal.ZERO); //系统余额
				addBill.setRealCost(BigDecimal.ZERO);//优惠金额
				addBill.setTransferCost(BigDecimal.ZERO);
				addBill.setTransferRealCost(BigDecimal.ZERO);//资金转移优惠金额
				addBill.setAddTime(prepaidCBussiness.getTradetime());
				addBill.setState(AddBillStateEnum.notPrint.getValue());//未打印
				addBill.setPrintNum(0);
				addBillDao.save(addBill);
			}
		}
	}

	public void updateReversal(PrepaidCBussiness prepaidCBussiness, MainAccountInfo mainAccountInfo,List<ReturnFee> returnFeeList,Long oldBussinessID, Map<String,Object> params) {
		/**
		 * 1、更改总帐户信息冻结金额2、修改储值卡业务记录状态 3、增加客服流水4、操作日志
		 */

		try {
			PrepaidCBussiness oldPrepaidCBusiness = prepaidCBussinessService.findById(oldBussinessID);
			if (oldPrepaidCBusiness == null) {
				logger.error("储值卡业务记录[{}]不存在", oldBussinessID);
				throw new ApplicationException("业务记录不存在");
			} else if (!PrepaidCardBussinessTradeStateEnum.save.getValue().equals(oldPrepaidCBusiness.getTradestate())) {
				logger.error("储值卡业务记录[{}]交易状态不对", oldBussinessID);
				throw new ApplicationException("储值卡业务记录交易状态不对");
			}

			oldPrepaidCBusiness.setMac(prepaidCBussiness.getMac());
			oldPrepaidCBusiness.setTac(prepaidCBussiness.getTac());
			oldPrepaidCBusiness.setTermcode(prepaidCBussiness.getTermcode());
			oldPrepaidCBusiness.setTermtradeno(prepaidCBussiness.getTermtradeno());
			oldPrepaidCBusiness.setOnlinetradeno(prepaidCBussiness.getOnlinetradeno());
			oldPrepaidCBusiness.setOfflinetradeno(prepaidCBussiness.getOfflinetradeno());
			oldPrepaidCBusiness.setBalance(prepaidCBussiness.getBalance());


			if (prepaidCBussinessService.updateTradeStateSuccess(oldPrepaidCBusiness) != 1) {
				logger.error("储值卡业务记录[{}]更新失败", oldBussinessID);
				throw new ApplicationException("业务记录数据发生变化");
			}

			Date reversalSureTime = new Date(); //临时存储充值确认时间，后边使用

			//PrepaidCBussiness oldPrepaidCBussiness=prepaidCBussinessDao.findById(oldBussinessID);
			if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(oldPrepaidCBusiness.getState())) {//免登陆充值冲正

				Long operPlaceId = oldPrepaidCBusiness.getPlaceid();
				ServiceFundMonitor fundMonitor = serviceFundMonitorService.findFundMonitorRechargeByCustomPoint(operPlaceId);
				//判断已用金额+充值金额+回退金额+转移金额是否超过充值上限
				if (fundMonitor != null) {
					int ret = serviceFundMonitorService.updateFundMonitorRechargeByCustomPoint(operPlaceId, oldPrepaidCBusiness.getRealprice());
					if (ret != 1) {
						logger.warn("网点[{}]授权资金更新不为1，需注意", operPlaceId);
					}
				}
			}

			//给清算数据，插入后半部分数据
			ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBusiness, "02", reversalSureTime, "4", ScAddSureConfirmTypeEnum.normal.getValue());
			//写给清算的数据
			scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBusiness, "02", reversalSureTime, "4");

			//ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("02", oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
			//给清算数据	给铭鸿
			saveStoreCardRecharge4Reversal(oldPrepaidCBusiness, scAddSure);

			// 发票处理(无需处理)

			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			Customer customer = customerService.findById(oldPrepaidCBusiness.getUserid());
			if (customer != null) {
				serviceWater.setCustomerId(customer.getId());
				serviceWater.setUserNo(customer.getUserNo());
				serviceWater.setUserName(customer.getOrgan());
			}
			serviceWater.setCardNo(oldPrepaidCBusiness.getCardno());
			PrepaidCBussiness preRechargeBusiness  = new PrepaidCBussiness();
			if (PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(oldPrepaidCBusiness.getState())) {
				//储值卡充值冲正
				serviceWater.setSerType(ServiceWaterSerType.manualRechargeReversal.getValue());//储值卡充值冲正
				serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
				serviceWater.setRemark(ServiceWaterSerType.manualRechargeReversal.getName());
				//对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
				preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
				if (preRechargeBusiness != null) {
					ServiceWater oldServiceWater = new ServiceWater();
					oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
					oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
					serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
				}
			} else if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(oldPrepaidCBusiness.getState())) {
				//储值卡直充充值冲正
				serviceWater.setSerType(ServiceWaterSerType.noLoginRechargeReversal.getValue());//储值卡直充充值冲正
				serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
				serviceWater.setRemark(ServiceWaterSerType.noLoginRechargeReversal.getName());

				//对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
				preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(oldPrepaidCBusiness.getCardno(), oldPrepaidCBusiness.getTradetime());
				if (preRechargeBusiness != null) {
					ServiceWater oldServiceWater = new ServiceWater();
					oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
					oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
					serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
				}
			}
			serviceWater.setAmt(oldPrepaidCBusiness.getRealprice());//应收金额
			serviceWater.setAulAmt(oldPrepaidCBusiness.getRealprice());//实收金额
			//serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
			serviceWater.setPrepaidCBussinessId(oldPrepaidCBusiness.getId());

			serviceWater.setOperId(oldPrepaidCBusiness.getOperid());
			serviceWater.setOperNo(oldPrepaidCBusiness.getOperNo());
			serviceWater.setOperName(oldPrepaidCBusiness.getOperName());
			serviceWater.setPlaceId(oldPrepaidCBusiness.getPlaceid());
			serviceWater.setPlaceNo(oldPrepaidCBusiness.getPlaceNo());
			serviceWater.setPlaceName(oldPrepaidCBusiness.getPlaceName());
			serviceWater.setOperTime(reversalSureTime);

			serviceWaterDao.save(serviceWater);

			//储值卡充值冲正回执(人工充值冲正)
			PreCardRechargeCorrectReceipt preCardRechargeCorrectReceipt = new PreCardRechargeCorrectReceipt();
			preCardRechargeCorrectReceipt.setTitle("储值卡充值冲正回执");
			preCardRechargeCorrectReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
			preCardRechargeCorrectReceipt.setRechargeReciptNo(this.receiptDao.findByBusIdAndPTC(preRechargeBusiness.getId(),ReceiptParentTypeCodeEnum.prepaidC.getValue()).getReceiptNo());
			preCardRechargeCorrectReceipt.setPreCardNo(oldPrepaidCBusiness.getCardno());
			preCardRechargeCorrectReceipt.setPreCardBeforebalance(NumberUtil.get2Decimal(oldPrepaidCBusiness.getBeforebalance().doubleValue()*0.01));
			preCardRechargeCorrectReceipt.setCorrectMoney(NumberUtil.get2Decimal(oldPrepaidCBusiness.getRealprice().doubleValue()*0.01).substring(1));
			preCardRechargeCorrectReceipt.setPreCardBalance(NumberUtil.get2Decimal(oldPrepaidCBusiness.getBalance().doubleValue()*0.01));
			Receipt receipt = new Receipt();
			receipt.setTypeCode(oldPrepaidCBusiness.getState());
			receipt.setTypeChName("储值卡充值冲正");	//代理点没有人工/直充概念，直接写充值冲正
			receipt.setCardNo(preCardRechargeCorrectReceipt.getPreCardNo());
			this.saveReceipt(receipt,oldPrepaidCBusiness,preCardRechargeCorrectReceipt,customer);

		} catch (ApplicationException e) {
			logger.error("储值卡充值失败", e);// 记录删除日志
			throw new ApplicationException("储值卡充值失败");
		}
	}

	public void saveStoreCardRecharge4Reversal(PrepaidCBussiness prepaidCBusiness, ScAddSure scAddSure) {
		StoreCardRecharge storeCardRecharge = new StoreCardRecharge();
		storeCardRecharge.setBalReq(scAddSure.getBalReq());//充值前余额
		storeCardRecharge.setBalSur(prepaidCBusiness.getBalance());//充值后余额
		storeCardRecharge.setCardCode(prepaidCBusiness.getCardno());//储值卡卡号
		storeCardRecharge.setDealtype("02");//交易类型
		storeCardRecharge.setLinkReq(scAddSure.getOnlineTradenoReq().toString());//充值前联机号
		storeCardRecharge.setLinkSur(prepaidCBusiness.getOnlinetradeno());//充值后联机号
		storeCardRecharge.setMoneySur(prepaidCBusiness.getRealprice());//
		storeCardRecharge.setReturnMoneySur(BigDecimal.ZERO);//
		storeCardRecharge.setTimeReq(prepaidCBusiness.getTradetime());//
		storeCardRecharge.setTrReq(scAddSure.getOfflineTradenoReq().toString());//充值前脱机号
		storeCardRecharge.setTrSur(prepaidCBusiness.getOfflinetradeno());//充值后脱机号
		storeCardRecharge.setTimeConfirm(scAddSure.getTimeSur());
		storeCardRecharge.setUpdatetime(new Date());//
		Long id = sequenceUtil.getSequenceLong("SEQ_tbstorecardrechargesend_NO");
		storeCardRecharge.setId(id);//
		storeCardRechargeDao.savePrepaidCCharge(storeCardRecharge);//
	}

	@SuppressWarnings({ "unused", "null"})
	private boolean saveAndDel(List<Map<String, Object>>  returnFees,String type,PrepaidCBussiness prepaidBussiness){
		boolean flag=false;
		if(returnFees !=null || returnFees.size()>0){
			Map<String, Object> m=null;
			for(int i=0;i<returnFees.size();i++){
				m=returnFees.get(i);
				if(m.get("FEETYPE").toString().equals(type)){//1:回退金额					
				//调用webservice验证是否有效，有效就搬到客户系统，并删除	
					if(true){
						tbScreturnSendDao.deleteById(Long.parseLong(m.get("ID").toString()));
						returnFeeDao.saveByMap(m, prepaidBussiness.getId());
					}else{
						flag=true;
						tbScreturnSendDao.deleteById(Long.parseLong(m.get("ID").toString()));
						return flag;
					}
				}
				
			}
		}
		return flag;
	}

	/**
	 * 保存回执
	 * @param receipt 回执主要信息
	 * @param prepaidCBussiness 储值卡业务
	 * @param baseReceiptContent 回执VO
	 * @param customer 客户信息
	 */
	private void saveReceipt(Receipt receipt, PrepaidCBussiness prepaidCBussiness, BaseReceiptContent baseReceiptContent, Customer customer){
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.prepaidC.getValue());
		receipt.setCreateTime(prepaidCBussiness.getTradetime());
		receipt.setPlaceId(prepaidCBussiness.getPlaceid());
		receipt.setPlaceNo(prepaidCBussiness.getPlaceNo());
		receipt.setPlaceName(prepaidCBussiness.getPlaceName());
		receipt.setOperId(prepaidCBussiness.getOperid());
		receipt.setOperNo(prepaidCBussiness.getOperName());
		receipt.setOperName(prepaidCBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

	public boolean saveRechargeGainCard(PrepaidCBussiness prepaidCBussiness, DbasCardFlow dbasCardFlow) {

		prepaidCBussiness.setRealprice(BigDecimal.ZERO);
		prepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
		if (prepaidCBussiness.getTransferSum() == null) {
			prepaidCBussiness.setTransferSum(BigDecimal.ZERO);
		}

		BigDecimal transfee = dbasCardFlow.getCardAmt();
		BigDecimal returnMoney = BigDecimal.ZERO;

		if (StringUtils.isBlank(prepaidCBussiness.getCardno())) {
			logger.error("卡号为空[{}]", prepaidCBussiness.getCardno());
			return false;
		}

		if (!prepaidCBussiness.getCardno().equals(dbasCardFlow.getNewCardNo())) {
			logger.warn("新卡号[{}]与资金转移新卡号[{}]不一致", prepaidCBussiness.getCardno(), dbasCardFlow.getNewCardNo());
			return false;
		}

		if (prepaidCBussiness.getTransferSum().compareTo(transfee) != 0) {
			logger.warn("系统转移金额[{}]与申请的转移金额[{}]不一致", transfee, prepaidCBussiness.getTransferSum());
			return false;
		}

		BigDecimal totalMoney = prepaidCBussiness.getRealprice().add(transfee).add(returnMoney);
		//判断金额是否大于0，总充值金额是否大于0
		if (prepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) < 0
				|| transfee.compareTo(BigDecimal.ZERO) < 0
				|| returnMoney.compareTo(BigDecimal.ZERO) < 0
				|| totalMoney.compareTo(BigDecimal.ZERO) <= 0
				|| totalMoney.add(prepaidCBussiness.getBeforebalance()).compareTo(new BigDecimal("3000000")) > 0) {
			logger.error("卡余额[{}],充值金额非法[{}]、[{}]、[{}]", prepaidCBussiness.getBeforebalance(), prepaidCBussiness.getRealprice(), transfee, returnMoney);
			return false;
		}

		if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())
				&& prepaidCBussiness.getRealprice().compareTo(BigDecimal.ZERO) != 0) {

			Long operPlaceId = prepaidCBussiness.getPlaceid();
			ServiceFundMonitor fundMonitor = serviceFundMonitorService.findFundMonitorRechargeByCustomPoint(operPlaceId);
			//判断已用金额+充值金额+回退金额+转移金额是否超过充值上限
			if (fundMonitor != null) {
				if (fundMonitor.getUseFund().add(prepaidCBussiness.getRealprice()).compareTo(fundMonitor.getFundMax()) > 0) {
					logger.error("网点[{}]超过授权资金上限，无法充值", operPlaceId);
					throw new ApplicationException("超过授权资金上限，不能进行充值");
				}
				int ret = serviceFundMonitorService.updateFundMonitorRechargeByCustomPoint(operPlaceId, prepaidCBussiness.getRealprice());
				if (ret != 1) {
					logger.error("网点[{}]授权资金发生变更，需重试", operPlaceId);
					throw new ApplicationException("授权资金发生变更，需重试");
				}
			}
		} else if (!PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
			logger.error("业务类型[{}]非法", prepaidCBussiness.getState());
			throw new ApplicationException("业务类型非法");
		}

		prepaidCBussiness.setBusinessId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
		prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness);

		//锁定转移资金
		int ret = dbasCardFlowDao.updateLockEndFlagById(prepaidCBussiness.getId(), prepaidCBussiness.getTransferSum(), prepaidCBussiness.getTradetime(), dbasCardFlow.getId());
		if (ret != 1) {
			throw new ApplicationException("转移金额数据发生变更，请重新操作");
		}

		//给清算数据
		scAddDao.saveScAddReqByBussiness(prepaidCBussiness, "01", ScAddReqPaychannelEnum.AGENT.getValue());
		scAddDao.saveScAddSendByBussiness(prepaidCBussiness, "01");//清算数据

		scAddDao.saveScAddSureByBussiness(prepaidCBussiness, "01", ScAddReqPaychannelEnum.AGENT.getValue());
		scAddDao.saveScAddSureSendByBussiness(prepaidCBussiness, "01");//清算数据

		return true;

	}
}
