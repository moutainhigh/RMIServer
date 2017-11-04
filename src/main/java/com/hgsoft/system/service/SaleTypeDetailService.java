package com.hgsoft.system.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.SaleTypeDetailDao;
import com.hgsoft.system.entity.SaleType;
import com.hgsoft.system.entity.SaleTypeDetail;
import com.hgsoft.system.serviceInterface.ISaleTypeDetailService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 销售类型
 * @author gaosiling
 * 2016-08-09 13:23:04
 */
@Service
public class SaleTypeDetailService implements ISaleTypeDetailService{

	private static Logger logger = Logger.getLogger(ParamConfigService.class.getName());
	
	@Resource
	private SaleTypeDetailDao saleTypeDetailDao;
	
	@Resource
	private SequenceUtil sequenceUtil;
	
	@Override
	public void save(SaleTypeDetail saleTypeDetail) {
		Long seq = sequenceUtil.getSequenceLong("seq_csmsSaleTypeDetail_no");
		saleTypeDetail.setId(seq);
		String code = seq.toString();
		for (int i = 0; i <seq.toString().length() && i < 4; i++) {
			code = "0"+code;
		}
		
		saleTypeDetail.setCode(code);
		saleTypeDetail.setSettime(new Date());
		try {
			saleTypeDetailDao.save(saleTypeDetail);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
	}
	
	@Override
	public SaleTypeDetail find(SaleTypeDetail saleTypeDetail) {
		try {
			return saleTypeDetailDao.find(saleTypeDetail);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
	}
	
	@Override
	public SaleTypeDetail findById(Long id) {
		try {
			return saleTypeDetailDao.findById(id);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
	}
	@Override
	public List<SaleTypeDetail> findByparamNo(String paramNo){
		try {
			return saleTypeDetailDao.findByparamNo(paramNo);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
		
	}
	@Override
	public List<SaleTypeDetail> findBySaleTypeDetail(SaleTypeDetail paramConfig){
		try {
			return saleTypeDetailDao.findBySaleTypeDetail(paramConfig);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
		
	}
	
	@Override
	public Pager findByPager(Pager pager, SaleTypeDetail saleTypeDetail) {
		try {
			return saleTypeDetailDao.findByPager(pager, saleTypeDetail);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
	}
	
	@Override
	public void update(SaleTypeDetail saleTypeDetail) {
		try {
			saleTypeDetailDao.update(saleTypeDetail);
		} catch (ApplicationException e) {
			logger.error("修改销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("修改销售类型失败");
		}
	}

	@Override
	public List<SaleType> findBySaleType(SaleType saleType) {
		// TODO Auto-generated method stub
		try {
			return saleTypeDetailDao.findBySaleType(saleType);
		} catch (ApplicationException e) {
			logger.error("查询销售类型失败");
			e.printStackTrace();
			throw new ApplicationException("查询销售类型失败");
		}
	}
}
