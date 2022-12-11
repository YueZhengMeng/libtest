package com.pwb.libtest.securityconfig;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //获取到登陆者的权限，然后做跳转
        if (roles.contains("ROLE_ADMIN")){
            response.sendRedirect("/admin/home");
            return;
        }else if (roles.contains("ROLE_vip0")){
            response.sendRedirect("/index");
            return;
        }else {
            response.sendRedirect("/login?error");
        }

    }
}
