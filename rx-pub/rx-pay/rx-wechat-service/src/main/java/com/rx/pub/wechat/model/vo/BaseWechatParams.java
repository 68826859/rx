package com.rx.pub.wechat.model.vo;

/**
 * 获取微信参数 accesstoken openid
 */
public class BaseWechatParams {

   	/**
	 *accessToken
     */
    private String accessToken;
   	/**
	 *失效时间
     */
    private Integer expiresIn;
   	/**
	 *刷新token
     */
    private String refreshToken;
   	/**
	 *openId
     */
    private String openid;
   	/**
	 * scope
     */
    private String scope;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     *unionid
     */
    private String unionid;

    /**
     * 错误信息
     */
    private String errmsg;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
