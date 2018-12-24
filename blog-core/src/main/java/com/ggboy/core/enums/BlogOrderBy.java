package com.ggboy.core.enums;

public enum BlogOrderBy {
    Time("最后修改时间", "blog.modify_time"),
    CreateTime("创建时间", "blog.create_time"),
    View("阅读量", "blog.view_count"),
    Weight("权重值", "blog.weight"),
    ;

    private String name;
    private String column;

    BlogOrderBy(String name, String column) {
        this.name = name;
        this.column = column;
    }

    public String desc() {
        return column + " desc";
    }

    public String asc() {
        return column + " asc";
    }
}
