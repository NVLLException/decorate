package mjia.decorate.entity;

import lombok.Data;

import java.util.List;

@Data
public class MaterialGroupOpenVo {
    private String groupId;
    private String name;
    private List<MaterialCategoryOpenVo> children;
}
