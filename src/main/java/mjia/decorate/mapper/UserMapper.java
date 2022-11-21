package mjia.decorate.mapper;

import mjia.decorate.entity.UserVo;
import mjia.decorate.entity.WxUserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select count(*) from user where name=#{name} and password=#{password}")
    Boolean checkUser(UserVo userVo);

    @Select("select * from customer where openId=#{openId}")
    WxUserVo queryWxUser(WxUserVo wxUserVo);

    @Insert("insert into customer(openId) values(openId)")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertWxUser(WxUserVo wxUserVo);
}
