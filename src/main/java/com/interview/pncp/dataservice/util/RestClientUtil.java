package com.interview.pncp.dataservice.util;

/**
 * Created by michael.zhang on 7/5/17.
 */

import com.github.underscore.lodash.$;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.interview.pncp.dataservice.constants.CommonConstants;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RestClientUtil {
    private static RestClientUtil restClientUtil;
    private RestTemplate restTemplate;

    public static RestClientUtil getInstance() {
        if(restClientUtil == null) {
            synchronized (RestClientUtil.class) {
                if(restClientUtil == null) {
                    restClientUtil = new RestClientUtil();
                }
            }
        }
        return restClientUtil;
    }

    private RestClientUtil() {
        this.restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(httpRequestFactory);
    }

    public <T> T processRequest(String requestUrl, Object paramData, Map<String, String> httpHeaderAttributes, HttpMethod requestMethod, TypeToken typeToken) {
        HttpEntity requesEntity = this.createRequestEntity(paramData, httpHeaderAttributes);
        ResponseEntity<LinkedHashMap> responseEntity = this.restTemplate.exchange(requestUrl, requestMethod, requesEntity, LinkedHashMap.class);
        return this.parseSingleResult(responseEntity, typeToken);
    }

    public <T> T processRequest(String requestUrl, Object paramData, HttpMethod requestMethod, TypeToken typeToken) {
        return this.processRequest(requestUrl, paramData, null, requestMethod, typeToken);
    }

    public HttpEntity createRequestEntity(Object paramData, Map<String, String> httpHeaderMaps) {
        HttpHeaders httpHeaders = null;
        if(httpHeaderMaps != null) {
            httpHeaders = this.createHttpHeaders(httpHeaderMaps);
        } else {
            httpHeaders = this.createHttpHeaders(new HashMap<String, String>(){{
                this.put(CommonConstants.HTTP_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            }});
        }

        HttpEntity requestEntity = null;
        if (paramData == null) {
            requestEntity = new HttpEntity(httpHeaders);
        } else if(paramData instanceof JsonObject || paramData instanceof String) {
            requestEntity = new HttpEntity(paramData.toString(), httpHeaders);
        } else if(paramData instanceof Map){

            requestEntity = new HttpEntity($.toJson((Map) paramData), httpHeaders);
        }
        return requestEntity;
    }

    public HttpEntity createRequestEntity(Object paramData) {
        return this.createRequestEntity(paramData, new HashMap<String, String>(){{
            this.put(CommonConstants.HTTP_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }});
    }

    public HttpHeaders createHttpHeaders(Map<String, String> keyValues) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for(String key : keyValues.keySet()) {
            httpHeaders.add(key, keyValues.get(key));
        }
        return httpHeaders;
    }

    public <T> T parseSingleResult(ResponseEntity<LinkedHashMap> responseEntity, TypeToken typeToken) {
        LinkedHashMap map = responseEntity.getBody();

        if (map == null || typeToken == null) {
            return null;
        }

        Object targetObject = null;

        targetObject = map;
        if (targetObject == null) {
            return null;
        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(targetObject);

        T target = gson.fromJson(jsonStr, typeToken.getType());
        return target;
    }

}

