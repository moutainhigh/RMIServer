package com.hgsoft.online.service;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.dao.AccountCApplyDao;
import com.hgsoft.accountC.dao.AccountCBussinessDao;
import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.serviceInterface.IAccountCService;
import com.hgsoft.accountC.serviceInterface.IRelieveStopPayService;
import com.hgsoft.common.Enum.CustomerBussinessTypeEnum;
import com.hgsoft.common.Enum.PrepaidCardBussinessTypeEnum;
import com.hgsoft.customer.dao.*;
import com.hgsoft.customer.entity.*;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.customer.serviceInterface.IInvoiceService;
import com.hgsoft.customer.serviceInterface.IVehicleInfoService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.online.dao.OnlineDataDao;
import com.hgsoft.online.dao.ServicePwdResetInfoDao;
import com.hgsoft.online.entity.ReqInterfaceFlow;
import com.hgsoft.online.entity.ServicePwdResetInfo;
import com.hgsoft.online.serviceInterface.IOnlineDataInterfaceService;
import com.hgsoft.prepaidC.dao.DbasCardFlowDao;
import com.hgsoft.prepaidC.dao.PrepaidCBussinessDao;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.serviceInterface.IPrepaidCService;
import com.hgsoft.settlement.dao.BillInfoDao;
import com.hgsoft.settlement.entity.MonthlyReg;
import com.hgsoft.settlement.serviceinterface.IMonthlyRegService;
import com.hgsoft.system.dao.ServiceWaterDao;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.ServiceWater;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.*;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
/**
 * 线上接口service
 * @author gsf
 * 2016-08-03
 */
@Service
public class OnlineDataInterfaceService implements IOnlineDataInterfaceService{
	
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private OnlineDataDao onlineDataDao;
	@Resource
	private BillInfoDao billInfoDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;
	
	@Resource
	private MaterialDao materialDao;
	
	@Resource
	private BillGetDao billGetDao;
	@Resource
	private BillGetHisDao billGetHisDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	@Resource
	private AccountCInfoDao  accountCInfoDao;
	@Resource
	private AccountCBussinessDao accountCBussinessDao;
	@Resource
	private AccountCApplyDao accountCApplyDao;
	@Resource
	private PrepaidCBussinessDao prepaidCBussinessDao;
	/*@Resource
	private AcInvoiceDao acInvoiceDao;*/
	
	@Resource
	private ICustomerService customerService;
	@Resource
	private IVehicleInfoService vehicleInfoService;
	@Resource
	private IInvoiceService invoiceService;
	@Resource
	private IAccountCService accountCService;
	@Resource
	private IPrepaidCService prepaidCService;
	@Resource
	private IRelieveStopPayService relieveStopPayService;

	@Resource
	private IMonthlyRegService monthlyRegService;
	
	@Resource
	private DbasCardFlowDao dbasCardFlowDao;

	@Resource
	private ServicePwdResetInfoDao servicePwdResetInfoDao;

	@Resource
	private CustomerBussinessDao customerBussinessDao;

	@Resource
	private ServiceWaterDao serviceWaterDao;
	
	private static Logger logger = Logger.getLogger(OnlineDataInterfaceService.class.getName());

	public static final String YMDHMS_STANDARD = "yyyyMMddHHmmss";


	@Override
	public void saveReqInterfaceFlow(ReqInterfaceFlow reqInterfaceFlow) {
		try {
			Long seq = sequenceUtil.getSequenceLong("SEQ_CSMSREQINTERFACEFLOW_NO");
			reqInterfaceFlow.setId(seq);
			onlineDataDao.saveReqInterfaceFlow(reqInterfaceFlow);			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：增加接口请求流水记录失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：增加接口请求流水记录失败");
		}
		
	}
	
	@Override
	public AccountCInfo findACByCardNo(String cardNo) {
		return accountCInfoDao.findByCardNo(cardNo);
	}

	@Override
	public PrepaidC findPCByCardNo(String cardNo) {
		return prepaidCDao.findByPrepaidCNo(cardNo);
	}
	
	//-----------------查询管理-------------------
	
	/**
	 * 客户校验
	 */
	@Override
	public List findCheckUserValidity(String IdType, String IdCode, String userNo, String cardNo, String ServicePwd) {
		return onlineDataDao.findCheckUserValidity(IdType, IdCode, userNo, cardNo, ServicePwd);
	}

