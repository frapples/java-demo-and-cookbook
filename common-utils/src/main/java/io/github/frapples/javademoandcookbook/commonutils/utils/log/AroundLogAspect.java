package io.github.frapples.javademoandcookbook.commonutils.utils.log;

import java.util.HashMap;
import org.apache.commons.text.StringSubstitutor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AroundLogAspect {

    @Around(value = "@annotation(aroundLog)")
    public Object around(ProceedingJoinPoint pointJoin, AroundLog aroundLog) throws Throwable {

        Logger log = LoggerFactory.getLogger(pointJoin.getTarget().getClass());

        if (!LogUtils.isEnable(log, aroundLog.level())) {
            return pointJoin.proceed();
        }

        HashMap<String, Object> map = new HashMap<>();
        String functionName = pointJoin.getSignature().getName();
        map.put("functionName", functionName);
        Object[] args = pointJoin.getArgs();
        for (int i = 0; i < args.length; i++) {
            map.put("arg" + i, args[i]);
        }

        String enterLog = StringSubstitutor.replace(aroundLog.enterFormat(), map);
        LogUtils.log(log, aroundLog.level(), enterLog);
        Object retValue = pointJoin.proceed();

        map.put("returnValue", retValue);
        String exitString = StringSubstitutor.replace(aroundLog.exitFormat(), map);
        LogUtils.log(log, aroundLog.level(), exitString);
        return retValue;
    }
}
