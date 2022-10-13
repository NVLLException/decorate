package mjia.decorate.service;

import mjia.decorate.entity.UserVo;
import mjia.decorate.entity.WxUserVo;
import mjia.decorate.mapper.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthServiceImpl implements AuthService{

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean checkUser(UserVo userVo) {
        return userMapper.checkUser(userVo);
    }

    @Override
    public WxUserVo queryWxUser(WxUserVo wxUserVo) {
        return userMapper.queryWxUser(wxUserVo);
    }

    @Override
    public Integer createWxUser(WxUserVo wxUserVo) {
        return 1;
    }


}
