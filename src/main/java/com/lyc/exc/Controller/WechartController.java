package com.lyc.exc.Controller;

import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * Created by lyc94 on 2017/12/23.
 */
//@RestController rest会将 "redirect" 的地址转为json输出，所有使用@Controller
@Controller
@RequestMapping("/wechart")
@Slf4j
public class WechartController {

    @Autowired
    WxMpService wxMpService;
//    @Autowired
//    WxMpConfigStorage wxMpConfigStorage;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

//        log.error("【configuration】appId={},appSecret={}",wxMpConfigStorage.getAppId(),
//                wxMpConfigStorage.getSecret());

        //1.配置
        //2.调用方法
        //网页授权url
        String url = "http://steve.natappvip.cc/sell/wechart/userInfo";
        String result = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return "redirect:" + result;

    }

    //redirect_uri	是	授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
    //如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state")String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHART_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openId=" + openId;
    }
}
