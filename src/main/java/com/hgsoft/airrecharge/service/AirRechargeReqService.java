package com.hgsoft.airrecharge.service;

import com.hgsoft.airrecharge.serviceInterface.IAirRechargeReqService;
import com.hgsoft.clearInterface.dao.ScaddSendDao;
import com.hgsoft.clearInterface.entity.ScAddSureSend;
import com.hgsoft.clearInterface.entity.ScaddSend;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.common.Enum.ReturnFeeStateEnum;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.dao.ReturnFeeDao;
import com.hgsoft.prepaidC.dao.ScAddDao;
import com.hgsoft.prepaidC.entity.*;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.SequenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : 吴锡霖
 * file : AirRechargeReqService.java
 * date : 2017-06-28
 * time : 14:40
 */
@Service
public class AirRechargeReqService implements IAirRechargeReqService {

    private static Logger logger = LoggerFactory.getLogger(AirRechargeReqService.class);

    @Resource
    private SequenceUtil sequenceUtil;

    @Resource
    private ScaddSendDao scaddSendDao;
    @Resource
    private ScAddDao scAddDao;
    @Resource
    private ReturnFeeDao returnFeeDao;
    @Resource
    private PrepaidCBussinessDao prepaidCBussinessDao;
    @Resource
    private PrepaidCDao prepaidCDao;

	@Override
	public void save(PrepaidC prepaidC, ScAddReq scAddReq, ScAddSure scAddSure,
			ScaddSend scaddSend, ScAddSureSend scAddSureSend,
			CusPointPoJo cusPointPoJo, SysAdmin sysAdmin) {

	    Long businessId = sequenceUtil.getSequenceLong(
                "SEQ_CSMS_PrePaidC_bussiness_NO");
        if (scAddReq.getReturnMoneyReq().compareTo(BigDecimal.ZERO) != 0) {
            List<ReturnFee> returnFeeList = returnFeeDao.findByCardNoState(
                    prepaidC.getCardNo(), ReturnFeeStateEnum.notUse.getValue());

            BigDecimal returnMoney = BigDecimal.ZERO;
            for (ReturnFee returnFee : returnFeeList) {
                returnMoney = returnMoney.add(returnFee.getReturnFee());
            }
			if (scAddReq.getReturnMoneyReq().compareTo(returnMoney) != 0) {
				logger.warn("系统回退金额[{}]与申请的回退金额[{}]不一致", returnMoney,
						scAddReq.getReturnMoneyReq());
                throw new ApplicationException("系统回退金额与申请的回退金额不一致");
            }

            //锁定回退资金
            for (ReturnFee returnFee : returnFeeList) {
                if (returnFeeDao.updateLockState(businessId, scAddReq.getDealtimeReq(), returnFee.getId()) != 1) {
                    throw new ApplicationException("回退金额数据发生变更，请重新操作");
                }
            }
        }
        
        //保存四个表的记录
        scAddReq.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDREQ_NO"));
        scaddSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSscaddsend_NO"));
        scAddSure.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDSURE_NO"));
        scAddSureSend.setId(sequenceUtil.getSequenceLong("SEQ_CSMSSCADDSURESEND_NO"));

        scAddDao.saveScAddReq(scAddReq);
        scAddDao.saveScAddSure(scAddSure);
        scAddDao.saveScAddSureSend(scAddSureSend);
        scaddSendDao.save(scaddSend);

        //插入储值卡业务记录表  交易状态为：1，
        PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
        prepaidCBussiness.setId(businessId);
        prepaidCBussiness.setUserid(prepaidC.getCustomerID());
        prepaidCBussiness.setCardno(prepaidC.getCardNo());
        prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.airRecharge.getValue()); //增加对应的数据字典
        prepaidCBussiness.setOperid(sysAdmin.getId());
        prepaidCBussiness.setOperName(sysAdmin.getUserName());
        prepaidCBussiness.setOperNo(sysAdmin.getStaffNo());
        prepaidCBussiness.setPlaceid(cusPointPoJo.getCusPoint());
        prepaidCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
        prepaidCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
        prepaidCBussiness.setTradetime(scAddReq.getDealtimeReq());
        prepaidCBussiness.setTradestate("1");
//        prepaidCBussiness.setBalance(scAddReq.getBalReq());
        prepaidCBussiness.setBeforebalance(scAddReq.getBalReq());
        prepaidCBussiness.setTermcode(scAddReq.getTermnoReq());
        prepaidCBussiness.setTransferSum(scAddReq.getTransfersumReq());
        prepaidCBussiness.setRealprice(scAddReq.getMoneyReq());
        prepaidCBussiness.setReturnMoney(scAddReq.getReturnMoneyReq());
        prepaidCBussiness.setOnlinetradeno(scAddReq.getOnlineTradeNoReq().toString());
        prepaidCBussiness.setOfflinetradeno(scAddReq.getOfflineTradeNoReq().toString());
        prepaidCBussiness.setMac(scAddSureSend.getMac());
        prepaidCBussiness.setInvoicestate("1");
        prepaidCBussiness.setBusinessId(scAddReq.getId());
        
        prepaidCBussinessDao.saveWithOutReceipt(prepaidCBussiness); 
        //更新为卡为非首充
        prepaidC.setFirstRecharge("0");
        prepaidCDao.update(prepaidC);
    }
}
