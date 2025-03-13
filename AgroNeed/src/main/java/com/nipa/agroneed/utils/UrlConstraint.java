package com.nipa.agroneed.utils;

public final class UrlConstraint {
    public UrlConstraint() {
    }
    public static class Categories {
        public static final String ROOT = "/Categories";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
        public static final String GET_BY_ID = "/get/{id}";
        public static final String GET_BY_ParentId = "/get/{parentId}";
        public static final String DELETE_BY_ID = "/delete/{id}";
        public static final String EDIT_BY_ID = "/edit/{id}";

    }
    public static class Products{

    }
}
