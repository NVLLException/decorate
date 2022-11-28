package mjia.decorate.mapper;

import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.openapi.AddCartOpenVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("insert into shopping_cart(wxUserId,materialId,count) values(#{wxUserId}, #{materialId}, #{count})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer addCart(AddCartOpenVo addCartOpenVo);

    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @Insert("update shopping_cart set count=#{count} where wxUserId = #{wxUserId} and materialId= #{materialId} and status=0")
    Integer updateCart(AddCartOpenVo addCartOpenVo);

    @Select("select * from shopping_cart where wxUserId=#{wxUserId} and materialId=#{materialId} and status=0")
    AddCartOpenVo queryCart(AddCartOpenVo addCartOpenVo);

    @Select("select * from shopping_cart where wxUserId=#{wxUserId} and status=0 and count>0")
    List<AddCartOpenVo> queryCartList(AddCartOpenVo addCartOpenVo);
}
