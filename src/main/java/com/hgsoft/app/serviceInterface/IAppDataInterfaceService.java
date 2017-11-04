package com.hgsoft.app.serviceInterface;

import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;

import java.util.Map;

public interface IAppDataInterfaceService {

	/**
	 * 车牌认证申请信息
	 * @param vehiclePlate
	 * @param vehplateColor
	 * @param source
	 * @param applyTime
	 * @param time
	 * @param cusPointPoJo
     * @param sysAdmin
     * @return
     */
	Map<String, Object> findVehplateAuthInfo(String vehiclePlate, String vehplateColor, String source, String applyTime, String time, CusPointPoJo cusPointPoJo, SysAdmin sysAdmin);


	/***
	 * 储值卡信息查询（用于验证网商贷证件类型）
	 * @param cardNo
	 * @return
	 */
	public Map<String, Object> queryPrepaidCardInfo(String cardNo);

	/***
	 * 1.4	通过手机获取用户卡号（应用于批量绑卡）
	 * @param mobile
	 * @return
	 */
	Map<String,Object> queryCardNoByMobile(String mobile);

	/***
	 * 1.5	车牌认证（应用于外省办理情况查询）
	 * @param vehiclePlate
	 * @param vehicleColor
	 * @return
	 */
	Map<String,Object> queryOutsideCarInfo(String vehiclePlate, String vehicleColor);

	/***
	 * 1.6	车牌绑定查询（应用于批量绑卡）
	 * @param vehiclePlate
	 * @param vehicleColor
	 * @return
	 */
	Map<String,Object> queryVehplateBindingInfo(String vehiclePlate, String vehicleColor);


	/***
	 * 1.7	卡片绑定查询
	 * cardNo
	 * @return cardType
	 * @return mobile
	 * @return obuNo
	 * @return vehiclePlate
	 * @return vehicleColor
	 * @return shortTel
	 * @return vehicleEngineNo
	 * @return identificationCode
	 * @return balance
	 */
	Map<String,Object> queryCardBindingInfo(String cardNo);
}
