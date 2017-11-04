package com.hgsoft.macao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.macao.entity.AcIssuceInfo;
import com.hgsoft.macao.entity.AcLossInfo;

@Repository
public class AcIssuceInfoDao extends BaseDao{
	
	public void save(AcIssuceInfo acIssuceInfo) {
		Map map = FieldUtil.getPreFieldMap(AcIssuceInfo.class,acIssuceInfo);
		StringBuffer sql=new StringBuffer("insert into CSMS_ACISSUCE_INFO");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	/**
	 * 随机查出一条数据
	 * @return
	 */
	public AcIssuceInfo findAcIssuceInfo(){
		// 这里采用随机抽取一条数据是为了保证发送失败的时候取的不是原来那条失败的数据
		String sql = "select "+FieldUtil.getFieldMap(AcIssuceInfo.class, new AcIssuceInfo()).get("nameStr")+" from CSMS_ACISSUCE_INFO order by dbms_random.value() ";
		
		/*SqlParamer params = new SqlParamer();
		
		sql = sql + params.getParam();
		Object[] Objects = params.getList().toArray();*/
		
		List<Map<String, Object>> list = queryList(sql,null);
		AcIssuceInfo acIssuceInfo = null;
		if (!list.isEmpty()) {
			acIssuceInfo = new AcIssuceInfo();
			this.convert2Bean(list.get(0), acIssuceInfo);
		}

		return acIssuceInfo;
	}
	
	public void update(String errorCode,Long id){
		// TODO: 2017/3/25 serviceFlowNo这个是否要操作
		StringBuffer sql=new StringBuffer("update CSMS_ACISSUCE_INFO set errorCode=? where id = ?");
		saveOrUpdate(sql.toString(), errorCode,id);
	}
	
	public void delete(Long id){
		StringBuffer sql=new StringBuffer("delete from CSMS_ACISSUCE_INFO  where id = ?");
		saveOrUpdate(sql.toString(),id);
	}
	
}
