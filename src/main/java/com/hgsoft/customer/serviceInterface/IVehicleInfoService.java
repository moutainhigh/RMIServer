package com.hgsoft.customer.serviceInterface;

import com.hgsoft.app.entity.AppPointCtr;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.entity.VehicleImp;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.jointCard.entity.CardHolder;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IVehicleInfoService {

	public List<VehicleInfo> listAll(VehicleInfo vehicleInfo);

	public void saveVehicle(VehicleInfo vehicleInfo, Material material, String rootPath, List<File> imageFile, Customer customer, String[] tempPicNameList, VehicleBussiness vehicleBussiness, String clientSystem, Map<String, Object> params);

	public void saveVehicle(VehicleInfo vehicleInfo, Customer customer, VehicleBussiness vehicleBussiness, String clientSystem);

	public void updateVehicle(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness);

	public void updateVehicle(VehicleInfo vehicleInfo, VehicleInfo newvehicleInfo, Map<String, Object> params);

	public void deleteVehicle(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness);

	public void deleteVehicleForAMMS(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness);

	public void deleteVehicleForACMS(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness);

	public VehicleInfo findById(Long id);

	public VehicleInfo find(VehicleInfo vehicleInfo);

	public int getVehicleInfoComplete(Long vehicleInfoId);

	public VehicleInfo loadByPlateAndColor(String plate, String color);

	public Pager findByPlateAndColor(Pager pager, Customer customer, String plate, String color);

	public Pager findByPlateAndColorForAMMS(Pager pager, Customer customer, String plate, String color, String bankCode);

	public VehicleInfo findByPlateAndColorAndIdentCode(String vehiclePlate, String vehicleColor, String identificationCode);

	public VehicleInfo findByPrepaidCNo(String cardNo);

	public VehicleInfo findByCustomerId(Long id);

	public Pager findByCustomer(Pager pager, Customer customer);

	public VehicleInfo findByPlateAndColor(Customer customer, String plate, String color);

	public List<Map<String, Object>> findListNotBindByCID(Long id);

	public List<Map<String, Object>> findListNotBindByCIDForAMMS(Long id);

	public void updateObu(Long vehicleID);

	public boolean saveOrUpdateMaterial(Material material, String rootPath, List<File> imageFile, Customer customer, VehicleInfo vehicleInfo);

	public boolean saveOrUpdateMaterial(Material material, String rootPath, String[] tempPicNameList, Customer customer, VehicleInfo vehicleInfo, String[] deleteOldMaterialIDList, String clientSystem);

	public List<Map<String, Object>> listNotBind(Long customerId);

	public List<Map<String, Object>> findVehicleTagNo(Long customerId);

	public List<Map<String, Object>> listNotBind(String cardNo);

	public List<Map<String, Object>> getAllVehByCusId(Long customerId);

	public List<Map<String, Object>> getAllVehByCusIdForAMMS(Long customerId);

	public boolean saveMigrate(VehicleInfo vehicleInfo, String rootPath, Long customerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);

	public List<Map<String, Object>> listBind(Long customerId);

	public List<Map<String, Object>> findBindCardVehicle(Long customerId);

	public void updateVehicleMessage(VehicleInfo vehicleInfo);

	public void updateVehicleForMacaoStartCard(VehicleInfo vehicleInfo, VehicleInfo newvehicleInfo);

	public List<Map<String, Object>> listBindForMacao(Long customerId);

	public List<Map<String, Object>> findListNotBindForMacao(MacaoCardCustomer macaoCardCustomer);

	public VehicleInfo findByAccountCNo(String cardNo);

	/**
	 * 判断车辆是否绑定卡片
	 *
	 * @param vehicleId
	 * @return
	 */
	public Boolean isVehicleBindCard(Long vehicleId);

	/**
	 * 判断车辆是否绑定储值卡
	 *
	 * @param carObuCardInfo
	 * @return
	 */
	public Boolean isVehicleBindPrepaidC(CarObuCardInfo carObuCardInfo);

	/**
	 * 判断车辆是否绑定储值卡
	 *
	 * @param carObuCardInfo
	 * @return
	 */
	public Boolean isVehicleBindPrepaidC(Long vehicleId);

	/**
	 * 判断车辆是否绑定记帐卡
	 *
	 * @param carObuCardInfo
	 * @return
	 */
	public Boolean isVehicleBindAccountC(CarObuCardInfo carObuCardInfo);

	/**
	 * 判断车辆是否绑定记帐卡
	 *
	 * @param carObuCardInfo
	 * @return
	 */
	public Boolean isVehicleBindAccountC(Long vehicleId);

	public boolean hasApproval(Long vehicleId);

	public void saveApply(VehicleInfo oldVehicleInfo, VehicleInfo newVehicleInfo, String path);

	public void saveVehicleForAMMS(VehicleInfo vehicleInfo, Material material, String rootPath, List<File> imageFile, Customer customer, String[] tempPicNameList, VehicleBussiness vehicleBussiness, String clientSystem, VehicleImp vehicleImp, Map<String, Object> params);

	public void updateVehicleForAMMS(VehicleInfo vehicleInfo, VehicleInfo newvehicleInfo, Map<String, Object> params);

	public boolean hasVehicleImp(VehicleImp vehicleImp);

	public boolean hasVehicle(VehicleImp vehicleImp);

	public void saveBatchVehicle(List<VehicleImp> advanceList, ServiceFlowRecord serviceFlowRecord);

	public List<Map<String, Object>> findVehicleImpByPlateAndColor(String vehiclePlate, String vehicleColor);

	public boolean checkRequired(Long id);

	/**
	 * 由于图片处理方式修改了，新建一个接口做车辆所属人变更
	 *
	 * @param vehicleInfo
	 * @param customerId
	 * @param sysAdmin
	 * @param cusPointPoJo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> saveMigrateReturnMap(VehicleInfo vehicleInfo, Long customerId, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);

	/**
	 * 车辆被动挂起
	 *
	 * @param vehicleInfo
	 * @param vehicleBussiness
	 * @return Map<String,Object>
	 */
	public Map<String, Object> savePassiveStop(VehicleInfo vehicleInfo, VehicleBussiness vehicleBussiness, Map<String, Object> params);

	//	Pager listNotBindByPager(Pager pager, Long customerId);
	Pager listNotBindByPager(Pager pager, String vehiclePlate, String vehicleColor, Long customerId);

	//	Pager listNotBindByPagerForTag(Pager pager, String vehiclePlate, String vehicleColor, Long customerId);
	Pager listNotBindByPagerForTag(Pager pager, String vehiclePlate, String vehicleColor, Long customerId);

	public AppPointCtr findAppPointCtrByNo(String placeNo);

	public Map<String, Object> findAuthByVehicleAndNo(VehicleInfo vehicleInfo, String placeNo);

	boolean saveMigrateACMS(VehicleInfo vehicleInfo, String rootPath, Long customerId, SysAdmin sysAdmin,
	                        CusPointPoJo cusPointPoJo);

	/**
	 * 查询车辆信息（营改增->香港联营）
	 *
	 * @param pager
	 * 		分页
	 * @param customer
	 * 		客户
	 * @param vehicleInfo
	 * 		车辆信息
	 * @param cardHolder
	 * 		持卡人信息
	 * @return 分页数据
	 * @author wangjinhao
	 */
	Pager findByVehicleAndHolderACMS(Pager pager, Customer customer, VehicleInfo vehicleInfo,
	                                 CardHolder cardHolder);

}
