package mjia.decorate.entity.openapi;

import lombok.Data;

import java.util.List;

@Data
public class MaterialGroupParentOpenVo {
    private String groupId;
    private String name;
    private List<MaterialGroupOpenVo> children;
}
