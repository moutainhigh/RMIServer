package com.hgsoft.httpInterface.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.dao.TagMigrateDao;
import com.hgsoft.httpInterface.serviceInterface.ITagMigrateInterfaceService;
import com.hgsoft.utils.Pager;

@Service
public class TagMigrateInterfaceService implements ITagMigrateInterfaceService {
	
	private static Logger logger = Logger.getLogger(AccountCInterfaceService.class.getName());
	@Resource
	private TagMigrateDao tagMigrateDao;

	
	
	@Override
	public Pager getVehicleList(Pager pager, String tagNo, String vehiclePlate,String vehicleColor,String authState) {
		try{
			return tagMigrateDao.findVehicleList(pager, tagNo, vehiclePlate, vehicleColor,authState);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，待审核查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，待审核查询失败！");
		}
	}

	@Override
	public Map<String, Object> getVehicleInfo(String viid) {
		try{
			return tagMigrateDao.findVehicleInfo(viid);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，审核记录详情查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，审核记录详情查询失败！");
		}
	}

	@Override
	public String updateVehicleState(String trid, String authDate, String authId, String authNo, String authName, String authState) {
		String message = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(authDate);
			// 根据传入的trid查询出电子标签迁移申请记录表记录
			Map<String, Object> migrate = tagMigrateDao.findMigrateByTrid(trid);
			if (migrate == null) {
				message = "审核状态修改失败,该记录不存在";
			} else if (!(migrate.get("AUTHSTATE")).toString().equals("1")) {
				message = "审核状态修改失败,该记录状态不是未审核状态";
			} else {
				if (authState.equals("2")) {
					String newVehicleId = migrate.get("NEWVEHICLEID").toString();
					String tagNo = migrate.get("TAGNO").toString();
					String newCustomerId = migrate.get("NEWCUSTOMERID").toString();
					String vehicleId = migrate.get("VEHICLEID").toString();
					// 根据新车辆id获取车卡标签绑定表记录的电子标签ID字段值,即tagId
					String CarObuCardTagId = tagMigrateDao.findCarObuCardTagId(newVehicleId);
					if (!CarObuCardTagId.equals("null")) {
						message = "当前车辆已绑定电子标签";
					} else {
						// 获取电子标签发行信息表id
						String tagInfoId = tagMigrateDao.findTagInfoId(tagNo);
						tagMigrateDao.updateNewCarObuCardTagId(newVehicleId,tagInfoId);
						tagMigrateDao.updateCarObuCardTagId(vehicleId);
						tagMigrateDao.updateTagInfoClientId(newCustomerId,tagNo);
					}
				}
				tagMigrateDao.updateMigrate(trid, date, authId, authNo,authName, authState);
				if(message.equals("")){
					message = "操作成功！";
				}
			}
			return message;
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，审核记录详情查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，审核记录详情查询失败！");
		} catch (ParseException e) {
			e.printStackTrace();
			return "操作时间格式不正确";
		}
	}

}