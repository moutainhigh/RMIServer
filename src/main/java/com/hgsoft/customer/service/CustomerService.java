package com.hgsoft.customer.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.dao.BlackListDao;
import com.hgsoft.clearInterface.entity.BlackListStatus;
import com.hgsoft.clearInterface.entity.BlackListTemp;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.CustomerImpDao;
import com.hgsoft.customer.dao.ExceptionCustomerDao;
import com.hgsoft.customer.dao.MaterialDao;
import com.hgsoft.customer.dao.SpecialListDao;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.customer.entity.CustomerImp;
import com.hgsoft.customer.entity.ExceptionCustomer;
import com.hgsoft.customer.entity.Invoice;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.SpecialList;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.serviceInterface.ICustomerService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.CustomerModifyApplyDao;
import com.hgsoft.httpInterface.entity.CustomerModifyApply;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.unifiedInterface.service.CustomerUnifiedInterfaceService;
import com.hgsoft.utils.DesEncrypt;
import com.hgsoft.utils.HttpUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.PropertiesUtil;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Service
public class CustomerService implements ICustomerService{
	
	
	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private CustomerUnifiedInterfaceService customerUnifiedInterfaceService;
	
	@Resource
	private MaterialDao materialDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	@Resource
	private CustomerModifyApplyDao customerModifyApplyDao;
	@Resource
	private CustomerImpDao customerImpDao;
	
	private CustomerDao customerDao;
	
	@Resource
	private BlackListDao blackListDao;
	
	@Resource
	private ExceptionCustomerDao exceptionCustomerDao;
	
	@Resource
	private SpecialListDao specialListDao;
	
	@Resource
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	private static Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	@Override
	public Customer find(Customer customer) {
		return customerDao.find(customer);
	}

	@Override
	public List<Map<String, Object>> findAll(Customer customer) {
		return customerDao.findAll(customer);
	}

	@Override
	public String saveCustomer(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo, Invoice invoice, ServiceFlowRecord serviceFlowRecord,String clientSystem) {
		return customerUnifiedInterfaceService.saveCutomer(customer,material,rootPath,imageFile,imageFileName,tempPicNameList,mainAccountInfo, subAccountInfo, invoice, serviceFlowRecord,clientSystem);
	}
	
	@Override
	public String saveCustomerForAMMS(Customer customer,Material material,String rootPath,List<File> imageFile,List<String> imageFileName,String[] tempPicNameList, MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo, List<BillGet> billGets, Invoice invoice, ServiceFlowRecord serviceFlowRecord,String clientSystem,CustomerImp customerImp) {
		return customerUnifiedInterfaceService.saveCutomerForAMMS(customer,material,rootPath,imageFile,imageFileName,tempPicNameList,mainAccountInfo, subAccountInfo, billGets, invoice, serviceFlowRecord,clientSystem,customerImp);
	}
	
	@Override
	public Customer saveCustomer(Customer customer,MainAccountInfo mainAccountInfo, SubAccountInfo subAccountInfo,ServiceFlowRecord serviceFlowRecord) {
		return customerUnifiedInterfaceService.saveCutomer(customer, mainAccountInfo, subAccountInfo, serviceFlowRecord);
	}
	
	@Override
	public void deleteCustomer(Long id) {
		customerDao.delete(id);
	}

	@Override
	public void updateCustomer(Customer customer, CustomerHis customerHis) {
		customerUnifiedInterfaceService.updateCutomer(customer, customerHis);
	}

	@Override
	public void updateCustomer4OnLine(Customer customer, CustomerHis customerHis,String remark) {
		customerUnifiedInterfaceService.updateCustomer4OnLine(customer, customerHis,remark);
	}
	
	@Override
	public void updateCustomer(Customer customer, CustomerHis customerHis,String newOrgan,Map<String,Object> params) {
		customerUnifiedInterfaceService.updateCutomer(customer, customerHis,newOrgan,params);
	}
	
	@Override
	public boolean updatePwd(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params) {
		//MD5 = 客户号+服务密码
		customer.setServicePwd((StringUtil.md5(customer.getServicePwd())));
		return customerUnifiedInterfaceService.updatePwd(customer,type,sysAdmin,cusPointPoJo,params);
	}

