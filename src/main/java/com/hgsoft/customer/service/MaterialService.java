package com.hgsoft.customer.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.customer.dao.MaterialDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.serviceInterface.IMaterialService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Service
public class MaterialService implements IMaterialService {
	private static Logger logger = Logger.getLogger(MaterialService.class.getName());
	@Resource
	SequenceUtil sequenceUtil;

	@Resource
	private MaterialDao materialDao;
	
	public void updateVehicleId(String materialIds,Long vehicleId){
		String materialId[]=null;
		if(materialIds!=null){
			materialId=materialIds.split(";");
			for(int i=0;i<materialId.length;i++){
				if(materialId[i]!=""){
					Material material=materialDao.findById(Long.parseLong(materialId[i]));
					material.setVehicleID(vehicleId);
					materialDao.updateMateria(material);
				}
			}
		}
	}
	
	@Override
	public List<Material> findMateria(Material material){
		try {
			return materialDao.findMateria(material);
		} catch (ApplicationException e) {
			throw new ApplicationException("查询图片信息失败");
		}
	}

	/**
	 * 客户号+图片类型+编码code可以唯一确认图片
	 * material中的type不能空
	 * species 为图片种类 client/car/other
	 */
	@Override
	public Map<String, Object> saveMateria(Customer customer,VehicleInfo vehicleInfo, Material material, String[] tempPicNameList,
			String[] deleteOldMaterialIDList,String species) {
		Map<String, Object> resultMap = new HashMap<String ,Object>();
		StringBuffer tempPicPaths = new StringBuffer();
		StringBuffer savePaths = new StringBuffer();;
		StringBuffer oldPicPaths = new StringBuffer();
		
		//List<Material> materials = materialDao.findMateria(material);
		//获得图片资料的最新一个编码
		try {
			if(deleteOldMaterialIDList!=null && deleteOldMaterialIDList.length>0){
				//表示有要删除的照片,执行以下程序
				for(int i=0;i<deleteOldMaterialIDList.length;i++){
					Material oldMaterial = null;
					if(StringUtil.isNotBlank(deleteOldMaterialIDList[i]))oldMaterial = materialDao.findById(Long.parseLong(deleteOldMaterialIDList[i]));
					if(oldMaterial!=null){
						oldPicPaths.append(","+oldMaterial.getPicAddr());
						//将图片资料表的数据删掉
						materialDao.deleteMateria(oldMaterial.getId());
					}
				}
			}
					
			//图片类型+编号 为文件名称，编号为code.
			if(tempPicNameList!=null && tempPicNameList.length>0){
				for(int i=0;i<tempPicNameList.length;i++){
					if(StringUtil.isNotBlank(tempPicNameList[i])){
						//图片后缀
						String endFlag = tempPicNameList[i].substring(tempPicNameList[i].indexOf("."));
						//最新编码
						long code = Long.parseLong(materialDao.getLastCode(customer.getId(), material.getType())) + 1;
						//保存图片文件
						//String savePath = "material"+File.separator+material.getType()+File.separator+customer.getUserNo()+File.separator+material.getCode() + System.currentTimeMillis()+".jpg";
						String savePath = StringUtil.getPicBasePath(material.getType(), code+"", customer.getUserNo(), species);
						String name = StringUtil.getPicName(material.getType(), code+"");
						savePath = savePath + name + endFlag;
						savePaths.append(","+savePath);
						tempPicPaths.append(","+tempPicNameList[i]);
						//保存新上传的图片资料
						Material newMaterial = new Material();
						BigDecimal SEQ_CSMS_Material_NO = sequenceUtil.getSequence("SEQ_CSMS_Material_NO");
						newMaterial.setId(Long.valueOf(SEQ_CSMS_Material_NO.toString()));
						newMaterial.setCustomerID(customer.getId());
						newMaterial.setType(material.getType());
						newMaterial.setCode(code+"");
						newMaterial.setPicAddr(savePath);
						newMaterial.setUp_Date(new Date());
						if(vehicleInfo!=null) newMaterial.setVehicleID(vehicleInfo.getId());
						if(material.getBussinessId() != null) newMaterial.setBussinessId(material.getBussinessId());
						materialDao.saveMateria(newMaterial);
					}
				}
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+"图片资料处理失败");
			throw new ApplicationException();
		}
				
				
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
		
		resultMap.put("result", "true");
		resultMap.put("oldPicPaths", oldPicPathsStr);
		resultMap.put("tempPicPaths", tempPicPathsStr);
		resultMap.put("savePaths", savePathsStr);
		return resultMap;
	}

	@Override
	public List<Material> findPreCardRefund(Long refundId){
		return materialDao.findPreCardRefund(refundId);
	}

	@Override
	public List<Material> findRefundMaterial(Long bussinessid, String type) {
		return materialDao.findRefundMaterial(bussinessid, type);
	}
	
	@Override
	public List<Material> findRefundMaterialForAMMS(Long bussinessid) {
		return materialDao.findRefundMaterialForAMMS(bussinessid);
	}
}
