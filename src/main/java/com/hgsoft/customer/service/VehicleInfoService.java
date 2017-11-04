package com.hgsoft.customer.service;

import com.hgsoft.accountC.dao.AccountCDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.app.dao.AppDataDao;
import com.hgsoft.app.dao.AppPointCtrDao;
import com.hgsoft.app.entity.AppPointCtr;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.common.Enum.IdTypeACMSEnum;
import com.hgsoft.common.Enum.IdTypeEnum;
import com.hgsoft.common.Enum.NSCVehicleTypeEnum;
import com.hgsoft.common.Enum.ReceiptParentTypeCodeEnum;
import com.hgsoft.common.Enum.ServiceWaterSerType;
import com.hgsoft.common.Enum.UsingNatureEnum;
import com.hgsoft.common.Enum.VehicleBussinessEnum;
import com.hgsoft.common.Enum.VehicleColorEnum;
import com.hgsoft.common.Enum.VehicleTypeEnum;
import com.hgsoft.common.Enum.VehicleUsingTypeEnum;
import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.dao.CustomerBussinessDao;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.MaterialDao;
import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.dao.VehicleImpDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.dao.VehicleInfoHisDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleImp;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.customer.myAbstract.entity.VehiclePassiveStopJob;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.VehicleModifyApplyDao;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.jointCard.serviceInterface.ICardHolderService;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.serviceInterface.ITagStopService;
import com.hgsoft.other.dao.ReceiptDao;
import com.hgsoft.other.entity.Receipt;
import com.hgsoft.other.vo.receiptContent.BaseReceiptContent;
import com.hgsoft.other.vo.receiptContent.acms.PassiveStopReceipt;
import com.hgsoft.other.vo.receiptContent.customer.VechileAddReceipt;
import com.hgsoft.other.vo.receiptContent.customer.VechileUpdateReceipt;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Constant;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.PropertiesUtil;
import com.hgsoft.utils.ReceiptUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.service.RealTransferService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleInfoService implements IVehicleInfoService {

	private static Logger logger = Logger.getLogger(VehicleInfoService.class.getName());

	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private VehicleInfoHisDao vehicleInfoHisDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	@Resource
	private MaterialDao materialDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCDao accountCDao;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;
	@Resource
	private CustomerBussinessDao customerBussinessDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private ITagStopService tagStopService;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private DarkListDao darkListDao;
	@Resource
	private ServiceWaterDao serviceWaterDao;
	@Resource
	private VehicleModifyApplyDao vehicleModifyApplyDao;
	@Resource
	private IBlackListService blackListService;
	@Resource
	private VehicleImpDao vehicleImpDao;
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private VehiclePassiveStopJob vehiclePassiveStopJob;
	@Resource
	private AppDataDao appDataDao;
	@Resource
	private AppPointCtrDao appPointCtrDao;
	private IdTypeACMSEnum[] idTypeACMSEnums = IdTypeACMSEnum.values();
	private VehicleTypeEnum[] vehicleTypeEnums = VehicleTypeEnum.values();
	private VehicleColorEnum[] vehicleColorEnums = VehicleColorEnum.values();
	//车辆用户性质
	private VehicleUsingTypeEnum[] vehicleUsingTypeEnum = VehicleUsingTypeEnum.values();
	//使用性质
	private UsingNatureEnum[] usingNatureEnum = UsingNatureEnum.values();
	//国标收费车型
	private NSCVehicleTypeEnum[] nscVehicleTypeEnum = NSCVehicleTypeEnum.values();
	@Resource
	private ICardHolderService cardHolderService;
	@Resource
	private AccountCInfoDao accountCInfoDao;

	//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
	@Resource
	private RealTransferService realTransferService;
	//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

	@Override
	public List<VehicleInfo> listAll(VehicleInfo vehicleInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleInfo findById(Long id) {
		return vehicleInfoDao.findById(id);
	}

	@Override
	public VehicleInfo find(VehicleInfo vehicleInfo) {
		return vehicleInfoDao.find(vehicleInfo);
	}

	@Override
	public int getVehicleInfoComplete(Long vehicleInfoId) {
		return vehicleInfoDao.getVehicleInfoComplete(vehicleInfoId);
	}

	@Override
	public void saveVehicle(VehicleInfo vehicleInfo, Material material, String rootPath, List<File> imageFile,
	                        Customer customer, String[] tempPicNameList, VehicleBussiness vehicleBussiness, String clientSystem, Map<String, Object> params) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			vehicleInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfo_NO"));
			vehicleInfoDao.save(vehicleInfo);
			
			/*if(tempPicNameList!=null){
				saveOrUpdateMaterial(material, rootPath, tempPicNameList, customer, vehicleInfo,null,clientSystem);
			}*/

			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setVehicleID(vehicleInfo.getId());

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			carObuCardInfoDao.save(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileAdd.getValue());
			customerBussiness.setOperId(vehicleInfo.getOperId());
			customerBussiness.setOperNo(vehicleInfo.getOperNo());
			customerBussiness.setOperName(vehicleInfo.getOperName());
			customerBussiness.setPlaceId(vehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(vehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(vehicleInfo.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			//保存历史id
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			customerBussinessDao.save(customerBussiness);


			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("108");//108车辆信息新增
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(vehicleInfo.getOperId());
			serviceWater.setOperName(vehicleInfo.getOperName());
			serviceWater.setOperNo(vehicleInfo.getOperNo());
			serviceWater.setPlaceId(vehicleInfo.getPlaceId());
			serviceWater.setPlaceName(vehicleInfo.getPlaceName());
			serviceWater.setPlaceNo(vehicleInfo.getPlaceNo());
			serviceWater.setRemark("自营客服系统：车辆信息新增");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			//车辆信息新增回执
			VechileAddReceipt vechileAddReceipt = new VechileAddReceipt();
			vechileAddReceipt.setTitle("车辆信息新增回执");
			vechileAddReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
			vechileAddReceipt.setVehicleOwner(vehicleInfo.getOwner());
			vechileAddReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileAddReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
			vechileAddReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits() + "");
			vechileAddReceipt.setVehicleModel(vehicleInfo.getModel());
			vechileAddReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
			vechileAddReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicleInfo.getUsingNature()));
			vechileAddReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
			vechileAddReceipt.setVehicleIdentificationCode(vehicleInfo.getIdentificationCode());
			vechileAddReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(vehicleInfo.getNSCVehicleType()));
			vechileAddReceipt.setVehicleLong(vehicleInfo.getVehicleLong() + "");
			vechileAddReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth() + "");
			vechileAddReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight() + "");
			vechileAddReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles() + "");
			vechileAddReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels() + "");
			vechileAddReceipt.setCustomerMobile(customer.getMobile());
			vechileAddReceipt.setCustomerTel(customer.getTel());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.vechileAdd.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.vechileAdd.getName());
			receipt.setVehicleColor(vehicleInfo.getVehicleColor());
			receipt.setVehiclePlate(vechileAddReceipt.getVehiclePlate());
			this.saveReceipt(receipt, customerBussiness, vechileAddReceipt, customer);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			/// TODO 自营车辆信息管理新增需要判断公务车白名单，在里面就不用上传数据了
			realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu.ADD
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "保存车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("保存车辆信息失败");
		}
	}

	/**
	 * 香港联营卡系统新增车辆信息
	 */
	@Override
	public void saveVehicle(VehicleInfo vehicleInfo, Customer customer, VehicleBussiness vehicleBussiness, String clientSystem) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			vehicleInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfo_NO"));
			vehicleInfoDao.save(vehicleInfo);

			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setVehicleID(vehicleInfo.getId());

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			carObuCardInfoDao.save(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileAdd.getValue());
			customerBussiness.setOperId(vehicleInfo.getOperId());
			customerBussiness.setOperNo(vehicleInfo.getOperNo());
			customerBussiness.setOperName(vehicleInfo.getOperName());
			customerBussiness.setPlaceId(vehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(vehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(vehicleInfo.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			//保存历史id
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			customerBussinessDao.save(customerBussiness);

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType(ServiceWaterSerType.vehicleInfoAdd.getValue());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(vehicleInfo.getOperId());
			serviceWater.setOperName(vehicleInfo.getOperName());
			serviceWater.setOperNo(vehicleInfo.getOperNo());
			serviceWater.setPlaceId(vehicleInfo.getPlaceId());
			serviceWater.setPlaceName(vehicleInfo.getPlaceName());
			serviceWater.setPlaceNo(vehicleInfo.getPlaceNo());
			serviceWater.setRemark("联营卡客服系统：车辆信息新增");
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			CardHolder cardHoler = cardHolderService.findByUserNo(vehicleInfo.getUserNo());
			if (null == cardHoler) {
				throw new ApplicationException("持卡人信息为空！");
			}
			Customer cardHolderCustomer = new Customer();
			cardHolderCustomer.setUserNo(cardHoler.getUserNo());
			cardHolderCustomer.setOrgan(cardHoler.getName());
			cardHolderCustomer.setAgentName(cardHoler.getAgentName());
			realTransferService.vehicleInfoTransfer(vehicleInfo, cardHolderCustomer, OperationTypeEmeu.ADD
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "保存车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("保存车辆信息失败");
		}
	}

	@Override
	public void saveVehicleForAMMS(VehicleInfo vehicleInfo, Material material, String rootPath, List<File> imageFile,
	                               Customer customer, String[] tempPicNameList, VehicleBussiness vehicleBussiness, String clientSystem, VehicleImp vehicleImp, Map<String, Object> params) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			vehicleInfo.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfo_NO"));
			vehicleInfoDao.save(vehicleInfo);
			
			/*if(tempPicNameList!=null){
				saveOrUpdateMaterial(material, rootPath, tempPicNameList, customer, vehicleInfo,null,clientSystem);
			}*/

			CarObuCardInfo carObuCardInfo = new CarObuCardInfo();
			carObuCardInfo.setVehicleID(vehicleInfo.getId());

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			carObuCardInfoDao.save(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customer.getId());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileAdd.getValue());
			customerBussiness.setOperId(vehicleInfo.getOperId());
			customerBussiness.setOperNo(vehicleInfo.getOperNo());
			customerBussiness.setOperName(vehicleInfo.getOperName());
			customerBussiness.setPlaceId(vehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(vehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(vehicleInfo.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			//保存历史id
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			customerBussinessDao.save(customerBussiness);

			//更新客户信息预录入
			if (vehicleImp != null && vehicleImp.getId() != null)
				vehicleImpDao.updateFlag(vehicleImp.getId());

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("108");//108车辆信息新增
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(vehicleInfo.getOperId());
			serviceWater.setOperName(vehicleInfo.getOperName());
			serviceWater.setOperNo(vehicleInfo.getOperNo());
			serviceWater.setPlaceId(vehicleInfo.getPlaceId());
			serviceWater.setPlaceName(vehicleInfo.getPlaceName());
			serviceWater.setPlaceNo(vehicleInfo.getPlaceNo());
			serviceWater.setRemark("代理点客服系统：车辆信息新增");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			//车辆信息新增回执
			VechileAddReceipt vechileAddReceipt = new VechileAddReceipt();
			vechileAddReceipt.setTitle("车辆信息新增回执");
			vechileAddReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
			vechileAddReceipt.setVehicleOwner(vehicleInfo.getOwner());
			vechileAddReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileAddReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
			vechileAddReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits() + "");
			vechileAddReceipt.setVehicleModel(vehicleInfo.getModel());
			vechileAddReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
			vechileAddReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicleInfo.getUsingNature()));
			vechileAddReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
			vechileAddReceipt.setVehicleIdentificationCode(vehicleInfo.getIdentificationCode());
			vechileAddReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(vehicleInfo.getNSCVehicleType()));
			vechileAddReceipt.setVehicleLong(vehicleInfo.getVehicleLong() + "");
			vechileAddReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth() + "");
			vechileAddReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight() + "");
			vechileAddReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles() + "");
			vechileAddReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels() + "");
			vechileAddReceipt.setCustomerMobile(customer.getMobile());
			vechileAddReceipt.setCustomerTel(customer.getTel());
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.vechileAdd.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.vechileAdd.getName());
			receipt.setVehicleColor(vehicleInfo.getVehicleColor());
			receipt.setVehiclePlate(vechileAddReceipt.getVehiclePlate());
			this.saveReceipt(receipt, customerBussiness, vechileAddReceipt, customer);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu.ADD
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "保存车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("保存车辆信息失败");
		}
	}

	@Override
	public void updateVehicleMessage(VehicleInfo vehicleInfo) {
		try {
			CustomerBussiness customerBussiness = new CustomerBussiness();
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
			vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
			vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
			vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
			vehicleInfoDao.update(vehicleInfo);

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			customerBussiness.setOperId(vehicleInfo.getOperId());
			customerBussiness.setOperNo(vehicleInfo.getOperNo());
			customerBussiness.setOperName(vehicleInfo.getOperName());
			customerBussiness.setPlaceId(vehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(vehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(vehicleInfo.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			//解绑
//			CarObuCardInfo cbd = new CarObuCardInfo();
//			cbd.setVehicleID(vehicleInfo.getId());
//			if("0".equals(vehicleInfo.getIsWriteOBU())){
//				cbd.setTagID(null);
//			}
//			if("0".equals(vehicleInfo.getIsWriteCard())){
//				cbd.setPrepaidCID(null);
//				cbd.setAccountCID(null);
//			}
//			carObuCardInfoDao.update(cbd);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if (carObuCardInfo != null) {
				if ("0".equals(vehicleInfo.getIsWriteOBU())) {
					carObuCardInfo.setTagID(null);
				}
				if ("0".equals(vehicleInfo.getIsWriteCard())) {
					if (carObuCardInfo.getAccountCID() != null) {
						accountCDao.updateBind("0", carObuCardInfo.getAccountCID());
						carObuCardInfo.setAccountCID(null);
					}
					if (carObuCardInfo.getPrepaidCID() != null) {
						prepaidCDao.updateBind("0", carObuCardInfo.getPrepaidCID());
						carObuCardInfo.setPrepaidCID(null);
					}
				}
				carObuCardInfoDao.update(carObuCardInfo);
			}

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "更新车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("更新车辆信息失败");
		}
	}

	/**
	 * 香港联营卡系统修改车辆信息
	 */
	@Override
	public void updateVehicle(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness) {
		try {
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
			vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
			vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
			vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
			vehicleInfoDao.updateNotNull(vehicleInfo);

			//车辆信息业务记录表
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆修改");
			vehicleBussinessDao.save(vehicleBussiness);

			// 客户业务记录表
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO"));
			customerBussiness.setVehicleHisId(vehicleInfoHis.getId());
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleBussiness.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			customerBussiness.setOperId(vehicleBussiness.getOperID());
			customerBussiness.setOperNo(vehicleBussiness.getOperNo());
			customerBussiness.setOperName(vehicleBussiness.getOperName());
			customerBussiness.setPlaceId(vehicleBussiness.getPlaceID());
			customerBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			customerBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			Customer customer = customerDao.findById(vehicleBussiness.getCustomerID());

			// 调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType(ServiceWaterSerType.vehicleInfoUpdate.getValue());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setRemark("联营卡客服系统：车辆信息修改");
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);
			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			CardHolder cardHolder = cardHolderService.findByUserNo(vehicleInfo.getUserNo());
			if (null == cardHolder) {
				throw new ApplicationException("持卡人信息为空！");
			}
			Customer cardHolderCustomer = new Customer();
			cardHolderCustomer.setUserNo(cardHolder.getUserNo());
			cardHolderCustomer.setOrgan(cardHolder.getName());
			cardHolderCustomer.setAgentName(cardHolder.getAgentName());
			realTransferService.vehicleInfoTransfer(vehicleInfo, cardHolderCustomer, OperationTypeEmeu.UPDATE
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026


		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "更新车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("更新车辆信息失败");
		}
	}

	@Override
	public void updateVehicle(VehicleInfo vehicleInfo, VehicleInfo newvehicleInfo, Map<String, Object> params) {
		try {

//			CarObuCardInfo carObuCardInfo=carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
//			
//			if(carObuCardInfo!=null && carObuCardInfo.getTagID()!=null){
//				TagInfo tagInfo = tagInfoDao.findById(carObuCardInfo.getTagID());
//				if(tagInfo!=null){
//					TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
//					//新增的字段
//					tagBusinessRecord.setOperName(newvehicleInfo.getOperName());//
//					tagBusinessRecord.setOperNo(newvehicleInfo.getOperNo());
//					tagBusinessRecord.setPlaceName(newvehicleInfo.getPlaceName());
//					tagBusinessRecord.setPlaceNo(newvehicleInfo.getPlaceNo());
//					tagStopService.saveTtopTagInfo(tagInfo.getId(), tagInfo.getTagNo(), tagInfo.getClientID(), vehicleInfo.getId()
//							, null, null, newvehicleInfo.getOperId(), newvehicleInfo.getPlaceId(), tagBusinessRecord);
//				}
//			}


			CustomerBussiness customerBussiness = new CustomerBussiness();
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
			vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
			vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
			vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
			vehicleInfoDao.updateNotNull(vehicleInfo);

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			customerBussiness.setOperId(newvehicleInfo.getOperId());
			customerBussiness.setOperNo(newvehicleInfo.getOperNo());
			customerBussiness.setOperName(newvehicleInfo.getOperName());
			customerBussiness.setPlaceId(newvehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(newvehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(newvehicleInfo.getPlaceName());
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);


			//车辆信息业务记录表
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setType(VehicleBussinessEnum.updateVehicle.getValue());
			vehicleBussiness.setOperID(newvehicleInfo.getOperId());
			vehicleBussiness.setOperNo(newvehicleInfo.getOperNo());
			vehicleBussiness.setOperName(newvehicleInfo.getOperName());
			vehicleBussiness.setPlaceID(newvehicleInfo.getPlaceId());
			vehicleBussiness.setPlaceNo(newvehicleInfo.getPlaceNo());
			vehicleBussiness.setPlaceName(newvehicleInfo.getPlaceName());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆修改");
			vehicleBussinessDao.save(vehicleBussiness);


			//解绑
//			CarObuCardInfo cbd = new CarObuCardInfo();
//			cbd.setVehicleID(vehicleInfo.getId());
//			if("0".equals(vehicleInfo.getIsWriteOBU())){
//				cbd.setTagID(null);
//			}
//			if("0".equals(vehicleInfo.getIsWriteCard())){
//				cbd.setPrepaidCID(null);
//				cbd.setAccountCID(null);
//			}
//			carObuCardInfoDao.update(cbd);

//			if(carObuCardInfo!=null){
//				if("0".equals(vehicleInfo.getIsWriteOBU())){
//					carObuCardInfo.setTagID(null);
//				}
//				if("0".equals(vehicleInfo.getIsWriteCard())){
//					if(carObuCardInfo.getAccountCID()!=null){
//						 accountCDao.updateBind("0", carObuCardInfo.getAccountCID());
//						 carObuCardInfo.setAccountCID(null);
//					}
//					if(carObuCardInfo.getPrepaidCID()!=null){
//						prepaidCDao.updateBind("0", carObuCardInfo.getPrepaidCID());
//						carObuCardInfo.setPrepaidCID(null);
//					}
//				}
//				carObuCardInfoDao.update(carObuCardInfo);
//			}

			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("103");//103车辆信息修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(newvehicleInfo.getOperId());
			serviceWater.setOperName(newvehicleInfo.getOperName());
			serviceWater.setOperNo(newvehicleInfo.getOperNo());
			serviceWater.setPlaceId(newvehicleInfo.getPlaceId());
			serviceWater.setPlaceName(newvehicleInfo.getPlaceName());
			serviceWater.setPlaceNo(newvehicleInfo.getPlaceNo());
			serviceWater.setRemark("自营客服系统：车辆信息修改");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			VehicleInfoHis oldVehicleInfo = this.vehicleInfoHisDao.findById(vehicleInfo.getHisSeqId());    //修改前车辆信息
			//车辆信息修改回执
			VechileUpdateReceipt vechileUpdateReceipt = new VechileUpdateReceipt();
			vechileUpdateReceipt.setTitle("车辆信息修改回执");
			vechileUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
			vechileUpdateReceipt.setVehicleOwner(vehicleInfo.getOwner());
			vechileUpdateReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileUpdateReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
			vechileUpdateReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits() + "");
			vechileUpdateReceipt.setVehicleModel(vehicleInfo.getModel());
			vechileUpdateReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
			vechileUpdateReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicleInfo.getUsingNature()));
			vechileUpdateReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
			vechileUpdateReceipt.setVehicleIdentificationCode(vehicleInfo.getIdentificationCode());
			vechileUpdateReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(vehicleInfo.getNSCVehicleType()));
			vechileUpdateReceipt.setVehicleLong(vehicleInfo.getVehicleLong() + "");
			vechileUpdateReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth() + "");
			vechileUpdateReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight() + "");
			vechileUpdateReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles() + "");
			vechileUpdateReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels() + "");
			vechileUpdateReceipt.setCustomerMobile(customer.getMobile());
			vechileUpdateReceipt.setCustomerTel(customer.getTel());

			vechileUpdateReceipt.setOldVehicleOwner(oldVehicleInfo.getOwner());
			vechileUpdateReceipt.setOldVehiclePlate(oldVehicleInfo.getVehiclePlate());
			vechileUpdateReceipt.setOldVehiclePlateColor(VehicleColorEnum.getName(oldVehicleInfo.getVehicleColor()));
			vechileUpdateReceipt.setOldVehicleWeightLimits(oldVehicleInfo.getVehicleWeightLimits() + "");
			vechileUpdateReceipt.setOldVehicleModel(oldVehicleInfo.getModel());
			vechileUpdateReceipt.setOldVehicleType(VehicleTypeEnum.getName(oldVehicleInfo.getVehicleType()));
			vechileUpdateReceipt.setOldVehicleUsingNature(UsingNatureEnum.getName(oldVehicleInfo.getUsingNature()));
			vechileUpdateReceipt.setOldVehicleEngineNo(oldVehicleInfo.getVehicleEngineNo());
			vechileUpdateReceipt.setOldVehicleIdentificationCode(oldVehicleInfo.getIdentificationCode());
			vechileUpdateReceipt.setOldVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(oldVehicleInfo.getNSCVehicleType()));
			vechileUpdateReceipt.setOldVehicleLong(oldVehicleInfo.getVehicleLong() + "");
			vechileUpdateReceipt.setOldVehicleWidth(oldVehicleInfo.getVehicleWidth() + "");
			vechileUpdateReceipt.setOldVehicleHeight(oldVehicleInfo.getVehicleHeight() + "");
			vechileUpdateReceipt.setOldVehicleAxles(oldVehicleInfo.getVehicleAxles() + "");
			vechileUpdateReceipt.setOldVehicleWheels(oldVehicleInfo.getVehicleWheels() + "");
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.vechileUpdate.getName());
			receipt.setVehicleColor(vehicleInfo.getVehicleColor());
			receipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			this.saveReceipt(receipt, customerBussiness, vechileUpdateReceipt, customer);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu.UPDATE
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "更新车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("更新车辆信息失败");
		}
	}

	@Override
	public void updateVehicleForAMMS(VehicleInfo vehicleInfo, VehicleInfo newvehicleInfo, Map<String, Object> params) {
		try {

			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());

			if (carObuCardInfo != null && carObuCardInfo.getTagID() != null) {
				TagInfo tagInfo = tagInfoDao.findById(carObuCardInfo.getTagID());
				if (tagInfo != null) {
					TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
					//新增的字段
					tagBusinessRecord.setOperName(newvehicleInfo.getOperName());//
					tagBusinessRecord.setOperNo(newvehicleInfo.getOperNo());
					tagBusinessRecord.setPlaceName(newvehicleInfo.getPlaceName());
					tagBusinessRecord.setPlaceNo(newvehicleInfo.getPlaceNo());
					tagStopService.saveTtopTagInfo(tagInfo.getId(), tagInfo.getTagNo(), tagInfo.getClientID(), vehicleInfo.getId()
							, null, null, newvehicleInfo.getOperId(), newvehicleInfo.getPlaceId(), tagBusinessRecord);
				}
			}


			CustomerBussiness customerBussiness = new CustomerBussiness();
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
			vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
			vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
			vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
			vehicleInfoDao.updateNotNull(vehicleInfo);

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			customerBussiness.setOperId(newvehicleInfo.getOperId());
			customerBussiness.setOperNo(newvehicleInfo.getOperNo());
			customerBussiness.setOperName(newvehicleInfo.getOperName());
			customerBussiness.setPlaceId(newvehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(newvehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(newvehicleInfo.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			//解绑
//			CarObuCardInfo cbd = new CarObuCardInfo();
//			cbd.setVehicleID(vehicleInfo.getId());
//			if("0".equals(vehicleInfo.getIsWriteOBU())){
//				cbd.setTagID(null);
//			}
//			if("0".equals(vehicleInfo.getIsWriteCard())){
//				cbd.setPrepaidCID(null);
//				cbd.setAccountCID(null);
//			}
//			carObuCardInfoDao.update(cbd);

			if (carObuCardInfo != null) {
				if ("0".equals(vehicleInfo.getIsWriteOBU())) {
					carObuCardInfo.setTagID(null);
				}
				if ("0".equals(vehicleInfo.getIsWriteCard())) {
					if (carObuCardInfo.getAccountCID() != null) {
						accountCDao.updateBind("0", carObuCardInfo.getAccountCID());
						carObuCardInfo.setAccountCID(null);
					}
					if (carObuCardInfo.getPrepaidCID() != null) {
						prepaidCDao.updateBind("0", carObuCardInfo.getPrepaidCID());
						carObuCardInfo.setPrepaidCID(null);
					}
				}
				carObuCardInfoDao.update(carObuCardInfo);
			}

			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("103");//103车辆信息修改
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(newvehicleInfo.getOperId());
			serviceWater.setOperName(newvehicleInfo.getOperName());
			serviceWater.setOperNo(newvehicleInfo.getOperNo());
			serviceWater.setPlaceId(newvehicleInfo.getPlaceId());
			serviceWater.setPlaceName(newvehicleInfo.getPlaceName());
			serviceWater.setPlaceNo(newvehicleInfo.getPlaceNo());
			serviceWater.setRemark("代理点客服系统：车辆信息修改");
			serviceWater.setOperTime(new Date());

			serviceWaterDao.save(serviceWater);

			VehicleInfoHis oldVehicleInfo = this.vehicleInfoHisDao.findById(vehicleInfo.getHisSeqId());    //修改前车辆信息
			//车辆信息修改回执
			VechileUpdateReceipt vechileUpdateReceipt = new VechileUpdateReceipt();
			vechileUpdateReceipt.setTitle("车辆信息修改回执");
			vechileUpdateReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType") + ""));
			vechileUpdateReceipt.setVehicleOwner(vehicleInfo.getOwner());
			vechileUpdateReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vechileUpdateReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
			vechileUpdateReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits() + "");
			vechileUpdateReceipt.setVehicleModel(vehicleInfo.getModel());
			vechileUpdateReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
			vechileUpdateReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicleInfo.getUsingNature()));
			vechileUpdateReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
			vechileUpdateReceipt.setVehicleIdentificationCode(vehicleInfo.getIdentificationCode());
			vechileUpdateReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(vehicleInfo.getNSCVehicleType()));
			vechileUpdateReceipt.setVehicleLong(vehicleInfo.getVehicleLong() + "");
			vechileUpdateReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth() + "");
			vechileUpdateReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight() + "");
			vechileUpdateReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles() + "");
			vechileUpdateReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels() + "");
			vechileUpdateReceipt.setCustomerMobile(customer.getMobile());
			vechileUpdateReceipt.setCustomerTel(customer.getTel());

			vechileUpdateReceipt.setOldVehicleOwner(oldVehicleInfo.getOwner());
			vechileUpdateReceipt.setOldVehiclePlate(oldVehicleInfo.getVehiclePlate());
			vechileUpdateReceipt.setOldVehiclePlateColor(VehicleColorEnum.getName(oldVehicleInfo.getVehicleColor()));
			vechileUpdateReceipt.setOldVehicleWeightLimits(oldVehicleInfo.getVehicleWeightLimits() + "");
			vechileUpdateReceipt.setOldVehicleModel(oldVehicleInfo.getModel());
			vechileUpdateReceipt.setOldVehicleType(VehicleTypeEnum.getName(oldVehicleInfo.getVehicleType()));
			vechileUpdateReceipt.setOldVehicleUsingNature(UsingNatureEnum.getName(oldVehicleInfo.getUsingNature()));
			vechileUpdateReceipt.setOldVehicleEngineNo(oldVehicleInfo.getVehicleEngineNo());
			vechileUpdateReceipt.setOldVehicleIdentificationCode(oldVehicleInfo.getIdentificationCode());
			vechileUpdateReceipt.setOldVehicleNSCvehicletype(NSCVehicleTypeEnum.getName(oldVehicleInfo.getNSCVehicleType()));
			vechileUpdateReceipt.setOldVehicleLong(oldVehicleInfo.getVehicleLong() + "");
			vechileUpdateReceipt.setOldVehicleWidth(oldVehicleInfo.getVehicleWidth() + "");
			vechileUpdateReceipt.setOldVehicleHeight(oldVehicleInfo.getVehicleHeight() + "");
			vechileUpdateReceipt.setOldVehicleAxles(oldVehicleInfo.getVehicleAxles() + "");
			vechileUpdateReceipt.setOldVehicleWheels(oldVehicleInfo.getVehicleWheels() + "");
			Receipt receipt = new Receipt();
			receipt.setTypeCode(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			receipt.setTypeChName(CustomerBussinessTypeEnum.vechileUpdate.getName());
			receipt.setVehicleColor(vehicleInfo.getVehicleColor());
			receipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			this.saveReceipt(receipt, customerBussiness, vechileUpdateReceipt, customer);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu.UPDATE
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "更新车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("更新车辆信息失败");
		}
	}

	@Override
	public void updateVehicleForMacaoStartCard(VehicleInfo vehicleInfo, VehicleInfo newvehicleInfo) {
		try {

			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());

//			if(carObuCardInfo!=null && carObuCardInfo.getTagID()!=null){
//				TagInfo tagInfo = tagInfoDao.findById(carObuCardInfo.getTagID());
//				if(tagInfo!=null){
//					TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
//					//新增的字段
//					tagBusinessRecord.setOperName(newvehicleInfo.getOperName());//
//					tagBusinessRecord.setOperNo(newvehicleInfo.getOperNo());
//					tagBusinessRecord.setPlaceName(newvehicleInfo.getPlaceName());
//					tagBusinessRecord.setPlaceNo(newvehicleInfo.getPlaceNo());
//					tagStopService.saveTtopTagInfo(tagInfo.getId(), tagInfo.getTagNo(), tagInfo.getClientID(), vehicleInfo.getId()
//							, null, null, newvehicleInfo.getOperId(), newvehicleInfo.getPlaceId(), tagBusinessRecord);
//				}
//			}


			CustomerBussiness customerBussiness = new CustomerBussiness();
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			BigDecimal SEQ_CSMSVehicleInfoHis_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleInfoHis_NO");
			vehicleInfoHis.setId(Long.valueOf(SEQ_CSMSVehicleInfoHis_NO.toString()));
			vehicleInfoHis.setGenReason("1"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			customerBussiness.setVehicleHisId(vehicleInfo.getHisSeqId());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);
			vehicleInfo.setHisSeqId(vehicleInfoHis.getId());
			vehicleInfoDao.update(vehicleInfo);

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileUpdate.getValue());
			customerBussiness.setOperId(newvehicleInfo.getOperId());
			customerBussiness.setOperNo(newvehicleInfo.getOperNo());
			customerBussiness.setOperName(newvehicleInfo.getOperName());
			customerBussiness.setPlaceId(newvehicleInfo.getPlaceId());
			customerBussiness.setPlaceNo(newvehicleInfo.getPlaceNo());
			customerBussiness.setPlaceName(newvehicleInfo.getPlaceName());
			customerBussiness.setCreateTime(new Date());
