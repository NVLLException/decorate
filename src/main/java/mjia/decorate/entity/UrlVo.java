package mjia.decorate.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlVo extends BaseVo{
    private String id;
    private String fileName;
    private String referId;
    //URLTypeEnum
    private String type;
    private String remoteUrl;
    private String localUrl;
}
