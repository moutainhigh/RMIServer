package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.system.entity.ParamConfig;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;


@Repository
public class TagIssueInfoDao extends BaseDao{
	
	//获取 电子标签业务操作记录表 除发行和发行删除记录之外的数据
	public boolean checkDelete(String tagNo){
		String sql = "select * from CSMS_Tag_BusinessRecord where BusinessType not in  (1,8) and tagNo=?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		if(list.size()>0)
			return true;
		return false;
	}
	
	public Map<String,Object> getCustomerInfo(String bankAccountNumber,String idCardType,String idCardNumber){
		String sql = "select mcc.cnname cnname,mcc.enname enname,mcc.usertype usertype from CSMS_MACAO_CARD_CUSTOMER mcc join csms_macao_bankaccount mb on mcc.id=mb.mainid where mb.bankAccountNumber=? and mcc.idCardType=? and mcc.idCardNumber=?";
		List<Map<String,Object>> list = queryList(sql, bankAccountNumber,idCardType,idCardNumber);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Pager getVehicleAndTag(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,String issuetime,MacaoCardCustomer macaoCardCustomer){
		String tagNo="";
		String operName="";
		String vehiclePlate="";
		String vehicleColor="";
		
		if(tagInfo!=null){
			tagNo = tagInfo.getTagNo();
			operName = tagInfo.getOperName();
		}
		if(vehicleInfo!=null){
			vehiclePlate = vehicleInfo.getVehiclePlate();
			vehicleColor = vehicleInfo.getVehicleColor();
		}
		
		StringBuffer sql = new StringBuffer(" select ti.id tagId,"
				   						   +" vi.vehiclePlate,"
				   						   +" c.id customerId,"
				   						   +" vi.id vehicleId,"
										   +" vi.vehicleColor,"
										   +" vi.Model,"
										   +" vi.vehicleType,"
										   +" vi.vehicleWeightLimits,"
										   +" vi.NSCvehicletype,"
										   +" vi.vehicleSpecificInformation,"
										   +" vi.vehicleLong,"
										   +" vi.vehicleWidth,"
										   +" vi.vehicleHeight,"
										   +" vi.vehicleAxles,"
										   +" vi.vehicleWheels,"
										   +" vi.vehicleEngineNo,"
										   +" vi.usingNature,"
										   +" vi.identificationCode,"
										   +" vi.vehicleWheelBases,"
										   +" vi.Owner,"
										   +" ti.tagno,"
										   +" ti.Installman,"
										   +" ti.chargeCost/100 chargeCost,"
										   +" ti.operNo,"
										   +" ti.operName,"
										   +" ti.placeNo,"
										   +" ti.Issuetime,"
										   +" ti.maintenanceTime,"
										   +" ti.startTime,"
										   +" ti.endTime,"
										   +" ti.correctTime"
										   +" from CSMS_Vehicle_Info vi"
										   +" join CSMS_CarObuCard_info ci"
										   +" on vi.id = ci.VehicleID"
										   +" join CSMS_Tag_info ti"
										   +" on ci.TagID = ti.id "
										   + "join csms_customer c on ti.clientid=c.id join csms_cardholder_info ch on ch.typeid=ti.id join csms_macao_bankaccount mb on mb.id=ch.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid where 1=1");
		SqlParamer sqlPar=new SqlParamer();
		if(macaoCardCustomer != null){
			sqlPar.eq("mcc.id", macaoCardCustomer.getId());
		}
		if(StringUtil.isNotBlank(tagNo)){
			sqlPar.eq("ti.tagNo", tagNo);
		}
		if(StringUtil.isNotBlank(operName)){
			sqlPar.eq("ti.operName", operName);
		}
		if(StringUtil.isNotBlank(issuetime)){
			sqlPar.eq("to_char(ti.issuetime,'yyyy/MM/dd')", issuetime);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			sqlPar.eq("vi.vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			sqlPar.eq("vi.vehicleColor", vehicleColor);
		}
		sql=sql.append(sqlPar.getParam());
		sql=sql.append("order by ti.Issuetime desc");
		return findByPages(sql.toString(), pager,sqlPar.getList().toArray());
	}
	
	public Map<String,Object> getVehicleAndTagById(String tagId){
		
		String sql = "select mcc.cnname cnName,mcc.enname enName,vi.*,ti.*"
					+"  from csms_macao_card_customer mcc"
					+"  join csms_macao_bankaccount mb"
					+"    on mcc.id = mb.mainid"
					+"  join csms_cardholder_info ci"
					+"    on ci.macaobankaccountid = mb.id"
					+"  join csms_tag_info ti"
					+"    on ti.id = ci.typeid"
					+"  join csms_carobucard_info coc"
					+"    on coc.tagid = ti.id"
					+"  join csms_vehicle_info vi"
					+"    on vi.id = coc.vehicleid"
					+" where ci.type = '3'"
					+"   and ti.id = ?";
		List<Map<String,Object>> list = queryList(sql, tagId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public TagTakeDetail tagTakeDetailFindByTagNo(String tagNo){
		
		String sql="select * from CSMS_TagTake_Detail where TagNo=?";
		List list=queryList(sql, tagNo);
		TagTakeDetail tagTakeDetail = null;
		if(list!=null && list.size()!=0) {
			tagTakeDetail = (TagTakeDetail) this.convert2Bean((Map<String, Object>) list.get(0), new TagTakeDetail());
		}

		return tagTakeDetail;
	}
	
	public TagInfo tagInfoFindByTagNo(String tagNo){
		String sql="select ID,TagNo,ClientID,"
				+ "IssueType,SalesType,Installman,ChargeCost,OperID"
				+ ",IssueplaceID,Issuetime,MaintenanceTime,StartTime,EndTime,"
				+ "TagState,HisSeqID,CorrectOperID,correctTime,correctPlaceID,obuserial,IsWriteOBU from CSMS_Tag_info where TagNo=?";
		List list=queryList(sql, tagNo);
		TagInfo tagInfo = null;
		if(list!=null && list.size()!=0) {
			tagInfo = (TagInfo) this.convert2Bean((Map<String, Object>) list.get(0), new TagInfo());
		}

		return tagInfo;
	}
	
	public List<ParamConfig> findByparamNoAndBankNo(String paramNo,String bankNo){
		String sql="select * from CSMS_param_config where paramNo=? and paramValueTwice = ? and state = 1";
		List<Map<String, Object>> l = queryList(sql, paramNo,bankNo);
		ParamConfig temp = null;
		List<ParamConfig> list=new ArrayList<ParamConfig>();
		if (!l.isEmpty()&&l.size()>0) {
			for(int i=0;i<l.size();i++){
				temp = new ParamConfig();
				this.convert2Bean(l.get(i), temp);
				list.add(temp);
			}

		}

		return list;
	}
	
	public List<Map<String, Object>> getAllVehByCusId(String idCardType,String idCardNumber){
		
		/**
		 * 根据需求，界面上去掉了银行账号
		 * String sql ="select vi.*,ci.* from CSMS_Vehicle_Info vi join csms_cardholder_info ci on vi.id=ci.typeid join CSMS_MACAO_BANKACCOUNT mb on ci.macaobankaccountid=mb.id join csms_carobucard_info coc on vi.id=coc.vehicleid join csms_macao_card_customer mcc on mcc.id=mb.mainid where coc.tagid is null and mb.bankAccountNumber=? and mcc.idCardType=? and mcc.idCardNumber=?";
		 */
		String sql ="select vi.*,ci.* from CSMS_Vehicle_Info vi join csms_cardholder_info ci on vi.id=ci.typeid join CSMS_MACAO_BANKACCOUNT mb on ci.macaobankaccountid=mb.id join csms_carobucard_info coc on vi.id=coc.vehicleid join csms_macao_card_customer mcc on mcc.id=mb.mainid where coc.tagid is null and mcc.idCardType=? and mcc.idCardNumber=?";
		List<Map<String, Object>> list = queryList(sql,idCardType,idCardNumber);
		return list;
	}
	
	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo,Long customerId){
		String sql="select * from csms_vehicle_info  where "
				+ " vehiclePlate=? and vehicleColor=? and customerId=?";
		List<Map<String, Object>> list = queryList(sql,new Object[]{vehicleInfo.getVehiclePlate(),vehicleInfo.getVehicleColor(),customerId});
		if (!list.isEmpty()) {
			vehicleInfo = (VehicleInfo) this.convert2Bean((Map<String, Object>) list.get(0), new VehicleInfo());
		}else{
			return null;
		}

		return vehicleInfo;
	}
	public VehicleInfo findByVehicleInfoForMacao(VehicleInfo vehicleInfo,Long customerId){
		String sql="select vi.* from csms_vehicle_info vi join csms_cardholder_info ci on vi.id=ci.typeid join csms_macao_bankaccount mb on mb.id = ci.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid where "
				+ " vi.vehiclePlate=? and vi.vehicleColor=? and mcc.Id=?";
		List<Map<String, Object>> list = queryList(sql,new Object[]{vehicleInfo.getVehiclePlate(),vehicleInfo.getVehicleColor(),customerId});
		if (!list.isEmpty()) {
			vehicleInfo = (VehicleInfo) this.convert2Bean((Map<String, Object>) list.get(0), new VehicleInfo());
		}else{
			return null;
		}

		return vehicleInfo;
	}
	
	public TagInfo findByVehicleInfoId(Long id){
		String sql="select ID,TagNo,ClientID,"
				+ "IssueType,SalesType,Installman,ChargeCost,OperID"
				+ ",IssueplaceID,Issuetime,MaintenanceTime,StartTime,EndTime,"
				+ "TagState,HisSeqID,CorrectOperID,correctTime,correctPlaceID from CSMS_Tag_info t join csms_carobucard_info c on t.id=c.TagID  where c.VehicleID=?";
		List list=queryList(sql, id);
		TagInfo tagInfo = null;
		if(list!=null && list.size()!=0) {
			tagInfo = (TagInfo) this.convert2Bean((Map<String, Object>) list.get(0), new TagInfo());
		}

		return tagInfo;
	}
	
	public boolean find(String license, String licenseColor) {
		String sql="select * from TB_GBISSUEUSERLIST where LICENSE='"+license+"' and LICENSECOLOR='"+licenseColor+"'";
		List<Map<String, Object>> list = queryList(sql.toString());
		if (list.isEmpty()|| list.size()==0) {
			return false;
		}
		return true;
	}
	
	
	public VehicleInfo findById(Long id) {
		String sql = "select * from CSMS_Vehicle_Info where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		VehicleInfo vehicleInfo = null;
		if (!list.isEmpty()) {
			vehicleInfo = new VehicleInfo();
			this.convert2Bean(list.get(0), vehicleInfo);
		}

		return vehicleInfo;
	}
	
	/*
	 * 根据数据tagNo查找对应对象
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> findByTagNo(String tagNo) {
		String sql = "select i.*,d.obuSerial,d.startTime,d.endTime from csms_tagtake_detail d join csms_tagtake_info i on d.mainid=i.id where d.tagno=?";
		return   queryList(sql, tagNo);
	}
	
	public void updateCarObuCardInfo(String viid,String tiid){
		String sql = "update csms_carusercard_info set TagID=? where VehicleID=?";
		saveOrUpdate(sql, tiid,viid);
	}
	public void updateCarObuCardInfoByTagId(String tiid){
		String sql = "update csms_carusercard_info set TagID='' where TagID=?";
		saveOrUpdate(sql,tiid);
	}
	
	public void updateIsWriteObu(Long id) {
		String sql = "update csms_tag_info set ISWRITEOBU=2 where id="+id+"";
		update(sql);
	}
	
	public TagInfo findByTagId(Long id){
		String sql="select ID,TagNo,ClientID,IssueType,SalesType,Installman," +
				"ChargeCost,OperID,IssueplaceID,Issuetime,MaintenanceTime," +
				"StartTime,EndTime,TagState,HisSeqID,CorrectOperID,correctTime," +
				"correctPlaceID,IsDaySet,SettleDay,SettletTime,operNo,operName," +
				"placeNo,placeName,CorrectOperNo,CorrectOperName,correctPlaceNo," +
				"correctPlaceName,WriteBackFlag,productType,Cost,InstallmanName," +
				"blackFlag,obuserial,IsWriteOBU from CSMS_Tag_info where id=?";
		List list=queryList(sql, id);
		TagInfo tagInfo = null;
		if(list!=null && list.size()!=0) {
			tagInfo = (TagInfo) this.convert2Bean((Map<String, Object>) list.get(0), new TagInfo());
		}

		return tagInfo;
	}
	
	public String getIdByBankaccountNumber(String bankaccountnumber){
		String sql = "select id from CSMS_MACAO_CARD_CUSTOMER where Bankaccountnumber=?";
		List<Map<String,Object>> list=queryList(sql, bankaccountnumber);
		if(list.size()>0){
			return list.get(0).get("ID").toString();
		}
		return null;
	}
	
	public void deleteById(Long typeId){
		String sql = "delete from csms_cardholder_info where typeId=?";
		delete(sql,typeId);
	}
	
	public Map<String,Object> getCarObuCardInfoByVehId(String vehicleId){
		String sql = "select * from CSMS_CarObuCard_info where vehicleid=?";
		List<Map<String,Object>> list = queryList(sql, vehicleId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据电子标签号获取持卡人信息
	 * */
	public Map<String,Object> getMacaoCardCustomerByTagNo(String tagNo) {
		String sql = "select mcc.id id,mcc.bankAccountNumber bankAccountNumber from csms_macao_card_customer mcc join csms_cardholder_info ci on ci.macaocarcustomerid=mcc.id join csms_tag_info ti on ti.id=ci.typeid where ci.type='3' and ti.tagno='"+tagNo+"'";
//		System.out.println(sql);
		List<Map<String,Object>> list = queryList(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 电子标签发行时获取客户信息
	 * @param vehicleInfo
	 * @return
	 * @return Map<String,Object>
	 * @author lgm
	 * @date 2017年1月20日
	 */
	public Map<String,Object> getMacaoCustomerInfoByVehicleInfo(VehicleInfo vehicleInfo){
		String sql = "select mcc.idCardType idCardType,mcc.idCardNumber idCardNumber,mcc.enName enName,mcc.cnName cnName,mcc.userType userType from csms_macao_card_customer mcc join csms_macao_bankaccount mb on mb.mainid=mcc.id join csms_cardholder_info ci on ci.macaobankaccountid=mb.id join csms_vehicle_info vi on vi.id=ci.typeid where 1=1 and vi.vehicleplate=? and vi.vehiclecolor=?";
		List<Map<String,Object>> list = queryList(sql,vehicleInfo.getVehiclePlate(),vehicleInfo.getVehicleColor());
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * 电子标签发行时获取客户信息
	 * @param macaoCardCustomer
	 * @return
	 * @return Map<String,Object>
	 * @author lgm
	 * @date 2017年1月20日
	 */
	public Map<String,Object> getMacaoCustomerInfoByMacaoCardCustomer(MacaoCardCustomer macaoCardCustomer){
		String sql = "select mcc.idCardType idCardType,mcc.idCardNumber idCardNumber,mcc.enName enName,mcc.cnName cnName,mcc.userType userType from csms_macao_card_customer mcc  where mcc.id=?";
		List<Map<String,Object>> list = queryList(sql,macaoCardCustomer.getId());
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	public boolean getMacaoBankAccount(String bankAccountNumber,String idCardType,String idCardNumber){
		String sql = "select mb.* from csms_macao_bankaccount mb join csms_macao_card_customer mcc on mb.mainid=mcc.id where mb.bankaccountnumber=? and mcc.idcardtype=? and mcc.idcardnumber=? ";
		List<Map<String,Object>> list = queryList(sql,bankAccountNumber,idCardType,idCardNumber);
		if(list.size()>0){
			return true;
		}
		return false;
	}
}
