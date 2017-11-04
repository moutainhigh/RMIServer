package com.hgsoft.acms.obu.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Component
public class TagReplaceDaoACMS extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	
	public String findBindByTagNo(String oldTagNo){
		String sql = "select * from csms_carobucard_info where tagid = (select id from csms_tag_info where tagno=?)";
		List<Map<String,Object>> list = queryList(sql,oldTagNo);
		String bind = "0";
		if(list.size()>0){
			Map<String,Object> map = list.get(0);
			if(map.get("accountCID")!=null)
				bind="1";
		}
		return bind;
	}
	
	public TagInfo getTagInfoByTagNo(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "select * from csms_tag_info where tagno=?";
		List<Map<String,Object>> list = queryList(sql, tagNo);
		TagInfo tagInfo = null;
		if(list.size()>0){
			tagInfo = new TagInfo();
			return (TagInfo) convert2Bean(list.get(0), tagInfo);
		}
		return null;
	}
	
	public MacaoCardCustomer getMacaoCardCustomer(String tagNo) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException{
		String sql = "SELECT mcc.* from csms_cardholder_info ci "
				+ " JOIN csms_macao_bankaccount mb ON ci.MACAOBANKACCOUNTID=mb.id "
				+ " JOIN csms_macao_card_customer mcc ON mb.mainid=mcc.id "
				+ " join csms_tag_info ti on ci.typeid=ti.id where ci.type='3' and ti.tagno=?";
		List<Map<String,Object>> list = queryList(sql, tagNo);
		MacaoCardCustomer macaoCardCustomer = null;
		if(list.size()>0){
			macaoCardCustomer = new MacaoCardCustomer();
			return (MacaoCardCustomer) convert2Bean(list.get(0), macaoCardCustomer);
		}
		return null;
	}
	
	public List findTagReplaces(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID){
		//
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id "
				+ "where ti.tagState='1' and cus.id="+customerID;
		
		SqlParamer params=new SqlParamer();
		
		if(StringUtil.isNotBlank(tagNo)){
			params.eq("tagNo", tagNo);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			params.eq("vehicleColor", vehicleColor);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			params.eq("vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}
		if(StringUtil.isNotBlank(endSixNo)){
			params.like("IdentificationCode", endSixNo);
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		List tagRecovers = queryList(sql,Objects);
		return tagRecovers;
	}
	
	public Pager findTagReplacesByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID){
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,coc.vehicleID,vehiclePlate,Organ,TagState,ti.Issuetime,ROWNUM as num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus  on ti.clientid=cus.id "
				+ "where ti.tagState='1' and cus.id="+customerID;
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(tagNo)){
			params.eq("tagNo", tagNo);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			params.eq("vehicleColor", vehicleColor);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			params.eq("vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}
		if(StringUtil.isNotBlank(endSixNo)){
			params.like("substr(IdentificationCode,-6)", endSixNo);
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by ti.Issuetime desc ");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}
	
	/**
	 * 澳门通用
	 * @param pager
	 * @param tagNo
	 * @param vehicleColor
	 * @param vehiclePlate
	 * @param idType
	 * @param idCode
	 * @param endSixNo
	 * @param customerID
	 * @param macaoCardCustomer
	 * @return
	 * @return Pager
	 */
	public Pager findTagReplacesByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer){
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,ti.Issuetime,ROWNUM as num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id join csms_cardholder_info ci on ci.typeid=ti.id join csms_macao_bankaccount mb on mb.id=ci.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid "
				+ "where ti.tagState='1' and cus.id="+customerID;
		SqlParamer params=new SqlParamer();
		if(macaoCardCustomer != null){
			params.eq("mcc.id", macaoCardCustomer.getId());
		}
		if(StringUtil.isNotBlank(tagNo)){
			params.eq("tagNo", tagNo);
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			params.eq("vehicleColor", vehicleColor);
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			params.eq("vehiclePlate", vehiclePlate);
		}
		if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}
		if(StringUtil.isNotBlank(endSixNo)){
			params.like("IdentificationCode", endSixNo);
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by ti.Issuetime desc ");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}
	
	public Map<String, Object> findTagReplaceById(Long tagInfoId){
		
		String sql = "select ti.id as tagInfoId,cus.id as clientID,Organ,coc.vehicleID,vehiclePlate,Model,vehicleColor,vehicleSpecificInformation,"
				+ "VehicleType,vehicleWeightLimits,vehicleAxles,vehicleEngineNo,"
				+ "UsingNature,IdentificationCode,owner,tagNo,ChargeCost,Issuetime,"
				+ "MaintenanceTime,StartTime,EndTime,vc.NSCVehicleType,vehicleWheels,vehicleLong,vehicleWidth,vehicleHeight "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.tagId=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id where ti.id=?";
		
		List<Map<String, Object>> list = queryList(sql,tagInfoId);
		
		return list.get(0);
	}
	
	
	//保存电子标签维护记录，维护类型为“更换”
	public TagMainRecord saveTagMainRecord(TagMainRecord tagMainRecord){
		
		/*String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BigDecimal SEQ_CSMSTagMainRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagMainRecord_NO");
		Long id = Long.valueOf(SEQ_CSMSTagMainRecord_NO.toString());
		
		TagMainRecord tagMainRecord = new TagMainRecord();
		tagMainRecord.setId(id);
		tagMainRecord.setTagInfoID(tagInfoId);
		tagMainRecord.setTagNo(tagNo);
		tagMainRecord.setNewTagNo(newTagNo);
		tagMainRecord.setClientID(clientID);
		tagMainRecord.setVehicleID(vehicleID);
		tagMainRecord.setMaintainType("2");
		tagMainRecord.setInstallman(installmanID+"");
		tagMainRecord.setChargeCost(chargeFee);
		tagMainRecord.setReason(replaceReason);
		tagMainRecord.setMemo(memo);
		tagMainRecord.setOperID(1l);
		tagMainRecord.setIssueplaceID(1l);
		tagMainRecord.setIssuetime(new Date());*/
		tagMainRecord.setHisSeqID(-tagMainRecord.getId());
		Map map = FieldUtil.getPreFieldMap(TagMainRecord.class,tagMainRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_TagMain_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		return tagMainRecord;
		/*StringBuffer sql = new StringBuffer("insert into "
				+ "CSMS_TagMain_Record(ID,TagInfoID,TagNo,NewTagNo,ClientID,"
				+ "vehicleID,MaintainType,Installman,ChargeCost,Reason,Memo,OperID,"
				+ "IssueplaceID,Issuetime,HisSeqID) "
				+ "values(");
		
		if(id == null){
			sql.append("NULL,");
		}else{
			sql.append(id + ",");
		}
		if(tagInfoId == null){
			sql.append("NULL,");
		}else{
			sql.append(tagInfoId + ",");
		}
		if(tagNo == null){
			sql.append("NULL,");
		}else{
			sql.append("'" + tagNo + "',");
		}
		if(newTagNo == null){
			sql.append("NULL,");
		}else{
			sql.append("'" + newTagNo + "',");
		}
		
		if(clientID == null){
			sql.append("NULL,");
		}else{
			sql.append(clientID + ",");
		}
		
		if(vehicleID == null){
			sql.append("NULL,");
		}else{
			sql.append(vehicleID + ",");
		}
		//MaintainType维护类型"更换"
		sql.append("2,");
		
		if(installmanID == null){
			sql.append("NULL,");
		}else{
			sql.append("'" + installmanID + "',");
		}
		//ChargeCost收费金额
		if(chargeFee == null){
			sql.append("NULL,");
		}else{
			sql.append(chargeFee+",");
		}
		
		
		if(replaceReason == null){
			sql.append("NULL,");
		}else{
			sql.append("'"+replaceReason + "',");
		}
		
		if(memo == null){
			sql.append("NULL,");
		}else{
			sql.append("'"+memo + "',");
		}
		
		sql.append(1+",");//操作员id
		sql.append(1+",");//维护网点id
		sql.append("sysdate,");
		sql.append(1+",");//历史序列id
		
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);*/
	}
	
	
	/*public void saveTagBusinessRecord(Long tagInfoId, String tagNo, Long clientID,
			Long vehicleID, String replaceReason, Long installmanID, String memo){
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
		Long id = Long.valueOf(SEQ_CSMSTagBusinessRecord_NO.toString());
		
		TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
		tagBusinessRecord.setId(id);
		tagBusinessRecord.setTagNo(tagNo);
		tagBusinessRecord.setClientID(clientID);
		tagBusinessRecord.setVehicleID(vehicleID);
		tagBusinessRecord.setOperID(1L);
		tagBusinessRecord.setOperTime(new Date());
		tagBusinessRecord.setOperplaceID(1l);
		tagBusinessRecord.setBusinessType("3");
		tagBusinessRecord.setInstallmanID(installmanID);
		tagBusinessRecord.setCurrentTagState("1");
		//tagBusinessRecord.setImportCardNo("1");
		//tagBusinessRecord.setWriteState("1");
		tagBusinessRecord.setMemo(memo);
		tagBusinessRecord.setFromID(tagInfoId);
		
		Map map = FieldUtil.getPreFieldMap(TagBusinessRecord.class,tagBusinessRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_Tag_BusinessRecord ");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		StringBuffer sql = new StringBuffer("insert into "
				+ "CSMS_Tag_BusinessRecord(ID,tagNo,clientID,vehicleID,"
				+ "OperID,OperTime,OperplaceID,BusinessType,InstallmanID,"
				+ "CurrentTagState,ImportCardNo,WriteState,Memo,FromID) values(");
		
		if(id == null){
			sql.append("NULL,");
		}else{
			sql.append(id + ",");
		}
		if(tagNo == null){
			sql.append("NULL,");
		}else{
			sql.append("'" + tagNo + "',");
		}
		if(clientID == null){
			sql.append("NULL,");
		}else{
			sql.append(clientID + ",");
		}
		if(vehicleID == null){
			sql.append("NULL,");
		}else{
			sql.append(vehicleID + ",");
		}
		//操作人、操作时间、操作网点
		sql.append("1,");
		sql.append("sysdate,");
		sql.append("1,");
		//业务原因是更改
		sql.append("3,");
		//CurrentTagState
		sql.append("1,");
		//ImportCardNo
		sql.append("'1',");
		//WriteState
		sql.append("'1',");
		
		if(installmanID == null){
			sql.append("NULL,");
		}else{
			sql.append(installmanID + ",");
		}
		
		if(memo == null){
			sql.append("NULL,");
		}else{
			sql.append("'"+memo + "',");
		}
		if(tagInfoId == null){
			sql.append("NULL,");
		}else{
			sql.append(tagInfoId + ",");
		}
		
		
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);
	}*/
	
	/**
	 * 修改车卡标签绑定表的该条记录的电子标签id
	 * @param newTagInfoId
	 * @param oldTagInfoId
	 * @param vehicleID
	 */
	/*public void updateCarObuCardInfo(Long newTagInfoId,CarObuCardInfo carObuCardInfo){
		StringBuffer sql = new StringBuffer("update CSMS_CarObuCard_info set ");
		String sqlString = "";

		if(carObuCardInfo.getPrepaidCID() == null){
			sql.append("PrepaidCID=NULL,");
		}else{
			sql.append("PrepaidCID='"+carObuCardInfo.getPrepaidCID()+"',");
		}
		if(carObuCardInfo.getAccountCID() == null){
			sql.append("AccountCID=NULL,");
		}else{
			sql.append("AccountCID='"+carObuCardInfo.getAccountCID()+"',");
		}
		if(carObuCardInfo.getVehicleID() == null){
			sql.append("VehicleID=NULL,");
		}else{
			sql.append("VehicleID='"+carObuCardInfo.getVehicleID()+"',");
		}
		//设置新的电子标签号id
		sql.append("TagID='"+newTagInfoId+"',");
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}

		sqlString += " where vehicleID=" + carObuCardInfo.getVehicleID() + "";
		update(sqlString);
		
	}*/
	/**
	 * 根据电子标签id查找CarObuCardInfo对象
	 * @param oldTagInfoId
	 * @return
	 */
	public CarObuCardInfo findOldCarObuCardInfo(Long oldTagInfoId,Long vehicleID){
		String sql = "select prepaidCID,accountCID,vehicleID,tagID from CSMS_CarObuCard_info "
				+ "where tagID=? and vehicleID=?";
		
		List<Map<String, Object>> list = queryList(sql, oldTagInfoId,vehicleID);
		
		CarObuCardInfo carObuCardInfo = null;

		if(list.size() > 0){
			carObuCardInfo = (CarObuCardInfo) this.convert2Bean(
					(Map<String, Object>) list.get(0), new CarObuCardInfo());
		}
			


		return carObuCardInfo;
	}
	
	/**
	 * 联营卡的
	 * @author gsf
	 * @param pager
	 * @param tagInfo
	 * @param vehicleInfo
	 * @param sessionCustomer
	 * @param listCustomer
	 * @return
	 */
	public Pager findForLian(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,Customer sessionCustomer,Customer listCustomer){
		StringBuffer sql = new StringBuffer("select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,ti.Issuetime,"
				+ " ROWNUM as num from CSMS_CarObuCard_info coc "
				+ "join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id "
				+ "join CSMS_Tag_info ti on coc.TagID=ti.id "
				+ "join csms_customer c on c.id=ti.clientid where ti.tagState='1' and c.id="+sessionCustomer.getId());
		if (tagInfo != null) {
			sql.append(FieldUtil.getFieldMap(TagInfo.class,tagInfo).get("nameAndValueNotNull"));
		}
		if(vehicleInfo != null){
			sql.append(FieldUtil.getFieldMap(VehicleInfo.class,vehicleInfo).get("nameAndValueNotNull"));
		}
		if(listCustomer != null){
			sql.append(FieldUtil.getFieldMap(Customer.class,listCustomer).get("nameAndValueNotNull"));
		}
		
		String strSql = sql.toString();
		if(strSql.contains("identificationCode")){
			strSql = strSql.replace("identificationCode='"+vehicleInfo.getIdentificationCode()+"'", "identificationCode like '%"+vehicleInfo.getIdentificationCode()+"%'");
		}
		strSql = strSql+" order by ti.id desc ";
		return this.findByPages(strSql, pager,null);
	}
	
}
