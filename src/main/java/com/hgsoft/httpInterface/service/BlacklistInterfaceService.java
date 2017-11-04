package com.hgsoft.httpInterface.service;

import com.alibaba.fastjson.JSON;
import com.hgsoft.accountC.dao.BlacklistDao;
import com.hgsoft.associateAcount.dao.DarkListDao;
import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.clearInterface.serviceInterface.IBlackListService;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.serviceInterface.IBlacklistInterfaceService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlacklistInterfaceService implements IBlacklistInterfaceService {

	private static Logger logger = Logger.getLogger(AccountCInterfaceService.class.getName());
	@Resource
	private BlacklistDao blacklistDao;
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private DarkListDao darkListDao;
	/*@Resource
	private TollCardBlackDetDao tollCardBlackDetDao;
	@Resource
	private TollCardBlackDetSendDao tollCardBlackDetSendDao;*/
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private IBlackListService blackListService;

	/**
	 * 待下发黑名单查询列表
	 * 
	 * */
	@Override
	public Pager blacklistList(Pager pager, String userNo, String organ,
			String cardNo, String cardType) {
		try {
			return blacklistDao.findBlacklist(pager, userNo, organ, cardNo,
					cardType);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，待下发黑名单查询失败！");
			e.printStackTrace();
			throw new ApplicationException("营运系统，待下发黑名单查询失败！");
		}
	}

	/**
	 * 批量下发到待下发黑名单表
	 * 
	 * */
	public String requestBlacklist(String param) {
		try {
			JSONObject paramJson = (JSONObject) JSONObject.fromObject(param)
					.get("param");
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date genDate = format.parse(paramJson.getString("genDate"));
			Long operId = Long.parseLong(paramJson.getString("operId"));
			String operName = paramJson.getString("operName");
			String operNo = paramJson.getString("operNo");
			JSONArray array = null;
			if (paramJson != null) {
				array = (JSONArray) paramJson.get("resultList");
			} else {
				return "参数param找不到";
			}

			List<Map> list = JSON.parseArray(array.toString(), Map.class);
			if (list != null && !list.isEmpty()) {
				StringBuffer errorMsg = new StringBuffer("");
				int errorCount = 0;
				JSONObject json = new JSONObject();
				int i = 1;

				// 验证传入参数
				for (Map<String, String> map : list) {
					String cardType = map.get("cardType");
					if (map.get("cardNo") == null|| map.get("cardType") == null) {
						errorMsg.append("," + i + ":传入的数据不全");
						errorCount++;
					} else if (!("1".equals(cardType) || "2".equals(cardType) || "3".equals(cardType))) {
						errorMsg.append("," + i + ":产品类型错误");
						errorCount++;
					}
					i++;
				}

				// 若传送过来的参数有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}

				for (Map<String, String> map : list) {
					String cardNo = map.get("cardNo");
					String cardType = map.get("cardType");
					String genCau = map.get("genCau");
					String remark = map.get("remark");
					String obuSerial = map.get("obuSerial");
					// 判断是否产品类型和产品编号存在
					boolean hasNoType = blacklistDao.findNoType(cardNo,
							cardType);
					// 根据产品类型和产品编号获取黑名单表记录
					DarkList oldDarklist = blacklistDao
							.darklistByCardNoAndCardType(cardNo, cardType);
					// 根据产品类型和产品编号获取客户信息
					Map<String, Object> customer = blacklistDao
							.customerByCardNoAndCardType(cardNo, cardType);
					if (!hasNoType) {
						remark = "【此卡无客户】" + remark;
					}

					// 根据产品编号和产品类型查询黑名单表，若hasDarklist!=null,即黑名单表已经有记录，则根据传过来的参数更新黑名单表；若不存在，则新增记录
					if (oldDarklist != null) {
						if(obuSerial==null||obuSerial.trim()==""){
							blacklistDao.updateDarklist(oldDarklist.getId(),genDate, genCau, operId, operNo, operName,remark);
							
						}else{
							blacklistDao.updateDarklist(oldDarklist.getId(),obuSerial,genDate, genCau, operId, operNo, operName,remark);
							
						}
					} else {
						DarkList darkList = new DarkList();
						darkList.setId(sequenceUtil
								.getSequenceLong("SEQ_CSMSDARKLIST_NO"));
						darkList.setCardNo(cardNo);// 产品编号
						darkList.setCardType(cardType);// 产品类型
						darkList.setObuSerial(obuSerial);
						darkList.setGenDate(genDate);// 产生时间
						darkList.setGencau(genCau);// 产生原因
						darkList.setGenmode("1");// 产生方式：人工产生
						darkList.setOperId(operId);// 操作员ID
						darkList.setOperName(operName);// 操作员名称
						darkList.setOperNo(operNo);// 操作员编号
						darkList.setUpdateTime(genDate);// 更新时间
						darkList.setState("1");// 状态：未下发
						darkList.setRemark(remark);
						if (hasNoType) {
							darkList.setCustomerId(Long.parseLong(customer.get(
									"customerid").toString()));
							darkList.setUserNo(customer.get("userno")
									.toString());
							darkList.setUserName(customer.get("organ")
									.toString());
						}
						darkListDao.save(darkList);
					}
					//发送数据给铭鸿
					blackListService.saveBlackListWarter(null, null, cardType, cardNo, new Date(), "3", 5, "0", operId, operNo, operName, null, null, null, new Date(), null);
					// 清算系统用的黑名单
//					TollCardBlackDet tollCardBlackDet = null;
//					TollCardBlackDetSend tollCardBlackDetSend = null;
//					String license = " ";// 车牌号
//					if ("1".equals(cardType)) {
//						tollCardBlackDet = new TollCardBlackDet(4401, null,cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						tollCardBlackDetSend = new TollCardBlackDetSend(4401,null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
//						if (vehicleInfo != null) {
//							license = vehicleInfo.getVehiclePlate();
//						}
//						tollCardBlackDet.setLicense(license);
//						tollCardBlackDetSend.setLicense(license);
//					} else if ("2".equals(cardType)) {
//						tollCardBlackDet = new TollCardBlackDet(4401, null,cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						tollCardBlackDetSend = new TollCardBlackDetSend(4401,null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
//						if (vehicleInfo != null) {
//							license = vehicleInfo.getVehiclePlate();
//						}
//						tollCardBlackDet.setLicense(license);
//						tollCardBlackDetSend.setLicense(license);
//					} else {
//						tollCardBlackDet = new TollCardBlackDet(0,null," ", null, obuSerial, null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						tollCardBlackDetSend = new TollCardBlackDetSend(0,null," " ,null, obuSerial, null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						VehicleInfo vehicleInfo = vehicleInfoDao.findByTagNo(cardNo);
//						if (vehicleInfo != null) {
//							license = vehicleInfo.getVehiclePlate();
//						}
//						tollCardBlackDet.setLicense(license);
//						tollCardBlackDetSend.setLicense(license);
//					}
//					tollCardBlackDetDao.save(tollCardBlackDet);
//					tollCardBlackDetSendDao.save(tollCardBlackDetSend);
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					TollCardBlackDetSend tollCardBlackDetSend = null;
					String license = " ";// 车牌号
					if ("1".equals(cardType)) {
						tollCardBlackDet = new TollCardBlackDet(4401, null,cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
						tollCardBlackDetSend = new TollCardBlackDetSend(4401,null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
						VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
						if (vehicleInfo != null) {
							license = vehicleInfo.getVehiclePlate();
						}
						tollCardBlackDet.setLicense(license);
						tollCardBlackDetSend.setLicense(license);
					} else if ("2".equals(cardType)) {
						tollCardBlackDet = new TollCardBlackDet(4401, null,cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
						tollCardBlackDetSend = new TollCardBlackDetSend(4401,null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
						VehicleInfo vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
						if (vehicleInfo != null) {
							license = vehicleInfo.getVehiclePlate();
						}
						tollCardBlackDet.setLicense(license);
						tollCardBlackDetSend.setLicense(license);
					} else {
						tollCardBlackDet = new TollCardBlackDet(0,null," ", null, obuSerial, null,Integer.parseInt(genCau), new Date(), 0,new Date());
						tollCardBlackDetSend = new TollCardBlackDetSend(0,null," " ,null, obuSerial, null,Integer.parseInt(genCau), new Date(), 0,new Date());
						VehicleInfo vehicleInfo = vehicleInfoDao.findByTagNo(cardNo);
						if (vehicleInfo != null) {
							license = vehicleInfo.getVehiclePlate();
						}
						tollCardBlackDet.setLicense(license);
						tollCardBlackDetSend.setLicense(license);
					}
					tollCardBlackDetDao.save(tollCardBlackDet);
					tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/

				}
				return "true";
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，批量下发到待下发黑名单表失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，批量下发到待下发黑名单表失败");
		} catch (ParseException e) {
			e.printStackTrace();
			return "操作时间格式不正确";
		}
		return null;
	}

	/**
	 * 待下发黑名单表接口
	 * 
	 * */
	public Pager queryBlacklist(Pager pager,String cardNo,String cardType,String startGenDate,String endGenDate,String state){
		try {
			return blacklistDao.getBlacklist(pager, cardNo, cardType,startGenDate, endGenDate, state);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，待下发黑名单表失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，待下发黑名单表失败");
		}
	}

	/**
	 * 待下发黑名单解除接口
	 * 
	 * */
	public String removeFromBlacklist(String param) {
		try {
			JSONObject paramJson = (JSONObject) JSONObject.fromObject(param).get("param");
			JSONArray array = null;
			if (paramJson != null) {
				array = (JSONArray) paramJson.get("resultList");
			} else {
				return "参数param找不到";
			}

			List<Map> list = JSON.parseArray(array.toString(), Map.class);
			if (list != null && !list.isEmpty()) {
				StringBuffer errorMsg = new StringBuffer("");
				int errorCount = 0;
				JSONObject json = new JSONObject();
				int i = 1;

				// 验证传入参数
				for (Map<String, String> map : list) {
					String cardNo = map.get("cardNo");
					if (cardNo == null) {
						errorMsg.append(" " + i + ":传入的数据不全");
						errorCount++;
					} else if (!blacklistDao.findDarkListByNo(cardNo)) {
						errorMsg.append(" " + i + ":id不存在");
						errorCount++;
					}
					i++;
				}
				// 若传送过来的参数有错误，则返回
				if (StringUtil.isNotBlank(errorMsg.toString())) {
					json.accumulate("errorMsg", errorMsg.toString());
					json.accumulate("errorCount", errorCount);
					json.accumulate("allCount", list.size());
					return json.toString();
				}
			
				for (Map<String, String> map : list) {
					String darklistId = map.get("cardNo");
					
					blacklistDao.changeState(darklistId);//修改黑名单状态
					
					// 清算系统用的黑名单
					String cardType = "";
					String cardNo = "";
					String genCau = "";
					DarkList darkList = blacklistDao.getDarklistById(darklistId);//根据参数Id获取darkList
					cardType = darkList.getCardType();
					cardNo = darkList.getCardNo();
					genCau = darkList.getGencau();
					//发送数据给铭鸿
					blackListService.saveBlackListWarter(null, null, cardType, cardNo, new Date(), "3", -5, "0", null, null, null, null, null, null, new Date(), null);
//					TollCardBlackDet tollCardBlackDet = null;
//					TollCardBlackDetSend tollCardBlackDetSend = null;
//					String license = " ";// 车牌号
//					VehicleInfo vehicleInfo = null;
//					if ("1".equals(cardType)||"2".equals(cardType)) {
//						tollCardBlackDet = new TollCardBlackDet        (4401, null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						tollCardBlackDetSend = new TollCardBlackDetSend(4401, null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
//					}else {
//						tollCardBlackDet = new TollCardBlackDet(0, null," ", null, cardNo, null,Integer.parseInt(genCau), new Date(), 0,new Date());
//						tollCardBlackDetSend = new TollCardBlackDetSend(0,null, " ", null, cardNo, null,Integer.parseInt(genCau), new Date(), 0,new Date());
//					}
//					//获取并设置车牌号
//					if("1".equals(cardType)||"2".equals(cardType)){
//						vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
//					}else{
//						vehicleInfo = vehicleInfoDao.findByTagNo(cardNo);
//					}
//					if (vehicleInfo != null) {
//						license = vehicleInfo.getVehiclePlate();
//					}
//					tollCardBlackDet.setLicense(license);
//					tollCardBlackDetSend.setLicense(license);
//					tollCardBlackDetDao.save(tollCardBlackDet);
//					tollCardBlackDetSendDao.save(tollCardBlackDetSend);
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					/*TollCardBlackDet tollCardBlackDet = null;
					TollCardBlackDetSend tollCardBlackDetSend = null;
					String license = " ";// 车牌号
					VehicleInfo vehicleInfo = null;
					if ("1".equals(cardType)||"2".equals(cardType)) {
						tollCardBlackDet = new TollCardBlackDet        (4401, null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
						tollCardBlackDetSend = new TollCardBlackDetSend(4401, null, cardNo, null, " ", null,Integer.parseInt(genCau), new Date(), 0,new Date());
					}else {
						tollCardBlackDet = new TollCardBlackDet(0, null," ", null, cardNo, null,Integer.parseInt(genCau), new Date(), 0,new Date());
						tollCardBlackDetSend = new TollCardBlackDetSend(0,null, " ", null, cardNo, null,Integer.parseInt(genCau), new Date(), 0,new Date());
					}
					//获取并设置车牌号
					if("1".equals(cardType)||"2".equals(cardType)){
						vehicleInfo = vehicleInfoDao.findByAccountCNo(cardNo);
					}else{
						vehicleInfo = vehicleInfoDao.findByTagNo(cardNo);
					}
					if (vehicleInfo != null) {
						license = vehicleInfo.getVehiclePlate();
					}
					tollCardBlackDet.setLicense(license);
					tollCardBlackDetSend.setLicense(license);
					tollCardBlackDetDao.save(tollCardBlackDet);
					tollCardBlackDetSendDao.save(tollCardBlackDetSend);*/
					
				}
				return "true";
			}
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"营运系统，待下发黑名单解除接口失败");
			e.printStackTrace();
			throw new ApplicationException("营运系统，批待下发黑名单解除接口失败");
		}
		return null;
	}

}