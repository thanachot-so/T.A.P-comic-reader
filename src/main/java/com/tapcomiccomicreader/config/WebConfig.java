package com.tapcomiccomicreader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.resource.location:./comics}")
    private String resourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = Paths.get(resourceLocation).toAbsolutePath().normalize();
        String pathUri = path.toUri().toString();

        registry.addResourceHandler("/images/**")
                .addResourceLocations(pathUri);

        System.out.println("getting resources from " + pathUri);
    }
}
