package com.hgsoft.clearInterface.dao;

import com.hgsoft.associateAcount.entity.JointCardBlackListQuery;
import com.hgsoft.clearInterface.entity.*;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class BlackListDao extends BaseDao {

	private static Logger logger = LoggerFactory.getLogger(BlackListDao.class);
	
	@Resource
	SequenceUtil sequenceUtil;
	
	public List<Map<String,Object>> findByBlackListTemp(BlackListTemp blackListTemp){
		StringBuffer sql = new StringBuffer("select id,netno,obuid,license,genmode,cardtype,cardno,gentime,status from csms_blacklist_temp where cardType=? and cardNo=? and status=?");
		return queryList(sql.toString(),blackListTemp.getCardType(),blackListTemp.getCardNo(),blackListTemp.getStatus());
	}
	
	public List<AccountCStopPayBlackList> findCurrentStopPay(){
		String sql = "SELECT A.* FROM TB_PAYMENTCARDBLACKLIST_RECV A full JOIN  "
				+ "CSMS_BLACK_LIST_WATER B ON A.CARDCODE = B.CARDNO AND A.GENTIME = B.GENTIME where A.CARDCODE is "
				+ "null  or B.CARDNO is null  or  A.GENTIME is null  or  B.GENTIME is null or A.FLAG is null  or "
				+ "B.STOPPAYSTATUS is null  or A.FLAG<>B.STOPPAYSTATUS";
		return super.queryObjectList(sql, AccountCStopPayBlackList.class);
	}
	
	public Boolean importStopPayCard(){
		String sql = "INSERT INTO CSMS_BLACK_LIST_WATER (ID,NETNO,TYPE,CARDNO,GENTIME,GENTYPE,"
				+ "STATUS,STOPPAYSTATUS,FLAG)SELECT SEQ_CSMSBLACKLISTWATER_NO.nextval,'4401','23',"
				+ "CARDCODE,GENTIME,1,CASE FLAG WHEN 3 THEN 0 ELSE 4 END AS STATUS,FLAG,0 FROM "
				+ "TB_PAYMENTCARDBLACKLIST_RECV";
		try {
			save(sql);
			return true;
		} catch (Exception e) {
			logger.error("importStopPayCard", e);
			return false;
		}
	}


	public void saveBlackListWarter(BlackListWarter blw) {
		blw.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBLACKLISTWATER_NO"));
		StringBuffer sql = new StringBuffer("insert into CSMS_BLACK_LIST_WATER(");
		sql.append(FieldUtil.getFieldMap(BlackListWarter.class, blw).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(BlackListWarter.class, blw).get("valueStr")
				+ ")");
		save(sql.toString());
	}

	public Long findNoHandleBlackListWarterCount(String currentTime){
		String sql = "SELECT COUNT(1) AS TOTALCOUNT FROM CSMS_BLACK_LIST_WATER WHERE FLAG = '0' AND TO_CHAR(GENTIME,'yyyy-mm-dd hh24:mi:ss')<='"+currentTime+"'";
		List<Map<String,Object>> list = queryList(sql);
		return (Long)list.get(0).get("TOTALCOUNT");
	}

	public List<BlackListWarter> findNoHandleBlackListWarter(Pager pager,String currentTime) {
		String sql = "SELECT * FROM CSMS_BLACK_LIST_WATER WHERE FLAG = '0' AND TO_CHAR(GENTIME,'yyyy-mm-dd hh24:mi:ss')<=? ";
		pager = this.findByPages(sql.toString(), pager, new Object[] {currentTime});
		List<Map<String,Object>> list = pager.getResultList();
		List<BlackListWarter> blackListWarter = null;
		if(list.size()>0){
			blackListWarter = new ArrayList<BlackListWarter>();
			for(Map<String, Object> map:list){
				BlackListWarter fee = new BlackListWarter();
				fee = (BlackListWarter) this.convert2Bean(
						(Map<String, Object>) map, new BlackListWarter());

				blackListWarter.add(fee);

			}
			
		}
		return blackListWarter;
	}


	public List<BlackListTemp> findBlackListTemp(BlackListWarter blackListWarter) {
		StringBuilder sql = new StringBuilder("SELECT * FROM CSMS_BLACKLIST_TEMP WHERE 1=1 ");
		
//		if(blackListWarter.getNetNo()==null){
//			sql.append(" AND NETNO IS NULL");
//		}
		if(blackListWarter.getObuId()==null){
			sql.append(" AND OBUID IS NULL");
		}else{
			sql.append(" AND OBUID = '" + blackListWarter.getObuId() + "'");
		}
		
		if(blackListWarter.getLicense()==null){
			sql.append(" AND LICENSE IS NULL");
		}else{
			sql.append(" AND LICENSE = '"+blackListWarter.getLicense()+"'");
		}
		
		if(blackListWarter.getCardNo() == null){
			sql.append(" AND CARDNO IS NULL");
		}else{
			sql.append(" AND CARDNO = '"+blackListWarter.getCardNo()+"'");
		}
		
		List<BlackListTemp> blackListTempList = null;
		List<Map<String, Object>> list = queryList(sql.toString());
		if(list.size()>0){
			blackListTempList = new ArrayList<BlackListTemp>();
			for(Map<String, Object> map:list){
				BlackListTemp fee = new BlackListTemp();
				fee = (BlackListTemp) this.convert2Bean(
						(Map<String, Object>) map, new BlackListTemp());

				blackListTempList.add(fee);

			}
			
		}
		return blackListTempList;
		
	}

	public BlackListTemp findBlackListTemp(BlackListWarter blackListWarter,Boolean isBlackList) {
		StringBuilder sql = new StringBuilder("SELECT * FROM CSMS_BLACKLIST_TEMP WHERE GENMODE = "+blackListWarter.getGenType());

		//黑名单状态
		if(isBlackList) {
			sql.append(" AND STATUS="+blackListWarter.getStatus());
		}else {
			sql.append(" AND STATUS+(" + blackListWarter.getStatus() + ")=0");
		}
		if(blackListWarter.getObuId()==null){
			sql.append(" AND OBUID IS NULL");
		}else{
			sql.append(" AND OBUID = '" + blackListWarter.getObuId() + "'");
		}
		
		if(blackListWarter.getLicense()==null){
			sql.append(" AND LICENSE IS NULL");
		}else{
			sql.append(" AND LICENSE = '"+blackListWarter.getLicense()+"'");
		}
		
		if(blackListWarter.getCardNo() == null){
			sql.append(" AND CARDNO IS NULL");
		}else{
			sql.append(" AND CARDNO = '"+blackListWarter.getCardNo()+"'");
		}
		
		List<Map<String, Object>> list = queryList(sql.toString());
		BlackListTemp blackListTempList = null;
		if (list!=null&&list.size()>0) {
			blackListTempList = new BlackListTemp();
			this.convert2Bean(list.get(0), blackListTempList);
		}

		return blackListTempList;
		
	}
	
	public List<BlackListTemp> findBlackList(String cardNo,String status,String genMode) {
		StringBuilder sql = new StringBuilder("SELECT * FROM CSMS_BLACKLIST_TEMP WHERE 1=1 ");

		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(status)){
			params.eq("status", status);
		}
		if(StringUtil.isNotBlank(genMode)){
			params.eq("genMode", genMode);
		}
		if (StringUtils.isBlank(params.getParam())) {
			throw new ApplicationException("条件为空");
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();


		List<BlackListTemp> blackListTempList = null;
		List<Map<String, Object>> mapList = queryList(sql.toString(),Objects);
		if(list.size()>0){
			blackListTempList = new ArrayList<BlackListTemp>();
			for(Map<String, Object> map:mapList){
				BlackListTemp fee = new BlackListTemp();
				fee = (BlackListTemp) this.convert2Bean(
						(Map<String, Object>) map, new BlackListTemp());
				blackListTempList.add(fee);

			}

		}
		return blackListTempList;

	}


	public BlackListTemp saveBlackListTemp(BlackListTemp blackListTemp) {
		blackListTemp.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBLACKLISTTEMP_NO"));
		StringBuffer sql = new StringBuffer("insert into CSMS_BLACKLIST_TEMP(");
		sql.append(FieldUtil.getFieldMap(BlackListTemp.class, blackListTemp).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(BlackListTemp.class, blackListTemp).get("valueStr")
				+ ")");
		save(sql.toString());
		return blackListTemp;
	}
	
	public void deleteBlackListTemp(BlackListTemp blackListTemp){
		String sql = "delete from CSMS_BLACKLIST_TEMP where id = ?";
		delete(sql, blackListTemp.getId());
	}


	public void saveBlackListRelieveTemp(BlackListRelieveTemp blackListTemp) {
		blackListTemp.setId(sequenceUtil.getSequenceLong("SEQ_BLACKLISTRELIEVETEMP_NO"));
		StringBuffer sql = new StringBuffer("insert into CSMS_BLACKLIST_RELIEVE_TEMP(");
		sql.append("ID,NETNO,OBUID,LICENSE,CARDTYPE,CARDNO,GENTIME,STATUS,DEALSTATUS,DEALTIME,GENMODE,SYSDEALTIME,FLAG,SYSGENTIME,TEMPID"
				+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		save(sql.toString(), blackListTemp.getId(), blackListTemp.getNetNo(), blackListTemp.getObuId(), blackListTemp.getLicense(), blackListTemp.getCardType(),
				blackListTemp.getCardNo(), blackListTemp.getGenTime(), blackListTemp.getStatus(), blackListTemp.getDealStatus(), blackListTemp.getDealTime(), blackListTemp.getGenMode(),
				blackListTemp.getSysDealTime(), blackListTemp.getFlag(), blackListTemp.getSysGenTime(), blackListTemp.getTempId());
	}

	public BlackListRelieveStatus findBlackListRelieveStatus(BlackListWarter blackListWarter) {
		StringBuilder sql = new StringBuilder("SELECT * FROM CSMS_BLACKLIST_RELIEVE_STATUS WHERE NETNO = '"+blackListWarter.getNetNo()+"'");
		List<Object> params = new ArrayList<Object>(3);
		if(blackListWarter.getObuId()==null){
			sql.append(" AND OBUID IS NULL");
		}else{
			sql.append(" AND OBUID = ?");
			params.add(blackListWarter.getObuId());
		}
		
		if(blackListWarter.getLicense()==null){
			sql.append(" AND LICENSE IS NULL");
		}else{
			sql.append(" AND LICENSE = ?");
			params.add(blackListWarter.getLicense());
		}
		
		if(blackListWarter.getCardNo() == null){
			sql.append(" AND CARDNO IS NULL");
		}else{
			sql.append(" AND CARDNO = ?");
			params.add(blackListWarter.getCardNo());
		}

		List<BlackListRelieveStatus> blackListRelieveStatuses = super.queryObjectList(sql.toString(), BlackListRelieveStatus.class, params.toArray());
		if (blackListRelieveStatuses == null || blackListRelieveStatuses.isEmpty()) {
			return null;
		}
		return blackListRelieveStatuses.get(0);
	}


	public void saveBlackListRelieveStatus(BlackListRelieveStatus blackListStatus) {

		blackListStatus.setId(sequenceUtil.getSequenceLong("SEQ_BLACKLISTRELIEVESTATUS_NO"));
		StringBuffer sql = new StringBuffer("insert into CSMS_BLACKLIST_RELIEVE_STATUS(");
		sql.append(FieldUtil.getFieldMap(BlackListRelieveStatus.class, blackListStatus).get("nameStr")
				+ ") values(");
		sql.append(FieldUtil.getFieldMap(BlackListRelieveStatus.class, blackListStatus).get("valueStr")
				+ ")");
		save(sql.toString());
	}

	public void saveBlackListClearAll(ClearBlackList clearBlackList){
		clearBlackList.setId(sequenceUtil.getSequenceLong("SEQ_GBTOLLCARDBLACKBASESEND_NO"));
		StringBuffer sql = new StringBuffer(
				"insert into TB_GBTOLLCARDBLACKBASE_SEND(");
		sql.append(FieldUtil.getFieldMap(ClearBlackList.class, clearBlackList).get("nameStr")
				+ ") values(");
		sql.append(
				FieldUtil.getFieldMap(ClearBlackList.class, clearBlackList).get("valueStr")  + ")");
		save(sql.toString());
	}
	public List<BlackListTemp> findBlackListByCardNo4AgentCard(String cardNo){
		String sql = "select * from CSMS_BLACKLIST_TEMP where CARDNO=?";
		return super.queryObjectList(sql, BlackListTemp.class, cardNo);
	}
	/**
	 * 查询黑名单临时表（卡或标签有多条黑名单记录）
	 * @param cardNo
	 * @param obuSerial
	 * @return List<BlackListTemp>
	 */
	public List<BlackListTemp> findBlackListTMP(String cardNo, String obuSerial){
		if (StringUtil.isEmpty(cardNo) && StringUtil.isEmpty(obuSerial)) {
			return null;
		}
		SqlParamer params=new SqlParamer();
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(BlackListTemp.class,new BlackListTemp()).get("nameStr")+" from CSMS_BLACKLIST_TEMP where 1=1 ");
		if(StringUtil.isNotBlank(cardNo)){
			params.eq("CARDNO", cardNo);
		}
		if(StringUtil.isNotBlank(obuSerial)){
			params.eq("OBUID", obuSerial);
		}
		if (StringUtils.isBlank(params.getParam())) {
			throw new ApplicationException("条件为空");
		}
		sql.append(params.getParam());
		sql.append(" order by id desc ");
		Object[] objects= params.getList().toArray();
		return super.queryObjectList(sql.toString(), BlackListTemp.class, objects);
	}
	
	public void saveBlackListClearAddition(ClearBlackList clearBlackList){
		try {
			clearBlackList.setId(sequenceUtil.getSequenceLong("SEQ_TOLLCARDBLACKDELTASEND_NO"));
			StringBuffer sql = new StringBuffer(
					"insert into TB_GBTOLLCARDBLACKDELTA_SEND(");
			sql.append(FieldUtil.getFieldMap(ClearBlackList.class, clearBlackList).get("nameStr")
					+ ") values(");
			sql.append(
					FieldUtil.getFieldMap(ClearBlackList.class, clearBlackList).get("valueStr") + ")");
			save(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("saveBlackListClearAddition", e);
		}
	}
	
	
	
	public void updateBlackListWarter(BlackListWarter blackListWarter) {
		String sql = "update CSMS_BLACK_LIST_WATER set flag = '1' where id = ?";
		update(sql, blackListWarter.getId());
	}


	
	public BlackListVersion findBlackListVersion(){
		String sql="SELECT * FROM (SELECT * FROM CSMS_BLACKLIST_VERSION ORDER BY VERSIONNO DESC) WHERE ROWNUM = 1 ";
		List<BlackListVersion> blackListVersions = super.queryObjectList(sql, BlackListVersion.class);
		if (blackListVersions == null || blackListVersions.isEmpty()) {
			return null;
		}
		return blackListVersions.get(0);
	}
	
	public List<BlackListVersion> findBlackListVersion(String status){
		String sql="SELECT * FROM CSMS_BLACKLIST_VERSION WHERE STATUS = ? ORDER BY ID ASC";
		return super.queryObjectList(sql, BlackListVersion.class, status);
	}
	
	public void saveBlackListVersion(BlackListVersion blackListVersion) {
		if(blackListVersion.getId()==null){
			blackListVersion.setId(sequenceUtil.getSequenceLong("SEQ_BLACKLISTVERSION_NO"));
			StringBuffer sql = new StringBuffer("insert into CSMS_BLACKLIST_VERSION(");
			sql.append(FieldUtil.getFieldMap(BlackListVersion.class, blackListVersion).get("nameStr")
					+ ") values(");
			sql.append(FieldUtil.getFieldMap(BlackListVersion.class, blackListVersion).get("valueStr")
					+ ")");
			save(sql.toString());
		}else{
			Map map = FieldUtil.getPreFieldMap(BlackListVersion.class,blackListVersion);
			StringBuffer sql=new StringBuffer("update CSMS_BLACKLIST_VERSION set ");
			sql.append(map.get("updateNameStr") +" where id = ?");
			saveOrUpdate(sql.toString(), (List) map.get("param"),blackListVersion.getId());
		}
		
	}
	
	public Date findLastSendTime(String versionType){
		String sql = "SELECT MAX(GENTIME) GENTIME FROM CSMS_BLACKLIST_VERSION WHERE VERSIONTYPE = ?";
		List<Map<String, Object>> list = queryList(sql,versionType);
		try {
			if (!list.isEmpty()) {
				return (Date)list.get(0);
			}
			return null;
		}catch(Exception e){
			logger.error("findLastSendTime", e);
			return null;
		}
	}
	

	public List<BlackListRelieveStatus> findBlackListRelieveStatusList(Long lastId){
		String sql = "SELECT * FROM CSMS_BLACKLIST_RELIEVE_STATUS WHERE ID>?";
		List<BlackListRelieveStatus> blackListRelieveStatusList = null;
		List<Map<String, Object>> list = queryList(sql.toString(), lastId);
		if(list.size()>0){
			blackListRelieveStatusList = new ArrayList<BlackListRelieveStatus>();
			for(Map<String, Object> map:list){
				BlackListRelieveStatus fee = new BlackListRelieveStatus();
				fee = (BlackListRelieveStatus) this.convert2Bean(
						(Map<String, Object>) map, new BlackListRelieveStatus());

				blackListRelieveStatusList.add(fee);
			}
			
		}
		return blackListRelieveStatusList;
	}



	public void deleteBlackListRelieveStatus(BlackListRelieveStatus blackListRelieveStatus) {
		String sql = "DELETE FROM CSMS_BLACKLIST_RELIEVE_STATUS WHERE ID = "+blackListRelieveStatus.getId();
		delete(sql);
	}


	public void deleteBlackListStatusRelieve() {
		String sql = "DELETE FROM CSMS_BLACKLIST_RELIEVE_STATUS";
		delete(sql);
	}

//	public void updateBlackListVersion(BlackListVersion blackListVersion) {
//		Map map = FieldUtil.getPreFieldMap(BlackListVersion.class,blackListVersion);
//		StringBuffer sql=new StringBuffer("update CSMS_BLACKLIST_VERSION set ");
//		sql.append(map.get("updateNameStr") +" where id = ?");
//		saveOrUpdate(sql.toString(), (List) map.get("param"),blackListVersion.getId());
//	}

	public void updateBlackListRelieveTemp(BlackListTemp blackListTemp, BlackListWarter blackListWarter) {
		Date date = new Date();
		String sql = "UPDATE CSMS_BLACKLIST_RELIEVE_TEMP SET DEALSTATUS=?,DEALTIME = to_date('"+DateUtil.formatDate(blackListWarter.getGenTime(), "yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'),"
				+ "SYSDEALTIME=TO_DATE('"+DateUtil.formatDate(date, "yyyyMMddHHmmss")+"', 'yyyymmddhh24miss'),FLAG=? WHERE TEMPID=?";
		update(sql, blackListWarter.getStatus(),1,blackListTemp.getId());
		
	}

	public List<BlackListWarter> findNoHandleBlackListWarter(String batchCount) {
		String sql = "SELECT * FROM CSMS_BLACK_LIST_WATER WHERE FLAG = '0' AND ROWNUM<? ORDER BY GENTIME,ID ASC";
		List<BlackListWarter> blackListWaterList = null;
		List<Map<String, Object>> list = queryList(sql,batchCount);
		if(list.size()>0){
			blackListWaterList = new ArrayList<BlackListWarter>();
			for(Map<String, Object> map:list){
				BlackListWarter fee = new BlackListWarter();
				fee = (BlackListWarter) this.convert2Bean(
						(Map<String, Object>) map, new BlackListWarter());

				blackListWaterList.add(fee);
			}
			
		}
		return blackListWaterList;
	}
	
	/**
	 * 香港联营卡系统查询黑名单
	 * @param pager
	 * @param customer
	 * @param jointCardBlackListQuery
	 * @return
	 */
	public Pager findBlackList(Pager pager, Customer customer, JointCardBlackListQuery jointCardBlackListQuery) {
		StringBuffer sql = new StringBuffer("select" 
				+ " blrt.cardno as cardno," + " blrt.gentime as gentime," + "blrt.status as status"
				+ " from CSMS_BLACKLIST_RELIEVE_TEMP blrt "
				+ " left join CSMS_HK_CARDHOLDER ch on blrt.cardno=ch.cardno "
				+ " left join Csms_Accountc_Info aci on blrt.cardno=aci.cardno ");
		sql.append(" where 1=1 ");
		SqlParamer params = new SqlParamer();
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getCardHolderName())) {
			params.eq("ch.name", jointCardBlackListQuery.getCardHolderName());
		} // if
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getIdType())) {
			params.eq("ch.idtype", jointCardBlackListQuery.getIdType());
		} // if
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getIdCode())) {
			params.eq("ch.idcode", jointCardBlackListQuery.getIdCode());
		} // if
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getCardNo())) {
			params.eq("blrt.cardno", jointCardBlackListQuery.getCardNo());
		} // if
		if (jointCardBlackListQuery.getStartTime() != null) {
			params.geDate("blrt.gentime", params.getFormat().format(jointCardBlackListQuery.getStartTime()));
		} // if
		if (jointCardBlackListQuery.getEndTime() != null) {
			params.leDate("blrt.gentime", params.getFormat().format(jointCardBlackListQuery.getEndTime()));
		} // if
		if (customer != null) {
			params.eq("aci.customerid", customer.getId());
		} // if
		sql.append(params.getParam());
		sql.append(" and blrt.cardno is not null and blrt.status is not null ");
		Object[] objects1 = params.getList().toArray();
		
		params.getList().clear();
		params.setParam("");
		sql.append(" union ");
		
		sql.append("select" 
				+ " blrt.cardno as cardno," + " blrt.dealtime as dealtime," + "blrt.dealstatus as dealstatus"
				+ " from CSMS_BLACKLIST_RELIEVE_TEMP blrt "
				+ " left join CSMS_HK_CARDHOLDER ch on blrt.cardno=ch.cardno "
				+ " left join Csms_Accountc_Info aci on blrt.cardno=aci.cardno ");
		sql.append(" where 1=1 ");
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getCardHolderName())) {
			params.eq("ch.name", jointCardBlackListQuery.getCardHolderName());
		} // if
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getIdType())) {
			params.eq("ch.idtype", jointCardBlackListQuery.getIdType());
		} // if
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getIdCode())) {
			params.eq("ch.idcode", jointCardBlackListQuery.getIdCode());
		} // if
		if (StringUtil.isNotBlank(jointCardBlackListQuery.getCardNo())) {
			params.eq("blrt.cardno", jointCardBlackListQuery.getCardNo());
		} // if
		if (jointCardBlackListQuery.getStartTime() != null) {
			params.geDate("blrt.gentime", params.getFormat().format(jointCardBlackListQuery.getStartTime()));
		} // if
		if (jointCardBlackListQuery.getEndTime() != null) {
			params.leDate("blrt.gentime", params.getFormat().format(jointCardBlackListQuery.getEndTime()));
		} // if
		if (customer != null) {
			params.eq("aci.customerid", customer.getId());
		} // if
		sql.append(params.getParam());
		sql.append(" and blrt.cardno is not null and blrt.dealstatus is not null ");
		Object[] objects2 = params.getList().toArray();
		
		sql.append(" order by cardno, gentime ");
		
		Object[] objects = ArrayUtils.addAll(objects1, objects2);
		return this.findByPages(sql.toString(), pager, objects);
	}

	/**
	 * 香港联营卡系统导出黑名单
	 * @param customer
	 * @return
	 */
	public List list(Customer customer) {
		StringBuffer sql = new StringBuffer("select" 
				+ " blrt.cardno as cardno," + " blrt.gentime as gentime," + "blrt.status as status"
				+ " from CSMS_BLACKLIST_RELIEVE_TEMP blrt "
				+ " left join CSMS_HK_CARDHOLDER ch on blrt.cardno=ch.cardno "
				+ " left join Csms_Accountc_Info aci on blrt.cardno=aci.cardno ");
		sql.append(" where 1=1 ");
		SqlParamer params = new SqlParamer();
		if (customer != null) {
			params.eq("aci.customerid", customer.getId());
		} // if
		if (StringUtils.isBlank(params.getParam())) {
			throw new ApplicationException("customerId为空");
		}
		sql.append(params.getParam());
		sql.append(" and blrt.cardno is not null and blrt.status is not null ");
		Object[] objects1 = params.getList().toArray();
		
		params.getList().clear();
		params.setParam("");
		sql.append(" union ");
		
		sql.append("select" 
				+ " blrt.cardno as cardno," + " blrt.dealtime as dealtime," + "blrt.dealstatus as dealstatus"
				+ " from CSMS_BLACKLIST_RELIEVE_TEMP blrt "
				+ " left join CSMS_HK_CARDHOLDER ch on blrt.cardno=ch.cardno "
				+ " left join Csms_Accountc_Info aci on blrt.cardno=aci.cardno ");
		sql.append(" where 1=1 ");
		if (customer != null) {
			params.eq("aci.customerid", customer.getId());
		} // if
		sql.append(params.getParam());
		sql.append(" and blrt.cardno is not null and blrt.dealstatus is not null ");
		Object[] objects2 = params.getList().toArray();
		
		sql.append(" order by cardno, gentime ");
		
		Object[] objects = ArrayUtils.addAll(objects1, objects2);
		return queryList(sql.toString(), objects);
	}
	
	public void saveBlackListAllHis(String boardListNo,String startTime,String endTime,Long versionNo) {
		StringBuffer sql = new StringBuffer("INSERT INTO TB_BLACKLIST_SEND_HIS (ID, BOARDLISTNO, NETNO, CARDCODE, CARDTYPE, OBUID, LICENSE, GENCAU, GENMODE, GENTIME, VERSION, REMARK, UPDATETIME) ");
		sql.append("SELECT SEQ_GBTOLLCARDBLACKBASESEND_NO.nextval as ID,"+boardListNo+" AS BOARDLISTNO,B.NETNO,CARDNO,CARDTYPE,OBUID,LICENSE,STATUS,0 AS GENMODE,GENTIME,"+String.valueOf(versionNo)+" AS VERSION ,null,SYSDATE ");
		sql.append("  FROM (SELECT AA.*, ROW_NUMBER() OVER(PARTITION BY AA.NETNO,AA.OBUID,AA.LICENSE,AA.CARDNO ORDER BY AA.PARAMVALUE ASC) RN ");
		sql.append(" FROM  (" );
		sql.append(" select CBRT.*,CPC.PARAMVALUE,CPC.PARAMCHNAME,CPC.PARAMNAME from CSMS_BLACKLIST_RELIEVE_TEMP CBRT,CSMS_PARAM_CONFIG CPC WHERE" );
		sql.append("   CBRT.STATUS = CPC.PARAMNAME AND CPC.PARAMNO = 'CSMSBLACKLIST01' " );
		sql.append(" AND " );
		sql.append(" ((CBRT.FLAG=0 AND TO_CHAR(CBRT.SYSGENTIME,'yyyymmddhh24miss')<='"+endTime+"' " );
		sql.append(" )" );
		sql.append(" OR " );
		sql.append(" (CBRT.FLAG=1 AND TO_CHAR(CBRT.SYSDEALTIME,'yyyymmddhh24miss')>'"+endTime+"' ) " );
		sql.append(" )");
//				+ "select CBRT.*,CPC.PARAMVALUE,CPC.PARAMCHNAME,CPC.PARAMNAME from CSMS_BLACKLIST_RELIEVE_TEMP CBRT,CSMS_PARAM_CONFIG CPC WHERE ");
//		sql.append(" CBRT.STATUS = CPC.PARAMVALUE AND CPC.PARAMNO = 'CSMSBLACKLIST01' AND TO_CHAR(CBRT.SYSGENTIME,'yyyymmddhh24miss')<='"+startTime+"' ");
//		sql.append(" AND (CBRT.FLAG=0 OR (CBRT.FLAG = 1 AND TO_CHAR(CBRT.SYSDEALTIME,'yyyymmddhh24miss')>'"+startTime+"'))) "
		sql.append(" )AA) B ");
		sql.append("  WHERE B.RN = 1");
		save(sql.toString());
		
//		String sqlSelect = "SELECT COUNT(1) AS TOTALCOUNT FROM TB_GBTOLLCARDBLACKBASE_SEND WHERE BOARDLISTNO=?";
//		List<Map<String, Object>> list = queryList(sqlSelect,boardListNo);
//		if(list.size()>0){
//			return Long.parseLong(list.get(0).get("TOTALCOUNT").toString());
//		}
//		return null;
		
	}

	public void saveBlackListHis(String boardListNo,String tableName) {
		String sql = "INSERT INTO TB_BLACKLIST_SEND_HIS (ID, NETNO, CARDCODE, CARDTYPE, OBUID, LICENSE, GENCAU, GENMODE, GENTIME, VERSION, REMARK, UPDATETIME,BOARDLISTNO) "
				+ "SELECT ID, NETNO, CARDCODE, CARDTYPE, OBUID, LICENSE, GENCAU, GENMODE, GENTIME, VERSION, REMARK, UPDATETIME,BOARDLISTNO FROM "+tableName+" WHERE BOARDLISTNO="+boardListNo;
		save(sql);
	}
	
	public void saveBlackListAddition(String boardListNo,String startTime,String endTime,Long versionNo){
//		StringBuffer sql = new StringBuffer("INSERT INTO TB_GBTOLLCARDBLACKDELTA_SEND (ID, BOARDLISTNO, NETNO, CARDCODE, CARDTYPE, OBUID, LICENSE, GENCAU, GENMODE, GENTIME, VERSION, REMARK, UPDATETIME) ");
//		sql.append(" SELECT SEQ_GBTOLLCARDBLACKBASESEND_NO.nextval as ID,"+boardListNo+" AS BOARDLISTNO,NETNO,CARDNO,CARDTYPE,OBUID,LICENSE,STATUS,0 AS GENMODE,GENTIME,"+String.valueOf(versionNo)+" AS VERSION ,null,SYSDATE FROM (");
////		sql.append(" SELECT * FROM  ( ");
////		sql.append(" TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') > '"+startTime+"' AND");
////		sql.append(" TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') <= '"+endTime+"') OR");
////		sql.append(" (FLAG = 1 AND TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') > '"+startTime+"' AND");
////		sql.append(" TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') <= '"+endTime+"' AND");
////		sql.append(" TO_CHAR(SYSDEALTIME, 'yyyymmddhh24miss') > '"+endTime+"')) A");
////		sql.append(" WHERE NOT EXISTS (SELECT * FROM (SELECT NETNO, OBUID, LICENSE, CARDTYPE, CARDNO");
////		sql.append(" FROM CSMS_BLACKLIST_RELIEVE_TEMP WHERE (FLAG = 0 AND TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')<'"+startTime+"')");
////		sql.append(" OR (FLAG = 1 AND TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')<'"+startTime+"' ");
////		sql.append(" AND TO_CHAR(SYSDEALTIME,'yyyymmddhh24miss')>'"+endTime+"')) B where ");
////		sql.append(" nvl(A.Netno,'1')=nvl(B.Netno,'1') and nvl(A.Obuid,'1')=nvl(B.Obuid,'1') and nvl(A.License,'1')=nvl(B.License,'1')");
////		sql.append(" and nvl(A.Cardno,'1')=nvl(B.Cardno,'1')) UNION SELECT ID, NETNO, OBUID, LICENSE, CARDTYPE, CARDNO, GENTIME, 0 AS STATUS, DEALSTATUS, DEALTIME, GENMODE, SYSGENTIME, SYSDEALTIME, FLAG, TEMPID FROM CSMS_BLACKLIST_RELIEVE_TEMP");
////		sql.append(" WHERE FLAG = 1 AND TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') < '"+startTime+"'");
////		sql.append(" AND TO_CHAR(SYSDEALTIME, 'yyyymmddhh24miss') > '"+startTime+"'");
////		sql.append(" AND TO_CHAR(SYSDEALTIME, 'yyyymmddhh24miss') <= '"+endTime+"'");
//		sql.append(" SELECT ID,NETNO,OBUID,LICENSE,CARDTYPE,CARDNO,GENTIME,CASE FLAG WHEN 1 THEN 0 ELSE STATUS END AS STATUS,DEALSTATUS,DEALTIME,GENMODE,SYSGENTIME,SYSDEALTIME,FLAG,TEMPID FROM(");
//		sql.append(" SELECT AA.*,ROW_NUMBER() OVER(PARTITION BY AA.NETNO,AA.OBUID,AA.LICENSE,AA.CARDNO ORDER BY AA.FLAG ASC) RN FROM(");
//		sql.append(" SELECT * FROM CSMS_BLACKLIST_RELIEVE_TEMP WHERE ");
//		sql.append(" (FLAG=0 AND TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') > '"+startTime+"' AND TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') <= '"+endTime+"')");
//		sql.append(" OR");
//		sql.append(" (FLAG=1 AND ");
//		sql.append(" (");
//		sql.append(" (TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') > '"+startTime+"' AND TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') <= '"+endTime+"' AND TO_CHAR(SYSDEALTIME, 'yyyymmddhh24miss') > '"+endTime+"')");
//		sql.append(" OR TO_CHAR(SYSGENTIME, 'yyyymmddhh24miss') < '"+startTime+"' AND TO_CHAR(SYSDEALTIME, 'yyyymmddhh24miss') <= '"+endTime+"' AND TO_CHAR(SYSDEALTIME, 'yyyymmddhh24miss') > '"+startTime+"'");
//		sql.append(" )");
//		sql.append(" )");
//		sql.append(" )AA)BB WHERE BB.RN=1 ");
//		sql.append(" AND NOT EXISTS (SELECT * FROM (SELECT NETNO, OBUID, LICENSE, CARDTYPE, CARDNO FROM CSMS_BLACKLIST_RELIEVE_TEMP WHERE (FLAG = 0 AND TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')<'"+startTime+"') OR (FLAG = 1 AND TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')<'"+startTime+"'  AND TO_CHAR(SYSDEALTIME,'yyyymmddhh24miss')>'"+startTime+"')) B where  nvl(BB.Netno,'1')=nvl(B.Netno,'1') and nvl(BB.Obuid,'1')=nvl(B.Obuid,'1') and nvl(BB.License,'1')=nvl(B.License,'1') and nvl(BB.Cardno,'1')=nvl(B.Cardno,'1') AND BB.FLAG=0)");
//		sql.append(")");
//		
		save("call proc_blacklistaddition_send(?,?,?,?)",boardListNo,versionNo,startTime,endTime);
		
//		String sqlSelect = "SELECT COUNT(1) AS TOTALCOUNT FROM TB_GBTOLLCARDBLACKBASE_SEND WHERE BOARDLISTNO=?";
//		List<Map<String, Object>> list = queryList(sqlSelect,boardListNo);
//		if(list.size()>0){
//			return Long.parseLong(list.get(0).get("TOTALCOUNT").toString());
//		}
//		return null;
	}
	
	public List<BlackListRelieveTemp> findThisBlackList(String  startTime,String endTime){
		StringBuffer sql = new StringBuffer("SELECT * FROM CSMS_BLACKLIST_RELIEVE_TEMP WHERE ");
		sql.append(" TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')>? AND TO_CHAR(SYSGENTIME)<=?");
		sql.append(" AND (FLAG = 0 OR (FLAG = 1 AND TO_CHAR(SYSDEALTIME,'yyyymmddhh24miss')>?))");
		
		List<BlackListRelieveTemp> blackListWaterList = null;
		List<Map<String, Object>> list = queryList(sql.toString(),startTime,endTime,endTime);
		if(list.size()>0){
			blackListWaterList = new ArrayList<BlackListRelieveTemp>();
			for(Map<String, Object> map:list){
				BlackListRelieveTemp fee = new BlackListRelieveTemp();
				fee = (BlackListRelieveTemp) this.convert2Bean(
						(Map<String, Object>) map, new BlackListRelieveTemp());

				blackListWaterList.add(fee);
			}
			
		}
		return blackListWaterList;
	}
	
	public List<BlackListRelieveTemp> findHisBlackList(String startTime,String endTime){
		StringBuffer sql = new StringBuffer("SELECT * FROM CSMS_BLACKLIST_RELIEVE_TEMP WHERE "); 
		sql.append(" TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')<? AND ");
		sql.append(" (FLAG = 0 OR (FLAG = 1 AND TO_CHAR(SYSDEALTIME,'yyyymmddhh24miss')>?))");
		List<BlackListRelieveTemp> blackListWaterList = null;
		List<Map<String, Object>> list = queryList(sql.toString(),startTime,endTime);
		if(list.size()>0){
			blackListWaterList = new ArrayList<BlackListRelieveTemp>();
			for(Map<String, Object> map:list){
				BlackListRelieveTemp fee = new BlackListRelieveTemp();
				fee = (BlackListRelieveTemp) this.convert2Bean(
						(Map<String, Object>) map, new BlackListRelieveTemp());

				blackListWaterList.add(fee);
			}
			
		}
		return blackListWaterList;
	}
	
	public List<BlackListRelieveTemp> findThisRelieve(String startTime,String endTime){
		StringBuffer sql = new StringBuffer("SELECT * FROM CSMS_BLACKLIST_RELIEVE_TEMP WHERE "); 
		sql.append(" FLAG = 1 AND TO_CHAR(SYSGENTIME,'yyyymmddhh24miss')<? ");
		sql.append(" AND TO_CHAR(SYSDEALTIME,'yyyymmddhh24miss')>? AND TO_CHAR(SYSDEALTIME)<=?");
		List<BlackListRelieveTemp> blackListWaterList = null;
		List<Map<String, Object>> list = queryList(sql.toString(),startTime,startTime,endTime);
		if(list.size()>0){
			blackListWaterList = new ArrayList<BlackListRelieveTemp>();
			for(Map<String, Object> map:list){
				BlackListRelieveTemp fee = new BlackListRelieveTemp();
				fee = (BlackListRelieveTemp) this.convert2Bean(
						(Map<String, Object>) map, new BlackListRelieveTemp());

				blackListWaterList.add(fee);
			}
			
		}
		return blackListWaterList;
	}

	public BlackListVersion findLastVersion(BlackListVersion blackListVersion) {
		String sql="SELECT * FROM CSMS_BLACKLIST_VERSION WHERE ROWNUM = 1 AND VERSIONNO <"+blackListVersion.getVersionNo()+" ORDER BY VERSIONNO DESC";
		List<Map<String, Object>> list = queryList(sql);
		BlackListVersion version = null;
		if (!list.isEmpty()) {
			version = new BlackListVersion();
			this.convert2Bean(list.get(0), version);
		}
		return version;
	}

	public void importStopPayCard(String netno, String accountType,String boardListNo) {
		StringBuilder sql = new StringBuilder("INSERT INTO CSMS_BLACK_LIST_WATER (ID, NETNO, OBUID, LICENSE, CARDTYPE, CARDNO, GENTIME, GENTYPE, STATUS, FLAG)");
		sql.append(" SELECT SEQ_CSMSBLACKLISTWATER_NO.NEXTVAL,'"+netno+"',NULL,NULL,'"+accountType+"',AA.CARDCODE,AA.GENTIME,1,CASE AA.FLAG WHEN 3 THEN -4 ELSE 4 END AS STATUS,0 AS FLAG FROM (");
		sql.append(" SELECT ID,BOARDLISTNO,CARDCODE,ACBACCOUNT,GENTIME,CASE FLAG WHEN 3 THEN 3 ELSE 0 END AS FLAG, ");
		sql.append(" UPDATETIME,REMARK,RECEVICETIME,FILENAME FROM CSMS_PAYMENTCARDBLACKLIST WHERE BOARDLISTNO="+boardListNo+") AA LEFT JOIN ");
		sql.append(" ( ");
		sql.append(" SELECT ID,BOARDLISTNO,CARDCODE,ACBACCOUNT,GENTIME,CASE FLAG WHEN 3 THEN 3 ELSE 0 END AS FLAG, ");
		sql.append("UPDATETIME,REMARK,RECEVICETIME,FILENAME FROM TB_PAYMENTCARDBLACKLIST_HIS) BB ");
		sql.append("ON AA.CARDCODE = BB.CARDCODE AND AA.GENTIME = BB.GENTIME AND AA.FLAG =BB.FLAG WHERE BB.CARDCODE IS NULL");
		save(sql.toString());
	}

	public void deleteStopPayCardHis() {
		String sql="DELETE FROM TB_PAYMENTCARDBLACKLIST_HIS";
		delete(sql);
		
	}

	public void importStopPayCardHis(String boardListNo) {
		String sql="INSERT INTO TB_PAYMENTCARDBLACKLIST_HIS SELECT * FROM TB_PAYMENTCARDBLACKLIST_RECV WHERE BOARDLISTNO=?";
		save(sql, boardListNo);
	}

	public List<ProviceRecvBoard> findRecvByTableCode(String tableCode) {
		String sql = "SELECT * FROM TB_PROVICERECVBOARD WHERE TABLECODE = ? ORDER BY LISTNO ASC";
		return super.queryObjectList(sql, ProviceRecvBoard.class, tableCode);
	}

	public Long saveGBBlackList(String boardListNo) {

		StringBuffer sql = new StringBuffer("INSERT INTO TB_GBTOLLCARDBLACKBASE_SEND (ID, BOARDLISTNO, NETNO, CARDCODE, CARDTYPE, OBUID, LICENSE, GENCAU, GENMODE, GENTIME, VERSION, REMARK, UPDATETIME) ");
		sql.append(" SELECT ID, BOARDLISTNO, NETNO, CARDCODE, CARDTYPE, OBUID, LICENSE, GENCAU, GENMODE, GENTIME, VERSION, REMARK, UPDATETIME FROM TB_BLACKLIST_SEND_HIS WHERE BOARDLISTNO = "+boardListNo);
		sql.append(" AND LENGTH(CARDCODE)=16 ");
		save(sql.toString());
		
		String sqlSelect = "SELECT COUNT(1) AS TOTALCOUNT FROM TB_GBTOLLCARDBLACKBASE_SEND WHERE BOARDLISTNO=?";
		List<Map<String, Object>> list = queryList(sqlSelect,boardListNo);
		if(list.size()>0){
			return Long.parseLong(list.get(0).get("TOTALCOUNT").toString());
		}
		return null;
	}
	
	public Long saveLocalBlackList(String boardListNo,String boardListNoLocal) {

		StringBuffer sql = new StringBuffer("INSERT INTO TB_DBTOLLCARDBLACKLIST_SEND (ID,BOARDLISTNO,CARDCODE,CARDTYPE,GENCAU,GENMODE,GENTIME,REMARK,UPDATETIME) ");
		sql.append(" SELECT ID, '"+boardListNoLocal+"', CARDCODE, CARDTYPE, GENCAU, GENMODE, GENTIME, REMARK, UPDATETIME FROM TB_BLACKLIST_SEND_HIS WHERE BOARDLISTNO = "+boardListNo);
		sql.append(" AND LENGTH(CARDCODE)<>16 ");
		save(sql.toString());
		
		String sqlSelect = "SELECT COUNT(1) AS TOTALCOUNT FROM TB_DBTOLLCARDBLACKLIST_SEND WHERE BOARDLISTNO=?";
		List<Map<String, Object>> list = queryList(sqlSelect,boardListNoLocal);
		if(list.size()>0){
			return Long.parseLong(list.get(0).get("TOTALCOUNT").toString());
		}
		return null;
	}

	public String findCurrentStopPayBoardNo() {
		String sql = "SELECT BOARDLISTNO FROM TB_PAYMENTCARDBLACKLIST_HIS WHERE BOARDLISTNO IS NOT NULL AND ROWNUM=1";
		List<Map<String, Object>> list = queryList(sql);
		if(list!=null&&list.size()>0){
			return String.valueOf(list.get(0).get("BOARDLISTNO").toString());
		}
		return null;
	}

	public String findNextStopPayBoardNo(String currentBoardNo) {
		String sql = "SELECT BOARDLISTNO FROM (SELECT BOARDLISTNO FROM CSMS_PAYMENTCARDBLACKLIST WHERE BOARDLISTNO>? ORDER BY BOARDLISTNO ASC) AA WHERE ROWNUM=1";
		List<Map<String, Object>> list = queryList(sql,currentBoardNo);
		if(list!=null&&list.size()>0){
			return String.valueOf(list.get(0).get("BOARDLISTNO").toString());
		}
		return null;
	}

	public void deletePerStopPayCard(String nextBoardNo) {
		String sql = "DELETE FROM CSMS_PAYMENTCARDBLACKLIST WHERE BOARDLISTNO<?";
		delete(sql,nextBoardNo);
	}

	public boolean isInBlackList(String cardNo) {
		String sql="select count(*) from CSMS_BLACKLIST_TEMP where cardno=?";
		int count = this.count(sql, cardNo);
		return count > 0;
	}
}