	/**
	 * 客户号查询
	 */
	@Override
	public Map<String, Object> findQueryUserNo(String IdType, String IdCode, String cardNo,String bankAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = null;
		list =  onlineDataDao.findByTypeCodeCardNoBankNo(IdType, IdCode, cardNo,bankAccount);
		if(list == null || list.isEmpty()){
			map.put("result", "false");
			map.put("message", "查无此信息");
		}else{
			map.put("result", "true");
			map.put("list", list);
			map.put("message", "查询成功");
		}
		return  map;
	}
	/**
	 * 客户信息查询
	 */
	@Override
	public Map<String, Object> findQueryCustormerInfo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> prePaidCardList = null;
		List<Map<String, Object>> accountCardList = null;
		List<Map<String, Object>> tagList = null;
		List<Map<String, Object>> VehicleList = null;
		
		Customer customer  = customerDao.findByUserNo(userNo);
		if(customer==null){
			map.put("result", "false");
			map.put("message", "客户不存在");
			return map;
		}else{
			//prePaidCardList = onlineDataDao.findPrePaidCardList(customer.getId());
			//accountCardList = onlineDataDao.findAccountCardList(customer.getId());
			//tagList = onlineDataDao.findTagList(customer.getId());
			//VehicleList = onlineDataDao.findVehicleList(customer.getId());
			
			map.put("result", "true");
			map.put("message", "查询到客户");
			//map.put("prePaidCardList", prePaidCardList);
			//map.put("accountCardList", accountCardList);
			//map.put("tagList", tagList);
			//map.put("VehicleList", VehicleList);
			
			map.put("customer", customer);
		}
		
