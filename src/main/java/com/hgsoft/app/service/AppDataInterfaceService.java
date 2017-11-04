package com.hgsoft.app.service;

import com.hgsoft.app.dao.AppDataDao;
import com.hgsoft.app.entity.VehplateAuthInfo;
import com.hgsoft.app.serviceInterface.IAppDataInterfaceService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ： 孙晓伟
 *         file : AppDataInterfaceService.java
 *         date : 2017/7/5
 *         time : 20:08
 */
@Service
public class AppDataInterfaceService implements IAppDataInterfaceService {

    private static Logger logger = Logger.getLogger(AppDataInterfaceService.class.getName());

    @Resource
    SequenceUtil sequenceUtil;

    @Resource
    private AppDataDao appDataDao;


    /**
     * 车牌认证信息申请
     * @param vehiclePlate
     * @param vehplateColor
     * @param source
     * @param applyTime
     * @param tel
     * @param cusPointPoJo
     * @param sysAdmin
     * @return
     */
    @Override
    public Map<String, Object> findVehplateAuthInfo(String vehiclePlate, String vehplateColor, String source, String applyTime, String tel, CusPointPoJo cusPointPoJo, SysAdmin sysAdmin) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            Map<String, Object> vehAuthInfoMap = appDataDao.findVehAuthInfo(vehiclePlate,vehplateColor);
            if(vehAuthInfoMap==null || vehAuthInfoMap.size()<1){
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                VehplateAuthInfo vehplateAuthInfo = new VehplateAuthInfo();
                Long seq = sequenceUtil.getSequenceLong("SEQ_VEHPLATEAUTHINFO");
                vehplateAuthInfo.setId(seq);
                vehplateAuthInfo.setVehiclePlate(vehiclePlate);
                vehplateAuthInfo.setVehicleColor(vehplateColor);
                vehplateAuthInfo.setSource(source);
                vehplateAuthInfo.setApplyTime(dateFormat.parse(applyTime));
                vehplateAuthInfo.setTel(tel);
                vehplateAuthInfo.setOperId(sysAdmin.getId());
                vehplateAuthInfo.setOperName(sysAdmin.getUserName());
                vehplateAuthInfo.setOperNo(sysAdmin.getStaffNo());
                vehplateAuthInfo.setPlaceId(cusPointPoJo.getCusPoint());
                vehplateAuthInfo.setPlaceName(cusPointPoJo.getCusPointName());
                vehplateAuthInfo.setPlaceNo(cusPointPoJo.getCusPointCode());
                vehplateAuthInfo.setOperTime(new Date());
                vehplateAuthInfo.setRemark("客服App接口：车牌认证申请信息");
                appDataDao.save(vehplateAuthInfo);
                map.put("result", "true");
                map.put("message", "车牌已经绑定");
            }else{
                map.put("result", "true");
                map.put("message", "车牌已经认证");
            }

        } catch (ParseException e) {
            logger.error(e.getMessage()+"App接口：车牌申请时间转化失败");
            e.printStackTrace();
        }catch (ApplicationException e) {
            logger.error(e.getMessage()+"App接口：车牌认证申请失败");
            e.printStackTrace();
            throw new ApplicationException("App接口：车牌认证申请失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryPrepaidCardInfo(String cardNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = appDataDao.queryPrepaidCardInfo(cardNo);
        if(map!=null&&!map.isEmpty()){
            map.put("result", "true");
            map.put("message", "查询成功");
        }else{
            map = new HashMap<String, Object>();
            map.put("result", "false");
            map.put("message", "查询结果为空");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCardNoByMobile(String mobile) {
        Map<String, Object> map = new HashMap<String,Object>();
        List<Map<String,Object>> list = null;
        list = appDataDao.queryCardNoByMobile(mobile);
        if(list == null || list.isEmpty()){
            map.put("result", "false");
            map.put("message", "查无此信息");
        }else{
            map.put("result", "true");
            map.put("list", list);
            map.put("message", "查询成功");
        }
        return  map;
    }

    @Override
    public Map<String, Object> queryOutsideCarInfo(String vehiclePlate, String vehicleColor) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = appDataDao.queryOutsideCarInfo(vehiclePlate,vehicleColor);
        if(map!=null&&!map.isEmpty()){
            map.put("result", "true");
            map.put("message", "查询成功");
        }else{
            map = new HashMap<String, Object>();
            map.put("result", "false");
            map.put("message", "查询结果为空");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryVehplateBindingInfo(String vehiclePlate, String vehicleColor) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = appDataDao.queryVehplateBindingInfo(vehiclePlate,vehicleColor);
        if(map!=null&&!map.isEmpty()){
            map.put("result", "true");
            map.put("message", "查询成功");
        }else{
            map = new HashMap<String, Object>();
            map.put("result", "false");
            map.put("message", "查询结果为空");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCardBindingInfo(String cardNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = appDataDao.queryCardBindingInfo(cardNo);
        if(map!=null&&!map.isEmpty()){
            map.put("result", "true");
            map.put("message", "查询成功");
        }else{
            map = new HashMap<String, Object>();
            map.put("result", "false");
            map.put("message", "查询结果为空");
        }
        return map;
    }
}
