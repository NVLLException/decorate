package mjia.decorate.controller.openapi;

import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.WxUserVo;
import mjia.decorate.service.AuthService;
import mjia.decorate.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import static mjia.decorate.enums.BizTypeEnum.CUSTOMER_USER;

@RequestMapping("/openApi/login")
public class LoginOpenController {
    @Autowired
    private AuthService authService;

    @RequestMapping("/loginWxUser")
    public BaseResponse loginWxUser(@RequestBody WxUserVo wxUserVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, CUSTOMER_USER, new DefaultCallback() {
            @Override
            public void execute() {
                Map result = new HashMap();
                WxUserVo dbWxUser = authService.queryWxUser(wxUserVo);
                if (dbWxUser == null) {
                    Integer customerId = authService.createWxUser(wxUserVo);
                }
/*                if (loginSuccess) {
                    Map info = new HashMap();
                    info.put("userName", userVo.getName());
                    String token = JwtUtil.sign("9999", info);
                    result.put("success", true);
                    result.put("token", token);
                } else {
                    result.put("success", true);
                }*/
                response.setData(result);
            }
        });
    }
}
