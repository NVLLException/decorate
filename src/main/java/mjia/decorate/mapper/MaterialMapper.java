package mjia.decorate.mapper;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaterialMapper {
    @Insert("insert into material(categoryId,name,description,price,length,width,high,creatorId,createTime,updaterId,updateTime)" +
            "values(#{categoryId},#{name},#{description},#{price},#{length},#{width},#{high},#{creatorId},now(),#{updaterId},now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertMaterial(MaterialVo material);

    @Update("update material set name=#{name},description=#{description},price=#{price},length=#{length},width=#{width},high=#{high},updaterId=#{updaterId},updateTime=now()" +
            " where id=#{id}")
    Integer updateMaterial(MaterialVo material);

    @Select("select * from material where id=#{id}")
    MaterialVo queryMaterialById(String id);

    @Select("select * from material where categoryId=#{categoryId}")
    List<MaterialVo> queryMaterialList(String categoryId);

    @Select("select *from material_category where groupId=#{groupId}")
    List<MaterialCategoryVo> listMaterialCategoryByGroupId(String groupId);
}
