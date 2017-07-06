package com.interview.pncp.dataservice.constants;

/**
 * Created by michael.zhang on 7/5/17.
 */
public class CommonConstants {
    public static final String HTTP_CONTENT_TYPE = "Content-Type";
    public static final String ES_ENDPOINT = "http://search-es-npcp-data-w7j3gycqonfwt3q4tt673ahsvm.us-west-1.es.amazonaws.com/";
    public static final String ES_QUERY_PARAM_KEY_QUERY = "query";
    public static final String ES_QUERY_PARAM_KEY_MULTI_MATCH = "multi_match";
    public static final String ES_QUERY_PARAM_KEY_SLOP = "slop";
    public static final String ES_QUERY_PARAM_KEY_FIELDS = "fields";
    public static final String ES_QUERY_PARAM_KEY_TYPE = "type";
    public static final String ES_QUERY_PARAM_KEY_INDEX = "index";
    public static final String ES_QUERY_PARAM_KEY_KEYWORD = "keyword";
    public static final String ES_QUERY_PARAM_VALUE_PHRASE = "phrase";
    public static final String ES_RESPONSE_KEY = "hits";

    public static final String ES_REST_API_PATH_SEARCH = "/_search";
    public static final String CSV_LOADER_CONFIG_PARAM_INDEX = "pncp";
    public static final String CSV_LOADER_CONFIG_PARAM_TYPE = "finance";
    public static final String CSV_LOADER_CONFIG_PARAM_FILE_PATH = "/data/f_5500_2016_latest.csv";
    public static final String CSV_LOADER_CONFIG_PARAM_ID_COLUMN_NAME = "ACK_ID";
}
