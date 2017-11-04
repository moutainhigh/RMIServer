package com.hgsoft.ygz.service;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 同步请求服务接口(实时)
 *
 * @author saint-yeb
 */
@Service
public interface RealTransferService {

	/**
	 * 储值卡信息同步
	 *
	 * @param customer
	 * @param prepaidC
	 * @param vehicle
	 * @param cardStatus
	 * @param operaTpye
	 */
	public void prepaidCTransfer(Customer customer, PrepaidC prepaidC, VehicleInfo vehicle, Integer cardStatus, Integer operaTpye);

	/**
	 * 预付卡发行同步
	 *
	 * @param customer
	 * @param accountCInfo
	 * @param vehicleInfo
	 * @param cardStatus
	 * @param operaTpye
	 */
	void accountCInfoTransfer(Customer customer, AccountCInfo accountCInfo, VehicleInfo vehicleInfo, Integer cardStatus, Integer operaTpye);

	/**
	 * OBU信息同步
	 */
	public void ObuInfoTransfer(Customer customer, TagInfo tagInfo, VehicleInfo vehicleInfo, Integer operaTpye, Integer obuSate);

	/**
	 * 客户信息同步
	 *
	 * @param customer
	 * 		客户信息
	 * @param operator
	 * 		修改、增加、删除
	 */
	public void userInfoTransfer(Customer customer, Integer operator);

	/**
	 * 充值信息同步
	 *
	 * @param scAddSureId
	 * @param customPointCode
	 * 		网点编号
	 * @param tradeTime
	 * @param realPrice
	 * @param returnAmount
	 * @param cardNo
	 */
	public void rechargeInfoTransfer(Long scAddSureId, String customPointCode, Date tradeTime, BigDecimal realPrice, BigDecimal returnAmount, String cardNo);

	/**
	 * 充值冲正信息同步
	 *
	 * @param scAddSureId
	 * @param reversalTime
	 * @param cardNo
	 */
	public void reversalInfoTransfer(Long scAddSureId, Date reversalTime, String cardNo);

	/**
	 * 用户车辆信息同步
	 *
	 * @param vehicleInfo
	 * 		车辆信息实体
	 * @param customer
	 * 		用户信息实体
	 * @param operaTpye
	 * 		操作类型
	 */
	void vehicleInfoTransfer(VehicleInfo vehicleInfo, Customer customer, Integer operaTpye);
}
