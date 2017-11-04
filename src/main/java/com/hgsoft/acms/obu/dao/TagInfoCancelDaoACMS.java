package com.hgsoft.acms.obu.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Component
public class TagInfoCancelDaoACMS extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	
	/**
	 *  更新电子标签发行记录的客户ID字段，值为空（NULL）,状态为注销（‘4’）。
	 * @param tagInfoId
	 */
	public void updateTagInfo(Long tagInfoId,Long hisSeqId){
		StringBuffer sql = new StringBuffer("update CSMS_Tag_info set ");
		String sqlString = "";

		
		//sql.append("ClientID=NULL,");
		sql.append("TagState=4,");//电子标签状态为注销
		sql.append("WriteBackFlag=0,");
		sql.append("hisseqid="+hisSeqId+",");//电子标签状态为停用
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}

		sqlString += " where id=? ";
		this.jdbcUtil.getJdbcTemplate().update(sqlString, tagInfoId);
	}
	/**
	 * //更新车卡电子标签绑定表的电子标签为NULL.
	 * @param vehicleID
	 */
	/*public void updateCarObuCardInfo(Long vehicleID){
		StringBuffer sql = new StringBuffer("update CSMS_CarObuCard_info set ");
		String sqlString = "";

		sql.append("TagID=NULL,");
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += " where vehicleID=" + vehicleID + "";
		update(sqlString);
	}*/
	
	/**
	 * // 记录电子标签业务操作记录，业务类型为“注销”，当前状态为”注销“（‘4’）
	 * @param tagInfoId
	 * @param tagNo
	 * @param clientID
	 * @param vehicleID
	 * @param recoverReason
	 * @param installmanID
	 * @param memo
	 */
