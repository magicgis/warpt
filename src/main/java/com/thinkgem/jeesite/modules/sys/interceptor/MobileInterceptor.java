/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.war.wechat.base.api.OauthAPI;
import com.war.wechat.base.api.config.ApiConfig;
import com.war.wechat.base.api.enums.OauthScope;
import com.war.wechat.base.api.response.GetUserInfoResponseForQYH;
import com.war.wechat.base.servlet.WeChatConst;

/**
 * 手机端视图拦截器
 * 
 * @author ThinkGem
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements
		HandlerInterceptor {

	@Autowired
	private SystemService systemService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 如果是手机或平板访问的话，则跳转到手机视图页面。
		if (this.isMobileOrTablet(request)) {
			String uri = request.getRequestURI();
			String ctx = request.getContextPath();
			uri = uri.substring(uri.indexOf(ctx) + ctx.length());
			System.out.println(uri + "===uri");
			String requestMark = request.getParameter("requestMark");// 请求标识
			// 微信公众号开发处理
			if (StringUtils.equals("wechat", requestMark)) {// 请求来自微信
				String code = request.getParameter("code");
				System.out.println("code:" + code);
				if (StringUtils.isBlank(code)) {
					// System.out.println("11111111:" + code);
					ApiConfig config = new ApiConfig(WeChatConst.APPID,
							WeChatConst.SECRET, false);
					OauthAPI api = new OauthAPI(config);
					String redirectURL = "http://www.zzzz.com:8181" + uri
							+ "?requestMark=wechat";
					String oAuthPageURL = api.getOauthPageUrl(redirectURL,
							OauthScope.SNSAPI_BASE, "STATE");
					try {
						((HttpServletResponse) response)
								.sendRedirect(oAuthPageURL);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return false;
				} else {
					// System.out.println("2222222:" + code);
					ApiConfig config = new ApiConfig(WeChatConst.APPID,
							WeChatConst.SECRET, false);
					OauthAPI api = new OauthAPI(config);
					String agentId = WeChatConst.KQ_AGENTID;
					GetUserInfoResponseForQYH tokenRes = api.getUserinfo(code,
							agentId);
					String fromUserName = tokenRes.getUserId();
					User user = UserUtils.getUser();// UserUtils.getByLoginName(fromUserName);

					// user = UserUtils.get(user.getId());
					if (user == null || StringUtils.isEmpty(user.getId())) {
						System.out.println("fromUserName:" + fromUserName);
						Subject currentUser = SecurityUtils.getSubject();
						AuthenticationToken authcToken = new UsernamePasswordToken(
								fromUserName, "888888".toCharArray(), false,
								"", "", true);
						currentUser.login(authcToken);
						user = systemService.getUserByLoginName(fromUserName);
						UserUtils.putCache(UserUtils.CACHE_AUTH_INFO, user);
					}

				}
			} else {

			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 如果是手机或平板访问的话，则跳转到手机视图页面。
		if (this.isMobileOrTablet(request)) {
			if (modelAndView != null
					&& !StringUtils.startsWithIgnoreCase(
							modelAndView.getViewName(), "redirect:")) {
				modelAndView
						.setViewName("mobile/" + modelAndView.getViewName());
			} else {

			}

		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/**
	 * 判断手机或平板电脑(debug也激活)
	 * 
	 * @param request
	 * @return
	 */
	private boolean isMobileOrTablet(HttpServletRequest request) {
		// 是否是手机debug模式
		String debugStr = Global.getConfig("mobileDebug");
		boolean debug = Boolean.valueOf(StringUtils.isEmpty(debugStr) ? "false"
				: debugStr);
		if (debug || UserAgentUtils.isMobileOrTablet(request)) {
			return true;
		}
		return false;
	}

}
