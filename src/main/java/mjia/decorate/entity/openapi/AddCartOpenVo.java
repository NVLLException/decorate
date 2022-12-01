package mjia.decorate.entity.openapi;

import lombok.Data;

@Data
public class AddCartOpenVo {
    private Integer id;
    private String openId;
    private String wxUserId;
    private String groupId;
    private String categoryId;
    private String materialId;
    private Integer count;
}