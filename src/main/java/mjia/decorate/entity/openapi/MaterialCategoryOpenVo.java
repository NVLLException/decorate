package mjia.decorate.entity.openapi;

import lombok.Data;

import java.util.List;

@Data
public class MaterialCategoryOpenVo{
    private String groupId;
    private String name;
    private String thumbnail;
    private List<MaterialOpenVo> children;
}
