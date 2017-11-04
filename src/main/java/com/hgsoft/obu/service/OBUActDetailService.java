package com.hgsoft.obu.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.obu.dao.OBUActDetailDao;
import com.hgsoft.obu.entity.OBUActDetail;
import com.hgsoft.obu.serviceInterface.IOBUActDetailService;

/**
 * obu激活卡明细
 * @author gaosiling
 * 2016年1月21日14:42:42
 */
@Service
public class OBUActDetailService implements IOBUActDetailService{
	
	private static Logger logger = Logger.getLogger(OBUActDetailService.class.getName());

	@Resource
	private OBUActDetailDao oBUActDetailDao;
	
	/**
	 * OBU激活卡明细
	 * @param  Id
	 * @author gaosiling
	 */
	public OBUActDetail findById(OBUActDetail obuActDetail){
		return oBUActDetailDao.find(obuActDetail);
	}
	
	public List<OBUActDetail> findList(OBUActDetail obuActDetail){
		return oBUActDetailDao.findList(obuActDetail);
	}
	public List<OBUActDetail> findListByMainId(OBUActDetail obuActDetail){
		return oBUActDetailDao.findListByMainId(obuActDetail);
	}
}
