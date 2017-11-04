package com.hgsoft.acms.obu.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.utils.SequenceUtil;

@Component
public class TagTakeDetailHisDaoACMS extends BaseDao{
	@Resource
	private SequenceUtil sequenceUtil;
	
	public void save(TagTakeDetail tagTakeDetail){
		
		//SEQ_CSMSTagTakeFeeInfoHis_NO
		BigDecimal SEQ_CSMSTagTakeDetailHis_NO = sequenceUtil.getSequence("SEQ_CSMSTagTakeDetailHis_NO");
		Long id = Long.valueOf(SEQ_CSMSTagTakeDetailHis_NO.toString());
		
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_TagTake_DetailHis(id,MainID,TagNo,"
				+ "TagState,Memo) values(");
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(id == null){
			sql.append("NULL,");
		}else{
			sql.append(id + ",");
		}
		if (tagTakeDetail.getMainID() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeDetail.getMainID() + "',");
		}
		if (tagTakeDetail.getTagNo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeDetail.getTagNo() + "',");
		}
		if (tagTakeDetail.getTagState() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeDetail.getTagState() + "',");
		}
		if (tagTakeDetail.getMemo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeDetail.getMemo() + "',");
		}
		
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);
	}
}
