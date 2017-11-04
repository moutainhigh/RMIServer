package com.hgsoft.ygz.service.impl;


import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.utils.JdbcUtil;
import com.hgsoft.ygz.common.BusinessTypeEmeu;
import com.hgsoft.ygz.common.OperationTypeEmeu;
import com.hgsoft.ygz.entity.RealBusinessReq;
import com.hgsoft.ygz.service.RealBusinessReqService;
import com.hgsoft.ygz.service.RealTransferService;
import com.hgsoft.ygz.util.DataTransfer;
import com.hgsoft.ygz.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 同步请求服务接口实现类
 *
 * @author saint-yeb
 */
@Service
public class RealTransferServiceImpl implements RealTransferService {

	private static Logger logger = Logger.getLogger(RealTransferServiceImpl.class);

	@Resource
	private RealBusinessReqService realBusinessReqService;

	@Resource
	protected JdbcUtil jdbcUtil;

	@Override
	public void prepaidCTransfer(Customer customer, PrepaidC prepaidC, VehicleInfo vehicle, Integer cardStatus, Integer operaTpye) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", prepaidC.getCardNo());
		map.put("cardType", "211");//储值卡固定211
		map.put("model", "");
//		map.put("agencyId", prepaidC.getSalePlaceId());
		map.put("userId", customer.getUserNo());
		map.put("vehiclePlate", vehicle.getVehiclePlate());
		map.put("vehicleColor", vehicle.getVehicleColor());
		map.put("enableTime", prepaidC.getStartDate().getTime());

