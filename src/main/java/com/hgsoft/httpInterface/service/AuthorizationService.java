package com.hgsoft.httpInterface.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.httpInterface.entity.BusinessAccredit;
import com.hgsoft.httpInterface.dao.AuthorizationDao;
import com.hgsoft.httpInterface.entity.BusinessAccreditAdmin;
import com.hgsoft.httpInterface.entity.BusinessAccreditAdminHis;
import com.hgsoft.httpInterface.entity.BusinessAccreditHis;
import com.hgsoft.httpInterface.serviceInterface.IAuthorizationService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by apple on 2016/12/12.
 */
@Service
public class AuthorizationService implements IAuthorizationService {
    private static Logger logger = Logger
            .getLogger(AuthorizationService.class.getName());
    @Resource
    private AuthorizationDao authorizationDao;
    @Resource
    private SequenceUtil sequenceUtil;

    @Override
    public Map<String, Object> saveBusinessAccredit(BusinessAccredit businessAccredit) {
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            boolean rel=authorizationDao.findBusinessAccredit(businessAccredit.getName());
            if (rel){
                resultMap.put("result", "false");
                resultMap.put("message", "业务名称不能重复,请重新输入!");
            }else{
                authorizationDao.saveBusinessAccredit(businessAccredit);
                resultMap.put("result", "true");
                resultMap.put("message", "保存业务授权信息成功");
            }
        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"保存业务授权信息失败!");
            e.printStackTrace();
            throw new ApplicationException("保存业务授权信息失败!");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteBusinessAccredit(Long id){
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            //保存业务授权记录历史
            BusinessAccreditHis businessAccreditHis = new BusinessAccreditHis();
            businessAccreditHis.setGenReason("2");//删除
            businessAccreditHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusinessAccreditHis_NO"));

            BusinessAccredit businessAccredit = authorizationDao.findBusinessAccredit(id);
            if (businessAccredit!=null){
                authorizationDao.saveBusinessAccreditHis(businessAccreditHis, businessAccredit);

                //保存业务授权用户记录历史
                List<BusinessAccreditAdmin> list = authorizationDao.findBusinessAccreditAdmin(id);
                Iterator<BusinessAccreditAdmin> it = list.iterator();
                while (it.hasNext())
                {
                    BusinessAccreditAdmin businessAccreditAdmin = it.next();
                    BusinessAccreditAdminHis businessAccreditAdminHis = new BusinessAccreditAdminHis();
                    businessAccreditAdminHis.setGenReason("2");
                    businessAccreditAdminHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusAccreditAdmHis_NO"));
                    authorizationDao.saveBusinessAccreditAdminHis(businessAccreditAdminHis, businessAccreditAdmin.getId());
                    authorizationDao.deleteBusinessAccreditAdmin(businessAccreditAdmin.getId());
                }

                authorizationDao.deleteBusinessAccredit(id);
                resultMap.put("result", "true");
                resultMap.put("message", "删除业务授权信息成功");
            }else{
                resultMap.put("result", "false");
                resultMap.put("message", "该业务授权信息不存在");
            }
        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"删除业务授权信息失败 id="+id);
            e.printStackTrace();
            throw new ApplicationException("删除业务授权信息失败");
        }

        return resultMap;
    }
    @Override
    public Map<String, Object> deleteBusinessAccreditAdmin(Long id) {
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            boolean rel=authorizationDao.findDelBusinessAccreditAdmin(id);
            if (rel){
                BusinessAccreditAdminHis businessAccreditAdminHis = new BusinessAccreditAdminHis();
                businessAccreditAdminHis.setGenReason("2");
                businessAccreditAdminHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusAccreditAdmHis_NO"));
                authorizationDao.saveBusinessAccreditAdminHis(businessAccreditAdminHis, id);
                authorizationDao.deleteBusinessAccreditAdmin(id);
                resultMap.put("result", "true");
                resultMap.put("message", "删除业务授权用户信息成功");
            }else{
                resultMap.put("result", "false");
                resultMap.put("message", "该业务授权用户信息不存在");
            }
        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"删除业务授权用户信息失败 id="+id);
            e.printStackTrace();
            throw new ApplicationException("删除业务授权用户信息失败");
        }
        return resultMap;

    }

    @Override
    public Pager findBusinessAccreditList(Pager pager,String name) {
        return authorizationDao.findBusinessAccreditList(pager,name);
    }

    @Override
    public Map<String, Object> modifyBusinessAccredit(BusinessAccredit businessAccredit) {
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            //保存业务授权记录历史
            BusinessAccreditHis businessAccreditHis = new BusinessAccreditHis();
            businessAccreditHis.setGenReason("1");//修改
            businessAccreditHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusinessAccreditHis_NO"));
            BusinessAccredit old_businessAccredit=  authorizationDao.findBusinessAccredit(businessAccredit.getId());
            authorizationDao.saveBusinessAccreditHis(businessAccreditHis, old_businessAccredit);

            authorizationDao.updateBusinessAccredit(businessAccredit);
            resultMap.put("result", "true");
            resultMap.put("message", "修改业务授权信息成功");
        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"修改业务授权信息失败");
            e.printStackTrace();
            throw new ApplicationException("修改业务授权信息失败");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> switchBusinessAccredit(BusinessAccredit businessAccredit) {
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            //保存业务授权记录历史
            BusinessAccreditHis businessAccreditHis = new BusinessAccreditHis();
            businessAccreditHis.setGenReason("1");//修改
            businessAccreditHis.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusinessAccreditHis_NO"));
            BusinessAccredit old_businessAccredit=  authorizationDao.findBusinessAccredit(businessAccredit.getId());
            authorizationDao.saveBusinessAccreditHis(businessAccreditHis, old_businessAccredit);

            businessAccredit.setName(old_businessAccredit.getName());
            businessAccredit.setUrl(old_businessAccredit.getUrl());
            businessAccredit.setRemark(old_businessAccredit.getRemark());
            businessAccredit.setBusinessState(old_businessAccredit.getBusinessState());
            authorizationDao.updateBusinessAccredit(businessAccredit);
            resultMap.put("result", "true");
            resultMap.put("message", "修改业务授权信息成功");
        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"修改业务授权信息失败");
            e.printStackTrace();
            throw new ApplicationException("修改业务授权信息失败");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> saveBusinessAccreditAdmin(BusinessAccreditAdmin businessAccreditAdmin) {
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            int admincount=authorizationDao.findBusinessAccreditAdmin(businessAccreditAdmin.getBusinessAccredit(),businessAccreditAdmin.getAdmin());
            if(admincount==0){
                authorizationDao.saveBusinessAccreditAdmin(businessAccreditAdmin);
                resultMap.put("result", "true");
                resultMap.put("message", "保存业务授权用户信息成功");
            }else{
                resultMap.put("result", "false");
                resultMap.put("message", "保存业务授权用户信息失败,该用户已授权!");
            }
        } catch (ApplicationException e) {
            logger.error(e.getMessage()+"保存业务授权信息失败");
            e.printStackTrace();
            throw new ApplicationException("保存业务授权信息失败");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> findBusinessAccredit(Long id) {
        return authorizationDao.findBusinessAccreditMap(id);
    }

    @Override
    public Pager findBusinessAccreditAdminList(Pager pager, Long id) {
        return authorizationDao.findBusinessAccreditAdminList(pager,id);
    }
}
