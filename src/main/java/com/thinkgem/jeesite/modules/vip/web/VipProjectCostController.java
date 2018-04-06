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
import com.thinkgem.jeesite.modules.vip.entity.VipProjectCost;
import com.thinkgem.jeesite.modules.vip.service.VipProjectCostService;

/**
 * 会员项目消费Controller
 * @author swbssd
 * @version 2018-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipProjectCost")
public class VipProjectCostController extends BaseController {

	@Autowired
	private VipProjectCostService vipProjectCostService;
	
	@ModelAttribute
	public VipProjectCost get(@RequestParam(required=false) String id) {
		VipProjectCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipProjectCostService.get(id);
		}
		if (entity == null){
			entity = new VipProjectCost();
		}
		return entity;
	}
	
	@RequiresPermissions("vip:vipProjectCost:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipProjectCost vipProjectCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		//登陆用户机构过滤
		vipProjectCost.setOfficeId(UserUtils.getUser().getOffice().getId());			
		Page<VipProjectCost> page = vipProjectCostService.findPage(new Page<VipProjectCost>(request, response), vipProjectCost); 
		model.addAttribute("page", page);
		return "modules/vip/vipProjectCostList";
	}

	@RequiresPermissions("vip:vipProjectCost:view")
	@RequestMapping(value = "form")
	public String form(VipProjectCost vipProjectCost, Model model) {
		model.addAttribute("vipProjectCost", vipProjectCost);
		return "modules/vip/vipProjectCostForm";
	}

	@RequiresPermissions("vip:vipProjectCost:edit")
	@RequestMapping(value = "save")
	public String save(VipProjectCost vipProjectCost, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipProjectCost)){
			return form(vipProjectCost, model);
		}
		if (vipProjectCost.getCostNum() == 0) {
			throw new RuntimeException("消费次数不能为0次");
		}
		//登陆用户机构过滤
		//vipProjectCost.setOfficeId(UserUtils.getUser().getOffice().getId());
		vipProjectCostService.save(vipProjectCost);
		addMessage(redirectAttributes, "保存会员项目消费成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipProjectCost/?repage";
	}
	
	@RequiresPermissions("vip:vipProjectCost:edit")
	@RequestMapping(value = "delete")
	public String delete(VipProjectCost vipProjectCost, RedirectAttributes redirectAttributes) {
		vipProjectCostService.delete(vipProjectCost);
		addMessage(redirectAttributes, "删除会员项目消费成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipProjectCost/?repage";
	}

}