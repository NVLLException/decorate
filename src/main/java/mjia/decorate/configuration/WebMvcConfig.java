package mjia.decorate.configuration;

import mjia.decorate.Interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations(materialFilePath, categoryFilePath, groupFilePath);

        registry.addResourceHandler("/page/**")
                .addResourceLocations(resourcesLocations.split(","));
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/manage/**");
    }
}
