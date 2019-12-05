package cn.bestsort.bbslite.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author bestsort
 */
@Slf4j
public class IpUtil {
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        String unknown = "unknown";
        String localhost = "127.0.0.1";
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals(localhost)) {
                    // 根据网卡取本机配置的IP
                    InetAddress address = null;
                    try {
                        address = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("Un known host: --> {}",e.getMessage());
                    }
                    ipAddress = address != null ? address.getHostAddress() : null;
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
            log.warn("this ip address is null --> {}",request.getHeader("Host"));
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
}