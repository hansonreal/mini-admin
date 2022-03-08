package com.github.ma.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_log")
public class SysLog implements Serializable {

    public static LambdaQueryWrapper<SysLog> gw() {
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "sys_log_id", type = IdType.AUTO)
    private Integer sysLogId;

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
     * 所属系统： MGR-运营平台, MCH-商户中心
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
    private Date createdAt;


}
