package com.hgsoft.obu.dao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagInfoHis;
import com.hgsoft.utils.SequenceUtil;

@Component
public class TagInfoHisDao extends BaseDao {
	@Autowired
	private SequenceUtil sequenceUtil;
	
	/**
	 * 保存电子标签历史
	 * @param tagInfo 查找出来的电子标签信息记录
	 * @param createReason  历史产生原因
	 * @return Long 返回历史id
	 */
	public Long saveTagInfoHis(TagInfo tagInfo,String createReason){
		TagInfoHis tagInfoHis = new TagInfoHis();
		BigDecimal seq = sequenceUtil.getSequence("SEQ_CSMSTaginfoHis_NO");
		tagInfoHis.setId(Long.parseLong(seq.toString()));
		tagInfoHis.setCreateReason(createReason);
		this.saveHis(tagInfoHis, tagInfo);
		return tagInfoHis.getId();
	}

	public void saveHis(TagInfoHis tagInfoHis,TagInfo tagInfo){
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_Tag_infoHis(ID,TagNo,ClientID,IssueType,SalesType,Installman,"
				+ "ChargeCost,OperID,IssueplaceID,operNo,operName,placeNo,placeName,Issuetime,MaintenanceTime,"
				+ "StartTime,EndTime,TagState,HisSeqID,CreateDate,CreateReason,CorrectOperID,correctTime," +
				"correctPlaceID,IsDaySet,SettleDay,SettletTime,CorrectOperNo,CorrectOperName,correctPlaceNo," +
				"correctPlaceName,WriteBackFlag,productType,Cost,InstallmanName,blackFlag,obuSerial )"
				+ "SELECT "+tagInfoHis.getId()+",TagNo,ClientID,IssueType,"
				+ "SalesType,Installman,ChargeCost,OperID,IssueplaceID,operNo,operName,placeNo,placeName,"
				+ "Issuetime,MaintenanceTime,StartTime,EndTime,TagState,HisSeqID,"
				+ "sysdate,'"+tagInfoHis.getCreateReason()+"',CorrectOperID,correctTime," +
						"correctPlaceID,IsDaySet,SettleDay,SettletTime,CorrectOperNo,CorrectOperName," +
						"correctPlaceNo,correctPlaceName,WriteBackFlag,productType,Cost,InstallmanName,blackFlag,obuSerial FROM CSMS_Tag_info WHERE ID="+tagInfo.getId()+"");
		save(sql.toString());
	}
}
