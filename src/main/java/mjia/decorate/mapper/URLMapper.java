package mjia.decorate.mapper;

import mjia.decorate.entity.UrlVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface URLMapper {
    @Select("select * from material_url where referId in(${referIds}) and type=#{type} and status=0")
    List<UrlVo> queryListByReferIdAndType(String referIds, String type);

    @Insert("insert into material_url(fileName,referId,type,remoteUrl,localUrl,creatorId,createTime,updaterId,updateTime) values" +
            "(#{fileName},#{referId},#{type},#{remoteUrl},#{localUrl},#{creatorId},now(),#{updaterId},now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer insertUrl(UrlVo urlVo);

    @Update("update material_url set status=-1 where referId in(${referIds}) and type=#{type}")
    Integer deleteUrl(String referIds, String type);

    @Update("update material_url set status=-1 where id=#{id}")
    Integer deleteUrlById(String id);
}
