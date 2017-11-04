package com.hgsoft.other.dao;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.account.entity.BankTransferInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.Material;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.customer.entity.VehicleInfoHis;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.prepaidC.entity.PrepaidC;
import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.prepaidC.entity.PrepaidCHis;
import com.hgsoft.prepaidC.entity.PrepaidCTransfer;
import com.hgsoft.prepaidC.entity.PrepaidCTransferDetail;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class EmboitementTransferDao extends BaseDao {
		
	public Pager findByPlateAndColor(Pager pager, Customer customer, String plate, String color, String cardno, String tagno) {
		StringBuffer sql = new StringBuffer("select v.ID,v.customerID,v.vehiclePlate,v.vehicleColor,v.vehicleUserType,v.UsingNature,v.IdentificationCode,"
				+ "v.VehicleType,v.vehicleWheels,v.vehicleAxles,v.vehicleWheelBases,v.vehicleWeightLimits,v.vehicleSpecificInformation,v.vehicleEngineNo,"
				+ "v.vehicleWidth,v.vehicleLong,v.vehicleHeight,v.vehicleHeadH,v.NSCvehicletype,v.owner,v.Model,v.OperID,v.PlaceID,v.createTime,v.HisSeqID,"
				+ "v.IsWriteOBU,v.IsWriteCard,pre.cardno as precardno,tag.tagno,ROWNUM as num ");
		sql.append(" from CSMS_Vehicle_Info v left join csms_carobucard_info cbd on v.id=cbd.vehicleid left join csms_tag_info tag on tag.id=cbd.tagid ");
		sql.append(" left join csms_prepaidc pre on pre.id=cbd.prepaidcid where v.customerid="
		+customer.getId() +"and (pre.cardno is not null or pre.cardno<>'') and (tag.tagno is not null or tag.tagno<>'')");
		SqlParamer sqlPar=new SqlParamer();
		if(StringUtil.isNotBlank(plate)){
			sqlPar.eq("v.vehicleplate", plate);
		}
		if(StringUtil.isNotBlank(color)){
			sqlPar.eq("v.vehiclecolor", color);
		}
		if(StringUtil.isNotBlank(cardno)){
			sqlPar.eq("pre.cardno", cardno);
		}
		if(StringUtil.isNotBlank(tagno)){
			sqlPar.eq("tag.tagno", tagno);
		}
		sql=sql.append(sqlPar.getParam());
		sql.append(" order by v.ID desc");
		return super.findByPages(sql.toString(), pager,sqlPar.getList().toArray());
	}

	//更新车辆信息表
	public void update(VehicleInfo vehicleInfo) {
		
		StringBuffer sql=new StringBuffer("update CSMS_Vehicle_Info set customerID=?,hisSeqId=?  where id = ? ");
		saveOrUpdate(sql.toString(), vehicleInfo.getCustomerID(),vehicleInfo.getHisSeqId(),vehicleInfo.getId());
	}
	
	//更新电子标签发行信息表
	public void update(TagInfo tagInfo) {
		StringBuffer sql=new StringBuffer("update CSMS_TAG_INFO set clientID=?,hisSeqId=?  where id = ? ");
		saveOrUpdate(sql.toString(), tagInfo.getClientID(),tagInfo.getHisSeqID(),tagInfo.getId());
	}

	//更新储值卡信息表
	public void update(PrepaidC prepaidC) {
		StringBuffer sql=new StringBuffer("update CSMS_PrePaidC set customerID=?,hisSeqId=?  where id = ? ");
		saveOrUpdate(sql.toString(), prepaidC.getCustomerID(),prepaidC.getHisSeqID(),prepaidC.getId());
	}
	
	//更新服务方式登记
	public void update(BillGet billGet) {
		StringBuffer sql=new StringBuffer("update csms_bill_get set mainId=?,hisSeqId=?  where id = ? ");
		saveOrUpdate(sql.toString(), billGet.getMainId(),billGet.getHisSeqId(),billGet.getId());
	}
	

}
