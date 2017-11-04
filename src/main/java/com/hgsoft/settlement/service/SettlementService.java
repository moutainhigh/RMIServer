package com.hgsoft.settlement.service;

import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.accountC.serviceInterface.IHandPaymentRepeatService;
import com.hgsoft.accountC.serviceInterface.IHandPaymentService;
import com.hgsoft.common.Enum.ReturnFeeStateEnum;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.prepaidC.serviceInterface.IReturnFeeService;
import com.hgsoft.settlement.entity.*;
import com.hgsoft.settlement.serviceinterface.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yangzhongji on 17/6/13.
 */
@Service
public class SettlementService implements ISettlementService {

    private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

    @Resource
    private IScTradeDetailInfoService scTradeDetailInfoService;
    @Resource
    private IAcTradeDetailInfoService acTradeDetailInfoService;
    @Resource
    private IMonthlyRegService monthlyRegService;
    @Resource
    private IScBillInfoMonthlyService scBillInfoMonthlyService;
    @Resource
    private IAcBillInfoMonthlyService acBillInfoMonthlyService;
    @Resource
    private ICommandInfoService commandInfoService;
    @Resource
    private IAccBankListReturnService accBankListReturnService;
    @Resource
    private IHandPaymentService handPaymentService;
    @Resource
    private IHandPaymentRepeatService handPaymentRepeatService;
    @Resource
    private IHandPaymentRepeatDetailService handPaymentRepeatDetailService;
    @Resource
    private ICardFeeDataService cardFeeDataService;
    @Resource
    private IReturnFeeService returnFeeService;

    @Override
    public void batchSaveScTradeDetailFromScAddSure() {
        scTradeDetailInfoService.batchSaveFromScAddSure();
    }

    @Override
    public void batchSaveScTradeDetailFromCardInSettleDetail() {
        scTradeDetailInfoService.batchSaveFromCardInSettleDetail();
    }

    @Override
    public void batchSaveScTradeDetailFromCardOutSettleDetail() {
        scTradeDetailInfoService.batchSaveFromCardOutSettleDetail();
    }

    @Override
    public void batchSaveAcTradeDetailFromCardInSettleDetail() {
        acTradeDetailInfoService.batchSaveFromCardInSettleDetail();
    }

    @Override
    public void batchSaveAcTradeDetailFromCardOutSettleDetail() {
        acTradeDetailInfoService.batchSaveFromCardOutSettleDetail();
    }

    @Override
    public List<MonthlyReg> findAllCheckedAndNotGenOfMonthlyRegs() {
        return monthlyRegService.findAllCheckedAndNotGen();
    }

    @Override
    public void saveMonthlyBill(MonthlyReg monthlyReg) {
        if (!MonthlyReg.CHECKFLAG_CHECKED.equals(monthlyReg.getCheckResult())) {
            logger.debug("未审核通过");
            return;
        }
        if (MonthlyReg.GENBILLFLAG_GEN.equals(monthlyReg.getGenBillFlag())) {
            logger.debug("已生成账单");
            return;
        }
        Date now = new Date();
        if (monthlyReg.getStartGenBillTime() == null
                || now.getTime() < monthlyReg.getStartGenBillTime().getTime()
                || now.getTime() < monthlyReg.getEndDispartTime().getTime()) {
            logger.debug("开始启动时间或结束接收时间不满足要求");
            return;
        }

        scBillInfoMonthlyService.batchSave(monthlyReg.getSettleMonth(), monthlyReg.getStartDispartTime(), monthlyReg.getEndDispartTime());

        acBillInfoMonthlyService.batchSave(monthlyReg.getSettleMonth(), monthlyReg.getStartDispartTime(), monthlyReg.getEndDispartTime());

        monthlyRegService.updateGenBillFlag(monthlyReg.getId(), MonthlyReg.GENBILLFLAG_GEN);
    }

    @Override
    public List<CommandInfo> findTollFeeCommand() {
        return commandInfoService.findTollFeeCommand();
    }

