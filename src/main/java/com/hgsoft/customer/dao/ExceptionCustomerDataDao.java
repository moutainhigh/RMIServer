package com.hgsoft.customer.dao;

import com.hgsoft.account.entity.MainAccountInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExceptionCustomerDataDao extends BaseDao{
	/**
	 * 查询客户信息表里面的未处理的异常客户数据
	 * @param searchCustomer
	 * @param searchVehicle
	 * @param cardNo
	 * @param tagNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findExceptionCustomerDatas(Customer searchCustomer, VehicleInfo searchVehicle,
			String cardNo, String tagNo) {
		//handleFlag ='0' 未处理 ；  exceptionMessage is not null 异常信息不为空
		StringBuffer sql = new StringBuffer(
				  " select id customerId,userNo,organ,idType,idCode,linkMan,mobile,exceptionMessage from csms_customer c "
				+ " where state='1'　and handleFlag ='0' and exceptionMessage is not null ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			//params.eq("cardNo", cardNo);
			params.getList().add(cardNo);
			params.getList().add(cardNo);
			sql.append(" and (c.id=(select customerid from csms_accountc_info where cardno=?) ");
			sql.append(" or c.id=(select customerid from csms_prepaidC where cardno=?)) ");
		}
		if(StringUtil.isNotBlank(tagNo)){
			//params.eq("tagNo", tagNo);
			params.getList().add(tagNo);
			sql.append(" and c.id=(select clientid from csms_tag_info  where tagNo=?) ");
		}
		if(searchVehicle != null){
			if(StringUtil.isNotBlank(searchVehicle.getVehicleColor()) && StringUtil.isNotBlank(searchVehicle.getVehiclePlate())){
				params.getList().add(searchVehicle.getVehicleColor());
				params.getList().add(searchVehicle.getVehiclePlate());
				sql.append(" and c.id=(select customerid from csms_vehicle_info where vehicleColor=? and vehiclePlate=?) ");
			}
		}
		if(searchCustomer != null){
			if(searchCustomer.getId() != null){
				params.getList().add(searchCustomer.getId());
				sql.append(" and c.id=? ");
			}
			if(StringUtil.isNotBlank(searchCustomer.getOrgan())){
				//params.eq("c.organ", searchCustomer.getOrgan());
				params.getList().add(searchCustomer.getOrgan());
				sql.append(" and c.organ=? ");
			}
			if(StringUtil.isNotBlank(searchCustomer.getIdType())){
				//params.eq("c.idType", searchCustomer.getIdType());
				params.getList().add(searchCustomer.getIdType());
				sql.append(" and c.idType=? ");
			}
			if(StringUtil.isNotBlank(searchCustomer.getIdCode())){
				//params.eq("c.idCode", searchCustomer.getIdCode());
				params.getList().add(searchCustomer.getIdCode());
				sql.append(" and c.idCode=? ");
			}
		}
		sql.append(" order by c.id desc ");
		return queryList(sql.toString(),params.getList().toArray());
	} 
	/**
	 * 查询产品列表
	 * @param customerId
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findProductInfoList(Long customerId){
		String sql = "select '记帐卡' productType,cardNo code,aa.bankAccount bankAccount from csms_accountc_info a"
				+ "   join CSMS_SubAccount_Info s on s.id=a.accountid "
				+ "   join csms_accountc_apply aa on aa.id=s.applyid where a.customerid=? "
				+ "     union all"
				+ "   select '储值卡' productType,cardNo code,'' bankAccount from csms_prepaidc where customerid=?"
				+ "     union all"
				+ "   select '电子标签' productType,tagNo code,'' bankAccount from csms_tag_info where clientID=?";
		return queryList(sql, customerId,customerId,customerId);
	}
	
	public List<Map<String, Object>> findVehicleList(Long customerId){
		String sql="select "+FieldUtil.getFieldMap(VehicleInfo.class,new VehicleInfo()).get("nameStr")+" from csms_vehicle_info where customerid=?";
		List<Map<String, Object>> list = queryList(sql,customerId);
		return list;
	}
	
	public void combineVehicleInfo(Customer existCustomer, Customer thisCustomer){
		String sql = "update csms_vehicle_info set customerId=? where customerId=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineSubAccountInfo(MainAccountInfo existMainAccountInfo,MainAccountInfo thisMainAccountInfo){
		String sql = "update CSMS_SubAccount_Info set MainID=? where MainID=?";
		saveOrUpdate(sql, existMainAccountInfo.getId(),thisMainAccountInfo.getId());
	}
	
	public void combinePrepaidC(Customer existCustomer, Customer thisCustomer){
		String sql = "update csms_prepaidc set customerId=? where customerId=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineAccountCInfo(Customer existCustomer, Customer thisCustomer){
		String sql = "update csms_accountc_info set customerId=? where customerId=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineAccountCApply(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_AccountC_apply set customerId=? where customerId=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineTagInfo(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_Tag_info set ClientID=? where ClientID=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineAddReg(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_add_reg set UserID=? where UserID=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineBankTransferInfo(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_BankTransfer_Info set MainID=? where MainID=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineRefundInfo(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_RefundInfo set MainID=? where MainID=? and AuditStatus in('0','1','2','3','5','7','9','10')";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combineSpecialList(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_Special_list set customerId=? where customerId=?";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	//保证金设置表
	public void combineBail(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_bail set userNo=? where userNo=?";
		saveOrUpdate(sql, existCustomer.getUserNo(),thisCustomer.getUserNo());
	}
	
	public void combineTagMainRecord(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_TagMain_Record set ClientID=? where ClientID=? and MaintainType='3' and BackToCustomerTime is null";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
	
	public void combinePrepaidCBussiness(Customer existCustomer, Customer thisCustomer){
		String sql = "update CSMS_PrePaidC_bussiness set userID=? where userID=? and State in('2','4','19','20') and tradeState='1' ";
		saveOrUpdate(sql, existCustomer.getId(),thisCustomer.getId());
	}
}
