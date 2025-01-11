package com.warehouse.common.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Value("#{${secrets.users}}")
    Map<String, String> secretsMap;

    @Value("${some}")
    private String some;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle(): before controller");
        request.getMethod();
        request.getRequestURI();

//        String secret = secretsMap.get(request.getHeader("clientId"));
//        String token = request.getHeader("token");
//        JwtUtils.verifyToken(token, secret);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        try {
            log.info("postHandle(): After the controller serves the request");
        } catch (Exception e) {
            log.error("Exception occurred in preHandle()", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            log.info("afterCompletion(): after request and response");
        } catch (Exception e) {
            log.error("Exception occurred in preHandle()", e);
        }
    }
}
