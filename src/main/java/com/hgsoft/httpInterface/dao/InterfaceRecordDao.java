package com.hgsoft.httpInterface.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.InterfaceRecord;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.StringUtil;

@Repository
public class InterfaceRecordDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;

	public InterfaceRecord findByNullCsmsState(String cardNo,String type) {
		InterfaceRecord interfaceRecord = null;
		if (StringUtil.isNotBlank(type)&&StringUtil.isNotBlank(cardNo)) {
			StringBuffer sql = new StringBuffer("select * from csms_interfacerecord where csmsstate is null and type=? and cardno=?");
			List<Map<String, Object>> list = queryList(sql.toString(),type,cardNo);
			if (!list.isEmpty()&&list.size()==1) {
				interfaceRecord = new InterfaceRecord();
				this.convert2Bean(list.get(0), interfaceRecord);
			}

		}
		return interfaceRecord;
	}
	
	public int save(InterfaceRecord interfaceRecord) {
		Map map = FieldUtil.getPreFieldMap(InterfaceRecord.class,interfaceRecord);
		StringBuffer sql=new StringBuffer("insert into csms_interfacerecord");
		sql.append(map.get("insertNameStr"));
		return saveOrUpdate(sql.toString(), (List) map.get("param"));
	}
	
	public void update(InterfaceRecord interfaceRecord) {
		StringBuffer sql=new StringBuffer("update csms_interfacerecord set ");
		sql.append(FieldUtil.getFieldMap(InterfaceRecord.class,interfaceRecord).get("nameAndValue")+" where id="+interfaceRecord.getId());
		update(sql.toString());
	}
	/**
	 * 根据起始、结束电子标签号查找：客服状态为null的记录
	 * @param startCode 起始编号
	 * @param endCode 结束编号
	 * @param type 类型（1.提货、2.冲正）
	 * @return
	 */
	public InterfaceRecord findByStartEndType(String startCode,String endCode,String type) {
		InterfaceRecord interfaceRecord = null;
		if (StringUtil.isNotBlank(type)&&StringUtil.isNotBlank(startCode)&&StringUtil.isNotBlank(endCode)) {
			StringBuffer sql = new StringBuffer("select * from csms_interfacerecord where csmsstate is null and type=? and startCode=? and endCode=? ");
			List<Map<String, Object>> list = queryList(sql.toString(),type,startCode,endCode);
			if (!list.isEmpty()&&list.size()==1) {
				interfaceRecord = new InterfaceRecord();
				this.convert2Bean(list.get(0), interfaceRecord);
			}

		}
		return interfaceRecord;
	}
	
	
}
