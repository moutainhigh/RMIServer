package com.hgsoft.other.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.other.clearDao.MhTestCardDao;
import com.hgsoft.other.dao.TestCardDao;
import com.hgsoft.other.entity.TestCard;
import com.hgsoft.other.serviceInterface.ITestCardService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@Service
public class TestCardService implements ITestCardService{
	private static Logger logger = Logger.getLogger(TestCardService.class.getName());
	
	@Resource
	private TestCardDao testCardDao;
	@Resource
	private MhTestCardDao mhTestCardDao;
	@Resource
	SequenceUtil sequenceUtil;
	
	@Override
	public Pager findByPager(Pager pager, TestCard testCard) {
		return testCardDao.findByPager(pager, testCard);
	}

	@Override
	public Map<String, Object> save(TestCard testCard) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("hasMsg", true);
		map.put("msg", "保存成功");
		try {
//			int count = testCardDao.findByPK(testCard);
//			if (count !=0) {
//				map.put("hasMsg", false);
//				map.put("msg", "项目编号+车型数据不能重复，请重新输入");
//				return map;
//			}
			
			BigDecimal ID = sequenceUtil.getSequence("TB_TESTCARD_SEQ");
			testCard.setId(Long.valueOf(ID.toString()));
			//保存本地
			testCardDao.save(testCard);
			//保存铭鸿
			mhTestCardDao.save(testCard);
		} catch (Exception e) {
			logger.error("保存失败,id:"+testCard.getId());
			e.printStackTrace();
			throw new ApplicationException("保存失败,id:"+testCard.getId());
		}
		return map;
	}

	@Override
	public TestCard findById(Long id) {
		return testCardDao.findById(id);
	}

	@Override
	public Map<String, Object> update(TestCard oldJoinCard, TestCard testCard) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("hasMsg", true);
		map.put("msg", "修改成功");
		try {
//			if (!oldJoinCard.getProjectNo().equals(testCard.getProjectNo())
//					&& !oldJoinCard.getCarType().equals(testCard.getCarType())) 
//			{
//				int count = testCardDao.findByPK(testCard);
//				if (count !=0) {
//					map.put("hasMsg", false);
//					map.put("msg", "项目编号+车型数据不能重复，请重新输入");
//					return map;
//				}
//			}

			//更新本地
			testCardDao.update(testCard);
			//更新铭鸿数据库
			mhTestCardDao.update(testCard);
			
		} catch (Exception e) {
			logger.error("修改失败,id:"+testCard.getId());
			e.printStackTrace();
			throw new ApplicationException("修改失败,id:"+testCard.getId());
		}
		return map;
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		TestCardService.logger = logger;
	}

	public TestCardDao getTestCardDao() {
		return testCardDao;
	}

	public void setTestCardDao(TestCardDao testCardDao) {
		this.testCardDao = testCardDao;
	}

	public MhTestCardDao getMhTestCardDao() {
		return mhTestCardDao;
	}

	public void setMhTestCardDao(MhTestCardDao mhTestCardDao) {
		this.mhTestCardDao = mhTestCardDao;
	}

	public SequenceUtil getSequenceUtil() {
		return sequenceUtil;
	}

	public void setSequenceUtil(SequenceUtil sequenceUtil) {
		this.sequenceUtil = sequenceUtil;
	}

}
