package mjia.decorate.mapper;

import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.openapi.AddCartOpenVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("insert into shopping_cart(wxUserId,groupId,categoryId,materialId,count) values(#{wxUserId}, #{groupId}, #{categoryId}, #{materialId}, #{count})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer addCart(AddCartOpenVo addCartOpenVo);

    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @Insert("update shopping_cart set count=#{count} where wxUserId = #{wxUserId} and materialId= #{materialId} and status=0")
    Integer updateCart(AddCartOpenVo addCartOpenVo);

    @Select("select * from shopping_cart where wxUserId=#{wxUserId} and materialId=#{materialId} and status=0")
    AddCartOpenVo queryCart(AddCartOpenVo addCartOpenVo);

    @Select("select * from shopping_cart where wxUserId=#{wxUserId} and status=0 and count>0")
    List<AddCartOpenVo> queryCartList(AddCartOpenVo addCartOpenVo);

    @Select("select * from material where id in(${ids}) and status=0")
    List<MaterialVo> queryMaterialByIds(@Param("ids") String ids);


    @Select("select * from shopping_cart where wxUserId=#{wxUserId} and materialId in(${ids}) and status=0 and count>0")
    List<AddCartOpenVo> queryCartListByUserAndMaterialId(@Param("wxUserId") String wxUserId, @Param("ids") String ids);

    @Select("select count(id) as total from shopping_cart where wxUserId=#{wxUserId} and status=0 and count>0")
    Long queryCartTotal(@Param("wxUserId") String wxUserId);
}
