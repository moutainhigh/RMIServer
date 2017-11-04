package com.hgsoft.httpInterface.service;


import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.hgsoft.httpInterface.dao.MinusBankDao;
import com.hgsoft.httpInterface.serviceInterface.IMinusBankInterfaceService;
import com.hgsoft.system.entity.OMSMinusBank;

@Service
public class MinusBankInterfaceService implements IMinusBankInterfaceService {
	
	private static Logger logger = Logger.getLogger(MinusBankInterfaceService.class.getName());

	@Resource
	private MinusBankDao minusBankDao;
	
	/**
	 * @Descriptioqn:
	 * @param omsMinusBank
	 * @author lgm
	 * @date 2017年3月7日
	 */
	@Override
	public boolean add(OMSMinusBank omsMinusBank) {
		try {
			minusBankDao.add(omsMinusBank);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Descriptioqn:
	 * @param id
	 * @author lgm
	 * @date 2017年3月7日
	 */
	@Override
	public void delete(Long id) {
		minusBankDao.delete(id);
	}

	/**
	 * @Descriptioqn:
	 * @param omsMinusBank
	 * @author lgm
	 * @date 2017年3月7日
	 */
	@Override
	public void update(OMSMinusBank omsMinusBank) {
		minusBankDao.update(omsMinusBank);
	}

}