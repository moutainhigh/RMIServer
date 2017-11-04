package com.hgsoft.acms.obu.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.obu.entity.TagTakeInfoHis;
import com.hgsoft.utils.SequenceUtil;

@Component
public class TagTakeInfoHisDaoACMS extends BaseDao{
	@Resource
	SequenceUtil sequenceUtil;
	@Resource
	private TagTakeInfoDaoACMS tagTakeInfoDaoACMS;
	
	/**
	 * 保存修改电子标签提货金额之前的信息（备份）
	 * 
	 * @param tagTakeInfo
	 * @param tagTakeInfoHis
	 */
	public void save(TagTakeInfo tagTakeInfo,TagTakeInfoHis tagTakeInfoHis) {
		
		tagTakeInfo = tagTakeInfoDaoACMS.findById(tagTakeInfo.getId());
		
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_TagTake_InfoHis(ID,clientName,certType,certNumber,"
				+ "Begin_TagNo,End_TagNo,TakeAmount,TagPrice,TotalPrice,cost,"
				+ "OperID,TakeplaceID,operNo,operName,placeNo,placeName,TakeDate,memo,HisSeqID,receiptId,tagtakeid,"
				+ "CREATEDATE,CREATEREASON) select "+tagTakeInfoHis.getId()+",clientName,certType,certNumber,"
				+ "Begin_TagNo,End_TagNo,TakeAmount,TagPrice,TotalPrice,cost,"
				+ "OperID,TakeplaceID,operNo,operName,placeNo,placeName,TakeDate,memo,HisSeqID" +
				",receiptId,tagtakeid,sysdate,'"+tagTakeInfoHis.getCreateReason()+"' from csms_tagtake_info where id = "+tagTakeInfo.getId());
		save(sql.toString());
	}
}