		map.put("expireTime", prepaidC.getEndDate().getTime());
		map.put("issuedType", "2");//默认为线下
		map.put("channelId", prepaidC.getPlaceNo());//网点编号
		map.put("issuedTime", prepaidC.getSaleTime().getTime());
		map.put("status", cardStatus);
		map.put("statusChangeTime", System.currentTimeMillis());//TODO
//		map.put("operation", prepaidC.getStartDate());
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		logger.debug("userCardTransfer:" + jsonObject.toString());
		RealBusinessReq realBusinessReq = new RealBusinessReq();
		realBusinessReq.setBusinessContent(jsonObject.toString());
		realBusinessReq.setBusinessType(BusinessTypeEmeu.CARDUPLOAD.getCode());
		realBusinessReq.setOperation(operaTpye);
		realBusinessReq.setCreateTime(DateUtil.getNowTime());
		realBusinessReqService.save(realBusinessReq);
	}

	@Override
	public void accountCInfoTransfer(Customer customer, AccountCInfo accountCInfo, VehicleInfo vehicleInfo, Integer cardStatus, Integer operaTpye) {
		Map<String, Object> map = new HashMap<>(20);
		// 车牌信息
		Map<String, Object> vehicleInfoMap = getVehicleInfo(vehicleInfo, accountCInfo);

		map.put("id", accountCInfo.getCardNo());
		map.put("model", "");
		map.put("userId", customer.getUserNo());
		map.put("vehiclePlate", vehicleInfoMap.get("vehiclePlate"));
		map.put("vehicleColor", vehicleInfoMap.get("vehicleColor"));
		map.put("enableTime", accountCInfo.getStartDate().getTime());
		map.put("expireTime", accountCInfo.getEndDate().getTime());
		map.put("issuedType", "2");
		map.put("channelId", accountCInfo.getPlaceNo());
		map.put("issuedTime", accountCInfo.getIssueTime().getTime());
		map.put("status", cardStatus);
		map.put("statusChangeTime", System.currentTimeMillis());
		map.put("accountId", accountCInfo.getAccountId());

		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		logger.debug("accountInfoTranser：" + jsonObject.toString());

		// 中间表入库
		RealBusinessReq realBusinessReq = new RealBusinessReq();
		realBusinessReq.setBusinessContent(jsonObject.toString());
		realBusinessReq.setBusinessType(BusinessTypeEmeu.CARDUPLOAD.getCode());
		realBusinessReq.setOperation(operaTpye);
		realBusinessReq.setCreateTime(DateUtil.getNowTime());
		realBusinessReqService.save(realBusinessReq);
	}

	/**
	 * 获取车牌以及车牌颜色，传入的车牌信息为空则进行查库
	 *
	 * @param vehicleInfo
	 * 		车牌信息
	 * @param accountCInfo
	 * 		卡信息
	 * @return 车牌信息Map
	 * @author wangjinhao
	 */
	private Map<String, Object> getVehicleInfo(VehicleInfo vehicleInfo, AccountCInfo
			accountCInfo) {
		Map<String, Object> result = new HashMap<>();

		if (null == vehicleInfo) {
			// 没有车牌信息就用用户卡信息进行查库
			StringBuffer vehicleInfoSql = new StringBuffer("select vehiclePlate,vehicleColor from CSMS_Vehicle_Info cvi " +
					"left join csms_carObuCard_info cci on cci.vehicleID = cvi.id where cci" +
					".AccountCID = ?");

			String vehiclePlate = "";
			String vehicleColor = "";
			Map<String, Object> vehicleInfoMap = null;

			try {
				vehicleInfoMap = jdbcUtil.getJdbcTemplate().queryForMap(vehicleInfoSql.toString(),
						accountCInfo.getId());
				vehiclePlate = vehicleInfoMap.containsKey("vehiclePlate") ? vehicleInfoMap.get
						("vehiclePlate").toString() : "";
				vehicleColor = vehicleInfoMap.containsKey("vehicleColor") ? vehicleInfoMap.get
						("vehicleColor").toString() : "";
			} catch (Exception e) {
				logger.error("营改增查询车牌信息失败！");
			}

			result.put("vehiclePlate", vehiclePlate);
			result.put("vehicleColor", vehicleColor);
		} else {
			// 有车牌信息
			result.put("vehiclePlate", vehicleInfo.getVehiclePlate());
			result.put("vehicleColor", vehicleInfo.getVehicleColor());
		}

		return result;
	}

	@Override
	public void ObuInfoTransfer(Customer customer, TagInfo tagInfo, VehicleInfo vehicleInfo, Integer operaTpye, Integer obuSate) {
		JSONObject jsonObject = new JSONObject();
		Date now = new Date();
		jsonObject.put("id", tagInfo.getTagNo());
		jsonObject.put("brand", "");
		jsonObject.put("model", "");
		jsonObject.put("userId", customer == null ? vehicleInfo.getCustomerID() : customer.getUserNo());
		jsonObject.put("vehicleId", vehicleInfo.getVehiclePlate() + "_" + vehicleInfo.getVehicleColor());
		jsonObject.put("enableTime", tagInfo.getStartTime() == null ? now.getTime() : tagInfo.getStartTime().getTime());
		jsonObject.put("expireTime", tagInfo.getEndTime() == null ? now.getTime() : tagInfo.getEndTime().getTime());
		jsonObject.put("registeredType", "2");//固定为线下
		jsonObject.put("registeredChannelId", tagInfo.getIssueplaceID());
		jsonObject.put("registeredTime", tagInfo.getIssuetime() == null ? now.getTime() : tagInfo.getIssuetime().getTime());
		jsonObject.put("installType", "2");//固定为网点安装
		jsonObject.put("installChannelId", tagInfo.getIssueplaceID());
		jsonObject.put("installTime", tagInfo.getIssuetime() == null ? now.getTime() : tagInfo.getIssuetime().getTime());
		jsonObject.put("status", obuSate);
		jsonObject.put("statusChangeTime", System.currentTimeMillis());
		jsonObject.put("operation", operaTpye.intValue());
		logger.debug("obuInfoTranser:" + jsonObject.toString());

		// 中间表入库
		RealBusinessReq realBusinessReq = new RealBusinessReq();
		realBusinessReq.setBusinessContent(jsonObject.toString());
		realBusinessReq.setBusinessType(BusinessTypeEmeu.OBUUPLOAD.getCode());
		realBusinessReq.setOperation(operaTpye);
		realBusinessReq.setCreateTime(new Timestamp(now.getTime()));
		realBusinessReqService.save(realBusinessReq);
	}

	@Override
	public void userInfoTransfer(Customer customer, Integer operator) {
              /*YGZ RuiHaoZ Add 上传客户信息*/
            if (customer == null) {
                    return;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", customer.getUserNo());
            String userType = (customer.getUserType().equals("8") || customer.getUserType().equals("08")) ? "1" : "2";
            map.put("userType", userType);
            map.put("userName", customer.getOrgan());
            String userIdType = DataTransfer.idTypeTransfer(customer.getIdType(), userType);
            map.put("userIdType", userIdType);
            map.put("userIdNum", customer.getIdCode());
            String tel = userType.equals("1") ? customer.getOrganTel() : customer.getAgentTel();
            map.put("tel", tel);
            map.put("address", customer.getAddr());
            // TODO: 2017/10/30   服务类型定为‘2’
            map.put("registeredType", 2);
            map.put("channelId", customer.getPlaceNo());
            long registeredTime = customer.getFirRunTime().getTime();
            map.put("registeredTime", registeredTime);
            String department = "";
            if (userType.equals("2")) {
                    if (StringUtils.isEmpty(customer.getSecondNo())) {
                            department = "本部";
                    } else {
                            department = customer.getSecondName();
                    }
            }
            map.put("department", department);
            map.put("agentName", customer.getAgentName());
            String agentIdType = DataTransfer.idTypeTransfer(customer.getAgentIdType(), userType);
            map.put("agentIdType", agentIdType);
            map.put("agentIdNum", customer.getAgentIdCode());
            map.put("status", customer.getState());
            long statusChangeTime = new Date().getTime();
            map.put("statusChangeTime", statusChangeTime);
            map.put("operation", operator);

            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            logger.debug("UserInfoTransfer:" + jsonObject.toString());
            RealBusinessReq realBusinessReq = new RealBusinessReq();
            realBusinessReq.setBusinessContent(jsonObject.toString());
            realBusinessReq.setBusinessType(BusinessTypeEmeu.USERUPLOAD.getCode());
            realBusinessReq.setOperation(OperationTypeEmeu.TRANSFER.getCode());
            realBusinessReq.setCreateTime(DateUtil.getNowTime());
            realBusinessReqService.save(realBusinessReq);
	}

	@Override
	public void rechargeInfoTransfer(Long scAddSureId, String customPointCode,
	                                 Date tradeTime, BigDecimal realPrice, BigDecimal returnAmount,
	                                 String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scAddSureId ", scAddSureId);//充值确认表id
		map.put("customPointCode ", customPointCode);//网点编号
		map.put("tradeTime", tradeTime.getTime());//交易时间
		map.put("paidAmount", realPrice);//实收金额
		map.put("giftAmount", returnAmount);//赠送金额
		map.put("rechargeAmount", realPrice.add(returnAmount));//充值金额
		map.put("cardId", cardNo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		logger.debug("userCardTransfer:" + jsonObject.toString());
		RealBusinessReq realBusinessReq = new RealBusinessReq();
		realBusinessReq.setBusinessContent(jsonObject.toString());
		realBusinessReq.setBusinessType(BusinessTypeEmeu.RECHARGEUPLOAD.getCode());
		realBusinessReq.setOperation(OperationTypeEmeu.TRANSFER.getCode());
		realBusinessReq.setCreateTime(DateUtil.getNowTime());
		realBusinessReqService.save(realBusinessReq);
	}

	@Override
	public void reversalInfoTransfer(Long scAddSureId, Date reversalTime,
	                                 String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id ", scAddSureId);
		map.put("effectiveTime", reversalTime.getTime());
		map.put("cardId", cardNo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		logger.debug("userCardTransfer:" + jsonObject.toString());
		RealBusinessReq realBusinessReq = new RealBusinessReq();
		realBusinessReq.setBusinessContent(jsonObject.toString());
		realBusinessReq.setBusinessType(BusinessTypeEmeu.REVERSALUPLOAD.getCode());
		realBusinessReq.setOperation(OperationTypeEmeu.TRANSFER.getCode());
		realBusinessReq.setCreateTime(DateUtil.getNowTime());
		realBusinessReqService.save(realBusinessReq);
	}

	@Override
	public void vehicleInfoTransfer(VehicleInfo vehicleInfo, Customer customer, Integer operaTpye) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("vehiclePlate", vehicleInfo.getVehiclePlate());
		map.put("vehicleColor", vehicleInfo.getVehicleColor());
		map.put("type", vehicleInfo.getNSCVehicleType());
		map.put("userId", customer.getUserNo());
		map.put("ownerName", vehicleInfo.getOwnerName());
		map.put("ownerIdType", vehicleInfo.getOwnerIdType());
		map.put("ownerIdNum", vehicleInfo.getOwnerIdNum());
		map.put("ownerTel", vehicleInfo.getOwnerTel());
		map.put("address", vehicleInfo.getAddress());

		// 指定联系人
		if (StringUtils.isNotBlank(vehicleInfo.getOwnerIdType())) {
			String ownerIdTypeStr = vehicleInfo.getOwnerIdType();
			if ("1".equals(ownerIdTypeStr.charAt(0) + "")) {
				// 个人
				map.put("contact", customer.getOrgan());
			} else {
				// 单位
				map.put("contact", customer.getAgentName());
			}
		}

		map.put("registeredType", "2");
		map.put("channelId ", vehicleInfo.getPlaceNo());
		map.put("registeredTime", System.currentTimeMillis());
		map.put("vehicleType ", vehicleInfo.getVehicleDriverType());
		map.put("vehicleModel", vehicleInfo.getVehicleModel());
		map.put("useCharacter", vehicleInfo.getUsingNature());
		map.put("VIN", vehicleInfo.getIdentificationCode());
		map.put("engineNum", vehicleInfo.getVehicleEngineNo());
		map.put("registerDate", vehicleInfo.getRegisterDate() != null ? vehicleInfo
				.getRegisterDate().getTime() : null);
		map.put("issueDate", vehicleInfo.getIssueDate() != null ? vehicleInfo.getIssueDate().getTime
				() : null);
		map.put("fileNum", vehicleInfo.getFileNum());
		map.put("approvedCount", vehicleInfo.getApprovedCount());
		map.put("totalMass", vehicleInfo.getTotalMass());
		map.put("maintenanceMass", vehicleInfo.getMaintenanceMass());
		map.put("permittedWeight", vehicleInfo.getPermittedWeight());
		map.put("outsideDimensions", vehicleInfo.getVehicleLong() + "X" + vehicleInfo
				.getVehicleWidth() + "X" + vehicleInfo.getVehicleHeight());
		map.put("permittedTowWeight", vehicleInfo.getPermittedTowWeight());
		map.put("testRecord", vehicleInfo.getTestRecord());
		map.put("wheelCount", vehicleInfo.getVehicleWheels());
		map.put("axleCount", vehicleInfo.getVehicleAxles());
		map.put("axleDistance", vehicleInfo.getAxleDistance());
		map.put("axisType", vehicleInfo.getAxisType());

		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		logger.debug("vehicleInfoTranser：" + jsonObject.toString());

		// 中间表入库
		RealBusinessReq realBusinessReq = new RealBusinessReq();
		realBusinessReq.setBusinessContent(jsonObject.toString());
		realBusinessReq.setBusinessType(BusinessTypeEmeu.VEHICLEUPLOAD.getCode());
		realBusinessReq.setOperation(operaTpye);
		realBusinessReq.setCreateTime(DateUtil.getNowTime());
		realBusinessReqService.save(realBusinessReq);
	}

}
