package mjia.decorate.entity.openapi;

import lombok.Data;

import java.util.List;

@Data
public class MaterialDetailOpenVo {
    private String id;
    private String title;
    private String desc;
    private String price;
    private String high;
    private String width;
    private String length;
    private List<String> images;
}