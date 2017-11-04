package com.hgsoft.agentCard.dao;

import com.hgsoft.agentCard.entity.CardBlacklistInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @FileName CardBlacklist.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年3月22日 下午6:02:12 
*/
@Repository
public class CardBlacklistDao extends BaseDao {

	public Pager findCreditCardBlacklist(Pager pager,String beginCode,String endCode, Customer customer, String startTime, String endTime) {
		StringBuffer sql = new StringBuffer("select UTCardNo,CreditCardNo,UserNo,ProduceTime,Remark,row_number() over (order by id desc) as num from CSMS_Card_blacklist_info where substr(UTCardNo,1,15) between '" + beginCode + "' and '" + endCode + "'");
		sql.append(" and UserNo="+customer.getUserNo());
		if (StringUtils.isNotBlank(startTime)) {
			sql.append(" and ProduceTime>=to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append(" and ProduceTime<=to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}
		return this.findByPages(sql.toString(), pager,null);
	}
	
	
	public void save(CardBlacklistInfo cardBlacklistInfo){
		StringBuffer sql = new StringBuffer(
				"insert into CSMS_Card_blacklist_info(");
		sql.append(FieldUtil.getFieldMap(CardBlacklistInfo.class, cardBlacklistInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(CardBlacklistInfo.class, cardBlacklistInfo).get("valueStr")+")");
		save(sql.toString());
	}
	
	public CardBlacklistInfo findByUTCardNo(String UTCardNo){
		String sql = "select ID,UTCardNo,CreditCardNo,UserNo,ProduceTime,ProduceReason,Remark from CSMS_Card_blacklist_info "
				+ "where UTCardNo='"+UTCardNo+"'";
		List<Map<String, Object>> list = queryList(sql);
		CardBlacklistInfo cardBlacklistInfo = null;
		if (!list.isEmpty()) {
			cardBlacklistInfo = new CardBlacklistInfo();
			this.convert2Bean(list.get(0), cardBlacklistInfo);
		}

		return cardBlacklistInfo;
	}

	public List<CardBlacklistInfo> findByUTCardNoType(String UTCardNo,String type){
		String sql = "select ID,UTCardNo,CreditCardNo,UserNo,ProduceTime,ProduceReason,Remark from CSMS_Card_blacklist_info "
				+ "where UTCardNo='"+UTCardNo+"' and ProduceReason='"+type+"'";
		List<Map<String, Object>> list = queryList(sql);
		CardBlacklistInfo cardBlacklistInfo = null;
		List<CardBlacklistInfo> cardBlacklistInfos = new ArrayList<CardBlacklistInfo>();
		if (list.size() > 0) {
			for (Map<String, Object> c : list) {
				cardBlacklistInfo = new CardBlacklistInfo();
				this.convert2Bean(c, cardBlacklistInfo);

				cardBlacklistInfos.add(cardBlacklistInfo);

			}
		}
		return cardBlacklistInfos;
	}
	public void deleteByCardNoType(String UTCardNo,String type){
		String sql = "delete from CSMS_Card_blacklist_info where UTCardNo= '"+UTCardNo+"' and ProduceReason='"+type+"'";
		super.delete(sql);
	}
	
	public void delete(CardBlacklistInfo cardBlacklistInfo){
		String sql = "delete from CSMS_Card_blacklist_info where id="+cardBlacklistInfo.getId();
		super.delete(sql);
	}

}
