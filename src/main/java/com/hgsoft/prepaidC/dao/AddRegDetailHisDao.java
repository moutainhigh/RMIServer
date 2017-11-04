package com.hgsoft.prepaidC.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.prepaidC.entity.AddRegDetail;
import com.hgsoft.prepaidC.entity.AddRegDetailHis;
import com.hgsoft.utils.SequenceUtil;

@Repository
public class AddRegDetailHisDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;
	
	public void save(AddRegDetailHis addRegDetailHis) {
		Map map = FieldUtil.getPreFieldMap(AddRegDetailHis.class,addRegDetailHis);
		StringBuffer sql=new StringBuffer("insert into csms_add_reg_detail_his");
		sql.append(map.get("insertNameStr"));
		saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public AddRegDetail findByCardNo(String cardNo,String flag) {
		String sql = "select * from csms_add_reg_detail_his where cardNo=?"+ " and flag=?" + "order by id asc";
		List<Map<String, Object>> list = queryList(sql,cardNo,flag);
		AddRegDetail addRegDetail = null;
		if (!list.isEmpty()) {
			addRegDetail = new AddRegDetail();
			this.convert2Bean(list.get(0), addRegDetail);
		}

		return addRegDetail;
	}
	
	
	public AddRegDetail findById(Long id) {
		String sql = "select * from csms_add_reg_detail_his where id=?";
		List<Map<String, Object>> list = queryList(sql,id);
		AddRegDetail addRegDetail = null;
		if (!list.isEmpty()) {
			addRegDetail = new AddRegDetail();
			this.convert2Bean(list.get(0), addRegDetail);
		}

		return addRegDetail;
	}
	
}
