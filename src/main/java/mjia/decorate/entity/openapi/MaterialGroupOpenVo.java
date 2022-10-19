package mjia.decorate.entity.openapi;

import lombok.Data;
import mjia.decorate.entity.openapi.MaterialCategoryOpenVo;

import java.util.List;

@Data
public class MaterialGroupOpenVo {
    private String groupId;
    private String name;
    private List<MaterialCategoryOpenVo> children;
}
