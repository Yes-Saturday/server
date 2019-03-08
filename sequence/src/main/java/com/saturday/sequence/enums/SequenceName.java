package com.saturday.sequence.enums;

public enum SequenceName {
    Blog("博客", "blog_sequence"),
    Category("分类", "category_sequence"),
    FileName("文件名", "file_name"),
    Default("默认", "system_base"),
    UserId("统一用户", "user_id_sequence")
    ;

    private String memo;
    private String name;

    SequenceName(String memo, String name) {
        this.memo = memo;
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
