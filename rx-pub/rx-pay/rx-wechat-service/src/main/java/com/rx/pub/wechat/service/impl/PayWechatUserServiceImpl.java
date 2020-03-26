package com.rx.pub.wechat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.base.utils.StringUtil;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.pub.wechat.mapper.PayWechatUserMapper;
import com.rx.pub.wechat.model.dto.PayWechatUserDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;
import com.rx.pub.wechat.model.po.PayWechatUserPo;
import com.rx.pub.wechat.model.seo.PayWechatUserSearchDto;
import com.rx.pub.wechat.model.vo.BaseWechatParams;
import com.rx.pub.wechat.service.PayWechatAccountPubService;
import com.rx.pub.wechat.service.PayWechatAccountService;
import com.rx.pub.wechat.service.PayWechatUserService;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信用户信息(PayWechatUser)Service实现类
 *
 * @author klf
 * @since 2020-01-09 20:09:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayWechatUserServiceImpl extends MybatisBaseService<PayWechatUserPo> implements PayWechatUserService {

	private static final Logger log = LoggerFactory.getLogger(PayWechatUserServiceImpl.class);

	@Autowired
	private PayWechatUserMapper payWechatUserMapper;
	@Autowired
	private PayWechatAccountService payWechatAccountService;
	@Autowired
	private PayWechatAccountPubService payWechatAccountPubService;
	

	@Override
	public Pager<PayWechatUserDto> searchPage(PayWechatUserSearchDto dto) {
		return this.getPageExcuter().selectByPage(new PageExcute<PayWechatUserDto>() {
			@Override
			public List<PayWechatUserDto> excute() {
				return payWechatUserMapper.searchList(dto);
			}
		}, this.getPagerProvider().getPager(PayWechatUserDto.class));
	}

	//获取微信用户的userinfo
    public PayWechatUserDto getUserInfo(HttpServletRequest request, String accessToken, String openID) {
    	
//		PayWechatAccountPubPo payWechatAccount = payWechatAccountPubService.getAccountPubByCode(accountCode, bizType);
//		WxMpService wxService = payWechatAccountPubService.getMpService(payWechatAccount.getId());
//		WxMpUserService userService = wxService.getUserService();
//		WxMpUser mpUser = userService.userInfo(openID);
//        String resultStr = JSON.toJSONString(mpUser);
    	
        String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openID;
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(URI.create(uri));
        HttpResponse response;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }
                PayWechatUserDto wechatUser = new PayWechatUserDto();
                JSONObject object = JSONObject.parseObject(sb.toString().trim());
                //通过微信API接口获取返回数据
                String nickname = object.getString("nickname");
                Integer sex = object.getInteger("sex");
                String headimgurl = object.getString("headimgurl");
                String languages = object.getString("language");
                String city = object.getString("city");
                String province = object.getString("province");
                String country = object.getString("country");
                String unionid = object.getString("unionid");

                wechatUser.setNickname(nickname);
                wechatUser.setSex(sex);
                wechatUser.setHeadimgurl(headimgurl);
                wechatUser.setLanguages(languages);
                wechatUser.setCity(city);
                wechatUser.setProvince(province);
                wechatUser.setCountry(country);
                wechatUser.setUnionid(unionid);
                return wechatUser;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    /**
     * 获取微信用户的accesstoken openid
     *
     * @param request
     * @param code
     * @return
     */
    public BaseWechatParams getAccessTokenAndOpenId(HttpServletRequest request, String code, String accountPubId) {
    	PayWechatAccountPubPo account = payWechatAccountPubService.getAccountPubById(accountPubId);
    	if(account == null){
    		return null;
    	}
    	
        String appid = account.getAppId();
        String secret = account.getSecret();
        log.info("appid:" + appid + "----------secret:" + secret);
        if (StringUtil.isNull(appid) || StringUtil.isNull(secret)) {
            return null;
        }
        String uri = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(URI.create(uri));
        HttpResponse response;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }
                log.info("wechat response info:" + sb.toString().trim());
                BaseWechatParams baseWechatParams = new BaseWechatParams();
                JSONObject object = JSONObject.parseObject(sb.toString().trim());
                if (object.containsKey("errcode")) {
                    return null;
                }
                //通过微信API接口获取返回数据
                String accessToken = object.getString("access_token");
                Integer expiresIn = object.getInteger("expires_in");
                String refreshToken = object.getString("refreshToken");
                String openid = object.getString("openid");
                String scope = object.getString("scope");
                String unionid = object.getString("unionid");
                if (StringUtil.isNull(accessToken) || StringUtil.isNull(openid)) {
                    return null;
                }

                baseWechatParams.setAccessToken(accessToken);
                baseWechatParams.setExpiresIn(expiresIn);
                baseWechatParams.setOpenid(openid);
                baseWechatParams.setRefreshToken(refreshToken);
                baseWechatParams.setScope(scope);
                baseWechatParams.setUnionid(unionid);
                return baseWechatParams;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public PayWechatUserDto getByOpenid(String openid, String accountCode) {
		PayWechatUserDto user = payWechatUserMapper.getByOpenid(openid, accountCode);
		return user;
	}

	@Override
	public PayWechatUserDto getByUnionId(String unionId, String accountCode) {
		PayWechatUserDto user = payWechatUserMapper.getByUnionId(unionId, accountCode);
		return user;
	}

	@Override
	public PayWechatUserDto getByUserId(String userId, String accountCode) {
		PayWechatUserDto user = payWechatUserMapper.getByUserId(userId, accountCode);
		return user;
	}
}