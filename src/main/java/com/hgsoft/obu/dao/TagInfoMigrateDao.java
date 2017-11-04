package com.hgsoft.obu.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class TagInfoMigrateDao extends BaseDao {

	@Resource
	SequenceUtil sequenceUtil;
	
	public String findBindByTagNo(String tagNo){
		String sql = "select * from csms_carobucard_info where tagid = (select id from csms_tag_info where tagno=?)";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		String bind = "0";
		if(list.size()>0){
			Map<String,Object> map = list.get(0);
			if(map.get("accountCID")!=null)
				bind="1";
		}
		return bind;
	}
	
	public TagInfo findTagInfoByTagNo(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_tag_info where tagNo=?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		TagInfo tagInfo = null;
		if(list.size()>0){
			tagInfo = new TagInfo();
			return (TagInfo) convert2Bean(list.get(0), tagInfo);
		}
		return tagInfo;
	}

	public Map<String,Object> getCarObuCardInfo(String vehiclePlate,String vehicleColor){
		String sql = "select coc.* from csms_carobucard_info coc join csms_vehicle_info vi on coc.vehicleid=vi.id where vi.vehicleplate=? and vi.vehiclecolor=?";
		List<Map<String,Object>> list = queryList(sql,vehiclePlate,vehicleColor);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public MacaoCardCustomer getMacaoCardCustomer(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select mcc.* from csms_macao_card_customer mcc join csms_cardholder_info ci on ci.macaocarcustomerid=mcc.id join csms_tag_info ti on ci.typeid=ti.id where ci.type='3' and ti.tagno=?";
		List<Map<String,Object>> list = queryList(sql,tagNo);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			return (MacaoCardCustomer)convert2Bean(list.get(0), macaoCardCustomer);
		}
		return macaoCardCustomer;
	}
	
	public Pager tagInfoList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6) {
		StringBuffer sql = new StringBuffer("select t.id,t.tagNo,t.issuetime,TagState,t.maintenancetime,v.id vid,v.vehicleColor,v.vehiclePlate,ROWNUM as num "
				+ "from csms_tag_info t left join "
				+ "CSMS_CarObuCard_info ca on t.id = ca.TagID left join "
				+ "csms_vehicle_info v on ca.VehicleID = v.id  "
				+ " where 1=1 and (t.tagState=1 or t.tagState=2) and t.IssueType !=3  ");
		SqlParamer params=new SqlParamer();
		if(tagInfo!=null){
			if(StringUtil.isNotBlank(tagInfo.getTagNo())){
				params.eq("t.tagNo", tagInfo.getTagNo());
			}
		}
		//标签状态为停用，放开限制注释掉
//		if(StringUtil.isNotBlank(tagInfo.getTagState())){
//			params.eq("t.tagState", tagInfo.getTagState());
//		}
		//根据客户的id找到对应的标签,这个是必需的，要通过身份验证
		if(customer.getId()!=null){
			params.eq("t.ClientID", customer.getId());
		}
		if(vehicleInfo != null){
			//以下条件页面已经注释
			if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
				params.eq("v.vehicleColor", vehicleInfo.getVehicleColor());
			}
			if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
				params.eq("v.vehiclePlate", vehicleInfo.getVehiclePlate());
			}
		}
		if(StringUtil.isNotBlank(identificationCode6)){
			params.like("v.identificationCode", identificationCode6);
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by t.Issuetime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public Pager tagInfoListForAMMS(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6,String bankCode) {
		String sql = " select allCard.* from (select (case"
					+" when coc.prepaidcId is null and coc.accountcId is not null then a.cardNo"
					+" when coc.prepaidcId is not null and coc.accountcId is null then p.cardNo"
					+" when coc.prepaidcId is null and coc.accountcId is null then null end) allCardNo,"
					+" t.tagNo tagNo,v.identificationCode identificationCode,t.ClientID ClientID,t.id,t.issuetime issuetime,t.tagState tagState,t.maintenanceTime,v.id vid,v.vehicleColor vehicleColor,v.vehiclePlate vehiclePlate"
					+" from csms_tag_info t"
					+" left join csms_carObuCard_info coc on coc.tagId = t.id"
					+" left join csms_vehicle_info v on coc.vehicleId = v.id"
					+" left join csms_accountc_info a on  coc.accountcId = a.id"
					+" left join csms_prepaidc p on coc.prepaidcId = p.id where (t.tagState = 1 or t.tagState = 2) and t.issueType != 3 and t.clientId=?) allCard"
					+" left join csms_joinCardNoSection j  on substr(allCard.allCardNo, 0, length(allCard.allCardNo) - 1) between j.code and j.endcode"
					+" where (allCard.allCardNo  is null or j.bankno = ?)";
		
		List<String> params = new ArrayList<String>();
		params.add(customer.getId().toString());
		params.add(bankCode);
		
		
		if(tagInfo!=null){
			String tagNo = tagInfo.getTagNo();
			if(StringUtil.isNotBlank(tagNo)){
				params.add(tagNo);
				sql = sql + " and allCard.tagNo=?";
			}
		}
		
		if(vehicleInfo != null){
			String vehicleColor = vehicleInfo.getVehicleColor();
			if(StringUtil.isNotBlank(vehicleColor)){
				params.add(vehicleColor);
				sql = sql + " and allCard.vehicleColor=?";
			}
			String vehiclePlate = vehicleInfo.getVehiclePlate();
			if(StringUtil.isNotBlank(vehiclePlate)){
				params.add(vehiclePlate);
				sql = sql + " and allCard.vehiclePlate=?";
			}
		}
		if(StringUtil.isNotBlank(identificationCode6)){
			params.add(identificationCode6);
			sql = sql + " and substr(allCard.IdentificationCode,-6)=?";
		}
		
		sql = sql + " order by allCard.issuetime desc";
		return this.findByPages(sql.toString(), pager,params.toArray());
	}
	
	public Pager tagInfoList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6,MacaoCardCustomer macaoCustomer) {
		StringBuffer sql = new StringBuffer("select t.id,t.tagNo,t.issuetime,TagState,t.maintenancetime,v.id vid,v.vehicleColor,v.vehiclePlate,ROWNUM as num "
				+ "from csms_tag_info t left join "
				+ "CSMS_CarObuCard_info ca on t.id = ca.TagID left join "
				+ "csms_vehicle_info v on ca.VehicleID = v.id join csms_cardholder_info ci on ci.typeid=t.id join csms_macao_bankaccount mb on ci.macaobankaccountid=mb.id join csms_macao_card_customer mcc on mb.mainid=mcc.id "
				+ " where 1=1 and (t.tagState=1 or t.tagState=2) and t.IssueType !=3  ");
		SqlParamer params=new SqlParamer();
		if(macaoCustomer != null){
			params.eq("mcc.id", macaoCustomer.getId());
		}
		if(tagInfo!=null){
			if(StringUtil.isNotBlank(tagInfo.getTagNo())){
				params.eq("t.tagNo", tagInfo.getTagNo());
			}
		}
		//标签状态为停用，放开限制注释掉
//		if(StringUtil.isNotBlank(tagInfo.getTagState())){
//			params.eq("t.tagState", tagInfo.getTagState());
//		}
		//根据客户的id找到对应的标签,这个是必需的，要通过身份验证
		if(customer.getId()!=null){
			params.eq("t.ClientID", customer.getId());
		}
		if(vehicleInfo != null){
			//以下条件页面已经注释
			if(StringUtil.isNotBlank(vehicleInfo.getVehicleColor())){
				params.eq("v.vehicleColor", vehicleInfo.getVehicleColor());
			}
			if(StringUtil.isNotBlank(vehicleInfo.getVehiclePlate())){
				params.eq("v.vehiclePlate", vehicleInfo.getVehiclePlate());
			}
		}
		if(StringUtil.isNotBlank(identificationCode6)){
			params.like("v.identificationCode", identificationCode6);
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by t.Issuetime desc ");
		return this.findByPages(sql.toString(), pager,Objects);
	}

	public Map<String, Object> tagInfoDetail(TagInfo tagInfo, Long clientID, Long vehicleID) {
//		StringBuffer sql = new StringBuffer("select c.id ClientID,c.organ,c.idType,c.idCode,v.*,t.id tagId,t.tagNo,t.chargeCost,t.issuetime,t.MaintenanceTime,t.StartTime,t.EndTime from "
//				+ "csms_tag_info t "
//				+ "join CSMS_CarObuCard_info ca on t.id = ca.TagID "
//				+ "join csms_vehicle_info v on ca.vehicleid=v.id "
//				+ "join csms_customer c on t.Clientid=c.id where 1=1");
		StringBuffer sql = new StringBuffer("select c.organ,t.* from csms_tag_info t join csms_customer c on t.clientid=c.id where 1=1 ");
		SqlParamer params=new SqlParamer();
//		if(clientID!=null){
//			params.eq("c.id", clientID);
//		}
//		if(vehicleID!=null){
//			params.eq("v.id", vehicleID);
//		}
		if(StringUtil.isNotBlank(tagInfo.getTagNo())){
			params.eq("t.tagNo", tagInfo.getTagNo());
		}
		sql=sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		return jdbcUtil.getJdbcTemplate().queryForMap(sql.toString(),Objects);
	}
	
	public void saveTagInfoHis(TagInfo tagInfo, TagInfoHis tagTnfoHis) {
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_TAG_INFOHIS( "
				+ "ID,TagNo,ClientID,IssueType,SalesType,"
				+ "Installman,ChargeCost,OperID,IssueplaceID,Issuetime,MaintenanceTime,StartTime,EndTime,TagState,HisSeqID,CreateDate,CreateReason)  "
				+ "SELECT "+tagTnfoHis.getId()+",TagNo,ClientID,IssueType,SalesType,"
				+ "Installman,ChargeCost,OperID,IssueplaceID,Issuetime,MaintenanceTime,StartTime,EndTime,TagState,HisSeqID," 
				+ "sysdate,"+ tagTnfoHis.getCreateReason()+" FROM CSMS_TAG_INFO WHERE TagNo=?");
		this.jdbcUtil.getJdbcTemplate().update(sql.toString(),tagInfo.getTagNo());
	}

	public void update(TagInfo tagInfo, Long clientID, Long vehicleID) {
		//更新电子标签信息表的clientID
		StringBuffer sql_1=new StringBuffer("update CSMS_TAG_INFO set ");
		sql_1.append("TagState=1 ,blackflag=?, clientID=?,WriteBackFlag='0' where tagNo=?");
//		this.jdbcUtil.getJdbcTemplate().update(sql_1.toString(),BlackFlagEnum.unblack.getValue(),clientID,tagInfo.getTagNo());
		saveOrUpdate(sql_1.toString(),BlackFlagEnum.unblack.getValue(),clientID,tagInfo.getTagNo());
		//更新车卡标签绑定表的vehicleID
		/*StringBuffer sql_2=new StringBuffer("update CSMS_CarObuCard_info set ");
//		sql_2.append("vehicleID=" +vehicleID+" where TagID="+tagInfo.getId());
		sql_2.append("TagID=" +tagInfo.getId()+" where vehicleID="+vehicleID);
		update(sql_2.toString());*/
	}

	public Pager findVehicleByOrgan(Customer customer , Pager pager) {
		StringBuffer sql = new StringBuffer("select v.*,ROWNUM as num "
				+ "from csms_vehicle_info v join csms_customer cus on v.customerid=cus.id left join CSMS_CarObuCard_info cci on v.id=cci.vehicleid   where cci.tagid is null and cus.state ='1' ");
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(customer.getIdType())){
			params.eq("cus.IdType", customer.getIdType());
		}
		if(StringUtil.isNotBlank(customer.getIdCode())){
			params.eq("cus.IdCode", customer.getIdCode());
		}
		sql.append(params.getParam());
		Object[] Objects= params.getList().toArray();
		sql.append(" order by v.id ");
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
//	public void saveTagBusinessRecord(Long tagInfoId, String tagNo, Long clientID,
//			Long vehicleID,Long installmanID, String memo) {
//		String sqlString = null;
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
//		Long id = Long.valueOf(SEQ_CSMSTagBusinessRecord_NO.toString());
//		
//		StringBuffer sql = new StringBuffer("insert into "
//				+ "CSMS_Tag_BusinessRecord(ID,tagNo,clientID,vehicleID,"
//				+ "OperID,OperTime,OperplaceID,BusinessType,InstallmanID,"
//				+ "CurrentTagState,ImportCardNo,WriteState,Memo,FromID) values(");
//		
//		if(id == null){
//			sql.append("NULL,");
//		}else{
//			sql.append(id + ",");
//		}
//		if(tagNo == null){
//			sql.append("NULL,");
//		}else{
//			sql.append("'" + tagNo + "',");
//		}
//		if(clientID == null){
//			sql.append("NULL,");
//		}else{
//			sql.append(clientID + ",");
//		}
//		if(vehicleID == null){
//			sql.append("NULL,");
//		}else{
//			sql.append(vehicleID + ",");
//		}
//		//操作人、操作时间、操作网点
//		sql.append("1,");
//		sql.append("sysdate,");
//		sql.append("1,");
//		//业务原因是迁移
//		sql.append("6,");
//		//CurrentTagState
//		sql.append("1,");
//		//ImportCardNo
//		sql.append("'1',");
//		//WriteState
//		sql.append("'1',");
//		
//		if(installmanID == null){
//			sql.append("NULL,");
//		}else{
//			sql.append(installmanID + ",");
//		}
//		
//		if(memo == null){
//			sql.append("NULL,");
//		}else{
//			sql.append("'"+memo + "',");
//		}
//		if(tagInfoId == null){
//			sql.append("NULL,");
//		}else{
//			sql.append(tagInfoId + ",");
//		}
//		
//		
//		
//		if (sql.toString().endsWith(",")) {
//			sqlString = sql.substring(0, sql.length() - 1);
//		}
//		sqlString += ")";
//		save(sqlString);
//		
//	}
}