//	public void saveTagBusinessRecord(Long tagInfoId, String tagNo, Long clientID,
//			Long vehicleID,Long installmanID, String memo){
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
//		//业务原因是注销
//		sql.append("7,");
//		//CurrentTagState
//		sql.append("4,");
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
//		if (sql.toString().endsWith(",")) {
//			sqlString = sql.substring(0, sql.length() - 1);
//		}
//		sqlString += ")";
//		save(sqlString);
//	}
	
	public Pager findTagCancels(Pager pager, String tagNo, String vehicleColor, String vehiclePlate, String idType, String idCode,
			String endSixNo, Long customerId) {
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,"
				+ "cus.id as clientID,coc.vehicleID,ROWNUM num "
				+ "from CSMS_CarObuCard_info coc  "
				+ " join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id "
				+ " right join CSMS_Tag_info ti on coc.TagID=ti.id  "
				+ " join CSMS_Customer cus on ti.clientid=cus.id "
				+ " where  cus.id="+customerId+" ";
		
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
			params.eq("substr(IdentificationCode,-6)", endSixNo);
		}
		
		sql=sql+params.getParam()+"  order by ti.Issuetime desc";
		Object[] Objects= params.getList().toArray();
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	
	public Pager findTagCancelsForAMMS(Pager pager, String tagNo, String vehicleColor, String vehiclePlate, String idType, String idCode,
			String endSixNo, Long customerId,String bankCode) {
		StringBuffer sql = new StringBuffer("select * from (select ti.issuetime issuetime,vc.IdentificationCode IdentificationCode,cus.idType idType,cus.idCode idCode,ti.id as tagInfoId,ti.tagNo tagNo,vc.vehicleColor vehicleColor,vc.vehiclePlate vehiclePlate,cus.Organ,ti.TagState,cus.id as clientID,coc.vehicleID,ROWNUM num ");
		sql.append(" from CSMS_Tag_info ti left join CSMS_CarObuCard_info coc on coc.TagID=ti.id left join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join CSMS_Customer cus on ti.clientid=cus.id ");
		sql.append(" where coc.accountcid is null and coc.prepaidcid is null and ti.tagState!='4' and cus.id="+customerId);
		sql.append(" union all");
		sql.append(" select ti.issuetime issuetime,vc.IdentificationCode IdentificationCode,cus.idType idType,cus.idCode idCode,ti.id as tagInfoId,ti.tagNo tagNo,vc.vehicleColor vehicleColor,vc.vehiclePlate vehiclePlate,cus.Organ,ti.TagState,cus.id as clientID,coc.vehicleID,ROWNUM num");
		sql.append(" from CSMS_Tag_info ti left join CSMS_CarObuCard_info coc on coc.TagID=ti.id left join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join CSMS_Customer cus on ti.clientid=cus.id ");
		sql.append(" join csms_prepaidc p on p.id=coc.prepaidcid join CSMS_joinCardNoSection j on substr(p.cardno, 0, length(p.cardno) - 1) between j.code and j.endcode ");
		sql.append(" where j.cardtype ='22' and j.bankno ="+ bankCode +" and ti.tagState!='4' and cus.id="+customerId);
		sql.append(" union all");
		sql.append(" select ti.issuetime issuetime,vc.IdentificationCode IdentificationCode,cus.idType idType,cus.idCode idCode,ti.id as tagInfoId,ti.tagNo tagNo,vc.vehicleColor vehicleColor,vc.vehiclePlate vehiclePlate,cus.Organ,ti.TagState,cus.id as clientID,coc.vehicleID,ROWNUM num");
		sql.append(" from CSMS_Tag_info ti left join CSMS_CarObuCard_info coc on coc.TagID=ti.id left join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join CSMS_Customer cus on ti.clientid=cus.id ");
		sql.append(" join csms_accountc_info a on a.id=coc.accountcid join CSMS_joinCardNoSection j on substr(a.cardno, 0, length(a.cardno) - 1) between j.code and j.endcode");
		sql.append(" where j.cardtype ='23' and j.bankno ="+ bankCode +" and ti.tagState!='4' and cus.id="+customerId+") where 1=1");
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
			params.eq("substr(IdentificationCode,-6)", endSixNo);
		}
		
		sql.append(params.getParam());
		sql.append(" order by Issuetime desc");
		Object[] Objects= params.getList().toArray();
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
	
	public Map<String, Object> findTagCancelById(Long tagInfoId) {
		String sql = "select ti.id as tagInfoId,cus.id as clientID,Organ,coc.vehicleID,vehiclePlate,Model,vehicleColor,vehicleSpecificInformation,"
				+ "VehicleType,vehicleWeightLimits,vehicleAxles,vehicleEngineNo,"
				+ "UsingNature,IdentificationCode,owner,tagNo,ChargeCost,Issuetime,"
				+ "MaintenanceTime,StartTime,EndTime,vc.NSCVehicleType "
				+ " from CSMS_CarObuCard_info coc  "
				+ " join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id  "
				+ " right join CSMS_Tag_info ti on coc.tagId=ti.id  "
				+ " join CSMS_Customer cus on ti.clientid=cus.id where ti.id=?";
		
		List<Map<String, Object>> list = queryList(sql,tagInfoId);
		
		return list.get(0);
	}
	
	
	
	public Pager findTagCancelsForMacao(Pager pager, String tagNo, String vehicleColor, String vehiclePlate, String idType, String idCode,
			String endSixNo, Long customerId,  MacaoCardCustomer macaoCardCustomer) {
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,"
				+ "cus.id as clientID,coc.vehicleID,ROWNUM num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id  join "
				+ "csms_cardholder_info ci on ci.typeid=ti.id and ci.type='3' join "
				+ "csms_macao_bankaccount mb on ci.MACAOBANKACCOUNTID=mb.id join "
				+ "csms_macao_card_customer mc on mc.id=mb.mainid "
				+ "where ti.tagState='1' and cus.id="+customerId+" ";
		
		SqlParamer params=new SqlParamer();
		
		if(macaoCardCustomer != null){
			params.eq("mc.id", macaoCardCustomer.getId());
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
			params.eq("IdentificationCode", endSixNo);
		}
		
		
		sql=sql+params.getParam()+"  order by ti.Issuetime desc";
		Object[] Objects= params.getList().toArray();
		pager = this.findByPages(sql.toString(), pager,Objects);
		return pager;
	}
}
