package com.hgsoft.accountC.dao;

import org.springframework.stereotype.Repository;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.StopList;
import com.hgsoft.clearInterface.entity.StopAcList;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

/**
 * @FileName AccountCStopPayDao.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月23日 下午4:17:12 
*/
@Repository
public class AccountCStopPayDao extends BaseDao {

/*	public Pager findStopPay(Pager pager, Customer customer, AccountCApply accountCApply) {
		StringBuffer sql = new StringBuffer("select app.bankaccount,cus.userno,st.id,st.gendate,st.reason,flag,row_number() over (order by app.id desc) as num from csms_stop_list st"
				+ " join csms_subaccount_info subc on st.accountid=subc.id join csms_accountc_apply app on subc.applyid=app.id"
				+ " join csms_customer cus on app.customerid=cus.id where 1=1  ");
//		if(customer!=null)
//			sql.append(FieldUtil.getFieldMap(Customer.class, customer).get("nameAndValueNotNull"));
		if(StringUtil.isNotBlank(customer.getUserNo()))
			sql.append(" and userno="+customer.getUserNo()+" ");
		if(accountCApply!=null)
			sql.append(FieldUtil.getFieldMap(AccountCApply.class, accountCApply).get("nameAndValueNotNull"));
		return this.findByPages(sql.toString(), pager,null);
	}*/
	// TODO: 2017/4/21 这个方法没有业务调用,是否删除掉
	public void updateStopFlag(StopList stopList) {
		String sql = "update csms_stop_list st set st.flag="+"?"+" where st.id="+stopList.getId();
		this.saveOrUpdate(sql,stopList.getFlag());
	}
	
	public  Pager findStopPay(Pager pager, Customer customer, StopAcList stopAcList){
		String sql="select s.*,ROWNUM  as num  from Tb_stopaclist s  where 1=1 ";
		SqlParamer params=new SqlParamer();
		if(StringUtil.isNotBlank(stopAcList.getAccode())){
			params.eq("accode", stopAcList.getAccode());
		}
		sql=sql+params.getParam();
		sql=sql+(" order by genTime desc ");
		 pager=this.findByPages(sql, pager, params.getList().toArray());
		 return pager;
	}	
}
