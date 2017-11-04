package com.hgsoft.clusterquartz.jobdetail;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * Created by yangzhongji on 17/5/26.
 */
@DisallowConcurrentExecution
public class MyDetailQuartzJobBean extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(MyDetailQuartzJobBean.class);
    private String targetObject;
    private String targetMethod;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            /*if (targetMethod == null || !targetMethod.startsWith("insertPrePayTotal")) {
                return;
            }*/
            //logger.info("execute [{}.{}] at once>>>>>>", targetObject, targetMethod);
            Object otargetObject = applicationContext.getBean(targetObject);
            Method m = null;
            try {
                m = otargetObject.getClass().getMethod(targetMethod, new Class[] {});
                m.invoke(otargetObject, new Object[] {});
            } catch (Exception e) {
            	logger.error("execute [{}.{}] 异常", targetObject, targetMethod);
                logger.error("执行任务", e);
            }
            //logger.info("execute [{}.{}] finish>>>>>>", targetObject, targetMethod);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
