package com.hgsoft.timerTask.job;

import com.hgsoft.settlement.entity.ProviceRecvBoard;
import com.hgsoft.settlement.serviceinterface.IProviceRecvBoardService;
import com.hgsoft.timerTask.serviceinterface.IClearDataRecvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 孙晓伟
 * file : ClearDataHandleJob.java
 * date : 2017/9/18
 * time : 14:42
 */
@Component
public class ClearDataHandleJob {
    private static final Logger logger = LoggerFactory.getLogger(ClearDataHandleJob.class);

    @Resource
    private IProviceRecvBoardService proviceRecvBoardService;

    @Resource
    private IClearDataRecvService clearDataRecvService;

    public void dealClearData(){
        logger.info("**********************处理清算数据公告接收记录开始****************************");
        //获取待处理的清算公共序列号信息
        //从本地获取的公告数据
        List<ProviceRecvBoard> proviceBoards = proviceRecvBoardService.queryClearData();
        if(proviceBoards!=null && !proviceBoards.isEmpty()){
            for(ProviceRecvBoard item :proviceBoards){
                try {
                    clearDataRecvService.handleClearData(item);
                }catch (Exception e) {
                    logger.error("清算数据公告序列号[{}]处理失败],", item.getListNo());
                    logger.error("清算数据公告处理失败", e);
                }
            }
        }else{
            logger.info("无需要处理的清算数据公告记录");
        }
        logger.info("**********************处理清算数据公告接收记录结束****************************");

    }

}
