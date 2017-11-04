package com.hgsoft.account.serviceInterface;

import com.hgsoft.account.entity.BailAccountInfo;

/**
 * @FileName IBailAccountInfoService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月13日 上午10:27:09 
*/
public interface IBailAccountInfoService {

	BailAccountInfo findByMainId(Long id);

}
