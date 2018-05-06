/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.war.wechat.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserCost;
import com.thinkgem.jeesite.modules.vip.entity.VipUserPay;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;
import com.thinkgem.jeesite.modules.vip.service.VipUserCostService;
import com.thinkgem.jeesite.modules.vip.service.VipUserPayService;

/**
 * 微信小程序端查询VIP数据
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${wechatPath}/vipWechat")
public class VipWechatController extends BaseController {

	@Autowired
	private VipUserBaseService vipUserBaseService;
	@Autowired
	private VipUserPayService vipUserPayService;
	@Autowired
	private VipUserCostService vipUserCostService;
	/**
	 * 
	 * 查询出用户钱包
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findUserAllVip")
	public List<VipUserBase> findUserAllVip(HttpServletRequest request) {
		String phone = request.getParameter("phone");
		String openid = request.getParameter("openid");
		return vipUserBaseService.findUserAllVip(phone, openid);
	}
	
	/**
	 * 
	 * 查询用户充值
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findVipUserPayList")
	public List<VipUserPay> findVipUserPayList(HttpServletRequest request) {
		String vipId = request.getParameter("vipId");
		VipUserPay parm = new VipUserPay();
		parm.setVipId(vipId);
		return vipUserPayService.findList(parm);
	}
	
	/**
	 * 
	 * 查询用户消费
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findVipUserCostList")
	public List<VipUserCost> findVipUserCostList(HttpServletRequest request) {
		String vipId = request.getParameter("vipId");
		VipUserCost parm = new VipUserCost();
		parm.setVipId(vipId);
		return vipUserCostService.findList(parm);
	}

}