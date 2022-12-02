package mjia.decorate.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qcloud.cos.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.WxUserVo;
import mjia.decorate.entity.openapi.WeChatSession;
import mjia.decorate.service.AuthService;
import mjia.decorate.utils.HttpClient;
import mjia.decorate.utils.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static mjia.decorate.enums.BizTypeEnum.CUSTOMER_USER;

@Slf4j
@RestController
@RequestMapping("/openApi/login")
public class LoginOpenController {

    private String appId;
    private String secret;

    @Value("${wx.login.url}")
    private String loginUrl;

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpClient httpClient;

    @PostConstruct
    public void init() {
        try{
            File file = ResourceUtils.getFile("/work/pass/wxpass");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            appId = reader.readLine();
            secret = decode(reader.readLine());
        } catch (Exception e) {
            log.error("LoginOpenController init error: ", e);
        }
    }

    @RequestMapping("/loginWxUser")
    public BaseResponse loginWxUser(@RequestParam("code") String code) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, CUSTOMER_USER, new DefaultCallback() {
            @Override
            public void execute() {
                Map<String, String> param = new HashMap();
                param.put("appid", appId);
                param.put("secret", secret);
                param.put("js_code", code);
                param.put("grant_type", "authorization_code");
                log.info("param" + JSONObject.toJSONString(param));
                String sessionData = httpClient.doGet(loginUrl, param);
                Gson gson = new Gson();
                //解析从微信服务器获得的openid和session_key;
                WeChatSession weChatSession = gson.fromJson(sessionData, WeChatSession.class);
                if (StringUtils.isNotBlank(weChatSession.getErrcode())) {
                    response.setSuccess(false);
                    response.setErrorCode(weChatSession.getErrcode());
                    response.setErrorMessage(weChatSession.getErrmsg());
                    return;
                }
                String openId = Md5Utils.md5Hex(weChatSession.getOpenid());
                WxUserVo wxUserVo = new WxUserVo();
                wxUserVo.setOpenId(openId);
                WxUserVo existWxUserVo = authService.queryWxUser(wxUserVo);
                if (Objects.isNull(existWxUserVo)) {
                    Integer wxUserId = authService.createWxUser(wxUserVo);
                    response.setSuccess(true);
                    response.setData(openId);
                } else {
                    response.setSuccess(true);
                    response.setData(existWxUserVo.getOpenId());
                }
            }
        });
    }

    private String decode(String encode) throws IOException {
        String s = new String(new BASE64Decoder().decodeBuffer(encode));
        char[] b = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<b.length; i++) {
            if(i%2 == 0) {
                sb.append(b[i]);
            }
        }
        return sb.toString();
    }
}
