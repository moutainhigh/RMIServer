package com.hgsoft.prepaidC.job;

import com.hgsoft.prepaidC.entity.ScAddFixResult;
import com.hgsoft.prepaidC.serviceInterface.IScAddFixResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangzhongji on 17/9/11.
 */
@Component
public class ScAddFixResultJob {

    private static final Logger logger = LoggerFactory.getLogger(ScAddFixResultJob.class);

    @Resource
    private IScAddFixResultService scAddFixResultService;

    public void dealScAddfixResult() {
        List<ScAddFixResult> scAddFixResults = scAddFixResultService.findCheckThroughNotDeal();
        for (ScAddFixResult scAddFixResult : scAddFixResults) {
            try {
                logger.info("处理半条审核通过的校验结果记录[{}]开始", scAddFixResult.getId());
                scAddFixResultService.updateRecharge(scAddFixResult);
                logger.info("处理半条审核通过的校验结果记录[{}]结束", scAddFixResult.getId());
            } catch (Exception e) {
                logger.error("处理半条审核通过的校验结果", e);
            }

        }

        scAddFixResults = scAddFixResultService.findCheckNotThroughNotDeal();
        for (ScAddFixResult scAddFixResult : scAddFixResults) {
            try {
                logger.info("处理审核不通过半条校验结果记录[{}]开始", scAddFixResult.getId());
                scAddFixResultService.updateNotSure(scAddFixResult);
                logger.info("处理审核不通过半条校验结果记录[{}]结束", scAddFixResult.getId());
            } catch (Exception e) {
                logger.error("处理半条校验结果", e);
            }

        }
    }
}
