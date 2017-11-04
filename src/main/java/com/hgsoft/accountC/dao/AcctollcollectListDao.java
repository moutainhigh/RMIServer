package com.hgsoft.accountC.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AcctollcollectList;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
@Repository
public class AcctollcollectListDao extends BaseDao {
	public Pager findByPage(Pager pager,Customer customer,AccountCApply accountCApply){
		String sql="select a.ID,a.state,a.UserID,a.accountID,a.DealNum,a.DealFee,a.RealDealFee,a.ServerFee,a.LateFee,a.TollFee,"
				+ "a.genTime,a.hdlTime,c.id as customerId,sc.mainId,aa.bankAccount,aa.id as accountApplyId,c.userNO,c.Organ,ROWNUM as num  "
				+ " from CSMS_AccTollCollect_list a join CSMS_Customer c on a.UserID=c.ID "
				+ " join CSMS_SubAccount_Info sc on a.accountID=sc.Id join CSMS_AccountC_apply aa on sc.ApplyID=aa.Id where 1=1 ";
		
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(customer.getUserNo())){
			params.eq("c.UserNo", customer.getUserNo());
		}
		if(StringUtil.isNotBlank(accountCApply.getBankAccount())){
			params.eq("aa.bankAccount", accountCApply.getBankAccount());
		}
		sql=sql+params.getParam();
		sql=sql+(" order by a.genTime ");
		Object[] Objects= params.getList().toArray();
		
		return this.findByPages(sql, pager,Objects);
	}
	
	public int updateStateById(String state,Long id){
		String sql="update CSMS_AccTollCollect_list set state=?,payTime=sysdate where id=? ";
		return this.jdbcUtil.getJdbcTemplate().update(sql, new Object[]{state,id});
	}
	
	public AcctollcollectList findById(Long id){
		String sql = "select ID,state,UserID,AccountID,DealNum,DealFee,RealDealFee,ServerFee,LateFee,TollFee,genTime,hdlTime,payTime from CSMS_AccTollCollect_list where id="+id;
		List<Map<String, Object>> list = queryList(sql);
		AcctollcollectList acctollcollectList = null;
		if (!list.isEmpty()) {
			acctollcollectList = new AcctollcollectList();
			this.convert2Bean(list.get(0), acctollcollectList);
		}

		return acctollcollectList;
	}
	
}
