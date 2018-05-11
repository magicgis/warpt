/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.shop.dao.ShopStockItemDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;

/**
 * 仓库库存Service
 * 
 * @author swbssd
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class ShopStockItemService extends CrudService<ShopStockItemDao, ShopStockItem> {
	@Autowired
	private ShopStockItemDao shopStockItemDao;

	public ShopStockItem get(String id) {
		return super.get(id);
	}

	public List<ShopStockItem> findList(ShopStockItem shopStockItem) {
		return super.findList(shopStockItem);
	}

	public Page<ShopStockItem> findPage(Page<ShopStockItem> page, ShopStockItem shopStockItem) {
		return super.findPage(page, shopStockItem);
	}

	@Transactional(readOnly = false)
	public void delete(ShopStockItem shopStockItem) {
		super.delete(shopStockItem);
	}

	@Transactional(readOnly = false)
	public void save(ShopStockItem shopStockItem) {
		super.save(shopStockItem);
	}

	/**
	 * 产品库存总数
	 * 
	 * @param shopStockItem
	 * @return
	 */
	public ShopStockItem findProductStockNum(ShopStockItem shopStockItem) {
		List<ShopStockItem> stockItemList = shopStockItemDao.findProductStockNum(shopStockItem);
		return stockItemList.isEmpty() ? null : stockItemList.get(0);
	}

	public void deleteByProductId(ShopStockItem shopStockItem) {
		// TODO Auto-generated method stub
		shopStockItemDao.deleteByProductId(shopStockItem);
	}

	/**
	 * 库存数据导出excel
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void exportExcel(String exportPath) throws FileNotFoundException, IOException {
		//查询当前机构会员数据
		String userId = UserUtils.getUser().getOffice().getId();
		ShopStockItem parm = new ShopStockItem();
		parm.setOfficeId(userId);
		List<ShopStockItem> list = this.findList(parm);
		//设置表头
		List<String> headerList = Lists.newArrayList();
		headerList.add("仓库名称");
		headerList.add("商品类型");
		headerList.add("商品名称");
		headerList.add("条码");
		headerList.add("库存量");
		headerList.add("库存预警数");
		headerList.add("更新日期");
		ExportExcel ee = new ExportExcel("商品库存", headerList);
		//设置表体
		for (ShopStockItem shopStockItem : list) {
			Row row = ee.addRow();
			ee.addCell(row, 0,shopStockItem.getStockName());
			ee.addCell(row, 1,shopStockItem.getProductTypeName());
			ee.addCell(row, 2,shopStockItem.getProductName());
			ee.addCell(row, 3,shopStockItem.getProductNo());
			ee.addCell(row, 4,shopStockItem.getStockNum());
			ee.addCell(row, 5,shopStockItem.getWarnStock());
			ee.addCell(row, 6,shopStockItem.getUpdateDate());
		}
		ee.writeFile(exportPath);
		ee.dispose();
	}

}