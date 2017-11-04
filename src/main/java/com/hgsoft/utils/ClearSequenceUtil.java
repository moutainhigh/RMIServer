package com.hgsoft.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.dao.ClearBaseDao;
/**
 * 
 * @ClassName: SequenceUtil
 * @Description: 获取序列
 * @author gaosiling
 * @date 2016年1月14日08:19:57
*/
@Component("clearSequenceUtil")
public class ClearSequenceUtil extends ClearBaseDao {

	/**
	 * 获取序列
	 * @param sequence
	 * @return
	 * @throws IOException
	 * @author gaosiling
	 */
	@SuppressWarnings("rawtypes")
	public BigDecimal getSequence(String sequence) {
		String sql="select "+sequence+".nextval from  dual";
		BigDecimal sequenceNo = (BigDecimal) queryObject(sql, null);
		return sequenceNo;
	}
	
	@SuppressWarnings("rawtypes")
	public Long getSequenceLong(String sequence) {
		String sql="select "+sequence+".nextval from  dual";
		BigDecimal sequenceNo = (BigDecimal) queryObject(sql, null);
		Long seq = Long.parseLong(sequenceNo.toString());
		return seq;
	}

}
