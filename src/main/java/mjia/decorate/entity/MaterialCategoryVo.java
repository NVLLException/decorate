package mjia.decorate.entity;

import lombok.Data;

import java.util.List;

@Data
public class MaterialCategoryVo extends BaseVo{
    private String groupId;
    private String name;
    private List<String> urlList;
    private List<UrlVo> urlVoList;
    private String ext1;
    private String ext2;
    private String ext3;
}
