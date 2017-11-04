package com.hgsoft.other.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.other.entity.OfficialCardImport;
import com.hgsoft.other.entity.OfficialCardInfo;
import com.hgsoft.other.entity.TestCard;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class OtherDao extends BaseDao {
	@Resource
	SequenceUtil sequenceUtil;
	/**
	 * 没有被调用的方法
	 * @param prepaidcid
	 * @param accountcid
	 * @param vehicleid
	 * @param tagid
	 */
	public void saveStart(Long prepaidcid, Long accountcid, Long vehicleid, Long tagid) {
		StringBuffer sql = new StringBuffer("insert into csms_carobucard_info(prepaidcid,accountcid,vehicleid,tagid) values(");
		String sqlString = null;
		if (prepaidcid == null||prepaidcid == 0) {
			sql.append("NULL,");
		} else {
			sql.append(prepaidcid + ",");
		}
		if (accountcid == null||accountcid == 0) {
			sql.append("NULL,");
		} else {
			sql.append(accountcid + ",");
		}
		if (vehicleid == null||vehicleid == 0) {
			sql.append("NULL,");
		} else {
			sql.append(vehicleid + ",");
		}
		if (tagid == null||tagid == 0) {
			sql.append("NULL,");
		} else {
			sql.append(tagid + ",");
		}
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		super.saveOrUpdate(sqlString);
	}
	
	/**
	 * 不用删除
	 * @param cardNo
	 */
	public void saveStop(String cardNo) {
		String sql = "delete from csms_carobucard_info where prepaidcid=(select p.id as p_id from csms_prepaidc p where p.cardno=?)";
		super.delete(sql,cardNo);
	}

	public List<Map<String,Object>> findCardListByCustomer(String idType, String idCode,String cardNo) {
		StringBuffer sql = new StringBuffer("with allCard as "
				+" (SELECT p.cardno cardno,p.enddate,c.organ,c.idtype,c.idcode,v.vehiclecolor,v.vehicleplate,p.startdate,cd.beforedelaytime,cd.id cdid,cd.flag,'1' cardType,SYSDATE nowTime from csms_prepaidc p "
				+" JOIN csms_customer c ON c.id=p.customerid" 
				+" LEFT JOIN csms_carobucard_info ci ON ci.prepaidcid=p.id"
				+" LEFT JOIN csms_vehicle_info v ON v.id=ci.vehicleid"
				+" LEFT JOIN csms_card_delay cd ON cd.cardno=p.cardno "
				+" WHERE c.idtype='"+idType+"' AND c.idcode='"+idCode+"' and p.state='0' AND p.enddate>SYSDATE"
				+" union all"
				+" SELECT ai.cardno cardno,ai.enddate,c.organ,c.idtype,c.idcode,v.vehiclecolor,v.vehicleplate,ai.startdate,cd.beforedelaytime,cd.id cdid,cd.flag,'2' cardType,SYSDATE nowTime from csms_accountc_info ai "
				+" JOIN csms_customer c ON c.id=ai.customerid" 
				+" LEFT JOIN csms_carobucard_info ci ON ci.accountcid=ai.id"
				+" LEFT JOIN csms_vehicle_info v ON v.id=ci.vehicleid"
				+" LEFT JOIN csms_card_delay cd ON cd.cardno=ai.cardno "
				+" WHERE c.idtype='"+idType+"' AND c.idcode='"+idCode+"' and ai.state='0' AND ai.enddate>SYSDATE)"
				+" select * from allCard where 1=1 ");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("cardNo", cardNo);
		}
		sql.append(" and (cdid is null or cdid in (select max(cdid) from allCard where cdid is not null and flag!='2' group by cardno))");
		sql=sql.append(sqlp.getParam());
		return queryList(sql.toString(),sqlp.getList().toArray());
	}

	public Map<String, Object> findDelayDetailByCardNo(String cardNo, String cardType) {
		String sql = null;
		List<Map<String, Object>> list = null;
		if(cardType.equals("1")){
			sql = "SELECT p.cardno cardno,'储值卡' cardType,v.vehiclecolor,v.vehicleplate,v.owner,v.vehicletype,v.vehicleweightlimits,v.nscvehicletype,c.organ,c.idtype,c.idcode,c.usertype,p.enddate,cd.delayTime,cd.id from csms_prepaidc p "
					+" JOIN csms_customer c ON c.id=p.customerid" 
					+" LEFT JOIN csms_carobucard_info ci ON ci.prepaidcid=p.id"
					+" LEFT JOIN csms_vehicle_info v ON v.id=ci.vehicleid "
					+" LEFT JOIN csms_card_delay cd on cd.cardno=p.cardno"
					+" WHERE p.cardNo=?";
		}else{
			sql = "SELECT ai.cardno cardno,'记帐卡' cardType,v.vehiclecolor,v.vehicleplate,v.owner,v.vehicletype,v.vehicleweightlimits,v.nscvehicletype,c.organ,c.idtype,c.idcode,c.usertype,ai.enddate,cd.delayTime,cd.id from csms_accountc_info ai "
					+" JOIN csms_customer c ON c.id=ai.customerid" 
					+" LEFT JOIN csms_carobucard_info ci ON ci.accountcid=ai.id"
					+" LEFT JOIN csms_vehicle_info v ON v.id=ci.vehicleid"
					+" LEFT JOIN csms_card_delay cd on cd.cardno=ai.cardno"
					+" WHERE ai.cardNo=?";
		}
		
		list = queryList(sql, cardNo);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	
	public OfficialCardImport findOfficialCardImportByNo(String cardNo){
		String sql = "SELECT * FROM CSMS_OFFICECARD_IMPORT WHERE CARDNO = '"+cardNo+"'";
		List<Map<String,Object>> list = queryList(sql);
		OfficialCardImport officialCard = null;
		if(list.size()>0){
			officialCard = new OfficialCardImport();
			convert2Bean(list.get(0), officialCard);
		}
		return officialCard;
	}
	
	public OfficialCardInfo findOfficialCardIssueByNo(String cardNo,String tagNo){
		String sql = "SELECT * FROM CSMS_OFFICIALCARD_ISSUE WHERE CARDNO = '"+cardNo+"'";
		List<Map<String,Object>> list = queryList(sql);
		OfficialCardInfo officialCard = null;
		if(list.size()>0){
			officialCard = new OfficialCardInfo();
			convert2Bean(list.get(0), officialCard);
		}
		return officialCard;
	}

	public void saveOfficialCard(OfficialCardInfo officialCardInfo) {
		Map map = FieldUtil.getPreFieldMap(OfficialCardInfo.class, officialCardInfo);
		
		StringBuffer sql = new StringBuffer("insert into CSMS_OFFICIALCARD_ISSUE");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List)map.get("param"));
	}

	public void updateOfficialCard(OfficialCardInfo officialCardInfo) {
		Map map = FieldUtil.getPreFieldMap(OfficialCardInfo.class,officialCardInfo);
		StringBuffer sql=new StringBuffer("update CSMS_OFFICIALCARD_ISSUE set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),officialCardInfo.getId());
	}
}
