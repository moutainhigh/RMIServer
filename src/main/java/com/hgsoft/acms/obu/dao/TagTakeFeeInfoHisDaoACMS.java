package com.hgsoft.acms.obu.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeFeeInfoHis;
import com.hgsoft.utils.SequenceUtil;

@Component
public class TagTakeFeeInfoHisDaoACMS extends BaseDao{
	
	@Resource
	private TagTakeFeeInfoDaoACMS tagTakeFeeInfoDaoACMS;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	/**
	 * 保存修改电子标签提货金额之前的信息（备份）
	 * 
	 * @param tagTakeFeeInfo
	 */
	public void save(TagTakeFeeInfo tagTakeFeeInfo,TagTakeFeeInfoHis tagTakeFeeInfoHis) {
		
		/*tagTakeFeeInfo = tagTakeFeeInfoDao.findById(tagTakeFeeInfo.getId());
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_TAGTAKEFEE_INFOHIS(ID,clientName,certType,certNumber,chargeFee,chargeType,payAccount,takeBalance,registerOperID,operNo,operName,placeNo,placeName,registerDate,modifyOperID,ModifyDate,memo,His_SeqID,CREATEDATE,CREATEREASON) values(");
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(tagTakeFeeInfoHis.getId() == null){
			sql.append("NULL,");
		}else{
			sql.append(tagTakeFeeInfoHis.getId()+ ",");
		}
		if (tagTakeFeeInfo.getClientName() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getClientName() + "',");
		}
		if (tagTakeFeeInfo.getCertType() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getCertType() + "',");
		}
		if (tagTakeFeeInfo.getCertNumber() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getCertNumber() + "',");
		}
		if (tagTakeFeeInfo.getChargeFee() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getChargeFee() + "',");
		}
		if (tagTakeFeeInfo.getChargeType() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getChargeType() + "',");
		}
		if (tagTakeFeeInfo.getPayAccount() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getPayAccount() + "',");
		}
		if (tagTakeFeeInfo.getTakeBalance() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getTakeBalance() + "',");
		}
		if (tagTakeFeeInfo.getRegisterOperID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getRegisterOperID() + ",");
		}
		
		if (StringUtil.isNotBlank(tagTakeFeeInfo.getOperNo())) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getOperNo() + "',");
		}
		if (StringUtil.isNotBlank(tagTakeFeeInfo.getOperName())) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getOperName() + "',");
		}
		if (StringUtil.isNotBlank(tagTakeFeeInfo.getPlaceNo())) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getPlaceNo() + "',");
		}
		if (StringUtil.isNotBlank(tagTakeFeeInfo.getPlaceName())) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getPlaceName() + "',");
		}
		
		
		if (tagTakeFeeInfo.getRegisterDate() == null) {
			sql.append("sysdate,");
		} else {
			sql.append("to_date('"
					+ format.format(tagTakeFeeInfo.getRegisterDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		if (tagTakeFeeInfo.getModifyOperID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getModifyOperID() + ",");
		}
		if (tagTakeFeeInfo.getModifyDate() == null) {
			sql.append("sysdate,");
		} else {
			sql.append("to_date('"
					+ format.format(tagTakeFeeInfo.getModifyDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		if (tagTakeFeeInfo.getMemo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'"+tagTakeFeeInfo.getMemo() + "',");
		}
		if (tagTakeFeeInfo.getHis_SeqID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getHis_SeqID() + ",");
		}
		
		//操作日期，操作原因（历史）
		sql.append("to_date('"
				+ format.format(tagTakeFeeInfoHis.getCreateDate())
				+ "','YYYY-MM-DD HH24:MI:SS'),");
		sql.append("'"+tagTakeFeeInfoHis.getCreateReason() + "',");
		
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);*/
		
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_TagTakeFee_InfoHis(ID,ClientName,CertType,CertNumber,ChargeFee,ChargeType,PayAccount,TakeBalance,RegisterOperID,RegisterDate,RegisterPlace,ModifyOperID,ModifyDate,Memo,His_SeqID,operNo,operName,placeNo,placeName,modifyOperNo,modifyOperName,receiptId,bankTransferInfoId,voucherNo,CreateDate,CreateReason )"
				+ "SELECT "+tagTakeFeeInfoHis.getId()+",ClientName,CertType,CertNumber,ChargeFee,ChargeType,PayAccount,TakeBalance,RegisterOperID,RegisterDate,RegisterPlace,ModifyOperID,ModifyDate,Memo,His_SeqID,operNo,operName,placeNo,placeName,modifyOperNo,modifyOperName,receiptId,bankTransferInfoId,voucherNo,"
				+ "sysdate,'"+tagTakeFeeInfoHis.getCreateReason()+"' FROM CSMS_TagTakeFee_Info WHERE ID="+tagTakeFeeInfo.getId()+"");
		save(sql.toString());
	}
}
