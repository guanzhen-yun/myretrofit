package com.inke.library;

abstract class ParameterHandler {

    abstract void apply(RequestBuilder builder, String value);

    static final class Query extends ParameterHandler {
        private final String name;
        Query(String name) {
            if(name.isEmpty()) {
                throw new IllegalArgumentException("name = null");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if(value == null) return;
            builder.addQueryParam(name, value);
        }
    }

    static final class Field extends ParameterHandler {
        private final String name;
        //注意：传过来的是注解的值，并非参数值
        Field(String name) {
            if(name.isEmpty()) {
                throw new IllegalArgumentException("name = null");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if(value == null) return;
            //拼接Field参数，此处name为参数注解的值，value为参数值
            builder.addFormField(name, value);
        }
    }
}