//			customerBussinessDao.save(customerBussiness);

			//解绑
//			CarObuCardInfo cbd = new CarObuCardInfo();
//			cbd.setVehicleID(vehicleInfo.getId());
//			if("0".equals(vehicleInfo.getIsWriteOBU())){
//				cbd.setTagID(null);
//			}
//			if("0".equals(vehicleInfo.getIsWriteCard())){
//				cbd.setPrepaidCID(null);
//				cbd.setAccountCID(null);
//			}
//			carObuCardInfoDao.update(cbd);

			if (carObuCardInfo != null) {
				if ("0".equals(vehicleInfo.getIsWriteOBU())) {
					carObuCardInfo.setTagID(null);
				}
				if ("0".equals(vehicleInfo.getIsWriteCard())) {
					if (carObuCardInfo.getAccountCID() != null) {
						accountCDao.updateBind("0", carObuCardInfo.getAccountCID());
						carObuCardInfo.setAccountCID(null);
					}
					if (carObuCardInfo.getPrepaidCID() != null) {
						prepaidCDao.updateBind("0", carObuCardInfo.getPrepaidCID());
						carObuCardInfo.setPrepaidCID(null);
					}
				}
				carObuCardInfoDao.update(carObuCardInfo);
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "更新车辆信息失败");
			e.printStackTrace();
			throw new ApplicationException("更新车辆信息失败");
		}
	}


	@Override
	public void deleteVehicle(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness) {
		try {
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			vehicleInfo = vehicleInfoDao.findById(vehicleInfo.getId());
			vehicleInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfoHis_NO"));
			vehicleInfoHis.setGenReason("2"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);

			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);

			vehicleInfoDao.delete(vehicleInfo.getId());

			carObuCardInfoDao.deleteByVehicleId(vehicleInfo.getId());


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileDelete.getValue());
			customerBussiness.setOperId(vehicleBussiness.getOperID());
			customerBussiness.setOperNo(vehicleBussiness.getOperNo());
			customerBussiness.setOperName(vehicleBussiness.getOperName());
			customerBussiness.setPlaceId(vehicleBussiness.getPlaceID());
			customerBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			customerBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("111");//111车辆信息删除
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("自营客服系统：车辆信息删除");

			serviceWaterDao.save(serviceWater);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu.DEL
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "车牌信息删除失败,车牌ID" + vehicleInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("车牌信息删除失败,车牌ID" + vehicleInfo.getId());
		}
	}

	@Override
	public void deleteVehicleForAMMS(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness) {
		try {
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			vehicleInfo = vehicleInfoDao.findById(vehicleInfo.getId());
			vehicleInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfoHis_NO"));
			vehicleInfoHis.setGenReason("2"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);

			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);

			VehicleImp vehicleImp = new VehicleImp();
			vehicleImp.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleImp.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleImp = vehicleImpDao.findByPlateAndColor(vehicleImp);
			if (vehicleImp != null)
				vehicleImpDao.updateFlag2(vehicleImp.getId());

			vehicleInfoDao.delete(vehicleInfo.getId());

			carObuCardInfoDao.deleteByVehicleId(vehicleInfo.getId());


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileDelete.getValue());
			customerBussiness.setOperId(vehicleBussiness.getOperID());
			customerBussiness.setOperNo(vehicleBussiness.getOperNo());
			customerBussiness.setOperName(vehicleBussiness.getOperName());
			customerBussiness.setPlaceId(vehicleBussiness.getPlaceID());
			customerBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			customerBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

			serviceWater.setId(serviceWater_id);

			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("111");//111车辆信息删除
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("代理点客服系统：车辆信息删除");

			serviceWaterDao.save(serviceWater);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			realTransferService.vehicleInfoTransfer(vehicleInfo, customer, OperationTypeEmeu.DEL
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (Exception e) {
			logger.error(e.getMessage() + "车牌信息删除失败,车牌ID" + vehicleInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("车牌信息删除失败,车牌ID" + vehicleInfo.getId());
		}
	}

	@Override
	public void deleteVehicleForACMS(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness) {
		try {
			VehicleInfoHis vehicleInfoHis = new VehicleInfoHis();
			vehicleInfo = vehicleInfoDao.findById(vehicleInfo.getId());
			vehicleInfoHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleInfoHis_NO"));
			vehicleInfoHis.setGenReason("2"); // 1表示修改，2表示删除
			vehicleInfoHis.setGenTime(new Date());
			vehicleInfoHisDao.saveHis(vehicleInfoHis, vehicleInfo);

			vehicleBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSVehicleBussiness_NO"));
			vehicleBussinessDao.save(vehicleBussiness);

			vehicleInfoDao.delete(vehicleInfo.getId());

			carObuCardInfoDao.deleteByVehicleId(vehicleInfo.getId());

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleHisId(vehicleInfoHis.getId());
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileDelete.getValue());
			customerBussiness.setOperId(vehicleBussiness.getOperID());
			customerBussiness.setOperNo(vehicleBussiness.getOperNo());
			customerBussiness.setOperName(vehicleBussiness.getOperName());
			customerBussiness.setPlaceId(vehicleBussiness.getPlaceID());
			customerBussiness.setPlaceNo(vehicleBussiness.getPlaceNo());
			customerBussiness.setPlaceName(vehicleBussiness.getPlaceName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);

			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());

			//调整的客服流水
			ServiceWater serviceWater = new ServiceWater();
			Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");
			serviceWater.setId(serviceWater_id);
			serviceWater.setCustomerId(customer.getId());
			serviceWater.setUserNo(customer.getUserNo());
			serviceWater.setUserName(customer.getOrgan());
			serviceWater.setSerType("111");//111车辆信息删除
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setOperTime(new Date());
			serviceWater.setRemark("联营卡客服系统：车辆信息删除");
			serviceWaterDao.save(serviceWater);

			//ygz wangjinhao---------------------------------- start VEHICLEUPLOAD20171026
			CardHolder cardHolder = cardHolderService.findByUserNo(vehicleInfo.getUserNo());
			if (null == cardHolder) {
				throw new ApplicationException("持卡人信息为空！");
			}
			Customer cardHolderCustomer = new Customer();
			cardHolderCustomer.setUserNo(cardHolder.getUserNo());
			cardHolderCustomer.setOrgan(cardHolder.getName());
			cardHolderCustomer.setAgentName(cardHolder.getAgentName());
			realTransferService.vehicleInfoTransfer(vehicleInfo, cardHolderCustomer, OperationTypeEmeu.DEL
					.getCode());
			//ygz wangjinhao---------------------------------- end VEHICLEUPLOAD20171026

		} catch (ApplicationException e) {
			logger.error(e.getMessage() + "车牌信息删除失败,车牌ID" + vehicleInfo.getId());
			e.printStackTrace();
			throw new ApplicationException("车牌信息删除失败,车牌ID" + vehicleInfo.getId());
		}
	}

	@Override
	public void updateObu(Long vehicleID) {
		VehicleInfo vehicle = findById(vehicleID);
		vehicle.setIsWriteOBU("1");
		updateVehicleMessage(vehicle);
	}

	@Override
	public List<Map<String, Object>> getAllVehByCusId(Long customerId) {
		return vehicleInfoDao.getAllVehByCusId(customerId);
	}

	@Override
	public List<Map<String, Object>> getAllVehByCusIdForAMMS(Long customerId) {
		return vehicleInfoDao.getAllVehByCusIdForAMMS(customerId);
	}

	@Override
	public VehicleInfo findByPlateAndColorAndIdentCode(String vehiclePlate, String vehicleColor, String identificationCode) {
		return vehicleInfoDao.findByPlateAndColorAncIdentCode(vehiclePlate, vehicleColor, identificationCode);
	}

	@Override
	public VehicleInfo loadByPlateAndColor(String vehiclePlate, String vehicleColor) {
		return vehicleInfoDao.loadByPlateAndColor(vehiclePlate, vehicleColor);
	}

	@Override
	public VehicleInfo findByPrepaidCNo(String cardNo) {
		return vehicleInfoDao.findByPrepaidCNo(cardNo);
	}

	@Override
	public VehicleInfo findByAccountCNo(String cardNo) {
		return vehicleInfoDao.findByAccountCNo(cardNo);
	}

	@Override
	public VehicleInfo findByCustomerId(Long id) {
		return vehicleInfoDao.findByCustomerId(id);
	}

	@Override
	public Pager findByCustomer(Pager pager, Customer customer) {
		return vehicleInfoDao.findByCustomer(pager, customer);
	}

	@Override
	public Pager findByPlateAndColor(Pager pager, Customer customer, String plate, String color) {
		return vehicleInfoDao.findByPlateAndColor(pager, customer, plate, color);
	}

	@Override
	public Pager findByPlateAndColorForAMMS(Pager pager, Customer customer, String plate, String color, String bankCode) {
		return vehicleInfoDao.findByPlateAndColorForAMMS(pager, customer, plate, color, bankCode);
	}

	@Override
	public VehicleInfo findByPlateAndColor(Customer customer, String plate, String color) {
		return vehicleInfoDao.findByPlateAndColor(customer, plate, color);
	}

	@Override
	public List<Map<String, Object>> findListNotBindByCID(Long id) {
		return vehicleInfoDao.findListNotBindByCID(id);
	}

	@Override
	public List<Map<String, Object>> findListNotBindByCIDForAMMS(Long id) {
		return vehicleInfoDao.findListNotBindByCIDForAMMS(id);
	}

	public List<Map<String, Object>> findListNotBindForMacao(MacaoCardCustomer macaoCardCustomer) {
		return vehicleInfoDao.findListNotBindForMacao(macaoCardCustomer);
	}

	/**
	 * 根据当前客户ID、类型、code查找图片资料表记录，
	 * 若有则进行更新，修改次数+1，更新时间=当前时间，图片地址为新地址，并把原图片删除；
	 * 若无则新增，修改次数=1，更新时间=当前时间
	 */
	@Override
	public boolean saveOrUpdateMaterial(Material material, String rootPath, List<File> imageFile, Customer customer, VehicleInfo vehicleInfo) {
		boolean flag = true;
		List<Material> newMaterials = new ArrayList<Material>();
		try {
			//文件 
			String dirPath = rootPath + "material" + File.separator + material.getType() + File.separator + customer.getUserNo() + File.separator;
			File dir = new File(dirPath);
			//建立文件夹
			if (!dir.exists()) {
				dir.mkdirs();
			}

			//车辆拍照时，图片资料表Material的code为车辆id_类型
			material.setCode(vehicleInfo.getId() + "_" + material.getType());

			if (imageFile != null) {
				//material.setPicAddr(savePath);//图片地址
				//根据当前客户ID、类型、code查找图片资料表记录
				List<Material> materials = materialDao.findMateria(material);

				if (materials != null) {
					Integer updateTime = 1;
					for (Material m : materials) {
						//若有则全部删掉
						//将原图片删除
						File img = new File(rootPath + m.getPicAddr());
						img.delete();

						//将图片资料表的数据删掉
						updateTime = m.getUpdateTime();//先存储要删掉的数据的修改次数
						materialDao.deleteMateria(m.getId());

						materialDao.updateMateria(m);
					}
					//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
					for (int i = 0; i < imageFile.size(); i++) {
						String newImageFileName = material.getCode() + System.currentTimeMillis() + ".jpg";
						//保存图片文件
						String savePath = "material" + File.separator + material.getType() + File.separator + customer.getUserNo() + File.separator + newImageFileName;
						FileUtils.copyFile(imageFile.get(i), new File(rootPath + savePath));
						//若无则新增，修改次数=1，更新时间=当前时间
						Material newMaterial = new Material();
						BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
						newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
						newMaterial.setCustomerID(customer.getId());
						newMaterial.setVehicleID(vehicleInfo.getId());
						newMaterial.setType(material.getType());
						newMaterial.setCode(material.getCode());
						newMaterial.setPicAddr(savePath);
						newMaterial.setUp_Date(new Date());
						newMaterial.setUpdateTime(updateTime + 1);

						materialDao.saveMateria(newMaterial);
						newMaterials.add(newMaterial);
					}

				} else {
					for (int i = 0; i < imageFile.size(); i++) {
						String newImageFileName = material.getCode() + System.currentTimeMillis() + ".jpg";
						//保存图片文件
						String savePath = "material" + File.separator + material.getType() + File.separator + customer.getUserNo() + File.separator + newImageFileName;
						FileUtils.copyFile(imageFile.get(i), new File(rootPath + savePath));
						//若无则新增，修改次数=1，更新时间=当前时间
						Material newMaterial = new Material();
						BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
						newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
						newMaterial.setCustomerID(customer.getId());
						newMaterial.setVehicleID(vehicleInfo.getId());
						newMaterial.setType(material.getType());
						newMaterial.setCode(material.getCode());
						newMaterial.setPicAddr(savePath);
						newMaterial.setUp_Date(new Date());
						newMaterial.setUpdateTime(1);

						materialDao.saveMateria(newMaterial);
						newMaterials.add(newMaterial);
					}
				}
			}


		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "图片资料数据保存失败");
			flag = false;
			//保存数据失败，删除刚刚上传的图片
			for (Material ma : newMaterials) {
				File img = new File(rootPath + ma.getPicAddr());
				img.delete();
			}


			throw new ApplicationException();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * @param material
	 * @param rootPath
	 * @param tempPicNameList
	 * 		要保存的临时图片路径组
	 * @param customer
	 * @param vehicleInfo
	 * @param deleteOldMaterialIDList
	 * 		要删除的图片组
	 * @param clientSystem
	 * 		"1":CSMSClient  2.AMMSClient
	 * @return
	 */
	@Override
	public boolean saveOrUpdateMaterial(Material material, String rootPath, String[] tempPicNameList, Customer customer,
	                                    VehicleInfo vehicleInfo, String[] deleteOldMaterialIDList, String clientSystem) {
		boolean flag = true;
		List<Material> newMaterials = new ArrayList<Material>();
		List<Material> oldMaterials = new ArrayList<Material>();
		StringBuffer tempPicPaths = new StringBuffer("");
		StringBuffer savePaths = new StringBuffer("");
		;
		StringBuffer oldPicPaths = new StringBuffer("");

		//文件 
		String dirPath = rootPath + "material" + File.separator + material.getType() + File.separator + customer.getUserNo() + File.separator;
		try {
			//修改：不在server处理
			/*File dir = new File(dirPath);
			//建立文件夹（）
			if(!dir.exists()){
				dir.mkdirs();
			}*/

			//车辆拍照时，图片资料表Material的code为车辆id_类型
			material.setCode(vehicleInfo.getId() + "_" + material.getType());

			if (tempPicNameList.length > 0) {
				//material.setPicAddr(savePath);//图片地址
				//根据当前客户ID、类型、code查找图片资料表记录
				List<Material> materials = materialDao.findMateria(material);

				//materials有可能为[]
				if (materials != null && materials.size() > 0) {
					Integer updateTime = 1;

					if (deleteOldMaterialIDList != null) {
						//表示有药删除的照片,执行以下程序
						for (int i = 0; i < deleteOldMaterialIDList.length; i++) {
							Material oldMaterial = null;
							if (StringUtil.isNotBlank(deleteOldMaterialIDList[i]))
								oldMaterial = materialDao.findById(Long.parseLong(deleteOldMaterialIDList[i]));
							if (oldMaterial != null) {
								oldPicPaths.append("," + oldMaterial.getPicAddr());
								//将图片资料表的数据删掉
								updateTime = materials.get(i).getUpdateTime();//先存储要删掉的数据的修改次数
								materialDao.deleteMateria(oldMaterial.getId());
								oldMaterials.add(oldMaterial);
							}
						}
					}

					//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
					if (tempPicNameList != null) {
						for (int i = 0; i < tempPicNameList.length; i++) {
							if (StringUtil.isNotBlank(tempPicNameList[i])) {
								String newImageFileName = material.getCode() + System.currentTimeMillis() + ".jpg";
								//保存图片文件
								String savePath = "material" + File.separator + material.getType() + File.separator + customer.getUserNo() + File.separator + newImageFileName;
								//临时图片
								//File tempPic = new File(rootPath+"picture"+File.separator+tempPicNameList[i]);
								//FileUtils.copyFile(tempPic, new File(rootPath+savePath));
								//移动图片到要保存的位置后，删除临时图片
								//tempPic.delete();
								//****不在server处理图片资源
								savePaths.append("," + savePath);
								//tempPicPaths.append(","+"picture"+File.separator+tempPicNameList[i]);
								tempPicPaths.append("," + tempPicNameList[i]);
								//若无则新增，修改次数=1，更新时间=当前时间
								Material newMaterial = new Material();
								BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
								newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
								newMaterial.setCustomerID(customer.getId());
								newMaterial.setVehicleID(vehicleInfo.getId());
								newMaterial.setType(material.getType());
								newMaterial.setCode(material.getCode());
								newMaterial.setPicAddr(savePath);
								newMaterial.setUp_Date(new Date());
								newMaterial.setUpdateTime(updateTime + 1);

								materialDao.saveMateria(newMaterial);
								newMaterials.add(newMaterial);
							}
						}
					}

				} else {
					for (int i = 0; i < tempPicNameList.length; i++) {
						String newImageFileName = material.getCode() + System.currentTimeMillis() + ".jpg";
						//保存图片文件
						String savePath = "material" + File.separator + material.getType() + File.separator + customer.getUserNo() + File.separator + newImageFileName;
						//临时图片
						//File tempPic = new File(rootPath+"picture"+File.separator+tempPicNameList[i]);
						//FileUtils.copyFile(tempPic, new File(rootPath+savePath));
						//移动图片到要保存的位置后，删除临时图片
						//tempPic.delete();
						savePaths.append("," + savePath);
						//tempPicPaths.append(","+"picture"+File.separator+tempPicNameList[i]);
						tempPicPaths.append("," + tempPicNameList[i]);
						//若无则新增，修改次数=1，更新时间=当前时间
						Material newMaterial = new Material();
						BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
						newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
						newMaterial.setCustomerID(customer.getId());
						newMaterial.setVehicleID(vehicleInfo.getId());
						newMaterial.setType(material.getType());
						newMaterial.setCode(material.getCode());
						newMaterial.setPicAddr(savePath);
						newMaterial.setUp_Date(new Date());
						newMaterial.setUpdateTime(1);

						materialDao.saveMateria(newMaterial);
						newMaterials.add(newMaterial);
					}
				}
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "图片资料数据保存失败");
			flag = false;
			throw new ApplicationException();
		}

		try {
			String oldPicPathsStr = "";
			String tempPicPathsStr = "";
			String savePathsStr = "";
			if (oldPicPaths.length() > 1) {
				oldPicPathsStr = oldPicPaths.substring(1);
			}
			if (tempPicPaths.length() > 1) {
				tempPicPathsStr = tempPicPaths.substring(1);
			}
			if (savePaths.length() > 1) {
				savePathsStr = savePaths.substring(1);
			}
			//通过http访问client项目，作图片资源的处理
			//csmsClientUrl  ammsClientUrl   acmsClientUrl
			/*if("1".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","csmsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}else if("2".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","ammsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}else if("3".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","acmsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}*/
			String url = PropertiesUtil.getValue("/url.properties", clientSystem) + "commonInterface/commonInterface_dealChangeMaterials.do";

			String data = "dirPath=" + dirPath + "&oldPicPaths=" + oldPicPathsStr + "&tempPicPaths=" + tempPicPathsStr + "&savePaths=" + savePathsStr;
			Map<String, Object> resultMap = HttpUtil.callClientByHTTP(url, data, "POST");
			//System.out.println(resultMap.get("result"));
			if (resultMap != null && resultMap.get("result") != null && resultMap.get("result").toString().equals("true")) {
				flag = true;
			} else {
				flag = false;

				//如果有异常表明只删除了数据库的图片资料，但是没有删除图片资源，所以要重新保存删除了的图片资料
				//同理，新增图片也会有这情况
				for (Material newM : newMaterials) {
					materialDao.deleteMateria(newM.getId());
				}
				for (Material oldM : oldMaterials) {
					materialDao.saveMateria(oldM);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + "图片资源处理失败：" + savePaths);
			//如果有异常表明只删除了数据库的图片资料，但是没有删除图片资源，所以要重新保存删除了的图片资料
			//同理，新增图片也会有这情况
			for (Material newM : newMaterials) {
				materialDao.deleteMateria(newM.getId());
			}
			for (Material oldM : oldMaterials) {
				materialDao.saveMateria(oldM);
			}
			return false;

		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> listNotBind(Long customerId) {
		return vehicleInfoDao.listNotBind(customerId);
	}

	@Override
	public Pager listNotBindByPager(Pager pager, String vehiclePlate, String vehicleColor, Long customerId) {
		return vehicleInfoDao.listNotBindByPager(pager, vehiclePlate, vehicleColor, customerId);
	}

	@Override
	public Pager listNotBindByPagerForTag(Pager pager, String vehiclePlate, String vehicleColor, Long customerId) {
		return vehicleInfoDao.listNotBindByPagerForTag(pager, vehiclePlate, vehicleColor, customerId);
	}

	@Override
	public List<Map<String, Object>> listNotBind(String cardNo) {
		return vehicleInfoDao.listNotBind(cardNo);
	}

	@Override
	public boolean saveMigrate(VehicleInfo vehicleInfo, String rootPath, Long customerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		try {
			vehicleInfoDao.update(vehicleInfo);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if (carObuCardInfo == null) {
				throw new ApplicationException();
			}
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setType(VehicleBussinessEnum.migrateVehicle.getValue());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆迁移");
			vehicleBussiness.setOperID(sysAdmin.getId());
			vehicleBussiness.setOperNo(sysAdmin.getStaffNo());
			vehicleBussiness.setOperName(sysAdmin.getUserName());
			vehicleBussiness.setPlaceID(cusPointPoJo.getCusPoint());
			vehicleBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			vehicleBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			vehicleBussiness.setCreateTime(new Date());

			Material material = new Material();
			material.setVehicleID(vehicleInfo.getId());
			List<Material> materials = materialDao.findMateria(material);
			/*Integer updateTime = 1;
			
			List<Material> newMaterials = new ArrayList<Material>();*/
			StringBuffer savePaths = new StringBuffer();
			;
			StringBuffer oldPicPaths = new StringBuffer();
			Material newMaterial = null;
			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());
			String dirPath = null;
			//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
			for (int i = 0; i < materials.size(); i++) {
				/*if(dirPath==null) rootPath = rootPath+"material"+File.separator+materials.get(i).getType()+File.separator+customer.getUserNo()+File.separator;*/
				oldPicPaths.append("," + rootPath + materials.get(i).getPicAddr());
				String newImageFileName = materials.get(i).getCode() + System.currentTimeMillis() + ".jpg";
				//保存图片文件
				String savePath = "material" + File.separator + materials.get(i).getType() + File.separator + customer.getUserNo() + File.separator + newImageFileName;
				System.out.println(savePath);
				//****不在server处理图片资源
				savePaths.append("," + rootPath + savePath);

				newMaterial = materials.get(i);
				newMaterial.setCustomerID(vehicleInfo.getCustomerID());
				newMaterial.setPicAddr(savePath);
				newMaterial.setUp_Date(new Date());
				newMaterial.setUpdateTime(newMaterial.getUpdateTime() + 1);
				materialDao.updateMateria(newMaterial);
			}

			try {
				String url = PropertiesUtil.getValue("/url.properties", "clientUrl") + "commonInterface/commonInterface_vechileMigrateMaterials.do";
				String data = "dirPath=" + rootPath + "&oldPicPaths=" + oldPicPaths.substring(1) + "&savePaths=" + savePaths.substring(1);
				Map<String, Object> resultMap = HttpUtil.callClientByHTTP(url, data, "POST");
				if (resultMap != null && resultMap.get("result") != null && !resultMap.get("result").toString().equals("true")) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println(resultMap.get("result"));
			/**/

			//将卡和OBU（未有黑名单）均下挂黑名单 （可能要修改）
			if (carObuCardInfo.getPrepaidCID() != null) {
				PrepaidC prepaidC = prepaidCDao.findById(carObuCardInfo.getPrepaidCID());
				if (prepaidC == null) return false;
				vehicleBussiness.setCardType(Constant.PREPAIDTYPE);
				vehicleBussiness.setCardNo(prepaidC.getCardNo());

				//原清算数据，没用了
				/*TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, prepaidC.getCardNo(), null, " ", vehicleInfo.getVehiclePlate(),10, new Date(),0, new Date());
				TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, prepaidC.getCardNo(), null, " ", vehicleInfo.getVehiclePlate(),10, new Date(),0, new Date());
				tollCardBlackDetDao.save(tollCardBlackDet);
				tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/

				//
				//DarkList darkList = darkListDao.findByCardNo(prepaidC.getCardNo());
				//
				//saveDarkList(prepaidC,null,darkList,"10",prepaidC.getBlackFlag() );
				//车辆迁移，如果原来车辆有储值卡，则把原储值卡注销（铭鸿的清算数）
				blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), new Date()
						, "2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(),
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(),
						new Date());
			}
			if (carObuCardInfo.getAccountCID() != null) {
				AccountCInfo accountCInfo = accountCDao.findById(carObuCardInfo.getAccountCID());
				if (accountCInfo == null) return false;
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);
				vehicleBussiness.setCardNo(accountCInfo.getCardNo());

				//原清算数据，没用了
				/*TollCardBlackDet tollCardBlackDet=new TollCardBlackDet(4401, null, accountCInfo.getCardNo(), null, " ", vehicleInfo.getVehiclePlate(),10, new Date(),0, new Date());
				TollCardBlackDetSend tollCardBlackDetSend=new TollCardBlackDetSend(4401, null, accountCInfo.getCardNo(), null, " ", vehicleInfo.getVehiclePlate(),10, new Date(),0, new Date());
				tollCardBlackDetDao.save(tollCardBlackDet);
				tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/

				//
				//DarkList darkList = darkListDao.findByCardNo(accountCInfo.getCardNo());
				//
				//saveDarkList(null,accountCInfo,darkList,"10", accountCInfo.getBlackFlag());
				//车辆迁移，如果原车辆有记帐卡，则注销记帐卡（铭鸿的清算数据）
				blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
						, "2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(),
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(),
						new Date());
			}
			carObuCardInfo.setTagID(null);
			carObuCardInfo.setPrepaidCID(null);
			carObuCardInfo.setAccountCID(null);
			carObuCardInfoDao.update(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customerId);
			customerBussiness.setMigratecustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileMigrate.getValue());
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getLoginName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);


			Customer orignalCustomer = customerDao.findById(customerId);
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if (orignalCustomer != null) serviceWater.setCustomerId(orignalCustomer.getId());
			if (orignalCustomer != null) serviceWater.setUserNo(orignalCustomer.getUserNo());
			if (orignalCustomer != null) serviceWater.setUserName(orignalCustomer.getOrgan());
			//serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setSerType("404");//车辆所属人变更
			serviceWater.setRemark("自营客服系统：车辆所属人变更");
			//serviceWater.setFlowState("1");//正常
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);


			return true;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "数据错误！车卡绑定表中无此车牌ID！");
			throw new ApplicationException("数据错误！车卡绑定表中无此车牌ID！");
		}
	}

	@Override
	public boolean saveMigrateACMS(VehicleInfo vehicleInfo, String rootPath, Long customerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		try {
//			vehicleInfoDao.update(vehicleInfo);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if (carObuCardInfo == null) {
				throw new ApplicationException();
			}
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setType(VehicleBussinessEnum.migrateVehicle.getValue());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆迁移");
			vehicleBussiness.setOperID(sysAdmin.getId());
			vehicleBussiness.setOperNo(sysAdmin.getStaffNo());
			vehicleBussiness.setOperName(sysAdmin.getUserName());
			vehicleBussiness.setPlaceID(cusPointPoJo.getCusPoint());
			vehicleBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			vehicleBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			vehicleBussiness.setCreateTime(new Date());

			Material material = new Material();
			material.setVehicleID(vehicleInfo.getId());
			List<Material> materials = materialDao.findMateria(material);
			/*Integer updateTime = 1;
			
			List<Material> newMaterials = new ArrayList<Material>();*/
			StringBuffer savePaths = new StringBuffer();
			;
			StringBuffer oldPicPaths = new StringBuffer();
			Material newMaterial = null;
			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());
			String dirPath = null;
			//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
//			for(int i=0;i<materials.size();i++){
//				/*if(dirPath==null) rootPath = rootPath+"material"+File.separator+materials.get(i).getType()+File.separator+customer.getUserNo()+File.separator;*/
//				oldPicPaths.append(","+rootPath+materials.get(i).getPicAddr());
//				String newImageFileName =  materials.get(i).getCode() + System.currentTimeMillis()+".jpg";
//				//保存图片文件
//				String savePath = "material"+File.separator+materials.get(i).getType()+File.separator+customer.getUserNo()+File.separator+newImageFileName;
//				System.out.println(savePath);
//				//****不在server处理图片资源
//				savePaths.append(","+rootPath+savePath);
//				
//				newMaterial = materials.get(i);
//				newMaterial.setCustomerID(vehicleInfo.getCustomerID());
//				newMaterial.setPicAddr(savePath);
//				newMaterial.setUp_Date(new Date());
//				newMaterial.setUpdateTime(newMaterial.getUpdateTime()+1);
//				materialDao.updateMateria(newMaterial);
//			}
//			
//			try {
//				String url = PropertiesUtil.getValue("/url.properties","clientUrl")+"commonInterface/commonInterface_vechileMigrateMaterials.do";
//				String data = "dirPath="+rootPath+"&oldPicPaths="+oldPicPaths.substring(1)+"&savePaths="+savePaths.substring(1);
//				Map<String, Object> resultMap = HttpUtil.callClientByHTTP(url, data, "POST");
//				if(resultMap!=null && resultMap.get("result")!=null &&  !resultMap.get("result").toString().equals("true")){
//					return false;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			//System.out.println(resultMap.get("result"));
			/**/

			if (carObuCardInfo.getAccountCID() != null) {
				AccountCInfo accountCInfo = accountCDao.findById(carObuCardInfo.getAccountCID());
				if (accountCInfo == null) return false;
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);
				vehicleBussiness.setCardNo(accountCInfo.getCardNo());
				//车辆迁移，如果原车辆有记帐卡，则注销记帐卡（铭鸿的清算数据）
				blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
						, "2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(),
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(),
						new Date());
			}
			carObuCardInfo.setTagID(null);
			carObuCardInfo.setPrepaidCID(null);
			carObuCardInfo.setAccountCID(null);
			carObuCardInfoDao.update(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);

			//卡签下发黑名单之后删除车辆
			vehicleInfoDao.delete(vehicleInfo.getId());

			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customerId);
			customerBussiness.setMigratecustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileMigrate.getValue());
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getLoginName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);


			//回执处理
			// 联营卡被动挂起回执
			Receipt receipt = new Receipt();
			receipt.setParentTypeCode("7");
			receipt.setTypeCode(VehicleBussinessEnum.passiveStop.getValue());
			receipt.setTypeChName(VehicleBussinessEnum.passiveStop.getName());
			receipt.setBusinessId(vehicleBussiness.getId());
			receipt.setOperId(sysAdmin.getId());
			receipt.setOperNo(sysAdmin.getStaffNo());
			receipt.setOperName(sysAdmin.getLoginName());
			receipt.setPlaceId(cusPointPoJo.getCusPoint());
			receipt.setPlaceNo(cusPointPoJo.getCusPointCode());
			receipt.setPlaceName(cusPointPoJo.getCusPointName());
			receipt.setCreateTime(new Date());
			receipt.setOrgan(customer.getOrgan());
			PassiveStopReceipt passiveStopReceipt = new PassiveStopReceipt();
			passiveStopReceipt.setTitle("车辆所属人变更");
			passiveStopReceipt.setHandleWay("凭资料办理");

			CarObuCardInfo ccobuInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if (ccobuInfo != null && ccobuInfo.getAccountCID() != null) {
				CardHolder cardHolder = cardHolderService.findCardHolderByCardNo(accountCInfoDao.findById(ccobuInfo.getAccountCID()).getCardNo());
				passiveStopReceipt.setName(cardHolder.getName());
				passiveStopReceipt.setLinkTel(cardHolder.getPhoneNum());
				passiveStopReceipt.setMobileNum(cardHolder.getMobileNum());
				passiveStopReceipt.setLinkMan(cardHolder.getLinkMan());
				passiveStopReceipt.setLinkAddr(cardHolder.getLinkAddr());
			}
			passiveStopReceipt.setOwner(vehicleInfo.getOwner());
			passiveStopReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			for (int i = 0; i < vehicleColorEnums.length; i++) {
				if (vehicleColorEnums[i].getValue().equals(vehicleBussiness.getVehicleColor())) {
					passiveStopReceipt.setVehicleColor((vehicleColorEnums[i].getName()));
				} // if
			} // for
			passiveStopReceipt.setWeightsOrSeats(vehicleInfo.getVehicleWeightLimits());
			passiveStopReceipt.setModel(vehicleInfo.getModel());
			for (int i = 0; i < vehicleTypeEnums.length; i++) {
				if (vehicleTypeEnums[i].getValue().equals(vehicleInfo.getVehicleType())) {
					passiveStopReceipt.setVehicleType(vehicleTypeEnums[i].getName());
				} // if
			} // for
			//使用性质
			for (int i = 0; i < usingNatureEnum.length; i++) {
				if (usingNatureEnum[i].getValue().equals(vehicleInfo.getUsingNature())) {
					passiveStopReceipt.setVehicleOperatingFlag(usingNatureEnum[i].getName());
				} // if
			} // for
			passiveStopReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
			passiveStopReceipt.setIdentificationCode(vehicleInfo.getIdentificationCode());
			for (int i = 0; i < nscVehicleTypeEnum.length; i++) {
				if (nscVehicleTypeEnum[i].getValue().equals(vehicleInfo.getNSCVehicleType())) {
					passiveStopReceipt.setNscVehicleType(nscVehicleTypeEnum[i].getName());
				} // if
			} // for
			passiveStopReceipt.setVehicleLong(vehicleInfo.getVehicleLong());
			passiveStopReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth());
			passiveStopReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight());
			passiveStopReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles());
			passiveStopReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels());
			receipt.setContent(JSONObject.fromObject(passiveStopReceipt).toString());
			receiptDao.saveReceipt(receipt);


			Customer orignalCustomer = customerDao.findById(customerId);
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if (orignalCustomer != null) serviceWater.setCustomerId(orignalCustomer.getId());
			if (orignalCustomer != null) serviceWater.setUserNo(orignalCustomer.getUserNo());
			if (orignalCustomer != null) serviceWater.setUserName(orignalCustomer.getOrgan());
			//serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setSerType("404");//车辆所属人变更
			serviceWater.setRemark("联营卡系统：车辆所属人变更");
			//serviceWater.setFlowState("1");//正常
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);


			return true;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "数据错误！车卡绑定表中无此车牌ID！");
			throw new ApplicationException("数据错误！车卡绑定表中无此车牌ID！");
		}
	}

	@Override
	public List<Map<String, Object>> listBind(Long customerId) {
		return vehicleInfoDao.listBind(customerId);
	}

	@Override
	public List<Map<String, Object>> listBindForMacao(Long customerId) {
		return vehicleInfoDao.listBindForMacao(customerId);
	}

	/**
	 * @param accountCInfo
	 * @param darkList
	 * @param genCau
	 * 		产生原因
	 * @param state
	 * 		状态
	 * @Description:TODO
	 */
	public void saveDarkList(PrepaidC prepaidC, AccountCInfo accountCInfo, DarkList darkList, String genCau, String state) {
		Customer customer = null;
		try {
			if (accountCInfo != null) {
				customer = customerDao.findById(accountCInfo.getCustomerId());
			}
			if (prepaidC != null) {
				customer = customerDao.findById(prepaidC.getCustomerID());
			}
			if (darkList == null) {
				darkList = new DarkList();
				darkList.setId(sequenceUtil.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
				if (accountCInfo != null) {

					darkList.setCustomerId(accountCInfo.getCustomerId());
					darkList.setCardNo(accountCInfo.getCardNo());
					darkList.setCardType("1");

					darkList.setOperId(accountCInfo.getIssueOperId());
					darkList.setPlaceId(accountCInfo.getIssuePlaceId());
					darkList.setOperNo(accountCInfo.getOperNo());
					darkList.setOperName(accountCInfo.getOperName());
					darkList.setPlaceNo(accountCInfo.getPlaceNo());
					darkList.setPlaceName(accountCInfo.getPlaceName());
					//darkList.setUpdateTime(updateTime);
					if (customer != null) {
						darkList.setUserNo(customer.getUserNo());
						darkList.setUserName(customer.getOrgan());
					}
					//darkList.setRemark(remark);
				}
				if (prepaidC != null) {
					customer = customerDao.findById(prepaidC.getCustomerID());
					darkList.setCustomerId(prepaidC.getCustomerID());
					darkList.setCardNo(prepaidC.getCardNo());
					darkList.setCardType("2");

					darkList.setOperId(prepaidC.getSaleOperId());
					darkList.setPlaceId(prepaidC.getSaleOperId());
					darkList.setOperNo(prepaidC.getOperNo());
					darkList.setOperName(prepaidC.getOperName());
					darkList.setPlaceNo(prepaidC.getPlaceNo());
					darkList.setPlaceName(prepaidC.getPlaceName());
					//darkList.setUpdateTime(updateTime);
					if (customer != null) {
						darkList.setUserNo(customer.getUserNo());
						darkList.setUserName(customer.getOrgan());
					}
					//darkList.setRemark(remark);
				}
				darkList.setGenmode("0");//产生方式	系统产生
				darkList.setGenDate(new Date());
				darkList.setGencau(genCau);//产生原因	10—无卡注销。
				darkList.setState(state);
				darkListDao.save(darkList);

			} else {
				darkList.setCustomerId(customer.getId());
				darkList.setUserNo(customer.getUserNo());
				darkList.setUserName(customer.getOrgan());
				darkList.setGencau(genCau);
				darkList.setUpdateTime(new Date());
				darkList.setState(state);
				darkListDao.updateDarkList(darkList);
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "保存清算黑名单数据失败" + accountCInfo.getCardNo());
			throw new ApplicationException("保存清算数黑名单据失败" + accountCInfo.getCardNo());
		}
	}

	@Override
	public Boolean isVehicleBindCard(Long vehicleId) {
		CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleId);
		//如果既没绑定储值卡也没绑定记帐卡，则表示该车辆没绑定卡片
		if (!(isVehicleBindPrepaidC(carObuCardInfo) || isVehicleBindAccountC(carObuCardInfo))) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isVehicleBindPrepaidC(CarObuCardInfo carObuCardInfo) {
		//如果表里找不到，表示没绑定
		if (carObuCardInfo == null) {
			return false;
		}
		if (carObuCardInfo.getPrepaidCID() == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isVehicleBindAccountC(CarObuCardInfo carObuCardInfo) {
		//如果表里找不到，表示没绑定
		if (carObuCardInfo == null) {
			return false;
		}
		if (carObuCardInfo.getAccountCID() == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isVehicleBindPrepaidC(Long vehicleId) {
		CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleId);
		//如果表里找不到，表示没绑定
		if (carObuCardInfo == null) {
			return false;
		}
		if (carObuCardInfo.getPrepaidCID() == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean isVehicleBindAccountC(Long vehicleId) {
		CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleId);
		//如果表里找不到，表示没绑定
		if (carObuCardInfo == null) {
			return false;
		}
		if (carObuCardInfo.getAccountCID() == null) {
			return false;
		}
		return true;
	}

	/**
	 * 根据客户id查找车辆信息与标签号
	 * 主要给查询  车辆模态框用(找没有绑定卡片的车辆与电子标签号)
	 */
	@Override
	public List<Map<String, Object>> findVehicleTagNo(Long customerId) {
		return vehicleInfoDao.findVehicleTagNo(customerId);
	}

	/*
	 * 主要给查询  车辆模态框用(找绑定卡片的车辆与电子标签号)
	 * (non-Javadoc)
	 * @see com.hgsoft.customer.serviceInterface.IVehicleInfoService#findBindCardVehicle(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> findBindCardVehicle(Long customerId) {
		return vehicleInfoDao.findBindCardVehicleTagNo(customerId);
	}

	@Override
	public boolean hasApproval(Long vehicleId) {
		try {
			return vehicleModifyApplyDao.hasApproval(vehicleId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "车辆审核信息获取失败");
			throw new ApplicationException();
		}
	}

	@Override
	public void saveApply(VehicleInfo oldVehicleInfo, VehicleInfo newVehicleInfo, String path) {
		// TODO Auto-generated method stub
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSVehicleModifyApply_NO");
		Long id = Long.parseLong(seq.toString());
		vehicleModifyApplyDao.save(id, oldVehicleInfo, newVehicleInfo, path, new Date());

	}

	/**
	 * @param vehicleImp
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年6月9日
	 */
	@Override
	public boolean hasVehicleImp(VehicleImp vehicleImp) {
		try {
			VehicleImp vehicle = vehicleImpDao.findByPlateAndColor(vehicleImp);
			return vehicle != null;
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException | InvocationTargetException e) {
		}
		return false;
	}

	public boolean hasVehicle(VehicleImp vehicleImp) {
		VehicleInfo vehicleInfo = vehicleInfoDao.findByVehicleImp(vehicleImp);
		return vehicleInfo != null;
	}

	/**
	 * @param advanceList
	 * @param serviceFlowRecord
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年6月9日
	 */
	@Override
	public void saveBatchVehicle(List<VehicleImp> vehicleImpList, ServiceFlowRecord serviceFlowRecord) {
		try {
			vehicleImpDao.batchSaveVehicle(vehicleImpList, serviceFlowRecord);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "车辆预录入保存失败");
			throw new ApplicationException();
		}

	}

	/**
	 * @param vehiclePlate
	 * @param vehicleColor
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年6月19日
	 */
	@Override
	public List<Map<String, Object>> findVehicleImpByPlateAndColor(String vehiclePlate, String vehicleColor) {
		return vehicleImpDao.findByPlateAndColor(vehiclePlate, vehicleColor);
	}


	/**
	 * @param id
	 * @return
	 * @Descriptioqn:验证车辆信息是否完整，
	 * @author lgm
	 * @date 2017年6月28日
	 */
	@Override
	public boolean checkRequired(Long id) {
		VehicleInfo vehicleInfo = vehicleInfoDao.findById(id);

		String vehiclePlate = vehicleInfo.getVehiclePlate();
		String owner = vehicleInfo.getOwner();
		String model = vehicleInfo.getModel();
		String usingNature = vehicleInfo.getUsingNature();
		String vehicleType = vehicleInfo.getVehicleType();
		String vehicleEngineNo = vehicleInfo.getVehicleEngineNo();
		String identificationCode = vehicleInfo.getIdentificationCode();
		Long wheels = vehicleInfo.getVehicleWheels();

		String vehicleColor = vehicleInfo.getVehicleColor();
		String vehicleUserType = vehicleInfo.getVehicleUserType();
		Long vehicleLong = vehicleInfo.getVehicleLong();
		Long vehicleWidth = vehicleInfo.getVehicleWidth();
		Long vehicleHeight = vehicleInfo.getVehicleHeight();
		Long vehicleWeightLimits = vehicleInfo.getVehicleWeightLimits();
		Long vehicleAxles = vehicleInfo.getVehicleAxles();

		if ("2".equals(vehicleType)) {
			if (!StringUtil.isNotBlank(vehiclePlate) || !StringUtil.isNotBlank(owner) || !StringUtil.isNotBlank(model) || !StringUtil.isNotBlank(usingNature)
					|| !StringUtil.isNotBlank(vehicleType) || !StringUtil.isNotBlank(vehicleEngineNo) || !StringUtil.isNotBlank(identificationCode) || wheels == null
					|| !StringUtil.isNotBlank(vehicleColor) || vehicleLong == null || vehicleWidth == null || vehicleHeight == null || !StringUtil.isNotBlank(vehicleUserType)
					|| vehicleWeightLimits == null || vehicleAxles == null) {
				return false;
			}
		} else {//货车无需判断车轮数和车轴数
			if (!StringUtil.isNotBlank(vehiclePlate) || !StringUtil.isNotBlank(owner) || !StringUtil.isNotBlank(model) || !StringUtil.isNotBlank(usingNature)
					|| !StringUtil.isNotBlank(vehicleType) || !StringUtil.isNotBlank(vehicleEngineNo) || !StringUtil.isNotBlank(identificationCode)
					|| !StringUtil.isNotBlank(vehicleColor) || vehicleLong == null || vehicleWidth == null || vehicleHeight == null || !StringUtil.isNotBlank(vehicleUserType)
					|| vehicleWeightLimits == null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Map<String, Object> saveMigrateReturnMap(VehicleInfo vehicleInfo, Long customerId, SysAdmin sysAdmin,
	                                                CusPointPoJo cusPointPoJo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			vehicleInfoDao.update(vehicleInfo);
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if (carObuCardInfo == null) {
				throw new ApplicationException();
			}
			//新增车辆业务记录表CSMS_Vehicle_Bussiness
			VehicleBussiness vehicleBussiness = new VehicleBussiness();
			BigDecimal SEQ_CSMSVehicleBussiness_NO = sequenceUtil.getSequence("SEQ_CSMSVehicleBussiness_NO");
			vehicleBussiness.setId(Long.parseLong(SEQ_CSMSVehicleBussiness_NO.toString()));
			vehicleBussiness.setCustomerID(vehicleInfo.getCustomerID());
			vehicleBussiness.setVehiclePlate(vehicleInfo.getVehiclePlate());
			vehicleBussiness.setVehicleColor(vehicleInfo.getVehicleColor());
			vehicleBussiness.setType(VehicleBussinessEnum.migrateVehicle.getValue());
			vehicleBussiness.setCreateTime(new Date());
			vehicleBussiness.setMemo("车辆迁移");
			vehicleBussiness.setOperID(sysAdmin.getId());
			vehicleBussiness.setOperNo(sysAdmin.getStaffNo());
			vehicleBussiness.setOperName(sysAdmin.getUserName());
			vehicleBussiness.setPlaceID(cusPointPoJo.getCusPoint());
			vehicleBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			vehicleBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			vehicleBussiness.setCreateTime(new Date());

			//找出该车辆所有图片
			Material material = new Material();
			material.setVehicleID(vehicleInfo.getId());
			List<Material> materials = materialDao.findMateria(material);

			StringBuffer savePaths = new StringBuffer();
			;
			StringBuffer oldPicPaths = new StringBuffer();
			Material newMaterial = null;
			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());
			//获得图片资料的最新一个编码
			long code = Long.parseLong(materialDao.getLastCode(customer.getId(), material.getType()));
			//修改该车辆的图片路径&&客户id
			if (materials != null && materials.size() > 0) {
				for (int i = 0; i < materials.size(); i++) {
					oldPicPaths.append("," + materials.get(i).getPicAddr());
					//图片后缀
					String endFlag = materials.get(i).getPicAddr().substring(materials.get(i).getPicAddr().indexOf("."));
					//最新编码+1
					code = code + 1;
					//保存图片文件
					String savePath = StringUtil.getPicBasePath(materials.get(i).getType(), code + "", customer.getUserNo(), "car");
					String name = StringUtil.getPicName(materials.get(i).getType(), code + "");
					savePath = savePath + name + endFlag;
					logger.info("savePath:" + savePath);
					savePaths.append("," + savePath);

					newMaterial = materials.get(i);
					newMaterial.setCustomerID(vehicleInfo.getCustomerID());
					newMaterial.setPicAddr(savePath);
					newMaterial.setUp_Date(new Date());
					materialDao.updateMateria(newMaterial);
				}
			}

			String oldPicPathsStr = "";
			String savePathsStr = "";
			if (oldPicPaths.length() > 1) {
				oldPicPathsStr = oldPicPaths.substring(1);
			}
			if (savePaths.length() > 1) {
				savePathsStr = savePaths.substring(1);
			}
			resultMap.put("oldPicPaths", oldPicPathsStr);
			resultMap.put("savePaths", savePathsStr);


			//将卡和OBU（未有黑名单）均下挂黑名单 （可能要修改）
			if (carObuCardInfo.getPrepaidCID() != null) {
				PrepaidC prepaidC = prepaidCDao.findById(carObuCardInfo.getPrepaidCID());
				if (prepaidC == null) {
					resultMap.put("result", "false");
					resultMap.put("message", "数据错误，无法找到储值卡信息");
					return resultMap;
				}

				vehicleBussiness.setCardType(Constant.PREPAIDTYPE);
				vehicleBussiness.setCardNo(prepaidC.getCardNo());

				//车辆迁移，如果原来车辆有储值卡，则把原储值卡注销（铭鸿的清算数）
				blackListService.saveCardCancle(Constant.PREPAIDTYPE, prepaidC.getCardNo(), new Date()
						, "2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(),
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(),
						new Date());
			}
			if (carObuCardInfo.getAccountCID() != null) {
				AccountCInfo accountCInfo = accountCDao.findById(carObuCardInfo.getAccountCID());
				if (accountCInfo == null) {
					resultMap.put("result", "false");
					resultMap.put("message", "数据错误，无法找到记帐卡信息");
					return resultMap;
				}
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);
				vehicleBussiness.setCardNo(accountCInfo.getCardNo());

				//车辆迁移，如果原车辆有记帐卡，则注销记帐卡（铭鸿的清算数据）
				blackListService.saveCardCancle(Constant.ACCOUNTCTYPE, accountCInfo.getCardNo(), new Date()
						, "2", vehicleBussiness.getOperID(), vehicleBussiness.getOperNo(), vehicleBussiness.getOperName(),
						vehicleBussiness.getPlaceID(), vehicleBussiness.getPlaceNo(), vehicleBussiness.getPlaceName(),
						new Date());
			}
			carObuCardInfo.setTagID(null);
			carObuCardInfo.setPrepaidCID(null);
			carObuCardInfo.setAccountCID(null);
			carObuCardInfoDao.update(carObuCardInfo);

			vehicleBussinessDao.save(vehicleBussiness);


			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");
			CustomerBussiness customerBussiness = new CustomerBussiness();
			customerBussiness.setId(seq);
			customerBussiness.setVehicleId(vehicleInfo.getId());
			customerBussiness.setCustomerId(customerId);
			customerBussiness.setMigratecustomerId(vehicleInfo.getCustomerID());
			customerBussiness.setType(CustomerBussinessTypeEnum.vechileMigrate.getValue());
			customerBussiness.setOperId(sysAdmin.getId());
			customerBussiness.setOperNo(sysAdmin.getStaffNo());
			customerBussiness.setOperName(sysAdmin.getLoginName());
			customerBussiness.setPlaceId(cusPointPoJo.getCusPoint());
			customerBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
			customerBussiness.setPlaceName(cusPointPoJo.getCusPointName());
			customerBussiness.setCreateTime(new Date());
			customerBussinessDao.save(customerBussiness);


			Customer orignalCustomer = customerDao.findById(customerId);
			//客户服务流水
			ServiceWater serviceWater = new ServiceWater();
			serviceWater.setId(Long.parseLong(sequenceUtil.getSequence("SEQ_CSMSServiceWater_NO").toString()));
			if (orignalCustomer != null) serviceWater.setCustomerId(orignalCustomer.getId());
			if (orignalCustomer != null) serviceWater.setUserNo(orignalCustomer.getUserNo());
			if (orignalCustomer != null) serviceWater.setUserName(orignalCustomer.getOrgan());
			//serviceWater.setAulAmt(accountBussiness.getRealPrice());
			serviceWater.setSerType("404");//车辆所属人变更
			serviceWater.setRemark("自营客服系统：车辆所属人变更");
			//serviceWater.setFlowState("1");//正常
			serviceWater.setVehicleBussinessId(vehicleBussiness.getId());
			serviceWater.setCustomerBussinessId(customerBussiness.getId());
			serviceWater.setOperId(vehicleBussiness.getOperID());
			serviceWater.setOperNo(vehicleBussiness.getOperNo());
			serviceWater.setOperName(vehicleBussiness.getOperName());
			serviceWater.setPlaceId(vehicleBussiness.getPlaceID());
			serviceWater.setPlaceNo(vehicleBussiness.getPlaceNo());
			serviceWater.setPlaceName(vehicleBussiness.getPlaceName());
			serviceWater.setOperTime(new Date());
			serviceWaterDao.save(serviceWater);


			resultMap.put("result", "true");
			return resultMap;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "数据错误！车卡绑定表中无此车牌ID！");
			throw new ApplicationException("数据错误！车卡绑定表中无此车牌ID！");
		}
	}

	/**
	 * 保存回执
	 *
	 * @param receipt
	 * 		回执主要信息
	 * @param customerBussiness
	 * 		客户业务
	 * @param baseReceiptContent
	 * 		回执VO
	 * @param customer
	 * 		客户信息
	 */
	private void saveReceipt(Receipt receipt, CustomerBussiness customerBussiness, BaseReceiptContent baseReceiptContent, Customer customer) {
		receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.customer.getValue());
		receipt.setCreateTime(customerBussiness.getCreateTime());
		receipt.setPlaceId(customerBussiness.getPlaceId());
		receipt.setPlaceNo(customerBussiness.getPlaceNo());
		receipt.setPlaceName(customerBussiness.getPlaceName());
		receipt.setOperId(customerBussiness.getOperId());
		receipt.setOperNo(customerBussiness.getOperName());
		receipt.setOperName(customerBussiness.getOperName());
		receipt.setOrgan(customer.getOrgan());
		baseReceiptContent.setCustomerNo(customer.getUserNo());
		baseReceiptContent.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
		baseReceiptContent.setCustomerIdCode(customer.getIdCode());
		baseReceiptContent.setCustomerName(customer.getOrgan());
		receipt.setContent(JSONObject.fromObject(baseReceiptContent).toString());
		this.receiptDao.saveReceipt(receipt);
	}

	//被动挂起
	@Override
	public Map<String, Object> savePassiveStop(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			Customer customer = customerDao.findById(vehicleInfo.getCustomerID());
			if (carObuCardInfo == null) {
				resultMap.put("result", "false");
				resultMap.put("message", "异常情况：无法找到carObuCardInfo");
				return resultMap;
			}
			if (customer == null) {
				resultMap.put("result", "false");
				resultMap.put("message", "异常情况：无法找到customer");
				return resultMap;
			}
			//处理卡签业务
			if (carObuCardInfo.getAccountCID() != null) {
				AccountCInfo accountCInfo = accountCDao.findById(carObuCardInfo.getAccountCID());
				if (accountCInfo == null) {
					throw new ApplicationException("数据错误！无法找到记帐卡信息记录");
				}
				vehiclePassiveStopJob.dealAccountC4PassiveStop(accountCInfo, vehicleInfo, vehicleBussiness, customer);
				vehicleBussiness.setCardNo(accountCInfo.getCardNo());
				vehicleBussiness.setCardType(Constant.ACCOUNTCTYPE);
			} else if (carObuCardInfo.getPrepaidCID() != null) {
				PrepaidC prepaidC = prepaidCDao.findById(carObuCardInfo.getPrepaidCID());
				if (prepaidC == null) {
					throw new ApplicationException("数据错误！无法找到储值卡信息记录");
				}
				vehiclePassiveStopJob.dealPrepaidC4PassiveStop(prepaidC, vehicleInfo, vehicleBussiness, customer);
				vehicleBussiness.setCardNo(prepaidC.getCardNo());
				vehicleBussiness.setCardType(Constant.PREPAIDTYPE);
			}
			if (carObuCardInfo.getTagID() != null) {
				TagInfo tagInfo = tagInfoDao.findById(carObuCardInfo.getTagID());
				if (tagInfo == null) {
					throw new ApplicationException("数据错误！无法找到电子标签信息记录");
				}
				vehiclePassiveStopJob.dealTag4PassiveStop(tagInfo, vehicleInfo, vehicleBussiness, customer);
				vehicleBussiness.setTagNo(tagInfo.getTagNo());
			}
			//处理完成卡签的相关业务，再做删除车辆信息的业务
			vehiclePassiveStopJob.deleteVehicleInfo(vehicleInfo, vehicleBussiness, customer);
			//只记录一条业务记录：被动挂起的车辆信息业务记录
			vehiclePassiveStopJob.saveVehicleBussiness(vehicleBussiness);
			//客服流水只应该记录一条：被动挂起
			vehiclePassiveStopJob.saveServiceWater(customer, vehicleBussiness, null);

			//被动挂起回执
//			PassiveStopReceipt passiveStopReceipt = new PassiveStopReceipt();
//			passiveStopReceipt.setTitle("被动挂起回执");
//			passiveStopReceipt.setHandleWay(ReceiptUtil.getHandleWay(params.get("authenticationType")+""));
//			passiveStopReceipt.setVehicleOwner(vehicleInfo.getOwner());
//			passiveStopReceipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
//			passiveStopReceipt.setVehiclePlateColor(VehicleColorEnum.getName(vehicleInfo.getVehicleColor()));
//			passiveStopReceipt.setVehicleWeightLimits(vehicleInfo.getVehicleWeightLimits()+"");
//			passiveStopReceipt.setVehicleModel(vehicleInfo.getModel());
//			passiveStopReceipt.setVehicleType(VehicleTypeEnum.getName(vehicleInfo.getVehicleType()));
//			passiveStopReceipt.setVehicleUsingNature(UsingNatureEnum.getName(vehicleInfo.getUsingNature()));
//			passiveStopReceipt.setVehicleEngineNo(vehicleInfo.getVehicleEngineNo());
//			passiveStopReceipt.setVehicleIdentificationCode(vehicleInfo.getIdentificationCode());
//			passiveStopReceipt.setVehicleNSCvehicletype(NSCVehicleTypeEnum.getNameByValue(vehicleInfo.getNSCVehicleType()));
//			passiveStopReceipt.setVehicleLong(vehicleInfo.getVehicleLong()+"");
//			passiveStopReceipt.setVehicleWidth(vehicleInfo.getVehicleWidth()+"");
//			passiveStopReceipt.setVehicleHeight(vehicleInfo.getVehicleHeight()+"");
//			passiveStopReceipt.setVehicleAxles(vehicleInfo.getVehicleAxles()+"");
//			passiveStopReceipt.setVehicleWheels(vehicleInfo.getVehicleWheels()+"");
//			passiveStopReceipt.setCustomerNo(customer.getUserNo());
//			passiveStopReceipt.setCustomerIdType(IdTypeEnum.getName(customer.getIdType()));
//			passiveStopReceipt.setCustomerIdCode(customer.getIdCode());
//			passiveStopReceipt.setCustomerName(customer.getOrgan());

			Receipt receipt = new Receipt();
			receipt.setParentTypeCode(ReceiptParentTypeCodeEnum.vehicle.getValue());
			receipt.setTypeCode(VehicleBussinessEnum.passiveStop.getValue());
			receipt.setTypeChName(VehicleBussinessEnum.passiveStop.getName());
			receipt.setCreateTime(new Date());
			receipt.setPlaceId(vehicleBussiness.getPlaceID());
			receipt.setPlaceNo(vehicleBussiness.getPlaceNo());
			receipt.setPlaceName(vehicleBussiness.getPlaceName());
			receipt.setOperId(vehicleBussiness.getOperID());
			receipt.setOperNo(vehicleBussiness.getOperNo());
			receipt.setOperName(vehicleBussiness.getOperName());
			receipt.setOrgan(customer.getOrgan());
//			receipt.setContent(JSONObject.fromObject(passiveStopReceipt).toString());
			receipt.setVehicleColor(vehicleInfo.getVehicleColor());
			receipt.setVehiclePlate(vehicleInfo.getVehiclePlate());
			this.receiptDao.saveReceipt(receipt);


		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + "被动挂起失败！！");
			throw new ApplicationException("被动挂起失败！！");
		}

		resultMap.put("result", "true");
		return resultMap;
	}

	/**
	 * @param placeNo
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年9月25日
	 */
	@Override
	public AppPointCtr findAppPointCtrByNo(String placeNo) {
		return appPointCtrDao.findByPlaceNo(placeNo);
	}

	/**
	 * @param vehicleInfo
	 * @param placeNo
	 * @return
	 * @Descriptioqn:
	 * @author lgm
	 * @date 2017年9月25日
	 */
	@Override
	public Map<String, Object> findAuthByVehicleAndNo(VehicleInfo vehicleInfo, String placeNo) {
		return appDataDao.findVehAuthInfo(vehicleInfo.getVehiclePlate(), vehicleInfo.getVehicleColor());
	}

	@Override
	public Pager findByVehicleAndHolderACMS(Pager pager, Customer customer, VehicleInfo vehicleInfo, CardHolder cardHolder) {
		return vehicleInfoDao.findByVehicleAndHolderACMS(pager, customer, vehicleInfo, cardHolder);
	}

}
