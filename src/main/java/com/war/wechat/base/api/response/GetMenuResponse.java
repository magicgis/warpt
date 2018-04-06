package com.war.wechat.base.api.response;

import com.war.wechat.base.api.entity.Menu;

/**
 * @author peiyu
 */
public class GetMenuResponse extends BaseResponse {

    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
