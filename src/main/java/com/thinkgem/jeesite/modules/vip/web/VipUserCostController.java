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
import com.thinkgem.jeesite.modules.vip.entity.VipUserCost;
import com.thinkgem.jeesite.modules.vip.service.VipUserCostService;

/**
 * 会员消费记录Controller
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipUserCost")
public class VipUserCostController extends BaseController {

	@Autowired
	private VipUserCostService vipUserCostService;
	
	@ModelAttribute
	public VipUserCost get(@RequestParam(required=false) String id) {
		VipUserCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipUserCostService.get(id);
		}
		if (entity == null){
			entity = new VipUserCost();
		}
		return entity;
	}
	
	@RequiresPermissions("vip:vipUserCost:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipUserCost vipUserCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		//登陆用户机构过滤
		vipUserCost.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<VipUserCost> page = vipUserCostService.findPage(new Page<VipUserCost>(request, response), vipUserCost); 
		model.addAttribute("page", page);
		return "modules/vip/vipUserCostList";
	}

	@RequiresPermissions("vip:vipUserCost:view")
	@RequestMapping(value = "form")
	public String form(VipUserCost vipUserCost, Model model) {
		model.addAttribute("vipUserCost", vipUserCost);
		return "modules/vip/vipUserCostForm";
	}

	@RequiresPermissions("vip:vipUserCost:edit")
	@RequestMapping(value = "save")
	public String save(VipUserCost vipUserCost, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipUserCost)){
			return form(vipUserCost, model);
		}
		if(vipUserCost.getCostMoeny() == 0 && vipUserCost.getCostScore() == 0){
			throw new RuntimeException("消费金额和积分必须有一个不能为0");
		}
		//登陆用户机构过滤
		vipUserCost.setOfficeId(UserUtils.getUser().getOffice().getId());		
		vipUserCostService.save(vipUserCost);
		addMessage(redirectAttributes, "保存会员消费记录成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipUserCost/?repage";
	}
	
	@RequiresPermissions("vip:vipUserCost:edit")
	@RequestMapping(value = "delete")
	public String delete(VipUserCost vipUserCost, RedirectAttributes redirectAttributes) {
		vipUserCostService.delete(vipUserCost);
		addMessage(redirectAttributes, "删除会员消费记录成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipUserCost/?repage";
	}

}