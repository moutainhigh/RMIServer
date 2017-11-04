package com.hgsoft.acms.other.service;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.dao.AccountCInfoHisDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.acms.other.serviceInterface.IOtherServiceACMS;
import com.hgsoft.common.Enum.AccountCBussinessTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.macao.dao.VechileChangeInfoDao;
import com.hgsoft.macao.entity.VechileChangeInfo;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.other.dao.CardDelayDao;
import com.hgsoft.other.dao.OtherDao;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.CardDelay;
import com.hgsoft.other.entity.OfficialCardImport;
import com.hgsoft.other.entity.OfficialCardInfo;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.acms.JointCardIssueReceipt;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

import net.sf.json.JSONObject;

@Service
public class OtherServiceACMS implements IOtherServiceACMS {
	private static Logger logger = Logger.getLogger(OtherServiceACMS.class.getName());
	
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private PrepaidCUnifiedInterfaceServiceACMS prepaidCUnifiedInterfaceServiceACMS;
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private VechileChangeInfoDao vechileChangeInfoDao;
	@Resource
	private OtherDao otherDao;
	@Resource
	private CardDelayDao cardDelayDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private AccountCInfoHisDao accountCInfoHisDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private CustomerDao customerDao;
	@Override
	public void saveStart(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness) {
		try {
			prepaidCUnifiedInterfaceServiceACMS.saveStart(prepaidC, carObuCardInfo,vehicleBussiness);
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);
			receiptDao.saveByVehicleBussiness(vehicleBussiness);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage()+"卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
		}
	}
	@Override
	public void saveStop(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness) {
		try {
			prepaidCUnifiedInterfaceServiceACMS.saveStop(prepaidC, carObuCardInfo,vehicleBussiness);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，储值卡卡号:" + prepaidC.getCardNo());
		}
	}
	
	
	@Override
	public void saveStart(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness) {
		try {
			prepaidCUnifiedInterfaceServiceACMS.saveStart(accountCInfo, carObuCardInfo,vehicleBussiness);
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);
			receiptDao.saveByVehicleBussiness(vehicleBussiness);
			
			//客服流水
			Customer customer = customerDao.findById(vehicleBussiness.getCustomerID());
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setSerType("402");//402卡片解除挂起
			
			
			//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
			//serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
			//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setOperId(accountCInfo.getIssueOperId());
			serviceWater.setOperName(accountCInfo.getOperName());
			serviceWater.setOperNo(accountCInfo.getOperNo());
			serviceWater.setPlaceId(accountCInfo.getIssuePlaceId());
			serviceWater.setPlaceName(accountCInfo.getPlaceName());
			serviceWater.setPlaceNo(accountCInfo.getPlaceNo());
			serviceWater.setRemark("联营卡系统：记账卡解除挂起");
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			
			
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"卡牌启用操作失败，联营卡卡卡号:" + accountCInfo.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌启用操作失败，联营卡卡号:" + accountCInfo.getCardNo());
		}
	}
	@Override
	public void saveStop(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness) {
		try {
			prepaidCUnifiedInterfaceServiceACMS.saveStop(accountCInfo, carObuCardInfo,vehicleBussiness);
			
			//客服流水
			Customer customer = customerDao.findById(vehicleBussiness.getCustomerID());
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			
			serviceWater.setId(serviceWater_id);
			
			if(customer!=null)serviceWater.setCustomerId(customer.getId());
			if(customer!=null)serviceWater.setUserNo(customer.getUserNo());
			if(customer!=null)serviceWater.setUserName(customer.getOrgan());
			if (accountCInfo.getCardNo()!=null&&accountCInfo.getCardNo()!="") {
				serviceWater.setCardNo(accountCInfo.getCardNo());
				serviceWater.setSerType("401");//401卡片挂起
				serviceWater.setRemark("联营卡系统：记账卡有卡挂起");
			}else{
				serviceWater.setSerType("403");//403无卡挂起
				serviceWater.setRemark("联营卡系统：记账卡无卡挂起");
			}
			//serviceWater.setAmt(tagBusinessRecord.getCost());//应收金额
			//serviceWater.setAulAmt(tagBusinessRecord.getRealPrice());//实收金额
			//serviceWater.setSaleWate(tagInfo.getSalesType());//销售方式
			serviceWater.setOperId(accountCInfo.getIssueOperId());
			serviceWater.setOperName(accountCInfo.getOperName());
			serviceWater.setOperNo(accountCInfo.getOperNo());
			serviceWater.setPlaceId(accountCInfo.getIssuePlaceId());
			serviceWater.setPlaceName(accountCInfo.getPlaceName());
			serviceWater.setPlaceNo(accountCInfo.getPlaceNo());
			serviceWater.setOperTime(new Date());
			
			serviceWaterDao.save(serviceWater);
			
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"卡牌停用操作失败，联营卡卡号:" + accountCInfo.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡牌停用操作失败，联营卡卡号:" + accountCInfo.getCardNo());
		}
	}
	/**
	 * 保存挂起时，清算数据
	 * */
	public void saveStopClear(String flag,VehicleInfo vehicleInfo){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());
			
			VechileChangeInfo vechileChangeInfo = new VechileChangeInfo();
			vechileChangeInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVechileChangeInfo_NO"));
			vechileChangeInfo.setInterCode("91005");
			vechileChangeInfo.setCreateTime(createTime);
			vechileChangeInfo.setSerType("2");
			vechileChangeInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileChangeInfo.setVehicleColor(vehicleInfo.getVehicleColor());
			
			vechileChangeInfo.setVehicleType(vehicleInfo.getVehicleType());
			vechileChangeInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits());
			vechileChangeInfo.setNscVehicleType(vehicleInfo.getNSCVehicleType());
			
			if("1".equals(flag))
				vechileChangeInfo.setWriteCardFlag("0");//有卡停用
			else
				vechileChangeInfo.setWriteCardFlag("1");//无卡停用
			vechileChangeInfoDao.save(vechileChangeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存解除挂起时，清算数据
	 * */
	public void saveStartClear(VehicleInfo vehicleInfo){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = format.format(new Date());
			
			VechileChangeInfo vechileChangeInfo = new VechileChangeInfo();
			vechileChangeInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVechileChangeInfo_NO"));
			vechileChangeInfo.setInterCode("91005");
			vechileChangeInfo.setCreateTime(createTime);
			vechileChangeInfo.setSerType("1");
			vechileChangeInfo.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileChangeInfo.setVehicleColor(vehicleInfo.getVehicleColor());
			
			vechileChangeInfo.setVehicleType(vehicleInfo.getVehicleType());
			vechileChangeInfo.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits());
			vechileChangeInfo.setNscVehicleType(vehicleInfo.getNSCVehicleType());
			
			vechileChangeInfo.setWriteCardFlag("0");//有卡停用
			vechileChangeInfoDao.save(vechileChangeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据证件类型，证件号码查询该客户的储值卡与记帐卡
	 */
	@Override
	public List<Map<String,Object>> findCardListByCustomer(String idType, String idCode,String expiredCatdNo) {
		try{
			List<Map<String,Object>> list = otherDao.findCardListByCustomer(idType,idCode,expiredCatdNo);
			return list;
		}catch (Exception e) {
			logger.error(e.getMessage()+"卡片延期失败");
			e.printStackTrace();
			throw new ApplicationException("卡片延期失败");
		}
	}
	@Override
	public Map<String, Object> findDetailByCardNo(String cardNo,String cardType) {
		Map<String,Object> m = otherDao.findDelayDetailByCardNo(cardNo,cardType);
		return m;
	}
	@Override
	public void saveCardDelay(CardDelay cardDelay) {
		try {
			cardDelay.setId(sequenceUtil.getSequenceLong("seq_csmscarddelay_no"));
			cardDelayDao.save(cardDelay);
			//更改发行表有效截止时间
			if("1".equals(cardDelay.getCardType())){//储值卡
				PrepaidC prepaidC = prepaidCDao.findByPrepaidCNo(cardDelay.getCardNo());
				DateTime dt2 = new DateTime(prepaidC.getEndDate());
				DateTime d = null;
				if(cardDelay.getDelayTime().compareTo("1")<0){
					d = dt2.plusMonths(6);
				}else{
					d = dt2.plusYears(Integer.parseInt(cardDelay.getDelayTime()));
				}
				prepaidC.setEndDate(new Date(d.getMillis()));
				prepaidCDao.updateEndDate(prepaidC);
			}else{//记帐卡
				AccountCInfo info = accountCInfoDao.findByCardNo(cardDelay.getCardNo());
				DateTime dt2 = new DateTime(info.getEndDate());
				DateTime d = null;
				if(cardDelay.getDelayTime().compareTo("1")<0){
					d = dt2.plusMonths(6);
				}else{
					d = dt2.plusYears(Integer.parseInt(cardDelay.getDelayTime()));
				}
				info.setEndDate(new Date(d.getMillis()));
				accountCInfoDao.update(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"卡片延期失败:" + cardDelay.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡片延期失败:" + cardDelay.getCardNo());
		}
	}
	@Override
	public void updateCardDelay(CardDelay cardDelay) {
		try {
			cardDelayDao.updateById(cardDelay);
			//更改发行表有效截止时间
			if(cardDelay.getCardType().equals("储值卡")){//更新储值卡信息表
				PrepaidC prepaidC = new PrepaidC();
				prepaidC.setCardNo(cardDelay.getCardNo());
				prepaidC.setEndDate(cardDelay.getBeforeDelayTime());
				prepaidCDao.updateEndDate(prepaidC);
			}else{//更新记帐卡信息表
				AccountCInfo info = new AccountCInfo();
				info.setCardNo(cardDelay.getCardNo());
				info.setEndDate(cardDelay.getBeforeDelayTime());
				accountCInfoDao.updateEndDate(info);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"卡片延期撤销失败:" + cardDelay.getCardNo());
			e.printStackTrace();
			throw new ApplicationException("卡片延期撤销失败:" + cardDelay.getCardNo());
		}
	}
	/**
	 * @Descriptioqn:
	 * @param cardNo
	 * @param state
	 * @return
	 * @author lgm
	 * @date 2017年4月19日
	 */
	@Override
	public AccountCInfoHis findByCardNoAndState(String cardNo, String state) {
		return accountCInfoHisDao.findByCardNoAndState(cardNo,state);
	}
	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年4月27日
	 */
	@Override
	public CardDelay findCardDelayById(Long id) {
		try {
			return cardDelayDao.findById(id);
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException| InvocationTargetException e) {
		}
		return null;
	}
	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年4月27日
	 */
	@Override
	public boolean updateFlag(Long id) {
		try{
			cardDelayDao.updateFlag(id);
			return true;
		} catch (Exception e) {
			logger.error("卡片延期标志更新失败"+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Map<String, Object> getVehicleByCardNo(String cardNo,Customer customer) {
		//判断卡号是否在公安公务卡导入表里
		OfficialCardImport officialCard = otherDao.findOfficialCardImportByNo(cardNo);
		
		if(officialCard==null){
			return null;
		}
		String cardType = officialCard.getCardType();
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("officialCard", officialCard);
		VehicleInfo vehicleInfo ;
		TagInfo tag = tagInfoDao.findByTagNo(officialCard.getTagNo());
		if(tag.getClientID().equals(customer.getId())){
			map.put("tag", tag);
		}
		
		if(StringUtil.isEquals(cardType, "22")){//储值卡
			PrepaidC prepaidC = prepaidCDao.findByPrepaidcNoAndCustomer(cardNo,customer);
			map.put("cardInfo", prepaidC);
			vehicleInfo = vehicleInfoDao.findByPrepaidCardNo(cardNo);
		}else{
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNoAndCustomer(cardNo,customer);
			map.put("cardInfo", accountCInfo);
			vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
		}
		map.put("vehicleInfo", vehicleInfo);
		return map;
	}
	
	@Override
	public OfficialCardImport getOfficialCard(String cardNo){
		return otherDao.findOfficialCardImportByNo(cardNo);
	}
	@Override
	public void saveOfficialCard(OfficialCardInfo officialCardInfo,Customer customer) {
		//保存公安公务卡发行信息
		Long officialCardInfoId = sequenceUtil.getSequenceLong("SEQ_OFFICIALCARD_ISSUE");
		officialCardInfo.setId(officialCardInfoId);
		officialCardInfo.setOrgId(customer.getId());
		otherDao.saveOfficialCard(officialCardInfo);
		//保存客服流水表信息
		ServiceWater serviceWater = new ServiceWater();
		Long serviceWaterId = Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString());
		serviceWater.setId(serviceWaterId);
		serviceWater.setCustomerId(customer.getId());
		serviceWater.setUserNo(customer.getUserNo());
		serviceWater.setUserName(customer.getOrgan());
		
		if(StringUtil.isEquals(officialCardInfo.getCardType(), "23")){//记帐卡
			AccountCInfo accountCInfo = accountCInfoDao.findByCardNo(officialCardInfo.getCardNo());
			serviceWater.setCardNo(accountCInfo.getCardNo());
			serviceWater.setNewCardNo(accountCInfo.getCardNo());
			serviceWater.setAmt(accountCInfo.getCost());
			serviceWater.setAulAmt(accountCInfo.getRealCost());
			serviceWater.setAccountBussinessId(officialCardInfoId);
		}else{
			PrepaidC prepaidC = prepaidCDao.findByCardNoToGain(officialCardInfo.getCardNo());
			serviceWater.setCardNo(prepaidC.getCardNo());
			serviceWater.setNewCardNo(prepaidC.getCardNo());
			serviceWater.setAmt(prepaidC.getCost_());
			serviceWater.setAulAmt(prepaidC.getRealCost());
			serviceWater.setPrepaidCBussinessId(officialCardInfoId);
		}
		
		serviceWater.setOperId(officialCardInfo.getOperId());
		serviceWater.setOperNo(officialCardInfo.getOperNo());
		serviceWater.setOperName(officialCardInfo.getOperName());
		serviceWater.setPlaceId(officialCardInfo.getPlaceId());
		serviceWater.setPlaceNo(officialCardInfo.getPlaceNo());
		serviceWater.setPlaceName(officialCardInfo.getPlaceName());
		serviceWater.setOperTime(officialCardInfo.getOperTime());
		serviceWater.setSerType("408");
		serviceWater.setRemark("公安公务卡发行");
		serviceWaterDao.save(serviceWater);
		
		//保存回执
		Receipt receipt=new Receipt();
		receipt.setParentTypeCode("8");
		receipt.setBusinessId(officialCardInfoId);
		receipt.setCustomerId(customer.getId());
		receipt.setTypeCode(null);
		receipt.setOperId(officialCardInfo.getOperId());
		receipt.setPlaceId(officialCardInfo.getPlaceId());
		receipt.setCreateTime(officialCardInfo.getOperTime());
		if(StringUtil.isEquals(officialCardInfo.getTag(), "1")){
			receipt.setTypeChName("公安公务卡片发行");
		}else{
			receipt.setTypeChName("公安公务标签发行");
		}
		receipt.setOperNo(officialCardInfo.getOperNo());
		receipt.setOperName(officialCardInfo.getOperName());
		receipt.setPlaceNo(officialCardInfo.getPlaceNo());
		receipt.setPlaceName(officialCardInfo.getPlaceName());
		receiptDao.save(receipt);
	}
}
