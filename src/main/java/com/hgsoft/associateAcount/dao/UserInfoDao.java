package com.hgsoft.associateAcount.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.LianCardInfo;
import com.hgsoft.associateAcount.entity.UserInfo;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class UserInfoDao extends BaseDao{
	public void save(UserInfo userInfo){
		/*StringBuffer sql=new StringBuffer("insert into CSMS_USER_INFO(");
		sql.append(FieldUtil.getFieldMap(UserInfo.class,userInfo).get("nameStr")+") values(");
		sql.append(FieldUtil.getFieldMap(UserInfo.class,userInfo).get("valueStr")+")");
		save(sql.toString());*/
		
		
		Map map = FieldUtil.getPreFieldMap(UserInfo.class,userInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_USER_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public Pager list(Pager pager,Date starTime, Date endTime, LianCardInfo lianCardInfo,Customer customer){
		
		String sql="select u.*,l.name,l.idtype,l.idcode, l.cardNo ,ROWNUM as num 　from csms_user_info u left join csms_lian_card_info l on u.accode=l.cardno where 1=1 ";
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			params.geDate("u.reqtime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("u.reqtime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(lianCardInfo.getName())){
			params.like("l.name", "%"+lianCardInfo.getName()+"%");
		}
		if(StringUtil.isNotBlank(lianCardInfo.getIdType())){
			params.eq("l.idtype", lianCardInfo.getIdType());
		}
		if(StringUtil.isNotBlank(lianCardInfo.getIdCode())){
			params.eq("l.idcode",lianCardInfo.getIdCode());
		}
		if(StringUtil.isNotBlank(lianCardInfo.getCardNo())){
			params.eq("u.AcCode",lianCardInfo.getCardNo());
		}
		if(customer.getId()!=null){
			params.eq("u.CustomerID",customer.getId());
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		sql=sql+(" order by u.reqtime desc ");
		return this.findByPages(sql, pager,Objects);	
	}
	
	public List list(LianCardInfo lianCardInfo,Date starTime, Date endTime,Customer customer){
		
		String sql="select u.*,l.name,l.idtype,l.idcode, l.cardNo 　from csms_user_info u left join csms_lian_card_info l on u.accode=l.cardno where 1=1 and u.CustomerID ="+customer.getId();
		SqlParamer params=new SqlParamer();
		if(starTime !=null){
			params.geDate("u.reqtime", params.getFormat().format(starTime));
		}
		if(endTime !=null){
			params.leDate("u.reqtime", params.getFormatEnd().format(endTime));
		}
		sql=sql+params.getParam();
		Object[] Objects= params.getList().toArray();
		List<Map<String, Object>> list = queryList(sql.toString(),Objects);
		return list;
	}
}
