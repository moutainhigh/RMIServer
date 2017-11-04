package com.hgsoft.obu.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.obu.entity.TagTakeFeeInfoHis;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TagTakeInfoDao extends BaseDao {
	
	
	public Pager findAllTagTakeInfos(Pager pager,TagTakeInfo tagTakeInfo,String tagNo,String operName,Date starTime,Date endTime){
		//List list = new ArrayList<TagTakeFeeInfo>();
		//v.*,row_number() over (order by v.ID) as num from CSMS_Vehicle_Info v
		String sql = "select ID,ClientName,CertType,CertNumber,Begin_TagNo,End_TagNo,"
				+ "OperID,TakeplaceID,TakeDate,isDaySet,settleDay,settletTime,memo,operName,placeName,operno,placeno,receiptid,"
				+ "ROWNUM as num "
				+ "from CSMS_TagTake_Info v where 1=1";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*if(tagTakeInfo !=null){
			if(tagTakeInfo.getClientName()!=null&&!"".equals(tagTakeInfo.getClientName())){
				sql+="and ClientName = '"+tagTakeInfo.getClientName()+"' ";
			}
			if(tagNo!=null&&!"".equals(tagNo)){
				sql+="and '"+tagNo+"' between Begin_TagNo and End_TagNo ";
			}
			if(StringUtil.isNotBlank(operName)){
				
			}
			if(starTime !=null){
				sql=sql+(" and TakeDate >= to_date('"+format.format((starTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			if(endTime !=null){
				sql=sql+(" and TakeDate <=to_date('"+format.format((endTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
		}*/
		SqlParamer params=new SqlParamer();
		
		if(tagTakeInfo != null){
			if(StringUtil.isNotBlank(tagTakeInfo.getClientName())){
				params.eq("ClientName", tagTakeInfo.getClientName());
			}
			if(StringUtil.isNotBlank(tagTakeInfo.getOperName())){
				params.eq("operName", tagTakeInfo.getOperName());
			}
		}
		if(StringUtil.isNotBlank(tagNo)){
			sql+=" and '"+tagNo+"' between Begin_TagNo and End_TagNo ";
		}
		if(StringUtil.isNotBlank(operName)){
			
		}
		if(starTime != null){
			params.geDate("TakeDate", format.format(starTime));
		}
		if(endTime != null){
			params.leDate("TakeDate", format.format(endTime));
		}
		
		sql=sql+params.getParam();
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql=sql+(" order by v.TakeDate desc ");
		pager = this.findByPages(sql, pager,Objects);
		//pager = findByPages(sql, pager, null);
		//list = queryList(sql);
		
		return pager;
	}

	/**
	 * 根据数据id查找对应对象
	 * 
	 * @param id
	 * @return
	 */
	public TagTakeInfo findById(Long id) {
		TagTakeInfo tagTakeInfo = new TagTakeInfo();
		tagTakeInfo.setId(id);
		return findByTagTakeInfo(tagTakeInfo);

	}
	public TagTakeInfo findByHisId(Long id) {
		TagTakeInfo tagTakeInfo = new TagTakeInfo();
		tagTakeInfo.setHisSeqID(id);
		return findByTagTakeInfo(tagTakeInfo);

	}
	public TagTakeInfo findByReceiptId(Long id) {
		TagTakeInfo tagTakeInfo = new TagTakeInfo();
		tagTakeInfo.setReceiptId(id);
		return findByTagTakeInfo(tagTakeInfo);

	} 
	public TagTakeInfo findByTagTakeInfo(TagTakeInfo tagTakeInfo){

		StringBuffer sql = new StringBuffer("select * from CSMS_TagTake_Info ");

		Map map = FieldUtil.getPreFieldMap(TagTakeInfo.class,tagTakeInfo);
		String condition = (String) map.get("selectNameStrNotNull");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("TagTakeInfoDao.findByTagTakeInfo条件为空");
		}
		sql.append(condition);
		sql.append(" order by id desc");
		List<TagTakeInfo> tagTakeInfos = super.queryObjectList(sql.toString(), TagTakeInfo.class, ((List) map.get("paramNotNull")).toArray());
		if (tagTakeInfos == null || tagTakeInfos.isEmpty()) {
			return null;
		}
		return tagTakeInfos.get(0);
	}
	public TagTakeFeeInfoHis findFromHisByHisId(Long id) {
		String sql = "select * from csms_tagtake_infohis where His_SeqID=?";
		List<TagTakeFeeInfoHis> tagTakeFeeInfoHises = super.queryObjectList(sql.toString(), TagTakeFeeInfoHis.class, id);
		if (tagTakeFeeInfoHises == null || tagTakeFeeInfoHises.isEmpty()) {
			return null;
		}
		return tagTakeFeeInfoHises.get(0);
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
	
	/**
	/**
	 * 保存登记电子标签提货金额
	 * 
	 * @param tagTakeInfo
	 */
	public void save(TagTakeInfo tagTakeInfo) {
		tagTakeInfo.setHisSeqID(-tagTakeInfo.getId());
		Map map = FieldUtil.getPreFieldMap(TagTakeInfo.class,tagTakeInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_TagTake_Info ");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		
		/*StringBuffer sql = new StringBuffer(
				"insert into CSMS_TagTake_Info(id,clientName,certType,"
				+ "certNumber,Begin_TagNo,End_TagNo,TakeAmount,TagPrice,"
				+ "TotalPrice,OperID,TakeplaceID,TakeDate,"
				+ "memo,HisSeqID) values(");
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(tagTakeInfo.getId() == null){
			sql.append("NULL,");
		}else{
			sql.append(tagTakeInfo.getId() + ",");
		}
		if (tagTakeInfo.getClientName() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeInfo.getClientName() + "',");
		}
		if (tagTakeInfo.getCertType() == null) {
			sql.append("1,");
		} else {
			sql.append(tagTakeInfo.getCertType() + ",");
		}
		if (tagTakeInfo.getCertNumber() == null) {
			sql.append("110,");
		} else {
			sql.append("'" + tagTakeInfo.getCertNumber() + "',");
		}
		if (tagTakeInfo.getBegin_TagNo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeInfo.getBegin_TagNo() + "',");
		}
		if (tagTakeInfo.getEnd_TagNo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeInfo.getEnd_TagNo() + "',");
		}
		if (tagTakeInfo.getTakeAmount() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeInfo.getTakeAmount() + "',");
		}
		if (tagTakeInfo.getTagPrice() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeInfo.getTagPrice() + "',");
		}
		if (tagTakeInfo.getTotalPrice() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeInfo.getTotalPrice() + ",");
		}
		if (tagTakeInfo.getOperID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeInfo.getOperID() + ",");
		}
		if (tagTakeInfo.getTakeplaceID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeInfo.getTakeplaceID() + ",");
		}
		if (tagTakeInfo.getTakeDate() == null) {
			sql.append("sysdate,");
		} else {
			sql.append("to_date('"
					+ format.format(tagTakeInfo.getTakeDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		if (tagTakeInfo.getMemo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeInfo.getMemo() + "',");
		}
		if (tagTakeInfo.getHisSeqID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeInfo.getHisSeqID() + ",");
		}

		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);*/
		
		
	}
	
	/**
	 * 删除一条电子标签提货信息
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		String sql = "delete from csms_tagtake_info where id=?";
		super.delete(sql,id);
	}
	
	/**
	 * 找出电子标签提货金额登记表中，提货登记余额>0的记录。
	 * @return
	 */
	public List findTagTakeInfoByTakeBalance(TagTakeInfo tagTakeInfo){
		
		String sql = "select distinct i.ID,ClientName,CertType,CertNumber,ChargeFee,"
				+ "ChargeType,PayAccount,TakeBalance,i.operName as registername,r.receiptNo,"
				+ "to_char(RegisterDate,'yyyy-MM-dd HH24:mi:ss') as RegisterDate,ModifyOperID,ModifyDate,Memo,His_SeqID from "
				+ "CSMS_TagTakeFee_Info i "
				+ " join CSMS_RECEIPT r on i.receiptId=r.id  "
				+ " where TakeBalance>? and ClientName=? order by i.id desc ";
		
		List<Map<String, Object>> list = queryList(sql, 0,tagTakeInfo.getClientName());
		
		return list;
	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		StringBuffer sql=new StringBuffer("update csms_tagtake_info set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(takeDate,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(takeDate,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		saveOrUpdate(sql.toString(),settleDay,startTime,endTime);
	}
	
}
