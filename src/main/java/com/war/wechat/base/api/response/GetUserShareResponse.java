package com.war.wechat.base.api.response;

import com.war.wechat.base.api.entity.UserShare;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUserShareResponse extends BaseResponse {

    private List<UserShare> list;

    public List<UserShare> getList() {
        return list;
    }

    public void setList(List<UserShare> list) {
        this.list = list;
    }
}
