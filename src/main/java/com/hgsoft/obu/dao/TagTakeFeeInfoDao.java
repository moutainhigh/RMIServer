package com.hgsoft.obu.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.obu.entity.TagTakeFeeInfo;
import com.hgsoft.obu.entity.TagTakeInfo;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

/**
 * 
 * @author guanshaofeng
 * 
 */
@Component
public class TagTakeFeeInfoDao extends BaseDao {

	/**
	 * 
	 * @return
	 */
	public List findAllTagTakeFeeInfos(TagTakeFeeInfo tagTakeFeeInfo) {

		List list = new ArrayList<TagTakeFeeInfo>();
		String sql = "select id,clientname,CertType,CertNumber,chargeFee,takeBalance,registerOperId,"
				+ "registerDate,registerplace from csms_tagtakefee_info where 1=1 ";

		if (tagTakeFeeInfo != null) {
			if (tagTakeFeeInfo.getClientName() != null
					&& !"".equals(tagTakeFeeInfo.getClientName())) {
				sql += "and clientname = '" + tagTakeFeeInfo.getClientName()
						+ "'";
			}
		}

		// else
		// if(tagTakeFeeInfo.getRegisterOperID()!=null&&!"".equals(tagTakeFeeInfo.getRegisterOperID())){
		// //sql += "and clientname = '"+clientName+"'";
		// }else
		// if(tagTakeFeeInfo.getRegisterDate()!=null&&!"".equals(tagTakeFeeInfo.getRegisterDate())){
		// sql+="and registerDate = '"+tagTakeFeeInfo.getRegisterDate()+"'";
		// }
		// else if(registerDateEnd!=null&&!"".equals(registerDateEnd)){
		// sql+=" or <= '"+registerDateEnd+"'";
		// }
		list = queryList(sql);

		return list;
	}

