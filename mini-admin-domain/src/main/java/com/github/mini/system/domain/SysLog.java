package com.github.mini.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统操作日志表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 系统用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 所属系统： Mini管理系统, 
     */
    private String sysType;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法描述
     */
    private String methodRemark;

    /**
     * 请求地址
     */
    private String reqUrl;

    /**
     * 操作请求参数
     */
    private String optReqParam;

    /**
     * 操作响应结果
     */
    private String optResInfo;

    /**
     * 创建时间
     */
    private Date createdTime;


}
