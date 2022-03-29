package com.github.ma.common.util;

import com.github.mini.system.domain.security.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.ma.common.constant.MiniAdminConstant.JWT_PAYLOAD_USER_KEY;

/**
 * @className: JwtUtil
 * @description: 生成token以及校验token相关方法
 * @author: HanSon.Q
 * @version: V1.0
 * @date: 2020/12/2
 */
public class JwtUtil {

    private static String createJTI() {
        return new String(Base64Util.encoder(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @return JWT
     */
    public static String generateToken(Object userInfo,
                                       PrivateKey privateKey) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JsonUtil.serialize(userInfo))
                .setId(createJTI())
                //过期时间 = 当前时间 + （设置过期时间[单位 ：s ] ）  ,但是现在token放置redis 设置过期时间无意义
                // .setExpiration(DateTime.now().plusMinutes(expire).toDate())
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return Claims
     */
    private static Claims parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static JWTPayload getInfoFromToken(String token,
                                              PublicKey publicKey) {
        Claims body = parserToken(token, publicKey);
        String json = (String) body.get(JWT_PAYLOAD_USER_KEY);
        return JsonUtil.parse(json, JWTPayload.class);
    }

    /**
     * 存放载体数据
     */
    @Data
    @NoArgsConstructor
    public static class JWTPayload {
        private Long sysUserId;       //登录用户ID
        private Long created;         //创建时间, 格式：13位时间戳
        private String cacheKey;      //redis保存的key


        public JWTPayload(JwtUserDetails jeeUserDetails) {
            this.setSysUserId(jeeUserDetails.getSysUser().getSysUserId());
            this.setCreated(System.currentTimeMillis());
            this.setCacheKey(jeeUserDetails.getCacheKey());
        }

        public Map<String, Object> toMap() {
            return new HashMap<>();
        }
    }
}
