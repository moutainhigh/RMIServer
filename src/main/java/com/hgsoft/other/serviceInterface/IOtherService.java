package com.hgsoft.other.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.accountC.entity.AccountCInfoHis;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.other.entity.CardDelay;
import com.hgsoft.other.entity.OfficialCardImport;
import com.hgsoft.other.entity.OfficialCardInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.system.entity.ServiceWater;

public interface IOtherService {
	public void saveStart(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness,Map<String,Object> params);
	public void saveStop(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness,Map<String,Object> params);
	public void saveStart(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness,Map<String,Object> params);
	public void saveStartAccountc(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness,Map<String,Object> params,Integer systemType,AccountCApply accountCApply,VehicleInfo vehicleInfo);
	public void saveStop(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness,Map<String,Object> params);
	public void saveStopClear(String flag,VehicleInfo vehicleInfo);
	public void saveStartClear(VehicleInfo vehicleInfo);
	public List<Map<String, Object>> findCardListByCustomer(String idType, String idCode, String expireCardNo);
	public Map<String, Object> findDetailByCardNo(String cardNo,String cardType);
	public void saveCardDelay(CardDelay cardDelay);
	public void updateCardDelay(CardDelay cardDelay);
	public AccountCInfoHis findByCardNoAndState(String cardNo,String state);
	public CardDelay findCardDelayById(Long id);
	public boolean updateFlag(Long id);
	public OfficialCardImport getOfficialCard(String cardNo);
	public void saveOfficialCard(OfficialCardInfo officialCardInfo,Customer customer,Map<String,Object> params);
	public Map<String, Object> getVehicleByCardNo(String cardNo,Customer customer);
	/**
	 * 卡片补写完后，更新卡片的写卡标志以及保存流水等
	 * @param prepaidC
	 * @param accountCInfo
	 * @param serviceWater
	 * @return Map<String, Object>
	 */
	public Map<String, Object> saveWriteSuccess(PrepaidC prepaidC,AccountCInfo accountCInfo,ServiceWater serviceWater);
}
