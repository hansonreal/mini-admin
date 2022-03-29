package com.github.mini.system.domain;

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
 * 系统配置表
 */
@Data
@Accessors(chain = true)
@TableName("t_sys_config")
@EqualsAndHashCode(callSuper = false)
public class SysConfig implements Serializable {

    //gw
    public static LambdaQueryWrapper<SysConfig> gw() {
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID = 1L;

    /**
     * 配置KEY
     */
    @TableId(value = "config_key", type = IdType.INPUT)
    private String configKey;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 描述信息
     */
    private String configDesc;

    /**
     * 分组key
     */
    private String groupKey;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 配置内容项
     */
    private String configVal;

    /**
     * 类型: text-输入框, textarea-多行文本, uploadImg-上传图片, switch-开关
     */
    private String type;

    /**
     * 显示顺序
     */
    private Long sortNum;

    /**
     * 更新时间
     */
    private Date updatedTime;


}