		return map;
	}
	
	/* 客户储值卡信息查询
	 * (non-Javadoc)
	 * @see com.hgsoft.online.serviceInterface.IOnlineDataInterfaceService#findQueryPrepaidCList(java.lang.String, com.hgsoft.utils.Pager)
	 */
	@Override
	public Map<String, Object> findQueryPrepaidCList(String userNo,Pager pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		Customer customer  = customerDao.findByUserNo(userNo);
		if(customer==null){
			map.put("result", "false");
			map.put("message", "客户不存在");
			return map;
		}else{
			pager = onlineDataDao.findQueryPrepaidC(userNo, pager);
			map.put("result", "true");
			map.put("message", "查询成功");
			map.put("pager", pager);
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> findQueryAccountCList(String userNo,Pager pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		Customer customer  = customerDao.findByUserNo(userNo);
		if(customer==null){
			map.put("result", "false");
			map.put("message", "客户不存在");
			return map;
		}else{
			pager = onlineDataDao.findQueryAccountC(userNo, pager);
			map.put("result", "true");
			map.put("message", "查询成功");
			map.put("pager", pager);
		}
		
		return map;
	}
	@Override
	public Map<String, Object> findQueryTagList(String userNo,Pager pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		Customer customer  = customerDao.findByUserNo(userNo);
		if(customer==null){
			map.put("result", "false");
			map.put("message", "客户不存在");
			return map;
		}else{
			pager = onlineDataDao.findQueryTag(userNo, pager);
			map.put("result", "true");
			map.put("message", "查询成功");
			map.put("pager", pager);
		}
		
		return map;
	}
	@Override
	public Map<String, Object> findQueryVehicleList(String userNo,Pager pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		Customer customer  = customerDao.findByUserNo(userNo);
		if(customer==null){
			map.put("result", "false");
			map.put("message", "客户不存在");
			return map;
		}else{
			pager = onlineDataDao.findQueryVehicle(userNo,pager);
			map.put("result", "true");
			map.put("message", "查询成功");
			map.put("pager", pager);
		}
		
		return map;
	}

	@Override
	public Map<String, Object> findVehicleCardBindQuery(String vehiclePlate, String vehicleColor, String cardNo) {
		Map<String, Object> map = null;
		
		map = onlineDataDao.findVehicleCardBindQuery(vehiclePlate, vehicleColor, cardNo);
		
		if(map==null){
			map = new HashMap<String,Object>();
			map.put("result", "false");
			map.put("message", "查无此信息");
		}else{
			map.put("result", "true");
			map.put("message", "查询成功");
		}
		return map;
	}
	@Override
	public Map<String, Object> findQueryCardAcinvoice(String cardUserNo,String cardType,String month){
		//原清算数据，没用了       要改为查铭鸿的表    TODO
		/*return acInvoiceDao.findQueryCardAcinvoice(cardUserNo, cardType,month);*/
		Map<String, Object> map = new HashMap<String,Object>();
		String cardNo = null;
		String userNo = null;
		//cardType ：1-客户号  2-卡号

//		//通过卡号获取UserNo
//		if("2".equals(cardType)){
//			Map<String, Object> userMap = null;
//			userMap = onlineDataDao.findByTypeCodeCardNoBankNo("", "", cardUserNo,"");
//			userNo = (String)userMap.get("UserNo");
//		}else{
//			userNo = cardUserNo;
//		}
		List<Map<String,Object>> list = null;
		if(cardType.equals("1")){
			userNo = cardUserNo;
			list = billInfoDao.findBillInfoByUserNo(userNo,month);
		}else if(cardType.equals("2")){
			cardNo = cardUserNo;
			if(RegularUtil.isPrePaidCard(cardNo)){
				list = billInfoDao.findScBillInfoByCardNo(cardNo,month);
			}else {
				list = billInfoDao.findAcBillInfoByCardNo(cardNo,month);
			}
		}
		if(list == null || list.isEmpty()){
			map.put("result", "false");
			map.put("message", "查无此信息");
		}else{
			map.put("result", "true");
			map.put("list", list);
			map.put("message", "查询成功");
		}
		return  map;
	}

	//---------------客户资料管理--------------------
	
	/**
	 * 验证客户是否已存在
	 * 已存在返回false
	 */
	@Override
	public Customer checkCustomer(Customer customer) {
		return customerDao.findByIdNo(customer.getIdType(), customer.getIdCode());
	}

	@Override
	public Map<String, Object> saveAddCustormerInfo(Customer customer, Invoice invoice, MainAccountInfo mainAccountInfo,
			SubAccountInfo subAccountInfo, ServiceFlowRecord serviceFlowRecord) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//客服系统的原来新增客户操作
			String CustomerUserno=customerService.saveCustomer(customer, null, null, null, null, null, mainAccountInfo, subAccountInfo, invoice, serviceFlowRecord,"");
			
			//线上接口的额外操作
			
			map.put("result", "true");
			map.put("message", "新增客户成功");
			map.put("Userno",CustomerUserno);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：客户新增失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：客户新增失败");
		}
		
		return map;
	}
	/**
	 * 客户信息修改
	 */
	@Override
	public Map<String, Object> updateCustormerInfo(Customer customer, CustomerHis customerHis) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			String remark = "网上营业厅系统：客户资料修改";
			//客服系统的原来修改客户操作
			customerService.updateCustomer4OnLine(customer, customerHis,remark);
			
			//线上接口的额外操作
			
			map.put("result", "true");
			map.put("message", "客户信息修改成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：客户信息修改失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：客户信息修改失败");
		}
		
		return map;
	}

	/**
	 * 客户信息修改(网厅)
	 */
	@Override
	public Map<String, Object> updateCustormerInfo4OnLine(Customer customer, CustomerHis customerHis,String remark) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//客服系统的原来修改客户操作
			customerService.updateCustomer4OnLine(customer, customerHis,remark);

			//线上接口的额外操作

			map.put("result", "true");
			map.put("message", "客户信息修改成功");

		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：客户信息修改失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：客户信息修改失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> saveAddVehicleInfo(VehicleInfo vehicleInfo, Customer customer,
			VehicleBussiness vehicleBussiness) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//客服系统的原来新增车辆操作
			vehicleInfoService.saveVehicle(vehicleInfo, null, null, null, customer, null, vehicleBussiness,"",new HashMap<String,Object>());
			
			//线上接口的额外操作
			
			map.put("result", "true");
			map.put("message", "新增车辆信息成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：车辆信息新增失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：车辆信息新增失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> updateVehicleInfo(VehicleInfo vehicleInfo,VehicleInfo newvehicleInfo) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//客服系统的原来车辆信息修改操作
			CarObuCardInfo carObuCardInfo = carObuCardInfoDao.findByVehicleID(vehicleInfo.getId());
			if(carObuCardInfo!=null&&(carObuCardInfo.getAccountCID()!=null||carObuCardInfo.getPrepaidCID()!=null||carObuCardInfo.getTagID()!=null)){
				map.put("result", "false");
				map.put("message", "该车牌已经绑定卡或标签");
				
				return map;
			}
			vehicleInfoService.updateVehicle(vehicleInfo,newvehicleInfo,new HashMap<String,Object>());
			
			//线上接口的额外操作
			
			
			map.put("result", "true");
			map.put("message", "车辆信息修改成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：车辆信息修改失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：车辆信息修改失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> saveFileUpload(Material material,String tempPicName, Customer customer,
			VehicleInfo vehicleInfo) {
		Map<String, Object> map = new HashMap<String,Object>();
		boolean result = false;
		try {
			//BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
			//material.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
			//customerService.saveOrUpdateMaterial(material, rootPath, tempPicNameList, customer);
			
			StringBuffer oldPicPaths = new StringBuffer("");
			
			if(vehicleInfo==null){
				//result = customerService.saveOrUpdateMaterial(material, rootPath, tempPicNameList, customer);
				try {
					//文件 
						//根据当前客户ID、类型、code查找图片资料表记录
						List<Material> materials = materialDao.findMateria(material);
						if(materials != null&&materials.size()>0){
							Integer updateTime = 1;
							for(int i=0;i<materials.size();i++){
								oldPicPaths.append(","+materials.get(i).getPicAddr());
								//将图片资料表的数据删掉
								updateTime = materials.get(i).getUpdateTime();//先存储要删掉的数据的修改次数
								materialDao.deleteMateria(materials.get(i).getId());
							}
							//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
							//保存图片文件
							
							BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
							material.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
							material.setCustomerID(customer.getId());
							material.setUpdateTime(updateTime+1);
								
							materialDao.saveMateria(material);
							
						}else{
							//保存图片文件
							BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
							material.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
							material.setUpdateTime(1);
							
							materialDao.saveMateria(material);
						}
				} catch (ApplicationException e) {
					e.printStackTrace();
					logger.error(e.getMessage()+"图片资料数据保存失败");
					throw new ApplicationException();
				} catch (Exception e) {
					logger.error(e.getMessage()+"图片资源处理失败");
					e.printStackTrace();
				}
			
			}else{
				try {
						//根据当前客户ID、类型、code查找图片资料表记录
						List<Material> materials = materialDao.findMateria(material);
						//materials有可能为[]
						if(materials != null&&materials.size()>0){
							Integer updateTime = 1;
							for(int i=0;i<materials.size();i++){
								oldPicPaths.append(","+materials.get(i).getPicAddr());
								//将图片资料表的数据删掉
								updateTime = materials.get(i).getUpdateTime();//先存储要删掉的数据的修改次数
								materialDao.deleteMateria(materials.get(i).getId());
							}
							//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
							//若无则新增，修改次数=1，更新时间=当前时间
							BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
							material.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
							material.setCustomerID(customer.getId());
							material.setVehicleID(vehicleInfo.getId());
							material.setUpdateTime(updateTime+1);
							
							materialDao.saveMateria(material);
						}else{
							//String newImageFileName =  material.getCode() + System.currentTimeMillis()+".jpg";
							//保存图片文件
							//若无则新增，修改次数=1，更新时间=当前时间
							BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
							material.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
							material.setCustomerID(customer.getId());
							material.setVehicleID(vehicleInfo.getId());
							material.setUpdateTime(1);
							
							materialDao.saveMateria(material);
						}
				} catch (ApplicationException e) {
					e.printStackTrace();
					logger.error(e.getMessage()+"图片资料数据保存失败");
					throw new ApplicationException();
				} catch (Exception e) {
					logger.error(e.getMessage()+"图片资源处理失败");
					e.printStackTrace();
					
				}
				
			}
			
			//HTTP访问图片服务器删除旧图片
			try {
				String url = PropertiesUtil.getValue("/url.properties","picDeleteUrl");
				String data = "oldPicPaths="+oldPicPaths;
				Map<String, Object> resultMap = HttpUtil.callClientByHTTP(url, data, "POST");
				//System.out.println(resultMap.get("result"));
				if(resultMap!=null&&resultMap.get("result")!=null&&"true".equals(resultMap.get("result").toString())){
					result = true;
				}else{
					result = false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage()+"线上接口：删除旧图片失败");
				e.printStackTrace();
			} 
			
			//线上接口的额外操作
			map.put("result", "true");
			map.put("message", "保存上传资料成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：保存上传资料失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：保存上传资料失败");
		}
		
		return map;
	}

	
	//---------------服务信息管理---------------------
	
	@Override
	public Map<String, Object> findQueryBillInfo(String userNo) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		List<Map<String, Object>> list = onlineDataDao.findQueryBillInfo(userNo);
		
		map.put("result", "true");
		map.put("message", "查询成功");
		map.put("list", list);
		
		return map;
	}

	/****
	 * 信息服务变更
	 * @param customer
	 * @param billGet
     * @return
     */
	@Override
	public Map<String, Object> saveModifyBillInfo(Customer customer,BillGet billGet) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//移历史
			BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMS_bill_get_his_NO");
			BillGetHis billGetHis = new BillGetHis();
			billGetHis.setId(Long.parseLong(seq.toString()));
			billGetHis.setGenReason("1");//修改
			billGetHisDao.saveHis(billGetHis,billGet);
			
			billGet.setHisSeqId(billGetHis.getId());
			billGetDao.update(billGet);

			//保存业务记录，客服流水, 115	信息服务修改
			saveBusinessService(customer,CustomerBussinessTypeEnum.billUpdate.getValue(),"115","网上营业厅：信息服务修改");


			map.put("result", "true");
			map.put("message", "修改服务信息成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：修改服务信息失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：修改服务信息失败");
		}
		
		return map;
	}

	//---------------发票信息管理--------------------
	
	@Override
	public List findQueryInvoiceInfo(Customer customer) {
		return onlineDataDao.findQueryInvoiceInfo(customer.getId());
	}

	/**
	 * 增加发票信息
	 */
	@Override
	public Map<String, Object> saveAddInvoiceInfo(Invoice invoice,Customer customer) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			invoiceService.save(invoice);

			//保存业务记录，客服流水, 112	发票信息新增
			saveBusinessService(customer,CustomerBussinessTypeEnum.invoiceAdd.getValue(),"112","网上营业厅：发票信息新增");
			
			map.put("result", "true");
			map.put("message", "增加发票信息成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：增加发票信息失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：增加发票信息失败");
		}
		
		return map;
	}

	/***
	 * 客户发票信息修改
	 * @param invoice
	 * @param customer
     * @return
     */
	@Override
	public Map<String, Object> updateInvoiceInfo(Invoice invoice,Customer customer) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			invoiceService.update(invoice);

			//保存业务记录，客服流水, 113	发票信息修改
			saveBusinessService(customer,CustomerBussinessTypeEnum.invoiceUpdate.getValue(),"113","网上营业厅：发票信息修改");


			map.put("result", "true");
			map.put("message", "修改发票信息成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：修改发票信息失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：修改发票信息失败");
		}
		
		return map;
	}

	
	//-----------------密码管理-----------------------
	
	@Override
	public Map<String, Object> updateUserPassword(Customer customer, String type, SysAdmin sysAdmin,
			CusPointPoJo cusPointPoJo) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			boolean updateResult = customerService.updatePwd(customer, type, sysAdmin, cusPointPoJo,new HashMap<String,Object>());
			if(updateResult){
				map.put("result", "true");
				map.put("message", "修改服务密码成功");
			}else{
				map.put("result", "false");
				map.put("message", "修改服务密码失败");
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：修改服务密码失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：修改服务密码失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> updateUserPassword4OnLine(Customer customer, String type, SysAdmin sysAdmin,
												  CusPointPoJo cusPointPoJo,String remark) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			boolean updateResult = customerService.updatePwd4OnLine(customer, type, sysAdmin, cusPointPoJo,remark);
			if(updateResult){
				map.put("result", "true");
				map.put("message", "修改服务密码成功");
			}else{
				map.put("result", "false");
				map.put("message", "修改服务密码失败");
			}

		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：修改服务密码失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：修改服务密码失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> updateCardPassword(AccountCInfo accountCInfo, PrepaidC prepaidC,SysAdmin sysAdmin, CusPointPoJo cusPointPoJo) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//boolean updateResult = false;
			if(accountCInfo!=null){
				//updateResult = accountCService.updatePwd(accountCInfo);
				//记帐卡业务记录
				AccountCBussiness accountCBussiness = new AccountCBussiness();
				accountCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMSAccountCbussiness_NO"));
				
				accountCBussiness.setUserId(accountCInfo.getCustomerId());
				accountCBussiness.setCardNo(accountCInfo.getCardNo());
				accountCBussiness.setState("13"); //13消费密码修改 14重设
				accountCBussiness.setOperId(sysAdmin.getId());
				accountCBussiness.setPlaceId(cusPointPoJo.getCusPoint());
				accountCBussiness.setOperName(sysAdmin.getUserName());
				accountCBussiness.setOperNo(sysAdmin.getStaffNo());
				accountCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
				accountCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
				accountCBussiness.setTradeTime(new Date());		
				accountCBussiness.setRealPrice(new BigDecimal("0"));
				//回执打印数据
				AccountCApply accountCApply = accountCApplyDao.findByCardNo(accountCInfo.getCardNo());
				accountCBussiness.setAccountCApplyHisID(accountCApply.getHisseqId());
				
				accountCBussinessDao.save(accountCBussiness);
				
				//MD5 = 卡号+交易密码
				accountCInfo.setTradingPwd(StringUtil.md5(accountCInfo.getTradingPwd()));
				accountCInfoDao.update(accountCInfo);
			}else if(prepaidC!=null){
			    PrepaidCBussiness prepaidCBussiness = new PrepaidCBussiness();
				prepaidCBussiness.setId(sequenceUtil.getSequenceLong("SEQ_CSMS_PrePaidC_bussiness_NO"));

				prepaidCBussiness.setUserid(prepaidC.getCustomerID());
				prepaidCBussiness.setCardno(prepaidC.getCardNo());
				prepaidCBussiness.setState(PrepaidCardBussinessTypeEnum.preCardPasswordModify.getValue()); // 5表示消费密码修改 6表示重设
				prepaidCBussiness.setPlaceid(cusPointPoJo.getCusPoint());
				prepaidCBussiness.setOperid(sysAdmin.getId());
				prepaidCBussiness.setOperName(sysAdmin.getUserName());
				prepaidCBussiness.setOperNo(sysAdmin.getStaffNo());
				prepaidCBussiness.setPlaceName(cusPointPoJo.getCusPointName());
				prepaidCBussiness.setPlaceNo(cusPointPoJo.getCusPointCode());
				prepaidCBussiness.setTradetime(new Date());
				prepaidCBussinessDao.save(prepaidCBussiness);
				// MD5 = 卡号+交易密码
				prepaidC.setTradingPwd(StringUtil.md5(prepaidC.getTradingPwd()));
				prepaidCDao.update(prepaidC);
			}
			
			map.put("result", "true");
			map.put("message", "修改交易密码成功");
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：修改交易密码失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：修改交易密码失败");
		}
		
		return map;
	}
	//---------------------储值卡管理-------------------------------
	
	/**
	 * 储值卡充值查询
	 */
	@Override
	public List findQueryPrepaidCardRecharge(String cardNo, String startTime, String endTime) {
		return onlineDataDao.findQueryPrepaidCardRecharge(cardNo, startTime, endTime);
	}
	/**
	 * 储值卡发票类型变更
	 */
	@Override
	public Map<String, Object> updateInvoicePrintType(PrepaidC prepaidC, PrepaidCBussiness prepaidCBussiness) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			prepaidCService.changeInvoicePrint(prepaidC, prepaidCBussiness,new HashMap<String,Object>());
			
			map.put("result", "true");
			map.put("message", "储值卡发票类型变更成功");
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：储值卡发票类型变更失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：储值卡发票类型变更失败");
		}
		
		return map;
	}

	//------------------------记帐卡管理----------------------------------
	
	/**
	 * 网上申请解除止付
	 */
	@Override
	public Map<String, Object> saveRemoveStopList(String bankNo, Customer customer, AccountCBussiness accountCBussiness, AccountCApply accountCApply,Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> resultMap = relieveStopPayService.saveApplyRelieveStopPayByBankNoCustomer(bankNo, customer,accountCBussiness,accountCApply,params);
			if(resultMap.get("result").equals("true")){
				map.put("result", "true");
				map.put("message", "网上申请解除止付成功");
			}else if(resultMap.get("result").equals("false")){
				map.put("result", "false");
				map.put("message", resultMap.get("msg"));
			}else {
				map.put("result", "false");
				map.put("message", "网上营业厅系统：申请解除止付失败");
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：网上申请解除止付失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：网上申请解除止付失败");
		}
		
		return map;
	}
	/**
	 * 保证金情况查询
	 */
	@Override
	public List findQueryBailInfo(String userNo, String cardNo, String bankAccount) {
		return onlineDataDao.findQueryBailInfo(userNo, cardNo, bankAccount);
	}
	/**
	 * 账单通知
	 */
	@Override
	public List findBillNotification() {
		return onlineDataDao.findAcInvoiceNotice();
	}

	
	//---------------其他服务---------------------
	
	/**
	 * 挂失（记帐卡或储值卡）
	 */
	@Override
	public Map<String, Object> saveCardLost(AccountCInfo accountCInfo, PrepaidC prepaidC,String systemType) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			
			if(accountCInfo!=null&&prepaidC==null){
				accountCService.saveLock(accountCInfo, systemType,new HashMap<String,Object>());
				
				map.put("result", "true");
				map.put("message", "记帐卡"+accountCInfo.getCardNo()+"挂失成功");
			}else if(accountCInfo==null&&prepaidC!=null){
				prepaidCService.saveLock(prepaidC, systemType);
				
				map.put("result", "true");
				map.put("message", "储值卡卡"+prepaidC.getCardNo()+"挂失成功");
			}else{
				map.put("result", "false");
				map.put("message", "操作失败");
			}
			
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"线上接口：挂失失败");
			e.printStackTrace();
			throw new ApplicationException("线上接口：挂失失败");
		}
		
		return map;
	}

	//粤通卡卡号变更情况
	@Override
	public List<Map<String, Object>> queryCardChageInfo(String cardNo){
		
		try {
			DbasCardFlow dbasCardFlow = dbasCardFlowDao.find(cardNo);
			List<Map<String, Object>> list = null;
			if(dbasCardFlow!=null){
				list = dbasCardFlowDao.findCardChangeFlow(dbasCardFlow.getCardNo());
			}else{
				list = new ArrayList<Map<String, Object>>();
			}
			return list;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"查询粤通卡变更情况失败，卡号为："+cardNo);
			e.printStackTrace();
			throw new ApplicationException("查询粤通卡变更情况失败，卡号为："+cardNo);
		}
		
	}

	/**
	 * 储值卡发票类型变更查询
	 */
	@Override
	public List findInvoiceChangeFlow(String cardNo) {
		return onlineDataDao.findInvoiceChangeFlow(cardNo);
	}


	/***
	 * 储值卡通行明细查询
	 * @param cardNo
	 * @param settleMonth
     * @return
     */
	@Override
	public List findPrepaidCTradeDetail(String cardNo,String settleMonth){ return onlineDataDao.findPrepaidCTradeDetail(cardNo,settleMonth);
	}

	/***
	 * 记帐卡通行明细查询
	 * @param cardNo
	 * @param settleMonth
	 * @return
	 */
	@Override
	public List findAccountCTradeDetail(String cardNo,String settleMonth){ return onlineDataDao.findAccountCTradeDetail(cardNo,settleMonth);
	}

	/***
	 * 记帐卡实时消费情况查询
	 * @param cardNo
	 * @param settleMonth
	 * @return
	 */
	@Override
	public List findAccountCCurrentTradeDetail(String cardNo,String settleMonth){ return onlineDataDao.findAccountCCurrentTradeDetail(cardNo,settleMonth);
	}

	@Override
	public List<Map<String, Object>> findAccountCListByCustomerId(Long customerId){
		return accountCInfoDao.getAccountCInfoByCustomerId(customerId);
	}

	@Override
	public Map<String, Object> saveServicePwdResetInfo(ServicePwdResetInfo servicePwdResetInfo){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			servicePwdResetInfo.setId(sequenceUtil.getSequenceLong("SEQ_SERVICEPWDRESETINFO"));
			servicePwdResetInfo.setRemark( "网厅接口：服务密码重设申请");
			servicePwdResetInfoDao.save(servicePwdResetInfo);

			map.put("result", "true");
			map.put("message", "服务密码重设申请成功");


		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"网厅接口：服务密码重设申请失败");
			e.printStackTrace();
			throw new ApplicationException("网厅接口：服务密码重设申请失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> confirmServicePwdResetConfirm(Customer customer, String type, SysAdmin sysAdmin,
															 CusPointPoJo cusPointPoJo,String remark,String userNo, String telNum, String checkCode, String ServicePwd) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//1) 查询服务密码重设申请记录
			ServicePwdResetInfo  servicePwdResetInfo  = servicePwdResetInfoDao.findByUserNoTelNumCheckCode(userNo,telNum, checkCode);
			if(servicePwdResetInfo != null){

				//2)查询条件不为空，修改服务密码申请确认标识（confirmFlag为1）
				servicePwdResetInfoDao.updateConfirmFlag(servicePwdResetInfo.getId());
				//3)修改服务密码,生成客服流水，业务记录
				customer.setServicePwd(ServicePwd);
				boolean updateResult = customerService.updatePwd4OnLine(customer, type, sysAdmin, cusPointPoJo,remark);
				if(updateResult){
					map.put("result", "true");
					map.put("message", "服务密码重设确认成功");
				}else{
					map.put("result", "false");
					map.put("message", "服务密码重设确认失败");
				}
			}else{
				map.put("result", "false");
				map.put("message", "服务密码重设短信校验失败");
			}



		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"网厅接口：服务密码重设确认失败");
			e.printStackTrace();
			throw new ApplicationException("网厅接口：服务密码重设确认失败");
		}

		return map;
	}

	/***
	 * 记账卡转账情况查询
	 * @param bankAccount
	 * @param startTime
	 * @param endTime
	 * @param month
     * @return
     */
	@Override
	public Map<String, Object> findAccountCardTransfer(String bankAccount, String startTime, String endTime, String month,String accType,String type) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			Date startDate = null;
			Date endDate = null;
			//1：转账日期 2：结算月
			if("2".equals(type)){
				//当查询类型为2（结算月）时，需要查询
				MonthlyReg monthlyReg = monthlyRegService.findBySettleMonth(Integer.parseInt(month));
				if(monthlyReg ==null){
					map.put("result", "false");
					map.put("message", "查无该结算月的对账周期登记记录");
					return map;
				}else{
					startDate = monthlyReg.getStartDispartTime();
					endDate = monthlyReg.getEndDispartTime();
				}
			}else if("1".equals(type)){
				//格式化日期
				startDate = DateUtil.string2Date(startTime,YMDHMS_STANDARD);
				endDate = DateUtil.string2Date(endTime,YMDHMS_STANDARD);
			}
			List<Map<String, Object>> list = new ArrayList<>();
			if("3".equals(accType)){
				list = onlineDataDao.findAccountCardTransferKH(bankAccount,startDate,endDate);
			}else {
				list = onlineDataDao.findAccountCardTransfer(bankAccount,startDate,endDate);
			}
