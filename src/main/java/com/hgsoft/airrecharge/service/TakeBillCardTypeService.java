package com.hgsoft.airrecharge.service;

import com.hgsoft.airrecharge.dao.TakeBillCardTypeDao;
import com.hgsoft.airrecharge.serviceInterface.ITakeBillCardTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : 吴锡霖
 * file : TakeBillCardTypeService.java
 * date : 2017-06-28
 * time : 9:41
 */
@Service
public class TakeBillCardTypeService implements ITakeBillCardTypeService {
    @Resource
    private TakeBillCardTypeDao takeBillCardTypeDao;
    
    @Override
    public String findRechargeLower(String firstRecharge) {
        String sql = "SELECT FirstLimit,AgainLimit from OMS_takeBillCardType" +
                " WHERE SystemState=2 AND ValidState=1 AND CheckFlag=4";
        List<Map<String, Object>> result = takeBillCardTypeDao.queryList(sql);
        if (result.isEmpty()) {
            return null;
        }
        if ("1".equals(firstRecharge)) {
            return (String) result.get(0).get("FIRSTLIMIT");
        }
        if ("0".equals(firstRecharge)) {
            return (String) result.get(0).get("AgainLimit");
        }
        return null;
    }
}
