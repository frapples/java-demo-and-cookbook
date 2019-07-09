package io.github.frapples.javademoandcookbook.commonutils.utils.log;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * FIXME: 未严谨编写单元测试
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/24
 */
@Aspect
@Component
public class AroundLogAspect {

    @Around(value = "@annotation(aroundLog)")
    public Object around(ProceedingJoinPoint pointJoin, AroundLog aroundLog) throws Throwable {

        Logger log = LoggerFactory.getLogger(pointJoin.getTarget().getClass());

        if (!LogUtils.isEnable(log, aroundLog.level())) {
            return pointJoin.proceed();
        }

        LookUp lookUp = new LookUp(pointJoin, aroundLog);
        StringSubstitutor stringSubstitutor = new StringSubstitutor(lookUp);

        String enterLog = stringSubstitutor.replace(aroundLog.enterFormat());
        LogUtils.log(log, aroundLog.level(), enterLog);

        try {

            lookUp.returnValue = pointJoin.proceed();
        } catch (Throwable e) {
            lookUp.exception = e;
            String exitString = stringSubstitutor.replace(aroundLog.throwFormat());
            LogUtils.log(log, aroundLog.level(), exitString);
            throw e;
        }

        String exitString = stringSubstitutor.replace(aroundLog.exitFormat());
        LogUtils.log(log, aroundLog.level(), exitString);
        return lookUp.returnValue;
    }

    @RequiredArgsConstructor
    static class LookUp implements StringLookup {

        private final ProceedingJoinPoint pointJoin;
        private final AroundLog aroundLog;

        private String requestId = null;

        Object returnValue = null;
        Throwable exception = null;

        @Override
        public String lookup(String key) {

            switch (key) {
                case "functionName":
                    return objectToString(pointJoin.getSignature().getName());

                case "returnValue":
                    return objectToString(returnValue);

                case "exception":
                    return ExceptionUtils.getStackTrace(exception);

                case "requestId":
                    return getRequestId();

                // FIXME: 防止死递归
                case "enterMessage":
                    return new StringSubstitutor(this).replace(aroundLog.enterMessage());

                case "exitMessage":
                    return new StringSubstitutor(this).replace(aroundLog.exitMessage());

                case "throwMessage":
                    return new StringSubstitutor(this).replace(aroundLog.throwMessage());

                default:
                    String argPrefix = "arg";
                    if (StringUtils.startsWith(key, argPrefix)) {
                        try {
                            int argumentIndex = Integer.parseInt(key.substring(argPrefix.length()));
                            if (argumentIndex < pointJoin.getArgs().length) {
                                return objectToString(pointJoin.getArgs()[argumentIndex]);
                            }
                        } catch (NumberFormatException ignore) {
                        }
                    }
                    return "";
            }
        }

        private String getRequestId() {
            if (requestId == null) {
                requestId = UUID.randomUUID().toString();
            }
            return requestId;
        }

        private String objectToString(Object o) {
            return String.valueOf(o);
        }
    }
}
