package com.hgsoft.prepaidC.service;

import com.hgsoft.airrecharge.serviceInterface.IAirRechargeSureService;
import com.hgsoft.common.Enum.PrepaidCardBussinessTradeStateEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.ScAddReqPaychannelEnum;
import com.hgsoft.common.Enum.ScAddSureConfirmTypeEnum;
import com.hgsoft.prepaidC.dao.ScAddDao;
import com.hgsoft.prepaidC.dao.ScAddFixResultDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddFixResult;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.prepaidC.serviceInterface.IAgentPrepaidCService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidHalfPayService;
import com.hgsoft.prepaidC.serviceInterface.IScAddFixResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yangzhongji on 17/9/11.
 */
@Service
public class ScAddFixResultService implements IScAddFixResultService {

    private static final Logger logger = LoggerFactory.getLogger(ScAddFixResultService.class);

    @Resource
    private ScAddFixResultDao scAddFixResultDao;

    @Resource
    private IPrepaidCBussinessService prepaidCBussinessService;
    @Resource
    private IPrepaidHalfPayService prepaidHalfPayService;
    @Resource
    private IAgentPrepaidCService agentPrepaidCService;
    @Resource
    private ScAddDao scAddDao;
    @Resource
    private IAirRechargeSureService airRechargeSureService;

    @Override
    public List<ScAddFixResult> findCheckThroughNotDeal() {
        return scAddFixResultDao.findScAddFixResultByCheckResultAndDealStatus(ScAddFixResult.CheckResult.CHECKED_THROUGH.getValue(), ScAddFixResult.DealStatus.NOT_DEAL.getValue());
    }

    @Override
    public List<ScAddFixResult> findCheckNotThroughNotDeal() {
        return scAddFixResultDao.findScAddFixResultByCheckResultAndDealStatus(ScAddFixResult.CheckResult.CHECKED_NOT_THROUGH.getValue(), ScAddFixResult.DealStatus.NOT_DEAL.getValue());
    }

