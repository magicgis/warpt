/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wym.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wym.entity.WymProductType;

/**
 * 商品类型DAO接口
 * @author swbssd
 * @version 2017-02-23
 */
@MyBatisDao
public interface WymProductTypeDao extends TreeDao<WymProductType> {
	
}