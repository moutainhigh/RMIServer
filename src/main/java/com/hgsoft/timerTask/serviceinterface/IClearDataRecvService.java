package com.hgsoft.timerTask.serviceinterface;

import com.hgsoft.settlement.entity.ProviceRecvBoard;

import java.io.Serializable;

/**
 * Created by 孙晓伟
 * file : IClearDataRecvService.java
 * date : 2017/9/13
 * time : 18:50
 */
public interface IClearDataRecvService extends Serializable{

    /***
     * 处理公告接收表清算数据
     * @param item
     */
    public void handleClearData(ProviceRecvBoard item);
}
