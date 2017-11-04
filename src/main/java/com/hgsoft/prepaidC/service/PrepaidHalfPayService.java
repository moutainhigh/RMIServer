package com.hgsoft.prepaidC.service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RechargeInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.RechargeInfo;
import com.hgsoft.account.serviceInterface.IMainAccountInfoService;
import com.hgsoft.account.serviceInterface.IRechargeInfoService;
import com.hgsoft.clearInterface.dao.ScaddSendDao;
import com.hgsoft.clearInterface.dao.ScreturnSendDao;
import com.hgsoft.clearInterface.dao.StoreCardRechargeDao;
import com.hgsoft.clearInterface.entity.ScreturnSend;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.Enum.*;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.invoice.dao.AddBillDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.prepaidC.dao.*;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.prepaidC.serviceInterface.IAddRegDetailService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidHalfPayService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.unifiedInterface.service.AgentPrepaidCUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrepaidHalfPayService implements IPrepaidHalfPayService {
    //private static Logger logger = Logger.getLogger(PrepaidHalfPayService.class.getName());
    private static final Logger logger = LoggerFactory.getLogger(PrepaidHalfPayService.class);

    @Resource
    private PrepaidHalfPayDao prepaidHalfPayDao;
    @Resource
    private ReturnFeeDao returnFeeDao;
    @Resource
    private AddRegDetailDao addRegDetailDao;
    @Resource
    private PrepaidCBussinessDao prepaidCBussinessDao;
    @Resource
    private MainAccountInfoDao mainAccountInfoDao;
    @Resource
    private IUnifiedInterface unifiedInterfaceService;
    @Resource
    private ScaddSendDao scaddSendDao;
    @Resource
    private ScreturnSendDao screturnSendDao;
    @Resource
    private IRechargeInfoService rechargeInfoService;
    @Resource
    private RechargeInfoDao rechargeInfoDao;
    @Resource
    private IMainAccountInfoService mainAccountInfoService;
    @Resource
    private ScAddDao scAddDao;
    @Resource
    private IPrepaidCBussinessService prepaidCBussinessService;
    @Resource
    private DbasCardFlowDao dbasCardFlowDao;
    @Resource
    private SequenceUtil sequenceUtil;
    @Resource
    private StoreCardRechargeDao storeCardRechargeDao;
    @Resource
    private IPrepaidCService prepaidCService;
    @Resource
    private AddBillDao addBillDao;
    @Resource
    private ReceiptDao receiptDao;
    @Resource
    private IAddRegDetailService addRegDetailService;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private ServiceWaterDao serviceWaterDao;
    @Resource
    private AgentPrepaidCUnifiedInterfaceService agentPrepaidCUnifiedInterfaceService;
    @Resource
    private PrepaidCUnifiedInterfaceService prepaidCUnifiedInterfaceService;
    @Resource
    private ICardObuService cardObuService;


    @Override
    public Pager findHalfPayByPage(Pager pager, PrepaidCBussiness prepaidCBussiness, Integer type) {
        // TODO Auto-generated method stub
        return prepaidHalfPayDao.findHalfPayByPage(pager, prepaidCBussiness, type);
    }

    private boolean isToday(Date date) {
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        return (day.format(new Date()).equals(day.format(date)));
    }

    // 充值失败
    @Override
    // 26快速充值半条确认失败 28人工充值半条确认失败 30充值冲正失败
    public Map < String, Object > saveHalfFalse(PrepaidCBussiness oldPrepaidCBussiness) {
        try {
            boolean flag = true;
            Map<String,Object> m = new HashMap<String,Object>();
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
            prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().abs());

            //prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
            //prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
            //prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
            prepaidCBussiness.setBalance(oldPrepaidCBussiness.getBalance());
            MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(prepaidCBussiness.getUserid());
            UnifiedParam unifiedParam = new UnifiedParam();
            unifiedParam.setMainAccountInfo(mainAccountInfo);
            unifiedParam.setPrepaidCBussiness(prepaidCBussiness);
            unifiedParam.setPlaceId(oldPrepaidCBussiness.getPlaceid());
            unifiedParam.setOperId(oldPrepaidCBussiness.getOperid());
            unifiedParam.setOperName(oldPrepaidCBussiness.getOperName());
            unifiedParam.setOperNo(oldPrepaidCBussiness.getOperNo());
            unifiedParam.setPlaceName(oldPrepaidCBussiness.getPlaceName());
            unifiedParam.setPlaceNo(oldPrepaidCBussiness.getPlaceNo());

            if (PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState())
                    || PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())
                    || PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) { //

                if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) {
                    unifiedParam.setType(AccChangeTypeEnum.imRechargeHalfFail.getValue());
                } else {
                    unifiedParam.setType(AccChangeTypeEnum.memberRechargeHalfFail.getValue());
                }

                if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
                    //完成储值卡业务记录
                    if(prepaidCBussinessService.updateTradeStateFail(prepaidCBussiness) != 1) {
                        throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]数据发生变化");
                    }
                    Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
                    if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
                        //完成回退金额
                        returnFeeDao.updateNotUseState(prepaidCBussiness.getId());
                    }

                    if (prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
                        //完成转移金额
                        dbasCardFlowDao.updateDisRechargeEndFlagByEndServiceId(prepaidCBussiness.getId());
                    }

                    oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
                    oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
                    oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
                    oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());
                    //插入后半部分数据
                    scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "01", addSureTime, "1", ScAddSureConfirmTypeEnum.frontSure.getValue());
                    //写给清算的数据
                    scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "01", addSureTime, "1");

                    if (PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())) {
                        if (!"1".equals(prepaidCBussiness.getIsDaySet()) && isToday(prepaidCBussiness.getTradetime())) { //未日结，且是当天
                            RechargeInfo rechargeInfoForId = rechargeInfoDao.findByPrepaidCBussinessId(oldPrepaidCBussiness.getId()); // 不为空的时候，免登陆快速充值
                            if (rechargeInfoForId == null) {
                                logger.error("业务记录[{}]没有对应的直充缴款记录", oldPrepaidCBussiness.getId());
                                throw new ApplicationException("业务记录没有对应的直充缴款记录");
                            }
                            if ("1".equals(rechargeInfoForId.getIsCorrect())) {
                                throw new ApplicationException("主账户缴款记录["+rechargeInfoForId.getId()+"]已经冲正");
                            }

                            mainAccountInfo = mainAccountInfoService.findByMainId(rechargeInfoForId.getMainId());
                            // 冲正
                            rechargeInfoForId.setTransactionType("2");
                            rechargeInfoForId.setCorrectId(rechargeInfoForId.getId()); // 冲正id
                            rechargeInfoForId.setOperId(oldPrepaidCBussiness.getOperid());
                            rechargeInfoForId.setPlaceId(oldPrepaidCBussiness.getPlaceid());
                            rechargeInfoForId.setOperTime(addSureTime);
                            rechargeInfoForId.setOperName(oldPrepaidCBussiness.getOperName());
                            rechargeInfoForId.setOperNo(oldPrepaidCBussiness.getOperNo());
                            rechargeInfoForId.setPlaceName(oldPrepaidCBussiness.getPlaceName());
                            rechargeInfoForId.setPlaceNo(oldPrepaidCBussiness.getPlaceNo());
                            rechargeInfoForId.setIsDaySet(null);
                            rechargeInfoForId.setSettleDay(null);
                            rechargeInfoForId.setSettletTime(null);
                            prepaidCUnifiedInterfaceService.saveRechargeInfo(mainAccountInfo, rechargeInfoForId, AccChangeTypeEnum.correct, AccChangeTypeEnum.imCorrect);

                            //rechargeInfoService.save(AccChangeTypeEnum.correct.getValue(), mainAccountInfo, rechargeInfoForId, null, AccChangeTypeEnum.imCorrect.getValue(), null);

                        }
                    } else if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) {
                        if (addRegDetailService.updateFlagNotRecharge(prepaidCBussiness.getId()) != 1) {
                            throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]对应的充值登记记录发生变更");
                        }
                        //addRegDetailDao.updateByBussinessID("0", prepaidCBussiness.getId());
                    }

                } else {
                    flag = false;
                }
            } else if (PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(prepaidCBussiness.getState().trim())
                    || PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState().trim())) { // 充值冲正

                unifiedParam.setType(AccChangeTypeEnum.rechargeRegisterHalfFail.getValue());
                if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
                    prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().negate());
                    if (prepaidCBussinessService.updateTradeStateFail(prepaidCBussiness) != 1) {
                        throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]数据发生变化");
                    }

                    Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
                    oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
                    oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
                    oldPrepaidCBussiness.setTransferSum(BigDecimal.ZERO);
                    oldPrepaidCBussiness.setReturnMoney(BigDecimal.ZERO);
                    //插入后半部分数据
                    scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "02", addSureTime, "2", ScAddSureConfirmTypeEnum.frontSure.getValue());
                    //写给清算的数据
                    scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "02", addSureTime, "2");

                    if (PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState())) {
                        //将直充的缴款记录对应的业务id更新为原先的充值记录
                        PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                        if (rechargeInfoDao.updatePrepaidCBussinessId(prepaidCBussiness.getId(), preRechargeBusiness.getId()) != 1) {
                            throw new ApplicationException("储值卡业务记录["+prepaidCBussiness.getId()+"]没有对应的主账户缴款记录");
                        }
                    }
                } else {
                    flag = false;
                }
            }
            m.put("success", flag);
            if (!flag) {
                m.put("message", "该条充值无法确认");
            }
            return m;
        } catch(ApplicationException e) {
            throw new ApplicationException("半条确认失败异常", e);
        }
    }

    // 充值成功
    @Override
    // 25 快速充值半条确认成功 27人工充值半条确认成功 29充值冲正成功
    public Map < String, Object > saveHalfTrue(PrepaidCBussiness oldPrepaidCBussiness) {
        try {
            boolean flag = true;
            Map<String,Object> m = new HashMap<String,Object>();
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
            prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().abs());

            prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
            prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
            prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
            prepaidCBussiness.setBalance(oldPrepaidCBussiness.getBalance());
            MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(prepaidCBussiness.getUserid());
            UnifiedParam unifiedParam = new UnifiedParam();
            unifiedParam.setMainAccountInfo(mainAccountInfo);
            unifiedParam.setPrepaidCBussiness(prepaidCBussiness);
            unifiedParam.setPlaceId(oldPrepaidCBussiness.getPlaceid());
            unifiedParam.setOperId(oldPrepaidCBussiness.getOperid());
            unifiedParam.setOperName(oldPrepaidCBussiness.getOperName());
            unifiedParam.setOperNo(oldPrepaidCBussiness.getOperNo());
            unifiedParam.setPlaceName(oldPrepaidCBussiness.getPlaceName());
            unifiedParam.setPlaceNo(oldPrepaidCBussiness.getPlaceNo());
            if (PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState().trim())
                    || PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState().trim())
                    || PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState().trim())) { // 人工充值||直充||充值登记充值

                if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState().trim())) {
                    unifiedParam.setType(AccChangeTypeEnum.imRechargeHalfSuccess.getValue());
                } else {
                    unifiedParam.setType(AccChangeTypeEnum.memberRechargeHalfSuccess.getValue());
                }

                if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
                    //完成储值卡业务记录
                    if(prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
                        throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]数据发生变化");
                    }

                    if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState().trim())) {
                        //充值登记记录更新
                        if (PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())) { //充值登记
                            if (addRegDetailService.updateFlagRechargeSuccess(prepaidCBussiness.getId()) != 1) {
                                throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]对应的充值登记数据发生变化");
                            }
                        }
                    }

                    Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
                    if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
                        //完成回退金额
                        returnFeeDao.updateRechargeSuccessState(addSureTime, prepaidCBussiness.getId());
                    }

                    if(prepaidCBussiness.getTransferSum().compareTo(BigDecimal.ZERO) != 0) {
                        //完成转移金额
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


                    //添加充值账单
                    PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());
                    agentPrepaidCUnifiedInterfaceService.saveAddBill(prepaidC, prepaidCBussiness, false);

                    oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
                    oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
                    oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
                    oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());

                    //给清算数据，插入后半部分数据
                    ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "01", addSureTime, "3", ScAddSureConfirmTypeEnum.frontSure.getValue());
                    //写给原清算的数据
                    scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "01", addSureTime, "3");

                    //ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("01", prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                    //给清算数据    充值	给铭鸿
                    agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge(prepaidCBussiness, scAddSure);

                    //给清算数据    资金转移		给铭鸿
                    agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge4Transfer(prepaidCBussiness, scAddSure);

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
                    if(PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState())){
                        //人工充值
                        serviceWater.setSerType(ServiceWaterSerType.manualRecharge.getValue());//储值卡人工充值
                        serviceWater.setRemark(ServiceWaterSerType.manualRecharge.getName());
                    }else if(PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())){
                        //充值登记充值
                        serviceWater.setSerType(ServiceWaterSerType.addRegRecharge.getValue());//储值卡充值登记充值
                        serviceWater.setRemark(ServiceWaterSerType.addRegRecharge.getName());
                    }else if(PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())){
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

                    //保存回执记录
                    Receipt receipt = receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);

                } else {
                    flag = false;
                }
            } else if (PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(prepaidCBussiness.getState().trim())
                    || PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState().trim())) { // 充值冲正

                unifiedParam.setType(AccChangeTypeEnum.rechargeRegisterHalfSuccess.getValue());
                if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {

                    prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().negate());
                    //完成储值卡业务记录
                    if(prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
                        throw new ApplicationException("业务记录["+prepaidCBussiness.getId()+"]数据发生变化");
                    }

                    //保存回执记录
                    Receipt receipt = receiptDao.saveByBussiness(prepaidCBussiness, null, null, null, null);

                    Date reversalSureTime = new Date(); //临时存储充值确认时间，后边使用

                    if(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState())){//免登陆充值冲正
                        if (!"1".equals(prepaidCBussiness.getIsDaySet()) && isToday(prepaidCBussiness.getTradetime())) {//未日结，且是当天
                            RechargeInfo rechargeInfoForId = rechargeInfoService.findByPrepaidCBussinessId(prepaidCBussiness.getId()); // 不为空的时候，免登陆快速充值
                            if (rechargeInfoForId == null) {
                                logger.error("储值卡业务记录[{}]不存在对应的主账户缴款记录", prepaidCBussiness.getId());
                                throw new ApplicationException("储值卡业务记录不存在对应的主账户缴款记录");
                            } else if ("1".equals(rechargeInfoForId.getIsCorrect())) {
                                logger.error("储值卡业务记录[{}]对应的主账户缴款记录[{}]已经冲正", prepaidCBussiness.getId(), rechargeInfoForId.getId());
                                throw new ApplicationException("主账户缴款记录已经冲正");
                            }

                            //将直充的缴款记录对应的业务id更新为原先的充值记录
                            PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                            if (rechargeInfoDao.updatePrepaidCBussinessId(prepaidCBussiness.getId(), preRechargeBusiness.getId()) != 1) {
                                throw new ApplicationException("储值卡业务记录["+prepaidCBussiness.getId()+"]没有对应的主账户缴款记录");
                            }

                            mainAccountInfo = mainAccountInfoService.findByMainId(rechargeInfoForId.getMainId());
                            // 冲正
                            rechargeInfoForId.setTransactionType("2");
                            rechargeInfoForId.setCorrectId(rechargeInfoForId.getId()); // 冲正id
                            rechargeInfoForId.setOperTime(reversalSureTime);

                            prepaidCUnifiedInterfaceService.saveRechargeInfo(mainAccountInfo, rechargeInfoForId, AccChangeTypeEnum.correct, AccChangeTypeEnum.imCorrect);
                            //rechargeInfoService.save(AccChangeTypeEnum.correct.getValue(), mainAccountInfo, rechargeInfoForId, null, AccChangeTypeEnum.imCorrect.getValue(), null);

                        } else {
                            //将直充的缴款记录对应的业务id更新为原先的充值记录
                            PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                            if (rechargeInfoDao.updatePrepaidCBussinessId(prepaidCBussiness.getId(), preRechargeBusiness.getId()) != 1) {
                                throw new ApplicationException("储值卡业务记录["+prepaidCBussiness.getId()+"]没有对应的主账户缴款记录");
                            }
                        }
                    }
                    oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
                    oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
                    oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
                    oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());
                    //给清算数据，插入后半部分数据
                    ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "02", reversalSureTime, "4", ScAddSureConfirmTypeEnum.frontSure.getValue());
                    //写给清算的数据
                    scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "02", reversalSureTime, "4");

                    //ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("02", prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                    //给清算数据	给铭鸿
                    agentPrepaidCUnifiedInterfaceService.saveStoreCardRecharge4Reversal(prepaidCBussiness, scAddSure);

                    //客户服务流水
                    ServiceWater serviceWater = new ServiceWater();
                    serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
                    Customer customer = customerDao.findById(prepaidCBussiness.getUserid());
                    if (customer != null) {
                        serviceWater.setCustomerId(customer.getId());
                        serviceWater.setUserNo(customer.getUserNo());
                        serviceWater.setUserName(customer.getOrgan());
                    }
                    serviceWater.setCardNo(prepaidCBussiness.getCardno());
                    if(PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(prepaidCBussiness.getState())){
                        //储值卡充值冲正
                        serviceWater.setSerType(ServiceWaterSerType.manualRechargeReversal.getValue());//储值卡充值冲正
                        serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
                        serviceWater.setRemark(ServiceWaterSerType.manualRechargeReversal.getName());
                        //对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
                        PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                        if (preRechargeBusiness != null) {
                            ServiceWater oldServiceWater = new ServiceWater();
                            oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
                            oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
                            serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
                        }
                    }else if(PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState())){
                        //储值卡直充充值冲正
                        serviceWater.setSerType(ServiceWaterSerType.noLoginRechargeReversal.getValue());//储值卡直充充值冲正
                        serviceWater.setFlowState(ServiceWaterFlowStateEnum.reversal.getValue());//冲正
                        serviceWater.setRemark(ServiceWaterSerType.noLoginRechargeReversal.getName());

                        //对储值卡充值进行冲正的同时，要找到之前的充值流水，将流水状态改为被冲正
                        PrepaidCBussiness preRechargeBusiness = prepaidCBussinessService.findNewRechargeBusinessByCardNoMaxTradetime(prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
                        if (preRechargeBusiness != null) {
                            ServiceWater oldServiceWater = new ServiceWater();
                            oldServiceWater.setFlowState(ServiceWaterFlowStateEnum.isReversaled.getValue());
                            oldServiceWater.setPrepaidCBussinessId(preRechargeBusiness.getId());
                            serviceWaterDao.updateByPrepaidCBusinessId(oldServiceWater);
                        }
                    }
                    serviceWater.setAmt(prepaidCBussiness.getRealprice());//应收金额
                    serviceWater.setAulAmt(prepaidCBussiness.getRealprice());//实收金额
                    //serviceWater.setSaleWate(prepaidC.getSaleFlag());//销售方式
                    serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());

                    serviceWater.setOperId(prepaidCBussiness.getOperid());
                    serviceWater.setOperNo(prepaidCBussiness.getOperNo());
                    serviceWater.setOperName(prepaidCBussiness.getOperName());
                    serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
                    serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
                    serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
                    serviceWater.setOperTime(reversalSureTime);

                    serviceWaterDao.save(serviceWater);

                } else {
                    flag = false;
                }
            }
            m.put("success", flag);
            if (!flag) {
                m.put("message", "该条充值无法确认");
            }
            return m;
        } catch(ApplicationException e) {
            throw new ApplicationException("半条确认成功异常", e);
        }
    }

    // 充值失败 联名卡
    @Override
    // 26快速充值半条确认失败 28人工充值半条确认失败 30充值冲正失败
    public Map < String, Object > saveHalfFalseIm(PrepaidCBussiness oldPrepaidCBussiness) {
        try {
            boolean flag = true;
            Map < String,
            Object > m = new HashMap < String,
            Object > ();
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
            //
            if (prepaidCBussiness.getRealprice().toString().startsWith("-")) {
                prepaidCBussiness.setRealprice(new BigDecimal(prepaidCBussiness.getRealprice().toString().replace("-", "")));
            }
            prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
            prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
            prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
            if ("19".equals(prepaidCBussiness.getState().trim())) { // 人工充值
                prepaidCBussinessDao.updateById("3", prepaidCBussiness.getId());
                returnFeeDao.updateByBussinessID("1", prepaidCBussiness.getId()); // 返回金额充值失败
            }

            if ("20".equals(prepaidCBussiness.getState().trim())) { // 充值冲正
                prepaidCBussinessDao.updateById("3", prepaidCBussiness.getId());
                returnFeeDao.updateByBussinessID("1", prepaidCBussiness.getId());
            }
            m.put("success", flag);
            if (!false) {
                m.put("message", "该条充值无法确认");
            }
            return m;
        } catch(ApplicationException e) {
            throw new ApplicationException("半条确认失败异常", e);
        }
    }

    // 充值成功 联名卡
    @Override
    // 25 快速充值半条确认成功 27人工充值半条确认成功 29充值冲正成功
    public Map < String,
    Object > saveHalfTrueIm(PrepaidCBussiness oldPrepaidCBussiness) {
        try {
            boolean flag = true;
            Map < String, Object > m = new HashMap < String, Object > ();
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
            //
            if (prepaidCBussiness.getRealprice().toString().startsWith("-")) {
                prepaidCBussiness.setRealprice(new BigDecimal(prepaidCBussiness.getRealprice().toString().replace("-", "")));
            }
            prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
            prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
            prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
            if ("19".equals(prepaidCBussiness.getState().trim())) { // 人工充值
                prepaidCBussinessDao.updateById("2", prepaidCBussiness.getId());
                returnFeeDao.updateByBussinessID("3", prepaidCBussiness.getId());

                //TODO 给清算数据
                //scAddDao.updateScAddSureByBussiness(prepaidCBussiness, "3");
                //scAddDao.updateScAddSureSendByBussiness(prepaidCBussiness, "3");

                if (prepaidCBussiness.getReturnMoney() == null) {
                    prepaidCBussiness.setReturnMoney(new BigDecimal("0"));
                } else { // 1,为回退金额，3充值成功
                    screturnSendDao.save(new ScreturnSend(), prepaidCBussiness, "1", "3");
                }
                if (prepaidCBussiness.getTransferSum() == null) {
                    prepaidCBussiness.setTransferSum(new BigDecimal("0"));
                } else { // 2,为转移金额，3充值成功
                    screturnSendDao.save(new ScreturnSend(), prepaidCBussiness, "2", "3");
                }
                // scaddSendDao.save(new ScaddSend(), prepaidCBussiness, "3");
            }

            if ("20".equals(prepaidCBussiness.getState().trim())) { // 充值冲正
                prepaidCBussinessDao.updateById("2", prepaidCBussiness.getId());
                returnFeeDao.updateByBussinessID("1", prepaidCBussiness.getId()); // 充值冲正成功
                //TODO 给清算数据
                //scAddDao.updateScAddSureByBussiness(prepaidCBussiness, "4");
                //scAddDao.updateScAddSureSendByBussiness(prepaidCBussiness, "4");

                if (prepaidCBussiness.getReturnMoney() == null) {
                    prepaidCBussiness.setReturnMoney(new BigDecimal("0"));
                } else { // 1,为回退金额，4充值冲正成功
                    screturnSendDao.save(new ScreturnSend(), prepaidCBussiness, "1", "5");
                }
                if (prepaidCBussiness.getTransferSum() == null) {
                    prepaidCBussiness.setTransferSum(new BigDecimal("0"));
                } else { // 2,为转移金额，4充值冲正成功
                    screturnSendDao.save(new ScreturnSend(), prepaidCBussiness, "2", "5");
                }
                // scaddSendDao.save(new ScaddSend(), prepaidCBussiness, "5");
            }
            m.put("success", flag);
            if (!false) {
                m.put("message", "该条充值无法确认");
            }
            return m;
        } catch(ApplicationException e) {
            throw new ApplicationException("半条确认成功异常", e);
        }
    }

    @Override
    public int findHalf(String cardno) {
        return prepaidHalfPayDao.findHalf(cardno);
    }

    @Override
    public int findNewHalf(PrepaidCBussiness prepaidCBussiness) {
        return prepaidHalfPayDao.findNewHalf(prepaidCBussiness);
    }

    @Override
    public int findImHalf(String cardno) {
        return prepaidHalfPayDao.findImHalf(cardno);
    }
}