    @Override
    public void updateRecharge(ScAddFixResult scAddFixResult) {

        PrepaidCBussiness prepaidCBussiness = prepaidCBussinessService.findRechargeBusinessByCardNoTradetime(scAddFixResult.getCardNo(), scAddFixResult.getTimeReq());

        if (prepaidCBussiness == null) {
            logger.error("卡号[{}]交易时间[{}]没有对应的储值卡业务记录", scAddFixResult.getCardNo(), scAddFixResult.getTimeReq());
            return;
        }

        if (!PrepaidCardBussinessTradeStateEnum.save.getValue().equals(prepaidCBussiness.getTradestate())) {
            logger.error("储值卡业务记录[{}]的交易状态[{}]已经结束", prepaidCBussiness.getId(), prepaidCBussiness.getTradestate());
            return;
        }

        if ((PrepaidCardBussinessTypeEnum.preCardMemberRecharge.getValue().equals(prepaidCBussiness.getState())
                || PrepaidCardBussinessTypeEnum.preCardRechargeRegister.getValue().equals(prepaidCBussiness.getState())
                || PrepaidCardBussinessTypeEnum.preCardImRegister.getValue().equals(prepaidCBussiness.getState())
                || PrepaidCardBussinessTypeEnum.airRecharge.getValue().equals(prepaidCBussiness.getState()))
                && "01".equals(scAddFixResult.getDealType())) {
            //normal  nothing to do

        } else if ((PrepaidCardBussinessTypeEnum.preCardRechargeCorrect.getValue().equals(prepaidCBussiness.getState())
                || PrepaidCardBussinessTypeEnum.preCardImRegisterCorrect.getValue().equals(prepaidCBussiness.getState()))
                && "02".equals(scAddFixResult.getDealType())) {
            //normal  nothing to do
        } else {
            logger.error("储值卡业务记录[{}]的类型[{}]与修复记录的交易类型[{}]不一致", prepaidCBussiness.getId(), prepaidCBussiness.getState(), scAddFixResult.getDealType());
            return;
        }

        ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime(scAddFixResult.getDealType(), prepaidCBussiness.getCardno(), prepaidCBussiness.getTradetime());
        if (scAddSure == null) {
            logger.error("储值卡业务记录[{}]对应的充值确认记录不存在", prepaidCBussiness.getId());
            return;
        }


        if (ScAddFixResult.Result.SUCCESS.getValue().equals(scAddFixResult.getResult())) {
            if ("01".equals(scAddFixResult.getDealType())) {
                prepaidCBussiness.setOnlinetradeno(String.valueOf(Long.valueOf(prepaidCBussiness.getOnlinetradeno())+1));
                prepaidCBussiness.setBalance(prepaidCBussiness.getBeforebalance()
                        .add(prepaidCBussiness.getRealprice())
                        .add(prepaidCBussiness.getReturnMoney())
                        .add(prepaidCBussiness.getTransferSum()));
            } else {
                prepaidCBussiness.setOfflinetradeno(String.valueOf(Long.valueOf(prepaidCBussiness.getOfflinetradeno()) + 1));
                prepaidCBussiness.setBalance(prepaidCBussiness.getBeforebalance().add(prepaidCBussiness.getRealprice()));
            }

            if (ScAddReqPaychannelEnum.SELF_OPERATED.getValue().equals(scAddSure.getPayChannel())) {
                prepaidHalfPayService.saveHalfTrue(prepaidCBussiness);
            } else if (ScAddReqPaychannelEnum.AGENT.getValue().equals(scAddSure.getPayChannel())) {
                agentPrepaidCService.saveHalfTrue(prepaidCBussiness);
            } else if (ScAddReqPaychannelEnum.AIR_RECHARGE.getValue().equals(scAddSure.getPayChannel())) {
                airRechargeSureService.saveHalfTrue(prepaidCBussiness);
            } else {
                logger.error("充值确认记录里的支付渠道[{}]非法", scAddSure.getPayChannel());
                return;
            }
            scAddDao.update("update csms_sc_add_sure set confirmType=? where id=?", ScAddSureConfirmTypeEnum.backSure.getValue(), scAddSure.getId());

        } else if (ScAddFixResult.Result.FAIL.getValue().equals(scAddFixResult.getResult())) {
            prepaidCBussiness.setBalance(prepaidCBussiness.getBeforebalance());
            if (ScAddReqPaychannelEnum.SELF_OPERATED.getValue().equals(scAddSure.getPayChannel())) {
                prepaidHalfPayService.saveHalfFalse(prepaidCBussiness);
            } else if (ScAddReqPaychannelEnum.AGENT.getValue().equals(scAddSure.getPayChannel())) {
                agentPrepaidCService.saveHalfFalse(prepaidCBussiness);
            } else if (ScAddReqPaychannelEnum.AIR_RECHARGE.getValue().equals(scAddSure.getPayChannel())) {
                airRechargeSureService.saveHalfFalse(prepaidCBussiness);
            } else {
                logger.error("充值确认记录里的支付渠道[{}]非法", scAddSure.getPayChannel());
                return;
            }
            scAddDao.update("update csms_sc_add_sure set confirmType=? where id=?", ScAddSureConfirmTypeEnum.backSure.getValue(), scAddSure.getId());
        } else if (ScAddFixResult.Result.ERROR.getValue().equals(scAddFixResult.getResult())) {
            //还原校验，下次重新校验
            scAddDao.updateScAddReqSendHalfNotSure(scAddFixResult.getReqId());
        } else {
            logger.error("半条修复记录里的确认结果[{}]非法", scAddFixResult.getResult());
            return;
        }
        scAddFixResult.setDealStatus(ScAddFixResult.DealStatus.DEALT.getValue());
        scAddFixResult.setDealtime(new Date());
        scAddFixResultDao.updateDealed(scAddFixResult);
    }

    public void updateNotSure(ScAddFixResult scAddFixResult) {

        scAddDao.updateScAddReqSendHalfNotSure(scAddFixResult.getReqId());

        scAddFixResult.setDealStatus(ScAddFixResult.DealStatus.DEALT.getValue());
        scAddFixResult.setDealtime(new Date());
        scAddFixResultDao.updateDealed(scAddFixResult);
    }
}