    @Override
    public void updateHandPayment(List<CommandInfo> commandInfos) {

        if (commandInfos.isEmpty()) {
            return;
        }

        List<AccBankListReturn> accBankListReturns = accBankListReturnService.listByCommandInfo(commandInfos.get(0));
        if (accBankListReturns.isEmpty() || accBankListReturns.size()>1) {
            logger.error("返盘结果(生成时间[{}]银行编码[{}]划账账户[{}])数量[{}]异常", commandInfos.get(0).getGentime(), commandInfos.get(0).getBankNo(),
                    commandInfos.get(0).getAcbAccount(), accBankListReturns.size());
            return;
        }
        AccBankListReturn accBankListReturn = accBankListReturns.get(0);
        if (!AccBankListReturn.StatusEnum.SUCCESS.getValue().equals(accBankListReturn.getStatus())) {
            commandInfoService.deleteByAccBankReturn(accBankListReturn);
            logger.info("返盘结果(生成时间[{}]银行编码[{}]划账账户[{}])状态[{}]不是成功，不更新", commandInfos.get(0).getGentime(), commandInfos.get(0).getBankNo(),
                    commandInfos.get(0).getAcbAccount(), accBankListReturn.getStatus());
            return;
        }

        Map<String, AcTradeDetailInfo> acTradeHandPaySumMaps = new HashMap<String, AcTradeDetailInfo>();
        for (CommandInfo commandInfo : commandInfos) {
            List<AcTradeDetailInfo> acTradeDetailInfos = acTradeDetailInfoService.findByDetailNoAndStatus(commandInfo.getDetailNo(), AcTradeDetailInfo.DealStatusEnum.NORMAL_TRADE.getValue());
            if (acTradeDetailInfos.size() != 1) {
                logger.error("清单中流水号[{}]记录数[{}]不为1", commandInfo.getDetailNo(), acTradeDetailInfos.size());
                throw new RuntimeException("清单中流水有问题");
            }

            AcTradeDetailInfo acTradeDetailInfo = acTradeDetailInfos.get(0);

            if (commandInfo.getRealToll().compareTo(acTradeDetailInfo.getRealToll()) != 0) {
                logger.error("清单流水[{}]金额[{}]与指令流水对照表（生成时间[{}]流水号[{}]划账账户银行[{}]编号[{}]）金额[{}]不一致",
                        acTradeDetailInfo.getDetailNo(), acTradeDetailInfo.getRealToll(), commandInfo.getGentime(),
                        commandInfo.getDetailNo(),commandInfo.getAcbAccount(),commandInfo.getBankNo(), commandInfo.getRealToll());
                throw new RuntimeException("清单流水与指令对照表金额不一致");
            }
            if (AcTradeDetailInfo.PayFlagEnum.PAID.getValue().equals(acTradeDetailInfo.getPayFlag())) {
                if (!AcTradeDetailInfo.PayTypeEnum.accCardMemberRechargeTollFee.getValue().equals(acTradeDetailInfo.getPayType())) {
                    logger.error("清单中流水号[{}]已支付，支付类型为[{}]", commandInfo.getDetailNo(), AcTradeDetailInfo.PayTypeEnum.getNameByValue(acTradeDetailInfo.getPayType()));
                    throw new RuntimeException("清单中流水号已支付");
                }

                String key = acTradeDetailInfo.getPayTime().getTime()+"-"+acTradeDetailInfo.getBankAccount();
                AcTradeDetailInfo acTradeHandPaySum = acTradeHandPaySumMaps.get(key);
                if (acTradeHandPaySum == null) {
                    acTradeHandPaySum = new AcTradeDetailInfo();
                    acTradeHandPaySum.setBankAccount(acTradeDetailInfo.getBankAccount());
                    acTradeHandPaySum.setPayTime(acTradeDetailInfo.getPayTime());
                    acTradeHandPaySum.setRealToll(acTradeDetailInfo.getRealToll());
                    acTradeHandPaySumMaps.put(key, acTradeHandPaySum);
                } else {
                    acTradeHandPaySum.setRealToll(acTradeHandPaySum.getRealToll().add(acTradeDetailInfo.getRealToll()));
                }
                // 生成重复手工缴费明细记录
                handPaymentRepeatDetailService.insertByAcTradeDetail(acTradeDetailInfo);
            }

            Integer oldPayFlag = acTradeDetailInfo.getPayFlag();

            acTradeDetailInfo.setBankAccount(accBankListReturn.getAcbAccount());
            acTradeDetailInfo.setBankNo(accBankListReturn.getBankNo());
            acTradeDetailInfo.setComGenTime(accBankListReturn.getGentime());
            acTradeDetailInfo.setPayFlag(AcTradeDetailInfo.PayFlagEnum.PAID.getValue());
            acTradeDetailInfo.setPayTime(accBankListReturn.getHdlDatetime());
            acTradeDetailInfo.setPayType(accBankListReturn.getCommandType());

            int ret = acTradeDetailInfoService.updatePayInfo(acTradeDetailInfo, oldPayFlag);
            if (ret != 1) {
                logger.error("更新记帐卡交易明细[{}]的支付信息记录数[{}]不为1", acTradeDetailInfo.getDetailNo(), ret);
                throw new RuntimeException("更新记帐卡交易明细失败");
            }
        }

        for (String key : acTradeHandPaySumMaps.keySet()) {
            AcTradeDetailInfo acTradeHandPaySum = acTradeHandPaySumMaps.get(key);
            // 校验手工缴纳金额是否与明细汇总相等，不等，说明指令数据有问题
            List<HandPayment> handPayments = handPaymentService.listByBaccountAndPayTime(acTradeHandPaySum.getBankAccount(), acTradeHandPaySum.getPayTime());
            if (handPayments.isEmpty() || handPayments.size() > 1) {
                logger.error("银行账户[{}]付款时间[{}]对应的手工缴纳通行费记录数[{}]不为1", acTradeHandPaySum.getBankAccount(),
                        acTradeHandPaySum.getPayTime(), handPayments.size());
                throw new RuntimeException("手工缴纳通行费记录数有问题");
            }

            HandPayment handPayment = handPayments.get(0);
            if (acTradeHandPaySum.getRealToll().compareTo(handPayment.getEtcFee()) != 0) {
                logger.error("清单流水(银行账户[{}]付款时间[{}])金额[{}]与对应的手工缴纳通行费金额[{}]不一致",
                        acTradeHandPaySum.getBankAccount(), acTradeHandPaySum.getPayTime(),
                        acTradeHandPaySum.getRealToll(), handPayment.getEtcFee());
                throw new RuntimeException("清单流水金额与手工缴纳通行费金额不一致");
            }
            // 生成重复缴费记录
            handPaymentRepeatService.insert(handPayment, accBankListReturn);
        }
        commandInfoService.deleteByAccBankReturn(accBankListReturn);
        logger.info("返盘结果(银行编码[{}]划扣账户[{}]生成时间[{}]指令类型[{}])处理完成", accBankListReturn.getBankNo(),
                accBankListReturn.getAcbAccount(), accBankListReturn.getGentime(), accBankListReturn.getCommandType());
    }

