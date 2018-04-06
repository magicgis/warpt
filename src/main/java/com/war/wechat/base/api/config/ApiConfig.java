package com.war.wechat.base.api.config;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpStatus;
//
//import com.war.code.systemlog.api.Logger;
//import com.war.code.systemlog.api.LoggerFactory;
import com.war.wechat.base.api.response.GetJsApiTicketResponse;
import com.war.wechat.base.api.response.GetTokenResponse;
import com.war.wechat.base.util.JSONUtil;
import com.war.wechat.base.util.NetWorkCenter;

/**
 * API配置类，项目中请保证其为单例
 *
 * @author peiyu
 * @since 1.2
 */
public final class ApiConfig {

//    private static final Logger        LOG        = LoggerFactory.getLogger(ApiConfig.class);
    
    public final         AtomicBoolean refreshing = new AtomicBoolean(false);
    private final String  appid;
    private final String  secret;
    private       String  accessToken;
    private       String  jsApiTicket;
    private       boolean enableJsApi;

    /**
     * 构造方法一，实现同时获取access_token。不启用jsApi
     *
     * @param appid  公众号appid
     * @param secret 公众号secret
     */
    public ApiConfig(String appid, String secret) {
        this(appid, secret, false);
    }

    /**
     * 构造方法二，实现同时获取access_token，启用jsApi
     *
     * @param appid  公众号appid
     * @param secret 公众号secret
     */
    public ApiConfig(String appid, String secret, boolean enableJsApi) {
        this.appid = appid;
        this.secret = secret;
        this.enableJsApi = enableJsApi;
        init();
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public boolean isEnableJsApi() {
        return enableJsApi;
    }

    public void setEnableJsApi(boolean enableJsApi) {
        this.enableJsApi = enableJsApi;
    }

    /**
     * 初始化微信配置，即第一次获取access_token
     */
    public void init() {
//        LOG.debug("开始第一次初始化access_token........");
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+this.appid+"&corpsecret="+this.secret;
        //String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret=" + this.secret;
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                if (HttpStatus.SC_OK == resultCode) {
                    GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
//                    LOG.debug("获取access_token:" + response.getAccessToken());
                    ApiConfig.this.accessToken = response.getAccessToken();
                }
            }
        });

        if (enableJsApi) {
            String url2 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
            NetWorkCenter.get(url2, null, new NetWorkCenter.ResponseCallback() {
                @Override
                public void onResponse(int resultCode, String resultJson) {
                    if (HttpStatus.SC_OK == resultCode) {
                        GetJsApiTicketResponse response = JSONUtil.toBean(resultJson, GetJsApiTicketResponse.class);
//                        LOG.debug("获取jsapi_ticket:" + response.getTicket());
                        ApiConfig.this.jsApiTicket = response.getTicket();
                    }
                }
            });
        }
    }
}
