package com.cobblers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置web拦截
 **/
@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册自定义拦截器，添加拦截路径和排除拦截路径
//        //registry.addInterceptor(new ManagerInterceptor()).addPathPatterns("/manager/**");
//    }

    /**
     * 配置静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
