package com.pwb.libtest.service;

import com.pwb.libtest.bean.LibUser;
import com.pwb.libtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LibUser libUser=userRepository.selectUserByName(name);
        if (libUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        String role= libUser.getRole();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        if(role.equals("ADMIN"))
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + "vip0"));
        }

        return new org.springframework.security.core.userdetails.
                User(libUser.getUserName(), libUser.getPassword(), authorities);
    }
}
