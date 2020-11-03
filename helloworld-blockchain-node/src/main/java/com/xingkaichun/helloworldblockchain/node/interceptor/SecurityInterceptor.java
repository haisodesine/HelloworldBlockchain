package com.xingkaichun.helloworldblockchain.node.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Security过滤器
 *
 * @author 邢开春 微信HelloworldBlockchain 邮箱xingkaichun@qq.com
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

	//允许的ip列表，多个ip之间以逗号(,)分隔。0.0.0.0代表允许所有ip访问。
	@Value("#{'${permit.ip}'.split(',')}")
	private List<String> permitIpList;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object){
		if(permitIpList != null){
			//0.0.0.0代表允许所有ip访问
			if(permitIpList.contains("0.0.0.0")){
				return true;
			}
			String remoteHost = httpServletRequest.getRemoteHost();
			if(permitIpList.contains(remoteHost)){
				return true;
			}
		}
		throw new RuntimeException("该IP无访问权限!");
	}
}