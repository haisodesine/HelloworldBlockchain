package com.xingkaichun.helloworldblockchain.node.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object){
		String remoteHost = httpServletRequest.getRemoteHost();
		if("localhost".equals(remoteHost) || "127.0.0.1".equals(remoteHost) || "0:0:0:0:0:0:0:1".equals(remoteHost)){
			return true;
		}
		logger.debug("该IP无操作权限!");
		return false;
	}
}