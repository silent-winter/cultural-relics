package com.buct.computer.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.buct.computer.common.enums.UserTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/16/21:00
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("path: {}", request.getServletPath());  // 打印访问路径
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        });
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            SaRouter.match("/**", () -> {
                if(request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
                    SaRouter.stop();
                }
            });
            //SaRouter.match("/**", () -> SaRouter.stop());  // 为了方便测试可以暂时设置为全部放行
            SaRouter.match("/", "/error", "/csrf", "/swagger-resources/**", "/**/swagger-ui.html", "/webjars/**").check(SaRouter::stop);  // swagger放行路由
            SaRouter.match("/user/register", SaRouter::stop);
            SaRouter.match("/**", "/user/login", StpUtil::checkLogin);
            SaRouter.match("/admin/**", () -> StpUtil.checkRole(UserTypeEnum.admin.getTypeName()));
        }));
    }
}