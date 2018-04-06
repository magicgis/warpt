package com.war.wechat.base.company.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.war.wechat.base.api.response.BaseResponse;
import com.war.wechat.base.company.api.entity.QYDepartment;

import java.util.List;

/**
 *  
 *  ====================================================================
 *  上海聚攒软件开发有限公司
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class GetDepartmentListResponse extends BaseResponse {

    @JSONField(name = "department")
    private List<QYDepartment> departments;

    public List<QYDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(List<QYDepartment> departments) {
        this.departments = departments;
    }
}
