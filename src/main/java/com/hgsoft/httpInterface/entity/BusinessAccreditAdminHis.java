package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 2016/12/12.
 */
public class BusinessAccreditAdminHis implements Serializable {

    private static final long serialVersionUID = 6441725643750459318L;

    private Long id;
    private Long admin;//系统用户
    private Long businessAccredit;//业务授权
    private String adminName;//用户名称
    private String loginName;//登陆名称
    private Long accreditAdminID;//业务授权用户ID
    private Date genTime;//产生时间
    private String genReason;//产生原因

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmin() {
        return admin;
    }

    public void setAdmin(Long admin) {
        this.admin = admin;
    }

    public Long getBusinessAccredit() {
        return businessAccredit;
    }

    public void setBusinessAccredit(Long businessAccredit) {
        this.businessAccredit = businessAccredit;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getAccreditAdminID() {
        return accreditAdminID;
    }

    public void setAccreditAdminID(Long accreditAdminID) {
        this.accreditAdminID = accreditAdminID;
    }

    public Date getGenTime() {
        return genTime;
    }

    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    public String getGenReason() {
        return genReason;
    }

    public void setGenReason(String genReason) {
        this.genReason = genReason;
    }
}
