package mjia.decorate.entity;

import lombok.Data;

@Data
public class BaseVo {
    private String id;
    private String creatorId;
    private String createTime;
    private String updaterId;
    private String updateTime;
}
