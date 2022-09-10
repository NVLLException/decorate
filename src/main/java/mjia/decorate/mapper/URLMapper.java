package mjia.decorate.mapper;

import mjia.decorate.entity.UrlVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface URLMapper {
    @Select("select * from material_url where referId in(${referIds}) and type=#{type}")
    List<UrlVo> queryListByReferIdAndType(String referIds, String type);
}
