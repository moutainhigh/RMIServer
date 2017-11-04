package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.prepaidC.entity.PrepareCardSet;

/**
 * Created by 孙晓伟
 * file : IPrepareCardSetService.java
 * date : 2017/10/2
 * time : 11:24
 */
public interface IPrepareCardSetService {
    public PrepareCardSet findByCardNo(String cardNo);
}
