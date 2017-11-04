package com.hgsoft.sertype.entity;

import java.io.Serializable;

/**
 * 客服类型
 * Created by wiki on 2017/6/13.
 */
public class SerType implements Serializable {

    private String serType;

    private String serName;

    public String getSerType() {
        return serType;
    }

    public void setSerType(String serType) {
        this.serType = serType;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SerType{");
        sb.append("serType='").append(serType).append('\'');
        sb.append(", serName='").append(serName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
