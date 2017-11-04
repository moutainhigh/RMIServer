package com.hgsoft.prepaidC.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hgsoft.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hgsoft.common.Enum.DBACardFlowEndFlagEnum;
import com.hgsoft.common.Enum.DBACardFlowExpireFlagEnum;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.serviceInterface.IOmsParamInterfaceService;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class DbasCardFlowDao extends BaseDao{

	@Resource
	private IOmsParamInterfaceService omsParamInterfaceService;
	
	public int[] batchUpdateFlagAndCardAmt(final List<Map<String,Object>> list) {  
        String sql = "update csms_dbascardflow set expireFlag='0',cardAmt=? where newCardNo = ? and serType='01' ";
    	return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i)throws java.sql.SQLException {
				Map<String,Object> map = list.get(i);
				ps.setBigDecimal(1, (BigDecimal)map.get("BALANCE"));
				ps.setString(2, (String)map.get("CARDNO"));
			}
			
			@Override
			public int getBatchSize() {
				 return list.size();
			}
		});
    }
	
	public void save(DbasCardFlow dbasCardFlow) {
		//获取资金争议期到期时间
		Map<String,Object> omsParamInterfaceMap = omsParamInterfaceService.findOmsParam("Capital dispute period");
		//营运系统访问成功
		if("0".equals(omsParamInterfaceMap.get("flag"))){
			//获取资金争议期
			Integer expireTime = Integer.parseInt((String)omsParamInterfaceMap.get("value"));
			if(dbasCardFlow.getExpireFlag()==null||StringUtil.isEquals(dbasCardFlow.getExpireFlag(), DBACardFlowExpireFlagEnum.disDispute.getValue())) {
				dbasCardFlow.setExpireTime(DateUtil.addDay(new Date(),expireTime));
			}else {
				dbasCardFlow.setExpireTime(dbasCardFlow.getApplyTime());
			}
			
		}
		Map map = FieldUtil.getPreFieldMap(DbasCardFlow.class,dbasCardFlow);
		StringBuffer sql=new StringBuffer("insert into CSMS_DBASCARDFLOW");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public void update(DbasCardFlow dbasCardFlow) {
		Map map = FieldUtil.getPreFieldMap(DbasCardFlow.class,dbasCardFlow);
		StringBuffer sql=new StringBuffer("update CSMS_DBASCARDFLOW set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),dbasCardFlow.getId());
	}
	
	public void updateNotNull(DbasCardFlow dbasCardFlow) {
		Map map = FieldUtil.getPreFieldMap(DbasCardFlow.class,dbasCardFlow);
		StringBuffer sql=new StringBuffer("update CSMS_DBASCARDFLOW set ");
		sql.append(map.get("updateNameStrNotNull") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("paramNotNull"),dbasCardFlow.getId());
	}
	
	public List<Map<String, Object>> findAll(DbasCardFlow dbasCardFlow) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (dbasCardFlow == null) {
			return list;
		}
		Map map = FieldUtil.getPreFieldMap(DbasCardFlow.class,dbasCardFlow);
		String condition = (String) map.get("selectNameStrNotNullAndWhere");
		if (StringUtils.isBlank(condition)) {
			throw new ApplicationException("DbasCardFlowDao.findAll查询条件为空");
		}
		StringBuffer sql = new StringBuffer("select * from CSMS_DBASCARDFLOW where 1=1 ");
		sql.append(condition);
		list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());

		return list;
	}
	public DbasCardFlow find(String cardNo){
		DbasCardFlow temp = null;
		StringBuffer sql = new StringBuffer("select * from csms_dbascardflow  where newCardNo = ? order by  applyTime desc fetch first 1 rows only");
		List<DbasCardFlow> dbasCardFlows = super.queryObjectList(sql.toString(), DbasCardFlow.class, cardNo);
		if (dbasCardFlows == null || dbasCardFlows.isEmpty()) {
			return null;
		}
		return dbasCardFlows.get(0);
	}
	
	public List<Map<String, Object>> findCardChangeFlow(String cardNo) {
		StringBuffer sql = new StringBuffer("select newCardNo \"newCardNo\",oldCardNo \"oldCardNo\"," +
				" to_char(applyTime,'YYYYMMDDHH24MISS') as \"time\",placeName \"placeName\"," +
				" (CASE serType  WHEN '02' THEN '2'  WHEN '11' THEN '3'  ELSE '1' END) as \"type\"  " +
				" from CSMS_DBASCARDFLOW where sertype in('24','27','02','11')  ");
		sql.append(" and cardNo = ? ");
		sql.append(" order by applytime desc");
		return queryList(sql.toString(),cardNo);
	}
	
	public List<Map<String, Object>> findCards(String cardNo) {
		StringBuffer sql = new StringBuffer("select id, newCardNo,oldCardNo," +
				" to_char(applyTime,'YYYYMMDDHH24MISS') as time,placeName," +
				" (CASE serType  WHEN '02' THEN '2'  WHEN '11' THEN '3'  ELSE '1' END) as type,cardAmt  " +
				" from CSMS_DBASCARDFLOW where cardAmt != 0 and (sertype='24' or sertype='02' or sertype='11') and endFlag='0' and expireFlag = '0' ");
		sql.append(" and cardNo = ? ");
		sql.append(" order by applytime ");
		return queryList(sql.toString(),cardNo);
	}
	//批量修改完成标志
	public int[] batchUpdateEndFlag(final List<Long> ids,final String endFlag,final BigDecimal transfee) {
		if(ids.size()<1){
			return null;
		}else{
			StringBuffer sql = new StringBuffer(
					"update CSMS_DBASCARDFLOW set endflag = ? ");
			
			if(transfee !=null){
				sql.append(",endCardAmt=? ");
			}
			sql.append(" where id = ?");
			return batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Long id = ids.get(i);
					ps.setString(1, endFlag);
					if(transfee !=null){
						ps.setBigDecimal(2, transfee);
						ps.setLong(3, id);
					}else{
						ps.setLong(2, id);
					}
					
				}
				@Override
				public int getBatchSize() {
					return ids.size();
				}
			});
		}
	}

	public int updateLockEndFlagById(Long endServiceId, BigDecimal endCardAmt, Date rechargeTime, Long id) {
		String sql = "update CSMS_DBASCARDFLOW set endServiceId=?,endSerType=sertype,endCardAmt=?,endflag=?,rechargeTime=? where sertype in('24','02','11') and endflag=? and expireflag=? and id=?";
		return this.update(sql, endServiceId, endCardAmt, DBACardFlowEndFlagEnum.waitComplete.getValue(), rechargeTime, DBACardFlowEndFlagEnum.disComplete.getValue(), DBACardFlowExpireFlagEnum.arriDispute.getValue(), id);
	}

	public int updateRechargedEndFlagByEndServiceId(Long endServiceId) {
		String sql = "update CSMS_DBASCARDFLOW set endflag=? where endServiceId=?";
		return this.update(sql, DBACardFlowEndFlagEnum.arriComplete.getValue(), endServiceId);
	}

	public int updateDisRechargeEndFlagByEndServiceId(Long endServiceId) {
		String sql = "update CSMS_DBASCARDFLOW set endServiceId=null,endSerType=null,endCardAmt=null,endflag=?,rechargeTime=null where endServiceId=?";
		return this.update(sql, DBACardFlowEndFlagEnum.disComplete.getValue(), endServiceId);
	}

	public DbasCardFlow findByEndServiceId(Long endServiceId) {
		String sql = "select * from CSMS_DBASCARDFLOW where endServiceId=?";
		return this.queryRowObject(sql, DbasCardFlow.class, endServiceId);
	}

	/*public List<DbasCardFlow> findWaitCompleteByServiceId(Long serviceId) {
		String sql = "select id, newCardNo,oldCardNo, applyTime,placeName,serType,cardAmt from CSMS_DBASCARDFLOW where sertype in('24','02','11') and endFlag=? and expireFlag=? and serviceid=?";
		return queryObjectList(sql, DbasCardFlow.class, DBACardFlowEndFlagEnum.waitComplete.getValue(),DBACardFlowExpireFlagEnum.arriDispute.getValue(), serviceId);
	}*/
	
	/**
	 * 查找储值卡终止使用的资金转移记录(按道理只有一条记录)
	 * @param cardNo
	 * @return DbasCardFlow
	 */
	public DbasCardFlow findPreCancelDbasCardFlow(String cardNo){
		DbasCardFlow temp = null;
		StringBuffer sql = new StringBuffer(
				  " select "+FieldUtil.getFieldMap(DbasCardFlow.class,new DbasCardFlow()).get("nameStr")+" from csms_dbascardflow  where newCardNo = ? and serType='01' "
				+ "	union all "
				+ " select "+FieldUtil.getFieldMap(DbasCardFlow.class,new DbasCardFlow()).get("nameStr")+" from csms_dbascardflow  where newCardNo = ? and serType='03' ");

		List<DbasCardFlow> dbasCardFlows = super.queryObjectList(sql.toString(), DbasCardFlow.class, cardNo, cardNo);
		if (dbasCardFlows == null || dbasCardFlows.isEmpty()) {
			return null;
		}
		return dbasCardFlows.get(0);
	}
	
	/**
	 * 根据原始卡号找出来所有未完成的资金转移记录
	 * @param originalCardNo 原始卡号
	 * @return Pager
	 */
	public List findByOriginalCard(String originalCardNo) {
		StringBuffer sql = new StringBuffer("select "+FieldUtil.getFieldMap(DbasCardFlow.class,new DbasCardFlow()).get("nameStr")+" from csms_dbascardflow "
				+ "     where 1=1 and cardNo=? ");
		sql.append(" order by applytime ");
		return this.queryList(sql.toString(), originalCardNo);
	}

	public DbasCardFlow findDisCompleteByOldCardNo(String oldCardNo){
		StringBuilder sqlBuilder = new StringBuilder()
				.append(" select * from CSMS_DBASCARDFLOW ")
				.append(" where sertype='02' ")
				.append("    and endFlag='0' and expireFlag = '0' and oldCardNo=? ")
				.append(" order by  applyTime fetch first 1 rows only ");
		List<DbasCardFlow> dbasCardFlows = super.queryObjectList(sqlBuilder.toString(), DbasCardFlow.class, oldCardNo);
		if (dbasCardFlows == null || dbasCardFlows.isEmpty()) {
			return null;
		}
		return dbasCardFlows.get(0);
	}
	
}
