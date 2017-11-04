package com.hgsoft.accountC.service;

import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.NewCardApplyDao;
import com.hgsoft.accountC.dao.NewCardApplyHisDao;
import com.hgsoft.accountC.entity.*;
import com.hgsoft.accountC.serviceInterface.INewCardApplyService;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.common.Enum.AccountTypeTypeEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.accountC.AccNewCardApplyReceipt;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NewCardApplyService implements INewCardApplyService{
    @Resource
    private AccountCApplyDao accountCApplyDao;

    @Resource
    private NewCardApplyDao newCardApplyDao;

    @Resource
    private AccountCBussinessDao accountCBussinessDao;

    @Resource
    private NewCardApplyHisDao newCardApplyHisDao;

    @Resource
    private ServiceWaterDao serviceWaterDao;

    @Resource
    private ReceiptDao receiptDao;

    @Resource
    private SequenceUtil sequenceUtil;
    NewCardVehicle newCardVehicle=new NewCardVehicle();

    private static Logger logger = Logger.getLogger(NewCardApplyService.class.getName());

    public Pager findNewCardApplyListByPager(Pager pager,Customer customer,String bankAccount){
        pager = newCardApplyDao.findNewCardApplyListByPager(pager, customer, bankAccount);

        return pager;
    }

    //查找记帐卡申请表中审批状态为通过的记帐卡
    public List<Map<String, Object>> listAccountCApplys(Customer customer,String debitCardType){
        return newCardApplyDao.listAccountCApplys(customer,debitCardType);
    }

    //查找记帐卡申请表中审批状态为通过的记帐卡
    public List<Map<String, Object>> findAccountCApplys(Customer customer,String bankname){
        return newCardApplyDao.findAccountCApplys(customer,bankname);
    }

    public Map<String, Object> findAccountCApplySomeInfo(Customer customer,String bankAccount){
        return newCardApplyDao.findAccountCApplySomeInfo(customer,bankAccount);
    }


    public AccountCApply findAccountCApplyByBankAccount(String bankAccount){
        return accountCApplyDao.findByBankAccount(bankAccount);
    }

    public void saveNewCardApply(NewCardApply newCardApply, Customer customer, String newCardVehicleStr,Map<String,Object> params){
        try {
            //1.保存新增卡申请
            BigDecimal SEQ_CSMSNewCardapply_NO = sequenceUtil.getSequence("SEQ_CSMSNewCardapply_NO");
            newCardApply.setId(Long.valueOf(SEQ_CSMSNewCardapply_NO.toString()));
            //newCardApply.setBail(new BigDecimal("100"));//单卡保证金
            //// TODO: 2017/4/13 这里的两个保证金目前没用
            newCardApply.setBail(new BigDecimal("0"));
            newCardApply.setTruckBail(new BigDecimal("0"));
            newCardApply.setAppState("1");
            newCardApply.setOperTime(new Date());
            newCardApply.setApplyTime(new Date());
            newCardApplyDao.saveNewCardApply(newCardApply);


            //2.保存新增卡车辆信息
            if(!"".equals(newCardVehicleStr)&&newCardVehicleStr!=null){
                String[] newCardVehicleListArray = newCardVehicleStr.split(";");
                if(newCardVehicleListArray.length!=0){
                    for (int i=0;i<newCardVehicleListArray.length;i++){
                        String[] newCardVehicleArray = newCardVehicleListArray[i].split(",");
                        if(newCardVehicleArray.length!=0){

                            BigDecimal SEQ_CSMSNewCardVehicle_NO = sequenceUtil.getSequence("SEQ_CSMS_NewCard_Vehicle_NO");
                            newCardVehicle.setId(Long.valueOf(SEQ_CSMSNewCardVehicle_NO.toString()));
                            newCardVehicle.setNewCardApplyid(newCardApply.getId());
                            newCardVehicle.setState("1");
                            newCardVehicle.setVehiclePlate(newCardVehicleArray[0]);
                            newCardVehicle.setVehicleColor(newCardVehicleArray[1]);
                            newCardVehicle.setBailType(newCardVehicleArray[2]);
                            newCardVehicle.setBail(new BigDecimal(newCardVehicleArray[3]).multiply(new BigDecimal("100")));
                            newCardApplyDao.saveNewCardVehicle(newCardVehicle);

                        }

                    }
                }

            }


            //3、保存业务操作表
            AccountCBussiness accountCBussiness = new AccountCBussiness();
            BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
            accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
            accountCBussiness.setUserId(customer.getId());
            accountCBussiness.setState("21");
            accountCBussiness.setRealPrice(new BigDecimal("0"));// 业务费用
            accountCBussiness.setTradeTime(new Date());
            accountCBussiness.setOperId(newCardApply.getOperId());
            accountCBussiness.setPlaceId(newCardApply.getPlaceId());
            //新增的字段
            accountCBussiness.setOperName(newCardApply.getOperName());
            accountCBussiness.setOperNo(newCardApply.getOperNo());
            accountCBussiness.setPlaceName(newCardApply.getPlaceName());
            accountCBussiness.setPlaceNo(newCardApply.getPlaceNo());
            accountCBussiness.setBusinessId(newCardApply.getHisseqId());
            accountCBussinessDao.save(accountCBussiness);


            AccountCApply accountCApply = accountCApplyDao.findById(newCardApply.getApplyId());

            //调整的客服流水
            ServiceWater serviceWater = new ServiceWater();
            Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

            serviceWater.setId(serviceWater_id);

            serviceWater.setCustomerId(customer.getId());
            serviceWater.setUserNo(customer.getUserNo());
            serviceWater.setUserName(customer.getOrgan());
            serviceWater.setSerType("213");//213记帐卡新增卡申请
            serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
            serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
            serviceWater.setAccountCBussinessId(accountCBussiness.getId());
            serviceWater.setOperId(accountCBussiness.getOperId());
            serviceWater.setOperName(accountCBussiness.getOperName());
            serviceWater.setOperNo(accountCBussiness.getOperNo());
            serviceWater.setPlaceId(accountCBussiness.getPlaceId());
            serviceWater.setPlaceName(accountCBussiness.getPlaceName());
            serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
            serviceWater.setRemark("自营客服系统：记帐卡新增卡申请");
            serviceWater.setOperTime(new Date());

            serviceWaterDao.save(serviceWater);

            //记帐卡申请回执
            AccNewCardApplyReceipt accNewCardApplyReceipt = new AccNewCardApplyReceipt();
            accNewCardApplyReceipt.setTitle("记帐卡申请回执");
            accNewCardApplyReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
            accNewCardApplyReceipt.setApplyAccountType(AccountTypeTypeEnum.getNameByValue(accountCApply.getAccountType()));
            accNewCardApplyReceipt.setApplyLinkman(accountCApply.getLinkman());
            accNewCardApplyReceipt.setApplyTel(accountCApply.getTel());
            accNewCardApplyReceipt.setApplyAccName(accountCApply.getAccName());
            accNewCardApplyReceipt.setApplyBankAccount(accountCApply.getBankAccount());
            accNewCardApplyReceipt.setApplyBankName(accountCApply.getBank());
            accNewCardApplyReceipt.setApplyReqcount(newCardApply.getReqcount()+"");
            Receipt receipt = new Receipt();
            receipt.setTypeCode(AccountCBussinessTypeEnum.accNewCardApply.getValue());
            receipt.setTypeChName(AccountCBussinessTypeEnum.accNewCardApply.getName());
            this.saveReceipt(receipt,accountCBussiness,accNewCardApplyReceipt,customer);

        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"新增卡申请失败");
            e.printStackTrace();
            throw new ApplicationException();
        }
    }

    public void updateNewCardApply(NewCardApply newCardApply,Customer customer){
        try {
            //1、将新增卡申请移到历史表
            NewCardApplyHis newCardApplyHis = new NewCardApplyHis();
            BigDecimal SEQ_CSMSNewCardapplyhis_NO = sequenceUtil.getSequence("SEQ_CSMSNewCardapplyhis_NO");
            newCardApplyHis.setId(Long.valueOf(SEQ_CSMSNewCardapplyhis_NO.toString()));
            newCardApplyHis.setGenReason("1");
            newCardApplyHisDao.saveHis(newCardApplyHis, newCardApply);
            //accountCapplyHis.setGenTime(genTime);

            //2、修改新增卡申请表，要把历史序列id也update进去
            NewCardApply oldNewCardApply = newCardApplyDao.findById(newCardApply.getId());
            newCardApply.setHisseqId(newCardApplyHis.getId());
            newCardApply.setApprOver(oldNewCardApply.getApprOver());
            newCardApply.setAppState("1");
            //newCardApply.setOperId(oldNewCardApply.getOperId());
            //newCardApply.setPlaceId(oldNewCardApply.getPlaceId());
            //newCardApply.setOperName(oldNewCardApply.getOperName());
            //newCardApply.setOperNo(oldNewCardApply.getOperNo());
            //newCardApply.setPlaceName(oldNewCardApply.getPlaceName());
            //newCardApply.setPlaceNo(oldNewCardApply.getPlaceNo());
            newCardApply.setOperTime(new Date());
            newCardApply.setAppTime(oldNewCardApply.getAppTime());
            //newCardApply.setBail(oldNewCardApply.getBail());
            newCardApply.setBail(oldNewCardApply.getBail());
            newCardApply.setTruckBail(oldNewCardApply.getTruckBail());

            newCardApplyDao.update(newCardApply);

            AccountCBussiness accountCBussiness = new AccountCBussiness();
            BigDecimal SEQ_CSMSAccountCbussiness_NO = sequenceUtil.getSequence("SEQ_CSMSAccountCbussiness_NO");
            accountCBussiness.setId(Long.valueOf(SEQ_CSMSAccountCbussiness_NO.toString()));
            accountCBussiness.setUserId(customer.getId());
            accountCBussiness.setState("27");//TODO 暂时定为新赠卡申请修改
            accountCBussiness.setRealPrice(new BigDecimal("100"));// 业务费用
            accountCBussiness.setTradeTime(new Date());
            accountCBussiness.setOperId(newCardApply.getOperId());
            accountCBussiness.setPlaceId(newCardApply.getPlaceId());
            //新增的字段
            accountCBussiness.setOperName(newCardApply.getOperName());
            accountCBussiness.setOperNo(newCardApply.getOperNo());
            accountCBussiness.setPlaceName(newCardApply.getPlaceName());
            accountCBussiness.setPlaceNo(newCardApply.getPlaceNo());
            accountCBussiness.setBusinessId(newCardApply.getHisseqId());
            accountCBussinessDao.save(accountCBussiness);


            AccountCApply accountCApply = accountCApplyDao.findById(newCardApply.getApplyId());

            //调整的客服流水
            ServiceWater serviceWater = new ServiceWater();
            Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

            serviceWater.setId(serviceWater_id);

            serviceWater.setCustomerId(customer.getId());
            serviceWater.setUserNo(customer.getUserNo());
            serviceWater.setUserName(customer.getOrgan());
            serviceWater.setSerType("214");//214记帐卡新增卡申请修改
            serviceWater.setBankAccount(accountCApply.getBankAccount());//银行账号
            serviceWater.setBankNo(accountCApply.getObaNo());//银行编码?
            serviceWater.setAccountCBussinessId(accountCBussiness.getId());
            serviceWater.setOperId(accountCBussiness.getOperId());
            serviceWater.setOperName(accountCBussiness.getOperName());
            serviceWater.setOperNo(accountCBussiness.getOperNo());
            serviceWater.setPlaceId(accountCBussiness.getPlaceId());
            serviceWater.setPlaceName(accountCBussiness.getPlaceName());
            serviceWater.setPlaceNo(accountCBussiness.getPlaceNo());
            serviceWater.setRemark("自营客服系统：记帐卡新增卡申请修改");
            serviceWater.setOperTime(new Date());

            serviceWaterDao.save(serviceWater);

        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"修改新增卡信息失败");
            e.printStackTrace();
            throw new ApplicationException();
        }

    }

    public NewCardApply findNewCardApplyById(Long newCardApplyId){
        return newCardApplyDao.findById(newCardApplyId);
    }

    public boolean canApply(String bankAccount,Long customerId){

        List<Map<String, Object>> list = newCardApplyDao.findByBankAccount(bankAccount,customerId);
        if(list.size()==0){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 获得营运的保证金类型和对应保证金金额
     * @return
     */
    @Override
    public Map<String, Object> marginPutList(){
        try {
            String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_marginPutList.do";
//			String url = "http://10.173.232.26:8080/OMSClient/otherInterface/otherInterface_marginPutList.do";
            JSONObject json = new JSONObject();
            json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
            json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
            json.accumulate("timer", System.currentTimeMillis());
            String data = DesEncrypt.getInstance().encrypt(json.toString());
            return HttpUtil.callClientByHTTP(url, "au_token="+data, "POST");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pager findNewCardVehiclePager(Long newCardApplyId,Pager pager){
        pager = newCardApplyDao.findNewCardVehiclePager(newCardApplyId, pager);
        return pager;
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
