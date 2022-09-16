package mjia.decorate.enums;

public enum URLTypeEnum {

    MATERIAL("1", "material"),
    CATEGORY("2", "category"),
    GROUP("3", "group"),
    ;

    private String code;
    private String desc;

    URLTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return desc;
    }

    public static URLTypeEnum getByCode(String code) {
        for(URLTypeEnum type : URLTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
