package com.saturday.blog.enums;

public enum BlogOrderBy {
    Time("最后修改时间", "blog_basics.modify_time"),
    CreateTime("创建时间", "blog_basics.create_time"),
    View("阅读量", "blog_basics.view_count"),
    Weight("权重值", "blog_basics.weight"),
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
