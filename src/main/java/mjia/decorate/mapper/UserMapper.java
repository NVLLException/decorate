package mjia.decorate.mapper;

import mjia.decorate.entity.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select count(*) from user where name=#{name} and password=#{password}")
    Boolean checkUser(UserVo userVo);
}
