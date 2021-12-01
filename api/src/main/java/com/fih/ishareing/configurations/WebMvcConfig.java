package com.fih.ishareing.configurations;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.common.ApiConstants.FILE_DIR;
import com.fih.ishareing.configurations.interceptor.ApiSignatureInterceptor;
import com.fih.ishareing.configurations.interceptor.LoggerInterceptor;
import com.fih.ishareing.utils.signature.ApiSignature;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

@Configuration
@PropertySources({@PropertySource("classpath:application.properties"),
        @PropertySource(value = "file:./application-prod.properties", ignoreResourceNotFound = true)})
public class WebMvcConfig implements WebMvcConfigurer {

    private final String FILE_PREFIX = "file:///";

    @Autowired
    private Environment env;

    @Autowired
    private ApiSignatureInterceptor apiSignatureInterceptor;

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/").addResourceLocations("/**");

        String rootDir = env.getProperty(ApiConstants.ENV_FILE_DIR_LOCATION);
        for (FILE_DIR dir : FILE_DIR.values()) {
            String childDir = rootDir + dir.name();
            initDir(childDir, true);
            registry.addResourceHandler("/" + dir.name() + "/**").addResourceLocations(FILE_PREFIX + childDir + "//");
        }

    }

    @Bean
    public ApiSignatureInterceptor apiSignatureInterceptor(ApiSignature
                                                                   apiSignature) {
        return new ApiSignatureInterceptor(apiSignature);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
         registry.addInterceptor(new LoggerInterceptor()).order(5);
         registry.addInterceptor(apiSignatureInterceptor).addPathPatterns("/api/**",
                 "/properties/**").excludePathPatterns("/api/v1/auth/sign");
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setMaxPageSize(65535);
        resolver.setOneIndexedParameters(false);
        String defaultPageSize = String.valueOf(env.getProperty("default.page.size"));
        int pageSize = 50;
        if (!StringUtils.isEmpty(defaultPageSize)) {
            pageSize = Integer.valueOf(defaultPageSize);
        }
        resolver.setFallbackPageable(PageRequest.of(0, pageSize));
        argumentResolvers.add(resolver);
        // super.addArgumentResolvers(argumentResolvers);
    }

    private void initDir(String dir, boolean devMode) {
        if (devMode) {
            if (!new File(dir).exists()) {
                new File(dir).mkdirs();
            }
        }
    }
}