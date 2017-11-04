package com.hgsoft.acms.other.serviceInterface;

import java.util.List;
import java.util.Map;

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

public interface IOtherServiceACMS {
	public void saveStart(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness);
	public void saveStop(PrepaidC prepaidC, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness);
	public void saveStart(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness);
	public void saveStop(AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness);
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
	public void saveOfficialCard(OfficialCardInfo officialCardInfo,Customer customer);
	public Map<String, Object> getVehicleByCardNo(String cardNo,Customer customer);
}
