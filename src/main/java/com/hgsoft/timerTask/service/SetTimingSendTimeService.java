package com.hgsoft.timerTask.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
//import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.timerTask.serviceinterface.ISetTimingSendTimeService;
import com.hgsoft.timerTask.serviceinterface.ITimedTaskService;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;

@Service
public class SetTimingSendTimeService implements ISetTimingSendTimeService{  

	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());
    private Scheduler scheduler; 
    
    @Resource
    private ITimedTaskService timedTaskService;
    
      
    /** 
     * 设置下发定时时间 
     */ 
    @Override
    public void setTimingSendTime() {
        try {  
          
             
           /* CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger("updateInvoiceChangeFlowTrigger", Scheduler.DEFAULT_GROUP);
            String sendTime = "2016.11.1 18:00:00";//获取需要设置的时间
              
            *//*if (sendTime == null) {
                return ;  
            }  *//*
          String t = sendTime.substring(0, sendTime.lastIndexOf("."));  
            String[] sendTimes = sendTime.split(" ")[1].split(":");  
            //设置时分秒
            String pushTime = sendTimes[2] + " " + sendTimes[1] + " " + sendTimes[0]+ " * * ? ";
            trigger.setCronExpression(pushTime);//设置定时器触发时间  
            scheduler.rescheduleJob("updateInvoiceChangeFlowTrigger",Scheduler.DEFAULT_GROUP, trigger); 
            
            timedTaskService.setTime(t, new Date());*/
        } catch (Exception e) {  
        	logger.error("");
			e.printStackTrace();
			throw new ApplicationException(""); 
        }  
    }  
  
    public Scheduler getScheduler() {  
        return scheduler;  
    }  
  
    public void setScheduler(Scheduler scheduler) {  
        this.scheduler = scheduler;  
    }  
    
  
} 
