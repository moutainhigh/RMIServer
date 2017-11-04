package com.hgsoft.timerTask.service;

import com.hgsoft.settlement.entity.ProviceRecvBoard;
import com.hgsoft.settlement.serviceinterface.IProviceRecvBoardService;
import com.hgsoft.timerTask.serviceinterface.IClearDataRecvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 孙晓伟
 * file : ClearDataRecvService.java
 * date : 2017/9/13
 * time : 18:51
 */
@Service
public class ClearDataRecvService implements IClearDataRecvService{


    private static final Logger logger = LoggerFactory.getLogger(ClearDataRecvService.class);

    @Resource
    private IProviceRecvBoardService proviceRecvBoardService;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        IClearDataRecvService clearDataRecvService = (IClearDataRecvService)context.getBean("clearDataRecvService");
        System.out.println();
    }

    @Override
    public void handleClearData(ProviceRecvBoard item) {
        //获取待处理的清算公共序列号信息
        //从本地获取的公告数据

        Long boardListNo = item.getListNo();
        String tableName = item.getTableName();

        int updateFlag = 0;
        //根据表名和公告序列号获取数据记录数
        int clearDataNumber = proviceRecvBoardService.queryCountClearData(tableName, boardListNo);
        //校验记录数
        if (item.getCnt().equals(clearDataNumber)) {
            //将接收数据移至客服接收表
            proviceRecvBoardService.saveClearData2Csms(tableName, boardListNo);

            //将接收数据移至客服数据历史表
            proviceRecvBoardService.saveClearData2CsmsHis(tableName, boardListNo);
            updateFlag = ProviceRecvBoard.UPDAETFLAG_VERIFIED;
        } else {
            //将接收数据移至客服接收异常
            proviceRecvBoardService.saveClearData2CsmsErr(tableName, boardListNo);

            updateFlag = ProviceRecvBoard.UPDAETFLAG_VERIFYFAIL;
        }




        //删除清算接收数据
        proviceRecvBoardService.deleteClearData(tableName, boardListNo);

        //备份清算接收公告表至客服公告历史表
        proviceRecvBoardService.saveRecvBoard2CsmsHis(boardListNo, updateFlag);

        //删除清算接收公告表
        proviceRecvBoardService.deleteRecvBoardByListNo(boardListNo);

        logger.error("清算数据公告序列号[{}]处理成功,表名为[{}],表编码为[{}],表记录数[{}],", item.getListNo(), item.getTableName(), item.getTableCode(), item.getCnt());

    }

}
