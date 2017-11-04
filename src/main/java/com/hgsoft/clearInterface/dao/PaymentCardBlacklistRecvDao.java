package com.hgsoft.clearInterface.dao;


import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentCardBlacklistRecvDao extends ClearBaseDao {
	
	public void save(PaymentCardBlacklistRecv paymentCardBlacklistRecv) {
		StringBuffer sql=new StringBuffer("insert into tb_paymentcardblacklist_recv(");
		sql.append(FieldUtil.getFieldMap(PaymentCardBlacklistRecv.class,paymentCardBlacklistRecv).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(PaymentCardBlacklistRecv.class,paymentCardBlacklistRecv).get("valueStr")+")");
		save(sql.toString());
	}
	
	public List<Map<String,Object>> findByACBAccount(String acbAccount){
		String sql = "select * from tb_paymentcardblacklist_recv where acbAccount=?";
		List<Map<String,Object>> list = queryList(sql,acbAccount);
		return list;
	}

	/**
	 * 查找最近一条  铭鸿的   止付卡黑名单全量数据
	 * @param bankAccount
	 * @2017年9月27日14:59:20 更新
	 * 黄志炜
	 */
	public Map<String,Object> findStateMap(String bankAccount){
		List<Map<String,Object>> list=new ArrayList<>();
		if(bankAccount!=null){
			String sql = "select flag from CSMS_PAYMENTCARDBLACKLIST where  " +
					"  acbAccount=?  order by BOARDLISTNO desc,gentime desc";
			list = queryList(sql,bankAccount);
		}
		if(list.size() <= 0){
			return null;
		}
		//当处理标志为 非"已经解除成功"的记录才返回
		BigDecimal flag = (BigDecimal) list.get(0).get("FLAG");
		if(list.size()>0 && new BigDecimal(3).compareTo(flag)!=0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public Map<String,Object> findStateMap(PaymentCardBlacklistRecv paymentCardBlacklistRecv){
		List<Map<String,Object>> list=new ArrayList<>();
		if(paymentCardBlacklistRecv!=null){
			String sql = "select FLAG,GENTIME,CARDCODE from tb_paymentcardblacklist_recv " +
					"where Flag in ('0','1') and acbAccount=? or cardcode=? order by GenTime desc";
			list = queryList(sql,paymentCardBlacklistRecv.getAcbAccount(),paymentCardBlacklistRecv.getCardCode());
		}

		return list.size()>0?list.get(0):null;
	}
}
