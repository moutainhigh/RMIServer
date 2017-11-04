package com.hgsoft.job.entity;

import org.quartz.JobDataMap;
import org.quartz.impl.JobDetailImpl;

import java.io.Serializable;

public class JobDetailDto implements Serializable {


    private static final long serialVersionUID = 4712486105679767082L;

    private String name;
    private String group;
    private String triggerState;
    private String triggerType;
    private String nextFireTime;
    private String prevFireTime;
    private String triggerDesc;
    private String fullName;
    private String description;
    private String className;
    private boolean durable;
    private JobDataMap jobDataMap;

    public JobDetailDto() {

    }

    public JobDetailDto(JobDetailImpl jobDetail) {
        name = jobDetail.getName();
        group = jobDetail.getGroup();
        jobDataMap = jobDetail.getJobDataMap();
        triggerState = jobDataMap.getString("triggerState");
        triggerType = jobDataMap.getString("triggerType");
        nextFireTime = jobDataMap.getString("nextFireTime");
        prevFireTime = jobDataMap.getString("prevFireTime");
        triggerDesc = jobDataMap.getString("triggerDesc");
        fullName = jobDetail.getFullName();
        description = jobDetail.getDescription();
        className = jobDetail.getJobClass().getName();
        durable = jobDetail.isDurable();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public String getTriggerDesc() {
        return triggerDesc;
    }

    public void setTriggerDesc(String triggerDesc) {
        this.triggerDesc = triggerDesc;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean getDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

}
