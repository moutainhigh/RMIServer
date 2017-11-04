package com.hgsoft.report.service;

import com.hgsoft.report.dao.ReportFormsDao;
import com.hgsoft.report.entity.CustomArea;
import com.hgsoft.report.entity.CustomPointType;
import com.hgsoft.report.serviceInterface.IReportFormsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportFormsService implements IReportFormsService{
    private static Logger logger = Logger.getLogger(IReportFormsService.class.getName());
    @Resource
    private ReportFormsDao reportFormsDao;
    
    /**
     * 获得网点类型列表
     * @return
     */
    @Override
    public List<CustomPointType> customPointTypeList(){
    	try {
        
        	List<CustomPointType> CustomPointTypeList = reportFormsDao.findAllTypeIdAndName();
            return CustomPointTypeList;
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error("客服报表查询网点类型列表错误！请联系管理员");
        }
        return null;
    }
    
    /**
     * 获得网点区域列表
     * @return
     */
    @Override
    public List<CustomArea> customPointAreaList() {
        try {
            List<CustomArea> CustomAreaList = reportFormsDao.findAllAreaCodeAndName();
            return CustomAreaList;
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error("客服报表查询网点区域列表错误！请联系管理员");
        }
        return null;
    }
}
