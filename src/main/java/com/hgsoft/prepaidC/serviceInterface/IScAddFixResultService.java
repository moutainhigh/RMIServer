package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.prepaidC.entity.ScAddFixResult;

import java.util.List;

/**
 * Created by yangzhongji on 17/9/11.
 */
public interface IScAddFixResultService {

    List<ScAddFixResult> findCheckThroughNotDeal();

    List<ScAddFixResult> findCheckNotThroughNotDeal();

    void updateRecharge(ScAddFixResult scAddFixResult);

    void updateNotSure(ScAddFixResult scAddFixResult);
}
