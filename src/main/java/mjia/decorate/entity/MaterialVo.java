package mjia.decorate.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MaterialVo {
    private String Id;
    private String groupId;
    private String name;
    private String description;
    private String length;
    private String width;
    private String high;
    private BigDecimal price;
    private List<String> url;
    private String ext1;
    private String ext2;
    private String ext3;
}
