package com.hgsoft.clearInterface.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.clearInterface.entity.StopAcList;
import com.hgsoft.common.dao.ClearBaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Repository
public class StopAcListDao extends ClearBaseDao {
	//这个使我们原来清算设计的止付名单表
	/*public List<Map<String,Object>> findByACBAccount(String acbAccount){
		String sql = "select * from "+urlUtils.getEtctolluser()+".Tb_stopaclist where acbAccount=?";
		List<Map<String,Object>> list = queryList(sql,acbAccount);
		return list;
	}*/
	/**
	 * 
	 * @param acbAccount
	 * @return
	 */
	public List<Map<String,Object>> findByACBAccountAndFlag(String acbAccount,String flag){
		String sql = "select * from Tb_stopaclist where acbAccount='"+acbAccount+"' and flag='"+flag+"'";
		List<Map<String,Object>> list = queryList(sql);
		return list;
	}
	/**
	 * 查询清算记帐卡止付名单表
	 * @param bankAccount
	 * @return
	 */
	public Map<String,Object> findByBankAccount(String bankAccount){
		String sql = "select pr.FLAG from TB_PAYMENTCARDBLACKLIST_RECV pr where pr.ACBACCOUNT=? order by pr.GENTIME";
		List<Map<String,Object>> list = queryList(sql,bankAccount);

		return list.size()>0?list.get(0):null;
	}
	
	public StopAcList find(StopAcList stopAcList) {
		StopAcList temp = null;
		if (stopAcList!= null) {
			StringBuffer sql = new StringBuffer("select * from tb_stopaclist where 1=1 ");
			sql.append(FieldUtil.getFieldMap(StopAcList.class,stopAcList).get("nameAndValueNotNull"));
			List<Map<String, Object>> list = queryList(sql.toString());
			if (!list.isEmpty()&&list.size()==1) {
				temp = new StopAcList();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public  Pager findStopPay(Pager pager, Customer customer, StopAcList stopAcList){
		String sql="select s.*,ROWNUM as num  from Tb_stopaclist s "
				+ "JOIN (SELECT MAX(UPDATEtime) ti from tb_stopaclist GROUP BY acbaccount) ss ON s.updatetime=ss.ti "
				+ " where 1=1 ";
		SqlParamer params=new SqlParamer();
		if(customer!=null && StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("s.userno", customer.getUserNo());
		}
		/*if(StringUtil.isNotBlank(stopAcList.getAccode())){
			params.eq("s.accode", stopAcList.getAccode());
		}*/
		if(StringUtil.isNotBlank(stopAcList.getACBAccount())){
			params.eq("s.ACBAccount", stopAcList.getACBAccount());
		}
		sql=sql+params.getParam();
		sql=sql+(" order by s.genTime desc ");
		 pager=this.findByPages(sql, pager, params.getList().toArray());
		 return pager;
	}	
	
}
