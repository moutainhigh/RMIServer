package com.hgsoft.accountC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Repository
public class BlacklistDao extends BaseDao{
	

	/**
	 * 待下发黑名单查询列表
	 * 
	 * */
	public Pager findBlacklist(Pager pager,String userNo,String organ,String cardNo,String cardType)
	{
		StringBuffer sql = new StringBuffer("with bl as"
										  + " (select c.userno, c.organ, dl.cardno,dl.cardtype,'' obuSerial"
										  + " from csms_prepaidc p"
										  + " join csms_customer c"
										  + " on p.customerid = c.id"
										  + " left join csms_dark_list dl"
										  + " on p.cardno = dl.cardno"
										  + " where dl.gencau = '1' and dl.state='0'"
										  + " union all"
										  + " select c.userno, c.organ, dl.cardno,dl.cardtype,'' obuSerial"
										  + " from csms_accountc_info ai"
										  + " join csms_customer c"
										  + " on ai.customerid = c.id"
										  + " left join csms_dark_list dl"
										  + " on ai.cardno = dl.cardno"
										  + " where dl.gencau = '1' and dl.state='0'"
										  + " union all"
										  + " select c.userno, c.organ, dl.cardno,dl.cardtype,dl.obuSerial obuSerial"
										  + " from csms_tag_info ti"
										  + " join csms_customer c"
										  + " on ti.clientid = c.id"
										  + " left join csms_dark_list dl"
										  + " on ti.tagno = dl.cardno"
										  + " where dl.gencau = '1' and dl.state='0')"
										  + " select * from bl where 1 = 1");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(userNo)){
			sqlp.eq("bl.userNo", userNo);
		}
		if(StringUtil.isNotBlank(organ)){
			sqlp.eq("bl.organ", organ);
		}
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("bl.cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(cardType)){
			sqlp.eq("bl.cardtype", cardType);
		}
		sql=sql.append(sqlp.getParam());
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	
	/**
	 * 根据产品类型和产品编号获取黑名单表记录
	 * 
	 * */
	public DarkList darklistByCardNoAndCardType(String cardNo,String cardType){
		DarkList temp = null;
		StringBuffer sql = new StringBuffer("select * from csms_dark_list where cardno=? and cardtype=?");
		List<Map<String, Object>> list = queryList(sql.toString(),cardNo,cardType);
		if (!list.isEmpty()) {
			temp = new DarkList();
			this.convert2Bean(list.get(0), temp);
		}

		return temp;
	}
	
	
	/**
	 * 根据产品类型和产品编号获取客户信息
	 * 
	 * */
	public Map<String,Object> customerByCardNoAndCardType(String cardNo,String cardType) {
		String sql = null;
		if("1".equals(cardType))
			sql = "select c.userno userno, c.organ organ, pp.customerid customerid from csms_customer c, (select p.customerid from csms_prepaidc p where cardno=?) pp where pp.customerid = c.id";
		else if("2".equals(cardType))
			sql = "select c.userno userno, c.organ organ, pp.customerid customerid from csms_customer c, (select ai.customerid from csms_accountc_info ai where cardno=?) pp where pp.customerid = c.id";
		else
			sql = "select c.userno userno, c.organ organ, pp.clientid customerid from csms_customer c, (select ti.clientid from csms_tag_info ti where tagno=?) pp where pp.clientid = c.id";
		List<Map<String, Object>> list = this.queryList(sql, cardNo);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据参数更新黑名单表
	 * 
	 * */
	public void updateDarklist(Long id,Date genDate,String genCau,Long operId,String operNo,String operName,String remark){
		String state = "1";//黑名单状态：待下发
		String sql = "update csms_dark_list set genDate=?,genCau=?,operId=?，operNo=?,operName=?,remark=?,state=? where id=?";
		saveOrUpdate(sql, genDate,genCau,operId,operNo,operName,remark,state,id);
	}
	public void updateDarklist(Long id,String obuSerial,Date genDate,String genCau,Long operId,String operNo,String operName,String remark){
		String state = "1";//黑名单状态：待下发
		String sql = "update csms_dark_list set obuSerial=?,genDate=?,genCau=?,operId=?，operNo=?,operName=?,remark=?,state=? where id=?";
		saveOrUpdate(sql, obuSerial,genDate,genCau,operId,operNo,operName,remark,state,id);
	}
	/**
	 * 根据产品类型和产品编号查看该产品是否已经存在记录
	 * 
	 * */
	public boolean findNoType(String cardNo,String cardType){
		String sql = null;
		if("1".equals(cardType))
			sql = "select p.customerid from csms_prepaidc p where cardno=?";
		else if("2".equals(cardType))
			sql = "select ai.customerid from csms_accountc_info ai where cardno=?";
		else
			sql = "select ti.clientid from csms_tag_info ti where tagno=?";
		List<Map<String, Object>> list = this.queryList(sql, cardNo);
		if(list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 待下发黑名单表接口
	 * 
	 * */
	public Pager getBlacklist(Pager pager,String cardNo,String cardType,String startGenDate,String endGenDate,String state){
		StringBuffer sql = new StringBuffer("with dl as"
										+" (select id id,"
										+" cardtype cardType,"
										+" cardno cardNo,"
										+" gencau genCau,"
										+" genmode genMode,"
										+" remark remark,"
										+" to_char(updatetime, 'yyyy-MM-dd hh24:mi:ss') genDate,"
										+" operid operId,"
										+" operno operNo,"
										+" opername operName,"
										+" to_char(state + 1) state,"
										+" to_char(updatetime, 'yyyy-MM-dd hh24:mi:ss') darklistDate"
										+" from csms_dark_list"
										+" where cardno not in (select cardcode from csms_tollcardblackdet_send union all select obuid from csms_tollcardblackdet_send)"
										+" union all"
										+" select id id,"
										+" cardtype cardType,"
										+" cardno cardNo,"
										+" gencau genCau,"
										+" genmode genMode,"
										+" remark remark,"
										+" to_char(updatetime, 'yyyy-MM-dd hh24:mi:ss') genDate,"
										+" operid operId,"
										+" operno operNo,"
										+" opername operName,"
										+" state state,"
										+" to_char(updatetime, 'yyyy-MM-dd hh24:mi:ss') darklistDate"
										+" from csms_dark_list"
										+" where cardno in (select cardcode from csms_tollcardblackdet_send union all select obuid from csms_tollcardblackdet_send))"
										+" select *"
										+" from dl"
										+" where 1 = 1 and state in (1,2,3)");
		SqlParamer sqlp=new SqlParamer();
		if(StringUtil.isNotBlank(cardNo)){
			sqlp.eq("cardNo", cardNo);
		}
		if(StringUtil.isNotBlank(cardType)){
			sqlp.eq("cardType", cardType);
		}
		if(StringUtil.isNotBlank(startGenDate)){
			sqlp.ge("gendate", startGenDate);
		}
		if(StringUtil.isNotBlank(endGenDate)){
			sqlp.le("gendate", endGenDate);
		}
		if(StringUtil.isNotBlank(state)){
			sqlp.eq("state", state);
		}
		sql=sql.append(sqlp.getParam());
		return this.findByPages(sql.toString(), pager,sqlp.getList().toArray());
	}
	
	/**
	 * 通过id查找黑名单中是否有该记录
	 * 
	 * */
	public boolean findDarklistById(String id){
		String sql = "select count(*) from csms_dark_list dl where 1=1 and id=?";
		int count = this.count(sql, id);
		return count>0;
	}
	
	public boolean findDarkListByNo(String cardNo){
		String sql = "select count(*) from csms_dark_list dl where 1=1 and cardno=?";
		int count = this.count(sql, cardNo);
		return count>0;
	}
	
	/**
	 * 修改黑名单状态为3，即待解除状态
	 * 
	 * */
	public void changeState(String id){
		String sql = "update csms_dark_list set state='3' where id=?";
		saveOrUpdate(sql, id);
	}
	
	/**
	 * 根据参数Id获取darkList
	 * 
	 * */
	public DarkList getDarklistById(String id ){
		DarkList temp = null;
		String sql = "select * from csms_dark_list where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		if (!list.isEmpty()) {
			temp = new DarkList();
			this.convert2Bean(list.get(0), temp);
		}
		return temp;
	}
}
