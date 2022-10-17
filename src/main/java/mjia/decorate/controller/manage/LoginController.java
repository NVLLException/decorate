package mjia.decorate.controller.manage;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.UserVo;
import mjia.decorate.entity.WxUserVo;
import mjia.decorate.service.AuthService;
import mjia.decorate.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static mjia.decorate.enums.BizTypeEnum.*;

@Slf4j
@RestController
@RequestMapping("/manage/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/loginUser")
    public BaseResponse loginUser(@RequestBody UserVo userVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, LOGIN_USER, new DefaultCallback() {
            @Override
            public void execute() {
                Map result = new HashMap();
                boolean loginSuccess = authService.checkUser(userVo);
                if (loginSuccess) {
                    Map info = new HashMap();
                    info.put("userName", userVo.getName());
                    String token = JwtUtil.sign("9999", info);
                    result.put("success", true);
                    result.put("token", token);
                } else {
                    result.put("success", false);
                }
                response.setData(result);
            }
        });
    }
}
