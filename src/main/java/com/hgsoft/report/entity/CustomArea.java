package com.hgsoft.report.entity;

import java.io.Serializable;

public class CustomArea implements Serializable {

    private static final long serialVersionUID = -1894451466208756702L;

    private String name;

    private String code;//对应网点表area

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
