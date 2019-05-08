package com.saturday.sequence.enums;

public enum SequenceName {
    BlogId("BL10", "blog_sequence"),
    Category("FL11", "category_sequence"),
    FileName("file_", "file_name"),
    Default(null, "system_base"),
    UserId("US103", "user_id_sequence")
    ;

    private String code;
    private String name;

    SequenceName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return name;
    }
}
