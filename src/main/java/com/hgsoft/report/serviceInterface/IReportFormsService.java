package com.hgsoft.report.serviceInterface;

import java.util.List;

import com.hgsoft.report.entity.CustomArea;
import com.hgsoft.report.entity.CustomPointType;

public interface IReportFormsService {
    public List<CustomPointType> customPointTypeList();
    public List<CustomArea> customPointAreaList();

}
