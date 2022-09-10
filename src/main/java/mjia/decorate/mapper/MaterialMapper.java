package mjia.decorate.mapper;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.UrlVo;
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

    @Insert("insert into material_category(groupId,name,creatorId,createTime,updaterId,updateTime) " +
            "values(#{groupId},#{name},#{creatorId},now(),#{updaterId},now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertCategory(MaterialCategoryVo categoryVo);

    @Insert("insert into material_url(fileName,referId,type,remoteUrl,localUrl,creatorId,createTime,updaterId,updateTime)" +
            "values(#{fileName},#{referId},#{type},#{remoteUrl},#{localUrl},#{creatorId},now(),#{updaterId},now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertUrl(UrlVo urlVo);

    @Update("update material_category set name=#{name},updaterId=#{updaterId},updateTime=now() where id=#{id}")
    Integer updateCategory(MaterialCategoryVo categoryVo);

    @Insert("insert into material_group(name) values(#{name})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertGroup(MaterialGroupVo groupVo);

    @Update("update material_group set name=#{name},updaterId=#{updaterId},updateTime=now() where id=#{id}")
    Integer updateGroup(MaterialGroupVo groupVo);

    @Update("update material set status=-1 where id in(${ids})")
    Integer deleteMaterials(String ids);

    @Update("update material_category set status=-1 where id in(${ids})")
    Integer deleteCategorys(String ids);

    @Update("update material_group set status=-1 where id in(${ids})")
    Integer deleteGroups(String ids);

    @Select("select * from material where id=#{id} and status=0")
    MaterialVo queryMaterialById(String id);

    @Select("select * from material where categoryId=#{categoryId} and status=0")
    List<MaterialVo> queryMaterialList(String categoryId);

    @Select("select * from material_category where groupId=#{groupId} and status=0")
    List<MaterialCategoryVo> listMaterialCategoryByGroupId(String groupId);

    @Select("select * from material_group and status=0")
    List<MaterialGroupVo> listGroup();

    @Select("select id from material_url where referId in(${referIds}) and type=#{type} and status=0")
    List<String> queryUrlIds(String referIds, String type);

    @Select("select id from material where categoryId in(${categoryIds}) and status=0")
    List<String> queryMaterialIdsByCategoryId(String categoryIds);

    @Select("select id from material_category where groupId in(${groupIds}) and status=0")
    List<String> queryCategoryIdsByGroupId(String groupIds);
}
