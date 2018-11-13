package com.zhuiyi.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/18 19:14
 * description: 从request中获取客户端的详细信息
 * own: zhuiyi
 */
public class HttpRequestUtil {

    private final static String REQUEST_IP_UNKNOWN = "unknown";

    /**
     * 从Request中获取请求的IP地址
     * @param request 客户端请求
     * @return 客户端真实IP地址
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || REQUEST_IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || REQUEST_IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || REQUEST_IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || REQUEST_IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || REQUEST_IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 从Request中获取客户端的浏览器信息
     * @param request 客户端请求
     * @return 浏览器信息
     */
    public static String getBrowser(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * 从Request中获取访问来源url
     * @param request 客户端请求
     * @return 访问来源url
     */
    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }
}
