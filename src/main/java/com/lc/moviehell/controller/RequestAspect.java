package com.lc.moviehell.controller;

import com.alibaba.fastjson.JSONObject;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.bean.ResponseEntry;
import com.lc.moviehell.util.RegexUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        // 日志打印，权限控制
        long starttime = System.currentTimeMillis();
        JSONObject logBean = new JSONObject();
        logBean.put("begintime", starttime);
        try {
            MethodSignature methodSignature = (MethodSignature) point
                .getSignature();
            Method reqMethod = methodSignature.getMethod();
            String methodname = reqMethod.getName();
            logBean.put("method", methodname);
            if (!(point.getArgs()[0] instanceof HttpServletRequest)) {
                throw new IllegalArgumentException(point.getSignature().getName()
                    + "first param must be HttpServletRequest");
            }
            HttpServletRequest request = (HttpServletRequest) point
                .getArgs()[0];

            String uri = request.getRequestURI();
            String userip = getRequestIp(request);
            logBean.put("uri", uri);
            logBean.put("userip", userip);

            request.setAttribute("logBean", logBean);

            Object object = point.proceed();
            ResponseEntry resp = (ResponseEntry) object;
            logBean.put("retcode", resp.getCode());
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logBean.put("retcode", RespCode.UNKNOW);
            return ResponseEntry.builder(RespCode.UNKNOW);
        } finally {
            logBean.put("spendtime", System.currentTimeMillis() - starttime);
            logger.info(logBean.toJSONString());
        }
    }

    public static String getRequestIp(HttpServletRequest request) {
        try {
            String realIp = null;

            // 优先拿前端nginx放在forward包头里的ip
            String xff = request.getHeader("X-Forwarded-For");
            if (xff != null && !xff.isEmpty()) {
                String[] ips = xff.split(",");
                for (String ip: ips) {
                    // 2g/3g网关可能在该包头里放一个内网ip，需要过滤
                    if (!RegexUtil.isInternalIp(ip)) {
                        realIp = ip;
                        break;
                    }
                }
            }
            if (realIp == null) {
                // logger.warn("X-Forwarded-For contains no external ip: " +
                // xff);
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
