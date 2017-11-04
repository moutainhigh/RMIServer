package com.hgsoft.job.service;


import com.hgsoft.exception.ApplicationException;
import com.hgsoft.job.entity.JobDetailDto;
import com.hgsoft.job.serviceInterface.IJobService;
import com.hgsoft.utils.DWRJobForm;
import com.hgsoft.utils.JobTaskUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("jobService")
public class JobServiceImpl implements IJobService {

    private final static String TIME_SPLIT = ":";
    private final static String QUESTION_MARK = "?";
    private final static String WILDCARD = "*";
    private static Logger log = Logger.getLogger(JobServiceImpl.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

//    @Autowired
    @Resource(name="quartzScheduler")
    private Scheduler schedulerFactory;

    @Override
    public void saveNewJob(JobDetailImpl jobDetail, Trigger cTrigger, String triggerDesc) {
        // log.info("saveNewJob()");
        // jobDetail.setTriggerType(jobDetail.getTriggerType());
        // jobDetail.setTriggerDesc(triggerDesc);
        jobDetail.getJobDataMap().put("triggerDesc", triggerDesc);

        // 新增任务
        this.addJob(jobDetail, false);
        this.scheduleJob(cTrigger);
    }

    /**
     * 增加新任务
     *
     * @param jobDetail
     * @param replace
     * @throws Exception
     */
    private void addJob(JobDetail jobDetail, boolean replace) {
        try {
            log.info("addJob()");
            sanityCheck();
            // replace是否替换原来存在的数据，新增false,修改ture
            schedulerFactory.addJob(jobDetail, replace);
        } catch (Exception e) {
            throw new ApplicationException("addJob", e);
        }
    }

    private void sanityCheck() {
        if (schedulerFactory == null) {
            throw new ApplicationException("The manager object is not initialized correctly.");
        }
    }

    private Date scheduleJob(Trigger trigger) {
        try {
            sanityCheck();
            return schedulerFactory.scheduleJob(trigger);
        } catch (Exception e) {
            throw new ApplicationException("scheduleJob", e);
        }

    }

	/*
     * private void addCalender(String calName,String startTime,String endTime)
	 * throws Exception{ sanityCheck(); DailyCalendar calendar = new
	 * DailyCalendar(startTime, endTime); calendar.setInvertTimeRange(true);
	 * schedulerFactory.addCalendar(calName, calendar, true, true); }
	 */

    private boolean unscheduleJob(String triggerName, String groupName) {
        try {
            sanityCheck();
            return schedulerFactory.unscheduleJob(new TriggerKey(triggerName, groupName));
        } catch (Exception e) {
            throw new ApplicationException("unscheduleJob", e);
        }
    }

    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() {
       try {
           // log.info("getCurrentlyExecutingJobs()");
           sanityCheck();
           return schedulerFactory.getCurrentlyExecutingJobs();
       } catch (Exception e) {
           throw new ApplicationException("getCurrentlyExecutingJobs", e);
       }
    }

    @Override
    public JobDetailDto getJobDetail(String jobName, String jobGroup) {
        try {
            sanityCheck();
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetailDto returnVal = new JobDetailDto((JobDetailImpl) schedulerFactory.getJobDetail(jobKey));
            return returnVal;
        } catch (Exception e) {
            throw new ApplicationException("getJobDetail", e);
        }
    }

    @Override
    public Trigger getTrigger(String triggerName, String triggerGroup) {
        try {
            sanityCheck();
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
            return schedulerFactory.getTrigger(triggerKey);
        } catch (Exception e) {
            throw new ApplicationException("getTrigger", e);
        }
    }

    @Override
    public TriggerState getTriggerState(String triggerName, String triggerGroup) {
        try {
            sanityCheck();
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
            TriggerState triggerState = schedulerFactory.getTriggerState(triggerKey);
            return triggerState;
        } catch (Exception e) {
            throw new ApplicationException("getTriggerState", e);
        }
    }

    @Override
    public void updateJob(JobDetailImpl jobDetail, Trigger tr,
                          String triggerDesc) {
        // log.info("updateJob()");
        if (StringUtils.isNotEmpty(triggerDesc)
                && StringUtils.isNotBlank(triggerDesc)) {
            // String sql = "update " + JOB_DETAILS + " set "
            // + Constants.COL_MY_TRIGGER_DESC + "='" + triggerDesc
            // + "' where " + Constants.COL_JOB_NAME + "='"
            // + jobDetail.getName() + "' and " + Constants.COL_JOB_GROUP
            // + "='" + jobDetail.getGroup() + "'";
            // this.jobDao.updOrDelWithSql(sql);
            jobDetail.getJobDataMap().put("triggerDesc", triggerDesc);
            // jobDetail.setTriggerDesc(triggerDesc);
        }

        // 替换原有任务
        this.addJob(jobDetail, true);

        Trigger trigger = this.getTrigger(jobDetail.getName(),
                jobDetail.getGroup());

        if (trigger != null) {
            // 删除原有触发器
            this.unscheduleJob(jobDetail.getName(), jobDetail.getGroup());
        }

		/*
         * String startTime = null; String endTime = null; String calName =
		 * null; switch(jobDetail.getName()){ case "本省异地预清分": startTime =
		 * "02:00:00"; endTime = "04:00:00"; calName = "预清分日历"; break; case
		 * "本省异地正式清分": startTime = "22:30:00"; endTime = "23:59:00"; calName =
		 * "正式清分日历"; break; case "扫描银行争议流水和生成记账包": startTime = "21:00:00";
		 * endTime = "22:30:00"; calName = "组装记账日历"; break; }
		 *
		 * if (null != startTime && null != endTime) { this.addCalender(calName,
		 * startTime, endTime); if (cTrigger instanceof CronTriggerImpl) {
		 * cTrigger = (CronTriggerImpl) cTrigger; ((CronTriggerImpl)
		 * cTrigger).setCalendarName(calName); } }
		 */

        // 开始任务
        scheduleJob(tr);
    }

    @Override
    public void triggerJob(String jobName, String groupName, JobDataMap params) {
        try {
            sanityCheck();
            schedulerFactory.triggerJob(new JobKey(jobName, groupName), params);
        } catch (Exception e) {
            throw new ApplicationException("triggerJob", e);
        }
    }

    @Override
    public boolean deleteJob(String jobName, String groupName) {
        try {
            // log.info("deleteJob()");
            sanityCheck();
            JobKey jobKey = new JobKey(jobName, groupName);
            return schedulerFactory.deleteJob(jobKey);
        } catch (Exception e) {
            throw new ApplicationException("deleteJob", e);
        }
    }

    @Override
    public void pauseAllJob(String jobname, String groupname) {
        // log.info("pauseAllJob()");
        // 暂停任务
        pauseJob(jobname, groupname);
        // 暂停触发器
        pauseTrigger(jobname, groupname);
    }

    @Override
    public void resumeAllJob(String jobname, String groupname) {
        // log.info("resumeAllJob()");
        // 恢复任务
        resumeJob(jobname, groupname);
        // 恢复触发器
        resumeTrigger(jobname, groupname);
    }

    private void pauseJob(String jobName, String groupName) {
       try {
           sanityCheck();
           JobKey jobKey = new JobKey(jobName, groupName);
           schedulerFactory.pauseJob(jobKey);
       } catch (Exception e) {
           throw new ApplicationException("pauseJob", e);
       }
    }

    private void pauseTrigger(String triggerName, String groupName) {
        try {
            sanityCheck();
            TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
            schedulerFactory.pauseTrigger(triggerKey);
        } catch (Exception e) {
            throw new ApplicationException("pauseTrigger", e);
        }
    }

    private void resumeJob(String jobName, String groupName) {
        try {
            sanityCheck();
            schedulerFactory.resumeJob(new JobKey(jobName, groupName));
        } catch (Exception e) {
            throw new ApplicationException("resumeJob", e);
        }
    }

    private void resumeTrigger(String triggerName, String groupName) {
        try {
            sanityCheck();
            schedulerFactory.resumeTrigger(new TriggerKey(triggerName, groupName));
        } catch (Exception e) {
            throw new ApplicationException("resumeTrigger", e);
        }
    }

    @Override
    public List<JobDetailDto> getJobList(String jobName, String groupName,
                                         String triggerType) {
        try {
            // log.info("getJobList()");
            List<JobDetailDto> jobDetailImpls = new ArrayList<JobDetailDto>();
            List<String> jobGroupNames = schedulerFactory.getJobGroupNames();
            for (String jobGroupName : jobGroupNames) {
                GroupMatcher<JobKey> matcher = GroupMatcher
                        .jobGroupEquals(jobGroupName);
                Set<JobKey> jobKeys = schedulerFactory.getJobKeys(matcher);

                for (JobKey jobKey : jobKeys) {
                    JobDetail jobDetail = schedulerFactory.getJobDetail(jobKey);
                    JobDetailImpl jobDetailImpl = (JobDetailImpl) jobDetail;

                    if (StringUtils.isNotEmpty(jobName)
                            && StringUtils.isNotBlank(jobName)) {
                        if (!jobDetailImpl.getName().contains(jobName)) {
                            continue;
                        }
                    }

                    if (StringUtils.isNotEmpty(groupName)
                            && StringUtils.isNotBlank(groupName)) {
                        if (!jobDetailImpl.getGroup().contains(groupName)) {
                            continue;
                        }
                    }

                    JobDataMap jobDataMap = jobDetailImpl.getJobDataMap();
                    if (StringUtils.isNotEmpty(triggerType)
                            && StringUtils.isNotBlank(triggerType)
                            && !StringUtils.equals(triggerType, "-1")) {
                        if (!triggerType.equals(jobDataMap.getInt("triggerType") + "")) {
                            continue;
                        }
                    }

				/*
                 * List<?extends Trigger> triggers =
				 * schedulerFactory.getTriggersOfJob(jobKey); for(Trigger
				 * trigger : triggers){
				 * logger.info(trigger.getPreviousFireTime()); }
				 */
                    TriggerKey triggerKey = new TriggerKey(jobDetailImpl.getName(),
                            jobDetailImpl.getGroup());
                    TriggerState triggerState = schedulerFactory
                            .getTriggerState(triggerKey);
                    Trigger trigger = schedulerFactory.getTrigger(triggerKey);
                    if (trigger != null) {
                        if (trigger.getNextFireTime() == null) {
                            jobDataMap.put("nextFireTime", "");
                        } else {
                        /*
                         * jobDetailImpl.setNextFireTime(sdf.format(trigger
						 * .getNextFireTime()));
						 */
                            jobDataMap.put("nextFireTime", sdf.format(trigger.getNextFireTime()));
                        }

                        if (trigger.getPreviousFireTime() == null) {
                            // jobDetailImpl.setPrevFireTime("");
                            jobDataMap.put("prevFireTime", "");
                        } else {
						/*
						 * jobDetailImpl.setPrevFireTime(sdf.format(trigger
						 * .getPreviousFireTime()));
						 */
                            jobDataMap.put("prevFireTime", sdf.format(trigger.getPreviousFireTime()));
                        }
                    }

                    if (triggerState != null) {
                        // jobDetailImpl.setTriggerState(triggerState.name());
                        jobDataMap.put("triggerState", triggerState.name());
                    }
                    JobDetailDto dto = new JobDetailDto(jobDetailImpl);
                    jobDetailImpls.add(dto);
                }
            }
            return jobDetailImpls;
        } catch (Exception e) {
            throw new ApplicationException("getJobList", e);
        }

    }

    @Override
    public JobDetailDto startJob(String jobname, String groupname,
                                 String[] dataKey, String[] dataValue) {

        log.info("startJob()");
        JobDataMap data = new JobDataMap();

        if (dataKey != null && dataKey.length > 0) {
            for (int i = 0; i < dataKey.length; i++) {
                if (!"".equals(dataValue[i].trim())) {
                    data.put(dataKey[i], dataValue[i]);
                }
            }
        }

        // 开始任务 只触发一次
        /**
         * Trigger trig = new org.quartz.SimpleTrigger(newTriggerId(),
         * Scheduler.DEFAULT_MANUAL_TRIGGERS, jobName, groupName, new Date(),
         * null, 0, 0);
         */
        // this.jobTaskService.triggerJob(jobname, groupname, data);
        // this.jobTaskService.resumeAll();
        this.triggerJob(jobname, groupname, data);

        // 根据任务名和组名查询查询任务明细
        JobDetailDto jobDetail = this.getJobDetail(jobname, groupname);

        DWRJobForm form = new DWRJobForm();
        form.copyFrom(jobDetail);

        List<JobExecutionContext> execContexts = (List<JobExecutionContext>) this
                .getCurrentlyExecutingJobs();
        for (int i = 0; i < execContexts.size(); i++) {
            JobExecutionContext execContext = execContexts.get(i);

            if (execContext.getJobDetail().equals(jobDetail)) {
                form.setStatus(DWRJobForm.STATUS_RUNNING);
            } else {
                form.setStatus(DWRJobForm.STATUS_STOPPED);
            }
        }

        return jobDetail;
    }

    @Override
    public JobDetailDto saveJob(String[] dataKey, String[] dataValue,
                                String name, String group, String className, String description,
                                String volatility, String durability, String cronExpression,
                                String taskTypeSel, String taskRate, String startTime,
                                String endTimeFlag, String endTime, String weekTimes,
                                String[] weekTimesVals, String monthFrontSel, String monthBackSel,
                                String taskRateDaySel, String oneTime, String betweenTimes,
                                String betweenTimeSel, String taskDate, String taskTime) {
        try {
            Class<Job> jobClass = null;
            if (StringUtils.isNotEmpty(className)
                    && StringUtils.isNotBlank(className)) {
                try {
                    jobClass = (Class<Job>) Class.forName(className.trim());
                } catch (ClassNotFoundException e) {
                    throw new ApplicationException("待执行的任务类不存在！", e);
                }
            }
            log.info("saveJob()");
            boolean shouldRecover = false;
            // 初始化任务详细信息
            // JobDetailImpl jobDetail = new JobDetailImpl();
            JobDetailImpl jobDetail = (JobDetailImpl) JobBuilder.newJob(jobClass)
                    .withIdentity(name, group).build();
            jobDetail.setName(name);// 设置任务名
            jobDetail.setGroup(group);// 设置组名

            DWRJobForm job = new DWRJobForm();

            if (StringUtils.isNotEmpty(description)
                    && StringUtils.isNotBlank(description)) {
                job.setDescription(description.trim());
            }
            job.setClassName(className.trim());// 设置类名
            job.setVolatility(Boolean.parseBoolean(volatility));
            job.setDurability(Boolean.parseBoolean(durability));
            job.setCronExpression(cronExpression);
            job.copyTo(jobDetail);

            JobDataMap jobDataMap = new JobDataMap();
            if (dataKey != null && dataKey.length > 0) {
                for (int i = 0; i < dataKey.length; i++) {
                    if (!"" .equals(dataValue[i].trim()))
                        jobDataMap.put(dataKey[i], dataValue[i]);// 放置任务参数
                }
                // if (jobDataMap.size() > 0)
                jobDetail.setJobDataMap(jobDataMap);
            }

            // jobDetail.setTriggerType(Integer.valueOf(taskTypeSel));
            jobDataMap.put("triggerType", taskTypeSel);
            // 表达式没有输入，则通过界面配置
            if (StringUtils.isEmpty(cronExpression)
                    || StringUtils.isBlank(cronExpression)) {
                // throw new Exception("表达式不能为空！");
                String taskDescription = "从";// 描述
                // 重复执行
                if (StringUtils.equals(taskTypeSel, "1")) {

                    // jobDetail.setTriggerType(1);
                    taskDescription += startTime + "开始,";

                    Date _startTime = null, _endTime = null;

                    // 持续时间
                    checkValid(endTimeFlag, "结束日期标识");
                    checkValid(startTime, "开始时间");
                    _startTime = sdf.parse(startTime);

                    if (StringUtils.equals(endTimeFlag, "yes")) {

                        checkValid(endTime, "结束时间 ");
                        _endTime = sdf.parse(endTime);

                        taskDescription += endTime + "结束,";
                    } else if (StringUtils.equals(endTimeFlag, "no")) {

                    } else {

                    }

                    String cron = "{1} {2} {3} {4} {5} {6}";// 少了 "年" 的配置

                    // 每天
                    if (StringUtils.equals(taskRate, TaskRate.DAY)) {
                        cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                        cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月 每月触发
                        cron = cron.replace(Placeholder.p6, WILDCARD);// 替换周

                        taskDescription += "在每天";
                    }
                    // 每周
                    else if (StringUtils.equals(taskRate, TaskRate.WEEK)) {

                        String str = "";
                        if (weekTimes == null || "" .equals(weekTimes)) {
                            weekTimes = "0";
                        }

                        weekTimes = weekTimes.trim();
                        int weekTimesInt = 0;
                        try {
                            if (StringUtils.equals(weekTimes, "1")) {
                                weekTimesInt = 0;
                            } else {
                                weekTimesInt = Integer.parseInt(weekTimes);
                            }

                        } catch (Exception e) {
                            weekTimesInt = 0;
                        }

                        if (weekTimesVals != null && weekTimesVals.length > 0) {
                            taskDescription += "在每"
                                    + (weekTimesInt == 0 ? "" : weekTimesInt)
                                    + "周,";
                            final int weekLen = weekTimesVals.length;

                            for (int i = 0; i < weekLen; i++) {
                                // 需要每隔*周执行
                                if (weekTimesInt > 0) {
                                    str += weekTimesVals[i] + "/" + weekTimesInt
                                            + ",";
                                } else {
                                    str += weekTimesVals[i] + ",";
                                }
                                // 拼接描述语句
                                if (i == (weekLen - 1)) {
                                    taskDescription += JobTaskUtils.Week
                                            .obtainWeekDetial(weekTimesVals[i]);
                                } else {
                                    taskDescription += JobTaskUtils.Week
                                            .obtainWeekDetial(weekTimesVals[i])
                                            + "、";
                                }

                            }

                            if (str.endsWith(",")) {
                                str = str.substring(0, str.length() - 1);
                            }
                            cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                            cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月
                            // 每月触发
                            cron = cron.replace(Placeholder.p6, str);// 替换周

                        } else {

                            // 需要每隔*周执行
                            if (weekTimesInt > 0) {
                                taskDescription += "在每"
                                        + (weekTimesInt == 0 ? "" : weekTimesInt)
                                        + "周";
                                str += "1/" + weekTimesInt + ",2/" + weekTimesInt
                                        + ",3/" + weekTimesInt + ",4/"
                                        + weekTimesInt + ",5/" + weekTimesInt
                                        + ",6/" + weekTimesInt + ",7/"
                                        + weekTimesInt;
                                cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                            } else {
                                taskDescription += "在每周";
                                str = QUESTION_MARK;
                                cron = cron.replace(Placeholder.p4, WILDCARD);// 替换日
                            }

                            cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月
                            // 每月触发
                            cron = cron.replace(Placeholder.p6, str);// 替换周
                        }

                    }
                    // 每月
                    else if (StringUtils.equals(taskRate, TaskRate.MONTH)) {
                        taskDescription += "在每月,";
                        /**
                         * -1 请选择 ;1 第一个 ;2 第二个 ;3 第三个 ;4 第四个 ;L 最后一个
                         */

                        /**
                         * -1 请选择;1 星期日;2 星期一 ;3 星期二;4 星期三 ;5 星期四 ;6 星期五 ;7 星期六 ;
                         * workday 工作日 ;restday 休息日
                         */

                        if (StringUtils.isEmpty(monthFrontSel)
                                || StringUtils.equals(monthFrontSel, "-1")) {
                            throw new Exception("需要选择每月触发频率！");
                        }

                        if (monthFrontSel.equals("L")) {
                            taskDescription += "最后一个";
                        } else {
                            taskDescription += "第" + monthFrontSel + "个";
                        }

                        if (StringUtils.isEmpty(monthBackSel)
                                || StringUtils.equals(monthBackSel, "-1")) {
                            throw new Exception("需要选择每月触发频率！");
                        }
                        taskDescription += JobTaskUtils.Month
                                .obtainMonthBackSel(monthBackSel);

                        String monthStr = "";
                        // 最后一个
                        if (StringUtils.equals(monthFrontSel, "L")) {
                            monthStr = monthBackSel + monthFrontSel;
                        } else {
                            monthStr = monthBackSel + "#" + monthFrontSel;
                        }

                        cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                        cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月 每月触发
                        cron = cron.replace(Placeholder.p6, monthStr);// 替换周
                    } else {

                    }
                    /**** 共有属性 ***/

                    // 执行一次
                    if (StringUtils.equals(taskRateDaySel, "one")) {

                        checkValid(oneTime, "执行一次，时间");

                        String[] times = oneTime.split(TIME_SPLIT);

                        String hour = times[0];// 时
                        if (hour.equals("00")) {
                            hour = "0";
                        }

                        String min = times[1];// 分
                        if (min.equals("00")) {
                            min = "0";
                        }

                        String sec = times[2];// 秒
                        if (sec.equals("00")) {
                            sec = "0";
                        }

                        cron = cron.replace(Placeholder.p1, sec);
                        cron = cron.replace(Placeholder.p2, min);
                        cron = cron.replace(Placeholder.p3, hour);

                        taskDescription += "," + hour + "时" + min + "分" + sec
                                + "秒执行";

                    }
                    // 间隔执行
                    else if (StringUtils.equals(taskRateDaySel, "more")) {

                        checkValid(betweenTimes, "执行间隔");
                        // Date now = new Date();
                        // String hour=now.getHours()+"", min =
                        // now.getMinutes()+"",sec = "0";
                        String hour = "*", min = "*", sec = "0";
                        if (StringUtils.equals(betweenTimeSel, "hour")) {
                            // hour=now.getHours()+"";
                            // hour = hour+"/"+betweenTimes;//时

                            hour = "0/" + betweenTimes;// 时
                            min = "0"; // modify 2014/12/28 gs

                            taskDescription += ",每隔" + betweenTimes + "小时重复执行";
                        } else if (StringUtils.equals(betweenTimeSel, "minute")) {
                            // min = now.getMinutes()+"";
                            // min = min+"/"+betweenTimes;//分

                            min = "0/" + betweenTimes;// 分

                            taskDescription += ",每隔" + betweenTimes + "分钟重复执行";
                        } else if (StringUtils.equals(betweenTimeSel, "second")) {
                            sec = "0/" + betweenTimes;// 秒

                            taskDescription += ",每隔" + betweenTimes + "秒钟重复执行";
                        } else {
                        }

                        cron = cron.replace(Placeholder.p1, sec);
                        cron = cron.replace(Placeholder.p2, min);
                        cron = cron.replace(Placeholder.p3, hour);

                    } else {
                    }
                    /**** 共有属性 ***/

                    CronExpression expression = null;
                    try {
                        expression = new CronExpression(cron);
                    } catch (ParseException e) {
                        throw new Exception("表达式'" + cron + "'生成有误！", e);
                    }
                    // CronTriggerImpl cTrigger = new CronTriggerImpl(name,group,
                    // name, group,_startTime ,_endTime ,cron);
                    CronTriggerImpl cTrigger = (CronTriggerImpl) TriggerBuilder
                            .newTrigger().withIdentity(name, group)
                            .withSchedule(
                                    CronScheduleBuilder.cronSchedule(expression))
                            .build();
                    cTrigger.setJobName(name);
                    cTrigger.setJobGroup(group);
                    cTrigger.setName(name);
                    cTrigger.setGroup(group);
                    cTrigger.setStartTime(_startTime);
                    cTrigger.setEndTime(_endTime);
                    cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
                    //cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
                    // shouldRecover属性为true，则当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务。
                    jobDetail.setRequestsRecovery(shouldRecover);
                    this.saveNewJob(jobDetail, cTrigger, taskDescription);
                    // logger.info("=====cron===="+cron);
                    job.copyFrom(cTrigger);
                }

                // 执行一次
                else if (StringUtils.equals(taskTypeSel, "2")) {

                    Date runTime;
                    String source = "";
                    if (StringUtils.isEmpty(taskDate)
                            || StringUtils.isBlank(taskDate)
                            || StringUtils.isEmpty(taskTime)
                            || StringUtils.isBlank(taskTime)) {
                        runTime = new Date();
                        source = sdf.format(runTime);

                    } else {
                        source = taskDate + " " + taskTime;
                        runTime = sdf.parse(source);

                    }
                    // SimpleTriggerImpl trigger = new SimpleTriggerImpl(name,
                    // group, runTime);
                    SimpleTriggerImpl trigger = (SimpleTriggerImpl) TriggerBuilder
                            .newTrigger().withIdentity(name, group).startAt(runTime)
                            .build();
                    trigger.setJobName(name);
                    trigger.setJobGroup(group);
                    trigger.setName(name);
                    trigger.setGroup(group);

                    taskDescription = "在" + source + "执行一次";
                    trigger.setMisfireInstruction(
                            SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
                    // shouldRecover属性为true，则当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务。
                    jobDetail.setRequestsRecovery(shouldRecover);
                    this.saveNewJob(jobDetail, trigger, taskDescription);
                    // logger.info("日期："+taskDate+",时间："+taskTime);
                }
                // 请选择
                else if (StringUtils.equals(taskTypeSel, "-1")) {
                    /** 空方法 */
                } else {
                    /** 空方法 */
                }
                // logger.info(taskDescription);
            } else {
                cronExpression = cronExpression.trim();

                // 执行中应用发生故障，需要重新执行
                // jobDetail.setRequestsRecovery(true);
                // 即使没有Trigger关联时，也不需要删除该JobDetail
                // jobDetail.setDurability(true);
                CronExpression expression = null;
                try {
                    expression = new CronExpression(cronExpression);
                } catch (ParseException e) {
                    throw new Exception("表达式Cron输入有误！", e);
                }

                // CronTriggerImpl cTrigger = new
                // CronTriggerImpl(jobDetail.getName(),
                // jobDetail.getGroup(), jobDetail.getName(), jobDetail
                // .getGroup(), cronExpression);
                CronTriggerImpl cTrigger = (CronTriggerImpl) TriggerBuilder
                        .newTrigger()
                        .withIdentity(jobDetail.getName(), jobDetail.getGroup())
                        .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                        .build();
                cTrigger.setJobName(jobDetail.getName());
                cTrigger.setJobGroup(jobDetail.getGroup());
                cTrigger.setGroup(jobDetail.getGroup());
                cTrigger.setName(jobDetail.getName());
                cTrigger.setCronExpression(expression);
                // cTrigger.setPriority(9);
                cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
                //cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
                // shouldRecover属性为true，则当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务。
                jobDetail.setRequestsRecovery(shouldRecover);
                jobDataMap.put("triggerType", "1");
                this.saveNewJob(jobDetail, cTrigger, "");

                job.copyFrom(cTrigger);
                // job.copyFrom(jobDetail);
            }
            JobDetailDto returnVal = new JobDetailDto(jobDetail);
            return returnVal;
        } catch (Exception e) {
            throw new ApplicationException("save job", e);
        }
    }

    @Override
    public JobDetailDto modifyJob(String[] dataKey, String[] dataValue,
                                  String jobname, String groupname, String className,
                                  String description, String durability, String cronExpression,
                                  String taskTypeSel, String taskRate, String startTime,
                                  String endTimeFlag, String endTime, String weekTimes,
                                  String[] weekTimesVals, String monthFrontSel, String monthBackSel,
                                  String taskRateDaySel, String oneTime, String betweenTimes,
                                  String betweenTimeSel, String taskDate, String taskTime) {

        try {
            boolean shouldRecover = false;
            Class<Job> jobClass = null;
            if (StringUtils.isNotEmpty(className)
                    && StringUtils.isNotBlank(className)) {
                try {
                    jobClass = (Class<Job>) Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new Exception("待执行的任务类不存在！", e);
                }
            }
            log.info("modifyJob()");
            // JobDetailImpl jobDetail = new JobDetailImpl();
            JobDetailImpl jobDetail = (JobDetailImpl) JobBuilder.newJob(jobClass)
                    .withIdentity(jobname, groupname).build();
            jobDetail.setName(jobname);// 任务名
            jobDetail.setGroup(groupname);// 组名

            if (StringUtils.isNotEmpty(description)
                    && StringUtils.isNotBlank(description)) {
                jobDetail.setDescription(description);
            }

            // Class<Job> jobClass = (Class<Job>)Class.forName(className);
            // jobDetail.setJobClass(jobClass);
            // jobDetail.setVolatility(Boolean.parseBoolean(volatility));
            jobDetail.setDurability(Boolean.parseBoolean(durability));

            JobDataMap data = new JobDataMap();
            if (dataKey != null && dataKey.length > 0) {
                for (int i = 0; i < dataKey.length; i++) {
                    if (!"" .equals(dataValue[i].trim())) {
                        data.put(dataKey[i], dataValue[i]);
                    }
                }
                if (data.size() > 0) {
                    jobDetail.setJobDataMap(data);
                }
            }

            // jobDetail.setTriggerType(Integer.valueOf(taskTypeSel));
            data.put("triggerType", taskTypeSel);

            /** mod by 20130621 **/
            // 表达式没有输入，则通过界面配置
            if (StringUtils.isEmpty(cronExpression)
                    || StringUtils.isBlank(cronExpression)) {
                // throw new Exception("表达式不能为空！");
                String taskDescription = "从";// 描述
                // 重复执行
                if (StringUtils.equals(taskTypeSel, "1")) {

                    // [秒] [分] [小时] [日] [月] [周] [年]

                    taskDescription += startTime + "开始,";

                    Date _startTime = null, _endTime = null;

                    // 持续时间
                    checkValid(endTimeFlag, "结束日期标识");
                    checkValid(startTime, "开始时间");
                    _startTime = sdf.parse(startTime);

                    if (StringUtils.equals(endTimeFlag, "yes")) {

                        checkValid(endTime, "结束时间 ");
                        _endTime = sdf.parse(endTime);

                        taskDescription += endTime + "结束,";
                    } else if (StringUtils.equals(endTimeFlag, "no")) {

                    } else {

                    }

                    String cron = "{1} {2} {3} {4} {5} {6}";// 少了 "年" 的配置

                    // 每天
                    if (StringUtils.equals(taskRate, TaskRate.DAY)) {
                        cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                        cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月 每月触发
                        cron = cron.replace(Placeholder.p6, WILDCARD);// 替换周

                        taskDescription += "在每天";
                    }
                    // 每周
                    else if (StringUtils.equals(taskRate, TaskRate.WEEK)) {

                        String str = "";
                        if (weekTimes == null || "" .equals(weekTimes)) {
                            weekTimes = "0";
                        }

                        weekTimes = weekTimes.trim();
                        int weekTimesInt = 0;
                        try {
                            if (StringUtils.equals(weekTimes, "1")) {
                                weekTimesInt = 0;
                            } else {
                                weekTimesInt = Integer.parseInt(weekTimes);
                            }

                        } catch (Exception e) {
                            weekTimesInt = 0;
                        }

                        if (weekTimesVals != null && weekTimesVals.length > 0) {
                            taskDescription += "在每"
                                    + (weekTimesInt == 0 ? "" : weekTimesInt)
                                    + "周,";
                            final int weekLen = weekTimesVals.length;
                            for (int i = 0; i < weekLen; i++) {
                                // 需要每隔*周执行
                                if (weekTimesInt > 0) {
                                    str += weekTimesVals[i] + "/" + weekTimesInt
                                            + ",";
                                } else {
                                    str += weekTimesVals[i] + ",";
                                }
                                // 拼接描述语句
                                if (i == (weekLen - 1))
                                    taskDescription += JobTaskUtils.Week
                                            .obtainWeekDetial(weekTimesVals[i]);
                                else
                                    taskDescription += JobTaskUtils.Week
                                            .obtainWeekDetial(weekTimesVals[i])
                                            + "、";
                            }

                            if (str.endsWith(",")) {
                                str = str.substring(0, str.length() - 1);
                            }
                            cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                            cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月
                            // 每月触发
                            cron = cron.replace(Placeholder.p6, str);// 替换周

                        } else {
                            // 需要每隔*周执行
                            if (weekTimesInt > 0) {
                                taskDescription += "在每"
                                        + (weekTimesInt == 0 ? "" : weekTimesInt)
                                        + "周";
                                str += "1/" + weekTimesInt + ",2/" + weekTimesInt
                                        + ",3/" + weekTimesInt + ",4/"
                                        + weekTimesInt + ",5/" + weekTimesInt
                                        + ",6/" + weekTimesInt + ",7/"
                                        + weekTimesInt;
                                cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                            } else {
                                taskDescription += "在每周";
                                str = QUESTION_MARK;
                                cron = cron.replace(Placeholder.p4, WILDCARD);// 替换日
                            }

                            cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月
                            // 每月触发
                            cron = cron.replace(Placeholder.p6, str);// 替换周
                        }

                    }
                    // 每月
                    else if (StringUtils.equals(taskRate, TaskRate.MONTH)) {
                        taskDescription += "在每月,";

                        if (StringUtils.isEmpty(monthFrontSel)
                                || StringUtils.equals(monthFrontSel, "-1")) {
                            throw new Exception("需要选择每月触发频率！");
                        }

                        if (monthFrontSel.equals("L")) {
                            taskDescription += "最后一个";
                        } else {
                            taskDescription += "第" + monthFrontSel + "个";
                        }

                        if (StringUtils.isEmpty(monthBackSel)
                                || StringUtils.equals(monthBackSel, "-1")) {
                            throw new Exception("需要选择每月触发频率！");
                        }
                        taskDescription += JobTaskUtils.Month
                                .obtainMonthBackSel(monthBackSel);

                        String monthStr = "";
                        // 最后一个
                        if (StringUtils.equals(monthFrontSel, "L")) {
                            monthStr = monthBackSel + monthFrontSel;
                        } else {
                            monthStr = monthBackSel + "#" + monthFrontSel;
                        }

                        cron = cron.replace(Placeholder.p4, QUESTION_MARK);// 替换日
                        cron = cron.replace(Placeholder.p5, WILDCARD);// 替换月 每月触发
                        cron = cron.replace(Placeholder.p6, monthStr);// 替换周
                    } else {

                    }
                    /**** 共有属性 ***/

                    // 执行一次
                    if (StringUtils.equals(taskRateDaySel, "one")) {

                        checkValid(oneTime, "执行一次，时间");

                        String[] times = oneTime.split(TIME_SPLIT);

                        String hour = times[0];// 时
                        if (hour.equals("00")) {
                            hour = "0";
                        }

                        String min = times[1];// 分
                        if (min.equals("00")) {
                            min = "0";
                        }

                        String sec = times[2];// 秒
                        if (sec.equals("00")) {
                            sec = "0";
                        }

                        cron = cron.replace(Placeholder.p1, sec);
                        cron = cron.replace(Placeholder.p2, min);
                        cron = cron.replace(Placeholder.p3, hour);

                        taskDescription += "," + hour + "时" + min + "分" + sec
                                + "秒执行";

                    }
                    // 间隔执行
                    else if (StringUtils.equals(taskRateDaySel, "more")) {

                        checkValid(betweenTimes, "执行间隔");
                        // Date now = new Date();
                        // String hour=now.getHours()+"", min =
                        // now.getMinutes()+"",sec = "0";
                        String hour = "*", min = "*", sec = "0";
                        if (StringUtils.equals(betweenTimeSel, "hour")) {
                            // hour=now.getHours()+"";
                            // hour = hour+"/"+betweenTimes;//时

                            hour = "0/" + betweenTimes;// 时
                            min = "0"; // modify 2014/12/28 gs

                            taskDescription += ",每隔" + betweenTimes + "小时执行";
                        } else if (StringUtils.equals(betweenTimeSel, "minute")) {
                            // min = now.getMinutes()+"";
                            // min = min+"/"+betweenTimes;//分

                            min = "0/" + betweenTimes;// 分

                            taskDescription += ",每隔" + betweenTimes + "分钟执行";
                        } else if (StringUtils.equals(betweenTimeSel, "second")) {
                            sec = "0/" + betweenTimes;// 秒

                            taskDescription += ",每隔" + betweenTimes + "秒钟执行";
                        } else {
                        }

                        cron = cron.replace(Placeholder.p1, sec);
                        cron = cron.replace(Placeholder.p2, min);
                        cron = cron.replace(Placeholder.p3, hour);

                    } else {
                    }
                    /**** 共有属性 ***/

                    // logger.info(taskDescription);
                    CronExpression expression = null;
                    try {
                        expression = new CronExpression(cron);
                    } catch (ParseException e) {
                        throw new Exception("表达式'" + cron + "'生成有误！", e);
                    }
                    // logger.info("=====cron===="+cron);
                    // CronTriggerImpl cTrigger = new
                    // CronTriggerImpl(jobname,groupname, jobname,
                    // groupname,_startTime ,_endTime ,cron);

                    CronTriggerImpl cTrigger = (CronTriggerImpl) TriggerBuilder
                            .newTrigger().withIdentity(jobname, groupname)
                            .withSchedule(
                                    CronScheduleBuilder.cronSchedule(expression))
                            .build();
                    cTrigger.setJobName(jobname);
                    cTrigger.setJobGroup(groupname);
                    cTrigger.setName(jobname);
                    cTrigger.setGroup(groupname);
                    cTrigger.setStartTime(_startTime);
                    cTrigger.setEndTime(_endTime);
                    cTrigger.setCronExpression(expression);
                    cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
                    //cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
                    // shouldRecover属性为true，则当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务。
                    jobDetail.setRequestsRecovery(shouldRecover);
                    // jobDetail.setTriggerType(1);
                    this.updateJob(jobDetail, cTrigger, taskDescription);
                }

                // 执行一次
                else if (StringUtils.equals(taskTypeSel, "2")) {
                    Date runTime;
                    String source = "";
                    if (StringUtils.isEmpty(taskDate)
                            || StringUtils.isBlank(taskDate)
                            || StringUtils.isEmpty(taskTime)
                            || StringUtils.isBlank(taskTime)) {
                        runTime = new Date();
                        source = sdf.format(runTime);

                    } else {
                        source = taskDate + " " + taskTime;
                        runTime = sdf.parse(source);

                    }
                    // SimpleTriggerImpl trigger = new SimpleTriggerImpl(name,
                    // group, runTime);
                    SimpleTriggerImpl trigger = (SimpleTriggerImpl) TriggerBuilder
                            .newTrigger().withIdentity(jobname, groupname)
                            .startAt(runTime).build();
                    trigger.setJobName(jobname);
                    trigger.setJobGroup(groupname);
                    trigger.setName(jobname);
                    trigger.setGroup(groupname);

                    taskDescription = "在" + source + "执行一次";
                    trigger.setMisfireInstruction(
                            SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
                    // shouldRecover属性为true，则当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务。
                    jobDetail.setRequestsRecovery(shouldRecover);
                    this.updateJob(jobDetail, trigger, taskDescription);
                }
                // 请选择
                else if (StringUtils.equals(taskTypeSel, "-1")) {
                    /** 空方法 */
                } else {
                    /** 空方法 */
                }

            } else {
                cronExpression = cronExpression.trim();

                // 执行中应用发生故障，需要重新执行
                // jobDetail.setRequestsRecovery(true);
                // 即使没有Trigger关联时，也不需要删除该JobDetail
                // jobDetail.setDurability(true);
                CronExpression expression = null;
                try {
                    expression = new CronExpression(cronExpression);
                } catch (ParseException e) {
                    throw new Exception("表达式Cron输入有误！", e);
                }

                // CronTriggerImpl cTrigger = new CronTriggerImpl(jobname,groupname,
                // jobname, groupname ,cronExpression);
                CronTriggerImpl cTrigger = (CronTriggerImpl) TriggerBuilder
                        .newTrigger().withIdentity(jobname, groupname)
                        .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                        .build();
                cTrigger.setJobName(jobname);
                cTrigger.setJobGroup(groupname);
                cTrigger.setName(jobname);
                cTrigger.setGroup(groupname);
                cTrigger.setCronExpression(expression);
                cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
                //cTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

                // shouldRecover属性为true，则当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务。
                jobDetail.setRequestsRecovery(shouldRecover);
                // jobDetail.setTriggerType(1);
                data.put("triggerType", "1");
                this.updateJob(jobDetail, cTrigger, null);

            }
            JobDetailDto returnVal = new JobDetailDto(jobDetail);
            return returnVal;
        } catch (Exception e) {
            throw new ApplicationException("修改job", e);
        }
    }

    private final void checkValid(String field, String showName) {
        if (StringUtils.isEmpty(field) || StringUtils.isBlank(field)) {
            throw new ApplicationException("输入参数'" + showName + "'有误");
        }
    }

    /**
     * 任务执行频率
     */
    public static class TaskRate {
        public final static String DAY = "day";
        public final static String WEEK = "week";
        public final static String MONTH = "month";
    }

    /**
     * 占位符
     */
    public static class Placeholder {
        public final static String p1 = "{1}";
        public final static String p2 = "{2}";
        public final static String p3 = "{3}";
        public final static String p4 = "{4}";
        public final static String p5 = "{5}";
        public final static String p6 = "{6}";
    }
}
