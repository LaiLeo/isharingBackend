package com.fih.ishareing.controller.base.vo.result;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class ApiBatchResult {
    private final Builder<Object> builder;

    public ApiBatchResult() {
        this.builder = new ImmutableList.Builder<Object>();
    }

    public ApiBatchResult add(Object element) {
        this.builder.add(element);
        return this;
    }

    public List<Object> build() {
        return this.builder.build();
    }
}
