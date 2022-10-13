package mjia.decorate.service;

import mjia.decorate.entity.UserVo;
import mjia.decorate.entity.WxUserVo;

public interface AuthService {
    boolean checkUser(UserVo userVo);
    WxUserVo queryWxUser(WxUserVo wxUserVo);
    Integer createWxUser(WxUserVo wxUserVo);
}
