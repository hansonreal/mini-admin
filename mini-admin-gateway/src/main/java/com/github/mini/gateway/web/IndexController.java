package com.github.mini.gateway.web;

import com.github.mini.common.ann.Anonymous;
import com.github.mini.common.constant.MiniConstant;
import com.github.mini.common.exception.MiniException;
import com.github.mini.common.properties.RsaKeyProperties;
import com.github.mini.common.result.CaptchaResult;
import com.github.mini.common.result.DataResult;
import com.github.mini.common.util.IdWorker;
import com.github.mini.common.util.RedisUtil;
import com.github.mini.common.util.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping
public class IndexController {


    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Anonymous
    @GetMapping({"/", "/index"})
    public String init() {
        return "Welcome to use Mini Admin Sys!";
    }


    /**
     * 获取公钥
     *
     * @return
     */
    @GetMapping("/rsakey")
    public DataResult getRsaPublicKey() throws Exception {
        PublicKey publicKey = rsaKeyProperties.getPublicKey();
        String publicKeyStr = RsaUtil.getPublicKeyStr(publicKey);
        return DataResult.ok(publicKeyStr);
    }

    /**
     * @return 获取图形验证码
     */
    @GetMapping("/captcha")
    public CaptchaResult captcha() throws MiniException {
        //1、利用工具 生成验证码
        String captchaVal = "";
        String imgBase64DataSrc = "";
        //2、利用Redis 存储验证码，并返回一个KEY
        String captchaToken = String.valueOf(idWorker.nextId());
        String cacheKeyImgCaptchaCode = MiniConstant.getCacheKeyImgCaptchaCode(captchaToken);
        RedisUtil.set(cacheKeyImgCaptchaCode,captchaVal,MiniConstant.CAPTCHA_CODE_CACHE_TIME.getSeconds());
        //3、构建结果返回，默认有效期为60秒
        return CaptchaResult.ok(captchaToken, imgBase64DataSrc);
    }


    @GetMapping("/hi")
    public String hello() {
        return "Welcome to use Mini Admin Sys!";
    }
}
