package mjia.decorate.entity;

import lombok.Data;

@Data
public class WxUserVo {
    private String id;
    private String openId;
    private String phone;
    private String name;
    private String session_key;
}
