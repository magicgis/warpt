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
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.service.VipUserWalletService;

/**
 * 会员钱包Controller
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipUserWallet")
public class VipUserWalletController extends BaseController {

	@Autowired
	private VipUserWalletService vipUserWalletService;
	
	@ModelAttribute
	public VipUserWallet get(@RequestParam(required=false) String id) {
		VipUserWallet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipUserWalletService.get(id);
		}
		if (entity == null){
			entity = new VipUserWallet();
		}
		return entity;
	}
	
	@RequiresPermissions("vip:vipUserWallet:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipUserWallet vipUserWallet, HttpServletRequest request, HttpServletResponse response, Model model) {
		//登陆用户机构过滤
		vipUserWallet.setOfficeId(UserUtils.getUser().getOffice().getId());			
		Page<VipUserWallet> page = vipUserWalletService.findPage(new Page<VipUserWallet>(request, response), vipUserWallet); 
		model.addAttribute("page", page);
		return "modules/vip/vipUserWalletList";
	}

	@RequiresPermissions("vip:vipUserWallet:view")
	@RequestMapping(value = "form")
	public String form(VipUserWallet vipUserWallet, Model model) {
		model.addAttribute("vipUserWallet", vipUserWallet);
		return "modules/vip/vipUserWalletForm";
	}

	@RequiresPermissions("vip:vipUserWallet:edit")
	@RequestMapping(value = "save")
	public String save(VipUserWallet vipUserWallet, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipUserWallet)){
			return form(vipUserWallet, model);
		}
		//登陆用户机构过滤
		vipUserWallet.setOfficeId(UserUtils.getUser().getOffice().getId());		
		vipUserWalletService.save(vipUserWallet);
		addMessage(redirectAttributes, "保存会员钱包成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipUserWallet/?repage";
	}
	
	@RequiresPermissions("vip:vipUserWallet:edit")
	@RequestMapping(value = "delete")
	public String delete(VipUserWallet vipUserWallet, RedirectAttributes redirectAttributes) {
		vipUserWalletService.delete(vipUserWallet);
		addMessage(redirectAttributes, "删除会员钱包成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vipUserWallet/?repage";
	}

}