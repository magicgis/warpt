package com.thinkgem.jeesite.modules.sys.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;


public class WechatAuthentication extends AuthenticationFilter {
	
	 protected Logger log = LoggerFactory.getLogger(getClass()); 

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("Wechat用户进入校验！" + getLoginUrl());
        HttpServletRequest req = (HttpServletRequest) request;
        String openId = req.getParameter("openId");  
		//验证openId是否绑定，判断用户是否合法
		VipUserBaseService vipUserBaseService = SpringContextHolder.getBean(VipUserBaseService.class);
		VipUserBase parm = new VipUserBase();
		// 登陆用户机构过滤
		parm.setOpenId(openId);
		List<VipUserBase> list = vipUserBaseService.findList(parm);
		if (list != null && !list.isEmpty()) {//TODO 考虑对TOKEN进行更严格的校验
			return true;
		}else{
			response.setContentType("text/plain;charset=UTF-8");
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("success", false);
			result.put("message", "非法的请求,token不合法");
			String outString = JSONUtils.toJSONString(result);
			try {
				response.getWriter().write(outString);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	

}
