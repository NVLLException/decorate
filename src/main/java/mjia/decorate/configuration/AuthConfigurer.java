package mjia.decorate.configuration;

import mjia.decorate.Interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Deprecated
public class AuthConfigurer implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // registry.addInterceptor(authInterceptor).addPathPatterns("/manage/**");
    }
}