    @Override
    public List<CardFeeData> listCardFeeData() {
        return cardFeeDataService.listCardFeeData();
    }


    private String[] splitCardFee(String data) {
        data = data.trim();
        if (data.endsWith(";")) {
            data = data.substring(0, data.length()-1);
        }
        return data.split(";", -1);
    }

    @Override
    public void saveReturnFeeData(CardFeeData cardFeeData) {
        if (cardFeeData.getReturnFee() == null) {
            logger.error("粤通卡优惠明细数据[{}]优惠金额为空", cardFeeData.getId());
        }
        if (cardFeeData.getFeeType() == null) {
            logger.error("粤通卡优惠明细数据[{}]优惠类型为空", cardFeeData.getId());
        }

        String[] strReturnFees = splitCardFee(cardFeeData.getReturnFee());
        String[] feeTypes = splitCardFee(cardFeeData.getFeeType());

        if (strReturnFees.length != feeTypes.length) {
            logger.error("粤通卡优惠明细数据[{}]优惠金额[{}]优惠类型[{}]数量不一致", cardFeeData.getId(), cardFeeData.getReturnFee(), cardFeeData.getFeeType());
        }

        Date now = new Date();
        for (int pos = 0; pos < strReturnFees.length; pos++) {
            BigDecimal amt = null;
            try {
                amt = new BigDecimal(strReturnFees[pos]);
            } catch (Exception e) {
                logger.error("粤通卡优惠明细数据[{}]索引[{}]的优惠金额[{}]", cardFeeData.getId(), pos, strReturnFees[pos]);
                throw e;
            }
            ReturnFee returnFee = new ReturnFee();
            returnFee.setCardNo(cardFeeData.getCardCode());
            returnFee.setFeeType(feeTypes[pos]);
            returnFee.setReturnFee(amt);
            returnFee.setState(ReturnFeeStateEnum.notUse.getValue());
            returnFee.setBalanceTime(cardFeeData.getBalanceTime());
            returnFee.setInsertTime(now);

            returnFeeService.save(returnFee);
        }

        int ret = cardFeeDataService.delete(cardFeeData.getId());
        if (ret != 1) {
            logger.error("粤通卡优惠明细数据[{}]更新数[{}]不为1，有问题", cardFeeData.getId(), ret);
            throw new RuntimeException("粤通卡优惠明细数据处理失败");
        }

    }
}
