package com.hgsoft.directorAudite.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.account.entity.RefundInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Repository
public class DirectorAuditeDao extends BaseDao{
	/**
	 * 
	 * @param pager
	 * @param refundApplyStartTime
	 * @param refundApplyEndTime
	 * @param directorAuditStartTime
	 * @param directorAuditEndTime
	 * @param refundInfo
	 * @return Pager
	 */
	public Pager findDirectorAuditePager(Pager pager,String refundApplyStartTime,String refundApplyEndTime,String directorAuditStartTime
			,String directorAuditEndTime,RefundInfo refundInfo,Long bussinessPlaceId){
		StringBuffer sql = new StringBuffer(
				  " select c.organ,c.idType,c.idCode,r.id refundInfoId,r.refundType,r.currentRefundBalance,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,"
				+ " r.auditStatus,r.directorAuditName,r.directorAuditTime,r.cardNo from CSMS_RefundInfo r"
				+ " join CSMS_Customer c on c.id=r.mainID where 1=1 ");
		SqlParamer param = new SqlParamer();
		
		param.eq("r.bussinessPlaceId", bussinessPlaceId);//主任所属营业部
		
		//进入该界面。查询结果默认最近24小时未审核的退款申请记录
		if(!StringUtil.isNotBlank(refundApplyStartTime) && !StringUtil.isNotBlank(refundApplyEndTime) && !StringUtil.isNotBlank(directorAuditStartTime)
				&& !StringUtil.isNotBlank(directorAuditEndTime) && refundInfo == null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar now = Calendar.getInstance();
			String nowDate = dateFormat.format(now.getTime());		
			now.add(Calendar.DATE, -1);//减一天
			String beforeDate = dateFormat.format(now.getTime());
			
			//24小时内
			param.geDate("r.refundApplyTime", beforeDate);
			param.leDate("r.refundApplyTime", nowDate);
			param.eq("r.auditStatus", "1");//审核状态为申请的记录
			
		}else{
			if(StringUtil.isNotBlank(refundApplyStartTime)){
				param.geDate("r.refundApplyTime", refundApplyStartTime + " 00:00:00");
			}
			if(StringUtil.isNotBlank(refundApplyEndTime)){
				param.leDate("r.refundApplyTime", refundApplyEndTime + " 23:59:59");
			}
			if(StringUtil.isNotBlank(directorAuditStartTime)){
				param.geDate("r.directorAuditTime", directorAuditStartTime);
			}
			if(StringUtil.isNotBlank(directorAuditEndTime)){
				param.leDate("r.directorAuditTime", directorAuditEndTime);
			}
			if(refundInfo != null){
				if(StringUtil.isNotBlank(refundInfo.getRefundType())){
					param.eq("r.refundType", refundInfo.getRefundType());
				}
				if(StringUtil.isNotBlank(refundInfo.getAuditStatus())){
					param.eq("r.auditStatus", refundInfo.getAuditStatus());
				}
			}
		}
		
		
		sql.append(param.getParam());
		sql.append(" order by r.id desc ");
		return findByPages(sql.toString(),pager, param.getList().toArray());
	}
	/**
	 * 根据退款记录id查询详情
	 * @param refundInfoId
	 * @return Map<String,Object>
	 */
	public Map<String, Object> findDirectorAuditeDetail(Long refundInfoId){
		String sql =  
				  " select c.id customerId,c.organ,c.idType,c.idCode,c.secondNo,c.secondName,c.userType,r.id refundInfoId,r.refundType,r.currentRefundBalance,r.bankNo,r.bankMember,r.bankOpenBranches,r.refundApplyTime,"
				+ " r.auditStatus,r.directorAuditName,r.directorAuditTime,r.cardNo,r.operName,r.placeName,r.memo from CSMS_RefundInfo r"
				+ " join CSMS_Customer c on c.id=r.mainID where r.id=? ";
		List<Map<String, Object>> list = queryList(sql, refundInfoId);
		if(list.size() == 1) return list.get(0);
		else return null;
		
	}
}