	@Override
	public boolean updatePwd4OnLine(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,String remark) {
		//MD5 = 客户号+服务密码
		customer.setServicePwd((StringUtil.md5(customer.getServicePwd())));
		return customerUnifiedInterfaceService.updatePwd4OnLine(customer,type,sysAdmin,cusPointPoJo,remark);
	}
	
	@Override
	public boolean updatePwdForAMMS(Customer customer,String type, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo,Map<String,Object> params) {
		//MD5 = 客户号+服务密码
		customer.setServicePwd((StringUtil.md5(customer.getServicePwd())));
		return customerUnifiedInterfaceService.updatePwdForAMMS(customer,type,sysAdmin,cusPointPoJo,params);
	}

	@Override
	public Customer findById(Long id) {
		return customerDao.findById(id);
	}

	@Override
	public Pager findByPage(Pager pager,Customer customer) {
		return customerDao.findByPage(pager,customer);
	}

	
	@Override
	public Pager findByPage(Pager pager, Customer customer,
			String startDate, String endDate) {
		return customerDao.findByPage(pager,customer,startDate,endDate);
	}

	@Override
	public Customer findByIdNo(String idType, String idCode) {
		return customerDao.findByIdNo(idType, idCode);
	}

	@Override
	public boolean checkCustomerToCancel(Long id) {
		
		return customerDao.checkCustomerToCancel(id);
	}

	@Override
	public Map<String, Object> authenticationCheck(String idType,
			String idCode, String servicePwd, String cardNo, String type) {
		return customerDao.authenticationCheck(idType, idCode, servicePwd, cardNo, type);
	}

	@Override
	public Customer getCustomerByPrepaidCardNo(String cardNo) {
		return customerDao.getCustomerByPrepaidCardNo(cardNo);
	}

	@Override
	public Customer findByUserNo(String userNo) {
		return customerDao.findByUserNo(userNo);
	}

