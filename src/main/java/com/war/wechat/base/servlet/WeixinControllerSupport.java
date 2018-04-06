package com.war.wechat.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信公众平台交互操作基类，提供几乎所有微信公众平台交互方式
 *
 */
public abstract class WeixinControllerSupport extends WeixinSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4293082541659692495L;

	/**
     * 绑定微信服务器
     *
     * @param request 请求
     * @return 响应内容
     */
	public String bind(HttpServletRequest request, HttpServletResponse response) {
        if (isLegal(request)) {
            //绑定微信服务器成功
            return request.getParameter("echostr");
        } else {
            //绑定微信服务器失败
            return "";
        }
    }

    /**
     * 微信消息交互处理
     *
     * @param request http 请求对象
     * @return 响应给微信服务器的消息报文
     * @throws ServletException 异常
     * @throws IOException      IO异常
     */
    public String process(HttpServletRequest request, HttpServletResponse response) {
		if (!isLegal(request)) {
            return "";
        }
        return processRequest(request);
    }
}