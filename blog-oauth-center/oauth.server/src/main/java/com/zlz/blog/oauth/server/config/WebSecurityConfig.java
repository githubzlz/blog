package com.zlz.blog.oauth.server.config;

import com.zlz.blog.oauth.server.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/18 11:41
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源不拦截
        web.ignoring().antMatchers("/js/**","/css/**","/favicon-20190918085337900.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                //设置登陆地址为gateway代理的地址
                .loginPage("/oauth-center/auth/login")
                .loginProcessingUrl("/oauth-center/oauth/form")
//                .and()
//                .requestCache()
//                .requestCache(new MyRequestCache())
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login","/token/logout","/getPublicKey", "/oauth/authorize").permitAll()
                .anyRequest().authenticated()
                .and()
                //允许iframe调用
                .headers().frameOptions().disable();
    }

}

