package com.bostoli.wxh5userapi.common.configuration;


import com.bostoli.wxh5userapi.model.internal.UserInfo;
import com.bostoli.wxh5userapi.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthFilter implements Filter {
    static String MID_CUSTOMER = "MID_CUSTOMER";

    UserInfoService authService;

    public AuthFilter(UserInfoService authService) {
        this.authService = authService;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String authToken = req.getHeader("Authorization");
        if(authToken==null){
            // 为空就滚去error
            res.sendError(401, "Auth fail!");
            return;
        }
        // 有值，就继续执行下一个过滤链
        UserInfo midCustomer = null;
        try {
            log.info("Token is {}", authToken);
            midCustomer = authService.authUser(authToken);
            log.info("Auth pass with user {}", midCustomer);
            req.setAttribute(MID_CUSTOMER, midCustomer);
        } catch (Exception e) {
            log.error("Token verify fail:  {}", e.getMessage());
            res.setContentType("application/json");
            res.sendError(401, "Auth fail!");
            return;
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}