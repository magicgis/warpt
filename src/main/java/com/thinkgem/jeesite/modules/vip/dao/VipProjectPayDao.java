/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vip.entity.VipProjectPay;

/**
 * 会员项目充值DAO接口
 * @author swbssd
 * @version 2018-01-14
 */
@MyBatisDao
public interface VipProjectPayDao extends CrudDao<VipProjectPay> {
	
}