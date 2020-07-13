package com.carry.www.utils.base;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 类描述：IP工具类
 *
 * @author carry
 * @version 1.0 CreateDate: 2019年4月28日
 * <p>
 * 修订历史： 日期 修订者 修订描述
 */
public class IpUtils {

    /**
     * @方法描述:
     * @Param: 获取IP地址
     * @return: java.lang.String
     * @Author: carry
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * @方法描述: 获取本地IP
     * @Param:
     * @return: java.lang.String
     * @Author: carry
     */
    public static String getHostIp() {
        try {

            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            return "127.0.0.1";
        }
    }

    /**
     * @方法描述: 获取本地服务器名称
     * @Param: []
     * @return: java.lang.String
     * @Author: carry
     */
    public static String getHostName() {
        try {

            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {

            return "未知";
        }
    }


}