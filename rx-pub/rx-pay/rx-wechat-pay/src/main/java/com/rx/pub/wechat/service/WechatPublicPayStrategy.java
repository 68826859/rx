package com.rx.pub.wechat.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.rx.pub.pay.enm.PayType;
import com.rx.pub.pay.servface.PayStrategy;

/**
 * 微信公众号支付
 */
public class WechatPublicPayStrategy implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(WechatPublicPayStrategy.class);

    //private static String appid = "wxc213a78444e35649";

    //private static String secret = "dac10a87a648f61a284921ef54d15832";

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
//
//        //获取app标识
//        String orderCode = (String) params.get("orderCode");
//        AppAccounts appAccounts = AppAccountUtil.getAccount(orderCode);
//
//        //BizType
//        Integer bizType = (Integer)params.get("bizType");
//        BizType bizTypeE = null;
//        if(bizType != null) {
//        	bizTypeE = EnumUtil.valueOf(BizType.class, bizType, null);
//        }
//        
//        
//        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//        String entity = genProductArgs(params, appAccounts,bizTypeE);
//        byte[] buf = Util.httpPost(url, entity);
//        String content = new String(buf);
//        if (logger.isDebugEnabled()) {
//            logger.debug("wechat content:{}", content);
//        }
//        Map<String, String> xml = XmlUtils.decodeXml(content);
//        if (logger.isDebugEnabled()) {
//            logger.debug("wechat xml:{}", xml.toString());
//        }
//        if (StringUtils.isEmpty(xml.get("prepay_id"))) {
//            return null;
//        }
//        List<NameValuePair> toRet = genPayReq(xml.get("prepay_id"), appAccounts,bizTypeE);
        Map<String, Object> map = new HashMap<>();
//        for (NameValuePair var : toRet) {
//            if ("package".equals(var.getName())) {
//
//                map.put("package", var.getValue());
//            } else {
//                map.put(var.getName(), var.getValue());
//            }
//        }
        return JSONObject.toJSONString(map);
    }


//    private String genProductArgs(Map<String, Object> params, AppAccounts appAccounts,BizType bizType) {
//        //String appid = XxlConfClient.get("frontend." + appAccounts.name().toLowerCase() + ".wechat.public.appid", null);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        StringBuffer xml = new StringBuffer();
//        try {
//            String nonceStr = XmlUtils.genNonceStr();
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<>();
//            packageParams.add(new BasicNameValuePair("appid", PublicChatAccounts.getPublicChatAccount(appAccounts,bizType).getAppid()));
//            String orderCode = (String) params.get("orderCode");
//            if (StringUtils.isNotBlank(orderCode)) {
//                packageParams.add(new BasicNameValuePair("body", "商品订单编号：" + orderCode));
//            } else {
//                packageParams.add(new BasicNameValuePair("body", "商品订单支付"));
//            }
//            packageParams.add(new BasicNameValuePair("mch_id", appAccounts.getMchId()));
//            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//            packageParams.add(new BasicNameValuePair("notify_url", PropertiesUtil.getValue("wechat.pay.notify.url") + "open/payNotify/wechatNotify"));
//            packageParams.add(new BasicNameValuePair("out_trade_no", (String) params.get("payCode")));
//
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            if (request != null) {
//                packageParams.add(new BasicNameValuePair("spbill_create_ip", HttpUtils.getIpAddr(request)));
//            } else {
//                packageParams.add(new BasicNameValuePair("spbill_create_ip", PropertiesUtil.getValue("pay.request.wechart.spbill_create_ip")));
//            }
//            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(((BigDecimal) params.get("toPay")).longValue())));
//            packageParams.add(new BasicNameValuePair("trade_type", "JSAPI"));
//            packageParams.add(new BasicNameValuePair("openid", String.valueOf(params.get("openId"))));
//            packageParams.add(new BasicNameValuePair("time_start", simpleDateFormat.format(new Date())));//有效期设置
//            Calendar expireCalendar = Calendar.getInstance();
//            expireCalendar.add(Calendar.MINUTE, Integer.parseInt(PropertiesUtil.getValue("pay.request.wechart.time_expire")));
//            packageParams.add(new BasicNameValuePair("time_expire", simpleDateFormat.format(expireCalendar.getTime())));
//            String sign = XmlUtils.genPackageSign(packageParams, appAccounts);
//            packageParams.add(new BasicNameValuePair("sign", sign));
//
//
//            String xmlstring = XmlUtils.toXml(packageParams);
//            return new String(xmlstring.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//        } catch (Exception e) {
//            logger.error("genProductArgs error, ex:{} " + e.getMessage(), e);
//        }
//        return null;
//    }


//    private List<NameValuePair> genPayReq(String prepayId, AppAccounts appAccounts,BizType bizType) {
//        //String appid = XxlConfClient.get("frontend." + appAccounts.name().toLowerCase() + ".wechat.public.appid", null);
//        List<NameValuePair> signParams = new LinkedList<>();
//        signParams.add(new BasicNameValuePair("appId",PublicChatAccounts.getPublicChatAccount(appAccounts,bizType).getAppid()));
//        signParams.add(new BasicNameValuePair("nonceStr", XmlUtils.genNonceStr()));
//        signParams.add(new BasicNameValuePair("package", "prepay_id=" + prepayId));
//        signParams.add(new BasicNameValuePair("signType","MD5"));
//        signParams.add(new BasicNameValuePair("timeStamp", String.valueOf(System.currentTimeMillis() / 1000)));
//        String sign = XmlUtils.genAppSign(signParams, appAccounts);
//        signParams.add(new BasicNameValuePair("sign", sign));
//        if (logger.isDebugEnabled()) {
//            logger.debug("signParams:{}", signParams.toString());
//        }
//        return signParams;
//    }
}
