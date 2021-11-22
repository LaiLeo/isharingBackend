package com.fih.ishareing.controller.base.vaild;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.google.common.collect.ForwardingList;

public class ValidList<T> extends ForwardingList<T> {

    private List<@Valid T> list;

    public ValidList() {
        this(new ArrayList<>());
    }

    public ValidList(List<@Valid T> list) {
        this.list = list;
    }

    @Override
    protected List<T> delegate() {
        return list;
    }

    /**
     * Exposed for the {@link javax.validation.Validator} to access the list path
     */
    public List<T> getList() {
        return list;
    }
}