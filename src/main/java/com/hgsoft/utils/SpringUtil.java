package com.hgsoft.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : 吴锡霖
 *         file : SpringUtil.java
 *         date : 2017-07-14r
 *         
 *         time : 11:43
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    public static ApplicationContext context;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.debug("================初始化SpringUtil=============");
		context = applicationContext;

	}
}
