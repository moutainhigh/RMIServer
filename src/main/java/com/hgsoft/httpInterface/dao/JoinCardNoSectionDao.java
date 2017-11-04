package com.hgsoft.httpInterface.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.JoinCardNoSection;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("joinCardNoSectionDao")
public class JoinCardNoSectionDao extends BaseDao {

	/*public Pager findByPager(Pager pager, JoinCardNoSection joinCardNoSection,String startCode,String endCode) {
		StringBuffer sql = new StringBuffer(
				"select t.*,row_number() over (order by ID desc) as num  from CSMS_joinCardNoSection t where 1=1 ");
		if (joinCardNoSection != null) {
	       if(StringUtil.isNotBlank(joinCardNoSection.getCheckFlag())){
	    	   sql.append("and t.checkFlag="+joinCardNoSection.getCheckFlag());
	       }
	       if(StringUtil.isNotBlank(startCode)){
				sql.append(" and length(t.Code)="+startCode.length()+" and substr(t.Code,0,15) >= "+StringUtil.escape(startCode));
			}
			if(StringUtil.isNotBlank(endCode)){
				sql.append(" and length(t.Code)="+endCode.length()+" and substr(t.Code,0,15) <="+StringUtil.escape(endCode));
			}
		}
		sql.append(" order by t.id desc");
		return this.findByPages(sql.toString(), pager, null);
	}*/
	public Pager findByPager(Pager pager, String cardno,String bankNo,String cardType,String CompoundFlag,String issFlag) {
		StringBuffer sql = new StringBuffer(
				"select t.id,t.code,t.EndCode,t.bankName,t.bankNo,t.CompoundFlag,t.cardType,t.issFlag,t.operateName,row_number() over (order by ID desc) as num  from CSMS_joinCardNoSection t where 1=1 ");
		/*if (joinCardNoSection != null) {
	       if(StringUtil.isNotBlank(joinCardNoSection.getCheckFlag())){
	    	   sql.append("and t.checkFlag="+joinCardNoSection.getCheckFlag());
	       }
	       if(StringUtil.isNotBlank(joinCardNoSection.getCode())){
	    	   sql.append("and t.code like '%"+joinCardNoSection.getCode()+"%'");
	       }
	       if(StringUtil.isNotBlank(startCode)){
				sql.append(" and length(t.Code)="+startCode.length()+" and substr(t.Code,0,15) >= "+StringUtil.escape(startCode));
			}
			if(StringUtil.isNotBlank(endCode)){
				sql.append(" and length(t.Code)="+endCode.length()+" and substr(t.Code,0,15) <="+StringUtil.escape(endCode));
			}
		}*/
//		if (cardno != null) {
	       if(StringUtil.isNotBlank(cardno)){
	    	   sql.append(" and t.code<="+cardno+" and t.EndCode >= "+cardno);
	       }
	       if(StringUtil.isNotBlank(bankNo)){
	    	   sql.append(" and t.bankNo ="+bankNo);
	       }
	       if(StringUtil.isNotBlank(cardType)){
	    	   sql.append(" and t.cardType ="+cardType);
	       }
	       if(StringUtil.isNotBlank(CompoundFlag)){
	    	   sql.append(" and t.CompoundFlag ="+CompoundFlag);
	       }
	       if(StringUtil.isNotBlank(issFlag)){
	    	   sql.append(" and t.issFlag ="+issFlag);
	       }


//		}

		sql.append(" order by t.id desc");
		return this.findByPages(sql.toString(), pager, null);
	}

	public JoinCardNoSection findById(Long id) {
		String sql = "select * from CSMS_JoinCardNoSection where id=?";
		List<JoinCardNoSection> joinCardNoSections = super.queryObjectList(sql, JoinCardNoSection.class, id);
		if (joinCardNoSections == null || joinCardNoSections.isEmpty()) {
			return null;
		}
		return joinCardNoSections.get(0);
	}

	public void save(JoinCardNoSection joinCardNoSection) {
		Map map = FieldUtil.getPreFieldMap(JoinCardNoSection.class, joinCardNoSection);
		StringBuffer sql = new StringBuffer("insert into CSMS_JoinCardNoSection");
		sql.append(map.get("insertNameStr"));
		map.get("param");
		saveOrUpdate(sql.toString(), (List) map.get("param"));

	}


