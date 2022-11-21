package mjia.decorate.entity.openapi;

import lombok.Data;

@Data
public class WeChatSession {
    private String errcode;
    private String errmsg;

    private String openid;
    private String session_key;
    private String unionid;
}
