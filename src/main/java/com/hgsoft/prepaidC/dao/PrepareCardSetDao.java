package com.hgsoft.prepaidC.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.prepaidC.entity.PrepareCardSet;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PrepareCardSetDao extends BaseDao {
	
	@Resource
	SequenceUtil sequenceUtil;


	public PrepareCardSet findByCardNo(String cardNo) {
		String sql = "select * from OMS_prepareCardSet where startCode = ? and flag = '2'";
		List<PrepareCardSet> prepareCardSets = super.queryObjectList(sql, PrepareCardSet.class, cardNo);
		if (prepareCardSets == null || prepareCardSets.isEmpty()) {
			return null;
		}
		return prepareCardSets.get(0);
	}
}
