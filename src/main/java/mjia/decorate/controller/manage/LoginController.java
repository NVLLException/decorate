package mjia.decorate.controller.manage;

import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.UserVo;
import mjia.decorate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static mjia.decorate.enums.BizTypeEnum.*;

@RestController
@RequestMapping("/manage/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/loginUser")
    public BaseResponse loginUser(@RequestBody UserVo userVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, LOGIN_USER, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(authService.checkUser(userVo));
            }
        });
    }
}