	public void update(JoinCardNoSection joinCardNoSection) {
		Map map = FieldUtil.getPreFieldMap(JoinCardNoSection.class, joinCardNoSection);
		StringBuffer sql = new StringBuffer("update CSMS_joinCardNoSection set ");
		sql.append(map.get("updateNameStr") + " where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"), joinCardNoSection.getId());
	}

	public void delete(Long id) {
		String sql = "delete from CSMS_joinCardNoSection where id = " + id;
		super.delete(sql);
	}

	public int[] batchSaveJoinCardNoSection(final List<JoinCardNoSection> JoinCardNoSectionList) {
		String sql = "insert into CSMS_JoinCardNoSection(ID,CODE,ENDCODE,BANKNO,BANKNAME,COMPOUNDFLAG,REMARk,CARDTYPE,CHECKFLAG,OPERATEID,OPERATENAME,OPERATETIME,CUSTOMPOINT,ISSFLAG,WORKFLAG)"
				+ "values(SEQ_CSMSJoinCardNoSection_NO.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
			@Override
			public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
				JoinCardNoSection joinCardNoSection = JoinCardNoSectionList.get(i);
				if (joinCardNoSection != null) {
					ps.setString(1, joinCardNoSection.getCode());
					ps.setString(2, joinCardNoSection.getEndCode());
					ps.setString(3, joinCardNoSection.getBankNo());
					ps.setString(4, joinCardNoSection.getBankName());
					ps.setString(5, joinCardNoSection.getCompoundFlag());
					ps.setString(6, joinCardNoSection.getRemark());
					ps.setString(7, joinCardNoSection.getCardType());
					ps.setString(8, joinCardNoSection.getCheckFlag());
					ps.setLong(9, joinCardNoSection.getOperateId());
					ps.setString(10, joinCardNoSection.getOperateName());
					ps.setTimestamp(11, new java.sql.Timestamp(joinCardNoSection.getOperateTime().getTime()));
					ps.setString(12, joinCardNoSection.getCustomPoint());
					ps.setString(13, joinCardNoSection.getIssFlag());
				/*	ps.setString(14, joinCardNoSection.getCheckTime());
					ps.setString(15, joinCardNoSection.getCheckId());
					ps.setString(16, joinCardNoSection.getCheckName());
					ps.setString(17, joinCardNoSection.getIssueTime());*/
					ps.setString(14, joinCardNoSection.getWorkFlag());
					//ps.setString(19, joinCardNoSection.getWorkTime());
				}
			}

			@Override
			public int getBatchSize() {
				return JoinCardNoSectionList.size();
			}
		});
	}

	/*public void batchSaveJoinCardNoSectionHis(String arrayId, SysAdmin operator, String genReason) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date opertorTime = new Date();
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_JoinCardNoSection_his(ID,CODE,ENDCODE,BANKNO,BANKNAME,COMPOUNDFLAG,REMARk,CARDTYPE,CHECKFLAG,OPERATEID,OPERATENAME,OPERATETIME,CUSTOMPOINT,ISSFLAG,");
		sql.append("CheckTime,CheckId,CheckName,IssueTime,WorkFlag,WorkTime,JoinCardNoSectionId,genreason,gentime)");
		sql.append(
				" (select OMS_JoinCardNoSection_HIS_SEQ.nextval,CODE,ENDCODE,BANKNO,BANKNAME,COMPOUNDFLAG,REMARk,CARDTYPE,CHECKFLAG,OPERATEID,OPERATENAME,OPERATETIME,CUSTOMPOINT,ISSFLAG,");
		sql.append(" CheckTime,CheckId,CheckName,IssueTime,WorkFlag,WorkTime,Id,'").append(genReason).append("' as genreason,");
		sql.append(" to_date('" + format.format(opertorTime) + "','YYYY-MM-DD HH24:MI:SS') as gentime");
		sql.append(" from OMS_JoinCardNoSection where id in (").append(arrayId).append("))");
		super.save(sql.toString());
	}*/

	public void batchUpdate(String arrayId, Map operator, String checkFlag) {
		String sql = "update CSMS_JoinCardNoSection  set checkId=?,checkName=?,checkFlag=?,checkTime=? where id in("
				+ arrayId + ")";
		super.saveOrUpdate(sql, new Long((Integer)operator.get("id")), (String)operator.get("loginName"), checkFlag, new Date());
	}

	/**
	 * @Description:TODO
	 * @param startCode
	 * @return JoinCardNoSection
	 */
	public int findCountByCode(String startCode,String endCode) {
		String sql = "select count(id) from CSMS_JoinCardNoSection where (not (endcode<? or code>?))";
		int i = count(sql, startCode,endCode);
		return i;
	}

	public List findList(String cardType,String cardNo){
		//卡号段是15位
		String sql = "select id,cardType,bankNo,bankName,code,EndCode from CSMS_joinCardNoSection "
				+ " where substr(?,0,15) between code and endcode and CARDTYPE = ? ";
		List<Map<String, Object>> list = queryList(sql,cardNo,cardType);
		return list;
	}
}
