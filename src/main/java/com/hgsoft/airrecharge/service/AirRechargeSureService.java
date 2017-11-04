package com.hgsoft.airrecharge.service;

import com.hgsoft.airrecharge.serviceInterface.IAirRechargeSureService;
import com.hgsoft.clearInterface.dao.StoreCardRechargeDao;
import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.common.Enum.PrepaidCardBussinessTradeStateEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.ScAddSureConfirmTypeEnum;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.invoice.dao.AddBillDao;
import com.hgsoft.prepaidC.dao.InvoiceChangeFlowDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.ReturnFeeDao;
import com.hgsoft.prepaidC.dao.ScAddDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.ScAddSure;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCBussinessService;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.unifiedInterface.service.AgentPrepaidCUnifiedInterfaceService;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 吴锡霖
 *         file : AirRechargeSureService.java
 *         date : 2017-06-29
 *         time : 9:44
 */
@Service
public class AirRechargeSureService implements IAirRechargeSureService {

    @Resource
    private ScAddDao scAddDao;
    @Resource
    private ReturnFeeDao returnFeeDao;
    @Resource
    private PrepaidCBussinessDao prepaidCBussinessDao;
    @Resource
    private InvoiceChangeFlowDao invoiceChangeFlowDao;
    @Resource
    private SequenceUtil sequenceUtil;
    @Resource
    private AddBillDao addBillDao;
    @Resource
    private StoreCardRechargeDao storeCardRechargeDao;
    @Resource
    private IPrepaidCBussinessService prepaidCBussinessService;
    @Resource
    private IPrepaidCService prepaidCService;
    @Resource
    private AgentPrepaidCUnifiedInterfaceService agentPrepaidCUnifiedInterfaceService;

    @Override
    public Long saveRechargeSure(PrepaidC prepaidC, Date addReqTimeCheck,
                                 ScAddSure scAddSure, ScAddSureSend addSureSend) {

        //更新回退表为完成
//        returnFeeDao.updateState(prepaidC.getCardNo(), "3");


        //更新业务记录表为完成
        PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao
                .findByCardNoAndTradeTime(prepaidC.getCardNo(),
                        scAddSure.getTimeReq(), PrepaidCardBussinessTypeEnum.airRecharge.getValue());
        prepaidCBussiness.setCheckcode(scAddSure.getCheckCode());
        prepaidCBussiness.setMac(scAddSure.getMac());
        prepaidCBussiness.setTac(scAddSure.getTac());
        prepaidCBussiness.setBalance(addSureSend.getBalSur());
        prepaidCBussiness.setOnlinetradeno(String.valueOf(scAddSure.getOnlineTradenoSur()));
        prepaidCBussiness.setOfflinetradeno(String.valueOf(scAddSure.getOfflineTradenoSur()));
        prepaidCBussiness.setBalance(addSureSend.getBalSur());
        prepaidCBussiness.setTradestate("2");
        //完成储值卡业务记录
        if (prepaidCBussinessDao.updateTradeState(prepaidCBussiness,
                PrepaidCardBussinessTradeStateEnum.save.getValue()) != 1) {
            throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
        }

        // 完成回退金额
        if (scAddSure.getReturnMoneyReq().compareTo(BigDecimal.ZERO) != 0) {
            returnFeeDao.updateRechargeSuccessState(scAddSure.getTimeSur(),
                    prepaidCBussiness.getId());
        }
        //添加充值账单
        agentPrepaidCUnifiedInterfaceService.saveAddBill(prepaidC,
                prepaidCBussiness, false);
        //给清算数据    充值	给铭鸿
        agentPrepaidCUnifiedInterfaceService
                .saveStoreCardRecharge(prepaidCBussiness, scAddSure);
        scAddDao.updateScAddSure(scAddSure);
        //给铭宏清算充值数据
        scAddDao.updateScAddSureSend(addSureSend);
        return prepaidCBussiness.getId();
    }

    @Override
    public Map<String, Object> saveHalfTrue(PrepaidCBussiness oldPrepaidCBussiness) {
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
            prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().abs());

            prepaidCBussiness.setTermcode(oldPrepaidCBussiness.getTermcode());
            prepaidCBussiness.setOnlinetradeno(oldPrepaidCBussiness.getOnlinetradeno());
            prepaidCBussiness.setOfflinetradeno(oldPrepaidCBussiness.getOfflinetradeno());
            prepaidCBussiness.setBalance(oldPrepaidCBussiness.getBalance());

            //完成储值卡业务记录
            if (prepaidCBussinessService.updateTradeStateSuccess(prepaidCBussiness) != 1) {
                throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
            }
            Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
            //完成回退金额
            if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
                returnFeeDao.updateNotUseState(prepaidCBussiness.getId());
            }

            //添加充值账单
            PrepaidC prepaidC = prepaidCService.findByPrepaidCNo(prepaidCBussiness.getCardno());

            agentPrepaidCUnifiedInterfaceService.saveAddBill(prepaidC,
                    prepaidCBussiness, false);

            oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
            oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
            oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
            oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());

            // 给清算数据，插入后半部分数据
            ScAddSure scAddSure = scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "01",
                    addSureTime, "3",
                    ScAddSureConfirmTypeEnum.backSure.getValue());
            // 写给原清算的数据
            scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "01",
                    addSureTime, "3");

			/*ScAddSure scAddSure = scAddDao.findScAddSureByTypeCardTime("01",
                    prepaidCBussiness.getCardno(),
					prepaidCBussiness.getTradetime());*/
            // 给清算数据 充值 给铭鸿
            agentPrepaidCUnifiedInterfaceService
                    .saveStoreCardRecharge(prepaidCBussiness, scAddSure);
            m.put("success", true);
            return m;
        } catch (ApplicationException e) {
            throw new ApplicationException("半条确认成功异常", e);
        }
    }

    @Override
    public Map<String, Object> saveHalfFalse(PrepaidCBussiness oldPrepaidCBussiness) {
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findById(oldPrepaidCBussiness.getId());
            prepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice().abs());

            prepaidCBussiness.setBalance(oldPrepaidCBussiness.getBalance());

            //完成储值卡业务记录
            if (prepaidCBussinessService.updateTradeStateFail(prepaidCBussiness) != 1) {
                throw new ApplicationException("业务记录[" + prepaidCBussiness.getId() + "]数据发生变化");
            }
            Date addSureTime = new Date(); //临时存储充值确认时间，后边使用
            //完成回退金额
            if (prepaidCBussiness.getReturnMoney().compareTo(BigDecimal.ZERO) != 0) {
                returnFeeDao.updateNotUseState(prepaidCBussiness.getId());
            }

            oldPrepaidCBussiness.setTradetime(prepaidCBussiness.getTradetime());
            oldPrepaidCBussiness.setRealprice(prepaidCBussiness.getRealprice());
            oldPrepaidCBussiness.setTransferSum(prepaidCBussiness.getTransferSum());
            oldPrepaidCBussiness.setReturnMoney(prepaidCBussiness.getReturnMoney());
            // 插入后半部分数据
            scAddDao.updateScAddSureByBussiness(oldPrepaidCBussiness, "01",
                    addSureTime, "1",
                    ScAddSureConfirmTypeEnum.backSure.getValue());
            // 写给清算的数据
            scAddDao.updateScAddSureSendByBussiness(oldPrepaidCBussiness, "01",
                    addSureTime, "1");

            m.put("success", true);
            return m;
        } catch (ApplicationException e) {
            throw new ApplicationException("半条确认失败异常", e);
        }
    }

}
