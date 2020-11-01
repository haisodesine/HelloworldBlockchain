package com.xingkaichun.helloworldblockchain.node.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Security过滤器
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

	@Value("#{'${permit.ip}'.split(',')}")
	private String[] permitIp;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object){
		if(permitIp != null){
			String remoteHost = httpServletRequest.getRemoteHost();
			for(String ip:permitIp){
				//0.0.0.0代表允许所有ip访问
				if("0.0.0.0".equals(ip)){
					return true;
				}
				//允许访问的ip
				if(remoteHost.equals(ip)){
					return true;
				}
			}
		}
		throw new RuntimeException("该IP无操作权限!");
	}
}