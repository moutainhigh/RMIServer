package com.hgsoft.obu.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.obu.entity.TagTakeDetail;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TagTakeDetailDao extends BaseDao{
	/**
	 * 保存每一个电子标签提货明细
	 * @param tagTakeDetail
	 */
	public void save(TagTakeDetail tagTakeDetail){
		
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_TagTake_Detail(id,MainID,TagNo,"
				+ "TagState,Memo,obuSerial,startTime,endTime) values(");
		String sqlString = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(tagTakeDetail.getId() == null){
			sql.append("NULL,");
		}else{
			sql.append(tagTakeDetail.getId() + ",");
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
		if (tagTakeDetail.getObuSerial() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeDetail.getObuSerial() + "',");
		}
		if (tagTakeDetail.getStartTime() == null) {
			sql.append("NULL,");
		} else {
			sql.append("to_date('"+format.format(tagTakeDetail.getStartTime())+"','YYYY-MM-DD HH24:MI:SS'),");
		}
		if (tagTakeDetail.getEndTime() == null) {
			sql.append("NULL,");
		} else {
			sql.append("to_date('"+format.format(tagTakeDetail.getEndTime())+"','YYYY-MM-DD HH24:MI:SS'),");
		}
		
		
		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";
		save(sqlString);
		
	}
	/**
	 * 
	 * @param id
	 */
	public void delete(Long id){
		String sql = "delete from CSMS_TagTake_Detail where MainID=?";

		delete(sql, id);
	}
	
	public void updateTagStateByTagNo(String state,String tagNo){
		String sql="update csms_tagtake_detail set tagstate=? where tagNo=?";
		this.jdbcUtil.getJdbcTemplate().update(sql, state, tagNo);
	}

	public TagTakeDetail findByTagNo(String tagNo) {
		String sql = "select * from CSMS_TagTake_Detail where TagNo=?";
		List<TagTakeDetail> tagTakeDetails = super.queryObjectList(sql, TagTakeDetail.class, tagNo);
		if (tagTakeDetails == null || tagTakeDetails.isEmpty()) {
			return null;
		}
		return tagTakeDetails.get(0);
	}

	public TagTakeDetail findByTagNoAndNotIssue(String tagNo) {

		String sql = "select * from CSMS_TagTake_Detail where TagNo=? and TagState=?";
		List<TagTakeDetail> tagTakeDetails = super.queryObjectList(sql, TagTakeDetail.class, tagNo, "0");
		if (tagTakeDetails == null || tagTakeDetails.isEmpty()) {
			return null;
		}
		return tagTakeDetails.get(0);
	}
	
	
	/**
	 * 根据电子标签明细表的MainID（电子标签提货id）查询出电子标签明细信息
	 * @param id
	 * @return
	 */
	public List<TagTakeDetail> findTagTakeDetailsByMainID(Long id){
		String sql = "select id,MainID,TagNo,"
				+ "TagState,Memo,obuSerial from CSMS_TagTake_Detail where MainID=?";
		return super.queryObjectList(sql, TagTakeDetail.class, id);
	}

	public TagTakeDetail findByTagNo(String beginNo, String endNo) {
		String sql = "select * from CSMS_TagTake_Detail where TagNo=? or TagNo=? ";
		List<TagTakeDetail> tagTakeDetails = super.queryObjectList(sql, TagTakeDetail.class, beginNo, endNo);
		if (tagTakeDetails == null || tagTakeDetails.isEmpty()) {
			return null;
		}
		return tagTakeDetails.get(0);
	}
}
