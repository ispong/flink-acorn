package com.isxcode.acorn.common.pojo.model;

public class AcornModelBuilder {

    public static AcornModelBuilder.Builder build() {

        return new AcornModelBuilder.Builder();
    }

    public static class Builder {

        public Class<AcornModel1> mode1() {
            return AcornModel1.class;
        }
    }
}
