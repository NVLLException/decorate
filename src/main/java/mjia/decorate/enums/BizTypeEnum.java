package mjia.decorate.enums;

public enum BizTypeEnum {
    QUERY_MATERIAL("1000", "查询图片列表"),
    QUERY_CATEGORY("1001", "查询分类列表"),
    QUERY_GROUP("1002", "查询图片分组"),
    ;

    private String code;
    private String desc;

    BizTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return desc;
    }

}
