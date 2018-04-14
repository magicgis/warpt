/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrder;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseSupplier;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.service.ShopProductService;
import com.thinkgem.jeesite.modules.shop.service.ShopPurchaseOrderService;
import com.thinkgem.jeesite.modules.shop.service.ShopPurchaseSupplierService;
import com.thinkgem.jeesite.modules.shop.service.ShopStockInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 商品采购单Controller
 * @author swbssd
 * @version 2018-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopPurchaseOrder")
public class ShopPurchaseOrderController extends BaseController {

	@Autowired
	private ShopPurchaseOrderService shopPurchaseOrderService;
	
	
	@ModelAttribute
	public ShopPurchaseOrder get(@RequestParam(required=false) String id) {
		ShopPurchaseOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopPurchaseOrderService.get(id);
		}
		if (entity == null){
			entity = new ShopPurchaseOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopPurchaseOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopPurchaseOrder shopPurchaseOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopPurchaseOrder> page = shopPurchaseOrderService.findPage(new Page<ShopPurchaseOrder>(request, response), shopPurchaseOrder); 
		model.addAttribute("page", page);
		return "modules/shop/shopPurchaseOrderList";
	}

	@RequiresPermissions("shop:shopPurchaseOrder:view")
	@RequestMapping(value = "form")
	public String form(ShopPurchaseOrder shopPurchaseOrder, Model model) {
		//model.addAttribute("shopPurchaseOrder", shopPurchaseOrder);
		return "modules/shop/shopPurchaseOrderForm";
	}
	
	/**
	 * 余额查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadingForm")
	public ShopPurchaseOrder loadingForm(HttpServletRequest request) {
		String id = request.getParameter("id");
		ShopPurchaseOrder shopPurchaseOrder = shopPurchaseOrderService.getEdit(id);
		return shopPurchaseOrder;
	}

	@RequiresPermissions("shop:shopPurchaseOrder:edit")
	@RequestMapping(value = "save")
	public String save(ShopPurchaseOrder shopPurchaseOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopPurchaseOrder)){
			return form(shopPurchaseOrder, model);
		}
		shopPurchaseOrderService.save(shopPurchaseOrder);
		addMessage(redirectAttributes, "保存商品采购单成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopPurchaseOrder/?repage";
	}
	
	@RequiresPermissions("shop:shopPurchaseOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopPurchaseOrder shopPurchaseOrder, RedirectAttributes redirectAttributes) {
		shopPurchaseOrderService.delete(shopPurchaseOrder);
		addMessage(redirectAttributes, "删除商品采购单成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopPurchaseOrder/?repage";
	}

}