package mjia.decorate.entity.openapi;

import lombok.Data;

@Data
public class AddCartOpenVo {
    private Integer id;
    private String openId;
    private String wxUserId;
    private String materialId;
    private Integer count;
}