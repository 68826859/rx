package com.rx.pub.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.wechat.service.PayWechatUserService;

@RestController
@RequestMapping("/wechat")
@ExtClass(extend = SpringProvider.class, alternateClassName = "FileController")
public class WeChatController {
	@Autowired
	private PayWechatUserService payWechatUserService;
	

//    @RxPermission
//    @RequestMapping(value = {"authentication"})
//    @ResponseBody
//    public DataResult authentication(HttpServletRequest request,
//                                 @RequestParam(value = "accessToken", required = true) String accessToken,
//                                 @RequestParam(value = "openID", required = true) String openID,
//                                 @RequestParam(value = "accountCode", required = true) String accountCode,
//                                 @RequestParam(value = "expiryMinutes", required = true) Integer expiryMinutes) {
//        try {
//            if (StringUtil.isNull(accessToken) || StringUtil.isNull(openID)) {
//                throw new IllegalArgumentException("非法请求");
//            }
//            String opd = Base64.getBase64(openID, null);
//            PayWechatUserDto baseCustomWechat = payWechatUserService.getByOpenid(openID, accountCode);
//            PayWechatUserDto baseCustomWechat1 = payWechatUserService.getUserInfo(request, accessToken, openID);
//            if (baseCustomWechat == null) {//微信用户还不是系统用户,为用户注册
//                baseCustomWechat = baseCustomWechat1;
//                if (null == baseCustomWechat) {
//                    throw new IllegalArgumentException("无效token！");
//                }
//                baseCustomWechat.setId(StringUtil.getUUIDPure());
//                baseCustomWechat.setOpenid(openID);
//                baseCustomWechat.setOpd(opd);
//                payWechatUserService.insert(baseCustomWechat);
//            }
//            if (baseCustomWechat1 != null) {
//                baseCustomWechat.setNickname(baseCustomWechat1.getNickname());
//                baseCustomWechat.setHeadimgurl(baseCustomWechat1.getHeadimgurl());
//            }
//            Map<String, Object> retMap = new HashMap<String, Object>();
//
//            if (baseCustomWechat.getBindstatus() == 1) {//已绑定
//                baseCustomWechat.setOpd(opd);
//                payWechatUserService.insert(baseCustomWechat);
//                BaseCustom baseCustom = customService.wechatLogin(request, opd);
//                if (baseCustom == null) {
//                    throw new IllegalArgumentException("用户信息异常！");
//                }
//                SecurityUtils.putUserToSession(request, baseCustom);
//                SessionInfo currentSessionInfo = SecurityUtils.getCurrentSessionInfo();
//                String string = JSON.toJSONString(currentSessionInfo);
//                String jwtString = JWTTokenUtil.createJWT(string, expiryMinutes, JWTTokenKeyUtil.getKey());
//                retMap.put("token", jwtString);
//                retMap.put("opd", opd);
//                retMap.put("sign", 1);
//                return Result.successResult().setObj(retMap);
//            } else {
//                if (baseCustomWechat.getBindstatus() == 0) {//未绑定
//                    baseCustomWechat.setOpd(opd);
//                    customWechatService.save(baseCustomWechat);
//                    retMap.put("opd", opd);
//                    retMap.put("sign", 1);
//                    return Result.errorResult().setObj(retMap);
//                } else {//已解绑
//                    baseCustomWechat.setOpd(opd);
//                    customWechatService.save(baseCustomWechat);
//                    retMap.put("opd", opd);
//                    retMap.put("sign", 2);
//                    return Result.errorResult().setObj(retMap);
//                }
//            }
//        } catch (IllegalArgumentException i) {
//            return Result.errorResult().setMsg(i.getMessage());
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//            return Result.errorResult().setMsg(ex.getMessage());
//        }
//    }
}
