package com.wt.master.core.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 请求上下文工具
 */
public class RequestContextUtils {
    /**
     * 获取request对象
     *
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();

    }

    /**
     * 获取response对象
     *
     * @return
     */
    public static HttpServletResponse getHttpServletResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 获取session对象
     *
     * @return
     */
    public static HttpSession getHttpSession() {
        return getServletRequestAttributes().getRequest().getSession();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes;
    }
}
