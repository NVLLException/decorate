package mjia.decorate.enums;

public enum BizTypeEnum {
    QUERY_MATERIAL("1000", "查询图片列表"),
    QUERY_CATEGORY("1001", "查询分类列表"),
    QUERY_GROUP("1002", "查询图片分组"),
    QUERY_MATERIAL_DETAIL("1010", "查询图片"),
    BACK_QUERY_MATERIAL("1003", "后台查询图片列表"),
    BACK_QUERY_CATEGORY("1004", "后台查询分类列表"),
    BACK_QUERY_GROUP("1005", "后台查询图片分组"),
    BACK_QUERY_CATEGORY_DETAIL("1007", "后台查询分类"),
    BACK_QUERY_MATERIAL_DETAIL("1008", "后台查询材料"),


    SAVE_MATERIAL("2000", "保存材料"),
    SAVE_CATEGORY("2001", "保存分类"),
    SAVE_GROUP("2002", "保存分组"),

    DELETE_MATERIAL("3000", "删除材料"),
    DELETE_CATEGORY("3001", "删除分类"),
    DELETE_GROUP("3002", "删除分组"),
    DELETE_URL("3003", "删除图片"),

    FILE_EMPTY("10000", "上传图片为空"),
    SAVE_FILE("10001", "保存图片"),

    LOGIN_USER("11000", "管理员登录"),
    CUSTOMER_USER("11001", "微信登录"),

    ADD_SHOP_CART("20000", "添加到购物车"),
    REMOVE_SHOP_CART("200001", "移除购物车"),
    QUERY_SHOP_CART_LIST("200002", "查询购物车列表"),

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
