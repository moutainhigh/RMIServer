package com.hgsoft.utils;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.hgsoft.common.dao.OmsDao;
/**
 * 
 * @ClassName: SequenceUtil
 * @Description: 获取序列
 * @author gaosiling
 * @date 2016年1月14日08:19:57
*/
@Component("oMSSequenceUtil")
public class OMSSequenceUtil extends OmsDao {

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
