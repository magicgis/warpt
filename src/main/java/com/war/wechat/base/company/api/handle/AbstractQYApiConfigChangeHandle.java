package com.war.wechat.base.company.api.handle;

import com.war.wechat.base.company.api.config.QYConfigChangeNotice;
import com.war.wechat.base.handle.ApiConfigChangeHandle;
import com.war.wechat.base.util.BeanUtil;

import java.util.Observable;

/**
 *  
 *  ====================================================================
 *  上海聚攒软件开发有限公司
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public abstract class AbstractQYApiConfigChangeHandle implements ApiConfigChangeHandle{

    public void update(Observable o, Object arg){
        if(BeanUtil.nonNull(arg) && arg instanceof QYConfigChangeNotice){
            configChange((QYConfigChangeNotice) arg);
        }
    }

    /**
     * 子类实现，当配置变化时触发该方法
     * @param notice
     */
    public abstract void configChange(QYConfigChangeNotice notice);
}
