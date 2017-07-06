package com.interview.pncp.dataservice.service;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.interview.pncp.dataservice.constants.CommonConstants;
import com.interview.pncp.dataservice.util.RestClientUtil;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import static com.interview.pncp.dataservice.constants.CommonConstants.ES_ENDPOINT;

/**
 * Created by michael.zhang on 7/5/17.
 */
public class ESService implements IESService{
    private RestClientUtil restClient;
    private String esEndpoint = ES_ENDPOINT;

    public ESService() {
        this.restClient = RestClientUtil.getInstance();
    }

    public <T, E> LinkedTreeMap<String, Object> index(String index, String type, String id, Map<T, E> paramData) {
        String requestUrl = esEndpoint + index + "/" + type + "/" + id;
        LinkedTreeMap<String, Object> response = this.restClient.processRequest(requestUrl, paramData, HttpMethod.POST, new TypeToken<LinkedTreeMap<String, Object>>(){});
        return response;
    }

    public LinkedTreeMap<String, Object> query(String index, String type, String keyword, String[] fields) {
        String requestUrl = esEndpoint + "/" + index + "/" + type + CommonConstants.ES_REST_API_PATH_SEARCH;
        Map paramData = new HashMap<>();
        paramData.put(CommonConstants.ES_QUERY_PARAM_KEY_QUERY, new HashMap(){{
            this.put(CommonConstants.ES_QUERY_PARAM_KEY_MULTI_MATCH, new HashMap(){{
                this.put(CommonConstants.ES_QUERY_PARAM_KEY_QUERY, keyword);
                this.put(CommonConstants.ES_QUERY_PARAM_KEY_TYPE, CommonConstants.ES_QUERY_PARAM_VALUE_PHRASE);
                this.put(CommonConstants.ES_QUERY_PARAM_KEY_SLOP, 0);
                this.put(CommonConstants.ES_QUERY_PARAM_KEY_FIELDS, fields);
            }});
        }});
        LinkedTreeMap<String, Object> response = this.restClient.processRequest(requestUrl, paramData, HttpMethod.GET, new TypeToken<LinkedTreeMap<String, Object>>(){});
        return response;
    }
}
