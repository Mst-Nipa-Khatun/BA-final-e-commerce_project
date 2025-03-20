package com.nipa.agroneed.utils;

public final class UrlConstraint {
    public UrlConstraint() {
    }
    public static class Categories {
        public static final String ROOT = "/Categories";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
        public static final String GET_ALL_PARENT_CATEGORY = "/getAllParentCategory";
        public static final String GET_BY_ID = "/getById/{id}";
        public static final String GET_BY_ParentId = "/getByParentId/{parentId}";
        public static final String DELETE_BY_ID = "/delete/{id}";
        public static final String EDIT_BY_ID = "/edit/{id}";

    }
    public static class Products{
        public static final String ROOT = "/Products";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";

    }
    public static class Users{
        public static final String ROOT = "/Users";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
    }
}
