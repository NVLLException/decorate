package mjia.decorate.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MaterialVo extends BaseVo{
    private String categoryId;
    private String name;
    private String description;
    private String length;
    private String width;
    private String high;
    private BigDecimal price;
    private Integer CartCount = 0;
    private List<String> urlList;
    private List<UrlVo> urlVoList;
    private String ext1;
    private String ext2;
    private String ext3;
}
