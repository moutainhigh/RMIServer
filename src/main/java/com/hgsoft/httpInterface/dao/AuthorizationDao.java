package com.hgsoft.httpInterface.dao;

import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.common.util.FieldUtil;
import com.hgsoft.httpInterface.entity.*;
import com.hgsoft.prepaidC.entity.ReturnFee;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import com.hgsoft.utils.SqlParamer;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2016/12/12.
 */
@Repository
public class AuthorizationDao extends BaseDao {
    @Resource
    SequenceUtil sequenceUtil;

    public void saveBusinessAccredit(BusinessAccredit businessAccredit) {
        businessAccredit.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusinessAccredit_NO"));
        StringBuffer sql=new StringBuffer("insert into CSMS_BusinessAccredit");
        Map map = FieldUtil.getPreFieldMap(BusinessAccredit.class,businessAccredit);
        sql.append(map.get("insertNameStr"));
        saveOrUpdate(sql.toString(), (List) map.get("param"));
    }

    public void updateBusinessAccredit(BusinessAccredit businessAccredit) {
        StringBuffer sql=new StringBuffer("update CSMS_BusinessAccredit set ");
        sql.append(FieldUtil.getFieldMap(BusinessAccredit.class,businessAccredit).get("nameAndValue")+" where id="+businessAccredit.getId());
        update(sql.toString());
    }

    public void saveBusinessAccreditAdmin(BusinessAccreditAdmin businessAccreditAdmin) {
        businessAccreditAdmin.setId(sequenceUtil.getSequenceLong("SEQ_CSMSBusinessAccreditAdm_NO"));
        StringBuffer sql=new StringBuffer("insert into CSMS_BusinessAccredit_Admin");
        Map map = FieldUtil.getPreFieldMap(BusinessAccreditAdmin.class,businessAccreditAdmin);
        sql.append(map.get("insertNameStr"));
        saveOrUpdate(sql.toString(), (List) map.get("param"));
    }

    //删除业务授权记录
    public void deleteBusinessAccredit(Long id) {
        String sql = "delete from CSMS_businessAccredit where id = " + id;
        super.delete(sql);
    }

    public Map<String, Object> findBusinessAccreditMap(Long id){
        String sql="select id,name,url,remark,usestate,businessState,operateid,to_char(operatetime,'yyyy/MM/dd HH:mm:ss') as operatetime from CSMS_businessAccredit where id=?";
        List list=queryList(sql, id);
//        BusinessAccredit businessAccredit = null;
//        try {
//            if(list!=null && list.size()!=0) {
//                businessAccredit = (BusinessAccredit) this.convert2Bean((Map<String, Object>) list.get(0), new BusinessAccredit());
//            }
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        return list!=null && list.size()!=0?(Map<String, Object>) list.get(0):null;
    }

    public BusinessAccredit findBusinessAccredit(Long id){
        String sql="select * from CSMS_businessAccredit where id=?";
        List list=queryList(sql, id);
        BusinessAccredit businessAccredit = null;
        if(list!=null && list.size()!=0) {
            businessAccredit = (BusinessAccredit) this.convert2Bean((Map<String, Object>) list.get(0), new BusinessAccredit());
        }

        return businessAccredit;
    }

    public void saveBusinessAccreditHis(BusinessAccreditHis businessAccreditHis, BusinessAccredit businessAccredit) {
        StringBuffer sql = new StringBuffer(
                "insert into CSMS_BusinessAccredit_His(id,name,url,remark,useState,operateID,operateTime,businessState,businessAccredit,genTime,genReason)  "
                        + "SELECT "+businessAccreditHis.getId()+",name,url,remark,useState,operateID,operateTime,businessState,"+businessAccredit.getId()+",sysdate,'"
                        + businessAccreditHis.getGenReason()+"'"
                        + " FROM CSMS_BusinessAccredit WHERE id="+businessAccredit.getId()+"");
        save(sql.toString());
    }

    public List<BusinessAccreditAdmin> findBusinessAccreditAdmin(Long id){
        BusinessAccreditAdmin businessAccreditAdmin;
        List<BusinessAccreditAdmin> businessAccreditAdminlist = new  ArrayList<BusinessAccreditAdmin>();
        String sql = "select id,admin,businessAccredit,adminName,loginName from csms_BusinessAccredit_Admin "
                +"where businessAccredit ="+id;
        List<Map<String,Object>> list = queryList(sql);
        for (Map<String, Object> map : list) {
            businessAccreditAdmin = new BusinessAccreditAdmin();
            this.convert2Bean(map, businessAccreditAdmin);
            businessAccreditAdminlist.add(businessAccreditAdmin);
        }

        return businessAccreditAdminlist;
    }

    public void saveBusinessAccreditAdminHis(BusinessAccreditAdminHis businessAccreditAdminHis,Long id) {
        StringBuffer sql = new StringBuffer(
                "insert into CSMS_BusinessAccredit_AdminHis(id,admin,businessAccredit,adminName,loginName,accreditAdminID,genTime,genReason)  "
                        + "SELECT "+businessAccreditAdminHis.getId()+",admin,businessAccredit,adminName,loginName,"+id+",sysdate,'"
                        + businessAccreditAdminHis.getGenReason()+"'"
                        + " FROM CSMS_BusinessAccredit_Admin WHERE id="+id+"");
        save(sql.toString());
    }

    //删除业务授权用户记录
    public void deleteBusinessAccreditAdmin(Long id) {
        String sql = "delete from CSMS_businessAccredit_admin where id = " + id;
        super.delete(sql);
    }

    //查询业务授权列表
    public Pager findBusinessAccreditList(Pager pager,String name) {
        StringBuffer sql = new StringBuffer( "select id,name,url,remark,usestate,businessState,operateid,to_char(operatetime,'yyyy/MM/dd HH:mm:ss') as operatetime from csms_BusinessAccredit where 1=1");
        SqlParamer params=new SqlParamer();

        if(StringUtil.isNotBlank(name)){
            params.like("name","%"+name+"%");
        }

        sql.append(params.getParam());
        Object[] Objects= params.getList().toArray();
        sql.append(" order by id desc ");
        return this.findByPages(sql.toString(), pager,Objects);
    }

    public boolean findBusinessAccredit(String name){
        String sql="select name from CSMS_businessAccredit where name=?";
        List list=queryList(sql, name);
        return list!=null && list.size()!=0?true:false;
    }

    public boolean findDelBusinessAccreditAdmin(Long id){
        String sql="select id from CSMS_businessAccredit_Admin where id=?";
        List list=queryList(sql, id);
        return list!=null && list.size()!=0?true:false;
    }
    //判断新增业务授权用户是否重复新增
    public int findBusinessAccreditAdmin(Long businessAccredit, Long admin) {
        String sql = "select count(1) from CSMS_BUSINESSACCREDIT_Admin where businessAccredit="+businessAccredit+"and admin="+admin ;
        return super.count(sql);
    }

    /**
     * 根据业务授权记录的Id查相关授权用户
     * @param pager
     * @param businessAccredit
     * @return
     */
    public Pager findBusinessAccreditAdminList(Pager pager, Long businessAccredit) {
        StringBuffer sql = new StringBuffer("select o.*,row_number() over (order by ID desc) as num  from CSMS_BUSINESSACCREDIT_Admin o where 1=1 and o.businessAccredit="+businessAccredit);
        return this.findByPages(sql.toString(), pager,null);
    }
}
