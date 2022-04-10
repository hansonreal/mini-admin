package com.github.mini.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysPermission implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名字
     */
    private String componentName;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    private Integer menuType;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 权限策略1显示2禁用
     */
    private String permsType;

    /**
     * 菜单排序
     */
    private Double sortNo;

    /**
     * 聚合子路由: 1是0否
     */
    private Byte alwaysShow;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    private Byte isRoute;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    private Byte isLeaf;

    /**
     * 是否缓存该页面:    1:是   0:不是
     */
    private Byte keepAlive;

    /**
     * 是否隐藏路由: 0否,1是
     */
    private Byte hidden;

    /**
     * 是否隐藏tab: 0否,1是
     */
    private Byte hideTab;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除状态 0正常 1已删除
     */
    private Integer delFlag;

    /**
     * 按钮权限状态(0无效1有效)
     */
    private String status;

    /**
     * 外链菜单打开方式 0/内部打开 1/外部打开
     */
    private Byte internalOrExternal;


}
