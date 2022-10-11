package mjia.decorate.Interceptor;

import mjia.decorate.entity.BaseResponse;
import mjia.decorate.utils.JwtUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "mjia.decorate.controller.manage")
public class AuthResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String url = request.getURI().getPath();
        if (!"/manage/login/loginUser".equals(url)) {
            Map info = new HashMap();
            info.put("userName", "authUser");
            String token = JwtUtil.sign("9999", info);
            response.getHeaders().add("material-token", token);
        }
        return body;
    }
}
