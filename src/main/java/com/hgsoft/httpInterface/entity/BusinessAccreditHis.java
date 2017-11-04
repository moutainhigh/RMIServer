package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 2016/12/12.
 */
public class BusinessAccreditHis implements Serializable {

    private static final long serialVersionUID = -7339718100673763544L;

    private Long id;
    private String name;//业务名称
    private String url;//url
    private String remark;//备注
    private String useState;//使用状态
    private Long operateID;//操作人ID
    private Date operateTime;//设置时间
    private Long businessAccredit;//业务授权ID
    private Date genTime;//产生时间
    private String genReason;//产生原因

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public Long getOperateID() {
        return operateID;
    }

    public void setOperateID(Long operateID) {
        this.operateID = operateID;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Long getBusinessAccredit() {
        return businessAccredit;
    }

    public void setBusinessAccredit(Long businessAccredit) {
        this.businessAccredit = businessAccredit;
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
