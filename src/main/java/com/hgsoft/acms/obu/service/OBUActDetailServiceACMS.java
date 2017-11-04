package com.hgsoft.acms.obu.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.acms.obu.dao.OBUActDetailDaoACMS;
import com.hgsoft.acms.obu.serviceInterface.IOBUActDetailServiceACMS;
import com.hgsoft.obu.entity.OBUActDetail;

/**
 * obu激活卡明细
 * @author gaosiling
 * 2016年1月21日14:42:42
 */
@Service
public class OBUActDetailServiceACMS implements IOBUActDetailServiceACMS{
	
	private static Logger logger = Logger.getLogger(OBUActDetailServiceACMS.class.getName());

	@Resource
	private OBUActDetailDaoACMS obuActDetailDaoACMS;
	
	/**
	 * OBU激活卡明细
	 * @param  Id
	 * @author gaosiling
	 */
	public OBUActDetail findById(OBUActDetail obuActDetail){
		return obuActDetailDaoACMS.find(obuActDetail);
	}
	
	public List<OBUActDetail> findList(OBUActDetail obuActDetail){
		return obuActDetailDaoACMS.findList(obuActDetail);
	}
}
