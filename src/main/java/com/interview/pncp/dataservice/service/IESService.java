package com.interview.pncp.dataservice.service;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

/**
 * Created by michael.zhang on 7/5/17.
 */
public interface IESService {
    <T, E> LinkedTreeMap<String, Object> index(String index, String type, String id, Map<T, E> paramData);
    LinkedTreeMap<String, Object> query(String index, String type, String keyword, String[] fields);
}
