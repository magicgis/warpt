/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipUserPay;
import com.thinkgem.jeesite.modules.vip.service.VipUserPayService;

/**
 * 会员充值记录Controller
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipUserPay")
public class VipUserPayController extends BaseController {

	@Autowired
	private VipUserPayService vipUserPayService;
	
	@ModelAttribute
	public VipUserPay get(@RequestParam(required=false) String id) {
		VipUserPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipUserPayService.get(id);
		}
		if (entity == null){
			entity = new VipUserPay();
		}
		return entity;
	}
	
	@RequiresPermissions("vip:vipUserPay:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipUserPay vipUserPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		//登陆用户机构过滤
		vipUserPay.setOfficeId(UserUtils.getUser().getOffice().getId());		
		Page<VipUserPay> page = vipUserPayService.findPage(new Page<VipUserPay>(request, response), vipUserPay); 
		model.addAttribute("page", page);
		return "modules/vip/vipUserPayList";
	}

	@RequiresPermissions("vip:vipUserPay:view")
	@RequestMapping(value = "form")
	public String form(VipUserPay vipUserPay, Model model) {
		model.addAttribute("vipUserPay", vipUserPay);
		return "modules/vip/vipUserPayForm";
	}

	@RequiresPermissions("vip:vipUserPay:edit")
	@RequestMapping(value = "save")
	public String save(VipUserPay vipUserPay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipUserPay)){
			return form(vipUserPay, model);
		}
		if(vipUserPay.getPayMoeny() == 0){
			throw new RuntimeException("充值金额不能为0");
		}
		//登陆用户机构过滤
		vipUserPay.setOfficeId(UserUtils.getUser().getOffice().getId());			
		vipUserPayService.save(vipUserPay);
		addMessage(redirectAttributes, "保存会员充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipUserPay/?repage";
	}
	
	@RequiresPermissions("vip:vipUserPay:edit")
	@RequestMapping(value = "delete")
	public String delete(VipUserPay vipUserPay, RedirectAttributes redirectAttributes) {
		vipUserPayService.delete(vipUserPay);
		addMessage(redirectAttributes, "删除会员充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipUserPay/?repage";
	}

}