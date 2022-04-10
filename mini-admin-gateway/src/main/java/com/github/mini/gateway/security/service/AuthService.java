package com.github.mini.gateway.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.mini.common.exception.MiniException;
import com.github.mini.common.properties.RsaKeyProperties;
import com.github.mini.common.util.RsaUtil;
import com.github.mini.system.domain.SysRole;
import com.github.mini.system.domain.SysUser;
import com.github.mini.system.domain.SysUserRole;
import com.github.mini.system.domain.security.JwtUserDetails;
import com.github.mini.system.service.ISysRolePermissionService;
import com.github.mini.system.service.ISysRoleService;
import com.github.mini.system.service.ISysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    public String auth(String username, String password) throws Exception {
        PrivateKey privateKey = rsaKeyProperties.getPrivateKey();

        log.info("前台输入密码为:{}", password);
        String decrypt = RsaUtil.decrypt(password, privateKey);
        log.info("私钥解密得到密码:{}", decrypt);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, decrypt);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new MiniException("用户名/密码错误！");
        } catch (AuthenticationException e) {
            log.error("AuthenticationException:", e);
            throw new MiniException("认证服务出现异常， 请重试或联系系统管理员！");
        }
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        //验证通过后 再查询用户角色和权限信息集合
        SysUser sysUser = jwtUserDetails.getSysUser();

        //根据用户去查询用户是否为管理员或者是否有菜单权限


        return "";
    }

    public List<SimpleGrantedAuthority> getUserAuthority(SysUser sysUser) {

        //用户拥有的角色集合  需要以ROLE_ 开头,  用户拥有的权限集合


        // 获取用户对应额全部角色信息
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(sysUserRoleQueryWrapper);
        List<String> roleIdList = new ArrayList<>();
        sysUserRoleList.forEach(sysUserRole -> roleIdList.add(sysUserRole.getRoleId()));


        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.in("id", roleIdList);
        List<SysRole> sysRoleList = sysRoleService.list(sysRoleQueryWrapper);
        List<String> roleCodeList = new ArrayList<>();
        sysRoleList.forEach(sysRole -> roleCodeList.add(sysRole.getRoleCode()));


        // 获取全部权限信息






        List<String> roleList = sysRoleService.findListByUser(sysUser.getSysUserId());
        List<String> entList = sysRoleEntRelaService.selectEntIdsByUserId(sysUser.getSysUserId(), sysUser.getIsAdmin(), sysUser.getSysType());

        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        roleList.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
        entList.forEach(ent -> grantedAuthorities.add(new SimpleGrantedAuthority(ent)));
        return grantedAuthorities;
    }
}
