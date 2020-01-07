package com.xl.auth.service;

import com.xl.auth.common.BaseUser;
import com.xl.auth.common.BaseUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserDetailServiceImpl
 * @Description:
 * @Author xr
 * @Date 2019/12/23
 * @Version V1.0
 **/
@Component
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BaseUser baseUser = new BaseUser();
        baseUser.setUsername("aa");
        baseUser.setPassword("123456");
        baseUser.setActive(1);
        List<GrantedAuthority> authorities = new ArrayList();
        GrantedAuthority authority = new SimpleGrantedAuthority("roleCode");
        authorities.add(authority);
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user =  new org.springframework.security.core.userdetails.User(baseUser.getUsername(),
                baseUser.getPassword(), isActive(baseUser.getActive()), true, true, true, authorities);
        return user;
    }

    private boolean isActive(int active){
        return active == 1 ? true : false;
    }
}
