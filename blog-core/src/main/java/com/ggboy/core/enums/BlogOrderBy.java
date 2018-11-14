package com.ggboy.core.enums;

public enum BlogOrderBy {
    Time("最后修改时间", "modify_time"),
    View("阅读量", "view_count"),
    Favorite("收藏量", "favorite_count"),
    Weight("权重值", "weight"),
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
