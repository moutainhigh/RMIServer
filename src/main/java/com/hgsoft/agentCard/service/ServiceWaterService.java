package com.hgsoft.agentCard.service;

import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.agentCard.serviceInterface.IServiceWaterService;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ： 孙晓伟
 *         file : ServiceWaterService.java
 *         date : 2017/6/14
 *         time : 11:55
 */
@Service
public class ServiceWaterService implements IServiceWaterService{
    @Resource
    private CustomerDao customerDao;
    @Resource
    private PrepaidCBussinessDao prepaidCBussinessDao;
    @Resource
    private SubAccountInfoDao subAccountInfoDao;
    @Resource
    private AccountCApplyDao accountCApplyDao;
    @Resource
    private ServiceWaterDao serviceWaterDao;
    @Resource
    SequenceUtil sequenceUtil;
    @Resource
    private AccountCBussinessDao accountCBussinessDao;

    private static Logger logger = Logger.getLogger(CardBusinessInfoService.class.getName());


    @Override
    public void saveAcServiceWater(Customer customer, String cardNo,String accouncCBussinessType,String serviceWaterType,String remark) {

        try {
            AccountCApply accountCApply = accountCApplyDao.findByCardNo(cardNo);
            SubAccountInfo subAccountInfo = subAccountInfoDao.findByApplyId(accountCApply.getId());

            // 记帐卡业务记录
            AccountCBussiness accountCBussiness = new AccountCBussiness();
            BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
            accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
            accountCBussiness.setUserId(customer.getId());
            accountCBussiness.setAccountId(subAccountInfo.getId());
            accountCBussiness.setState(accouncCBussinessType);		//业务记录类型
            accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
            accountCBussiness.setTradeTime(new Date());
            accountCBussiness.setOperId(accountCApply.getOperId());
            accountCBussiness.setPlaceId(accountCApply.getPlaceId());
            //新增的字段
            accountCBussiness.setOperName(accountCApply.getOperName());
            accountCBussiness.setOperNo(accountCApply.getOperNo());
            accountCBussiness.setPlaceName(accountCApply.getPlaceName());
            accountCBussiness.setPlaceNo(accountCApply.getPlaceNo());
            accountCBussiness.setBusinessId(accountCApply.getHisseqId());
            accountCBussinessDao.save(accountCBussiness);

            //调整的客服流水（新）
            ServiceWater serviceWater = new ServiceWater();
            Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

            serviceWater.setId(serviceWater_id);

            serviceWater.setCustomerId(customer.getId());
            serviceWater.setUserNo(customer.getUserNo());
            serviceWater.setUserName(customer.getOrgan());
            serviceWater.setSerType(serviceWaterType);//107:开户申请
            serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
            serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
            serviceWater.setAccountCBussinessId(accountCBussiness.getId());
            serviceWater.setOperId(accountCBussiness.getOperId());
            serviceWater.setOperName(accountCBussiness.getOperName());
            serviceWater.setOperNo(accountCBussiness.getOperNo());
            serviceWater.setPlaceId(accountCBussiness.getPlaceId());
            serviceWater.setPlaceName(accountCBussiness.getPlaceName());
            serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
            serviceWater.setRemark(remark);
            serviceWater.setOperTime(new Date());

            serviceWaterDao.save(serviceWater);

        } catch (ApplicationException e) {
            e.printStackTrace();
            logger.error(e.getMessage()+"生成记帐卡客服流水失败");
            throw new ApplicationException();
        }


    }

    @Override
    public void savePcServiceWater(Customer customer, String cardNo, String ServiceWaterType,String remark) {
        try {
            PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findByCardNo(cardNo);
            //客户服务流水
            ServiceWater serviceWater = new ServiceWater();
            serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
            if(customer!=null)serviceWater.setCustomerId(customer.getId());
            if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
            if(customer!=null)serviceWater.setUserName(customer.getOrgan());
            serviceWater.setCardNo(prepaidCBussiness.getCardno());
            serviceWater.setSerType(ServiceWaterType);//储值卡发票类型变更
            serviceWater.setPrepaidCBussinessId(prepaidCBussiness.getId());
            serviceWater.setOperId(prepaidCBussiness.getOperid());
            serviceWater.setOperNo(prepaidCBussiness.getOperNo());
            serviceWater.setOperName(prepaidCBussiness.getOperName());
            serviceWater.setPlaceId(prepaidCBussiness.getPlaceid());
            serviceWater.setPlaceNo(prepaidCBussiness.getPlaceNo());
            serviceWater.setPlaceName(prepaidCBussiness.getPlaceName());
            serviceWater.setOperTime(new Date());
            serviceWater.setRemark(remark);
            serviceWaterDao.save(serviceWater);
        } catch (ApplicationException e) {
            e.printStackTrace();
            logger.error(e.getMessage()+"生成储值卡客服流水失败");
            throw new ApplicationException();
        }

    }
}