	public Pager findAllTagTakeFeeInfosByPager(Pager pager,String registerName,String modifyName,
			TagTakeFeeInfo tagTakeFeeInfo,Date modifyStarTime,Date modifyEndTime,Date registStarTime,Date registEndTime) {

//		pager.setPageSize("5");

		StringBuffer sql = new StringBuffer("select receiptid, id,clientname,CertType,CertNumber,chargeFee,takeBalance,registerOperId,opername,modifyopername,"
				+ "registerDate,registerplace,ROWNUM as num "
				+ "from csms_tagtakefee_info v where 1=1 ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (tagTakeFeeInfo != null) {
			if (tagTakeFeeInfo.getClientName() != null
					&& !"".equals(tagTakeFeeInfo.getClientName())) {
				sql += "and clientname = '" + tagTakeFeeInfo.getClientName()
						+ "'";
			}
			if(modifyStarTime !=null){
				sql=sql+(" and ModifyDate >= to_date('"+format.format((modifyStarTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			if(modifyEndTime !=null){
				sql=sql+(" and ModifyDate <=to_date('"+format.format((modifyEndTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			if(registStarTime !=null){
				sql=sql+(" and RegisterDate >= to_date('"+format.format((registStarTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			if(registEndTime !=null){
				sql=sql+(" and RegisterDate <=to_date('"+format.format((registEndTime) )+"','YYYY-MM-DD HH24:MI:SS') ");
			}
			if(tagTakeFeeInfo.getModifyDate()!=null){
				sql=sql+" and ModifyDate=to_date('"+ format.format(tagTakeFeeInfo.getModifyDate())+"','YYYY-MM-DD HH24:MI:SS')";
			}
			
			if(tagTakeFeeInfo.getRegisterDate()!=null){
				sql=sql+" and RegisterDate=to_date('"+ format.format(tagTakeFeeInfo.getRegisterDate())+"','YYYY-MM-DD HH24:MI:SS')";
			}
			if(StringUtil.isNotBlank(registerName)){
		
			}
			if(StringUtil.isNotBlank(modifyName)){
				
			}
		}*/
		
		// else
		// if(tagTakeFeeInfo.getRegisterOperID()!=null&&!"".equals(tagTakeFeeInfo.getRegisterOperID())){
		// //sql += "and clientname = '"+clientName+"'";
		// }else
		// if(tagTakeFeeInfo.getRegisterDate()!=null&&!"".equals(tagTakeFeeInfo.getRegisterDate())){
		// sql+="and registerDate = '"+tagTakeFeeInfo.getRegisterDate()+"'";
		// }
		// else if(registerDateEnd!=null&&!"".equals(registerDateEnd)){
		// sql+=" or <= '"+registerDateEnd+"'";
		// }
		SqlParamer params=new SqlParamer();
		if(tagTakeFeeInfo != null){
			if(StringUtil.isNotBlank(tagTakeFeeInfo.getClientName())){
				params.eq("ClientName", tagTakeFeeInfo.getClientName());
			}
			if(StringUtil.isNotBlank(tagTakeFeeInfo.getOperName())){
				params.eq("opername", tagTakeFeeInfo.getOperName());
			}
			if(StringUtil.isNotBlank(tagTakeFeeInfo.getModifyOperName())){
				params.eq("modifyopername", tagTakeFeeInfo.getModifyOperName());
			}
		}
		if(modifyStarTime != null){
			params.geDate("ModifyDate", format.format(modifyStarTime));
		}
		if(modifyEndTime != null){
			params.leDate("ModifyDate", format.format(modifyEndTime));
		}
		if(registStarTime !=null){
			params.geDate("RegisterDate", format.format(registStarTime));
		}
		if(registEndTime !=null){
			params.leDate("RegisterDate", format.format(registEndTime));
		}
		sql.append(params.getParam());
		List list = params.getList();
		Object[] Objects= list.toArray();
		sql.append(" order by v.id desc ");
		pager = this.findByPages(sql.toString(), pager,Objects);

		return pager;
	}

	/**
	 * 保存登记电子标签提货金额
	 * 
	 * @param tagTakeFeeInfo
	 */
	public void save(TagTakeFeeInfo tagTakeFeeInfo) {
		tagTakeFeeInfo.setHis_SeqID(-tagTakeFeeInfo.getId());
		Map map = FieldUtil.getPreFieldMap(TagTakeFeeInfo.class,tagTakeFeeInfo);
		StringBuffer sql=new StringBuffer("insert into csms_tagtakefee_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*if (tagTakeFeeInfo.getId() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getId() + ",");
		}
		if (tagTakeFeeInfo.getClientName() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getClientName() + "',");
		}
		if (tagTakeFeeInfo.getCertType() == null) {
			sql.append("1,");
		} else {
			sql.append(tagTakeFeeInfo.getCertType() + ",");
		}
		if (tagTakeFeeInfo.getCertNumber() == null) {
			sql.append("110,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getCertNumber() + "',");
		}
		if (tagTakeFeeInfo.getChargeFee() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getChargeFee() + "',");
		}
		if (tagTakeFeeInfo.getChargeType() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getChargeType() + "',");
		}
		if (tagTakeFeeInfo.getPayAccount() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getPayAccount() + "',");
		}
		if (tagTakeFeeInfo.getTakeBalance() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getTakeBalance() + "',");
		}
		if (tagTakeFeeInfo.getRegisterOperID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getRegisterOperID() + ",");
		}
		if (tagTakeFeeInfo.getRegisterDate() == null) {
			sql.append("NULL,");//默认不存在更新时间
		} else {
			sql.append("to_date('"
					+ format.format(tagTakeFeeInfo.getRegisterDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		if(tagTakeFeeInfo.getRegisterPlace() == null){
			sql.append("NULL,");//默认不存在更新时间
		}else{
			sql.append(tagTakeFeeInfo.getRegisterPlace() + ",");
		}
		if (tagTakeFeeInfo.getModifyOperID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getModifyOperID() + ",");
		}
		if (tagTakeFeeInfo.getModifyDate() == null) {
			sql.append("NULL,");
		} else {
			sql.append("to_date('"
					+ format.format(tagTakeFeeInfo.getModifyDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		if (tagTakeFeeInfo.getMemo() == null) {
			sql.append("NULL,");
		} else {
			sql.append("'" + tagTakeFeeInfo.getMemo() + "',");
		}
		if (tagTakeFeeInfo.getHis_SeqID() == null) {
			sql.append("NULL,");
		} else {
			sql.append(tagTakeFeeInfo.getHis_SeqID() + ",");
		}

		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}
		sqlString += ")";*/
		
		//save(sqlString);
	}

	/**
	 * 点击保存修改电子标签提货金额信息
	 * 
	 * @param tagTakeFeeInfo
	 */
	public void update(TagTakeFeeInfo tagTakeFeeInfo) {
		/*StringBuffer sql = new StringBuffer("update csms_tagtakefee_info set ");
		String sqlString = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (tagTakeFeeInfo.getClientName() != null) {
			sql.append("CLIENTNAME='" + tagTakeFeeInfo.getClientName() + "',");
		}

		// if (tagTakeFeeInfo.getCertType() == null) {
		// sql.append("CERTTYPE=1,");
		// } else {
		// sql.append("CERTTYPE='" + tagTakeFeeInfo.getCertType() + "',");
		// }
		// if (tagTakeFeeInfo.getCertNumber() == null) {
		// sql.append("CERTNUMBER=1,");
		// } else {
		// sql.append("CERTNUMBER='" + tagTakeFeeInfo.getCertNumber() + "',");
		// }
		if (tagTakeFeeInfo.getChargeFee() != null) {
			sql.append("CHARGEFEE=" + tagTakeFeeInfo.getChargeFee() + ",");
		}
		// if (tagTakeFeeInfo.getChargeType() == null) {
		// sql.append("CHARGETYPE=NULL,");
		// } else {
		// sql.append("CHARGETYPE='" + tagTakeFeeInfo.getChargeType() + "',");
		// }
		// if (tagTakeFeeInfo.getPayAccount() == null) {
		// sql.append("PAYACCOUNT=NULL,");
		// } else {
		// sql.append("PAYACCOUNT='" + tagTakeFeeInfo.getPayAccount() + "',");
		// }
		if (tagTakeFeeInfo.getTakeBalance() != null) {
			sql.append("TAKEBALANCE=" + tagTakeFeeInfo.getTakeBalance() + ",");
		}
		if (tagTakeFeeInfo.getRegisterOperID() != null) {
			sql.append("REGISTEROPERID='" + tagTakeFeeInfo.getRegisterOperID()
					+ "',");
		}
		if (tagTakeFeeInfo.getRegisterDate() != null) {
			sql.append("RegisterDate=to_date('"
					+ format.format(tagTakeFeeInfo.getRegisterDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		if (tagTakeFeeInfo.getModifyOperID() != null) {
			sql.append("MODIFYOPERID='" + tagTakeFeeInfo.getModifyOperID()
					+ "',");
		}
		if (tagTakeFeeInfo.getModifyDate() != null) {

			sql.append("MODIFYDATE=to_date('"
					+ format.format(tagTakeFeeInfo.getModifyDate())
					+ "','YYYY-MM-DD HH24:MI:SS'),");
		}
		// if (tagTakeFeeInfo.getMemo() == null) {
		// sql.append("MEMO=NULL,");
		// } else {
		// sql.append("MEMO='" + tagTakeFeeInfo.getMemo() + "',");
		// }
		// if (tagTakeFeeInfo.getHis_SeqID() == null) {
		// sql.append("HIS_SEQID=NULL,");
		// } else {
		// sql.append("HIS_SEQID='" + tagTakeFeeInfo.getHis_SeqID() + "',");
		// }

		if (sql.toString().endsWith(",")) {
			sqlString = sql.substring(0, sql.length() - 1);
		}


		sqlString += " where id=" + tagTakeFeeInfo.getId();
		update(sqlString);*/
		
		Map map = FieldUtil.getPreFieldMap(TagTakeFeeInfo.class,tagTakeFeeInfo);
		StringBuffer sql=new StringBuffer("update csms_tagtakefee_info set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),tagTakeFeeInfo.getId());
		
		/*StringBuffer sql =  new StringBuffer("update csms_tagtakefee_info set ");
		sql.append(FieldUtil.getFieldMap(TagTakeFeeInfo.class, tagTakeFeeInfo).get("nameAndValue")+" where id="+tagTakeFeeInfo.getId());
		update(sql.toString());*/

	}

	/**
	 * 修改提货登记余额
	 * @param tagTakeFeeInfo
	 * @param changePrice	要修改的金额（+-）
	 */
	public void updateTakeBalanceById(TagTakeFeeInfo tagTakeFeeInfo,BigDecimal changePrice) {
		/*String sql = "update csms_tagtakefee_info set " + "TakeBalance=TakeBalance+"
				+ changePrice + " where id="+tagTakeFeeInfo.getId();
		update(sql);*/
		StringBuffer sql = new StringBuffer("update csms_tagtakefee_info set TakeBalance=TakeBalance+? ");
		if(tagTakeFeeInfo.getHis_SeqID()!=null){
			sql.append(",His_SeqID=? where id=? ");
			saveOrUpdate(sql.toString(), changePrice,tagTakeFeeInfo.getHis_SeqID(), tagTakeFeeInfo.getId());
		}else{
			sql.append(" where id=? ");
			saveOrUpdate(sql.toString(), changePrice, tagTakeFeeInfo.getId());
		}
		
	}

	/**
	 * 删除一条电子标签提货金额信息
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		String sql = "delete from csms_tagtakefee_info where id=?";

		delete(sql,id);
	}

	/**
	 * 根据数据id查找对应对象
	 * 
	 * @param id
	 * @return
	 */
	public TagTakeFeeInfo findById(Long id) {
		TagTakeFeeInfo tagTakeFeeInfo = null;

		String sql = "select id,clientName,certType,certNumber,chargeFee,"
				+ "chargeType,payAccount,takeBalance,registerOperID,"
				+ "registerDate,registerplace,modifyOperID,modifyDate,memo,"
				+ "His_SeqID,isDaySet,settleDay,settletTime,opername,operno,placeno,placename,modifyopername,modifyoperno,receiptid,bankTransferInfoId,voucherNo from csms_tagtakefee_info where id=?";

		List<Map<String, Object>> list = queryList(sql, id);

		if(list!=null&&list.size()>0){
			tagTakeFeeInfo = (TagTakeFeeInfo) this.convert2Bean(
					(Map<String, Object>) list.get(0), new TagTakeFeeInfo());
		}


		return tagTakeFeeInfo;

	}
	public TagTakeFeeInfo findByHisId(Long id) {
		TagTakeFeeInfo tagTakeFeeInfo = null;

		String sql = "select id,clientName,certType,certNumber,chargeFee,"
				+ "chargeType,payAccount,takeBalance,registerOperID,"
				+ "registerDate,registerplace,modifyOperID,modifyDate,memo,"
				+ "His_SeqID,isDaySet,settleDay,settletTime,opername,operno,placeno,placename,modifyopername,modifyoperno from csms_tagtakefee_info where His_SeqID=?";

		List<Map<String, Object>> list = queryList(sql, id);

			if(list!=null && list.size()>0){
				tagTakeFeeInfo = (TagTakeFeeInfo) this.convert2Bean(
						(Map<String, Object>) list.get(0), new TagTakeFeeInfo());
			}

		return tagTakeFeeInfo;

	}
	public TagTakeFeeInfo findFromHisByHisId(Long id) {
		TagTakeFeeInfo tagTakeFeeInfo = null;

		String sql = "select id,clientName,certType,certNumber,chargeFee,"
				+ "chargeType,payAccount,takeBalance,registerOperID,"
				+ "registerDate,registerplace,modifyOperID,modifyDate,memo,"
				+ "His_SeqID,isDaySet,settleDay,settletTime,opername,operno,placeno,placename,modifyopername,modifyoperno from csms_tagtakefee_infohis where His_SeqID=?";

		List<Map<String, Object>> list = queryList(sql, id);

		tagTakeFeeInfo = (TagTakeFeeInfo) this.convert2Bean(
		(Map<String, Object>) list.get(0), new TagTakeFeeInfo());


		return tagTakeFeeInfo;

	}
	/**
	 * 根据客户名查找TagTakeFeeInfo
	 * 
	 * @param tagTakeInfo
	 * @return
	 */
	public TagTakeFeeInfo findTagTakeInfoByThreeValue(TagTakeInfo tagTakeInfo) {
		TagTakeFeeInfo tagTakeFeeInfo = null;

		String sql = "select id,clientName,certType,certNumber,chargeFee,"
				+ "chargeType,payAccount,takeBalance,registerOperID,"
				+ "registerDate,registerplace,modifyOperID,ModifyDate,memo,His_SeqID from "
				+ "csms_tagtakefee_info where clientName=? and certType=? and "
				+ "certNumber=?";

		List<Map<String, Object>> list = queryList(sql,
				tagTakeInfo.getClientName(), tagTakeInfo.getCertType(),
				tagTakeInfo.getCertNumber());

		if (list.size() > 0) {
			tagTakeFeeInfo = (TagTakeFeeInfo) this
					.convert2Bean((Map<String, Object>) list.get(0),
							new TagTakeFeeInfo());
		}



		return tagTakeFeeInfo;

	}
	
	public void updateDaySettle(String settleDay,String startTime,String endTime,Long placeId,List<String> placeList){
		
		StringBuffer sql=new StringBuffer("update csms_tagtakefee_info set IsDaySet='1',SettleDay=?,SettletTime=sysdate"
				+ " where to_char(RegisterDate,'YYYYMMDDHH24MISS')>=?"
				+ " and to_char(RegisterDate,'YYYYMMDDHH24MISS')<? and placeno in( ");
		for (int i = 0; i < placeList.size(); i++) {
			sql.append("'"+placeList.get(i)+"'");
			if(i!=placeList.size()-1){
				sql.append(" , ");
			}
		};
		sql.append(" ) ");
		saveOrUpdate(sql.toString(),settleDay,startTime,endTime);
	}
	
	public TagTakeFeeInfo findByReceiptId(Long id) {
		TagTakeFeeInfo tagTakeFeeInfo = null;

		String sql = "select * from csms_tagtakefee_info where receiptid=?";

		List<Map<String, Object>> list = queryList(sql, id);

		if(list!=null && list.size()>0){
			tagTakeFeeInfo = (TagTakeFeeInfo) this.convert2Bean(
					(Map<String, Object>) list.get(0), new TagTakeFeeInfo());
		}


		return tagTakeFeeInfo;

	}
}
