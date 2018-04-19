/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import java.util.HashMap;
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
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrder;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrderItem;
import com.thinkgem.jeesite.modules.shop.service.ShopPurchaseOrderService;

/**
 * 商品采购单Controller
 * 
 * @author swbssd
 * @version 2018-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopPurchaseOrder")
public class ShopPurchaseOrderController extends BaseController {

	@Autowired
	private ShopPurchaseOrderService shopPurchaseOrderService;

	@ModelAttribute
	public ShopPurchaseOrder get(@RequestParam(required = false) String id) {
		ShopPurchaseOrder entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = shopPurchaseOrderService.get(id);
		}
		if (entity == null) {
			entity = new ShopPurchaseOrder();
		}
		return entity;
	}

	@RequiresPermissions("shop:shopPurchaseOrder:view")
	@RequestMapping(value = { "list", "" })
	public String list(ShopPurchaseOrder shopPurchaseOrder, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ShopPurchaseOrder> page = shopPurchaseOrderService.findPage(new Page<ShopPurchaseOrder>(request, response),
				shopPurchaseOrder);
		model.addAttribute("page", page);
		return "modules/shop/shopPurchaseOrderList";
	}

	@RequiresPermissions("shop:shopPurchaseOrder:view")
	@RequestMapping(value = "form")
	public String form(ShopPurchaseOrder shopPurchaseOrder, Model model) {
		// model.addAttribute("shopPurchaseOrder", shopPurchaseOrder);
		return "modules/shop/shopPurchaseOrderForm";
	}

	/**
	 * 加载数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadingForm")
	public Map<String, Object> loadingForm(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ShopPurchaseOrder shopPurchaseOrder = shopPurchaseOrderService.getEdit(id);
			returnMap.put("obj", shopPurchaseOrder);
			returnMap.put("success", true);
		} catch (Exception e) {
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}
	
	/**
	 * 保存采购单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveOrderForm")
	public Map<String, Object> saveOrderForm(HttpServletRequest request) {
		String saveJson = request.getParameter("saveJson");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ShopPurchaseOrder shopPurchaseOrder = (ShopPurchaseOrder) JsonMapper.fromJsonString(saveJson, ShopPurchaseOrder.class);
			shopPurchaseOrderService.saveOrde(shopPurchaseOrder);
			returnMap.put("success", true);
		} catch (Exception e) {
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}	

	/**
	 * 选择某产品
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addOrderItem")
	public Map<String, Object> addOrderItem(HttpServletRequest request) {
		String productNo = request.getParameter("productNo");
		String productId = request.getParameter("productId");
		String stockId = request.getParameter("stockId");
		String supplierId = request.getParameter("supplierId");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ShopPurchaseOrderItem orderItem = shopPurchaseOrderService.addOrderItem(productNo,productId, stockId, supplierId);
			returnMap.put("obj", orderItem);
			returnMap.put("success", true);
		} catch (Exception e) {
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}

	@RequiresPermissions("shop:shopPurchaseOrder:edit")
	@RequestMapping(value = "save")
	public String save(ShopPurchaseOrder shopPurchaseOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopPurchaseOrder)) {
			return form(shopPurchaseOrder, model);
		}
		shopPurchaseOrderService.save(shopPurchaseOrder);
		addMessage(redirectAttributes, "保存商品采购单成功");
		return "redirect:" + Global.getAdminPath() + "/shop/shopPurchaseOrder/?repage";
	}

	@RequiresPermissions("shop:shopPurchaseOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopPurchaseOrder shopPurchaseOrder, RedirectAttributes redirectAttributes) {
		shopPurchaseOrderService.delete(shopPurchaseOrder);
		addMessage(redirectAttributes, "删除商品采购单成功");
		return "redirect:" + Global.getAdminPath() + "/shop/shopPurchaseOrder/?repage";
	}

}