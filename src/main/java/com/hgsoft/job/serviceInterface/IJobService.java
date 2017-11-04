package com.hgsoft.job.serviceInterface;

import com.hgsoft.job.entity.JobDetailDto;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.JobDetailImpl;

import java.util.List;

public interface IJobService {

    void saveNewJob(JobDetailImpl jobDetail, Trigger trigger,
                    String taskDescription);

    List<JobExecutionContext> getCurrentlyExecutingJobs();

    JobDetailDto getJobDetail(String jobname, String groupname);

    Trigger getTrigger(String name, String group);

    TriggerState getTriggerState(String jobname, String groupname);

    void updateJob(JobDetailImpl jobDetail, Trigger trigger, String triggerDesc);

    void triggerJob(String jobname, String groupname, JobDataMap data);

    boolean deleteJob(String jobname, String groupname);

    void pauseAllJob(String jobname, String groupname);

    void resumeAllJob(String jobname, String groupname);

    public List<JobDetailDto> getJobList(String jobName, String groupName,
                                         String triggerType);

    public JobDetailDto startJob(String jobname, String groupname,
                                 String[] dataKey, String[] dataValue);

    public JobDetailDto saveJob(String[] dataKey, String[] dataValue,
                                String name, String group, String className, String description,
                                String volatility, String durability, String cronExpression,
                                String taskTypeSel, String taskRate, String startTime,
                                String endTimeFlag, String endTime, String weekTimes,
                                String[] weekTimesVals, String monthFrontSel, String monthBackSel,
                                String taskRateDaySel, String oneTime, String betweenTimes,
                                String betweenTimeSel, String taskDate, String taskTime);

    public JobDetailDto modifyJob(String[] dataKey, String[] dataValue,
                                  String jobname, String groupname, String className,
                                  String description, String durability, String cronExpression,
                                  String taskTypeSel, String taskRate, String startTime,
                                  String endTimeFlag, String endTime, String weekTimes,
                                  String[] weekTimesVals, String monthFrontSel, String monthBackSel,
                                  String taskRateDaySel, String oneTime, String betweenTimes,
                                  String betweenTimeSel, String taskDate, String taskTime);
}
