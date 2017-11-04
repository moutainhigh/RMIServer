package com.hgsoft.report.entity;

import java.io.Serializable;

public class CustomPointType implements Serializable {

    private static final long serialVersionUID = 8589413200017966148L;

    private String name;

    private String id;//对应网点表customPointType

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
