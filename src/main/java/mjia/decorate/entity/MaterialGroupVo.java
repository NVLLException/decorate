package mjia.decorate.entity;

import lombok.Data;

import java.util.List;

@Data
public class MaterialGroupVo extends BaseVo{
    private String name;
    private List<String> urlList;
    private String ext1;
    private String ext2;
    private String ext3;
}