//

			map.put("result", "true");
			map.put("message", "记账卡转账情况查询成功");
			map.put("list", list);


		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"网厅接口：记账卡转账情况查询失败");
			e.printStackTrace();
			throw new ApplicationException("网厅接口：记账卡转账情况查询失败");
		}

		return map;
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IOnlineDataInterfaceService iOnlineDataInterfaceService = (IOnlineDataInterfaceService)context.getBean("onlineDataInterfaceService");
		System.out.println("1");
	}

	/***
	 * 保存业务记录、客服流水
	 * @param customer
	 * @param businessType
	 * @param serviceType
     * @param remark
     */
	public void saveBusinessService(Customer customer,String businessType,String serviceType,String remark){
		//写业务记录
		CustomerBussiness customerBussiness = new CustomerBussiness();
		Long seqNo = sequenceUtil.getSequenceLong("SEQ_CSMSCustomerBussiness_NO");

		customerBussiness.setId(seqNo);
		customerBussiness.setCustomerId(customer.getId());

		customerBussiness.setType(businessType);
		customerBussiness.setOperId(customer.getOperId());
		customerBussiness.setOperNo(customer.getOperNo());
		customerBussiness.setOperName(customer.getOperName());
		customerBussiness.setPlaceId(customer.getPlaceId());
		customerBussiness.setPlaceNo(customer.getPlaceNo());
		customerBussiness.setPlaceName(customer.getPlaceName());
		customerBussiness.setCreateTime(new Date());
		customerBussinessDao.save(customerBussiness);

		//调整的客服流水
		ServiceWater serviceWater = new ServiceWater();
		Long serviceWater_id = sequenceUtil.getSequenceLong("seq_csmsservicewater_no");

		serviceWater.setId(serviceWater_id);

		serviceWater.setCustomerId(customer.getId());
		serviceWater.setUserNo(customer.getUserNo());
		serviceWater.setUserName(customer.getOrgan());
		serviceWater.setSerType(serviceType);
		serviceWater.setCustomerBussinessId(customerBussiness.getId());
		serviceWater.setOperId(customer.getOperId());
		serviceWater.setOperName(customer.getOperName());
		serviceWater.setOperNo(customer.getOperNo());
		serviceWater.setPlaceId(customer.getPlaceId());
		serviceWater.setPlaceName(customer.getPlaceName());
		serviceWater.setPlaceNo(customer.getPlaceNo());
		serviceWater.setRemark(remark);
		serviceWater.setOperTime(new Date());

		serviceWaterDao.save(serviceWater);
	}


}
