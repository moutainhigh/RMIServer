package com.hgsoft.obu.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
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
public class TagRecoverDao extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	
	public void updateWriteBackFlag(Long id){
		String sql = "update csms_tag_info set writebackflag='0' where id=?";
		saveOrUpdate(sql, id);
	}
	
	public List findTagRecovers(String tagNo, String vehicleColor,
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
	
	public Pager findTagRecoversByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID){
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,ti.Issuetime,ROWNUM as num "
				+ " from CSMS_Tag_info ti "
				+ " left join CSMS_CarObuCard_info coc on ti.id=coc.TagID  "
				+ " left join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id  "
				+ " join CSMS_Customer cus on ti.clientid=cus.id "
				+ " where ti.tagState in('1','2','4') and cus.id="+customerID;
		
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
		sql=sql+(" order by ti.Issuetime desc");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}
	public Pager findTagRecoversByPagerForAMMS(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,String bankCode){
		String sql = " select * from (select nvl(a.cardNo,p.cardNo) allCardNo,vc.IdentificationCode IdentificationCode,cus.idtype idtype,cus.idcode idcode,ti.id as tagInfoId,ti.tagNo tagNo,vc.vehicleColor vehicleColor,vc.vehiclePlate vehiclePlate,cus.Organ Organ,ti.TagState TagState,ti.Issuetime Issuetime,ROWNUM as num"
					+" from CSMS_CarObuCard_info coc join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join CSMS_Tag_info ti on coc.TagID=ti.id join CSMS_Customer cus on ti.clientid=cus.id"
					+" left join csms_accountc_info a on  coc.accountcId = a.id"
					+" left join csms_prepaidc p on coc.prepaidcId = p.id where ti.tagState='1' and cus.id=?) allCard"
					+" left join csms_joinCardNoSection j  on substr(allCard.allCardNo, 0, length(allCard.allCardNo) - 1) between j.code and j.endcode"
					+" where (allCard.allCardNo  is null or j.bankno = ?)";
		
		List<String> params = new ArrayList<String>();
		params.add(customerID.toString());
		params.add(bankCode);
		
		
		if(StringUtil.isNotBlank(tagNo)){
			params.add(tagNo);
			sql = sql + " and allCard.tagNo=?";
		}
		if(StringUtil.isNotBlank(vehicleColor)){
			params.add(vehicleColor);
			sql = sql + " and allCard.vehicleColor=?";
		}
		if(StringUtil.isNotBlank(vehiclePlate)){
			params.add(vehiclePlate);
			sql = sql + " and allCard.vehiclePlate=?";
		}
		if(StringUtil.isNotBlank(idType)){
			params.add(idType);
			sql = sql + " and allCard.idType=?";
		}
		if(StringUtil.isNotBlank(idCode)){
			params.add(idCode);
			sql = sql + " and allCard.idCode=?";
		}
		if(StringUtil.isNotBlank(endSixNo)){
			params.add(endSixNo);
			sql = sql + " and substr(allCard.IdentificationCode,-6)=?";
		}
		
		sql = sql + " order by allCard.issuetime desc";
		return findByPages(sql.toString(), pager, params.toArray());
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
	 * @return Pager
	 */
	public Pager findTagRecoversByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer){
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,mcc.cnname Organ,TagState,ti.Issuetime,ROWNUM as num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id join csms_cardholder_info ci on ci.typeid=ti.id join csms_macao_bankaccount mb on mb.id=ci.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid  "
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
		sql=sql+(" order by ti.Issuetime desc");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}
	/**
	 * 澳门通查标签恢复详情
	 * @param tagInfoId
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findMacaoTag(Long tagInfoId){
		String sql = "select ti.id as tagInfoId,cus.id as clientID,mcc.cnname Organ,coc.vehicleID,vehiclePlate,Model,vehicleColor,vehicleSpecificInformation,"
				+ "VehicleType,vehicleWeightLimits,vehicleAxles,vehicleEngineNo,"
				+ "UsingNature,IdentificationCode,owner,tagNo,ChargeCost,Issuetime,"
				+ "MaintenanceTime,StartTime,EndTime,vc.NSCVehicleType "
				+ "from CSMS_CarObuCard_info coc "
				+ " join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id "
				+ " join CSMS_Tag_info ti on coc.TagID=ti.id "
				+ " join CSMS_Customer cus on ti.clientid=cus.id "
				+ " join csms_cardholder_info ci on ci.typeid=ti.id "
				+ " join csms_macao_bankaccount mb on mb.id=ci.macaobankaccountid "
				+ " join csms_macao_card_customer mcc on mcc.id=mb.mainid where ti.id=? ";
		
		List<Map<String, Object>> list = queryList(sql,tagInfoId);
		
		return list.get(0);
	}
	
	public Map<String, Object> findTagRecoverById(Long tagInfoId){
		
		String sql = "select ti.id as tagInfoId,cus.id as clientID,Organ,coc.vehicleID,vehiclePlate,Model,vehicleColor,vehicleSpecificInformation,"
				+ "VehicleType,vehicleWeightLimits,vehicleAxles,vehicleEngineNo,"
				+ "UsingNature,IdentificationCode,owner,tagNo,ChargeCost,Issuetime,"
				+ "MaintenanceTime,StartTime,EndTime,vc.NSCVehicleType,vehicleWheels,vehicleLong,vehicleWidth,vehicleHeight "
				+ "from CSMS_Tag_info ti "
				+ "left join CSMS_CarObuCard_info coc on coc.tagId=ti.id "
				+ "left join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id  "
				+ "join CSMS_Customer cus on ti.clientid=cus.id where ti.id=?";
		
		List<Map<String, Object>> list = queryList(sql,tagInfoId);
		
		return list.get(0);
	}
	
	
	//保存电子标签维护记录，维护类型为“恢复”
	public void saveTagMainRecord(TagMainRecord tagMainRecord){
		tagMainRecord.setHisSeqID(-tagMainRecord.getId());
		Map map = FieldUtil.getPreFieldMap(TagMainRecord.class,tagMainRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_TagMain_Record");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		
		/*StringBuffer sql = new StringBuffer("insert into "
				+ "CSMS_TagMain_Record(ID,TagInfoID,TagNo,ClientID,"
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
		//维护类型为1、恢复
		sql.append("1,");
		
		if(installmanID == null){
			sql.append("NULL,");
		}else{
			sql.append("'" + installmanID + "',");
		}
		//收费金额chargeCost
		if(chargeCost == null){
			sql.append("NULL,");
		}else{
			sql.append(chargeCost + ",");
		}
		
		if(recoverReason == null){
			sql.append("NULL,");
		}else{
			sql.append("'"+recoverReason + "',");
		}
		
		if(memo == null){
			sql.append("NULL,");
		}else{
			sql.append("'"+memo + "',");
		}
		
		sql.append(1+",");
		sql.append(1+",");
		sql.append("sysdate,");
		sql.append(1+",");
		
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);*/
	}
	
	
	public void saveTagBusinessRecord(Long tagInfoId, String tagNo, Long clientID,
			Long vehicleID, String recoverReason, Long installmanID, String memo){
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
		Long id = Long.valueOf(SEQ_CSMSTagBusinessRecord_NO.toString());
		
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
		//业务原因是恢复
		sql.append("4,");
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
	}
	public TagMainRecord findById(Long id){
		String sql="select * from CSMS_TagMain_Record where id="+id;
		List list=queryList(sql);
		TagMainRecord tagMainRecord = null;
		if(list!=null && list.size()!=0) {
			tagMainRecord = (TagMainRecord) this.convert2Bean((Map<String, Object>) list.get(0), new TagMainRecord());
		}

		return tagMainRecord;
	}
	public Pager findForLian(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,Customer sessionCustomer,Customer listCustomer){
		StringBuffer sql = new StringBuffer("select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,ti.Issuetime,"
				+ "ROWNUM as num from CSMS_CarObuCard_info coc "
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
		strSql =strSql+ " order by ti.id desc ";
		return this.findByPages(strSql, pager,null);
	}
	
}
