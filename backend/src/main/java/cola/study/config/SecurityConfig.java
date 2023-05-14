package cola.study.config;

import cola.study.entity.Result;
import cola.study.service.AuthoriseService;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: SecurityConfig
 * @date 2023/5/13 17:29
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private AuthoriseService authoriseService;
    @Resource
    private SecurityIgnoreUrl securityIgnoreUrl;

    @Resource
    DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,PersistentTokenRepository repository) throws Exception {
        http
                .csrf().disable() // 关闭 csrf
                .authorizeHttpRequests()
                .requestMatchers(securityIgnoreUrl.getUrls()).permitAll() // 放行自定义 URL
                .anyRequest().authenticated()
                // 自定义登录接口
                .and()
                .formLogin()
                .loginProcessingUrl("/api/v1/auth/login")
                .successHandler(this::onAuthenticationSuccess)
                .failureHandler(this::onAuthenticationFailure)
                // 自定义退出接口
                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .logoutSuccessHandler(this::onAuthenticationSuccess)
                // remember-me
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(3600 * 24 * 7)
                // 开启 cors 跨域请求
                .and()
                .cors()
                .configurationSource(this.corsConfigurationSource())
                // 配置异常
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this::onAuthenticationFailure);
        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动时创建表
        tokenRepository.setCreateTableOnStartup(false);

        return tokenRepository;
    }

    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",cors);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return
                http
                        .getSharedObject(AuthenticationManagerBuilder.class)
                        .userDetailsService(authoriseService)
                        .and()
                        .build();
    }
    /**
     * 配置密码解析器
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        if (request.getRequestURI().endsWith("/login")){
            response.getWriter().write(JSONObject.toJSONString(Result.ok("operation success",null)));
        }else if (request.getRequestURI().endsWith("/logout")){
            response.getWriter().write(JSONObject.toJSONString(Result.ok("operation success",null)));
        }
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.getWriter().write(JSONObject.toJSONString(Result.error("operation failure",null)));
    }

}

