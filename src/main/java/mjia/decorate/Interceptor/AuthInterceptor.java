package mjia.decorate.Interceptor;

import mjia.decorate.utils.JwtUtil;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURI();
        if ("/manage/login/loginUser".equals(url)) {
            return true;
        }
        try {
            String token = request.getHeader("material-token");
            boolean valid = JwtUtil.checkSign(token);
            return valid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