	@Override
	public List<Map<String, Object>> getCustomerByBank(String bankAccount) {
		try {
			return customerDao.getCustomerByBank(bankAccount);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"记帐卡卡号列表查询失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Customer getCustomerByAccountCardNo(String cardNo) {
		return customerDao.getCustomerByAccountCardNo(cardNo);
	}

	@Override
	public Pager getAccountCList(Pager pager,Long customerId,AccountCInfo accountCInfo, VehicleInfo vehicleInfo, TagInfo tagInfo) {
		try {
			return customerDao.getAccountCList(pager,customerId, accountCInfo,  vehicleInfo,  tagInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询记帐卡失败");
			throw new ApplicationException();
		}
	}
	
	@Override
	public Pager getAccountCListForAMMS(Pager pager,Long customerId,AccountCInfo accountCInfo, VehicleInfo vehicleInfo, TagInfo tagInfo,String bankCode) {
		try {
			return customerDao.getAccountCListForAMMS(pager,customerId, accountCInfo,  vehicleInfo,  tagInfo,bankCode);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询记帐卡失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getPrepaidCList(Pager pager,Long customerId,PrepaidC prePaidc, VehicleInfo vehicleInfo, TagInfo tagInfo) {
		try {
			return customerDao.getPrepaidCList(pager,customerId, prePaidc,  vehicleInfo,  tagInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询储值卡失败");
			throw new ApplicationException();
		}
	}
	
	@Override
	public Pager getPrepaidCListForAMMS(Pager pager,Long customerId,PrepaidC prePaidc, VehicleInfo vehicleInfo, TagInfo tagInfo,String bankCode) {
		try {
			return customerDao.getPrepaidCListForAMMS(pager,customerId, prePaidc,  vehicleInfo,  tagInfo,bankCode);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询储值卡失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getTagInfoList(Pager pager,Long customerId,TagInfo tagInfo) {
		try {
			return customerDao.getTagInfoList(pager,customerId,tagInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询电子标签失败");
			throw new ApplicationException();
		}
	}
	
	@Override
	public Pager getTagInfoListForAMMS(Pager pager,Long customerId,TagInfo tagInfo,String bankCode) {
		try {
			return customerDao.getTagInfoListForAMMS(pager,customerId,tagInfo,bankCode);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询电子标签失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getVehicleList(Pager pager,Long customerId,VehicleInfo vehicleInfo) {
		try {
			return customerDao.getVehicleList(pager,customerId,vehicleInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询车牌信息失败");
			throw new ApplicationException();
		}
	}
	
	@Override
	public Pager getVehicleListForAMMS(Pager pager,Long customerId,VehicleInfo vehicleInfo,String bankCode) {
		try {
			return customerDao.getVehicleListForAMMS(pager,customerId,vehicleInfo,bankCode);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询车牌信息失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getUnBindTagInfoList(Pager pager,Long customerId,TagInfo tagInfo) {
		try {
			return customerDao.getUnBindTagInfoList(pager,customerId,tagInfo);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询未绑定车牌的电子标签失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getUnUseVehicleList(Pager pager,Long customerId) {
		try {
			return customerDao.getUnUseVehicleList(pager,customerId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询未使用车牌号失败");
			throw new ApplicationException();
		}
	}
	//hzw 
	@Override
	public Pager getAccountCSubList(Pager accountCSubPager, Long customerId) {
		try {
			return customerDao.getAccountCSubList(accountCSubPager,customerId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询记帐卡子账户列表失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getAccountCInfoList(Pager accountCInfoPager, Long accountCId) {
		try {
			return customerDao.getAccountCInfoList(accountCInfoPager,accountCId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户综合信息查询，查询记帐卡详情列表失败");
			throw new ApplicationException();
		}
	}

	/**根据当前客户ID、类型、code查找图片资料表记录，
	 * 若有则进行更新，修改次数+1，更新时间=当前时间，图片地址为新地址，并把原图片删除；
	 * 若无则新增，修改次数=1，更新时间=当前时间
	 * 
	 */
	@Override
	public boolean saveOrUpdateMaterial(Material material,String rootPath,List<File> imageFile,List<String> imageFileName,Customer customer) {
		boolean flag = true;
		List<Material> newMaterials = new ArrayList<Material>();
		try {
			//文件 
			//System.out.println("分隔符："+File.separator);
			String dirPath = rootPath+"material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator;
			File dir = new File(dirPath);
			//建立文件夹
			if(!dir.exists()){
				dir.mkdirs();
			}
			if(imageFile != null && imageFileName != null){
				//material.setPicAddr(savePath);//图片地址
				//根据当前客户ID、类型、code查找图片资料表记录
				List<Material> materials = materialDao.findMateria(material);
		
				if(materials != null){
					Integer updateTime = 1;
					for(Material m:materials){
						//若有则全部删掉
						//将原图片删除
						File img = new File(rootPath+m.getPicAddr());
						img.delete();
						
						//将图片资料表的数据删掉
						updateTime = m.getUpdateTime();//先存储要删掉的数据的修改次数
						materialDao.deleteMateria(m.getId());
						
						materialDao.updateMateria(m);
					}
					//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
					for(int i=0;i<imageFile.size();i++){
						//保存图片文件
						String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
						FileUtils.copyFile(imageFile.get(i), new File(rootPath+savePath));
						//若无则新增，修改次数=1，更新时间=当前时间
						Material newMaterial = new Material();
						BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
						newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
						newMaterial.setCustomerID(customer.getId());
						newMaterial.setType(customer.getIdType());
						newMaterial.setCode(customer.getIdCode());
						newMaterial.setPicAddr(savePath);
						newMaterial.setUp_Date(new Date());
						newMaterial.setUpdateTime(updateTime+1);
						
						materialDao.saveMateria(newMaterial);
						newMaterials.add(newMaterial);
					}
					
				}else{
					for(int i=0;i<imageFile.size();i++){
						//保存图片文件
						String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
						FileUtils.copyFile(imageFile.get(i), new File(rootPath+savePath));
						//若无则新增，修改次数=1，更新时间=当前时间
						Material newMaterial = new Material();
						BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
						newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
						newMaterial.setCustomerID(customer.getId());
						newMaterial.setType(customer.getIdType());
						newMaterial.setCode(customer.getIdCode());
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
			logger.error(e.getMessage()+"图片资料数据保存失败");
			flag = false;
			//保存数据失败，删除刚刚上传的图片
			for(Material ma:newMaterials){
				File img = new File(rootPath+ma.getPicAddr());
				img.delete();
			}
			
			
			throw new ApplicationException();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 拍照或者本地浏览图片后先上传了图片到服务器，保存资料时再转移到目的路径
	 */
	@Override
	public boolean saveOrUpdateMaterial(Material material,String rootPath,String[] tempPicNameList,Customer customer,String[] deleteOldMaterialIDList,String clientSystem){
		boolean flag = true;
		List<Material> newMaterials = new ArrayList<Material>();
		List<Material> oldMaterials = new ArrayList<Material>();
		StringBuffer tempPicPaths = new StringBuffer();
		StringBuffer savePaths = new StringBuffer();;
		StringBuffer oldPicPaths = new StringBuffer();
		
		//文件 
		String dirPath = rootPath+"material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator;
		try {
			
			//修改：不一定是要上传了临时照片才能执行以下程序，有可能是删除了旧图片
				//material.setPicAddr(savePath);//图片地址
				//根据当前客户ID、类型、code查找图片资料表记录
				List<Material> materials = materialDao.findMateria(material);
				if(materials != null&&materials.size()>0){
					Integer updateTime = 1;
					//oldPicPaths = new String[materials.size()];
					//注：区分两种情况：1.客户新增时上传。2.新拍照片editPicture
					if(deleteOldMaterialIDList!=null){
						//表示有药删除的照片,执行以下程序
						for(int i=0;i<deleteOldMaterialIDList.length;i++){
							Material oldMaterial = null;
							if(StringUtil.isNotBlank(deleteOldMaterialIDList[i]))oldMaterial = materialDao.findById(Long.parseLong(deleteOldMaterialIDList[i]));
							if(oldMaterial!=null){
								oldPicPaths.append(","+oldMaterial.getPicAddr());
								//将图片资料表的数据删掉
								updateTime = materials.get(i).getUpdateTime();//先存储要删掉的数据的修改次数
								materialDao.deleteMateria(oldMaterial.getId());
								oldMaterials.add(oldMaterial);
							}
						}
					}
					
					//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
					if(tempPicNameList!=null){
						for(int i=0;i<tempPicNameList.length;i++){
							if(StringUtil.isNotBlank(tempPicNameList[i])){
								//保存图片文件
								String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
								//savePaths[i] = rootPath+savePath;
								//tempPicPaths[i] = rootPath+"picture"+File.separator+tempPicNameList[i];
								savePaths.append(","+savePath);
								//tempPicPaths.append(","+"picture"+File.separator+tempPicNameList[i]);
								tempPicPaths.append(","+File.separator+tempPicNameList[i]);
								//移动图片到要保存的位置后，删除临时图片
								//tempPic.delete();
								//
								Material newMaterial = new Material();
								BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
								newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
								newMaterial.setCustomerID(customer.getId());
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
					
				}else{
					if(tempPicNameList!=null){
						for(int i=0;i<tempPicNameList.length;i++){
							if(StringUtil.isNotBlank(tempPicNameList[i])){
								//保存图片文件
								String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
								//savePaths[i] = rootPath+savePath;
								//tempPicPaths[i] = rootPath+"picture"+File.separator+tempPicNameList[i];
								savePaths.append(","+savePath);
								//tempPicPaths.append(","+"picture"+File.separator+tempPicNameList[i]);
								tempPicPaths.append(","+tempPicNameList[i]);
								//移动图片到要保存的位置后，删除临时图片
								//若无则新增，修改次数=1，更新时间=当前时间
								Material newMaterial = new Material();
								BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
								newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
								newMaterial.setCustomerID(customer.getId());
								//图片的类型
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
					
				}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"图片资料数据保存失败");
			flag = false;
			throw new ApplicationException();
		}
		try{
			String oldPicPathsStr = "";
			String tempPicPathsStr = "";
			String savePathsStr = "";
			if(oldPicPaths.length()>1){
				oldPicPathsStr = oldPicPaths.substring(1);
			}
			if(tempPicPaths.length()>1){
				tempPicPathsStr = tempPicPaths.substring(1);
			}
			if(savePaths.length()>1){
				savePathsStr = savePaths.substring(1);
			}
			//通过http访问client项目，作图片资源的处理
			//csmsClientUrl ammsClientUrl acmsClientUrl
			/*if("1".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","csmsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}else if("2".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","ammsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}*/
			String url = PropertiesUtil.getValue("/url.properties",clientSystem)+"commonInterface/commonInterface_dealChangeMaterials.do";
			
			String data = "dirPath="+dirPath+"&oldPicPaths="+oldPicPathsStr+"&tempPicPaths="+tempPicPathsStr+"&savePaths="+savePathsStr;
			Map<String, Object> resultMap = HttpUtil.callClientByHTTP(url, data, "POST");
			//System.out.println(resultMap.get("result"));
			if(resultMap!=null&&resultMap.get("result")!=null&&"true".equals(resultMap.get("result").toString())){
				flag = true;
			}else{
				flag = false;
				
				//如果有异常表明只删除了数据库的图片资料，但是没有删除图片资源，所以要重新保存删除了的图片资料
				//同理，新增图片也会有这情况
				for(Material newM:newMaterials){
					materialDao.deleteMateria(newM.getId());
				}
				for(Material oldM:oldMaterials){
					materialDao.saveMateria(oldM);
				}
				
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage()+"图片资源处理失败："+savePaths);
			
			//如果有异常表明只删除了数据库的图片资料，但是没有删除图片资源，所以要重新保存删除了的图片资料
			//同理，新增图片也会有这情况
			for(Material newM:newMaterials){
				materialDao.deleteMateria(newM.getId());
			}
			for(Material oldM:oldMaterials){
				materialDao.saveMateria(oldM);
			}
			
			return false;
		}
		
		return flag;
	}
	@Override
	public Map<String,Object> saveOrUpdateMaterial1(Material material,String rootPath,String[] tempPicNameList,Customer customer,String[] deleteOldMaterialIDList,String clientSystem){
		boolean flag = true;
		List<Material> newMaterials = new ArrayList<Material>();
		List<Material> oldMaterials = new ArrayList<Material>();
		StringBuffer tempPicPaths = new StringBuffer();
		StringBuffer savePaths = new StringBuffer();;
		StringBuffer oldPicPaths = new StringBuffer();
		Map<String, Object> m = new HashMap<String,Object>();
		StringBuffer id = new StringBuffer();
		
		//文件 
		String dirPath = rootPath+"material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator;
		try {
			
			//修改：不一定是要上传了临时照片才能执行以下程序，有可能是删除了旧图片
			//material.setPicAddr(savePath);//图片地址
			//根据当前客户ID、类型、code查找图片资料表记录
			List<Material> materials = materialDao.findMateria(material);
			if(materials != null&&materials.size()>0){
				Integer updateTime = 1;
				//oldPicPaths = new String[materials.size()];
				//注：区分两种情况：1.客户新增时上传。2.新拍照片editPicture
				if(deleteOldMaterialIDList!=null){
					//表示有药删除的照片,执行以下程序
					for(int i=0;i<deleteOldMaterialIDList.length;i++){
						Material oldMaterial = null;
						if(StringUtil.isNotBlank(deleteOldMaterialIDList[i]))oldMaterial = materialDao.findById(Long.parseLong(deleteOldMaterialIDList[i]));
						if(oldMaterial!=null){
							oldPicPaths.append(","+oldMaterial.getPicAddr());
							//将图片资料表的数据删掉
							updateTime = materials.get(i).getUpdateTime();//先存储要删掉的数据的修改次数
							materialDao.deleteMateria(oldMaterial.getId());
							oldMaterials.add(oldMaterial);
						}
					}
				}
				
				//将原有的图片资料表的数据删掉后，添加新的数据，修改次数+1，更新时间=当前时间，图片地址为新地址，
				if(tempPicNameList!=null){
					for(int i=0;i<tempPicNameList.length;i++){
						if(StringUtil.isNotBlank(tempPicNameList[i])){
							//保存图片文件
							String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
							//savePaths[i] = rootPath+savePath;
							//tempPicPaths[i] = rootPath+"picture"+File.separator+tempPicNameList[i];
							savePaths.append(","+savePath);
							//tempPicPaths.append(","+"picture"+File.separator+tempPicNameList[i]);
							tempPicPaths.append(","+tempPicNameList[i]);
							//移动图片到要保存的位置后，删除临时图片
							//tempPic.delete();
							//
							Material newMaterial = new Material();
							BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
							newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
							newMaterial.setCustomerID(customer.getId());
							newMaterial.setType(material.getType());
							newMaterial.setCode(material.getCode());
							newMaterial.setPicAddr(savePath);
							newMaterial.setUp_Date(new Date());
							newMaterial.setUpdateTime(1);
							materialDao.saveMateria(newMaterial);
							id.append(SEQ_CSMS_Material_NO+";");
							newMaterials.add(newMaterial);
						}
					}
				}
				
			}else{
				if(tempPicNameList!=null){
					for(int i=0;i<tempPicNameList.length;i++){
						if(StringUtil.isNotBlank(tempPicNameList[i])){
							//保存图片文件
							String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
							//savePaths[i] = rootPath+savePath;
							//tempPicPaths[i] = rootPath+"picture"+File.separator+tempPicNameList[i];
							savePaths.append(","+savePath);
							//tempPicPaths.append(","+"picture"+File.separator+tempPicNameList[i]);
							tempPicPaths.append(","+tempPicNameList[i]);
							//移动图片到要保存的位置后，删除临时图片
							//若无则新增，修改次数=1，更新时间=当前时间
							Material newMaterial = new Material();
							BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
							newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
							newMaterial.setCustomerID(customer.getId());
							//图片的类型
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
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"图片资料数据保存失败");
			flag = false;
			throw new ApplicationException();
		}
		try{
			String oldPicPathsStr = "";
			String tempPicPathsStr = "";
			String savePathsStr = "";
			if(oldPicPaths.length()>1){
				oldPicPathsStr = oldPicPaths.substring(1);
			}
			if(tempPicPaths.length()>1){
				tempPicPathsStr = tempPicPaths.substring(1);
			}
			if(savePaths.length()>1){
				savePathsStr = savePaths.substring(1);
			}
			//通过http访问client项目，作图片资源的处理
			//csmsClientUrl ammsClientUrl acmsClientUrl
			/*if("1".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","csmsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}else if("2".equals(clientSystem)){
				url = PropertiesUtil.getValue("/url.properties","ammsClientUrl")+"commonInterface/commonInterface_dealChangeMaterials.do";
			}*/
			String url = PropertiesUtil.getValue("/url.properties",clientSystem)+"commonInterface/commonInterface_dealChangeMaterials.do";
			
			String data = "dirPath="+dirPath+"&oldPicPaths="+oldPicPathsStr+"&tempPicPaths="+tempPicPathsStr+"&savePaths="+savePathsStr;
			Map<String, Object> resultMap = HttpUtil.callClientByHTTP(url, data, "POST");
			//System.out.println(resultMap.get("result"));
			if(resultMap!=null&&resultMap.get("result")!=null&&"true".equals(resultMap.get("result").toString())){
				flag = true;
			}else{
				flag = false;
				
				//如果有异常表明只删除了数据库的图片资料，但是没有删除图片资源，所以要重新保存删除了的图片资料
				//同理，新增图片也会有这情况
				for(Material newM:newMaterials){
					materialDao.deleteMateria(newM.getId());
				}
				for(Material oldM:oldMaterials){
					materialDao.saveMateria(oldM);
				}
				
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage()+"图片资源处理失败："+savePaths);
			
			//如果有异常表明只删除了数据库的图片资料，但是没有删除图片资源，所以要重新保存删除了的图片资料
			//同理，新增图片也会有这情况
			for(Material newM:newMaterials){
				materialDao.deleteMateria(newM.getId());
			}
			for(Material oldM:oldMaterials){
				materialDao.saveMateria(oldM);
			}
			m.put("result", false);
			return m;
		}
		m.put("result", flag);
		m.put("id", id.toString());
		return m;
	}
	
	@Override
	public Customer findByCustomer(Customer customer) {
		try {
			return customerDao.findByCustomer(customer);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户信息失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getMacaoAccountCList(Pager accountCPager, Long customerId) {
		try {
			return customerDao.getMacaoAccountCList(accountCPager,customerId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"获取澳门通记帐卡数据失败");
			throw new ApplicationException();
		}
	}
	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年3月8日
	 */
	@Override
	public boolean checkBalance(Long id) {
		try {
			MainAccountInfo mainAccountInfo = mainAccountInfoDao.findByMainId(id);
			boolean hasCard = customerDao.getCard(id);
			BigDecimal balance = mainAccountInfo.getBalance();
			return ((balance.intValue())==0) && hasCard;
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户信息失败");
			throw new ApplicationException();
		}
	}
	@Override
	public Pager getMacaoTagInfoList(Pager accountCPager, Long customerId) {
		try {
			return customerDao.getMacaoTagInfoList(accountCPager,customerId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"获取澳门通记帐卡数据失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getMacaoVehicleList(Pager vehiclePager, Long id) {
		try {
			return customerDao.getMacaoVehicleList(vehiclePager,id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"获取澳门通记帐卡数据失败");
			throw new ApplicationException();
		}
	}

	@Override
	public Pager getMacaoUnBindTagInfoList(Pager pager, Long id) {
		try {
			return customerDao.getMacaoUnBindTagInfoList(pager,id);
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"获取澳门通记帐卡数据失败");
			throw new ApplicationException();
		}
	}


	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年3月16日
	 */
	@Override
	public boolean hasApproval(Long customerId) {
		try {
			return customerModifyApplyDao.hasApproval(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"客户信息失败");
			throw new ApplicationException();
		}
	}

	/**
	 * @Descriptioqn:
	 * @param customer
	 * @param oldCustomer
	 * @param tempPicName
	 * @param deleteOldMaterialID
	 * @return
	 * @author lgm
	 * @date 2017年3月16日
	 */
	@Override
	public void saveApply(Customer oldCustomer, Customer newCustomer,String oldpath,String newpath) {
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSCUSTOMERMODIFYAPPLY_NO");
		Long id = Long.parseLong(seq.toString());
		customerModifyApplyDao.save(id,oldCustomer,newCustomer,oldpath,newpath,new Date());
	}

	/**
	 * @Descriptioqn:
	 * @param idType
	 * @param idCode
	 * @return
	 * @author lgm
	 * @date 2017年3月18日
	 */
	@Override
	public boolean hasCustomer2(String idType, String idCode) {
		return customerDao.findByTypeAndCode(idType,idCode);
	}
	
	public Map omsInterface(Long operateId,String operateName){
		try {
			
			JSONObject json = new JSONObject();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(new Date());
			json.accumulate("loginName", PropertiesUtil.getValue("/url.properties","loginName"));
			json.accumulate("password", PropertiesUtil.getValue("/url.properties","password"));
			json.accumulate("timer", format.format(new Date()));
			String data = DesEncrypt.getInstance().encrypt(json.toString());
			String url = PropertiesUtil.getValue("/url.properties","omsurl")+"otherInterface/otherInterface_getServiceParam.do";
//			System.out.println(url);
//			System.out.println("&key=Customer Registration time&operateId="+operateId+"&operateName="+operateName+"operateTime"+(new Date()));
			return HttpUtil.callClientByHTTP(url, "au_token="+data+"&key=Customer Registration time&operateId="+operateId+"&operateName="+operateName+"&operateTime="+nowTime, "POST");
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Customer findByCustomerNameAndIdNo(String customerName,
			String idType, String idCode) {
		 
		return customerDao.findByCustomerNameAndIdNo(customerName,idType, idCode);
	}

	@Override
	public Pager findAll(Pager pager) {
		return customerDao.findAll(pager);
	}
	
	
	/**
	 * 根据证件类型+证件号码+二级编码唯一查找客户
	 */
	@Override
	public Customer findByIdTypeCodeSecondNo(String idType, String idCode, String secondNo) {
		return customerDao.findByIdTypeCodeSecondNo(idType, idCode, secondNo);
	}

	@Override
	public Pager findByCardNo(Pager pager, String cardNo) {
		// TODO Auto-generated method stub
		return customerDao.findByCardNo(pager,cardNo);
	}

	/**
	 * 根据证件类型+证件号码，查找二级编码不为空的的客户
	 */
	@Override
	public Customer findByIdNoWithSecondNo(String idType, String idCode) {
		return customerDao.findByIdNoWithSecondNo(idType, idCode);
	}

	@Override
	public List<BlackListTemp> findBlackListTMP(String cardNo,String obuSerial) {
		List<BlackListTemp> list = blackListDao.findBlackListTMP(cardNo,obuSerial);
		return list;
	}
	
	/**
	 * @Descriptioqn:
	 * @param customerImp
	 * @return
	 * @author lgm
	 * @date 2017年6月7日
	 */
	@Override
	public boolean hasCustomerImp(CustomerImp customerImp) {
		try {
			CustomerImp customer = customerImpDao.findByIdTypeAndIdCode(customerImp);
			return customer!=null;
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException | InvocationTargetException e) {
		}
		return false;
	}
	
	@Override
	public boolean hasCustomer(CustomerImp customerImp) {
		Customer customer = customerDao.findByIdTypeAndIdCode(customerImp);
		return customer!=null;
	}

	/**
	 * @Descriptioqn:
	 * @param idType
	 * @param idCode
	 * @return
	 * @author lgm
	 * @date 2017年6月19日
	 */
	@Override
	public List<Map<String, Object>> findByIdTypeAndIdCode(String idType,String idCode) {
		return customerImpDao.findByIdTypeAndIdCode(idType, idCode);
	}

	/**
	 * @Descriptioqn:
	 * @param idType
	 * @param idCode
	 * @return
	 * @author lgm
	 * @date 2017年6月21日
	 */
	@Override
	public boolean findByTypeAndCode(String idType, String idCode) {
		return customerDao.findByTypeAndCode(idType, idCode);
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年6月21日
	 */
	@Override
	public boolean hasCardOrTag(Long id) {
		Integer count = customerDao.hasCardOrTag(id);
		return count>0;
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @param idType
	 * @param idCode
	 * @return
	 * @author lgm
	 * @date 2017年6月26日
	 */
	@Override
	public boolean hasCustomerForEdit(Long id, String idType, String idCode,String secondNo) {
		List<Map<String,Object>> list = null;
		if(StringUtil.isNotBlank(secondNo)){
			//二级编码不为空，先通过id、证件类型和证件号码获取客户
			list = customerDao.findByTypeCodeSecondNo(id, idType, idCode, null);
			if(list.size()>0){
				//如果list只有一条记录，获取list的二级编码，如果list的二级编码为空，返回true
				//即不能存在相同证件类型和证件号码，但是有一个有二级编码和一个没有二级编码
				if(list.size()==1){
					String temp = (String)list.get(0).get("SECONDNO");
					if(!StringUtil.isNotBlank(temp))
						return true;
				}
				//如果list不只有一条记录，则使用id、idType、idCode来获取集合
				list = customerDao.findByTypeCodeSecondNo(id, idType, idCode, secondNo);
			}
		}else{
			//二级编码为空，则通过id、 idType、 idCode和secondNo获取客户
			list = customerDao.findByTypeCodeSecondNo(id, idType, idCode, secondNo);
		}
		return list.size()>0;
	}

	@Override
	public List<Map<String, Object>> findCardNoByIdTypeAndIdCode(Long customerId) {
		return customerDao.findCardNoByIdTypeAndIdCode(customerId);
	}

	@Override
	public List<Map<String, Object>> findCustomerList(String organ,
			String idCode, String idType) {
		 
		return customerDao.findCustomerList(organ,idCode, idType);
	}

	@Override
	public List<ExceptionCustomer> findExcCustomerByCustomerId(Long customerId) {
		return exceptionCustomerDao.findAllByCustomerId(customerId);
	}

	@Override
	public Map<String, Object> updateExcCustomer(ExceptionCustomer exceptionCustomer) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		exceptionCustomerDao.updateNotNull(exceptionCustomer);
		resultMap.put("result", "true");
		return resultMap;
	}

	@Override
	public List<SpecialList> findSpecialListCustomerId(Long customerId) {
		return specialListDao.findAllByCustomerId(customerId);
	}
	@Override
	public Customer findbyTagInfoId(Long id) {
		return customerDao.findbyTagInfoId(id);
	}

	@Override
	public boolean checkModifyApply(Customer customer) {
		try {
			CustomerModifyApply customerModifyApply = customerModifyApplyDao.findByTypeCode(customer.getIdCode(), customer.getIdType());
			if(customerModifyApply != null){
				//如果存在待审批的新证件类型+证件号码.返回true
				return true;
			}else{
				return false;
			}
		
		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException | InvocationTargetException e) {
		}
		
		return false;
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @return
	 * @author lgm
	 * @date 2017年9月23日
	 */
	@Override
	public CustomerImp findCustomerImpById(Long id) {
		return customerImpDao.findById(id);
	}

	/***
	 * 根据卡号获取客户信息
	 * @param cardNo
	 * @return
     */
	@Override
	public Customer findByCardNo(String cardNo) {
		return customerDao.findByCardNo(cardNo);
	}

	@Override
	public Pager findByUserNO(Pager pager, String userNo) {
		return customerDao.findByUserNO(pager,userNo);
	}


}
