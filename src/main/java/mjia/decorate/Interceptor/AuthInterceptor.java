package mjia.decorate.Interceptor;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${skip.validate}")
    private String skipValidate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURI();
        if ("/manage/login/loginUser".equals(url)) {
            return true;
        }
        try {
            if ("true".equals(skipValidate)) {
                return true;
            }
            String token = request.getHeader("material-token");
            boolean valid = JwtUtil.checkSign(token);
            return valid;
        } catch (Exception e) {
            log.error("鉴权失败!", e);
            e.printStackTrace();
        }
        return false;
    }
}
