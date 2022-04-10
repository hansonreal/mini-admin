package com.github.mini.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统用户认证表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserAuth implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "auth_id", type = IdType.AUTO)
    private Long authId;

    /**
     * 系统用户表标识
     */
    private Long userId;

    /**
     * 登录类型  1-登录账号 2-手机号 3-邮箱  4-微信  5-QQ 6-支付宝 7-微博
     */
    private Byte identityType;

    /**
     * 认证标识 ( 用户名 | open_id )
     */
    private String identifier;

    /**
     * 密码凭证
     */
    private String credential;

    /**
     * salt
     */
    private String salt;


}
