package com.hgsoft.daysettle.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.daysettle.dao.BeforeDaySetDao;
import com.hgsoft.daysettle.entity.BeforeDaySet;
import com.hgsoft.daysettle.serviceInterface.IBeforeDaySetService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.dao.TagBusinessRecordDao;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.dao.TagInfoHisDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.unifiedInterface.serviceInterface.IUnifiedInterface;
import com.hgsoft.unifiedInterface.util.UnifiedParam;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;

@Service
public class BeforeDaySetService implements IBeforeDaySetService{
	
	private static Logger logger = Logger.getLogger(BeforeDaySetService.class.getName());

	@Resource
	private BeforeDaySetDao beforeDaySetDao;
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	@Resource
	private TagInfoDao tagInfoDao;
	
	@Resource
	private TagInfoHisDao tagInfoHisDao;
	
	@Resource
	private PrepaidCDao prepaidCDao;
	
	@Resource
	private AccountCInfoDao accountCInfoDao;
	
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	
	@Resource
	private IUnifiedInterface unifiedInterfaceService;
	
	@Resource
	private TagBusinessRecordDao tagBusinessRecordDao;
	
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	
	@Override
	public Pager findByPager(Pager pager,Date starTime ,Date endTime,Customer customer,VehicleInfo vehicleInfo,String type,String settleDay,Long placeId,Long id,
			List<String> pointList){
		try {
			return beforeDaySetDao.list(pager, starTime, endTime, customer, vehicleInfo, type, settleDay,placeId,id,pointList);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"日结前修正查询失败");
			e.printStackTrace();
			throw new ApplicationException("日结前修正查询失败");
		}
	}

	@Override
	public boolean update(BeforeDaySet beforeDaySet, BigDecimal price) {
		String tableName="";
		UnifiedParam unifiedParam = new UnifiedParam();
		unifiedParam.setType("24");
		unifiedParam.setOperId(beforeDaySet.getOperId());
		unifiedParam.setPlaceId(beforeDaySet.getOperPlaceId());
		unifiedParam.setOperName(beforeDaySet.getOperName());
		unifiedParam.setOperNo(beforeDaySet.getOperNo());
		unifiedParam.setPlaceName(beforeDaySet.getPlaceName());
		unifiedParam.setPlaceNo(beforeDaySet.getPlaceNo());
		unifiedParam.setFlag(beforeDaySet.getBusinessType());
		try{
			if(Constant.OBUISSUE.equals(beforeDaySet.getBusinessType()) || Constant.OBUREPLACE.equals(beforeDaySet.getBusinessType())){
				//电子标签发行 电子标签更换
				tableName = "CSMS_TAG_INFO";
				TagInfo tagInfo = tagInfoDao.findById(beforeDaySet.getSourceId());
				String tagNo = tagInfo.getTagNo();
				beforeDaySet.setBeforeFee(tagInfo.getChargeCost());
				price = price.multiply(new BigDecimal("100"));
				/*price = price.add(tagInfo.getChargeCost());*/
				beforeDaySet.setAfterFee(price);
				beforeDaySet.setCustomerId(tagInfo.getClientID());
				//修正后变动的金额
				unifiedParam.setChangePrice(tagInfo.getChargeCost().subtract(price));
				System.out.println("修正变动的金额"+unifiedParam.getChangePrice());
				MainAccountInfo mainAccountInfo = new MainAccountInfo();
				mainAccountInfo.setMainId(tagInfo.getClientID());
				unifiedParam.setDate(tagInfo.getIssuetime());
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					
					//当前为更换的标签，费用为0的时候则找到旧标签的维保时间更新维保时间
					//如果为有偿更换的标签，找到当时发行的业务记录的业务时间更新维保时间
					
					
					TagInfoHis tagInfoHis = new TagInfoHis();
					Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSTaginfoHis_NO");
					tagInfoHis.setId(seq);
					tagInfoHis.setCreateReason("日结前修正收费金额");
					
					tagInfo = new TagInfo();
					tagInfo.setId(beforeDaySet.getSourceId());
					tagInfo.setChargeCost(price);
					tagInfo.setHisSeqID(seq);
					
					if(Constant.OBUREPLACE.equals(beforeDaySet.getBusinessType())){
						TagBusinessRecord tagBusinessRecord = tagBusinessRecordDao.findByIdTagNo(tagNo);
						if(new BigDecimal("0").compareTo(price)==0){//判断是否是免费更换电子标签
							TagInfo oldTagInfo = tagInfoDao.findByTagNo(tagBusinessRecord.getOldTagNo());
							if(oldTagInfo!=null)
							tagInfo.setMaintenanceTime(oldTagInfo.getMaintenanceTime());
						}else{
							tagInfo.setMaintenanceTime(tagBusinessRecord.getOperTime());
						}
					}
					
					tagInfoHisDao.saveHis(tagInfoHis, tagInfo);
					tagInfoDao.updateNotNull(tagInfo);
					Long id = sequenceUtil.getSequenceLong("SEQ_CSMSBeforeDaySetFee_NO");
					beforeDaySet.setId(id); 
					beforeDaySet.setSourceTable(tableName);
					beforeDaySetDao.save(beforeDaySet);
					return true;
				}
			}
			if(Constant.PAIDISSUE.equals(beforeDaySet.getBusinessType())|| Constant.PAIDCHANGE.equals(beforeDaySet.getBusinessType()) || Constant.PAIDREPLACE.equals(beforeDaySet.getBusinessType())){
				//储值卡发行 储值卡换卡    储值卡补领
				tableName = "CSMS_PREPAIDC";		
				PrepaidC prepaidC = prepaidCDao.findById(beforeDaySet.getSourceId());
				String cardNo = prepaidC.getCardNo();
				beforeDaySet.setBeforeFee(prepaidC.getRealCost());
				price = price.multiply(new BigDecimal("100"));
				/*price = price.add(prepaidC.getRealCost());*/
				beforeDaySet.setAfterFee(price);
				beforeDaySet.setCustomerId(prepaidC.getCustomerID());
				
				unifiedParam.setDate(prepaidC.getSaleTime());
				unifiedParam.setChangePrice(prepaidC.getRealCost().subtract(price));
				
				MainAccountInfo mainAccountInfo = new MainAccountInfo();
				mainAccountInfo.setMainId(prepaidC.getCustomerID());
				
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					
					PrepaidCHis prepaidCHis = new PrepaidCHis(new Date(),"8",prepaidC);
					Long seq = sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_his_NO");
					prepaidCHis.setId(seq);
					prepaidC = new PrepaidC();
					prepaidC.setId(beforeDaySet.getSourceId());
					prepaidC.setRealCost(price);
					prepaidC.setHisSeqID(seq);
					
					if(Constant.PAIDCHANGE.equals(beforeDaySet.getBusinessType()) || Constant.PAIDREPLACE.equals(beforeDaySet.getBusinessType())){
						
						PrepaidCBussiness prepaidCBussiness = prepaidCBussinessDao.findByCardNo(cardNo);
						
						//当前为更换的卡，费用为0的时候则找到旧卡的维保时间更新维保时间
						//如果为有偿更换的卡，找到当时发行的业务记录的业务时间更新维保时间
						if(new BigDecimal("0").compareTo(price)==0){
							PrepaidC oldPrepaidC = prepaidCDao.findByPrepaidCNo(prepaidCBussiness.getOldCardno());
							prepaidC.setMaintainTime(oldPrepaidC.getMaintainTime());
						}else{
							prepaidC.setMaintainTime(prepaidCBussiness.getTradetime());
						}
					}
					
					
					prepaidCDao.saveHis(prepaidCHis);
					prepaidCDao.updateNotNull(prepaidC);
					Long id = sequenceUtil.getSequenceLong("SEQ_CSMSBeforeDaySetFee_NO");
					beforeDaySet.setId(id); 
					beforeDaySet.setSourceTable(tableName);
					beforeDaySetDao.save(beforeDaySet);
					return true;
				}
			}
			if(Constant.DEBITISSUE.equals(beforeDaySet.getBusinessType()) || Constant.DEBITRECHAGE.equals(beforeDaySet.getBusinessType()) || Constant.DEBITREPLACE.equals(beforeDaySet.getBusinessType())){
				//记帐卡发行 记帐卡换卡   记帐卡补领
				tableName = "CSMS_ACCOUNTC_INFO";
				AccountCInfo accountCInfo = accountCInfoDao.findById(beforeDaySet.getSourceId());
				String cardNo = accountCInfo.getCardNo();
				beforeDaySet.setBeforeFee(accountCInfo.getRealCost());
				price = price.multiply(new BigDecimal("100"));
				/*price = price.add(accountCInfo.getRealCost());*/
				beforeDaySet.setAfterFee(price);
				beforeDaySet.setCustomerId(accountCInfo.getCustomerId());
				
				
				unifiedParam.setChangePrice(accountCInfo.getRealCost().subtract(price));
				unifiedParam.setDate(accountCInfo.getIssueTime());
				MainAccountInfo mainAccountInfo = new MainAccountInfo();
				mainAccountInfo.setMainId(accountCInfo.getCustomerId());
				unifiedParam.setMainAccountInfo(mainAccountInfo);
				if (unifiedInterfaceService.saveAccAvailableBalance(unifiedParam)) {
					AccountCInfoHis accountCInfoHis = new AccountCInfoHis(new Date(), "9", accountCInfo);
					Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSAccountCinfohis_NO");
					accountCInfoHis.setId(seq);
					
					accountCInfo = new AccountCInfo();
					accountCInfo.setId(beforeDaySet.getSourceId());
					accountCInfo.setRealCost(price);
					accountCInfo.setHisSeqId(seq);
					
					if(Constant.DEBITRECHAGE.equals(beforeDaySet.getBusinessType()) || Constant.DEBITREPLACE.equals(beforeDaySet.getBusinessType())){
						
						AccountCBussiness accountCBussiness = accountCBussinessDao.findByCardNo(cardNo);
						
						//当前为更换的卡，费用为0的时候则找到旧卡的维保时间更新维保时间
						//如果为有偿更换的卡，找到当时发行的业务记录的业务时间更新维保时间
						if(new BigDecimal("0").compareTo(price)==0){
							AccountCInfo oldAccountCInfo = accountCInfoDao.findByCardNo(accountCBussiness.getOldCardNo());
							accountCInfo.setMaintainTime(oldAccountCInfo.getMaintainTime());
						}else{
							accountCInfo.setMaintainTime(accountCBussiness.getTradeTime());
						}
					}
					
					accountCInfoDao.saveHis(accountCInfoHis);
					accountCInfoDao.updateNotNull(accountCInfo);
					Long id = sequenceUtil.getSequenceLong("SEQ_CSMSBeforeDaySetFee_NO");
					beforeDaySet.setId(id); 
					beforeDaySet.setSourceTable(tableName);
					beforeDaySetDao.save(beforeDaySet);
					return true;
				}
			}
			return false;
		}catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存日结前资金修正失败");
			e.printStackTrace();
			throw new ApplicationException("保存日结前资金修正失败");
		}
	}
}
