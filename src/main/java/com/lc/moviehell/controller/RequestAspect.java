package com.lc.moviehell.controller;

import com.lc.moviehell.util.LogUtil;
import com.lc.moviehell.bean.RespBody;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.util.RegexUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by lc on 15/5/5.
 */
@Component
@Aspect
public class RequestAspect {
    private static final Logger logger = LoggerFactory.getLogger(
        RequestAspect.class);

    @Pointcut("execution(* com.lc.moviehell.controller.MovieController.get*(..))")
    private void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        LogUtil.init();
        long starttime = System.currentTimeMillis();
        LogUtil.setStarttime(starttime);
        try {
            MethodSignature methodSignature = (MethodSignature) point
                .getSignature();
            Method reqMethod = methodSignature.getMethod();
            String methodname = reqMethod.getName();
            LogUtil.addProp("method", methodname);
            String uri = request.getRequestURI();
            String userip = getRequestIp(request);
            LogUtil.setUri(uri);
            LogUtil.addProp("userip", userip);
            Object object = point.proceed();
            RespBody resp = (RespBody) object;
            LogUtil.setRetcode(resp.getCode());
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogUtil.setRetcode(RespCode.UNKNOW);
            return RespBody.builder(RespCode.UNKNOW);
        } finally {
            LogUtil.setSpendtime(System.currentTimeMillis() - starttime);
            LogUtil.info();
        }
    }

    public static String getRequestIp(HttpServletRequest request) {
        try {
            String realIp = null;
            String xff = request.getHeader("X-Forwarded-For");
            if (xff != null && !xff.isEmpty()) {
                String[] ips = xff.split(",");
                for (String ip: ips) {
                    if (!RegexUtil.isInternalIp(ip)) {
                        realIp = ip;
                        break;
                    }
                }
            }
            if (realIp == null) {
                realIp = request.getRemoteAddr();
            }

            realIp = realIp.trim();
            return realIp;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }
}
