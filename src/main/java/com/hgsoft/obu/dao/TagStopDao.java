package com.hgsoft.obu.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.Enum.BlackFlagEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Component
public class TagStopDao extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	
	public List findTagStops(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID){
		//
		String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,"
				+ "cus.id as clientID,coc.vehicleID "
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
			params.eq("IdentificationCode", endSixNo);
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		
		List tagRecovers = queryList(sql,Objects);
		return tagRecovers;
	}
	
	public Pager findTagStopsByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID){
		//
		/*String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,"
				+ "cus.id as clientID,coc.vehicleID,ti.Issuetime,ROWNUM as num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id "
				+ "where ti.tagState='1' and cus.id="+customerID;*/
		
		String sql = "select tagInfoId,tagNo,TagState,vehicleColor,vehiclePlate," +
				" coc.vehicleID,ti.Issuetime,clientid,writebackflag,ROWNUM as num from ( "+
				" select ti.id as tagInfoId,tagNo,TagState,Issuetime,ti.clientid as clientid,writebackflag from CSMS_Tag_info ti " +
				" where (ti.tagState='1'or ti.tagState='2') and ti.clientid= "+customerID +
				" ) ti  left join CSMS_CarObuCard_info coc on coc.TagID=tagInfoId " +
				" left join CSMS_Vehicle_Info  vc on coc.vehicleID=vc.id  where 1=1  ";
		
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
		/*if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}*/
		if(StringUtil.isNotBlank(endSixNo)){
			params.like("substr(IdentificationCode,-6)", endSixNo);
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		
		//List tagRecovers = queryList(sql,Objects);
		sql=sql+(" order by ti.Issuetime desc ");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}
	
	public Pager findTagStopsByPagerForAMMS(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,String bankCode){
		String sql = " select allCard.* from (select nvl(a.cardNo,p.cardNo) allCardNo,vc.identificationCode identificationCode,ti.id tagInfoId,ti.tagNo tagNo,ti.tagState TagState,vc.vehicleColor vehicleColor,vc.vehiclePlate vehiclePlate,ti.Issuetime Issuetime,ti.clientid clientid,ti.writebackflag writebackflag,coc.vehicleID,ROWNUM as num" 
					+" from CSMS_Tag_info ti"
					+" left join CSMS_CarObuCard_info coc on coc.TagID=ti.id"
					+" left join CSMS_Vehicle_Info  vc on coc.vehicleID=vc.id"
					+" left join csms_accountc_info a on  coc.accountcId = a.id"
					+" left join csms_prepaidc p on coc.prepaidcId = p.id where (ti.tagState='1'or ti.tagState='2') and ti.clientid=?) allCard"
					+" left join csms_joinCardNoSection j  on substr(allCard.allCardNo, 0, length(allCard.allCardNo) - 1) between j.code and j.endcode"
					+" where (allCard.allCardNo  is null or j.bankno =?)";
		
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
	 * @return
	 * @return Pager
	 */
	public Pager findTagStopsByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer){
		//
		/*String sql = "select ti.id as tagInfoId,tagNo,vehicleColor,vehiclePlate,Organ,TagState,"
				+ "cus.id as clientID,coc.vehicleID,ti.Issuetime,ROWNUM as num "
				+ "from CSMS_CarObuCard_info coc join "
				+ "CSMS_Vehicle_Info vc on coc.vehicleID=vc.id join "
				+ "CSMS_Tag_info ti on coc.TagID=ti.id join "
				+ "CSMS_Customer cus on ti.clientid=cus.id "
				+ "where ti.tagState='1' and cus.id="+customerID;*/
		
		String sql = "select tagInfoId,tagNo,TagState,vehicleColor,vehiclePlate," +
				" coc.vehicleID,ti.Issuetime,clientid,writebackflag,ROWNUM as num from ( "+
				" select ti.id as tagInfoId,tagNo,TagState,Issuetime,ti.clientid as clientid,writebackflag from CSMS_Tag_info ti " +
				" where (ti.tagState='1'or ti.tagState='2') and ti.clientid= "+customerID +
				" ) ti  left join CSMS_CarObuCard_info coc on coc.TagID=tagInfoId " +
				" left join CSMS_Vehicle_Info  vc on coc.vehicleID=vc.id join csms_cardholder_info ci on ci.typeid=ti.tagInfoId join csms_macao_bankaccount mb on mb.id=ci.macaobankaccountid join csms_macao_card_customer mcc on mcc.id=mb.mainid where 1=1  ";
		
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
		/*if(StringUtil.isNotBlank(idType)){
			params.eq("idType", idType);
		}
		if(StringUtil.isNotBlank(idCode)){
			params.eq("idCode", idCode);
		}*/
		if(StringUtil.isNotBlank(endSixNo)){
			params.like("IdentificationCode", endSixNo);
		}
		
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		
		//List tagRecovers = queryList(sql,Objects);
		sql=sql+(" order by ti.Issuetime desc ");
		pager = findByPages(sql, pager, Objects);
		return pager;
	}
	
	public Map<String, Object> findTagStopById(Long tagInfoId){
		
		String sql = "select ti.id as tagInfoId,cus.id as clientID,Organ,coc.vehicleID,vehiclePlate,Model,vehicleColor,vehicleSpecificInformation,"
				+ "VehicleType,vehicleWeightLimits,vehicleAxles,vehicleEngineNo,"
				+ "UsingNature,IdentificationCode,owner,tagNo,ChargeCost,Issuetime,"
				+ "MaintenanceTime,StartTime,EndTime,vc.NSCVehicleType,vehicleWheels,vehicleLong,vehicleWidth,vehicleHeight "
				+ " from CSMS_Tag_info ti "
				+ " left join CSMS_CarObuCard_info coc on coc.tagId=ti.id "
				+ " left join CSMS_Vehicle_Info vc on coc.vehicleID=vc.id "
				+ " join CSMS_Customer cus on ti.clientid=cus.id where ti.id=?";
		
		List<Map<String, Object>> list = queryList(sql,tagInfoId);
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 *  更新电子标签发行记录的客户ID字段，值为空（NULL）,状态为停用（‘2’）。
	 * @param tagInfoId
	 */
	public void updateTagInfo(Long tagInfoId,Long hisSeqId){
		StringBuffer sql = new StringBuffer("update CSMS_Tag_info set TagState=? ,WriteBackFlag=?,blackFlag=?,hisseqid=?  where id=? ");
		saveOrUpdate(sql.toString(), "2","0",BlackFlagEnum.black.getValue(), hisSeqId,tagInfoId);
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
	 * // 记录电子标签业务操作记录，业务类型为“停用”，当前状态为”停用“（‘2’）
	 * @param tagInfoId
	 * @param tagNo
	 * @param clientID
	 * @param vehicleID
	 * @param recoverReason
	 * @param installmanID
	 * @param memo
	 */
	public void saveTagBusinessRecord(Long tagInfoId, String tagNo, Long clientID,
			Long vehicleID,Long installmanID, String memo){
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BigDecimal SEQ_CSMSTagBusinessRecord_NO = sequenceUtil.getSequence("SEQ_CSMSTagBusinessRecord_NO");
		Long id = Long.valueOf(SEQ_CSMSTagBusinessRecord_NO.toString());
		
		/*StringBuffer sql = new StringBuffer("insert into "
				+ "CSMS_Tag_BusinessRecord(ID,tagNo,clientID,vehicleID,"
				+ "OperID,OperTime,OperplaceID,BusinessType,InstallmanID,"
				+ "CurrentTagState,ImportCardNo,WriteState,Memo,FromID) values(");
		*/
		TagBusinessRecord tagBusinessRecord = new TagBusinessRecord();
		tagBusinessRecord.setId(id);
		tagBusinessRecord.setTagNo(tagNo);
		tagBusinessRecord.setClientID(clientID);
		tagBusinessRecord.setVehicleID(vehicleID);
		tagBusinessRecord.setOperID(1L);
		tagBusinessRecord.setOperTime(new Date());
		tagBusinessRecord.setOperplaceID(1l);
		tagBusinessRecord.setBusinessType("5");
		tagBusinessRecord.setInstallmanID(installmanID);
		tagBusinessRecord.setCurrentTagState("2");
		//tagBusinessRecord.setImportCardNo("1");
		//tagBusinessRecord.setWriteState("1");
		tagBusinessRecord.setMemo(memo);
		tagBusinessRecord.setFromID(tagInfoId);
		
		Map map = FieldUtil.getPreFieldMap(TagBusinessRecord.class,tagBusinessRecord);
		StringBuffer sql=new StringBuffer("insert into CSMS_Tag_BusinessRecord ");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
/*		if(id == null){
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
		//业务原因是停用
		sql.append("5,");
		//CurrentTagState
		sql.append("2,");
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
		save(sqlString);*/
	}
	/**
	 * 保存停用时候的电子标签发行历史
	 * @param tagInfo
	 */
	public void saveTagInfoHis(TagInfoHis tagInfoHis){
		StringBuffer sql=new StringBuffer("insert into CSMS_Tag_infoHis(");
		sql.append(FieldUtil.getFieldMap(TagInfoHis.class,tagInfoHis).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(TagInfoHis.class,tagInfoHis).get("valueStr")+")");
		save(sql.toString());
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
		strSql = strSql+" order by ti.id desc ";
		return this.findByPages(strSql, pager,null);
	}
	
}
