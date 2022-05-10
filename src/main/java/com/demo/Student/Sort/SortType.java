package com.demo.Student.Sort;

public enum SortType {
    BUBBLE("bubble"),
    MERGE("merge"),
    HEAP("heap");

    private String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortType() {
        return sortType;
    }
}
