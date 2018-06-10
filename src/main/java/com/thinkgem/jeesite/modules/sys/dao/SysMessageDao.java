/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysMessage;

/**
 * 短信平台DAO接口
 * @author swbssd
 * @version 2018-06-10
 */
@MyBatisDao
public interface SysMessageDao extends CrudDao<SysMessage> {
	
}