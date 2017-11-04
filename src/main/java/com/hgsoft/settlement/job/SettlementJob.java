package com.hgsoft.settlement.job;

import com.hgsoft.settlement.entity.AcTradeDetailInfo;
import com.hgsoft.settlement.entity.CardFeeData;
import com.hgsoft.settlement.entity.CommandInfo;
import com.hgsoft.settlement.entity.MonthlyReg;
import com.hgsoft.settlement.serviceinterface.ISettlementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yangzhongji on 17/6/15.
 */
@Component
public class SettlementJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettlementJob.class);

    @Resource
    private ISettlementService settlementService;

    public final void genTradeDetail() {

        try {
            settlementService.batchSaveScTradeDetailFromScAddSure();
        } catch (Exception e) {
            LOGGER.error("生成充值记录清单", e);
        }

        try {
            settlementService.batchSaveScTradeDetailFromCardInSettleDetail();
        } catch (Exception e) {
            LOGGER.error("生成省内储值卡消费流水清单", e);
        }

        try {
            settlementService.batchSaveScTradeDetailFromCardOutSettleDetail();
        } catch (Exception e) {
            LOGGER.error("生成外省储值卡消费流水清单", e);
        }

        try {
            settlementService.batchSaveAcTradeDetailFromCardInSettleDetail();
        } catch (Exception e) {
            LOGGER.error("生成省内记账卡消费流水清单", e);
        }

        try {
            settlementService.batchSaveAcTradeDetailFromCardOutSettleDetail();
        } catch (Exception e) {
            LOGGER.error("生成外省记账卡消费流水清单", e);
        }

    }

    public final void genTradeBill() {
        List<MonthlyReg> monthlyRegs = settlementService.findAllCheckedAndNotGenOfMonthlyRegs();
        for (MonthlyReg monthlyReg : monthlyRegs) {
            try {
                LOGGER.info("生成{}账单开始", monthlyReg.getSettleMonth());
                settlementService.saveMonthlyBill(monthlyReg);
                LOGGER.info("生成{}账单结束", monthlyReg.getSettleMonth());
            } catch (Exception e) {
                LOGGER.error("生成账单", e);
            }
        }
    }

    public final void dealCommandInfo() {
        List<CommandInfo> commandInfos = settlementService.findTollFeeCommand();
        CommandInfo lastCommandInfo = null;
        List<CommandInfo> tempCommandInfos = new LinkedList<CommandInfo>();

        for (CommandInfo commandInfo : commandInfos) {
            try {
                if (lastCommandInfo == null) {
                    lastCommandInfo = commandInfo;
                }

                if (!lastCommandInfo.isSameAcBankReturn(commandInfo)) {
                    try {
                        settlementService.updateHandPayment(tempCommandInfos);
                    } catch (Exception e) {
                        LOGGER.error("处理失败", e);
                    } finally {
                        tempCommandInfos.clear();
                    }
                }
                tempCommandInfos.add(commandInfo);

            } catch (Exception e) {
                LOGGER.error("更新指令流水", e);
            }
        }
        try {
            settlementService.updateHandPayment(tempCommandInfos);
        } catch (Exception e) {
            LOGGER.error("处理失败", e);
        } finally {
            tempCommandInfos.clear();
        }
    }

    public final void dealReturnFeeData() {
        List<CardFeeData> cardFeeDatas = settlementService.listCardFeeData();
        if (cardFeeDatas == null) {
            return;
        }

        for (CardFeeData cardFeeData : cardFeeDatas) {
            try {
                LOGGER.info("处理优惠回退记录[{}]-开始", cardFeeData.getId());
                settlementService.saveReturnFeeData(cardFeeData);
                LOGGER.info("处理优惠回退记录[{}]-结束", cardFeeData.getId());
            } catch (Exception e) {
                LOGGER.error("处理失败", e);
            }
        }
    }
}
