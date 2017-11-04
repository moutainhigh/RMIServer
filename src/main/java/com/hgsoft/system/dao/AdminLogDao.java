package com.hgsoft.system.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.system.entity.AdminLog;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;

@Component
public class AdminLogDao extends BaseDao{

	public Pager findByPager(Pager pager,AdminLog adminLog,Date startTime,Date endTime) {
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer("select id,type,admin,loginName,userName,module,moduleName,logData,createTime,remark from CSMS_adminLog where 1=1 ");
		if(startTime !=null){
			params.geDate("createTime", params.getFormat().format(startTime));
		}
		if(endTime !=null){
			params.leDate("createTime", params.getFormatEnd().format(endTime));
		}
		
		if(adminLog.getAdmin()!=null){
			params.eq("admin", adminLog.getAdmin());
		}
		if(StringUtil.isNotBlank(adminLog.getType())){
			params.eq("type", adminLog.getType());
		}
		/*if(StringUtil.isNotBlank(obuActRecord.getWritebackFlag())){
			params.eq("t.WritebackFlag", obuActRecord.getWritebackFlag());
		}*/
		sql.append(params.getParam());
		sql.append(" order by createTime desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public Pager findServiceByPager(Pager pager,Long operId,String type,Date startTime,Date endTime) {
		SqlParamer params=new SqlParamer();
		StringBuffer sql =new StringBuffer(
			/*	select type,state,userid,realprice,tradetime,operid,opername,placeno,placename,organ from(
         select '1' type,'1'||ab.state state,ab.userid userid,ab.realprice realprice,ab.tradetime tradetime,ab.operid operid,
         ab.opername opername,ab.placeno placeno,ab.placename placename,c.organ organ 
         from CSMS_account_bussiness ab join csms_customer c on ab.userid = c.id where c.systemtype!='2' 
         union all 
         select '2' type,'2'||pb.state state,pb.userid userid,pb.realprice realprice,pb.tradetime tradetime,pb.operid operid,
         pb.opername opername,pb.placeno placeno,pb.placename placename,c.organ organ 
         from CSMS_PrePaidC_bussiness pb join csms_customer c on pb.userid = c.id where  c.systemtype!='2' 
         union all 
         select '3' type,'3'||atb.state state,atb.userid userid,atb.realprice realprice,atb.tradetime tradetime,atb.operid operid,
         atb.opername opername,atb.placeno placeno,atb.placename placename,c.organ organ 
         from CSMS_AccountC_bussiness atb join csms_customer c on atb.userid = c.id  where  c.systemtype!='2' 
         union all 
         select '4' type,'4'||tbr.BusinessType state,tbr.clientid userid,tbr.RealPrice realprice,tbr.OperTime tradetime,tbr.operid operid,
         tbr.opername opername,tbr.placeno placeno,tbr.placename placename,c.organ organ 
         from CSMS_Tag_BusinessRecord tbr join csms_customer c on tbr.clientid = c.id  where  c.systemtype!='2' 
         union all 
         select '5' type,'5'||cbr.Type state,cbr.customerID userid,0 realprice,cbr.createTime tradetime,cbr.operid operid,
         cbr.opername opername,cbr.placeno placeno,cbr.placename placename,c.organ organ 
         from CSMS_Customer_Bussiness cbr join csms_customer c on cbr.customerID = c.id  where  c.systemtype!='2' 
        ) where 1=1 */
				
				" select type,state,userid,realprice,tradetime,operid,opername,placeno,placename,organ from service_view where 1=1 "
				
		  );
		if(startTime !=null){
			params.geDate("tradetime", params.getFormat().format(startTime));
		}
		if(endTime !=null){
			params.leDate("tradetime", params.getFormatEnd().format(endTime));
		}
		if(StringUtil.isNotBlank(type)){
			params.eq("state", type);
		}
		if(operId !=null){
			params.eq("operid", operId);
		}
		sql.append(params.getParam());
		sql.append(" order by tradetime desc ");
		Object[] Objects= params.getList().toArray();
		return this.findByPages(sql.toString(), pager,Objects);
	}
	
	public void save(AdminLog adminLog) {
		Map map = FieldUtil.getPreFieldMap(AdminLog.class,adminLog);
		StringBuffer sql=new StringBuffer("insert into csms_adminlog");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}


	public void update(AdminLog adminLog) {
		Map map = FieldUtil.getPreFieldMap(AdminLog.class,adminLog);
		StringBuffer sql=new StringBuffer("update csms_adminlog set ");
		sql.append(map.get("updateNameStr") +" where id = ?");
		saveOrUpdate(sql.toString(), (List) map.get("param"),adminLog.getId());
	}
	
	
	
	public AdminLog find(AdminLog adminLog) {
		AdminLog temp = null;
		if (adminLog != null) {
			StringBuffer sql = new StringBuffer("select id,type,admin,loginName,userName,module,moduleName,logData,createTime,remark from csms_adminlog ");
			Map map = FieldUtil.getPreFieldMap(AdminLog.class,adminLog);
			sql.append(map.get("selectNameStrNotNull"));
			sql.append(" order by id");
			List<Map<String, Object>> list = queryList(sql.toString(), ((List) map.get("paramNotNull")).toArray());
			if (!list.isEmpty()) {
				temp = new AdminLog();
				this.convert2Bean(list.get(0), temp);
			}

		}
		return temp;
	}
	
	public AdminLog findById(Long id) {
		String sql = "select id,type,admin,loginName,userName,module,moduleName,logData,createTime,remark from csms_adminlog where id=? ";
		List<Map<String, Object>> list = queryList(sql,id);
		AdminLog adminLog = null;
		if (!list.isEmpty()) {
			adminLog = new AdminLog();
			this.convert2Bean(list.get(0), adminLog);
		}

		return adminLog;
	}
}
