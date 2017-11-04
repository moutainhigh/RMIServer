/*package com.hgsoft.clearInterface.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hgsoft.accountC.dao.AccountCInfoDao;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.entity.UserInfoBaseList;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.dao.CustomerDao;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.dao.TagInfoDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.prepaidC.dao.PrepaidCDao;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.utils.UrlUtils;
@Component
public class UserInfoBaseListDao extends ClearBaseDao {
	@Autowired
	private UrlUtils urlUtils;
	
	@Resource
	private AccountCInfoDao accountCInfoDao;
	@Resource
	private TagInfoDao tagInfoDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private CustomerDao customerDao;
	@Resource
	private PrepaidCDao prepaidCDao;
	public void save(UserInfoBaseList userInfoBaseList) {
		StringBuffer sql=new StringBuffer("insert into "+urlUtils.getEtctolluser()+".CSMS_USERINFOBASELIST (");
		sql.append(FieldUtil.getFieldMap(UserInfoBaseList.class,userInfoBaseList).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(UserInfoBaseList.class,userInfoBaseList).get("valueStr")+")");
		save(sql.toString());
	}
	
	public void save(UserInfoBaseList userInfoBaseList,AccountCInfo accountCInfo,TagInfo tagInfo){
		VehicleInfo vehicleInfo=null;
		Customer customer=null;
		
		if(accountCInfo != null){
			accountCInfo=accountCInfoDao.findByCardNo(accountCInfo.getCardNo());
			userInfoBaseList.setCardCode(accountCInfo.getCardNo());
			userInfoBaseList.setCardIssueTime(accountCInfo.getStartDate());
			userInfoBaseList.setCardExpireTime(accountCInfo.getEndDate());
		}
		if(tagInfo !=null){
			tagInfo=tagInfoDao.findByTagNo(tagInfo.getTagNo());
			userInfoBaseList.setObuId(tagInfo.getTagNo());
			userInfoBaseList.setObuIssueTime(tagInfo.getStartTime());
			userInfoBaseList.setObuExpireTime(tagInfo.getEndTime());
		}
		
		
		if(accountCInfo !=null){
			vehicleInfo=vehicleInfoDao.findByAccountCNo(accountCInfo.getCardNo());
			customer=customerDao.findById(accountCInfo.getCustomerId());
			if(vehicleInfo !=null){
				userInfoBaseList.setModel(vehicleInfo.getModel());
				userInfoBaseList.setVehType(Integer.parseInt(vehicleInfo.getVehicleType()));
				userInfoBaseList.setLicense(vehicleInfo.getVehiclePlate());
				userInfoBaseList.setLicenseColor(vehicleInfo.getVehicleColor());
			}
		}else if(tagInfo !=null){
			vehicleInfo=vehicleInfoDao.findByTagNo(tagInfo.getTagNo());
			customer=customerDao.findById(tagInfo.getClientID());
			if(vehicleInfo !=null){
				userInfoBaseList.setModel(vehicleInfo.getModel());
				userInfoBaseList.setVehType(Integer.parseInt(vehicleInfo.getVehicleType()));
				userInfoBaseList.setLicense(vehicleInfo.getVehiclePlate());
				userInfoBaseList.setLicenseColor(vehicleInfo.getVehicleColor());
			}
		}
		
		userInfoBaseList.setUserType(Integer.parseInt(customer.getUserType()));
		userInfoBaseList.setUserName(customer.getOrgan());
		userInfoBaseList.setUserTel(customer.getMobile());
		
		Date date=new Date();
		userInfoBaseList.setCreationTime(date);
		userInfoBaseList.setGenTime(date);
		userInfoBaseList.setAlterFlag(0);
		userInfoBaseList.setBusinessTime(date);
		save(userInfoBaseList);
		
	}
	
	public void save(UserInfoBaseList userInfoBaseList,PrepaidC prepaidC){
		VehicleInfo vehicleInfo=null;
		Customer customer=null;
		
		if(prepaidC != null){
			prepaidC=prepaidCDao.findByPrepaidCNo(prepaidC.getCardNo());
			vehicleInfo=vehicleInfoDao.findByPrepaidCardNo(prepaidC.getCardNo());
			customer=customerDao.findById(prepaidC.getCustomerID());
			userInfoBaseList.setCardCode(prepaidC.getCardNo());
			userInfoBaseList.setCardIssueTime(prepaidC.getStartDate());
			userInfoBaseList.setCardExpireTime(prepaidC.getEndDate());
			if(vehicleInfo !=null){
				userInfoBaseList.setModel(vehicleInfo.getModel());
				userInfoBaseList.setVehType(Integer.parseInt(vehicleInfo.getVehicleType()));
				userInfoBaseList.setLicense(vehicleInfo.getVehiclePlate());
				userInfoBaseList.setLicenseColor(vehicleInfo.getVehicleColor());
			}
		}
		
		userInfoBaseList.setUserType(Integer.parseInt(customer.getUserType()));
		userInfoBaseList.setUserName(customer.getOrgan());
		userInfoBaseList.setUserTel(customer.getMobile());
		
		Date date=new Date();
		userInfoBaseList.setCreationTime(date);
		userInfoBaseList.setGenTime(date);
		userInfoBaseList.setAlterFlag(0);
		userInfoBaseList.setBusinessTime(date);
		save(userInfoBaseList);
		
	}
}
*/