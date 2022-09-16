package mjia.decorate.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${material.file.path}")
    private String materialFilePath;

    @Value("${category.file.path}")
    private String categoryFilePath;

    @Value("${group.file.path}")
    private String groupFilePath;

    @Value("${resources.static.locations}")
    private String resourcesLocations;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations(materialFilePath, categoryFilePath, groupFilePath);

        registry.addResourceHandler("/page/**")
                .addResourceLocations(resourcesLocations.split(","));
    }
}
