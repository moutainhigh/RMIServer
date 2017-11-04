package com.hgsoft.httpInterface.entity;

import java.io.Serializable;

/**
 * Created by apple on 2016/12/12.
 */
public class BusinessAccreditAdmin implements Serializable {

    private static final long serialVersionUID = -2460972184887489020L;

    private Long id;
    private Long admin;//系统用户
    private Long businessAccredit;//业务授权
    private String adminName;//用户名称
    private String loginName;//登陆名称

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
}
