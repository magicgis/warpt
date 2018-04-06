package com.war.wechat.base.company.api;

import com.war.wechat.base.api.response.BaseResponse;
import com.war.wechat.base.company.api.config.QYAPIConfig;
import com.war.wechat.base.company.api.entity.QYDepartment;
import com.war.wechat.base.company.api.enums.QYResultType;
import com.war.wechat.base.company.api.response.CreateDepartmentResponse;
import com.war.wechat.base.company.api.response.GetDepartmentListResponse;
import com.war.wechat.base.util.JSONUtil;

/**
 *  
 *  ====================================================================
 *  上海聚攒软件开发有限公司
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public class QYDepartmentAPI extends QYBaseAPI {

    /**
     * 构造方法，设置apiConfig
     *
     * @param config 微信API配置对象
     */
    public QYDepartmentAPI(QYAPIConfig config) {
        super(config);
    }

    /**
     * 获取部门列表
     * @param parentId 父级部门ID。可不填，不填表示顶级部门
     * @return
     */
    public GetDepartmentListResponse getList(Integer parentId){
        GetDepartmentListResponse response;
        String url = null;
        if(parentId != null) {
            url = BASE_API_URL + "cgi-bin/department/list?access_token=#&id=" + parentId;
        }else{
            url = BASE_API_URL + "cgi-bin/department/list?access_token=#&id=";
        }
        BaseResponse r = executeGet(url);
        String resultJson = isSuccess(r.getErrcode()) ? r.getErrmsg() : r.toJsonString();
        response = JSONUtil.toBean(resultJson, GetDepartmentListResponse.class);
        return response;
    }

    /**
     * 创建部门
     * @param department 部门信息
     * @return
     */
    public CreateDepartmentResponse create(QYDepartment department) {
        CreateDepartmentResponse response;
        String url = BASE_API_URL + "cgi-bin/department/create?access_token=#";
        BaseResponse r = executePost(url, JSONUtil.toJson(department));
        String resultJson = isSuccess(r.getErrcode()) ? r.getErrmsg() : r.toJsonString();
        response = JSONUtil.toBean(resultJson, CreateDepartmentResponse.class);
        return response;
    }

    /**
     * 更新部门
     * @param department
     * @return
     */
    public QYResultType update(QYDepartment department){
        String url = BASE_API_URL + "cgi-bin/department/update?access_token=#";
        BaseResponse r = executePost(url, JSONUtil.toJson(department));
        return QYResultType.get(r.getErrcode());
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    public QYResultType delete(Integer id){
        String url = BASE_API_URL + "cgi-bin/department/delete?access_token=#&id=" + id;
        BaseResponse r = executeGet(url);
        return QYResultType.get(r.getErrcode());
    }
}
