package me.antoniocaccamo.player.rx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.player.rx.ApplicationUI;

/**
 * @author antoniocaccamo on 07/04/2020
 */

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableSpringConfigured
public class PlayerConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.resource-prefix-path}")
    private String resourcePrefixPath;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("registry.addResourceHandler(\"{}\").addResourceLocations(\"resources\") ",resourcePrefixPath);
//        registry.addResourceHandler(resourcePrefixPath).addResourceLocations("resources");
//    }

    @Bean @Lazy
    public ApplicationUI mainUI(){
        return new ApplicationUI();
    }

}
