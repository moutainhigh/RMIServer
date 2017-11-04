package com.hgsoft.httpInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 2016/12/12.
 */
public class BusinessAccredit implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -368132127583353794L;
	private Long id;
    private String name;//业务名称
    private String url;//url
    private String remark;//备注
    private String useState;//使用状态
    private Long operateID;//操作人ID
    private Date operateTime;//设置时间
    private String businessState;
    
    public BusinessAccredit() {
		// TODO Auto-generated constructor stub
	}

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

	public String getBusinessState() {
		return businessState;
	}

	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}

	public BusinessAccredit(Long id, String name, String url, String remark, String useState, Long operateID,
			Date operateTime, String businessState) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.remark = remark;
		this.useState = useState;
		this.operateID = operateID;
		this.operateTime = operateTime;
		this.businessState = businessState;
	}

	@Override
	public String toString() {
		return "BusinessAccredit [id=" + id + ", name=" + name + ", url=" + url + ", remark=" + remark + ", useState="
				+ useState + ", operateID=" + operateID + ", operateTime=" + operateTime + ", businessState="
				+ businessState + "]";
	}
}
