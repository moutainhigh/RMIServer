package com.hgsoft.utils;

import com.hgsoft.job.entity.JobDetailDto;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.JobDetailImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class DWRJobForm {
	/**
	 * 0- 停止状态 
	 */
	public final static String STATUS_STOPPED = "0";
	/**
	 * 1- 运行状态 
	 */
	public final static String STATUS_RUNNING = "1";

	/**
	 * 1-触发器暂停
	 */
	public final static String TRIGGER_STOPPED = "1";
	/**
	 * 0-触发器运行
	 */
	public final static String TRIGGER_RUNNING = "0";
	
	private String id;

	private String name;

	private String group;

	private String description;

	private String className;

	private boolean volatility = false;

	private boolean durability = true;//

	private boolean shouldRecover = true;//是否重新执行

	private String cronExpression = null;

	private String status;
	
	private TriggerState triggerStatus;

	private String groupNameEqualFilter = null;

	private String statusEqualFilter = null;
	
	private String triggerDesc;

	private DWRJobDataForm[] data = null;
	/**
	 * 任务所关联的触发器 0-SimpleTrigger 1-CronTrigger
	 */
	private String triggerType ;

	
	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public DWRJobForm() {
	}

	public String toString() {
		return "id=" + id + ",name=" + name + ",group=" + group
				+ ",description=" + description + ",className=" + className
				+ ",volatility=" + volatility + ",durability=" + durability
				+ ",shouldRecover=" + shouldRecover + ",status=" + status
				+ "cronExpression=" + cronExpression+",triggerType="+triggerType;
	}

	public String getFilter() {
		return null;
	}

	public void copyFrom(JobDetailDto jobDetail) {
		
		//JobDetailImpl jobDetailImpl = (JobDetailImpl)jobDetail;
		this.id = jobDetail.getFullName();
		this.name = jobDetail.getName();
		this.group = jobDetail.getGroup();
		this.description = jobDetail.getDescription();
		this.className = jobDetail.getClassName();

		//this.volatility = jobDetailImpl.isVolatile();
		this.durability = jobDetail.getDurable();
		this.triggerType = jobDetail.getTriggerType();
		
		List<DWRJobDataForm> list = new ArrayList<DWRJobDataForm>();
		
		//获取任务参数
		JobDataMap dataMap = jobDetail.getJobDataMap();
		
		
		Iterator<String> iter = dataMap.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			Object value = dataMap.get(key);
			
			DWRJobDataForm dataForm = new DWRJobDataForm();
			dataForm.setKey(key.toString());
			dataForm.setValue(value.toString());
			list.add(dataForm);
		}
		this.data = (DWRJobDataForm[]) list.toArray(new DWRJobDataForm[0]);
	}

	public final void copyFrom(CronTrigger trigger) {
		if (trigger != null) {
			this.cronExpression = trigger.getCronExpression();
		}
	}

	public final void copyTo(JobDetail jobDetail) throws Exception {
		JobDetailImpl jobDetailImpl = (JobDetailImpl)jobDetail;
		Class<Job> jobClass = (Class<Job>) Class.forName(className);
		
		jobDetailImpl.setDescription(description);
		jobDetailImpl.setJobClass(jobClass);
		
		//jobDetailImpl.setVolatility(volatility);
		jobDetailImpl.setDurability(durability);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVolatility() {
		return volatility;
	}

	public void setVolatility(boolean volatility) {
		this.volatility = volatility;
	}

	public boolean isDurability() {
		return durability;
	}

	public void setDurability(boolean durability) {
		this.durability = durability;
	}

	public boolean isShouldRecover() {
		return shouldRecover;
	}

	public void setShouldRecover(boolean shouldRecover) {
		this.shouldRecover = shouldRecover;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupNameEqualFilter() {
		return groupNameEqualFilter;
	}

	public void setGroupNameEqualFilter(String groupNameEqualFilter) {
		this.groupNameEqualFilter = groupNameEqualFilter;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStatusEqualFilter() {
		return statusEqualFilter;
	}

	public void setStatusEqualFilter(String statusEqualFilter) {
		this.statusEqualFilter = statusEqualFilter;
	}

	public DWRJobDataForm[] getData() {
		return data;
	}

	public void setData(DWRJobDataForm[] data) {
		this.data = data;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public TriggerState getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(TriggerState triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	public String getTriggerDesc() {
		return triggerDesc;
	}

	public void setTriggerDesc(String triggerDesc) {
		this.triggerDesc = triggerDesc;
	}
	
	
}
