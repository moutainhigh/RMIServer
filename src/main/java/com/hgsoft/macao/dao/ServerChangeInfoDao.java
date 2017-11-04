package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcCancelInfo;
import com.hgsoft.macao.entity.ServerChangeInfo;

@Repository
public class ServerChangeInfoDao extends BaseDao{
	
	public void save(ServerChangeInfo serverChangeInfo) {
		Map map = FieldUtil.getPreFieldMap(ServerChangeInfo.class,serverChangeInfo);
		StringBuffer sql=new StringBuffer("insert into csms_serverchange_info");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public ServerChangeInfo findServerChangeInfo(){
		String sql = "select "+FieldUtil.getFieldMap(ServerChangeInfo.class, new ServerChangeInfo()).get("nameStr")+" from csms_serverchange_info  order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		ServerChangeInfo serverChangeInfo = null;
		if (!list.isEmpty()) {
			serverChangeInfo = new ServerChangeInfo();
			this.convert2Bean(list.get(0), serverChangeInfo);
		}

		return serverChangeInfo;
	}
	
	public void update(String errorCode,Long id){
		StringBuffer sql=new StringBuffer("update csms_serverchange_info set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from csms_serverchange_info  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
}
