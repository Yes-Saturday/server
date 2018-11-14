package com.ggboy.system.enums;

public enum KeyType {
    Blog("博客", "13"),
    Category("分类", "60")
    ;
    private String name;
    private String falg;

    KeyType(String name, String falg) {
        this.name = name;
        this.falg = falg;
    }

    public String getFalg() {
        return falg;
    }
}
