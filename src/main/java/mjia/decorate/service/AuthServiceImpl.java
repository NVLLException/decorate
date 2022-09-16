package mjia.decorate.service;

import mjia.decorate.entity.UserVo;
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
}
